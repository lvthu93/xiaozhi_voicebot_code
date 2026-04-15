package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.ArrayComprehension;
import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.BreakStatement;
import org.mozilla.javascript.ast.CatchClause;
import org.mozilla.javascript.ast.Comment;
import org.mozilla.javascript.ast.ConditionalExpression;
import org.mozilla.javascript.ast.ContinueStatement;
import org.mozilla.javascript.ast.DestructuringForm;
import org.mozilla.javascript.ast.DoLoop;
import org.mozilla.javascript.ast.ElementGet;
import org.mozilla.javascript.ast.EmptyExpression;
import org.mozilla.javascript.ast.EmptyStatement;
import org.mozilla.javascript.ast.ErrorNode;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.GeneratorExpression;
import org.mozilla.javascript.ast.GeneratorExpressionLoop;
import org.mozilla.javascript.ast.IdeErrorReporter;
import org.mozilla.javascript.ast.IfStatement;
import org.mozilla.javascript.ast.InfixExpression;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.KeywordLiteral;
import org.mozilla.javascript.ast.Label;
import org.mozilla.javascript.ast.LabeledStatement;
import org.mozilla.javascript.ast.LetNode;
import org.mozilla.javascript.ast.Loop;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NewExpression;
import org.mozilla.javascript.ast.NumberLiteral;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.mozilla.javascript.ast.ObjectProperty;
import org.mozilla.javascript.ast.ParenthesizedExpression;
import org.mozilla.javascript.ast.PropertyGet;
import org.mozilla.javascript.ast.RegExpLiteral;
import org.mozilla.javascript.ast.ReturnStatement;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;
import org.mozilla.javascript.ast.StringLiteral;
import org.mozilla.javascript.ast.SwitchStatement;
import org.mozilla.javascript.ast.Symbol;
import org.mozilla.javascript.ast.ThrowStatement;
import org.mozilla.javascript.ast.TryStatement;
import org.mozilla.javascript.ast.UnaryExpression;
import org.mozilla.javascript.ast.VariableDeclaration;
import org.mozilla.javascript.ast.VariableInitializer;
import org.mozilla.javascript.ast.WhileLoop;
import org.mozilla.javascript.ast.WithStatement;
import org.mozilla.javascript.ast.XmlElemRef;
import org.mozilla.javascript.ast.XmlExpression;
import org.mozilla.javascript.ast.XmlLiteral;
import org.mozilla.javascript.ast.XmlMemberGet;
import org.mozilla.javascript.ast.XmlPropRef;
import org.mozilla.javascript.ast.XmlRef;
import org.mozilla.javascript.ast.XmlString;
import org.mozilla.javascript.ast.Yield;

public class Parser {
    public static final int ARGC_LIMIT = 65536;
    static final int CLEAR_TI_MASK = 65535;
    private static final int GET_ENTRY = 2;
    private static final int METHOD_ENTRY = 8;
    private static final int PROP_ENTRY = 1;
    private static final int SET_ENTRY = 4;
    static final int TI_AFTER_EOL = 65536;
    static final int TI_CHECK_LABEL = 131072;
    boolean calledByCompileFunction;
    CompilerEnvirons compilerEnv;
    private int currentFlaggedToken;
    private Comment currentJsDocComment;
    private LabeledStatement currentLabel;
    Scope currentScope;
    ScriptNode currentScriptOrFn;
    private int currentToken;
    private boolean defaultUseStrictDirective;
    /* access modifiers changed from: private */
    public int endFlags;
    private IdeErrorReporter errorCollector;
    private ErrorReporter errorReporter;
    private boolean inDestructuringAssignment;
    /* access modifiers changed from: private */
    public boolean inForInit;
    protected boolean inUseStrictDirective;
    /* access modifiers changed from: private */
    public Map<String, LabeledStatement> labelSet;
    /* access modifiers changed from: private */
    public List<Jump> loopAndSwitchSet;
    /* access modifiers changed from: private */
    public List<Loop> loopSet;
    protected int nestingOfFunction;
    private boolean parseFinished;
    private int prevNameTokenLineno;
    private int prevNameTokenStart;
    private String prevNameTokenString;
    private List<Comment> scannedComments;
    private char[] sourceChars;
    private String sourceURI;
    private int syntaxErrorCount;
    private TokenStream ts;

    public static class ConditionData {
        AstNode condition;
        int lp;
        int rp;

        private ConditionData() {
            this.lp = -1;
            this.rp = -1;
        }
    }

    public static class ParserException extends RuntimeException {
        private static final long serialVersionUID = 5882582646773765630L;

        private ParserException() {
        }
    }

    public class PerFunctionVariables {
        private Scope savedCurrentScope;
        private ScriptNode savedCurrentScriptOrFn;
        private int savedEndFlags;
        private boolean savedInForInit;
        private Map<String, LabeledStatement> savedLabelSet;
        private List<Jump> savedLoopAndSwitchSet;
        private List<Loop> savedLoopSet;

        public PerFunctionVariables(FunctionNode functionNode) {
            this.savedCurrentScriptOrFn = Parser.this.currentScriptOrFn;
            Parser.this.currentScriptOrFn = functionNode;
            this.savedCurrentScope = Parser.this.currentScope;
            Parser.this.currentScope = functionNode;
            this.savedLabelSet = Parser.this.labelSet;
            Map unused = Parser.this.labelSet = null;
            this.savedLoopSet = Parser.this.loopSet;
            List unused2 = Parser.this.loopSet = null;
            this.savedLoopAndSwitchSet = Parser.this.loopAndSwitchSet;
            List unused3 = Parser.this.loopAndSwitchSet = null;
            this.savedEndFlags = Parser.this.endFlags;
            int unused4 = Parser.this.endFlags = 0;
            this.savedInForInit = Parser.this.inForInit;
            boolean unused5 = Parser.this.inForInit = false;
        }

        public void restore() {
            Parser parser = Parser.this;
            parser.currentScriptOrFn = this.savedCurrentScriptOrFn;
            parser.currentScope = this.savedCurrentScope;
            Map unused = parser.labelSet = this.savedLabelSet;
            List unused2 = Parser.this.loopSet = this.savedLoopSet;
            List unused3 = Parser.this.loopAndSwitchSet = this.savedLoopAndSwitchSet;
            int unused4 = Parser.this.endFlags = this.savedEndFlags;
            boolean unused5 = Parser.this.inForInit = this.savedInForInit;
        }
    }

    public Parser() {
        this(new CompilerEnvirons());
    }

    private AstNode addExpr() throws IOException {
        AstNode mulExpr = mulExpr();
        while (true) {
            int peekToken = peekToken();
            int i = this.ts.tokenBeg;
            if (peekToken != 21 && peekToken != 22) {
                return mulExpr;
            }
            consumeToken();
            mulExpr = new InfixExpression(peekToken, mulExpr, mulExpr(), i);
        }
    }

    private AstNode andExpr() throws IOException {
        AstNode bitOrExpr = bitOrExpr();
        if (!matchToken(106, true)) {
            return bitOrExpr;
        }
        return new InfixExpression(106, bitOrExpr, andExpr(), this.ts.tokenBeg);
    }

    /* JADX INFO: finally extract failed */
    private List<AstNode> argumentList() throws IOException {
        if (matchToken(89, true)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = this.inForInit;
        this.inForInit = false;
        while (true) {
            try {
                if (peekToken() != 89) {
                    if (peekToken() == 73) {
                        reportError("msg.yield.parenthesized");
                    }
                    AstNode assignExpr = assignExpr();
                    if (peekToken() == 120) {
                        try {
                            arrayList.add(generatorExpression(assignExpr, 0, true));
                        } catch (IOException unused) {
                        }
                    } else {
                        arrayList.add(assignExpr);
                    }
                    if (!matchToken(90, true)) {
                        break;
                    }
                } else {
                    break;
                }
            } catch (Throwable th) {
                this.inForInit = z;
                throw th;
            }
        }
        this.inForInit = z;
        mustMatchToken(89, "msg.no.paren.arg", true);
        return arrayList;
    }

    private AstNode arrayComprehension(AstNode astNode, int i) throws IOException {
        ConditionData conditionData;
        int i2;
        ArrayList arrayList = new ArrayList();
        while (peekToken() == 120) {
            arrayList.add(arrayComprehensionLoop());
        }
        if (peekToken() == 113) {
            consumeToken();
            i2 = this.ts.tokenBeg - i;
            conditionData = condition();
        } else {
            i2 = -1;
            conditionData = null;
        }
        mustMatchToken(85, "msg.no.bracket.arg", true);
        ArrayComprehension arrayComprehension = new ArrayComprehension(i, this.ts.tokenEnd - i);
        arrayComprehension.setResult(astNode);
        arrayComprehension.setLoops(arrayList);
        if (conditionData != null) {
            arrayComprehension.setIfPosition(i2);
            arrayComprehension.setFilter(conditionData.condition);
            arrayComprehension.setFilterLp(conditionData.lp - i);
            arrayComprehension.setFilterRp(conditionData.rp - i);
        }
        return arrayComprehension;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0049 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0050 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0067 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0086 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0091 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c2 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c8 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00df A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e0 A[Catch:{ all -> 0x00ee }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.ArrayComprehensionLoop arrayComprehensionLoop() throws java.io.IOException {
        /*
            r13 = this;
            int r0 = r13.nextToken()
            r1 = 120(0x78, float:1.68E-43)
            if (r0 == r1) goto L_0x000b
            r13.codeBug()
        L_0x000b:
            org.mozilla.javascript.TokenStream r0 = r13.ts
            int r0 = r0.tokenBeg
            org.mozilla.javascript.ast.ArrayComprehensionLoop r1 = new org.mozilla.javascript.ast.ArrayComprehensionLoop
            r1.<init>(r0)
            r13.pushScope(r1)
            r2 = 39
            r3 = 1
            boolean r4 = r13.matchToken(r2, r3)     // Catch:{ all -> 0x00ee }
            java.lang.String r5 = "msg.no.paren.for"
            r6 = -1
            if (r4 == 0) goto L_0x003a
            org.mozilla.javascript.TokenStream r4 = r13.ts     // Catch:{ all -> 0x00ee }
            java.lang.String r4 = r4.getString()     // Catch:{ all -> 0x00ee }
            java.lang.String r7 = "each"
            boolean r4 = r4.equals(r7)     // Catch:{ all -> 0x00ee }
            if (r4 == 0) goto L_0x0037
            org.mozilla.javascript.TokenStream r4 = r13.ts     // Catch:{ all -> 0x00ee }
            int r4 = r4.tokenBeg     // Catch:{ all -> 0x00ee }
            int r4 = r4 - r0
            goto L_0x003b
        L_0x0037:
            r13.reportError(r5)     // Catch:{ all -> 0x00ee }
        L_0x003a:
            r4 = -1
        L_0x003b:
            r7 = 88
            boolean r5 = r13.mustMatchToken(r7, r5, r3)     // Catch:{ all -> 0x00ee }
            if (r5 == 0) goto L_0x0049
            org.mozilla.javascript.TokenStream r5 = r13.ts     // Catch:{ all -> 0x00ee }
            int r5 = r5.tokenBeg     // Catch:{ all -> 0x00ee }
            int r5 = r5 - r0
            goto L_0x004a
        L_0x0049:
            r5 = -1
        L_0x004a:
            int r7 = r13.peekToken()     // Catch:{ all -> 0x00ee }
            if (r7 == r2) goto L_0x0067
            r8 = 84
            if (r7 == r8) goto L_0x005f
            r8 = 86
            if (r7 == r8) goto L_0x005f
            java.lang.String r7 = "msg.bad.var"
            r13.reportError(r7)     // Catch:{ all -> 0x00ee }
            r7 = 0
            goto L_0x006e
        L_0x005f:
            org.mozilla.javascript.ast.AstNode r7 = r13.destructuringPrimaryExpr()     // Catch:{ all -> 0x00ee }
            r13.markDestructuring(r7)     // Catch:{ all -> 0x00ee }
            goto L_0x006e
        L_0x0067:
            r13.consumeToken()     // Catch:{ all -> 0x00ee }
            org.mozilla.javascript.ast.Name r7 = r13.createNameNode()     // Catch:{ all -> 0x00ee }
        L_0x006e:
            int r8 = r7.getType()     // Catch:{ all -> 0x00ee }
            if (r8 != r2) goto L_0x007f
            org.mozilla.javascript.TokenStream r8 = r13.ts     // Catch:{ all -> 0x00ee }
            java.lang.String r8 = r8.getString()     // Catch:{ all -> 0x00ee }
            r9 = 154(0x9a, float:2.16E-43)
            r13.defineSymbol(r9, r8, r3)     // Catch:{ all -> 0x00ee }
        L_0x007f:
            int r8 = r13.nextToken()     // Catch:{ all -> 0x00ee }
            r9 = 0
            if (r8 == r2) goto L_0x0091
            r2 = 52
            if (r8 == r2) goto L_0x008b
            goto L_0x00ad
        L_0x008b:
            org.mozilla.javascript.TokenStream r2 = r13.ts     // Catch:{ all -> 0x00ee }
            int r2 = r2.tokenBeg     // Catch:{ all -> 0x00ee }
            int r2 = r2 - r0
            goto L_0x00b3
        L_0x0091:
            java.lang.String r2 = "of"
            org.mozilla.javascript.TokenStream r8 = r13.ts     // Catch:{ all -> 0x00ee }
            java.lang.String r8 = r8.getString()     // Catch:{ all -> 0x00ee }
            boolean r2 = r2.equals(r8)     // Catch:{ all -> 0x00ee }
            if (r2 == 0) goto L_0x00ad
            if (r4 == r6) goto L_0x00a6
            java.lang.String r2 = "msg.invalid.for.each"
            r13.reportError(r2)     // Catch:{ all -> 0x00ee }
        L_0x00a6:
            org.mozilla.javascript.TokenStream r2 = r13.ts     // Catch:{ all -> 0x00ee }
            int r2 = r2.tokenBeg     // Catch:{ all -> 0x00ee }
            int r2 = r2 - r0
            r8 = 1
            goto L_0x00b4
        L_0x00ad:
            java.lang.String r2 = "msg.in.after.for.name"
            r13.reportError(r2)     // Catch:{ all -> 0x00ee }
            r2 = -1
        L_0x00b3:
            r8 = 0
        L_0x00b4:
            org.mozilla.javascript.ast.AstNode r10 = r13.expr()     // Catch:{ all -> 0x00ee }
            java.lang.String r11 = "msg.no.paren.for.ctrl"
            r12 = 89
            boolean r11 = r13.mustMatchToken(r12, r11, r3)     // Catch:{ all -> 0x00ee }
            if (r11 == 0) goto L_0x00c8
            org.mozilla.javascript.TokenStream r11 = r13.ts     // Catch:{ all -> 0x00ee }
            int r11 = r11.tokenBeg     // Catch:{ all -> 0x00ee }
            int r11 = r11 - r0
            goto L_0x00c9
        L_0x00c8:
            r11 = -1
        L_0x00c9:
            org.mozilla.javascript.TokenStream r12 = r13.ts     // Catch:{ all -> 0x00ee }
            int r12 = r12.tokenEnd     // Catch:{ all -> 0x00ee }
            int r12 = r12 - r0
            r1.setLength(r12)     // Catch:{ all -> 0x00ee }
            r1.setIterator(r7)     // Catch:{ all -> 0x00ee }
            r1.setIteratedObject(r10)     // Catch:{ all -> 0x00ee }
            r1.setInPosition(r2)     // Catch:{ all -> 0x00ee }
            r1.setEachPosition(r4)     // Catch:{ all -> 0x00ee }
            if (r4 == r6) goto L_0x00e0
            goto L_0x00e1
        L_0x00e0:
            r3 = 0
        L_0x00e1:
            r1.setIsForEach(r3)     // Catch:{ all -> 0x00ee }
            r1.setParens(r5, r11)     // Catch:{ all -> 0x00ee }
            r1.setIsForOf(r8)     // Catch:{ all -> 0x00ee }
            r13.popScope()
            return r1
        L_0x00ee:
            r0 = move-exception
            r13.popScope()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.arrayComprehensionLoop():org.mozilla.javascript.ast.ArrayComprehensionLoop");
    }

    private AstNode arrayLiteral() throws IOException {
        int peekToken;
        if (this.currentToken != 84) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        int i2 = tokenStream.tokenEnd;
        ArrayList arrayList = new ArrayList();
        ArrayLiteral arrayLiteral = new ArrayLiteral(i);
        int i3 = 1;
        int i4 = 0;
        while (true) {
            int i5 = -1;
            while (true) {
                peekToken = peekToken();
                if (peekToken != 90) {
                    if (peekToken != 162) {
                        break;
                    }
                    consumeToken();
                } else {
                    consumeToken();
                    i5 = this.ts.tokenEnd;
                    if (i3 == 0) {
                        i3 = 1;
                    } else {
                        arrayList.add(new EmptyExpression(this.ts.tokenBeg, 1));
                        i4++;
                    }
                }
            }
            if (peekToken == 85) {
                consumeToken();
                i2 = this.ts.tokenEnd;
                arrayLiteral.setDestructuringLength(arrayList.size() + i3);
                arrayLiteral.setSkipCount(i4);
                if (i5 != -1) {
                    warnTrailingComma(i, arrayList, i5);
                }
            } else if (peekToken == 120 && i3 == 0 && arrayList.size() == 1) {
                return arrayComprehension((AstNode) arrayList.get(0), i);
            } else {
                if (peekToken == 0) {
                    reportError("msg.no.bracket.arg");
                    break;
                }
                if (i3 == 0) {
                    reportError("msg.no.bracket.arg");
                }
                arrayList.add(assignExpr());
                i3 = 0;
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayLiteral.addElement((AstNode) it.next());
        }
        arrayLiteral.setLength(i2 - i);
        return arrayLiteral;
    }

    /* JADX INFO: finally extract failed */
    private AstNode arrowFunction(AstNode astNode) throws IOException {
        int i;
        int i2 = this.ts.lineno;
        if (astNode != null) {
            i = astNode.getPosition();
        } else {
            i = -1;
        }
        FunctionNode functionNode = new FunctionNode(i);
        functionNode.setFunctionType(4);
        functionNode.setJsDocNode(getAndResetJsDoc());
        HashMap hashMap = new HashMap();
        HashSet hashSet = new HashSet();
        PerFunctionVariables perFunctionVariables = new PerFunctionVariables(functionNode);
        try {
            if (astNode instanceof ParenthesizedExpression) {
                functionNode.setParens(0, astNode.getLength());
                AstNode expression = ((ParenthesizedExpression) astNode).getExpression();
                if (!(expression instanceof EmptyExpression)) {
                    arrowFunctionParams(functionNode, expression, hashMap, hashSet);
                }
            } else {
                arrowFunctionParams(functionNode, astNode, hashMap, hashSet);
            }
            if (!hashMap.isEmpty()) {
                Node node = new Node(90);
                for (Map.Entry entry : hashMap.entrySet()) {
                    node.addChildToBack(createDestructuringAssignment(123, (Node) entry.getValue(), createName((String) entry.getKey())));
                }
                functionNode.putProp(23, node);
            }
            functionNode.setBody(parseFunctionBody(4, functionNode));
            functionNode.setEncodedSourceBounds(i, this.ts.tokenEnd);
            functionNode.setLength(this.ts.tokenEnd - i);
            perFunctionVariables.restore();
            if (functionNode.isGenerator()) {
                reportError("msg.arrowfunction.generator");
                return makeErrorNode();
            }
            functionNode.setSourceName(this.sourceURI);
            functionNode.setBaseLineno(i2);
            functionNode.setEndLineno(this.ts.lineno);
            return functionNode;
        } catch (Throwable th) {
            perFunctionVariables.restore();
            throw th;
        }
    }

    private void arrowFunctionParams(FunctionNode functionNode, AstNode astNode, Map<String, Node> map, Set<String> set) {
        if ((astNode instanceof ArrayLiteral) || (astNode instanceof ObjectLiteral)) {
            markDestructuring(astNode);
            functionNode.addParam(astNode);
            String nextTempName = this.currentScriptOrFn.getNextTempName();
            defineSymbol(88, nextTempName, false);
            map.put(nextTempName, astNode);
        } else if ((astNode instanceof InfixExpression) && astNode.getType() == 90) {
            InfixExpression infixExpression = (InfixExpression) astNode;
            arrowFunctionParams(functionNode, infixExpression.getLeft(), map, set);
            arrowFunctionParams(functionNode, infixExpression.getRight(), map, set);
        } else if (astNode instanceof Name) {
            functionNode.addParam(astNode);
            String identifier = ((Name) astNode).getIdentifier();
            defineSymbol(88, identifier);
            if (this.inUseStrictDirective) {
                if ("eval".equals(identifier) || "arguments".equals(identifier)) {
                    reportError("msg.bad.id.strict", identifier);
                }
                if (set.contains(identifier)) {
                    addError("msg.dup.param.strict", identifier);
                }
                set.add(identifier);
            }
        } else {
            reportError("msg.no.parm", astNode.getPosition(), astNode.getLength());
            functionNode.addParam(makeErrorNode());
        }
    }

    private AstNode assignExpr() throws IOException {
        int peekToken = peekToken();
        boolean z = true;
        if (peekToken == 73) {
            return returnOrYield(peekToken, true);
        }
        AstNode condExpr = condExpr();
        int peekTokenOrEOL = peekTokenOrEOL();
        if (peekTokenOrEOL == 1) {
            peekTokenOrEOL = peekToken();
        } else {
            z = false;
        }
        if (91 <= peekTokenOrEOL && peekTokenOrEOL <= 102) {
            if (this.inDestructuringAssignment) {
                reportError("msg.destruct.default.vals");
            }
            consumeToken();
            Comment andResetJsDoc = getAndResetJsDoc();
            markDestructuring(condExpr);
            Assignment assignment = new Assignment(peekTokenOrEOL, condExpr, assignExpr(), this.ts.tokenBeg);
            if (andResetJsDoc != null) {
                assignment.setJsDocNode(andResetJsDoc);
            }
            return assignment;
        } else if (peekTokenOrEOL == 83) {
            if (this.currentJsDocComment == null) {
                return condExpr;
            }
            condExpr.setJsDocNode(getAndResetJsDoc());
            return condExpr;
        } else if (z || peekTokenOrEOL != 165) {
            return condExpr;
        } else {
            consumeToken();
            return arrowFunction(condExpr);
        }
    }

    private AstNode attributeAccess() throws IOException {
        int nextToken = nextToken();
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        if (nextToken == 23) {
            saveNameTokenData(i, "*", tokenStream.lineno);
            return propertyName(i, 0);
        } else if (nextToken == 39) {
            return propertyName(i, 0);
        } else {
            if (nextToken == 84) {
                return xmlElemRef(i, (Name) null, -1);
            }
            reportError("msg.no.name.after.xmlAttr");
            return makeErrorNode();
        }
    }

    private void autoInsertSemicolon(AstNode astNode) throws IOException {
        int peekFlaggedToken = peekFlaggedToken();
        int position = astNode.getPosition();
        int i = 65535 & peekFlaggedToken;
        if (!(i == -1 || i == 0)) {
            if (i == 83) {
                consumeToken();
                astNode.setLength(this.ts.tokenEnd - position);
                return;
            } else if (i != 87) {
                if ((peekFlaggedToken & 65536) == 0) {
                    reportError("msg.no.semi.stmt");
                    return;
                } else {
                    warnMissingSemi(position, nodeEnd(astNode));
                    return;
                }
            }
        }
        warnMissingSemi(position, Math.max(position + 1, nodeEnd(astNode)));
    }

    private AstNode bitAndExpr() throws IOException {
        AstNode eqExpr = eqExpr();
        while (matchToken(11, true)) {
            eqExpr = new InfixExpression(11, eqExpr, eqExpr(), this.ts.tokenBeg);
        }
        return eqExpr;
    }

    private AstNode bitOrExpr() throws IOException {
        AstNode bitXorExpr = bitXorExpr();
        while (matchToken(9, true)) {
            bitXorExpr = new InfixExpression(9, bitXorExpr, bitXorExpr(), this.ts.tokenBeg);
        }
        return bitXorExpr;
    }

    private AstNode bitXorExpr() throws IOException {
        AstNode bitAndExpr = bitAndExpr();
        while (matchToken(10, true)) {
            bitAndExpr = new InfixExpression(10, bitAndExpr, bitAndExpr(), this.ts.tokenBeg);
        }
        return bitAndExpr;
    }

    private AstNode block() throws IOException {
        if (this.currentToken != 86) {
            codeBug();
        }
        consumeToken();
        int i = this.ts.tokenBeg;
        Scope scope = new Scope(i);
        scope.setLineno(this.ts.lineno);
        pushScope(scope);
        try {
            statements(scope);
            mustMatchToken(87, "msg.no.brace.block", true);
            scope.setLength(this.ts.tokenEnd - i);
            return scope;
        } finally {
            popScope();
        }
    }

    private BreakStatement breakStatement() throws IOException {
        int i;
        Name name;
        if (this.currentToken != 121) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.tokenEnd;
        Jump jump = null;
        if (peekTokenOrEOL() == 39) {
            name = createNameNode();
            i = getNodeEnd(name);
        } else {
            i = i4;
            name = null;
        }
        LabeledStatement matchJumpLabelName = matchJumpLabelName();
        if (matchJumpLabelName != null) {
            jump = matchJumpLabelName.getFirstLabel();
        }
        if (jump == null && name == null) {
            List<Jump> list = this.loopAndSwitchSet;
            if (list == null || list.size() == 0) {
                reportError("msg.bad.break", i3, i - i3);
            } else {
                List<Jump> list2 = this.loopAndSwitchSet;
                jump = list2.get(list2.size() - 1);
            }
        }
        BreakStatement breakStatement = new BreakStatement(i3, i - i3);
        breakStatement.setBreakLabel(name);
        if (jump != null) {
            breakStatement.setBreakTarget(jump);
        }
        breakStatement.setLineno(i2);
        return breakStatement;
    }

    private void checkBadIncDec(UnaryExpression unaryExpression) {
        String str;
        int type = removeParens(unaryExpression.getOperand()).getType();
        if (type != 39 && type != 33 && type != 36 && type != 68 && type != 38) {
            if (unaryExpression.getType() == 107) {
                str = "msg.bad.incr";
            } else {
                str = "msg.bad.decr";
            }
            reportError(str);
        }
    }

    private void checkCallRequiresActivation(AstNode astNode) {
        if ((astNode.getType() == 39 && "eval".equals(((Name) astNode).getIdentifier())) || (astNode.getType() == 33 && "eval".equals(((PropertyGet) astNode).getProperty().getIdentifier()))) {
            setRequiresActivation();
        }
    }

    private RuntimeException codeBug() throws RuntimeException {
        throw Kit.codeBug("ts.cursor=" + this.ts.cursor + ", ts.tokenBeg=" + this.ts.tokenBeg + ", currentToken=" + this.currentToken);
    }

    /* JADX INFO: finally extract failed */
    private AstNode condExpr() throws IOException {
        int i;
        AstNode orExpr = orExpr();
        if (!matchToken(103, true)) {
            return orExpr;
        }
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        boolean z = this.inForInit;
        this.inForInit = false;
        try {
            AstNode assignExpr = assignExpr();
            this.inForInit = z;
            if (mustMatchToken(104, "msg.no.colon.cond", true)) {
                i = this.ts.tokenBeg;
            } else {
                i = -1;
            }
            AstNode assignExpr2 = assignExpr();
            int position = orExpr.getPosition();
            ConditionalExpression conditionalExpression = new ConditionalExpression(position, getNodeEnd(assignExpr2) - position);
            conditionalExpression.setLineno(i2);
            conditionalExpression.setTestExpression(orExpr);
            conditionalExpression.setTrueExpression(assignExpr);
            conditionalExpression.setFalseExpression(assignExpr2);
            conditionalExpression.setQuestionMarkPosition(i3 - position);
            conditionalExpression.setColonPosition(i - position);
            return conditionalExpression;
        } catch (Throwable th) {
            this.inForInit = z;
            throw th;
        }
    }

    private ConditionData condition() throws IOException {
        ConditionData conditionData = new ConditionData();
        if (mustMatchToken(88, "msg.no.paren.cond", true)) {
            conditionData.lp = this.ts.tokenBeg;
        }
        conditionData.condition = expr();
        if (mustMatchToken(89, "msg.no.paren.after.cond", true)) {
            conditionData.rp = this.ts.tokenBeg;
        }
        AstNode astNode = conditionData.condition;
        if (astNode instanceof Assignment) {
            addStrictWarning("msg.equal.as.assign", "", astNode.getPosition(), conditionData.condition.getLength());
        }
        return conditionData;
    }

    private void consumeToken() {
        this.currentFlaggedToken = 0;
    }

    private ContinueStatement continueStatement() throws IOException {
        int i;
        Name name;
        if (this.currentToken != 122) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.tokenEnd;
        Loop loop = null;
        if (peekTokenOrEOL() == 39) {
            name = createNameNode();
            i = getNodeEnd(name);
        } else {
            i = i4;
            name = null;
        }
        LabeledStatement matchJumpLabelName = matchJumpLabelName();
        if (matchJumpLabelName == null && name == null) {
            List<Loop> list = this.loopSet;
            if (list == null || list.size() == 0) {
                reportError("msg.continue.outside");
            } else {
                List<Loop> list2 = this.loopSet;
                loop = list2.get(list2.size() - 1);
            }
        } else {
            if (matchJumpLabelName == null || !(matchJumpLabelName.getStatement() instanceof Loop)) {
                reportError("msg.continue.nonloop", i3, i - i3);
            }
            if (matchJumpLabelName != null) {
                loop = (Loop) matchJumpLabelName.getStatement();
            }
        }
        ContinueStatement continueStatement = new ContinueStatement(i3, i - i3);
        if (loop != null) {
            continueStatement.setTarget(loop);
        }
        continueStatement.setLabel(name);
        continueStatement.setLineno(i2);
        return continueStatement;
    }

    private Name createNameNode() {
        return createNameNode(false, 39);
    }

    private StringLiteral createStringLiteral() {
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        StringLiteral stringLiteral = new StringLiteral(i, tokenStream.tokenEnd - i);
        stringLiteral.setLineno(this.ts.lineno);
        stringLiteral.setValue(this.ts.getString());
        stringLiteral.setQuoteCharacter(this.ts.getQuoteChar());
        return stringLiteral;
    }

    private AstNode defaultXmlNamespace() throws IOException {
        if (this.currentToken != 117) {
            codeBug();
        }
        consumeToken();
        mustHaveXML();
        setRequiresActivation();
        TokenStream tokenStream = this.ts;
        int i = tokenStream.lineno;
        int i2 = tokenStream.tokenBeg;
        if (!matchToken(39, true) || !"xml".equals(this.ts.getString())) {
            reportError("msg.bad.namespace");
        }
        if (!matchToken(39, true) || !"namespace".equals(this.ts.getString())) {
            reportError("msg.bad.namespace");
        }
        if (!matchToken(91, true)) {
            reportError("msg.bad.namespace");
        }
        AstNode expr = expr();
        UnaryExpression unaryExpression = new UnaryExpression(i2, getNodeEnd(expr) - i2);
        unaryExpression.setOperator(75);
        unaryExpression.setOperand(expr);
        unaryExpression.setLineno(i);
        return new ExpressionStatement((AstNode) unaryExpression, true);
    }

    /* JADX INFO: finally extract failed */
    private AstNode destructuringPrimaryExpr() throws IOException, ParserException {
        try {
            this.inDestructuringAssignment = true;
            AstNode primaryExpr = primaryExpr();
            this.inDestructuringAssignment = false;
            return primaryExpr;
        } catch (Throwable th) {
            this.inDestructuringAssignment = false;
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    private DoLoop doLoop() throws IOException {
        if (this.currentToken != 119) {
            codeBug();
        }
        consumeToken();
        int i = this.ts.tokenBeg;
        DoLoop doLoop = new DoLoop(i);
        doLoop.setLineno(this.ts.lineno);
        enterLoop(doLoop);
        try {
            AstNode nextStatementAfterInlineComments = getNextStatementAfterInlineComments(doLoop);
            mustMatchToken(118, "msg.no.while.do", true);
            doLoop.setWhilePosition(this.ts.tokenBeg - i);
            ConditionData condition = condition();
            doLoop.setCondition(condition.condition);
            doLoop.setParens(condition.lp - i, condition.rp - i);
            int nodeEnd = getNodeEnd(nextStatementAfterInlineComments);
            doLoop.setBody(nextStatementAfterInlineComments);
            exitLoop();
            if (matchToken(83, true)) {
                nodeEnd = this.ts.tokenEnd;
            }
            doLoop.setLength(nodeEnd - i);
            return doLoop;
        } catch (Throwable th) {
            exitLoop();
            throw th;
        }
    }

    private void enterLoop(Loop loop) {
        if (this.loopSet == null) {
            this.loopSet = new ArrayList();
        }
        this.loopSet.add(loop);
        if (this.loopAndSwitchSet == null) {
            this.loopAndSwitchSet = new ArrayList();
        }
        this.loopAndSwitchSet.add(loop);
        pushScope(loop);
        LabeledStatement labeledStatement = this.currentLabel;
        if (labeledStatement != null) {
            labeledStatement.setStatement(loop);
            this.currentLabel.getFirstLabel().setLoop(loop);
            loop.setRelative(-this.currentLabel.getPosition());
        }
    }

    private void enterSwitch(SwitchStatement switchStatement) {
        if (this.loopAndSwitchSet == null) {
            this.loopAndSwitchSet = new ArrayList();
        }
        this.loopAndSwitchSet.add(switchStatement);
    }

    private AstNode eqExpr() throws IOException {
        AstNode relExpr = relExpr();
        while (true) {
            int peekToken = peekToken();
            int i = this.ts.tokenBeg;
            if (peekToken != 12 && peekToken != 13 && peekToken != 46 && peekToken != 47) {
                return relExpr;
            }
            consumeToken();
            if (this.compilerEnv.getLanguageVersion() == 120) {
                if (peekToken == 12) {
                    peekToken = 46;
                } else if (peekToken == 13) {
                    peekToken = 47;
                }
            }
            relExpr = new InfixExpression(peekToken, relExpr, relExpr(), i);
        }
    }

    private void exitLoop() {
        List<Loop> list = this.loopSet;
        Loop remove = list.remove(list.size() - 1);
        List<Jump> list2 = this.loopAndSwitchSet;
        list2.remove(list2.size() - 1);
        if (remove.getParent() != null) {
            remove.setRelative(remove.getParent().getPosition());
        }
        popScope();
    }

    private void exitSwitch() {
        List<Jump> list = this.loopAndSwitchSet;
        list.remove(list.size() - 1);
    }

    private AstNode expr() throws IOException {
        AstNode assignExpr = assignExpr();
        int position = assignExpr.getPosition();
        while (matchToken(90, true)) {
            int i = this.ts.tokenBeg;
            if (this.compilerEnv.isStrictMode() && !assignExpr.hasSideEffects()) {
                addStrictWarning("msg.no.side.effects", "", position, nodeEnd(assignExpr) - position);
            }
            if (peekToken() == 73) {
                reportError("msg.yield.parenthesized");
            }
            assignExpr = new InfixExpression(90, assignExpr, assignExpr(), i);
        }
        return assignExpr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: org.mozilla.javascript.ast.ForInLoop} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: org.mozilla.javascript.ast.ForInLoop} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: org.mozilla.javascript.ast.ForLoop} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v20, resolved type: org.mozilla.javascript.ast.ForInLoop} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004a A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0050 A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064 A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0073 A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ec A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00f2 A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f5 A[Catch:{ all -> 0x0169, all -> 0x016e }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x015f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.Loop forLoop() throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            int r0 = r1.currentToken
            r2 = 120(0x78, float:1.68E-43)
            if (r0 == r2) goto L_0x000b
            r16.codeBug()
        L_0x000b:
            r16.consumeToken()
            org.mozilla.javascript.TokenStream r0 = r1.ts
            int r2 = r0.tokenBeg
            int r0 = r0.lineno
            org.mozilla.javascript.ast.Scope r3 = new org.mozilla.javascript.ast.Scope
            r3.<init>()
            r1.pushScope(r3)
            r4 = 39
            r5 = 1
            boolean r6 = r1.matchToken(r4, r5)     // Catch:{ all -> 0x016e }
            java.lang.String r7 = "msg.no.paren.for"
            r8 = 0
            if (r6 == 0) goto L_0x0040
            java.lang.String r6 = "each"
            org.mozilla.javascript.TokenStream r10 = r1.ts     // Catch:{ all -> 0x016e }
            java.lang.String r10 = r10.getString()     // Catch:{ all -> 0x016e }
            boolean r6 = r6.equals(r10)     // Catch:{ all -> 0x016e }
            if (r6 == 0) goto L_0x003d
            org.mozilla.javascript.TokenStream r6 = r1.ts     // Catch:{ all -> 0x016e }
            int r6 = r6.tokenBeg     // Catch:{ all -> 0x016e }
            int r6 = r6 - r2
            r10 = 1
            goto L_0x0042
        L_0x003d:
            r1.reportError(r7)     // Catch:{ all -> 0x016e }
        L_0x0040:
            r6 = -1
            r10 = 0
        L_0x0042:
            r11 = 88
            boolean r7 = r1.mustMatchToken(r11, r7, r5)     // Catch:{ all -> 0x016e }
            if (r7 == 0) goto L_0x0050
            org.mozilla.javascript.TokenStream r7 = r1.ts     // Catch:{ all -> 0x016e }
            int r7 = r7.tokenBeg     // Catch:{ all -> 0x016e }
            int r7 = r7 - r2
            goto L_0x0051
        L_0x0050:
            r7 = -1
        L_0x0051:
            int r11 = r16.peekToken()     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.ast.AstNode r11 = r1.forLoopInit(r11)     // Catch:{ all -> 0x016e }
            r12 = 52
            boolean r12 = r1.matchToken(r12, r5)     // Catch:{ all -> 0x016e }
            r13 = 89
            r14 = 0
            if (r12 == 0) goto L_0x0073
            org.mozilla.javascript.TokenStream r4 = r1.ts     // Catch:{ all -> 0x016e }
            int r4 = r4.tokenBeg     // Catch:{ all -> 0x016e }
            int r4 = r4 - r2
            org.mozilla.javascript.ast.AstNode r12 = r16.expr()     // Catch:{ all -> 0x016e }
            r15 = r14
            r8 = 1
            r14 = r12
            r12 = 0
            goto L_0x00e4
        L_0x0073:
            org.mozilla.javascript.CompilerEnvirons r12 = r1.compilerEnv     // Catch:{ all -> 0x016e }
            int r12 = r12.getLanguageVersion()     // Catch:{ all -> 0x016e }
            r15 = 200(0xc8, float:2.8E-43)
            if (r12 < r15) goto L_0x009e
            boolean r4 = r1.matchToken(r4, r5)     // Catch:{ all -> 0x016e }
            if (r4 == 0) goto L_0x009e
            java.lang.String r4 = "of"
            org.mozilla.javascript.TokenStream r12 = r1.ts     // Catch:{ all -> 0x016e }
            java.lang.String r12 = r12.getString()     // Catch:{ all -> 0x016e }
            boolean r4 = r4.equals(r12)     // Catch:{ all -> 0x016e }
            if (r4 == 0) goto L_0x009e
            org.mozilla.javascript.TokenStream r4 = r1.ts     // Catch:{ all -> 0x016e }
            int r4 = r4.tokenBeg     // Catch:{ all -> 0x016e }
            int r4 = r4 - r2
            org.mozilla.javascript.ast.AstNode r12 = r16.expr()     // Catch:{ all -> 0x016e }
            r15 = r14
            r14 = r12
            r12 = 1
            goto L_0x00e4
        L_0x009e:
            java.lang.String r4 = "msg.no.semi.for"
            r12 = 83
            r1.mustMatchToken(r12, r4, r5)     // Catch:{ all -> 0x016e }
            int r4 = r16.peekToken()     // Catch:{ all -> 0x016e }
            if (r4 != r12) goto L_0x00bc
            org.mozilla.javascript.ast.EmptyExpression r4 = new org.mozilla.javascript.ast.EmptyExpression     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.TokenStream r14 = r1.ts     // Catch:{ all -> 0x016e }
            int r14 = r14.tokenBeg     // Catch:{ all -> 0x016e }
            r4.<init>(r14, r5)     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.TokenStream r14 = r1.ts     // Catch:{ all -> 0x016e }
            int r14 = r14.lineno     // Catch:{ all -> 0x016e }
            r4.setLineno(r14)     // Catch:{ all -> 0x016e }
            goto L_0x00c0
        L_0x00bc:
            org.mozilla.javascript.ast.AstNode r4 = r16.expr()     // Catch:{ all -> 0x016e }
        L_0x00c0:
            java.lang.String r14 = "msg.no.semi.for.cond"
            r1.mustMatchToken(r12, r14, r5)     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.TokenStream r12 = r1.ts     // Catch:{ all -> 0x016e }
            int r12 = r12.tokenEnd     // Catch:{ all -> 0x016e }
            int r14 = r16.peekToken()     // Catch:{ all -> 0x016e }
            if (r14 != r13) goto L_0x00dc
            org.mozilla.javascript.ast.EmptyExpression r14 = new org.mozilla.javascript.ast.EmptyExpression     // Catch:{ all -> 0x016e }
            r14.<init>(r12, r5)     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.TokenStream r12 = r1.ts     // Catch:{ all -> 0x016e }
            int r12 = r12.lineno     // Catch:{ all -> 0x016e }
            r14.setLineno(r12)     // Catch:{ all -> 0x016e }
            goto L_0x00e0
        L_0x00dc:
            org.mozilla.javascript.ast.AstNode r14 = r16.expr()     // Catch:{ all -> 0x016e }
        L_0x00e0:
            r15 = r14
            r12 = 0
            r14 = r4
            r4 = -1
        L_0x00e4:
            java.lang.String r9 = "msg.no.paren.for.ctrl"
            boolean r9 = r1.mustMatchToken(r13, r9, r5)     // Catch:{ all -> 0x016e }
            if (r9 == 0) goto L_0x00f2
            org.mozilla.javascript.TokenStream r9 = r1.ts     // Catch:{ all -> 0x016e }
            int r9 = r9.tokenBeg     // Catch:{ all -> 0x016e }
            int r9 = r9 - r2
            goto L_0x00f3
        L_0x00f2:
            r9 = -1
        L_0x00f3:
            if (r8 != 0) goto L_0x0107
            if (r12 == 0) goto L_0x00f8
            goto L_0x0107
        L_0x00f8:
            org.mozilla.javascript.ast.ForLoop r4 = new org.mozilla.javascript.ast.ForLoop     // Catch:{ all -> 0x016e }
            r4.<init>(r2)     // Catch:{ all -> 0x016e }
            r4.setInitializer(r11)     // Catch:{ all -> 0x016e }
            r4.setCondition(r14)     // Catch:{ all -> 0x016e }
            r4.setIncrement(r15)     // Catch:{ all -> 0x016e }
            goto L_0x013e
        L_0x0107:
            org.mozilla.javascript.ast.ForInLoop r8 = new org.mozilla.javascript.ast.ForInLoop     // Catch:{ all -> 0x016e }
            r8.<init>(r2)     // Catch:{ all -> 0x016e }
            boolean r13 = r11 instanceof org.mozilla.javascript.ast.VariableDeclaration     // Catch:{ all -> 0x016e }
            if (r13 == 0) goto L_0x0122
            r13 = r11
            org.mozilla.javascript.ast.VariableDeclaration r13 = (org.mozilla.javascript.ast.VariableDeclaration) r13     // Catch:{ all -> 0x016e }
            java.util.List r13 = r13.getVariables()     // Catch:{ all -> 0x016e }
            int r13 = r13.size()     // Catch:{ all -> 0x016e }
            if (r13 <= r5) goto L_0x0122
            java.lang.String r5 = "msg.mult.index"
            r1.reportError(r5)     // Catch:{ all -> 0x016e }
        L_0x0122:
            if (r12 == 0) goto L_0x012b
            if (r10 == 0) goto L_0x012b
            java.lang.String r5 = "msg.invalid.for.each"
            r1.reportError(r5)     // Catch:{ all -> 0x016e }
        L_0x012b:
            r8.setIterator(r11)     // Catch:{ all -> 0x016e }
            r8.setIteratedObject(r14)     // Catch:{ all -> 0x016e }
            r8.setInPosition(r4)     // Catch:{ all -> 0x016e }
            r8.setIsForEach(r10)     // Catch:{ all -> 0x016e }
            r8.setEachPosition(r6)     // Catch:{ all -> 0x016e }
            r8.setIsForOf(r12)     // Catch:{ all -> 0x016e }
            r4 = r8
        L_0x013e:
            org.mozilla.javascript.ast.Scope r5 = r1.currentScope     // Catch:{ all -> 0x016e }
            r5.replaceWith(r4)     // Catch:{ all -> 0x016e }
            r16.popScope()     // Catch:{ all -> 0x016e }
            r1.enterLoop(r4)     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.ast.AstNode r5 = r1.getNextStatementAfterInlineComments(r4)     // Catch:{ all -> 0x0169 }
            int r6 = getNodeEnd(r5)     // Catch:{ all -> 0x0169 }
            int r6 = r6 - r2
            r4.setLength(r6)     // Catch:{ all -> 0x0169 }
            r4.setBody(r5)     // Catch:{ all -> 0x0169 }
            r16.exitLoop()     // Catch:{ all -> 0x016e }
            org.mozilla.javascript.ast.Scope r2 = r1.currentScope
            if (r2 != r3) goto L_0x0162
            r16.popScope()
        L_0x0162:
            r4.setParens(r7, r9)
            r4.setLineno(r0)
            return r4
        L_0x0169:
            r0 = move-exception
            r16.exitLoop()     // Catch:{ all -> 0x016e }
            throw r0     // Catch:{ all -> 0x016e }
        L_0x016e:
            r0 = move-exception
            org.mozilla.javascript.ast.Scope r2 = r1.currentScope
            if (r2 != r3) goto L_0x0176
            r16.popScope()
        L_0x0176:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.forLoop():org.mozilla.javascript.ast.Loop");
    }

    /* JADX INFO: finally extract failed */
    private AstNode forLoopInit(int i) throws IOException {
        AstNode astNode;
        try {
            this.inForInit = true;
            if (i == 83) {
                astNode = new EmptyExpression(this.ts.tokenBeg, 1);
                astNode.setLineno(this.ts.lineno);
            } else {
                if (i != 123) {
                    if (i != 154) {
                        astNode = expr();
                        markDestructuring(astNode);
                    }
                }
                consumeToken();
                astNode = variables(i, this.ts.tokenBeg, false);
            }
            this.inForInit = false;
            return astNode;
        } catch (Throwable th) {
            this.inForInit = false;
            throw th;
        }
    }

    private FunctionNode function(int i) throws IOException {
        return function(i, false);
    }

    private AstNode generatorExpression(AstNode astNode, int i) throws IOException {
        return generatorExpression(astNode, i, false);
    }

    private GeneratorExpressionLoop generatorExpressionLoop() throws IOException {
        int i;
        AstNode astNode;
        int i2;
        if (nextToken() != 120) {
            codeBug();
        }
        int i3 = this.ts.tokenBeg;
        GeneratorExpressionLoop generatorExpressionLoop = new GeneratorExpressionLoop(i3);
        pushScope(generatorExpressionLoop);
        try {
            int i4 = -1;
            if (mustMatchToken(88, "msg.no.paren.for", true)) {
                i = this.ts.tokenBeg - i3;
            } else {
                i = -1;
            }
            int peekToken = peekToken();
            if (peekToken == 39) {
                consumeToken();
                astNode = createNameNode();
            } else if (peekToken == 84 || peekToken == 86) {
                astNode = destructuringPrimaryExpr();
                markDestructuring(astNode);
            } else {
                reportError("msg.bad.var");
                astNode = null;
            }
            if (astNode.getType() == 39) {
                defineSymbol(Token.LET, this.ts.getString(), true);
            }
            if (mustMatchToken(52, "msg.in.after.for.name", true)) {
                i2 = this.ts.tokenBeg - i3;
            } else {
                i2 = -1;
            }
            AstNode expr = expr();
            if (mustMatchToken(89, "msg.no.paren.for.ctrl", true)) {
                i4 = this.ts.tokenBeg - i3;
            }
            generatorExpressionLoop.setLength(this.ts.tokenEnd - i3);
            generatorExpressionLoop.setIterator(astNode);
            generatorExpressionLoop.setIteratedObject(expr);
            generatorExpressionLoop.setInPosition(i2);
            generatorExpressionLoop.setParens(i, i4);
            return generatorExpressionLoop;
        } finally {
            popScope();
        }
    }

    private Comment getAndResetJsDoc() {
        Comment comment = this.currentJsDocComment;
        this.currentJsDocComment = null;
        return comment;
    }

    private static String getDirective(AstNode astNode) {
        if (!(astNode instanceof ExpressionStatement)) {
            return null;
        }
        AstNode expression = ((ExpressionStatement) astNode).getExpression();
        if (expression instanceof StringLiteral) {
            return ((StringLiteral) expression).getValue();
        }
        return null;
    }

    private AstNode getNextStatementAfterInlineComments(AstNode astNode) throws IOException {
        AstNode statement = statement();
        if (162 != statement.getType()) {
            return statement;
        }
        AstNode statement2 = statement();
        if (astNode != null) {
            astNode.setInlineComment(statement);
        } else {
            statement2.setInlineComment(statement);
        }
        return statement2;
    }

    private static int getNodeEnd(AstNode astNode) {
        return astNode.getLength() + astNode.getPosition();
    }

    private IfStatement ifStatement() throws IOException {
        AstNode astNode;
        int i;
        AstNode astNode2;
        if (this.currentToken != 113) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        int i3 = tokenStream.lineno;
        IfStatement ifStatement = new IfStatement(i2);
        ConditionData condition = condition();
        AstNode nextStatementAfterInlineComments = getNextStatementAfterInlineComments(ifStatement);
        if (matchToken(114, true)) {
            if (peekToken() == 162) {
                List<Comment> list = this.scannedComments;
                ifStatement.setElseKeyWordInlineComment(list.get(list.size() - 1));
                consumeToken();
            }
            i = this.ts.tokenBeg - i2;
            astNode = statement();
        } else {
            i = -1;
            astNode = null;
        }
        if (astNode != null) {
            astNode2 = astNode;
        } else {
            astNode2 = nextStatementAfterInlineComments;
        }
        ifStatement.setLength(getNodeEnd(astNode2) - i2);
        ifStatement.setCondition(condition.condition);
        ifStatement.setParens(condition.lp - i2, condition.rp - i2);
        ifStatement.setThenPart(nextStatementAfterInlineComments);
        ifStatement.setElsePart(astNode);
        ifStatement.setElsePosition(i);
        ifStatement.setLineno(i3);
        return ifStatement;
    }

    private AstNode let(boolean z, int i) throws IOException {
        LetNode letNode = new LetNode(i);
        letNode.setLineno(this.ts.lineno);
        boolean z2 = true;
        if (mustMatchToken(88, "msg.no.paren.after.let", true)) {
            letNode.setLp(this.ts.tokenBeg - i);
        }
        pushScope(letNode);
        try {
            letNode.setVariables(variables(Token.LET, this.ts.tokenBeg, z));
            if (mustMatchToken(89, "msg.no.paren.let", true)) {
                letNode.setRp(this.ts.tokenBeg - i);
            }
            if (!z || peekToken() != 86) {
                AstNode expr = expr();
                letNode.setLength(getNodeEnd(expr) - i);
                letNode.setBody(expr);
                if (z) {
                    if (insideFunction()) {
                        z2 = false;
                    }
                    ExpressionStatement expressionStatement = new ExpressionStatement((AstNode) letNode, z2);
                    expressionStatement.setLineno(letNode.getLineno());
                    popScope();
                    return expressionStatement;
                }
            } else {
                consumeToken();
                int i2 = this.ts.tokenBeg;
                AstNode statements = statements();
                mustMatchToken(87, "msg.no.curly.let", true);
                statements.setLength(this.ts.tokenEnd - i2);
                letNode.setLength(this.ts.tokenEnd - i);
                letNode.setBody(statements);
                letNode.setType(Token.LET);
            }
            return letNode;
        } finally {
            popScope();
        }
    }

    private AstNode letStatement() throws IOException {
        AstNode astNode;
        if (this.currentToken != 154) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i = tokenStream.lineno;
        int i2 = tokenStream.tokenBeg;
        if (peekToken() == 88) {
            astNode = let(true, i2);
        } else {
            astNode = variables(Token.LET, i2, true);
        }
        astNode.setLineno(i);
        return astNode;
    }

    private int lineBeginningFor(int i) {
        char[] cArr = this.sourceChars;
        if (cArr == null) {
            return -1;
        }
        if (i <= 0) {
            return 0;
        }
        if (i >= cArr.length) {
            i = cArr.length - 1;
        }
        do {
            i--;
            if (i < 0) {
                return 0;
            }
        } while (!ScriptRuntime.isJSLineTerminator(cArr[i]));
        return i + 1;
    }

    private ErrorNode makeErrorNode() {
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        ErrorNode errorNode = new ErrorNode(i, tokenStream.tokenEnd - i);
        errorNode.setLineno(this.ts.lineno);
        return errorNode;
    }

    private LabeledStatement matchJumpLabelName() throws IOException {
        LabeledStatement labeledStatement = null;
        if (peekTokenOrEOL() == 39) {
            consumeToken();
            Map<String, LabeledStatement> map = this.labelSet;
            if (map != null) {
                labeledStatement = map.get(this.ts.getString());
            }
            if (labeledStatement == null) {
                reportError("msg.undef.label");
            }
        }
        return labeledStatement;
    }

    private boolean matchToken(int i, boolean z) throws IOException {
        int peekToken = peekToken();
        while (peekToken == 162 && z) {
            consumeToken();
            peekToken = peekToken();
        }
        if (peekToken != i) {
            return false;
        }
        consumeToken();
        return true;
    }

    private AstNode memberExpr(boolean z) throws IOException {
        AstNode astNode;
        int peekToken = peekToken();
        int i = this.ts.lineno;
        if (peekToken != 30) {
            astNode = primaryExpr();
        } else {
            consumeToken();
            int i2 = this.ts.tokenBeg;
            NewExpression newExpression = new NewExpression(i2);
            AstNode memberExpr = memberExpr(false);
            int nodeEnd = getNodeEnd(memberExpr);
            newExpression.setTarget(memberExpr);
            if (matchToken(88, true)) {
                int i3 = this.ts.tokenBeg;
                List<AstNode> argumentList = argumentList();
                if (argumentList != null && argumentList.size() > 65536) {
                    reportError("msg.too.many.constructor.args");
                }
                TokenStream tokenStream = this.ts;
                int i4 = tokenStream.tokenBeg;
                int i5 = tokenStream.tokenEnd;
                if (argumentList != null) {
                    newExpression.setArguments(argumentList);
                }
                newExpression.setParens(i3 - i2, i4 - i2);
                nodeEnd = i5;
            }
            if (matchToken(86, true)) {
                ObjectLiteral objectLiteral = objectLiteral();
                nodeEnd = getNodeEnd(objectLiteral);
                newExpression.setInitializer(objectLiteral);
            }
            newExpression.setLength(nodeEnd - i2);
            astNode = newExpression;
        }
        astNode.setLineno(i);
        return memberExprTail(z, astNode);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: org.mozilla.javascript.ast.ElementGet} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: org.mozilla.javascript.ast.ElementGet} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: org.mozilla.javascript.ast.ElementGet} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.AstNode memberExprTail(boolean r10, org.mozilla.javascript.ast.AstNode r11) throws java.io.IOException {
        /*
            r9 = this;
            if (r11 != 0) goto L_0x0005
            r9.codeBug()
        L_0x0005:
            int r0 = r11.getPosition()
        L_0x0009:
            int r1 = r9.peekToken()
            r2 = 84
            r3 = 1
            r4 = -1
            if (r1 == r2) goto L_0x00cb
            r2 = 88
            r5 = 65536(0x10000, float:9.18355E-41)
            if (r1 == r2) goto L_0x0084
            r2 = 109(0x6d, float:1.53E-43)
            if (r1 == r2) goto L_0x0078
            r2 = 144(0x90, float:2.02E-43)
            if (r1 == r2) goto L_0x0078
            r2 = 147(0x93, float:2.06E-43)
            if (r1 == r2) goto L_0x0039
            r2 = 162(0xa2, float:2.27E-43)
            if (r1 == r2) goto L_0x002a
            goto L_0x0086
        L_0x002a:
            int r2 = r9.currentFlaggedToken
            r9.peekUntilNonComment(r1)
            int r1 = r9.currentFlaggedToken
            r3 = r1 & r5
            if (r3 == 0) goto L_0x0036
            r2 = r1
        L_0x0036:
            r9.currentFlaggedToken = r2
            goto L_0x0009
        L_0x0039:
            r9.consumeToken()
            org.mozilla.javascript.TokenStream r1 = r9.ts
            int r2 = r1.tokenBeg
            int r1 = r1.lineno
            r9.mustHaveXML()
            r9.setRequiresActivation()
            org.mozilla.javascript.ast.AstNode r5 = r9.expr()
            int r6 = getNodeEnd(r5)
            r7 = 89
            java.lang.String r8 = "msg.no.paren"
            boolean r3 = r9.mustMatchToken(r7, r8, r3)
            if (r3 == 0) goto L_0x0060
            org.mozilla.javascript.TokenStream r3 = r9.ts
            int r4 = r3.tokenBeg
            int r6 = r3.tokenEnd
        L_0x0060:
            org.mozilla.javascript.ast.XmlDotQuery r3 = new org.mozilla.javascript.ast.XmlDotQuery
            int r6 = r6 - r0
            r3.<init>(r0, r6)
            r3.setLeft(r11)
            r3.setRight(r5)
            r3.setOperatorPosition(r2)
            int r4 = r4 - r0
            r3.setRp(r4)
            r3.setLineno(r1)
            goto L_0x00fe
        L_0x0078:
            org.mozilla.javascript.TokenStream r2 = r9.ts
            int r2 = r2.lineno
            org.mozilla.javascript.ast.AstNode r11 = r9.propertyAccess(r1, r11)
            r11.setLineno(r2)
            goto L_0x0009
        L_0x0084:
            if (r10 != 0) goto L_0x0087
        L_0x0086:
            return r11
        L_0x0087:
            org.mozilla.javascript.TokenStream r1 = r9.ts
            int r1 = r1.lineno
            r9.consumeToken()
            r9.checkCallRequiresActivation(r11)
            org.mozilla.javascript.ast.FunctionCall r2 = new org.mozilla.javascript.ast.FunctionCall
            r2.<init>(r0)
            r2.setTarget(r11)
            r2.setLineno(r1)
            org.mozilla.javascript.TokenStream r11 = r9.ts
            int r11 = r11.tokenBeg
            int r11 = r11 - r0
            r2.setLp(r11)
            java.util.List r11 = r9.argumentList()
            if (r11 == 0) goto L_0x00b5
            int r1 = r11.size()
            if (r1 <= r5) goto L_0x00b5
            java.lang.String r1 = "msg.too.many.function.args"
            r9.reportError(r1)
        L_0x00b5:
            r2.setArguments(r11)
            org.mozilla.javascript.TokenStream r11 = r9.ts
            int r11 = r11.tokenBeg
            int r11 = r11 - r0
            r2.setRp(r11)
            org.mozilla.javascript.TokenStream r11 = r9.ts
            int r11 = r11.tokenEnd
            int r11 = r11 - r0
            r2.setLength(r11)
            r11 = r2
            goto L_0x0009
        L_0x00cb:
            r9.consumeToken()
            org.mozilla.javascript.TokenStream r1 = r9.ts
            int r2 = r1.tokenBeg
            int r1 = r1.lineno
            org.mozilla.javascript.ast.AstNode r5 = r9.expr()
            int r6 = getNodeEnd(r5)
            r7 = 85
            java.lang.String r8 = "msg.no.bracket.index"
            boolean r3 = r9.mustMatchToken(r7, r8, r3)
            if (r3 == 0) goto L_0x00ec
            org.mozilla.javascript.TokenStream r3 = r9.ts
            int r4 = r3.tokenBeg
            int r6 = r3.tokenEnd
        L_0x00ec:
            org.mozilla.javascript.ast.ElementGet r3 = new org.mozilla.javascript.ast.ElementGet
            int r6 = r6 - r0
            r3.<init>((int) r0, (int) r6)
            r3.setTarget(r11)
            r3.setElement(r5)
            r3.setParens(r2, r4)
            r3.setLineno(r1)
        L_0x00fe:
            r11 = r3
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.memberExprTail(boolean, org.mozilla.javascript.ast.AstNode):org.mozilla.javascript.ast.AstNode");
    }

    private ObjectProperty methodDefinition(int i, AstNode astNode, int i2) throws IOException {
        FunctionNode function = function(2);
        Name functionName = function.getFunctionName();
        if (!(functionName == null || functionName.length() == 0)) {
            reportError("msg.bad.prop");
        }
        ObjectProperty objectProperty = new ObjectProperty(i);
        if (i2 == 2) {
            objectProperty.setIsGetterMethod();
            function.setFunctionIsGetterMethod();
        } else if (i2 == 4) {
            objectProperty.setIsSetterMethod();
            function.setFunctionIsSetterMethod();
        } else if (i2 == 8) {
            objectProperty.setIsNormalMethod();
            function.setFunctionIsNormalMethod();
        }
        int nodeEnd = getNodeEnd(function);
        objectProperty.setLeft(astNode);
        objectProperty.setRight(function);
        objectProperty.setLength(nodeEnd - i);
        return objectProperty;
    }

    private AstNode mulExpr() throws IOException {
        AstNode unaryExpr = unaryExpr();
        while (true) {
            int peekToken = peekToken();
            int i = this.ts.tokenBeg;
            switch (peekToken) {
                case 23:
                case 24:
                case 25:
                    consumeToken();
                    unaryExpr = new InfixExpression(peekToken, unaryExpr, unaryExpr(), i);
                default:
                    return unaryExpr;
            }
        }
    }

    private void mustHaveXML() {
        if (!this.compilerEnv.isXmlAvailable()) {
            reportError("msg.XML.not.available");
        }
    }

    private boolean mustMatchToken(int i, String str, boolean z) throws IOException {
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        return mustMatchToken(i, str, i2, tokenStream.tokenEnd - i2, z);
    }

    private AstNode name(int i, int i2) throws IOException {
        String string = this.ts.getString();
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.tokenBeg;
        int i4 = tokenStream.lineno;
        if ((i & 131072) == 0 || peekToken() != 104) {
            saveNameTokenData(i3, string, i4);
            if (this.compilerEnv.isXmlAvailable()) {
                return propertyName(-1, 0);
            }
            return createNameNode(true, 39);
        }
        Label label = new Label(i3, this.ts.tokenEnd - i3);
        label.setName(string);
        label.setLineno(this.ts.lineno);
        return label;
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private org.mozilla.javascript.ast.AstNode nameOrLabel() throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r8.currentToken
            r1 = 39
            if (r0 != r1) goto L_0x00ff
            org.mozilla.javascript.TokenStream r0 = r8.ts
            int r0 = r0.tokenBeg
            int r2 = r8.currentFlaggedToken
            r3 = 131072(0x20000, float:1.83671E-40)
            r2 = r2 | r3
            r8.currentFlaggedToken = r2
            org.mozilla.javascript.ast.AstNode r2 = r8.expr()
            int r4 = r2.getType()
            r5 = 131(0x83, float:1.84E-43)
            if (r4 == r5) goto L_0x002d
            org.mozilla.javascript.ast.ExpressionStatement r0 = new org.mozilla.javascript.ast.ExpressionStatement
            boolean r1 = r8.insideFunction()
            r1 = r1 ^ 1
            r0.<init>((org.mozilla.javascript.ast.AstNode) r2, (boolean) r1)
            int r1 = r2.lineno
            r0.lineno = r1
            return r0
        L_0x002d:
            org.mozilla.javascript.ast.LabeledStatement r4 = new org.mozilla.javascript.ast.LabeledStatement
            r4.<init>(r0)
            org.mozilla.javascript.ast.Label r2 = (org.mozilla.javascript.ast.Label) r2
            r8.recordLabel(r2, r4)
            org.mozilla.javascript.TokenStream r2 = r8.ts
            int r2 = r2.lineno
            r4.setLineno(r2)
        L_0x003e:
            int r2 = r8.peekToken()
            r6 = 0
            if (r2 != r1) goto L_0x0069
            int r2 = r8.currentFlaggedToken
            r2 = r2 | r3
            r8.currentFlaggedToken = r2
            org.mozilla.javascript.ast.AstNode r2 = r8.expr()
            int r7 = r2.getType()
            if (r7 == r5) goto L_0x0063
            org.mozilla.javascript.ast.ExpressionStatement r1 = new org.mozilla.javascript.ast.ExpressionStatement
            boolean r3 = r8.insideFunction()
            r3 = r3 ^ 1
            r1.<init>((org.mozilla.javascript.ast.AstNode) r2, (boolean) r3)
            r8.autoInsertSemicolon(r1)
            goto L_0x006a
        L_0x0063:
            org.mozilla.javascript.ast.Label r2 = (org.mozilla.javascript.ast.Label) r2
            r8.recordLabel(r2, r4)
            goto L_0x003e
        L_0x0069:
            r1 = r6
        L_0x006a:
            r8.currentLabel = r4     // Catch:{ all -> 0x00dd }
            if (r1 != 0) goto L_0x00a6
            org.mozilla.javascript.ast.AstNode r1 = r8.statementHelper()     // Catch:{ all -> 0x00dd }
            int r2 = r8.peekToken()     // Catch:{ all -> 0x00dd }
            r3 = 162(0xa2, float:2.27E-43)
            if (r2 != r3) goto L_0x00a6
            int r2 = r1.getLineno()     // Catch:{ all -> 0x00dd }
            java.util.List<org.mozilla.javascript.ast.Comment> r3 = r8.scannedComments     // Catch:{ all -> 0x00dd }
            int r5 = r3.size()     // Catch:{ all -> 0x00dd }
            int r5 = r5 + -1
            java.lang.Object r3 = r3.get(r5)     // Catch:{ all -> 0x00dd }
            org.mozilla.javascript.ast.Comment r3 = (org.mozilla.javascript.ast.Comment) r3     // Catch:{ all -> 0x00dd }
            int r3 = r3.getLineno()     // Catch:{ all -> 0x00dd }
            if (r2 != r3) goto L_0x00a6
            java.util.List<org.mozilla.javascript.ast.Comment> r2 = r8.scannedComments     // Catch:{ all -> 0x00dd }
            int r3 = r2.size()     // Catch:{ all -> 0x00dd }
            int r3 = r3 + -1
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x00dd }
            org.mozilla.javascript.ast.AstNode r2 = (org.mozilla.javascript.ast.AstNode) r2     // Catch:{ all -> 0x00dd }
            r1.setInlineComment(r2)     // Catch:{ all -> 0x00dd }
            r8.consumeToken()     // Catch:{ all -> 0x00dd }
        L_0x00a6:
            r8.currentLabel = r6
            java.util.List r2 = r4.getLabels()
            java.util.Iterator r2 = r2.iterator()
        L_0x00b0:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00c6
            java.lang.Object r3 = r2.next()
            org.mozilla.javascript.ast.Label r3 = (org.mozilla.javascript.ast.Label) r3
            java.util.Map<java.lang.String, org.mozilla.javascript.ast.LabeledStatement> r5 = r8.labelSet
            java.lang.String r3 = r3.getName()
            r5.remove(r3)
            goto L_0x00b0
        L_0x00c6:
            org.mozilla.javascript.ast.AstNode r2 = r1.getParent()
            if (r2 != 0) goto L_0x00d2
            int r2 = getNodeEnd(r1)
            int r2 = r2 - r0
            goto L_0x00d6
        L_0x00d2:
            int r2 = getNodeEnd(r1)
        L_0x00d6:
            r4.setLength(r2)
            r4.setStatement(r1)
            return r4
        L_0x00dd:
            r0 = move-exception
            r8.currentLabel = r6
            java.util.List r1 = r4.getLabels()
            java.util.Iterator r1 = r1.iterator()
        L_0x00e8:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x00fe
            java.lang.Object r2 = r1.next()
            org.mozilla.javascript.ast.Label r2 = (org.mozilla.javascript.ast.Label) r2
            java.util.Map<java.lang.String, org.mozilla.javascript.ast.LabeledStatement> r3 = r8.labelSet
            java.lang.String r2 = r2.getName()
            r3.remove(r2)
            goto L_0x00e8
        L_0x00fe:
            throw r0
        L_0x00ff:
            java.lang.RuntimeException r0 = r8.codeBug()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.nameOrLabel():org.mozilla.javascript.ast.AstNode");
    }

    private int nextToken() throws IOException {
        int peekToken = peekToken();
        consumeToken();
        return peekToken;
    }

    private static int nodeEnd(AstNode astNode) {
        return astNode.getLength() + astNode.getPosition();
    }

    private static final boolean nowAllSet(int i, int i2, int i3) {
        return (i & i3) != i3 && (i2 & i3) == i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d5, code lost:
        if (r5 != 8) goto L_0x0107;
     */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0113 A[LOOP:0: B:5:0x0024->B:72:0x0113, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x011a A[EDGE_INSN: B:78:0x011a->B:73:0x011a ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.ObjectLiteral objectLiteral() throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            org.mozilla.javascript.TokenStream r1 = r0.ts
            int r2 = r1.tokenBeg
            int r1 = r1.lineno
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            boolean r4 = r0.inUseStrictDirective
            if (r4 == 0) goto L_0x001c
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.HashSet r6 = new java.util.HashSet
            r6.<init>()
            goto L_0x001e
        L_0x001c:
            r4 = 0
            r6 = 0
        L_0x001e:
            org.mozilla.javascript.ast.Comment r7 = r16.getAndResetJsDoc()
            r8 = -1
            r9 = -1
        L_0x0024:
            int r10 = r16.peekToken()
            org.mozilla.javascript.ast.Comment r11 = r16.getAndResetJsDoc()
            r12 = 162(0xa2, float:2.27E-43)
            if (r10 != r12) goto L_0x0037
            r16.consumeToken()
            int r10 = r0.peekUntilNonComment(r10)
        L_0x0037:
            r12 = 87
            if (r10 != r12) goto L_0x0043
            if (r9 == r8) goto L_0x0040
            r0.warnTrailingComma(r2, r3, r9)
        L_0x0040:
            r8 = 1
            goto L_0x011a
        L_0x0043:
            org.mozilla.javascript.ast.AstNode r9 = r16.objliteralProperty()
            java.lang.String r15 = "msg.bad.prop"
            r5 = 90
            if (r9 != 0) goto L_0x0054
            r0.reportError(r15)
            r5 = 1
        L_0x0051:
            r13 = 0
            goto L_0x00c2
        L_0x0054:
            org.mozilla.javascript.TokenStream r13 = r0.ts
            java.lang.String r13 = r13.getString()
            org.mozilla.javascript.TokenStream r8 = r0.ts
            int r8 = r8.tokenBeg
            r16.consumeToken()
            int r14 = r16.peekToken()
            if (r14 == r5) goto L_0x00b7
            r5 = 104(0x68, float:1.46E-43)
            if (r14 == r5) goto L_0x00b7
            if (r14 == r12) goto L_0x00b7
            r5 = 88
            if (r14 != r5) goto L_0x0074
            r5 = 8
            goto L_0x0091
        L_0x0074:
            int r5 = r9.getType()
            r10 = 39
            if (r5 != r10) goto L_0x0090
            java.lang.String r5 = "get"
            boolean r5 = r5.equals(r13)
            if (r5 == 0) goto L_0x0086
            r5 = 2
            goto L_0x0091
        L_0x0086:
            java.lang.String r5 = "set"
            boolean r5 = r5.equals(r13)
            if (r5 == 0) goto L_0x0090
            r5 = 4
            goto L_0x0091
        L_0x0090:
            r5 = 1
        L_0x0091:
            r10 = 2
            if (r5 == r10) goto L_0x0097
            r10 = 4
            if (r5 != r10) goto L_0x00a3
        L_0x0097:
            org.mozilla.javascript.ast.AstNode r9 = r16.objliteralProperty()
            if (r9 != 0) goto L_0x00a0
            r0.reportError(r15)
        L_0x00a0:
            r16.consumeToken()
        L_0x00a3:
            if (r9 != 0) goto L_0x00a6
            goto L_0x0051
        L_0x00a6:
            org.mozilla.javascript.TokenStream r10 = r0.ts
            java.lang.String r13 = r10.getString()
            org.mozilla.javascript.ast.ObjectProperty r8 = r0.methodDefinition(r8, r9, r5)
            r9.setJsDocNode(r11)
            r3.add(r8)
            goto L_0x00c2
        L_0x00b7:
            r9.setJsDocNode(r11)
            org.mozilla.javascript.ast.ObjectProperty r5 = r0.plainProperty(r9, r10)
            r3.add(r5)
            r5 = 1
        L_0x00c2:
            boolean r8 = r0.inUseStrictDirective
            if (r8 == 0) goto L_0x0107
            if (r13 == 0) goto L_0x0107
            java.lang.String r8 = "msg.dup.obj.lit.prop.strict"
            r9 = 1
            if (r5 == r9) goto L_0x00f2
            r9 = 2
            if (r5 == r9) goto L_0x00e5
            r9 = 4
            if (r5 == r9) goto L_0x00d8
            r9 = 8
            if (r5 == r9) goto L_0x00f2
            goto L_0x0107
        L_0x00d8:
            boolean r5 = r6.contains(r13)
            if (r5 == 0) goto L_0x00e1
            r0.addError((java.lang.String) r8, (java.lang.String) r13)
        L_0x00e1:
            r6.add(r13)
            goto L_0x0107
        L_0x00e5:
            boolean r5 = r4.contains(r13)
            if (r5 == 0) goto L_0x00ee
            r0.addError((java.lang.String) r8, (java.lang.String) r13)
        L_0x00ee:
            r4.add(r13)
            goto L_0x0107
        L_0x00f2:
            boolean r5 = r4.contains(r13)
            if (r5 != 0) goto L_0x00fe
            boolean r5 = r6.contains(r13)
            if (r5 == 0) goto L_0x0101
        L_0x00fe:
            r0.addError((java.lang.String) r8, (java.lang.String) r13)
        L_0x0101:
            r4.add(r13)
            r6.add(r13)
        L_0x0107:
            r16.getAndResetJsDoc()
            r5 = 90
            r8 = 1
            boolean r5 = r0.matchToken(r5, r8)
            if (r5 == 0) goto L_0x011a
            org.mozilla.javascript.TokenStream r5 = r0.ts
            int r9 = r5.tokenEnd
            r8 = -1
            goto L_0x0024
        L_0x011a:
            java.lang.String r4 = "msg.no.brace.prop"
            r0.mustMatchToken(r12, r4, r8)
            org.mozilla.javascript.ast.ObjectLiteral r4 = new org.mozilla.javascript.ast.ObjectLiteral
            org.mozilla.javascript.TokenStream r5 = r0.ts
            int r5 = r5.tokenEnd
            int r5 = r5 - r2
            r4.<init>(r2, r5)
            if (r7 == 0) goto L_0x012e
            r4.setJsDocNode(r7)
        L_0x012e:
            r4.setElements(r3)
            r4.setLineno(r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.objectLiteral():org.mozilla.javascript.ast.ObjectLiteral");
    }

    private AstNode objliteralProperty() throws IOException {
        switch (peekToken()) {
            case 39:
                return createNameNode();
            case 40:
                TokenStream tokenStream = this.ts;
                return new NumberLiteral(tokenStream.tokenBeg, tokenStream.getString(), this.ts.getNumber());
            case 41:
                return createStringLiteral();
            default:
                if (!this.compilerEnv.isReservedKeywordAsIdentifier() || !TokenStream.isKeyword(this.ts.getString(), this.compilerEnv.getLanguageVersion(), this.inUseStrictDirective)) {
                    return null;
                }
                return createNameNode();
        }
    }

    private AstNode orExpr() throws IOException {
        AstNode andExpr = andExpr();
        if (!matchToken(105, true)) {
            return andExpr;
        }
        return new InfixExpression(105, andExpr, orExpr(), this.ts.tokenBeg);
    }

    private AstNode parenExpr() throws IOException {
        AstNode astNode;
        boolean z = this.inForInit;
        this.inForInit = false;
        try {
            Comment andResetJsDoc = getAndResetJsDoc();
            TokenStream tokenStream = this.ts;
            int i = tokenStream.lineno;
            int i2 = tokenStream.tokenBeg;
            if (peekToken() == 89) {
                astNode = new EmptyExpression(i2);
            } else {
                astNode = expr();
            }
            if (peekToken() == 120) {
                return generatorExpression(astNode, i2);
            }
            mustMatchToken(89, "msg.no.paren", true);
            if (astNode.getType() != 129 || peekToken() == 165) {
                ParenthesizedExpression parenthesizedExpression = new ParenthesizedExpression(i2, this.ts.tokenEnd - i2, astNode);
                parenthesizedExpression.setLineno(i);
                if (andResetJsDoc == null) {
                    andResetJsDoc = getAndResetJsDoc();
                }
                if (andResetJsDoc != null) {
                    parenthesizedExpression.setJsDocNode(andResetJsDoc);
                }
                this.inForInit = z;
                return parenthesizedExpression;
            }
            reportError("msg.syntax");
            ErrorNode makeErrorNode = makeErrorNode();
            this.inForInit = z;
            return makeErrorNode;
        } finally {
            this.inForInit = z;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042 A[SYNTHETIC, Splitter:B:13:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0068 A[Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.AstNode parseFunctionBody(int r11, org.mozilla.javascript.ast.FunctionNode r12) throws java.io.IOException {
        /*
            r10 = this;
            r0 = 86
            r1 = 1
            boolean r0 = r10.matchToken(r0, r1)
            r2 = 4
            r3 = 0
            if (r0 != 0) goto L_0x001f
            org.mozilla.javascript.CompilerEnvirons r0 = r10.compilerEnv
            int r0 = r0.getLanguageVersion()
            r4 = 180(0xb4, float:2.52E-43)
            if (r0 >= r4) goto L_0x001d
            if (r11 == r2) goto L_0x001d
            java.lang.String r0 = "msg.no.brace.body"
            r10.reportError(r0)
            goto L_0x001f
        L_0x001d:
            r0 = 1
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            if (r11 != r2) goto L_0x0024
            r11 = 1
            goto L_0x0025
        L_0x0024:
            r11 = 0
        L_0x0025:
            int r2 = r10.nestingOfFunction
            int r2 = r2 + r1
            r10.nestingOfFunction = r2
            org.mozilla.javascript.TokenStream r2 = r10.ts
            int r2 = r2.tokenBeg
            org.mozilla.javascript.ast.Block r4 = new org.mozilla.javascript.ast.Block
            r4.<init>(r2)
            boolean r5 = r10.inUseStrictDirective
            r10.inUseStrictDirective = r3
            org.mozilla.javascript.TokenStream r6 = r10.ts
            int r6 = r6.lineno
            r4.setLineno(r6)
            r6 = 87
            if (r0 == 0) goto L_0x0068
            org.mozilla.javascript.ast.AstNode r12 = r10.assignExpr()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            org.mozilla.javascript.ast.ReturnStatement r3 = new org.mozilla.javascript.ast.ReturnStatement     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            int r7 = r12.getPosition()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            int r8 = r12.getLength()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            r3.<init>(r7, r8, r12)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            java.lang.Boolean r12 = java.lang.Boolean.TRUE     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            r7 = 25
            r3.putProp(r7, r12)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            r4.putProp(r7, r12)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            if (r11 == 0) goto L_0x0064
            r11 = 27
            r3.putProp(r11, r12)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
        L_0x0064:
            r4.addStatement(r3)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            goto L_0x00c2
        L_0x0068:
            r11 = 1
        L_0x0069:
            int r7 = r10.peekToken()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            r8 = -1
            if (r7 == r8) goto L_0x00c2
            if (r7 == 0) goto L_0x00c2
            if (r7 == r6) goto L_0x00c2
            r8 = 110(0x6e, float:1.54E-43)
            if (r7 == r8) goto L_0x00ae
            r8 = 162(0xa2, float:2.27E-43)
            if (r7 == r8) goto L_0x009d
            org.mozilla.javascript.ast.AstNode r7 = r10.statement()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            if (r11 == 0) goto L_0x00b5
            java.lang.String r8 = getDirective(r7)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            if (r8 != 0) goto L_0x008a
            r11 = 0
            goto L_0x00b5
        L_0x008a:
            java.lang.String r9 = "use strict"
            boolean r8 = r8.equals(r9)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            if (r8 == 0) goto L_0x00b5
            r10.inUseStrictDirective = r1     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            r12.setInStrictMode(r1)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            if (r5 != 0) goto L_0x00b5
            r10.setRequiresActivation()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            goto L_0x00b5
        L_0x009d:
            r10.consumeToken()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            java.util.List<org.mozilla.javascript.ast.Comment> r7 = r10.scannedComments     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            int r8 = r7.size()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            int r8 = r8 - r1
            java.lang.Object r7 = r7.get(r8)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            org.mozilla.javascript.ast.AstNode r7 = (org.mozilla.javascript.ast.AstNode) r7     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            goto L_0x00b5
        L_0x00ae:
            r10.consumeToken()     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            org.mozilla.javascript.ast.FunctionNode r7 = r10.function(r1)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
        L_0x00b5:
            r4.addStatement(r7)     // Catch:{ ParserException -> 0x00c2, all -> 0x00b9 }
            goto L_0x0069
        L_0x00b9:
            r11 = move-exception
            int r12 = r10.nestingOfFunction
            int r12 = r12 - r1
            r10.nestingOfFunction = r12
            r10.inUseStrictDirective = r5
            throw r11
        L_0x00c2:
            int r11 = r10.nestingOfFunction
            int r11 = r11 - r1
            r10.nestingOfFunction = r11
            r10.inUseStrictDirective = r5
            org.mozilla.javascript.TokenStream r11 = r10.ts
            int r11 = r11.tokenEnd
            r10.getAndResetJsDoc()
            if (r0 != 0) goto L_0x00de
            java.lang.String r12 = "msg.no.brace.after.body"
            boolean r12 = r10.mustMatchToken(r6, r12, r1)
            if (r12 == 0) goto L_0x00de
            org.mozilla.javascript.TokenStream r11 = r10.ts
            int r11 = r11.tokenEnd
        L_0x00de:
            int r11 = r11 - r2
            r4.setLength(r11)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.parseFunctionBody(int, org.mozilla.javascript.ast.FunctionNode):org.mozilla.javascript.ast.AstNode");
    }

    private void parseFunctionParams(FunctionNode functionNode) throws IOException {
        if (matchToken(89, true)) {
            functionNode.setRp(this.ts.tokenBeg - functionNode.getPosition());
            return;
        }
        HashSet hashSet = new HashSet();
        HashMap hashMap = null;
        do {
            int peekToken = peekToken();
            if (peekToken == 84 || peekToken == 86) {
                AstNode destructuringPrimaryExpr = destructuringPrimaryExpr();
                markDestructuring(destructuringPrimaryExpr);
                functionNode.addParam(destructuringPrimaryExpr);
                if (hashMap == null) {
                    hashMap = new HashMap();
                }
                String nextTempName = this.currentScriptOrFn.getNextTempName();
                defineSymbol(88, nextTempName, false);
                hashMap.put(nextTempName, destructuringPrimaryExpr);
            } else if (mustMatchToken(39, "msg.no.parm", true)) {
                Name createNameNode = createNameNode();
                Comment andResetJsDoc = getAndResetJsDoc();
                if (andResetJsDoc != null) {
                    createNameNode.setJsDocNode(andResetJsDoc);
                }
                functionNode.addParam(createNameNode);
                String string = this.ts.getString();
                defineSymbol(88, string);
                if (this.inUseStrictDirective) {
                    if ("eval".equals(string) || "arguments".equals(string)) {
                        reportError("msg.bad.id.strict", string);
                    }
                    if (hashSet.contains(string)) {
                        addError("msg.dup.param.strict", string);
                    }
                    hashSet.add(string);
                }
            } else {
                functionNode.addParam(makeErrorNode());
            }
        } while (matchToken(90, true));
        if (hashMap != null) {
            Node node = new Node(90);
            for (Map.Entry entry : hashMap.entrySet()) {
                node.addChildToBack(createDestructuringAssignment(123, (Node) entry.getValue(), createName((String) entry.getKey())));
            }
            functionNode.putProp(23, node);
        }
        if (mustMatchToken(89, "msg.no.paren.after.parms", true)) {
            functionNode.setRp(this.ts.tokenBeg - functionNode.getPosition());
        }
    }

    private int peekFlaggedToken() throws IOException {
        peekToken();
        return this.currentFlaggedToken;
    }

    private int peekToken() throws IOException {
        if (this.currentFlaggedToken != 0) {
            return this.currentToken;
        }
        int lineno = this.ts.getLineno();
        int token = this.ts.getToken();
        int i = 0;
        boolean z = false;
        while (true) {
            if (token != 1 && token != 162) {
                break;
            } else if (token == 1) {
                lineno++;
                token = this.ts.getToken();
                z = true;
            } else if (this.compilerEnv.isRecordingComments()) {
                recordComment(lineno, this.ts.getAndResetCurrentComment());
                break;
            } else {
                token = this.ts.getToken();
            }
        }
        this.currentToken = token;
        if (z) {
            i = 65536;
        }
        this.currentFlaggedToken = token | i;
        return token;
    }

    private int peekTokenOrEOL() throws IOException {
        int peekToken = peekToken();
        if ((this.currentFlaggedToken & 65536) != 0) {
            return 1;
        }
        return peekToken;
    }

    private int peekUntilNonComment(int i) throws IOException {
        while (i == 162) {
            consumeToken();
            i = peekToken();
        }
        return i;
    }

    private ObjectProperty plainProperty(AstNode astNode, int i) throws IOException {
        int peekToken = peekToken();
        if ((peekToken == 90 || peekToken == 87) && i == 39 && this.compilerEnv.getLanguageVersion() >= 180) {
            if (!this.inDestructuringAssignment) {
                reportError("msg.bad.object.init");
            }
            Name name = new Name(astNode.getPosition(), astNode.getString());
            ObjectProperty objectProperty = new ObjectProperty();
            objectProperty.putProp(26, Boolean.TRUE);
            objectProperty.setLeftAndRight(astNode, name);
            return objectProperty;
        }
        mustMatchToken(104, "msg.no.colon.prop", true);
        ObjectProperty objectProperty2 = new ObjectProperty();
        objectProperty2.setOperatorPosition(this.ts.tokenBeg);
        objectProperty2.setLeftAndRight(astNode, assignExpr());
        return objectProperty2;
    }

    private AstNode primaryExpr() throws IOException {
        int peekFlaggedToken = peekFlaggedToken();
        int i = 65535 & peekFlaggedToken;
        if (i == -1) {
            consumeToken();
        } else if (i != 0) {
            if (i != 24) {
                if (i == 84) {
                    consumeToken();
                    return arrayLiteral();
                } else if (i == 86) {
                    consumeToken();
                    return objectLiteral();
                } else if (i == 88) {
                    consumeToken();
                    return parenExpr();
                } else if (i != 101) {
                    if (i == 110) {
                        consumeToken();
                        return function(2);
                    } else if (i == 128) {
                        consumeToken();
                        reportError("msg.reserved.id", this.ts.getString());
                    } else if (i == 148) {
                        consumeToken();
                        mustHaveXML();
                        return attributeAccess();
                    } else if (i != 154) {
                        switch (i) {
                            case 39:
                                consumeToken();
                                return name(peekFlaggedToken, i);
                            case 40:
                                consumeToken();
                                String string = this.ts.getString();
                                if (this.inUseStrictDirective && this.ts.isNumberOldOctal()) {
                                    reportError("msg.no.old.octal.strict");
                                }
                                if (this.ts.isNumberBinary()) {
                                    string = y2.i("0b", string);
                                }
                                if (this.ts.isNumberOldOctal()) {
                                    string = y2.i("0", string);
                                }
                                if (this.ts.isNumberOctal()) {
                                    string = y2.i("0o", string);
                                }
                                if (this.ts.isNumberHex()) {
                                    string = y2.i("0x", string);
                                }
                                TokenStream tokenStream = this.ts;
                                return new NumberLiteral(tokenStream.tokenBeg, string, tokenStream.getNumber());
                            case 41:
                                consumeToken();
                                return createStringLiteral();
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                                consumeToken();
                                TokenStream tokenStream2 = this.ts;
                                int i2 = tokenStream2.tokenBeg;
                                return new KeywordLiteral(i2, tokenStream2.tokenEnd - i2, i);
                            default:
                                consumeToken();
                                reportError("msg.syntax");
                                break;
                        }
                    } else {
                        consumeToken();
                        return let(false, this.ts.tokenBeg);
                    }
                }
            }
            consumeToken();
            this.ts.readRegExp(i);
            TokenStream tokenStream3 = this.ts;
            int i3 = tokenStream3.tokenBeg;
            RegExpLiteral regExpLiteral = new RegExpLiteral(i3, tokenStream3.tokenEnd - i3);
            regExpLiteral.setValue(this.ts.getString());
            regExpLiteral.setFlags(this.ts.readAndClearRegExpFlags());
            return regExpLiteral;
        } else {
            consumeToken();
            reportError("msg.unexpected.eof");
        }
        consumeToken();
        return makeErrorNode();
    }

    private AstNode propertyAccess(int i, AstNode astNode) throws IOException {
        int i2;
        AstNode astNode2;
        InfixExpression infixExpression;
        String keywordToName;
        if (astNode == null) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i3 = tokenStream.lineno;
        int i4 = tokenStream.tokenBeg;
        consumeToken();
        if (i == 144) {
            mustHaveXML();
            i2 = 4;
        } else {
            i2 = 0;
        }
        if (!this.compilerEnv.isXmlAvailable()) {
            if (nextToken() != 39 && (!this.compilerEnv.isReservedKeywordAsIdentifier() || !TokenStream.isKeyword(this.ts.getString(), this.compilerEnv.getLanguageVersion(), this.inUseStrictDirective))) {
                reportError("msg.no.name.after.dot");
            }
            PropertyGet propertyGet = new PropertyGet(astNode, createNameNode(true, 33), i4);
            propertyGet.setLineno(i3);
            return propertyGet;
        }
        int nextToken = nextToken();
        if (nextToken == 23) {
            TokenStream tokenStream2 = this.ts;
            saveNameTokenData(tokenStream2.tokenBeg, "*", tokenStream2.lineno);
            astNode2 = propertyName(-1, i2);
        } else if (nextToken == 39) {
            astNode2 = propertyName(-1, i2);
        } else if (nextToken == 50) {
            TokenStream tokenStream3 = this.ts;
            saveNameTokenData(tokenStream3.tokenBeg, "throw", tokenStream3.lineno);
            astNode2 = propertyName(-1, i2);
        } else if (nextToken == 128) {
            String string = this.ts.getString();
            TokenStream tokenStream4 = this.ts;
            saveNameTokenData(tokenStream4.tokenBeg, string, tokenStream4.lineno);
            astNode2 = propertyName(-1, i2);
        } else if (nextToken == 148) {
            astNode2 = attributeAccess();
        } else if (!this.compilerEnv.isReservedKeywordAsIdentifier() || (keywordToName = Token.keywordToName(nextToken)) == null) {
            reportError("msg.no.name.after.dot");
            return makeErrorNode();
        } else {
            TokenStream tokenStream5 = this.ts;
            saveNameTokenData(tokenStream5.tokenBeg, keywordToName, tokenStream5.lineno);
            astNode2 = propertyName(-1, i2);
        }
        boolean z = astNode2 instanceof XmlRef;
        if (z) {
            infixExpression = new XmlMemberGet();
        } else {
            infixExpression = new PropertyGet();
        }
        if (z && i == 109) {
            infixExpression.setType(109);
        }
        int position = astNode.getPosition();
        infixExpression.setPosition(position);
        infixExpression.setLength(getNodeEnd(astNode2) - position);
        infixExpression.setOperatorPosition(i4 - position);
        infixExpression.setLineno(astNode.getLineno());
        infixExpression.setLeft(astNode);
        infixExpression.setRight(astNode2);
        return infixExpression;
    }

    private AstNode propertyName(int i, int i2) throws IOException {
        int i3;
        Name name;
        int i4;
        if (i != -1) {
            i3 = i;
        } else {
            i3 = this.ts.tokenBeg;
        }
        int i5 = this.ts.lineno;
        Name createNameNode = createNameNode(true, this.currentToken);
        if (matchToken(Token.COLONCOLON, true)) {
            i4 = this.ts.tokenBeg;
            int nextToken = nextToken();
            if (nextToken == 23) {
                TokenStream tokenStream = this.ts;
                saveNameTokenData(tokenStream.tokenBeg, "*", tokenStream.lineno);
                name = createNameNode(false, -1);
            } else if (nextToken == 39) {
                name = createNameNode();
            } else if (nextToken == 84) {
                return xmlElemRef(i, createNameNode, i4);
            } else {
                reportError("msg.no.name.after.coloncolon");
                return makeErrorNode();
            }
        } else {
            name = createNameNode;
            createNameNode = null;
            i4 = -1;
        }
        if (createNameNode == null && i2 == 0 && i == -1) {
            return name;
        }
        XmlPropRef xmlPropRef = new XmlPropRef(i3, getNodeEnd(name) - i3);
        xmlPropRef.setAtPos(i);
        xmlPropRef.setNamespace(createNameNode);
        xmlPropRef.setColonPos(i4);
        xmlPropRef.setPropName(name);
        xmlPropRef.setLineno(i5);
        return xmlPropRef;
    }

    private void recordComment(int i, String str) {
        if (this.scannedComments == null) {
            this.scannedComments = new ArrayList();
        }
        TokenStream tokenStream = this.ts;
        Comment comment = new Comment(tokenStream.tokenBeg, tokenStream.getTokenLength(), this.ts.commentType, str);
        if (this.ts.commentType == Token.CommentType.JSDOC && this.compilerEnv.isRecordingLocalJsDocComments()) {
            TokenStream tokenStream2 = this.ts;
            Comment comment2 = new Comment(tokenStream2.tokenBeg, tokenStream2.getTokenLength(), this.ts.commentType, str);
            this.currentJsDocComment = comment2;
            comment2.setLineno(i);
        }
        comment.setLineno(i);
        this.scannedComments.add(comment);
    }

    private void recordLabel(Label label, LabeledStatement labeledStatement) throws IOException {
        if (peekToken() != 104) {
            codeBug();
        }
        consumeToken();
        String name = label.getName();
        Map<String, LabeledStatement> map = this.labelSet;
        if (map == null) {
            this.labelSet = new HashMap();
        } else {
            LabeledStatement labeledStatement2 = map.get(name);
            if (labeledStatement2 != null) {
                if (this.compilerEnv.isIdeMode()) {
                    Label labelByName = labeledStatement2.getLabelByName(name);
                    reportError("msg.dup.label", labelByName.getAbsolutePosition(), labelByName.getLength());
                }
                reportError("msg.dup.label", label.getPosition(), label.getLength());
            }
        }
        labeledStatement.addLabel(label);
        this.labelSet.put(name, labeledStatement);
    }

    private AstNode relExpr() throws IOException {
        AstNode shiftExpr = shiftExpr();
        while (true) {
            int peekToken = peekToken();
            int i = this.ts.tokenBeg;
            if (peekToken != 52) {
                if (peekToken != 53) {
                    switch (peekToken) {
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            break;
                    }
                } else {
                    continue;
                }
                consumeToken();
                shiftExpr = new InfixExpression(peekToken, shiftExpr, shiftExpr(), i);
            } else if (!this.inForInit) {
                consumeToken();
                shiftExpr = new InfixExpression(peekToken, shiftExpr, shiftExpr(), i);
            }
        }
        return shiftExpr;
    }

    private AstNode returnOrYield(int i, boolean z) throws IOException {
        boolean z2;
        int i2;
        AstNode astNode;
        AstNode astNode2;
        String str;
        int i3 = 4;
        if (!insideFunction()) {
            if (i == 4) {
                str = "msg.bad.return";
            } else {
                str = "msg.bad.yield";
            }
            reportError(str);
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i4 = tokenStream.lineno;
        int i5 = tokenStream.tokenBeg;
        int i6 = tokenStream.tokenEnd;
        if (i == 73 && this.compilerEnv.getLanguageVersion() >= 200 && peekToken() == 23) {
            consumeToken();
            z2 = true;
        } else {
            z2 = false;
        }
        int peekTokenOrEOL = peekTokenOrEOL();
        if (peekTokenOrEOL == -1 || peekTokenOrEOL == 0 || peekTokenOrEOL == 1 || (peekTokenOrEOL == 73 ? this.compilerEnv.getLanguageVersion() < 200 : peekTokenOrEOL == 83 || peekTokenOrEOL == 85 || peekTokenOrEOL == 87 || peekTokenOrEOL == 89)) {
            i2 = i6;
            astNode = null;
        } else {
            astNode = expr();
            i2 = getNodeEnd(astNode);
        }
        int i7 = this.endFlags;
        if (i == 4) {
            if (astNode == null) {
                i3 = 2;
            }
            this.endFlags = i7 | i3;
            int i8 = i2 - i5;
            astNode2 = new ReturnStatement(i5, i8, astNode);
            if (nowAllSet(i7, this.endFlags, 6)) {
                addStrictWarning("msg.return.inconsistent", "", i5, i8);
            }
        } else {
            if (!insideFunction()) {
                reportError("msg.bad.yield");
            }
            this.endFlags |= 8;
            astNode2 = new Yield(i5, i2 - i5, astNode, z2);
            setRequiresActivation();
            setIsGenerator();
            if (!z) {
                astNode2 = new ExpressionStatement(astNode2);
            }
        }
        if (insideFunction() && nowAllSet(i7, this.endFlags, 12) && !((FunctionNode) this.currentScriptOrFn).isES6Generator()) {
            Name functionName = ((FunctionNode) this.currentScriptOrFn).getFunctionName();
            if (functionName == null || functionName.length() == 0) {
                addError("msg.anon.generator.returns", "");
            } else {
                addError("msg.generator.returns", functionName.getIdentifier());
            }
        }
        astNode2.setLineno(i4);
        return astNode2;
    }

    private void saveNameTokenData(int i, String str, int i2) {
        this.prevNameTokenStart = i;
        this.prevNameTokenString = str;
        this.prevNameTokenLineno = i2;
    }

    private AstNode shiftExpr() throws IOException {
        AstNode addExpr = addExpr();
        while (true) {
            int peekToken = peekToken();
            int i = this.ts.tokenBeg;
            switch (peekToken) {
                case 18:
                case 19:
                case 20:
                    consumeToken();
                    addExpr = new InfixExpression(peekToken, addExpr, addExpr(), i);
                default:
                    return addExpr;
            }
        }
    }

    private AstNode statement() throws IOException {
        int peekTokenOrEOL;
        String str;
        int i = this.ts.tokenBeg;
        try {
            AstNode statementHelper = statementHelper();
            if (statementHelper != null) {
                if (this.compilerEnv.isStrictMode() && !statementHelper.hasSideEffects()) {
                    int position = statementHelper.getPosition();
                    int max = Math.max(position, lineBeginningFor(position));
                    if (statementHelper instanceof EmptyStatement) {
                        str = "msg.extra.trailing.semi";
                    } else {
                        str = "msg.no.side.effects";
                    }
                    addStrictWarning(str, "", max, nodeEnd(statementHelper) - max);
                }
                if (peekToken() == 162) {
                    int lineno = statementHelper.getLineno();
                    List<Comment> list = this.scannedComments;
                    if (lineno == list.get(list.size() - 1).getLineno()) {
                        List<Comment> list2 = this.scannedComments;
                        statementHelper.setInlineComment(list2.get(list2.size() - 1));
                        consumeToken();
                    }
                }
                return statementHelper;
            }
        } catch (ParserException unused) {
        }
        do {
            peekTokenOrEOL = peekTokenOrEOL();
            consumeToken();
            if (peekTokenOrEOL == -1 || peekTokenOrEOL == 0 || peekTokenOrEOL == 1 || peekTokenOrEOL == 83) {
            }
            peekTokenOrEOL = peekTokenOrEOL();
            consumeToken();
            break;
        } while (peekTokenOrEOL == 83);
        return new EmptyStatement(i, this.ts.tokenBeg - i);
    }

    private AstNode statementHelper() throws IOException {
        AstNode astNode;
        LabeledStatement labeledStatement = this.currentLabel;
        if (!(labeledStatement == null || labeledStatement.getStatement() == null)) {
            this.currentLabel = null;
        }
        int peekToken = peekToken();
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        if (peekToken != -1) {
            if (peekToken != 4) {
                if (peekToken == 39) {
                    astNode = nameOrLabel();
                    if (!(astNode instanceof ExpressionStatement)) {
                        return astNode;
                    }
                } else if (peekToken == 50) {
                    astNode = throwStatement();
                } else if (peekToken != 73) {
                    if (peekToken == 86) {
                        return block();
                    }
                    if (peekToken == 110) {
                        consumeToken();
                        return function(3);
                    } else if (peekToken == 113) {
                        return ifStatement();
                    } else {
                        if (peekToken == 115) {
                            return switchStatement();
                        }
                        if (peekToken == 82) {
                            return tryStatement();
                        }
                        if (peekToken == 83) {
                            consumeToken();
                            int i2 = this.ts.tokenBeg;
                            EmptyStatement emptyStatement = new EmptyStatement(i2, this.ts.tokenEnd - i2);
                            emptyStatement.setLineno(this.ts.lineno);
                            return emptyStatement;
                        } else if (peekToken != 154) {
                            if (peekToken != 155) {
                                if (peekToken == 161) {
                                    consumeToken();
                                    TokenStream tokenStream2 = this.ts;
                                    int i3 = tokenStream2.tokenBeg;
                                    astNode = new KeywordLiteral(i3, tokenStream2.tokenEnd - i3, peekToken);
                                    astNode.setLineno(this.ts.lineno);
                                } else if (peekToken != 162) {
                                    switch (peekToken) {
                                        case 117:
                                            astNode = defaultXmlNamespace();
                                            break;
                                        case 118:
                                            return whileLoop();
                                        case 119:
                                            return doLoop();
                                        case 120:
                                            return forLoop();
                                        case 121:
                                            astNode = breakStatement();
                                            break;
                                        case 122:
                                            astNode = continueStatement();
                                            break;
                                        case 123:
                                            break;
                                        case 124:
                                            if (this.inUseStrictDirective) {
                                                reportError("msg.no.with.strict");
                                            }
                                            return withStatement();
                                        default:
                                            int i4 = tokenStream.lineno;
                                            astNode = new ExpressionStatement(expr(), true ^ insideFunction());
                                            astNode.setLineno(i4);
                                            break;
                                    }
                                } else {
                                    List<Comment> list = this.scannedComments;
                                    return list.get(list.size() - 1);
                                }
                            }
                            consumeToken();
                            TokenStream tokenStream3 = this.ts;
                            int i5 = tokenStream3.lineno;
                            VariableDeclaration variables = variables(this.currentToken, tokenStream3.tokenBeg, true);
                            variables.setLineno(i5);
                            astNode = variables;
                        } else {
                            astNode = letStatement();
                            if (!(astNode instanceof VariableDeclaration) || peekToken() != 83) {
                                return astNode;
                            }
                        }
                    }
                }
                autoInsertSemicolon(astNode);
                return astNode;
            }
            astNode = returnOrYield(peekToken, false);
            autoInsertSemicolon(astNode);
            return astNode;
        }
        consumeToken();
        return makeErrorNode();
    }

    private AstNode statements(AstNode astNode) throws IOException {
        if (this.currentToken != 86 && !this.compilerEnv.isIdeMode()) {
            codeBug();
        }
        int i = this.ts.tokenBeg;
        if (astNode == null) {
            astNode = new Block(i);
        }
        astNode.setLineno(this.ts.lineno);
        while (true) {
            int peekToken = peekToken();
            if (peekToken <= 0 || peekToken == 87) {
                astNode.setLength(this.ts.tokenBeg - i);
            } else {
                astNode.addChild(statement());
            }
        }
        astNode.setLength(this.ts.tokenBeg - i);
        return astNode;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        reportError("msg.bad.switch");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.SwitchStatement switchStatement() throws java.io.IOException {
        /*
            r14 = this;
            int r0 = r14.currentToken
            r1 = 115(0x73, float:1.61E-43)
            if (r0 == r1) goto L_0x0009
            r14.codeBug()
        L_0x0009:
            r14.consumeToken()
            org.mozilla.javascript.TokenStream r0 = r14.ts
            int r0 = r0.tokenBeg
            org.mozilla.javascript.ast.SwitchStatement r1 = new org.mozilla.javascript.ast.SwitchStatement
            r1.<init>(r0)
            r2 = 88
            java.lang.String r3 = "msg.no.paren.switch"
            r4 = 1
            boolean r2 = r14.mustMatchToken(r2, r3, r4)
            if (r2 == 0) goto L_0x0028
            org.mozilla.javascript.TokenStream r2 = r14.ts
            int r2 = r2.tokenBeg
            int r2 = r2 - r0
            r1.setLp(r2)
        L_0x0028:
            org.mozilla.javascript.TokenStream r2 = r14.ts
            int r2 = r2.lineno
            r1.setLineno(r2)
            org.mozilla.javascript.ast.AstNode r2 = r14.expr()
            r1.setExpression(r2)
            r14.enterSwitch(r1)
            java.lang.String r2 = "msg.no.paren.after.switch"
            r3 = 89
            boolean r2 = r14.mustMatchToken(r3, r2, r4)     // Catch:{ all -> 0x0102 }
            if (r2 == 0) goto L_0x004b
            org.mozilla.javascript.TokenStream r2 = r14.ts     // Catch:{ all -> 0x0102 }
            int r2 = r2.tokenBeg     // Catch:{ all -> 0x0102 }
            int r2 = r2 - r0
            r1.setRp(r2)     // Catch:{ all -> 0x0102 }
        L_0x004b:
            java.lang.String r2 = "msg.no.brace.switch"
            r3 = 86
            r14.mustMatchToken(r3, r2, r4)     // Catch:{ all -> 0x0102 }
            r2 = 0
        L_0x0053:
            int r3 = r14.nextToken()     // Catch:{ all -> 0x0102 }
            org.mozilla.javascript.TokenStream r5 = r14.ts     // Catch:{ all -> 0x0102 }
            int r6 = r5.tokenBeg     // Catch:{ all -> 0x0102 }
            int r7 = r5.lineno     // Catch:{ all -> 0x0102 }
            r8 = 87
            if (r3 == r8) goto L_0x00f8
            r5 = 162(0xa2, float:2.27E-43)
            if (r3 == r5) goto L_0x00e6
            java.lang.String r9 = "msg.no.colon.case"
            r10 = 104(0x68, float:1.46E-43)
            r11 = 117(0x75, float:1.64E-43)
            r12 = 116(0x74, float:1.63E-43)
            if (r3 == r12) goto L_0x0085
            if (r3 == r11) goto L_0x0078
            java.lang.String r0 = "msg.bad.switch"
            r14.reportError(r0)     // Catch:{ all -> 0x0102 }
            goto L_0x00fe
        L_0x0078:
            if (r2 == 0) goto L_0x007f
            java.lang.String r2 = "msg.double.switch.default"
            r14.reportError(r2)     // Catch:{ all -> 0x0102 }
        L_0x007f:
            r14.mustMatchToken(r10, r9, r4)     // Catch:{ all -> 0x0102 }
            r2 = 0
            r3 = 1
            goto L_0x008f
        L_0x0085:
            org.mozilla.javascript.ast.AstNode r3 = r14.expr()     // Catch:{ all -> 0x0102 }
            r14.mustMatchToken(r10, r9, r4)     // Catch:{ all -> 0x0102 }
            r13 = r3
            r3 = r2
            r2 = r13
        L_0x008f:
            org.mozilla.javascript.ast.SwitchCase r9 = new org.mozilla.javascript.ast.SwitchCase     // Catch:{ all -> 0x0102 }
            r9.<init>(r6)     // Catch:{ all -> 0x0102 }
            r9.setExpression(r2)     // Catch:{ all -> 0x0102 }
            org.mozilla.javascript.TokenStream r2 = r14.ts     // Catch:{ all -> 0x0102 }
            int r2 = r2.tokenEnd     // Catch:{ all -> 0x0102 }
            int r2 = r2 - r0
            r9.setLength(r2)     // Catch:{ all -> 0x0102 }
            r9.setLineno(r7)     // Catch:{ all -> 0x0102 }
        L_0x00a2:
            int r2 = r14.peekToken()     // Catch:{ all -> 0x0102 }
            if (r2 == r8) goto L_0x00e0
            if (r2 == r12) goto L_0x00e0
            if (r2 == r11) goto L_0x00e0
            if (r2 == 0) goto L_0x00e0
            if (r2 != r5) goto L_0x00d8
            java.util.List<org.mozilla.javascript.ast.Comment> r2 = r14.scannedComments     // Catch:{ all -> 0x0102 }
            int r6 = r2.size()     // Catch:{ all -> 0x0102 }
            int r6 = r6 - r4
            java.lang.Object r2 = r2.get(r6)     // Catch:{ all -> 0x0102 }
            org.mozilla.javascript.ast.Comment r2 = (org.mozilla.javascript.ast.Comment) r2     // Catch:{ all -> 0x0102 }
            org.mozilla.javascript.ast.AstNode r6 = r9.getInlineComment()     // Catch:{ all -> 0x0102 }
            if (r6 != 0) goto L_0x00d1
            int r6 = r2.getLineno()     // Catch:{ all -> 0x0102 }
            int r7 = r9.getLineno()     // Catch:{ all -> 0x0102 }
            if (r6 != r7) goto L_0x00d1
            r9.setInlineComment(r2)     // Catch:{ all -> 0x0102 }
            goto L_0x00d4
        L_0x00d1:
            r9.addStatement(r2)     // Catch:{ all -> 0x0102 }
        L_0x00d4:
            r14.consumeToken()     // Catch:{ all -> 0x0102 }
            goto L_0x00a2
        L_0x00d8:
            org.mozilla.javascript.ast.AstNode r2 = r14.statement()     // Catch:{ all -> 0x0102 }
            r9.addStatement(r2)     // Catch:{ all -> 0x0102 }
            goto L_0x00a2
        L_0x00e0:
            r1.addCase(r9)     // Catch:{ all -> 0x0102 }
            r2 = r3
            goto L_0x0053
        L_0x00e6:
            java.util.List<org.mozilla.javascript.ast.Comment> r3 = r14.scannedComments     // Catch:{ all -> 0x0102 }
            int r5 = r3.size()     // Catch:{ all -> 0x0102 }
            int r5 = r5 - r4
            java.lang.Object r3 = r3.get(r5)     // Catch:{ all -> 0x0102 }
            org.mozilla.javascript.ast.AstNode r3 = (org.mozilla.javascript.ast.AstNode) r3     // Catch:{ all -> 0x0102 }
            r1.addChild(r3)     // Catch:{ all -> 0x0102 }
            goto L_0x0053
        L_0x00f8:
            int r2 = r5.tokenEnd     // Catch:{ all -> 0x0102 }
            int r2 = r2 - r0
            r1.setLength(r2)     // Catch:{ all -> 0x0102 }
        L_0x00fe:
            r14.exitSwitch()
            return r1
        L_0x0102:
            r0 = move-exception
            r14.exitSwitch()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.switchStatement():org.mozilla.javascript.ast.SwitchStatement");
    }

    private ThrowStatement throwStatement() throws IOException {
        if (this.currentToken != 50) {
            codeBug();
        }
        consumeToken();
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        int i2 = tokenStream.lineno;
        if (peekTokenOrEOL() == 1) {
            reportError("msg.bad.throw.eol");
        }
        ThrowStatement throwStatement = new ThrowStatement(i, expr());
        throwStatement.setLineno(i2);
        return throwStatement;
    }

    private TryStatement tryStatement() throws IOException {
        Comment comment;
        ArrayList arrayList;
        int i;
        AstNode astNode;
        int i2;
        int i3;
        boolean z;
        AstNode astNode2;
        int i4;
        int i5;
        if (this.currentToken != 82) {
            codeBug();
        }
        consumeToken();
        Comment andResetJsDoc = getAndResetJsDoc();
        TokenStream tokenStream = this.ts;
        int i6 = tokenStream.tokenBeg;
        int i7 = tokenStream.lineno;
        TryStatement tryStatement = new TryStatement(i6);
        int peekToken = peekToken();
        if (peekToken == 162) {
            List<Comment> list = this.scannedComments;
            tryStatement.setInlineComment(list.get(list.size() - 1));
            consumeToken();
            peekToken = peekToken();
        }
        if (peekToken != 86) {
            reportError("msg.no.brace.try");
        }
        AstNode nextStatementAfterInlineComments = getNextStatementAfterInlineComments(tryStatement);
        int nodeEnd = getNodeEnd(nextStatementAfterInlineComments);
        int peekToken2 = peekToken();
        if (peekToken2 == 125) {
            boolean z2 = false;
            arrayList = null;
            for (int i8 = Token.CATCH; matchToken(i8, true); i8 = Token.CATCH) {
                int i9 = this.ts.lineno;
                if (z2) {
                    reportError("msg.catch.unreachable");
                }
                int i10 = this.ts.tokenBeg;
                if (mustMatchToken(88, "msg.no.paren.catch", true)) {
                    i3 = this.ts.tokenBeg;
                } else {
                    i3 = -1;
                }
                mustMatchToken(39, "msg.bad.catchcond", true);
                Name createNameNode = createNameNode();
                Comment andResetJsDoc2 = getAndResetJsDoc();
                if (andResetJsDoc2 != null) {
                    createNameNode.setJsDocNode(andResetJsDoc2);
                }
                String identifier = createNameNode.getIdentifier();
                if (this.inUseStrictDirective && ("eval".equals(identifier) || "arguments".equals(identifier))) {
                    reportError("msg.bad.id.strict", identifier);
                }
                if (matchToken(113, true)) {
                    i4 = this.ts.tokenBeg;
                    astNode2 = expr();
                    z = z2;
                } else {
                    i4 = -1;
                    astNode2 = null;
                    z = true;
                }
                if (mustMatchToken(89, "msg.bad.catchcond", true)) {
                    i5 = this.ts.tokenBeg;
                } else {
                    i5 = -1;
                }
                Comment comment2 = andResetJsDoc;
                mustMatchToken(86, "msg.no.brace.catchblock", true);
                Block block = (Block) statements();
                int nodeEnd2 = getNodeEnd(block);
                CatchClause catchClause = new CatchClause(i10);
                catchClause.setVarName(createNameNode);
                catchClause.setCatchCondition(astNode2);
                catchClause.setBody(block);
                if (i4 != -1) {
                    catchClause.setIfPosition(i4 - i10);
                }
                catchClause.setParens(i3, i5);
                catchClause.setLineno(i9);
                if (mustMatchToken(87, "msg.no.brace.after.body", true)) {
                    nodeEnd = this.ts.tokenEnd;
                } else {
                    nodeEnd = nodeEnd2;
                }
                catchClause.setLength(nodeEnd - i10);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(catchClause);
                z2 = z;
                andResetJsDoc = comment2;
            }
            comment = andResetJsDoc;
            i = Token.FINALLY;
        } else {
            comment = andResetJsDoc;
            i = Token.FINALLY;
            if (peekToken2 != 126) {
                mustMatchToken(Token.FINALLY, "msg.try.no.catchfinally", true);
            }
            arrayList = null;
        }
        if (matchToken(i, true)) {
            i2 = this.ts.tokenBeg;
            astNode = statement();
            nodeEnd = getNodeEnd(astNode);
        } else {
            i2 = -1;
            astNode = null;
        }
        tryStatement.setLength(nodeEnd - i6);
        tryStatement.setTryBlock(nextStatementAfterInlineComments);
        tryStatement.setCatchClauses(arrayList);
        tryStatement.setFinallyBlock(astNode);
        if (i2 != -1) {
            tryStatement.setFinallyPosition(i2 - i6);
        }
        tryStatement.setLineno(i7);
        if (comment != null) {
            tryStatement.setJsDocNode(comment);
        }
        return tryStatement;
    }

    private AstNode unaryExpr() throws IOException {
        int peekToken = peekToken();
        if (peekToken == 162) {
            consumeToken();
            peekToken = peekUntilNonComment(peekToken);
        }
        int i = this.ts.lineno;
        if (peekToken != -1) {
            if (peekToken != 14) {
                if (peekToken != 127) {
                    if (peekToken == 21) {
                        consumeToken();
                        UnaryExpression unaryExpression = new UnaryExpression(28, this.ts.tokenBeg, unaryExpr());
                        unaryExpression.setLineno(i);
                        return unaryExpression;
                    } else if (peekToken == 22) {
                        consumeToken();
                        UnaryExpression unaryExpression2 = new UnaryExpression(29, this.ts.tokenBeg, unaryExpr());
                        unaryExpression2.setLineno(i);
                        return unaryExpression2;
                    } else if (!(peekToken == 26 || peekToken == 27)) {
                        if (peekToken == 31) {
                            consumeToken();
                            UnaryExpression unaryExpression3 = new UnaryExpression(peekToken, this.ts.tokenBeg, unaryExpr());
                            unaryExpression3.setLineno(i);
                            return unaryExpression3;
                        } else if (peekToken != 32) {
                            if (peekToken == 107 || peekToken == 108) {
                                consumeToken();
                                UnaryExpression unaryExpression4 = new UnaryExpression(peekToken, this.ts.tokenBeg, memberExpr(true));
                                unaryExpression4.setLineno(i);
                                checkBadIncDec(unaryExpression4);
                                return unaryExpression4;
                            }
                        }
                    }
                }
                consumeToken();
                UnaryExpression unaryExpression5 = new UnaryExpression(peekToken, this.ts.tokenBeg, unaryExpr());
                unaryExpression5.setLineno(i);
                return unaryExpression5;
            } else if (this.compilerEnv.isXmlAvailable()) {
                consumeToken();
                return memberExprTail(true, xmlInitializer());
            }
            AstNode memberExpr = memberExpr(true);
            int peekTokenOrEOL = peekTokenOrEOL();
            if (peekTokenOrEOL != 107 && peekTokenOrEOL != 108) {
                return memberExpr;
            }
            consumeToken();
            UnaryExpression unaryExpression6 = new UnaryExpression(peekTokenOrEOL, this.ts.tokenBeg, memberExpr, true);
            unaryExpression6.setLineno(i);
            checkBadIncDec(unaryExpression6);
            return unaryExpression6;
        }
        consumeToken();
        return makeErrorNode();
    }

    private VariableDeclaration variables(int i, int i2, boolean z) throws IOException {
        int i3;
        Name name;
        AstNode astNode;
        VariableDeclaration variableDeclaration = new VariableDeclaration(i2);
        variableDeclaration.setType(i);
        variableDeclaration.setLineno(this.ts.lineno);
        Comment andResetJsDoc = getAndResetJsDoc();
        if (andResetJsDoc != null) {
            variableDeclaration.setJsDocNode(andResetJsDoc);
        }
        do {
            int peekToken = peekToken();
            TokenStream tokenStream = this.ts;
            int i4 = tokenStream.tokenBeg;
            int i5 = tokenStream.tokenEnd;
            AstNode astNode2 = null;
            if (peekToken == 84 || peekToken == 86) {
                astNode = destructuringPrimaryExpr();
                int nodeEnd = getNodeEnd(astNode);
                if (!(astNode instanceof DestructuringForm)) {
                    reportError("msg.bad.assign.left", i4, nodeEnd - i4);
                }
                markDestructuring(astNode);
                i3 = nodeEnd;
                name = null;
            } else {
                mustMatchToken(39, "msg.bad.var", true);
                Name createNameNode = createNameNode();
                createNameNode.setLineno(this.ts.getLineno());
                if (this.inUseStrictDirective) {
                    String string = this.ts.getString();
                    if ("eval".equals(string) || "arguments".equals(this.ts.getString())) {
                        reportError("msg.bad.id.strict", string);
                    }
                }
                defineSymbol(i, this.ts.getString(), this.inForInit);
                i3 = i5;
                name = createNameNode;
                astNode = null;
            }
            int i6 = this.ts.lineno;
            Comment andResetJsDoc2 = getAndResetJsDoc();
            if (matchToken(91, true)) {
                astNode2 = assignExpr();
                i3 = getNodeEnd(astNode2);
            }
            VariableInitializer variableInitializer = new VariableInitializer(i4, i3 - i4);
            if (astNode != null) {
                if (astNode2 == null && !this.inForInit) {
                    reportError("msg.destruct.assign.no.init");
                }
                variableInitializer.setTarget(astNode);
            } else {
                variableInitializer.setTarget(name);
            }
            variableInitializer.setInitializer(astNode2);
            variableInitializer.setType(i);
            variableInitializer.setJsDocNode(andResetJsDoc2);
            variableInitializer.setLineno(i6);
            variableDeclaration.addVariable(variableInitializer);
        } while (matchToken(90, true));
        variableDeclaration.setLength(i3 - i2);
        variableDeclaration.setIsStatement(z);
        return variableDeclaration;
    }

    private void warnMissingSemi(int i, int i2) {
        if (this.compilerEnv.isStrictMode()) {
            int[] iArr = new int[2];
            String line = this.ts.getLine(i2, iArr);
            if (this.compilerEnv.isIdeMode()) {
                i = Math.max(i, i2 - iArr[1]);
            }
            int i3 = i;
            if (line != null) {
                addStrictWarning("msg.missing.semi", "", i3, i2 - i3, iArr[0], line, iArr[1]);
                return;
            }
            addStrictWarning("msg.missing.semi", "", i3, i2 - i3);
        }
    }

    private void warnTrailingComma(int i, List<?> list, int i2) {
        if (this.compilerEnv.getWarnTrailingComma()) {
            if (!list.isEmpty()) {
                i = ((AstNode) list.get(0)).getPosition();
            }
            int max = Math.max(i, lineBeginningFor(i2));
            addWarning("msg.extra.trailing.comma", max, i2 - max);
        }
    }

    private WhileLoop whileLoop() throws IOException {
        if (this.currentToken != 118) {
            codeBug();
        }
        consumeToken();
        int i = this.ts.tokenBeg;
        WhileLoop whileLoop = new WhileLoop(i);
        whileLoop.setLineno(this.ts.lineno);
        enterLoop(whileLoop);
        try {
            ConditionData condition = condition();
            whileLoop.setCondition(condition.condition);
            whileLoop.setParens(condition.lp - i, condition.rp - i);
            AstNode nextStatementAfterInlineComments = getNextStatementAfterInlineComments(whileLoop);
            whileLoop.setLength(getNodeEnd(nextStatementAfterInlineComments) - i);
            whileLoop.setBody(nextStatementAfterInlineComments);
            return whileLoop;
        } finally {
            exitLoop();
        }
    }

    private WithStatement withStatement() throws IOException {
        int i;
        if (this.currentToken != 124) {
            codeBug();
        }
        consumeToken();
        Comment andResetJsDoc = getAndResetJsDoc();
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        int i4 = -1;
        if (mustMatchToken(88, "msg.no.paren.with", true)) {
            i = this.ts.tokenBeg;
        } else {
            i = -1;
        }
        AstNode expr = expr();
        if (mustMatchToken(89, "msg.no.paren.after.with", true)) {
            i4 = this.ts.tokenBeg;
        }
        WithStatement withStatement = new WithStatement(i3);
        AstNode nextStatementAfterInlineComments = getNextStatementAfterInlineComments(withStatement);
        withStatement.setLength(getNodeEnd(nextStatementAfterInlineComments) - i3);
        withStatement.setJsDocNode(andResetJsDoc);
        withStatement.setExpression(expr);
        withStatement.setStatement(nextStatementAfterInlineComments);
        withStatement.setParens(i, i4);
        withStatement.setLineno(i2);
        return withStatement;
    }

    private XmlElemRef xmlElemRef(int i, Name name, int i2) throws IOException {
        int i3;
        int i4 = this.ts.tokenBeg;
        int i5 = -1;
        if (i != -1) {
            i3 = i;
        } else {
            i3 = i4;
        }
        AstNode expr = expr();
        int nodeEnd = getNodeEnd(expr);
        if (mustMatchToken(85, "msg.no.bracket.index", true)) {
            TokenStream tokenStream = this.ts;
            int i6 = tokenStream.tokenBeg;
            nodeEnd = tokenStream.tokenEnd;
            i5 = i6;
        }
        XmlElemRef xmlElemRef = new XmlElemRef(i3, nodeEnd - i3);
        xmlElemRef.setNamespace(name);
        xmlElemRef.setColonPos(i2);
        xmlElemRef.setAtPos(i);
        xmlElemRef.setExpression(expr);
        xmlElemRef.setBrackets(i4, i5);
        return xmlElemRef;
    }

    private AstNode xmlInitializer() throws IOException {
        AstNode astNode;
        if (this.currentToken != 14) {
            codeBug();
        }
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        int firstXMLToken = tokenStream.getFirstXMLToken();
        if (firstXMLToken == 146 || firstXMLToken == 149) {
            XmlLiteral xmlLiteral = new XmlLiteral(i);
            xmlLiteral.setLineno(this.ts.lineno);
            while (firstXMLToken == 146) {
                TokenStream tokenStream2 = this.ts;
                xmlLiteral.addFragment(new XmlString(tokenStream2.tokenBeg, tokenStream2.getString()));
                mustMatchToken(86, "msg.syntax", true);
                int i2 = this.ts.tokenBeg;
                if (peekToken() == 87) {
                    astNode = new EmptyExpression(i2, this.ts.tokenEnd - i2);
                } else {
                    astNode = expr();
                }
                mustMatchToken(87, "msg.syntax", true);
                XmlExpression xmlExpression = new XmlExpression(i2, astNode);
                xmlExpression.setIsXmlAttribute(this.ts.isXMLAttribute());
                xmlExpression.setLength(this.ts.tokenEnd - i2);
                xmlLiteral.addFragment(xmlExpression);
                firstXMLToken = this.ts.getNextXMLToken();
            }
            if (firstXMLToken != 149) {
                reportError("msg.syntax");
                return makeErrorNode();
            }
            TokenStream tokenStream3 = this.ts;
            xmlLiteral.addFragment(new XmlString(tokenStream3.tokenBeg, tokenStream3.getString()));
            return xmlLiteral;
        }
        reportError("msg.syntax");
        return makeErrorNode();
    }

    public void addError(String str) {
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        addError(str, i, tokenStream.tokenEnd - i);
    }

    public void addStrictWarning(String str, String str2) {
        int i;
        int i2;
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            i = tokenStream.tokenBeg;
            i2 = tokenStream.tokenEnd - i;
        } else {
            i = -1;
            i2 = -1;
        }
        addStrictWarning(str, str2, i, i2);
    }

    public void addWarning(String str, String str2) {
        int i;
        int i2;
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            i = tokenStream.tokenBeg;
            i2 = tokenStream.tokenEnd - i;
        } else {
            i = -1;
            i2 = -1;
        }
        addWarning(str, str2, i, i2);
    }

    public void checkActivationName(String str, int i) {
        if (insideFunction()) {
            boolean z = true;
            if ((!"arguments".equals(str) || ((FunctionNode) this.currentScriptOrFn).getFunctionType() == 4) && ((this.compilerEnv.getActivationNames() == null || !this.compilerEnv.getActivationNames().contains(str)) && !("length".equals(str) && i == 33 && this.compilerEnv.getLanguageVersion() == 120))) {
                z = false;
            }
            if (z) {
                setRequiresActivation();
            }
        }
    }

    public void checkMutableReference(Node node) {
        if ((node.getIntProp(16, 0) & 4) != 0) {
            reportError("msg.bad.assign.left");
        }
    }

    public Node createDestructuringAssignment(int i, Node node, Node node2) {
        String nextTempName = this.currentScriptOrFn.getNextTempName();
        Node destructuringAssignmentHelper = destructuringAssignmentHelper(i, node, node2, nextTempName);
        destructuringAssignmentHelper.getLastChild().addChildToBack(createName(nextTempName));
        return destructuringAssignmentHelper;
    }

    public Node createName(String str) {
        checkActivationName(str, 39);
        return Node.newString(39, str);
    }

    public Node createNumber(double d) {
        return Node.newNumber(d);
    }

    public Scope createScopeNode(int i, int i2) {
        Scope scope = new Scope();
        scope.setType(i);
        scope.setLineno(i2);
        return scope;
    }

    public void defineSymbol(int i, String str) {
        defineSymbol(i, str, false);
    }

    public boolean destructuringArray(ArrayLiteral arrayLiteral, int i, String str, Node node, List<String> list) {
        int i2;
        if (i == 155) {
            i2 = Token.SETCONST;
        } else {
            i2 = 8;
        }
        boolean z = true;
        int i3 = 0;
        for (AstNode next : arrayLiteral.getElements()) {
            if (next.getType() == 129) {
                i3++;
            } else {
                Node node2 = new Node(36, createName(str), createNumber((double) i3));
                if (next.getType() == 39) {
                    String string = next.getString();
                    node.addChildToBack(new Node(i2, createName(49, string, (Node) null), node2));
                    if (i != -1) {
                        defineSymbol(i, string, true);
                        list.add(string);
                    }
                } else {
                    node.addChildToBack(destructuringAssignmentHelper(i, next, node2, this.currentScriptOrFn.getNextTempName()));
                }
                i3++;
                z = false;
            }
        }
        return z;
    }

    /* JADX INFO: finally extract failed */
    public Node destructuringAssignmentHelper(int i, Node node, Node node2, String str) {
        Scope createScopeNode = createScopeNode(Token.LETEXPR, node.getLineno());
        createScopeNode.addChildToFront(new Node((int) Token.LET, createName(39, str, node2)));
        try {
            pushScope(createScopeNode);
            boolean z = true;
            defineSymbol(Token.LET, str, true);
            popScope();
            Node node3 = new Node(90);
            createScopeNode.addChildToBack(node3);
            ArrayList arrayList = new ArrayList();
            int type = node.getType();
            if (type == 33 || type == 36) {
                if (i == 123 || i == 154 || i == 155) {
                    reportError("msg.bad.assign.left");
                }
                node3.addChildToBack(simpleAssignment(node, createName(str)));
            } else if (type == 66) {
                z = destructuringArray((ArrayLiteral) node, i, str, node3, arrayList);
            } else if (type != 67) {
                reportError("msg.bad.assign.left");
            } else {
                z = destructuringObject((ObjectLiteral) node, i, str, node3, arrayList);
            }
            if (z) {
                node3.addChildToBack(createNumber(0.0d));
            }
            createScopeNode.putProp(22, arrayList);
            return createScopeNode;
        } catch (Throwable th) {
            popScope();
            throw th;
        }
    }

    public boolean destructuringObject(ObjectLiteral objectLiteral, int i, String str, Node node, List<String> list) {
        int i2;
        int i3;
        Node node2;
        if (i == 155) {
            i2 = Token.SETCONST;
        } else {
            i2 = 8;
        }
        boolean z = true;
        for (ObjectProperty next : objectLiteral.getElements()) {
            TokenStream tokenStream = this.ts;
            if (tokenStream != null) {
                i3 = tokenStream.lineno;
            } else {
                i3 = 0;
            }
            AstNode left = next.getLeft();
            if (left instanceof Name) {
                node2 = new Node(33, createName(str), Node.newString(((Name) left).getIdentifier()));
            } else if (left instanceof StringLiteral) {
                node2 = new Node(33, createName(str), Node.newString(((StringLiteral) left).getValue()));
            } else if (left instanceof NumberLiteral) {
                node2 = new Node(36, createName(str), createNumber((double) ((int) ((NumberLiteral) left).getNumber())));
            } else {
                throw codeBug();
            }
            node2.setLineno(i3);
            AstNode right = next.getRight();
            if (right.getType() == 39) {
                String identifier = ((Name) right).getIdentifier();
                node.addChildToBack(new Node(i2, createName(49, identifier, (Node) null), node2));
                if (i != -1) {
                    defineSymbol(i, identifier, true);
                    list.add(identifier);
                }
            } else {
                node.addChildToBack(destructuringAssignmentHelper(i, right, node2, this.currentScriptOrFn.getNextTempName()));
            }
            z = false;
        }
        return z;
    }

    public boolean eof() {
        return this.ts.eof();
    }

    public boolean inUseStrictDirective() {
        return this.inUseStrictDirective;
    }

    public boolean insideFunction() {
        return this.nestingOfFunction != 0;
    }

    public String lookupMessage(String str) {
        return lookupMessage(str, (String) null);
    }

    public void markDestructuring(AstNode astNode) {
        if (astNode instanceof DestructuringForm) {
            ((DestructuringForm) astNode).setIsDestructuring(true);
        } else if (astNode instanceof ParenthesizedExpression) {
            markDestructuring(((ParenthesizedExpression) astNode).getExpression());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:11|12|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
        throw new java.lang.IllegalStateException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        r1.parseFinished = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        throw r3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0026 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.ast.AstRoot parse(java.lang.String r2, java.lang.String r3, int r4) {
        /*
            r1 = this;
            boolean r0 = r1.parseFinished
            if (r0 != 0) goto L_0x002f
            r1.sourceURI = r3
            org.mozilla.javascript.CompilerEnvirons r3 = r1.compilerEnv
            boolean r3 = r3.isIdeMode()
            if (r3 == 0) goto L_0x0014
            char[] r3 = r2.toCharArray()
            r1.sourceChars = r3
        L_0x0014:
            org.mozilla.javascript.TokenStream r3 = new org.mozilla.javascript.TokenStream
            r0 = 0
            r3.<init>(r1, r0, r2, r4)
            r1.ts = r3
            r2 = 1
            org.mozilla.javascript.ast.AstRoot r3 = r1.parse()     // Catch:{ IOException -> 0x0026 }
            r1.parseFinished = r2
            return r3
        L_0x0024:
            r3 = move-exception
            goto L_0x002c
        L_0x0026:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0024 }
            r3.<init>()     // Catch:{ all -> 0x0024 }
            throw r3     // Catch:{ all -> 0x0024 }
        L_0x002c:
            r1.parseFinished = r2
            throw r3
        L_0x002f:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "parser reused"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.parse(java.lang.String, java.lang.String, int):org.mozilla.javascript.ast.AstRoot");
    }

    public void popScope() {
        this.currentScope = this.currentScope.getParentScope();
    }

    public void pushScope(Scope scope) {
        Scope parentScope = scope.getParentScope();
        if (parentScope == null) {
            this.currentScope.addChildScope(scope);
        } else if (parentScope != this.currentScope) {
            codeBug();
        }
        this.currentScope = scope;
    }

    public AstNode removeParens(AstNode astNode) {
        while (astNode instanceof ParenthesizedExpression) {
            astNode = ((ParenthesizedExpression) astNode).getExpression();
        }
        return astNode;
    }

    public void reportError(String str) {
        reportError(str, (String) null);
    }

    public void setDefaultUseStrictDirective(boolean z) {
        this.defaultUseStrictDirective = z;
    }

    public void setIsGenerator() {
        if (insideFunction()) {
            ((FunctionNode) this.currentScriptOrFn).setIsGenerator();
        }
    }

    public void setRequiresActivation() {
        if (insideFunction()) {
            ((FunctionNode) this.currentScriptOrFn).setRequiresActivation();
        }
    }

    public Node simpleAssignment(Node node, Node node2) {
        Node node3;
        Node node4;
        int i;
        int type = node.getType();
        if (type == 33 || type == 36) {
            if (node instanceof PropertyGet) {
                PropertyGet propertyGet = (PropertyGet) node;
                node4 = propertyGet.getTarget();
                node3 = propertyGet.getProperty();
            } else if (node instanceof ElementGet) {
                ElementGet elementGet = (ElementGet) node;
                node4 = elementGet.getTarget();
                node3 = elementGet.getElement();
            } else {
                node4 = node.getFirstChild();
                node3 = node.getLastChild();
            }
            if (type == 33) {
                node3.setType(41);
                i = 35;
            } else {
                i = 37;
            }
            return new Node(i, node4, node3, node2);
        } else if (type == 39) {
            String identifier = ((Name) node).getIdentifier();
            if (this.inUseStrictDirective && ("eval".equals(identifier) || "arguments".equals(identifier))) {
                reportError("msg.bad.id.strict", identifier);
            }
            node.setType(49);
            return new Node(8, node, node2);
        } else if (type == 68) {
            Node firstChild = node.getFirstChild();
            checkMutableReference(firstChild);
            return new Node(69, firstChild, node2);
        } else {
            throw codeBug();
        }
    }

    public Parser(CompilerEnvirons compilerEnvirons) {
        this(compilerEnvirons, compilerEnvirons.getErrorReporter());
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.ast.Name createNameNode(boolean r6, int r7) {
        /*
            r5 = this;
            org.mozilla.javascript.TokenStream r0 = r5.ts
            int r1 = r0.tokenBeg
            java.lang.String r0 = r0.getString()
            org.mozilla.javascript.TokenStream r2 = r5.ts
            int r2 = r2.lineno
            java.lang.String r3 = r5.prevNameTokenString
            java.lang.String r4 = ""
            boolean r3 = r4.equals(r3)
            if (r3 != 0) goto L_0x0023
            int r1 = r5.prevNameTokenStart
            java.lang.String r0 = r5.prevNameTokenString
            int r2 = r5.prevNameTokenLineno
            r3 = 0
            r5.prevNameTokenStart = r3
            r5.prevNameTokenString = r4
            r5.prevNameTokenLineno = r3
        L_0x0023:
            if (r0 != 0) goto L_0x0031
            org.mozilla.javascript.CompilerEnvirons r3 = r5.compilerEnv
            boolean r3 = r3.isIdeMode()
            if (r3 == 0) goto L_0x002e
            goto L_0x0032
        L_0x002e:
            r5.codeBug()
        L_0x0031:
            r4 = r0
        L_0x0032:
            org.mozilla.javascript.ast.Name r0 = new org.mozilla.javascript.ast.Name
            r0.<init>((int) r1, (java.lang.String) r4)
            r0.setLineno(r2)
            if (r6 == 0) goto L_0x003f
            r5.checkActivationName(r4, r7)
        L_0x003f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Parser.createNameNode(boolean, int):org.mozilla.javascript.ast.Name");
    }

    private FunctionNode function(int i, boolean z) throws IOException {
        Name name;
        String str;
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.lineno;
        int i3 = tokenStream.tokenBeg;
        AstNode astNode = null;
        if (matchToken(39, true)) {
            name = createNameNode(true, 39);
            if (this.inUseStrictDirective) {
                String identifier = name.getIdentifier();
                if ("eval".equals(identifier) || "arguments".equals(identifier)) {
                    reportError("msg.bad.id.strict", identifier);
                }
            }
            if (!matchToken(88, true)) {
                if (this.compilerEnv.isAllowMemberExprAsFunctionName()) {
                    astNode = memberExprTail(false, name);
                    name = null;
                }
                mustMatchToken(88, "msg.no.paren.parms", true);
            }
        } else if (matchToken(88, true)) {
            name = null;
        } else if (matchToken(23, true) && this.compilerEnv.getLanguageVersion() >= 200) {
            return function(i, true);
        } else {
            AstNode memberExpr = this.compilerEnv.isAllowMemberExprAsFunctionName() ? memberExpr(false) : null;
            mustMatchToken(88, "msg.no.paren.parms", true);
            astNode = memberExpr;
            name = null;
        }
        int i4 = this.currentToken == 88 ? this.ts.tokenBeg : -1;
        if (!((astNode != null ? 2 : i) == 2 || name == null || name.length() <= 0)) {
            defineSymbol(110, name.getIdentifier());
        }
        FunctionNode functionNode = new FunctionNode(i3, name);
        functionNode.setFunctionType(i);
        if (z) {
            functionNode.setIsES6Generator();
        }
        if (i4 != -1) {
            functionNode.setLp(i4 - i3);
        }
        functionNode.setJsDocNode(getAndResetJsDoc());
        PerFunctionVariables perFunctionVariables = new PerFunctionVariables(functionNode);
        try {
            parseFunctionParams(functionNode);
            functionNode.setBody(parseFunctionBody(i, functionNode));
            functionNode.setEncodedSourceBounds(i3, this.ts.tokenEnd);
            functionNode.setLength(this.ts.tokenEnd - i3);
            if (this.compilerEnv.isStrictMode() && !functionNode.getBody().hasConsistentReturnUsage()) {
                String str2 = (name == null || name.length() <= 0) ? "msg.anon.no.return.value" : "msg.no.return.value";
                if (name == null) {
                    str = "";
                } else {
                    str = name.getIdentifier();
                }
                addStrictWarning(str2, str);
            }
            if (astNode != null) {
                Kit.codeBug();
                functionNode.setMemberExprNode(astNode);
            }
            functionNode.setSourceName(this.sourceURI);
            functionNode.setBaseLineno(i2);
            functionNode.setEndLineno(this.ts.lineno);
            if (this.compilerEnv.isIdeMode()) {
                functionNode.setParentScope(this.currentScope);
            }
            return functionNode;
        } finally {
            perFunctionVariables.restore();
        }
    }

    private AstNode generatorExpression(AstNode astNode, int i, boolean z) throws IOException {
        ConditionData conditionData;
        int i2;
        ArrayList arrayList = new ArrayList();
        while (peekToken() == 120) {
            arrayList.add(generatorExpressionLoop());
        }
        if (peekToken() == 113) {
            consumeToken();
            i2 = this.ts.tokenBeg - i;
            conditionData = condition();
        } else {
            i2 = -1;
            conditionData = null;
        }
        if (!z) {
            mustMatchToken(89, "msg.no.paren.let", true);
        }
        GeneratorExpression generatorExpression = new GeneratorExpression(i, this.ts.tokenEnd - i);
        generatorExpression.setResult(astNode);
        generatorExpression.setLoops(arrayList);
        if (conditionData != null) {
            generatorExpression.setIfPosition(i2);
            generatorExpression.setFilter(conditionData.condition);
            generatorExpression.setFilterLp(conditionData.lp - i);
            generatorExpression.setFilterRp(conditionData.rp - i);
        }
        return generatorExpression;
    }

    private boolean mustMatchToken(int i, String str, int i2, int i3, boolean z) throws IOException {
        if (matchToken(i, z)) {
            return true;
        }
        reportError(str, i2, i3);
        return false;
    }

    public void addError(String str, int i, int i2) {
        addError(str, (String) null, i, i2);
    }

    public void defineSymbol(int i, String str, boolean z) {
        if (str == null) {
            if (!this.compilerEnv.isIdeMode()) {
                codeBug();
            } else {
                return;
            }
        }
        Scope definingScope = this.currentScope.getDefiningScope(str);
        Symbol symbol = definingScope != null ? definingScope.getSymbol(str) : null;
        int declType = symbol != null ? symbol.getDeclType() : -1;
        String str2 = "msg.var.redecl";
        if (symbol != null && (declType == 155 || i == 155 || (definingScope == this.currentScope && declType == 154))) {
            if (declType == 155) {
                str2 = "msg.const.redecl";
            } else if (declType == 154) {
                str2 = "msg.let.redecl";
            } else if (declType != 123) {
                str2 = declType == 110 ? "msg.fn.redecl" : "msg.parm.redecl";
            }
            addError(str2, str);
        } else if (i != 88) {
            if (!(i == 110 || i == 123)) {
                if (i != 154) {
                    if (i != 155) {
                        throw codeBug();
                    }
                } else if (z || (this.currentScope.getType() != 113 && !(this.currentScope instanceof Loop))) {
                    this.currentScope.putSymbol(new Symbol(i, str));
                    return;
                } else {
                    addError("msg.let.decl.not.in.block");
                    return;
                }
            }
            if (symbol == null) {
                this.currentScriptOrFn.putSymbol(new Symbol(i, str));
            } else if (declType == 123) {
                addStrictWarning(str2, str);
            } else if (declType == 88) {
                addStrictWarning("msg.var.hides.arg", str);
            }
        } else {
            if (symbol != null) {
                addWarning("msg.dup.parms", str);
            }
            this.currentScriptOrFn.putSymbol(new Symbol(i, str));
        }
    }

    public String lookupMessage(String str, String str2) {
        if (str2 == null) {
            return ScriptRuntime.getMessage0(str);
        }
        return ScriptRuntime.getMessage1(str, str2);
    }

    public void reportError(String str, String str2) {
        TokenStream tokenStream = this.ts;
        if (tokenStream == null) {
            reportError(str, str2, 1, 1);
            return;
        }
        int i = tokenStream.tokenBeg;
        reportError(str, str2, i, tokenStream.tokenEnd - i);
    }

    public Parser(CompilerEnvirons compilerEnvirons, ErrorReporter errorReporter2) {
        this.currentFlaggedToken = 0;
        this.prevNameTokenString = "";
        this.compilerEnv = compilerEnvirons;
        this.errorReporter = errorReporter2;
        if (errorReporter2 instanceof IdeErrorReporter) {
            this.errorCollector = (IdeErrorReporter) errorReporter2;
        }
    }

    public void addError(String str, String str2) {
        TokenStream tokenStream = this.ts;
        int i = tokenStream.tokenBeg;
        addError(str, str2, i, tokenStream.tokenEnd - i);
    }

    public Node createName(int i, String str, Node node) {
        Node createName = createName(str);
        createName.setType(i);
        if (node != null) {
            createName.addChildToBack(node);
        }
        return createName;
    }

    public void addError(String str, int i) {
        String ch = Character.toString((char) i);
        TokenStream tokenStream = this.ts;
        int i2 = tokenStream.tokenBeg;
        addError(str, ch, i2, tokenStream.tokenEnd - i2);
    }

    public void addStrictWarning(String str, String str2, int i, int i2) {
        if (this.compilerEnv.isStrictMode()) {
            addWarning(str, str2, i, i2);
        }
    }

    public void addWarning(String str, int i, int i2) {
        addWarning(str, (String) null, i, i2);
    }

    public void reportError(String str, int i, int i2) {
        reportError(str, (String) null, i, i2);
    }

    public void addError(String str, String str2, int i, int i2) {
        int i3;
        String str3;
        int i4;
        this.syntaxErrorCount++;
        String lookupMessage = lookupMessage(str, str2);
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.error(lookupMessage, this.sourceURI, i, i2);
            return;
        }
        TokenStream tokenStream = this.ts;
        if (tokenStream != null) {
            int lineno = tokenStream.getLineno();
            str3 = this.ts.getLine();
            i3 = this.ts.getOffset();
            i4 = lineno;
        } else {
            str3 = "";
            i4 = 1;
            i3 = 1;
        }
        this.errorReporter.error(lookupMessage, this.sourceURI, i4, str3, i3);
    }

    public void addWarning(String str, String str2, int i, int i2) {
        String lookupMessage = lookupMessage(str, str2);
        if (this.compilerEnv.reportWarningAsError()) {
            addError(str, str2, i, i2);
            return;
        }
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.warning(lookupMessage, this.sourceURI, i, i2);
        } else {
            this.errorReporter.warning(lookupMessage, this.sourceURI, this.ts.getLineno(), this.ts.getLine(), this.ts.getOffset());
        }
    }

    public void reportError(String str, String str2, int i, int i2) {
        addError(str, str2, i, i2);
        if (!this.compilerEnv.recoverFromErrors()) {
            throw new ParserException();
        }
    }

    private void addStrictWarning(String str, String str2, int i, int i2, int i3, String str3, int i4) {
        if (this.compilerEnv.isStrictMode()) {
            addWarning(str, str2, i, i2, i3, str3, i4);
        }
    }

    private AstNode statements() throws IOException {
        return statements((AstNode) null);
    }

    @Deprecated
    public AstRoot parse(Reader reader, String str, int i) throws IOException {
        if (this.parseFinished) {
            throw new IllegalStateException("parser reused");
        } else if (this.compilerEnv.isIdeMode()) {
            return parse(Kit.readReader(reader), str, i);
        } else {
            try {
                this.sourceURI = str;
                this.ts = new TokenStream(this, reader, (String) null, i);
                return parse();
            } finally {
                this.parseFinished = true;
            }
        }
    }

    private void addWarning(String str, String str2, int i, int i2, int i3, String str3, int i4) {
        String lookupMessage = lookupMessage(str, str2);
        if (this.compilerEnv.reportWarningAsError()) {
            addError(str, str2, i, i2, i3, str3, i4);
            return;
        }
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.warning(lookupMessage, this.sourceURI, i, i2);
        } else {
            this.errorReporter.warning(lookupMessage, this.sourceURI, i3, str3, i4);
        }
    }

    private void addError(String str, String str2, int i, int i2, int i3, String str3, int i4) {
        this.syntaxErrorCount++;
        String lookupMessage = lookupMessage(str, str2);
        IdeErrorReporter ideErrorReporter = this.errorCollector;
        if (ideErrorReporter != null) {
            ideErrorReporter.error(lookupMessage, this.sourceURI, i, i2);
        } else {
            this.errorReporter.error(lookupMessage, this.sourceURI, i3, str3, i4);
        }
    }

    private AstRoot parse() throws IOException {
        AstNode astNode;
        AstRoot astRoot = new AstRoot(0);
        this.currentScriptOrFn = astRoot;
        this.currentScope = astRoot;
        int i = this.ts.lineno;
        boolean z = this.inUseStrictDirective;
        boolean z2 = this.defaultUseStrictDirective;
        this.inUseStrictDirective = z2;
        if (z2) {
            astRoot.setInStrictMode(true);
        }
        boolean z3 = true;
        int i2 = 0;
        while (true) {
            try {
                int peekToken = peekToken();
                if (peekToken <= 0) {
                    break;
                }
                if (peekToken == 110) {
                    consumeToken();
                    try {
                        astNode = function(this.calledByCompileFunction ? 2 : 1);
                    } catch (ParserException unused) {
                    }
                } else if (peekToken == 162) {
                    List<Comment> list = this.scannedComments;
                    astNode = list.get(list.size() - 1);
                    consumeToken();
                } else {
                    astNode = statement();
                    if (z3) {
                        String directive = getDirective(astNode);
                        if (directive == null) {
                            z3 = false;
                        } else if (directive.equals("use strict")) {
                            this.inUseStrictDirective = true;
                            astRoot.setInStrictMode(true);
                        }
                    }
                }
                i2 = getNodeEnd(astNode);
                astRoot.addChildToBack(astNode);
                astNode.setParent(astRoot);
            } catch (StackOverflowError unused2) {
                String lookupMessage = lookupMessage("msg.too.deep.parser.recursion");
                if (!this.compilerEnv.isIdeMode()) {
                    throw Context.reportRuntimeError(lookupMessage, this.sourceURI, this.ts.lineno, (String) null, 0);
                }
            } catch (Throwable th) {
                this.inUseStrictDirective = z;
                throw th;
            }
        }
        this.inUseStrictDirective = z;
        int i3 = this.syntaxErrorCount;
        if (i3 != 0) {
            String lookupMessage2 = lookupMessage("msg.got.syntax.errors", String.valueOf(i3));
            if (!this.compilerEnv.isIdeMode()) {
                throw this.errorReporter.runtimeError(lookupMessage2, this.sourceURI, i, (String) null, 0);
            }
        }
        List<Comment> list2 = this.scannedComments;
        if (list2 != null) {
            i2 = Math.max(i2, getNodeEnd(this.scannedComments.get(list2.size() - 1)));
            for (Comment addComment : this.scannedComments) {
                astRoot.addComment(addComment);
            }
        }
        astRoot.setLength(i2 - 0);
        astRoot.setSourceName(this.sourceURI);
        astRoot.setBaseLineno(i);
        astRoot.setEndLineno(this.ts.lineno);
        return astRoot;
    }
}
