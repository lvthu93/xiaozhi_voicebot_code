package defpackage;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/* renamed from: dd  reason: default package */
public final class dd implements Runnable {
    public final /* synthetic */ zc c;

    public dd(zc zcVar) {
        this.c = zcVar;
    }

    public final void run() {
        zc zcVar = this.c;
        zcVar.getClass();
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1400], 1400);
        Log.d("UdpAudioChannel", "UDP audio receiver started");
        while (zcVar.k && zcVar.j) {
            try {
                zcVar.h.receive(datagramPacket);
                zcVar.c(Arrays.copyOf(datagramPacket.getData(), datagramPacket.getLength()));
                zcVar.m++;
                datagramPacket.getLength();
            } catch (IOException | SocketTimeoutException unused) {
            }
        }
        Log.d("UdpAudioChannel", "UDP audio receiver stopped");
    }
}
