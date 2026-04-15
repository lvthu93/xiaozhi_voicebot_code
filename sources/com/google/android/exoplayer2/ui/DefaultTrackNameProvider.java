package com.google.android.exoplayer2.ui;

import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import info.dourok.voicebot.R;
import java.util.Locale;

public class DefaultTrackNameProvider implements TrackNameProvider {
    public final Resources a;

    public DefaultTrackNameProvider(Resources resources) {
        this.a = (Resources) Assertions.checkNotNull(resources);
    }

    public final String a(Format format) {
        int i = format.l;
        if (i == -1) {
            return "";
        }
        return this.a.getString(R.string.exo_track_bitrate, new Object[]{Float.valueOf(((float) i) / 1000000.0f)});
    }

    public final String b(Format format) {
        String str;
        Locale locale;
        String[] strArr = new String[2];
        String str2 = format.g;
        String str3 = "";
        if (TextUtils.isEmpty(str2) || "und".equals(str2)) {
            str = str3;
        } else {
            if (Util.a >= 21) {
                locale = Locale.forLanguageTag(str2);
            } else {
                locale = new Locale(str2);
            }
            str = locale.getDisplayName();
        }
        strArr[0] = str;
        strArr[1] = c(format);
        String d = d(strArr);
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        String str4 = format.f;
        if (!TextUtils.isEmpty(str4)) {
            str3 = str4;
        }
        return str3;
    }

    public final String c(Format format) {
        String str;
        int i = format.i & 2;
        Resources resources = this.a;
        if (i != 0) {
            str = resources.getString(R.string.exo_track_role_alternate);
        } else {
            str = "";
        }
        int i2 = format.i;
        if ((i2 & 4) != 0) {
            str = d(str, resources.getString(R.string.exo_track_role_supplementary));
        }
        if ((i2 & 8) != 0) {
            str = d(str, resources.getString(R.string.exo_track_role_commentary));
        }
        if ((i2 & 1088) == 0) {
            return str;
        }
        return d(str, resources.getString(R.string.exo_track_role_closed_captions));
    }

    public final String d(String... strArr) {
        String str = "";
        for (String str2 : strArr) {
            if (str2.length() > 0) {
                if (TextUtils.isEmpty(str)) {
                    str = str2;
                } else {
                    str = this.a.getString(R.string.exo_item_list, new Object[]{str, str2});
                }
            }
        }
        return str;
    }

    public String getTrackName(Format format) {
        String str;
        int i;
        int trackType = MimeTypes.getTrackType(format.p);
        if (trackType == -1) {
            String str2 = format.m;
            if (MimeTypes.getVideoMediaMimeType(str2) == null) {
                if (MimeTypes.getAudioMediaMimeType(str2) == null) {
                    if (format.u == -1 && format.v == -1) {
                        if (format.ac == -1 && format.ad == -1) {
                            trackType = -1;
                        }
                    }
                }
                trackType = 1;
            }
            trackType = 2;
        }
        String str3 = "";
        Resources resources = this.a;
        if (trackType == 2) {
            String[] strArr = new String[3];
            strArr[0] = c(format);
            int i2 = format.u;
            if (!(i2 == -1 || (i = format.v) == -1)) {
                str3 = resources.getString(R.string.exo_track_resolution, new Object[]{Integer.valueOf(i2), Integer.valueOf(i)});
            }
            strArr[1] = str3;
            strArr[2] = a(format);
            str = d(strArr);
        } else if (trackType == 1) {
            String[] strArr2 = new String[3];
            strArr2[0] = b(format);
            int i3 = format.ac;
            if (i3 != -1 && i3 >= 1) {
                str3 = i3 != 1 ? i3 != 2 ? (i3 == 6 || i3 == 7) ? resources.getString(R.string.exo_track_surround_5_point_1) : i3 != 8 ? resources.getString(R.string.exo_track_surround) : resources.getString(R.string.exo_track_surround_7_point_1) : resources.getString(R.string.exo_track_stereo) : resources.getString(R.string.exo_track_mono);
            }
            strArr2[1] = str3;
            strArr2[2] = a(format);
            str = d(strArr2);
        } else {
            str = b(format);
        }
        if (str.length() == 0) {
            return resources.getString(R.string.exo_track_unknown);
        }
        return str;
    }
}
