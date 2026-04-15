package org.mozilla.javascript.tools.shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Synchronizer;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Global extends ImporterTopLevel {
    static final long serialVersionUID = 4029130780977538005L;
    boolean attemptedJLineLoad;
    private ShellConsole console;
    private HashMap<String, String> doctestCanonicalizations;
    private PrintStream errStream;
    NativeArray history;
    private InputStream inStream;
    boolean initialized;
    private PrintStream outStream;
    private String[] prompts = {"js> ", "  > "};
    private QuitAction quitAction;
    private boolean sealedStdLib = false;

    public Global() {
    }

    public static void defineClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> cls = getClass(objArr);
        if (Scriptable.class.isAssignableFrom(cls)) {
            ScriptableObject.defineClass(scriptable, cls);
            return;
        }
        throw reportRuntimeError("msg.must.implement.Scriptable");
    }

    public static Object deserialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException, ClassNotFoundException {
        if (objArr.length >= 1) {
            FileInputStream fileInputStream = new FileInputStream(Context.toString(objArr[0]));
            Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
            ScriptableInputStream scriptableInputStream = new ScriptableInputStream(fileInputStream, topLevelScope);
            Object readObject = scriptableInputStream.readObject();
            scriptableInputStream.close();
            return Context.toObject(readObject, topLevelScope);
        }
        throw Context.reportRuntimeError("Expected a filename to read the serialization from");
    }

    private static Object doPrint(Object[] objArr, Function function, boolean z) {
        PrintStream out = getInstance(function).getOut();
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                out.getClass();
            }
            Context.toString(objArr[i]);
            out.getClass();
        }
        if (z) {
            out.getClass();
        }
        return Context.getUndefinedValue();
    }

    public static Object doctest(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length == 0) {
            return Boolean.FALSE;
        }
        String context2 = Context.toString(objArr[0]);
        Global instance = getInstance(function);
        return Integer.valueOf(instance.runDoctest(context, instance, context2, (String) null, 0));
    }

    private boolean doctestOutputMatches(String str, String str2) {
        String trim = str.trim();
        String replace = str2.trim().replace("\r\n", "\n");
        if (trim.equals(replace)) {
            return true;
        }
        for (Map.Entry next : this.doctestCanonicalizations.entrySet()) {
            trim = trim.replace((CharSequence) next.getKey(), (CharSequence) next.getValue());
        }
        if (trim.equals(replace)) {
            return true;
        }
        Pattern compile = Pattern.compile("@[0-9a-fA-F]+");
        Matcher matcher = compile.matcher(trim);
        Matcher matcher2 = compile.matcher(replace);
        while (matcher.find() && matcher2.find() && matcher2.start() == matcher.start()) {
            int start = matcher.start();
            if (!trim.substring(0, start).equals(replace.substring(0, start))) {
                return false;
            }
            String group = matcher.group();
            String group2 = matcher2.group();
            String str3 = this.doctestCanonicalizations.get(group);
            if (str3 == null) {
                this.doctestCanonicalizations.put(group, group2);
                trim = trim.replace(group, group2);
            } else if (!group2.equals(str3)) {
                return false;
            }
            if (trim.equals(replace)) {
                return true;
            }
        }
        return false;
    }

    public static void gc(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        System.gc();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004a A[LOOP:3: B:22:0x004a->B:24:0x0052, LOOP_START, PHI: r1 
      PHI: (r1v1 int) = (r1v0 int), (r1v2 int) binds: [B:21:0x0048, B:24:0x0052] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getCharCodingFromType(java.lang.String r9) {
        /*
            r0 = 59
            int r0 = r9.indexOf(r0)
            if (r0 < 0) goto L_0x005a
            int r1 = r9.length()
        L_0x000c:
            int r0 = r0 + 1
            r8 = 32
            if (r0 == r1) goto L_0x0019
            char r2 = r9.charAt(r0)
            if (r2 > r8) goto L_0x0019
            goto L_0x000c
        L_0x0019:
            java.lang.String r2 = "charset"
            r3 = 1
            r4 = 0
            r7 = 7
            r5 = r9
            r6 = r0
            boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x005a
            int r0 = r0 + 7
        L_0x0028:
            if (r0 == r1) goto L_0x0033
            char r2 = r9.charAt(r0)
            if (r2 > r8) goto L_0x0033
            int r0 = r0 + 1
            goto L_0x0028
        L_0x0033:
            if (r0 == r1) goto L_0x005a
            char r2 = r9.charAt(r0)
            r3 = 61
            if (r2 != r3) goto L_0x005a
        L_0x003d:
            int r0 = r0 + 1
            if (r0 == r1) goto L_0x0048
            char r2 = r9.charAt(r0)
            if (r2 > r8) goto L_0x0048
            goto L_0x003d
        L_0x0048:
            if (r0 == r1) goto L_0x005a
        L_0x004a:
            int r2 = r1 + -1
            char r2 = r9.charAt(r2)
            if (r2 > r8) goto L_0x0055
            int r1 = r1 + -1
            goto L_0x004a
        L_0x0055:
            java.lang.String r9 = r9.substring(r0, r1)
            return r9
        L_0x005a:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.getCharCodingFromType(java.lang.String):java.lang.String");
    }

    private static Class<?> getClass(Object[] objArr) {
        if (objArr.length != 0) {
            Wrapper wrapper = objArr[0];
            if (wrapper instanceof Wrapper) {
                Object unwrap = wrapper.unwrap();
                if (unwrap instanceof Class) {
                    return (Class) unwrap;
                }
            }
            String context = Context.toString(objArr[0]);
            try {
                return Class.forName(context);
            } catch (ClassNotFoundException unused) {
                throw reportRuntimeError("msg.class.not.found", context);
            }
        } else {
            throw reportRuntimeError("msg.expected.string.arg");
        }
    }

    private static Global getInstance(Function function) {
        Scriptable parentScope = function.getParentScope();
        if (parentScope instanceof Global) {
            return (Global) parentScope;
        }
        throw reportRuntimeError("msg.bad.shell.function.scope", String.valueOf(parentScope));
    }

    public static void help(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        PrintStream out = getInstance(function).getOut();
        ToolErrorReporter.getMessage("msg.help");
        out.getClass();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ Object lambda$init$0(Context context) {
        init(context);
        return null;
    }

    public static void load(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            String context2 = Context.toString(objArr[i]);
            try {
                Main.processFile(context, scriptable, context2);
                i++;
            } catch (IOException e) {
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.couldnt.read.source", context2, e.getMessage()));
            } catch (VirtualMachineError e2) {
                e2.printStackTrace();
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e2.toString()));
            }
        }
    }

    public static void loadClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException {
        Class<?> cls = getClass(objArr);
        if (Script.class.isAssignableFrom(cls)) {
            ((Script) cls.newInstance()).exec(context, scriptable);
            return;
        }
        throw reportRuntimeError("msg.must.implement.Script");
    }

    private boolean loadJLine(Charset charset) {
        if (!this.attemptedJLineLoad) {
            this.attemptedJLineLoad = true;
            this.console = ShellConsole.getConsole(this, charset);
        }
        if (this.console != null) {
            return true;
        }
        return false;
    }

    public static void pipe(boolean z, InputStream inputStream, OutputStream outputStream) throws IOException {
        int i;
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                if (!z) {
                    i = inputStream.read(bArr, 0, 4096);
                } else {
                    try {
                        i = inputStream.read(bArr, 0, 4096);
                    } catch (IOException unused) {
                    }
                }
                if (i < 0) {
                    break;
                } else if (z) {
                    outputStream.write(bArr, 0, i);
                    outputStream.flush();
                } else {
                    outputStream.write(bArr, 0, i);
                    outputStream.flush();
                }
            }
            if (z) {
            } else {
                outputStream.close();
            }
        } finally {
            if (z) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            } else {
                outputStream.close();
            }
        }
    }

    public static Object print(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, true);
    }

    public static void quit(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Global instance = getInstance(function);
        if (instance.quitAction != null) {
            int i = 0;
            if (objArr.length != 0) {
                i = ScriptRuntime.toInt32(objArr[0]);
            }
            instance.quitAction.quit(context, i);
        }
    }

    public static Object readFile(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        String str;
        if (objArr.length != 0) {
            String scriptRuntime = ScriptRuntime.toString(objArr[0]);
            if (objArr.length >= 2) {
                str = ScriptRuntime.toString(objArr[1]);
            } else {
                str = null;
            }
            return readUrl(scriptRuntime, str, true);
        }
        throw reportRuntimeError("msg.shell.readFile.bad.args");
    }

    private static String readReader(Reader reader) throws IOException {
        return readReader(reader, 4096);
    }

    public static Object readUrl(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length != 0) {
            return readUrl(ScriptRuntime.toString(objArr[0]), objArr.length >= 2 ? ScriptRuntime.toString(objArr[1]) : null, false);
        }
        throw reportRuntimeError("msg.shell.readUrl.bad.args");
    }

    public static Object readline(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        Global instance = getInstance(function);
        if (objArr.length > 0) {
            return instance.console.readLine(Context.toString(objArr[0]));
        }
        return instance.console.readLine();
    }

    public static RuntimeException reportRuntimeError(String str) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v15, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: java.io.OutputStream} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00e6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object runCommand(org.mozilla.javascript.Context r20, org.mozilla.javascript.Scriptable r21, java.lang.Object[] r22, org.mozilla.javascript.Function r23) throws java.io.IOException {
        /*
            r0 = r22
            int r1 = r0.length
            if (r1 == 0) goto L_0x0187
            r2 = 0
            r3 = 1
            if (r1 != r3) goto L_0x000f
            r3 = r0[r2]
            boolean r3 = r3 instanceof org.mozilla.javascript.Scriptable
            if (r3 != 0) goto L_0x0187
        L_0x000f:
            int r3 = r1 + -1
            r3 = r0[r3]
            boolean r4 = r3 instanceof org.mozilla.javascript.Scriptable
            java.lang.String r5 = "err"
            java.lang.String r6 = "output"
            r7 = 0
            if (r4 == 0) goto L_0x00ff
            org.mozilla.javascript.Scriptable r3 = (org.mozilla.javascript.Scriptable) r3
            int r1 = r1 + -1
            java.lang.String r4 = "env"
            java.lang.Object r4 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r4)
            java.lang.Object r8 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r4 == r8) goto L_0x008c
            if (r4 != 0) goto L_0x002f
            java.lang.String[] r4 = new java.lang.String[r2]
            goto L_0x008d
        L_0x002f:
            boolean r8 = r4 instanceof org.mozilla.javascript.Scriptable
            if (r8 == 0) goto L_0x0085
            org.mozilla.javascript.Scriptable r4 = (org.mozilla.javascript.Scriptable) r4
            java.lang.Object[] r8 = org.mozilla.javascript.ScriptableObject.getPropertyIds(r4)
            int r9 = r8.length
            java.lang.String[] r9 = new java.lang.String[r9]
            r10 = 0
        L_0x003d:
            int r11 = r8.length
            if (r10 == r11) goto L_0x0083
            r11 = r8[r10]
            boolean r12 = r11 instanceof java.lang.String
            if (r12 == 0) goto L_0x004d
            java.lang.String r11 = (java.lang.String) r11
            java.lang.Object r12 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r4, (java.lang.String) r11)
            goto L_0x0060
        L_0x004d:
            java.lang.Number r11 = (java.lang.Number) r11
            int r11 = r11.intValue()
            java.lang.String r12 = java.lang.Integer.toString(r11)
            java.lang.Object r11 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r4, (int) r11)
            r19 = r12
            r12 = r11
            r11 = r19
        L_0x0060:
            java.lang.Object r13 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r12 != r13) goto L_0x0066
            java.lang.Object r12 = org.mozilla.javascript.Undefined.instance
        L_0x0066:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r11)
            r11 = 61
            r13.append(r11)
            java.lang.String r11 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r12)
            r13.append(r11)
            java.lang.String r11 = r13.toString()
            r9[r10] = r11
            int r10 = r10 + 1
            goto L_0x003d
        L_0x0083:
            r4 = r9
            goto L_0x008d
        L_0x0085:
            java.lang.String r0 = "msg.runCommand.bad.env"
            java.lang.RuntimeException r0 = reportRuntimeError(r0)
            throw r0
        L_0x008c:
            r4 = r7
        L_0x008d:
            java.lang.String r8 = "dir"
            java.lang.Object r8 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r8)
            java.lang.Object r9 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r8 == r9) goto L_0x00a1
            java.io.File r10 = new java.io.File
            java.lang.String r8 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r8)
            r10.<init>(r8)
            goto L_0x00a2
        L_0x00a1:
            r10 = r7
        L_0x00a2:
            java.lang.String r8 = "input"
            java.lang.Object r8 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r8)
            if (r8 == r9) goto L_0x00af
            java.io.InputStream r8 = toInputStream(r8)
            goto L_0x00b0
        L_0x00af:
            r8 = r7
        L_0x00b0:
            java.lang.Object r11 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r6)
            if (r11 == r9) goto L_0x00c4
            java.io.OutputStream r12 = toOutputStream(r11)
            if (r12 != 0) goto L_0x00c2
            java.io.ByteArrayOutputStream r12 = new java.io.ByteArrayOutputStream
            r12.<init>()
            goto L_0x00c5
        L_0x00c2:
            r13 = r7
            goto L_0x00c6
        L_0x00c4:
            r12 = r7
        L_0x00c5:
            r13 = r12
        L_0x00c6:
            java.lang.Object r14 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r5)
            if (r14 == r9) goto L_0x00db
            java.io.OutputStream r15 = toOutputStream(r14)
            if (r15 != 0) goto L_0x00d8
            java.io.ByteArrayOutputStream r15 = new java.io.ByteArrayOutputStream
            r15.<init>()
            goto L_0x00dc
        L_0x00d8:
            r16 = r7
            goto L_0x00de
        L_0x00db:
            r15 = r7
        L_0x00dc:
            r16 = r15
        L_0x00de:
            java.lang.String r2 = "args"
            java.lang.Object r2 = org.mozilla.javascript.ScriptableObject.getProperty((org.mozilla.javascript.Scriptable) r3, (java.lang.String) r2)
            if (r2 == r9) goto L_0x00f4
            org.mozilla.javascript.Scriptable r7 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r21)
            org.mozilla.javascript.Scriptable r2 = org.mozilla.javascript.Context.toObject(r2, r7)
            r7 = r20
            java.lang.Object[] r7 = r7.getElements(r2)
        L_0x00f4:
            r9 = r4
            r2 = r7
            r4 = r11
            r7 = r12
            r11 = r8
            r19 = r13
            r13 = r3
            r3 = r19
            goto L_0x010a
        L_0x00ff:
            r2 = r7
            r3 = r2
            r4 = r3
            r9 = r4
            r10 = r9
            r11 = r10
            r13 = r11
            r14 = r13
            r15 = r14
            r16 = r15
        L_0x010a:
            org.mozilla.javascript.tools.shell.Global r8 = getInstance(r23)
            if (r7 != 0) goto L_0x0114
            java.io.PrintStream r7 = r8.getOut()
        L_0x0114:
            r12 = r7
            if (r15 != 0) goto L_0x011b
            java.io.PrintStream r15 = r8.getErr()
        L_0x011b:
            if (r2 != 0) goto L_0x011f
            r7 = r1
            goto L_0x0121
        L_0x011f:
            int r7 = r2.length
            int r7 = r7 + r1
        L_0x0121:
            java.lang.String[] r8 = new java.lang.String[r7]
            r7 = 0
        L_0x0124:
            if (r7 == r1) goto L_0x0131
            r18 = r0[r7]
            java.lang.String r18 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r18)
            r8[r7] = r18
            int r7 = r7 + 1
            goto L_0x0124
        L_0x0131:
            if (r2 == 0) goto L_0x0144
            r0 = 0
        L_0x0134:
            int r7 = r2.length
            if (r0 == r7) goto L_0x0144
            int r7 = r1 + r0
            r17 = r2[r0]
            java.lang.String r17 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r17)
            r8[r7] = r17
            int r0 = r0 + 1
            goto L_0x0134
        L_0x0144:
            r7 = r13
            r13 = r15
            int r0 = runProcess(r8, r9, r10, r11, r12, r13)
            if (r3 == 0) goto L_0x0166
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r4)
            r1.append(r2)
            java.lang.String r2 = r3.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r7, (java.lang.String) r6, (java.lang.Object) r1)
        L_0x0166:
            if (r16 == 0) goto L_0x0182
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r14)
            r1.append(r2)
            java.lang.String r2 = r16.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.mozilla.javascript.ScriptableObject.putProperty((org.mozilla.javascript.Scriptable) r7, (java.lang.String) r5, (java.lang.Object) r1)
        L_0x0182:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            return r0
        L_0x0187:
            java.lang.String r0 = "msg.runCommand.bad.args"
            java.lang.RuntimeException r0 = reportRuntimeError(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.runCommand(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[], org.mozilla.javascript.Function):java.lang.Object");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(15:0|(1:2)(1:3)|(2:5|6)(1:7)|8|(1:10)(1:11)|(1:13)(1:14)|15|16|(1:18)|(1:20)|(1:22)|23|24|25|26) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x005a */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005f A[Catch:{ InterruptedException -> 0x005a }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0064 A[Catch:{ InterruptedException -> 0x005a }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0069 A[Catch:{ InterruptedException -> 0x005a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int runProcess(java.lang.String[] r2, java.lang.String[] r3, java.io.File r4, java.io.InputStream r5, java.io.OutputStream r6, java.io.OutputStream r7) throws java.io.IOException {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x000c
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()
            java.lang.Process r2 = r3.exec(r2, r0, r4)
            goto L_0x0014
        L_0x000c:
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()
            java.lang.Process r2 = r1.exec(r2, r3, r4)
        L_0x0014:
            if (r5 == 0) goto L_0x0024
            org.mozilla.javascript.tools.shell.PipeThread r3 = new org.mozilla.javascript.tools.shell.PipeThread     // Catch:{ all -> 0x0074 }
            java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ all -> 0x0074 }
            r1 = 0
            r3.<init>(r1, r5, r4)     // Catch:{ all -> 0x0074 }
            r3.start()     // Catch:{ all -> 0x0074 }
            goto L_0x002c
        L_0x0024:
            java.io.OutputStream r3 = r2.getOutputStream()     // Catch:{ all -> 0x0074 }
            r3.close()     // Catch:{ all -> 0x0074 }
            r3 = r0
        L_0x002c:
            r4 = 1
            if (r6 == 0) goto L_0x003c
            org.mozilla.javascript.tools.shell.PipeThread r5 = new org.mozilla.javascript.tools.shell.PipeThread     // Catch:{ all -> 0x0074 }
            java.io.InputStream r1 = r2.getInputStream()     // Catch:{ all -> 0x0074 }
            r5.<init>(r4, r1, r6)     // Catch:{ all -> 0x0074 }
            r5.start()     // Catch:{ all -> 0x0074 }
            goto L_0x0044
        L_0x003c:
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ all -> 0x0074 }
            r5.close()     // Catch:{ all -> 0x0074 }
            r5 = r0
        L_0x0044:
            if (r7 == 0) goto L_0x0053
            org.mozilla.javascript.tools.shell.PipeThread r0 = new org.mozilla.javascript.tools.shell.PipeThread     // Catch:{ all -> 0x0074 }
            java.io.InputStream r6 = r2.getErrorStream()     // Catch:{ all -> 0x0074 }
            r0.<init>(r4, r6, r7)     // Catch:{ all -> 0x0074 }
            r0.start()     // Catch:{ all -> 0x0074 }
            goto L_0x005a
        L_0x0053:
            java.io.InputStream r4 = r2.getErrorStream()     // Catch:{ all -> 0x0074 }
            r4.close()     // Catch:{ all -> 0x0074 }
        L_0x005a:
            r2.waitFor()     // Catch:{ InterruptedException -> 0x005a }
            if (r5 == 0) goto L_0x0062
            r5.join()     // Catch:{ InterruptedException -> 0x005a }
        L_0x0062:
            if (r3 == 0) goto L_0x0067
            r3.join()     // Catch:{ InterruptedException -> 0x005a }
        L_0x0067:
            if (r0 == 0) goto L_0x006c
            r0.join()     // Catch:{ InterruptedException -> 0x005a }
        L_0x006c:
            int r3 = r2.exitValue()     // Catch:{ all -> 0x0074 }
            r2.destroy()
            return r3
        L_0x0074:
            r3 = move-exception
            r2.destroy()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.runProcess(java.lang.String[], java.lang.String[], java.io.File, java.io.InputStream, java.io.OutputStream, java.io.OutputStream):int");
    }

    public static void seal(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        int i = 0;
        while (i != objArr.length) {
            Object obj = objArr[i];
            if ((obj instanceof ScriptableObject) && obj != Undefined.instance) {
                i++;
            } else if (!(obj instanceof Scriptable) || obj == Undefined.instance) {
                throw reportRuntimeError("msg.shell.seal.not.object");
            } else {
                throw reportRuntimeError("msg.shell.seal.not.scriptable");
            }
        }
        for (int i2 = 0; i2 != objArr.length; i2++) {
            objArr[i2].sealObject();
        }
    }

    public static void serialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length >= 2) {
            Object obj = objArr[0];
            ScriptableOutputStream scriptableOutputStream = new ScriptableOutputStream(new FileOutputStream(Context.toString(objArr[1])), ScriptableObject.getTopLevelScope(scriptable));
            scriptableOutputStream.writeObject(obj);
            scriptableOutputStream.close();
            return;
        }
        throw Context.reportRuntimeError("Expected an object to serialize and a filename to write the serialization to");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object spawn(org.mozilla.javascript.Context r2, org.mozilla.javascript.Scriptable r3, java.lang.Object[] r4, org.mozilla.javascript.Function r5) {
        /*
            org.mozilla.javascript.Scriptable r3 = r5.getParentScope()
            int r5 = r4.length
            r0 = 0
            if (r5 == 0) goto L_0x002e
            r5 = r4[r0]
            boolean r5 = r5 instanceof org.mozilla.javascript.Function
            if (r5 == 0) goto L_0x002e
            int r5 = r4.length
            r1 = 1
            if (r5 <= r1) goto L_0x001f
            r5 = r4[r1]
            boolean r1 = r5 instanceof org.mozilla.javascript.Scriptable
            if (r1 == 0) goto L_0x001f
            org.mozilla.javascript.Scriptable r5 = (org.mozilla.javascript.Scriptable) r5
            java.lang.Object[] r5 = r2.getElements(r5)
            goto L_0x0020
        L_0x001f:
            r5 = 0
        L_0x0020:
            if (r5 != 0) goto L_0x0024
            java.lang.Object[] r5 = org.mozilla.javascript.ScriptRuntime.emptyArgs
        L_0x0024:
            org.mozilla.javascript.tools.shell.Runner r1 = new org.mozilla.javascript.tools.shell.Runner
            r4 = r4[r0]
            org.mozilla.javascript.Function r4 = (org.mozilla.javascript.Function) r4
            r1.<init>(r3, r4, r5)
            goto L_0x003e
        L_0x002e:
            int r5 = r4.length
            if (r5 == 0) goto L_0x004d
            r4 = r4[r0]
            boolean r5 = r4 instanceof org.mozilla.javascript.Script
            if (r5 == 0) goto L_0x004d
            org.mozilla.javascript.tools.shell.Runner r1 = new org.mozilla.javascript.tools.shell.Runner
            org.mozilla.javascript.Script r4 = (org.mozilla.javascript.Script) r4
            r1.<init>(r3, r4)
        L_0x003e:
            org.mozilla.javascript.ContextFactory r2 = r2.getFactory()
            r1.factory = r2
            java.lang.Thread r2 = new java.lang.Thread
            r2.<init>(r1)
            r2.start()
            return r2
        L_0x004d:
            java.lang.String r2 = "msg.spawn.args"
            java.lang.RuntimeException r2 = reportRuntimeError(r2)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.spawn(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[], org.mozilla.javascript.Function):java.lang.Object");
    }

    public static Object sync(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Object obj;
        if (objArr.length >= 1 && objArr.length <= 2) {
            Function function2 = objArr[0];
            if (function2 instanceof Function) {
                if (objArr.length != 2 || (obj = objArr[1]) == Undefined.instance) {
                    obj = null;
                }
                return new Synchronizer(function2, obj);
            }
        }
        throw reportRuntimeError("msg.sync.args");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.io.InputStream toInputStream(java.lang.Object r4) throws java.io.IOException {
        /*
            boolean r0 = r4 instanceof org.mozilla.javascript.Wrapper
            r1 = 0
            if (r0 == 0) goto L_0x003c
            r0 = r4
            org.mozilla.javascript.Wrapper r0 = (org.mozilla.javascript.Wrapper) r0
            java.lang.Object r0 = r0.unwrap()
            boolean r2 = r0 instanceof java.io.InputStream
            if (r2 == 0) goto L_0x0016
            java.io.InputStream r0 = (java.io.InputStream) r0
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x003d
        L_0x0016:
            boolean r2 = r0 instanceof byte[]
            if (r2 == 0) goto L_0x0024
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            byte[] r0 = (byte[]) r0
            r2.<init>(r0)
            r0 = r1
            r1 = r2
            goto L_0x003d
        L_0x0024:
            boolean r2 = r0 instanceof java.io.Reader
            if (r2 == 0) goto L_0x002f
            java.io.Reader r0 = (java.io.Reader) r0
            java.lang.String r0 = readReader(r0)
            goto L_0x003d
        L_0x002f:
            boolean r2 = r0 instanceof char[]
            if (r2 == 0) goto L_0x003c
            java.lang.String r2 = new java.lang.String
            char[] r0 = (char[]) r0
            r2.<init>(r0)
            r0 = r2
            goto L_0x003d
        L_0x003c:
            r0 = r1
        L_0x003d:
            if (r1 != 0) goto L_0x004e
            if (r0 != 0) goto L_0x0045
            java.lang.String r0 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r4)
        L_0x0045:
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream
            byte[] r4 = r0.getBytes()
            r1.<init>(r4)
        L_0x004e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.toInputStream(java.lang.Object):java.io.InputStream");
    }

    private static OutputStream toOutputStream(Object obj) {
        if (obj instanceof Wrapper) {
            Object unwrap = ((Wrapper) obj).unwrap();
            if (unwrap instanceof OutputStream) {
                return (OutputStream) unwrap;
            }
        }
        return null;
    }

    public static Object toint32(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Object obj;
        if (objArr.length != 0) {
            obj = objArr[0];
        } else {
            obj = Undefined.instance;
        }
        if (obj instanceof Integer) {
            return obj;
        }
        return ScriptRuntime.wrapInt(ScriptRuntime.toInt32(obj));
    }

    public static double version(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length > 0) {
            context.setLanguageVersion((int) Context.toNumber(objArr[0]));
        }
        return (double) context.getLanguageVersion();
    }

    public static Object write(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, false);
    }

    public ShellConsole getConsole(Charset charset) {
        if (!loadJLine(charset)) {
            this.console = ShellConsole.getConsole(getIn(), getErr(), charset);
        }
        return this.console;
    }

    public PrintStream getErr() {
        PrintStream printStream = this.errStream;
        return printStream == null ? System.err : printStream;
    }

    public InputStream getIn() {
        if (this.inStream == null && !this.attemptedJLineLoad && loadJLine(Charset.defaultCharset())) {
            this.inStream = this.console.getIn();
        }
        InputStream inputStream = this.inStream;
        if (inputStream == null) {
            return System.in;
        }
        return inputStream;
    }

    public PrintStream getOut() {
        PrintStream printStream = this.outStream;
        return printStream == null ? System.out : printStream;
    }

    public String[] getPrompts(Context context) {
        if (ScriptableObject.hasProperty((Scriptable) this, "prompts")) {
            Object property = ScriptableObject.getProperty((Scriptable) this, "prompts");
            if (property instanceof Scriptable) {
                Scriptable scriptable = (Scriptable) property;
                if (ScriptableObject.hasProperty(scriptable, 0) && ScriptableObject.hasProperty(scriptable, 1)) {
                    Object property2 = ScriptableObject.getProperty(scriptable, 0);
                    if (property2 instanceof Function) {
                        property2 = ((Function) property2).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[0] = Context.toString(property2);
                    Object property3 = ScriptableObject.getProperty(scriptable, 1);
                    if (property3 instanceof Function) {
                        property3 = ((Function) property3).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[1] = Context.toString(property3);
                }
            }
        }
        return this.prompts;
    }

    public void init(ContextFactory contextFactory) {
        contextFactory.call(new i2(14, this));
    }

    public void initQuitAction(QuitAction quitAction2) {
        if (quitAction2 == null) {
            throw new IllegalArgumentException("quitAction is null");
        } else if (this.quitAction == null) {
            this.quitAction = quitAction2;
        } else {
            throw new IllegalArgumentException("The method is once-call.");
        }
    }

    public Require installRequire(Context context, List<String> list, boolean z) {
        RequireBuilder requireBuilder = new RequireBuilder();
        requireBuilder.setSandboxed(z);
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (String next : list) {
                try {
                    URI uri = new URI(next);
                    if (!uri.isAbsolute()) {
                        uri = new File(next).toURI().resolve("");
                    }
                    if (!uri.toString().endsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                        uri = new URI(uri + MqttTopic.TOPIC_LEVEL_SEPARATOR);
                    }
                    arrayList.add(uri);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        requireBuilder.setModuleScriptProvider(new SoftCachingModuleScriptProvider(new UrlModuleSourceProvider(arrayList, (Iterable<URI>) null)));
        Require createRequire = requireBuilder.createRequire(context, this);
        createRequire.install(this);
        return createRequire;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x01bc A[LOOP:1: B:7:0x003d->B:54:0x01bc, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x018d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int runDoctest(org.mozilla.javascript.Context r24, org.mozilla.javascript.Scriptable r25, java.lang.String r26, java.lang.String r27, int r28) {
        /*
            r23 = this;
            r1 = r23
            r8 = r24
            r9 = r27
            java.lang.String r10 = ""
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1.doctestCanonicalizations = r0
            java.lang.String r0 = "\r\n?|\n"
            r2 = r26
            java.lang.String[] r11 = r2.split(r0)
            java.lang.String[] r0 = r1.prompts
            r2 = 0
            r0 = r0[r2]
            java.lang.String r12 = r0.trim()
            java.lang.String[] r0 = r1.prompts
            r3 = 1
            r0 = r0[r3]
            java.lang.String r13 = r0.trim()
            r0 = 0
        L_0x002a:
            int r3 = r11.length
            if (r0 >= r3) goto L_0x003c
            r3 = r11[r0]
            java.lang.String r3 = r3.trim()
            boolean r3 = r3.startsWith(r12)
            if (r3 != 0) goto L_0x003c
            int r0 = r0 + 1
            goto L_0x002a
        L_0x003c:
            r3 = 0
        L_0x003d:
            int r4 = r11.length
            if (r0 >= r4) goto L_0x01d8
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r4 = r11[r0]
            java.lang.String r4 = r4.trim()
            int r5 = r12.length()
            java.lang.String r4 = r4.substring(r5)
            r14.<init>(r4)
            r4 = 10
            r14.append(r4)
        L_0x0058:
            int r0 = r0 + 1
            int r5 = r11.length
            if (r0 >= r5) goto L_0x007e
            r5 = r11[r0]
            java.lang.String r5 = r5.trim()
            boolean r5 = r5.startsWith(r13)
            if (r5 == 0) goto L_0x007e
            r5 = r11[r0]
            java.lang.String r5 = r5.trim()
            int r6 = r13.length()
            java.lang.String r5 = r5.substring(r6)
            r14.append(r5)
            r14.append(r4)
            goto L_0x0058
        L_0x007e:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r7 = r0
        L_0x0084:
            int r0 = r11.length
            if (r7 >= r0) goto L_0x009e
            r0 = r11[r7]
            java.lang.String r0 = r0.trim()
            boolean r0 = r0.startsWith(r12)
            if (r0 != 0) goto L_0x009e
            r0 = r11[r7]
            r15.append(r0)
            r15.append(r4)
            int r7 = r7 + 1
            goto L_0x0084
        L_0x009e:
            java.io.PrintStream r6 = r23.getOut()
            java.io.PrintStream r5 = r23.getErr()
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>()
            r26 = r7
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream
            r7.<init>()
            java.io.PrintStream r0 = new java.io.PrintStream
            r0.<init>(r4)
            r1.setOut(r0)
            java.io.PrintStream r0 = new java.io.PrintStream
            r0.<init>(r7)
            r1.setErr(r0)
            r16 = r7
            org.mozilla.javascript.ErrorReporter r7 = r24.getErrorReporter()
            org.mozilla.javascript.tools.ToolErrorReporter r0 = new org.mozilla.javascript.tools.ToolErrorReporter
            r17 = r4
            java.io.PrintStream r4 = r23.getErr()
            r0.<init>(r2, r4)
            r8.setErrorReporter(r0)
            int r18 = r3 + 1
            java.lang.String r0 = r14.toString()     // Catch:{ RhinoException -> 0x0150, all -> 0x0148 }
            java.lang.String r19 = "doctest input"
            r20 = 1
            r21 = 0
            r2 = r24
            r3 = r25
            r4 = r0
            r22 = r5
            r5 = r19
            r19 = r11
            r11 = r6
            r6 = r20
            r20 = r16
            r16 = r26
            r26 = r12
            r12 = r7
            r7 = r21
            java.lang.Object r2 = r2.evaluateString(r3, r4, r5, r6, r7)     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            java.lang.Object r3 = org.mozilla.javascript.Context.getUndefinedValue()     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            if (r2 == r3) goto L_0x0118
            boolean r3 = r2 instanceof org.mozilla.javascript.Function     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            if (r3 == 0) goto L_0x0113
            java.lang.String r0 = r0.trim()     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            java.lang.String r3 = "function"
            boolean r0 = r0.startsWith(r3)     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            if (r0 != 0) goto L_0x0118
        L_0x0113:
            java.lang.String r0 = org.mozilla.javascript.Context.toString(r2)     // Catch:{ RhinoException -> 0x0144, all -> 0x013f }
            goto L_0x0119
        L_0x0118:
            r0 = r10
        L_0x0119:
            r1.setOut(r11)
            r2 = r22
            r1.setErr(r2)
            r8.setErrorReporter(r12)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = r20.toString()
            r2.append(r0)
            java.lang.String r0 = r17.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            goto L_0x0183
        L_0x013f:
            r0 = move-exception
            r2 = r22
            goto L_0x01c8
        L_0x0144:
            r0 = move-exception
            r2 = r22
            goto L_0x015c
        L_0x0148:
            r0 = move-exception
            r2 = r5
            r11 = r6
            r12 = r7
            r20 = r16
            goto L_0x01c8
        L_0x0150:
            r0 = move-exception
            r2 = r5
            r19 = r11
            r20 = r16
            r16 = r26
            r11 = r6
            r26 = r12
            r12 = r7
        L_0x015c:
            org.mozilla.javascript.ErrorReporter r3 = r24.getErrorReporter()     // Catch:{ all -> 0x01c7 }
            org.mozilla.javascript.tools.ToolErrorReporter.reportException(r3, r0)     // Catch:{ all -> 0x01c7 }
            r1.setOut(r11)
            r1.setErr(r2)
            r8.setErrorReporter(r12)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r10)
            java.lang.String r2 = r20.toString()
            r0.append(r2)
            java.lang.String r2 = r17.toString()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
        L_0x0183:
            java.lang.String r2 = r15.toString()
            boolean r2 = r1.doctestOutputMatches(r2, r0)
            if (r2 != 0) goto L_0x01bc
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "doctest failure running:\n"
            r2.<init>(r3)
            r2.append(r14)
            java.lang.String r3 = "expected: "
            r2.append(r3)
            r2.append(r15)
            java.lang.String r3 = "actual: "
            r2.append(r3)
            java.lang.String r3 = "\n"
            java.lang.String r0 = defpackage.y2.k(r2, r0, r3)
            if (r9 == 0) goto L_0x01b7
            int r2 = r28 + r16
            int r2 = r2 + -1
            r3 = 0
            r4 = 0
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError(r0, r9, r2, r3, r4)
            throw r0
        L_0x01b7:
            org.mozilla.javascript.EvaluatorException r0 = org.mozilla.javascript.Context.reportRuntimeError(r0)
            throw r0
        L_0x01bc:
            r2 = 0
            r12 = r26
            r0 = r16
            r3 = r18
            r11 = r19
            goto L_0x003d
        L_0x01c7:
            r0 = move-exception
        L_0x01c8:
            r1.setOut(r11)
            r1.setErr(r2)
            r8.setErrorReporter(r12)
            r20.toString()
            r17.toString()
            throw r0
        L_0x01d8:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.runDoctest(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.String, java.lang.String, int):int");
    }

    public void setErr(PrintStream printStream) {
        this.errStream = printStream;
    }

    public void setIn(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public void setOut(PrintStream printStream) {
        this.outStream = printStream;
    }

    public void setSealedStdLib(boolean z) {
        this.sealedStdLib = z;
    }

    private static String readReader(Reader reader, int i) throws IOException {
        char[] cArr = new char[i];
        int i2 = 0;
        while (true) {
            int read = reader.read(cArr, i2, cArr.length - i2);
            if (read < 0) {
                return new String(cArr, 0, i2);
            }
            i2 += read;
            if (i2 == cArr.length) {
                char[] cArr2 = new char[(cArr.length * 2)];
                System.arraycopy(cArr, 0, cArr2, 0, i2);
                cArr = cArr2;
            }
        }
    }

    public void init(Context context) {
        Context context2 = context;
        initStandardObjects(context2, this.sealedStdLib);
        defineFunctionProperties(new String[]{"defineClass", "deserialize", "doctest", "gc", "help", "load", "loadClass", "print", "quit", "readline", "readFile", "readUrl", "runCommand", "seal", "serialize", "spawn", "sync", "toint32", "version", "write"}, Global.class, 2);
        Environment.defineClass(this);
        defineProperty("environment", (Object) new Environment(this), 2);
        NativeArray nativeArray = (NativeArray) context2.newArray((Scriptable) this, 0);
        this.history = nativeArray;
        defineProperty("history", (Object) nativeArray, 2);
        this.initialized = true;
    }

    public static RuntimeException reportRuntimeError(String str, String str2) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str, str2));
    }

    public Global(Context context) {
        init(context);
    }

    private static String readUrl(String str, String str2, boolean z) throws IOException {
        int i;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        String contentType;
        InputStream inputStream2 = null;
        if (!z) {
            try {
                URLConnection openConnection = new URL(str).openConnection();
                inputStream = openConnection.getInputStream();
                i = openConnection.getContentLength();
                if (i <= 0) {
                    i = 1024;
                }
                if (str2 == null && (contentType = openConnection.getContentType()) != null) {
                    str2 = getCharCodingFromType(contentType);
                }
            } catch (Throwable th) {
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                throw th;
            }
        } else {
            File file = new File(str);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + str);
            } else if (file.canRead()) {
                long length = file.length();
                int i2 = (int) length;
                if (((long) i2) != length) {
                    throw new IOException("Too big file size: " + length);
                } else if (i2 == 0) {
                    return "";
                } else {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    i = i2;
                    inputStream = fileInputStream;
                }
            } else {
                throw new IOException("Cannot read file: " + str);
            }
        }
        if (str2 == null) {
            inputStreamReader = new InputStreamReader(inputStream);
        } else {
            inputStreamReader = new InputStreamReader(inputStream, str2);
        }
        String readReader = readReader(inputStreamReader, i);
        if (inputStream != null) {
            inputStream.close();
        }
        return readReader;
    }
}
