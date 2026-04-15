package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: ma  reason: default package */
public final class ma extends TagPayloadReader {
    public long b = -9223372036854775807L;
    public long[] c = new long[0];
    public long[] d = new long[0];

    public ma() {
        super(new DummyTrackOutput());
    }

    @Nullable
    public static Serializable c(int i, ParsableByteArray parsableByteArray) {
        if (i == 0) {
            return Double.valueOf(Double.longBitsToDouble(parsableByteArray.readLong()));
        }
        boolean z = true;
        if (i == 1) {
            if (parsableByteArray.readUnsignedByte() != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        } else if (i == 2) {
            return e(parsableByteArray);
        } else {
            if (i == 3) {
                HashMap hashMap = new HashMap();
                while (true) {
                    String e = e(parsableByteArray);
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte == 9) {
                        return hashMap;
                    }
                    Serializable c2 = c(readUnsignedByte, parsableByteArray);
                    if (c2 != null) {
                        hashMap.put(e, c2);
                    }
                }
            } else if (i == 8) {
                return d(parsableByteArray);
            } else {
                if (i == 10) {
                    int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                    ArrayList arrayList = new ArrayList(readUnsignedIntToInt);
                    for (int i2 = 0; i2 < readUnsignedIntToInt; i2++) {
                        Serializable c3 = c(parsableByteArray.readUnsignedByte(), parsableByteArray);
                        if (c3 != null) {
                            arrayList.add(c3);
                        }
                    }
                    return arrayList;
                } else if (i != 11) {
                    return null;
                } else {
                    Date date = new Date((long) Double.valueOf(Double.longBitsToDouble(parsableByteArray.readLong())).doubleValue());
                    parsableByteArray.skipBytes(2);
                    return date;
                }
            }
        }
    }

    public static HashMap<String, Object> d(ParsableByteArray parsableByteArray) {
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        HashMap<String, Object> hashMap = new HashMap<>(readUnsignedIntToInt);
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            String e = e(parsableByteArray);
            Serializable c2 = c(parsableByteArray.readUnsignedByte(), parsableByteArray);
            if (c2 != null) {
                hashMap.put(e, c2);
            }
        }
        return hashMap;
    }

    public static String e(ParsableByteArray parsableByteArray) {
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(readUnsignedShort);
        return new String(parsableByteArray.getData(), position, readUnsignedShort);
    }

    public final boolean a(ParsableByteArray parsableByteArray) {
        return true;
    }

    public final boolean b(ParsableByteArray parsableByteArray, long j) {
        if (parsableByteArray.readUnsignedByte() != 2 || !"onMetaData".equals(e(parsableByteArray)) || parsableByteArray.readUnsignedByte() != 8) {
            return false;
        }
        HashMap<String, Object> d2 = d(parsableByteArray);
        Object obj = d2.get("duration");
        if (obj instanceof Double) {
            double doubleValue = ((Double) obj).doubleValue();
            if (doubleValue > 0.0d) {
                this.b = (long) (doubleValue * 1000000.0d);
            }
        }
        Object obj2 = d2.get("keyframes");
        if (obj2 instanceof Map) {
            Map map = (Map) obj2;
            Object obj3 = map.get("filepositions");
            Object obj4 = map.get("times");
            if ((obj3 instanceof List) && (obj4 instanceof List)) {
                List list = (List) obj3;
                List list2 = (List) obj4;
                int size = list2.size();
                this.c = new long[size];
                this.d = new long[size];
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    Object obj5 = list.get(i);
                    Object obj6 = list2.get(i);
                    if (!(obj6 instanceof Double) || !(obj5 instanceof Double)) {
                        this.c = new long[0];
                        this.d = new long[0];
                    } else {
                        this.c[i] = (long) (((Double) obj6).doubleValue() * 1000000.0d);
                        this.d[i] = ((Double) obj5).longValue();
                        i++;
                    }
                }
                this.c = new long[0];
                this.d = new long[0];
            }
        }
        return false;
    }

    public long getDurationUs() {
        return this.b;
    }

    public long[] getKeyFrameTagPositions() {
        return this.d;
    }

    public long[] getKeyFrameTimesUs() {
        return this.c;
    }

    public void seek() {
    }
}
