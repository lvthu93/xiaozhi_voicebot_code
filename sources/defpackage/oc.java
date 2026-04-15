package defpackage;

import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* renamed from: oc  reason: default package */
public final /* synthetic */ class oc {
    public static int a(TrackOutput trackOutput, DataReader dataReader, int i, boolean z) throws IOException {
        return trackOutput.sampleData(dataReader, i, z, 0);
    }

    public static void b(TrackOutput trackOutput, ParsableByteArray parsableByteArray, int i) {
        trackOutput.sampleData(parsableByteArray, i, 0);
    }
}
