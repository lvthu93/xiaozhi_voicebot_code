package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.Util;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Deprecated
public final class a {
    public final AtomicFile a;

    public a(File file) {
        this.a = new AtomicFile(file);
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0109  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.offline.DownloadRequest a(java.io.DataInputStream r18) throws java.io.IOException {
        /*
            java.lang.String r0 = r18.readUTF()
            int r1 = r18.readInt()
            java.lang.String r2 = r18.readUTF()
            android.net.Uri r2 = android.net.Uri.parse(r2)
            boolean r3 = r18.readBoolean()
            int r4 = r18.readInt()
            if (r4 == 0) goto L_0x0022
            byte[] r4 = new byte[r4]
            r6 = r18
            r6.readFully(r4)
            goto L_0x0025
        L_0x0022:
            r6 = r18
            r4 = 0
        L_0x0025:
            java.lang.String r9 = "progressive"
            if (r1 != 0) goto L_0x0031
            boolean r10 = r9.equals(r0)
            if (r10 == 0) goto L_0x0031
            r10 = 1
            goto L_0x0032
        L_0x0031:
            r10 = 0
        L_0x0032:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.lang.String r12 = "ss"
            java.lang.String r13 = "hls"
            if (r10 != 0) goto L_0x007a
            int r10 = r18.readInt()
            r14 = 0
        L_0x0042:
            if (r14 >= r10) goto L_0x007a
            boolean r15 = r13.equals(r0)
            if (r15 != 0) goto L_0x0050
            boolean r15 = r12.equals(r0)
            if (r15 == 0) goto L_0x005f
        L_0x0050:
            if (r1 != 0) goto L_0x005f
            int r15 = r18.readInt()
            int r16 = r18.readInt()
            r5 = r15
            r8 = r16
            r15 = 0
            goto L_0x006f
        L_0x005f:
            int r15 = r18.readInt()
            int r16 = r18.readInt()
            int r17 = r18.readInt()
            r5 = r16
            r8 = r17
        L_0x006f:
            com.google.android.exoplayer2.offline.StreamKey r7 = new com.google.android.exoplayer2.offline.StreamKey
            r7.<init>(r15, r5, r8)
            r11.add(r7)
            int r14 = r14 + 1
            goto L_0x0042
        L_0x007a:
            r5 = 2
            java.lang.String r7 = "dash"
            if (r1 >= r5) goto L_0x0093
            boolean r8 = r7.equals(r0)
            if (r8 != 0) goto L_0x0091
            boolean r8 = r13.equals(r0)
            if (r8 != 0) goto L_0x0091
            boolean r8 = r12.equals(r0)
            if (r8 == 0) goto L_0x0093
        L_0x0091:
            r8 = 1
            goto L_0x0094
        L_0x0093:
            r8 = 0
        L_0x0094:
            if (r8 != 0) goto L_0x00a8
            boolean r8 = r18.readBoolean()
            if (r8 == 0) goto L_0x00a3
            java.lang.String r8 = r18.readUTF()
            r16 = r8
            goto L_0x00a5
        L_0x00a3:
            r16 = 0
        L_0x00a5:
            r8 = r16
            goto L_0x00a9
        L_0x00a8:
            r8 = 0
        L_0x00a9:
            r10 = 3
            if (r1 >= r10) goto L_0x00b5
            if (r8 == 0) goto L_0x00b0
            r1 = r8
            goto L_0x00b9
        L_0x00b0:
            java.lang.String r1 = r2.toString()
            goto L_0x00b9
        L_0x00b5:
            java.lang.String r1 = r18.readUTF()
        L_0x00b9:
            if (r3 != 0) goto L_0x0120
            com.google.android.exoplayer2.offline.DownloadRequest$Builder r3 = new com.google.android.exoplayer2.offline.DownloadRequest$Builder
            r3.<init>(r1, r2)
            int r1 = r0.hashCode()
            r2 = 3680(0xe60, float:5.157E-42)
            if (r1 == r2) goto L_0x00f0
            r2 = 103407(0x193ef, float:1.44904E-40)
            if (r1 == r2) goto L_0x00e8
            r2 = 3075986(0x2eef92, float:4.310374E-39)
            if (r1 == r2) goto L_0x00e0
            r2 = 1131547531(0x43720b8b, float:242.04509)
            if (r1 == r2) goto L_0x00d8
            goto L_0x00f8
        L_0x00d8:
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x00f8
            r0 = 3
            goto L_0x00f9
        L_0x00e0:
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x00f8
            r0 = 0
            goto L_0x00f9
        L_0x00e8:
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x00f8
            r0 = 1
            goto L_0x00f9
        L_0x00f0:
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00f8
            r0 = 2
            goto L_0x00f9
        L_0x00f8:
            r0 = -1
        L_0x00f9:
            if (r0 == 0) goto L_0x0109
            r1 = 1
            if (r0 == r1) goto L_0x0106
            if (r0 == r5) goto L_0x0103
            java.lang.String r0 = "video/x-unknown"
            goto L_0x010b
        L_0x0103:
            java.lang.String r0 = "application/vnd.ms-sstr+xml"
            goto L_0x010b
        L_0x0106:
            java.lang.String r0 = "application/x-mpegURL"
            goto L_0x010b
        L_0x0109:
            java.lang.String r0 = "application/dash+xml"
        L_0x010b:
            com.google.android.exoplayer2.offline.DownloadRequest$Builder r0 = r3.setMimeType(r0)
            com.google.android.exoplayer2.offline.DownloadRequest$Builder r0 = r0.setStreamKeys(r11)
            com.google.android.exoplayer2.offline.DownloadRequest$Builder r0 = r0.setCustomCacheKey(r8)
            com.google.android.exoplayer2.offline.DownloadRequest$Builder r0 = r0.setData(r4)
            com.google.android.exoplayer2.offline.DownloadRequest r0 = r0.build()
            return r0
        L_0x0120:
            com.google.android.exoplayer2.offline.DownloadRequest$UnsupportedRequestException r0 = new com.google.android.exoplayer2.offline.DownloadRequest$UnsupportedRequestException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.a.a(java.io.DataInputStream):com.google.android.exoplayer2.offline.DownloadRequest");
    }

    public void delete() {
        this.a.delete();
    }

    public boolean exists() {
        return this.a.exists();
    }

    public DownloadRequest[] load() throws IOException {
        if (!exists()) {
            return new DownloadRequest[0];
        }
        InputStream inputStream = null;
        try {
            inputStream = this.a.openRead();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int readInt = dataInputStream.readInt();
            if (readInt <= 0) {
                int readInt2 = dataInputStream.readInt();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < readInt2; i++) {
                    try {
                        arrayList.add(a(dataInputStream));
                    } catch (DownloadRequest.UnsupportedRequestException unused) {
                    }
                }
                return (DownloadRequest[]) arrayList.toArray(new DownloadRequest[0]);
            }
            StringBuilder sb = new StringBuilder(44);
            sb.append("Unsupported action file version: ");
            sb.append(readInt);
            throw new IOException(sb.toString());
        } finally {
            Util.closeQuietly((Closeable) inputStream);
        }
    }
}
