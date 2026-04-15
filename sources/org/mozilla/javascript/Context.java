package org.mozilla.javascript;

import androidx.appcompat.widget.ActivityChooserView;
import defpackage.l0;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ScriptNode;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.Debugger;
import org.mozilla.javascript.xml.XMLLib;

public class Context {
    public static final int FEATURE_DYNAMIC_SCOPE = 7;
    public static final int FEATURE_E4X = 6;
    public static final int FEATURE_ENABLE_XML_SECURE_PARSING = 20;
    public static final int FEATURE_ENHANCED_JAVA_ACCESS = 13;
    public static final int FEATURE_ENUMERATE_IDS_FIRST = 16;
    public static final int FEATURE_INTEGER_WITHOUT_DECIMAL_PLACE = 18;
    public static final int FEATURE_LITTLE_ENDIAN = 19;
    public static final int FEATURE_LOCATION_INFORMATION_IN_ERROR = 10;
    public static final int FEATURE_MEMBER_EXPR_AS_FUNCTION_NAME = 2;
    public static final int FEATURE_NON_ECMA_GET_YEAR = 1;
    public static final int FEATURE_OLD_UNDEF_NULL_THIS = 15;
    public static final int FEATURE_PARENT_PROTO_PROPERTIES = 5;
    @Deprecated
    public static final int FEATURE_PARENT_PROTO_PROPRTIES = 5;
    public static final int FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER = 3;
    public static final int FEATURE_STRICT_EVAL = 9;
    public static final int FEATURE_STRICT_MODE = 11;
    public static final int FEATURE_STRICT_VARS = 8;
    public static final int FEATURE_THREAD_SAFE_OBJECTS = 17;
    public static final int FEATURE_TO_STRING_AS_SOURCE = 4;
    public static final int FEATURE_V8_EXTENSIONS = 14;
    public static final int FEATURE_WARNING_AS_ERROR = 12;
    public static final int VERSION_1_0 = 100;
    public static final int VERSION_1_1 = 110;
    public static final int VERSION_1_2 = 120;
    public static final int VERSION_1_3 = 130;
    public static final int VERSION_1_4 = 140;
    public static final int VERSION_1_5 = 150;
    public static final int VERSION_1_6 = 160;
    public static final int VERSION_1_7 = 170;
    public static final int VERSION_1_8 = 180;
    public static final int VERSION_DEFAULT = 0;
    public static final int VERSION_ES6 = 200;
    public static final int VERSION_UNKNOWN = -1;
    private static Class<?> codegenClass = Kit.classOrNull("org.mozilla.javascript.optimizer.Codegen");
    public static final Object[] emptyArgs = ScriptRuntime.emptyArgs;
    public static final String errorReporterProperty = "error reporter";
    private static Class<?> interpreterClass = Kit.classOrNull("org.mozilla.javascript.Interpreter");
    public static final String languageVersionProperty = "language version";
    Set<String> activationNames;
    private ClassLoader applicationClassLoader;
    XMLLib cachedXMLLib;
    /* access modifiers changed from: private */
    public ClassShutter classShutter;
    NativeCall currentActivationCall;
    Debugger debugger;
    private Object debuggerData;
    private int enterCount;
    private ErrorReporter errorReporter;
    private final ContextFactory factory;
    public boolean generateObserverCount;
    private boolean generatingDebug;
    private boolean generatingDebugChanged;
    private boolean generatingSource;
    private boolean hasClassShutter;
    int instructionCount;
    int instructionThreshold;
    Object interpreterSecurityDomain;
    boolean isContinuationsTopCall;
    boolean isTopLevelStrict;
    ObjToIntMap iterating;
    Object lastInterpreterFrame;
    private Locale locale;
    private int maximumInterpreterStackDepth;
    private int optimizationLevel;
    ObjArray previousInterpreterInvocations;
    private Object propertyListeners;
    RegExpProxy regExpProxy;
    Scriptable scratchScriptable;
    long scratchUint32;
    private Object sealKey;
    private boolean sealed;
    private SecurityController securityController;
    private Map<Object, Object> threadLocalMap;
    Scriptable topCallScope;
    BaseFunction typeErrorThrower;
    boolean useDynamicScope;
    int version;
    private WrapFactory wrapFactory;

    public interface ClassShutterSetter {
        ClassShutter getClassShutter();

        void setClassShutter(ClassShutter classShutter);
    }

    @Deprecated
    public Context() {
        this(ContextFactory.getGlobal());
    }

    @Deprecated
    public static void addContextListener(ContextListener contextListener) {
        if ("org.mozilla.javascript.tools.debugger.Main".equals(contextListener.getClass().getName())) {
            Class<?> cls = contextListener.getClass();
            Class[] clsArr = {Kit.classOrNull("org.mozilla.javascript.ContextFactory")};
            try {
                cls.getMethod("attachTo", clsArr).invoke(contextListener, new Object[]{ContextFactory.getGlobal()});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ContextFactory.getGlobal().addListener(contextListener);
        }
    }

    @Deprecated
    public static <T> T call(ContextAction<T> contextAction) {
        return call(ContextFactory.getGlobal(), contextAction);
    }

    public static void checkLanguageVersion(int i) {
        if (!isValidLanguageVersion(i)) {
            throw new IllegalArgumentException(y2.f("Bad language version: ", i));
        }
    }

    public static void checkOptimizationLevel(int i) {
        if (!isValidOptimizationLevel(i)) {
            throw new IllegalArgumentException(y2.f("Optimization level outside [-1..9]: ", i));
        }
    }

    private Object compileImpl(Scriptable scriptable, String str, String str2, int i, Object obj, boolean z, Evaluator evaluator, ErrorReporter errorReporter2) throws IOException {
        String str3;
        boolean z2;
        ErrorReporter errorReporter3;
        Evaluator evaluator2;
        Object obj2;
        Scriptable scriptable2 = scriptable;
        String str4 = str;
        Object obj3 = obj;
        boolean z3 = z;
        if (str2 == null) {
            str3 = "unnamed script";
        } else {
            str3 = str2;
        }
        if (obj3 == null || getSecurityController() != null) {
            if (scriptable2 == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!(z2 ^ z3)) {
                Kit.codeBug();
            }
            CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
            compilerEnvirons.initFromContext(this);
            if (errorReporter2 == null) {
                errorReporter3 = compilerEnvirons.getErrorReporter();
            } else {
                errorReporter3 = errorReporter2;
            }
            ScriptNode parse = parse(str, str3, i, compilerEnvirons, errorReporter3, z);
            if (evaluator == null) {
                try {
                    evaluator2 = createCompiler();
                } catch (l0.a unused) {
                    ScriptNode parse2 = parse(str, str3, i, compilerEnvirons, errorReporter3, z);
                    evaluator2 = createInterpreter();
                    obj2 = evaluator2.compile(compilerEnvirons, parse2, parse2.getEncodedSource(), z3);
                }
            } else {
                evaluator2 = evaluator;
            }
            obj2 = evaluator2.compile(compilerEnvirons, parse, parse.getEncodedSource(), z3);
            if (this.debugger != null) {
                if (str4 == null) {
                    Kit.codeBug();
                }
                if (obj2 instanceof DebuggableScript) {
                    notifyDebugger_r(this, (DebuggableScript) obj2, str4);
                } else {
                    throw new RuntimeException("NOT SUPPORTED");
                }
            }
            if (z3) {
                return evaluator2.createFunctionObject(this, scriptable2, obj2, obj3);
            }
            return evaluator2.createScriptObject(obj2, obj3);
        }
        throw new IllegalArgumentException("securityDomain should be null if setSecurityController() was never called");
    }

    private Evaluator createCompiler() {
        Evaluator evaluator;
        Class<?> cls;
        if (this.optimizationLevel < 0 || (cls = codegenClass) == null) {
            evaluator = null;
        } else {
            evaluator = (Evaluator) Kit.newInstanceOrNull(cls);
        }
        if (evaluator == null) {
            return createInterpreter();
        }
        return evaluator;
    }

    public static Evaluator createInterpreter() {
        return (Evaluator) Kit.newInstanceOrNull(interpreterClass);
    }

    public static Context enter() {
        return enter((Context) null, ContextFactory.getGlobal());
    }

    public static void exit() {
        VMBridge vMBridge = VMBridge.instance;
        Object threadContextHelper = vMBridge.getThreadContextHelper();
        Context context = vMBridge.getContext(threadContextHelper);
        if (context != null) {
            if (context.enterCount < 1) {
                Kit.codeBug();
            }
            int i = context.enterCount - 1;
            context.enterCount = i;
            if (i == 0) {
                vMBridge.setContext(threadContextHelper, (Context) null);
                context.factory.onContextReleased(context);
                return;
            }
            return;
        }
        throw new IllegalStateException("Calling Context.exit without previous Context.enter");
    }

    private void firePropertyChangeImpl(Object obj, String str, Object obj2, Object obj3) {
        int i = 0;
        while (true) {
            Object listener = Kit.getListener(obj, i);
            if (listener != null) {
                if (listener instanceof PropertyChangeListener) {
                    ((PropertyChangeListener) listener).propertyChange(new PropertyChangeEvent(this, str, obj2, obj3));
                }
                i++;
            } else {
                return;
            }
        }
    }

    public static Context getContext() {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            return currentContext;
        }
        throw new RuntimeException("No Context associated with current Thread");
    }

    public static Context getCurrentContext() {
        VMBridge vMBridge = VMBridge.instance;
        return vMBridge.getContext(vMBridge.getThreadContextHelper());
    }

    public static DebuggableScript getDebuggableView(Script script) {
        if (script instanceof NativeFunction) {
            return ((NativeFunction) script).getDebuggableView();
        }
        return null;
    }

    public static String getSourcePositionFromStack(int[] iArr) {
        int lineNumber;
        Evaluator createInterpreter;
        Context currentContext = getCurrentContext();
        if (currentContext == null) {
            return null;
        }
        if (currentContext.lastInterpreterFrame != null && (createInterpreter = createInterpreter()) != null) {
            return createInterpreter.getSourcePositionFromStack(currentContext, iArr);
        }
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int length = stackTrace.length;
        int i = 0;
        while (i < length) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String fileName = stackTraceElement.getFileName();
            if (fileName == null || fileName.endsWith(".java") || (lineNumber = stackTraceElement.getLineNumber()) < 0) {
                i++;
            } else {
                iArr[0] = lineNumber;
                return fileName;
            }
        }
        return null;
    }

    public static Object getUndefinedValue() {
        return Undefined.instance;
    }

    public static boolean isValidLanguageVersion(int i) {
        switch (i) {
            case 0:
            case 100:
            case 110:
            case 120:
            case 130:
            case 140:
            case 150:
            case 160:
            case VERSION_1_7 /*170*/:
            case VERSION_1_8 /*180*/:
            case 200:
                return true;
            default:
                return false;
        }
    }

    public static boolean isValidOptimizationLevel(int i) {
        return -1 <= i && i <= 9;
    }

    public static Object javaToJS(Object obj, Scriptable scriptable) {
        if ((obj instanceof String) || (obj instanceof Number) || (obj instanceof Boolean) || (obj instanceof Scriptable)) {
            return obj;
        }
        if (obj instanceof Character) {
            return String.valueOf(((Character) obj).charValue());
        }
        Context context = getContext();
        return context.getWrapFactory().wrap(context, scriptable, obj, (Class<?>) null);
    }

    public static Object jsToJava(Object obj, Class<?> cls) throws EvaluatorException {
        return NativeJavaObject.coerceTypeImpl(cls, obj);
    }

    private static void notifyDebugger_r(Context context, DebuggableScript debuggableScript, String str) {
        context.debugger.handleCompilationDone(context, debuggableScript, str);
        for (int i = 0; i != debuggableScript.getFunctionCount(); i++) {
            notifyDebugger_r(context, debuggableScript.getFunction(i), str);
        }
    }

    public static void onSealedMutation() {
        throw new IllegalStateException();
    }

    private ScriptNode parse(String str, String str2, int i, CompilerEnvirons compilerEnvirons, ErrorReporter errorReporter2, boolean z) throws IOException {
        Parser parser = new Parser(compilerEnvirons, errorReporter2);
        if (z) {
            parser.calledByCompileFunction = true;
        }
        if (isStrictMode()) {
            parser.setDefaultUseStrictDirective(true);
        }
        AstRoot parse = parser.parse(str, str2, i);
        if (!z || (parse.getFirstChild() != null && parse.getFirstChild().getType() == 110)) {
            return new IRFactory(compilerEnvirons, errorReporter2).transformTree(parse);
        }
        throw new IllegalArgumentException(y2.i("compileFunction only accepts source with single JS function: ", str));
    }

    @Deprecated
    public static void removeContextListener(ContextListener contextListener) {
        ContextFactory.getGlobal().addListener(contextListener);
    }

    public static void reportError(String str, String str2, int i, String str3, int i2) {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            currentContext.getErrorReporter().error(str, str2, i, str3, i2);
            return;
        }
        throw new EvaluatorException(str, str2, i, str3, i2);
    }

    public static EvaluatorException reportRuntimeError(String str, String str2, int i, String str3, int i2) {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            return currentContext.getErrorReporter().runtimeError(str, str2, i, str3, i2);
        }
        throw new EvaluatorException(str, str2, i, str3, i2);
    }

    public static EvaluatorException reportRuntimeError0(String str) {
        return reportRuntimeError(ScriptRuntime.getMessage0(str));
    }

    public static EvaluatorException reportRuntimeError1(String str, Object obj) {
        return reportRuntimeError(ScriptRuntime.getMessage1(str, obj));
    }

    public static EvaluatorException reportRuntimeError2(String str, Object obj, Object obj2) {
        return reportRuntimeError(ScriptRuntime.getMessage2(str, obj, obj2));
    }

    public static EvaluatorException reportRuntimeError3(String str, Object obj, Object obj2, Object obj3) {
        return reportRuntimeError(ScriptRuntime.getMessage3(str, obj, obj2, obj3));
    }

    public static EvaluatorException reportRuntimeError4(String str, Object obj, Object obj2, Object obj3, Object obj4) {
        return reportRuntimeError(ScriptRuntime.getMessage4(str, obj, obj2, obj3, obj4));
    }

    public static void reportWarning(String str, String str2, int i, String str3, int i2) {
        Context context = getContext();
        if (context.hasFeature(12)) {
            reportError(str, str2, i, str3, i2);
        } else {
            context.getErrorReporter().warning(str, str2, i, str3, i2);
        }
    }

    @Deprecated
    public static void setCachingEnabled(boolean z) {
    }

    public static RuntimeException throwAsScriptRuntimeEx(Throwable th) {
        Context context;
        while (th instanceof InvocationTargetException) {
            th = ((InvocationTargetException) th).getTargetException();
        }
        if ((th instanceof Error) && ((context = getContext()) == null || !context.hasFeature(13))) {
            throw ((Error) th);
        } else if (th instanceof RhinoException) {
            throw ((RhinoException) th);
        } else {
            throw new WrappedException(th);
        }
    }

    public static boolean toBoolean(Object obj) {
        return ScriptRuntime.toBoolean(obj);
    }

    public static double toNumber(Object obj) {
        return ScriptRuntime.toNumber(obj);
    }

    public static Scriptable toObject(Object obj, Scriptable scriptable) {
        return ScriptRuntime.toObject(scriptable, obj);
    }

    public static String toString(Object obj) {
        return ScriptRuntime.toString(obj);
    }

    @Deprecated
    public static Object toType(Object obj, Class<?> cls) throws IllegalArgumentException {
        try {
            return jsToJava(obj, cls);
        } catch (EvaluatorException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public void addActivationName(String str) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (this.activationNames == null) {
            this.activationNames = new HashSet();
        }
        this.activationNames.add(str);
    }

    public final void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.propertyListeners = Kit.addListener(this.propertyListeners, propertyChangeListener);
    }

    public Object callFunctionWithContinuations(Callable callable, Scriptable scriptable, Object[] objArr) throws ContinuationPending {
        if (!(callable instanceof InterpretedFunction)) {
            throw new IllegalArgumentException("Function argument was not created by interpreted mode ");
        } else if (!ScriptRuntime.hasTopCall(this)) {
            this.isContinuationsTopCall = true;
            return ScriptRuntime.doTopCall(callable, this, scriptable, scriptable, objArr, this.isTopLevelStrict);
        } else {
            throw new IllegalStateException("Cannot have any pending top calls when executing a script with continuations");
        }
    }

    public ContinuationPending captureContinuation() {
        return new ContinuationPending(Interpreter.captureContinuation(this));
    }

    public final Function compileFunction(Scriptable scriptable, String str, String str2, int i, Object obj) {
        return compileFunction(scriptable, str, (Evaluator) null, (ErrorReporter) null, str2, i, obj);
    }

    @Deprecated
    public final Script compileReader(Scriptable scriptable, Reader reader, String str, int i, Object obj) throws IOException {
        return compileReader(reader, str, i, obj);
    }

    public final Script compileString(String str, String str2, int i, Object obj) {
        return compileString(str, (Evaluator) null, (ErrorReporter) null, str2, i < 0 ? 0 : i, obj);
    }

    public GeneratedClassLoader createClassLoader(ClassLoader classLoader) {
        return getFactory().createClassLoader(classLoader);
    }

    public final String decompileFunction(Function function, int i) {
        if (function instanceof BaseFunction) {
            return ((BaseFunction) function).decompile(i, 0);
        }
        return "function " + function.getClassName() + "() {\n\t[native code]\n}\n";
    }

    public final String decompileFunctionBody(Function function, int i) {
        if (function instanceof BaseFunction) {
            return ((BaseFunction) function).decompile(i, 1);
        }
        return "[native code]\n";
    }

    public final String decompileScript(Script script, int i) {
        return ((NativeFunction) script).decompile(i, 0);
    }

    public final Object evaluateReader(Scriptable scriptable, Reader reader, String str, int i, Object obj) throws IOException {
        Script compileReader = compileReader(scriptable, reader, str, i, obj);
        if (compileReader != null) {
            return compileReader.exec(this, scriptable);
        }
        return null;
    }

    public final Object evaluateString(Scriptable scriptable, String str, String str2, int i, Object obj) {
        Script compileString = compileString(str, str2, i, obj);
        if (compileString != null) {
            return compileString.exec(this, scriptable);
        }
        return null;
    }

    public Object executeScriptWithContinuations(Script script, Scriptable scriptable) throws ContinuationPending {
        if (script instanceof InterpretedFunction) {
            InterpretedFunction interpretedFunction = (InterpretedFunction) script;
            if (interpretedFunction.isScript()) {
                return callFunctionWithContinuations(interpretedFunction, scriptable, ScriptRuntime.emptyArgs);
            }
        }
        throw new IllegalArgumentException("Script argument was not a script or was not created by interpreted mode ");
    }

    public final void firePropertyChange(String str, Object obj, Object obj2) {
        Object obj3 = this.propertyListeners;
        if (obj3 != null) {
            firePropertyChangeImpl(obj3, str, obj, obj2);
        }
    }

    public final ClassLoader getApplicationClassLoader() {
        if (this.applicationClassLoader == null) {
            ContextFactory factory2 = getFactory();
            ClassLoader applicationClassLoader2 = factory2.getApplicationClassLoader();
            if (applicationClassLoader2 == null) {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (contextClassLoader != null && Kit.testIfCanLoadRhinoClasses(contextClassLoader)) {
                    return contextClassLoader;
                }
                Class<?> cls = factory2.getClass();
                if (cls != ScriptRuntime.ContextFactoryClass) {
                    applicationClassLoader2 = cls.getClassLoader();
                } else {
                    applicationClassLoader2 = getClass().getClassLoader();
                }
            }
            this.applicationClassLoader = applicationClassLoader2;
        }
        return this.applicationClassLoader;
    }

    public final synchronized ClassShutter getClassShutter() {
        return this.classShutter;
    }

    public final synchronized ClassShutterSetter getClassShutterSetter() {
        if (this.hasClassShutter) {
            return null;
        }
        this.hasClassShutter = true;
        return new ClassShutterSetter() {
            public ClassShutter getClassShutter() {
                return Context.this.classShutter;
            }

            public void setClassShutter(ClassShutter classShutter) {
                ClassShutter unused = Context.this.classShutter = classShutter;
            }
        };
    }

    public final Debugger getDebugger() {
        return this.debugger;
    }

    public final Object getDebuggerContextData() {
        return this.debuggerData;
    }

    public XMLLib.Factory getE4xImplementationFactory() {
        return getFactory().getE4xImplementationFactory();
    }

    public final Object[] getElements(Scriptable scriptable) {
        return ScriptRuntime.getArrayElements(scriptable);
    }

    public final ErrorReporter getErrorReporter() {
        ErrorReporter errorReporter2 = this.errorReporter;
        if (errorReporter2 == null) {
            return DefaultErrorReporter.instance;
        }
        return errorReporter2;
    }

    public final ContextFactory getFactory() {
        return this.factory;
    }

    public final String getImplementationVersion() {
        return ImplementationVersion.get();
    }

    public final int getInstructionObserverThreshold() {
        return this.instructionThreshold;
    }

    public final int getLanguageVersion() {
        return this.version;
    }

    public final Locale getLocale() {
        if (this.locale == null) {
            this.locale = Locale.getDefault();
        }
        return this.locale;
    }

    public final int getMaximumInterpreterStackDepth() {
        return this.maximumInterpreterStackDepth;
    }

    public final int getOptimizationLevel() {
        return this.optimizationLevel;
    }

    public RegExpProxy getRegExpProxy() {
        Class<?> classOrNull;
        if (this.regExpProxy == null && (classOrNull = Kit.classOrNull("org.mozilla.javascript.regexp.RegExpImpl")) != null) {
            this.regExpProxy = (RegExpProxy) Kit.newInstanceOrNull(classOrNull);
        }
        return this.regExpProxy;
    }

    public SecurityController getSecurityController() {
        SecurityController global = SecurityController.global();
        if (global != null) {
            return global;
        }
        return this.securityController;
    }

    public final Object getThreadLocal(Object obj) {
        Map<Object, Object> map = this.threadLocalMap;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    public final WrapFactory getWrapFactory() {
        if (this.wrapFactory == null) {
            this.wrapFactory = new WrapFactory();
        }
        return this.wrapFactory;
    }

    public boolean hasFeature(int i) {
        return getFactory().hasFeature(this, i);
    }

    public final ScriptableObject initSafeStandardObjects() {
        return initSafeStandardObjects((ScriptableObject) null, false);
    }

    public final ScriptableObject initStandardObjects() {
        return initStandardObjects((ScriptableObject) null, false);
    }

    public final boolean isActivationNeeded(String str) {
        Set<String> set = this.activationNames;
        return set != null && set.contains(str);
    }

    public final boolean isGeneratingDebug() {
        return this.generatingDebug;
    }

    public final boolean isGeneratingDebugChanged() {
        return this.generatingDebugChanged;
    }

    public final boolean isGeneratingSource() {
        return this.generatingSource;
    }

    public final boolean isSealed() {
        return this.sealed;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.currentActivationCall;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isStrictMode() {
        /*
            r1 = this;
            boolean r0 = r1.isTopLevelStrict
            if (r0 != 0) goto L_0x000f
            org.mozilla.javascript.NativeCall r0 = r1.currentActivationCall
            if (r0 == 0) goto L_0x000d
            boolean r0 = r0.isStrict
            if (r0 == 0) goto L_0x000d
            goto L_0x000f
        L_0x000d:
            r0 = 0
            goto L_0x0010
        L_0x000f:
            r0 = 1
        L_0x0010:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Context.isStrictMode():boolean");
    }

    public final boolean isVersionECMA1() {
        int i = this.version;
        return i == 0 || i >= 130;
    }

    public Scriptable newArray(Scriptable scriptable, int i) {
        NativeArray nativeArray = new NativeArray((long) i);
        ScriptRuntime.setBuiltinProtoAndParent(nativeArray, scriptable, TopLevel.Builtins.Array);
        return nativeArray;
    }

    public Scriptable newObject(Scriptable scriptable) {
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, scriptable, TopLevel.Builtins.Object);
        return nativeObject;
    }

    public void observeInstructionCount(int i) {
        getFactory().observeInstructionCount(this, i);
    }

    public final synchronized void putThreadLocal(Object obj, Object obj2) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (this.threadLocalMap == null) {
            this.threadLocalMap = new HashMap();
        }
        this.threadLocalMap.put(obj, obj2);
    }

    public void removeActivationName(String str) {
        if (this.sealed) {
            onSealedMutation();
        }
        Set<String> set = this.activationNames;
        if (set != null) {
            set.remove(str);
        }
    }

    public final void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.propertyListeners = Kit.removeListener(this.propertyListeners, propertyChangeListener);
    }

    public final void removeThreadLocal(Object obj) {
        if (this.sealed) {
            onSealedMutation();
        }
        Map<Object, Object> map = this.threadLocalMap;
        if (map != null) {
            map.remove(obj);
        }
    }

    public Object resumeContinuation(Object obj, Scriptable scriptable, Object obj2) throws ContinuationPending {
        return Interpreter.restartContinuation((NativeContinuation) obj, this, scriptable, new Object[]{obj2});
    }

    public final void seal(Object obj) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.sealed = true;
        this.sealKey = obj;
    }

    public final void setApplicationClassLoader(ClassLoader classLoader) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (classLoader == null) {
            this.applicationClassLoader = null;
        } else if (Kit.testIfCanLoadRhinoClasses(classLoader)) {
            this.applicationClassLoader = classLoader;
        } else {
            throw new IllegalArgumentException("Loader can not resolve Rhino classes");
        }
    }

    public final synchronized void setClassShutter(ClassShutter classShutter2) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (classShutter2 == null) {
            throw new IllegalArgumentException();
        } else if (!this.hasClassShutter) {
            this.classShutter = classShutter2;
            this.hasClassShutter = true;
        } else {
            throw new SecurityException("Cannot overwrite existing ClassShutter object");
        }
    }

    public final void setDebugger(Debugger debugger2, Object obj) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.debugger = debugger2;
        this.debuggerData = obj;
    }

    public final ErrorReporter setErrorReporter(ErrorReporter errorReporter2) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (errorReporter2 != null) {
            ErrorReporter errorReporter3 = getErrorReporter();
            if (errorReporter2 == errorReporter3) {
                return errorReporter3;
            }
            Object obj = this.propertyListeners;
            if (obj != null) {
                firePropertyChangeImpl(obj, errorReporterProperty, errorReporter3, errorReporter2);
            }
            this.errorReporter = errorReporter2;
            return errorReporter3;
        }
        throw new IllegalArgumentException();
    }

    public void setGenerateObserverCount(boolean z) {
        this.generateObserverCount = z;
    }

    public final void setGeneratingDebug(boolean z) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.generatingDebugChanged = true;
        if (z && getOptimizationLevel() > 0) {
            setOptimizationLevel(0);
        }
        this.generatingDebug = z;
    }

    public final void setGeneratingSource(boolean z) {
        if (this.sealed) {
            onSealedMutation();
        }
        this.generatingSource = z;
    }

    public final void setInstructionObserverThreshold(int i) {
        boolean z;
        if (this.sealed) {
            onSealedMutation();
        }
        if (i >= 0) {
            this.instructionThreshold = i;
            if (i > 0) {
                z = true;
            } else {
                z = false;
            }
            setGenerateObserverCount(z);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setLanguageVersion(int i) {
        int i2;
        if (this.sealed) {
            onSealedMutation();
        }
        checkLanguageVersion(i);
        Object obj = this.propertyListeners;
        if (!(obj == null || i == (i2 = this.version))) {
            firePropertyChangeImpl(obj, languageVersionProperty, Integer.valueOf(i2), Integer.valueOf(i));
        }
        this.version = i;
    }

    public final Locale setLocale(Locale locale2) {
        if (this.sealed) {
            onSealedMutation();
        }
        Locale locale3 = this.locale;
        this.locale = locale2;
        return locale3;
    }

    public final void setMaximumInterpreterStackDepth(int i) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (this.optimizationLevel != -1) {
            throw new IllegalStateException("Cannot set maximumInterpreterStackDepth when optimizationLevel != -1");
        } else if (i >= 1) {
            this.maximumInterpreterStackDepth = i;
        } else {
            throw new IllegalArgumentException("Cannot set maximumInterpreterStackDepth to less than 1");
        }
    }

    public final void setOptimizationLevel(int i) {
        if (this.sealed) {
            onSealedMutation();
        }
        int i2 = -1;
        if (i == -2) {
            i = -1;
        }
        checkOptimizationLevel(i);
        if (codegenClass != null) {
            i2 = i;
        }
        this.optimizationLevel = i2;
    }

    public final void setSecurityController(SecurityController securityController2) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (securityController2 == null) {
            throw new IllegalArgumentException();
        } else if (this.securityController != null) {
            throw new SecurityException("Can not overwrite existing SecurityController object");
        } else if (!SecurityController.hasGlobal()) {
            this.securityController = securityController2;
        } else {
            throw new SecurityException("Can not overwrite existing global SecurityController object");
        }
    }

    public final void setWrapFactory(WrapFactory wrapFactory2) {
        if (this.sealed) {
            onSealedMutation();
        }
        if (wrapFactory2 != null) {
            this.wrapFactory = wrapFactory2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public final boolean stringIsCompilableUnit(String str) {
        boolean z;
        CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
        compilerEnvirons.initFromContext(this);
        compilerEnvirons.setGeneratingSource(false);
        Parser parser = new Parser(compilerEnvirons, DefaultErrorReporter.instance);
        try {
            parser.parse(str, (String) null, 1);
            z = false;
        } catch (EvaluatorException unused) {
            z = true;
        }
        if (!z || !parser.eof()) {
            return true;
        }
        return false;
    }

    public final void unseal(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        } else if (this.sealKey != obj) {
            throw new IllegalArgumentException();
        } else if (this.sealed) {
            this.sealed = false;
            this.sealKey = null;
        } else {
            throw new IllegalStateException();
        }
    }

    public Context(ContextFactory contextFactory) {
        this.generatingSource = true;
        int i = 0;
        this.generateObserverCount = false;
        if (contextFactory != null) {
            this.factory = contextFactory;
            this.version = 0;
            this.optimizationLevel = codegenClass == null ? -1 : i;
            this.maximumInterpreterStackDepth = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            return;
        }
        throw new IllegalArgumentException("factory == null");
    }

    public static Object call(ContextFactory contextFactory, Callable callable, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (contextFactory == null) {
            contextFactory = ContextFactory.getGlobal();
        }
        return call(contextFactory, new u0(callable, scriptable, scriptable2, objArr));
    }

    @Deprecated
    public static Context enter(Context context) {
        return enter(context, ContextFactory.getGlobal());
    }

    @Deprecated
    public static Scriptable toObject(Object obj, Scriptable scriptable, Class<?> cls) {
        return ScriptRuntime.toObject(scriptable, obj);
    }

    public final Function compileFunction(Scriptable scriptable, String str, Evaluator evaluator, ErrorReporter errorReporter2, String str2, int i, Object obj) {
        try {
            return (Function) compileImpl(scriptable, str, str2, i, obj, true, evaluator, errorReporter2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final Script compileReader(Reader reader, String str, int i, Object obj) throws IOException {
        return (Script) compileImpl((Scriptable) null, Kit.readReader(reader), str, i < 0 ? 0 : i, obj, false, (Evaluator) null, (ErrorReporter) null);
    }

    public final Script compileString(String str, Evaluator evaluator, ErrorReporter errorReporter2, String str2, int i, Object obj) {
        try {
            return (Script) compileImpl((Scriptable) null, str, str2, i, obj, false, evaluator, errorReporter2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final Scriptable initSafeStandardObjects(ScriptableObject scriptableObject) {
        return initSafeStandardObjects(scriptableObject, false);
    }

    public final Scriptable initStandardObjects(ScriptableObject scriptableObject) {
        return initStandardObjects(scriptableObject, false);
    }

    public static final Context enter(Context context, ContextFactory contextFactory) {
        VMBridge vMBridge = VMBridge.instance;
        Object threadContextHelper = vMBridge.getThreadContextHelper();
        Context context2 = vMBridge.getContext(threadContextHelper);
        if (context2 == null) {
            if (context == null) {
                context = contextFactory.makeContext();
                if (context.enterCount == 0) {
                    contextFactory.onContextCreated(context);
                    if (contextFactory.isSealed() && !context.isSealed()) {
                        context.seal((Object) null);
                    }
                } else {
                    throw new IllegalStateException("factory.makeContext() returned Context instance already associated with some thread");
                }
            } else if (context.enterCount != 0) {
                throw new IllegalStateException("can not use Context instance already associated with some thread");
            }
            vMBridge.setContext(threadContextHelper, context);
            context2 = context;
        }
        context2.enterCount++;
        return context2;
    }

    public ScriptableObject initSafeStandardObjects(ScriptableObject scriptableObject, boolean z) {
        return ScriptRuntime.initSafeStandardObjects(this, scriptableObject, z);
    }

    public ScriptableObject initStandardObjects(ScriptableObject scriptableObject, boolean z) {
        return ScriptRuntime.initStandardObjects(this, scriptableObject, z);
    }

    public Scriptable newArray(Scriptable scriptable, Object[] objArr) {
        if (objArr.getClass().getComponentType() == ScriptRuntime.ObjectClass) {
            NativeArray nativeArray = new NativeArray(objArr);
            ScriptRuntime.setBuiltinProtoAndParent(nativeArray, scriptable, TopLevel.Builtins.Array);
            return nativeArray;
        }
        throw new IllegalArgumentException();
    }

    public Scriptable newObject(Scriptable scriptable, String str) {
        return newObject(scriptable, str, ScriptRuntime.emptyArgs);
    }

    public static <T> T call(ContextFactory contextFactory, ContextAction<T> contextAction) {
        try {
            return contextAction.run(enter((Context) null, contextFactory));
        } finally {
            exit();
        }
    }

    public static void reportError(String str) {
        int[] iArr = {0};
        reportError(str, getSourcePositionFromStack(iArr), iArr[0], (String) null, 0);
    }

    public Scriptable newObject(Scriptable scriptable, String str, Object[] objArr) {
        return ScriptRuntime.newObject(this, scriptable, str, objArr);
    }

    public static EvaluatorException reportRuntimeError(String str) {
        int[] iArr = {0};
        return reportRuntimeError(str, getSourcePositionFromStack(iArr), iArr[0], (String) null, 0);
    }

    public static void reportWarning(String str) {
        int[] iArr = {0};
        reportWarning(str, getSourcePositionFromStack(iArr), iArr[0], (String) null, 0);
    }

    public static void reportWarning(String str, Throwable th) {
        int[] iArr = {0};
        String sourcePositionFromStack = getSourcePositionFromStack(iArr);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println(str);
        th.printStackTrace(printWriter);
        printWriter.flush();
        reportWarning(stringWriter.toString(), sourcePositionFromStack, iArr[0], (String) null, 0);
    }
}
