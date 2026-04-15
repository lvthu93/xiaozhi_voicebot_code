package androidx.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor implements Closeable {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_CRC = "dex.crc.";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_DEX_TIME = "dex.time.";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final String LOCK_FILENAME = "MultiDex.lock";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private final FileLock cacheLock;
    private final File dexDir;
    private final FileChannel lockChannel;
    private final RandomAccessFile lockRaf;
    private final File sourceApk;
    private final long sourceCrc;

    public static class ExtractedDex extends File {
        public long crc = -1;

        public ExtractedDex(File file, String str) {
            super(file, str);
        }
    }

    public MultiDexExtractor(File file, File file2) throws IOException {
        file.getPath();
        file2.getPath();
        this.sourceApk = file;
        this.dexDir = file2;
        this.sourceCrc = getZipCrc(file);
        File file3 = new File(file2, LOCK_FILENAME);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file3, "rw");
        this.lockRaf = randomAccessFile;
        try {
            FileChannel channel = randomAccessFile.getChannel();
            this.lockChannel = channel;
            try {
                file3.getPath();
                this.cacheLock = channel.lock();
                file3.getPath();
            } catch (IOException e) {
                e = e;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (RuntimeException e2) {
                e = e2;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (Error e3) {
                e = e3;
                closeQuietly(this.lockChannel);
                throw e;
            }
        } catch (IOException | Error | RuntimeException e4) {
            closeQuietly(this.lockRaf);
            throw e4;
        }
    }

    private void clearDexDir() {
        File[] listFiles = this.dexDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.getName().equals(MultiDexExtractor.LOCK_FILENAME);
            }
        });
        if (listFiles == null) {
            this.dexDir.getPath();
            return;
        }
        for (File file : listFiles) {
            file.getPath();
            file.length();
            if (!file.delete()) {
                file.getPath();
            } else {
                file.getPath();
            }
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    private static void extract(ZipFile zipFile, ZipEntry zipEntry, File file, String str) throws IOException, FileNotFoundException {
        ZipOutputStream zipOutputStream;
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        File createTempFile = File.createTempFile(y2.i("tmp-", str), EXTRACTED_SUFFIX, file.getParentFile());
        createTempFile.getPath();
        try {
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(createTempFile)));
            ZipEntry zipEntry2 = new ZipEntry("classes.dex");
            zipEntry2.setTime(zipEntry.getTime());
            zipOutputStream.putNextEntry(zipEntry2);
            byte[] bArr = new byte[16384];
            for (int read = inputStream.read(bArr); read != -1; read = inputStream.read(bArr)) {
                zipOutputStream.write(bArr, 0, read);
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            if (createTempFile.setReadOnly()) {
                file.getPath();
                if (createTempFile.renameTo(file)) {
                    closeQuietly(inputStream);
                    createTempFile.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + createTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"");
            }
            throw new IOException("Failed to mark readonly \"" + createTempFile.getAbsolutePath() + "\" (tmp of \"" + file.getAbsolutePath() + "\")");
        } catch (Throwable th) {
            closeQuietly(inputStream);
            createTempFile.delete();
            throw th;
        }
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, 4);
    }

    private static long getTimeStamp(File file) {
        long lastModified = file.lastModified();
        return lastModified == -1 ? lastModified - 1 : lastModified;
    }

    private static long getZipCrc(File file) throws IOException {
        long zipCrc = ZipUtil.getZipCrc(file);
        return zipCrc == -1 ? zipCrc - 1 : zipCrc;
    }

    private static boolean isModified(Context context, File file, long j, String str) {
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        if (multiDexPreferences.getLong(str + KEY_TIME_STAMP, -1) == getTimeStamp(file)) {
            if (multiDexPreferences.getLong(str + KEY_CRC, -1) != j) {
                return true;
            }
            return false;
        }
        return true;
    }

    private List<ExtractedDex> loadExistingExtractions(Context context, String str) throws IOException {
        String str2 = str;
        String str3 = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        int i = multiDexPreferences.getInt(str2 + KEY_DEX_NUMBER, 1);
        ArrayList arrayList = new ArrayList(i + -1);
        int i2 = 2;
        while (i2 <= i) {
            ExtractedDex extractedDex = new ExtractedDex(this.dexDir, str3 + i2 + EXTRACTED_SUFFIX);
            if (extractedDex.isFile()) {
                extractedDex.crc = getZipCrc(extractedDex);
                long j = multiDexPreferences.getLong(str2 + KEY_DEX_CRC + i2, -1);
                long j2 = multiDexPreferences.getLong(str2 + KEY_DEX_TIME + i2, -1);
                long lastModified = extractedDex.lastModified();
                if (j2 == lastModified) {
                    String str4 = str3;
                    SharedPreferences sharedPreferences = multiDexPreferences;
                    if (j == extractedDex.crc) {
                        arrayList.add(extractedDex);
                        i2++;
                        multiDexPreferences = sharedPreferences;
                        str3 = str4;
                    }
                }
                throw new IOException("Invalid extracted dex: " + extractedDex + " (key \"" + str2 + "\"), expected modification time: " + j2 + ", modification time: " + lastModified + ", expected crc: " + j + ", file crc: " + extractedDex.crc);
            }
            throw new IOException("Missing extracted secondary dex file '" + extractedDex.getPath() + "'");
        }
        return arrayList;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:12|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r6.getAbsolutePath();
        r8 = false;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0065 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<androidx.multidex.MultiDexExtractor.ExtractedDex> performExtractions() throws java.io.IOException {
        /*
            r10 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.File r1 = r10.sourceApk
            java.lang.String r1 = r1.getName()
            r0.append(r1)
            java.lang.String r1 = ".classes"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r10.clearDexDir()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile
            java.io.File r3 = r10.sourceApk
            r2.<init>(r3)
            java.lang.String r3 = "classes2.dex"
            java.util.zip.ZipEntry r3 = r2.getEntry(r3)     // Catch:{ all -> 0x00c9 }
            r4 = 2
        L_0x002d:
            if (r3 == 0) goto L_0x00c5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
            r5.<init>()     // Catch:{ all -> 0x00c9 }
            r5.append(r0)     // Catch:{ all -> 0x00c9 }
            r5.append(r4)     // Catch:{ all -> 0x00c9 }
            java.lang.String r6 = ".zip"
            r5.append(r6)     // Catch:{ all -> 0x00c9 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00c9 }
            androidx.multidex.MultiDexExtractor$ExtractedDex r6 = new androidx.multidex.MultiDexExtractor$ExtractedDex     // Catch:{ all -> 0x00c9 }
            java.io.File r7 = r10.dexDir     // Catch:{ all -> 0x00c9 }
            r6.<init>(r7, r5)     // Catch:{ all -> 0x00c9 }
            r1.add(r6)     // Catch:{ all -> 0x00c9 }
            r6.toString()     // Catch:{ all -> 0x00c9 }
            r5 = 0
            r7 = 0
            r8 = 0
        L_0x0053:
            r9 = 3
            if (r7 >= r9) goto L_0x007e
            if (r8 != 0) goto L_0x007e
            int r7 = r7 + 1
            extract(r2, r3, r6, r0)     // Catch:{ all -> 0x00c9 }
            long r8 = getZipCrc(r6)     // Catch:{ IOException -> 0x0065 }
            r6.crc = r8     // Catch:{ IOException -> 0x0065 }
            r8 = 1
            goto L_0x0069
        L_0x0065:
            r6.getAbsolutePath()     // Catch:{ all -> 0x00c9 }
            r8 = 0
        L_0x0069:
            r6.getAbsolutePath()     // Catch:{ all -> 0x00c9 }
            r6.length()     // Catch:{ all -> 0x00c9 }
            if (r8 != 0) goto L_0x0053
            r6.delete()     // Catch:{ all -> 0x00c9 }
            boolean r9 = r6.exists()     // Catch:{ all -> 0x00c9 }
            if (r9 == 0) goto L_0x0053
            r6.getPath()     // Catch:{ all -> 0x00c9 }
            goto L_0x0053
        L_0x007e:
            if (r8 == 0) goto L_0x009d
            int r4 = r4 + 1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
            r3.<init>()     // Catch:{ all -> 0x00c9 }
            java.lang.String r5 = "classes"
            r3.append(r5)     // Catch:{ all -> 0x00c9 }
            r3.append(r4)     // Catch:{ all -> 0x00c9 }
            java.lang.String r5 = ".dex"
            r3.append(r5)     // Catch:{ all -> 0x00c9 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00c9 }
            java.util.zip.ZipEntry r3 = r2.getEntry(r3)     // Catch:{ all -> 0x00c9 }
            goto L_0x002d
        L_0x009d:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x00c9 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
            r1.<init>()     // Catch:{ all -> 0x00c9 }
            java.lang.String r3 = "Could not create zip file "
            r1.append(r3)     // Catch:{ all -> 0x00c9 }
            java.lang.String r3 = r6.getAbsolutePath()     // Catch:{ all -> 0x00c9 }
            r1.append(r3)     // Catch:{ all -> 0x00c9 }
            java.lang.String r3 = " for secondary dex ("
            r1.append(r3)     // Catch:{ all -> 0x00c9 }
            r1.append(r4)     // Catch:{ all -> 0x00c9 }
            java.lang.String r3 = ")"
            r1.append(r3)     // Catch:{ all -> 0x00c9 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00c9 }
            r0.<init>(r1)     // Catch:{ all -> 0x00c9 }
            throw r0     // Catch:{ all -> 0x00c9 }
        L_0x00c5:
            r2.close()     // Catch:{ IOException -> 0x00c8 }
        L_0x00c8:
            return r1
        L_0x00c9:
            r0 = move-exception
            r2.close()     // Catch:{ IOException -> 0x00cd }
        L_0x00cd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.multidex.MultiDexExtractor.performExtractions():java.util.List");
    }

    private static void putStoredApkInfo(Context context, String str, long j, long j2, List<ExtractedDex> list) {
        SharedPreferences.Editor edit = getMultiDexPreferences(context).edit();
        edit.putLong(str + KEY_TIME_STAMP, j);
        edit.putLong(y2.k(new StringBuilder(), str, KEY_CRC), j2);
        edit.putInt(str + KEY_DEX_NUMBER, list.size() + 1);
        int i = 2;
        for (ExtractedDex next : list) {
            edit.putLong(str + KEY_DEX_CRC + i, next.crc);
            edit.putLong(str + KEY_DEX_TIME + i, next.lastModified());
            i++;
        }
        edit.commit();
    }

    public void close() throws IOException {
        this.cacheLock.release();
        this.lockChannel.close();
        this.lockRaf.close();
    }

    public List<? extends File> load(Context context, String str, boolean z) throws IOException {
        List<ExtractedDex> list;
        this.sourceApk.getPath();
        if (this.cacheLock.isValid()) {
            if (z || isModified(context, this.sourceApk, this.sourceCrc, str)) {
                list = performExtractions();
                putStoredApkInfo(context, str, getTimeStamp(this.sourceApk), this.sourceCrc, list);
            } else {
                try {
                    list = loadExistingExtractions(context, str);
                } catch (IOException unused) {
                    list = performExtractions();
                    putStoredApkInfo(context, str, getTimeStamp(this.sourceApk), this.sourceCrc, list);
                }
            }
            list.size();
            return list;
        }
        throw new IllegalStateException("MultiDexExtractor was closed");
    }
}
