package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class km extends PatternsHolder {
    public static final String[] j = {"វិនាទី​មុន", "១វិនាទីមុន"};
    public static final String[] k = {"នាទីមុន", "១នាទីមុន"};
    public static final String[] l = {"ម៉ោង​មុន", "១ម៉ោង​មុន"};
    public static final String[] m = {"ថ្ងៃមុន", "១ថ្ងៃ​មុន"};
    public static final String[] n = {"ស​ប្តា​ហ៍​មុន", "១ស​ប្តា​ហ៍​មុន"};
    public static final String[] o = {"ខែមុន", "១ខែមុន"};
    public static final String[] p = {"ឆ្នាំ​មុន", "១ឆ្នាំមុន"};
    public static final km q = new km();

    public km() {
        super("", j, k, l, m, n, o, p);
    }

    public static km getInstance() {
        return q;
    }
}
