package org.java_websocket.extensions.permessage_deflate;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.java_websocket.enums.Opcode;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.extensions.CompressionExtension;
import org.java_websocket.extensions.ExtensionRequestData;
import org.java_websocket.extensions.IExtension;
import org.java_websocket.framing.ContinuousFrame;
import org.java_websocket.framing.DataFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

public class PerMessageDeflateExtension extends CompressionExtension {
    private static final int BUFFER_SIZE = 1024;
    private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";
    private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
    private static final String EXTENSION_REGISTERED_NAME = "permessage-deflate";
    private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
    private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
    private static final byte[] TAIL_BYTES = {0, 0, -1, -1};
    private static final int clientMaxWindowBits = 32768;
    private static final int serverMaxWindowBits = 32768;
    private boolean clientNoContextTakeover = false;
    private Deflater deflater = new Deflater(-1, true);
    private Inflater inflater = new Inflater(true);
    private Map<String, String> requestedParameters = new LinkedHashMap();
    private boolean serverNoContextTakeover = true;
    private int threshold = 1024;

    private void decompress(byte[] bArr, ByteArrayOutputStream byteArrayOutputStream) throws DataFormatException {
        this.inflater.setInput(bArr);
        byte[] bArr2 = new byte[1024];
        while (true) {
            int inflate = this.inflater.inflate(bArr2);
            if (inflate > 0) {
                byteArrayOutputStream.write(bArr2, 0, inflate);
            } else {
                return;
            }
        }
    }

    private static boolean endsWithTail(byte[] bArr) {
        if (bArr.length < 4) {
            return false;
        }
        int length = bArr.length;
        int i = 0;
        while (true) {
            byte[] bArr2 = TAIL_BYTES;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr2[i] != bArr[(length - bArr2.length) + i]) {
                return false;
            }
            i++;
        }
    }

    public boolean acceptProvidedExtensionAsClient(String str) {
        String[] split = str.split(",");
        int length = split.length;
        int i = 0;
        while (i < length) {
            ExtensionRequestData parseExtensionRequest = ExtensionRequestData.parseExtensionRequest(split[i]);
            if (!EXTENSION_REGISTERED_NAME.equalsIgnoreCase(parseExtensionRequest.getExtensionName())) {
                i++;
            } else {
                parseExtensionRequest.getExtensionParameters();
                return true;
            }
        }
        return false;
    }

    public boolean acceptProvidedExtensionAsServer(String str) {
        String[] split = str.split(",");
        int length = split.length;
        int i = 0;
        while (i < length) {
            ExtensionRequestData parseExtensionRequest = ExtensionRequestData.parseExtensionRequest(split[i]);
            if (!EXTENSION_REGISTERED_NAME.equalsIgnoreCase(parseExtensionRequest.getExtensionName())) {
                i++;
            } else {
                this.requestedParameters.putAll(parseExtensionRequest.getExtensionParameters());
                if (this.requestedParameters.containsKey(CLIENT_NO_CONTEXT_TAKEOVER)) {
                    this.clientNoContextTakeover = true;
                }
                return true;
            }
        }
        return false;
    }

    public IExtension copyInstance() {
        return new PerMessageDeflateExtension();
    }

    public void decodeFrame(Framedata framedata) throws InvalidDataException {
        if (framedata instanceof DataFrame) {
            if (!framedata.isRSV1() && framedata.getOpcode() != Opcode.CONTINUOUS) {
                return;
            }
            if (framedata.getOpcode() != Opcode.CONTINUOUS || !framedata.isRSV1()) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    decompress(framedata.getPayloadData().array(), byteArrayOutputStream);
                    if (this.inflater.getRemaining() > 0) {
                        this.inflater = new Inflater(true);
                        decompress(framedata.getPayloadData().array(), byteArrayOutputStream);
                    }
                    if (framedata.isFin()) {
                        decompress(TAIL_BYTES, byteArrayOutputStream);
                        if (this.clientNoContextTakeover) {
                            this.inflater = new Inflater(true);
                        }
                    }
                    ((FramedataImpl1) framedata).setPayload(ByteBuffer.wrap(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size()));
                } catch (DataFormatException e) {
                    throw new InvalidDataException(1008, e.getMessage());
                }
            } else {
                throw new InvalidDataException(1008, "RSV1 bit can only be set for the first frame.");
            }
        }
    }

    public void encodeFrame(Framedata framedata) {
        if (framedata instanceof DataFrame) {
            byte[] array = framedata.getPayloadData().array();
            if (array.length >= this.threshold) {
                if (!(framedata instanceof ContinuousFrame)) {
                    ((DataFrame) framedata).setRSV1(true);
                }
                this.deflater.setInput(array);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int deflate = this.deflater.deflate(bArr, 0, 1024, 2);
                    if (deflate <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, deflate);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                int length = byteArray.length;
                if (framedata.isFin()) {
                    if (endsWithTail(byteArray)) {
                        length -= TAIL_BYTES.length;
                    }
                    if (this.serverNoContextTakeover) {
                        this.deflater.end();
                        this.deflater = new Deflater(-1, true);
                    }
                }
                ((FramedataImpl1) framedata).setPayload(ByteBuffer.wrap(byteArray, 0, length));
            }
        }
    }

    public Deflater getDeflater() {
        return this.deflater;
    }

    public Inflater getInflater() {
        return this.inflater;
    }

    public String getProvidedExtensionAsClient() {
        this.requestedParameters.put(CLIENT_NO_CONTEXT_TAKEOVER, "");
        this.requestedParameters.put(SERVER_NO_CONTEXT_TAKEOVER, "");
        return "permessage-deflate; server_no_context_takeover; client_no_context_takeover";
    }

    public String getProvidedExtensionAsServer() {
        return "permessage-deflate; server_no_context_takeover".concat(this.clientNoContextTakeover ? "; client_no_context_takeover" : "");
    }

    public int getThreshold() {
        return this.threshold;
    }

    public boolean isClientNoContextTakeover() {
        return this.clientNoContextTakeover;
    }

    public void isFrameValid(Framedata framedata) throws InvalidDataException {
        if (!(framedata instanceof ContinuousFrame) || (!framedata.isRSV1() && !framedata.isRSV2() && !framedata.isRSV3())) {
            super.isFrameValid(framedata);
            return;
        }
        throw new InvalidFrameException("bad rsv RSV1: " + framedata.isRSV1() + " RSV2: " + framedata.isRSV2() + " RSV3: " + framedata.isRSV3());
    }

    public boolean isServerNoContextTakeover() {
        return this.serverNoContextTakeover;
    }

    public void setClientNoContextTakeover(boolean z) {
        this.clientNoContextTakeover = z;
    }

    public void setDeflater(Deflater deflater2) {
        this.deflater = deflater2;
    }

    public void setInflater(Inflater inflater2) {
        this.inflater = inflater2;
    }

    public void setServerNoContextTakeover(boolean z) {
        this.serverNoContextTakeover = z;
    }

    public void setThreshold(int i) {
        this.threshold = i;
    }

    public String toString() {
        return "PerMessageDeflateExtension";
    }
}
