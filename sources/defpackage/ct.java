package defpackage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* renamed from: ct  reason: default package */
public final class ct {
    public static final String[] c = {"name", "length", "last_touch_timestamp"};
    public final DatabaseProvider a;
    public String b;

    public ct(DatabaseProvider databaseProvider) {
        this.a = databaseProvider;
    }

    @WorkerThread
    public static void delete(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
        String str;
        SQLiteDatabase writableDatabase;
        String str2;
        String hexString = Long.toHexString(j);
        try {
            String valueOf = String.valueOf(hexString);
            if (valueOf.length() != 0) {
                str = "ExoPlayerCacheFileMetadata".concat(valueOf);
            } else {
                str = new String("ExoPlayerCacheFileMetadata");
            }
            writableDatabase = databaseProvider.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            VersionTable.removeVersion(writableDatabase, 2, hexString);
            String valueOf2 = String.valueOf(str);
            if (valueOf2.length() != 0) {
                str2 = "DROP TABLE IF EXISTS ".concat(valueOf2);
            } else {
                str2 = new String("DROP TABLE IF EXISTS ");
            }
            writableDatabase.execSQL(str2);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @WorkerThread
    public Map<String, cs> getAll() throws DatabaseIOException {
        Cursor query;
        try {
            Assertions.checkNotNull(this.b);
            query = this.a.getReadableDatabase().query(this.b, c, (String) null, (String[]) null, (String) null, (String) null, (String) null);
            HashMap hashMap = new HashMap(query.getCount());
            while (query.moveToNext()) {
                hashMap.put(query.getString(0), new cs(query.getLong(1), query.getLong(2)));
            }
            query.close();
            return hashMap;
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    @WorkerThread
    public void initialize(long j) throws DatabaseIOException {
        String str;
        SQLiteDatabase writableDatabase;
        String str2;
        DatabaseProvider databaseProvider = this.a;
        try {
            String hexString = Long.toHexString(j);
            String valueOf = String.valueOf(hexString);
            if (valueOf.length() != 0) {
                str = "ExoPlayerCacheFileMetadata".concat(valueOf);
            } else {
                str = new String("ExoPlayerCacheFileMetadata");
            }
            this.b = str;
            if (VersionTable.getVersion(databaseProvider.getReadableDatabase(), 2, hexString) != 1) {
                writableDatabase = databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.setVersion(writableDatabase, 2, hexString, 1);
                String valueOf2 = String.valueOf(this.b);
                if (valueOf2.length() != 0) {
                    str2 = "DROP TABLE IF EXISTS ".concat(valueOf2);
                } else {
                    str2 = new String("DROP TABLE IF EXISTS ");
                }
                writableDatabase.execSQL(str2);
                String str3 = this.b;
                StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 108);
                sb.append("CREATE TABLE ");
                sb.append(str3);
                sb.append(" (name TEXT PRIMARY KEY NOT NULL,length INTEGER NOT NULL,last_touch_timestamp INTEGER NOT NULL)");
                writableDatabase.execSQL(sb.toString());
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @WorkerThread
    public void remove(String str) throws DatabaseIOException {
        Assertions.checkNotNull(this.b);
        try {
            this.a.getWritableDatabase().delete(this.b, "name = ?", new String[]{str});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    @WorkerThread
    public void removeAll(Set<String> set) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        Assertions.checkNotNull(this.b);
        try {
            writableDatabase = this.a.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            for (String str : set) {
                writableDatabase.delete(this.b, "name = ?", new String[]{str});
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @WorkerThread
    public void set(String str, long j, long j2) throws DatabaseIOException {
        Assertions.checkNotNull(this.b);
        try {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("length", Long.valueOf(j));
            contentValues.put("last_touch_timestamp", Long.valueOf(j2));
            writableDatabase.replaceOrThrow(this.b, (String) null, contentValues);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }
}
