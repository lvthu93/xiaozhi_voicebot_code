package org.eclipse.paho.android.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import j$.util.Iterator;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;
import org.eclipse.paho.android.service.MessageStore;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class DatabaseMessageStore implements MessageStore {
    private static final String ARRIVED_MESSAGE_TABLE_NAME = "MqttArrivedMessageTable";
    private static final String MTIMESTAMP = "mtimestamp";
    private static final String TAG = "DatabaseMessageStore";
    /* access modifiers changed from: private */
    public SQLiteDatabase db = null;
    /* access modifiers changed from: private */
    public MQTTDatabaseHelper mqttDb = null;
    private MqttTraceHandler traceHandler;

    public class DbStoredData implements MessageStore.StoredMessage {
        private String clientHandle;
        private MqttMessage message;
        private String messageId;
        private String topic;

        public DbStoredData(String str, String str2, String str3, MqttMessage mqttMessage) {
            this.messageId = str;
            this.topic = str3;
            this.message = mqttMessage;
        }

        public String getClientHandle() {
            return this.clientHandle;
        }

        public MqttMessage getMessage() {
            return this.message;
        }

        public String getMessageId() {
            return this.messageId;
        }

        public String getTopic() {
            return this.topic;
        }
    }

    public static class MQTTDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mqttAndroidService.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TAG = "MQTTDatabaseHelper";
        private MqttTraceHandler traceHandler;

        public MQTTDatabaseHelper(MqttTraceHandler mqttTraceHandler, Context context) {
            super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
            this.traceHandler = mqttTraceHandler;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            this.traceHandler.traceDebug(TAG, "onCreate {CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);}");
            try {
                sQLiteDatabase.execSQL("CREATE TABLE MqttArrivedMessageTable(messageId TEXT PRIMARY KEY, clientHandle TEXT, destinationName TEXT, payload BLOB, qos INTEGER, retained TEXT, duplicate TEXT, mtimestamp INTEGER);");
                this.traceHandler.traceDebug(TAG, "created the table");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onCreate", e);
                throw e;
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            this.traceHandler.traceDebug(TAG, "onUpgrade");
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MqttArrivedMessageTable");
                onCreate(sQLiteDatabase);
                this.traceHandler.traceDebug(TAG, "onUpgrade complete");
            } catch (SQLException e) {
                this.traceHandler.traceException(TAG, "onUpgrade", e);
                throw e;
            }
        }
    }

    public class MqttMessageHack extends MqttMessage {
        public MqttMessageHack(byte[] bArr) {
            super(bArr);
        }

        public void setDuplicate(boolean z) {
            super.setDuplicate(z);
        }
    }

    public DatabaseMessageStore(MqttService mqttService, Context context) {
        this.traceHandler = mqttService;
        this.mqttDb = new MQTTDatabaseHelper(this.traceHandler, context);
        this.traceHandler.traceDebug(TAG, "DatabaseMessageStore<init> complete");
    }

    private int getArrivedRowCount(String str) {
        int i = 0;
        Cursor query = this.db.query(ARRIVED_MESSAGE_TABLE_NAME, new String[]{MqttServiceConstants.MESSAGE_ID}, "clientHandle=?", new String[]{str}, (String) null, (String) null, (String) null);
        if (query.moveToFirst()) {
            i = query.getInt(0);
        }
        query.close();
        return i;
    }

    public void clearArrivedMessages(String str) {
        int i;
        this.db = this.mqttDb.getWritableDatabase();
        String[] strArr = {str};
        if (str == null) {
            this.traceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table");
            i = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, (String) null, (String[]) null);
        } else {
            MqttTraceHandler mqttTraceHandler = this.traceHandler;
            mqttTraceHandler.traceDebug(TAG, "clearArrivedMessages: clearing the table of " + str + " messages");
            i = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, "clientHandle=?", strArr);
        }
        MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
        mqttTraceHandler2.traceDebug(TAG, "clearArrivedMessages: rows affected = " + i);
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.db;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }

    public boolean discardArrived(String str, String str2) {
        this.db = this.mqttDb.getWritableDatabase();
        MqttTraceHandler mqttTraceHandler = this.traceHandler;
        mqttTraceHandler.traceDebug(TAG, "discardArrived{" + str + "}, {" + str2 + "}");
        try {
            int delete = this.db.delete(ARRIVED_MESSAGE_TABLE_NAME, "messageId=? AND clientHandle=?", new String[]{str2, str});
            if (delete != 1) {
                MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
                mqttTraceHandler2.traceError(TAG, "discardArrived - Error deleting message {" + str2 + "} from database: Rows affected = " + delete);
                return false;
            }
            int arrivedRowCount = getArrivedRowCount(str);
            MqttTraceHandler mqttTraceHandler3 = this.traceHandler;
            mqttTraceHandler3.traceDebug(TAG, "discardArrived - Message deleted successfully. - messages in db for this clientHandle " + arrivedRowCount);
            return true;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "discardArrived", e);
            throw e;
        }
    }

    public Iterator<MessageStore.StoredMessage> getAllArrivedMessages(String str) {
        return new Object(this, str) {
            private Cursor c;
            private boolean hasNext;
            private final String[] selectionArgs;
            final /* synthetic */ DatabaseMessageStore this$0;
            final /* synthetic */ String val$clientHandle;

            {
                DatabaseMessageStore databaseMessageStore = r18;
                String str = r19;
                this.this$0 = databaseMessageStore;
                this.val$clientHandle = str;
                String[] strArr = {str};
                this.selectionArgs = strArr;
                SQLiteDatabase unused = databaseMessageStore.db = r18.mqttDb.getWritableDatabase();
                if (str == null) {
                    this.c = r18.db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "mtimestamp ASC");
                } else {
                    this.c = r18.db.query(DatabaseMessageStore.ARRIVED_MESSAGE_TABLE_NAME, (String[]) null, "clientHandle=?", strArr, (String) null, (String) null, "mtimestamp ASC");
                }
                this.hasNext = this.c.moveToFirst();
            }

            public void finalize() throws Throwable {
                this.c.close();
                super.finalize();
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                if (!this.hasNext) {
                    this.c.close();
                }
                return this.hasNext;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public MessageStore.StoredMessage next() {
                Cursor cursor = this.c;
                String string = cursor.getString(cursor.getColumnIndex(MqttServiceConstants.MESSAGE_ID));
                Cursor cursor2 = this.c;
                String string2 = cursor2.getString(cursor2.getColumnIndex(MqttServiceConstants.CLIENT_HANDLE));
                Cursor cursor3 = this.c;
                String string3 = cursor3.getString(cursor3.getColumnIndex(MqttServiceConstants.DESTINATION_NAME));
                Cursor cursor4 = this.c;
                byte[] blob = cursor4.getBlob(cursor4.getColumnIndex(MqttServiceConstants.PAYLOAD));
                Cursor cursor5 = this.c;
                int i = cursor5.getInt(cursor5.getColumnIndex(MqttServiceConstants.QOS));
                Cursor cursor6 = this.c;
                boolean parseBoolean = Boolean.parseBoolean(cursor6.getString(cursor6.getColumnIndex(MqttServiceConstants.RETAINED)));
                Cursor cursor7 = this.c;
                boolean parseBoolean2 = Boolean.parseBoolean(cursor7.getString(cursor7.getColumnIndex(MqttServiceConstants.DUPLICATE)));
                MqttMessageHack mqttMessageHack = new MqttMessageHack(blob);
                mqttMessageHack.setQos(i);
                mqttMessageHack.setRetained(parseBoolean);
                mqttMessageHack.setDuplicate(parseBoolean2);
                this.hasNext = this.c.moveToNext();
                return new DbStoredData(string, string2, string3, mqttMessageHack);
            }
        };
    }

    public String storeArrived(String str, String str2, MqttMessage mqttMessage) {
        this.db = this.mqttDb.getWritableDatabase();
        MqttTraceHandler mqttTraceHandler = this.traceHandler;
        mqttTraceHandler.traceDebug(TAG, "storeArrived{" + str + "}, {" + mqttMessage.toString() + "}");
        byte[] payload = mqttMessage.getPayload();
        int qos = mqttMessage.getQos();
        boolean isRetained = mqttMessage.isRetained();
        boolean isDuplicate = mqttMessage.isDuplicate();
        ContentValues contentValues = new ContentValues();
        String uuid = UUID.randomUUID().toString();
        contentValues.put(MqttServiceConstants.MESSAGE_ID, uuid);
        contentValues.put(MqttServiceConstants.CLIENT_HANDLE, str);
        contentValues.put(MqttServiceConstants.DESTINATION_NAME, str2);
        contentValues.put(MqttServiceConstants.PAYLOAD, payload);
        contentValues.put(MqttServiceConstants.QOS, Integer.valueOf(qos));
        contentValues.put(MqttServiceConstants.RETAINED, Boolean.valueOf(isRetained));
        contentValues.put(MqttServiceConstants.DUPLICATE, Boolean.valueOf(isDuplicate));
        contentValues.put(MTIMESTAMP, Long.valueOf(System.currentTimeMillis()));
        try {
            this.db.insertOrThrow(ARRIVED_MESSAGE_TABLE_NAME, (String) null, contentValues);
            int arrivedRowCount = getArrivedRowCount(str);
            MqttTraceHandler mqttTraceHandler2 = this.traceHandler;
            mqttTraceHandler2.traceDebug(TAG, "storeArrived: inserted message with id of {" + uuid + "} - Number of messages in database for this clientHandle = " + arrivedRowCount);
            return uuid;
        } catch (SQLException e) {
            this.traceHandler.traceException(TAG, "onUpgrade", e);
            throw e;
        }
    }
}
