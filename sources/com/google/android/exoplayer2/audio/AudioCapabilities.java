package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;

public final class AudioCapabilities {
    public static final AudioCapabilities c = new AudioCapabilities(new int[]{2}, 8);
    public static final AudioCapabilities d = new AudioCapabilities(new int[]{2, 5, 6}, 8);
    public static final int[] e = {5, 6, 18, 17, 14, 7, 8};
    public final int[] a;
    public final int b;

    @RequiresApi(29)
    public static final class a {
        @DoNotInline
        public static int[] getDirectPlaybackSupportedEncodingsV29() {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (int i : AudioCapabilities.e) {
                if (AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setChannelMask(12).setEncoding(i).setSampleRate(48000).build(), new AudioAttributes.Builder().setUsage(1).setContentType(3).setFlags(0).build())) {
                    builder.add((Object) Integer.valueOf(i));
                }
            }
            builder.add((Object) 2);
            return y3.c(builder.build());
        }
    }

    public AudioCapabilities(@Nullable int[] iArr, int i) {
        if (iArr != null) {
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            this.a = copyOf;
            Arrays.sort(copyOf);
        } else {
            this.a = new int[0];
        }
        this.b = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
    @android.annotation.SuppressLint({"InlinedApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.audio.AudioCapabilities a(android.content.Context r5, @androidx.annotation.Nullable android.content.Intent r6) {
        /*
            int r0 = com.google.android.exoplayer2.util.Util.a
            r1 = 17
            r2 = 1
            r3 = 0
            if (r0 < r1) goto L_0x001c
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.c
            java.lang.String r4 = "Amazon"
            boolean r4 = r4.equals(r1)
            if (r4 != 0) goto L_0x001a
            java.lang.String r4 = "Xiaomi"
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L_0x001c
        L_0x001a:
            r1 = 1
            goto L_0x001d
        L_0x001c:
            r1 = 0
        L_0x001d:
            if (r1 == 0) goto L_0x002e
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r1 = "external_surround_sound_enabled"
            int r5 = android.provider.Settings.Global.getInt(r5, r1, r3)
            if (r5 != r2) goto L_0x002e
            com.google.android.exoplayer2.audio.AudioCapabilities r5 = d
            return r5
        L_0x002e:
            r5 = 29
            r1 = 8
            if (r0 < r5) goto L_0x003e
            com.google.android.exoplayer2.audio.AudioCapabilities r5 = new com.google.android.exoplayer2.audio.AudioCapabilities
            int[] r6 = com.google.android.exoplayer2.audio.AudioCapabilities.a.getDirectPlaybackSupportedEncodingsV29()
            r5.<init>(r6, r1)
            return r5
        L_0x003e:
            if (r6 == 0) goto L_0x005b
            java.lang.String r5 = "android.media.extra.AUDIO_PLUG_STATE"
            int r5 = r6.getIntExtra(r5, r3)
            if (r5 != 0) goto L_0x0049
            goto L_0x005b
        L_0x0049:
            com.google.android.exoplayer2.audio.AudioCapabilities r5 = new com.google.android.exoplayer2.audio.AudioCapabilities
            java.lang.String r0 = "android.media.extra.ENCODINGS"
            int[] r0 = r6.getIntArrayExtra(r0)
            java.lang.String r2 = "android.media.extra.MAX_CHANNEL_COUNT"
            int r6 = r6.getIntExtra(r2, r1)
            r5.<init>(r0, r6)
            return r5
        L_0x005b:
            com.google.android.exoplayer2.audio.AudioCapabilities r5 = c
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioCapabilities.a(android.content.Context, android.content.Intent):com.google.android.exoplayer2.audio.AudioCapabilities");
    }

    public static AudioCapabilities getCapabilities(Context context) {
        return a(context, context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioCapabilities)) {
            return false;
        }
        AudioCapabilities audioCapabilities = (AudioCapabilities) obj;
        if (!Arrays.equals(this.a, audioCapabilities.a) || this.b != audioCapabilities.b) {
            return false;
        }
        return true;
    }

    public int getMaxChannelCount() {
        return this.b;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.a) * 31) + this.b;
    }

    public boolean supportsEncoding(int i) {
        return Arrays.binarySearch(this.a, i) >= 0;
    }

    public String toString() {
        String arrays = Arrays.toString(this.a);
        StringBuilder sb = new StringBuilder(y2.c(arrays, 67));
        sb.append("AudioCapabilities[maxChannelCount=");
        sb.append(this.b);
        sb.append(", supportedEncodings=");
        sb.append(arrays);
        sb.append("]");
        return sb.toString();
    }
}
