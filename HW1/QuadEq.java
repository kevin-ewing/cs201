import java.util.Scanner;
import java.lang.Math;

class QuadEq{
  public static String toString(String inputA, String inputB, String inputC){
    //method that returns the properly formatted quadradic equation
    return inputA+"x^2 + "+ inputB+"x + "+ inputC+" = 0";
  }

  public static void solve(String inputA, String inputB, String inputC){
      //method that solves the quadratic equation
    Double doubleA =Double.parseDouble(inputA);
    Double doubleB =Double.parseDouble(inputB);
    Double doubleC =Double.parseDouble(inputC);
    Double answer1 = ((-doubleB)+Math.sqrt((doubleB*doubleB)-(4*doubleA*doubleC)))/(2*doubleA);
    Double answer2 = ((-doubleB)-Math.sqrt((doubleB*doubleB)-(4*doubleA*doubleC)))/(2*doubleA);
    System.out.println("The roots of this equation are "+answer1+" and "+answer2);
  }

  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter the coefficients a, b, and c of the quadratic equation:");
      //print opening statement

    System.out.print("a:");
    String inputA = scan.next();
      // input of A

    System.out.print("b:");
    String inputB = scan.next();
      // input of B

    System.out.print("c:");
    String inputC = scan.next();
      // input of C

    System.out.println(QuadEq.toString(inputA, inputB, inputC));
    QuadEq.solve(inputA, inputB, inputC);
      //using the methods of QaudEq, toString() and solve(), to proudce final output

  }
}
