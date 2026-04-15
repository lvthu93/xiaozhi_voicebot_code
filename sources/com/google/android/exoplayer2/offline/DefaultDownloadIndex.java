package com.google.android.exoplayer2.offline;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

public final class DefaultDownloadIndex implements WritableDownloadIndex {
    public static final String f = e(3, 4);
    public static final String[] g = {"id", "mime_type", "uri", "stream_keys", "custom_cache_key", "data", "state", "start_time_ms", "update_time_ms", "content_length", "stop_reason", "failure_reason", "percent_downloaded", "bytes_downloaded", "key_set_id"};
    public final String a;
    public final String b;
    public final DatabaseProvider c;
    public final Object d;
    @GuardedBy("initializationLock")
    public boolean e;

    public static final class a implements DownloadCursor {
        public final Cursor c;

        public a(Cursor cursor) {
            this.c = cursor;
        }

        public void close() {
            this.c.close();
        }

        public int getCount() {
            return this.c.getCount();
        }

        public Download getDownload() {
            return DefaultDownloadIndex.c(this.c);
        }

        public int getPosition() {
            return this.c.getPosition();
        }

        public /* bridge */ /* synthetic */ boolean isAfterLast() {
            return n1.a(this);
        }

        public /* bridge */ /* synthetic */ boolean isBeforeFirst() {
            return n1.b(this);
        }

        public boolean isClosed() {
            return this.c.isClosed();
        }

        public /* bridge */ /* synthetic */ boolean isFirst() {
            return n1.c(this);
        }

        public /* bridge */ /* synthetic */ boolean isLast() {
            return n1.d(this);
        }

        public /* bridge */ /* synthetic */ boolean moveToFirst() {
            return n1.e(this);
        }

        public /* bridge */ /* synthetic */ boolean moveToLast() {
            return n1.f(this);
        }

        public /* bridge */ /* synthetic */ boolean moveToNext() {
            return n1.g(this);
        }

        public boolean moveToPosition(int i) {
            return this.c.moveToPosition(i);
        }

        public /* bridge */ /* synthetic */ boolean moveToPrevious() {
            return n1.h(this);
        }
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, "");
    }

    public static ArrayList a(String str) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        if (str.isEmpty()) {
            return arrayList;
        }
        for (String split : Util.split(str, ",")) {
            String[] split2 = Util.split(split, "\\.");
            if (split2.length == 3) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            arrayList.add(new StreamKey(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]), Integer.parseInt(split2[2])));
        }
        return arrayList;
    }

    public static Download c(Cursor cursor) {
        int i;
        byte[] blob = cursor.getBlob(14);
        DownloadRequest.Builder streamKeys = new DownloadRequest.Builder(cursor.getString(0), Uri.parse(cursor.getString(2))).setMimeType(cursor.getString(1)).setStreamKeys(a(cursor.getString(3)));
        if (blob.length <= 0) {
            blob = null;
        }
        DownloadRequest build = streamKeys.setKeySetId(blob).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.a = cursor.getLong(13);
        downloadProgress.b = cursor.getFloat(12);
        int i2 = cursor.getInt(6);
        if (i2 == 4) {
            i = cursor.getInt(11);
        } else {
            i = 0;
        }
        return new Download(build, i2, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i, downloadProgress);
    }

    public static Download d(Cursor cursor) {
        String str;
        int i;
        DownloadRequest.Builder builder = new DownloadRequest.Builder(cursor.getString(0), Uri.parse(cursor.getString(2)));
        String string = cursor.getString(1);
        if ("dash".equals(string)) {
            str = "application/dash+xml";
        } else if ("hls".equals(string)) {
            str = "application/x-mpegURL";
        } else if ("ss".equals(string)) {
            str = "application/vnd.ms-sstr+xml";
        } else {
            str = "video/x-unknown";
        }
        DownloadRequest build = builder.setMimeType(str).setStreamKeys(a(cursor.getString(3))).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.a = cursor.getLong(13);
        downloadProgress.b = cursor.getFloat(12);
        int i2 = cursor.getInt(6);
        if (i2 == 4) {
            i = cursor.getInt(11);
        } else {
            i = 0;
        }
        return new Download(build, i2, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i, downloadProgress);
    }

    public static String e(int... iArr) {
        if (iArr.length == 0) {
            return "1";
        }
        StringBuilder sb = new StringBuilder("state IN (");
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(iArr[i]);
        }
        sb.append(')');
        return sb.toString();
    }

    public final void b() throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        ArrayList<Download> arrayList;
        String str;
        synchronized (this.d) {
            if (!this.e) {
                try {
                    int version = VersionTable.getVersion(this.c.getReadableDatabase(), 0, this.a);
                    if (version != 3) {
                        writableDatabase = this.c.getWritableDatabase();
                        writableDatabase.beginTransactionNonExclusive();
                        VersionTable.setVersion(writableDatabase, 0, this.a, 3);
                        if (version == 2) {
                            arrayList = f(writableDatabase);
                        } else {
                            arrayList = new ArrayList<>();
                        }
                        String valueOf = String.valueOf(this.b);
                        if (valueOf.length() != 0) {
                            str = "DROP TABLE IF EXISTS ".concat(valueOf);
                        } else {
                            str = new String("DROP TABLE IF EXISTS ");
                        }
                        writableDatabase.execSQL(str);
                        String str2 = this.b;
                        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 415);
                        sb.append("CREATE TABLE ");
                        sb.append(str2);
                        sb.append(" (id TEXT PRIMARY KEY NOT NULL,mime_type TEXT,uri TEXT NOT NULL,stream_keys TEXT NOT NULL,custom_cache_key TEXT,data BLOB NOT NULL,state INTEGER NOT NULL,start_time_ms INTEGER NOT NULL,update_time_ms INTEGER NOT NULL,content_length INTEGER NOT NULL,stop_reason INTEGER NOT NULL,failure_reason INTEGER NOT NULL,percent_downloaded REAL NOT NULL,bytes_downloaded INTEGER NOT NULL,key_set_id BLOB NOT NULL)");
                        writableDatabase.execSQL(sb.toString());
                        for (Download g2 : arrayList) {
                            g(g2, writableDatabase);
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                    }
                    this.e = true;
                } catch (SQLException e2) {
                    throw new DatabaseIOException(e2);
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
        }
    }

    public final ArrayList f(SQLiteDatabase sQLiteDatabase) {
        Throwable th;
        ArrayList arrayList = new ArrayList();
        if (!Util.tableExists(sQLiteDatabase, this.b)) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
        Cursor query = sQLiteDatabase2.query(this.b, new String[]{"id", "title", "uri", "stream_keys", "custom_cache_key", "data", "state", "start_time_ms", "update_time_ms", "content_length", "stop_reason", "failure_reason", "percent_downloaded", "bytes_downloaded"}, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        while (query.moveToNext()) {
            try {
                arrayList.add(d(query));
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        }
        query.close();
        return arrayList;
        throw th;
    }

    public final void g(Download download, SQLiteDatabase sQLiteDatabase) {
        byte[] bArr = download.a.i;
        if (bArr == null) {
            bArr = Util.f;
        }
        ContentValues contentValues = new ContentValues();
        DownloadRequest downloadRequest = download.a;
        contentValues.put("id", downloadRequest.c);
        contentValues.put("mime_type", downloadRequest.g);
        contentValues.put("uri", downloadRequest.f.toString());
        List<StreamKey> list = downloadRequest.h;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            StreamKey streamKey = list.get(i);
            sb.append(streamKey.c);
            sb.append('.');
            sb.append(streamKey.f);
            sb.append('.');
            sb.append(streamKey.g);
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        contentValues.put("stream_keys", sb.toString());
        contentValues.put("custom_cache_key", downloadRequest.j);
        contentValues.put("data", downloadRequest.k);
        contentValues.put("state", Integer.valueOf(download.b));
        contentValues.put("start_time_ms", Long.valueOf(download.c));
        contentValues.put("update_time_ms", Long.valueOf(download.d));
        contentValues.put("content_length", Long.valueOf(download.e));
        contentValues.put("stop_reason", Integer.valueOf(download.f));
        contentValues.put("failure_reason", Integer.valueOf(download.g));
        contentValues.put("percent_downloaded", Float.valueOf(download.getPercentDownloaded()));
        contentValues.put("bytes_downloaded", Long.valueOf(download.getBytesDownloaded()));
        contentValues.put("key_set_id", bArr);
        sQLiteDatabase.replaceOrThrow(this.b, (String) null, contentValues);
    }

    @Nullable
    public Download getDownload(String str) throws DatabaseIOException {
        Cursor query;
        b();
        try {
            query = this.c.getReadableDatabase().query(this.b, g, "id = ?", new String[]{str}, (String) null, (String) null, "start_time_ms ASC");
            if (query.getCount() == 0) {
                query.close();
                return null;
            }
            query.moveToNext();
            Download c2 = c(query);
            query.close();
            return c2;
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        } catch (SQLiteException e3) {
            throw new DatabaseIOException(e3);
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public DownloadCursor getDownloads(int... iArr) throws DatabaseIOException {
        b();
        try {
            return new a(this.c.getReadableDatabase().query(this.b, g, e(iArr), (String[]) null, (String) null, (String) null, "start_time_ms ASC"));
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public void putDownload(Download download) throws DatabaseIOException {
        b();
        try {
            g(download, this.c.getWritableDatabase());
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public void removeDownload(String str) throws DatabaseIOException {
        b();
        try {
            this.c.getWritableDatabase().delete(this.b, "id = ?", new String[]{str});
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public void setDownloadingStatesToQueued() throws DatabaseIOException {
        b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("state", 0);
            this.c.getWritableDatabase().update(this.b, contentValues, "state = 2", (String[]) null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public void setStatesToRemoving() throws DatabaseIOException {
        b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("state", 5);
            contentValues.put("failure_reason", 0);
            this.c.getWritableDatabase().update(this.b, contentValues, (String) null, (String[]) null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public void setStopReason(int i) throws DatabaseIOException {
        b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("stop_reason", Integer.valueOf(i));
            this.c.getWritableDatabase().update(this.b, contentValues, f, (String[]) null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider, String str) {
        this.a = str;
        this.c = databaseProvider;
        String valueOf = String.valueOf(str);
        this.b = valueOf.length() != 0 ? "ExoPlayerDownloads".concat(valueOf) : new String("ExoPlayerDownloads");
        this.d = new Object();
    }

    public void setStopReason(String str, int i) throws DatabaseIOException {
        b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("stop_reason", Integer.valueOf(i));
            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
            String str2 = this.b;
            String str3 = f;
            StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 11);
            sb.append(str3);
            sb.append(" AND id = ?");
            writableDatabase.update(str2, contentValues, sb.toString(), new String[]{str});
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }
}
