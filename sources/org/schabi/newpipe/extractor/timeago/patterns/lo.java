package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class lo extends PatternsHolder {
    public static final String[] j = {"ວິນາທີກ່ອນນີ້"};
    public static final String[] k = {"ນາທີກ່ອນນີ້", "ນາ​ທີ​ກ່ອນ​ນີ້"};
    public static final String[] l = {"ຊົ່ວ​ໂມງ​ກ່ອນ​ນີ້"};
    public static final String[] m = {"ມື້ກ່ອນນີ້"};
    public static final String[] n = {"ອາ​ທິດ​ກ່ອນ​ນີ້"};
    public static final String[] o = {"ເດືອນ​ກ່ອນ​ນີ້"};
    public static final String[] p = {"ປີ​ກ່ອນ​ນີ້"};
    public static final lo q = new lo();

    public lo() {
        super("", j, k, l, m, n, o, p);
    }

    public static lo getInstance() {
        return q;
    }
}
