package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSSetter;
import org.mozilla.javascript.annotations.JSStaticFunction;
import org.mozilla.javascript.debug.DebuggableObject;

public abstract class ScriptableObject implements Scriptable, SymbolScriptable, Serializable, DebuggableObject, ConstProperties {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONST = 13;
    public static final int DONTENUM = 2;
    public static final int EMPTY = 0;
    private static final Method GET_ARRAY_LENGTH;
    private static final Comparator<Object> KEY_COMPARATOR = new KeyComparator();
    public static final int PERMANENT = 4;
    public static final int READONLY = 1;
    public static final int UNINITIALIZED_CONST = 8;
    private static final long serialVersionUID = 2829861078851942586L;
    private volatile Map<Object, Object> associatedValues;
    private transient ExternalArrayData externalData;
    private boolean isExtensible;
    private boolean isSealed;
    private Scriptable parentScopeObject;
    private Scriptable prototypeObject;
    private transient SlotMapContainer slotMap;

    public static final class GetterSlot extends Slot {
        private static final long serialVersionUID = -4900574849788797588L;
        Object getter;
        Object setter;

        public GetterSlot(Object obj, int i, int i2) {
            super(obj, i, i2);
        }

        public ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            boolean z;
            boolean z2;
            String str;
            int attributes = getAttributes();
            NativeObject nativeObject = new NativeObject();
            ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
            boolean z3 = true;
            if ((attributes & 2) == 0) {
                z = true;
            } else {
                z = false;
            }
            nativeObject.defineProperty("enumerable", (Object) Boolean.valueOf(z), 0);
            if ((attributes & 4) == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            nativeObject.defineProperty("configurable", (Object) Boolean.valueOf(z2), 0);
            if (this.getter == null && this.setter == null) {
                if ((attributes & 1) != 0) {
                    z3 = false;
                }
                nativeObject.defineProperty("writable", (Object) Boolean.valueOf(z3), 0);
            }
            Object obj = this.name;
            if (obj == null) {
                str = "f";
            } else {
                str = obj.toString();
            }
            Object obj2 = this.getter;
            if (obj2 != null) {
                if (obj2 instanceof MemberBox) {
                    nativeObject.defineProperty("get", (Object) new FunctionObject(str, ((MemberBox) this.getter).member(), scriptable), 0);
                } else if (obj2 instanceof Member) {
                    nativeObject.defineProperty("get", (Object) new FunctionObject(str, (Member) this.getter, scriptable), 0);
                } else {
                    nativeObject.defineProperty("get", obj2, 0);
                }
            }
            Object obj3 = this.setter;
            if (obj3 != null) {
                if (obj3 instanceof MemberBox) {
                    nativeObject.defineProperty("set", (Object) new FunctionObject(str, ((MemberBox) this.setter).member(), scriptable), 0);
                } else if (obj3 instanceof Member) {
                    nativeObject.defineProperty("set", (Object) new FunctionObject(str, (Member) this.setter, scriptable), 0);
                } else {
                    nativeObject.defineProperty("set", obj3, 0);
                }
            }
            return nativeObject;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: org.mozilla.javascript.Scriptable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: org.mozilla.javascript.Scriptable} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object getValue(org.mozilla.javascript.Scriptable r5) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.getter
                if (r0 == 0) goto L_0x0033
                boolean r1 = r0 instanceof org.mozilla.javascript.MemberBox
                if (r1 == 0) goto L_0x001e
                org.mozilla.javascript.MemberBox r0 = (org.mozilla.javascript.MemberBox) r0
                java.lang.Object r1 = r0.delegateTo
                if (r1 != 0) goto L_0x0011
                java.lang.Object[] r1 = org.mozilla.javascript.ScriptRuntime.emptyArgs
                goto L_0x0019
            L_0x0011:
                r2 = 1
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                r2[r3] = r5
                r5 = r1
                r1 = r2
            L_0x0019:
                java.lang.Object r5 = r0.invoke(r5, r1)
                return r5
            L_0x001e:
                boolean r1 = r0 instanceof org.mozilla.javascript.Function
                if (r1 == 0) goto L_0x0033
                org.mozilla.javascript.Function r0 = (org.mozilla.javascript.Function) r0
                org.mozilla.javascript.Context r1 = org.mozilla.javascript.Context.getContext()
                org.mozilla.javascript.Scriptable r2 = r0.getParentScope()
                java.lang.Object[] r3 = org.mozilla.javascript.ScriptRuntime.emptyArgs
                java.lang.Object r5 = r0.call(r1, r2, r5, r3)
                return r5
            L_0x0033:
                java.lang.Object r5 = r4.value
                boolean r0 = r5 instanceof org.mozilla.javascript.LazilyLoadedCtor
                if (r0 == 0) goto L_0x004d
                org.mozilla.javascript.LazilyLoadedCtor r5 = (org.mozilla.javascript.LazilyLoadedCtor) r5
                r5.init()     // Catch:{ all -> 0x0045 }
                java.lang.Object r5 = r5.getValue()
                r4.value = r5
                goto L_0x004d
            L_0x0045:
                r0 = move-exception
                java.lang.Object r5 = r5.getValue()
                r4.value = r5
                throw r0
            L_0x004d:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.GetterSlot.getValue(org.mozilla.javascript.Scriptable):java.lang.Object");
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: org.mozilla.javascript.Scriptable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: org.mozilla.javascript.Scriptable} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean setValue(java.lang.Object r6, org.mozilla.javascript.Scriptable r7, org.mozilla.javascript.Scriptable r8) {
            /*
                r5 = this;
                java.lang.Object r0 = r5.setter
                r1 = 1
                if (r0 != 0) goto L_0x0050
                java.lang.Object r0 = r5.getter
                if (r0 == 0) goto L_0x004b
                org.mozilla.javascript.Context r7 = org.mozilla.javascript.Context.getContext()
                boolean r0 = r7.isStrictMode()
                if (r0 != 0) goto L_0x001d
                r0 = 11
                boolean r7 = r7.hasFeature(r0)
                if (r7 == 0) goto L_0x001c
                goto L_0x001d
            L_0x001c:
                return r1
            L_0x001d:
                java.lang.Object r7 = r5.name
                if (r7 == 0) goto L_0x003e
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r0 = "["
                r7.<init>(r0)
                java.lang.String r8 = r8.getClassName()
                r7.append(r8)
                java.lang.String r8 = "]."
                r7.append(r8)
                java.lang.Object r8 = r5.name
                r7.append(r8)
                java.lang.String r7 = r7.toString()
                goto L_0x0040
            L_0x003e:
                java.lang.String r7 = ""
            L_0x0040:
                java.lang.String r8 = "msg.set.prop.no.setter"
                java.lang.String r6 = org.mozilla.javascript.Context.toString(r6)
                org.mozilla.javascript.EcmaError r6 = org.mozilla.javascript.ScriptRuntime.typeError2(r8, r7, r6)
                throw r6
            L_0x004b:
                boolean r6 = super.setValue(r6, r7, r8)
                return r6
            L_0x0050:
                org.mozilla.javascript.Context r7 = org.mozilla.javascript.Context.getContext()
                java.lang.Object r0 = r5.setter
                boolean r2 = r0 instanceof org.mozilla.javascript.MemberBox
                r3 = 0
                if (r2 == 0) goto L_0x0081
                org.mozilla.javascript.MemberBox r0 = (org.mozilla.javascript.MemberBox) r0
                java.lang.Class<?>[] r2 = r0.argTypes
                int r4 = r2.length
                int r4 = r4 - r1
                r2 = r2[r4]
                int r2 = org.mozilla.javascript.FunctionObject.getTypeTag(r2)
                java.lang.Object r6 = org.mozilla.javascript.FunctionObject.convertArg((org.mozilla.javascript.Context) r7, (org.mozilla.javascript.Scriptable) r8, (java.lang.Object) r6, (int) r2)
                java.lang.Object r7 = r0.delegateTo
                if (r7 != 0) goto L_0x0074
                java.lang.Object[] r7 = new java.lang.Object[r1]
                r7[r3] = r6
                goto L_0x007d
            L_0x0074:
                r2 = 2
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r2[r3] = r8
                r2[r1] = r6
                r8 = r7
                r7 = r2
            L_0x007d:
                r0.invoke(r8, r7)
                goto L_0x0092
            L_0x0081:
                boolean r2 = r0 instanceof org.mozilla.javascript.Function
                if (r2 == 0) goto L_0x0092
                org.mozilla.javascript.Function r0 = (org.mozilla.javascript.Function) r0
                org.mozilla.javascript.Scriptable r2 = r0.getParentScope()
                java.lang.Object[] r4 = new java.lang.Object[r1]
                r4[r3] = r6
                r0.call(r7, r2, r8, r4)
            L_0x0092:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.GetterSlot.setValue(java.lang.Object, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable):boolean");
        }
    }

    public static final class KeyComparator implements Comparator<Object>, Serializable {
        private static final long serialVersionUID = 6411335891523988149L;

        public int compare(Object obj, Object obj2) {
            int intValue;
            int intValue2;
            if (obj instanceof Integer) {
                if (!(obj2 instanceof Integer) || (intValue = ((Integer) obj).intValue()) < (intValue2 = ((Integer) obj2).intValue())) {
                    return -1;
                }
                if (intValue > intValue2) {
                    return 1;
                }
                return 0;
            } else if (obj2 instanceof Integer) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static class Slot implements Serializable {
        private static final long serialVersionUID = -6090581677123995491L;
        private short attributes;
        int indexOrHash;
        Object name;
        transient Slot next;
        transient Slot orderedNext;
        Object value;

        public Slot(Object obj, int i, int i2) {
            this.name = obj;
            this.indexOrHash = i;
            this.attributes = (short) i2;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            Object obj = this.name;
            if (obj != null) {
                this.indexOrHash = obj.hashCode();
            }
        }

        public int getAttributes() {
            return this.attributes;
        }

        public ScriptableObject getPropertyDescriptor(Context context, Scriptable scriptable) {
            return ScriptableObject.buildDataDescriptor(scriptable, this.value, this.attributes);
        }

        public Object getValue(Scriptable scriptable) {
            return this.value;
        }

        public synchronized void setAttributes(int i) {
            ScriptableObject.checkValidAttributes(i);
            this.attributes = (short) i;
        }

        public boolean setValue(Object obj, Scriptable scriptable, Scriptable scriptable2) {
            if ((this.attributes & 1) != 0) {
                if (!Context.getContext().isStrictMode()) {
                    return true;
                }
                throw ScriptRuntime.typeError1("msg.modify.readonly", this.name);
            } else if (scriptable != scriptable2) {
                return false;
            } else {
                this.value = obj;
                return true;
            }
        }
    }

    public enum SlotAccess {
        QUERY,
        MODIFY,
        MODIFY_CONST,
        MODIFY_GETTER_SETTER,
        CONVERT_ACCESSOR_TO_DATA
    }

    static {
        try {
            GET_ARRAY_LENGTH = ScriptableObject.class.getMethod("getExternalArrayLength", new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public ScriptableObject() {
        this.isExtensible = true;
        this.isSealed = false;
        this.slotMap = createSlotMap(0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: org.mozilla.javascript.FunctionObject} */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0227  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x02cf  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x02a7 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0143  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T extends org.mozilla.javascript.Scriptable> org.mozilla.javascript.BaseFunction buildClassCtor(org.mozilla.javascript.Scriptable r24, java.lang.Class<T> r25, boolean r26, boolean r27) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.reflect.InvocationTargetException {
        /*
            r0 = r24
            r1 = r26
            r2 = r27
            java.lang.reflect.Method[] r3 = org.mozilla.javascript.FunctionObject.getMethodList(r25)
            r4 = 0
            r5 = 0
        L_0x000c:
            int r6 = r3.length
            r7 = 3
            r8 = 2
            r9 = 0
            r10 = 1
            if (r5 >= r6) goto L_0x007a
            r6 = r3[r5]
            java.lang.String r11 = r6.getName()
            java.lang.String r12 = "init"
            boolean r11 = r11.equals(r12)
            if (r11 != 0) goto L_0x0022
            goto L_0x0077
        L_0x0022:
            java.lang.Class[] r11 = r6.getParameterTypes()
            int r12 = r11.length
            if (r12 != r7) goto L_0x005c
            r12 = r11[r4]
            java.lang.Class<?> r13 = org.mozilla.javascript.ScriptRuntime.ContextClass
            if (r12 != r13) goto L_0x005c
            r12 = r11[r10]
            java.lang.Class<org.mozilla.javascript.Scriptable> r13 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r12 != r13) goto L_0x005c
            r12 = r11[r8]
            java.lang.Class r13 = java.lang.Boolean.TYPE
            if (r12 != r13) goto L_0x005c
            int r12 = r6.getModifiers()
            boolean r12 = java.lang.reflect.Modifier.isStatic(r12)
            if (r12 == 0) goto L_0x005c
            java.lang.Object[] r2 = new java.lang.Object[r7]
            org.mozilla.javascript.Context r3 = org.mozilla.javascript.Context.getContext()
            r2[r4] = r3
            r2[r10] = r0
            if (r1 == 0) goto L_0x0054
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            goto L_0x0056
        L_0x0054:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
        L_0x0056:
            r2[r8] = r0
            r6.invoke(r9, r2)
            return r9
        L_0x005c:
            int r7 = r11.length
            if (r7 != r10) goto L_0x0077
            r7 = r11[r4]
            java.lang.Class<org.mozilla.javascript.Scriptable> r8 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r7 != r8) goto L_0x0077
            int r7 = r6.getModifiers()
            boolean r7 = java.lang.reflect.Modifier.isStatic(r7)
            if (r7 == 0) goto L_0x0077
            java.lang.Object[] r1 = new java.lang.Object[r10]
            r1[r4] = r0
            r6.invoke(r9, r1)
            return r9
        L_0x0077:
            int r5 = r5 + 1
            goto L_0x000c
        L_0x007a:
            java.lang.reflect.Constructor[] r5 = r25.getConstructors()
            r6 = 0
        L_0x007f:
            int r11 = r5.length
            if (r6 >= r11) goto L_0x0091
            r11 = r5[r6]
            java.lang.Class[] r11 = r11.getParameterTypes()
            int r11 = r11.length
            if (r11 != 0) goto L_0x008e
            r6 = r5[r6]
            goto L_0x0092
        L_0x008e:
            int r6 = r6 + 1
            goto L_0x007f
        L_0x0091:
            r6 = r9
        L_0x0092:
            if (r6 == 0) goto L_0x02da
            java.lang.Object[] r11 = org.mozilla.javascript.ScriptRuntime.emptyArgs
            java.lang.Object r6 = r6.newInstance(r11)
            org.mozilla.javascript.Scriptable r6 = (org.mozilla.javascript.Scriptable) r6
            java.lang.String r11 = r6.getClassName()
            org.mozilla.javascript.Scriptable r12 = getTopLevelScope(r24)
            java.lang.Object r12 = getProperty((org.mozilla.javascript.Scriptable) r12, (java.lang.String) r11)
            boolean r13 = r12 instanceof org.mozilla.javascript.BaseFunction
            if (r13 == 0) goto L_0x00c1
            org.mozilla.javascript.BaseFunction r12 = (org.mozilla.javascript.BaseFunction) r12
            java.lang.Object r13 = r12.getPrototypeProperty()
            if (r13 == 0) goto L_0x00c1
            java.lang.Class r13 = r13.getClass()
            r14 = r25
            boolean r13 = r14.equals(r13)
            if (r13 == 0) goto L_0x00c3
            return r12
        L_0x00c1:
            r14 = r25
        L_0x00c3:
            if (r2 == 0) goto L_0x00ea
            java.lang.Class r12 = r25.getSuperclass()
            java.lang.Class<org.mozilla.javascript.Scriptable> r13 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            boolean r13 = r13.isAssignableFrom(r12)
            if (r13 == 0) goto L_0x00ea
            int r13 = r12.getModifiers()
            boolean r13 = java.lang.reflect.Modifier.isAbstract(r13)
            if (r13 != 0) goto L_0x00ea
            java.lang.Class r12 = extendsScriptable(r12)
            java.lang.String r2 = defineClass(r0, r12, r1, r2)
            if (r2 == 0) goto L_0x00ea
            org.mozilla.javascript.Scriptable r2 = getClassPrototype(r0, r2)
            goto L_0x00eb
        L_0x00ea:
            r2 = r9
        L_0x00eb:
            if (r2 != 0) goto L_0x00f1
            org.mozilla.javascript.Scriptable r2 = getObjectPrototype(r24)
        L_0x00f1:
            r6.setPrototype(r2)
            java.lang.Class<org.mozilla.javascript.annotations.JSConstructor> r2 = org.mozilla.javascript.annotations.JSConstructor.class
            java.lang.reflect.Member r12 = findAnnotatedMember(r3, r2)
            if (r12 != 0) goto L_0x0100
            java.lang.reflect.Member r12 = findAnnotatedMember(r5, r2)
        L_0x0100:
            java.lang.String r2 = "jsConstructor"
            if (r12 != 0) goto L_0x0108
            java.lang.reflect.Method r12 = org.mozilla.javascript.FunctionObject.findSingleMethod(r3, r2)
        L_0x0108:
            if (r12 != 0) goto L_0x0138
            int r13 = r5.length
            if (r13 != r10) goto L_0x0110
            r12 = r5[r4]
            goto L_0x012a
        L_0x0110:
            int r13 = r5.length
            if (r13 != r8) goto L_0x012a
            r13 = r5[r4]
            java.lang.Class[] r13 = r13.getParameterTypes()
            int r13 = r13.length
            if (r13 != 0) goto L_0x011f
            r12 = r5[r10]
            goto L_0x012a
        L_0x011f:
            r13 = r5[r10]
            java.lang.Class[] r13 = r13.getParameterTypes()
            int r13 = r13.length
            if (r13 != 0) goto L_0x012a
            r12 = r5[r4]
        L_0x012a:
            if (r12 == 0) goto L_0x012d
            goto L_0x0138
        L_0x012d:
            java.lang.String r0 = r25.getName()
            java.lang.String r1 = "msg.ctor.multiple.parms"
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError1(r1, r0)
            throw r0
        L_0x0138:
            org.mozilla.javascript.FunctionObject r5 = new org.mozilla.javascript.FunctionObject
            r5.<init>(r11, r12, r0)
            boolean r11 = r5.isVarArgsMethod()
            if (r11 != 0) goto L_0x02cf
            r5.initAsConstructor(r0, r6)
            java.util.HashSet r11 = new java.util.HashSet
            r11.<init>()
            java.util.HashSet r13 = new java.util.HashSet
            r13.<init>()
            int r14 = r3.length
            r15 = 0
        L_0x0152:
            if (r15 >= r14) goto L_0x02ae
            r8 = r3[r15]
            if (r8 != r12) goto L_0x015f
            r22 = r2
            r25 = r11
            r27 = r13
            goto L_0x0196
        L_0x015f:
            java.lang.String r10 = r8.getName()
            java.lang.String r4 = "finishInit"
            boolean r4 = r10.equals(r4)
            if (r4 == 0) goto L_0x019a
            java.lang.Class[] r4 = r8.getParameterTypes()
            r25 = r11
            int r11 = r4.length
            if (r11 != r7) goto L_0x019c
            r11 = 0
            r7 = r4[r11]
            java.lang.Class<org.mozilla.javascript.Scriptable> r11 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r7 != r11) goto L_0x019c
            r27 = r13
            r7 = 1
            r13 = r4[r7]
            java.lang.Class<org.mozilla.javascript.FunctionObject> r7 = org.mozilla.javascript.FunctionObject.class
            if (r13 != r7) goto L_0x019e
            r7 = 2
            r4 = r4[r7]
            if (r4 != r11) goto L_0x019e
            int r4 = r8.getModifiers()
            boolean r4 = java.lang.reflect.Modifier.isStatic(r4)
            if (r4 == 0) goto L_0x019e
            r22 = r2
            r9 = r8
        L_0x0196:
            r23 = r14
            goto L_0x0288
        L_0x019a:
            r25 = r11
        L_0x019c:
            r27 = r13
        L_0x019e:
            r4 = 36
            int r4 = r10.indexOf(r4)
            r7 = -1
            if (r4 == r7) goto L_0x01aa
        L_0x01a7:
            r22 = r2
            goto L_0x0196
        L_0x01aa:
            boolean r4 = r10.equals(r2)
            if (r4 == 0) goto L_0x01b1
            goto L_0x01a7
        L_0x01b1:
            java.lang.Class<org.mozilla.javascript.annotations.JSFunction> r4 = org.mozilla.javascript.annotations.JSFunction.class
            boolean r7 = r8.isAnnotationPresent(r4)
            if (r7 == 0) goto L_0x01be
            java.lang.annotation.Annotation r4 = r8.getAnnotation(r4)
            goto L_0x01e2
        L_0x01be:
            java.lang.Class<org.mozilla.javascript.annotations.JSStaticFunction> r4 = org.mozilla.javascript.annotations.JSStaticFunction.class
            boolean r7 = r8.isAnnotationPresent(r4)
            if (r7 == 0) goto L_0x01cb
            java.lang.annotation.Annotation r4 = r8.getAnnotation(r4)
            goto L_0x01e2
        L_0x01cb:
            java.lang.Class<org.mozilla.javascript.annotations.JSGetter> r4 = org.mozilla.javascript.annotations.JSGetter.class
            boolean r7 = r8.isAnnotationPresent(r4)
            if (r7 == 0) goto L_0x01d8
            java.lang.annotation.Annotation r4 = r8.getAnnotation(r4)
            goto L_0x01e2
        L_0x01d8:
            java.lang.Class<org.mozilla.javascript.annotations.JSSetter> r4 = org.mozilla.javascript.annotations.JSSetter.class
            boolean r4 = r8.isAnnotationPresent(r4)
            if (r4 == 0) goto L_0x01e1
            goto L_0x01a7
        L_0x01e1:
            r4 = 0
        L_0x01e2:
            java.lang.String r7 = "jsGet_"
            java.lang.String r11 = "jsStaticFunction_"
            if (r4 != 0) goto L_0x0207
            java.lang.String r13 = "jsFunction_"
            boolean r16 = r10.startsWith(r13)
            if (r16 == 0) goto L_0x01f3
            r22 = r2
            goto L_0x020a
        L_0x01f3:
            boolean r13 = r10.startsWith(r11)
            if (r13 == 0) goto L_0x01fd
            r22 = r2
            r13 = r11
            goto L_0x020a
        L_0x01fd:
            boolean r13 = r10.startsWith(r7)
            if (r13 == 0) goto L_0x01a7
            r22 = r2
            r13 = r7
            goto L_0x020a
        L_0x0207:
            r22 = r2
            r13 = 0
        L_0x020a:
            boolean r2 = r4 instanceof org.mozilla.javascript.annotations.JSStaticFunction
            if (r2 != 0) goto L_0x0213
            if (r13 != r11) goto L_0x0211
            goto L_0x0213
        L_0x0211:
            r11 = 0
            goto L_0x0214
        L_0x0213:
            r11 = 1
        L_0x0214:
            if (r11 == 0) goto L_0x0219
            r2 = r25
            goto L_0x021b
        L_0x0219:
            r2 = r27
        L_0x021b:
            r23 = r14
            java.lang.String r14 = getPropertyName(r10, r13, r4)
            boolean r16 = r2.contains(r14)
            if (r16 != 0) goto L_0x02a7
            r2.add(r14)
            boolean r2 = r4 instanceof org.mozilla.javascript.annotations.JSGetter
            if (r2 != 0) goto L_0x026a
            if (r13 != r7) goto L_0x0231
            goto L_0x026a
        L_0x0231:
            if (r11 == 0) goto L_0x0245
            int r2 = r8.getModifiers()
            boolean r2 = java.lang.reflect.Modifier.isStatic(r2)
            if (r2 == 0) goto L_0x023e
            goto L_0x0245
        L_0x023e:
            java.lang.String r0 = "jsStaticFunction must be used with static method."
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError(r0)
            throw r0
        L_0x0245:
            org.mozilla.javascript.FunctionObject r2 = new org.mozilla.javascript.FunctionObject
            r2.<init>(r14, r8, r6)
            boolean r4 = r2.isVarArgsConstructor()
            if (r4 != 0) goto L_0x025f
            if (r11 == 0) goto L_0x0254
            r4 = r5
            goto L_0x0255
        L_0x0254:
            r4 = r6
        L_0x0255:
            r7 = 2
            defineProperty(r4, r14, r2, r7)
            if (r1 == 0) goto L_0x0288
            r2.sealObject()
            goto L_0x0288
        L_0x025f:
            java.lang.String r0 = "msg.varargs.fun"
            java.lang.String r1 = r12.getName()
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError1(r0, r1)
            throw r0
        L_0x026a:
            boolean r2 = r6 instanceof org.mozilla.javascript.ScriptableObject
            if (r2 == 0) goto L_0x0298
            java.lang.String r2 = "jsSet_"
            java.lang.reflect.Method r20 = findSetterMethod(r3, r14, r2)
            if (r20 == 0) goto L_0x0278
            r11 = 0
            goto L_0x0279
        L_0x0278:
            r11 = 1
        L_0x0279:
            r21 = r11 | 6
            r16 = r6
            org.mozilla.javascript.ScriptableObject r16 = (org.mozilla.javascript.ScriptableObject) r16
            r18 = 0
            r17 = r14
            r19 = r8
            r16.defineProperty(r17, r18, r19, r20, r21)
        L_0x0288:
            int r15 = r15 + 1
            r11 = r25
            r13 = r27
            r2 = r22
            r14 = r23
            r4 = 0
            r7 = 3
            r8 = 2
            r10 = 1
            goto L_0x0152
        L_0x0298:
            java.lang.Class r0 = r6.getClass()
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "msg.extend.scriptable"
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError2(r1, r0, r14)
            throw r0
        L_0x02a7:
            java.lang.String r0 = "duplicate.defineClass.name"
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError2(r0, r10, r14)
            throw r0
        L_0x02ae:
            if (r9 == 0) goto L_0x02c0
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r2[r3] = r0
            r0 = 1
            r2[r0] = r5
            r0 = 2
            r2[r0] = r6
            r0 = 0
            r9.invoke(r0, r2)
        L_0x02c0:
            if (r1 == 0) goto L_0x02ce
            r5.sealObject()
            boolean r0 = r6 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 == 0) goto L_0x02ce
            org.mozilla.javascript.ScriptableObject r6 = (org.mozilla.javascript.ScriptableObject) r6
            r6.sealObject()
        L_0x02ce:
            return r5
        L_0x02cf:
            java.lang.String r0 = "msg.varargs.ctor"
            java.lang.String r1 = r12.getName()
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError1(r0, r1)
            throw r0
        L_0x02da:
            r14 = r25
            java.lang.String r0 = r25.getName()
            java.lang.String r1 = "msg.zero.arg.ctor"
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError1(r1, r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.buildClassCtor(org.mozilla.javascript.Scriptable, java.lang.Class, boolean, boolean):org.mozilla.javascript.BaseFunction");
    }

    public static ScriptableObject buildDataDescriptor(Scriptable scriptable, Object obj, int i) {
        boolean z;
        boolean z2;
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
        nativeObject.defineProperty(ES6Iterator.VALUE_PROPERTY, obj, 0);
        boolean z3 = true;
        if ((i & 1) == 0) {
            z = true;
        } else {
            z = false;
        }
        nativeObject.defineProperty("writable", (Object) Boolean.valueOf(z), 0);
        if ((i & 2) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        nativeObject.defineProperty("enumerable", (Object) Boolean.valueOf(z2), 0);
        if ((i & 4) != 0) {
            z3 = false;
        }
        nativeObject.defineProperty("configurable", (Object) Boolean.valueOf(z3), 0);
        return nativeObject;
    }

    public static Object callMethod(Scriptable scriptable, String str, Object[] objArr) {
        return callMethod((Context) null, scriptable, str, objArr);
    }

    private void checkNotSealed(Object obj, int i) {
        String str;
        if (isSealed()) {
            if (obj != null) {
                str = obj.toString();
            } else {
                str = Integer.toString(i);
            }
            throw Context.reportRuntimeError1("msg.modify.sealed", str);
        }
    }

    public static void checkValidAttributes(int i) {
        if ((i & -16) != 0) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    private static SlotMapContainer createSlotMap(int i) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext == null || !currentContext.hasFeature(17)) {
            return new SlotMapContainer(i);
        }
        return new ThreadSafeSlotMapContainer(i);
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        defineClass(scriptable, cls, false, false);
    }

    public static void defineConstProperty(Scriptable scriptable, String str) {
        if (scriptable instanceof ConstProperties) {
            ((ConstProperties) scriptable).defineConst(str, scriptable);
        } else {
            defineProperty(scriptable, str, Undefined.instance, 13);
        }
    }

    public static boolean deleteProperty(Scriptable scriptable, String str) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            return true;
        }
        base.delete(str);
        return !base.has(str, scriptable);
    }

    public static Scriptable ensureScriptable(Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    public static ScriptableObject ensureScriptableObject(Object obj) {
        if (obj instanceof ScriptableObject) {
            return (ScriptableObject) obj;
        }
        throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(obj));
    }

    public static SymbolScriptable ensureSymbolScriptable(Object obj) {
        if (obj instanceof SymbolScriptable) {
            return (SymbolScriptable) obj;
        }
        throw ScriptRuntime.typeError1("msg.object.not.symbolscriptable", ScriptRuntime.typeof(obj));
    }

    private static <T extends Scriptable> Class<T> extendsScriptable(Class<?> cls) {
        if (ScriptRuntime.ScriptableClass.isAssignableFrom(cls)) {
            return cls;
        }
        return null;
    }

    private static Member findAnnotatedMember(AccessibleObject[] accessibleObjectArr, Class<? extends Annotation> cls) {
        for (AccessibleObject accessibleObject : accessibleObjectArr) {
            if (accessibleObject.isAnnotationPresent(cls)) {
                return (Member) accessibleObject;
            }
        }
        return null;
    }

    private Slot findAttributeSlot(String str, int i, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(str, i, slotAccess);
        if (slot != null) {
            return slot;
        }
        if (str == null) {
            str = Integer.toString(i);
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", str);
    }

    private static Method findSetterMethod(Method[] methodArr, String str, String str2) {
        StringBuilder sb = new StringBuilder("set");
        sb.append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1));
        String sb2 = sb.toString();
        for (Method method : methodArr) {
            JSSetter jSSetter = (JSSetter) method.getAnnotation(JSSetter.class);
            if (jSSetter != null && (str.equals(jSSetter.value()) || ("".equals(jSSetter.value()) && sb2.equals(method.getName())))) {
                return method;
            }
        }
        String x = y2.x(str2, str);
        for (Method method2 : methodArr) {
            if (x.equals(method2.getName())) {
                return method2;
            }
        }
        return null;
    }

    public static Scriptable getArrayPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Array);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000b, LOOP_START, MTH_ENTER_BLOCK, PHI: r1 
      PHI: (r1v1 org.mozilla.javascript.Scriptable) = (r1v0 org.mozilla.javascript.Scriptable), (r1v3 org.mozilla.javascript.Scriptable) binds: [B:0:0x0000, B:3:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.mozilla.javascript.Scriptable getBase(org.mozilla.javascript.Scriptable r1, java.lang.String r2) {
        /*
        L_0x0000:
            boolean r0 = r1.has((java.lang.String) r2, (org.mozilla.javascript.Scriptable) r1)
            if (r0 == 0) goto L_0x0007
            goto L_0x000d
        L_0x0007:
            org.mozilla.javascript.Scriptable r1 = r1.getPrototype()
            if (r1 != 0) goto L_0x0000
        L_0x000d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.getBase(org.mozilla.javascript.Scriptable, java.lang.String):org.mozilla.javascript.Scriptable");
    }

    public static Scriptable getClassPrototype(Scriptable scriptable, String str) {
        Object obj;
        Object property = getProperty(getTopLevelScope(scriptable), str);
        if (property instanceof BaseFunction) {
            obj = ((BaseFunction) property).getPrototypeProperty();
        } else {
            if (property instanceof Scriptable) {
                Scriptable scriptable2 = (Scriptable) property;
                obj = scriptable2.get("prototype", scriptable2);
            }
            return null;
        }
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        return null;
    }

    public static Scriptable getFunctionPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Function);
    }

    public static Scriptable getGeneratorFunctionPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.GeneratorFunction);
    }

    public static Scriptable getObjectPrototype(Scriptable scriptable) {
        return TopLevel.getBuiltinPrototype(getTopLevelScope(scriptable), TopLevel.Builtins.Object);
    }

    public static Object getProperty(Scriptable scriptable, String str) {
        Object obj;
        Scriptable scriptable2 = scriptable;
        do {
            obj = scriptable2.get(str, scriptable);
            if (obj != Scriptable.NOT_FOUND || (scriptable2 = scriptable2.getPrototype()) == null) {
                return obj;
            }
            obj = scriptable2.get(str, scriptable);
            break;
        } while ((scriptable2 = scriptable2.getPrototype()) == null);
        return obj;
    }

    public static Object[] getPropertyIds(Scriptable scriptable) {
        if (scriptable == null) {
            return ScriptRuntime.emptyArgs;
        }
        Object[] ids = scriptable.getIds();
        ObjToIntMap objToIntMap = null;
        while (true) {
            scriptable = scriptable.getPrototype();
            if (scriptable == null) {
                break;
            }
            Object[] ids2 = scriptable.getIds();
            if (ids2.length != 0) {
                if (objToIntMap == null) {
                    if (ids.length == 0) {
                        ids = ids2;
                    } else {
                        objToIntMap = new ObjToIntMap(ids.length + ids2.length);
                        for (int i = 0; i != ids.length; i++) {
                            objToIntMap.intern(ids[i]);
                        }
                        ids = null;
                    }
                }
                for (int i2 = 0; i2 != ids2.length; i2++) {
                    objToIntMap.intern(ids2[i2]);
                }
            }
        }
        if (objToIntMap != null) {
            return objToIntMap.getKeys();
        }
        return ids;
    }

    private static String getPropertyName(String str, String str2, Annotation annotation) {
        String str3;
        if (str2 != null) {
            return str.substring(str2.length());
        }
        if (annotation instanceof JSGetter) {
            str3 = ((JSGetter) annotation).value();
            if ((str3 == null || str3.length() == 0) && str.length() > 3 && str.startsWith("get")) {
                str3 = str.substring(3);
                if (Character.isUpperCase(str3.charAt(0))) {
                    if (str3.length() == 1) {
                        str3 = str3.toLowerCase();
                    } else if (!Character.isUpperCase(str3.charAt(1))) {
                        str3 = Character.toLowerCase(str3.charAt(0)) + str3.substring(1);
                    }
                }
            }
        } else {
            str3 = annotation instanceof JSFunction ? ((JSFunction) annotation).value() : annotation instanceof JSStaticFunction ? ((JSStaticFunction) annotation).value() : null;
        }
        if (str3 == null || str3.length() == 0) {
            return str;
        }
        return str3;
    }

    public static Scriptable getTopLevelScope(Scriptable scriptable) {
        while (true) {
            Scriptable parentScope = scriptable.getParentScope();
            if (parentScope == null) {
                return scriptable;
            }
            scriptable = parentScope;
        }
    }

    public static Object getTopScopeValue(Scriptable scriptable, Object obj) {
        Object associatedValue;
        Scriptable topLevelScope = getTopLevelScope(scriptable);
        do {
            if ((topLevelScope instanceof ScriptableObject) && (associatedValue = ((ScriptableObject) topLevelScope).getAssociatedValue(obj)) != null) {
                return associatedValue;
            }
            topLevelScope = topLevelScope.getPrototype();
        } while (topLevelScope != null);
        return null;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, int i, Class<T> cls) {
        Object property = getProperty(scriptable, i);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public static boolean hasProperty(Scriptable scriptable, String str) {
        return getBase(scriptable, str) != null;
    }

    public static boolean isFalse(Object obj) {
        return !isTrue(obj);
    }

    public static boolean isTrue(Object obj) {
        return obj != Scriptable.NOT_FOUND && ScriptRuntime.toBoolean(obj);
    }

    private boolean putConstImpl(String str, int i, Scriptable scriptable, Object obj, int i2) {
        Slot slot;
        if (this.isExtensible || !Context.getContext().isStrictMode()) {
            if (this != scriptable) {
                slot = this.slotMap.query(str, i);
                if (slot == null) {
                    return false;
                }
            } else if (!isExtensible()) {
                slot = this.slotMap.query(str, i);
                if (slot == null) {
                    return true;
                }
            } else {
                checkNotSealed(str, i);
                Slot slot2 = this.slotMap.get(str, i, SlotAccess.MODIFY_CONST);
                int attributes = slot2.getAttributes();
                if ((attributes & 1) != 0) {
                    if ((attributes & 8) != 0) {
                        slot2.value = obj;
                        if (i2 != 8) {
                            slot2.setAttributes(attributes & -9);
                        }
                    }
                    return true;
                }
                throw Context.reportRuntimeError1("msg.var.redecl", str);
            }
            return slot.setValue(obj, this, scriptable);
        }
        throw ScriptRuntime.typeError0("msg.not.extensible");
    }

    public static void putConstProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        if (base instanceof ConstProperties) {
            ((ConstProperties) base).putConst(str, scriptable, obj);
        }
    }

    private boolean putImpl(Object obj, int i, Scriptable scriptable, Object obj2) {
        Slot slot;
        if (this != scriptable) {
            slot = this.slotMap.query(obj, i);
            if (!this.isExtensible && ((slot == null || (!(slot instanceof GetterSlot) && (slot.getAttributes() & 1) != 0)) && Context.getContext().isStrictMode())) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            } else if (slot == null) {
                return false;
            }
        } else if (!this.isExtensible) {
            slot = this.slotMap.query(obj, i);
            if ((slot == null || (!(slot instanceof GetterSlot) && (slot.getAttributes() & 1) != 0)) && Context.getContext().isStrictMode()) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            } else if (slot == null) {
                return true;
            }
        } else {
            if (this.isSealed) {
                checkNotSealed(obj, i);
            }
            slot = this.slotMap.get(obj, i, SlotAccess.MODIFY);
        }
        return slot.setValue(obj2, this, scriptable);
    }

    public static void putProperty(Scriptable scriptable, String str, Object obj) {
        Scriptable base = getBase(scriptable, str);
        if (base == null) {
            base = scriptable;
        }
        base.put(str, scriptable, obj);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        this.slotMap = createSlotMap(readInt);
        for (int i = 0; i < readInt; i++) {
            this.slotMap.addSlot((Slot) objectInputStream.readObject());
        }
    }

    public static void redefineProperty(Scriptable scriptable, String str, boolean z) {
        Scriptable base = getBase(scriptable, str);
        if (base != null) {
            if ((base instanceof ConstProperties) && ((ConstProperties) base).isConst(str)) {
                throw ScriptRuntime.typeError1("msg.const.redecl", str);
            } else if (z) {
                throw ScriptRuntime.typeError1("msg.var.redecl", str);
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        long readLock = this.slotMap.readLock();
        try {
            int dirtySize = this.slotMap.dirtySize();
            if (dirtySize == 0) {
                objectOutputStream.writeInt(0);
            } else {
                objectOutputStream.writeInt(dirtySize);
                Iterator<Slot> it = this.slotMap.iterator();
                while (it.hasNext()) {
                    objectOutputStream.writeObject(it.next());
                }
            }
        } finally {
            this.slotMap.unlockRead(readLock);
        }
    }

    public void addLazilyInitializedValue(String str, int i, LazilyLoadedCtor lazilyLoadedCtor, int i2) {
        if (str == null || i == 0) {
            checkNotSealed(str, i);
            GetterSlot getterSlot = (GetterSlot) this.slotMap.get(str, i, SlotAccess.MODIFY_GETTER_SETTER);
            getterSlot.setAttributes(i2);
            getterSlot.getter = null;
            getterSlot.setter = null;
            getterSlot.value = lazilyLoadedCtor;
            return;
        }
        throw new IllegalArgumentException(str);
    }

    public int applyDescriptorToAttributeBitset(int i, ScriptableObject scriptableObject) {
        Object property = getProperty((Scriptable) scriptableObject, "enumerable");
        Object obj = Scriptable.NOT_FOUND;
        if (property != obj) {
            if (ScriptRuntime.toBoolean(property)) {
                i &= -3;
            } else {
                i |= 2;
            }
        }
        Object property2 = getProperty((Scriptable) scriptableObject, "writable");
        if (property2 != obj) {
            if (ScriptRuntime.toBoolean(property2)) {
                i &= -2;
            } else {
                i |= 1;
            }
        }
        Object property3 = getProperty((Scriptable) scriptableObject, "configurable");
        if (property3 == obj) {
            return i;
        }
        if (ScriptRuntime.toBoolean(property3)) {
            return i & -5;
        }
        return i | 4;
    }

    public final synchronized Object associateValue(Object obj, Object obj2) {
        Map map;
        if (obj2 != null) {
            map = this.associatedValues;
            if (map == null) {
                map = new HashMap();
                this.associatedValues = map;
            }
        } else {
            throw new IllegalArgumentException();
        }
        return Kit.initHash(map, obj, obj2);
    }

    public boolean avoidObjectDetection() {
        return false;
    }

    public void checkPropertyChange(Object obj, ScriptableObject scriptableObject, ScriptableObject scriptableObject2) {
        if (scriptableObject == null) {
            if (!isExtensible()) {
                throw ScriptRuntime.typeError0("msg.not.extensible");
            }
        } else if (!isFalse(scriptableObject.get("configurable", (Scriptable) scriptableObject))) {
        } else {
            if (isTrue(getProperty((Scriptable) scriptableObject2, "configurable"))) {
                throw ScriptRuntime.typeError1("msg.change.configurable.false.to.true", obj);
            } else if (isTrue(scriptableObject.get("enumerable", (Scriptable) scriptableObject)) == isTrue(getProperty((Scriptable) scriptableObject2, "enumerable"))) {
                boolean isDataDescriptor = isDataDescriptor(scriptableObject2);
                boolean isAccessorDescriptor = isAccessorDescriptor(scriptableObject2);
                if (!isDataDescriptor && !isAccessorDescriptor) {
                    return;
                }
                if (!isDataDescriptor || !isDataDescriptor(scriptableObject)) {
                    if (!isAccessorDescriptor || !isAccessorDescriptor(scriptableObject)) {
                        if (isDataDescriptor(scriptableObject)) {
                            throw ScriptRuntime.typeError1("msg.change.property.data.to.accessor.with.configurable.false", obj);
                        }
                        throw ScriptRuntime.typeError1("msg.change.property.accessor.to.data.with.configurable.false", obj);
                    } else if (!sameValue(getProperty((Scriptable) scriptableObject2, "set"), scriptableObject.get("set", (Scriptable) scriptableObject))) {
                        throw ScriptRuntime.typeError1("msg.change.setter.with.configurable.false", obj);
                    } else if (!sameValue(getProperty((Scriptable) scriptableObject2, "get"), scriptableObject.get("get", (Scriptable) scriptableObject))) {
                        throw ScriptRuntime.typeError1("msg.change.getter.with.configurable.false", obj);
                    }
                } else if (!isFalse(scriptableObject.get("writable", (Scriptable) scriptableObject))) {
                } else {
                    if (isTrue(getProperty((Scriptable) scriptableObject2, "writable"))) {
                        throw ScriptRuntime.typeError1("msg.change.writable.false.to.true.with.configurable.false", obj);
                    } else if (!sameValue(getProperty((Scriptable) scriptableObject2, ES6Iterator.VALUE_PROPERTY), scriptableObject.get(ES6Iterator.VALUE_PROPERTY, (Scriptable) scriptableObject))) {
                        throw ScriptRuntime.typeError1("msg.change.value.with.writable.false", obj);
                    }
                }
            } else {
                throw ScriptRuntime.typeError1("msg.change.enumerable.with.configurable.false", obj);
            }
        }
    }

    public void checkPropertyDefinition(ScriptableObject scriptableObject) {
        Object property = getProperty((Scriptable) scriptableObject, "get");
        Object obj = Scriptable.NOT_FOUND;
        if (property == obj || property == Undefined.instance || (property instanceof Callable)) {
            Object property2 = getProperty((Scriptable) scriptableObject, "set");
            if (property2 != obj && property2 != Undefined.instance && !(property2 instanceof Callable)) {
                throw ScriptRuntime.notFunctionError(property2);
            } else if (isDataDescriptor(scriptableObject) && isAccessorDescriptor(scriptableObject)) {
                throw ScriptRuntime.typeError0("msg.both.data.and.accessor.desc");
            }
        } else {
            throw ScriptRuntime.notFunctionError(property);
        }
    }

    public void defineConst(String str, Scriptable scriptable) {
        if (!putConstImpl(str, 0, scriptable, Undefined.instance, 8)) {
            if (scriptable == this) {
                throw Kit.codeBug();
            } else if (scriptable instanceof ConstProperties) {
                ((ConstProperties) scriptable).defineConst(str, scriptable);
            }
        }
    }

    public void defineFunctionProperties(String[] strArr, Class<?> cls, int i) {
        Method[] methodList = FunctionObject.getMethodList(cls);
        int i2 = 0;
        while (i2 < strArr.length) {
            String str = strArr[i2];
            Method findSingleMethod = FunctionObject.findSingleMethod(methodList, str);
            if (findSingleMethod != null) {
                defineProperty(str, (Object) new FunctionObject(str, findSingleMethod, this), i);
                i2++;
            } else {
                throw Context.reportRuntimeError2("msg.method.not.found", str, cls.getName());
            }
        }
    }

    public void defineOwnProperties(Context context, ScriptableObject scriptableObject) {
        Object[] ids = scriptableObject.getIds(false, true);
        ScriptableObject[] scriptableObjectArr = new ScriptableObject[ids.length];
        int length = ids.length;
        for (int i = 0; i < length; i++) {
            ScriptableObject ensureScriptableObject = ensureScriptableObject(ScriptRuntime.getObjectElem((Scriptable) scriptableObject, ids[i], context));
            checkPropertyDefinition(ensureScriptableObject);
            scriptableObjectArr[i] = ensureScriptableObject;
        }
        int length2 = ids.length;
        for (int i2 = 0; i2 < length2; i2++) {
            defineOwnProperty(context, ids[i2], scriptableObjectArr[i2]);
        }
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject) {
        checkPropertyDefinition(scriptableObject);
        defineOwnProperty(context, obj, scriptableObject, true);
    }

    public void defineProperty(String str, Object obj, int i) {
        checkNotSealed(str, 0);
        put(str, (Scriptable) this, obj);
        setAttributes(str, i);
    }

    public void delete(String str) {
        checkNotSealed(str, 0);
        this.slotMap.remove(str, 0);
    }

    public Object equivalentValues(Object obj) {
        return this == obj ? Boolean.TRUE : Scriptable.NOT_FOUND;
    }

    public Object get(String str, Scriptable scriptable) {
        Slot query = this.slotMap.query(str, 0);
        if (query == null) {
            return Scriptable.NOT_FOUND;
        }
        return query.getValue(scriptable);
    }

    public Object[] getAllIds() {
        return getIds(true, false);
    }

    public final Object getAssociatedValue(Object obj) {
        Map<Object, Object> map = this.associatedValues;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    @Deprecated
    public final int getAttributes(String str, Scriptable scriptable) {
        return getAttributes(str);
    }

    public abstract String getClassName();

    public Object getDefaultValue(Class<?> cls) {
        return getDefaultValue(this, cls);
    }

    public ExternalArrayData getExternalArrayData() {
        return this.externalData;
    }

    public Object getExternalArrayLength() {
        ExternalArrayData externalArrayData = this.externalData;
        return Integer.valueOf(externalArrayData == null ? 0 : externalArrayData.getArrayLength());
    }

    public Object getGetterOrSetter(String str, int i, boolean z) {
        Object obj;
        if (str == null || i == 0) {
            Slot query = this.slotMap.query(str, i);
            if (query == null) {
                return null;
            }
            if (!(query instanceof GetterSlot)) {
                return Undefined.instance;
            }
            GetterSlot getterSlot = (GetterSlot) query;
            if (z) {
                obj = getterSlot.setter;
            } else {
                obj = getterSlot.getter;
            }
            if (obj != null) {
                return obj;
            }
            return Undefined.instance;
        }
        throw new IllegalArgumentException(str);
    }

    public Object[] getIds() {
        return getIds(false, false);
    }

    public ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        if (slot == null) {
            return null;
        }
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        return slot.getPropertyDescriptor(context, parentScope);
    }

    public Scriptable getParentScope() {
        return this.parentScopeObject;
    }

    public Scriptable getPrototype() {
        return this.prototypeObject;
    }

    public Slot getSlot(Context context, Object obj, SlotAccess slotAccess) {
        if (obj instanceof Symbol) {
            return this.slotMap.get(obj, 0, slotAccess);
        }
        ScriptRuntime.StringIdOrIndex stringIdOrIndex = ScriptRuntime.toStringIdOrIndex(context, obj);
        String str = stringIdOrIndex.stringId;
        if (str == null) {
            return this.slotMap.get((Object) null, stringIdOrIndex.index, slotAccess);
        }
        return this.slotMap.get(str, 0, slotAccess);
    }

    public String getTypeOf() {
        return avoidObjectDetection() ? "undefined" : "object";
    }

    public boolean has(String str, Scriptable scriptable) {
        return this.slotMap.query(str, 0) != null;
    }

    public boolean hasInstance(Scriptable scriptable) {
        return ScriptRuntime.jsDelegatesTo(scriptable, this);
    }

    public boolean isAccessorDescriptor(ScriptableObject scriptableObject) {
        if (hasProperty((Scriptable) scriptableObject, "get") || hasProperty((Scriptable) scriptableObject, "set")) {
            return true;
        }
        return false;
    }

    public boolean isConst(String str) {
        Slot query = this.slotMap.query(str, 0);
        if (query != null && (query.getAttributes() & 5) == 5) {
            return true;
        }
        return false;
    }

    public boolean isDataDescriptor(ScriptableObject scriptableObject) {
        if (hasProperty((Scriptable) scriptableObject, ES6Iterator.VALUE_PROPERTY) || hasProperty((Scriptable) scriptableObject, "writable")) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.slotMap.isEmpty();
    }

    public boolean isExtensible() {
        return this.isExtensible;
    }

    public boolean isGenericDescriptor(ScriptableObject scriptableObject) {
        return !isDataDescriptor(scriptableObject) && !isAccessorDescriptor(scriptableObject);
    }

    public boolean isGetterOrSetter(String str, int i, boolean z) {
        Slot query = this.slotMap.query(str, i);
        if (!(query instanceof GetterSlot)) {
            return false;
        }
        if (z && ((GetterSlot) query).setter != null) {
            return true;
        }
        if (z || ((GetterSlot) query).getter == null) {
            return false;
        }
        return true;
    }

    public final boolean isSealed() {
        return this.isSealed;
    }

    public void preventExtensions() {
        this.isExtensible = false;
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (!putImpl(str, 0, scriptable, obj)) {
            if (scriptable != this) {
                scriptable.put(str, scriptable, obj);
                return;
            }
            throw Kit.codeBug();
        }
    }

    public void putConst(String str, Scriptable scriptable, Object obj) {
        if (!putConstImpl(str, 0, scriptable, obj, 1)) {
            if (scriptable == this) {
                throw Kit.codeBug();
            } else if (scriptable instanceof ConstProperties) {
                ((ConstProperties) scriptable).putConst(str, scriptable, obj);
            } else {
                scriptable.put(str, scriptable, obj);
            }
        }
    }

    public boolean sameValue(Object obj, Object obj2) {
        Object obj3 = Scriptable.NOT_FOUND;
        if (obj == obj3) {
            return true;
        }
        if (obj2 == obj3) {
            obj2 = Undefined.instance;
        }
        if ((obj2 instanceof Number) && (obj instanceof Number)) {
            double doubleValue = ((Number) obj2).doubleValue();
            double doubleValue2 = ((Number) obj).doubleValue();
            if (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2)) {
                return true;
            }
            if (doubleValue == 0.0d && Double.doubleToLongBits(doubleValue) != Double.doubleToLongBits(doubleValue2)) {
                return false;
            }
        }
        return ScriptRuntime.shallowEq(obj2, obj);
    }

    public void sealObject() {
        Slot next;
        LazilyLoadedCtor lazilyLoadedCtor;
        if (!this.isSealed) {
            long readLock = this.slotMap.readLock();
            try {
                Iterator<Slot> it = this.slotMap.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    Object obj = next.value;
                    if (obj instanceof LazilyLoadedCtor) {
                        lazilyLoadedCtor = (LazilyLoadedCtor) obj;
                        lazilyLoadedCtor.init();
                        next.value = lazilyLoadedCtor.getValue();
                    }
                }
                this.isSealed = true;
                this.slotMap.unlockRead(readLock);
            } catch (Throwable th) {
                this.slotMap.unlockRead(readLock);
                throw th;
            }
        }
    }

    @Deprecated
    public final void setAttributes(String str, Scriptable scriptable, int i) {
        setAttributes(str, i);
    }

    public void setExternalArrayData(ExternalArrayData externalArrayData) {
        this.externalData = externalArrayData;
        if (externalArrayData == null) {
            delete("length");
            return;
        }
        defineProperty("length", (Object) null, GET_ARRAY_LENGTH, (Method) null, 3);
    }

    public void setGetterOrSetter(String str, int i, Callable callable, boolean z) {
        setGetterOrSetter(str, i, callable, z, false);
    }

    public void setParentScope(Scriptable scriptable) {
        this.parentScopeObject = scriptable;
    }

    public void setPrototype(Scriptable scriptable) {
        if (isExtensible() || Context.getContext().getLanguageVersion() < 180) {
            this.prototypeObject = scriptable;
            return;
        }
        throw ScriptRuntime.typeError0("msg.not.extensible");
    }

    public int size() {
        return this.slotMap.size();
    }

    public static Object callMethod(Context context, Scriptable scriptable, String str, Object[] objArr) {
        Object property = getProperty(scriptable, str);
        if (property instanceof Function) {
            Function function = (Function) property;
            Scriptable topLevelScope = getTopLevelScope(scriptable);
            if (context != null) {
                return function.call(context, topLevelScope, scriptable, objArr);
            }
            return Context.call((ContextFactory) null, function, topLevelScope, scriptable, objArr);
        }
        throw ScriptRuntime.notFunctionError(scriptable, str);
    }

    public static <T extends Scriptable> void defineClass(Scriptable scriptable, Class<T> cls, boolean z) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        defineClass(scriptable, cls, z, false);
    }

    public static Object getDefaultValue(Scriptable scriptable, Class<?> cls) {
        String str;
        Context context = null;
        for (int i = 0; i < 2; i++) {
            boolean z = true;
            if (cls != ScriptRuntime.StringClass ? i != 1 : i != 0) {
                z = false;
            }
            Object property = getProperty(scriptable, z ? "toString" : "valueOf");
            if (property instanceof Function) {
                Function function = (Function) property;
                if (context == null) {
                    context = Context.getContext();
                }
                Object call = function.call(context, function.getParentScope(), scriptable, ScriptRuntime.emptyArgs);
                if (call == null) {
                    continue;
                } else if (!(call instanceof Scriptable)) {
                    return call;
                } else {
                    if (!(cls == ScriptRuntime.ScriptableClass || cls == ScriptRuntime.FunctionClass)) {
                        if (z && (call instanceof Wrapper)) {
                            call = ((Wrapper) call).unwrap();
                            if (call instanceof String) {
                            }
                        }
                    }
                    return call;
                }
            }
        }
        if (cls == null) {
            str = "undefined";
        } else {
            str = cls.getName();
        }
        throw ScriptRuntime.typeError1("msg.default.value", str);
    }

    public static boolean hasProperty(Scriptable scriptable, int i) {
        return getBase(scriptable, i) != null;
    }

    private void setGetterOrSetter(String str, int i, Callable callable, boolean z, boolean z2) {
        GetterSlot getterSlot;
        if (str == null || i == 0) {
            if (!z2) {
                checkNotSealed(str, i);
            }
            if (isExtensible()) {
                getterSlot = (GetterSlot) this.slotMap.get(str, i, SlotAccess.MODIFY_GETTER_SETTER);
            } else {
                Slot query = this.slotMap.query(str, i);
                if (query instanceof GetterSlot) {
                    getterSlot = (GetterSlot) query;
                } else {
                    return;
                }
            }
            if (z2 || (getterSlot.getAttributes() & 1) == 0) {
                if (z) {
                    getterSlot.setter = callable;
                } else {
                    getterSlot.getter = callable;
                }
                getterSlot.value = Undefined.instance;
                return;
            }
            throw Context.reportRuntimeError1("msg.modify.readonly", str);
        }
        throw new IllegalArgumentException(str);
    }

    @Deprecated
    public final int getAttributes(int i, Scriptable scriptable) {
        return getAttributes(i);
    }

    /* JADX INFO: finally extract failed */
    public Object[] getIds(boolean z, boolean z2) {
        Object[] objArr;
        ExternalArrayData externalArrayData = this.externalData;
        int arrayLength = externalArrayData == null ? 0 : externalArrayData.getArrayLength();
        if (arrayLength == 0) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            objArr = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                objArr[i] = Integer.valueOf(i);
            }
        }
        if (this.slotMap.isEmpty()) {
            return objArr;
        }
        long readLock = this.slotMap.readLock();
        try {
            Iterator<Slot> it = this.slotMap.iterator();
            int i2 = arrayLength;
            while (it.hasNext()) {
                Slot next = it.next();
                if ((z || (next.getAttributes() & 2) == 0) && (z2 || !(next.name instanceof Symbol))) {
                    if (i2 == arrayLength) {
                        Object[] objArr2 = new Object[(this.slotMap.dirtySize() + arrayLength)];
                        if (objArr != null) {
                            System.arraycopy(objArr, 0, objArr2, 0, arrayLength);
                        }
                        objArr = objArr2;
                    }
                    int i3 = i2 + 1;
                    Object obj = next.name;
                    if (obj == null) {
                        obj = Integer.valueOf(next.indexOrHash);
                    }
                    objArr[i2] = obj;
                    i2 = i3;
                }
            }
            this.slotMap.unlockRead(readLock);
            if (i2 != objArr.length + arrayLength) {
                Object[] objArr3 = new Object[i2];
                System.arraycopy(objArr, 0, objArr3, 0, i2);
                objArr = objArr3;
            }
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null && currentContext.hasFeature(16)) {
                Arrays.sort(objArr, KEY_COMPARATOR);
            }
            return objArr;
        } catch (Throwable th) {
            this.slotMap.unlockRead(readLock);
            throw th;
        }
    }

    public boolean has(int i, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i < externalArrayData.getArrayLength()) {
                return true;
            }
            return false;
        } else if (this.slotMap.query((Object) null, i) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void setAttributes(int i, Scriptable scriptable, int i2) {
        setAttributes(i, i2);
    }

    public static <T extends Scriptable> String defineClass(Scriptable scriptable, Class<T> cls, boolean z, boolean z2) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        BaseFunction buildClassCtor = buildClassCtor(scriptable, cls, z, z2);
        if (buildClassCtor == null) {
            return null;
        }
        String className = buildClassCtor.getClassPrototype().getClassName();
        defineProperty(scriptable, className, buildClassCtor, 2);
        return className;
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000b, LOOP_START, MTH_ENTER_BLOCK, PHI: r1 
      PHI: (r1v1 org.mozilla.javascript.Scriptable) = (r1v0 org.mozilla.javascript.Scriptable), (r1v3 org.mozilla.javascript.Scriptable) binds: [B:0:0x0000, B:3:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.mozilla.javascript.Scriptable getBase(org.mozilla.javascript.Scriptable r1, int r2) {
        /*
        L_0x0000:
            boolean r0 = r1.has((int) r2, (org.mozilla.javascript.Scriptable) r1)
            if (r0 == 0) goto L_0x0007
            goto L_0x000d
        L_0x0007:
            org.mozilla.javascript.Scriptable r1 = r1.getPrototype()
            if (r1 != 0) goto L_0x0000
        L_0x000d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.getBase(org.mozilla.javascript.Scriptable, int):org.mozilla.javascript.Scriptable");
    }

    public static boolean hasProperty(Scriptable scriptable, Symbol symbol) {
        return getBase(scriptable, symbol) != null;
    }

    public static void putProperty(Scriptable scriptable, Symbol symbol, Object obj) {
        Scriptable base = getBase(scriptable, symbol);
        if (base == null) {
            base = scriptable;
        }
        ensureSymbolScriptable(base).put(symbol, scriptable, obj);
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject, boolean z) {
        int i;
        ScriptableObject scriptableObject2;
        Slot slot = getSlot(context, obj, SlotAccess.QUERY);
        boolean z2 = slot == null;
        if (z) {
            if (slot == null) {
                scriptableObject2 = null;
            } else {
                scriptableObject2 = slot.getPropertyDescriptor(context, this);
            }
            checkPropertyChange(obj, scriptableObject2, scriptableObject);
        }
        boolean isAccessorDescriptor = isAccessorDescriptor(scriptableObject);
        if (slot == null) {
            slot = getSlot(context, obj, isAccessorDescriptor ? SlotAccess.MODIFY_GETTER_SETTER : SlotAccess.MODIFY);
            i = applyDescriptorToAttributeBitset(7, scriptableObject);
        } else {
            i = applyDescriptorToAttributeBitset(slot.getAttributes(), scriptableObject);
        }
        if (isAccessorDescriptor) {
            if (!(slot instanceof GetterSlot)) {
                slot = getSlot(context, obj, SlotAccess.MODIFY_GETTER_SETTER);
            }
            GetterSlot getterSlot = (GetterSlot) slot;
            Object property = getProperty((Scriptable) scriptableObject, "get");
            Object obj2 = Scriptable.NOT_FOUND;
            if (property != obj2) {
                getterSlot.getter = property;
            }
            Object property2 = getProperty((Scriptable) scriptableObject, "set");
            if (property2 != obj2) {
                getterSlot.setter = property2;
            }
            getterSlot.value = Undefined.instance;
            getterSlot.setAttributes(i);
            return;
        }
        if ((slot instanceof GetterSlot) && isDataDescriptor(scriptableObject)) {
            slot = getSlot(context, obj, SlotAccess.CONVERT_ACCESSOR_TO_DATA);
        }
        Object property3 = getProperty((Scriptable) scriptableObject, ES6Iterator.VALUE_PROPERTY);
        if (property3 != Scriptable.NOT_FOUND) {
            slot.value = property3;
        } else if (z2) {
            slot.value = Undefined.instance;
        }
        slot.setAttributes(i);
    }

    public void delete(int i) {
        checkNotSealed((Object) null, i);
        this.slotMap.remove((Object) null, i);
    }

    public int getAttributes(String str) {
        return findAttributeSlot(str, 0, SlotAccess.QUERY).getAttributes();
    }

    public void setAttributes(String str, int i) {
        checkNotSealed(str, 0);
        findAttributeSlot(str, 0, SlotAccess.MODIFY).setAttributes(i);
    }

    public static boolean deleteProperty(Scriptable scriptable, int i) {
        Scriptable base = getBase(scriptable, i);
        if (base == null) {
            return true;
        }
        base.delete(i);
        return !base.has(i, scriptable);
    }

    private Slot findAttributeSlot(Symbol symbol, SlotAccess slotAccess) {
        Slot slot = this.slotMap.get(symbol, 0, slotAccess);
        if (slot != null) {
            return slot;
        }
        throw Context.reportRuntimeError1("msg.prop.not.found", symbol);
    }

    public static Object getProperty(Scriptable scriptable, Symbol symbol) {
        Object obj;
        Scriptable scriptable2 = scriptable;
        do {
            obj = ensureSymbolScriptable(scriptable2).get(symbol, scriptable);
            if (obj != Scriptable.NOT_FOUND || (scriptable2 = scriptable2.getPrototype()) == null) {
                return obj;
            }
            obj = ensureSymbolScriptable(scriptable2).get(symbol, scriptable);
            break;
        } while ((scriptable2 = scriptable2.getPrototype()) == null);
        return obj;
    }

    public static <T> T getTypedProperty(Scriptable scriptable, String str, Class<T> cls) {
        Object property = getProperty(scriptable, str);
        if (property == Scriptable.NOT_FOUND) {
            property = null;
        }
        return cls.cast(Context.jsToJava(property, cls));
    }

    public void defineProperty(Symbol symbol, Object obj, int i) {
        checkNotSealed(symbol, 0);
        put(symbol, (Scriptable) this, obj);
        setAttributes(symbol, i);
    }

    public Object get(int i, Scriptable scriptable) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData == null) {
            Slot query = this.slotMap.query((Object) null, i);
            if (query == null) {
                return Scriptable.NOT_FOUND;
            }
            return query.getValue(scriptable);
        } else if (i < externalArrayData.getArrayLength()) {
            return this.externalData.getArrayElement(i);
        } else {
            return Scriptable.NOT_FOUND;
        }
    }

    public int getAttributes(int i) {
        return findAttributeSlot((String) null, i, SlotAccess.QUERY).getAttributes();
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        ExternalArrayData externalArrayData = this.externalData;
        if (externalArrayData != null) {
            if (i < externalArrayData.getArrayLength()) {
                this.externalData.setArrayElement(i, obj);
                return;
            }
            throw new JavaScriptException(ScriptRuntime.newNativeError(Context.getCurrentContext(), this, TopLevel.NativeErrors.RangeError, new Object[]{"External array index out of bounds "}), (String) null, 0);
        } else if (!putImpl((Object) null, i, scriptable, obj)) {
            if (scriptable != this) {
                scriptable.put(i, scriptable, obj);
                return;
            }
            throw Kit.codeBug();
        }
    }

    public ScriptableObject(Scriptable scriptable, Scriptable scriptable2) {
        this.isExtensible = true;
        this.isSealed = false;
        if (scriptable != null) {
            this.parentScopeObject = scriptable;
            this.prototypeObject = scriptable2;
            this.slotMap = createSlotMap(0);
            return;
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000f, LOOP_START, MTH_ENTER_BLOCK, PHI: r1 
      PHI: (r1v1 org.mozilla.javascript.Scriptable) = (r1v0 org.mozilla.javascript.Scriptable), (r1v3 org.mozilla.javascript.Scriptable) binds: [B:0:0x0000, B:3:0x000f] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.mozilla.javascript.Scriptable getBase(org.mozilla.javascript.Scriptable r1, org.mozilla.javascript.Symbol r2) {
        /*
        L_0x0000:
            org.mozilla.javascript.SymbolScriptable r0 = ensureSymbolScriptable(r1)
            boolean r0 = r0.has(r2, r1)
            if (r0 == 0) goto L_0x000b
            goto L_0x0011
        L_0x000b:
            org.mozilla.javascript.Scriptable r1 = r1.getPrototype()
            if (r1 != 0) goto L_0x0000
        L_0x0011:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.getBase(org.mozilla.javascript.Scriptable, org.mozilla.javascript.Symbol):org.mozilla.javascript.Scriptable");
    }

    public static void putProperty(Scriptable scriptable, int i, Object obj) {
        Scriptable base = getBase(scriptable, i);
        if (base == null) {
            base = scriptable;
        }
        base.put(i, scriptable, obj);
    }

    public void delete(Symbol symbol) {
        checkNotSealed(symbol, 0);
        this.slotMap.remove(symbol, 0);
    }

    public int getAttributes(Symbol symbol) {
        return findAttributeSlot(symbol, SlotAccess.QUERY).getAttributes();
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        return this.slotMap.query(symbol, 0) != null;
    }

    public void setAttributes(int i, int i2) {
        checkNotSealed((Object) null, i);
        findAttributeSlot((String) null, i, SlotAccess.MODIFY).setAttributes(i2);
    }

    public static void defineProperty(Scriptable scriptable, String str, Object obj, int i) {
        if (!(scriptable instanceof ScriptableObject)) {
            scriptable.put(str, scriptable, obj);
        } else {
            ((ScriptableObject) scriptable).defineProperty(str, obj, i);
        }
    }

    public static Object getProperty(Scriptable scriptable, int i) {
        Object obj;
        Scriptable scriptable2 = scriptable;
        do {
            obj = scriptable2.get(i, scriptable);
            if (obj != Scriptable.NOT_FOUND || (scriptable2 = scriptable2.getPrototype()) == null) {
                return obj;
            }
            obj = scriptable2.get(i, scriptable);
            break;
        } while ((scriptable2 = scriptable2.getPrototype()) == null);
        return obj;
    }

    public void setAttributes(Symbol symbol, int i) {
        checkNotSealed(symbol, 0);
        findAttributeSlot(symbol, SlotAccess.MODIFY).setAttributes(i);
    }

    public void defineProperty(String str, Class<?> cls, int i) {
        int length = str.length();
        if (length != 0) {
            char[] cArr = new char[(length + 3)];
            str.getChars(0, length, cArr, 3);
            cArr[3] = Character.toUpperCase(cArr[3]);
            cArr[0] = 'g';
            cArr[1] = 'e';
            cArr[2] = 't';
            String str2 = new String(cArr);
            cArr[0] = 's';
            String str3 = new String(cArr);
            Method[] methodList = FunctionObject.getMethodList(cls);
            Method findSingleMethod = FunctionObject.findSingleMethod(methodList, str2);
            Method findSingleMethod2 = FunctionObject.findSingleMethod(methodList, str3);
            if (findSingleMethod2 == null) {
                i |= 1;
            }
            int i2 = i;
            if (findSingleMethod2 == null) {
                findSingleMethod2 = null;
            }
            defineProperty(str, (Object) null, findSingleMethod, findSingleMethod2, i2);
            return;
        }
        throw new IllegalArgumentException();
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        Slot query = this.slotMap.query(symbol, 0);
        if (query == null) {
            return Scriptable.NOT_FOUND;
        }
        return query.getValue(scriptable);
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        if (!putImpl(symbol, 0, scriptable, obj)) {
            if (scriptable != this) {
                ensureSymbolScriptable(scriptable).put(symbol, scriptable, obj);
                return;
            }
            throw Kit.codeBug();
        }
    }

    public Object get(Object obj) {
        Object obj2;
        if (obj instanceof String) {
            obj2 = get((String) obj, (Scriptable) this);
        } else if (obj instanceof Symbol) {
            obj2 = get((Symbol) obj, (Scriptable) this);
        } else {
            obj2 = obj instanceof Number ? get(((Number) obj).intValue(), (Scriptable) this) : null;
        }
        if (obj2 == Scriptable.NOT_FOUND || obj2 == Undefined.instance) {
            return null;
        }
        return obj2 instanceof Wrapper ? ((Wrapper) obj2).unwrap() : obj2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: org.mozilla.javascript.MemberBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void defineProperty(java.lang.String r9, java.lang.Object r10, java.lang.reflect.Method r11, java.lang.reflect.Method r12, int r13) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            r2 = 0
            if (r11 == 0) goto L_0x004e
            org.mozilla.javascript.MemberBox r3 = new org.mozilla.javascript.MemberBox
            r3.<init>((java.lang.reflect.Method) r11)
            int r4 = r11.getModifiers()
            boolean r4 = java.lang.reflect.Modifier.isStatic(r4)
            if (r4 != 0) goto L_0x001c
            if (r10 == 0) goto L_0x0018
            r4 = 1
            goto L_0x0019
        L_0x0018:
            r4 = 0
        L_0x0019:
            r3.delegateTo = r10
            goto L_0x0021
        L_0x001c:
            java.lang.Class r4 = java.lang.Void.TYPE
            r3.delegateTo = r4
            r4 = 1
        L_0x0021:
            java.lang.Class[] r5 = r11.getParameterTypes()
            int r6 = r5.length
            if (r6 != 0) goto L_0x002f
            if (r4 == 0) goto L_0x002d
            java.lang.String r4 = "msg.obj.getter.parms"
            goto L_0x0042
        L_0x002d:
            r4 = r2
            goto L_0x0042
        L_0x002f:
            int r6 = r5.length
            java.lang.String r7 = "msg.bad.getter.parms"
            if (r6 != r1) goto L_0x0041
            r5 = r5[r0]
            java.lang.Class<org.mozilla.javascript.Scriptable> r6 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r5 == r6) goto L_0x003f
            java.lang.Class<?> r6 = org.mozilla.javascript.ScriptRuntime.ScriptableObjectClass
            if (r5 == r6) goto L_0x003f
            goto L_0x0041
        L_0x003f:
            if (r4 != 0) goto L_0x002d
        L_0x0041:
            r4 = r7
        L_0x0042:
            if (r4 != 0) goto L_0x0045
            goto L_0x004f
        L_0x0045:
            java.lang.String r9 = r11.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r4, r9)
            throw r9
        L_0x004e:
            r3 = r2
        L_0x004f:
            if (r12 == 0) goto L_0x00b2
            java.lang.Class r11 = r12.getReturnType()
            java.lang.Class r4 = java.lang.Void.TYPE
            if (r11 != r4) goto L_0x00a7
            org.mozilla.javascript.MemberBox r11 = new org.mozilla.javascript.MemberBox
            r11.<init>((java.lang.reflect.Method) r12)
            int r4 = r12.getModifiers()
            boolean r4 = java.lang.reflect.Modifier.isStatic(r4)
            if (r4 != 0) goto L_0x0070
            if (r10 == 0) goto L_0x006c
            r4 = 1
            goto L_0x006d
        L_0x006c:
            r4 = 0
        L_0x006d:
            r11.delegateTo = r10
            goto L_0x0075
        L_0x0070:
            java.lang.Class r10 = java.lang.Void.TYPE
            r11.delegateTo = r10
            r4 = 1
        L_0x0075:
            java.lang.Class[] r10 = r12.getParameterTypes()
            int r5 = r10.length
            if (r5 != r1) goto L_0x0081
            if (r4 == 0) goto L_0x009a
            java.lang.String r2 = "msg.setter2.expected"
            goto L_0x009a
        L_0x0081:
            int r1 = r10.length
            r5 = 2
            if (r1 != r5) goto L_0x0098
            r10 = r10[r0]
            java.lang.Class<org.mozilla.javascript.Scriptable> r1 = org.mozilla.javascript.ScriptRuntime.ScriptableClass
            if (r10 == r1) goto L_0x0092
            java.lang.Class<?> r1 = org.mozilla.javascript.ScriptRuntime.ScriptableObjectClass
            if (r10 == r1) goto L_0x0092
            java.lang.String r10 = "msg.setter2.parms"
            goto L_0x0096
        L_0x0092:
            if (r4 != 0) goto L_0x009a
            java.lang.String r10 = "msg.setter1.parms"
        L_0x0096:
            r2 = r10
            goto L_0x009a
        L_0x0098:
            java.lang.String r2 = "msg.setter.parms"
        L_0x009a:
            if (r2 != 0) goto L_0x009e
            r2 = r11
            goto L_0x00b2
        L_0x009e:
            java.lang.String r9 = r12.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r2, r9)
            throw r9
        L_0x00a7:
            java.lang.String r9 = "msg.setter.return"
            java.lang.String r10 = r12.toString()
            org.mozilla.javascript.EvaluatorException r9 = org.mozilla.javascript.Context.reportRuntimeError1(r9, r10)
            throw r9
        L_0x00b2:
            org.mozilla.javascript.SlotMapContainer r10 = r8.slotMap
            org.mozilla.javascript.ScriptableObject$SlotAccess r11 = org.mozilla.javascript.ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER
            org.mozilla.javascript.ScriptableObject$Slot r9 = r10.get(r9, r0, r11)
            org.mozilla.javascript.ScriptableObject$GetterSlot r9 = (org.mozilla.javascript.ScriptableObject.GetterSlot) r9
            r9.setAttributes(r13)
            r9.getter = r3
            r9.setter = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptableObject.defineProperty(java.lang.String, java.lang.Object, java.lang.reflect.Method, java.lang.reflect.Method, int):void");
    }
}
