package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.eval.CatscriptRuntime;
import edu.montana.csci.csci468.parser.CatscriptType;
import edu.montana.csci.csci468.parser.ErrorType;
import edu.montana.csci.csci468.parser.ParseError;
import edu.montana.csci.csci468.parser.SymbolTable;
import edu.montana.csci.csci468.parser.expressions.Expression;
import org.objectweb.asm.Opcodes;

public class AssignmentStatement extends Statement {
    private Expression expression;
    private String variableName;
    private CatscriptType type;
    private boolean global;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = addChild(expression);
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        expression.validate(symbolTable);
        type = symbolTable.getSymbolType(variableName);
        global = symbolTable.isSymbolGlobal(variableName);

        CatscriptType symbolType = symbolTable.getSymbolType(getVariableName());
        if (symbolType == null) {
            addError(ErrorType.UNKNOWN_NAME);
        } else {
            // TODO - verify compatibility of types
            CatscriptType exprType = expression.getType();
            if (!exprType.isAssignableFrom(symbolType)) {
                addError(ErrorType.INCOMPATIBLE_TYPES, exprType, symbolType);
            }
        }
    }

    //==============================================================
    // Implementation
    //==============================================================
    @Override
    public void execute(CatscriptRuntime runtime) {
        //super.execute(runtime);
        runtime.setValue(variableName,expression.evaluate(runtime));
    }

    @Override
    public void transpile(StringBuilder javascript) {
        super.transpile(javascript);
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        if (global) {
            String descriptor;
            if (type.equals(CatscriptType.INT)) {
                descriptor = "I";
            } else if (type.equals(CatscriptType.BOOLEAN)) {
                descriptor = "Z";
            } else if (type.equals(CatscriptType.STRING)) {
                descriptor = "Ljava/lang/String;";
            } else {
                descriptor = "Ljava/lang/Object;";
                if(expression.getType().equals(CatscriptType.INT) || expression.getType().equals(CatscriptType.BOOLEAN)){
                    box(code,expression.getType());
                }
            }

            code.addVarInstruction(Opcodes.ALOAD, 0);

            expression.compile(code);
            code.addFieldInstruction(Opcodes.PUTFIELD, variableName, descriptor, code.getProgramInternalName());

        } else {
            expression.compile(code);
            int slot = code.resolveLocalStorageSlotFor(variableName);

            if (type.equals(CatscriptType.OBJECT) &&
                    (expression.getType().equals(CatscriptType.INT) || expression.getType().equals(CatscriptType.BOOLEAN))) {
                box(code, expression.getType());
            }

            if (expression.getType().equals(CatscriptType.INT) || expression.getType().equals(CatscriptType.BOOLEAN)) {
                code.addVarInstruction(Opcodes.ISTORE, slot);
            } else {
                code.addVarInstruction(Opcodes.ASTORE, slot);
            }
        }
    }

}
