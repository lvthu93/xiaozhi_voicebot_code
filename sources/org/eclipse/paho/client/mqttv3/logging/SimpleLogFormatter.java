package org.eclipse.paho.client.mqttv3.logging;

import java.util.logging.Formatter;

public class SimpleLogFormatter extends Formatter {
    private static final String LS = System.getProperty("line.separator");

    public static String left(String str, int i, char c) {
        if (str.length() >= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int length = i - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00c2 A[SYNTHETIC, Splitter:B:20:0x00c2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String format(java.util.logging.LogRecord r9) {
        /*
            r8 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.util.logging.Level r1 = r9.getLevel()
            java.lang.String r1 = r1.getName()
            r0.append(r1)
            java.lang.String r1 = "\t"
            r0.append(r1)
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.util.Date r4 = new java.util.Date
            long r5 = r9.getMillis()
            r4.<init>(r5)
            r5 = 0
            r3[r5] = r4
            java.lang.String r4 = "{0, date, yy-MM-dd} {0, time, kk:mm:ss.SSSS} "
            java.lang.String r3 = java.text.MessageFormat.format(r4, r3)
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r3 = r3.concat(r1)
            r0.append(r3)
            java.lang.String r3 = r9.getSourceClassName()
            r4 = 32
            if (r3 == 0) goto L_0x0064
            int r6 = r3.length()
            r7 = 20
            if (r6 <= r7) goto L_0x0050
            java.lang.String r2 = r9.getSourceClassName()
            int r6 = r6 + -19
            java.lang.String r2 = r2.substring(r6)
            goto L_0x0066
        L_0x0050:
            char[] r6 = new char[r2]
            r6[r5] = r4
            java.lang.StringBuffer r7 = new java.lang.StringBuffer
            r7.<init>()
            r7.append(r3)
            r7.append(r6, r5, r2)
            java.lang.String r2 = r7.toString()
            goto L_0x0066
        L_0x0064:
            java.lang.String r2 = ""
        L_0x0066:
            r0.append(r2)
            java.lang.String r2 = "\t "
            r0.append(r2)
            java.lang.String r2 = r9.getSourceMethodName()
            r3 = 23
            java.lang.String r2 = left(r2, r3, r4)
            r0.append(r2)
            r0.append(r1)
            int r2 = r9.getThreadID()
            r0.append(r2)
            r0.append(r1)
            java.lang.String r1 = r8.formatMessage(r9)
            r0.append(r1)
            java.lang.String r1 = LS
            r0.append(r1)
            java.lang.Throwable r1 = r9.getThrown()
            if (r1 == 0) goto L_0x00c6
            java.lang.String r1 = "Throwable occurred: "
            r0.append(r1)
            java.lang.Throwable r9 = r9.getThrown()
            r1 = 0
            java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ all -> 0x00bf }
            r2.<init>()     // Catch:{ all -> 0x00bf }
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ all -> 0x00bf }
            r3.<init>(r2)     // Catch:{ all -> 0x00bf }
            r9.printStackTrace(r3)     // Catch:{ all -> 0x00bc }
            java.lang.String r9 = r2.toString()     // Catch:{ all -> 0x00bc }
            r0.append(r9)     // Catch:{ all -> 0x00bc }
            r3.close()     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00c6
        L_0x00bc:
            r9 = move-exception
            r1 = r3
            goto L_0x00c0
        L_0x00bf:
            r9 = move-exception
        L_0x00c0:
            if (r1 == 0) goto L_0x00c5
            r1.close()     // Catch:{ Exception -> 0x00c5 }
        L_0x00c5:
            throw r9
        L_0x00c6:
            java.lang.String r9 = r0.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.logging.SimpleLogFormatter.format(java.util.logging.LogRecord):java.lang.String");
    }
}
