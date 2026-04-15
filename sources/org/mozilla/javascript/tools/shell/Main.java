package org.mozilla.javascript.tools.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.commonjs.module.ModuleScope;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.tools.SourceReader;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Main {
    private static final int EXITCODE_FILE_NOT_FOUND = 4;
    private static final int EXITCODE_RUNTIME_ERROR = 3;
    protected static ToolErrorReporter errorReporter;
    protected static int exitCode = 0;
    static List<String> fileList = new ArrayList();
    public static Global global = new Global();
    static String mainModule;
    static List<String> modulePath;
    static boolean processStdin = true;
    static Require require;
    static boolean sandboxed = false;
    private static final ScriptCache scriptCache = new ScriptCache(32);
    private static SecurityProxy securityImpl;
    public static ShellContextFactory shellContextFactory = new ShellContextFactory();
    static boolean useRequire = false;

    public static class IProxy implements ContextAction<Object>, QuitAction {
        private static final int EVAL_INLINE_SCRIPT = 2;
        private static final int PROCESS_FILES = 1;
        private static final int SYSTEM_EXIT = 3;
        String[] args;
        String scriptText;
        private int type;

        public IProxy(int i) {
            this.type = i;
        }

        public void quit(Context context, int i) {
            if (this.type == 3) {
                System.exit(i);
                return;
            }
            throw Kit.codeBug();
        }

        public Object run(Context context) {
            if (Main.useRequire) {
                Main.require = Main.global.installRequire(context, Main.modulePath, Main.sandboxed);
            }
            int i = this.type;
            if (i == 1) {
                Main.processFiles(context, this.args);
                return null;
            } else if (i == 2) {
                Main.evalInlineScript(context, this.scriptText);
                return null;
            } else {
                throw Kit.codeBug();
            }
        }
    }

    public static class ScriptCache extends LinkedHashMap<String, ScriptReference> {
        private static final long serialVersionUID = -6866856136258508615L;
        int capacity;
        ReferenceQueue<Script> queue = new ReferenceQueue<>();

        public ScriptCache(int i) {
            super(i + 1, 2.0f, true);
            this.capacity = i;
        }

        public ScriptReference get(String str, byte[] bArr) {
            while (true) {
                ScriptReference scriptReference = (ScriptReference) this.queue.poll();
                if (scriptReference == null) {
                    break;
                }
                remove(scriptReference.path);
            }
            ScriptReference scriptReference2 = (ScriptReference) get(str);
            if (scriptReference2 == null || Arrays.equals(bArr, scriptReference2.digest)) {
                return scriptReference2;
            }
            remove(scriptReference2.path);
            return null;
        }

        public void put(String str, byte[] bArr, Script script) {
            put(str, new ScriptReference(str, bArr, script, this.queue));
        }

        public boolean removeEldestEntry(Map.Entry<String, ScriptReference> entry) {
            return size() > this.capacity;
        }
    }

    public static class ScriptReference extends SoftReference<Script> {
        byte[] digest;
        String path;

        public ScriptReference(String str, byte[] bArr, Script script, ReferenceQueue<Script> referenceQueue) {
            super(script, referenceQueue);
            this.path = str;
            this.digest = bArr;
        }
    }

    static {
        global.initQuitAction(new IProxy(3));
    }

    public static void evalInlineScript(Context context, String str) {
        try {
            Script compileString = context.compileString(str, "<command>", 1, (Object) null);
            if (compileString != null) {
                compileString.exec(context, getShellScope());
            }
        } catch (RhinoException e) {
            ToolErrorReporter.reportException(context.getErrorReporter(), e);
            exitCode = 3;
        } catch (VirtualMachineError e2) {
            e2.printStackTrace();
            Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e2.toString()));
            exitCode = 3;
        }
    }

    public static int exec(String[] strArr) {
        ToolErrorReporter toolErrorReporter = new ToolErrorReporter(false, global.getErr());
        errorReporter = toolErrorReporter;
        shellContextFactory.setErrorReporter(toolErrorReporter);
        String[] processOptions = processOptions(strArr);
        int i = exitCode;
        if (i > 0) {
            return i;
        }
        if (processStdin) {
            fileList.add((Object) null);
        }
        Global global2 = global;
        if (!global2.initialized) {
            global2.init((ContextFactory) shellContextFactory);
        }
        IProxy iProxy = new IProxy(1);
        iProxy.args = processOptions;
        shellContextFactory.call(iProxy);
        return exitCode;
    }

    private static byte[] getDigest(Object obj) {
        byte[] bArr;
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            bArr = ((String) obj).getBytes(StandardCharsets.UTF_8);
        } else {
            bArr = (byte[]) obj;
        }
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrintStream getErr() {
        return getGlobal().getErr();
    }

    public static Global getGlobal() {
        return global;
    }

    public static InputStream getIn() {
        return getGlobal().getIn();
    }

    public static PrintStream getOut() {
        return getGlobal().getOut();
    }

    public static Scriptable getScope(String str) {
        URI uri;
        if (!useRequire) {
            return global;
        }
        if (str == null) {
            uri = new File(System.getProperty("user.dir")).toURI();
        } else if (SourceReader.toUrl(str) != null) {
            try {
                uri = new URI(str);
            } catch (URISyntaxException unused) {
                uri = new File(str).toURI();
            }
        } else {
            uri = new File(str).toURI();
        }
        return new ModuleScope(global, uri, (URI) null);
    }

    public static Scriptable getShellScope() {
        return getScope((String) null);
    }

    private static void initJavaPolicySecuritySupport() {
        try {
            SecurityProxy newInstance = JavaPolicySecurity.class.newInstance();
            securityImpl = newInstance;
            SecurityController.initGlobal(newInstance);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | LinkageError e) {
            throw new IllegalStateException("Can not load security support: " + e, e);
        }
    }

    private static Script loadCompiledScript(Context context, String str, byte[] bArr, Object obj) throws FileNotFoundException {
        int i;
        if (bArr != null) {
            int lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf < 0) {
                i = 0;
            } else {
                i = lastIndexOf + 1;
            }
            int lastIndexOf2 = str.lastIndexOf(46);
            if (lastIndexOf2 < i) {
                lastIndexOf2 = str.length();
            }
            String substring = str.substring(i, lastIndexOf2);
            try {
                GeneratedClassLoader createLoader = SecurityController.createLoader(context.getApplicationClassLoader(), obj);
                Class<?> defineClass = createLoader.defineClass(substring, bArr);
                createLoader.linkClass(defineClass);
                if (Script.class.isAssignableFrom(defineClass)) {
                    return (Script) defineClass.newInstance();
                }
                throw Context.reportRuntimeError("msg.must.implement.Script");
            } catch (IllegalAccessException e) {
                Context.reportError(e.toString());
                throw new RuntimeException(e);
            } catch (InstantiationException e2) {
                Context.reportError(e2.toString());
                throw new RuntimeException(e2);
            }
        } else {
            throw new FileNotFoundException(str);
        }
    }

    public static void main(String[] strArr) {
        try {
            if (Boolean.getBoolean("rhino.use_java_policy_security")) {
                initJavaPolicySecuritySupport();
            }
        } catch (SecurityException e) {
            e.printStackTrace(System.err);
        }
        int exec = exec(strArr);
        if (exec != 0) {
            System.exit(exec);
        }
    }

    public static void processFile(Context context, Scriptable scriptable, String str) throws IOException {
        SecurityProxy securityProxy = securityImpl;
        if (securityProxy == null) {
            processFileSecure(context, scriptable, str, (Object) null);
        } else {
            securityProxy.callProcessFileSecure(context, scriptable, str);
        }
    }

    public static void processFileNoThrow(Context context, Scriptable scriptable, String str) {
        try {
            processFile(context, scriptable, str);
        } catch (IOException e) {
            Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.read.source", str, e.getMessage()));
            exitCode = 4;
        } catch (RhinoException e2) {
            ToolErrorReporter.reportException(context.getErrorReporter(), e2);
            exitCode = 3;
        } catch (VirtualMachineError e3) {
            e3.printStackTrace();
            Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e3.toString()));
            exitCode = 3;
        }
    }

    public static void processFileSecure(Context context, Scriptable scriptable, String str, Object obj) throws IOException {
        Script script;
        Script compileString;
        boolean endsWith = str.endsWith(".class");
        Object readFileOrUrl = readFileOrUrl(str, !endsWith);
        byte[] digest = getDigest(readFileOrUrl);
        StringBuilder o = y2.o(str, "_");
        o.append(context.getOptimizationLevel());
        String sb = o.toString();
        ScriptReference scriptReference = scriptCache.get(sb, digest);
        if (scriptReference != null) {
            script = (Script) scriptReference.get();
        } else {
            script = null;
        }
        if (script == null) {
            if (endsWith) {
                compileString = loadCompiledScript(context, str, (byte[]) readFileOrUrl, obj);
            } else {
                String str2 = (String) readFileOrUrl;
                if (str2.length() > 0 && str2.charAt(0) == '#') {
                    int i = 1;
                    while (true) {
                        if (i == str2.length()) {
                            break;
                        }
                        char charAt = str2.charAt(i);
                        if (charAt == 10 || charAt == 13) {
                            str2 = str2.substring(i);
                        } else {
                            i++;
                        }
                    }
                    str2 = str2.substring(i);
                }
                compileString = context.compileString(str2, str, 1, obj);
            }
            script = compileString;
            scriptCache.put(sb, digest, script);
        }
        if (script != null) {
            script.exec(context, scriptable);
        }
    }

    public static void processFiles(Context context, String[] strArr) {
        Object[] objArr = new Object[strArr.length];
        System.arraycopy(strArr, 0, objArr, 0, strArr.length);
        global.defineProperty("arguments", (Object) context.newArray((Scriptable) global, objArr), 2);
        for (String next : fileList) {
            try {
                processSource(context, next);
            } catch (IOException e) {
                Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.read.source", next, e.getMessage()));
                exitCode = 4;
            } catch (RhinoException e2) {
                ToolErrorReporter.reportException(context.getErrorReporter(), e2);
                exitCode = 3;
            } catch (VirtualMachineError e3) {
                e3.printStackTrace();
                Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e3.toString()));
                exitCode = 3;
            }
        }
    }

    public static String[] processOptions(String[] strArr) {
        int i = 0;
        while (i != strArr.length) {
            String str = strArr[i];
            if (!str.startsWith("-")) {
                processStdin = false;
                fileList.add(str);
                mainModule = str;
                String[] strArr2 = new String[((strArr.length - i) - 1)];
                System.arraycopy(strArr, i + 1, strArr2, 0, (strArr.length - i) - 1);
                return strArr2;
            }
            Class<Main> cls = Main.class;
            if (str.equals("-version")) {
                i++;
                if (i != strArr.length) {
                    try {
                        int parseInt = Integer.parseInt(strArr[i]);
                        if (!Context.isValidLanguageVersion(parseInt)) {
                            str = strArr[i];
                        } else {
                            shellContextFactory.setLanguageVersion(parseInt);
                        }
                    } catch (NumberFormatException unused) {
                        str = strArr[i];
                    }
                }
                PrintStream out = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.invalid", str);
                out.getClass();
                PrintStream out2 = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                out2.getClass();
                exitCode = 1;
                return null;
            } else if (str.equals("-opt") || str.equals("-O")) {
                i++;
                if (i != strArr.length) {
                    try {
                        int parseInt2 = Integer.parseInt(strArr[i]);
                        if (parseInt2 == -2) {
                            parseInt2 = -1;
                        } else if (!Context.isValidOptimizationLevel(parseInt2)) {
                            str = strArr[i];
                        }
                        shellContextFactory.setOptimizationLevel(parseInt2);
                    } catch (NumberFormatException unused2) {
                        str = strArr[i];
                    }
                }
                PrintStream out3 = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.invalid", str);
                out3.getClass();
                PrintStream out22 = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                out22.getClass();
                exitCode = 1;
                return null;
            } else if (str.equals("-encoding")) {
                i++;
                if (i == strArr.length) {
                    PrintStream out32 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.invalid", str);
                    out32.getClass();
                    PrintStream out222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                    out222.getClass();
                    exitCode = 1;
                    return null;
                }
                shellContextFactory.setCharacterEncoding(strArr[i]);
            } else if (str.equals("-strict")) {
                shellContextFactory.setStrictMode(true);
                shellContextFactory.setAllowReservedKeywords(false);
                errorReporter.setIsReportingWarnings(true);
            } else if (str.equals("-fatal-warnings")) {
                shellContextFactory.setWarningAsError(true);
            } else if (str.equals("-e")) {
                processStdin = false;
                i++;
                if (i == strArr.length) {
                    PrintStream out322 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.invalid", str);
                    out322.getClass();
                    PrintStream out2222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                    out2222.getClass();
                    exitCode = 1;
                    return null;
                }
                Global global2 = global;
                if (!global2.initialized) {
                    global2.init((ContextFactory) shellContextFactory);
                }
                IProxy iProxy = new IProxy(2);
                iProxy.scriptText = strArr[i];
                shellContextFactory.call(iProxy);
            } else if (str.equals("-require")) {
                useRequire = true;
            } else if (str.equals("-sandbox")) {
                sandboxed = true;
                useRequire = true;
            } else if (str.equals("-modules")) {
                i++;
                if (i == strArr.length) {
                    PrintStream out3222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.invalid", str);
                    out3222.getClass();
                    PrintStream out22222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                    out22222.getClass();
                    exitCode = 1;
                    return null;
                }
                if (modulePath == null) {
                    modulePath = new ArrayList();
                }
                modulePath.add(strArr[i]);
                useRequire = true;
            } else if (str.equals("-w")) {
                errorReporter.setIsReportingWarnings(true);
            } else if (str.equals("-f")) {
                processStdin = false;
                i++;
                if (i == strArr.length) {
                    PrintStream out32222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.invalid", str);
                    out32222.getClass();
                    PrintStream out222222 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                    out222222.getClass();
                    exitCode = 1;
                    return null;
                } else if (strArr[i].equals("-")) {
                    fileList.add((Object) null);
                } else {
                    fileList.add(strArr[i]);
                    mainModule = strArr[i];
                }
            } else if (str.equals("-sealedlib")) {
                global.setSealedStdLib(true);
            } else if (str.equals("-debug")) {
                shellContextFactory.setGeneratingDebug(true);
            } else {
                if (str.equals("-?") || str.equals("-help")) {
                    PrintStream out4 = global.getOut();
                    ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                    out4.getClass();
                    exitCode = 1;
                    return null;
                }
                PrintStream out322222 = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.invalid", str);
                out322222.getClass();
                PrintStream out2222222 = global.getOut();
                ToolErrorReporter.getMessage("msg.shell.usage", cls.getName());
                out2222222.getClass();
                exitCode = 1;
                return null;
            }
            i++;
        }
        return new String[0];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00bd, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        org.mozilla.javascript.tools.ToolErrorReporter.reportException(r12.getErrorReporter(), r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d3, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00d4, code lost:
        r7.printStackTrace();
        org.mozilla.javascript.Context.reportError(org.mozilla.javascript.tools.ToolErrorReporter.getMessage("msg.uncaughtJSException", r7.toString()));
        exitCode = 3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d3 A[ExcHandler: VirtualMachineError (r7v2 'e' java.lang.VirtualMachineError A[CUSTOM_DECLARE]), Splitter:B:33:0x008f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void processSource(org.mozilla.javascript.Context r12, java.lang.String r13) throws java.io.IOException {
        /*
            if (r13 == 0) goto L_0x0027
            java.lang.String r0 = "-"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x000b
            goto L_0x0027
        L_0x000b:
            boolean r0 = useRequire
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = mainModule
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x001e
            org.mozilla.javascript.commonjs.module.Require r0 = require
            r0.requireMain(r12, r13)
            goto L_0x00fa
        L_0x001e:
            org.mozilla.javascript.Scriptable r0 = getScope(r13)
            processFile(r12, r0, r13)
            goto L_0x00fa
        L_0x0027:
            org.mozilla.javascript.Scriptable r0 = getShellScope()
            org.mozilla.javascript.tools.shell.ShellContextFactory r1 = shellContextFactory
            java.lang.String r1 = r1.getCharacterEncoding()
            if (r1 == 0) goto L_0x0038
            java.nio.charset.Charset r1 = java.nio.charset.Charset.forName(r1)
            goto L_0x003c
        L_0x0038:
            java.nio.charset.Charset r1 = java.nio.charset.Charset.defaultCharset()
        L_0x003c:
            org.mozilla.javascript.tools.shell.Global r2 = global
            org.mozilla.javascript.tools.shell.ShellConsole r1 = r2.getConsole(r1)
            if (r13 != 0) goto L_0x004b
            java.lang.String r2 = r12.getImplementationVersion()
            r1.println(r2)
        L_0x004b:
            r2 = 0
            r3 = 1
            r4 = 0
            r5 = 1
        L_0x004f:
            if (r4 != 0) goto L_0x00f4
            org.mozilla.javascript.tools.shell.Global r6 = global
            java.lang.String[] r6 = r6.getPrompts(r12)
            r7 = 0
            if (r13 != 0) goto L_0x005d
            r8 = r6[r2]
            goto L_0x005e
        L_0x005d:
            r8 = r7
        L_0x005e:
            r1.flush()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
        L_0x0066:
            java.lang.String r8 = r1.readLine(r8)     // Catch:{ IOException -> 0x0086 }
            if (r8 != 0) goto L_0x006e
            r4 = 1
            goto L_0x008e
        L_0x006e:
            r9.append(r8)
            r8 = 10
            r9.append(r8)
            int r5 = r5 + 1
            java.lang.String r8 = r9.toString()
            boolean r8 = r12.stringIsCompilableUnit(r8)
            if (r8 == 0) goto L_0x0083
            goto L_0x008e
        L_0x0083:
            r8 = r6[r3]
            goto L_0x0066
        L_0x0086:
            r6 = move-exception
            java.lang.String r6 = r6.toString()
            r1.println(r6)
        L_0x008e:
            r6 = 3
            java.lang.String r8 = r9.toString()     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            java.lang.String r10 = "<stdin>"
            org.mozilla.javascript.Script r7 = r12.compileString(r8, r10, r5, r7)     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            if (r7 == 0) goto L_0x004f
            java.lang.Object r7 = r7.exec(r12, r0)     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            java.lang.Object r10 = org.mozilla.javascript.Context.getUndefinedValue()     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            if (r7 == r10) goto L_0x00c5
            boolean r10 = r7 instanceof org.mozilla.javascript.Function     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            if (r10 == 0) goto L_0x00b5
            java.lang.String r8 = r8.trim()     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            java.lang.String r10 = "function"
            boolean r8 = r8.startsWith(r10)     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            if (r8 != 0) goto L_0x00c5
        L_0x00b5:
            java.lang.String r7 = org.mozilla.javascript.Context.toString(r7)     // Catch:{ RhinoException -> 0x00bd, VirtualMachineError -> 0x00d3 }
            r1.println(r7)     // Catch:{ RhinoException -> 0x00bd, VirtualMachineError -> 0x00d3 }
            goto L_0x00c5
        L_0x00bd:
            r7 = move-exception
            org.mozilla.javascript.ErrorReporter r8 = r12.getErrorReporter()     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            org.mozilla.javascript.tools.ToolErrorReporter.reportException(r8, r7)     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
        L_0x00c5:
            org.mozilla.javascript.tools.shell.Global r7 = global     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            org.mozilla.javascript.NativeArray r7 = r7.history     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            long r10 = r7.getLength()     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            int r8 = (int) r10     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            r7.put((int) r8, (org.mozilla.javascript.Scriptable) r7, (java.lang.Object) r9)     // Catch:{ RhinoException -> 0x00e8, VirtualMachineError -> 0x00d3 }
            goto L_0x004f
        L_0x00d3:
            r7 = move-exception
            r7.printStackTrace()
            java.lang.String r8 = "msg.uncaughtJSException"
            java.lang.String r7 = r7.toString()
            java.lang.String r7 = org.mozilla.javascript.tools.ToolErrorReporter.getMessage((java.lang.String) r8, (java.lang.String) r7)
            org.mozilla.javascript.Context.reportError(r7)
            exitCode = r6
            goto L_0x004f
        L_0x00e8:
            r7 = move-exception
            org.mozilla.javascript.ErrorReporter r8 = r12.getErrorReporter()
            org.mozilla.javascript.tools.ToolErrorReporter.reportException(r8, r7)
            exitCode = r6
            goto L_0x004f
        L_0x00f4:
            r1.println()
            r1.flush()
        L_0x00fa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Main.processSource(org.mozilla.javascript.Context, java.lang.String):void");
    }

    private static Object readFileOrUrl(String str, boolean z) throws IOException {
        return SourceReader.readFileOrUrl(str, z, shellContextFactory.getCharacterEncoding());
    }

    public static void setErr(PrintStream printStream) {
        getGlobal().setErr(printStream);
    }

    public static void setIn(InputStream inputStream) {
        getGlobal().setIn(inputStream);
    }

    public static void setOut(PrintStream printStream) {
        getGlobal().setOut(printStream);
    }
}
