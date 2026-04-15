package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public final class TextInformationFrame extends Id3Frame {
    public static final Parcelable.Creator<TextInformationFrame> CREATOR = new a();
    @Nullable
    public final String f;
    public final String g;

    public class a implements Parcelable.Creator<TextInformationFrame> {
        public TextInformationFrame createFromParcel(Parcel parcel) {
            return new TextInformationFrame(parcel);
        }

        public TextInformationFrame[] newArray(int i) {
            return new TextInformationFrame[i];
        }
    }

    public TextInformationFrame(String str, @Nullable String str2, String str3) {
        super(str);
        this.f = str2;
        this.g = str3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TextInformationFrame.class != obj.getClass()) {
            return false;
        }
        TextInformationFrame textInformationFrame = (TextInformationFrame) obj;
        if (!Util.areEqual(this.c, textInformationFrame.c) || !Util.areEqual(this.f, textInformationFrame.f) || !Util.areEqual(this.g, textInformationFrame.g)) {
            return false;
        }
        return true;
    }

    @Nullable
    public /* bridge */ /* synthetic */ byte[] getWrappedMetadataBytes() {
        return q6.a(this);
    }

    @Nullable
    public /* bridge */ /* synthetic */ Format getWrappedMetadataFormat() {
        return q6.b(this);
    }

    public int hashCode() {
        int i;
        int hashCode = (this.c.hashCode() + 527) * 31;
        int i2 = 0;
        String str = this.f;
        if (str != null) {
            i = str.hashCode();
        } else {
            i = 0;
        }
        int i3 = (hashCode + i) * 31;
        String str2 = this.g;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return i3 + i2;
    }

    public void populateMediaMetadata(MediaMetadata.Builder builder) {
        Integer num;
        String str = this.c;
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case 82815:
                if (str.equals("TAL")) {
                    c = 0;
                    break;
                }
                break;
            case 83253:
                if (str.equals("TP1")) {
                    c = 1;
                    break;
                }
                break;
            case 83254:
                if (str.equals("TP2")) {
                    c = 2;
                    break;
                }
                break;
            case 83341:
                if (str.equals("TRK")) {
                    c = 3;
                    break;
                }
                break;
            case 83378:
                if (str.equals("TT2")) {
                    c = 4;
                    break;
                }
                break;
            case 83552:
                if (str.equals("TYE")) {
                    c = 5;
                    break;
                }
                break;
            case 2567331:
                if (str.equals("TALB")) {
                    c = 6;
                    break;
                }
                break;
            case 2575251:
                if (str.equals("TIT2")) {
                    c = 7;
                    break;
                }
                break;
            case 2581512:
                if (str.equals("TPE1")) {
                    c = 8;
                    break;
                }
                break;
            case 2581513:
                if (str.equals("TPE2")) {
                    c = 9;
                    break;
                }
                break;
            case 2583398:
                if (str.equals("TRCK")) {
                    c = 10;
                    break;
                }
                break;
            case 2590194:
                if (str.equals("TYER")) {
                    c = 11;
                    break;
                }
                break;
        }
        String str2 = this.g;
        switch (c) {
            case 0:
            case 6:
                builder.setAlbumTitle(str2);
                return;
            case 1:
            case 8:
                builder.setArtist(str2);
                return;
            case 2:
            case 9:
                builder.setAlbumArtist(str2);
                return;
            case 3:
            case 10:
                String[] split = Util.split(str2, MqttTopic.TOPIC_LEVEL_SEPARATOR);
                int parseInt = Integer.parseInt(split[0]);
                if (split.length > 1) {
                    num = Integer.valueOf(Integer.parseInt(split[1]));
                } else {
                    num = null;
                }
                builder.setTrackNumber(Integer.valueOf(parseInt)).setTotalTrackCount(num);
                return;
            case 4:
            case 7:
                builder.setTitle(str2);
                return;
            case 5:
            case 11:
                try {
                    builder.setYear(Integer.valueOf(Integer.parseInt(str2)));
                    return;
                } catch (NumberFormatException unused) {
                }
            default:
                return;
        }
    }

    public String toString() {
        String str = this.c;
        int c = y2.c(str, 22);
        String str2 = this.f;
        int c2 = y2.c(str2, c);
        String str3 = this.g;
        StringBuilder l = y2.l(y2.c(str3, c2), str, ": description=", str2, ": value=");
        l.append(str3);
        return l.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.c);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
    }

    public TextInformationFrame(Parcel parcel) {
        super((String) Util.castNonNull(parcel.readString()));
        this.f = parcel.readString();
        this.g = (String) Util.castNonNull(parcel.readString());
    }
}
