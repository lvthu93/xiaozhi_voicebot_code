package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sr_Latn extends PatternsHolder {
    public static final String[] j = {"sekunde", "sekundi"};
    public static final String[] k = {"minuta"};
    public static final String[] l = {"sat", "sati", "sata"};
    public static final String[] m = {"Pre 1 dan", "Pre 2 dana", "Pre 3 dana", "Pre 4 dana", "Pre 5 dana", "Pre 6 dana"};
    public static final String[] n = {"nedelja", "nedelje", "nedelju"};
    public static final String[] o = {"mesec", "meseci", "meseca"};
    public static final String[] p = {"godine", "godina", "godinu"};
    public static final sr_Latn q = new sr_Latn();

    public sr_Latn() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sr_Latn getInstance() {
        return q;
    }
}
