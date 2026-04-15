package org.schabi.newpipe.extractor.utils.jsextractor;

import java.util.Stack;

public class Lexer {
    public final a2 a;
    public final c b = new c();
    public final Stack<a> c = new Stack<>();
    public final Stack<e> d = new Stack<>();

    public static class ParsedToken {
        public final Token a;
        public final int b;

        public ParsedToken(Token token, int i, int i2) {
            this.a = token;
            this.b = i2;
        }
    }

    public static class a {
        public final boolean a;
        public final e b;

        public a(boolean z, e eVar) {
            this.a = z;
            this.b = eVar;
        }
    }

    public static class b extends d {
        public final a c;

        public b(Token token, int i, a aVar) {
            super(token, i);
            this.c = aVar;
        }
    }

    public static class c {
        public final d[] a = new d[3];

        public final void a(d dVar) {
            int i = 0;
            while (i < 3) {
                d[] dVarArr = this.a;
                d dVar2 = dVarArr[i];
                dVarArr[i] = dVar;
                i++;
                dVar = dVar2;
            }
        }
    }

    public static class d {
        public final Token a;
        public final int b;

        public d(Token token, int i) {
            this.a = token;
            this.b = i;
        }
    }

    public static class e {
        public final boolean a;
        public final boolean b;

        public e(boolean z, boolean z2) {
            this.a = z;
            this.b = z2;
        }
    }

    public static class f extends d {
        public final e c;

        public f(Token token, int i, e eVar) {
            super(token, i);
            this.c = eVar;
        }
    }

    public Lexer(String str) {
        this.a = new a2(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x015f, code lost:
        if (r1 != false) goto L_0x0183;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0181, code lost:
        if (r1 != false) goto L_0x0183;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0207, code lost:
        if (r12.lastElement().a != false) goto L_0x0216;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0212, code lost:
        if (r3.b != r0.g) goto L_0x0216;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0053, code lost:
        if (r12 != org.schabi.newpipe.extractor.utils.jsextractor.Token.RB) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00ee, code lost:
        throw new org.schabi.newpipe.extractor.exceptions.ParsingException("msg.unterminated.re.lit");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0027, code lost:
        if (r12 != org.schabi.newpipe.extractor.utils.jsextractor.Token.THIS) goto L_0x0058;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.schabi.newpipe.extractor.utils.jsextractor.Lexer.ParsedToken getNextToken() throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r15 = this;
            a2 r0 = r15.a
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = r0.nextToken()
            org.schabi.newpipe.extractor.utils.jsextractor.Token r2 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DIV
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_DIV
            org.schabi.newpipe.extractor.utils.jsextractor.Token r4 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RC
            org.schabi.newpipe.extractor.utils.jsextractor.Token r5 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RP
            r6 = 2
            r7 = 65
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$c r8 = r15.b
            r9 = 1
            r10 = 0
            if (r1 == r2) goto L_0x0019
            if (r1 != r3) goto L_0x00fa
        L_0x0019:
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$d[] r11 = r8.a
            r11 = r11[r10]
            if (r11 == 0) goto L_0x0058
            org.schabi.newpipe.extractor.utils.jsextractor.Token r12 = r11.a
            boolean r13 = r12.g
            if (r13 == 0) goto L_0x002a
            org.schabi.newpipe.extractor.utils.jsextractor.Token r11 = org.schabi.newpipe.extractor.utils.jsextractor.Token.THIS
            if (r12 == r11) goto L_0x0056
            goto L_0x0058
        L_0x002a:
            if (r12 != r5) goto L_0x0037
            boolean r13 = r11 instanceof org.schabi.newpipe.extractor.utils.jsextractor.Lexer.f
            if (r13 == 0) goto L_0x0037
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f r11 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.f) r11
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e r11 = r11.c
            boolean r11 = r11.b
            goto L_0x0059
        L_0x0037:
            if (r12 != r4) goto L_0x004d
            boolean r13 = r11 instanceof org.schabi.newpipe.extractor.utils.jsextractor.Lexer.b
            if (r13 == 0) goto L_0x004d
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$b r11 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.b) r11
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a r11 = r11.c
            boolean r12 = r11.a
            if (r12 == 0) goto L_0x0056
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e r11 = r11.b
            if (r11 == 0) goto L_0x0058
            boolean r11 = r11.a
            r11 = r11 ^ r9
            goto L_0x0059
        L_0x004d:
            boolean r11 = r12.f
            if (r11 == 0) goto L_0x0056
            org.schabi.newpipe.extractor.utils.jsextractor.Token r11 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RB
            if (r12 == r11) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r11 = 0
            goto L_0x0059
        L_0x0058:
            r11 = 1
        L_0x0059:
            if (r11 == 0) goto L_0x00fa
            int r11 = r0.k
            r0.c = r10
            java.lang.String r12 = "msg.unterminated.re.lit"
            if (r1 != r3) goto L_0x0069
            r1 = 61
            r0.a(r1)
            goto L_0x0079
        L_0x0069:
            if (r1 == r2) goto L_0x006e
            org.mozilla.javascript.Kit.codeBug()
        L_0x006e:
            int r1 = r0.b()
            r0.j(r1)
            r2 = 42
            if (r1 == r2) goto L_0x00ef
        L_0x0079:
            r1 = 0
        L_0x007a:
            int r2 = r0.b()
            r3 = 47
            r13 = -1
            if (r2 != r3) goto L_0x00be
            if (r1 == 0) goto L_0x0086
            goto L_0x00be
        L_0x0086:
            int r1 = r0.c(r9, r9)
            java.lang.String r2 = "gimysu"
            int r2 = r2.indexOf(r1)
            if (r2 == r13) goto L_0x0096
            r0.a(r1)
            goto L_0x0086
        L_0x0096:
            r2 = 90
            if (r1 > r2) goto L_0x009d
            if (r7 > r1) goto L_0x00a7
            goto L_0x00a5
        L_0x009d:
            r2 = 97
            if (r2 > r1) goto L_0x00a7
            r2 = 122(0x7a, float:1.71E-43)
            if (r1 > r2) goto L_0x00a7
        L_0x00a5:
            r2 = 1
            goto L_0x00a8
        L_0x00a7:
            r2 = 0
        L_0x00a8:
            if (r2 != 0) goto L_0x00b6
            r0.k(r1)
            int r1 = r0.c
            int r11 = r11 + r1
            int r11 = r11 + r6
            r0.l = r11
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.REGEXP
            goto L_0x00fa
        L_0x00b6:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "msg.invalid.re.flag"
            r0.<init>(r1)
            throw r0
        L_0x00be:
            r3 = 10
            if (r2 == r3) goto L_0x00e9
            if (r2 == r13) goto L_0x00e9
            r14 = 92
            if (r2 != r14) goto L_0x00da
            r0.a(r2)
            int r2 = r0.b()
            if (r2 == r3) goto L_0x00d4
            if (r2 == r13) goto L_0x00d4
            goto L_0x00e5
        L_0x00d4:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r0.<init>(r12)
            throw r0
        L_0x00da:
            r3 = 91
            if (r2 != r3) goto L_0x00e0
            r1 = 1
            goto L_0x00e5
        L_0x00e0:
            r3 = 93
            if (r2 != r3) goto L_0x00e5
            r1 = 0
        L_0x00e5:
            r0.a(r2)
            goto L_0x007a
        L_0x00e9:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r0.<init>(r12)
            throw r0
        L_0x00ef:
            int r1 = r0.j
            int r1 = r1 - r9
            r0.l = r1
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r0.<init>(r12)
            throw r0
        L_0x00fa:
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$ParsedToken r2 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$ParsedToken
            int r3 = r0.k
            int r11 = r0.l
            r2.<init>(r1, r3, r11)
            boolean r11 = r1.f
            if (r11 == 0) goto L_0x023b
            int r11 = r1.ordinal()
            java.util.Stack<org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a> r12 = r15.c
            java.util.Stack<org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e> r13 = r15.d
            switch(r11) {
                case 46: goto L_0x01cc;
                case 47: goto L_0x01a8;
                case 48: goto L_0x0138;
                case 49: goto L_0x0114;
                default: goto L_0x0112;
            }
        L_0x0112:
            goto L_0x023b
        L_0x0114:
            boolean r1 = r13.isEmpty()
            if (r1 != 0) goto L_0x012c
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f r1 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f
            int r0 = r0.g
            java.lang.Object r3 = r13.pop()
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e r3 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.e) r3
            r1.<init>(r5, r0, r3)
            r8.a(r1)
            goto L_0x0249
        L_0x012c:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "unmatched closing paren at "
            java.lang.String r1 = defpackage.y2.f(r1, r3)
            r0.<init>(r1)
            throw r0
        L_0x0138:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.FUNCTION
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$d[] r3 = r8.a
            r4 = r3[r10]
            if (r4 == 0) goto L_0x0146
            org.schabi.newpipe.extractor.utils.jsextractor.Token r5 = r4.a
            if (r5 != r1) goto L_0x0146
            r5 = 1
            goto L_0x0147
        L_0x0146:
            r5 = 0
        L_0x0147:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r7 = org.schabi.newpipe.extractor.utils.jsextractor.Token.CASE
            org.schabi.newpipe.extractor.utils.jsextractor.Token r10 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RETURN
            if (r5 == 0) goto L_0x0162
            r1 = r3[r9]
            if (r1 == 0) goto L_0x0185
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = r1.a
            boolean r3 = r1.c
            if (r3 != 0) goto L_0x015e
            if (r1 == r10) goto L_0x015e
            if (r1 != r7) goto L_0x015c
            goto L_0x015e
        L_0x015c:
            r1 = 0
            goto L_0x015f
        L_0x015e:
            r1 = 1
        L_0x015f:
            if (r1 == 0) goto L_0x0185
            goto L_0x0183
        L_0x0162:
            r5 = r3[r9]
            if (r5 == 0) goto L_0x016c
            org.schabi.newpipe.extractor.utils.jsextractor.Token r5 = r5.a
            if (r5 != r1) goto L_0x016c
            r1 = 1
            goto L_0x016d
        L_0x016c:
            r1 = 0
        L_0x016d:
            if (r1 == 0) goto L_0x0185
            r1 = r3[r6]
            if (r1 == 0) goto L_0x0185
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = r1.a
            boolean r3 = r1.c
            if (r3 != 0) goto L_0x0180
            if (r1 == r10) goto L_0x0180
            if (r1 != r7) goto L_0x017e
            goto L_0x0180
        L_0x017e:
            r1 = 0
            goto L_0x0181
        L_0x0180:
            r1 = 1
        L_0x0181:
            if (r1 == 0) goto L_0x0185
        L_0x0183:
            r1 = 1
            goto L_0x0186
        L_0x0185:
            r1 = 0
        L_0x0186:
            if (r4 == 0) goto L_0x0191
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = r4.a
            boolean r3 = r3.isConditional()
            if (r3 == 0) goto L_0x0191
            goto L_0x0192
        L_0x0191:
            r9 = 0
        L_0x0192:
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e r3 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e
            r3.<init>(r1, r9)
            r13.push(r3)
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f r1 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f
            org.schabi.newpipe.extractor.utils.jsextractor.Token r4 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LP
            int r0 = r0.g
            r1.<init>(r4, r0, r3)
            r8.a(r1)
            goto L_0x0249
        L_0x01a8:
            boolean r1 = r12.isEmpty()
            if (r1 != 0) goto L_0x01c0
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$b r1 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$b
            int r0 = r0.g
            java.lang.Object r3 = r12.pop()
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a r3 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.a) r3
            r1.<init>(r4, r0, r3)
            r8.a(r1)
            goto L_0x0249
        L_0x01c0:
            org.schabi.newpipe.extractor.exceptions.ParsingException r0 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r1 = "unmatched closing brace at "
            java.lang.String r1 = defpackage.y2.f(r1, r3)
            r0.<init>(r1)
            throw r0
        L_0x01cc:
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$d[] r1 = r8.a
            r3 = r1[r10]
            if (r3 == 0) goto L_0x0216
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = r3.a
            int r3 = r3.ordinal()
            r4 = 3
            if (r3 == r4) goto L_0x020a
            r4 = 39
            if (r3 == r4) goto L_0x020a
            r4 = 46
            if (r3 == r4) goto L_0x0215
            r4 = 48
            if (r3 == r4) goto L_0x0215
            if (r3 == r7) goto L_0x01f9
            r4 = 77
            if (r3 == r4) goto L_0x0215
            r4 = 95
            if (r3 == r4) goto L_0x020a
            r3 = r1[r10]
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = r3.a
            boolean r3 = r3.c
            r9 = r9 ^ r3
            goto L_0x0216
        L_0x01f9:
            boolean r3 = r12.isEmpty()
            if (r3 != 0) goto L_0x0215
            java.lang.Object r3 = r12.lastElement()
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a r3 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.a) r3
            boolean r3 = r3.a
            if (r3 == 0) goto L_0x0215
            goto L_0x0216
        L_0x020a:
            r3 = r1[r9]
            if (r3 == 0) goto L_0x0215
            int r3 = r3.b
            int r4 = r0.g
            if (r3 == r4) goto L_0x0215
            goto L_0x0216
        L_0x0215:
            r9 = 0
        L_0x0216:
            r1 = r1[r10]
            boolean r3 = r1 instanceof org.schabi.newpipe.extractor.utils.jsextractor.Lexer.f
            if (r3 == 0) goto L_0x0225
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = r1.a
            if (r3 != r5) goto L_0x0225
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$f r1 = (org.schabi.newpipe.extractor.utils.jsextractor.Lexer.f) r1
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$e r1 = r1.c
            goto L_0x0226
        L_0x0225:
            r1 = 0
        L_0x0226:
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a r3 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$a
            r3.<init>(r9, r1)
            r12.push(r3)
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$b r1 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$b
            org.schabi.newpipe.extractor.utils.jsextractor.Token r4 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LC
            int r0 = r0.g
            r1.<init>(r4, r0, r3)
            r8.a(r1)
            goto L_0x0249
        L_0x023b:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r3 = org.schabi.newpipe.extractor.utils.jsextractor.Token.COMMENT
            if (r1 == r3) goto L_0x0249
            org.schabi.newpipe.extractor.utils.jsextractor.Lexer$d r3 = new org.schabi.newpipe.extractor.utils.jsextractor.Lexer$d
            int r0 = r0.g
            r3.<init>(r1, r0)
            r8.a(r3)
        L_0x0249:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.utils.jsextractor.Lexer.getNextToken():org.schabi.newpipe.extractor.utils.jsextractor.Lexer$ParsedToken");
    }

    public boolean isBalanced() {
        return this.c.isEmpty() && this.d.isEmpty();
    }
}
