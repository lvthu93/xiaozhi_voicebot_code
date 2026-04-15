package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;

public class NodeTransformer {
    private boolean hasFinally;
    private ObjArray loopEnds;
    private ObjArray loops;

    private static Node addBeforeCurrent(Node node, Node node2, Node node3, Node node4) {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.addChildToFront(node4);
        } else {
            if (node3 != node2.getNext()) {
                Kit.codeBug();
            }
            node.addChildAfter(node4, node2);
        }
        return node4;
    }

    private static Node replaceCurrent(Node node, Node node2, Node node3, Node node4) {
        if (node2 == null) {
            if (node3 != node.getFirstChild()) {
                Kit.codeBug();
            }
            node.replaceChild(node3, node4);
        } else if (node2.next == node3) {
            node.replaceChildAfter(node2, node4);
        } else {
            node.replaceChild(node3, node4);
        }
        return node4;
    }

    private void transformCompilationUnit(ScriptNode scriptNode, boolean z) {
        boolean z2;
        this.loops = new ObjArray();
        this.loopEnds = new ObjArray();
        this.hasFinally = false;
        if (scriptNode.getType() != 110 || ((FunctionNode) scriptNode).requiresActivation()) {
            z2 = true;
        } else {
            z2 = false;
        }
        scriptNode.flattenSymbolTable(!z2);
        transformCompilationUnit_r(scriptNode, scriptNode, scriptNode, z2, z);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: org.mozilla.javascript.ast.Scope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: org.mozilla.javascript.Node} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v25, resolved type: org.mozilla.javascript.ast.Jump} */
    /* JADX WARNING: type inference failed for: r1v40 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x03a0  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x03a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void transformCompilationUnit_r(org.mozilla.javascript.ast.ScriptNode r19, org.mozilla.javascript.Node r20, org.mozilla.javascript.ast.Scope r21, boolean r22, boolean r23) {
        /*
            r18 = this;
            r6 = r18
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = 0
            r0 = r10
        L_0x000a:
            if (r0 != 0) goto L_0x0012
            org.mozilla.javascript.Node r0 = r20.getFirstChild()
            r1 = r10
            goto L_0x001b
        L_0x0012:
            org.mozilla.javascript.Node r1 = r0.getNext()
            r17 = r1
            r1 = r0
            r0 = r17
        L_0x001b:
            if (r0 != 0) goto L_0x001e
            return
        L_0x001e:
            int r2 = r0.getType()
            r3 = 154(0x9a, float:2.16E-43)
            r4 = 130(0x82, float:1.82E-43)
            r5 = 159(0x9f, float:2.23E-43)
            r11 = 39
            if (r22 == 0) goto L_0x0087
            r12 = 158(0x9e, float:2.21E-43)
            if (r2 == r4) goto L_0x0036
            r13 = 133(0x85, float:1.86E-43)
            if (r2 == r13) goto L_0x0036
            if (r2 != r12) goto L_0x0087
        L_0x0036:
            boolean r13 = r0 instanceof org.mozilla.javascript.ast.Scope
            if (r13 == 0) goto L_0x0087
            r13 = r0
            org.mozilla.javascript.ast.Scope r13 = (org.mozilla.javascript.ast.Scope) r13
            java.util.Map r14 = r13.getSymbolTable()
            if (r14 == 0) goto L_0x0087
            org.mozilla.javascript.Node r14 = new org.mozilla.javascript.Node
            if (r2 != r12) goto L_0x004a
            r2 = 159(0x9f, float:2.23E-43)
            goto L_0x004c
        L_0x004a:
            r2 = 154(0x9a, float:2.16E-43)
        L_0x004c:
            r14.<init>(r2)
            org.mozilla.javascript.Node r2 = new org.mozilla.javascript.Node
            r2.<init>(r3)
            r14.addChildToBack(r2)
            java.util.Map r12 = r13.getSymbolTable()
            java.util.Set r12 = r12.keySet()
            java.util.Iterator r12 = r12.iterator()
        L_0x0063:
            boolean r15 = r12.hasNext()
            if (r15 == 0) goto L_0x0077
            java.lang.Object r15 = r12.next()
            java.lang.String r15 = (java.lang.String) r15
            org.mozilla.javascript.Node r15 = org.mozilla.javascript.Node.newString(r11, r15)
            r2.addChildToBack(r15)
            goto L_0x0063
        L_0x0077:
            r13.setSymbolTable(r10)
            org.mozilla.javascript.Node r2 = replaceCurrent(r8, r1, r0, r14)
            int r12 = r2.getType()
            r14.addChildToBack(r0)
            r0 = r2
            r2 = r12
        L_0x0087:
            r12 = 3
            if (r2 == r12) goto L_0x0380
            r13 = 4
            r14 = 136(0x88, float:1.9E-43)
            r15 = 124(0x7c, float:1.74E-43)
            r16 = 0
            r4 = 82
            if (r2 == r13) goto L_0x02de
            r13 = 7
            if (r2 == r13) goto L_0x027e
            r13 = 49
            r10 = 8
            if (r2 == r10) goto L_0x0205
            r10 = 38
            if (r2 == r10) goto L_0x0200
            if (r2 == r11) goto L_0x020c
            r10 = 73
            if (r2 == r10) goto L_0x01f8
            if (r2 == r4) goto L_0x01e0
            r10 = 115(0x73, float:1.61E-43)
            if (r2 == r10) goto L_0x01cf
            r10 = 138(0x8a, float:1.93E-43)
            if (r2 == r10) goto L_0x01c0
            if (r2 == r5) goto L_0x0142
            r10 = 166(0xa6, float:2.33E-43)
            if (r2 == r10) goto L_0x01f8
            switch(r2) {
                case 30: goto L_0x013d;
                case 31: goto L_0x020c;
                case 32: goto L_0x027e;
                default: goto L_0x00bb;
            }
        L_0x00bb:
            switch(r2) {
                case 121: goto L_0x00df;
                case 122: goto L_0x00df;
                case 123: goto L_0x0167;
                case 124: goto L_0x00c6;
                default: goto L_0x00be;
            }
        L_0x00be:
            switch(r2) {
                case 131: goto L_0x01cf;
                case 132: goto L_0x0380;
                case 133: goto L_0x01cf;
                default: goto L_0x00c1;
            }
        L_0x00c1:
            switch(r2) {
                case 154: goto L_0x0142;
                case 155: goto L_0x0167;
                case 156: goto L_0x020c;
                default: goto L_0x00c4;
            }
        L_0x00c4:
            goto L_0x039a
        L_0x00c6:
            org.mozilla.javascript.ObjArray r1 = r6.loops
            r1.push(r0)
            org.mozilla.javascript.Node r1 = r0.getNext()
            int r2 = r1.getType()
            if (r2 == r12) goto L_0x00d8
            org.mozilla.javascript.Kit.codeBug()
        L_0x00d8:
            org.mozilla.javascript.ObjArray r2 = r6.loopEnds
            r2.push(r1)
            goto L_0x039a
        L_0x00df:
            r3 = r0
            org.mozilla.javascript.ast.Jump r3 = (org.mozilla.javascript.ast.Jump) r3
            org.mozilla.javascript.ast.Jump r5 = r3.getJumpStatement()
            if (r5 != 0) goto L_0x00eb
            org.mozilla.javascript.Kit.codeBug()
        L_0x00eb:
            org.mozilla.javascript.ObjArray r10 = r6.loops
            int r10 = r10.size()
        L_0x00f1:
            if (r10 == 0) goto L_0x0138
            int r10 = r10 + -1
            org.mozilla.javascript.ObjArray r11 = r6.loops
            java.lang.Object r11 = r11.get(r10)
            org.mozilla.javascript.Node r11 = (org.mozilla.javascript.Node) r11
            if (r11 != r5) goto L_0x0114
            r1 = 121(0x79, float:1.7E-43)
            if (r2 != r1) goto L_0x0108
            org.mozilla.javascript.Node r1 = r5.target
            r3.target = r1
            goto L_0x010e
        L_0x0108:
            org.mozilla.javascript.Node r1 = r5.getContinue()
            r3.target = r1
        L_0x010e:
            r1 = 5
            r3.setType(r1)
            goto L_0x039a
        L_0x0114:
            int r13 = r11.getType()
            if (r13 != r15) goto L_0x0124
            org.mozilla.javascript.Node r11 = new org.mozilla.javascript.Node
            r11.<init>(r12)
            org.mozilla.javascript.Node r1 = addBeforeCurrent(r8, r1, r0, r11)
            goto L_0x00f1
        L_0x0124:
            if (r13 != r4) goto L_0x00f1
            org.mozilla.javascript.ast.Jump r11 = (org.mozilla.javascript.ast.Jump) r11
            org.mozilla.javascript.ast.Jump r13 = new org.mozilla.javascript.ast.Jump
            r13.<init>(r14)
            org.mozilla.javascript.Node r11 = r11.getFinally()
            r13.target = r11
            org.mozilla.javascript.Node r1 = addBeforeCurrent(r8, r1, r0, r13)
            goto L_0x00f1
        L_0x0138:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x013d:
            r6.visitNew(r0, r7)
            goto L_0x039a
        L_0x0142:
            org.mozilla.javascript.Node r4 = r0.getFirstChild()
            int r4 = r4.getType()
            if (r4 != r3) goto L_0x0167
            int r2 = r19.getType()
            r3 = 110(0x6e, float:1.54E-43)
            if (r2 != r3) goto L_0x0160
            r2 = r7
            org.mozilla.javascript.ast.FunctionNode r2 = (org.mozilla.javascript.ast.FunctionNode) r2
            boolean r2 = r2.requiresActivation()
            if (r2 == 0) goto L_0x015e
            goto L_0x0160
        L_0x015e:
            r2 = 0
            goto L_0x0161
        L_0x0160:
            r2 = 1
        L_0x0161:
            org.mozilla.javascript.Node r0 = r6.visitLet(r2, r8, r1, r0)
            goto L_0x039a
        L_0x0167:
            org.mozilla.javascript.Node r3 = new org.mozilla.javascript.Node
            r4 = 130(0x82, float:1.82E-43)
            r3.<init>(r4)
            org.mozilla.javascript.Node r4 = r0.getFirstChild()
        L_0x0172:
            if (r4 == 0) goto L_0x01ba
            org.mozilla.javascript.Node r10 = r4.getNext()
            int r12 = r4.getType()
            if (r12 != r11) goto L_0x019f
            boolean r12 = r4.hasChildren()
            if (r12 != 0) goto L_0x0185
            goto L_0x01b3
        L_0x0185:
            org.mozilla.javascript.Node r12 = r4.getFirstChild()
            r4.removeChild(r12)
            r4.setType(r13)
            org.mozilla.javascript.Node r14 = new org.mozilla.javascript.Node
            r15 = 155(0x9b, float:2.17E-43)
            if (r2 != r15) goto L_0x0198
            r15 = 156(0x9c, float:2.19E-43)
            goto L_0x019a
        L_0x0198:
            r15 = 8
        L_0x019a:
            r14.<init>((int) r15, (org.mozilla.javascript.Node) r4, (org.mozilla.javascript.Node) r12)
            r4 = r14
            goto L_0x01a5
        L_0x019f:
            int r12 = r4.getType()
            if (r12 != r5) goto L_0x01b5
        L_0x01a5:
            org.mozilla.javascript.Node r12 = new org.mozilla.javascript.Node
            r14 = 134(0x86, float:1.88E-43)
            int r15 = r0.getLineno()
            r12.<init>((int) r14, (org.mozilla.javascript.Node) r4, (int) r15)
            r3.addChildToBack(r12)
        L_0x01b3:
            r4 = r10
            goto L_0x0172
        L_0x01b5:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x01ba:
            org.mozilla.javascript.Node r0 = replaceCurrent(r8, r1, r0, r3)
            goto L_0x039a
        L_0x01c0:
            java.lang.String r1 = r0.getString()
            org.mozilla.javascript.ast.Scope r1 = r9.getDefiningScope(r1)
            if (r1 == 0) goto L_0x039a
            r0.setScope(r1)
            goto L_0x039a
        L_0x01cf:
            org.mozilla.javascript.ObjArray r1 = r6.loops
            r1.push(r0)
            org.mozilla.javascript.ObjArray r1 = r6.loopEnds
            r2 = r0
            org.mozilla.javascript.ast.Jump r2 = (org.mozilla.javascript.ast.Jump) r2
            org.mozilla.javascript.Node r2 = r2.target
            r1.push(r2)
            goto L_0x039a
        L_0x01e0:
            r1 = r0
            org.mozilla.javascript.ast.Jump r1 = (org.mozilla.javascript.ast.Jump) r1
            org.mozilla.javascript.Node r1 = r1.getFinally()
            if (r1 == 0) goto L_0x039a
            r2 = 1
            r6.hasFinally = r2
            org.mozilla.javascript.ObjArray r2 = r6.loops
            r2.push(r0)
            org.mozilla.javascript.ObjArray r2 = r6.loopEnds
            r2.push(r1)
            goto L_0x039a
        L_0x01f8:
            r1 = r7
            org.mozilla.javascript.ast.FunctionNode r1 = (org.mozilla.javascript.ast.FunctionNode) r1
            r1.addResumptionPoint(r0)
            goto L_0x039a
        L_0x0200:
            r6.visitCall(r0, r7)
            goto L_0x039a
        L_0x0205:
            if (r23 == 0) goto L_0x020c
            r3 = 74
            r0.setType(r3)
        L_0x020c:
            if (r22 == 0) goto L_0x0210
            goto L_0x039a
        L_0x0210:
            r3 = 31
            if (r2 != r11) goto L_0x0216
            r4 = r0
            goto L_0x0229
        L_0x0216:
            org.mozilla.javascript.Node r4 = r0.getFirstChild()
            int r5 = r4.getType()
            if (r5 == r13) goto L_0x0229
            if (r2 != r3) goto L_0x0224
            goto L_0x039a
        L_0x0224:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x0229:
            org.mozilla.javascript.ast.Scope r5 = r4.getScope()
            if (r5 == 0) goto L_0x0231
            goto L_0x039a
        L_0x0231:
            java.lang.String r5 = r4.getString()
            org.mozilla.javascript.ast.Scope r5 = r9.getDefiningScope(r5)
            if (r5 == 0) goto L_0x039a
            r4.setScope(r5)
            if (r2 != r11) goto L_0x0247
            r1 = 55
            r0.setType(r1)
            goto L_0x039a
        L_0x0247:
            r5 = 41
            r10 = 8
            if (r2 == r10) goto L_0x0274
            r10 = 74
            if (r2 != r10) goto L_0x0252
            goto L_0x0274
        L_0x0252:
            r10 = 156(0x9c, float:2.19E-43)
            if (r2 != r10) goto L_0x0260
            r1 = 157(0x9d, float:2.2E-43)
            r0.setType(r1)
            r4.setType(r5)
            goto L_0x039a
        L_0x0260:
            if (r2 != r3) goto L_0x026f
            org.mozilla.javascript.Node r2 = new org.mozilla.javascript.Node
            r3 = 44
            r2.<init>(r3)
            org.mozilla.javascript.Node r0 = replaceCurrent(r8, r1, r0, r2)
            goto L_0x039a
        L_0x026f:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x0274:
            r1 = 56
            r0.setType(r1)
            r4.setType(r5)
            goto L_0x039a
        L_0x027e:
            org.mozilla.javascript.Node r1 = r0.getFirstChild()
            r3 = 7
            if (r2 != r3) goto L_0x02cf
        L_0x0285:
            int r2 = r1.getType()
            r3 = 26
            if (r2 != r3) goto L_0x0292
            org.mozilla.javascript.Node r1 = r1.getFirstChild()
            goto L_0x0285
        L_0x0292:
            int r2 = r1.getType()
            r3 = 12
            if (r2 == r3) goto L_0x02a2
            int r2 = r1.getType()
            r3 = 13
            if (r2 != r3) goto L_0x02cf
        L_0x02a2:
            org.mozilla.javascript.Node r2 = r1.getFirstChild()
            org.mozilla.javascript.Node r3 = r1.getLastChild()
            int r4 = r2.getType()
            java.lang.String r5 = "undefined"
            if (r4 != r11) goto L_0x02be
            java.lang.String r4 = r2.getString()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x02be
            r1 = r3
            goto L_0x02cf
        L_0x02be:
            int r4 = r3.getType()
            if (r4 != r11) goto L_0x02cf
            java.lang.String r3 = r3.getString()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x02cf
            r1 = r2
        L_0x02cf:
            int r2 = r1.getType()
            r3 = 33
            if (r2 != r3) goto L_0x039a
            r2 = 34
            r1.setType(r2)
            goto L_0x039a
        L_0x02de:
            int r2 = r19.getType()
            r3 = 110(0x6e, float:1.54E-43)
            if (r2 != r3) goto L_0x02f1
            r2 = r7
            org.mozilla.javascript.ast.FunctionNode r2 = (org.mozilla.javascript.ast.FunctionNode) r2
            boolean r2 = r2.isGenerator()
            if (r2 == 0) goto L_0x02f1
            r16 = 1
        L_0x02f1:
            if (r16 == 0) goto L_0x02fa
            r2 = 20
            r3 = 1
            r0.putIntProp(r2, r3)
            goto L_0x02fb
        L_0x02fa:
            r3 = 1
        L_0x02fb:
            boolean r2 = r6.hasFinally
            if (r2 != 0) goto L_0x0301
            goto L_0x039a
        L_0x0301:
            org.mozilla.javascript.ObjArray r2 = r6.loops
            int r2 = r2.size()
            int r2 = r2 - r3
            r3 = 0
        L_0x0309:
            if (r2 < 0) goto L_0x034a
            org.mozilla.javascript.ObjArray r5 = r6.loops
            java.lang.Object r5 = r5.get(r2)
            org.mozilla.javascript.Node r5 = (org.mozilla.javascript.Node) r5
            int r10 = r5.getType()
            if (r10 == r4) goto L_0x031f
            if (r10 != r15) goto L_0x031c
            goto L_0x031f
        L_0x031c:
            r11 = 130(0x82, float:1.82E-43)
            goto L_0x0347
        L_0x031f:
            if (r10 != r4) goto L_0x032f
            org.mozilla.javascript.ast.Jump r10 = new org.mozilla.javascript.ast.Jump
            r10.<init>(r14)
            org.mozilla.javascript.ast.Jump r5 = (org.mozilla.javascript.ast.Jump) r5
            org.mozilla.javascript.Node r5 = r5.getFinally()
            r10.target = r5
            goto L_0x0334
        L_0x032f:
            org.mozilla.javascript.Node r10 = new org.mozilla.javascript.Node
            r10.<init>(r12)
        L_0x0334:
            if (r3 != 0) goto L_0x0342
            org.mozilla.javascript.Node r3 = new org.mozilla.javascript.Node
            int r5 = r0.getLineno()
            r11 = 130(0x82, float:1.82E-43)
            r3.<init>((int) r11, (int) r5)
            goto L_0x0344
        L_0x0342:
            r11 = 130(0x82, float:1.82E-43)
        L_0x0344:
            r3.addChildToBack(r10)
        L_0x0347:
            int r2 = r2 + -1
            goto L_0x0309
        L_0x034a:
            if (r3 == 0) goto L_0x039a
            org.mozilla.javascript.Node r2 = r0.getFirstChild()
            org.mozilla.javascript.Node r10 = replaceCurrent(r8, r1, r0, r3)
            if (r2 == 0) goto L_0x037c
            if (r16 == 0) goto L_0x0359
            goto L_0x037c
        L_0x0359:
            org.mozilla.javascript.Node r4 = new org.mozilla.javascript.Node
            r0 = 135(0x87, float:1.89E-43)
            r4.<init>((int) r0, (org.mozilla.javascript.Node) r2)
            r3.addChildToFront(r4)
            org.mozilla.javascript.Node r0 = new org.mozilla.javascript.Node
            r1 = 65
            r0.<init>(r1)
            r3.addChildToBack(r0)
            r0 = r18
            r1 = r19
            r2 = r4
            r3 = r21
            r4 = r22
            r5 = r23
            r0.transformCompilationUnit_r(r1, r2, r3, r4, r5)
            goto L_0x03b2
        L_0x037c:
            r3.addChildToBack(r0)
            goto L_0x03b2
        L_0x0380:
            org.mozilla.javascript.ObjArray r1 = r6.loopEnds
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x039a
            org.mozilla.javascript.ObjArray r1 = r6.loopEnds
            java.lang.Object r1 = r1.peek()
            if (r1 != r0) goto L_0x039a
            org.mozilla.javascript.ObjArray r1 = r6.loopEnds
            r1.pop()
            org.mozilla.javascript.ObjArray r1 = r6.loops
            r1.pop()
        L_0x039a:
            r10 = r0
            boolean r0 = r10 instanceof org.mozilla.javascript.ast.Scope
            if (r0 == 0) goto L_0x03a5
            r0 = r10
            org.mozilla.javascript.ast.Scope r0 = (org.mozilla.javascript.ast.Scope) r0
            r3 = r0
            goto L_0x03a6
        L_0x03a5:
            r3 = r9
        L_0x03a6:
            r0 = r18
            r1 = r19
            r2 = r10
            r4 = r22
            r5 = r23
            r0.transformCompilationUnit_r(r1, r2, r3, r4, r5)
        L_0x03b2:
            r0 = r10
            r10 = 0
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NodeTransformer.transformCompilationUnit_r(org.mozilla.javascript.ast.ScriptNode, org.mozilla.javascript.Node, org.mozilla.javascript.ast.Scope, boolean, boolean):void");
    }

    public final void transform(ScriptNode scriptNode, CompilerEnvirons compilerEnvirons) {
        transform(scriptNode, false, compilerEnvirons);
    }

    public void visitCall(Node node, ScriptNode scriptNode) {
    }

    public Node visitLet(boolean z, Node node, Node node2, Node node3) {
        boolean z2;
        Node node4;
        int i;
        Node node5;
        Node node6;
        int i2;
        Node node7;
        Node node8;
        Node node9 = node;
        Node node10 = node2;
        Node node11 = node3;
        Node firstChild = node3.getFirstChild();
        Node next = firstChild.getNext();
        node11.removeChild(firstChild);
        node11.removeChild(next);
        int type = node3.getType();
        int i3 = Token.LETEXPR;
        if (type == 159) {
            z2 = true;
        } else {
            z2 = false;
        }
        int i4 = Token.LET;
        int i5 = 90;
        if (z) {
            if (z2) {
                i2 = 160;
            } else {
                i2 = 130;
            }
            node4 = replaceCurrent(node9, node10, node11, new Node(i2));
            ArrayList arrayList = new ArrayList();
            Node node12 = new Node(67);
            Node firstChild2 = firstChild.getFirstChild();
            while (firstChild2 != null) {
                if (firstChild2.getType() == i3) {
                    List list = (List) firstChild2.getProp(22);
                    Node firstChild3 = firstChild2.getFirstChild();
                    if (firstChild3.getType() == i4) {
                        if (z2) {
                            node7 = new Node(i5, firstChild3.getNext(), next);
                        } else {
                            node7 = new Node(130, new Node((int) Token.EXPR_VOID, firstChild3.getNext()), next);
                        }
                        if (list != null) {
                            arrayList.addAll(list);
                            for (int i6 = 0; i6 < list.size(); i6++) {
                                node12.addChildToBack(new Node((int) Token.VOID, Node.newNumber(0.0d)));
                            }
                        }
                        node8 = firstChild3.getFirstChild();
                    } else {
                        throw Kit.codeBug();
                    }
                } else {
                    node7 = next;
                    node8 = firstChild2;
                }
                if (node8.getType() == 39) {
                    arrayList.add(ScriptRuntime.getIndexObject(node8.getString()));
                    Node firstChild4 = node8.getFirstChild();
                    if (firstChild4 == null) {
                        firstChild4 = new Node((int) Token.VOID, Node.newNumber(0.0d));
                    }
                    node12.addChildToBack(firstChild4);
                    firstChild2 = firstChild2.getNext();
                    next = node7;
                    i3 = Token.LETEXPR;
                    i4 = Token.LET;
                    i5 = 90;
                } else {
                    throw Kit.codeBug();
                }
            }
            node12.putProp(12, arrayList.toArray());
            node4.addChildToBack(new Node(2, node12));
            node4.addChildToBack(new Node(124, next));
            node4.addChildToBack(new Node(3));
        } else {
            if (z2) {
                i = 90;
            } else {
                i = 130;
            }
            node4 = replaceCurrent(node9, node10, node11, new Node(i));
            Node node13 = new Node(90);
            Node firstChild5 = firstChild.getFirstChild();
            while (firstChild5 != null) {
                if (firstChild5.getType() == 159) {
                    Node firstChild6 = firstChild5.getFirstChild();
                    if (firstChild6.getType() == 154) {
                        if (z2) {
                            node5 = new Node(90, firstChild6.getNext(), next);
                        } else {
                            node5 = new Node(130, new Node((int) Token.EXPR_VOID, firstChild6.getNext()), next);
                        }
                        Scope.joinScopes((Scope) firstChild5, (Scope) node11);
                        node6 = firstChild6.getFirstChild();
                    } else {
                        throw Kit.codeBug();
                    }
                } else {
                    node5 = next;
                    node6 = firstChild5;
                }
                if (node6.getType() == 39) {
                    Node newString = Node.newString(node6.getString());
                    newString.setScope((Scope) node11);
                    Node firstChild7 = node6.getFirstChild();
                    if (firstChild7 == null) {
                        firstChild7 = new Node((int) Token.VOID, Node.newNumber(0.0d));
                    }
                    node13.addChildToBack(new Node(56, newString, firstChild7));
                    firstChild5 = firstChild5.getNext();
                    next = node5;
                } else {
                    throw Kit.codeBug();
                }
            }
            if (z2) {
                node4.addChildToBack(node13);
                node11.setType(90);
                node4.addChildToBack(node11);
                node11.addChildToBack(next);
                if (next instanceof Scope) {
                    Scope scope = (Scope) next;
                    Scope parentScope = scope.getParentScope();
                    Scope scope2 = (Scope) node11;
                    scope.setParentScope(scope2);
                    scope2.setParentScope(parentScope);
                }
            } else {
                node4.addChildToBack(new Node((int) Token.EXPR_VOID, node13));
                node11.setType(130);
                node4.addChildToBack(node11);
                node11.addChildrenToBack(next);
                if (next instanceof Scope) {
                    Scope scope3 = (Scope) next;
                    Scope parentScope2 = scope3.getParentScope();
                    Scope scope4 = (Scope) node11;
                    scope3.setParentScope(scope4);
                    scope4.setParentScope(parentScope2);
                }
            }
        }
        return node4;
    }

    public void visitNew(Node node, ScriptNode scriptNode) {
    }

    public final void transform(ScriptNode scriptNode, boolean z, CompilerEnvirons compilerEnvirons) {
        if (compilerEnvirons.getLanguageVersion() >= 200 && scriptNode.isInStrictMode()) {
            z = true;
        }
        transformCompilationUnit(scriptNode, z);
        for (int i = 0; i != scriptNode.getFunctionCount(); i++) {
            transform(scriptNode.getFunctionNode(i), z, compilerEnvirons);
        }
    }
}
