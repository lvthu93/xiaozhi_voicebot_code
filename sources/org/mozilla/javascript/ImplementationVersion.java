package org.mozilla.javascript;

public class ImplementationVersion {
    private static final ImplementationVersion version = new ImplementationVersion();
    private String versionString;

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0071, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0072, code lost:
        if (r2 != null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007c, code lost:
        throw r4;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private ImplementationVersion() {
        /*
            r6 = this;
            java.lang.String r0 = " "
            r6.<init>()
            java.lang.Class<org.mozilla.javascript.ImplementationVersion> r1 = org.mozilla.javascript.ImplementationVersion.class
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ IOException -> 0x007f }
            java.lang.String r2 = "META-INF/MANIFEST.MF"
            java.util.Enumeration r1 = r1.getResources(r2)     // Catch:{ IOException -> 0x007f }
        L_0x0011:
            boolean r2 = r1.hasMoreElements()
            if (r2 == 0) goto L_0x007f
            java.lang.Object r2 = r1.nextElement()
            java.net.URL r2 = (java.net.URL) r2
            java.io.InputStream r2 = r2.openStream()     // Catch:{ IOException -> 0x007d }
            java.util.jar.Manifest r3 = new java.util.jar.Manifest     // Catch:{ all -> 0x006f }
            r3.<init>(r2)     // Catch:{ all -> 0x006f }
            java.util.jar.Attributes r3 = r3.getMainAttributes()     // Catch:{ all -> 0x006f }
            java.lang.String r4 = "Mozilla Rhino"
            java.lang.String r5 = "Implementation-Title"
            java.lang.String r5 = r3.getValue(r5)     // Catch:{ all -> 0x006f }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x006f }
            if (r4 == 0) goto L_0x0069
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x006f }
            r4.<init>()     // Catch:{ all -> 0x006f }
            java.lang.String r5 = "Rhino "
            r4.append(r5)     // Catch:{ all -> 0x006f }
            java.lang.String r5 = "Implementation-Version"
            java.lang.String r5 = r3.getValue(r5)     // Catch:{ all -> 0x006f }
            r4.append(r5)     // Catch:{ all -> 0x006f }
            r4.append(r0)     // Catch:{ all -> 0x006f }
            java.lang.String r5 = "Built-Date"
            java.lang.String r3 = r3.getValue(r5)     // Catch:{ all -> 0x006f }
            java.lang.String r5 = "-"
            java.lang.String r3 = r3.replaceAll(r5, r0)     // Catch:{ all -> 0x006f }
            r4.append(r3)     // Catch:{ all -> 0x006f }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x006f }
            r6.versionString = r3     // Catch:{ all -> 0x006f }
            if (r2 == 0) goto L_0x0068
            r2.close()     // Catch:{ IOException -> 0x007d }
        L_0x0068:
            return
        L_0x0069:
            if (r2 == 0) goto L_0x0011
            r2.close()     // Catch:{ IOException -> 0x007d }
            goto L_0x0011
        L_0x006f:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0071 }
        L_0x0071:
            r4 = move-exception
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ all -> 0x0078 }
            goto L_0x007c
        L_0x0078:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch:{ IOException -> 0x007d }
        L_0x007c:
            throw r4     // Catch:{ IOException -> 0x007d }
        L_0x007d:
            goto L_0x0011
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ImplementationVersion.<init>():void");
    }

    public static String get() {
        return version.versionString;
    }
}
