package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ast.ScriptNode;

class Optimizer {
    static final int AnyType = 3;
    static final int NoType = 0;
    static final int NumberType = 1;
    private boolean inDirectCallFunction;
    private boolean parameterUsedInNumberContext;
    OptFunctionNode theFunction;

    private static void buildStatementList_r(Node node, ObjArray objArray) {
        int type = node.getType();
        if (type == 130 || type == 142 || type == 133 || type == 110) {
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
                buildStatementList_r(firstChild, objArray);
            }
            return;
        }
        objArray.add(node);
    }

    private boolean convertParameter(Node node) {
        if (!this.inDirectCallFunction || node.getType() != 55) {
            return false;
        }
        if (!this.theFunction.isParameter(this.theFunction.getVarIndex(node))) {
            return false;
        }
        node.removeProp(8);
        return true;
    }

    private void markDCPNumberContext(Node node) {
        if (this.inDirectCallFunction && node.getType() == 55) {
            if (this.theFunction.isParameter(this.theFunction.getVarIndex(node))) {
                this.parameterUsedInNumberContext = true;
            }
        }
    }

    private void optimizeFunction(OptFunctionNode optFunctionNode) {
        if (!optFunctionNode.fnode.requiresActivation()) {
            this.inDirectCallFunction = optFunctionNode.isTargetOfDirectCall();
            this.theFunction = optFunctionNode;
            ObjArray objArray = new ObjArray();
            buildStatementList_r(optFunctionNode.fnode, objArray);
            int size = objArray.size();
            Node[] nodeArr = new Node[size];
            objArray.toArray(nodeArr);
            Block.runFlowAnalyzes(optFunctionNode, nodeArr);
            if (!optFunctionNode.fnode.requiresActivation()) {
                this.parameterUsedInNumberContext = false;
                for (int i = 0; i < size; i++) {
                    rewriteForNumberVariables(nodeArr[i], 1);
                }
                optFunctionNode.setParameterNumberContext(this.parameterUsedInNumberContext);
            }
        }
    }

    private void rewriteAsObjectChildren(Node node, Node node2) {
        while (node2 != null) {
            Node next = node2.getNext();
            if (rewriteForNumberVariables(node2, 0) == 1 && !convertParameter(node2)) {
                node.removeChild(node2);
                Node node3 = new Node(150, node2);
                if (next == null) {
                    node.addChildToBack(node3);
                } else {
                    node.addChildBefore(node3, next);
                }
            }
            node2 = next;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0191, code lost:
        r9.putIntProp(8, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0194, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0125, code lost:
        r10 = r9.getFirstChild();
        r0 = r10.getNext();
        r1 = rewriteForNumberVariables(r10, 1);
        r5 = rewriteForNumberVariables(r0, 1);
        markDCPNumberContext(r10);
        markDCPNumberContext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x013b, code lost:
        if (r1 != 1) goto L_0x0158;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x013d, code lost:
        if (r5 != 1) goto L_0x0143;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x013f, code lost:
        r9.putIntProp(8, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0142, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0147, code lost:
        if (convertParameter(r0) != false) goto L_0x0157;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0149, code lost:
        r9.removeChild(r0);
        r9.addChildToBack(new org.mozilla.javascript.Node((int) org.mozilla.javascript.Token.TO_DOUBLE, r0));
        r9.putIntProp(8, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0157, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0158, code lost:
        if (r5 != 1) goto L_0x016f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x015e, code lost:
        if (convertParameter(r10) != false) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0160, code lost:
        r9.removeChild(r10);
        r9.addChildToFront(new org.mozilla.javascript.Node((int) org.mozilla.javascript.Token.TO_DOUBLE, r10));
        r9.putIntProp(8, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x016e, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0173, code lost:
        if (convertParameter(r10) != false) goto L_0x0180;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0175, code lost:
        r9.removeChild(r10);
        r9.addChildToFront(new org.mozilla.javascript.Node((int) org.mozilla.javascript.Token.TO_DOUBLE, r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0184, code lost:
        if (convertParameter(r0) != false) goto L_0x0191;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0186, code lost:
        r9.removeChild(r0);
        r9.addChildToBack(new org.mozilla.javascript.Node((int) org.mozilla.javascript.Token.TO_DOUBLE, r0));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int rewriteForNumberVariables(org.mozilla.javascript.Node r9, int r10) {
        /*
            r8 = this;
            int r0 = r9.getType()
            r1 = 40
            r2 = 8
            r3 = 0
            r4 = 1
            if (r0 == r1) goto L_0x02a2
            r1 = 134(0x86, float:1.88E-43)
            if (r0 == r1) goto L_0x0294
            r1 = 141(0x8d, float:1.98E-43)
            r5 = 150(0x96, float:2.1E-43)
            if (r0 == r1) goto L_0x024a
            r1 = 157(0x9d, float:2.2E-43)
            r6 = 151(0x97, float:2.12E-43)
            if (r0 == r1) goto L_0x01eb
            r1 = 55
            if (r0 == r1) goto L_0x01c6
            r10 = 56
            if (r0 == r10) goto L_0x01eb
            r10 = 107(0x6b, float:1.5E-43)
            if (r0 == r10) goto L_0x0195
            r10 = 108(0x6c, float:1.51E-43)
            if (r0 == r10) goto L_0x0195
            switch(r0) {
                case 9: goto L_0x0125;
                case 10: goto L_0x0125;
                case 11: goto L_0x0125;
                default: goto L_0x002f;
            }
        L_0x002f:
            r10 = 2
            switch(r0) {
                case 14: goto L_0x00de;
                case 15: goto L_0x00de;
                case 16: goto L_0x00de;
                case 17: goto L_0x00de;
                case 18: goto L_0x0125;
                case 19: goto L_0x0125;
                default: goto L_0x0033;
            }
        L_0x0033:
            switch(r0) {
                case 21: goto L_0x009d;
                case 22: goto L_0x0125;
                case 23: goto L_0x0125;
                case 24: goto L_0x0125;
                case 25: goto L_0x0125;
                default: goto L_0x0036;
            }
        L_0x0036:
            switch(r0) {
                case 36: goto L_0x006e;
                case 37: goto L_0x024a;
                case 38: goto L_0x0041;
                default: goto L_0x0039;
            }
        L_0x0039:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            r8.rewriteAsObjectChildren(r9, r10)
            return r3
        L_0x0041:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            org.mozilla.javascript.Node r0 = r10.getFirstChild()
            r8.rewriteAsObjectChildren(r10, r0)
            org.mozilla.javascript.Node r10 = r10.getNext()
            r0 = 9
            java.lang.Object r0 = r9.getProp(r0)
            org.mozilla.javascript.optimizer.OptFunctionNode r0 = (org.mozilla.javascript.optimizer.OptFunctionNode) r0
            if (r0 == 0) goto L_0x006a
        L_0x005a:
            if (r10 == 0) goto L_0x006d
            int r9 = r8.rewriteForNumberVariables(r10, r4)
            if (r9 != r4) goto L_0x0065
            r8.markDCPNumberContext(r10)
        L_0x0065:
            org.mozilla.javascript.Node r10 = r10.getNext()
            goto L_0x005a
        L_0x006a:
            r8.rewriteAsObjectChildren(r9, r10)
        L_0x006d:
            return r3
        L_0x006e:
            org.mozilla.javascript.Node r0 = r9.getFirstChild()
            org.mozilla.javascript.Node r1 = r0.getNext()
            int r6 = r8.rewriteForNumberVariables(r0, r4)
            if (r6 != r4) goto L_0x008d
            boolean r6 = r8.convertParameter(r0)
            if (r6 != 0) goto L_0x008d
            r9.removeChild(r0)
            org.mozilla.javascript.Node r6 = new org.mozilla.javascript.Node
            r6.<init>((int) r5, (org.mozilla.javascript.Node) r0)
            r9.addChildToFront(r6)
        L_0x008d:
            int r0 = r8.rewriteForNumberVariables(r1, r4)
            if (r0 != r4) goto L_0x009c
            boolean r0 = r8.convertParameter(r1)
            if (r0 != 0) goto L_0x009c
            r9.putIntProp(r2, r10)
        L_0x009c:
            return r3
        L_0x009d:
            org.mozilla.javascript.Node r0 = r9.getFirstChild()
            org.mozilla.javascript.Node r1 = r0.getNext()
            int r5 = r8.rewriteForNumberVariables(r0, r4)
            int r6 = r8.rewriteForNumberVariables(r1, r4)
            boolean r0 = r8.convertParameter(r0)
            if (r0 == 0) goto L_0x00c0
            boolean r0 = r8.convertParameter(r1)
            if (r0 == 0) goto L_0x00ba
            return r3
        L_0x00ba:
            if (r6 != r4) goto L_0x00dd
            r9.putIntProp(r2, r10)
            goto L_0x00dd
        L_0x00c0:
            boolean r0 = r8.convertParameter(r1)
            if (r0 == 0) goto L_0x00cc
            if (r5 != r4) goto L_0x00dd
            r9.putIntProp(r2, r4)
            goto L_0x00dd
        L_0x00cc:
            if (r5 != r4) goto L_0x00d8
            if (r6 != r4) goto L_0x00d4
            r9.putIntProp(r2, r3)
            return r4
        L_0x00d4:
            r9.putIntProp(r2, r4)
            goto L_0x00dd
        L_0x00d8:
            if (r6 != r4) goto L_0x00dd
            r9.putIntProp(r2, r10)
        L_0x00dd:
            return r3
        L_0x00de:
            org.mozilla.javascript.Node r0 = r9.getFirstChild()
            org.mozilla.javascript.Node r1 = r0.getNext()
            int r5 = r8.rewriteForNumberVariables(r0, r4)
            int r6 = r8.rewriteForNumberVariables(r1, r4)
            r8.markDCPNumberContext(r0)
            r8.markDCPNumberContext(r1)
            boolean r0 = r8.convertParameter(r0)
            if (r0 == 0) goto L_0x0107
            boolean r0 = r8.convertParameter(r1)
            if (r0 == 0) goto L_0x0101
            return r3
        L_0x0101:
            if (r6 != r4) goto L_0x0124
            r9.putIntProp(r2, r10)
            goto L_0x0124
        L_0x0107:
            boolean r0 = r8.convertParameter(r1)
            if (r0 == 0) goto L_0x0113
            if (r5 != r4) goto L_0x0124
            r9.putIntProp(r2, r4)
            goto L_0x0124
        L_0x0113:
            if (r5 != r4) goto L_0x011f
            if (r6 != r4) goto L_0x011b
            r9.putIntProp(r2, r3)
            goto L_0x0124
        L_0x011b:
            r9.putIntProp(r2, r4)
            goto L_0x0124
        L_0x011f:
            if (r6 != r4) goto L_0x0124
            r9.putIntProp(r2, r10)
        L_0x0124:
            return r3
        L_0x0125:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            org.mozilla.javascript.Node r0 = r10.getNext()
            int r1 = r8.rewriteForNumberVariables(r10, r4)
            int r5 = r8.rewriteForNumberVariables(r0, r4)
            r8.markDCPNumberContext(r10)
            r8.markDCPNumberContext(r0)
            if (r1 != r4) goto L_0x0158
            if (r5 != r4) goto L_0x0143
            r9.putIntProp(r2, r3)
            return r4
        L_0x0143:
            boolean r10 = r8.convertParameter(r0)
            if (r10 != 0) goto L_0x0157
            r9.removeChild(r0)
            org.mozilla.javascript.Node r10 = new org.mozilla.javascript.Node
            r10.<init>((int) r6, (org.mozilla.javascript.Node) r0)
            r9.addChildToBack(r10)
            r9.putIntProp(r2, r3)
        L_0x0157:
            return r4
        L_0x0158:
            if (r5 != r4) goto L_0x016f
            boolean r0 = r8.convertParameter(r10)
            if (r0 != 0) goto L_0x016e
            r9.removeChild(r10)
            org.mozilla.javascript.Node r0 = new org.mozilla.javascript.Node
            r0.<init>((int) r6, (org.mozilla.javascript.Node) r10)
            r9.addChildToFront(r0)
            r9.putIntProp(r2, r3)
        L_0x016e:
            return r4
        L_0x016f:
            boolean r1 = r8.convertParameter(r10)
            if (r1 != 0) goto L_0x0180
            r9.removeChild(r10)
            org.mozilla.javascript.Node r1 = new org.mozilla.javascript.Node
            r1.<init>((int) r6, (org.mozilla.javascript.Node) r10)
            r9.addChildToFront(r1)
        L_0x0180:
            boolean r10 = r8.convertParameter(r0)
            if (r10 != 0) goto L_0x0191
            r9.removeChild(r0)
            org.mozilla.javascript.Node r10 = new org.mozilla.javascript.Node
            r10.<init>((int) r6, (org.mozilla.javascript.Node) r0)
            r9.addChildToBack(r10)
        L_0x0191:
            r9.putIntProp(r2, r3)
            return r4
        L_0x0195:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            int r0 = r8.rewriteForNumberVariables(r10, r4)
            int r5 = r10.getType()
            if (r5 != r1) goto L_0x01b3
            if (r0 != r4) goto L_0x01b2
            boolean r0 = r8.convertParameter(r10)
            if (r0 != 0) goto L_0x01b2
            r9.putIntProp(r2, r3)
            r8.markDCPNumberContext(r10)
            return r4
        L_0x01b2:
            return r3
        L_0x01b3:
            int r9 = r10.getType()
            r1 = 36
            if (r9 == r1) goto L_0x01c5
            int r9 = r10.getType()
            r10 = 33
            if (r9 != r10) goto L_0x01c4
            goto L_0x01c5
        L_0x01c4:
            return r3
        L_0x01c5:
            return r0
        L_0x01c6:
            org.mozilla.javascript.optimizer.OptFunctionNode r0 = r8.theFunction
            int r0 = r0.getVarIndex(r9)
            boolean r1 = r8.inDirectCallFunction
            if (r1 == 0) goto L_0x01de
            org.mozilla.javascript.optimizer.OptFunctionNode r1 = r8.theFunction
            boolean r1 = r1.isParameter(r0)
            if (r1 == 0) goto L_0x01de
            if (r10 != r4) goto L_0x01de
            r9.putIntProp(r2, r3)
            return r4
        L_0x01de:
            org.mozilla.javascript.optimizer.OptFunctionNode r10 = r8.theFunction
            boolean r10 = r10.isNumberVar(r0)
            if (r10 == 0) goto L_0x01ea
            r9.putIntProp(r2, r3)
            return r4
        L_0x01ea:
            return r3
        L_0x01eb:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            org.mozilla.javascript.Node r10 = r10.getNext()
            int r0 = r8.rewriteForNumberVariables(r10, r4)
            org.mozilla.javascript.optimizer.OptFunctionNode r1 = r8.theFunction
            int r1 = r1.getVarIndex(r9)
            boolean r7 = r8.inDirectCallFunction
            if (r7 == 0) goto L_0x021a
            org.mozilla.javascript.optimizer.OptFunctionNode r7 = r8.theFunction
            boolean r7 = r7.isParameter(r1)
            if (r7 == 0) goto L_0x021a
            if (r0 != r4) goto L_0x0219
            boolean r0 = r8.convertParameter(r10)
            if (r0 != 0) goto L_0x0215
            r9.putIntProp(r2, r3)
            return r4
        L_0x0215:
            r8.markDCPNumberContext(r10)
            return r3
        L_0x0219:
            return r0
        L_0x021a:
            org.mozilla.javascript.optimizer.OptFunctionNode r7 = r8.theFunction
            boolean r1 = r7.isNumberVar(r1)
            if (r1 == 0) goto L_0x0236
            if (r0 == r4) goto L_0x022f
            r9.removeChild(r10)
            org.mozilla.javascript.Node r0 = new org.mozilla.javascript.Node
            r0.<init>((int) r6, (org.mozilla.javascript.Node) r10)
            r9.addChildToBack(r0)
        L_0x022f:
            r9.putIntProp(r2, r3)
            r8.markDCPNumberContext(r10)
            return r4
        L_0x0236:
            if (r0 != r4) goto L_0x0249
            boolean r0 = r8.convertParameter(r10)
            if (r0 != 0) goto L_0x0249
            r9.removeChild(r10)
            org.mozilla.javascript.Node r0 = new org.mozilla.javascript.Node
            r0.<init>((int) r5, (org.mozilla.javascript.Node) r10)
            r9.addChildToBack(r0)
        L_0x0249:
            return r3
        L_0x024a:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            org.mozilla.javascript.Node r0 = r10.getNext()
            org.mozilla.javascript.Node r1 = r0.getNext()
            int r6 = r8.rewriteForNumberVariables(r10, r4)
            if (r6 != r4) goto L_0x026d
            boolean r6 = r8.convertParameter(r10)
            if (r6 != 0) goto L_0x026d
            r9.removeChild(r10)
            org.mozilla.javascript.Node r6 = new org.mozilla.javascript.Node
            r6.<init>((int) r5, (org.mozilla.javascript.Node) r10)
            r9.addChildToFront(r6)
        L_0x026d:
            int r10 = r8.rewriteForNumberVariables(r0, r4)
            if (r10 != r4) goto L_0x027c
            boolean r10 = r8.convertParameter(r0)
            if (r10 != 0) goto L_0x027c
            r9.putIntProp(r2, r4)
        L_0x027c:
            int r10 = r8.rewriteForNumberVariables(r1, r4)
            if (r10 != r4) goto L_0x0293
            boolean r10 = r8.convertParameter(r1)
            if (r10 != 0) goto L_0x0293
            r9.removeChild(r1)
            org.mozilla.javascript.Node r10 = new org.mozilla.javascript.Node
            r10.<init>((int) r5, (org.mozilla.javascript.Node) r1)
            r9.addChildToBack(r10)
        L_0x0293:
            return r3
        L_0x0294:
            org.mozilla.javascript.Node r10 = r9.getFirstChild()
            int r10 = r8.rewriteForNumberVariables(r10, r4)     // Catch:{ all -> 0x02a6 }
            if (r10 != r4) goto L_0x02a1
            r9.putIntProp(r2, r3)
        L_0x02a1:
            return r3
        L_0x02a2:
            r9.putIntProp(r2, r3)
            return r4
        L_0x02a6:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.Optimizer.rewriteForNumberVariables(org.mozilla.javascript.Node, int):int");
    }

    public void optimize(ScriptNode scriptNode) {
        int functionCount = scriptNode.getFunctionCount();
        for (int i = 0; i != functionCount; i++) {
            optimizeFunction(OptFunctionNode.get(scriptNode, i));
        }
    }
}
