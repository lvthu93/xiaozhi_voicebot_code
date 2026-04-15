package defpackage;

import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Parser;

/* renamed from: kg  reason: default package */
public final class kg {
    public static final Pattern[] a = {Pattern.compile("\\b(?:[a-zA-Z0-9_$]+)&&\\((?:[a-zA-Z0-9_$]+)=([a-zA-Z0-9_$]{2,})\\(decodeURIComponent\\((?:[a-zA-Z0-9_$]+)\\)\\)"), Pattern.compile("\\bm=([a-zA-Z0-9$]{2,})\\(decodeURIComponent\\(h\\.s\\)\\)"), Pattern.compile("\\bc&&\\(c=([a-zA-Z0-9$]{2,})\\(decodeURIComponent\\(c\\)\\)"), Pattern.compile("(?:\\b|[^a-zA-Z0-9$])([a-zA-Z0-9$]{2,})\\s*=\\s*function\\(\\s*a\\s*\\)\\s*\\{\\s*a\\s*=\\s*a\\.split\\(\\s*\"\"\\s*\\)"), Pattern.compile("([\\w$]+)\\s*=\\s*function\\((\\w+)\\)\\{\\s*\\2=\\s*\\2\\.split\\(\"\"\\)\\s*;")};

    /* JADX WARNING: Can't wrap try/catch for region: R(2:5|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
        r3 = "(" + java.util.regex.Pattern.quote(r2) + "=function\\([a-zA-Z0-9_]+\\)\\{.+?\\})";
        r3 = "var " + org.schabi.newpipe.extractor.utils.Parser.matchGroup1(r3, r8);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x002f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r8) throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            java.lang.String r0 = ";"
            java.lang.String r1 = "function deobfuscate(a){return "
            java.util.regex.Pattern[] r2 = a     // Catch:{ RegexException -> 0x00b9 }
            java.lang.String r2 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1MultiplePatterns(r2, r8)     // Catch:{ RegexException -> 0x00b9 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x002f }
            r3.<init>()     // Catch:{ Exception -> 0x002f }
            r3.append(r2)     // Catch:{ Exception -> 0x002f }
            java.lang.String r4 = "=function"
            r3.append(r4)     // Catch:{ Exception -> 0x002f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x002f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x002f }
            r4.<init>()     // Catch:{ Exception -> 0x002f }
            r4.append(r3)     // Catch:{ Exception -> 0x002f }
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.jsextractor.JavaScriptExtractor.matchToClosingBrace(r8, r3)     // Catch:{ Exception -> 0x002f }
            r4.append(r3)     // Catch:{ Exception -> 0x002f }
            java.lang.String r3 = r4.toString()     // Catch:{ Exception -> 0x002f }
            goto L_0x0058
        L_0x002f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r4 = "("
            r3.<init>(r4)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r4 = java.util.regex.Pattern.quote(r2)     // Catch:{ Exception -> 0x00b7 }
            r3.append(r4)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r4 = "=function\\([a-zA-Z0-9_]+\\)\\{.+?\\})"
            r3.append(r4)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00b7 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = "var "
            r4.<init>(r5)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r3 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r3, (java.lang.String) r8)     // Catch:{ Exception -> 0x00b7 }
            r4.append(r3)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r3 = r4.toString()     // Catch:{ Exception -> 0x00b7 }
        L_0x0058:
            org.schabi.newpipe.extractor.utils.JavaScript.compileOrThrow(r3)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r4 = "(var [A-z]=['\"].*['\"].split\\(\";\"\\))"
            java.lang.String r4 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r4, (java.lang.String) r8)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = ";([A-Za-z0-9_\\$]{2,})\\[.."
            java.lang.String r5 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r5, (java.lang.String) r3)     // Catch:{ Exception -> 0x00b7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "(var "
            r6.<init>(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = java.util.regex.Pattern.quote(r5)     // Catch:{ Exception -> 0x00b7 }
            r6.append(r5)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = "=\\{(?>.|\\n)+?\\}\\};)"
            r6.append(r5)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r8 = org.schabi.newpipe.extractor.utils.Parser.matchGroup1((java.lang.String) r5, (java.lang.String) r8)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r5 = "\n"
            java.lang.String r6 = ""
            java.lang.String r8 = r8.replace(r5, r6)     // Catch:{ Exception -> 0x00b7 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            r5.<init>(r1)     // Catch:{ Exception -> 0x00b7 }
            r5.append(r2)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r1 = "(a);}"
            r5.append(r1)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x00b7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            r2.<init>()     // Catch:{ Exception -> 0x00b7 }
            r2.append(r4)     // Catch:{ Exception -> 0x00b7 }
            r2.append(r0)     // Catch:{ Exception -> 0x00b7 }
            r2.append(r8)     // Catch:{ Exception -> 0x00b7 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b7 }
            r2.append(r0)     // Catch:{ Exception -> 0x00b7 }
            r2.append(r1)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r8 = r2.toString()     // Catch:{ Exception -> 0x00b7 }
            return r8
        L_0x00b7:
            r8 = move-exception
            goto L_0x00c2
        L_0x00b9:
            r8 = move-exception
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r1 = "Could not find deobfuscation function with any of the known patterns"
            r0.<init>(r1, r8)     // Catch:{ Exception -> 0x00b7 }
            throw r0     // Catch:{ Exception -> 0x00b7 }
        L_0x00c2:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "Could not parse deobfuscation function"
            r0.<init>(r1, r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.kg.a(java.lang.String):java.lang.String");
    }

    public static String b(String str) throws ParsingException {
        try {
            return Parser.matchGroup1("signatureTimestamp[=:](\\d+)", str);
        } catch (ParsingException e) {
            throw new ParsingException("Could not extract signature timestamp from JavaScript code", e);
        }
    }
}
