package org.mozilla.javascript.tools.shell;

import java.io.OutputStream;
import javax.swing.SwingUtilities;

class ConsoleWriter extends OutputStream {
    private StringBuffer buffer = new StringBuffer();
    private ConsoleTextArea textArea;

    public ConsoleWriter(ConsoleTextArea consoleTextArea) {
        this.textArea = consoleTextArea;
    }

    private void flushBuffer() {
        String stringBuffer = this.buffer.toString();
        this.buffer.setLength(0);
        SwingUtilities.invokeLater(new ConsoleWrite(this.textArea, stringBuffer));
    }

    public void close() {
        flush();
    }

    public synchronized void flush() {
        if (this.buffer.length() > 0) {
            flushBuffer();
        }
    }

    public synchronized void write(int i) {
        this.buffer.append((char) i);
        if (i == 10) {
            flushBuffer();
        }
    }

    public synchronized void write(char[] cArr, int i, int i2) {
        while (i < i2) {
            this.buffer.append(cArr[i]);
            if (cArr[i] == 10) {
                flushBuffer();
            }
            i++;
        }
    }
}
