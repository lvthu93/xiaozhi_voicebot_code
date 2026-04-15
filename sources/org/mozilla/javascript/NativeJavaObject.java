package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import org.mozilla.javascript.TopLevel;

public class NativeJavaObject implements Scriptable, SymbolScriptable, Wrapper, Serializable {
    private static final Object COERCED_INTERFACE_KEY = "Coerced Interface";
    static final byte CONVERSION_NONE = 99;
    static final byte CONVERSION_NONTRIVIAL = 0;
    static final byte CONVERSION_TRIVIAL = 1;
    private static final int JSTYPE_BOOLEAN = 2;
    private static final int JSTYPE_JAVA_ARRAY = 7;
    private static final int JSTYPE_JAVA_CLASS = 5;
    private static final int JSTYPE_JAVA_OBJECT = 6;
    private static final int JSTYPE_NULL = 1;
    private static final int JSTYPE_NUMBER = 3;
    private static final int JSTYPE_OBJECT = 8;
    private static final int JSTYPE_STRING = 4;
    private static final int JSTYPE_UNDEFINED = 0;
    private static Method adapter_readAdapterObject = null;
    private static Method adapter_writeAdapterObject = null;
    private static final long serialVersionUID = -6948590651130498591L;
    private transient Map<String, FieldAndMethods> fieldAndMethods;
    protected transient boolean isAdapter;
    protected transient Object javaObject;
    protected transient JavaMembers members;
    protected Scriptable parent;
    protected Scriptable prototype;
    protected transient Class<?> staticType;

    static {
        Class[] clsArr = new Class[2];
        Class<?> classOrNull = Kit.classOrNull("org.mozilla.javascript.JavaAdapter");
        if (classOrNull != null) {
            try {
                clsArr[0] = ScriptRuntime.ObjectClass;
                clsArr[1] = Kit.classOrNull("java.io.ObjectOutputStream");
                adapter_writeAdapterObject = classOrNull.getMethod("writeAdapterObject", clsArr);
                clsArr[0] = ScriptRuntime.ScriptableClass;
                clsArr[1] = Kit.classOrNull("java.io.ObjectInputStream");
                adapter_readAdapterObject = classOrNull.getMethod("readAdapterObject", clsArr);
            } catch (NoSuchMethodException unused) {
                adapter_writeAdapterObject = null;
                adapter_readAdapterObject = null;
            }
        }
    }

    public NativeJavaObject() {
    }

    public NativeJavaObject(Scriptable scriptable, Object obj, Class<?> cls) {
        this(scriptable, obj, cls, false);
    }

    public static boolean canConvert(Object obj, Class<?> cls) {
        return getConversionWeight(obj, cls) < 99;
    }

    private static Object coerceToNumber(Class<?> cls, Object obj) {
        int i;
        float f;
        float f2;
        Class<?> cls2 = obj.getClass();
        if (cls == Character.TYPE || cls == ScriptRuntime.CharacterClass) {
            Class<?> cls3 = ScriptRuntime.CharacterClass;
            if (cls2 == cls3) {
                return obj;
            }
            return Character.valueOf((char) ((int) toInteger(obj, cls3, 0.0d, 65535.0d)));
        } else if (cls != ScriptRuntime.ObjectClass && cls != ScriptRuntime.DoubleClass && cls != Double.TYPE) {
            Class<?> cls4 = ScriptRuntime.FloatClass;
            if (cls != cls4 && cls != Float.TYPE) {
                Class<?> cls5 = ScriptRuntime.IntegerClass;
                if (cls != cls5 && cls != Integer.TYPE) {
                    Class<?> cls6 = ScriptRuntime.LongClass;
                    if (cls != cls6 && cls != Long.TYPE) {
                        Class<?> cls7 = ScriptRuntime.ShortClass;
                        if (cls != cls7 && cls != Short.TYPE) {
                            Class<?> cls8 = ScriptRuntime.ByteClass;
                            if (cls != cls8 && cls != Byte.TYPE) {
                                return Double.valueOf(toDouble(obj));
                            }
                            if (cls2 == cls8) {
                                return obj;
                            }
                            return Byte.valueOf((byte) ((int) toInteger(obj, cls8, -128.0d, 127.0d)));
                        } else if (cls2 == cls7) {
                            return obj;
                        } else {
                            return Short.valueOf((short) ((int) toInteger(obj, cls7, -32768.0d, 32767.0d)));
                        }
                    } else if (cls2 == cls6) {
                        return obj;
                    } else {
                        return Long.valueOf(toInteger(obj, cls6, Double.longBitsToDouble(-4332462841530417152L), Double.longBitsToDouble(4890909195324358655L)));
                    }
                } else if (cls2 == cls5) {
                    return obj;
                } else {
                    return Integer.valueOf((int) toInteger(obj, cls5, -2.147483648E9d, 2.147483647E9d));
                }
            } else if (cls2 == cls4) {
                return obj;
            } else {
                double d = toDouble(obj);
                if (Double.isInfinite(d) || Double.isNaN(d) || d == 0.0d) {
                    return Float.valueOf((float) d);
                }
                double abs = Math.abs(d);
                if (abs < 1.401298464324817E-45d) {
                    if (i > 0) {
                        f2 = 0.0f;
                    } else {
                        f2 = -0.0f;
                    }
                    return Float.valueOf(f2);
                } else if (abs <= 3.4028234663852886E38d) {
                    return Float.valueOf((float) d);
                } else {
                    if (i > 0) {
                        f = Float.POSITIVE_INFINITY;
                    } else {
                        f = Float.NEGATIVE_INFINITY;
                    }
                    return Float.valueOf(f);
                }
            }
        } else if (cls2 == ScriptRuntime.DoubleClass) {
            return obj;
        } else {
            return Double.valueOf(toDouble(obj));
        }
    }

    @Deprecated
    public static Object coerceType(Class<?> cls, Object obj) {
        return coerceTypeImpl(cls, obj);
    }

    public static Object coerceTypeImpl(Class<?> cls, Object obj) {
        if (obj != null && obj.getClass() == cls) {
            return obj;
        }
        switch (getJSTypeCode(obj)) {
            case 0:
                if (cls != ScriptRuntime.StringClass && cls != ScriptRuntime.ObjectClass) {
                    reportConversionError("undefined", cls);
                    break;
                } else {
                    return "undefined";
                }
            case 1:
                if (!cls.isPrimitive()) {
                    return null;
                }
                reportConversionError(obj, cls);
                return null;
            case 2:
                if (cls != Boolean.TYPE && cls != ScriptRuntime.BooleanClass && cls != ScriptRuntime.ObjectClass) {
                    if (cls != ScriptRuntime.StringClass) {
                        reportConversionError(obj, cls);
                        break;
                    } else {
                        return obj.toString();
                    }
                } else {
                    return obj;
                }
                break;
            case 3:
                if (cls == ScriptRuntime.StringClass) {
                    return ScriptRuntime.toString(obj);
                }
                if (cls == ScriptRuntime.ObjectClass) {
                    Context currentContext = Context.getCurrentContext();
                    if (currentContext == null || !currentContext.hasFeature(18) || ((double) Math.round(toDouble(obj))) != toDouble(obj)) {
                        return coerceToNumber(Double.TYPE, obj);
                    }
                    return coerceToNumber(Long.TYPE, obj);
                } else if ((!cls.isPrimitive() || cls == Boolean.TYPE) && !ScriptRuntime.NumberClass.isAssignableFrom(cls)) {
                    reportConversionError(obj, cls);
                    break;
                } else {
                    return coerceToNumber(cls, obj);
                }
                break;
            case 4:
                if (cls == ScriptRuntime.StringClass || cls.isInstance(obj)) {
                    return obj.toString();
                }
                if (cls == Character.TYPE || cls == ScriptRuntime.CharacterClass) {
                    CharSequence charSequence = (CharSequence) obj;
                    if (charSequence.length() == 1) {
                        return Character.valueOf(charSequence.charAt(0));
                    }
                    return coerceToNumber(cls, obj);
                } else if ((!cls.isPrimitive() || cls == Boolean.TYPE) && !ScriptRuntime.NumberClass.isAssignableFrom(cls)) {
                    reportConversionError(obj, cls);
                    break;
                } else {
                    return coerceToNumber(cls, obj);
                }
                break;
            case 5:
                if (obj instanceof Wrapper) {
                    obj = ((Wrapper) obj).unwrap();
                }
                if (cls != ScriptRuntime.ClassClass && cls != ScriptRuntime.ObjectClass) {
                    if (cls != ScriptRuntime.StringClass) {
                        reportConversionError(obj, cls);
                        break;
                    } else {
                        return obj.toString();
                    }
                } else {
                    return obj;
                }
                break;
            case 6:
            case 7:
                if (obj instanceof Wrapper) {
                    obj = ((Wrapper) obj).unwrap();
                }
                if (!cls.isPrimitive()) {
                    if (cls != ScriptRuntime.StringClass) {
                        if (!cls.isInstance(obj)) {
                            reportConversionError(obj, cls);
                            break;
                        } else {
                            return obj;
                        }
                    } else {
                        return obj.toString();
                    }
                } else {
                    if (cls == Boolean.TYPE) {
                        reportConversionError(obj, cls);
                    }
                    return coerceToNumber(cls, obj);
                }
            case 8:
                if (cls == ScriptRuntime.StringClass) {
                    return ScriptRuntime.toString(obj);
                }
                if (cls.isPrimitive()) {
                    if (cls == Boolean.TYPE) {
                        reportConversionError(obj, cls);
                    }
                    return coerceToNumber(cls, obj);
                } else if (cls.isInstance(obj)) {
                    return obj;
                } else {
                    if (cls == ScriptRuntime.DateClass && (obj instanceof NativeDate)) {
                        return new Date((long) ((NativeDate) obj).getJSTimeValue());
                    }
                    if (cls.isArray() && (obj instanceof NativeArray)) {
                        NativeArray nativeArray = (NativeArray) obj;
                        long length = nativeArray.getLength();
                        Class<?> componentType = cls.getComponentType();
                        Object newInstance = Array.newInstance(componentType, (int) length);
                        for (int i = 0; ((long) i) < length; i++) {
                            try {
                                Array.set(newInstance, i, coerceTypeImpl(componentType, nativeArray.get(i, nativeArray)));
                            } catch (EvaluatorException unused) {
                                reportConversionError(obj, cls);
                            }
                        }
                        return newInstance;
                    } else if (obj instanceof Wrapper) {
                        obj = ((Wrapper) obj).unwrap();
                        if (!cls.isInstance(obj)) {
                            reportConversionError(obj, cls);
                            break;
                        } else {
                            return obj;
                        }
                    } else if (!cls.isInterface() || (!(obj instanceof NativeObject) && !(obj instanceof NativeFunction) && !(obj instanceof ArrowFunction))) {
                        reportConversionError(obj, cls);
                        break;
                    } else {
                        return createInterfaceAdapter(cls, (ScriptableObject) obj);
                    }
                }
                break;
        }
        return obj;
    }

    public static Object createInterfaceAdapter(Class<?> cls, ScriptableObject scriptableObject) {
        Object makeHashKeyFromPair = Kit.makeHashKeyFromPair(COERCED_INTERFACE_KEY, cls);
        Object associatedValue = scriptableObject.getAssociatedValue(makeHashKeyFromPair);
        if (associatedValue != null) {
            return associatedValue;
        }
        return scriptableObject.associateValue(makeHashKeyFromPair, InterfaceAdapter.create(Context.getContext(), cls, scriptableObject));
    }

    public static int getConversionWeight(Object obj, Class<?> cls) {
        int jSTypeCode = getJSTypeCode(obj);
        switch (jSTypeCode) {
            case 0:
                if (cls == ScriptRuntime.StringClass || cls == ScriptRuntime.ObjectClass) {
                    return 1;
                }
                break;
            case 1:
                if (!cls.isPrimitive()) {
                    return 1;
                }
                break;
            case 2:
                if (cls == Boolean.TYPE) {
                    return 1;
                }
                if (cls == ScriptRuntime.BooleanClass) {
                    return 2;
                }
                if (cls == ScriptRuntime.ObjectClass) {
                    return 3;
                }
                if (cls == ScriptRuntime.StringClass) {
                    return 4;
                }
                break;
            case 3:
                if (cls.isPrimitive()) {
                    if (cls == Double.TYPE) {
                        return 1;
                    }
                    if (cls != Boolean.TYPE) {
                        return getSizeRank(cls) + 1;
                    }
                } else if (cls == ScriptRuntime.StringClass) {
                    return 9;
                } else {
                    if (cls == ScriptRuntime.ObjectClass) {
                        return 10;
                    }
                    if (ScriptRuntime.NumberClass.isAssignableFrom(cls)) {
                        return 2;
                    }
                }
                break;
            case 4:
                if (cls == ScriptRuntime.StringClass) {
                    return 1;
                }
                if (cls.isInstance(obj)) {
                    return 2;
                }
                if (cls.isPrimitive()) {
                    if (cls == Character.TYPE) {
                        return 3;
                    }
                    if (cls != Boolean.TYPE) {
                        return 4;
                    }
                }
                break;
            case 5:
                if (cls == ScriptRuntime.ClassClass) {
                    return 1;
                }
                if (cls == ScriptRuntime.ObjectClass) {
                    return 3;
                }
                if (cls == ScriptRuntime.StringClass) {
                    return 4;
                }
                break;
            case 6:
            case 7:
                if (obj instanceof Wrapper) {
                    obj = ((Wrapper) obj).unwrap();
                }
                if (cls.isInstance(obj)) {
                    return 0;
                }
                if (cls == ScriptRuntime.StringClass) {
                    return 2;
                }
                if (!cls.isPrimitive() || cls == Boolean.TYPE || jSTypeCode == 7) {
                    return 99;
                }
                return getSizeRank(cls) + 2;
            case 8:
                Class<?> cls2 = ScriptRuntime.ObjectClass;
                if (cls != cls2 && cls.isInstance(obj)) {
                    return 1;
                }
                if (cls.isArray()) {
                    if (obj instanceof NativeArray) {
                        return 2;
                    }
                } else if (cls == cls2) {
                    return 3;
                } else {
                    if (cls == ScriptRuntime.StringClass) {
                        return 4;
                    }
                    if (cls == ScriptRuntime.DateClass) {
                        if (obj instanceof NativeDate) {
                            return 1;
                        }
                    } else if (cls.isInterface()) {
                        if (obj instanceof NativeFunction) {
                            return 1;
                        }
                        if (obj instanceof NativeObject) {
                            return 2;
                        }
                        return 12;
                    } else if (cls.isPrimitive() && cls != Boolean.TYPE) {
                        return getSizeRank(cls) + 4;
                    }
                }
                break;
        }
        return 99;
    }

    private static int getJSTypeCode(Object obj) {
        if (obj == null) {
            return 1;
        }
        if (obj == Undefined.instance) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return 4;
        }
        if (obj instanceof Number) {
            return 3;
        }
        if (obj instanceof Boolean) {
            return 2;
        }
        if (obj instanceof Scriptable) {
            if (obj instanceof NativeJavaClass) {
                return 5;
            }
            if (obj instanceof NativeJavaArray) {
                return 7;
            }
            if (obj instanceof Wrapper) {
                return 6;
            }
            return 8;
        } else if (obj instanceof Class) {
            return 5;
        } else {
            if (obj.getClass().isArray()) {
                return 7;
            }
            return 6;
        }
    }

    public static int getSizeRank(Class<?> cls) {
        if (cls == Double.TYPE) {
            return 1;
        }
        if (cls == Float.TYPE) {
            return 2;
        }
        if (cls == Long.TYPE) {
            return 3;
        }
        if (cls == Integer.TYPE) {
            return 4;
        }
        if (cls == Short.TYPE) {
            return 5;
        }
        if (cls == Character.TYPE) {
            return 6;
        }
        if (cls == Byte.TYPE) {
            return 7;
        }
        if (cls == Boolean.TYPE) {
            return 99;
        }
        return 8;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        boolean readBoolean = objectInputStream.readBoolean();
        this.isAdapter = readBoolean;
        if (readBoolean) {
            Method method = adapter_readAdapterObject;
            if (method != null) {
                try {
                    this.javaObject = method.invoke((Object) null, new Object[]{this, objectInputStream});
                } catch (Exception unused) {
                    throw new IOException();
                }
            } else {
                throw new ClassNotFoundException();
            }
        } else {
            this.javaObject = objectInputStream.readObject();
        }
        String str = (String) objectInputStream.readObject();
        if (str != null) {
            this.staticType = Class.forName(str);
        } else {
            this.staticType = null;
        }
        initMembers();
    }

    public static void reportConversionError(Object obj, Class<?> cls) {
        throw Context.reportRuntimeError2("msg.conversion.not.allowed", String.valueOf(obj), JavaMembers.javaSignature(cls));
    }

    private static double toDouble(Object obj) {
        Method method;
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof String) {
            return ScriptRuntime.toNumber((String) obj);
        }
        if (!(obj instanceof Scriptable)) {
            try {
                method = obj.getClass().getMethod("doubleValue", (Class[]) null);
            } catch (NoSuchMethodException | SecurityException unused) {
                method = null;
            }
            if (method != null) {
                try {
                    return ((Number) method.invoke(obj, (Object[]) null)).doubleValue();
                } catch (IllegalAccessException unused2) {
                    reportConversionError(obj, Double.TYPE);
                } catch (InvocationTargetException unused3) {
                    reportConversionError(obj, Double.TYPE);
                }
            }
            return ScriptRuntime.toNumber(obj.toString());
        } else if (obj instanceof Wrapper) {
            return toDouble(((Wrapper) obj).unwrap());
        } else {
            return ScriptRuntime.toNumber(obj);
        }
    }

    private static long toInteger(Object obj, Class<?> cls, double d, double d2) {
        double d3;
        double d4 = toDouble(obj);
        if (Double.isInfinite(d4) || Double.isNaN(d4)) {
            reportConversionError(ScriptRuntime.toString(obj), cls);
        }
        if (d4 > 0.0d) {
            d3 = Math.floor(d4);
        } else {
            d3 = Math.ceil(d4);
        }
        if (d3 < d || d3 > d2) {
            reportConversionError(ScriptRuntime.toString(obj), cls);
        }
        return (long) d3;
    }

    @Deprecated
    public static Object wrap(Scriptable scriptable, Object obj, Class<?> cls) {
        Context context = Context.getContext();
        return context.getWrapFactory().wrap(context, scriptable, obj, cls);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeBoolean(this.isAdapter);
        if (this.isAdapter) {
            Method method = adapter_writeAdapterObject;
            if (method != null) {
                try {
                    method.invoke((Object) null, new Object[]{this.javaObject, objectOutputStream});
                } catch (Exception unused) {
                    throw new IOException();
                }
            } else {
                throw new IOException();
            }
        } else {
            objectOutputStream.writeObject(this.javaObject);
        }
        Class<?> cls = this.staticType;
        if (cls != null) {
            objectOutputStream.writeObject(cls.getName());
        } else {
            objectOutputStream.writeObject((Object) null);
        }
    }

    public void delete(int i) {
    }

    public void delete(String str) {
    }

    public void delete(Symbol symbol) {
    }

    public Object get(String str, Scriptable scriptable) {
        FieldAndMethods fieldAndMethods2;
        Map<String, FieldAndMethods> map = this.fieldAndMethods;
        if (map == null || (fieldAndMethods2 = map.get(str)) == null) {
            return this.members.get(this, str, this.javaObject, false);
        }
        return fieldAndMethods2;
    }

    public String getClassName() {
        return "JavaObject";
    }

    public Object getDefaultValue(Class<?> cls) {
        String str;
        if (cls == null) {
            Object obj = this.javaObject;
            if (obj instanceof Boolean) {
                cls = ScriptRuntime.BooleanClass;
            }
            if (obj instanceof Number) {
                cls = ScriptRuntime.NumberClass;
            }
        }
        if (cls == null || cls == ScriptRuntime.StringClass) {
            return this.javaObject.toString();
        }
        if (cls == ScriptRuntime.BooleanClass) {
            str = "booleanValue";
        } else if (cls == ScriptRuntime.NumberClass) {
            str = "doubleValue";
        } else {
            throw Context.reportRuntimeError0("msg.default.value");
        }
        Object obj2 = get(str, (Scriptable) this);
        if (obj2 instanceof Function) {
            Function function = (Function) obj2;
            return function.call(Context.getContext(), function.getParentScope(), this, ScriptRuntime.emptyArgs);
        }
        if (cls == ScriptRuntime.NumberClass) {
            Object obj3 = this.javaObject;
            if (obj3 instanceof Boolean) {
                if (((Boolean) obj3).booleanValue()) {
                    return ScriptRuntime.wrapNumber(1.0d);
                }
                return ScriptRuntime.zeroObj;
            }
        }
        return this.javaObject.toString();
    }

    public Object[] getIds() {
        return this.members.getIds(false);
    }

    public Scriptable getParentScope() {
        return this.parent;
    }

    public Scriptable getPrototype() {
        Scriptable scriptable = this.prototype;
        if (scriptable != null || !(this.javaObject instanceof String)) {
            return scriptable;
        }
        return TopLevel.getBuiltinPrototype(ScriptableObject.getTopLevelScope(this.parent), TopLevel.Builtins.String);
    }

    public boolean has(int i, Scriptable scriptable) {
        return false;
    }

    public boolean has(String str, Scriptable scriptable) {
        return this.members.has(str, false);
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        return false;
    }

    public boolean hasInstance(Scriptable scriptable) {
        return false;
    }

    public void initMembers() {
        Class<?> cls;
        Object obj = this.javaObject;
        if (obj != null) {
            cls = obj.getClass();
        } else {
            cls = this.staticType;
        }
        JavaMembers lookupClass = JavaMembers.lookupClass(this.parent, cls, this.staticType, this.isAdapter);
        this.members = lookupClass;
        this.fieldAndMethods = lookupClass.getFieldAndMethodsObjects(this, this.javaObject, false);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        if (this.prototype == null || this.members.has(str, false)) {
            this.members.put(this, str, this.javaObject, obj, false);
            return;
        }
        Scriptable scriptable2 = this.prototype;
        scriptable2.put(str, scriptable2, obj);
    }

    public void setParentScope(Scriptable scriptable) {
        this.parent = scriptable;
    }

    public void setPrototype(Scriptable scriptable) {
        this.prototype = scriptable;
    }

    public Object unwrap() {
        return this.javaObject;
    }

    public NativeJavaObject(Scriptable scriptable, Object obj, Class<?> cls, boolean z) {
        this.parent = scriptable;
        this.javaObject = obj;
        this.staticType = cls;
        this.isAdapter = z;
        initMembers();
    }

    public Object get(Symbol symbol, Scriptable scriptable) {
        return Scriptable.NOT_FOUND;
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        String obj2 = symbol.toString();
        if (this.prototype == null || this.members.has(obj2, false)) {
            this.members.put(this, obj2, this.javaObject, obj, false);
            return;
        }
        Scriptable scriptable2 = this.prototype;
        if (scriptable2 instanceof SymbolScriptable) {
            ((SymbolScriptable) scriptable2).put(symbol, scriptable2, obj);
        }
    }

    public Object get(int i, Scriptable scriptable) {
        throw this.members.reportMemberNotFound(Integer.toString(i));
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        throw this.members.reportMemberNotFound(Integer.toString(i));
    }
}
