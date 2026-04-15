package org.mozilla.javascript;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class JavaMembers {
    private Class<?> cl;
    NativeJavaMethod ctors;
    private Map<String, FieldAndMethods> fieldAndMethods;
    private Map<String, Object> members;
    private Map<String, FieldAndMethods> staticFieldAndMethods;
    private Map<String, Object> staticMembers;

    public JavaMembers(Scriptable scriptable, Class<?> cls) {
        this(scriptable, cls, false);
    }

    private static Method[] discoverAccessibleMethods(Class<?> cls, boolean z, boolean z2) {
        HashMap hashMap = new HashMap();
        discoverAccessibleMethods(cls, hashMap, z, z2);
        return (Method[]) hashMap.values().toArray(new Method[hashMap.size()]);
    }

    private static MemberBox extractGetMethod(MemberBox[] memberBoxArr, boolean z) {
        int length = memberBoxArr.length;
        int i = 0;
        while (i < length) {
            MemberBox memberBox = memberBoxArr[i];
            if (memberBox.argTypes.length != 0 || (z && !memberBox.isStatic())) {
                i++;
            } else if (memberBox.method().getReturnType() != Void.TYPE) {
                return memberBox;
            } else {
                return null;
            }
        }
        return null;
    }

    private static MemberBox extractSetMethod(Class<?> cls, MemberBox[] memberBoxArr, boolean z) {
        for (int i = 1; i <= 2; i++) {
            for (MemberBox memberBox : memberBoxArr) {
                if (!z || memberBox.isStatic()) {
                    Class<?>[] clsArr = memberBox.argTypes;
                    if (clsArr.length != 1) {
                        continue;
                    } else if (i != 1) {
                        if (i != 2) {
                            Kit.codeBug();
                        }
                        if (clsArr[0].isAssignableFrom(cls)) {
                            return memberBox;
                        }
                    } else if (clsArr[0] == cls) {
                        return memberBox;
                    }
                }
            }
        }
        return null;
    }

    private MemberBox findExplicitFunction(String str, boolean z) {
        Map<String, Object> map;
        boolean z2;
        MemberBox[] memberBoxArr;
        int indexOf = str.indexOf(40);
        if (indexOf < 0) {
            return null;
        }
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        if (!z || indexOf != 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            memberBoxArr = this.ctors.methods;
        } else {
            String substring = str.substring(0, indexOf);
            Object obj = map.get(substring);
            if (!z && obj == null) {
                obj = this.staticMembers.get(substring);
            }
            if (obj instanceof NativeJavaMethod) {
                memberBoxArr = ((NativeJavaMethod) obj).methods;
            } else {
                memberBoxArr = null;
            }
        }
        if (memberBoxArr != null) {
            for (MemberBox memberBox : memberBoxArr) {
                String liveConnectSignature = liveConnectSignature(memberBox.argTypes);
                if (liveConnectSignature.length() + indexOf == str.length() && str.regionMatches(indexOf, liveConnectSignature, 0, liveConnectSignature.length())) {
                    return memberBox;
                }
            }
        }
        return null;
    }

    private static MemberBox findGetter(boolean z, Map<String, Object> map, String str, String str2) {
        String concat = str.concat(str2);
        if (!map.containsKey(concat)) {
            return null;
        }
        Object obj = map.get(concat);
        if (obj instanceof NativeJavaMethod) {
            return extractGetMethod(((NativeJavaMethod) obj).methods, z);
        }
        return null;
    }

    private Constructor<?>[] getAccessibleConstructors(boolean z) {
        Class<?> cls;
        if (z && (cls = this.cl) != ScriptRuntime.ClassClass) {
            try {
                Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
                AccessibleObject.setAccessible(declaredConstructors, true);
                return declaredConstructors;
            } catch (SecurityException unused) {
                Context.reportWarning("Could not access constructor  of class " + this.cl.getName() + " due to lack of privileges.");
            }
        }
        return this.cl.getConstructors();
    }

    private Field[] getAccessibleFields(boolean z, boolean z2) {
        if (z2 || z) {
            try {
                ArrayList arrayList = new ArrayList();
                for (Class cls = this.cl; cls != null; cls = cls.getSuperclass()) {
                    for (Field field : cls.getDeclaredFields()) {
                        int modifiers = field.getModifiers();
                        if (z2 || Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            arrayList.add(field);
                        }
                    }
                }
                return (Field[]) arrayList.toArray(new Field[arrayList.size()]);
            } catch (SecurityException unused) {
            }
        }
        return this.cl.getFields();
    }

    private Object getExplicitFunction(Scriptable scriptable, String str, Object obj, boolean z) {
        Map<String, Object> map;
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        MemberBox findExplicitFunction = findExplicitFunction(str, z);
        if (findExplicitFunction == null) {
            return null;
        }
        Scriptable functionPrototype = ScriptableObject.getFunctionPrototype(scriptable);
        if (findExplicitFunction.isCtor()) {
            NativeJavaConstructor nativeJavaConstructor = new NativeJavaConstructor(findExplicitFunction);
            nativeJavaConstructor.setPrototype(functionPrototype);
            map.put(str, nativeJavaConstructor);
            return nativeJavaConstructor;
        }
        Object obj2 = map.get(findExplicitFunction.getName());
        if (!(obj2 instanceof NativeJavaMethod) || ((NativeJavaMethod) obj2).methods.length <= 1) {
            return obj2;
        }
        NativeJavaMethod nativeJavaMethod = new NativeJavaMethod(findExplicitFunction, str);
        nativeJavaMethod.setPrototype(functionPrototype);
        map.put(str, nativeJavaMethod);
        return nativeJavaMethod;
    }

    public static String javaSignature(Class<?> cls) {
        if (!cls.isArray()) {
            return cls.getName();
        }
        int i = 0;
        do {
            i++;
            cls = cls.getComponentType();
        } while (cls.isArray());
        String name = cls.getName();
        if (i == 1) {
            return name.concat("[]");
        }
        StringBuilder sb = new StringBuilder((i * 2) + name.length());
        sb.append(name);
        while (i != 0) {
            i--;
            sb.append("[]");
        }
        return sb.toString();
    }

    public static String liveConnectSignature(Class<?>[] clsArr) {
        int length = clsArr.length;
        if (length == 0) {
            return "()";
        }
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i != length; i++) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(javaSignature(clsArr[i]));
        }
        sb.append(')');
        return sb.toString();
    }

    public static JavaMembers lookupClass(Scriptable scriptable, Class<?> cls, Class<?> cls2, boolean z) {
        ClassCache classCache = ClassCache.get(scriptable);
        Map<Class<?>, JavaMembers> classCacheMap = classCache.getClassCacheMap();
        Class<?> cls3 = cls;
        while (true) {
            JavaMembers javaMembers = classCacheMap.get(cls3);
            if (javaMembers != null) {
                if (cls3 != cls) {
                    classCacheMap.put(cls, javaMembers);
                }
                return javaMembers;
            }
            try {
                JavaMembers javaMembers2 = new JavaMembers(classCache.getAssociatedScope(), cls3, z);
                if (classCache.isCachingEnabled()) {
                    classCacheMap.put(cls3, javaMembers2);
                    if (cls3 != cls) {
                        classCacheMap.put(cls, javaMembers2);
                    }
                }
                return javaMembers2;
            } catch (SecurityException e) {
                if (cls2 == null || !cls2.isInterface()) {
                    Class<?> superclass = cls3.getSuperclass();
                    if (superclass == null) {
                        if (cls3.isInterface()) {
                            superclass = ScriptRuntime.ObjectClass;
                        } else {
                            throw e;
                        }
                    }
                    cls3 = superclass;
                } else {
                    cls3 = cls2;
                    cls2 = null;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:118:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0219  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void reflect(org.mozilla.javascript.Scriptable r17, boolean r18, boolean r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            java.lang.Class<?> r4 = r0.cl
            java.lang.reflect.Method[] r4 = discoverAccessibleMethods(r4, r2, r3)
            int r5 = r4.length
            r6 = 0
            r7 = 0
        L_0x0011:
            if (r7 >= r5) goto L_0x0052
            r8 = r4[r7]
            int r9 = r8.getModifiers()
            boolean r9 = java.lang.reflect.Modifier.isStatic(r9)
            if (r9 == 0) goto L_0x0022
            java.util.Map<java.lang.String, java.lang.Object> r9 = r0.staticMembers
            goto L_0x0024
        L_0x0022:
            java.util.Map<java.lang.String, java.lang.Object> r9 = r0.members
        L_0x0024:
            java.lang.String r10 = r8.getName()
            java.lang.Object r11 = r9.get(r10)
            if (r11 != 0) goto L_0x0032
            r9.put(r10, r8)
            goto L_0x004f
        L_0x0032:
            boolean r12 = r11 instanceof org.mozilla.javascript.ObjArray
            if (r12 == 0) goto L_0x0039
            org.mozilla.javascript.ObjArray r11 = (org.mozilla.javascript.ObjArray) r11
            goto L_0x004c
        L_0x0039:
            boolean r12 = r11 instanceof java.lang.reflect.Method
            if (r12 != 0) goto L_0x0040
            org.mozilla.javascript.Kit.codeBug()
        L_0x0040:
            org.mozilla.javascript.ObjArray r12 = new org.mozilla.javascript.ObjArray
            r12.<init>()
            r12.add(r11)
            r9.put(r10, r12)
            r11 = r12
        L_0x004c:
            r11.add(r8)
        L_0x004f:
            int r7 = r7 + 1
            goto L_0x0011
        L_0x0052:
            r4 = 0
        L_0x0053:
            r5 = 2
            r7 = 1
            if (r4 == r5) goto L_0x00c3
            if (r4 != 0) goto L_0x005b
            r8 = 1
            goto L_0x005c
        L_0x005b:
            r8 = 0
        L_0x005c:
            if (r8 == 0) goto L_0x0061
            java.util.Map<java.lang.String, java.lang.Object> r8 = r0.staticMembers
            goto L_0x0063
        L_0x0061:
            java.util.Map<java.lang.String, java.lang.Object> r8 = r0.members
        L_0x0063:
            java.util.Set r9 = r8.entrySet()
            java.util.Iterator r9 = r9.iterator()
        L_0x006b:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00c0
            java.lang.Object r10 = r9.next()
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10
            java.lang.Object r11 = r10.getValue()
            boolean r12 = r11 instanceof java.lang.reflect.Method
            if (r12 == 0) goto L_0x008b
            org.mozilla.javascript.MemberBox[] r12 = new org.mozilla.javascript.MemberBox[r7]
            org.mozilla.javascript.MemberBox r13 = new org.mozilla.javascript.MemberBox
            java.lang.reflect.Method r11 = (java.lang.reflect.Method) r11
            r13.<init>((java.lang.reflect.Method) r11)
            r12[r6] = r13
            goto L_0x00ad
        L_0x008b:
            org.mozilla.javascript.ObjArray r11 = (org.mozilla.javascript.ObjArray) r11
            int r12 = r11.size()
            if (r12 >= r5) goto L_0x0096
            org.mozilla.javascript.Kit.codeBug()
        L_0x0096:
            org.mozilla.javascript.MemberBox[] r13 = new org.mozilla.javascript.MemberBox[r12]
            r14 = 0
        L_0x0099:
            if (r14 == r12) goto L_0x00ac
            java.lang.Object r15 = r11.get(r14)
            java.lang.reflect.Method r15 = (java.lang.reflect.Method) r15
            org.mozilla.javascript.MemberBox r7 = new org.mozilla.javascript.MemberBox
            r7.<init>((java.lang.reflect.Method) r15)
            r13[r14] = r7
            int r14 = r14 + 1
            r7 = 1
            goto L_0x0099
        L_0x00ac:
            r12 = r13
        L_0x00ad:
            org.mozilla.javascript.NativeJavaMethod r7 = new org.mozilla.javascript.NativeJavaMethod
            r7.<init>(r12)
            if (r1 == 0) goto L_0x00b7
            org.mozilla.javascript.ScriptRuntime.setFunctionProtoAndParent(r7, r1)
        L_0x00b7:
            java.lang.Object r10 = r10.getKey()
            r8.put(r10, r7)
            r7 = 1
            goto L_0x006b
        L_0x00c0:
            int r4 = r4 + 1
            goto L_0x0053
        L_0x00c3:
            java.lang.reflect.Field[] r2 = r0.getAccessibleFields(r2, r3)
            int r4 = r2.length
            r7 = 0
        L_0x00c9:
            if (r7 >= r4) goto L_0x0157
            r8 = r2[r7]
            java.lang.String r9 = r8.getName()
            int r10 = r8.getModifiers()
            boolean r10 = java.lang.reflect.Modifier.isStatic(r10)     // Catch:{ SecurityException -> 0x012f }
            if (r10 == 0) goto L_0x00de
            java.util.Map<java.lang.String, java.lang.Object> r11 = r0.staticMembers     // Catch:{ SecurityException -> 0x012f }
            goto L_0x00e0
        L_0x00de:
            java.util.Map<java.lang.String, java.lang.Object> r11 = r0.members     // Catch:{ SecurityException -> 0x012f }
        L_0x00e0:
            java.lang.Object r12 = r11.get(r9)     // Catch:{ SecurityException -> 0x012f }
            if (r12 != 0) goto L_0x00ea
            r11.put(r9, r8)     // Catch:{ SecurityException -> 0x012f }
            goto L_0x0153
        L_0x00ea:
            boolean r13 = r12 instanceof org.mozilla.javascript.NativeJavaMethod     // Catch:{ SecurityException -> 0x012f }
            if (r13 == 0) goto L_0x0113
            org.mozilla.javascript.NativeJavaMethod r12 = (org.mozilla.javascript.NativeJavaMethod) r12     // Catch:{ SecurityException -> 0x012f }
            org.mozilla.javascript.FieldAndMethods r13 = new org.mozilla.javascript.FieldAndMethods     // Catch:{ SecurityException -> 0x012f }
            org.mozilla.javascript.MemberBox[] r12 = r12.methods     // Catch:{ SecurityException -> 0x012f }
            r13.<init>(r1, r12, r8)     // Catch:{ SecurityException -> 0x012f }
            if (r10 == 0) goto L_0x00fc
            java.util.Map<java.lang.String, org.mozilla.javascript.FieldAndMethods> r8 = r0.staticFieldAndMethods     // Catch:{ SecurityException -> 0x012f }
            goto L_0x00fe
        L_0x00fc:
            java.util.Map<java.lang.String, org.mozilla.javascript.FieldAndMethods> r8 = r0.fieldAndMethods     // Catch:{ SecurityException -> 0x012f }
        L_0x00fe:
            if (r8 != 0) goto L_0x010c
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ SecurityException -> 0x012f }
            r8.<init>()     // Catch:{ SecurityException -> 0x012f }
            if (r10 == 0) goto L_0x010a
            r0.staticFieldAndMethods = r8     // Catch:{ SecurityException -> 0x012f }
            goto L_0x010c
        L_0x010a:
            r0.fieldAndMethods = r8     // Catch:{ SecurityException -> 0x012f }
        L_0x010c:
            r8.put(r9, r13)     // Catch:{ SecurityException -> 0x012f }
            r11.put(r9, r13)     // Catch:{ SecurityException -> 0x012f }
            goto L_0x0153
        L_0x0113:
            boolean r10 = r12 instanceof java.lang.reflect.Field     // Catch:{ SecurityException -> 0x012f }
            if (r10 == 0) goto L_0x012b
            java.lang.reflect.Field r12 = (java.lang.reflect.Field) r12     // Catch:{ SecurityException -> 0x012f }
            java.lang.Class r10 = r12.getDeclaringClass()     // Catch:{ SecurityException -> 0x012f }
            java.lang.Class r12 = r8.getDeclaringClass()     // Catch:{ SecurityException -> 0x012f }
            boolean r10 = r10.isAssignableFrom(r12)     // Catch:{ SecurityException -> 0x012f }
            if (r10 == 0) goto L_0x0153
            r11.put(r9, r8)     // Catch:{ SecurityException -> 0x012f }
            goto L_0x0153
        L_0x012b:
            org.mozilla.javascript.Kit.codeBug()     // Catch:{ SecurityException -> 0x012f }
            goto L_0x0153
        L_0x012f:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r10 = "Could not access field "
            r8.<init>(r10)
            r8.append(r9)
            java.lang.String r9 = " of class "
            r8.append(r9)
            java.lang.Class<?> r9 = r0.cl
            java.lang.String r9 = r9.getName()
            r8.append(r9)
            java.lang.String r9 = " due to lack of privileges."
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            org.mozilla.javascript.Context.reportWarning(r8)
        L_0x0153:
            int r7 = r7 + 1
            goto L_0x00c9
        L_0x0157:
            r1 = 0
        L_0x0158:
            if (r1 == r5) goto L_0x0258
            if (r1 != 0) goto L_0x015e
            r2 = 1
            goto L_0x015f
        L_0x015e:
            r2 = 0
        L_0x015f:
            if (r2 == 0) goto L_0x0164
            java.util.Map<java.lang.String, java.lang.Object> r4 = r0.staticMembers
            goto L_0x0166
        L_0x0164:
            java.util.Map<java.lang.String, java.lang.Object> r4 = r0.members
        L_0x0166:
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            java.util.Set r8 = r4.keySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x0173:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0250
            java.lang.Object r9 = r8.next()
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r10 = "get"
            boolean r11 = r9.startsWith(r10)
            java.lang.String r12 = "set"
            boolean r13 = r9.startsWith(r12)
            java.lang.String r14 = "is"
            boolean r15 = r9.startsWith(r14)
            if (r11 != 0) goto L_0x019a
            if (r15 != 0) goto L_0x019a
            if (r13 == 0) goto L_0x0198
            goto L_0x019a
        L_0x0198:
            r15 = 1
            goto L_0x0173
        L_0x019a:
            if (r15 == 0) goto L_0x019e
            r11 = 2
            goto L_0x019f
        L_0x019e:
            r11 = 3
        L_0x019f:
            java.lang.String r9 = r9.substring(r11)
            int r11 = r9.length()
            if (r11 != 0) goto L_0x01aa
            goto L_0x0173
        L_0x01aa:
            char r11 = r9.charAt(r6)
            boolean r13 = java.lang.Character.isUpperCase(r11)
            if (r13 == 0) goto L_0x01e2
            int r13 = r9.length()
            r15 = 1
            if (r13 != r15) goto L_0x01c0
            java.lang.String r11 = r9.toLowerCase()
            goto L_0x01e3
        L_0x01c0:
            char r13 = r9.charAt(r15)
            boolean r13 = java.lang.Character.isUpperCase(r13)
            if (r13 != 0) goto L_0x01e2
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            char r11 = java.lang.Character.toLowerCase(r11)
            r13.append(r11)
            java.lang.String r11 = r9.substring(r15)
            r13.append(r11)
            java.lang.String r11 = r13.toString()
            goto L_0x01e3
        L_0x01e2:
            r11 = r9
        L_0x01e3:
            boolean r13 = r7.containsKey(r11)
            if (r13 == 0) goto L_0x01ea
            goto L_0x0173
        L_0x01ea:
            java.lang.Object r13 = r4.get(r11)
            if (r13 == 0) goto L_0x0204
            if (r3 == 0) goto L_0x0173
            boolean r15 = r13 instanceof java.lang.reflect.Member
            if (r15 == 0) goto L_0x0173
            java.lang.reflect.Member r13 = (java.lang.reflect.Member) r13
            int r13 = r13.getModifiers()
            boolean r13 = java.lang.reflect.Modifier.isPrivate(r13)
            if (r13 != 0) goto L_0x0204
            goto L_0x0173
        L_0x0204:
            org.mozilla.javascript.MemberBox r10 = findGetter(r2, r4, r10, r9)
            if (r10 != 0) goto L_0x020e
            org.mozilla.javascript.MemberBox r10 = findGetter(r2, r4, r14, r9)
        L_0x020e:
            java.lang.String r9 = r12.concat(r9)
            boolean r12 = r4.containsKey(r9)
            r13 = 0
            if (r12 == 0) goto L_0x0244
            java.lang.Object r9 = r4.get(r9)
            boolean r12 = r9 instanceof org.mozilla.javascript.NativeJavaMethod
            if (r12 == 0) goto L_0x0244
            org.mozilla.javascript.NativeJavaMethod r9 = (org.mozilla.javascript.NativeJavaMethod) r9
            if (r10 == 0) goto L_0x0234
            java.lang.reflect.Method r12 = r10.method()
            java.lang.Class r12 = r12.getReturnType()
            org.mozilla.javascript.MemberBox[] r14 = r9.methods
            org.mozilla.javascript.MemberBox r12 = extractSetMethod(r12, r14, r2)
            goto L_0x023a
        L_0x0234:
            org.mozilla.javascript.MemberBox[] r12 = r9.methods
            org.mozilla.javascript.MemberBox r12 = extractSetMethod(r12, r2)
        L_0x023a:
            org.mozilla.javascript.MemberBox[] r14 = r9.methods
            int r14 = r14.length
            r15 = 1
            if (r14 <= r15) goto L_0x0241
            goto L_0x0242
        L_0x0241:
            r9 = r13
        L_0x0242:
            r13 = r12
            goto L_0x0246
        L_0x0244:
            r15 = 1
            r9 = r13
        L_0x0246:
            org.mozilla.javascript.BeanProperty r12 = new org.mozilla.javascript.BeanProperty
            r12.<init>(r10, r13, r9)
            r7.put(r11, r12)
            goto L_0x0173
        L_0x0250:
            r15 = 1
            r4.putAll(r7)
            int r1 = r1 + 1
            goto L_0x0158
        L_0x0258:
            java.lang.reflect.Constructor[] r1 = r0.getAccessibleConstructors(r3)
            int r2 = r1.length
            org.mozilla.javascript.MemberBox[] r2 = new org.mozilla.javascript.MemberBox[r2]
        L_0x025f:
            int r3 = r1.length
            if (r6 == r3) goto L_0x026e
            org.mozilla.javascript.MemberBox r3 = new org.mozilla.javascript.MemberBox
            r4 = r1[r6]
            r3.<init>((java.lang.reflect.Constructor<?>) r4)
            r2[r6] = r3
            int r6 = r6 + 1
            goto L_0x025f
        L_0x026e:
            org.mozilla.javascript.NativeJavaMethod r1 = new org.mozilla.javascript.NativeJavaMethod
            java.lang.Class<?> r3 = r0.cl
            java.lang.String r3 = r3.getSimpleName()
            r1.<init>((org.mozilla.javascript.MemberBox[]) r2, (java.lang.String) r3)
            r0.ctors = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.JavaMembers.reflect(org.mozilla.javascript.Scriptable, boolean, boolean):void");
    }

    public Object get(Scriptable scriptable, String str, Object obj, boolean z) {
        Map<String, Object> map;
        Class<?> cls;
        Object obj2;
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        Object obj3 = map.get(str);
        if (!z && obj3 == null) {
            obj3 = this.staticMembers.get(str);
        }
        if (obj3 == null && (obj3 = getExplicitFunction(scriptable, str, obj, z)) == null) {
            return Scriptable.NOT_FOUND;
        }
        if (obj3 instanceof Scriptable) {
            return obj3;
        }
        Context context = Context.getContext();
        try {
            if (obj3 instanceof BeanProperty) {
                BeanProperty beanProperty = (BeanProperty) obj3;
                MemberBox memberBox = beanProperty.getter;
                if (memberBox == null) {
                    return Scriptable.NOT_FOUND;
                }
                obj2 = memberBox.invoke(obj, Context.emptyArgs);
                cls = beanProperty.getter.method().getReturnType();
            } else {
                Field field = (Field) obj3;
                if (z) {
                    obj = null;
                }
                obj2 = field.get(obj);
                cls = field.getType();
            }
            return context.getWrapFactory().wrap(context, ScriptableObject.getTopLevelScope(scriptable), obj2, cls);
        } catch (Exception e) {
            throw Context.throwAsScriptRuntimeEx(e);
        }
    }

    public Map<String, FieldAndMethods> getFieldAndMethodsObjects(Scriptable scriptable, Object obj, boolean z) {
        Map<String, FieldAndMethods> map;
        if (z) {
            map = this.staticFieldAndMethods;
        } else {
            map = this.fieldAndMethods;
        }
        if (map == null) {
            return null;
        }
        HashMap hashMap = new HashMap(map.size());
        for (FieldAndMethods next : map.values()) {
            FieldAndMethods fieldAndMethods2 = new FieldAndMethods(scriptable, next.methods, next.field);
            fieldAndMethods2.javaObject = obj;
            hashMap.put(next.field.getName(), fieldAndMethods2);
        }
        return hashMap;
    }

    public Object[] getIds(boolean z) {
        Map<String, Object> map;
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        return map.keySet().toArray(new Object[map.size()]);
    }

    public boolean has(String str, boolean z) {
        Map<String, Object> map;
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        if (map.get(str) == null && findExplicitFunction(str, z) == null) {
            return false;
        }
        return true;
    }

    public void put(Scriptable scriptable, String str, Object obj, Object obj2, boolean z) {
        Map<String, Object> map;
        String str2;
        if (z) {
            map = this.staticMembers;
        } else {
            map = this.members;
        }
        Object obj3 = map.get(str);
        if (!z && obj3 == null) {
            obj3 = this.staticMembers.get(str);
        }
        if (obj3 != null) {
            if (obj3 instanceof FieldAndMethods) {
                obj3 = ((FieldAndMethods) map.get(str)).field;
            }
            if (obj3 instanceof BeanProperty) {
                BeanProperty beanProperty = (BeanProperty) obj3;
                MemberBox memberBox = beanProperty.setter;
                if (memberBox != null) {
                    NativeJavaMethod nativeJavaMethod = beanProperty.setters;
                    if (nativeJavaMethod == null || obj2 == null) {
                        try {
                            beanProperty.setter.invoke(obj, new Object[]{Context.jsToJava(obj2, memberBox.argTypes[0])});
                        } catch (Exception e) {
                            throw Context.throwAsScriptRuntimeEx(e);
                        }
                    } else {
                        nativeJavaMethod.call(Context.getContext(), ScriptableObject.getTopLevelScope(scriptable), scriptable, new Object[]{obj2});
                    }
                } else {
                    throw reportMemberNotFound(str);
                }
            } else if (!(obj3 instanceof Field)) {
                if (obj3 == null) {
                    str2 = "msg.java.internal.private";
                } else {
                    str2 = "msg.java.method.assign";
                }
                throw Context.reportRuntimeError1(str2, str);
            } else {
                Field field = (Field) obj3;
                try {
                    field.set(obj, Context.jsToJava(obj2, field.getType()));
                } catch (IllegalAccessException e2) {
                    if ((field.getModifiers() & 16) == 0) {
                        throw Context.throwAsScriptRuntimeEx(e2);
                    }
                } catch (IllegalArgumentException unused) {
                    throw Context.reportRuntimeError3("msg.java.internal.field.type", obj2.getClass().getName(), field, obj.getClass().getName());
                }
            }
        } else {
            throw reportMemberNotFound(str);
        }
    }

    public RuntimeException reportMemberNotFound(String str) {
        return Context.reportRuntimeError2("msg.java.member.not.found", this.cl.getName(), str);
    }

    public JavaMembers(Scriptable scriptable, Class<?> cls, boolean z) {
        try {
            Context enterContext = ContextFactory.getGlobal().enterContext();
            ClassShutter classShutter = enterContext.getClassShutter();
            if (classShutter != null) {
                if (!classShutter.visibleToScripts(cls.getName())) {
                    throw Context.reportRuntimeError1("msg.access.prohibited", cls.getName());
                }
            }
            this.members = new HashMap();
            this.staticMembers = new HashMap();
            this.cl = cls;
            reflect(scriptable, z, enterContext.hasFeature(13));
        } finally {
            Context.exit();
        }
    }

    public static final class MethodSignature {
        private final Class<?>[] args;
        private final String name;

        private MethodSignature(String str, Class<?>[] clsArr) {
            this.name = str;
            this.args = clsArr;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MethodSignature)) {
                return false;
            }
            MethodSignature methodSignature = (MethodSignature) obj;
            if (!methodSignature.name.equals(this.name) || !Arrays.equals(this.args, methodSignature.args)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.name.hashCode() ^ this.args.length;
        }

        public MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:33|34|(3:36|(2:38|59)(1:60)|39)|58|66) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0027, code lost:
        r8.put(r5, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002d, code lost:
        if (r7 == null) goto L_0x00ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0 = r7.getDeclaredMethods();
        r2 = r0.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r3 >= r2) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        r4 = r0[r3];
        r5 = r4.getModifiers();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        if (java.lang.reflect.Modifier.isPublic(r5) != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0047, code lost:
        if (java.lang.reflect.Modifier.isProtected(r5) != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        if (r10 == false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        r5 = new org.mozilla.javascript.JavaMembers.MethodSignature(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0054, code lost:
        if (r8.containsKey(r5) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0056, code lost:
        if (r10 == false) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005c, code lost:
        if (r4.isAccessible() != false) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        r4.setAccessible(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0062, code lost:
        r8.put(r5, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0068, code lost:
        r0 = r7.getInterfaces();
        r2 = r0.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
        if (r10 != false) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006e, code lost:
        if (r3 >= r2) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0070, code lost:
        discoverAccessibleMethods(r0[r3], r8, r9, r10);
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0078, code lost:
        r7 = r7.getSuperclass();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007d, code lost:
        r7 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r0 = r7.getMethods();
        r2 = r0.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0083, code lost:
        if (r3 < r2) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0085, code lost:
        r4 = r0[r3];
        r5 = new org.mozilla.javascript.JavaMembers.MethodSignature(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0090, code lost:
        if (r8.containsKey(r5) == false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0092, code lost:
        r8.put(r5, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0095, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000d, code lost:
        if (r9 != false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0098, code lost:
        org.mozilla.javascript.Context.reportWarning("Could not discover accessible methods of class " + r7.getName() + " due to lack of privileges, attemping superclasses/interfaces.");
        r7 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b2, code lost:
        r0 = r7.getInterfaces();
        r2 = r0.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b7, code lost:
        if (r1 < r2) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b9, code lost:
        discoverAccessibleMethods(r0[r1], r8, r9, r10);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c1, code lost:
        r7 = r7.getSuperclass();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c5, code lost:
        if (r7 != null) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c7, code lost:
        discoverAccessibleMethods(r7, r8, r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000f, code lost:
        if (r10 == false) goto L_0x0012;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x002d, code lost:
        r7 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        r7 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
        r0 = r7.getMethods();
        r2 = r0.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
        if (r3 >= r2) goto L_0x00ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r4 = r0[r3];
        r5 = new org.mozilla.javascript.JavaMembers.MethodSignature(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0025, code lost:
        if (r8.containsKey(r5) != false) goto L_0x002a;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x007d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void discoverAccessibleMethods(java.lang.Class<?> r7, java.util.Map<org.mozilla.javascript.JavaMembers.MethodSignature, java.lang.reflect.Method> r8, boolean r9, boolean r10) {
        /*
            int r0 = r7.getModifiers()
            boolean r0 = java.lang.reflect.Modifier.isPublic(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000d
            if (r10 == 0) goto L_0x00b2
        L_0x000d:
            if (r9 != 0) goto L_0x002d
            if (r10 == 0) goto L_0x0012
            goto L_0x002d
        L_0x0012:
            java.lang.reflect.Method[] r0 = r7.getMethods()     // Catch:{ SecurityException -> 0x0098 }
            int r2 = r0.length     // Catch:{ SecurityException -> 0x0098 }
            r3 = 0
        L_0x0018:
            if (r3 >= r2) goto L_0x00ca
            r4 = r0[r3]     // Catch:{ SecurityException -> 0x0098 }
            org.mozilla.javascript.JavaMembers$MethodSignature r5 = new org.mozilla.javascript.JavaMembers$MethodSignature     // Catch:{ SecurityException -> 0x0098 }
            r5.<init>(r4)     // Catch:{ SecurityException -> 0x0098 }
            boolean r6 = r8.containsKey(r5)     // Catch:{ SecurityException -> 0x0098 }
            if (r6 != 0) goto L_0x002a
            r8.put(r5, r4)     // Catch:{ SecurityException -> 0x0098 }
        L_0x002a:
            int r3 = r3 + 1
            goto L_0x0018
        L_0x002d:
            if (r7 == 0) goto L_0x00ca
            java.lang.reflect.Method[] r0 = r7.getDeclaredMethods()     // Catch:{ SecurityException -> 0x007d }
            int r2 = r0.length     // Catch:{ SecurityException -> 0x007d }
            r3 = 0
        L_0x0035:
            if (r3 >= r2) goto L_0x0068
            r4 = r0[r3]     // Catch:{ SecurityException -> 0x007d }
            int r5 = r4.getModifiers()     // Catch:{ SecurityException -> 0x007d }
            boolean r6 = java.lang.reflect.Modifier.isPublic(r5)     // Catch:{ SecurityException -> 0x007d }
            if (r6 != 0) goto L_0x004b
            boolean r5 = java.lang.reflect.Modifier.isProtected(r5)     // Catch:{ SecurityException -> 0x007d }
            if (r5 != 0) goto L_0x004b
            if (r10 == 0) goto L_0x0065
        L_0x004b:
            org.mozilla.javascript.JavaMembers$MethodSignature r5 = new org.mozilla.javascript.JavaMembers$MethodSignature     // Catch:{ SecurityException -> 0x007d }
            r5.<init>(r4)     // Catch:{ SecurityException -> 0x007d }
            boolean r6 = r8.containsKey(r5)     // Catch:{ SecurityException -> 0x007d }
            if (r6 != 0) goto L_0x0065
            if (r10 == 0) goto L_0x0062
            boolean r6 = r4.isAccessible()     // Catch:{ SecurityException -> 0x007d }
            if (r6 != 0) goto L_0x0062
            r6 = 1
            r4.setAccessible(r6)     // Catch:{ SecurityException -> 0x007d }
        L_0x0062:
            r8.put(r5, r4)     // Catch:{ SecurityException -> 0x007d }
        L_0x0065:
            int r3 = r3 + 1
            goto L_0x0035
        L_0x0068:
            java.lang.Class[] r0 = r7.getInterfaces()     // Catch:{ SecurityException -> 0x007d }
            int r2 = r0.length     // Catch:{ SecurityException -> 0x007d }
            r3 = 0
        L_0x006e:
            if (r3 >= r2) goto L_0x0078
            r4 = r0[r3]     // Catch:{ SecurityException -> 0x007d }
            discoverAccessibleMethods(r4, r8, r9, r10)     // Catch:{ SecurityException -> 0x007d }
            int r3 = r3 + 1
            goto L_0x006e
        L_0x0078:
            java.lang.Class r7 = r7.getSuperclass()     // Catch:{ SecurityException -> 0x007d }
            goto L_0x002d
        L_0x007d:
            java.lang.reflect.Method[] r0 = r7.getMethods()     // Catch:{ SecurityException -> 0x0098 }
            int r2 = r0.length     // Catch:{ SecurityException -> 0x0098 }
            r3 = 0
        L_0x0083:
            if (r3 >= r2) goto L_0x00ca
            r4 = r0[r3]     // Catch:{ SecurityException -> 0x0098 }
            org.mozilla.javascript.JavaMembers$MethodSignature r5 = new org.mozilla.javascript.JavaMembers$MethodSignature     // Catch:{ SecurityException -> 0x0098 }
            r5.<init>(r4)     // Catch:{ SecurityException -> 0x0098 }
            boolean r6 = r8.containsKey(r5)     // Catch:{ SecurityException -> 0x0098 }
            if (r6 != 0) goto L_0x0095
            r8.put(r5, r4)     // Catch:{ SecurityException -> 0x0098 }
        L_0x0095:
            int r3 = r3 + 1
            goto L_0x0083
        L_0x0098:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Could not discover accessible methods of class "
            r0.<init>(r2)
            java.lang.String r2 = r7.getName()
            r0.append(r2)
            java.lang.String r2 = " due to lack of privileges, attemping superclasses/interfaces."
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            org.mozilla.javascript.Context.reportWarning(r0)
        L_0x00b2:
            java.lang.Class[] r0 = r7.getInterfaces()
            int r2 = r0.length
        L_0x00b7:
            if (r1 >= r2) goto L_0x00c1
            r3 = r0[r1]
            discoverAccessibleMethods(r3, r8, r9, r10)
            int r1 = r1 + 1
            goto L_0x00b7
        L_0x00c1:
            java.lang.Class r7 = r7.getSuperclass()
            if (r7 == 0) goto L_0x00ca
            discoverAccessibleMethods(r7, r8, r9, r10)
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.JavaMembers.discoverAccessibleMethods(java.lang.Class, java.util.Map, boolean, boolean):void");
    }

    private static MemberBox extractSetMethod(MemberBox[] memberBoxArr, boolean z) {
        for (MemberBox memberBox : memberBoxArr) {
            if ((!z || memberBox.isStatic()) && memberBox.method().getReturnType() == Void.TYPE && memberBox.argTypes.length == 1) {
                return memberBox;
            }
        }
        return null;
    }
}
