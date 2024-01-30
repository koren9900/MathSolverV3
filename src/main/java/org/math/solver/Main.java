package org.math.solver;

public class Main {


    public static void main(String[] args) {
        String exp = "max(8,7";
        System.out.println(new MathProblemSolver(exp).solve());

    }

}