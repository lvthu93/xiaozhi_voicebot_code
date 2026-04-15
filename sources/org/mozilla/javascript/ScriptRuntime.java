package org.mozilla.javascript;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.mozilla.javascript.NativeIterator;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.typedarrays.NativeArrayBuffer;
import org.mozilla.javascript.typedarrays.NativeDataView;
import org.mozilla.javascript.v8dtoa.DoubleConversion;
import org.mozilla.javascript.v8dtoa.FastDtoa;
import org.mozilla.javascript.xml.XMLLib;
import org.mozilla.javascript.xml.XMLObject;

public class ScriptRuntime {
    public static final Class<?> BooleanClass = Kit.classOrNull("java.lang.Boolean");
    public static final Class<?> ByteClass = Kit.classOrNull("java.lang.Byte");
    public static final Class<?> CharacterClass = Kit.classOrNull("java.lang.Character");
    public static final Class<?> ClassClass = Kit.classOrNull("java.lang.Class");
    public static final Class<?> ContextClass = Kit.classOrNull("org.mozilla.javascript.Context");
    public static final Class<?> ContextFactoryClass = Kit.classOrNull("org.mozilla.javascript.ContextFactory");
    private static final String DEFAULT_NS_TAG = "__default_namespace__";
    public static final Class<?> DateClass = Kit.classOrNull("java.util.Date");
    public static final Class<?> DoubleClass = Kit.classOrNull("java.lang.Double");
    public static final int ENUMERATE_ARRAY = 2;
    public static final int ENUMERATE_ARRAY_NO_ITERATOR = 5;
    public static final int ENUMERATE_KEYS = 0;
    public static final int ENUMERATE_KEYS_NO_ITERATOR = 3;
    public static final int ENUMERATE_VALUES = 1;
    public static final int ENUMERATE_VALUES_IN_ORDER = 6;
    public static final int ENUMERATE_VALUES_NO_ITERATOR = 4;
    public static final Class<?> FloatClass = Kit.classOrNull("java.lang.Float");
    public static final Class<?> FunctionClass = Kit.classOrNull("org.mozilla.javascript.Function");
    public static final Class<?> IntegerClass = Kit.classOrNull("java.lang.Integer");
    private static final Object LIBRARY_SCOPE_KEY = "LIBRARY_SCOPE";
    public static final Class<?> LongClass = Kit.classOrNull("java.lang.Long");
    public static final double NaN = Double.NaN;
    public static final Double NaNobj = Double.valueOf(Double.NaN);
    public static final Class<?> NumberClass = Kit.classOrNull("java.lang.Number");
    public static final Class<?> ObjectClass = Kit.classOrNull("java.lang.Object");
    public static final Class<Scriptable> ScriptableClass = Scriptable.class;
    public static final Class<?> ScriptableObjectClass = Kit.classOrNull("org.mozilla.javascript.ScriptableObject");
    public static final Class<?> ShortClass = Kit.classOrNull("java.lang.Short");
    public static final Class<?> StringClass = Kit.classOrNull("java.lang.String");
    public static final Object[] emptyArgs = new Object[0];
    public static final String[] emptyStrings = new String[0];
    public static final MessageProvider messageProvider = new DefaultMessageProvider();
    public static final double negativeZero = Double.longBitsToDouble(Long.MIN_VALUE);
    public static final Double negativeZeroObj = Double.valueOf(-0.0d);
    public static final Double zeroObj = Double.valueOf(0.0d);

    public static class DefaultMessageProvider implements MessageProvider {
        private DefaultMessageProvider() {
        }

        public String getMessage(String str, Object[] objArr) {
            Locale locale;
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null) {
                locale = currentContext.getLocale();
            } else {
                locale = Locale.getDefault();
            }
            try {
                return new MessageFormat(ResourceBundle.getBundle("org.mozilla.javascript.resources.Messages", locale).getString(str)).format(objArr);
            } catch (MissingResourceException unused) {
                throw new RuntimeException(y2.i("no message resource found for message property ", str));
            }
        }
    }

    public static class IdEnumeration implements Serializable {
        private static final long serialVersionUID = 1;
        Object currentId;
        boolean enumNumbers;
        int enumType;
        Object[] ids;
        int index;
        Scriptable iterator;
        Scriptable obj;
        ObjToIntMap used;

        private IdEnumeration() {
        }
    }

    public interface MessageProvider {
        String getMessage(String str, Object[] objArr);
    }

    public static class NoSuchMethodShim implements Callable {
        String methodName;
        Callable noSuchMethodMethod;

        public NoSuchMethodShim(Callable callable, String str) {
            this.noSuchMethodMethod = callable;
            this.methodName = str;
        }

        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            return this.noSuchMethodMethod.call(context, scriptable, scriptable2, new Object[]{this.methodName, ScriptRuntime.newArrayLiteral(objArr, (int[]) null, context, scriptable)});
        }
    }

    public static Object add(Object obj, Object obj2, Context context) {
        Object addValues;
        Object addValues2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            return wrapNumber(((Number) obj2).doubleValue() + ((Number) obj).doubleValue());
        } else if ((obj instanceof XMLObject) && (addValues2 = ((XMLObject) obj).addValues(context, true, obj2)) != Scriptable.NOT_FOUND) {
            return addValues2;
        } else {
            if ((obj2 instanceof XMLObject) && (addValues = ((XMLObject) obj2).addValues(context, false, obj)) != Scriptable.NOT_FOUND) {
                return addValues;
            }
            if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
                throw typeError0("msg.not.a.number");
            }
            if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue((Class<?>) null);
            }
            if (obj2 instanceof Scriptable) {
                obj2 = ((Scriptable) obj2).getDefaultValue((Class<?>) null);
            }
            if ((obj instanceof CharSequence) || (obj2 instanceof CharSequence)) {
                return new ConsString(toCharSequence(obj), toCharSequence(obj2));
            }
            if (!(obj instanceof Number) || !(obj2 instanceof Number)) {
                return wrapNumber(toNumber(obj2) + toNumber(obj));
            }
            return wrapNumber(((Number) obj2).doubleValue() + ((Number) obj).doubleValue());
        }
    }

    public static void addInstructionCount(Context context, int i) {
        int i2 = context.instructionCount + i;
        context.instructionCount = i2;
        if (i2 > context.instructionThreshold) {
            context.observeInstructionCount(i2);
            context.instructionCount = 0;
        }
    }

    public static Object applyOrCall(boolean z, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable scriptable3;
        Object[] objArr2;
        int length = objArr.length;
        Callable callable = getCallable(scriptable2);
        if (length == 0) {
            scriptable3 = null;
        } else if (context.hasFeature(15)) {
            scriptable3 = toObjectOrNull(context, objArr[0], scriptable);
        } else {
            Object obj = objArr[0];
            if (obj == Undefined.instance) {
                scriptable3 = Undefined.SCRIPTABLE_UNDEFINED;
            } else {
                scriptable3 = toObjectOrNull(context, obj, scriptable);
            }
        }
        if (scriptable3 == null && context.hasFeature(15)) {
            scriptable3 = getTopCallScope(context);
        }
        if (z) {
            if (length <= 1) {
                objArr2 = emptyArgs;
            } else {
                objArr2 = getApplyArguments(context, objArr[1]);
            }
        } else if (length <= 1) {
            objArr2 = emptyArgs;
        } else {
            int i = length - 1;
            objArr2 = new Object[i];
            System.arraycopy(objArr, 1, objArr2, 0, i);
        }
        return callable.call(context, scriptable, scriptable3, objArr2);
    }

    public static Scriptable bind(Context context, Scriptable scriptable, String str) {
        Scriptable parentScope = scriptable.getParentScope();
        XMLObject xMLObject = null;
        if (parentScope != null) {
            XMLObject xMLObject2 = null;
            while (true) {
                if (scriptable instanceof NativeWith) {
                    Scriptable prototype = scriptable.getPrototype();
                    if (prototype instanceof XMLObject) {
                        XMLObject xMLObject3 = (XMLObject) prototype;
                        if (xMLObject3.has(context, str)) {
                            return xMLObject3;
                        }
                        if (xMLObject2 == null) {
                            xMLObject2 = xMLObject3;
                        }
                    } else if (ScriptableObject.hasProperty(prototype, str)) {
                        return prototype;
                    }
                    Scriptable parentScope2 = parentScope.getParentScope();
                    if (parentScope2 == null) {
                        break;
                    }
                    Scriptable scriptable2 = parentScope;
                    parentScope = parentScope2;
                    scriptable = scriptable2;
                } else {
                    while (!ScriptableObject.hasProperty(scriptable, str)) {
                        Scriptable parentScope3 = parentScope.getParentScope();
                        if (parentScope3 != null) {
                            Scriptable scriptable3 = parentScope;
                            parentScope = parentScope3;
                            scriptable = scriptable3;
                        }
                    }
                    return scriptable;
                }
            }
            scriptable = parentScope;
            xMLObject = xMLObject2;
        }
        if (context.useDynamicScope) {
            scriptable = checkDynamicScope(context.topCallScope, scriptable);
        }
        if (ScriptableObject.hasProperty(scriptable, str)) {
            return scriptable;
        }
        return xMLObject;
    }

    @Deprecated
    public static Object call(Context context, Object obj, Object obj2, Object[] objArr, Scriptable scriptable) {
        if (obj instanceof Function) {
            Function function = (Function) obj;
            Scriptable objectOrNull = toObjectOrNull(context, obj2, scriptable);
            if (objectOrNull != null) {
                return function.call(context, scriptable, objectOrNull, objArr);
            }
            throw undefCallError((Object) null, "function");
        }
        throw notFunctionError(toString(obj));
    }

    public static Object callIterator(Object obj, Context context, Scriptable scriptable) {
        return getElemFunctionAndThis(obj, SymbolKey.ITERATOR, context, scriptable).call(context, scriptable, lastStoredScriptable(context), emptyArgs);
    }

    public static Ref callRef(Callable callable, Scriptable scriptable, Object[] objArr, Context context) {
        if (callable instanceof RefCallable) {
            RefCallable refCallable = (RefCallable) callable;
            Ref refCall = refCallable.refCall(context, scriptable, objArr);
            if (refCall != null) {
                return refCall;
            }
            throw new IllegalStateException(refCallable.getClass().getName().concat(".refCall() returned null"));
        }
        throw constructError("ReferenceError", getMessage1("msg.no.ref.from.function", toString((Object) callable)));
    }

    public static Object callSpecial(Context context, Callable callable, Scriptable scriptable, Object[] objArr, Scriptable scriptable2, Scriptable scriptable3, int i, String str, int i2) {
        if (i == 1) {
            if (scriptable.getParentScope() == null && NativeGlobal.isEvalFunction(callable)) {
                return evalSpecial(context, scriptable2, scriptable3, objArr, str, i2);
            }
        } else if (i != 2) {
            throw Kit.codeBug();
        } else if (NativeWith.isWithFunction(callable)) {
            throw Context.reportRuntimeError1("msg.only.from.new", "With");
        }
        return callable.call(context, scriptable2, scriptable, objArr);
    }

    public static void checkDeprecated(Context context, String str) {
        int languageVersion = context.getLanguageVersion();
        if (languageVersion >= 140 || languageVersion == 0) {
            String message1 = getMessage1("msg.deprec.ctor", str);
            if (languageVersion == 0) {
                Context.reportWarning(message1);
                return;
            }
            throw Context.reportRuntimeError(message1);
        }
    }

    public static Scriptable checkDynamicScope(Scriptable scriptable, Scriptable scriptable2) {
        if (scriptable == scriptable2) {
            return scriptable;
        }
        Scriptable scriptable3 = scriptable;
        do {
            scriptable3 = scriptable3.getPrototype();
            if (scriptable3 == scriptable2) {
                return scriptable;
            }
        } while (scriptable3 != null);
        return scriptable2;
    }

    public static RegExpProxy checkRegExpProxy(Context context) {
        RegExpProxy regExpProxy = getRegExpProxy(context);
        if (regExpProxy != null) {
            return regExpProxy;
        }
        throw Context.reportRuntimeError0("msg.no.regexp");
    }

    public static boolean cmp_LE(Object obj, Object obj2) {
        double d;
        double d2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            d2 = ((Number) obj).doubleValue();
            d = ((Number) obj2).doubleValue();
        } else if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
            throw typeError0("msg.compare.symbol");
        } else {
            if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue(NumberClass);
            }
            if (obj2 instanceof Scriptable) {
                obj2 = ((Scriptable) obj2).getDefaultValue(NumberClass);
            }
            if (!(obj instanceof CharSequence) || !(obj2 instanceof CharSequence)) {
                d2 = toNumber(obj);
                d = toNumber(obj2);
            } else if (obj.toString().compareTo(obj2.toString()) <= 0) {
                return true;
            } else {
                return false;
            }
        }
        if (d2 <= d) {
            return true;
        }
        return false;
    }

    public static boolean cmp_LT(Object obj, Object obj2) {
        double d;
        double d2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            d2 = ((Number) obj).doubleValue();
            d = ((Number) obj2).doubleValue();
        } else if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
            throw typeError0("msg.compare.symbol");
        } else {
            if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue(NumberClass);
            }
            if (obj2 instanceof Scriptable) {
                obj2 = ((Scriptable) obj2).getDefaultValue(NumberClass);
            }
            if (!(obj instanceof CharSequence) || !(obj2 instanceof CharSequence)) {
                d2 = toNumber(obj);
                d = toNumber(obj2);
            } else if (obj.toString().compareTo(obj2.toString()) < 0) {
                return true;
            } else {
                return false;
            }
        }
        if (d2 < d) {
            return true;
        }
        return false;
    }

    public static EcmaError constructError(String str, String str2) {
        int[] iArr = new int[1];
        return constructError(str, str2, Context.getSourcePositionFromStack(iArr), iArr[0], (String) null, 0);
    }

    public static Scriptable createArrowFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr, boolean z) {
        return new NativeCall(nativeFunction, scriptable, objArr, true, z);
    }

    @Deprecated
    public static Scriptable createFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr) {
        return createFunctionActivation(nativeFunction, scriptable, objArr, false);
    }

    private static XMLLib currentXMLLib(Context context) {
        Scriptable scriptable = context.topCallScope;
        if (scriptable != null) {
            XMLLib xMLLib = context.cachedXMLLib;
            if (xMLLib == null) {
                xMLLib = XMLLib.extractFromScope(scriptable);
                if (xMLLib != null) {
                    context.cachedXMLLib = xMLLib;
                } else {
                    throw new IllegalStateException();
                }
            }
            return xMLLib;
        }
        throw new IllegalStateException();
    }

    public static String defaultObjectToSource(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        boolean z;
        boolean z2;
        Object obj;
        ObjToIntMap objToIntMap = context.iterating;
        if (objToIntMap == null) {
            context.iterating = new ObjToIntMap(31);
            z = true;
            z2 = false;
        } else {
            z2 = objToIntMap.has(scriptable2);
            z = false;
        }
        StringBuilder sb = new StringBuilder(128);
        if (z) {
            sb.append("(");
        }
        sb.append('{');
        if (!z2) {
            try {
                context.iterating.intern(scriptable2);
                Object[] ids = scriptable2.getIds();
                for (int i = 0; i < ids.length; i++) {
                    Object obj2 = ids[i];
                    if (obj2 instanceof Integer) {
                        int intValue = ((Integer) obj2).intValue();
                        obj = scriptable2.get(intValue, scriptable2);
                        if (obj == Scriptable.NOT_FOUND) {
                        } else {
                            if (i > 0) {
                                sb.append(", ");
                            }
                            sb.append(intValue);
                        }
                    } else {
                        String str = (String) obj2;
                        obj = scriptable2.get(str, scriptable2);
                        if (obj == Scriptable.NOT_FOUND) {
                        } else {
                            if (i > 0) {
                                sb.append(", ");
                            }
                            if (isValidIdentifierName(str, context, context.isStrictMode())) {
                                sb.append(str);
                            } else {
                                sb.append('\'');
                                sb.append(escapeString(str, '\''));
                                sb.append('\'');
                            }
                        }
                    }
                    sb.append(':');
                    sb.append(uneval(context, scriptable, obj));
                }
            } catch (Throwable th) {
                if (z) {
                    context.iterating = null;
                }
                throw th;
            }
        }
        if (z) {
            context.iterating = null;
        }
        sb.append('}');
        if (z) {
            sb.append(')');
        }
        return sb.toString();
    }

    public static String defaultObjectToString(Scriptable scriptable) {
        if (scriptable == null) {
            return "[object Null]";
        }
        if (Undefined.isUndefined(scriptable)) {
            return "[object Undefined]";
        }
        return "[object " + scriptable.getClassName() + ']';
    }

    @Deprecated
    public static Object delete(Object obj, Object obj2, Context context) {
        return delete(obj, obj2, context, false);
    }

    public static boolean deleteObjectElem(Scriptable scriptable, Object obj, Context context) {
        if (isSymbol(obj)) {
            SymbolScriptable ensureSymbolScriptable = ScriptableObject.ensureSymbolScriptable(scriptable);
            Symbol symbol = (Symbol) obj;
            ensureSymbolScriptable.delete(symbol);
            return !ensureSymbolScriptable.has(symbol, scriptable);
        }
        StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, obj);
        String str = stringIdOrIndex.stringId;
        if (str == null) {
            scriptable.delete(stringIdOrIndex.index);
            return !scriptable.has(stringIdOrIndex.index, scriptable);
        }
        scriptable.delete(str);
        return !scriptable.has(stringIdOrIndex.stringId, scriptable);
    }

    private static Object doScriptableIncrDecr(Scriptable scriptable, String str, Scriptable scriptable2, Object obj, int i) {
        boolean z;
        double d;
        double d2;
        if ((i & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (obj instanceof Number) {
            d = ((Number) obj).doubleValue();
        } else {
            d = toNumber(obj);
            if (z) {
                obj = wrapNumber(d);
            }
        }
        if ((i & 1) == 0) {
            d2 = d + 1.0d;
        } else {
            d2 = d - 1.0d;
        }
        Number wrapNumber = wrapNumber(d2);
        scriptable.put(str, scriptable2, (Object) wrapNumber);
        if (z) {
            return obj;
        }
        return wrapNumber;
    }

    @Deprecated
    public static Object doTopCall(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return doTopCall(callable, context, scriptable, scriptable2, objArr, context.isTopLevelStrict);
    }

    @Deprecated
    public static Object elemIncrDecr(Object obj, Object obj2, Context context, int i) {
        return elemIncrDecr(obj, obj2, context, getTopCallScope(context), i);
    }

    public static void enterActivationFunction(Context context, Scriptable scriptable) {
        if (context.topCallScope != null) {
            NativeCall nativeCall = (NativeCall) scriptable;
            nativeCall.parentActivationCall = context.currentActivationCall;
            context.currentActivationCall = nativeCall;
            nativeCall.defineAttributesForArguments();
            return;
        }
        throw new IllegalStateException();
    }

    public static Scriptable enterDotQuery(Object obj, Scriptable scriptable) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).enterDotQuery(scriptable);
        }
        throw notXmlError(obj);
    }

    public static Scriptable enterWith(Object obj, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw typeError1("msg.undef.with", toString(obj));
        } else if (objectOrNull instanceof XMLObject) {
            return ((XMLObject) objectOrNull).enterWith(scriptable);
        } else {
            return new NativeWith(scriptable, objectOrNull);
        }
    }

    private static void enumChangeObject(IdEnumeration idEnumeration) {
        Object[] objArr;
        Object[] objArr2 = null;
        while (true) {
            Scriptable scriptable = idEnumeration.obj;
            if (scriptable == null) {
                break;
            }
            objArr2 = scriptable.getIds();
            if (objArr2.length != 0) {
                break;
            }
            idEnumeration.obj = idEnumeration.obj.getPrototype();
        }
        if (!(idEnumeration.obj == null || (objArr = idEnumeration.ids) == null)) {
            int length = objArr.length;
            if (idEnumeration.used == null) {
                idEnumeration.used = new ObjToIntMap(length);
            }
            for (int i = 0; i != length; i++) {
                idEnumeration.used.intern(objArr[i]);
            }
        }
        idEnumeration.ids = objArr2;
        idEnumeration.index = 0;
    }

    public static Object enumId(Object obj, Context context) {
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        if (idEnumeration.iterator != null) {
            return idEnumeration.currentId;
        }
        int i = idEnumeration.enumType;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 5) {
                                throw Kit.codeBug();
                            }
                        }
                    }
                }
                return context.newArray(ScriptableObject.getTopLevelScope(idEnumeration.obj), new Object[]{idEnumeration.currentId, enumValue(obj, context)});
            }
            return enumValue(obj, context);
        }
        return idEnumeration.currentId;
    }

    @Deprecated
    public static Object enumInit(Object obj, Context context, boolean z) {
        return enumInit(obj, context, z ? 1 : 0);
    }

    private static Object enumInitInOrder(Context context, IdEnumeration idEnumeration) {
        Scriptable scriptable = idEnumeration.obj;
        if (scriptable instanceof SymbolScriptable) {
            SymbolKey symbolKey = SymbolKey.ITERATOR;
            if (ScriptableObject.hasProperty(scriptable, (Symbol) symbolKey)) {
                Object property = ScriptableObject.getProperty(idEnumeration.obj, (Symbol) symbolKey);
                if (property instanceof Callable) {
                    Object call = ((Callable) property).call(context, idEnumeration.obj.getParentScope(), idEnumeration.obj, new Object[0]);
                    if (call instanceof Scriptable) {
                        idEnumeration.iterator = (Scriptable) call;
                        return idEnumeration;
                    }
                    throw typeError1("msg.not.iterable", toString((Object) idEnumeration.obj));
                }
                throw typeError1("msg.not.iterable", toString((Object) idEnumeration.obj));
            }
        }
        throw typeError1("msg.not.iterable", toString((Object) idEnumeration.obj));
    }

    public static Boolean enumNext(Object obj) {
        Object obj2;
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        Scriptable scriptable = idEnumeration.iterator;
        if (scriptable == null) {
            while (true) {
                Scriptable scriptable2 = idEnumeration.obj;
                if (scriptable2 == null) {
                    return Boolean.FALSE;
                }
                int i = idEnumeration.index;
                Object[] objArr = idEnumeration.ids;
                if (i == objArr.length) {
                    idEnumeration.obj = scriptable2.getPrototype();
                    enumChangeObject(idEnumeration);
                } else {
                    idEnumeration.index = i + 1;
                    Object obj3 = objArr[i];
                    ObjToIntMap objToIntMap = idEnumeration.used;
                    if ((objToIntMap == null || !objToIntMap.has(obj3)) && !(obj3 instanceof Symbol)) {
                        if (obj3 instanceof String) {
                            String str = (String) obj3;
                            Scriptable scriptable3 = idEnumeration.obj;
                            if (scriptable3.has(str, scriptable3)) {
                                idEnumeration.currentId = str;
                                break;
                            }
                        } else {
                            int intValue = ((Number) obj3).intValue();
                            Scriptable scriptable4 = idEnumeration.obj;
                            if (scriptable4.has(intValue, scriptable4)) {
                                if (idEnumeration.enumNumbers) {
                                    obj2 = Integer.valueOf(intValue);
                                } else {
                                    obj2 = String.valueOf(intValue);
                                }
                                idEnumeration.currentId = obj2;
                            }
                        }
                    }
                }
            }
            return Boolean.TRUE;
        } else if (idEnumeration.enumType == 6) {
            return enumNextInOrder(idEnumeration);
        } else {
            Object property = ScriptableObject.getProperty(scriptable, ES6Iterator.NEXT_METHOD);
            if (!(property instanceof Callable)) {
                return Boolean.FALSE;
            }
            try {
                idEnumeration.currentId = ((Callable) property).call(Context.getContext(), idEnumeration.iterator.getParentScope(), idEnumeration.iterator, emptyArgs);
                return Boolean.TRUE;
            } catch (JavaScriptException e) {
                if (e.getValue() instanceof NativeIterator.StopIteration) {
                    return Boolean.FALSE;
                }
                throw e;
            }
        }
    }

    private static Boolean enumNextInOrder(IdEnumeration idEnumeration) {
        Object property = ScriptableObject.getProperty(idEnumeration.iterator, ES6Iterator.NEXT_METHOD);
        if (property instanceof Callable) {
            Context context = Context.getContext();
            Scriptable parentScope = idEnumeration.iterator.getParentScope();
            Scriptable object = toObject(context, parentScope, ((Callable) property).call(context, parentScope, idEnumeration.iterator, emptyArgs));
            Object property2 = ScriptableObject.getProperty(object, ES6Iterator.DONE_PROPERTY);
            if (property2 != Scriptable.NOT_FOUND && toBoolean(property2)) {
                return Boolean.FALSE;
            }
            idEnumeration.currentId = ScriptableObject.getProperty(object, ES6Iterator.VALUE_PROPERTY);
            return Boolean.TRUE;
        }
        throw notFunctionError(idEnumeration.iterator, ES6Iterator.NEXT_METHOD);
    }

    public static Object enumValue(Object obj, Context context) {
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        if (isSymbol(idEnumeration.currentId)) {
            return ScriptableObject.ensureSymbolScriptable(idEnumeration.obj).get((Symbol) idEnumeration.currentId, idEnumeration.obj);
        }
        StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, idEnumeration.currentId);
        String str = stringIdOrIndex.stringId;
        if (str == null) {
            Scriptable scriptable = idEnumeration.obj;
            return scriptable.get(stringIdOrIndex.index, scriptable);
        }
        Scriptable scriptable2 = idEnumeration.obj;
        return scriptable2.get(str, scriptable2);
    }

    public static boolean eq(Object obj, Object obj2) {
        Object equivalentValues;
        Object equivalentValues2;
        Object equivalentValues3;
        Object equivalentValues4;
        Object equivalentValues5;
        if (obj == null || obj == Undefined.instance) {
            if (obj2 == null || obj2 == Undefined.instance) {
                return true;
            }
            if (!(obj2 instanceof ScriptableObject) || (equivalentValues = ((ScriptableObject) obj2).equivalentValues(obj)) == Scriptable.NOT_FOUND) {
                return false;
            }
            return ((Boolean) equivalentValues).booleanValue();
        } else if (obj instanceof Number) {
            return eqNumber(((Number) obj).doubleValue(), obj2);
        } else {
            if (obj == obj2) {
                return true;
            }
            if (obj instanceof CharSequence) {
                return eqString((CharSequence) obj, obj2);
            }
            double d = 1.0d;
            if (obj instanceof Boolean) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (obj2 instanceof Boolean) {
                    if (booleanValue == ((Boolean) obj2).booleanValue()) {
                        return true;
                    }
                    return false;
                } else if ((obj2 instanceof ScriptableObject) && (equivalentValues5 = ((ScriptableObject) obj2).equivalentValues(obj)) != Scriptable.NOT_FOUND) {
                    return ((Boolean) equivalentValues5).booleanValue();
                } else {
                    if (!booleanValue) {
                        d = 0.0d;
                    }
                    return eqNumber(d, obj2);
                }
            } else if (!(obj instanceof Scriptable)) {
                warnAboutNonJSObject(obj);
                if (obj == obj2) {
                    return true;
                }
                return false;
            } else if (obj2 instanceof Scriptable) {
                if ((obj instanceof ScriptableObject) && (equivalentValues4 = ((ScriptableObject) obj).equivalentValues(obj2)) != Scriptable.NOT_FOUND) {
                    return ((Boolean) equivalentValues4).booleanValue();
                }
                if ((obj2 instanceof ScriptableObject) && (equivalentValues3 = ((ScriptableObject) obj2).equivalentValues(obj)) != Scriptable.NOT_FOUND) {
                    return ((Boolean) equivalentValues3).booleanValue();
                }
                if (!(obj instanceof Wrapper) || !(obj2 instanceof Wrapper)) {
                    return false;
                }
                Object unwrap = ((Wrapper) obj).unwrap();
                Object unwrap2 = ((Wrapper) obj2).unwrap();
                if (unwrap == unwrap2) {
                    return true;
                }
                if (!isPrimitive(unwrap) || !isPrimitive(unwrap2) || !eq(unwrap, unwrap2)) {
                    return false;
                }
                return true;
            } else if (obj2 instanceof Boolean) {
                if ((obj instanceof ScriptableObject) && (equivalentValues2 = ((ScriptableObject) obj).equivalentValues(obj2)) != Scriptable.NOT_FOUND) {
                    return ((Boolean) equivalentValues2).booleanValue();
                }
                if (!((Boolean) obj2).booleanValue()) {
                    d = 0.0d;
                }
                return eqNumber(d, obj);
            } else if (obj2 instanceof Number) {
                return eqNumber(((Number) obj2).doubleValue(), obj);
            } else {
                if (obj2 instanceof CharSequence) {
                    return eqString((CharSequence) obj2, obj);
                }
                return false;
            }
        }
    }

    public static boolean eqNumber(double d, Object obj) {
        double d2;
        Object equivalentValues;
        while (true) {
            if (obj != null && obj != Undefined.instance) {
                if (!(obj instanceof Number)) {
                    if (!(obj instanceof CharSequence)) {
                        if (!(obj instanceof Boolean)) {
                            if (!isSymbol(obj)) {
                                if (!(obj instanceof Scriptable)) {
                                    warnAboutNonJSObject(obj);
                                    break;
                                } else if ((obj instanceof ScriptableObject) && (equivalentValues = ((ScriptableObject) obj).equivalentValues(wrapNumber(d))) != Scriptable.NOT_FOUND) {
                                    return ((Boolean) equivalentValues).booleanValue();
                                } else {
                                    obj = toPrimitive(obj);
                                }
                            } else {
                                return false;
                            }
                        } else {
                            if (((Boolean) obj).booleanValue()) {
                                d2 = 1.0d;
                            } else {
                                d2 = 0.0d;
                            }
                            if (d == d2) {
                                return true;
                            }
                            return false;
                        }
                    } else if (d == toNumber(obj)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (d == ((Number) obj).doubleValue()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static boolean eqString(CharSequence charSequence, Object obj) {
        double d;
        Object equivalentValues;
        while (true) {
            if (obj != null && obj != Undefined.instance) {
                if (!(obj instanceof CharSequence)) {
                    if (!(obj instanceof Number)) {
                        if (!(obj instanceof Boolean)) {
                            if (!isSymbol(obj)) {
                                if (!(obj instanceof Scriptable)) {
                                    warnAboutNonJSObject(obj);
                                    break;
                                } else if ((obj instanceof ScriptableObject) && (equivalentValues = ((ScriptableObject) obj).equivalentValues(charSequence.toString())) != Scriptable.NOT_FOUND) {
                                    return ((Boolean) equivalentValues).booleanValue();
                                } else {
                                    obj = toPrimitive(obj);
                                }
                            } else {
                                return false;
                            }
                        } else {
                            double number = toNumber(charSequence.toString());
                            if (((Boolean) obj).booleanValue()) {
                                d = 1.0d;
                            } else {
                                d = 0.0d;
                            }
                            if (number == d) {
                                return true;
                            }
                            return false;
                        }
                    } else if (toNumber(charSequence.toString()) == ((Number) obj).doubleValue()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    CharSequence charSequence2 = (CharSequence) obj;
                    if (charSequence.length() != charSequence2.length() || !charSequence.toString().equals(charSequence2.toString())) {
                        return false;
                    }
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static RuntimeException errorWithClassName(String str, Object obj) {
        return Context.reportRuntimeError1(str, obj.getClass().getName());
    }

    public static String escapeAttributeValue(Object obj, Context context) {
        return currentXMLLib(context).escapeAttributeValue(obj);
    }

    public static String escapeString(String str) {
        return escapeString(str, '\"');
    }

    public static String escapeTextValue(Object obj, Context context) {
        return currentXMLLib(context).escapeTextValue(obj);
    }

    public static Object evalSpecial(Context context, Scriptable scriptable, Object obj, Object[] objArr, String str, int i) {
        if (objArr.length < 1) {
            return Undefined.instance;
        }
        Object obj2 = objArr[0];
        if (obj2 instanceof CharSequence) {
            if (str == null) {
                int[] iArr = new int[1];
                String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
                if (sourcePositionFromStack != null) {
                    i = iArr[0];
                    str = sourcePositionFromStack;
                } else {
                    str = "";
                }
            }
            String makeUrlForGeneratedScript = makeUrlForGeneratedScript(true, str, i);
            ErrorReporter forEval = DefaultErrorReporter.forEval(context.getErrorReporter());
            Evaluator createInterpreter = Context.createInterpreter();
            if (createInterpreter != null) {
                Script compileString = context.compileString(obj2.toString(), createInterpreter, forEval, makeUrlForGeneratedScript, 1, (Object) null);
                createInterpreter.setEvalScriptFlag(compileString);
                return ((Callable) compileString).call(context, scriptable, (Scriptable) obj, emptyArgs);
            }
            throw new JavaScriptException("Interpreter not present", str, i);
        } else if (context.hasFeature(11) || context.hasFeature(9)) {
            throw Context.reportRuntimeError0("msg.eval.nonstring.strict");
        } else {
            Context.reportWarning(getMessage0("msg.eval.nonstring"));
            return obj2;
        }
    }

    public static void exitActivationFunction(Context context) {
        NativeCall nativeCall = context.currentActivationCall;
        context.currentActivationCall = nativeCall.parentActivationCall;
        nativeCall.parentActivationCall = null;
    }

    public static NativeCall findFunctionActivation(Context context, Function function) {
        for (NativeCall nativeCall = context.currentActivationCall; nativeCall != null; nativeCall = nativeCall.parentActivationCall) {
            if (nativeCall.function == function) {
                return nativeCall;
            }
        }
        return null;
    }

    public static Object[] getApplyArguments(Context context, Object obj) {
        if (obj == null || obj == Undefined.instance) {
            return emptyArgs;
        }
        if (obj instanceof Scriptable) {
            Scriptable scriptable = (Scriptable) obj;
            if (isArrayLike(scriptable)) {
                return context.getElements(scriptable);
            }
        }
        if (obj instanceof ScriptableObject) {
            return emptyArgs;
        }
        throw typeError0("msg.arg.isnt.array");
    }

    public static Object[] getArrayElements(Scriptable scriptable) {
        long lengthProperty = NativeArray.getLengthProperty(Context.getContext(), scriptable, false);
        if (lengthProperty <= 2147483647L) {
            int i = (int) lengthProperty;
            if (i == 0) {
                return emptyArgs;
            }
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                Object property = ScriptableObject.getProperty(scriptable, i2);
                if (property == Scriptable.NOT_FOUND) {
                    property = Undefined.instance;
                }
                objArr[i2] = property;
            }
            return objArr;
        }
        throw new IllegalArgumentException();
    }

    public static Callable getCallable(Scriptable scriptable) {
        if (scriptable instanceof Callable) {
            return (Callable) scriptable;
        }
        Object defaultValue = scriptable.getDefaultValue(FunctionClass);
        if (defaultValue instanceof Callable) {
            return (Callable) defaultValue;
        }
        throw notFunctionError(defaultValue, scriptable);
    }

    @Deprecated
    public static Callable getElemFunctionAndThis(Object obj, Object obj2, Context context) {
        return getElemFunctionAndThis(obj, obj2, context, getTopCallScope(context));
    }

    public static Function getExistingCtor(Context context, Scriptable scriptable, String str) {
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (property instanceof Function) {
            return (Function) property;
        }
        if (property == Scriptable.NOT_FOUND) {
            throw Context.reportRuntimeError1("msg.ctor.not.found", str);
        }
        throw Context.reportRuntimeError1("msg.not.ctor", str);
    }

    public static ScriptableObject getGlobal(Context context) {
        Class<?> classOrNull = Kit.classOrNull("org.mozilla.javascript.tools.shell.Global");
        if (classOrNull != null) {
            try {
                return (ScriptableObject) classOrNull.getConstructor(new Class[]{ContextClass}).newInstance(new Object[]{context});
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
        return new ImporterTopLevel(context);
    }

    public static Object getIndexObject(String str) {
        long indexFromString = indexFromString(str);
        return indexFromString >= 0 ? Integer.valueOf((int) indexFromString) : str;
    }

    public static ScriptableObject getLibraryScopeOrNull(Scriptable scriptable) {
        return (ScriptableObject) ScriptableObject.getTopScopeValue(scriptable, LIBRARY_SCOPE_KEY);
    }

    public static String getMessage(String str, Object[] objArr) {
        return messageProvider.getMessage(str, objArr);
    }

    public static String getMessage0(String str) {
        return getMessage(str, (Object[]) null);
    }

    public static String getMessage1(String str, Object obj) {
        return getMessage(str, new Object[]{obj});
    }

    public static String getMessage2(String str, Object obj, Object obj2) {
        return getMessage(str, new Object[]{obj, obj2});
    }

    public static String getMessage3(String str, Object obj, Object obj2, Object obj3) {
        return getMessage(str, new Object[]{obj, obj2, obj3});
    }

    public static String getMessage4(String str, Object obj, Object obj2, Object obj3, Object obj4) {
        return getMessage(str, new Object[]{obj, obj2, obj3, obj4});
    }

    public static Callable getNameFunctionAndThis(String str, Context context, Scriptable scriptable) {
        Scriptable parentScope = scriptable.getParentScope();
        if (parentScope != null) {
            return (Callable) nameOrFunction(context, scriptable, parentScope, str, true);
        }
        Object obj = topScopeName(context, scriptable, str);
        if (obj instanceof Callable) {
            storeScriptable(context, scriptable);
            return (Callable) obj;
        } else if (obj == Scriptable.NOT_FOUND) {
            throw notFoundError(scriptable, str);
        } else {
            throw notFunctionError(obj, str);
        }
    }

    @Deprecated
    public static Object getObjectElem(Object obj, Object obj2, Context context) {
        return getObjectElem(obj, obj2, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectIndex(Object obj, double d, Context context) {
        return getObjectIndex(obj, d, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectProp(Object obj, String str, Context context) {
        return getObjectProp(obj, str, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectPropNoWarn(Object obj, String str, Context context) {
        return getObjectPropNoWarn(obj, str, context, getTopCallScope(context));
    }

    @Deprecated
    public static Callable getPropFunctionAndThis(Object obj, String str, Context context) {
        return getPropFunctionAndThis(obj, str, context, getTopCallScope(context));
    }

    private static Callable getPropFunctionAndThisHelper(Object obj, String str, Context context, Scriptable scriptable) {
        if (scriptable != null) {
            Object property = ScriptableObject.getProperty(scriptable, str);
            if (!(property instanceof Callable)) {
                Object property2 = ScriptableObject.getProperty(scriptable, "__noSuchMethod__");
                if (property2 instanceof Callable) {
                    property = new NoSuchMethodShim((Callable) property2, str);
                }
            }
            if (property instanceof Callable) {
                storeScriptable(context, scriptable);
                return (Callable) property;
            }
            throw notFunctionError(scriptable, property, str);
        }
        throw undefCallError(obj, str);
    }

    public static RegExpProxy getRegExpProxy(Context context) {
        return context.getRegExpProxy();
    }

    public static Scriptable getTopCallScope(Context context) {
        Scriptable scriptable = context.topCallScope;
        if (scriptable != null) {
            return scriptable;
        }
        throw new IllegalStateException();
    }

    public static Object getTopLevelProp(Scriptable scriptable, String str) {
        return ScriptableObject.getProperty(ScriptableObject.getTopLevelScope(scriptable), str);
    }

    public static String[] getTopPackageNames() {
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            return new String[]{"java", "javax", "org", "com", "edu", "net", "android"};
        }
        return new String[]{"java", "javax", "org", "com", "edu", "net"};
    }

    public static Callable getValueFunctionAndThis(Object obj, Context context) {
        Scriptable scriptable;
        if (obj instanceof Callable) {
            Callable callable = (Callable) obj;
            if (callable instanceof Scriptable) {
                scriptable = ((Scriptable) callable).getParentScope();
            } else {
                scriptable = null;
            }
            if (scriptable == null && (scriptable = context.topCallScope) == null) {
                throw new IllegalStateException();
            }
            if (scriptable.getParentScope() != null && !(scriptable instanceof NativeWith) && (scriptable instanceof NativeCall)) {
                scriptable = ScriptableObject.getTopLevelScope(scriptable);
            }
            storeScriptable(context, scriptable);
            return callable;
        }
        throw notFunctionError(obj);
    }

    public static boolean hasObjectElem(Scriptable scriptable, Object obj, Context context) {
        if (isSymbol(obj)) {
            return ScriptableObject.hasProperty(scriptable, (Symbol) obj);
        }
        StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, obj);
        String str = stringIdOrIndex.stringId;
        if (str == null) {
            return ScriptableObject.hasProperty(scriptable, stringIdOrIndex.index);
        }
        return ScriptableObject.hasProperty(scriptable, str);
    }

    public static boolean hasTopCall(Context context) {
        return context.topCallScope != null;
    }

    public static boolean in(Object obj, Object obj2, Context context) {
        if (obj2 instanceof Scriptable) {
            return hasObjectElem((Scriptable) obj2, obj, context);
        }
        throw typeError0("msg.in.not.object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005a, code lost:
        if (r4 <= r12) goto L_0x005c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long indexFromString(java.lang.String r12) {
        /*
            int r0 = r12.length()
            r1 = -1
            if (r0 <= 0) goto L_0x0068
            r3 = 0
            char r4 = r12.charAt(r3)
            r5 = 45
            r6 = 48
            r7 = 1
            if (r4 != r5) goto L_0x0020
            if (r0 <= r7) goto L_0x0020
            char r4 = r12.charAt(r7)
            if (r4 != r6) goto L_0x001d
            return r1
        L_0x001d:
            r5 = 1
            r8 = 1
            goto L_0x0022
        L_0x0020:
            r5 = 0
            r8 = 0
        L_0x0022:
            int r4 = r4 + -48
            if (r4 < 0) goto L_0x0068
            r9 = 9
            if (r4 > r9) goto L_0x0068
            if (r5 == 0) goto L_0x002f
            r10 = 11
            goto L_0x0031
        L_0x002f:
            r10 = 10
        L_0x0031:
            if (r0 > r10) goto L_0x0068
            int r10 = -r4
            int r8 = r8 + r7
            if (r10 == 0) goto L_0x004b
        L_0x0037:
            if (r8 == r0) goto L_0x004b
            char r4 = r12.charAt(r8)
            int r4 = r4 - r6
            if (r4 < 0) goto L_0x004b
            if (r4 > r9) goto L_0x004b
            int r3 = r10 * 10
            int r3 = r3 - r4
            int r8 = r8 + 1
            r11 = r10
            r10 = r3
            r3 = r11
            goto L_0x0037
        L_0x004b:
            if (r8 != r0) goto L_0x0068
            r12 = -214748364(0xfffffffff3333334, float:-1.4197688E31)
            if (r3 > r12) goto L_0x005c
            if (r3 != r12) goto L_0x0068
            if (r5 == 0) goto L_0x0059
            r12 = 8
            goto L_0x005a
        L_0x0059:
            r12 = 7
        L_0x005a:
            if (r4 > r12) goto L_0x0068
        L_0x005c:
            if (r5 == 0) goto L_0x005f
            goto L_0x0060
        L_0x005f:
            int r10 = -r10
        L_0x0060:
            long r0 = (long) r10
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r2
            return r0
        L_0x0068:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.indexFromString(java.lang.String):long");
    }

    public static void initFunction(Context context, Scriptable scriptable, NativeFunction nativeFunction, int i, boolean z) {
        if (i == 1) {
            String functionName = nativeFunction.getFunctionName();
            if (functionName != null && functionName.length() != 0) {
                if (!z) {
                    ScriptableObject.defineProperty(scriptable, functionName, nativeFunction, 4);
                } else {
                    scriptable.put(functionName, scriptable, (Object) nativeFunction);
                }
            }
        } else if (i == 3) {
            String functionName2 = nativeFunction.getFunctionName();
            if (functionName2 != null && functionName2.length() != 0) {
                while (scriptable instanceof NativeWith) {
                    scriptable = scriptable.getParentScope();
                }
                scriptable.put(functionName2, scriptable, (Object) nativeFunction);
            }
        } else {
            throw Kit.codeBug();
        }
    }

    public static ScriptableObject initSafeStandardObjects(Context context, ScriptableObject scriptableObject, boolean z) {
        boolean z2;
        if (scriptableObject == null) {
            scriptableObject = new NativeObject();
        }
        scriptableObject.associateValue(LIBRARY_SCOPE_KEY, scriptableObject);
        new ClassCache().associate(scriptableObject);
        BaseFunction.init(scriptableObject, z);
        NativeObject.init(scriptableObject, z);
        Scriptable objectPrototype = ScriptableObject.getObjectPrototype(scriptableObject);
        ScriptableObject.getClassPrototype(scriptableObject, "Function").setPrototype(objectPrototype);
        if (scriptableObject.getPrototype() == null) {
            scriptableObject.setPrototype(objectPrototype);
        }
        NativeError.init(scriptableObject, z);
        NativeGlobal.init(context, scriptableObject, z);
        NativeArray.init(scriptableObject, z);
        if (context.getOptimizationLevel() > 0) {
            NativeArray.setMaximumInitialCapacity(200000);
        }
        NativeString.init(scriptableObject, z);
        NativeBoolean.init(scriptableObject, z);
        NativeNumber.init(scriptableObject, z);
        NativeDate.init(scriptableObject, z);
        NativeMath.init(scriptableObject, z);
        NativeJSON.init(scriptableObject, z);
        NativeWith.init(scriptableObject, z);
        NativeCall.init(scriptableObject, z);
        NativeScript.init(scriptableObject, z);
        NativeIterator.init(context, scriptableObject, z);
        NativeArrayIterator.init(scriptableObject, z);
        NativeStringIterator.init(scriptableObject, z);
        if (!context.hasFeature(6) || context.getE4xImplementationFactory() == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        ScriptableObject scriptableObject2 = scriptableObject;
        boolean z3 = z;
        new LazilyLoadedCtor(scriptableObject2, "RegExp", "org.mozilla.javascript.regexp.NativeRegExp", z3, true);
        new LazilyLoadedCtor(scriptableObject2, "Continuation", "org.mozilla.javascript.NativeContinuation", z3, true);
        if (z2) {
            ScriptableObject scriptableObject3 = scriptableObject;
            String implementationClassName = context.getE4xImplementationFactory().getImplementationClassName();
            boolean z4 = z;
            new LazilyLoadedCtor(scriptableObject3, "XML", implementationClassName, z4, true);
            new LazilyLoadedCtor(scriptableObject3, "XMLList", implementationClassName, z4, true);
            new LazilyLoadedCtor(scriptableObject3, "Namespace", implementationClassName, z4, true);
            new LazilyLoadedCtor(scriptableObject3, "QName", implementationClassName, z4, true);
        }
        if ((context.getLanguageVersion() >= 180 && context.hasFeature(14)) || context.getLanguageVersion() >= 200) {
            ScriptableObject scriptableObject4 = scriptableObject;
            boolean z5 = z;
            new LazilyLoadedCtor(scriptableObject4, NativeArrayBuffer.CLASS_NAME, "org.mozilla.javascript.typedarrays.NativeArrayBuffer", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Int8Array", "org.mozilla.javascript.typedarrays.NativeInt8Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint8Array", "org.mozilla.javascript.typedarrays.NativeUint8Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint8ClampedArray", "org.mozilla.javascript.typedarrays.NativeUint8ClampedArray", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Int16Array", "org.mozilla.javascript.typedarrays.NativeInt16Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint16Array", "org.mozilla.javascript.typedarrays.NativeUint16Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Int32Array", "org.mozilla.javascript.typedarrays.NativeInt32Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint32Array", "org.mozilla.javascript.typedarrays.NativeUint32Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Float32Array", "org.mozilla.javascript.typedarrays.NativeFloat32Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, "Float64Array", "org.mozilla.javascript.typedarrays.NativeFloat64Array", z5, true);
            new LazilyLoadedCtor(scriptableObject4, NativeDataView.CLASS_NAME, "org.mozilla.javascript.typedarrays.NativeDataView", z5, true);
        }
        if (context.getLanguageVersion() >= 200) {
            NativeSymbol.init(context, scriptableObject, z);
            NativeCollectionIterator.init(scriptableObject, "Set Iterator", z);
            NativeCollectionIterator.init(scriptableObject, "Map Iterator", z);
            NativeMap.init(context, scriptableObject, z);
            NativeSet.init(context, scriptableObject, z);
            NativeWeakMap.init(scriptableObject, z);
            NativeWeakSet.init(scriptableObject, z);
        }
        if (scriptableObject instanceof TopLevel) {
            ((TopLevel) scriptableObject).cacheBuiltins(scriptableObject, z);
        }
        return scriptableObject;
    }

    public static void initScript(NativeFunction nativeFunction, Scriptable scriptable, Context context, Scriptable scriptable2, boolean z) {
        if (context.topCallScope != null) {
            int paramAndVarCount = nativeFunction.getParamAndVarCount();
            if (paramAndVarCount != 0) {
                Scriptable scriptable3 = scriptable2;
                while (scriptable3 instanceof NativeWith) {
                    scriptable3 = scriptable3.getParentScope();
                }
                while (true) {
                    int i = paramAndVarCount - 1;
                    if (paramAndVarCount != 0) {
                        String paramOrVarName = nativeFunction.getParamOrVarName(i);
                        boolean paramOrVarConst = nativeFunction.getParamOrVarConst(i);
                        if (ScriptableObject.hasProperty(scriptable2, paramOrVarName)) {
                            ScriptableObject.redefineProperty(scriptable2, paramOrVarName, paramOrVarConst);
                        } else if (paramOrVarConst) {
                            ScriptableObject.defineConstProperty(scriptable3, paramOrVarName);
                        } else if (z) {
                            scriptable3.put(paramOrVarName, scriptable3, Undefined.instance);
                        } else if (!(nativeFunction instanceof InterpretedFunction) || ((InterpretedFunction) nativeFunction).hasFunctionNamed(paramOrVarName)) {
                            ScriptableObject.defineProperty(scriptable3, paramOrVarName, Undefined.instance, 4);
                        }
                        paramAndVarCount = i;
                    } else {
                        return;
                    }
                }
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public static ScriptableObject initStandardObjects(Context context, ScriptableObject scriptableObject, boolean z) {
        ScriptableObject initSafeStandardObjects = initSafeStandardObjects(context, scriptableObject, z);
        ScriptableObject scriptableObject2 = initSafeStandardObjects;
        boolean z2 = z;
        new LazilyLoadedCtor(scriptableObject2, "Packages", "org.mozilla.javascript.NativeJavaTopPackage", z2, true);
        new LazilyLoadedCtor(scriptableObject2, "getClass", "org.mozilla.javascript.NativeJavaTopPackage", z2, true);
        new LazilyLoadedCtor(scriptableObject2, "JavaAdapter", "org.mozilla.javascript.JavaAdapter", z2, true);
        new LazilyLoadedCtor(scriptableObject2, "JavaImporter", "org.mozilla.javascript.ImporterTopLevel", z2, true);
        for (String lazilyLoadedCtor : getTopPackageNames()) {
            new LazilyLoadedCtor(initSafeStandardObjects, lazilyLoadedCtor, "org.mozilla.javascript.NativeJavaTopPackage", z, true);
        }
        return initSafeStandardObjects;
    }

    public static boolean instanceOf(Object obj, Object obj2, Context context) {
        if (!(obj2 instanceof Scriptable)) {
            throw typeError0("msg.instanceof.not.object");
        } else if (!(obj instanceof Scriptable)) {
            return false;
        } else {
            return ((Scriptable) obj2).hasInstance((Scriptable) obj);
        }
    }

    private static boolean isArrayLike(Scriptable scriptable) {
        if (scriptable == null || (!(scriptable instanceof NativeArray) && !(scriptable instanceof Arguments) && !ScriptableObject.hasProperty(scriptable, "length"))) {
            return false;
        }
        return true;
    }

    public static boolean isArrayObject(Object obj) {
        return (obj instanceof NativeArray) || (obj instanceof Arguments);
    }

    public static boolean isGeneratedScript(String str) {
        if (str.indexOf("(eval)") >= 0 || str.indexOf("(Function)") >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isIteratorDone(Context context, Object obj) {
        if (!(obj instanceof Scriptable)) {
            return false;
        }
        return toBoolean(getObjectProp((Scriptable) obj, ES6Iterator.DONE_PROPERTY, context));
    }

    public static boolean isJSLineTerminator(int i) {
        if ((57296 & i) != 0) {
            return false;
        }
        return i == 10 || i == 13 || i == 8232 || i == 8233;
    }

    public static boolean isJSWhitespaceOrLineTerminator(int i) {
        return isStrWhiteSpaceChar(i) || isJSLineTerminator(i);
    }

    public static boolean isNaN(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).isNaN();
        }
        if (obj instanceof Float) {
            return ((Float) obj).isNaN();
        }
        return false;
    }

    public static boolean isObject(Object obj) {
        if (obj == null || Undefined.instance.equals(obj)) {
            return false;
        }
        if (obj instanceof ScriptableObject) {
            String typeOf = ((ScriptableObject) obj).getTypeOf();
            if ("object".equals(typeOf) || "function".equals(typeOf)) {
                return true;
            }
            return false;
        } else if (obj instanceof Scriptable) {
            return !(obj instanceof Callable);
        } else {
            return false;
        }
    }

    public static boolean isPrimitive(Object obj) {
        return obj == null || obj == Undefined.instance || (obj instanceof Number) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static boolean isRhinoRuntimeType(Class<?> cls) {
        if (cls.isPrimitive()) {
            if (cls != Character.TYPE) {
                return true;
            }
            return false;
        } else if (cls == StringClass || cls == BooleanClass || NumberClass.isAssignableFrom(cls) || ScriptableClass.isAssignableFrom(cls)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSpecialProperty(String str) {
        if (str.equals("__proto__") || str.equals("__parent__")) {
            return true;
        }
        return false;
    }

    public static boolean isStrWhiteSpaceChar(int i) {
        if (i == 32 || i == 160 || i == 65279 || i == 8232 || i == 8233) {
            return true;
        }
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return true;
            default:
                return Character.getType(i) == 12;
        }
    }

    public static boolean isSymbol(Object obj) {
        return ((obj instanceof NativeSymbol) && ((NativeSymbol) obj).isSymbol()) || (obj instanceof SymbolKey);
    }

    public static boolean isValidIdentifierName(String str, Context context, boolean z) {
        int length = str.length();
        if (length == 0 || !Character.isJavaIdentifierStart(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i != length; i++) {
            if (!Character.isJavaIdentifierPart(str.charAt(i))) {
                return false;
            }
        }
        return !TokenStream.isKeyword(str, context.getLanguageVersion(), z);
    }

    private static boolean isVisible(Context context, Object obj) {
        ClassShutter classShutter = context.getClassShutter();
        if (classShutter == null || classShutter.visibleToScripts(obj.getClass().getName())) {
            return true;
        }
        return false;
    }

    public static boolean jsDelegatesTo(Scriptable scriptable, Scriptable scriptable2) {
        for (Scriptable prototype = scriptable.getPrototype(); prototype != null; prototype = prototype.getPrototype()) {
            if (prototype.equals(scriptable2)) {
                return true;
            }
        }
        return false;
    }

    public static Scriptable lastStoredScriptable(Context context) {
        Scriptable scriptable = context.scratchScriptable;
        context.scratchScriptable = null;
        return scriptable;
    }

    public static long lastUint32Result(Context context) {
        long j = context.scratchUint32;
        if ((j >>> 32) == 0) {
            return j;
        }
        throw new IllegalStateException();
    }

    public static Scriptable leaveDotQuery(Scriptable scriptable) {
        return ((NativeWith) scriptable).getParentScope();
    }

    public static Scriptable leaveWith(Scriptable scriptable) {
        return ((NativeWith) scriptable).getParentScope();
    }

    public static String makeUrlForGeneratedScript(boolean z, String str, int i) {
        if (z) {
            return str + '#' + i + "(eval)";
        }
        return str + '#' + i + "(Function)";
    }

    public static Ref memberRef(Object obj, Object obj2, Context context, int i) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).memberRef(context, obj2, i);
        }
        throw notXmlError(obj);
    }

    public static Object name(Context context, Scriptable scriptable, String str) {
        Scriptable parentScope = scriptable.getParentScope();
        if (parentScope != null) {
            return nameOrFunction(context, scriptable, parentScope, str, false);
        }
        Object obj = topScopeName(context, scriptable, str);
        if (obj != Scriptable.NOT_FOUND) {
            return obj;
        }
        throw notFoundError(scriptable, str);
    }

    @Deprecated
    public static Object nameIncrDecr(Scriptable scriptable, String str, int i) {
        return nameIncrDecr(scriptable, str, Context.getContext(), i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: org.mozilla.javascript.IdScriptableObject} */
    /* JADX WARNING: type inference failed for: r1v2, types: [org.mozilla.javascript.Scriptable] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0046, code lost:
        r6 = r2;
        r1 = r1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0075 A[LOOP:0: B:1:0x0002->B:39:0x0075, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x004e A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object nameOrFunction(org.mozilla.javascript.Context r5, org.mozilla.javascript.Scriptable r6, org.mozilla.javascript.Scriptable r7, java.lang.String r8, boolean r9) {
        /*
            r0 = 0
            r1 = r6
        L_0x0002:
            boolean r2 = r1 instanceof org.mozilla.javascript.NativeWith
            if (r2 == 0) goto L_0x0028
            org.mozilla.javascript.Scriptable r1 = r1.getPrototype()
            boolean r2 = r1 instanceof org.mozilla.javascript.xml.XMLObject
            if (r2 == 0) goto L_0x001f
            org.mozilla.javascript.xml.XMLObject r1 = (org.mozilla.javascript.xml.XMLObject) r1
            boolean r2 = r1.has((java.lang.String) r8, (org.mozilla.javascript.Scriptable) r1)
            if (r2 == 0) goto L_0x001b
            java.lang.Object r6 = r1.get((java.lang.String) r8, (org.mozilla.javascript.Scriptable) r1)
            goto L_0x0065
        L_0x001b:
            if (r0 != 0) goto L_0x0048
            r0 = r1
            goto L_0x0048
        L_0x001f:
            java.lang.Object r2 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r1, (java.lang.String) r8)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r2 == r3) goto L_0x0048
            goto L_0x0046
        L_0x0028:
            boolean r2 = r1 instanceof org.mozilla.javascript.NativeCall
            if (r2 == 0) goto L_0x003e
            java.lang.Object r1 = r1.get((java.lang.String) r8, (org.mozilla.javascript.Scriptable) r1)
            java.lang.Object r2 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r1 == r2) goto L_0x0048
            if (r9 == 0) goto L_0x003a
            org.mozilla.javascript.Scriptable r6 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r7)
        L_0x003a:
            r4 = r1
            r1 = r6
            r6 = r4
            goto L_0x0065
        L_0x003e:
            java.lang.Object r2 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r1, (java.lang.String) r8)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r2 == r3) goto L_0x0048
        L_0x0046:
            r6 = r2
            goto L_0x0065
        L_0x0048:
            org.mozilla.javascript.Scriptable r1 = r7.getParentScope()
            if (r1 != 0) goto L_0x0075
            java.lang.Object r6 = topScopeName(r5, r7, r8)
            java.lang.Object r1 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r6 != r1) goto L_0x0064
            if (r0 == 0) goto L_0x005f
            if (r9 != 0) goto L_0x005f
            java.lang.Object r6 = r0.get((java.lang.String) r8, (org.mozilla.javascript.Scriptable) r0)
            goto L_0x0064
        L_0x005f:
            java.lang.RuntimeException r5 = notFoundError(r7, r8)
            throw r5
        L_0x0064:
            r1 = r7
        L_0x0065:
            if (r9 == 0) goto L_0x0074
            boolean r7 = r6 instanceof org.mozilla.javascript.Callable
            if (r7 == 0) goto L_0x006f
            storeScriptable(r5, r1)
            goto L_0x0074
        L_0x006f:
            java.lang.RuntimeException r5 = notFunctionError(r6, r8)
            throw r5
        L_0x0074:
            return r6
        L_0x0075:
            r4 = r1
            r1 = r7
            r7 = r4
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.nameOrFunction(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.String, boolean):java.lang.Object");
    }

    public static Ref nameRef(Object obj, Context context, Scriptable scriptable, int i) {
        return currentXMLLib(context).nameRef(context, obj, scriptable, i);
    }

    public static Scriptable newArrayLiteral(Object[] objArr, int[] iArr, Context context, Scriptable scriptable) {
        int i;
        int length = objArr.length;
        int i2 = 0;
        if (iArr != null) {
            i = iArr.length;
        } else {
            i = 0;
        }
        int i3 = length + i;
        if (i3 <= 1 || i * 2 >= i3) {
            Scriptable newArray = context.newArray(scriptable, i3);
            int i4 = 0;
            int i5 = 0;
            while (i2 != i3) {
                if (i4 == i || iArr[i4] != i2) {
                    newArray.put(i2, newArray, objArr[i5]);
                    i5++;
                } else {
                    i4++;
                }
                i2++;
            }
            return newArray;
        }
        if (i != 0) {
            Object[] objArr2 = new Object[i3];
            int i6 = 0;
            int i7 = 0;
            while (i2 != i3) {
                if (i6 == i || iArr[i6] != i2) {
                    objArr2[i2] = objArr[i7];
                    i7++;
                } else {
                    objArr2[i2] = Scriptable.NOT_FOUND;
                    i6++;
                }
                i2++;
            }
            objArr = objArr2;
        }
        return context.newArray(scriptable, objArr);
    }

    public static Scriptable newBuiltinObject(Context context, Scriptable scriptable, TopLevel.Builtins builtins, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function builtinCtor = TopLevel.getBuiltinCtor(context, topLevelScope, builtins);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return builtinCtor.construct(context, topLevelScope, objArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.mozilla.javascript.Scriptable newCatchScope(java.lang.Throwable r11, org.mozilla.javascript.Scriptable r12, java.lang.String r13, org.mozilla.javascript.Context r14, org.mozilla.javascript.Scriptable r15) {
        /*
            boolean r0 = r11 instanceof org.mozilla.javascript.JavaScriptException
            r1 = 0
            if (r0 == 0) goto L_0x000e
            r12 = r11
            org.mozilla.javascript.JavaScriptException r12 = (org.mozilla.javascript.JavaScriptException) r12
            java.lang.Object r12 = r12.getValue()
            goto L_0x00e6
        L_0x000e:
            r0 = 1
            if (r12 == 0) goto L_0x001f
            org.mozilla.javascript.NativeObject r12 = (org.mozilla.javascript.NativeObject) r12
            java.lang.Object r12 = r12.getAssociatedValue(r11)
            if (r12 != 0) goto L_0x001c
            org.mozilla.javascript.Kit.codeBug()
        L_0x001c:
            r1 = 1
            goto L_0x00e6
        L_0x001f:
            boolean r12 = r11 instanceof org.mozilla.javascript.EcmaError
            r2 = 0
            if (r12 == 0) goto L_0x0036
            r12 = r11
            org.mozilla.javascript.EcmaError r12 = (org.mozilla.javascript.EcmaError) r12
            java.lang.String r3 = r12.getName()
            org.mozilla.javascript.TopLevel$NativeErrors r3 = org.mozilla.javascript.TopLevel.NativeErrors.valueOf(r3)
            java.lang.String r4 = r12.getErrorMessage()
        L_0x0033:
            r5 = r4
            r4 = r2
            goto L_0x0089
        L_0x0036:
            boolean r12 = r11 instanceof org.mozilla.javascript.WrappedException
            if (r12 == 0) goto L_0x0067
            r12 = r11
            org.mozilla.javascript.WrappedException r12 = (org.mozilla.javascript.WrappedException) r12
            java.lang.Throwable r3 = r12.getWrappedException()
            org.mozilla.javascript.TopLevel$NativeErrors r4 = org.mozilla.javascript.TopLevel.NativeErrors.JavaException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r6 = r3.getClass()
            java.lang.String r6 = r6.getName()
            r5.append(r6)
            java.lang.String r6 = ": "
            r5.append(r6)
            java.lang.String r6 = r3.getMessage()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r10 = r4
            r4 = r3
            r3 = r10
            goto L_0x0089
        L_0x0067:
            boolean r12 = r11 instanceof org.mozilla.javascript.EvaluatorException
            if (r12 == 0) goto L_0x0075
            r12 = r11
            org.mozilla.javascript.EvaluatorException r12 = (org.mozilla.javascript.EvaluatorException) r12
            org.mozilla.javascript.TopLevel$NativeErrors r3 = org.mozilla.javascript.TopLevel.NativeErrors.InternalError
            java.lang.String r4 = r12.getMessage()
            goto L_0x0033
        L_0x0075:
            r12 = 13
            boolean r12 = r14.hasFeature(r12)
            if (r12 == 0) goto L_0x0105
            org.mozilla.javascript.WrappedException r12 = new org.mozilla.javascript.WrappedException
            r12.<init>(r11)
            org.mozilla.javascript.TopLevel$NativeErrors r3 = org.mozilla.javascript.TopLevel.NativeErrors.JavaException
            java.lang.String r4 = r11.toString()
            goto L_0x0033
        L_0x0089:
            java.lang.String r6 = r12.sourceName()
            if (r6 != 0) goto L_0x0091
            java.lang.String r6 = ""
        L_0x0091:
            int r7 = r12.lineNumber()
            r8 = 2
            if (r7 <= 0) goto L_0x00a6
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r1] = r5
            r9[r0] = r6
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)
            r9[r8] = r1
            goto L_0x00ac
        L_0x00a6:
            java.lang.Object[] r9 = new java.lang.Object[r8]
            r9[r1] = r5
            r9[r0] = r6
        L_0x00ac:
            org.mozilla.javascript.Scriptable r1 = newNativeError(r14, r15, r3, r9)
            boolean r3 = r1 instanceof org.mozilla.javascript.NativeError
            if (r3 == 0) goto L_0x00ba
            r3 = r1
            org.mozilla.javascript.NativeError r3 = (org.mozilla.javascript.NativeError) r3
            r3.setStackProvider(r12)
        L_0x00ba:
            r3 = 7
            if (r4 == 0) goto L_0x00d0
            boolean r5 = isVisible(r14, r4)
            if (r5 == 0) goto L_0x00d0
            org.mozilla.javascript.WrapFactory r5 = r14.getWrapFactory()
            java.lang.Object r4 = r5.wrap(r14, r15, r4, r2)
            java.lang.String r5 = "javaException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r1, r5, r4, r3)
        L_0x00d0:
            boolean r4 = isVisible(r14, r12)
            if (r4 == 0) goto L_0x00e3
            org.mozilla.javascript.WrapFactory r4 = r14.getWrapFactory()
            java.lang.Object r12 = r4.wrap(r14, r15, r12, r2)
            java.lang.String r2 = "rhinoException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r1, r2, r12, r3)
        L_0x00e3:
            r12 = r1
            goto L_0x001c
        L_0x00e6:
            org.mozilla.javascript.NativeObject r0 = new org.mozilla.javascript.NativeObject
            r0.<init>()
            r2 = 4
            r0.defineProperty((java.lang.String) r13, (java.lang.Object) r12, (int) r2)
            boolean r13 = isVisible(r14, r11)
            if (r13 == 0) goto L_0x00ff
            java.lang.Object r13 = org.mozilla.javascript.Context.javaToJS(r11, r15)
            r14 = 6
            java.lang.String r15 = "__exception__"
            r0.defineProperty((java.lang.String) r15, (java.lang.Object) r13, (int) r14)
        L_0x00ff:
            if (r1 == 0) goto L_0x0104
            r0.associateValue(r11, r12)
        L_0x0104:
            return r0
        L_0x0105:
            java.lang.RuntimeException r11 = org.mozilla.javascript.Kit.codeBug()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.newCatchScope(java.lang.Throwable, org.mozilla.javascript.Scriptable, java.lang.String, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable):org.mozilla.javascript.Scriptable");
    }

    public static Scriptable newNativeError(Context context, Scriptable scriptable, TopLevel.NativeErrors nativeErrors, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function nativeErrorCtor = TopLevel.getNativeErrorCtor(context, topLevelScope, nativeErrors);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return nativeErrorCtor.construct(context, topLevelScope, objArr);
    }

    public static Scriptable newObject(Context context, Scriptable scriptable, String str, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function existingCtor = getExistingCtor(context, topLevelScope, str);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return existingCtor.construct(context, topLevelScope, objArr);
    }

    @Deprecated
    public static Scriptable newObjectLiteral(Object[] objArr, Object[] objArr2, Context context, Scriptable scriptable) {
        return newObjectLiteral(objArr, objArr2, (int[]) null, context, scriptable);
    }

    public static Object newSpecial(Context context, Object obj, Object[] objArr, Scriptable scriptable, int i) {
        if (i == 1) {
            if (NativeGlobal.isEvalFunction(obj)) {
                throw typeError1("msg.not.ctor", "eval");
            }
        } else if (i != 2) {
            throw Kit.codeBug();
        } else if (NativeWith.isWithFunction(obj)) {
            return NativeWith.newWithSpecial(context, scriptable, objArr);
        }
        return newObject(obj, context, scriptable, objArr);
    }

    public static RuntimeException notFoundError(Scriptable scriptable, String str) {
        throw constructError("ReferenceError", getMessage1("msg.is.not.defined", str));
    }

    public static RuntimeException notFunctionError(Object obj) {
        return notFunctionError(obj, obj);
    }

    private static RuntimeException notXmlError(Object obj) {
        throw typeError1("msg.isnt.xml.object", toString(obj));
    }

    public static String numberToString(double d, int i) {
        if (i < 2 || i > 36) {
            throw Context.reportRuntimeError1("msg.bad.radix", Integer.toString(i));
        } else if (Double.isNaN(d)) {
            return "NaN";
        } else {
            if (d == Double.POSITIVE_INFINITY) {
                return "Infinity";
            }
            if (d == Double.NEGATIVE_INFINITY) {
                return "-Infinity";
            }
            if (d == 0.0d) {
                return "0";
            }
            if (i != 10) {
                return DToA.JS_dtobasestr(i, d);
            }
            String numberToString = FastDtoa.numberToString(d);
            if (numberToString != null) {
                return numberToString;
            }
            StringBuilder sb = new StringBuilder();
            DToA.JS_dtostr(sb, 0, 0, d);
            return sb.toString();
        }
    }

    public static Object[] padArguments(Object[] objArr, int i) {
        if (i < objArr.length) {
            return objArr;
        }
        Object[] objArr2 = new Object[i];
        System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        if (objArr.length < i) {
            Arrays.fill(objArr2, objArr.length, i, Undefined.instance);
        }
        return objArr2;
    }

    @Deprecated
    public static Object propIncrDecr(Object obj, String str, Context context, int i) {
        return propIncrDecr(obj, str, context, getTopCallScope(context), i);
    }

    public static EcmaError rangeError(String str) {
        return constructError("RangeError", str);
    }

    public static Object refDel(Ref ref, Context context) {
        return wrapBoolean(ref.delete(context));
    }

    public static Object refGet(Ref ref, Context context) {
        return ref.get(context);
    }

    @Deprecated
    public static Object refIncrDecr(Ref ref, Context context, int i) {
        return refIncrDecr(ref, context, getTopCallScope(context), i);
    }

    @Deprecated
    public static Object refSet(Ref ref, Object obj, Context context) {
        return refSet(ref, obj, context, getTopCallScope(context));
    }

    public static boolean same(Object obj, Object obj2) {
        if (!typeof(obj).equals(typeof(obj2))) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return eq(obj, obj2);
        }
        if (!isNaN(obj) || !isNaN(obj2)) {
            return obj.equals(obj2);
        }
        return true;
    }

    public static boolean sameZero(Object obj, Object obj2) {
        if (!typeof(obj).equals(typeof(obj2))) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return eq(obj, obj2);
        }
        if (isNaN(obj) && isNaN(obj2)) {
            return true;
        }
        double doubleValue = ((Number) obj).doubleValue();
        if (obj2 instanceof Number) {
            double doubleValue2 = ((Number) obj2).doubleValue();
            double d = negativeZero;
            if ((doubleValue == d && doubleValue2 == 0.0d) || (doubleValue == 0.0d && doubleValue2 == d)) {
                return true;
            }
        }
        return eqNumber(doubleValue, obj2);
    }

    public static Object searchDefaultNamespace(Context context) {
        Scriptable scriptable = context.currentActivationCall;
        if (scriptable == null) {
            scriptable = getTopCallScope(context);
        }
        while (true) {
            Scriptable parentScope = scriptable.getParentScope();
            if (parentScope == null) {
                Object property = ScriptableObject.getProperty(scriptable, DEFAULT_NS_TAG);
                if (property == Scriptable.NOT_FOUND) {
                    return null;
                }
                return property;
            }
            Object obj = scriptable.get(DEFAULT_NS_TAG, scriptable);
            if (obj != Scriptable.NOT_FOUND) {
                return obj;
            }
            scriptable = parentScope;
        }
    }

    public static void setBuiltinProtoAndParent(ScriptableObject scriptableObject, Scriptable scriptable, TopLevel.Builtins builtins) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        scriptableObject.setParentScope(topLevelScope);
        scriptableObject.setPrototype(TopLevel.getBuiltinPrototype(topLevelScope, builtins));
    }

    public static Object setConst(Scriptable scriptable, Object obj, Context context, String str) {
        if (scriptable instanceof XMLObject) {
            scriptable.put(str, scriptable, obj);
        } else {
            ScriptableObject.putConstProperty(scriptable, str, obj);
        }
        return obj;
    }

    public static Object setDefaultNamespace(Object obj, Context context) {
        Scriptable scriptable = context.currentActivationCall;
        if (scriptable == null) {
            scriptable = getTopCallScope(context);
        }
        Object defaultXmlNamespace = currentXMLLib(context).toDefaultXmlNamespace(context, obj);
        if (!scriptable.has(DEFAULT_NS_TAG, scriptable)) {
            ScriptableObject.defineProperty(scriptable, DEFAULT_NS_TAG, defaultXmlNamespace, 6);
        } else {
            scriptable.put(DEFAULT_NS_TAG, scriptable, defaultXmlNamespace);
        }
        return Undefined.instance;
    }

    public static void setEnumNumbers(Object obj, boolean z) {
        ((IdEnumeration) obj).enumNumbers = z;
    }

    public static void setFunctionProtoAndParent(BaseFunction baseFunction, Scriptable scriptable) {
        setFunctionProtoAndParent(baseFunction, scriptable, false);
    }

    public static Object setName(Scriptable scriptable, Object obj, Context context, Scriptable scriptable2, String str) {
        if (scriptable != null) {
            ScriptableObject.putProperty(scriptable, str, obj);
        } else {
            if (context.hasFeature(11) || context.hasFeature(8)) {
                Context.reportWarning(getMessage1("msg.assn.create.strict", str));
            }
            Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable2);
            if (context.useDynamicScope) {
                topLevelScope = checkDynamicScope(context.topCallScope, topLevelScope);
            }
            topLevelScope.put(str, topLevelScope, obj);
        }
        return obj;
    }

    @Deprecated
    public static Object setObjectElem(Object obj, Object obj2, Object obj3, Context context) {
        return setObjectElem(obj, obj2, obj3, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object setObjectIndex(Object obj, double d, Object obj2, Context context) {
        return setObjectIndex(obj, d, obj2, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object setObjectProp(Object obj, String str, Object obj2, Context context) {
        return setObjectProp(obj, str, obj2, context, getTopCallScope(context));
    }

    public static void setObjectProtoAndParent(ScriptableObject scriptableObject, Scriptable scriptable) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        scriptableObject.setParentScope(topLevelScope);
        scriptableObject.setPrototype(ScriptableObject.getClassPrototype(topLevelScope, scriptableObject.getClassName()));
    }

    public static void setRegExpProxy(Context context, RegExpProxy regExpProxy) {
        if (regExpProxy != null) {
            context.regExpProxy = regExpProxy;
            return;
        }
        throw new IllegalArgumentException();
    }

    public static boolean shallowEq(Object obj, Object obj2) {
        if (obj == obj2) {
            if (!(obj instanceof Number)) {
                return true;
            }
            return !Double.isNaN(((Number) obj).doubleValue());
        } else if (obj == null || obj == Undefined.instance || obj == Undefined.SCRIPTABLE_UNDEFINED) {
            Object obj3 = Undefined.instance;
            if ((obj == obj3 && obj2 == Undefined.SCRIPTABLE_UNDEFINED) || (obj == Undefined.SCRIPTABLE_UNDEFINED && obj2 == obj3)) {
                return true;
            }
            return false;
        } else {
            if (obj instanceof Number) {
                if (!(obj2 instanceof Number) || ((Number) obj).doubleValue() != ((Number) obj2).doubleValue()) {
                    return false;
                }
                return true;
            } else if (obj instanceof CharSequence) {
                if (obj2 instanceof CharSequence) {
                    return obj.toString().equals(obj2.toString());
                }
            } else if (obj instanceof Boolean) {
                if (obj2 instanceof Boolean) {
                    return obj.equals(obj2);
                }
            } else if (!(obj instanceof Scriptable)) {
                warnAboutNonJSObject(obj);
                if (obj == obj2) {
                    return true;
                }
                return false;
            } else if (!(obj instanceof Wrapper) || !(obj2 instanceof Wrapper) || ((Wrapper) obj).unwrap() != ((Wrapper) obj2).unwrap()) {
                return false;
            } else {
                return true;
            }
            return false;
        }
    }

    @Deprecated
    public static Ref specialRef(Object obj, String str, Context context) {
        return specialRef(obj, str, context, getTopCallScope(context));
    }

    private static void storeScriptable(Context context, Scriptable scriptable) {
        if (context.scratchScriptable == null) {
            context.scratchScriptable = scriptable;
            return;
        }
        throw new IllegalStateException();
    }

    public static void storeUint32Result(Context context, long j) {
        if ((j >>> 32) == 0) {
            context.scratchUint32 = j;
            return;
        }
        throw new IllegalArgumentException();
    }

    public static Object strictSetName(Scriptable scriptable, Object obj, Context context, Scriptable scriptable2, String str) {
        if (scriptable != null) {
            ScriptableObject.putProperty(scriptable, str, obj);
            return obj;
        }
        throw constructError("ReferenceError", "Assignment to undefined \"" + str + "\" in strict mode");
    }

    public static double stringPrefixToNumber(String str, int i, int i2) {
        return stringToNumber(str, i, str.length() - 1, i2, true);
    }

    public static double stringToNumber(String str, int i, int i2, int i3) {
        return stringToNumber(str, i, i2, i3, false);
    }

    public static long testUint32String(String str) {
        int length = str.length();
        if (1 <= length && length <= 10) {
            int charAt = str.charAt(0) - '0';
            if (charAt == 0) {
                if (length == 1) {
                    return 0;
                }
                return -1;
            } else if (1 <= charAt && charAt <= 9) {
                long j = (long) charAt;
                for (int i = 1; i != length; i++) {
                    int charAt2 = str.charAt(i) - '0';
                    if (charAt2 < 0 || charAt2 > 9) {
                        return -1;
                    }
                    j = (j * 10) + ((long) charAt2);
                }
                if ((j >>> 32) == 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    public static JavaScriptException throwCustomError(Context context, Scriptable scriptable, String str, String str2) {
        int[] iArr = {0};
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        return new JavaScriptException(context.newObject(scriptable, str, new Object[]{str2, sourcePositionFromStack, Integer.valueOf(iArr[0])}), sourcePositionFromStack, iArr[0]);
    }

    public static JavaScriptException throwError(Context context, Scriptable scriptable, String str) {
        int[] iArr = {0};
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        return new JavaScriptException(newBuiltinObject(context, scriptable, TopLevel.Builtins.Error, new Object[]{str, sourcePositionFromStack, Integer.valueOf(iArr[0])}), sourcePositionFromStack, iArr[0]);
    }

    public static boolean toBoolean(Object obj) {
        while (!(obj instanceof Boolean)) {
            if (obj == null || obj == Undefined.instance) {
                return false;
            }
            if (obj instanceof CharSequence) {
                if (((CharSequence) obj).length() != 0) {
                    return true;
                }
                return false;
            } else if (obj instanceof Number) {
                double doubleValue = ((Number) obj).doubleValue();
                if (Double.isNaN(doubleValue) || doubleValue == 0.0d) {
                    return false;
                }
                return true;
            } else if (!(obj instanceof Scriptable)) {
                warnAboutNonJSObject(obj);
                return true;
            } else if ((obj instanceof ScriptableObject) && ((ScriptableObject) obj).avoidObjectDetection()) {
                return false;
            } else {
                if (Context.getContext().isVersionECMA1()) {
                    return true;
                }
                obj = ((Scriptable) obj).getDefaultValue(BooleanClass);
                if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                    throw errorWithClassName("msg.primitive.expected", obj);
                }
            }
        }
        return ((Boolean) obj).booleanValue();
    }

    public static CharSequence toCharSequence(Object obj) {
        if (obj instanceof NativeString) {
            return ((NativeString) obj).toCharSequence();
        }
        if (obj instanceof CharSequence) {
            return (CharSequence) obj;
        }
        return toString(obj);
    }

    public static int toInt32(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return toInt32(toNumber(obj));
    }

    public static double toInteger(Object obj) {
        return toInteger(toNumber(obj));
    }

    public static Scriptable toIterator(Context context, Scriptable scriptable, Scriptable scriptable2, boolean z) {
        Boolean bool;
        if (!ScriptableObject.hasProperty(scriptable2, NativeIterator.ITERATOR_PROPERTY_NAME)) {
            return null;
        }
        Object property = ScriptableObject.getProperty(scriptable2, NativeIterator.ITERATOR_PROPERTY_NAME);
        if (property instanceof Callable) {
            Callable callable = (Callable) property;
            Object[] objArr = new Object[1];
            if (z) {
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            objArr[0] = bool;
            Object call = callable.call(context, scriptable, scriptable2, objArr);
            if (call instanceof Scriptable) {
                return (Scriptable) call;
            }
            throw typeError0("msg.iterator.primitive");
        }
        throw typeError0("msg.invalid.iterator");
    }

    public static long toLength(Object[] objArr, int i) {
        double integer = toInteger(objArr, i);
        if (integer <= 0.0d) {
            return 0;
        }
        return (long) Math.min(integer, 9.007199254740991E15d);
    }

    public static double toNumber(Object obj) {
        while (!(obj instanceof Number)) {
            if (obj == null) {
                return 0.0d;
            }
            if (obj == Undefined.instance) {
                return Double.NaN;
            }
            if (obj instanceof String) {
                return toNumber((String) obj);
            }
            if (obj instanceof CharSequence) {
                return toNumber(obj.toString());
            }
            if (obj instanceof Boolean) {
                if (((Boolean) obj).booleanValue()) {
                    return 1.0d;
                }
                return 0.0d;
            } else if (obj instanceof Symbol) {
                throw typeError0("msg.not.a.number");
            } else if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue(NumberClass);
                if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                    throw errorWithClassName("msg.primitive.expected", obj);
                }
            } else {
                warnAboutNonJSObject(obj);
                return Double.NaN;
            }
        }
        return ((Number) obj).doubleValue();
    }

    public static Scriptable toObject(Scriptable scriptable, Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        return toObject(Context.getContext(), scriptable, obj);
    }

    @Deprecated
    public static Scriptable toObjectOrNull(Context context, Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj == null || obj == Undefined.instance) {
            return null;
        }
        return toObject(context, getTopCallScope(context), obj);
    }

    public static Object toPrimitive(Object obj) {
        return toPrimitive(obj, (Class<?>) null);
    }

    public static String toString(Object obj) {
        while (obj != null) {
            if (obj == Undefined.instance || obj == Undefined.SCRIPTABLE_UNDEFINED) {
                return "undefined";
            }
            if (obj instanceof String) {
                return (String) obj;
            }
            if (obj instanceof CharSequence) {
                return obj.toString();
            }
            if (obj instanceof Number) {
                return numberToString(((Number) obj).doubleValue(), 10);
            }
            if (obj instanceof Symbol) {
                throw typeError0("msg.not.a.string");
            } else if (!(obj instanceof Scriptable)) {
                return obj.toString();
            } else {
                obj = ((Scriptable) obj).getDefaultValue(StringClass);
                if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                    throw errorWithClassName("msg.primitive.expected", obj);
                }
            }
        }
        return "null";
    }

    public static StringIdOrIndex toStringIdOrIndex(Context context, Object obj) {
        String str;
        if (obj instanceof Number) {
            double doubleValue = ((Number) obj).doubleValue();
            int i = (int) doubleValue;
            if (((double) i) == doubleValue) {
                return new StringIdOrIndex(i);
            }
            return new StringIdOrIndex(toString(obj));
        }
        if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = toString(obj);
        }
        long indexFromString = indexFromString(str);
        if (indexFromString >= 0) {
            return new StringIdOrIndex((int) indexFromString);
        }
        return new StringIdOrIndex(str);
    }

    public static char toUint16(Object obj) {
        return (char) DoubleConversion.doubleToInt32(toNumber(obj));
    }

    public static long toUint32(double d) {
        return ((long) DoubleConversion.doubleToInt32(d)) & 4294967295L;
    }

    private static Object topScopeName(Context context, Scriptable scriptable, String str) {
        if (context.useDynamicScope) {
            scriptable = checkDynamicScope(context.topCallScope, scriptable);
        }
        return ScriptableObject.getProperty(scriptable, str);
    }

    public static EcmaError typeError(String str) {
        return constructError("TypeError", str);
    }

    public static EcmaError typeError0(String str) {
        return typeError(getMessage0(str));
    }

    public static EcmaError typeError1(String str, Object obj) {
        return typeError(getMessage1(str, obj));
    }

    public static EcmaError typeError2(String str, Object obj, Object obj2) {
        return typeError(getMessage2(str, obj, obj2));
    }

    public static EcmaError typeError3(String str, String str2, String str3, String str4) {
        return typeError(getMessage3(str, str2, str3, str4));
    }

    @Deprecated
    public static BaseFunction typeErrorThrower() {
        return typeErrorThrower(Context.getCurrentContext());
    }

    public static String typeof(Object obj) {
        if (obj == null) {
            return "object";
        }
        if (obj == Undefined.instance) {
            return "undefined";
        }
        if (obj instanceof ScriptableObject) {
            return ((ScriptableObject) obj).getTypeOf();
        }
        if (obj instanceof Scriptable) {
            if (obj instanceof Callable) {
                return "function";
            }
            return "object";
        } else if (obj instanceof CharSequence) {
            return "string";
        } else {
            if (obj instanceof Number) {
                return "number";
            }
            if (obj instanceof Boolean) {
                return "boolean";
            }
            throw errorWithClassName("msg.invalid.type", obj);
        }
    }

    public static String typeofName(Scriptable scriptable, String str) {
        Context context = Context.getContext();
        Scriptable bind = bind(context, scriptable, str);
        if (bind == null) {
            return "undefined";
        }
        return typeof(getObjectProp(bind, str, context));
    }

    public static RuntimeException undefCallError(Object obj, Object obj2) {
        return typeError2("msg.undef.method.call", toString(obj), toString(obj2));
    }

    private static RuntimeException undefDeleteError(Object obj, Object obj2) {
        throw typeError2("msg.undef.prop.delete", toString(obj), toString(obj2));
    }

    public static RuntimeException undefReadError(Object obj, Object obj2) {
        return typeError2("msg.undef.prop.read", toString(obj), toString(obj2));
    }

    public static RuntimeException undefWriteError(Object obj, Object obj2, Object obj3) {
        return typeError3("msg.undef.prop.write", toString(obj), toString(obj2), toString(obj3));
    }

    public static String uneval(Context context, Scriptable scriptable, Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj == Undefined.instance) {
            return "undefined";
        }
        if (obj instanceof CharSequence) {
            String escapeString = escapeString(obj.toString());
            StringBuilder sb = new StringBuilder(escapeString.length() + 2);
            sb.append('\"');
            sb.append(escapeString);
            sb.append('\"');
            return sb.toString();
        } else if (obj instanceof Number) {
            double doubleValue = ((Number) obj).doubleValue();
            if (doubleValue != 0.0d || 1.0d / doubleValue >= 0.0d) {
                return toString(doubleValue);
            }
            return "-0";
        } else if (obj instanceof Boolean) {
            return toString(obj);
        } else {
            if (obj instanceof Scriptable) {
                Scriptable scriptable2 = (Scriptable) obj;
                if (ScriptableObject.hasProperty(scriptable2, "toSource")) {
                    Object property = ScriptableObject.getProperty(scriptable2, "toSource");
                    if (property instanceof Function) {
                        return toString(((Function) property).call(context, scriptable, scriptable2, emptyArgs));
                    }
                }
                return toString(obj);
            }
            warnAboutNonJSObject(obj);
            return obj.toString();
        }
    }

    public static Object updateDotQuery(boolean z, Scriptable scriptable) {
        return ((NativeWith) scriptable).updateDotQuery(z);
    }

    private static void warnAboutNonJSObject(Object obj) {
        if (!"true".equals(getMessage0("params.omit.non.js.object.warning"))) {
            Context.reportWarning(getMessage2("msg.non.js.object.warning", obj, obj.getClass().getName()));
            System.err.getClass();
        }
    }

    public static Boolean wrapBoolean(boolean z) {
        return Boolean.valueOf(z);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: org.mozilla.javascript.WrappedException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: org.mozilla.javascript.EvaluatorException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: org.mozilla.javascript.EvaluatorException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: org.mozilla.javascript.WrappedException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: org.mozilla.javascript.EvaluatorException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v15, resolved type: org.mozilla.javascript.EvaluatorException} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.mozilla.javascript.Scriptable wrapException(java.lang.Throwable r11, org.mozilla.javascript.Scriptable r12, org.mozilla.javascript.Context r13) {
        /*
            boolean r0 = r11 instanceof org.mozilla.javascript.EcmaError
            r1 = 0
            if (r0 == 0) goto L_0x0012
            org.mozilla.javascript.EcmaError r11 = (org.mozilla.javascript.EcmaError) r11
            java.lang.String r0 = r11.getName()
            java.lang.String r2 = r11.getErrorMessage()
        L_0x000f:
            r3 = r2
        L_0x0010:
            r2 = r1
            goto L_0x0064
        L_0x0012:
            boolean r0 = r11 instanceof org.mozilla.javascript.WrappedException
            java.lang.String r2 = "JavaException"
            if (r0 == 0) goto L_0x0042
            org.mozilla.javascript.WrappedException r11 = (org.mozilla.javascript.WrappedException) r11
            java.lang.Throwable r0 = r11.getWrappedException()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = ": "
            r3.append(r4)
            java.lang.String r4 = r0.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r10 = r2
            r2 = r0
            r0 = r10
            goto L_0x0064
        L_0x0042:
            boolean r0 = r11 instanceof org.mozilla.javascript.EvaluatorException
            if (r0 == 0) goto L_0x004f
            org.mozilla.javascript.EvaluatorException r11 = (org.mozilla.javascript.EvaluatorException) r11
            java.lang.String r2 = r11.getMessage()
            java.lang.String r0 = "InternalError"
            goto L_0x000f
        L_0x004f:
            r0 = 13
            boolean r0 = r13.hasFeature(r0)
            if (r0 == 0) goto L_0x00c6
            org.mozilla.javascript.WrappedException r0 = new org.mozilla.javascript.WrappedException
            r0.<init>(r11)
            java.lang.String r11 = r11.toString()
            r3 = r11
            r11 = r0
            r0 = r2
            goto L_0x0010
        L_0x0064:
            java.lang.String r4 = r11.sourceName()
            if (r4 != 0) goto L_0x006c
            java.lang.String r4 = ""
        L_0x006c:
            int r5 = r11.lineNumber()
            r6 = 1
            r7 = 0
            r8 = 2
            if (r5 <= 0) goto L_0x0083
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r7] = r3
            r9[r6] = r4
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
            r9[r8] = r3
            goto L_0x0089
        L_0x0083:
            java.lang.Object[] r9 = new java.lang.Object[r8]
            r9[r7] = r3
            r9[r6] = r4
        L_0x0089:
            org.mozilla.javascript.Scriptable r3 = r13.newObject(r12, r0, r9)
            java.lang.String r4 = "name"
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r4, (java.lang.Object) r0)
            boolean r0 = r3 instanceof org.mozilla.javascript.NativeError
            if (r0 == 0) goto L_0x009c
            r0 = r3
            org.mozilla.javascript.NativeError r0 = (org.mozilla.javascript.NativeError) r0
            r0.setStackProvider(r11)
        L_0x009c:
            r0 = 7
            if (r2 == 0) goto L_0x00b2
            boolean r4 = isVisible(r13, r2)
            if (r4 == 0) goto L_0x00b2
            org.mozilla.javascript.WrapFactory r4 = r13.getWrapFactory()
            java.lang.Object r2 = r4.wrap(r13, r12, r2, r1)
            java.lang.String r4 = "javaException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r3, r4, r2, r0)
        L_0x00b2:
            boolean r2 = isVisible(r13, r11)
            if (r2 == 0) goto L_0x00c5
            org.mozilla.javascript.WrapFactory r2 = r13.getWrapFactory()
            java.lang.Object r11 = r2.wrap(r13, r12, r11, r1)
            java.lang.String r12 = "rhinoException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r3, r12, r11, r0)
        L_0x00c5:
            return r3
        L_0x00c6:
            java.lang.RuntimeException r11 = org.mozilla.javascript.Kit.codeBug()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.wrapException(java.lang.Throwable, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Context):org.mozilla.javascript.Scriptable");
    }

    public static Integer wrapInt(int i) {
        return Integer.valueOf(i);
    }

    public static Number wrapNumber(double d) {
        if (Double.isNaN(d)) {
            return NaNobj;
        }
        return Double.valueOf(d);
    }

    public static Scriptable wrapRegExp(Context context, Scriptable scriptable, Object obj) {
        return context.getRegExpProxy().wrapRegExp(context, scriptable, obj);
    }

    public static Scriptable createFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr, boolean z) {
        return new NativeCall(nativeFunction, scriptable, objArr, false, z);
    }

    @Deprecated
    public static Object delete(Object obj, Object obj2, Context context, boolean z) {
        return delete(obj, obj2, context, getTopCallScope(context), z);
    }

    public static Object doTopCall(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr, boolean z) {
        if (scriptable == null) {
            throw new IllegalArgumentException();
        } else if (context.topCallScope == null) {
            context.topCallScope = ScriptableObject.getTopLevelScope(scriptable);
            context.useDynamicScope = context.hasFeature(7);
            boolean z2 = context.isTopLevelStrict;
            context.isTopLevelStrict = z;
            try {
                Object doTopCall = context.getFactory().doTopCall(callable, context, scriptable, scriptable2, objArr);
                context.topCallScope = null;
                context.cachedXMLLib = null;
                context.isTopLevelStrict = z2;
                if (context.currentActivationCall == null) {
                    return doTopCall;
                }
                throw new IllegalStateException();
            } catch (Throwable th) {
                context.topCallScope = null;
                context.cachedXMLLib = null;
                context.isTopLevelStrict = z2;
                if (context.currentActivationCall != null) {
                    throw new IllegalStateException();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public static Object elemIncrDecr(Object obj, Object obj2, Context context, Scriptable scriptable, int i) {
        double d;
        Object objectElem = getObjectElem(obj, obj2, context, scriptable);
        boolean z = (i & 2) != 0;
        if (objectElem instanceof Number) {
            d = ((Number) objectElem).doubleValue();
        } else {
            d = toNumber(objectElem);
            if (z) {
                objectElem = wrapNumber(d);
            }
        }
        Number wrapNumber = wrapNumber((i & 1) == 0 ? d + 1.0d : d - 1.0d);
        setObjectElem(obj, obj2, wrapNumber, context, scriptable);
        return z ? objectElem : wrapNumber;
    }

    @Deprecated
    public static Object enumInit(Object obj, Context context, int i) {
        return enumInit(obj, context, getTopCallScope(context), i);
    }

    public static String escapeString(String str, char c) {
        int i;
        if (!(c == '\"' || c == '\'' || c == '`')) {
            Kit.codeBug();
        }
        int length = str.length();
        StringBuilder sb = null;
        for (int i2 = 0; i2 != length; i2++) {
            char charAt = str.charAt(i2);
            int i3 = 32;
            if (' ' > charAt || charAt > '~' || charAt == c || charAt == '\\') {
                if (sb == null) {
                    sb = new StringBuilder(length + 3);
                    sb.append(str);
                    sb.setLength(i2);
                }
                if (charAt != ' ') {
                    if (charAt != '\\') {
                        switch (charAt) {
                            case 8:
                                i3 = 98;
                                break;
                            case 9:
                                i3 = 116;
                                break;
                            case 10:
                                i3 = 110;
                                break;
                            case 11:
                                i3 = 118;
                                break;
                            case 12:
                                i3 = 102;
                                break;
                            case 13:
                                i3 = 114;
                                break;
                            default:
                                i3 = -1;
                                break;
                        }
                    } else {
                        i3 = 92;
                    }
                }
                if (i3 >= 0) {
                    sb.append('\\');
                    sb.append((char) i3);
                } else if (charAt == c) {
                    sb.append('\\');
                    sb.append(c);
                } else {
                    if (charAt < 256) {
                        sb.append("\\x");
                        i = 2;
                    } else {
                        sb.append("\\u");
                        i = 4;
                    }
                    for (int i4 = (i - 1) * 4; i4 >= 0; i4 -= 4) {
                        int i5 = (charAt >> i4) & 15;
                        sb.append((char) (i5 < 10 ? i5 + 48 : i5 + 87));
                    }
                }
            } else if (sb != null) {
                sb.append((char) charAt);
            }
        }
        return sb == null ? str : sb.toString();
    }

    public static Callable getElemFunctionAndThis(Object obj, Object obj2, Context context, Scriptable scriptable) {
        Scriptable scriptable2;
        Object obj3;
        if (isSymbol(obj2)) {
            scriptable2 = toObjectOrNull(context, obj, scriptable);
            if (scriptable2 != null) {
                obj3 = ScriptableObject.getProperty(scriptable2, (Symbol) obj2);
            } else {
                throw undefCallError(obj, String.valueOf(obj2));
            }
        } else {
            StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, obj2);
            String str = stringIdOrIndex.stringId;
            if (str != null) {
                return getPropFunctionAndThis(obj, str, context, scriptable);
            }
            scriptable2 = toObjectOrNull(context, obj, scriptable);
            if (scriptable2 != null) {
                obj3 = ScriptableObject.getProperty(scriptable2, stringIdOrIndex.index);
            } else {
                throw undefCallError(obj, String.valueOf(obj2));
            }
        }
        if (obj3 instanceof Callable) {
            storeScriptable(context, scriptable2);
            return (Callable) obj3;
        }
        throw notFunctionError(obj3, obj2);
    }

    public static Object getObjectElem(Object obj, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return getObjectElem(objectOrNull, obj2, context);
        }
        throw undefReadError(obj, obj2);
    }

    public static Object getObjectIndex(Object obj, double d, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            int i = (int) d;
            if (((double) i) == d) {
                return getObjectIndex(objectOrNull, i, context);
            }
            return getObjectProp(objectOrNull, toString(d), context);
        }
        throw undefReadError(obj, toString(d));
    }

    public static Object getObjectProp(Object obj, String str, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return getObjectProp(objectOrNull, str, context);
        }
        throw undefReadError(obj, str);
    }

    public static Object getObjectPropNoWarn(Object obj, String str, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            Object property = ScriptableObject.getProperty(objectOrNull, str);
            return property == Scriptable.NOT_FOUND ? Undefined.instance : property;
        }
        throw undefReadError(obj, str);
    }

    public static Callable getPropFunctionAndThis(Object obj, String str, Context context, Scriptable scriptable) {
        return getPropFunctionAndThisHelper(obj, str, context, toObjectOrNull(context, obj, scriptable));
    }

    public static Object nameIncrDecr(Scriptable scriptable, String str, Context context, int i) {
        do {
            if (context.useDynamicScope && scriptable.getParentScope() == null) {
                scriptable = checkDynamicScope(context.topCallScope, scriptable);
            }
            Scriptable scriptable2 = scriptable;
            do {
                if ((scriptable2 instanceof NativeWith) && (scriptable2.getPrototype() instanceof XMLObject)) {
                    break;
                }
                Object obj = scriptable2.get(str, scriptable);
                if (obj != Scriptable.NOT_FOUND) {
                    return doScriptableIncrDecr(scriptable2, str, scriptable, obj, i);
                }
                scriptable2 = scriptable2.getPrototype();
            } while (scriptable2 != null);
            scriptable = scriptable.getParentScope();
        } while (scriptable != null);
        throw notFoundError((Scriptable) null, str);
    }

    public static Scriptable newObjectLiteral(Object[] objArr, Object[] objArr2, int[] iArr, Context context, Scriptable scriptable) {
        int i;
        Scriptable newObject = context.newObject(scriptable);
        int length = objArr.length;
        for (int i2 = 0; i2 != length; i2++) {
            String str = objArr[i2];
            if (iArr == null) {
                i = 0;
            } else {
                i = iArr[i2];
            }
            Callable callable = objArr2[i2];
            if (!(str instanceof String)) {
                newObject.put(((Integer) str).intValue(), newObject, (Object) callable);
            } else if (i == 0) {
                String str2 = str;
                if (isSpecialProperty(str2)) {
                    specialRef(newObject, str2, context, scriptable).set(context, scriptable, callable);
                } else {
                    newObject.put(str2, newObject, (Object) callable);
                }
            } else {
                ScriptableObject scriptableObject = (ScriptableObject) newObject;
                Callable callable2 = callable;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                scriptableObject.setGetterOrSetter(str, 0, callable2, z);
            }
        }
        return newObject;
    }

    public static RuntimeException notFunctionError(Object obj, Object obj2) {
        String obj3 = obj2 == null ? "null" : obj2.toString();
        if (obj == Scriptable.NOT_FOUND) {
            return typeError1("msg.function.not.found", obj3);
        }
        return typeError2("msg.isnt.function", obj3, typeof(obj));
    }

    public static Object propIncrDecr(Object obj, String str, Context context, Scriptable scriptable, int i) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            Scriptable scriptable2 = objectOrNull;
            do {
                Object obj2 = scriptable2.get(str, objectOrNull);
                if (obj2 != Scriptable.NOT_FOUND) {
                    return doScriptableIncrDecr(scriptable2, str, objectOrNull, obj2, i);
                }
                scriptable2 = scriptable2.getPrototype();
            } while (scriptable2 != null);
            Double d = NaNobj;
            objectOrNull.put(str, objectOrNull, (Object) d);
            return d;
        }
        throw undefReadError(obj, str);
    }

    public static Object refIncrDecr(Ref ref, Context context, Scriptable scriptable, int i) {
        double d;
        Object obj = ref.get(context);
        boolean z = (i & 2) != 0;
        if (obj instanceof Number) {
            d = ((Number) obj).doubleValue();
        } else {
            d = toNumber(obj);
            if (z) {
                obj = wrapNumber(d);
            }
        }
        Number wrapNumber = wrapNumber((i & 1) == 0 ? d + 1.0d : d - 1.0d);
        ref.set(context, scriptable, wrapNumber);
        return z ? obj : wrapNumber;
    }

    public static Object refSet(Ref ref, Object obj, Context context, Scriptable scriptable) {
        return ref.set(context, scriptable, obj);
    }

    public static void setFunctionProtoAndParent(BaseFunction baseFunction, Scriptable scriptable, boolean z) {
        baseFunction.setParentScope(scriptable);
        if (z) {
            baseFunction.setPrototype(ScriptableObject.getGeneratorFunctionPrototype(scriptable));
        } else {
            baseFunction.setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
        }
    }

    public static Object setObjectElem(Object obj, Object obj2, Object obj3, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return setObjectElem(objectOrNull, obj2, obj3, context);
        }
        throw undefWriteError(obj, obj2, obj3);
    }

    public static Object setObjectIndex(Object obj, double d, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            int i = (int) d;
            if (((double) i) == d) {
                return setObjectIndex(objectOrNull, i, obj2, context);
            }
            return setObjectProp(objectOrNull, toString(d), obj2, context);
        }
        throw undefWriteError(obj, String.valueOf(d), obj2);
    }

    public static Object setObjectProp(Object obj, String str, Object obj2, Context context, Scriptable scriptable) {
        if ((obj instanceof Scriptable) || !context.isStrictMode() || context.getLanguageVersion() < 180) {
            Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
            if (objectOrNull != null) {
                return setObjectProp(objectOrNull, str, obj2, context);
            }
            throw undefWriteError(obj, str, obj2);
        }
        throw undefWriteError(obj, str, obj2);
    }

    public static Ref specialRef(Object obj, String str, Context context, Scriptable scriptable) {
        return SpecialRef.createSpecial(context, scriptable, obj, str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009f, code lost:
        if (r12 != false) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a4, code lost:
        if ((r12 & r16) != false) goto L_0x00a6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static double stringToNumber(java.lang.String r24, int r25, int r26, int r27, boolean r28) {
        /*
            r0 = r24
            r1 = r25
            r2 = r27
            r3 = 1
            r5 = 10
            if (r2 >= r5) goto L_0x0010
            int r6 = r2 + 48
            int r6 = r6 - r3
            char r6 = (char) r6
            goto L_0x0012
        L_0x0010:
            r6 = 57
        L_0x0012:
            r7 = 97
            r8 = 65
            if (r2 <= r5) goto L_0x0021
            int r9 = r2 + 97
            int r9 = r9 - r5
            char r9 = (char) r9
            int r10 = r2 + 65
            int r10 = r10 - r5
            char r10 = (char) r10
            goto L_0x0025
        L_0x0021:
            r9 = 97
            r10 = 65
        L_0x0025:
            r13 = r1
            r14 = 0
        L_0x0028:
            r16 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            r11 = 48
            r12 = r26
            if (r13 > r12) goto L_0x005b
            char r4 = r0.charAt(r13)
            if (r11 > r4) goto L_0x003d
            if (r4 > r6) goto L_0x003d
            int r4 = r4 + -48
        L_0x003a:
            r19 = r9
            goto L_0x004c
        L_0x003d:
            if (r7 > r4) goto L_0x0045
            if (r4 >= r9) goto L_0x0045
            int r4 = r4 + -97
        L_0x0043:
            int r4 = r4 + r5
            goto L_0x003a
        L_0x0045:
            if (r8 > r4) goto L_0x0058
            if (r4 >= r10) goto L_0x0058
            int r4 = r4 + -65
            goto L_0x0043
        L_0x004c:
            double r8 = (double) r2
            double r14 = r14 * r8
            double r8 = (double) r4
            double r14 = r14 + r8
            int r13 = r13 + 1
            r9 = r19
            r8 = 65
            goto L_0x0028
        L_0x0058:
            if (r28 != 0) goto L_0x005b
            return r16
        L_0x005b:
            if (r1 != r13) goto L_0x005e
            return r16
        L_0x005e:
            r8 = 4845873199050653695(0x433fffffffffffff, double:9.007199254740991E15)
            int r4 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x010d
            if (r2 != r5) goto L_0x0073
            java.lang.String r0 = r0.substring(r1, r13)     // Catch:{ NumberFormatException -> 0x0072 }
            double r0 = java.lang.Double.parseDouble(r0)     // Catch:{ NumberFormatException -> 0x0072 }
            return r0
        L_0x0072:
            return r16
        L_0x0073:
            r4 = 2
            r5 = 4
            if (r2 == r4) goto L_0x0085
            if (r2 == r5) goto L_0x0085
            r6 = 8
            if (r2 == r6) goto L_0x0085
            r6 = 16
            if (r2 == r6) goto L_0x0085
            r6 = 32
            if (r2 != r6) goto L_0x010d
        L_0x0085:
            r8 = 53
            r9 = 1
            r10 = 0
            r12 = 0
            r16 = 0
            r17 = 0
            r18 = 0
        L_0x0090:
            r6 = 3
            r20 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r9 != r3) goto L_0x00d1
            if (r1 != r13) goto L_0x00b0
            if (r10 == 0) goto L_0x00ac
            if (r10 == r6) goto L_0x00a2
            if (r10 == r5) goto L_0x009f
            goto L_0x010d
        L_0x009f:
            if (r12 == 0) goto L_0x00a8
            goto L_0x00a6
        L_0x00a2:
            r0 = r12 & r16
            if (r0 == 0) goto L_0x00a8
        L_0x00a6:
            double r14 = r14 + r20
        L_0x00a8:
            double r11 = r14 * r18
            goto L_0x010e
        L_0x00ac:
            r11 = 0
            goto L_0x010e
        L_0x00b0:
            int r9 = r1 + 1
            char r1 = r0.charAt(r1)
            if (r11 > r1) goto L_0x00bf
            r11 = 57
            if (r1 > r11) goto L_0x00c1
            int r1 = r1 + -48
            goto L_0x00cc
        L_0x00bf:
            r11 = 57
        L_0x00c1:
            if (r7 > r1) goto L_0x00ca
            r7 = 122(0x7a, float:1.71E-43)
            if (r1 > r7) goto L_0x00ca
            int r1 = r1 + -87
            goto L_0x00cc
        L_0x00ca:
            int r1 = r1 + -55
        L_0x00cc:
            r17 = r1
            r1 = r9
            r9 = r2
            goto L_0x00d3
        L_0x00d1:
            r11 = 57
        L_0x00d3:
            int r9 = r9 >> r3
            r7 = r17 & r9
            if (r7 == 0) goto L_0x00da
            r7 = 1
            goto L_0x00db
        L_0x00da:
            r7 = 0
        L_0x00db:
            if (r10 == 0) goto L_0x0101
            r22 = 4611686018427387904(0x4000000000000000, double:2.0)
            if (r10 == r3) goto L_0x00f3
            if (r10 == r4) goto L_0x00ee
            if (r10 == r6) goto L_0x00e8
            if (r10 == r5) goto L_0x00eb
            goto L_0x0108
        L_0x00e8:
            if (r7 == 0) goto L_0x00eb
            r10 = 4
        L_0x00eb:
            double r18 = r18 * r22
            goto L_0x0108
        L_0x00ee:
            r12 = r7
            r18 = r22
            r10 = 3
            goto L_0x0108
        L_0x00f3:
            double r14 = r14 * r22
            if (r7 == 0) goto L_0x00f9
            double r14 = r14 + r20
        L_0x00f9:
            int r8 = r8 + -1
            if (r8 != 0) goto L_0x0108
            r16 = r7
            r10 = 2
            goto L_0x0108
        L_0x0101:
            if (r7 == 0) goto L_0x0108
            int r8 = r8 + -1
            r14 = r20
            r10 = 1
        L_0x0108:
            r7 = 97
            r11 = 48
            goto L_0x0090
        L_0x010d:
            r11 = r14
        L_0x010e:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.stringToNumber(java.lang.String, int, int, int, boolean):double");
    }

    public static double toInteger(double d) {
        if (Double.isNaN(d)) {
            return 0.0d;
        }
        int i = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
        if (i == 0 || Double.isInfinite(d)) {
            return d;
        }
        if (i > 0) {
            return Math.floor(d);
        }
        return Math.ceil(d);
    }

    public static Object toPrimitive(Object obj, Class<?> cls) {
        if (!(obj instanceof Scriptable)) {
            return obj;
        }
        Object defaultValue = ((Scriptable) obj).getDefaultValue(cls);
        if (!(defaultValue instanceof Scriptable) || isSymbol(defaultValue)) {
            return defaultValue;
        }
        throw typeError0("msg.bad.default.value");
    }

    public static long toUint32(Object obj) {
        return toUint32(toNumber(obj));
    }

    public static BaseFunction typeErrorThrower(Context context) {
        if (context.typeErrorThrower == null) {
            AnonymousClass1 r0 = new BaseFunction() {
                private static final long serialVersionUID = -5891740962154902286L;

                public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
                    throw ScriptRuntime.typeError0("msg.op.not.allowed");
                }

                public int getLength() {
                    return 0;
                }
            };
            setFunctionProtoAndParent(r0, context.topCallScope);
            r0.preventExtensions();
            context.typeErrorThrower = r0;
        }
        return context.typeErrorThrower;
    }

    public static final class StringIdOrIndex {
        final int index;
        final String stringId;

        public StringIdOrIndex(String str) {
            this.stringId = str;
            this.index = -1;
        }

        public StringIdOrIndex(int i) {
            this.stringId = null;
            this.index = i;
        }
    }

    public static EcmaError constructError(String str, String str2, int i) {
        int[] iArr = new int[1];
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        int i2 = iArr[0];
        if (i2 != 0) {
            iArr[0] = i2 + i;
        }
        return constructError(str, str2, sourcePositionFromStack, iArr[0], (String) null, 0);
    }

    public static Object delete(Object obj, Object obj2, Context context, Scriptable scriptable, boolean z) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return wrapBoolean(deleteObjectElem(objectOrNull, obj2, context));
        }
        if (z) {
            return Boolean.TRUE;
        }
        throw undefDeleteError(obj, obj2);
    }

    public static Object enumInit(Object obj, Context context, Scriptable scriptable, int i) {
        IdEnumeration idEnumeration = new IdEnumeration();
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        idEnumeration.obj = objectOrNull;
        if (i == 6) {
            idEnumeration.enumType = i;
            idEnumeration.iterator = null;
            return enumInitInOrder(context, idEnumeration);
        } else if (objectOrNull == null) {
            return idEnumeration;
        } else {
            idEnumeration.enumType = i;
            idEnumeration.iterator = null;
            if (!(i == 3 || i == 4 || i == 5)) {
                idEnumeration.iterator = toIterator(context, objectOrNull.getParentScope(), idEnumeration.obj, i == 0);
            }
            if (idEnumeration.iterator == null) {
                enumChangeObject(idEnumeration);
            }
            return idEnumeration;
        }
    }

    public static Object getIndexObject(double d) {
        int i = (int) d;
        if (((double) i) == d) {
            return Integer.valueOf(i);
        }
        return toString(d);
    }

    public static Ref nameRef(Object obj, Object obj2, Context context, Scriptable scriptable, int i) {
        return currentXMLLib(context).nameRef(context, obj, obj2, scriptable, i);
    }

    public static int toInt32(Object[] objArr, int i) {
        if (i < objArr.length) {
            return toInt32(objArr[i]);
        }
        return 0;
    }

    @Deprecated
    public static Scriptable toObject(Scriptable scriptable, Object obj, Class<?> cls) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        return toObject(Context.getContext(), scriptable, obj);
    }

    public static EcmaError constructError(String str, String str2, String str3, int i, String str4, int i2) {
        return new EcmaError(str, str2, str3, i, str4, i2);
    }

    public static Object getObjectElem(Scriptable scriptable, Object obj, Context context) {
        Object obj2;
        if (scriptable instanceof XMLObject) {
            obj2 = ((XMLObject) scriptable).get(context, obj);
        } else if (isSymbol(obj)) {
            obj2 = ScriptableObject.getProperty(scriptable, (Symbol) obj);
        } else {
            StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, obj);
            String str = stringIdOrIndex.stringId;
            if (str == null) {
                obj2 = ScriptableObject.getProperty(scriptable, stringIdOrIndex.index);
            } else {
                obj2 = ScriptableObject.getProperty(scriptable, str);
            }
        }
        return obj2 == Scriptable.NOT_FOUND ? Undefined.instance : obj2;
    }

    public static Object getObjectProp(Scriptable scriptable, String str, Context context) {
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (property != Scriptable.NOT_FOUND) {
            return property;
        }
        if (context.hasFeature(11)) {
            Context.reportWarning(getMessage1("msg.ref.undefined.prop", str));
        }
        return Undefined.instance;
    }

    public static Ref memberRef(Object obj, Object obj2, Object obj3, Context context, int i) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).memberRef(context, obj2, obj3, i);
        }
        throw notXmlError(obj);
    }

    public static Scriptable newObject(Object obj, Context context, Scriptable scriptable, Object[] objArr) {
        if (obj instanceof Function) {
            return ((Function) obj).construct(context, scriptable, objArr);
        }
        throw notFunctionError(obj);
    }

    public static Object setObjectElem(Scriptable scriptable, Object obj, Object obj2, Context context) {
        if (scriptable instanceof XMLObject) {
            ((XMLObject) scriptable).put(context, obj, obj2);
        } else if (isSymbol(obj)) {
            ScriptableObject.putProperty(scriptable, (Symbol) obj, obj2);
        } else {
            StringIdOrIndex stringIdOrIndex = toStringIdOrIndex(context, obj);
            String str = stringIdOrIndex.stringId;
            if (str == null) {
                ScriptableObject.putProperty(scriptable, stringIdOrIndex.index, obj2);
            } else {
                ScriptableObject.putProperty(scriptable, str, obj2);
            }
        }
        return obj2;
    }

    public static int toInt32(double d) {
        return DoubleConversion.doubleToInt32(d);
    }

    public static Scriptable toObjectOrNull(Context context, Object obj, Scriptable scriptable) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj == null || obj == Undefined.instance) {
            return null;
        }
        return toObject(context, scriptable, obj);
    }

    public static RuntimeException notFunctionError(Object obj, Object obj2, String str) {
        int indexOf;
        String scriptRuntime = toString(obj);
        if ((obj instanceof NativeFunction) && (indexOf = scriptRuntime.indexOf(123, scriptRuntime.indexOf(41))) > -1) {
            scriptRuntime = scriptRuntime.substring(0, indexOf + 1) + "...}";
        }
        if (obj2 == Scriptable.NOT_FOUND) {
            return typeError2("msg.function.not.found.in", str, scriptRuntime);
        }
        return typeError3("msg.isnt.function.in", str, scriptRuntime, typeof(obj2));
    }

    public static double toInteger(Object[] objArr, int i) {
        if (i < objArr.length) {
            return toInteger(objArr[i]);
        }
        return 0.0d;
    }

    public static Object getObjectIndex(Scriptable scriptable, int i, Context context) {
        Object property = ScriptableObject.getProperty(scriptable, i);
        return property == Scriptable.NOT_FOUND ? Undefined.instance : property;
    }

    public static Object setObjectIndex(Scriptable scriptable, int i, Object obj, Context context) {
        ScriptableObject.putProperty(scriptable, i, obj);
        return obj;
    }

    public static Scriptable toObject(Context context, Scriptable scriptable, Object obj) {
        if (obj == null) {
            throw typeError0("msg.null.to.object");
        } else if (Undefined.isUndefined(obj)) {
            throw typeError0("msg.undef.to.object");
        } else if (isSymbol(obj)) {
            NativeSymbol nativeSymbol = new NativeSymbol((NativeSymbol) obj);
            setBuiltinProtoAndParent(nativeSymbol, scriptable, TopLevel.Builtins.Symbol);
            return nativeSymbol;
        } else if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        } else {
            if (obj instanceof CharSequence) {
                NativeString nativeString = new NativeString((CharSequence) obj);
                setBuiltinProtoAndParent(nativeString, scriptable, TopLevel.Builtins.String);
                return nativeString;
            } else if (obj instanceof Number) {
                NativeNumber nativeNumber = new NativeNumber(((Number) obj).doubleValue());
                setBuiltinProtoAndParent(nativeNumber, scriptable, TopLevel.Builtins.Number);
                return nativeNumber;
            } else if (obj instanceof Boolean) {
                NativeBoolean nativeBoolean = new NativeBoolean(((Boolean) obj).booleanValue());
                setBuiltinProtoAndParent(nativeBoolean, scriptable, TopLevel.Builtins.Boolean);
                return nativeBoolean;
            } else {
                Object wrap = context.getWrapFactory().wrap(context, scriptable, obj, (Class<?>) null);
                if (wrap instanceof Scriptable) {
                    return (Scriptable) wrap;
                }
                throw errorWithClassName("msg.invalid.type", obj);
            }
        }
    }

    public static Object setObjectProp(Scriptable scriptable, String str, Object obj, Context context) {
        ScriptableObject.putProperty(scriptable, str, obj);
        return obj;
    }

    public static String toString(Object[] objArr, int i) {
        return i < objArr.length ? toString(objArr[i]) : "undefined";
    }

    public static String toString(double d) {
        return numberToString(d, 10);
    }

    public static double toNumber(Object[] objArr, int i) {
        if (i < objArr.length) {
            return toNumber(objArr[i]);
        }
        return Double.NaN;
    }

    public static double toNumber(String str) {
        char charAt;
        int i;
        char charAt2;
        String str2 = str;
        int length = str.length();
        int i2 = 0;
        while (i2 != length) {
            char charAt3 = str2.charAt(i2);
            if (!isStrWhiteSpaceChar(charAt3)) {
                int i3 = length - 1;
                while (true) {
                    charAt = str2.charAt(i3);
                    if (!isStrWhiteSpaceChar(charAt)) {
                        break;
                    }
                    i3--;
                }
                Context currentContext = Context.getCurrentContext();
                boolean z = currentContext == null || currentContext.getLanguageVersion() < 200;
                int i4 = 16;
                if (charAt3 == '0') {
                    int i5 = i2 + 2;
                    if (i5 <= i3) {
                        char charAt4 = str2.charAt(i2 + 1);
                        if (!(charAt4 == 'x' || charAt4 == 'X')) {
                            i4 = (z || !(charAt4 == 'o' || charAt4 == 'O')) ? (z || !(charAt4 == 'b' || charAt4 == 'B')) ? -1 : 2 : 8;
                        }
                        if (i4 != -1) {
                            if (z) {
                                return stringPrefixToNumber(str2, i5, i4);
                            }
                            return stringToNumber(str2, i5, i3, i4);
                        }
                    }
                } else if (z && ((charAt3 == '+' || charAt3 == '-') && (i = i2 + 3) <= i3 && str2.charAt(i2 + 1) == '0' && ((charAt2 = str2.charAt(i2 + 2)) == 'x' || charAt2 == 'X'))) {
                    double stringPrefixToNumber = stringPrefixToNumber(str2, i, 16);
                    return charAt3 == '-' ? -stringPrefixToNumber : stringPrefixToNumber;
                }
                if (charAt == 'y') {
                    if (charAt3 == '+' || charAt3 == '-') {
                        i2++;
                    }
                    if (i2 + 7 != i3 || !str2.regionMatches(i2, "Infinity", 0, 8)) {
                        return Double.NaN;
                    }
                    return charAt3 == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                }
                String substring = str2.substring(i2, i3 + 1);
                for (int length2 = substring.length() - 1; length2 >= 0; length2--) {
                    char charAt5 = substring.charAt(length2);
                    if (('0' > charAt5 || charAt5 > '9') && charAt5 != '.' && charAt5 != 'e' && charAt5 != 'E' && charAt5 != '+' && charAt5 != '-') {
                        return Double.NaN;
                    }
                }
                try {
                    return Double.parseDouble(substring);
                } catch (NumberFormatException unused) {
                    return Double.NaN;
                }
            } else {
                i2++;
            }
        }
        return 0.0d;
    }

    public static CharSequence add(CharSequence charSequence, Object obj) {
        return new ConsString(charSequence, toCharSequence(obj));
    }

    public static CharSequence add(Object obj, CharSequence charSequence) {
        return new ConsString(toCharSequence(obj), charSequence);
    }

    @Deprecated
    public static Scriptable toObject(Context context, Scriptable scriptable, Object obj, Class<?> cls) {
        return toObject(context, scriptable, obj);
    }
}
