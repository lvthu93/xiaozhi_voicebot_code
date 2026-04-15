package okhttp3.internal.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public interface FileSystem {
    public static final FileSystem SYSTEM = new FileSystem() {
        public za appendingSink(File file) throws FileNotFoundException {
            try {
                Logger logger = s7.a;
                if (file != null) {
                    return new o7(new FileOutputStream(file, true), new lc());
                }
                throw new IllegalArgumentException("file == null");
            } catch (FileNotFoundException unused) {
                file.getParentFile().mkdirs();
                Logger logger2 = s7.a;
                return new o7(new FileOutputStream(file, true), new lc());
            }
        }

        public void delete(File file) throws IOException {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        public void deleteContents(File file) throws IOException {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                int i = 0;
                while (i < length) {
                    File file2 = listFiles[i];
                    if (file2.isDirectory()) {
                        deleteContents(file2);
                    }
                    if (file2.delete()) {
                        i++;
                    } else {
                        throw new IOException("failed to delete " + file2);
                    }
                }
                return;
            }
            throw new IOException("not a readable directory: " + file);
        }

        public boolean exists(File file) {
            return file.exists();
        }

        public void rename(File file, File file2) throws IOException {
            delete(file2);
            if (!file.renameTo(file2)) {
                throw new IOException("failed to rename " + file + " to " + file2);
            }
        }

        public za sink(File file) throws FileNotFoundException {
            try {
                Logger logger = s7.a;
                if (file != null) {
                    return new o7(new FileOutputStream(file), new lc());
                }
                throw new IllegalArgumentException("file == null");
            } catch (FileNotFoundException unused) {
                file.getParentFile().mkdirs();
                Logger logger2 = s7.a;
                return new o7(new FileOutputStream(file), new lc());
            }
        }

        public long size(File file) {
            return file.length();
        }

        public jb source(File file) throws FileNotFoundException {
            Logger logger = s7.a;
            if (file != null) {
                return new p7(new FileInputStream(file), new lc());
            }
            throw new IllegalArgumentException("file == null");
        }
    };

    za appendingSink(File file) throws FileNotFoundException;

    void delete(File file) throws IOException;

    void deleteContents(File file) throws IOException;

    boolean exists(File file);

    void rename(File file, File file2) throws IOException;

    za sink(File file) throws FileNotFoundException;

    long size(File file);

    jb source(File file) throws FileNotFoundException;
}
