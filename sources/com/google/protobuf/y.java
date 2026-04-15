package com.google.protobuf;

import defpackage.cp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.ES6Iterator;

public final class y {
    public static final char[] a;

    static {
        char[] cArr = new char[80];
        a = cArr;
        Arrays.fill(cArr, ' ');
    }

    public static void a(StringBuilder sb, int i) {
        while (i > 0) {
            int i2 = 80;
            if (i <= 80) {
                i2 = i;
            }
            sb.append(a, 0, i2);
            i -= i2;
        }
    }

    public static void b(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object b : (List) obj) {
                b(sb, i, str, b);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry b2 : ((Map) obj).entrySet()) {
                b(sb, i, str, b2);
            }
        } else {
            sb.append(10);
            a(sb, i);
            if (!str.isEmpty()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Character.toLowerCase(str.charAt(0)));
                for (int i2 = 1; i2 < str.length(); i2++) {
                    char charAt = str.charAt(i2);
                    if (Character.isUpperCase(charAt)) {
                        sb2.append("_");
                    }
                    sb2.append(Character.toLowerCase(charAt));
                }
                str = sb2.toString();
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                cp.h hVar = cp.f;
                sb.append(cg.g(new cp.h(((String) obj).getBytes(p.a))));
                sb.append('\"');
            } else if (obj instanceof cp) {
                sb.append(": \"");
                sb.append(cg.g((cp) obj));
                sb.append('\"');
            } else if (obj instanceof n) {
                sb.append(" {");
                c((n) obj, sb, i + 2);
                sb.append("\n");
                a(sb, i);
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry = (Map.Entry) obj;
                int i3 = i + 2;
                b(sb, i3, "key", entry.getKey());
                b(sb, i3, ES6Iterator.VALUE_PROPERTY, entry.getValue());
                sb.append("\n");
                a(sb, i);
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0198, code lost:
        if (((java.lang.Integer) r7).intValue() == 0) goto L_0x01f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01aa, code lost:
        if (java.lang.Float.floatToRawIntBits(((java.lang.Float) r7).floatValue()) == 0) goto L_0x01f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01c0, code lost:
        if (java.lang.Double.doubleToRawLongBits(((java.lang.Double) r7).doubleValue()) == 0) goto L_0x01f4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(com.google.protobuf.x r20, java.lang.StringBuilder r21, int r22) {
        /*
            r0 = r20
            r1 = r21
            r2 = r22
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.HashMap r4 = new java.util.HashMap
            r4.<init>()
            java.util.TreeMap r5 = new java.util.TreeMap
            r5.<init>()
            java.lang.Class r6 = r20.getClass()
            java.lang.reflect.Method[] r6 = r6.getDeclaredMethods()
            int r7 = r6.length
            r8 = 0
            r9 = 0
        L_0x0020:
            r10 = 3
            java.lang.String r11 = "get"
            java.lang.String r12 = "has"
            java.lang.String r13 = "set"
            if (r9 >= r7) goto L_0x008c
            r14 = r6[r9]
            int r15 = r14.getModifiers()
            boolean r15 = java.lang.reflect.Modifier.isStatic(r15)
            if (r15 == 0) goto L_0x0036
            goto L_0x0089
        L_0x0036:
            java.lang.String r15 = r14.getName()
            int r15 = r15.length()
            if (r15 >= r10) goto L_0x0041
            goto L_0x0089
        L_0x0041:
            java.lang.String r10 = r14.getName()
            boolean r10 = r10.startsWith(r13)
            if (r10 == 0) goto L_0x0053
            java.lang.String r10 = r14.getName()
            r3.add(r10)
            goto L_0x0089
        L_0x0053:
            int r10 = r14.getModifiers()
            boolean r10 = java.lang.reflect.Modifier.isPublic(r10)
            if (r10 != 0) goto L_0x005e
            goto L_0x0089
        L_0x005e:
            java.lang.Class[] r10 = r14.getParameterTypes()
            int r10 = r10.length
            if (r10 == 0) goto L_0x0066
            goto L_0x0089
        L_0x0066:
            java.lang.String r10 = r14.getName()
            boolean r10 = r10.startsWith(r12)
            if (r10 == 0) goto L_0x0078
            java.lang.String r10 = r14.getName()
            r4.put(r10, r14)
            goto L_0x0089
        L_0x0078:
            java.lang.String r10 = r14.getName()
            boolean r10 = r10.startsWith(r11)
            if (r10 == 0) goto L_0x0089
            java.lang.String r10 = r14.getName()
            r5.put(r10, r14)
        L_0x0089:
            int r9 = r9 + 1
            goto L_0x0020
        L_0x008c:
            java.util.Set r6 = r5.entrySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x0094:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0210
            java.lang.Object r7 = r6.next()
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7
            java.lang.Object r9 = r7.getKey()
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r9 = r9.substring(r10)
            java.lang.String r14 = "List"
            boolean r15 = r9.endsWith(r14)
            if (r15 == 0) goto L_0x00e9
            java.lang.String r15 = "OrBuilderList"
            boolean r15 = r9.endsWith(r15)
            if (r15 != 0) goto L_0x00e9
            boolean r14 = r9.equals(r14)
            if (r14 != 0) goto L_0x00e9
            java.lang.Object r14 = r7.getValue()
            java.lang.reflect.Method r14 = (java.lang.reflect.Method) r14
            if (r14 == 0) goto L_0x00e9
            java.lang.Class r15 = r14.getReturnType()
            java.lang.Class<java.util.List> r10 = java.util.List.class
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x00e9
            int r7 = r9.length()
            int r7 = r7 + -4
            java.lang.String r7 = r9.substring(r8, r7)
            java.lang.Object[] r9 = new java.lang.Object[r8]
            java.lang.Object r9 = com.google.protobuf.n.invokeOrDie(r14, r0, r9)
            b(r1, r2, r7, r9)
            goto L_0x020d
        L_0x00e9:
            java.lang.String r10 = "Map"
            boolean r14 = r9.endsWith(r10)
            if (r14 == 0) goto L_0x0132
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x0132
            java.lang.Object r10 = r7.getValue()
            java.lang.reflect.Method r10 = (java.lang.reflect.Method) r10
            if (r10 == 0) goto L_0x0132
            java.lang.Class r14 = r10.getReturnType()
            java.lang.Class<java.util.Map> r15 = java.util.Map.class
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x0132
            java.lang.Class<java.lang.Deprecated> r14 = java.lang.Deprecated.class
            boolean r14 = r10.isAnnotationPresent(r14)
            if (r14 != 0) goto L_0x0132
            int r14 = r10.getModifiers()
            boolean r14 = java.lang.reflect.Modifier.isPublic(r14)
            if (r14 == 0) goto L_0x0132
            int r7 = r9.length()
            r14 = 3
            int r7 = r7 - r14
            java.lang.String r7 = r9.substring(r8, r7)
            java.lang.Object[] r9 = new java.lang.Object[r8]
            java.lang.Object r9 = com.google.protobuf.n.invokeOrDie(r10, r0, r9)
            b(r1, r2, r7, r9)
            goto L_0x020d
        L_0x0132:
            java.lang.String r10 = r13.concat(r9)
            boolean r10 = r3.contains(r10)
            if (r10 != 0) goto L_0x013e
            goto L_0x020d
        L_0x013e:
            java.lang.String r10 = "Bytes"
            boolean r10 = r9.endsWith(r10)
            if (r10 == 0) goto L_0x0164
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r11)
            int r14 = r9.length()
            int r14 = r14 + -5
            java.lang.String r14 = r9.substring(r8, r14)
            r10.append(r14)
            java.lang.String r10 = r10.toString()
            boolean r10 = r5.containsKey(r10)
            if (r10 == 0) goto L_0x0164
            goto L_0x020d
        L_0x0164:
            java.lang.Object r7 = r7.getValue()
            java.lang.reflect.Method r7 = (java.lang.reflect.Method) r7
            java.lang.String r10 = r12.concat(r9)
            java.lang.Object r10 = r4.get(r10)
            java.lang.reflect.Method r10 = (java.lang.reflect.Method) r10
            if (r7 == 0) goto L_0x020d
            java.lang.Object[] r14 = new java.lang.Object[r8]
            java.lang.Object r7 = com.google.protobuf.n.invokeOrDie(r7, r0, r14)
            if (r10 != 0) goto L_0x01fc
            boolean r10 = r7 instanceof java.lang.Boolean
            r14 = 1
            if (r10 == 0) goto L_0x018d
            r10 = r7
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            r10 = r10 ^ r14
            goto L_0x01f7
        L_0x018d:
            boolean r10 = r7 instanceof java.lang.Integer
            if (r10 == 0) goto L_0x019b
            r10 = r7
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            if (r10 != 0) goto L_0x01f6
            goto L_0x01f4
        L_0x019b:
            boolean r10 = r7 instanceof java.lang.Float
            if (r10 == 0) goto L_0x01ad
            r10 = r7
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            int r10 = java.lang.Float.floatToRawIntBits(r10)
            if (r10 != 0) goto L_0x01f6
            goto L_0x01f4
        L_0x01ad:
            boolean r10 = r7 instanceof java.lang.Double
            if (r10 == 0) goto L_0x01c3
            r10 = r7
            java.lang.Double r10 = (java.lang.Double) r10
            double r16 = r10.doubleValue()
            long r16 = java.lang.Double.doubleToRawLongBits(r16)
            r18 = 0
            int r10 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r10 != 0) goto L_0x01f6
            goto L_0x01f4
        L_0x01c3:
            boolean r10 = r7 instanceof java.lang.String
            if (r10 == 0) goto L_0x01ce
            java.lang.String r10 = ""
            boolean r10 = r7.equals(r10)
            goto L_0x01f7
        L_0x01ce:
            boolean r10 = r7 instanceof defpackage.cp
            if (r10 == 0) goto L_0x01d9
            cp$h r10 = defpackage.cp.f
            boolean r10 = r7.equals(r10)
            goto L_0x01f7
        L_0x01d9:
            boolean r10 = r7 instanceof com.google.protobuf.x
            if (r10 == 0) goto L_0x01e7
            r10 = r7
            com.google.protobuf.x r10 = (com.google.protobuf.x) r10
            com.google.protobuf.x r10 = r10.getDefaultInstanceForType()
            if (r7 != r10) goto L_0x01f6
            goto L_0x01f4
        L_0x01e7:
            boolean r10 = r7 instanceof java.lang.Enum
            if (r10 == 0) goto L_0x01f6
            r10 = r7
            java.lang.Enum r10 = (java.lang.Enum) r10
            int r10 = r10.ordinal()
            if (r10 != 0) goto L_0x01f6
        L_0x01f4:
            r10 = 1
            goto L_0x01f7
        L_0x01f6:
            r10 = 0
        L_0x01f7:
            if (r10 != 0) goto L_0x01fa
            goto L_0x0208
        L_0x01fa:
            r14 = 0
            goto L_0x0208
        L_0x01fc:
            java.lang.Object[] r14 = new java.lang.Object[r8]
            java.lang.Object r10 = com.google.protobuf.n.invokeOrDie(r10, r0, r14)
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r14 = r10.booleanValue()
        L_0x0208:
            if (r14 == 0) goto L_0x020d
            b(r1, r2, r9, r7)
        L_0x020d:
            r10 = 3
            goto L_0x0094
        L_0x0210:
            boolean r3 = r0 instanceof com.google.protobuf.n.c
            if (r3 == 0) goto L_0x024c
            r3 = r0
            com.google.protobuf.n$c r3 = (com.google.protobuf.n.c) r3
            com.google.protobuf.l<com.google.protobuf.n$d> r3 = r3.c
            java.util.Iterator r3 = r3.k()
        L_0x021d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x024c
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "["
            r5.<init>(r6)
            java.lang.Object r6 = r4.getKey()
            com.google.protobuf.n$d r6 = (com.google.protobuf.n.d) r6
            int r6 = r6.c
            r5.append(r6)
            java.lang.String r6 = "]"
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object r4 = r4.getValue()
            b(r1, r2, r5, r4)
            goto L_0x021d
        L_0x024c:
            com.google.protobuf.n r0 = (com.google.protobuf.n) r0
            com.google.protobuf.ah r0 = r0.unknownFields
            if (r0 == 0) goto L_0x026a
        L_0x0252:
            int r3 = r0.a
            if (r8 >= r3) goto L_0x026a
            int[] r3 = r0.b
            r3 = r3[r8]
            r4 = 3
            int r3 = r3 >>> r4
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.Object[] r5 = r0.c
            r5 = r5[r8]
            b(r1, r2, r3, r5)
            int r8 = r8 + 1
            goto L_0x0252
        L_0x026a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.y.c(com.google.protobuf.x, java.lang.StringBuilder, int):void");
    }
}
