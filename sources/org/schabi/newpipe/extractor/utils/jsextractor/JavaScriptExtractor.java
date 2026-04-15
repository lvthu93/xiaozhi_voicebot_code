package org.schabi.newpipe.extractor.utils.jsextractor;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.jsextractor.Lexer;

public final class JavaScriptExtractor {
    public static String matchToClosingBrace(String str, String str2) throws ParsingException {
        int indexOf = str.indexOf(str2);
        if (indexOf >= 0) {
            String substring = str.substring(str2.length() + indexOf);
            Lexer lexer = new Lexer(substring);
            boolean z = false;
            while (true) {
                Lexer.ParsedToken nextToken = lexer.getNextToken();
                Token token = nextToken.a;
                if (token == Token.LC) {
                    z = true;
                } else if (z && lexer.isBalanced()) {
                    return substring.substring(0, nextToken.b);
                } else {
                    if (token == Token.EOF) {
                        throw new ParsingException("Could not find matching braces");
                    }
                }
            }
        } else {
            throw new ParsingException("Start not found");
        }
    }
}
