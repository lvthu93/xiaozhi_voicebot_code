package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class mr extends PatternsHolder {
    public static final String[] j = {"सेकंदांपूर्वी", "सेकंदापूर्वी"};
    public static final String[] k = {"मिनिटांपूर्वी", "मिनिटापूर्वी"};
    public static final String[] l = {"तासांपूर्वी", "तासापूर्वी"};
    public static final String[] m = {"दिवसांपूर्वी", "दिवसापूर्वी"};
    public static final String[] n = {"आठवड्यांपूर्वी", "आठवड्यापूर्वी"};
    public static final String[] o = {"महिन्यांपूर्वी", "महिन्यापूर्वी"};
    public static final String[] p = {"वर्षांपूर्वी", "वर्षापूर्वी"};
    public static final mr q = new mr();

    public mr() {
        super("", j, k, l, m, n, o, p);
    }

    public static mr getInstance() {
        return q;
    }
}
