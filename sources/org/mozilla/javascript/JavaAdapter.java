package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import org.mozilla.javascript.ObjToIntMap;

public final class JavaAdapter implements IdFunctionCall {
    private static final Object FTAG = "JavaAdapter";
    private static final int Id_JavaAdapter = 1;

    public static class JavaAdapterSignature {
        Class<?>[] interfaces;
        ObjToIntMap names;
        Class<?> superClass;

        public JavaAdapterSignature(Class<?> cls, Class<?>[] clsArr, ObjToIntMap objToIntMap) {
            this.superClass = cls;
            this.interfaces = clsArr;
            this.names = objToIntMap;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof JavaAdapterSignature)) {
                return false;
            }
            JavaAdapterSignature javaAdapterSignature = (JavaAdapterSignature) obj;
            if (this.superClass != javaAdapterSignature.superClass) {
                return false;
            }
            Class<?>[] clsArr = this.interfaces;
            Class<?>[] clsArr2 = javaAdapterSignature.interfaces;
            if (clsArr != clsArr2) {
                if (clsArr.length == clsArr2.length) {
                    int i = 0;
                    while (true) {
                        Class<?>[] clsArr3 = this.interfaces;
                        if (i >= clsArr3.length) {
                            break;
                        } else if (clsArr3[i] != javaAdapterSignature.interfaces[i]) {
                            return false;
                        } else {
                            i++;
                        }
                    }
                } else {
                    return false;
                }
            }
            if (this.names.size() != javaAdapterSignature.names.size()) {
                return false;
            }
            ObjToIntMap.Iterator iterator = new ObjToIntMap.Iterator(this.names);
            iterator.start();
            while (!iterator.done()) {
                int value = iterator.getValue();
                if (value != javaAdapterSignature.names.get((String) iterator.getKey(), value + 1)) {
                    return false;
                }
                iterator.next();
            }
            return true;
        }

        public int hashCode() {
            return (this.superClass.hashCode() + Arrays.hashCode(this.interfaces)) ^ this.names.size();
        }
    }

    public static int appendMethodSignature(Class<?>[] clsArr, Class<?> cls, StringBuilder sb) {
        sb.append('(');
        int length = clsArr.length + 1;
        for (Class<?> cls2 : clsArr) {
            appendTypeString(sb, cls2);
            if (cls2 == Long.TYPE || cls2 == Double.TYPE) {
                length++;
            }
        }
        sb.append(')');
        appendTypeString(sb, cls);
        return length;
    }

    private static void appendOverridableMethods(Class<?> cls, ArrayList<Method> arrayList, HashSet<String> hashSet) {
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(declaredMethods[i].getName());
            Method method = declaredMethods[i];
            sb.append(getMethodSignature(method, method.getParameterTypes()));
            String sb2 = sb.toString();
            if (!hashSet.contains(sb2)) {
                int modifiers = declaredMethods[i].getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    if (Modifier.isFinal(modifiers)) {
                        hashSet.add(sb2);
                    } else if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                        arrayList.add(declaredMethods[i]);
                        hashSet.add(sb2);
                    }
                }
            }
        }
    }

    private static StringBuilder appendTypeString(StringBuilder sb, Class<?> cls) {
        char c;
        while (cls.isArray()) {
            sb.append('[');
            cls = cls.getComponentType();
        }
        if (cls.isPrimitive()) {
            if (cls == Boolean.TYPE) {
                c = 'Z';
            } else if (cls == Long.TYPE) {
                c = 'J';
            } else {
                c = Character.toUpperCase(cls.getName().charAt(0));
            }
            sb.append(c);
        } else {
            sb.append('L');
            sb.append(cls.getName().replace('.', '/'));
            sb.append(';');
        }
        return sb;
    }

    public static Object callMethod(ContextFactory contextFactory, Scriptable scriptable, Function function, Object[] objArr, long j) {
        if (function == null) {
            return null;
        }
        if (contextFactory == null) {
            contextFactory = ContextFactory.getGlobal();
        }
        Scriptable parentScope = function.getParentScope();
        if (j == 0) {
            return Context.call(contextFactory, function, parentScope, scriptable, objArr);
        }
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null) {
            return doCall(currentContext, parentScope, scriptable, function, objArr, j);
        }
        return contextFactory.call(new a4(parentScope, scriptable, function, objArr, j));
    }

    public static Object convertResult(Object obj, Class<?> cls) {
        if (obj != Undefined.instance || cls == ScriptRuntime.ObjectClass || cls == ScriptRuntime.StringClass) {
            return Context.jsToJava(obj, cls);
        }
        return null;
    }

    public static byte[] createAdapterCode(ObjToIntMap objToIntMap, String str, Class<?> cls, Class<?>[] clsArr, String str2) {
        int i;
        Method[] methodArr;
        int i2;
        ObjToIntMap objToIntMap2 = objToIntMap;
        String str3 = str;
        Class<?>[] clsArr2 = clsArr;
        String str4 = str2;
        l0 l0Var = new l0(str3, cls.getName(), "<adapter>");
        l0Var.k("factory", "Lorg/mozilla/javascript/ContextFactory;", 17);
        l0Var.k("delegee", "Lorg/mozilla/javascript/Scriptable;", 17);
        l0Var.k("self", "Lorg/mozilla/javascript/Scriptable;", 17);
        if (clsArr2 == null) {
            i = 0;
        } else {
            i = clsArr2.length;
        }
        for (int i3 = 0; i3 < i; i3++) {
            Class<?> cls2 = clsArr2[i3];
            if (cls2 != null) {
                l0Var.r.add(Short.valueOf(l0Var.k.a(cls2.getName())));
            }
        }
        String replace = cls.getName().replace('.', '/');
        for (Constructor constructor : cls.getDeclaredConstructors()) {
            int modifiers = constructor.getModifiers();
            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                generateCtor(l0Var, str3, replace, constructor);
            }
        }
        generateSerialCtor(l0Var, str3, replace);
        if (str4 != null) {
            generateEmptyCtor(l0Var, str3, replace, str4);
        }
        ObjToIntMap objToIntMap3 = new ObjToIntMap();
        ObjToIntMap objToIntMap4 = new ObjToIntMap();
        int i4 = 0;
        while (i4 < i) {
            Method[] methods = clsArr2[i4].getMethods();
            int i5 = 0;
            while (i5 < methods.length) {
                Method method = methods[i5];
                int modifiers2 = method.getModifiers();
                if (!Modifier.isStatic(modifiers2) && !Modifier.isFinal(modifiers2) && !method.isDefault()) {
                    String name = method.getName();
                    Class[] parameterTypes = method.getParameterTypes();
                    if (!objToIntMap2.has(name)) {
                        try {
                            cls.getMethod(name, parameterTypes);
                        } catch (NoSuchMethodException unused) {
                        }
                    } else {
                        Class<?> cls3 = cls;
                        String x = y2.x(name, getMethodSignature(method, parameterTypes));
                        if (!objToIntMap3.has(x)) {
                            String str5 = name;
                            i2 = i5;
                            generateMethod(l0Var, str, name, parameterTypes, method.getReturnType(), true);
                            objToIntMap3.put(x, 0);
                            objToIntMap4.put(str5, 0);
                            i5 = i2 + 1;
                            Class<?>[] clsArr3 = clsArr;
                        }
                    }
                }
                i2 = i5;
                i5 = i2 + 1;
                Class<?>[] clsArr32 = clsArr;
            }
            i4++;
            clsArr2 = clsArr;
        }
        Method[] overridableMethods = getOverridableMethods(cls);
        int i6 = 0;
        while (i6 < overridableMethods.length) {
            Method method2 = overridableMethods[i6];
            boolean isAbstract = Modifier.isAbstract(method2.getModifiers());
            String name2 = method2.getName();
            if (isAbstract || objToIntMap2.has(name2)) {
                Class[] parameterTypes2 = method2.getParameterTypes();
                String methodSignature = getMethodSignature(method2, parameterTypes2);
                String x2 = y2.x(name2, methodSignature);
                if (!objToIntMap3.has(x2)) {
                    String str6 = methodSignature;
                    Class[] clsArr4 = parameterTypes2;
                    methodArr = overridableMethods;
                    String str7 = name2;
                    generateMethod(l0Var, str, name2, parameterTypes2, method2.getReturnType(), true);
                    objToIntMap3.put(x2, 0);
                    objToIntMap4.put(str7, 0);
                    if (!isAbstract) {
                        generateSuper(l0Var, str, replace, str7, str6, clsArr4, method2.getReturnType());
                    }
                    i6++;
                    String str8 = str;
                    overridableMethods = methodArr;
                }
            }
            methodArr = overridableMethods;
            i6++;
            String str82 = str;
            overridableMethods = methodArr;
        }
        ObjToIntMap.Iterator iterator = new ObjToIntMap.Iterator(objToIntMap2);
        iterator.start();
        while (!iterator.done()) {
            String str9 = (String) iterator.getKey();
            if (!objToIntMap4.has(str9)) {
                int value = iterator.getValue();
                Class[] clsArr5 = new Class[value];
                for (int i7 = 0; i7 < value; i7++) {
                    clsArr5[i7] = ScriptRuntime.ObjectClass;
                }
                generateMethod(l0Var, str, str9, clsArr5, ScriptRuntime.ObjectClass, false);
            }
            iterator.next();
        }
        return l0Var.ar();
    }

    public static Scriptable createAdapterWrapper(Scriptable scriptable, Object obj) {
        NativeJavaObject nativeJavaObject = new NativeJavaObject(ScriptableObject.getTopLevelScope(scriptable), obj, (Class<?>) null, true);
        nativeJavaObject.setPrototype(scriptable);
        return nativeJavaObject;
    }

    /* access modifiers changed from: private */
    public static Object doCall(Context context, Scriptable scriptable, Scriptable scriptable2, Function function, Object[] objArr, long j) {
        for (int i = 0; i != objArr.length; i++) {
            if (0 != (((long) (1 << i)) & j)) {
                Object obj = objArr[i];
                if (!(obj instanceof Scriptable)) {
                    objArr[i] = context.getWrapFactory().wrap(context, scriptable, obj, (Class<?>) null);
                }
            }
        }
        return function.call(context, scriptable, scriptable2, objArr);
    }

    private static void generateCtor(l0 l0Var, String str, String str2, Constructor<?> constructor) {
        Class[] parameterTypes = constructor.getParameterTypes();
        short s = 3;
        if (parameterTypes.length == 0) {
            l0Var.ap("<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/ContextFactory;)V", 1);
            l0Var.c(42);
            l0Var.m(str2, 183, "<init>", "()V");
        } else {
            StringBuilder sb = new StringBuilder("(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/ContextFactory;");
            int length = sb.length();
            for (Class appendTypeString : parameterTypes) {
                appendTypeString(sb, appendTypeString);
            }
            sb.append(")V");
            l0Var.ap("<init>", sb.toString(), 1);
            l0Var.c(42);
            for (Class generatePushParam : parameterTypes) {
                s = (short) (s + generatePushParam(l0Var, s, generatePushParam));
            }
            sb.delete(1, length);
            l0Var.m(str2, 183, "<init>", sb.toString());
        }
        l0Var.c(42);
        l0Var.c(43);
        l0Var.f(str, 181, "delegee", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(42);
        l0Var.c(44);
        l0Var.f(str, 181, "factory", "Lorg/mozilla/javascript/ContextFactory;");
        l0Var.c(42);
        l0Var.c(43);
        l0Var.c(42);
        l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "createAdapterWrapper", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
        l0Var.f(str, 181, "self", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(177);
        l0Var.aq(s);
    }

    private static void generateEmptyCtor(l0 l0Var, String str, String str2, String str3) {
        l0Var.ap("<init>", "()V", 1);
        l0Var.c(42);
        l0Var.m(str2, 183, "<init>", "()V");
        l0Var.c(42);
        l0Var.c(1);
        l0Var.f(str, 181, "factory", "Lorg/mozilla/javascript/ContextFactory;");
        l0Var.e(187, str3);
        l0Var.c(89);
        l0Var.m(str3, 183, "<init>", "()V");
        l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "runScript", "(Lorg/mozilla/javascript/Script;)Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(76);
        l0Var.c(42);
        l0Var.c(43);
        l0Var.f(str, 181, "delegee", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(42);
        l0Var.c(43);
        l0Var.c(42);
        l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "createAdapterWrapper", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
        l0Var.f(str, 181, "self", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(177);
        l0Var.aq(2);
    }

    private static void generateMethod(l0 l0Var, String str, String str2, Class<?>[] clsArr, Class<?> cls, boolean z) {
        StringBuilder sb = new StringBuilder();
        int appendMethodSignature = appendMethodSignature(clsArr, cls, sb);
        l0Var.ap(str2, sb.toString(), 1);
        l0Var.c(42);
        l0Var.f(str, Context.VERSION_1_8, "factory", "Lorg/mozilla/javascript/ContextFactory;");
        l0Var.c(42);
        l0Var.f(str, Context.VERSION_1_8, "self", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(42);
        l0Var.f(str, Context.VERSION_1_8, "delegee", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.t(str2);
        l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "getFunction", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Lorg/mozilla/javascript/Function;");
        generatePushWrappedArgs(l0Var, clsArr, clsArr.length);
        if (clsArr.length <= 64) {
            long j = 0;
            for (int i = 0; i != clsArr.length; i++) {
                if (!clsArr[i].isPrimitive()) {
                    j |= (long) (1 << i);
                }
            }
            int i2 = (int) j;
            if (((long) i2) == j) {
                l0Var.s(i2);
                l0Var.c(Token.LOOP);
            } else {
                s0 s0Var = l0Var.k;
                s0Var.d(9);
                byte[] bArr = s0Var.k;
                int i3 = s0Var.g;
                int i4 = i3 + 1;
                s0Var.g = i4;
                bArr[i3] = 5;
                s0Var.g = l0.al(bArr, i2, l0.al(bArr, (int) (j >>> 32), i4));
                int i5 = s0Var.h;
                s0Var.h = i5 + 2;
                s0Var.j.put(i5, 5);
                l0Var.d(20, i5);
            }
            l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "callMethod", "(Lorg/mozilla/javascript/ContextFactory;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Function;[Ljava/lang/Object;J)Ljava/lang/Object;");
            generateReturnResult(l0Var, cls, z);
            l0Var.aq((short) appendMethodSignature);
            return;
        }
        throw Context.reportRuntimeError0("JavaAdapter can not subclass methods with more then 64 arguments.");
    }

    private static void generatePopResult(l0 l0Var, Class<?> cls) {
        if (cls.isPrimitive()) {
            char charAt = cls.getName().charAt(0);
            if (charAt != 'f') {
                if (charAt != 'i') {
                    if (charAt == 'l') {
                        l0Var.c(173);
                        return;
                    } else if (!(charAt == 's' || charAt == 'z')) {
                        switch (charAt) {
                            case 'b':
                            case 'c':
                                break;
                            case 'd':
                                l0Var.c(175);
                                return;
                            default:
                                return;
                        }
                    }
                }
                l0Var.c(172);
                return;
            }
            l0Var.c(174);
            return;
        }
        l0Var.c(176);
    }

    private static int generatePushParam(l0 l0Var, int i, Class<?> cls) {
        if (!cls.isPrimitive()) {
            l0Var.g(i);
            return 1;
        }
        char charAt = cls.getName().charAt(0);
        if (charAt != 'f') {
            if (charAt != 'i') {
                if (charAt == 'l') {
                    l0Var.as(30, 22, i);
                    return 2;
                } else if (!(charAt == 's' || charAt == 'z')) {
                    switch (charAt) {
                        case 'b':
                        case 'c':
                            break;
                        case 'd':
                            l0Var.i(i);
                            return 2;
                        default:
                            throw Kit.codeBug();
                    }
                }
            }
            l0Var.l(i);
            return 1;
        }
        l0Var.as(34, 23, i);
        return 1;
    }

    public static void generatePushWrappedArgs(l0 l0Var, Class<?>[] clsArr, int i) {
        l0Var.s(i);
        l0Var.e(189, "java/lang/Object");
        int i2 = 1;
        for (int i3 = 0; i3 != clsArr.length; i3++) {
            l0Var.c(89);
            l0Var.s(i3);
            i2 += generateWrapArg(l0Var, i2, clsArr[i3]);
            l0Var.c(83);
        }
    }

    public static void generateReturnResult(l0 l0Var, Class<?> cls, boolean z) {
        if (cls == Void.TYPE) {
            l0Var.c(87);
            l0Var.c(177);
        } else if (cls == Boolean.TYPE) {
            l0Var.m("org/mozilla/javascript/Context", 184, "toBoolean", "(Ljava/lang/Object;)Z");
            l0Var.c(172);
        } else if (cls == Character.TYPE) {
            l0Var.m("org/mozilla/javascript/Context", 184, "toString", "(Ljava/lang/Object;)Ljava/lang/String;");
            l0Var.c(3);
            l0Var.m("java/lang/String", 182, "charAt", "(I)C");
            l0Var.c(172);
        } else if (cls.isPrimitive()) {
            l0Var.m("org/mozilla/javascript/Context", 184, "toNumber", "(Ljava/lang/Object;)D");
            char charAt = cls.getName().charAt(0);
            if (charAt != 'b') {
                if (charAt == 'd') {
                    l0Var.c(175);
                    return;
                } else if (charAt == 'f') {
                    l0Var.c(Token.DOTDOT);
                    l0Var.c(174);
                    return;
                } else if (charAt != 'i') {
                    if (charAt == 'l') {
                        l0Var.c(Token.SET_REF_OP);
                        l0Var.c(173);
                        return;
                    } else if (charAt != 's') {
                        throw new RuntimeException("Unexpected return type " + cls);
                    }
                }
            }
            l0Var.c(Token.LOCAL_BLOCK);
            l0Var.c(172);
        } else {
            String name = cls.getName();
            if (z) {
                l0Var.p(name);
                l0Var.m("java/lang/Class", 184, "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
                l0Var.m("org/mozilla/javascript/JavaAdapter", 184, "convertResult", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
            }
            l0Var.e(192, name);
            l0Var.c(176);
        }
    }

    private static void generateSerialCtor(l0 l0Var, String str, String str2) {
        l0Var.ap("<init>", "(Lorg/mozilla/javascript/ContextFactory;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;)V", 1);
        l0Var.c(42);
        l0Var.m(str2, 183, "<init>", "()V");
        l0Var.c(42);
        l0Var.c(43);
        l0Var.f(str, 181, "factory", "Lorg/mozilla/javascript/ContextFactory;");
        l0Var.c(42);
        l0Var.c(44);
        l0Var.f(str, 181, "delegee", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(42);
        l0Var.c(45);
        l0Var.f(str, 181, "self", "Lorg/mozilla/javascript/Scriptable;");
        l0Var.c(177);
        l0Var.aq(4);
    }

    private static void generateSuper(l0 l0Var, String str, String str2, String str3, String str4, Class<?>[] clsArr, Class<?> cls) {
        l0Var.ap("super$" + str3, str4, 1);
        l0Var.d(25, 0);
        int i = 1;
        for (Class<?> generatePushParam : clsArr) {
            i += generatePushParam(l0Var, i, generatePushParam);
        }
        l0Var.m(str2, 183, str3, str4);
        if (!cls.equals(Void.TYPE)) {
            generatePopResult(l0Var, cls);
        } else {
            l0Var.c(177);
        }
        l0Var.aq((short) (i + 1));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0068, code lost:
        if (r9 != 's') goto L_0x0090;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int generateWrapArg(defpackage.l0 r7, int r8, java.lang.Class<?> r9) {
        /*
            boolean r0 = r9.isPrimitive()
            r1 = 1
            if (r0 != 0) goto L_0x000e
            r9 = 25
            r7.d(r9, r8)
            goto L_0x0095
        L_0x000e:
            java.lang.Class r0 = java.lang.Boolean.TYPE
            java.lang.String r2 = "<init>"
            r3 = 183(0xb7, float:2.56E-43)
            r4 = 89
            r5 = 187(0xbb, float:2.62E-43)
            r6 = 21
            if (r9 != r0) goto L_0x002d
            java.lang.String r9 = "java/lang/Boolean"
            r7.e(r5, r9)
            r7.c(r4)
            r7.d(r6, r8)
            java.lang.String r8 = "(Z)V"
            r7.m(r9, r3, r2, r8)
            goto L_0x0095
        L_0x002d:
            java.lang.Class r0 = java.lang.Character.TYPE
            if (r9 != r0) goto L_0x0040
            r7.d(r6, r8)
            java.lang.String r8 = "valueOf"
            java.lang.String r9 = "(C)Ljava/lang/String;"
            java.lang.String r0 = "java/lang/String"
            r2 = 184(0xb8, float:2.58E-43)
            r7.m(r0, r2, r8, r9)
            goto L_0x0095
        L_0x0040:
            java.lang.String r0 = "java/lang/Double"
            r7.e(r5, r0)
            r7.c(r4)
            java.lang.String r9 = r9.getName()
            r4 = 0
            char r9 = r9.charAt(r4)
            r4 = 98
            if (r9 == r4) goto L_0x0088
            r4 = 100
            r5 = 2
            if (r9 == r4) goto L_0x0081
            r4 = 102(0x66, float:1.43E-43)
            if (r9 == r4) goto L_0x0076
            r4 = 105(0x69, float:1.47E-43)
            if (r9 == r4) goto L_0x0088
            r4 = 108(0x6c, float:1.51E-43)
            if (r9 == r4) goto L_0x006b
            r4 = 115(0x73, float:1.61E-43)
            if (r9 == r4) goto L_0x0088
            goto L_0x0090
        L_0x006b:
            r9 = 22
            r7.d(r9, r8)
            r8 = 138(0x8a, float:1.93E-43)
            r7.c(r8)
            goto L_0x0086
        L_0x0076:
            r9 = 23
            r7.d(r9, r8)
            r8 = 141(0x8d, float:1.98E-43)
            r7.c(r8)
            goto L_0x0090
        L_0x0081:
            r9 = 24
            r7.d(r9, r8)
        L_0x0086:
            r1 = 2
            goto L_0x0090
        L_0x0088:
            r7.d(r6, r8)
            r8 = 135(0x87, float:1.89E-43)
            r7.c(r8)
        L_0x0090:
            java.lang.String r8 = "(D)V"
            r7.m(r0, r3, r2, r8)
        L_0x0095:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.JavaAdapter.generateWrapArg(l0, int, java.lang.Class):int");
    }

    private static Class<?> getAdapterClass(Scriptable scriptable, Class<?> cls, Class<?>[] clsArr, Scriptable scriptable2) {
        ClassCache classCache = ClassCache.get(scriptable);
        Map<JavaAdapterSignature, Class<?>> interfaceAdapterCacheMap = classCache.getInterfaceAdapterCacheMap();
        ObjToIntMap objectFunctionNames = getObjectFunctionNames(scriptable2);
        JavaAdapterSignature javaAdapterSignature = new JavaAdapterSignature(cls, clsArr, objectFunctionNames);
        Class<?> cls2 = interfaceAdapterCacheMap.get(javaAdapterSignature);
        if (cls2 == null) {
            String str = "adapter" + classCache.newClassSerialNumber();
            cls2 = loadAdapterClass(str, createAdapterCode(objectFunctionNames, str, cls, clsArr, (String) null));
            if (classCache.isCachingEnabled()) {
                interfaceAdapterCacheMap.put(javaAdapterSignature, cls2);
            }
        }
        return cls2;
    }

    public static Object getAdapterSelf(Class<?> cls, Object obj) throws NoSuchFieldException, IllegalAccessException {
        return cls.getDeclaredField("self").get(obj);
    }

    public static int[] getArgsToConvert(Class<?>[] clsArr) {
        int i = 0;
        for (int i2 = 0; i2 != clsArr.length; i2++) {
            if (!clsArr[i2].isPrimitive()) {
                i++;
            }
        }
        if (i == 0) {
            return null;
        }
        int[] iArr = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 != clsArr.length; i4++) {
            if (!clsArr[i4].isPrimitive()) {
                iArr[i3] = i4;
                i3++;
            }
        }
        return iArr;
    }

    public static Function getFunction(Scriptable scriptable, String str) {
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (property == Scriptable.NOT_FOUND) {
            return null;
        }
        if (property instanceof Function) {
            return (Function) property;
        }
        throw ScriptRuntime.notFunctionError(property, str);
    }

    private static String getMethodSignature(Method method, Class<?>[] clsArr) {
        StringBuilder sb = new StringBuilder();
        appendMethodSignature(clsArr, method.getReturnType(), sb);
        return sb.toString();
    }

    private static ObjToIntMap getObjectFunctionNames(Scriptable scriptable) {
        Object[] propertyIds = ScriptableObject.getPropertyIds(scriptable);
        ObjToIntMap objToIntMap = new ObjToIntMap(propertyIds.length);
        for (int i = 0; i != propertyIds.length; i++) {
            Object obj = propertyIds[i];
            if (obj instanceof String) {
                String str = (String) obj;
                Object property = ScriptableObject.getProperty(scriptable, str);
                if (property instanceof Function) {
                    int int32 = ScriptRuntime.toInt32(ScriptableObject.getProperty((Scriptable) (Function) property, "length"));
                    if (int32 < 0) {
                        int32 = 0;
                    }
                    objToIntMap.put(str, int32);
                }
            }
        }
        return objToIntMap;
    }

    public static Method[] getOverridableMethods(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (Class<?> cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
            appendOverridableMethods(cls2, arrayList, hashSet);
        }
        for (Class<? super Object> cls3 = cls; cls3 != null; cls3 = cls3.getSuperclass()) {
            for (Class appendOverridableMethods : cls3.getInterfaces()) {
                appendOverridableMethods(appendOverridableMethods, arrayList, hashSet);
            }
        }
        return (Method[]) arrayList.toArray(new Method[arrayList.size()]);
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        IdFunctionObject idFunctionObject = new IdFunctionObject(new JavaAdapter(), FTAG, 1, "JavaAdapter", 1, scriptable);
        idFunctionObject.markAsConstructor((Scriptable) null);
        if (z) {
            idFunctionObject.sealObject();
        }
        idFunctionObject.exportAsScopeProperty();
    }

    public static Object js_createAdapter(Context context, Scriptable scriptable, Object[] objArr) {
        Object obj;
        int length = objArr.length;
        if (length != 0) {
            int i = 0;
            while (i < length - 1) {
                Object obj2 = objArr[i];
                if (obj2 instanceof NativeObject) {
                    break;
                } else if (obj2 instanceof NativeJavaClass) {
                    i++;
                } else {
                    throw ScriptRuntime.typeError2("msg.not.java.class.arg", String.valueOf(i), ScriptRuntime.toString(obj2));
                }
            }
            Class[] clsArr = new Class[i];
            Class<?> cls = null;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                Class<?> classObject = objArr[i3].getClassObject();
                if (classObject.isInterface()) {
                    clsArr[i2] = classObject;
                    i2++;
                } else if (cls == null) {
                    cls = classObject;
                } else {
                    throw ScriptRuntime.typeError2("msg.only.one.super", cls.getName(), classObject.getName());
                }
            }
            if (cls == null) {
                cls = ScriptRuntime.ObjectClass;
            }
            Class[] clsArr2 = new Class[i2];
            System.arraycopy(clsArr, 0, clsArr2, 0, i2);
            Scriptable ensureScriptable = ScriptableObject.ensureScriptable(objArr[i]);
            Class<?> adapterClass = getAdapterClass(scriptable, cls, clsArr2, ensureScriptable);
            int i4 = (length - i) - 1;
            if (i4 > 0) {
                try {
                    Object[] objArr2 = new Object[(i4 + 2)];
                    objArr2[0] = ensureScriptable;
                    objArr2[1] = context.getFactory();
                    System.arraycopy(objArr, i + 1, objArr2, 2, i4);
                    NativeJavaMethod nativeJavaMethod = new NativeJavaClass(scriptable, adapterClass, true).members.ctors;
                    int findCachedFunction = nativeJavaMethod.findCachedFunction(context, objArr2);
                    if (findCachedFunction >= 0) {
                        obj = NativeJavaClass.constructInternal(objArr2, nativeJavaMethod.methods[findCachedFunction]);
                    } else {
                        throw Context.reportRuntimeError2("msg.no.java.ctor", adapterClass.getName(), NativeJavaMethod.scriptSignature(objArr));
                    }
                } catch (Exception e) {
                    throw Context.throwAsScriptRuntimeEx(e);
                }
            } else {
                Class[] clsArr3 = {ScriptRuntime.ScriptableClass, ScriptRuntime.ContextFactoryClass};
                obj = adapterClass.getConstructor(clsArr3).newInstance(new Object[]{ensureScriptable, context.getFactory()});
            }
            Object adapterSelf = getAdapterSelf(adapterClass, obj);
            if (adapterSelf instanceof Wrapper) {
                Object unwrap = ((Wrapper) adapterSelf).unwrap();
                if (unwrap instanceof Scriptable) {
                    if (unwrap instanceof ScriptableObject) {
                        ScriptRuntime.setObjectProtoAndParent((ScriptableObject) unwrap, scriptable);
                    }
                    return unwrap;
                }
            }
            return adapterSelf;
        }
        throw ScriptRuntime.typeError0("msg.adapter.zero.args");
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ ScriptableObject lambda$runScript$1(Script script, Context context) {
        ScriptableObject global = ScriptRuntime.getGlobal(context);
        script.exec(context, global);
        return global;
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [java.security.CodeSource] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Class<?> loadAdapterClass(java.lang.String r4, byte[] r5) {
        /*
            java.lang.Class r0 = org.mozilla.javascript.SecurityController.getStaticSecurityDomainClass()
            r1 = 0
            java.lang.Class<java.security.CodeSource> r2 = java.security.CodeSource.class
            if (r0 == r2) goto L_0x0010
            java.lang.Class<java.security.ProtectionDomain> r3 = java.security.ProtectionDomain.class
            if (r0 != r3) goto L_0x000e
            goto L_0x0010
        L_0x000e:
            r3 = r1
            goto L_0x0026
        L_0x0010:
            java.security.ProtectionDomain r3 = org.mozilla.javascript.SecurityUtilities.getScriptProtectionDomain()
            if (r3 != 0) goto L_0x001c
            java.lang.Class<org.mozilla.javascript.JavaAdapter> r3 = org.mozilla.javascript.JavaAdapter.class
            java.security.ProtectionDomain r3 = r3.getProtectionDomain()
        L_0x001c:
            if (r0 != r2) goto L_0x0026
            if (r3 != 0) goto L_0x0021
            goto L_0x000e
        L_0x0021:
            java.security.CodeSource r0 = r3.getCodeSource()
            r3 = r0
        L_0x0026:
            org.mozilla.javascript.GeneratedClassLoader r0 = org.mozilla.javascript.SecurityController.createLoader(r1, r3)
            java.lang.Class r4 = r0.defineClass(r4, r5)
            r0.linkClass(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.JavaAdapter.loadAdapterClass(java.lang.String, byte[]):java.lang.Class");
    }

    public static Object readAdapterObject(Scriptable scriptable, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        ContextFactory contextFactory;
        Context currentContext = Context.getCurrentContext();
        if (currentContext != null) {
            contextFactory = currentContext.getFactory();
        } else {
            contextFactory = null;
        }
        Class<?> cls = Class.forName((String) objectInputStream.readObject());
        String[] strArr = (String[]) objectInputStream.readObject();
        Class[] clsArr = new Class[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            clsArr[i] = Class.forName(strArr[i]);
        }
        Scriptable scriptable2 = (Scriptable) objectInputStream.readObject();
        Class<?> adapterClass = getAdapterClass(scriptable, cls, clsArr, scriptable2);
        Class<Scriptable> cls2 = ScriptRuntime.ScriptableClass;
        try {
            return adapterClass.getConstructor(new Class[]{ScriptRuntime.ContextFactoryClass, cls2, cls2}).newInstance(new Object[]{contextFactory, scriptable2, scriptable});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            throw new ClassNotFoundException("adapter");
        }
    }

    public static Scriptable runScript(Script script) {
        return (Scriptable) ContextFactory.getGlobal().call(new i2(13, script));
    }

    public static void writeAdapterObject(Object obj, ObjectOutputStream objectOutputStream) throws IOException {
        Class<?> cls = obj.getClass();
        objectOutputStream.writeObject(cls.getSuperclass().getName());
        Class[] interfaces = cls.getInterfaces();
        String[] strArr = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            strArr[i] = interfaces[i].getName();
        }
        objectOutputStream.writeObject(strArr);
        try {
            objectOutputStream.writeObject(cls.getField("delegee").get(obj));
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            throw new IOException();
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (idFunctionObject.hasTag(FTAG) && idFunctionObject.methodId() == 1) {
            return js_createAdapter(context, scriptable, objArr);
        }
        throw idFunctionObject.unknown();
    }
}
