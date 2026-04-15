package org.mozilla.javascript;

import java.lang.reflect.Array;
import java.util.Map;

public class NativeJavaClass extends NativeJavaObject implements Function {
    static final String javaClassPropertyName = "__javaObject__";
    private static final long serialVersionUID = -6460763940409461664L;
    private Map<String, FieldAndMethods> staticFieldAndMethods;

    public NativeJavaClass() {
    }

    public static Object constructInternal(Object[] objArr, MemberBox memberBox) {
        Object[] objArr2;
        Object obj;
        Class<?>[] clsArr = memberBox.argTypes;
        int i = 0;
        if (memberBox.vararg) {
            objArr2 = new Object[clsArr.length];
            for (int i2 = 0; i2 < clsArr.length - 1; i2++) {
                objArr2[i2] = Context.jsToJava(objArr[i2], clsArr[i2]);
            }
            if (objArr.length != clsArr.length || (objArr[objArr.length - 1] != null && !(objArr[objArr.length - 1] instanceof NativeArray) && !(objArr[objArr.length - 1] instanceof NativeJavaArray))) {
                Class<?> componentType = clsArr[clsArr.length - 1].getComponentType();
                Object newInstance = Array.newInstance(componentType, (objArr.length - clsArr.length) + 1);
                while (i < Array.getLength(newInstance)) {
                    Array.set(newInstance, i, Context.jsToJava(objArr[(clsArr.length - 1) + i], componentType));
                    i++;
                }
                obj = newInstance;
            } else {
                obj = Context.jsToJava(objArr[objArr.length - 1], clsArr[clsArr.length - 1]);
            }
            objArr2[clsArr.length - 1] = obj;
        } else {
            objArr2 = objArr;
            while (i < objArr2.length) {
                Object obj2 = objArr2[i];
                Object jsToJava = Context.jsToJava(obj2, clsArr[i]);
                if (jsToJava != obj2) {
                    if (objArr2 == objArr) {
                        objArr2 = (Object[]) objArr.clone();
                    }
                    objArr2[i] = jsToJava;
                }
                i++;
            }
        }
        return memberBox.newInstance(objArr2);
    }

    public static Scriptable constructSpecific(Context context, Scriptable scriptable, Object[] objArr, MemberBox memberBox) {
        Object constructInternal = constructInternal(objArr, memberBox);
        return context.getWrapFactory().wrapNewObject(context, ScriptableObject.getTopLevelScope(scriptable), constructInternal);
    }

    private static Class<?> findNestedClass(Class<?> cls, String str) {
        String str2 = cls.getName() + '$' + str;
        ClassLoader classLoader = cls.getClassLoader();
        if (classLoader == null) {
            return Kit.classOrNull(str2);
        }
        return Kit.classOrNull(classLoader, str2);
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (objArr.length != 1 || !(objArr[0] instanceof Scriptable)) {
            return construct(context, scriptable, objArr);
        }
        Class<?> classObject = getClassObject();
        Scriptable scriptable3 = objArr[0];
        do {
            if ((scriptable3 instanceof Wrapper) && classObject.isInstance(((Wrapper) scriptable3).unwrap())) {
                return scriptable3;
            }
            scriptable3 = scriptable3.getPrototype();
        } while (scriptable3 != null);
        return construct(context, scriptable, objArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0086, code lost:
        if (r5 == null) goto L_0x0089;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.Scriptable construct(org.mozilla.javascript.Context r5, org.mozilla.javascript.Scriptable r6, java.lang.Object[] r7) {
        /*
            r4 = this;
            java.lang.Class r0 = r4.getClassObject()
            int r1 = r0.getModifiers()
            boolean r2 = java.lang.reflect.Modifier.isInterface(r1)
            if (r2 != 0) goto L_0x0036
            boolean r1 = java.lang.reflect.Modifier.isAbstract(r1)
            if (r1 != 0) goto L_0x0036
            org.mozilla.javascript.JavaMembers r1 = r4.members
            org.mozilla.javascript.NativeJavaMethod r1 = r1.ctors
            int r2 = r1.findCachedFunction(r5, r7)
            if (r2 < 0) goto L_0x0027
            org.mozilla.javascript.MemberBox[] r0 = r1.methods
            r0 = r0[r2]
            org.mozilla.javascript.Scriptable r5 = constructSpecific(r5, r6, r7, r0)
            return r5
        L_0x0027:
            java.lang.String r5 = org.mozilla.javascript.NativeJavaMethod.scriptSignature(r7)
            java.lang.String r6 = r0.getName()
            java.lang.String r7 = "msg.no.java.ctor"
            org.mozilla.javascript.EvaluatorException r5 = org.mozilla.javascript.Context.reportRuntimeError2(r7, r6, r5)
            throw r5
        L_0x0036:
            int r1 = r7.length
            if (r1 == 0) goto L_0x0096
            org.mozilla.javascript.Scriptable r1 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r4)
            java.lang.String r2 = "Dalvik"
            java.lang.String r3 = "java.vm.name"
            java.lang.String r3 = java.lang.System.getProperty(r3)     // Catch:{ Exception -> 0x0081 }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x0081 }
            r3 = 0
            if (r2 == 0) goto L_0x0066
            boolean r2 = r0.isInterface()     // Catch:{ Exception -> 0x0081 }
            if (r2 == 0) goto L_0x0066
            r7 = r7[r3]     // Catch:{ Exception -> 0x0081 }
            org.mozilla.javascript.ScriptableObject r7 = org.mozilla.javascript.ScriptableObject.ensureScriptableObject(r7)     // Catch:{ Exception -> 0x0081 }
            java.lang.Object r7 = org.mozilla.javascript.NativeJavaObject.createInterfaceAdapter(r0, r7)     // Catch:{ Exception -> 0x0081 }
            org.mozilla.javascript.WrapFactory r1 = r5.getWrapFactory()     // Catch:{ Exception -> 0x0081 }
            r2 = 0
            org.mozilla.javascript.Scriptable r5 = r1.wrapAsJavaObject(r5, r6, r7, r2)     // Catch:{ Exception -> 0x0081 }
            return r5
        L_0x0066:
            java.lang.String r6 = "JavaAdapter"
            java.lang.Object r6 = r1.get((java.lang.String) r6, (org.mozilla.javascript.Scriptable) r1)     // Catch:{ Exception -> 0x0081 }
            java.lang.Object r2 = org.mozilla.javascript.Scriptable.NOT_FOUND     // Catch:{ Exception -> 0x0081 }
            if (r6 == r2) goto L_0x0089
            org.mozilla.javascript.Function r6 = (org.mozilla.javascript.Function) r6     // Catch:{ Exception -> 0x0081 }
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0081 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0081 }
            r7 = r7[r3]     // Catch:{ Exception -> 0x0081 }
            r3 = 1
            r2[r3] = r7     // Catch:{ Exception -> 0x0081 }
            org.mozilla.javascript.Scriptable r5 = r6.construct(r5, r1, r2)     // Catch:{ Exception -> 0x0081 }
            return r5
        L_0x0081:
            r5 = move-exception
            java.lang.String r5 = r5.getMessage()
            if (r5 == 0) goto L_0x0089
            goto L_0x008b
        L_0x0089:
            java.lang.String r5 = ""
        L_0x008b:
            java.lang.String r6 = r0.getName()
            java.lang.String r7 = "msg.cant.instantiate"
            org.mozilla.javascript.EvaluatorException r5 = org.mozilla.javascript.Context.reportRuntimeError2(r7, r5, r6)
            throw r5
        L_0x0096:
            java.lang.String r5 = "msg.adapter.zero.args"
            org.mozilla.javascript.EvaluatorException r5 = org.mozilla.javascript.Context.reportRuntimeError0(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeJavaClass.construct(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[]):org.mozilla.javascript.Scriptable");
    }

    public Object get(String str, Scriptable scriptable) {
        FieldAndMethods fieldAndMethods;
        if (str.equals("prototype")) {
            return null;
        }
        Map<String, FieldAndMethods> map = this.staticFieldAndMethods;
        if (map != null && (fieldAndMethods = map.get(str)) != null) {
            return fieldAndMethods;
        }
        if (this.members.has(str, true)) {
            return this.members.get(this, str, this.javaObject, true);
        }
        Context context = Context.getContext();
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        WrapFactory wrapFactory = context.getWrapFactory();
        if (javaClassPropertyName.equals(str)) {
            return wrapFactory.wrap(context, topLevelScope, this.javaObject, ScriptRuntime.ClassClass);
        }
        Class<?> findNestedClass = findNestedClass(getClassObject(), str);
        if (findNestedClass != null) {
            Scriptable wrapJavaClass = wrapFactory.wrapJavaClass(context, topLevelScope, findNestedClass);
            wrapJavaClass.setParentScope(this);
            return wrapJavaClass;
        }
        throw this.members.reportMemberNotFound(str);
    }

    public String getClassName() {
        return "JavaClass";
    }

    public Class<?> getClassObject() {
        return (Class) super.unwrap();
    }

    public Object getDefaultValue(Class<?> cls) {
        if (cls == null || cls == ScriptRuntime.StringClass) {
            return toString();
        }
        if (cls == ScriptRuntime.BooleanClass) {
            return Boolean.TRUE;
        }
        if (cls == ScriptRuntime.NumberClass) {
            return ScriptRuntime.NaNobj;
        }
        return this;
    }

    public Object[] getIds() {
        return this.members.getIds(true);
    }

    public boolean has(String str, Scriptable scriptable) {
        return this.members.has(str, true) || javaClassPropertyName.equals(str);
    }

    public boolean hasInstance(Scriptable scriptable) {
        if (!(scriptable instanceof Wrapper) || (scriptable instanceof NativeJavaClass)) {
            return false;
        }
        return getClassObject().isInstance(((Wrapper) scriptable).unwrap());
    }

    public void initMembers() {
        Class cls = (Class) this.javaObject;
        JavaMembers lookupClass = JavaMembers.lookupClass(this.parent, cls, cls, this.isAdapter);
        this.members = lookupClass;
        this.staticFieldAndMethods = lookupClass.getFieldAndMethodsObjects(this, cls, true);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        this.members.put(this, str, this.javaObject, obj, true);
    }

    public String toString() {
        return "[JavaClass " + getClassObject().getName() + "]";
    }

    public NativeJavaClass(Scriptable scriptable, Class<?> cls) {
        this(scriptable, cls, false);
    }

    public NativeJavaClass(Scriptable scriptable, Class<?> cls, boolean z) {
        super(scriptable, cls, (Class<?>) null, z);
    }
}
