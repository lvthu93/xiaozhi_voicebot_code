package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class th extends PatternsHolder {
    public static final String[] j = {"วินาทีที่ผ่านมา"};
    public static final String[] k = {"นาทีที่ผ่านมา"};
    public static final String[] l = {"ชั่วโมงที่ผ่านมา"};
    public static final String[] m = {"วันที่ผ่านมา"};
    public static final String[] n = {"สัปดาห์ที่ผ่านมา"};
    public static final String[] o = {"เดือนที่ผ่านมา"};
    public static final String[] p = {"ปีที่ผ่านมา"};
    public static final th q = new th();

    public th() {
        super("", j, k, l, m, n, o, p);
    }

    public static th getInstance() {
        return q;
    }
}
