import java.util.Scanner;
import java.lang.Math;

class NumProps{

  // methods
  static boolean isEven(int input){
    return input%2==0;
  }

  static boolean isPrime(int input){
    boolean isPrimePH = true;
    for(int index = 2 ; index<=input/2; index++){
      if (input%index==0){
        isPrimePH=false;
      }
    }
    return isPrimePH;
  }

  static boolean isTriangular(int input){
    boolean isTriangularPH = false;
    for(int index = 1 ; index<=input; index++){
      if ((index)*(index + 1) / 2 == input){
        isTriangularPH = true;
      }
    }
    return isTriangularPH;
  }

  static boolean isSquare(int input){
    return Math.sqrt(input)==(int)Math.sqrt(input);
  }

  //main function
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);

    System.out.print("Start: ");
    String startStr = scan.next();
    int startInt = Integer.parseInt(startStr);

    System.out.print("Stop: ");
    String stopStr = scan.next();
    int stopInt = Integer.parseInt(stopStr);

    System.out.print("Step: ");
    String stepStr = scan.next();
    int stepInt = Integer.parseInt(stepStr);

    System.out.println("Num\tEven\tPrime\tSquare\tTriangular");
    for (int i = startInt; i<= stopInt; i=i+stepInt){
      System.out.println(i +"\t"+ NumProps.isEven(i) +"\t"+ NumProps.isPrime(i) +"\t"+ NumProps.isSquare(i) +"\t"+ NumProps.isTriangular(i));
    }
  }
}
