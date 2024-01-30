package org.math.solver;

public class Main {
    public static void main(String[] args) {
        String exp = "(5,3,7)+3*(4,7,-1)";
        System.out.println(new MathProblemSolver(exp).solve());

    }

}