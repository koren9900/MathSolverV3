import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.math.solver.MathProblemSolver;
import org.math.solver.RuntimeError;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class MainTest {

    @Test
    public void testLiteralBinary(){
        // check literals work correctly
        assertEquals(new MathProblemSolver("1").solve(), "1=1");
        // check addition works with literals
        assertEquals(new MathProblemSolver("1+1").solve(), "1+1=2");
        // check subtraction works with literals
        assertEquals(new MathProblemSolver("1-1").solve(), "1-1=0");
        // check multiplication works with literals
        assertEquals(new MathProblemSolver("2*3").solve(), "2*3=6");
        // check division works with literals
        assertEquals(new MathProblemSolver("100/10").solve(), "100/10=10");
        // check exponents work
        assertEquals(new MathProblemSolver("2^5").solve(), "2^5=32");
        assertEquals(new MathProblemSolver("121^0.5").solve(), "121^0.5=11");
        assertEquals(new MathProblemSolver("121+4^0.5-4").solve(), "121+4^0.5-4=119");
    }
    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,    Expected
              1!,       1!=1
              2!,        2!=2
              3!,        3!=6
              7!,        7!=5040
            """)
    public void testLiteralFactorial(String input, String expected){
        // check factorial works
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }
    @Test
    public void testLiteralNegation(){
        // check single literal
        assertEquals(new MathProblemSolver("-5").solve(), "-5=-5");
        // check with binary operations
        assertEquals(new MathProblemSolver("-5+5").solve(), "-5+5=0");
        assertEquals(new MathProblemSolver("-5+-5").solve(), "-5+-5=-10");
        assertEquals(new MathProblemSolver("-10-5").solve(), "-10-5=-15");
        assertEquals(new MathProblemSolver("-11*3").solve(), "-11*3=-33");
        assertEquals(new MathProblemSolver("-12*-3").solve(), "-12*-3=36");
        assertEquals(new MathProblemSolver("100/-10").solve(), "100/-10=-10");
        assertEquals(new MathProblemSolver("-5/-0.5").solve(), "-5/-0.5=10");
    }
    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,    Expected
              -5!,      -5!=-120
              -1!,      -1!=-1
            """)
    public void testLiteralFactorialNegation(String input, String expected){
        // check factorial works
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,                    Expected
              (5),                      (5)=5
              (5)+1,                    (5)+1=6
              (5*3),                    (5*3)=15
              (5-4+3/1),                (5-4+3/1)=4
              (9-0+1)*(1+3),            (9-0+1)*(1+3)=40
              8+(1/2)+(0.5)-9+(5^2),    8+(1/2)+(0.5)-9+(5^2)=25
              (1+2+3*3)+(7/4)*(1--3),   (1+2+3*3)+(7/4)*(1--3)=19
              -(7+4),                   -(7+4)=-11
              5*-(11*2),                5*-(11*2)=-110
              (1+2)!+3,                 (1+2)!+3=9
            """)
    public void testGrouping(String input, String expected){
        // check single literal
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }

    @Test
    public void testLiteralFunctions(){
        // check functions work
        assertEquals(new MathProblemSolver("MAX(1,2,3,4,5)").solve(), "max(1,2,3,4,5)=5");
        assertEquals(new MathProblemSolver("Min(100,50)").solve(), "min(100,50)=50");

        assertEquals(new MathProblemSolver("log(10,100)").solve(), "log(10,100)=2");
        assertEquals(new MathProblemSolver("sqrt(1024)").solve(), "sqrt(1024)=32");

        assertEquals(new MathProblemSolver("ceil(10.100)").solve(), "ceil(10.1)=11");
        assertEquals(new MathProblemSolver("floor(1.9024)").solve(), "floor(1.9024)=1");

        assertEquals(new MathProblemSolver("round(1.5001)").solve(), "round(1.5001)=2");
        assertEquals(new MathProblemSolver("abs(---6)").solve(), "abs(---6)=6");

        // check function stacking
        assertEquals(new MathProblemSolver("round(max(1.49,1.51))").solve(), "round(max(1.49,1.51))=2");
        assertEquals(new MathProblemSolver("min(4,3,2,1,abs(-5), log(10,100))").solve(), "min(4,3,2,1,abs(-5),log(10,100))=1");
        assertEquals(new MathProblemSolver("min(ceil(min(ceil(10.79),10.99)),12)").solve(), "min(ceil(min(ceil(10.79),10.99)),12)=11");

        // check factorial on functions
        assertEquals(new MathProblemSolver("max(5,root(36,2))!").solve(), "max(5,root(36,2))!=720");
        assertEquals(new MathProblemSolver("log(abs(-2),abs(8))!!").solve(), "log(abs(-2),abs(8))!!=720");

        //check negation
        assertEquals(new MathProblemSolver("-max(2,3)+-abs(-3)").solve(), "-max(2,3)+-abs(-3)=-6");
        assertEquals(new MathProblemSolver("-max(800,log(4,12))/-sqrt(64)").solve(), "-max(800,log(4,12))/-sqrt(64)=100");
    }
    @Test
    public void testVectorBinary(){
        // check vectors work correctly
        assertEquals(new MathProblemSolver("(1,1)").solve(), "(1,1)=(1,1)");
        // check addition works with vectors
        assertEquals(new MathProblemSolver("(1,1)+(1,1)").solve(), "(1,1)+(1,1)=(2,2)");
        // check subtraction works with vectors
        assertEquals(new MathProblemSolver("(3,2)-(3,3)").solve(), "(3,2)-(3,3)=(0,-1)");
        // check multiplication works with vectors
        assertEquals(new MathProblemSolver("(3,2,1)*7").solve(), "(3,2,1)*7=(21,14,7)");
        // check division works with vectors
        assertEquals(new MathProblemSolver("(121,99,11,1100)/11").solve(), "(121,99,11,1100)/11=(11,9,1,100)");
        // additional tests
        assertEquals(new MathProblemSolver("(2,7,5,4,8)+5*(1,1,1,1,1)").solve(), "(2,7,5,4,8)+5*(1,1,1,1,1)=(7,12,10,9,13)");
    }

    @Test
    public void testVectorNegation(){
        // check vectors negation
        assertEquals(new MathProblemSolver("-(1,1)").solve(), "-(1,1)=(-1,-1)");
        assertEquals(new MathProblemSolver("(5,3)+-(2,3)").solve(), "(5,3)+-(2,3)=(3,0)");
    }
    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,                            Expected Error
              (3+4)):                           ) is not in the correct place
              5+4-6^6++5:                       + is not in the correct place
              5+*max(12,7):                     * is not in the correct place
              (-:                               incomplete expression
              5!+(9-13)!-max(3,13*2):           Factorial works only on natural numbers
              444- !max(12,7):                  ! is not in the correct place
              min(4,min(45,max(5^(4-3)))):      Max functions take at least two parameters
              sqrt(max(4,5),89-89):             Sqrt functions take exactly one parameter
              max(min(3,4,3,3),fun(23)):        f is an unrecognised identifier
              8!+max7,8)-120:                   Expect '(' after a function
              2^min(4,7,max(-1,7):              Expect '(' in the end of a function
            """,delimiter = ':')
    public void testErrors(String input, String error){
      Exception e = assertThrows(RuntimeError.class, ()-> new MathProblemSolver(input).solve() );
      assertEquals(e.getMessage(), error);
    }
}
