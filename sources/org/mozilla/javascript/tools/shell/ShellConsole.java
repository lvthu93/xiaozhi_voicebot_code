package org.mozilla.javascript.tools.shell;

import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Scriptable;

public abstract class ShellConsole {
    private static final Class[] BOOLEAN_ARG = {Boolean.TYPE};
    /* access modifiers changed from: private */
    public static final Class[] CHARSEQ_ARG = {CharSequence.class};
    /* access modifiers changed from: private */
    public static final Class[] NO_ARG = new Class[0];
    /* access modifiers changed from: private */
    public static final Class[] STRING_ARG = {String.class};

    public static class JLineShellConsoleV1 extends ShellConsole {
        private final InputStream in;
        private final Object reader;

        public JLineShellConsoleV1(Object obj, Charset charset) {
            this.reader = obj;
            this.in = new ConsoleInputStream(this, charset);
        }

        public void flush() throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "flushConsole", ShellConsole.NO_ARG, new Object[0]);
        }

        public InputStream getIn() {
            return this.in;
        }

        public void print(String str) throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "printString", ShellConsole.STRING_ARG, str);
        }

        public void println() throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "printNewline", ShellConsole.NO_ARG, new Object[0]);
        }

        public String readLine() throws IOException {
            return (String) ShellConsole.tryInvoke(this.reader, "readLine", ShellConsole.NO_ARG, new Object[0]);
        }

        public void println(String str) throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "printString", ShellConsole.STRING_ARG, str);
            Object unused2 = ShellConsole.tryInvoke(this.reader, "printNewline", ShellConsole.NO_ARG, new Object[0]);
        }

        public String readLine(String str) throws IOException {
            return (String) ShellConsole.tryInvoke(this.reader, "readLine", ShellConsole.STRING_ARG, str);
        }
    }

    public static class JLineShellConsoleV2 extends ShellConsole {
        private final InputStream in;
        private final Object reader;

        public JLineShellConsoleV2(Object obj, Charset charset) {
            this.reader = obj;
            this.in = new ConsoleInputStream(this, charset);
        }

        public void flush() throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "flush", ShellConsole.NO_ARG, new Object[0]);
        }

        public InputStream getIn() {
            return this.in;
        }

        public void print(String str) throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "print", ShellConsole.CHARSEQ_ARG, str);
        }

        public void println() throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "println", ShellConsole.NO_ARG, new Object[0]);
        }

        public String readLine() throws IOException {
            return (String) ShellConsole.tryInvoke(this.reader, "readLine", ShellConsole.NO_ARG, new Object[0]);
        }

        public void println(String str) throws IOException {
            Object unused = ShellConsole.tryInvoke(this.reader, "println", ShellConsole.CHARSEQ_ARG, str);
        }

        public String readLine(String str) throws IOException {
            return (String) ShellConsole.tryInvoke(this.reader, "readLine", ShellConsole.STRING_ARG, str);
        }
    }

    public static class SimpleShellConsole extends ShellConsole {
        private final InputStream in;
        private final PrintWriter out;
        private final BufferedReader reader;

        public SimpleShellConsole(InputStream inputStream, PrintStream printStream, Charset charset) {
            this.in = inputStream;
            this.out = new PrintWriter(printStream);
            this.reader = new BufferedReader(new InputStreamReader(inputStream, charset));
        }

        public void flush() throws IOException {
            this.out.flush();
        }

        public InputStream getIn() {
            return this.in;
        }

        public void print(String str) throws IOException {
            this.out.print(str);
        }

        public void println() throws IOException {
            this.out.println();
        }

        public String readLine() throws IOException {
            return this.reader.readLine();
        }

        public void println(String str) throws IOException {
            this.out.println(str);
        }

        public String readLine(String str) throws IOException {
            if (str != null) {
                this.out.write(str);
                this.out.flush();
            }
            return this.reader.readLine();
        }
    }

    public static ShellConsole getConsole(InputStream inputStream, PrintStream printStream, Charset charset) {
        return new SimpleShellConsole(inputStream, printStream, charset);
    }

    private static JLineShellConsoleV1 getJLineShellConsoleV1(ClassLoader classLoader, Class<?> cls, Scriptable scriptable, Charset charset) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        tryInvoke(newInstance, "setBellEnabled", BOOLEAN_ARG, Boolean.FALSE);
        Class<?> classOrNull = Kit.classOrNull(classLoader, "jline.Completor");
        Object newProxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{classOrNull}, new FlexibleCompletor(classOrNull, scriptable));
        tryInvoke(newInstance, "addCompletor", new Class[]{classOrNull}, newProxyInstance);
        return new JLineShellConsoleV1(newInstance, charset);
    }

    private static JLineShellConsoleV2 getJLineShellConsoleV2(ClassLoader classLoader, Class<?> cls, Scriptable scriptable, Charset charset) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        tryInvoke(newInstance, "setBellEnabled", BOOLEAN_ARG, Boolean.FALSE);
        Class<?> classOrNull = Kit.classOrNull(classLoader, "jline.console.completer.Completer");
        Object newProxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{classOrNull}, new FlexibleCompletor(classOrNull, scriptable));
        tryInvoke(newInstance, "addCompleter", new Class[]{classOrNull}, newProxyInstance);
        return new JLineShellConsoleV2(newInstance, charset);
    }

    /* access modifiers changed from: private */
    public static Object tryInvoke(Object obj, String str, Class[] clsArr, Object... objArr) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }

    public abstract void flush() throws IOException;

    public abstract InputStream getIn();

    public abstract void print(String str) throws IOException;

    public abstract void println() throws IOException;

    public abstract void println(String str) throws IOException;

    public abstract String readLine() throws IOException;

    public abstract String readLine(String str) throws IOException;

    public static ShellConsole getConsole(Scriptable scriptable, Charset charset) {
        ClassLoader classLoader = ShellConsole.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        if (classLoader == null) {
            return null;
        }
        try {
            Class<?> classOrNull = Kit.classOrNull(classLoader, "jline.console.ConsoleReader");
            if (classOrNull != null) {
                return getJLineShellConsoleV2(classLoader, classOrNull, scriptable, charset);
            }
            Class<?> classOrNull2 = Kit.classOrNull(classLoader, "jline.ConsoleReader");
            if (classOrNull2 != null) {
                return getJLineShellConsoleV1(classLoader, classOrNull2, scriptable, charset);
            }
            return null;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
        }
    }

    public static class ConsoleInputStream extends InputStream implements InputStreamRetargetInterface {
        private static final byte[] EMPTY = new byte[0];
        private boolean atEOF = false;
        private byte[] buffer = EMPTY;
        private final ShellConsole console;
        private final Charset cs;
        private int cursor = -1;

        public ConsoleInputStream(ShellConsole shellConsole, Charset charset) {
            this.console = shellConsole;
            this.cs = charset;
        }

        private boolean ensureInput() throws IOException {
            if (this.atEOF) {
                return false;
            }
            int i = this.cursor;
            if (i < 0 || i > this.buffer.length) {
                if (readNextLine() == -1) {
                    this.atEOF = true;
                    return false;
                }
                this.cursor = 0;
            }
            return true;
        }

        private int readNextLine() throws IOException {
            String readLine = this.console.readLine((String) null);
            if (readLine != null) {
                byte[] bytes = readLine.getBytes(this.cs);
                this.buffer = bytes;
                return bytes.length;
            }
            this.buffer = EMPTY;
            return -1;
        }

        public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
            if (bArr != null) {
                if (i >= 0 && i2 >= 0) {
                    if (i2 <= bArr.length - i) {
                        if (i2 == 0) {
                            return 0;
                        }
                        if (!ensureInput()) {
                            return -1;
                        }
                        int min = Math.min(i2, this.buffer.length - this.cursor);
                        for (int i3 = 0; i3 < min; i3++) {
                            bArr[i + i3] = this.buffer[this.cursor + i3];
                        }
                        if (min < i2) {
                            bArr[i + min] = 10;
                            min++;
                        }
                        this.cursor += min;
                        return min;
                    }
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException();
        }

        public final /* synthetic */ long transferTo(OutputStream outputStream) {
            return DesugarInputStream.transferTo(this, outputStream);
        }

        public synchronized int read() throws IOException {
            if (!ensureInput()) {
                return -1;
            }
            int i = this.cursor;
            byte[] bArr = this.buffer;
            if (i == bArr.length) {
                this.cursor = i + 1;
                return 10;
            }
            this.cursor = i + 1;
            return bArr[i];
        }
    }
}
