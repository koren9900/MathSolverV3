import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.math.solver.MathProblemSolver;
import org.math.solver.RuntimeError;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class MainTest {

    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,        Expected
            # check literals work correctly
            1,              1=1
            # check addition works with literals
            1+1,            1+1=2
            # check subtraction works with literals
            1-1,            1-1=0
            # check multiplication works with literals
            2*3,            2*3=6
            # check division works with literals
            100/10,         100/10=10
            # check exponents work
            2^5,            2^5=32
            121^0.5,        121^0.5=11
            # additional checks
            121+4^0.5-4,    121+4^0.5-4=119
            """)
    public void testLiteralBinary(String input, String expected){
        assertEquals(new MathProblemSolver(input).solve(), expected);
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
    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,        Expected
            # check single literal
              -5,           -5=-5
            # check with binary operations
              -5+5,         -5+5=0
              -5+-5,        -5+-5=-10
              -10-5,        -10-5=-15
              -11*3,        -11*3=-33
              -12*-3,       -12*-3=36
              100/-10,      100/-10=-10
              -5/-0.5,      -5/-0.5=10
            """)

    public void testLiteralNegation(String input, String expected){
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }
    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input,    Expected
            # check factorial works
              -5!,      -5!=-120
              -1!,      -1!=-1
            """)
    public void testLiteralFactorialNegation(String input, String expected){
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
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input:                    Expected Error
            # check functions work
            MAX(1,2,3,4,5):                         max(1,2,3,4,5)=5
            Min(100,50):                            min(100,50)=50
            log(10,100):                            log(10,100)=2
            sqrt(1024):                             sqrt(1024)=32
            ceil(10.100):                           ceil(10.1)=11
            floor(1.9024):                          floor(1.9024)=1
            round(1.5001):                          round(1.5001)=2
            abs(---6):                              abs(---6)=6
            # check function stacking
            round(max(1.49,1.51)):                  round(max(1.49,1.51))=2
            min(4,3,2,1,abs(-5), log(10,100)):      min(4,3,2,1,abs(-5),log(10,100))=1
            min(ceil(min(ceil(10.79),10.99)),12):   min(ceil(min(ceil(10.79),10.99)),12)=11
            # check factorial on functions
            max(5,root(36,2))!:                     max(5,root(36,2))!=720
            log(abs(-2),abs(8))!!:                  log(abs(-2),abs(8))!!=720
            # check negation
            -max(2,3)+-abs(-3):                     -max(2,3)+-abs(-3)=-6
            -max(800,log(4,12))/-sqrt(64):          -max(800,log(4,12))/-sqrt(64)=100
            """, delimiter = ':')
    public void testLiteralFunctions(String input, String expected){
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input:                    Expected Error
            # check vectors work correctly
            (1,1):                      (1,1)=(1,1)
            # check addition works with vectors
            (1,1)+(1,1):                (1,1)+(1,1)=(2,2)
            # check subtraction works with vectors
            (3,2)-(3,3):                (3,2)-(3,3)=(0,-1)
            # check multiplication works with vectors
            (3,2,1)*7:                  (3,2,1)*7=(21,14,7)
            # check division works with vectors
            (121,99,11,1100)/11:        (121,99,11,1100)/11=(11,9,1,100)
            # additional tests
            (2,7,5,4,8)+5*(1,1,1,1,1):  (2,7,5,4,8)+5*(1,1,1,1,1)=(7,12,10,9,13)
            """, delimiter = ':')
    public void testVectorBinary(String input, String expected){
        assertEquals(new MathProblemSolver(input).solve(), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock= """
            # Input:             Expected Error
            # check vectors negation
            -(1,1):              -(1,1)=(-1,-1)
            (5,3)+-(2,3):        (5,3)+-(2,3)=(3,0)
            """, delimiter = ':')
    public void testVectorNegation(String input, String expected){
        assertEquals(new MathProblemSolver(input).solve(), expected);
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
              (4+7,5^3*max(2,2))+(1^2,2,3):     Can't operate between different size vectors
            """,delimiter = ':')
    public void testErrors(String input, String error){
      Exception e = assertThrows(RuntimeError.class, ()-> new MathProblemSolver(input).solve() );
      assertEquals(e.getMessage(), error);
    }
}
