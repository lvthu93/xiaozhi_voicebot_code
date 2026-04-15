package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ta extends PatternsHolder {
    public static final String[] j = {"வினாடி", "வினாடிகளுக்கு"};
    public static final String[] k = {"நிமிடங்கள்", "நிமிடம்"};
    public static final String[] l = {"மணிநேரத்திற்கு"};
    public static final String[] m = {"நாட்களுக்கு", "நாளுக்கு"};
    public static final String[] n = {"வாரங்களுக்கு", "வாரம்"};
    public static final String[] o = {"மாதங்கள்", "மாதம்"};
    public static final String[] p = {"ஆண்டு", "ஆண்டுகளுக்கு"};
    public static final ta q = new ta();

    public ta() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ta getInstance() {
        return q;
    }
}
