package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Map;

public final class UdpDataSource extends BaseDataSource {
    public final int e;
    public final byte[] f;
    public final DatagramPacket g;
    @Nullable
    public Uri h;
    @Nullable
    public DatagramSocket i;
    @Nullable
    public MulticastSocket j;
    @Nullable
    public InetAddress k;
    @Nullable
    public InetSocketAddress l;
    public boolean m;
    public int n;

    public static final class UdpDataSourceException extends IOException {
        public UdpDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public UdpDataSource() {
        this(2000);
    }

    public void close() {
        this.h = null;
        MulticastSocket multicastSocket = this.j;
        if (multicastSocket != null) {
            try {
                multicastSocket.leaveGroup(this.k);
            } catch (IOException unused) {
            }
            this.j = null;
        }
        DatagramSocket datagramSocket = this.i;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.i = null;
        }
        this.k = null;
        this.l = null;
        this.n = 0;
        if (this.m) {
            this.m = false;
            b();
        }
    }

    public int getLocalPort() {
        DatagramSocket datagramSocket = this.i;
        if (datagramSocket == null) {
            return -1;
        }
        return datagramSocket.getLocalPort();
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return this.h;
    }

    public long open(DataSpec dataSpec) throws UdpDataSourceException {
        Uri uri = dataSpec.a;
        this.h = uri;
        String host = uri.getHost();
        int port = this.h.getPort();
        c(dataSpec);
        try {
            this.k = InetAddress.getByName(host);
            this.l = new InetSocketAddress(this.k, port);
            if (this.k.isMulticastAddress()) {
                MulticastSocket multicastSocket = new MulticastSocket(this.l);
                this.j = multicastSocket;
                multicastSocket.joinGroup(this.k);
                this.i = this.j;
            } else {
                this.i = new DatagramSocket(this.l);
            }
            try {
                this.i.setSoTimeout(this.e);
                this.m = true;
                d(dataSpec);
                return -1;
            } catch (SocketException e2) {
                throw new UdpDataSourceException(e2);
            }
        } catch (IOException e3) {
            throw new UdpDataSourceException(e3);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws UdpDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        int i4 = this.n;
        DatagramPacket datagramPacket = this.g;
        if (i4 == 0) {
            try {
                this.i.receive(datagramPacket);
                int length = datagramPacket.getLength();
                this.n = length;
                a(length);
            } catch (IOException e2) {
                throw new UdpDataSourceException(e2);
            }
        }
        int length2 = datagramPacket.getLength();
        int i5 = this.n;
        int min = Math.min(i5, i3);
        System.arraycopy(this.f, length2 - i5, bArr, i2, min);
        this.n -= min;
        return min;
    }

    public UdpDataSource(int i2) {
        this(i2, 8000);
    }

    public UdpDataSource(int i2, int i3) {
        super(true);
        this.e = i3;
        byte[] bArr = new byte[i2];
        this.f = bArr;
        this.g = new DatagramPacket(bArr, 0, i2);
    }
}
