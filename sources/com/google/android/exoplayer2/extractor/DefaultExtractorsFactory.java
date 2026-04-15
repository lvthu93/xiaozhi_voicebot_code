package com.google.android.exoplayer2.extractor;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.amr.AmrExtractor;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.extractor.flv.FlvExtractor;
import com.google.android.exoplayer2.extractor.jpeg.JpegExtractor;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import com.google.android.exoplayer2.util.FileTypes;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DefaultExtractorsFactory implements ExtractorsFactory {
    public static final int[] p = {5, 4, 12, 8, 3, 10, 9, 11, 6, 2, 0, 1, 7, 14};
    @Nullable
    public static final Constructor<? extends Extractor> q;
    public boolean c;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m = 1;
    public int n;
    public int o = 112800;

    static {
        Constructor<? extends U> constructor = null;
        try {
            if (Boolean.TRUE.equals(Class.forName("com.google.android.exoplayer2.ext.flac.FlacLibrary").getMethod("isAvailable", new Class[0]).invoke((Object) null, new Object[0]))) {
                constructor = Class.forName("com.google.android.exoplayer2.ext.flac.FlacExtractor").asSubclass(Extractor.class).getConstructor(new Class[]{Integer.TYPE});
            }
        } catch (ClassNotFoundException unused) {
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating FLAC extension", e);
        }
        q = constructor;
    }

    public final void a(int i2, ArrayList arrayList) {
        switch (i2) {
            case 0:
                arrayList.add(new Ac3Extractor());
                return;
            case 1:
                arrayList.add(new Ac4Extractor());
                return;
            case 2:
                arrayList.add(new AdtsExtractor(this.f | this.c ? 1 : 0));
                return;
            case 3:
                arrayList.add(new AmrExtractor(this.g | this.c ? 1 : 0));
                return;
            case 4:
                Constructor<? extends Extractor> constructor = q;
                if (constructor != null) {
                    try {
                        arrayList.add((Extractor) constructor.newInstance(new Object[]{Integer.valueOf(this.h)}));
                        return;
                    } catch (Exception e) {
                        throw new IllegalStateException("Unexpected error creating FLAC extractor", e);
                    }
                } else {
                    arrayList.add(new FlacExtractor(this.h));
                    return;
                }
            case 5:
                arrayList.add(new FlvExtractor());
                return;
            case 6:
                arrayList.add(new MatroskaExtractor(this.i));
                return;
            case 7:
                arrayList.add(new Mp3Extractor(this.l | this.c ? 1 : 0));
                return;
            case 8:
                arrayList.add(new FragmentedMp4Extractor(this.k));
                arrayList.add(new Mp4Extractor(this.j));
                return;
            case 9:
                arrayList.add(new OggExtractor());
                return;
            case 10:
                arrayList.add(new PsExtractor());
                return;
            case 11:
                arrayList.add(new TsExtractor(this.m, this.n, this.o));
                return;
            case 12:
                arrayList.add(new WavExtractor());
                return;
            case 14:
                arrayList.add(new JpegExtractor());
                return;
            default:
                return;
        }
    }

    public synchronized Extractor[] createExtractors() {
        return createExtractors(Uri.EMPTY, new HashMap());
    }

    public synchronized DefaultExtractorsFactory setAdtsExtractorFlags(int i2) {
        this.f = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setAmrExtractorFlags(int i2) {
        this.g = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setConstantBitrateSeekingEnabled(boolean z) {
        this.c = z;
        return this;
    }

    public synchronized DefaultExtractorsFactory setFlacExtractorFlags(int i2) {
        this.h = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setFragmentedMp4ExtractorFlags(int i2) {
        this.k = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMatroskaExtractorFlags(int i2) {
        this.i = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp3ExtractorFlags(int i2) {
        this.l = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp4ExtractorFlags(int i2) {
        this.j = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorFlags(int i2) {
        this.n = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorMode(int i2) {
        this.m = i2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorTimestampSearchBytes(int i2) {
        this.o = i2;
        return this;
    }

    public synchronized Extractor[] createExtractors(Uri uri, Map<String, List<String>> map) {
        ArrayList arrayList;
        arrayList = new ArrayList(14);
        int inferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(map);
        if (inferFileTypeFromResponseHeaders != -1) {
            a(inferFileTypeFromResponseHeaders, arrayList);
        }
        int inferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
        if (!(inferFileTypeFromUri == -1 || inferFileTypeFromUri == inferFileTypeFromResponseHeaders)) {
            a(inferFileTypeFromUri, arrayList);
        }
        int[] iArr = p;
        for (int i2 = 0; i2 < 14; i2++) {
            int i3 = iArr[i2];
            if (!(i3 == inferFileTypeFromResponseHeaders || i3 == inferFileTypeFromUri)) {
                a(i3, arrayList);
            }
        }
        return (Extractor[]) arrayList.toArray(new Extractor[arrayList.size()]);
    }
}
