package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = "ActivityChooserModel";
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo2) {
            this.resolveInfo = resolveInfo2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && ActivityResolveInfo.class == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            return "[resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }

        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = list.get(i);
                activityResolveInfo.weight = 0.0f;
                ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
                map.put(new ComponentName(activityInfo.packageName, activityInfo.name), activityResolveInfo);
            }
            float f = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight = (historicalRecord.weight * f) + activityResolveInfo2.weight;
                    f *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || HistoricalRecord.class != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.activity)) {
                return false;
            }
            if (this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                i = 0;
            } else {
                i = componentName.hashCode();
            }
            long j = this.time;
            return Float.floatToIntBits(this.weight) + ((((i + 31) * 31) + ((int) (j ^ (j >>> 32)))) * 31);
        }

        public String toString() {
            return "[; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal((double) this.weight) + "]";
        }

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            this.activity = componentName;
            this.time = j;
            this.weight = f;
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        public PersistHistoryAsyncTask() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0069, code lost:
            if (r14 != null) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r14.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x006f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME;
            r0 = r13.this$0;
            r1 = r0.mHistoryFileName;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0077, code lost:
            r0.mCanReadHistoricalData = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0079, code lost:
            if (r14 == null) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME;
            r0 = r13.this$0;
            r1 = r0.mHistoryFileName;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0082, code lost:
            r0.mCanReadHistoricalData = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0084, code lost:
            if (r14 == null) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME;
            r0 = r13.this$0;
            r1 = r0.mHistoryFileName;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x008d, code lost:
            r0.mCanReadHistoricalData = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x008f, code lost:
            if (r14 == null) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0093, code lost:
            r13.this$0.mCanReadHistoricalData = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0097, code lost:
            if (r14 != null) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r14.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x009c, code lost:
            throw r0;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [B:14:0x0071, B:18:0x007c, B:22:0x0087] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0071 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x007c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0087 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Object... r14) {
            /*
                r13 = this;
                java.lang.String r0 = "historical-record"
                java.lang.String r1 = "historical-records"
                r2 = 0
                r3 = r14[r2]
                java.util.List r3 = (java.util.List) r3
                r4 = 1
                r14 = r14[r4]
                java.lang.String r14 = (java.lang.String) r14
                r5 = 0
                androidx.appcompat.widget.ActivityChooserModel r6 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ FileNotFoundException -> 0x009d }
                android.content.Context r6 = r6.mContext     // Catch:{ FileNotFoundException -> 0x009d }
                java.io.FileOutputStream r14 = r6.openFileOutput(r14, r2)     // Catch:{ FileNotFoundException -> 0x009d }
                org.xmlpull.v1.XmlSerializer r6 = android.util.Xml.newSerializer()
                r6.setOutput(r14, r5)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r7 = "UTF-8"
                java.lang.Boolean r8 = java.lang.Boolean.TRUE     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.startDocument(r7, r8)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.startTag(r5, r1)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                int r7 = r3.size()     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r8 = 0
            L_0x002d:
                if (r8 >= r7) goto L_0x005f
                java.lang.Object r9 = r3.remove(r2)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord r9 = (androidx.appcompat.widget.ActivityChooserModel.HistoricalRecord) r9     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.startTag(r5, r0)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r10 = "activity"
                android.content.ComponentName r11 = r9.activity     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r11 = r11.flattenToString()     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.attribute(r5, r10, r11)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r10 = "time"
                long r11 = r9.time     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.attribute(r5, r10, r11)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r10 = "weight"
                float r9 = r9.weight     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.attribute(r5, r10, r9)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.endTag(r5, r0)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                int r8 = r8 + 1
                goto L_0x002d
            L_0x005f:
                r6.endTag(r5, r1)     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                r6.endDocument()     // Catch:{ IllegalArgumentException -> 0x0087, IllegalStateException -> 0x007c, IOException -> 0x0071 }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r4
                if (r14 == 0) goto L_0x0092
            L_0x006b:
                r14.close()     // Catch:{ IOException -> 0x0092 }
                goto L_0x0092
            L_0x006f:
                r0 = move-exception
                goto L_0x0093
            L_0x0071:
                java.lang.String r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME     // Catch:{ all -> 0x006f }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x006f }
                java.lang.String r1 = r0.mHistoryFileName     // Catch:{ all -> 0x006f }
                r0.mCanReadHistoricalData = r4
                if (r14 == 0) goto L_0x0092
                goto L_0x006b
            L_0x007c:
                java.lang.String r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME     // Catch:{ all -> 0x006f }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x006f }
                java.lang.String r1 = r0.mHistoryFileName     // Catch:{ all -> 0x006f }
                r0.mCanReadHistoricalData = r4
                if (r14 == 0) goto L_0x0092
                goto L_0x006b
            L_0x0087:
                java.lang.String r0 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME     // Catch:{ all -> 0x006f }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x006f }
                java.lang.String r1 = r0.mHistoryFileName     // Catch:{ all -> 0x006f }
                r0.mCanReadHistoricalData = r4
                if (r14 == 0) goto L_0x0092
                goto L_0x006b
            L_0x0092:
                return r5
            L_0x0093:
                androidx.appcompat.widget.ActivityChooserModel r1 = androidx.appcompat.widget.ActivityChooserModel.this
                r1.mCanReadHistoricalData = r4
                if (r14 == 0) goto L_0x009c
                r14.close()     // Catch:{ IOException -> 0x009c }
            L_0x009c:
                throw r0
            L_0x009d:
                java.lang.String r14 = androidx.appcompat.widget.ActivityChooserModel.DEFAULT_HISTORY_FILE_NAME
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.PersistHistoryAsyncTask.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }

    private ActivityChooserModel(Context context, String str) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = str;
        } else {
            this.mHistoryFileName = str.concat(HISTORY_FILE_EXTENSION);
        }
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean add = this.mHistoricalRecords.add(historicalRecord);
        if (add) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return add;
    }

    private void ensureConsistentState() {
        boolean loadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (loadActivitiesIfNeeded) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            Map<String, ActivityChooserModel> map = sDataModelRegistry;
            activityChooserModel = map.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                map.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = queryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            this.mActivities.add(new ActivityResolveInfo(queryIntentActivities.get(i)));
        }
        return true;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList(this.mHistoricalRecords), this.mHistoryFileName});
            }
        }
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < size; i++) {
                HistoricalRecord remove = this.mHistoricalRecords.remove(0);
            }
        }
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readHistoricalDataImpl() {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext     // Catch:{ FileNotFoundException -> 0x008e }
            java.lang.String r1 = r9.mHistoryFileName     // Catch:{ FileNotFoundException -> 0x008e }
            java.io.FileInputStream r0 = r0.openFileInput(r1)     // Catch:{ FileNotFoundException -> 0x008e }
            org.xmlpull.v1.XmlPullParser r1 = android.util.Xml.newPullParser()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r2 = "UTF-8"
            r1.setInput(r0, r2)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            r2 = 0
        L_0x0012:
            r3 = 1
            if (r2 == r3) goto L_0x001d
            r4 = 2
            if (r2 == r4) goto L_0x001d
            int r2 = r1.next()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            goto L_0x0012
        L_0x001d:
            java.lang.String r2 = "historical-records"
            java.lang.String r4 = r1.getName()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            boolean r2 = r2.equals(r4)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            if (r2 == 0) goto L_0x0079
            java.util.List<androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord> r2 = r9.mHistoricalRecords     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            r2.clear()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
        L_0x002e:
            int r4 = r1.next()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            if (r4 != r3) goto L_0x003a
            if (r0 == 0) goto L_0x008e
        L_0x0036:
            r0.close()     // Catch:{  }
            goto L_0x008e
        L_0x003a:
            r5 = 3
            if (r4 == r5) goto L_0x002e
            r5 = 4
            if (r4 != r5) goto L_0x0041
            goto L_0x002e
        L_0x0041:
            java.lang.String r4 = r1.getName()     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r5 = "historical-record"
            boolean r4 = r5.equals(r4)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            if (r4 == 0) goto L_0x0071
            java.lang.String r4 = "activity"
            r5 = 0
            java.lang.String r4 = r1.getAttributeValue(r5, r4)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r6 = "time"
            java.lang.String r6 = r1.getAttributeValue(r5, r6)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r8 = "weight"
            java.lang.String r5 = r1.getAttributeValue(r5, r8)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            float r5 = java.lang.Float.parseFloat(r5)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord r8 = new androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            r8.<init>((java.lang.String) r4, (long) r6, (float) r5)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            r2.add(r8)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            goto L_0x002e
        L_0x0071:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r2 = "Share records file not well-formed."
            r1.<init>(r2)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            throw r1     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
        L_0x0079:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            java.lang.String r2 = "Share records file does not start with historical-records tag."
            r1.<init>(r2)     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
            throw r1     // Catch:{ XmlPullParserException -> 0x008b, IOException -> 0x0088, all -> 0x0081 }
        L_0x0081:
            r1 = move-exception
            if (r0 == 0) goto L_0x0087
            r0.close()     // Catch:{ IOException -> 0x0087 }
        L_0x0087:
            throw r1
        L_0x0088:
            if (r0 == 0) goto L_0x008e
            goto L_0x0036
        L_0x008b:
            if (r0 == 0) goto L_0x008e
            goto L_0x0036
        L_0x008e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    public Intent chooseActivity(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityInfo activityInfo = this.mActivities.get(i).resolveInfo.activityInfo;
            ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
            Intent intent = new Intent(this.mIntent);
            intent.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(componentName, System.currentTimeMillis(), (float) DEFAULT_HISTORICAL_RECORD_WEIGHT));
            return intent;
        }
    }

    public ResolveInfo getActivity(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(i).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = this.mActivities.get(0).resolveInfo;
            return resolveInfo;
        }
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActivitySorter(androidx.appcompat.widget.ActivityChooserModel.ActivitySorter r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            androidx.appcompat.widget.ActivityChooserModel$ActivitySorter r1 = r2.mActivitySorter     // Catch:{ all -> 0x0016 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0009:
            r2.mActivitySorter = r3     // Catch:{ all -> 0x0016 }
            boolean r3 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0016 }
            if (r3 == 0) goto L_0x0014
            r2.notifyChanged()     // Catch:{ all -> 0x0016 }
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0016:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setActivitySorter(androidx.appcompat.widget.ActivityChooserModel$ActivitySorter):void");
    }

    public void setDefaultActivity(int i) {
        float f;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(i);
            ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
            if (activityResolveInfo2 != null) {
                f = (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f;
            } else {
                f = DEFAULT_HISTORICAL_RECORD_WEIGHT;
            }
            ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
            addHistoricalRecord(new HistoricalRecord(new ComponentName(activityInfo.packageName, activityInfo.name), System.currentTimeMillis(), f));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHistoryMaxSize(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            int r1 = r2.mHistoryMaxSize     // Catch:{ all -> 0x0019 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0009:
            r2.mHistoryMaxSize = r3     // Catch:{ all -> 0x0019 }
            r2.pruneExcessiveHistoricalRecordsIfNeeded()     // Catch:{ all -> 0x0019 }
            boolean r3 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0019 }
            if (r3 == 0) goto L_0x0017
            r2.notifyChanged()     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0019:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent != intent) {
                this.mIntent = intent;
                this.mReloadActivities = true;
                ensureConsistentState();
            }
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }
}
