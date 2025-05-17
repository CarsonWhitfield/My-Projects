package edu.montana.csci.csci468.parser.statements;

import edu.montana.csci.csci468.bytecode.ByteCodeGenerator;
import edu.montana.csci.csci468.eval.CatscriptRuntime;
import edu.montana.csci.csci468.eval.ReturnException;
import edu.montana.csci.csci468.parser.*;
import edu.montana.csci.csci468.parser.expressions.Expression;
import org.objectweb.asm.Opcodes;
import org.w3c.dom.Node;

public class ReturnStatement extends Statement {
    private Expression expression;
    private FunctionDefinitionStatement function;
    public void setExpression(Expression parseExpression) {
        this.expression = addChild(parseExpression);
    }

    public void setFunctionDefinition(FunctionDefinitionStatement func){
        this.function = func;
    }

    public Expression getExpression() {
        return expression;
    }

    public FunctionDefinitionStatement getFunctionDefinitionStatement() {
        // TODO implement - recurse up the parent hierarchy and find a FunctionDefinitionStatement
        // use the `instanceof` operator in java
        // if there are none, return null
        ParseElement parent = getParent();
        while (parent != null){
            if(parent instanceof FunctionDefinitionStatement){
                return (FunctionDefinitionStatement) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    @Override
    public void validate(SymbolTable symbolTable) {
        FunctionDefinitionStatement func = getFunctionDefinitionStatement();
        if (func == null) {
            addError(ErrorType.INVALID_RETURN_STATEMENT);
            return;
        }

        if (expression != null) {
            expression.validate(symbolTable);
            if (!function.getType().isAssignableFrom(expression.getType())) {
                expression.addError(ErrorType.INCOMPATIBLE_TYPES);
            }
        } else {
            if (!function.getType().equals(CatscriptType.VOID)) {
                addError(ErrorType.INCOMPATIBLE_TYPES);
            }
        }
    }


    //==============================================================
    // Implementation
    //==============================================================
    @Override
    public void execute(CatscriptRuntime runtime) {
        Object val = null;
        if(expression != null){
            val = expression.evaluate(runtime);
        }
        throw new ReturnException(val);
    }

    @Override
    public void transpile(StringBuilder javascript) {
        super.transpile(javascript);
    }

    @Override
    public void compile(ByteCodeGenerator code) {
        if (expression == null) {
            code.addInstruction(Opcodes.RETURN);
        } else {
            expression.compile(code);

            CatscriptType functionType = getFunctionDefinitionStatement().getType();
            CatscriptType returnType = expression.getType();

            if (functionType.equals(CatscriptType.OBJECT) && (returnType.equals(CatscriptType.INT) || returnType.equals(CatscriptType.BOOLEAN))) {
                box(code, returnType);
            }

            if (functionType.equals(CatscriptType.INT) || functionType.equals(CatscriptType.BOOLEAN)) {
                code.addInstruction(Opcodes.IRETURN);
            } else {
                code.addInstruction(Opcodes.ARETURN);
            }
        }
    }

}