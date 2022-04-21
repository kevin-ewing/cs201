import java.util.Scanner;

class Palindrome {
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in); //create scanner
    System.out.print("Enter a string:  ");
    String inputWord = scan.nextLine(); //stores input as inputWord
    boolean isPalindrome = true; //boolean "switch" for if it is a palendrome (there is definatle and easier way I am just very rusty at coding)
    for (int i=0;i<(inputWord.length()/2);i++){
      if (inputWord.charAt(i)!=inputWord.charAt(inputWord.length()-(1+i))){ //compare first term to last term and confirm
        isPalindrome = false; //if first term does not equal last then isPalindrome = False
      }
    }
    if (isPalindrome==true){
      System.out.println(inputWord +" is a palindrome!"); //printing if it is a palendrome
    }

    if (isPalindrome==false){
      System.out.println(inputWord +" is not a palindrome."); //printing if it is a palendrome
    }
  }
}
