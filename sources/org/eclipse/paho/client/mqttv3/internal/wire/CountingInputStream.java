package org.eclipse.paho.client.mqttv3.internal.wire;

import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CountingInputStream extends InputStream implements InputStreamRetargetInterface {
    private int counter = 0;
    private InputStream in;

    public CountingInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public int getCounter() {
        return this.counter;
    }

    public int read() throws IOException {
        int read = this.in.read();
        if (read != -1) {
            this.counter++;
        }
        return read;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public final /* synthetic */ long transferTo(OutputStream outputStream) {
        return DesugarInputStream.transferTo(this, outputStream);
    }
}
