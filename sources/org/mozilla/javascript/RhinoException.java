package org.mozilla.javascript;

import java.io.CharArrayWriter;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public abstract class RhinoException extends RuntimeException {
    private static final Pattern JAVA_STACK_PATTERN = Pattern.compile("_c_(.*)_\\d+");
    private static final long serialVersionUID = 1883500631321581169L;
    private static StackStyle stackStyle;
    private int columnNumber;
    int[] interpreterLineData;
    Object interpreterStackInfo;
    private int lineNumber;
    private String lineSource;
    private String sourceName;

    /* renamed from: org.mozilla.javascript.RhinoException$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mozilla$javascript$StackStyle;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                org.mozilla.javascript.StackStyle[] r0 = org.mozilla.javascript.StackStyle.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$mozilla$javascript$StackStyle = r0
                org.mozilla.javascript.StackStyle r1 = org.mozilla.javascript.StackStyle.MOZILLA     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$mozilla$javascript$StackStyle     // Catch:{ NoSuchFieldError -> 0x001d }
                org.mozilla.javascript.StackStyle r1 = org.mozilla.javascript.StackStyle.V8     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$org$mozilla$javascript$StackStyle     // Catch:{ NoSuchFieldError -> 0x0028 }
                org.mozilla.javascript.StackStyle r1 = org.mozilla.javascript.StackStyle.RHINO     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.RhinoException.AnonymousClass1.<clinit>():void");
        }
    }

    static {
        StackStyle stackStyle2 = StackStyle.RHINO;
        stackStyle = stackStyle2;
        String property = System.getProperty("rhino.stack.style");
        if (property == null) {
            return;
        }
        if ("Rhino".equalsIgnoreCase(property)) {
            stackStyle = stackStyle2;
        } else if ("Mozilla".equalsIgnoreCase(property)) {
            stackStyle = StackStyle.MOZILLA;
        } else if ("V8".equalsIgnoreCase(property)) {
            stackStyle = StackStyle.V8;
        }
    }

    public RhinoException() {
        Evaluator createInterpreter = Context.createInterpreter();
        if (createInterpreter != null) {
            createInterpreter.captureStackInfo(this);
        }
    }

    public static String formatStackTrace(ScriptStackElement[] scriptStackElementArr, String str) {
        StringBuilder sb = new StringBuilder();
        String systemProperty = SecurityUtilities.getSystemProperty("line.separator");
        if (stackStyle == StackStyle.V8 && !"null".equals(str)) {
            sb.append(str);
            sb.append(systemProperty);
        }
        for (ScriptStackElement scriptStackElement : scriptStackElementArr) {
            int i = AnonymousClass1.$SwitchMap$org$mozilla$javascript$StackStyle[stackStyle.ordinal()];
            if (i == 1) {
                scriptStackElement.renderMozillaStyle(sb);
            } else if (i == 2) {
                scriptStackElement.renderV8Style(sb);
            } else if (i == 3) {
                scriptStackElement.renderJavaStyle(sb);
            }
            sb.append(systemProperty);
        }
        return sb.toString();
    }

    private String generateStackTrace() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        super.printStackTrace(new PrintWriter(charArrayWriter));
        String charArrayWriter2 = charArrayWriter.toString();
        Evaluator createInterpreter = Context.createInterpreter();
        if (createInterpreter != null) {
            return createInterpreter.getPatchedStack(this, charArrayWriter2);
        }
        return null;
    }

    public static StackStyle getStackStyle() {
        return stackStyle;
    }

    public static void setStackStyle(StackStyle stackStyle2) {
        stackStyle = stackStyle2;
    }

    public static void useMozillaStackStyle(boolean z) {
        stackStyle = z ? StackStyle.MOZILLA : StackStyle.RHINO;
    }

    public static boolean usesMozillaStackStyle() {
        return stackStyle == StackStyle.MOZILLA;
    }

    public final int columnNumber() {
        return this.columnNumber;
    }

    public String details() {
        return super.getMessage();
    }

    public final String getMessage() {
        String details = details();
        if (this.sourceName == null || this.lineNumber <= 0) {
            return details;
        }
        StringBuilder sb = new StringBuilder(details);
        sb.append(" (");
        sb.append(this.sourceName);
        if (this.lineNumber > 0) {
            sb.append('#');
            sb.append(this.lineNumber);
        }
        sb.append(')');
        return sb.toString();
    }

    public ScriptStackElement[] getScriptStack() {
        return getScriptStack(-1, (String) null);
    }

    public String getScriptStackTrace() {
        return getScriptStackTrace(-1, (String) null);
    }

    public final void initColumnNumber(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.valueOf(i));
        } else if (this.columnNumber <= 0) {
            this.columnNumber = i;
        } else {
            throw new IllegalStateException();
        }
    }

    public final void initLineNumber(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.valueOf(i));
        } else if (this.lineNumber <= 0) {
            this.lineNumber = i;
        } else {
            throw new IllegalStateException();
        }
    }

    public final void initLineSource(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        } else if (this.lineSource == null) {
            this.lineSource = str;
        } else {
            throw new IllegalStateException();
        }
    }

    public final void initSourceName(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        } else if (this.sourceName == null) {
            this.sourceName = str;
        } else {
            throw new IllegalStateException();
        }
    }

    public final int lineNumber() {
        return this.lineNumber;
    }

    public final String lineSource() {
        return this.lineSource;
    }

    public void printStackTrace(PrintWriter printWriter) {
        if (this.interpreterStackInfo == null) {
            super.printStackTrace(printWriter);
        } else {
            printWriter.print(generateStackTrace());
        }
    }

    public final void recordErrorOrigin(String str, int i, String str2, int i2) {
        if (i == -1) {
            i = 0;
        }
        if (str != null) {
            initSourceName(str);
        }
        if (i != 0) {
            initLineNumber(i);
        }
        if (str2 != null) {
            initLineSource(str2);
        }
        if (i2 != 0) {
            initColumnNumber(i2);
        }
    }

    public final String sourceName() {
        return this.sourceName;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.ScriptStackElement[] getScriptStack(int r17, java.lang.String r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.lang.Object r4 = r0.interpreterStackInfo
            if (r4 == 0) goto L_0x001e
            org.mozilla.javascript.Evaluator r4 = org.mozilla.javascript.Context.createInterpreter()
            boolean r6 = r4 instanceof org.mozilla.javascript.Interpreter
            if (r6 == 0) goto L_0x001e
            org.mozilla.javascript.Interpreter r4 = (org.mozilla.javascript.Interpreter) r4
            org.mozilla.javascript.ScriptStackElement[][] r4 = r4.getScriptStackElements(r0)
            goto L_0x001f
        L_0x001e:
            r4 = 0
        L_0x001f:
            java.lang.StackTraceElement[] r6 = r16.getStackTrace()
            if (r2 != 0) goto L_0x0027
            r9 = 1
            goto L_0x0028
        L_0x0027:
            r9 = 0
        L_0x0028:
            int r10 = r6.length
            r11 = 0
            r12 = 0
            r13 = 0
        L_0x002c:
            if (r11 >= r10) goto L_0x00d8
            r14 = r6[r11]
            java.lang.String r15 = r14.getFileName()
            java.lang.String r5 = r14.getMethodName()
            java.lang.String r7 = "_c_"
            boolean r5 = r5.startsWith(r7)
            if (r5 == 0) goto L_0x0090
            int r5 = r14.getLineNumber()
            r7 = -1
            if (r5 <= r7) goto L_0x0090
            if (r15 == 0) goto L_0x0090
            java.lang.String r5 = ".java"
            boolean r5 = r15.endsWith(r5)
            if (r5 != 0) goto L_0x0090
            java.lang.String r5 = r14.getMethodName()
            java.util.regex.Pattern r7 = JAVA_STACK_PATTERN
            java.util.regex.Matcher r7 = r7.matcher(r5)
            java.lang.String r8 = "_c_script_0"
            boolean r5 = r8.equals(r5)
            if (r5 != 0) goto L_0x006f
            boolean r5 = r7.find()
            if (r5 == 0) goto L_0x006f
            r5 = 1
            java.lang.String r7 = r7.group(r5)
            goto L_0x0071
        L_0x006f:
            r5 = 1
            r7 = 0
        L_0x0071:
            if (r9 != 0) goto L_0x007b
            boolean r8 = r2.equals(r7)
            if (r8 == 0) goto L_0x007b
            r9 = 1
            goto L_0x00d4
        L_0x007b:
            if (r9 == 0) goto L_0x00d4
            if (r1 < 0) goto L_0x0081
            if (r13 >= r1) goto L_0x00d4
        L_0x0081:
            org.mozilla.javascript.ScriptStackElement r8 = new org.mozilla.javascript.ScriptStackElement
            int r14 = r14.getLineNumber()
            r8.<init>(r15, r7, r14)
            r3.add(r8)
            int r13 = r13 + 1
            goto L_0x00d4
        L_0x0090:
            r5 = 1
            java.lang.String r7 = "org.mozilla.javascript.Interpreter"
            java.lang.String r8 = r14.getClassName()
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00d4
            java.lang.String r7 = "interpretLoop"
            java.lang.String r8 = r14.getMethodName()
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00d4
            if (r4 == 0) goto L_0x00d4
            int r7 = r4.length
            if (r7 <= r12) goto L_0x00d4
            int r7 = r12 + 1
            r8 = r4[r12]
            int r12 = r8.length
            r14 = 0
        L_0x00b4:
            if (r14 >= r12) goto L_0x00d3
            r15 = r8[r14]
            if (r9 != 0) goto L_0x00c4
            java.lang.String r5 = r15.functionName
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x00c4
            r9 = 1
            goto L_0x00cf
        L_0x00c4:
            if (r9 == 0) goto L_0x00cf
            if (r1 < 0) goto L_0x00ca
            if (r13 >= r1) goto L_0x00cf
        L_0x00ca:
            r3.add(r15)
            int r13 = r13 + 1
        L_0x00cf:
            int r14 = r14 + 1
            r5 = 1
            goto L_0x00b4
        L_0x00d3:
            r12 = r7
        L_0x00d4:
            int r11 = r11 + 1
            goto L_0x002c
        L_0x00d8:
            int r1 = r3.size()
            org.mozilla.javascript.ScriptStackElement[] r1 = new org.mozilla.javascript.ScriptStackElement[r1]
            java.lang.Object[] r1 = r3.toArray(r1)
            org.mozilla.javascript.ScriptStackElement[] r1 = (org.mozilla.javascript.ScriptStackElement[]) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.RhinoException.getScriptStack(int, java.lang.String):org.mozilla.javascript.ScriptStackElement[]");
    }

    public String getScriptStackTrace(int i, String str) {
        return formatStackTrace(getScriptStack(i, str), details());
    }

    public RhinoException(String str) {
        super(str);
        Evaluator createInterpreter = Context.createInterpreter();
        if (createInterpreter != null) {
            createInterpreter.captureStackInfo(this);
        }
    }

    @Deprecated
    public String getScriptStackTrace(FilenameFilter filenameFilter) {
        return getScriptStackTrace();
    }

    public void printStackTrace(PrintStream printStream) {
        if (this.interpreterStackInfo == null) {
            super.printStackTrace(printStream);
            return;
        }
        generateStackTrace();
        printStream.getClass();
    }
}
