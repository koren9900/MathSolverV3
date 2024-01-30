package org.math.solver;

public class Main {


    public static void main(String[] args) {
        String exp = "(3+4)";
        System.out.println(new MathProblemSolver(exp).solve());

    }

}