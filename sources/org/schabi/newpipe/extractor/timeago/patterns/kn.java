package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class kn extends PatternsHolder {
    public static final String[] j = {"ಸೆಕೆಂಡುಗಳ", "ಸೆಕೆಂಡ್"};
    public static final String[] k = {"ನಿಮಿಷಗಳ", "ನಿಮಿಷದ"};
    public static final String[] l = {"ಗಂಟೆಗಳ", "ಗಂಟೆಯ"};
    public static final String[] m = {"ದಿನಗಳ", "ದಿನದ"};
    public static final String[] n = {"ವಾರಗಳ", "ವಾರದ"};
    public static final String[] o = {"ತಿಂಗಳ", "ತಿಂಗಳುಗಳ"};
    public static final String[] p = {"ವರ್ಷಗಳ", "ವರ್ಷದ"};
    public static final kn q = new kn();

    public kn() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static kn getInstance() {
        return q;
    }
}
