package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ScriptNode;

public class ClassCompiler {
    private CompilerEnvirons compilerEnv;
    private String mainMethodClassName;
    private Class<?> targetExtends;
    private Class<?>[] targetImplements;

    public ClassCompiler(CompilerEnvirons compilerEnvirons) {
        if (compilerEnvirons != null) {
            this.compilerEnv = compilerEnvirons;
            this.mainMethodClassName = "org.mozilla.javascript.optimizer.OptRuntime";
            return;
        }
        throw new IllegalArgumentException();
    }

    public Object[] compileToClassFiles(String str, String str2, int i, String str3) {
        boolean z;
        String str4;
        ScriptNode transformTree = new IRFactory(this.compilerEnv).transformTree(new Parser(this.compilerEnv).parse(str, str2, i));
        Class<?> targetExtends2 = getTargetExtends();
        Class[] targetImplements2 = getTargetImplements();
        if (targetImplements2 == null && targetExtends2 == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            str4 = str3;
        } else {
            str4 = makeAuxiliaryClassName(str3, "1");
        }
        Codegen codegen = new Codegen();
        codegen.setMainMethodClass(this.mainMethodClassName);
        Object compileToClassFile = codegen.compileToClassFile(this.compilerEnv, str4, transformTree, transformTree.getEncodedSource(), false);
        if (z) {
            return new Object[]{str4, compileToClassFile};
        }
        int functionCount = transformTree.getFunctionCount();
        ObjToIntMap objToIntMap = new ObjToIntMap(functionCount);
        for (int i2 = 0; i2 != functionCount; i2++) {
            FunctionNode functionNode = transformTree.getFunctionNode(i2);
            String name = functionNode.getName();
            if (!(name == null || name.length() == 0)) {
                objToIntMap.put(name, functionNode.getParamCount());
            }
        }
        if (targetExtends2 == null) {
            targetExtends2 = ScriptRuntime.ObjectClass;
        }
        return new Object[]{str3, JavaAdapter.createAdapterCode(objToIntMap, str3, targetExtends2, targetImplements2, str4), str4, compileToClassFile};
    }

    public CompilerEnvirons getCompilerEnv() {
        return this.compilerEnv;
    }

    public String getMainMethodClass() {
        return this.mainMethodClassName;
    }

    public Class<?> getTargetExtends() {
        return this.targetExtends;
    }

    public Class<?>[] getTargetImplements() {
        Class<?>[] clsArr = this.targetImplements;
        if (clsArr == null) {
            return null;
        }
        return (Class[]) clsArr.clone();
    }

    public String makeAuxiliaryClassName(String str, String str2) {
        return y2.x(str, str2);
    }

    public void setMainMethodClass(String str) {
        this.mainMethodClassName = str;
    }

    public void setTargetExtends(Class<?> cls) {
        this.targetExtends = cls;
    }

    public void setTargetImplements(Class<?>[] clsArr) {
        this.targetImplements = clsArr == null ? null : (Class[]) clsArr.clone();
    }
}
