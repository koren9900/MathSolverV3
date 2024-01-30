package org.math.solver;

import java.util.List;

public class MathProblemSolver {
    private static  List<Expr> expressions;
    public MathProblemSolver(String exp){
        Scanner scanner = new Scanner(exp);
        List<Token> scanned = scanner.scanTokens();
        Parser parser = new Parser(scanned);
        expressions = parser.parse();

    }

    public String solve(){
        Interpreter interpreter = new Interpreter();
        List<Expr> evaluatedExpressions = interpreter.interpret(expressions);
        Stringer stringer = new Stringer();
        return stringer.stringify(expressions).toString() + "=" + stringer.stringify(evaluatedExpressions);
    }

}
