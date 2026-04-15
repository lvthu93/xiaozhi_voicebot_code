package defpackage;

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.BundledExtractorsAdapter;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;

/* renamed from: e9  reason: default package */
public final /* synthetic */ class e9 implements ProgressiveMediaExtractor.Factory {
    public final /* synthetic */ int c;
    public final /* synthetic */ ExtractorsFactory f;

    public /* synthetic */ e9(ExtractorsFactory extractorsFactory, int i) {
        this.c = i;
        this.f = extractorsFactory;
    }

    public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
        int i = this.c;
        ExtractorsFactory extractorsFactory = this.f;
        switch (i) {
            case 0:
                return new BundledExtractorsAdapter(extractorsFactory);
            default:
                if (extractorsFactory == null) {
                    extractorsFactory = new DefaultExtractorsFactory();
                }
                return new BundledExtractorsAdapter(extractorsFactory);
        }
    }
}
