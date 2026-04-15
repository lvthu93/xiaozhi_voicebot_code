package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

public class MqttDefaultFilePersistence implements MqttClientPersistence {
    private static FilenameFilter FILENAME_FILTER = null;
    private static final String LOCK_FILENAME = ".lck";
    private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
    private static final String MESSAGE_FILE_EXTENSION = ".msg";
    private File clientDir;
    private File dataDir;
    private FileLock fileLock;

    public MqttDefaultFilePersistence() {
        this(System.getProperty("user.dir"));
    }

    private void checkIsOpen() throws MqttPersistenceException {
        if (this.clientDir == null) {
            throw new MqttPersistenceException();
        }
    }

    private static FilenameFilter getFilenameFilter() {
        if (FILENAME_FILTER == null) {
            FILENAME_FILTER = new PersistanceFileNameFilter(MESSAGE_FILE_EXTENSION);
        }
        return FILENAME_FILTER;
    }

    private File[] getFiles() throws MqttPersistenceException {
        checkIsOpen();
        File[] listFiles = this.clientDir.listFiles(getFilenameFilter());
        if (listFiles != null) {
            return listFiles;
        }
        throw new MqttPersistenceException();
    }

    private boolean isSafeChar(char c) {
        return Character.isJavaIdentifierPart(c) || c == '-';
    }

    private void restoreBackups(File file) throws MqttPersistenceException {
        File[] listFiles = file.listFiles(new PersistanceFileFilter(MESSAGE_BACKUP_FILE_EXTENSION));
        if (listFiles != null) {
            for (File file2 : listFiles) {
                File file3 = new File(file, file2.getName().substring(0, file2.getName().length() - 4));
                if (!file2.renameTo(file3)) {
                    file3.delete();
                    file2.renameTo(file3);
                }
            }
            return;
        }
        throw new MqttPersistenceException();
    }

    public void clear() throws MqttPersistenceException {
        checkIsOpen();
        for (File delete : getFiles()) {
            delete.delete();
        }
        this.clientDir.delete();
    }

    public void close() throws MqttPersistenceException {
        synchronized (this) {
            FileLock fileLock2 = this.fileLock;
            if (fileLock2 != null) {
                fileLock2.release();
            }
            if (getFiles().length == 0) {
                this.clientDir.delete();
            }
            this.clientDir = null;
        }
    }

    public boolean containsKey(String str) throws MqttPersistenceException {
        checkIsOpen();
        return new File(this.clientDir, String.valueOf(str).concat(MESSAGE_FILE_EXTENSION)).exists();
    }

    public MqttPersistable get(String str) throws MqttPersistenceException {
        checkIsOpen();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.clientDir, String.valueOf(str).concat(MESSAGE_FILE_EXTENSION)));
            int available = fileInputStream.available();
            byte[] bArr = new byte[available];
            for (int i = 0; i < available; i += fileInputStream.read(bArr, i, available - i)) {
            }
            fileInputStream.close();
            return new MqttPersistentData(str, bArr, 0, available, (byte[]) null, 0, 0);
        } catch (IOException e) {
            throw new MqttPersistenceException((Throwable) e);
        }
    }

    public Enumeration<String> keys() throws MqttPersistenceException {
        checkIsOpen();
        File[] files = getFiles();
        Vector vector = new Vector(files.length);
        for (File name : files) {
            String name2 = name.getName();
            vector.addElement(name2.substring(0, name2.length() - 4));
        }
        return vector.elements();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:21|22|(2:24|(1:26))|27|28|(1:30)|31|32|33|34) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x007d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void open(java.lang.String r6, java.lang.String r7) throws org.eclipse.paho.client.mqttv3.MqttPersistenceException {
        /*
            r5 = this;
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x0017
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.isDirectory()
            if (r0 == 0) goto L_0x0011
            goto L_0x0017
        L_0x0011:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            throw r6
        L_0x0017:
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x002e
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.mkdirs()
            if (r0 == 0) goto L_0x0028
            goto L_0x002e
        L_0x0028:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            throw r6
        L_0x002e:
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.canWrite()
            if (r0 == 0) goto L_0x00a7
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x003d:
            int r3 = r6.length()
            if (r2 < r3) goto L_0x0097
            java.lang.String r6 = "-"
            r0.append(r6)
        L_0x0048:
            int r6 = r7.length()
            if (r1 < r6) goto L_0x0087
            monitor-enter(r5)
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x0084 }
            if (r6 != 0) goto L_0x006b
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0084 }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x0084 }
            java.io.File r0 = r5.dataDir     // Catch:{ all -> 0x0084 }
            r7.<init>(r0, r6)     // Catch:{ all -> 0x0084 }
            r5.clientDir = r7     // Catch:{ all -> 0x0084 }
            boolean r6 = r7.exists()     // Catch:{ all -> 0x0084 }
            if (r6 != 0) goto L_0x006b
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x0084 }
            r6.mkdir()     // Catch:{ all -> 0x0084 }
        L_0x006b:
            org.eclipse.paho.client.mqttv3.internal.FileLock r6 = r5.fileLock     // Catch:{ Exception -> 0x007d }
            if (r6 == 0) goto L_0x0072
            r6.release()     // Catch:{ Exception -> 0x007d }
        L_0x0072:
            org.eclipse.paho.client.mqttv3.internal.FileLock r6 = new org.eclipse.paho.client.mqttv3.internal.FileLock     // Catch:{ Exception -> 0x007d }
            java.io.File r7 = r5.clientDir     // Catch:{ Exception -> 0x007d }
            java.lang.String r0 = ".lck"
            r6.<init>(r7, r0)     // Catch:{ Exception -> 0x007d }
            r5.fileLock = r6     // Catch:{ Exception -> 0x007d }
        L_0x007d:
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x0084 }
            r5.restoreBackups(r6)     // Catch:{ all -> 0x0084 }
            monitor-exit(r5)     // Catch:{ all -> 0x0084 }
            return
        L_0x0084:
            r6 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0084 }
            throw r6
        L_0x0087:
            char r6 = r7.charAt(r1)
            boolean r2 = r5.isSafeChar(r6)
            if (r2 == 0) goto L_0x0094
            r0.append(r6)
        L_0x0094:
            int r1 = r1 + 1
            goto L_0x0048
        L_0x0097:
            char r3 = r6.charAt(r2)
            boolean r4 = r5.isSafeChar(r3)
            if (r4 == 0) goto L_0x00a4
            r0.append(r3)
        L_0x00a4:
            int r2 = r2 + 1
            goto L_0x003d
        L_0x00a7:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence.open(java.lang.String, java.lang.String):void");
    }

    public void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException {
        checkIsOpen();
        File file = new File(this.clientDir, String.valueOf(str).concat(MESSAGE_FILE_EXTENSION));
        File file2 = new File(this.clientDir, String.valueOf(str).concat(".msg.bup"));
        if (file.exists() && !file.renameTo(file2)) {
            file2.delete();
            file.renameTo(file2);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(mqttPersistable.getHeaderBytes(), mqttPersistable.getHeaderOffset(), mqttPersistable.getHeaderLength());
            if (mqttPersistable.getPayloadBytes() != null) {
                fileOutputStream.write(mqttPersistable.getPayloadBytes(), mqttPersistable.getPayloadOffset(), mqttPersistable.getPayloadLength());
            }
            fileOutputStream.getFD().sync();
            fileOutputStream.close();
            if (file2.exists()) {
                file2.delete();
            }
            if (file2.exists() && !file2.renameTo(file)) {
                file.delete();
                file2.renameTo(file);
            }
        } catch (IOException e) {
            throw new MqttPersistenceException((Throwable) e);
        } catch (Throwable th) {
            if (file2.exists() && !file2.renameTo(file)) {
                file.delete();
                file2.renameTo(file);
            }
            throw th;
        }
    }

    public void remove(String str) throws MqttPersistenceException {
        checkIsOpen();
        File file = new File(this.clientDir, String.valueOf(str).concat(MESSAGE_FILE_EXTENSION));
        if (file.exists()) {
            file.delete();
        }
    }

    public MqttDefaultFilePersistence(String str) {
        this.clientDir = null;
        this.fileLock = null;
        this.dataDir = new File(str);
    }
}
