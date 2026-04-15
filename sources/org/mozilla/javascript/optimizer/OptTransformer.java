package org.mozilla.javascript.optimizer;

import java.util.Map;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.NodeTransformer;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ast.ScriptNode;

class OptTransformer extends NodeTransformer {
    private ObjArray directCallTargets;
    private Map<String, OptFunctionNode> possibleDirectCalls;

    public OptTransformer(Map<String, OptFunctionNode> map, ObjArray objArray) {
        this.possibleDirectCalls = map;
        this.directCallTargets = objArray;
    }

    private void detectDirectCall(Node node, ScriptNode scriptNode) {
        String str;
        OptFunctionNode optFunctionNode;
        if (scriptNode.getType() == 110) {
            Node firstChild = node.getFirstChild();
            Node next = firstChild.getNext();
            int i = 0;
            while (next != null) {
                next = next.getNext();
                i++;
            }
            if (i == 0) {
                OptFunctionNode.get(scriptNode).itsContainsCalls0 = true;
            }
            if (this.possibleDirectCalls != null) {
                if (firstChild.getType() == 39) {
                    str = firstChild.getString();
                } else if (firstChild.getType() == 33) {
                    str = firstChild.getFirstChild().getNext().getString();
                } else if (firstChild.getType() != 34) {
                    str = null;
                } else {
                    throw Kit.codeBug();
                }
                if (str != null && (optFunctionNode = this.possibleDirectCalls.get(str)) != null && i == optFunctionNode.fnode.getParamCount() && !optFunctionNode.fnode.requiresActivation() && i <= 32) {
                    node.putProp(9, optFunctionNode);
                    if (!optFunctionNode.isTargetOfDirectCall()) {
                        int size = this.directCallTargets.size();
                        this.directCallTargets.add(optFunctionNode);
                        optFunctionNode.setDirectTargetIndex(size);
                    }
                }
            }
        }
    }

    public void visitCall(Node node, ScriptNode scriptNode) {
        detectDirectCall(node, scriptNode);
        super.visitCall(node, scriptNode);
    }

    public void visitNew(Node node, ScriptNode scriptNode) {
        detectDirectCall(node, scriptNode);
        super.visitNew(node, scriptNode);
    }
}
