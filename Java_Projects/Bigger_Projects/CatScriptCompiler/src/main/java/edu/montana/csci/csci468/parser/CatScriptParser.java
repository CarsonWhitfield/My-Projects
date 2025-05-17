package edu.montana.csci.csci468.parser;

import edu.montana.csci.csci468.parser.expressions.*;
import edu.montana.csci.csci468.parser.statements.*;
import edu.montana.csci.csci468.tokenizer.CatScriptTokenizer;
import edu.montana.csci.csci468.tokenizer.Token;
import edu.montana.csci.csci468.tokenizer.TokenList;
import edu.montana.csci.csci468.tokenizer.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static edu.montana.csci.csci468.tokenizer.TokenType.*;

public class CatScriptParser {

    private TokenList tokens;
    private FunctionDefinitionStatement currentFunctionDefinition;

    public CatScriptProgram parse(String source) {
        tokens = new CatScriptTokenizer(source).getTokens();

        // first parse an expression
        CatScriptProgram program = new CatScriptProgram();
        program.setStart(tokens.getCurrentToken());
        Expression expression = null;
        try {
            expression = parseExpression();
        } catch(RuntimeException re) {
            // ignore :)
        }
        if (expression == null || tokens.hasMoreTokens()) {
            tokens.reset();
            while (tokens.hasMoreTokens()) {
                program.addStatement(parseProgramStatement());
            }
        } else {
            program.setExpression(expression);
        }

        program.setEnd(tokens.getCurrentToken());
        return program;
    }

    public CatScriptProgram parseAsExpression(String source) {
        tokens = new CatScriptTokenizer(source).getTokens();
        CatScriptProgram program = new CatScriptProgram();
        program.setStart(tokens.getCurrentToken());
        Expression expression = parseExpression();
        program.setExpression(expression);
        program.setEnd(tokens.getCurrentToken());
        return program;
    }

    //============================================================
    //  Statements
    //============================================================

    private Statement parseProgramStatement() {
        Statement funcDefStmt = praseFunctionDefinitionStatement();
        if (funcDefStmt != null) {
            return funcDefStmt;
        }
        return parseStatement();
    }

    private Statement praseFunctionDefinitionStatement(){
        if(tokens.match(FUNCTION)){
            try {
                currentFunctionDefinition = new FunctionDefinitionStatement();
                Token start = tokens.consumeToken();
                currentFunctionDefinition.setStart(start);
                Token functionName = require(IDENTIFIER, currentFunctionDefinition);
                currentFunctionDefinition.setName(functionName.getStringValue());
                require(LEFT_PAREN, currentFunctionDefinition);
                // parameter_list
                while (!tokens.match(RIGHT_PAREN)) {
                    if (tokens.match(EOF)) {
                        break;
                    } else {
                        Token paramToken = tokens.consumeToken();
                        if (tokens.match(COLON)) {
                            tokens.consumeToken();
                            TypeLiteral paramType = parseTypeLiteral();
                            currentFunctionDefinition.addParameter(paramToken.getStringValue(), paramType);
                            if(tokens.match(COMMA)) {
                                tokens.consumeToken();
                            }
                        } else {
                            TypeLiteral objectType = new TypeLiteral();
                            objectType.setType(CatscriptType.OBJECT);
                            currentFunctionDefinition.addParameter(paramToken.getStringValue(), objectType);
                            if (tokens.match(COMMA)) {
                                tokens.consumeToken();
                            }
                        }

                    }

                }
                require(RIGHT_PAREN, currentFunctionDefinition);
                if (tokens.match(COLON)) {
                    tokens.consumeToken();
                    TypeLiteral functionType = parseTypeLiteral();
                    currentFunctionDefinition.setType(functionType);

                } else {
                    TypeLiteral voidType = new TypeLiteral();
                    voidType.setType(CatscriptType.VOID);
                    currentFunctionDefinition.setType(voidType);
                }
                require(LEFT_BRACE, currentFunctionDefinition);
                List<Statement> functionBodyStatements = new LinkedList<>();
                while (!tokens.match(RIGHT_BRACE)) {
                    if (tokens.match(EOF)) {
                        break;
                    } else {
                        functionBodyStatements.add(parseFunctionBodyStatement());
                    }
                }
                currentFunctionDefinition.setBody(functionBodyStatements);

                if (tokens.match(EOF)) {
                    currentFunctionDefinition.addError(ErrorType.UNTERMINATED_ARG_LIST);
                }
                currentFunctionDefinition.setEnd(require(RIGHT_BRACE, currentFunctionDefinition));
                return currentFunctionDefinition;
            } finally {
                currentFunctionDefinition = null;
            }
        }
        return null;
    }

    private TypeLiteral parseTypeLiteral(){
        Token currentToken = tokens.getCurrentToken();
        if(currentToken.getType() == IDENTIFIER){
            String stringVal = currentToken.getStringValue();
            TypeLiteral typeLiteral = new TypeLiteral();
            if(stringVal.equals("int")){
                typeLiteral.setToken(currentToken);
                typeLiteral.setType(CatscriptType.INT);
                tokens.consumeToken();
                return typeLiteral;
            }
            else if(stringVal.equals("string")){
                typeLiteral.setToken(currentToken);
                typeLiteral.setType(CatscriptType.STRING);
                tokens.consumeToken();
                return typeLiteral;
            }
            else if(stringVal.equals("bool")){
                typeLiteral.setToken(currentToken);
                typeLiteral.setType(CatscriptType.BOOLEAN);
                tokens.consumeToken();
                return typeLiteral;
            }
            else if(stringVal.equals("object")){
                typeLiteral.setToken(currentToken);
                typeLiteral.setType(CatscriptType.OBJECT);
                tokens.consumeToken();
                return typeLiteral;
            }
            else if(stringVal.equals("list")){
                tokens.consumeToken();
                if(tokens.match(LEFT_BRACE)){
                    typeLiteral.setType(CatscriptType.getListType(CatscriptType.OBJECT));
                    return typeLiteral;
                }
                if(tokens.match(LESS)){
                    tokens.consumeToken();
                    TypeLiteral componentTypeLiteral = parseTypeLiteral();
                    CatscriptType componentType = componentTypeLiteral.getType();


                    componentTypeLiteral.setType(CatscriptType.getListType(componentType));
                    parseTypeLiteral();
                    require(GREATER, componentTypeLiteral);
                    return componentTypeLiteral;
                }

            }

        }
        return null;
    }

    private Statement parseFunctionBodyStatement(){
        return parseStatement();
    }
    private Statement parseStatement(){
        Statement printStnt = parsePrintStatement();
        if (printStnt != null){
            return printStnt;
        }
        Statement varStatement = parseVarStatement();
        if (varStatement != null){
            return varStatement;
        }
        Statement returnStatment = parseReturnStatement();
        if (returnStatment != null){
            return returnStatment;
        }

        Statement forStmt = parseForStatement();
        if (forStmt != null) {
            return forStmt;
        }

        Statement ifStmt = parseIfStatement();
        if (ifStmt != null) {
            return ifStmt;
        }

        Statement assignmentOrFunctionCall = parseAssignmentOrFunction();
        if (assignmentOrFunctionCall != null){
            return assignmentOrFunctionCall;
        }
        return new SyntaxErrorStatement(tokens.consumeToken());
    }

    private Statement parseReturnStatement(){
        if (tokens.match(RETURN)){
            ReturnStatement returnStatement = new ReturnStatement();
            returnStatement.setStart(tokens.consumeToken());
            returnStatement.setFunctionDefinition(currentFunctionDefinition);
            if(tokens.match(RIGHT_BRACE)){
                returnStatement.setEnd(tokens.getCurrentToken());
                return returnStatement;
            }
            else {
                returnStatement.setExpression(parseExpression());
                returnStatement.setEnd(tokens.getCurrentToken());
                return returnStatement;
            }
        }
        return null;


    }


    private Statement parseAssignmentOrFunction(){
        if (tokens.match(IDENTIFIER)){
            Token id = tokens.consumeToken();

            if(tokens.match(LEFT_PAREN)){
                FunctionCallExpression fce = praseFunctionCallExpression(id);
                FunctionCallStatement fcs = new FunctionCallStatement(fce);
                return fcs;
            }else{
                AssignmentStatement assignment = new AssignmentStatement();
                assignment.setVariableName(id.getStringValue());
                require(EQUAL, assignment);
                assignment.setExpression(parseExpression());
                return assignment;
            }
        } else {
            return null;
        }
    }

    public FunctionCallExpression praseFunctionCallExpression(Token id){
        List<Expression> args = new ArrayList<>();

        tokens.consumeToken();
        if (!tokens.match(RIGHT_PAREN)) {
            do {
                args.add(parseExpression());
            } while (tokens.matchAndConsume(COMMA));
        }
        FunctionCallExpression funCall = new FunctionCallExpression(id.getStringValue(), args);
        Token end = require(RIGHT_PAREN, funCall);
        funCall.setStart(id);
        funCall.setEnd(end);
        return funCall;
    }

    private Statement parsePrintStatement() {
        if (tokens.match(PRINT)) {

            PrintStatement printStatement = new PrintStatement();
            printStatement.setStart(tokens.consumeToken());

            require(LEFT_PAREN, printStatement);

            Expression expr = parseExpression();
            printStatement.setExpression(expr);

            printStatement.setEnd(require(RIGHT_PAREN, printStatement));

            return printStatement;
        } else {
            return null;
        }
    }

    private Statement parseVarStatement(){
        if(tokens.match(VAR)){
            VariableStatement variableStatement = new VariableStatement();
            variableStatement.setStart(tokens.consumeToken());
            String identifierString = tokens.consumeToken().getStringValue();

            if(tokens.match(COLON)){
                tokens.consumeToken();
                String explicitIdentifierString = tokens.consumeToken().getStringValue();
                CatscriptType variableType = parseTypeExpression(explicitIdentifierString);
                variableStatement.setExplicitType(variableType);

            }

            require(EQUAL,variableStatement);
            variableStatement.setVariableName(identifierString);
            Expression endExp = parseExpression();
            variableStatement.setExpression(endExp);
            variableStatement.setEnd(endExp.getEnd());

            return variableStatement;
        }
        return null;

    }

    private CatscriptType parseTypeExpression(String explicitIdentifierString) {
        if (explicitIdentifierString.equals("int")) {
            return CatscriptType.INT;
        } else if (explicitIdentifierString.equals("bool")) {
            return CatscriptType.BOOLEAN;
        } else if (explicitIdentifierString.equals("string")) {
            return CatscriptType.STRING;
        } else if (explicitIdentifierString.equals("object")) {
            return CatscriptType.OBJECT;
        } else if (explicitIdentifierString.equals("list")) {
            if (tokens.match(LESS)) {
                tokens.consumeToken();
                String listType = tokens.consumeToken().getStringValue();
                CatscriptType elementType = parseTypeExpression(listType);

                if (tokens.match(GREATER)) {
                    tokens.consumeToken();
                    return CatscriptType.getListType(elementType);
                } else {
                    //System.out.println("Error: Missing closing '>' in list type declaration.");
                    return null;
                }
            } else {
                //System.out.println("Error: List type missing '<Type>' syntax.");
                return null;
            }
        }
        //System.out.println("Error: Unknown type '" + explicitIdentifierString + "'");
        return null;
    }



    private Statement parseForStatement(){
        if (tokens.match(FOR)) {
            ForStatement forStatement = new ForStatement();
            forStatement.setStart(tokens.consumeToken());


            require(LEFT_PAREN, forStatement);


            Token variableToken = require(IDENTIFIER, forStatement);
            forStatement.setVariableName(variableToken.getStringValue());


            require(IN, forStatement);

            // Parse iterable expression
            Expression iterableExpression = parsePrimaryExpression();
            forStatement.setExpression(iterableExpression);

            // Ensure ')' exists
            require(RIGHT_PAREN, forStatement);

            // Ensure '{' exists
            require(LEFT_BRACE, forStatement);

            // Parse loop body
            List<Statement> bodyStatements = new ArrayList<>();
            while (!tokens.match(RIGHT_BRACE) && tokens.hasMoreTokens()) {
                Statement stmt = parseStatement();
                if (stmt != null) {
                    bodyStatements.add(stmt);
                }
            }


            // Ensure '}' exists
            forStatement.setEnd(require(RIGHT_BRACE, forStatement));

            forStatement.setBody(bodyStatements);
            return forStatement;
        }
        return null;
    }

    private Statement parseIfStatement() {
        if (tokens.match(IF)) {
            IfStatement ifStatement = new IfStatement();
            ifStatement.setStart(tokens.consumeToken()); // Consume 'if'

            // Ensure '(' exists
            require(LEFT_PAREN, ifStatement);

            // Parse the condition expression
            Expression conditionExpression = parseExpression();
            ifStatement.setExpression(conditionExpression);

            // Ensure ')' exists
            require(RIGHT_PAREN, ifStatement);

            // Ensure '{' exists for the true block
            require(LEFT_BRACE, ifStatement);

            // Parse the true block
            List<Statement> trueStatements = new ArrayList<>();
            while (!tokens.match(RIGHT_BRACE) && tokens.hasMoreTokens()) {
                Statement stmt = parseStatement();
                if (stmt != null) {
                    trueStatements.add(stmt);
                }
            }

            // Ensure '}' exists for the true block
            ifStatement.setTrueStatements(trueStatements);
            require(RIGHT_BRACE, ifStatement);

            // Optional 'else' part
            if (tokens.match(ELSE)) {
                tokens.consumeToken(); // Consume 'else'
                List<Statement> elseStatements = new ArrayList<>();

                // Check if the 'else' part is another if statement
                if (tokens.match(IF)) {
                    Statement elseIfStatement = parseIfStatement();
                    elseStatements.add(elseIfStatement);
                } else {
                    // Otherwise, it's a block of statements
                    require(LEFT_BRACE, ifStatement);
                    while (!tokens.match(RIGHT_BRACE) && tokens.hasMoreTokens()) {
                        Statement stmt = parseStatement();
                        if (stmt != null) {
                            elseStatements.add(stmt);
                        }
                    }
                    require(RIGHT_BRACE, ifStatement);
                }
                ifStatement.setElseStatements(elseStatements);
            }

            ifStatement.setEnd(tokens.getCurrentToken());
            return ifStatement;
        }
        return null;
    }


    //============================================================
    //  Expressions
    //============================================================

    private Expression parseExpression() {
        return parseEqualityExpression();
    }

    private Expression parseEqualityExpression() {
        Expression expression = parseComparisonExpression();  // Fix infinite recursion
        while (tokens.match(EQUAL_EQUAL, BANG_EQUAL)) {
            Token operator = tokens.consumeToken();
            Expression right = parseComparisonExpression();
            expression = new EqualityExpression(operator, expression, right);
        }
        return expression;
    }

    private Expression parseComparisonExpression() {
        Expression expression = parseAdditiveExpression();  // Fix infinite recursion
        while (tokens.match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = tokens.consumeToken();
            Expression right = parseAdditiveExpression();
            expression = new ComparisonExpression(operator, expression, right);
        }
        return expression;
    }


    private Expression parseAdditiveExpression() {
        Expression expression = parseMultiplyExpression();
        while (tokens.match(PLUS, MINUS)) {
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseMultiplyExpression();
            AdditiveExpression additiveExpression = new AdditiveExpression(operator, expression, rightHandSide);
            additiveExpression.setStart(expression.getStart());
            additiveExpression.setEnd(rightHandSide.getEnd());
            expression = additiveExpression;
        }
        return expression;
    }

    private Expression parseMultiplyExpression(){
        Expression expression = parseUnaryExpression();
        while(tokens.match(STAR, SLASH)){
            Token operator = tokens.consumeToken();
            final Expression rightHandSide = parseUnaryExpression();
            FactorExpression factorExpression = new FactorExpression(operator, expression, rightHandSide);
            factorExpression.setStart(expression.getStart());
            factorExpression.setEnd(rightHandSide.getEnd());
            expression = factorExpression;
        }
        return expression;
    }

    private Expression parseUnaryExpression() {
        if (tokens.match(MINUS, NOT)) {
            Token token = tokens.consumeToken();
            Expression rhs = parseUnaryExpression();
            UnaryExpression unaryExpression = new UnaryExpression(token, rhs);
            unaryExpression.setStart(token);
            unaryExpression.setEnd(rhs.getEnd());
            return unaryExpression;
        } else {
            return parsePrimaryExpression();
        }
    }



    private Expression parsePrimaryExpression() {
        if (tokens.match(IDENTIFIER)) {
            Token identifierToken = tokens.consumeToken();

            if(tokens.match(LEFT_PAREN)) {
                return parseFunctionCall(identifierToken);
            }

            IdentifierExpression identifierExpression = new IdentifierExpression(identifierToken.getStringValue());
            identifierExpression.setToken(identifierToken);
            return identifierExpression;
        }
        if (tokens.match(INTEGER)) {
            Token integerToken = tokens.consumeToken();
            IntegerLiteralExpression integerExpression = new IntegerLiteralExpression(integerToken.getStringValue());
            integerExpression.setToken(integerToken);
            return integerExpression;
        } else if (tokens.match(STRING)) {
            Token stringToken = tokens.consumeToken();
            StringLiteralExpression stringExpression = new StringLiteralExpression(stringToken.getStringValue());
            stringExpression.setToken(stringToken);
            return stringExpression;
        } else if (tokens.match(LEFT_PAREN)) {
            Token token = tokens.consumeToken();
            Expression expr = parseExpression();
            ParenthesizedExpression parenthesizedExpression = new ParenthesizedExpression(expr);
            parenthesizedExpression.setStart(token);
            Token endToken = require(RIGHT_PAREN, parenthesizedExpression);
            parenthesizedExpression.setEnd(endToken);
            return parenthesizedExpression;
        } else if (tokens.match(LEFT_BRACKET)) {
            return parseListExpression();
        } else if (tokens.match(TRUE, FALSE, NULL)) {
            if (tokens.match(TRUE)) {
                Token boolean_token = tokens.consumeToken();
                boolean value = Boolean.parseBoolean(boolean_token.getStringValue());
                BooleanLiteralExpression booleanLiteralExpression = new BooleanLiteralExpression(value);
                booleanLiteralExpression.setToken(boolean_token);
                return booleanLiteralExpression;
            } else if (tokens.match(FALSE)) {
                Token boolean_token = tokens.consumeToken();
                boolean value = Boolean.parseBoolean(boolean_token.getStringValue());
                BooleanLiteralExpression booleanLiteralExpression = new BooleanLiteralExpression(value);
                booleanLiteralExpression.setToken(boolean_token);
                return booleanLiteralExpression;
            } else if (tokens.match(NULL)) {
                Token null_token = tokens.consumeToken();
                NullLiteralExpression nullLiteralExpression = new NullLiteralExpression();
                nullLiteralExpression.setToken(null_token);
                return nullLiteralExpression;
            }
        }

        // Handle syntax errors if no valid expression was found
        SyntaxErrorExpression syntaxErrorExpression = new SyntaxErrorExpression(tokens.consumeToken());
        return syntaxErrorExpression;
    }

    private Expression parseFunctionCall(Token functionName){
        List<Expression> funcArgs = new ArrayList<>();
        Token firstParen = tokens.consumeToken();
        if (tokens.match(RIGHT_PAREN)) {
            tokens.consumeToken();
            FunctionCallExpression functionCallExpression = new FunctionCallExpression(functionName.getStringValue(), funcArgs);
            return functionCallExpression;

        }

        else {
            Expression firstArg = parseExpression();
            funcArgs.add(firstArg);
            while (tokens.match(COMMA)) {
                tokens.consumeToken();
                Expression argument = parseExpression();
                funcArgs.add(argument);
            }
        }
        boolean unterminated = false;
        if (!tokens.match(RIGHT_PAREN)) {
            // throw an unterminated arg list error
            unterminated = true;

        }
        if(!tokens.match(EOF)) {
            tokens.consumeToken();
        }
        FunctionCallExpression functionCallExpression = new FunctionCallExpression(functionName.getStringValue(), funcArgs);
        if(unterminated){
            functionCallExpression.addError(ErrorType.UNTERMINATED_ARG_LIST);
        }
        return functionCallExpression;
    }

    private Expression parseListExpression() {
        Token start = tokens.consumeToken();
        List<Expression> values = new ArrayList<>();


        if (!tokens.match(RIGHT_BRACKET)) {
            values.add(parseExpression());

            while (tokens.match(COMMA)) {
                tokens.consumeToken();
                values.add(parseExpression());
            }

            if (!tokens.match(RIGHT_BRACKET)) {
                ListLiteralExpression unterminatedList = new ListLiteralExpression(values);
                unterminatedList.setStart(start);
                unterminatedList.addError(new ParseError(start, ErrorType.UNTERMINATED_LIST).getErrorType());
                return unterminatedList;
            }
        }

        Token end = require(RIGHT_BRACKET, null); // Consume ']'
        ListLiteralExpression listExpr = new ListLiteralExpression(values);
        listExpr.setStart(start);
        listExpr.setEnd(end);
        return listExpr;
    }


    //============================================================
    //  Parse Helpers
    //============================================================
    private Token require(TokenType type, ParseElement elt) {
        return require(type, elt, ErrorType.UNEXPECTED_TOKEN);
    }

    private Token require(TokenType type, ParseElement elt, ErrorType msg) {
        if(tokens.match(type)){
            return tokens.consumeToken();
        } else {
            elt.addError(msg, tokens.getCurrentToken());
            return tokens.getCurrentToken();
        }
    }

}
