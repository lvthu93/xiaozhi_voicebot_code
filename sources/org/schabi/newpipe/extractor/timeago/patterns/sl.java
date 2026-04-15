package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sl extends PatternsHolder {
    public static final String[] j = {"sekundama", "sekundami", "sekundo"};
    public static final String[] k = {"minutama", "minutami", "minuto"};
    public static final String[] l = {"urama", "urami", "uro"};
    public static final String[] m = {"dnem", "dnevi", "dnevoma"};
    public static final String[] n = {"tedni", "tednom", "tednoma"};
    public static final String[] o = {"mesecem", "mesecema", "meseci"};
    public static final String[] p = {"leti", "letom", "letoma"};
    public static final sl q = new sl();

    public sl() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sl getInstance() {
        return q;
    }
}
