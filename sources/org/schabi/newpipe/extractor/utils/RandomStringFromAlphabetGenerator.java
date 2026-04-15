package org.schabi.newpipe.extractor.utils;

import java.util.Random;

public final class RandomStringFromAlphabetGenerator {
    public static String generate(String str, int i, Random random) {
        StringBuilder sb = new StringBuilder(i);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str.charAt(random.nextInt(str.length())));
        }
        return sb.toString();
    }
}
