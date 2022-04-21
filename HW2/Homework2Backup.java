import java.util.Scanner;
import java.util.Random;

class Battleship{
  //fields
  public int numOG;
  public int number_of_hits;
  public String[][] playBd = new String[10][10];
  public String[][] gameBd = new String[10][10];
  public String alphabet = "ABCDEFGHIJ";

  Battleship(){
    numOG = 0;
    number_of_hits = 0;
    for (int iter1=0; iter1 <10; iter1++){
      for (int iter2=0; iter2 <10; iter2++){
        gameBd[iter1][iter2]= "-";
      }
    }
    for (int iter1=0; iter1 <10; iter1++){
      for (int iter2=0; iter2 <10; iter2++){
        playBd[iter1][iter2]= "-";
      }
    }
  }

  void printBoard(){
    System.out.printf("\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\n\n");
    for (int iter1=0; iter1 <10; iter1++){
      char current_letter=alphabet.charAt(iter1);
      System.out.printf(current_letter+"\t");
      for (int iter2=0; iter2 <10; iter2++){
        System.out.printf(playBd[iter1][iter2]+"\t");
      }
      System.out.printf("\n\n");
    }
  }

  void giveUpPrintBoard(){
    System.out.printf("\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9\n\n");
    for (int iter1=0; iter1 <10; iter1++){
      char current_letter=alphabet.charAt(iter1);
      System.out.printf(current_letter+"\t");
      for (int iter2=0; iter2 <10; iter2++){
        System.out.printf(gameBd[iter1][iter2]+"\t");
      }
      System.out.printf("\n\n");
    }
  }

  void makeBoard(){ //need work

    //Carrier - Size 5
    Random rand = new Random();
    int orientation = rand.nextInt(2); //orientation will either be 0 (horizontal) or 1(verticle)
    if (orientation == 0){ //horizontal
      int x = rand.nextInt(6);
      int y = rand.nextInt(10);
      for (int iter=x; iter < x+5; iter++){
        gameBd[y][iter]="X";
      }
    }
    if (orientation == 1){ //verticle
      int x = rand.nextInt(10);
      int y = rand.nextInt(6);
      for (int iter=y; iter < y+5; iter++){
        gameBd[iter][x]="X";
      }
    }

    //Battleship - Size 4
    int orientation1 = rand.nextInt(2); //orientation will either be 0 (horizontal) or 1(verticle)
    String[][] tempBd1 = new String[10][10];
    Boolean overlap1 = false;
    int vx = rand.nextInt(10);//(0-9)
    int vy = rand.nextInt(7);//(0-6)
    int hx = rand.nextInt(7);
    int hy = rand.nextInt(10);

    //creating a temp board 1
    if (orientation == 0){ //horizontal
      for (int iter=hx; iter < hx+4; iter++){
        tempBd1[hy][iter]="X";
      }
    }
    if (orientation == 1){ //verticle
      for (int iter=vy; iter < vy+4; iter++){
        tempBd1[iter][vx]="X";
      }
    }

    //comparing temp board 1 to actaul board
    for (int iter1=0; iter1 <10; iter1++){
      for (int iter2=0; iter2 <10; iter2++){
        if (gameBd[iter1][iter2] == tempBd1[iter1][iter2]){
          overlap1 = true;
        }
      }
    }

    if(overlap1 == false){
      if (orientation == 0){ //horizontal
        for (int iter=hx; iter < hx+4; iter++){
          gameBd[hy][iter]="X";
        }
      }
      if (orientation == 1){ //verticle
        for (int iter=vy; iter < vy+4; iter++){
          gameBd[iter][vx]="X";
        }
      }
    }







  }

  void btsGuess(String input){
    if (input.length() != 2 && input !="/endgame"){
      System.out.println("Invalid character, input as 'A0' to 'J9'");
    }
    else{
      String xquard = String.valueOf(input.charAt(0));
      String yquard = String.valueOf(input.charAt(1));
      int index1 = alphabet.indexOf(xquard.toUpperCase());
      int index2 = Integer.parseInt(yquard);
      if (playBd[index1][index2]== "O" || playBd[index1][index2] =="X"){
        System.out.println("Location already attempted.");
      }
      else if (playBd[index1][index2]==gameBd[index1][index2]){
        gameBd[index1][index2]="O";
        playBd[index1][index2]="O";
        System.out.println("Miss.");
        numOG++;
      }
      else if (gameBd[index1][index2] =="X"){
        playBd[index1][index2]= "X";
        System.out.println("Hit!");
        number_of_hits++;
        numOG++;
      }
    }
  }
}

class Homework2{
  public static void main(String[] args){
    Boolean winner = false;
    System.out.printf("This game implements a single player version of Battleship. There are 5 ships of \nlengths 5, 4, 3, 3, and 2. Your hit chart is shown after each round.  Enter a \ncoordinate in the form of a letter (A-J) followed by a number (0-9), e.g. E8. \nThe game ends when all ships have been sunk. Good luck!\n('/endgame' will reveal all ship locations)\n\n");
    Battleship bts = new Battleship();
    bts.makeBoard();
    bts.printBoard();
    while (winner == false){
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a coord (eg. E8): ");
      String input_guess = scan.next();
      if (input_guess.equals("/endgame")){
        bts.giveUpPrintBoard();
        System.out.println("Game ended. Ships revealed!");
        winner=true; //not really hahaha
      }
      else{
        bts.btsGuess(input_guess);
        bts.printBoard();
        if (bts.number_of_hits == 3){ //this will be changed to 17 once I have my full randomized ships
          System.out.println("You Won! Number of attempts: " + bts.numOG);
          winner=true;
        }
      }
    }
  }
}
