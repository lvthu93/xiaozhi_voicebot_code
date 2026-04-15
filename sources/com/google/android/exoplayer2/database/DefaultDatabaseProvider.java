package com.google.android.exoplayer2.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DefaultDatabaseProvider implements DatabaseProvider {
    public final SQLiteOpenHelper c;

    public DefaultDatabaseProvider(SQLiteOpenHelper sQLiteOpenHelper) {
        this.c = sQLiteOpenHelper;
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.c.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.c.getWritableDatabase();
    }
}
