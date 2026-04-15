package com.google.android.exoplayer2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class AtomicFile {
    public final File a;
    public final File b;

    public static final class a extends OutputStream {
        public final FileOutputStream c;
        public boolean f = false;

        public a(File file) throws FileNotFoundException {
            this.c = new FileOutputStream(file);
        }

        public void close() throws IOException {
            FileOutputStream fileOutputStream = this.c;
            if (!this.f) {
                this.f = true;
                flush();
                try {
                    fileOutputStream.getFD().sync();
                } catch (IOException e) {
                    Log.w("AtomicFile", "Failed to sync file descriptor:", e);
                }
                fileOutputStream.close();
            }
        }

        public void flush() throws IOException {
            this.c.flush();
        }

        public void write(int i) throws IOException {
            this.c.write(i);
        }

        public void write(byte[] bArr) throws IOException {
            this.c.write(bArr);
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.c.write(bArr, i, i2);
        }
    }

    public AtomicFile(File file) {
        this.a = file;
        this.b = new File(String.valueOf(file.getPath()).concat(".bak"));
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
    }

    public void endWrite(OutputStream outputStream) throws IOException {
        outputStream.close();
        this.b.delete();
    }

    public boolean exists() {
        return this.a.exists() || this.b.exists();
    }

    public InputStream openRead() throws FileNotFoundException {
        File file = this.b;
        boolean exists = file.exists();
        File file2 = this.a;
        if (exists) {
            file2.delete();
            file.renameTo(file2);
        }
        return new FileInputStream(file2);
    }

    public OutputStream startWrite() throws IOException {
        File file = this.a;
        if (file.exists()) {
            File file2 = this.b;
            if (file2.exists()) {
                file.delete();
            } else if (!file.renameTo(file2)) {
                String valueOf = String.valueOf(file);
                String valueOf2 = String.valueOf(file2);
                StringBuilder sb = new StringBuilder(valueOf2.length() + valueOf.length() + 37);
                sb.append("Couldn't rename file ");
                sb.append(valueOf);
                sb.append(" to backup file ");
                sb.append(valueOf2);
                Log.w("AtomicFile", sb.toString());
            }
        }
        try {
            return new a(file);
        } catch (FileNotFoundException e) {
            File parentFile = file.getParentFile();
            if (parentFile == null || !parentFile.mkdirs()) {
                String valueOf3 = String.valueOf(file);
                StringBuilder sb2 = new StringBuilder(valueOf3.length() + 16);
                sb2.append("Couldn't create ");
                sb2.append(valueOf3);
                throw new IOException(sb2.toString(), e);
            }
            try {
                return new a(file);
            } catch (FileNotFoundException e2) {
                String valueOf4 = String.valueOf(file);
                StringBuilder sb3 = new StringBuilder(valueOf4.length() + 16);
                sb3.append("Couldn't create ");
                sb3.append(valueOf4);
                throw new IOException(sb3.toString(), e2);
            }
        }
    }
}
