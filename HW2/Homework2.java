/*
Compilation:  javac Homework2.java
Execution:    java Homework2

CS201 Homework Assignment 2
Author: Kevin Ewing

Implements a signle play version of battleship with randomly placed ships. Have fun!
*/

import java.util.Scanner;
import java.util.Random;

class Battleship{
  //fields
  public int numOG; //number of guesses
  public int number_of_hits; //number of hits

  // Set the board size
  public String[][] playBd = new String[10][10]; //play board (what is printed that the player sees)
  public String[][] gameBd = new String[10][10]; //game board (what the computer 'knows' and where the battleship locations are added to)
  public String alphabet = "ABCDEFGHIJ"; //colom headers so that you can index through them to find the coordinates

  Battleship(){
    numOG = 0;
    number_of_hits = 0;

    // This creates the base game board filling it with the '-' character
    for (int iter1=0; iter1 <10; iter1++){
      for (int iter2=0; iter2 <10; iter2++){
        gameBd[iter1][iter2]= "-";
      }
    }
    // This creates the base game board filling it with the '-' character
    for (int iter1=0; iter1 <10; iter1++){
      for (int iter2=0; iter2 <10; iter2++){
        playBd[iter1][iter2]= "-";
      }
    }
  }

  void printBoard(){
    // This method prints the board from the player board (and does all the formatting).
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
    //this method is called when the player give gives up and prints the game board
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

  void makeBoard(){
    //this method randomly assings locations to all of the ships
    int numberOfXs=0;
    while (numberOfXs != 17){ //retry the board until there is no overlap and all Xs of the ships are on the game board

      //reset to game board of only '-'
      numberOfXs = 0;
      for (int iter1=0; iter1 <10; iter1++){
        for (int iter2=0; iter2 <10; iter2++){
          gameBd[iter1][iter2]= "-";
        }
      }

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
      if (orientation1 == 0){ //horizontal
        int x1 = rand.nextInt(7);
        int y1 = rand.nextInt(10);
        for (int iter=x1; iter < x1+4; iter++){
          gameBd[y1][iter]="X";
        }
      }
      if (orientation1 == 1){ //verticle
        int x1 = rand.nextInt(10);
        int y1 = rand.nextInt(7);
        for (int iter=y1; iter < y1+4; iter++){
          gameBd[iter][x1]="X";
        }
      }

      //Destroyer - Size 3
      int orientation2 = rand.nextInt(2); //orientation will either be 0 (horizontal) or 1(verticle)
      if (orientation2 == 0){ //horizontal
        int x2 = rand.nextInt(8);
        int y2 = rand.nextInt(10);
        for (int iter=x2; iter < x2+3; iter++){
          gameBd[y2][iter]="X";
        }
      }
      if (orientation2 == 1){ //verticle
        int x2 = rand.nextInt(10);
        int y2 = rand.nextInt(8);
        for (int iter=y2; iter < y2+3; iter++){
          gameBd[iter][x2]="X";
        }
      }

      //Submarine - Size 3
      int orientation3 = rand.nextInt(2); //orientation will either be 0 (horizontal) or 1(verticle)
      if (orientation3 == 0){ //horizontal
        int x3 = rand.nextInt(8);
        int y3 = rand.nextInt(10);
        for (int iter=x3; iter < x3+3; iter++){
          gameBd[y3][iter]="X";
        }
      }
      if (orientation3 == 1){ //verticle
        int x3 = rand.nextInt(10);
        int y3 = rand.nextInt(8);
        for (int iter=y3; iter < y3+3; iter++){
          gameBd[iter][x3]="X";
        }
      }

      //Patrol Boat - Size 2
      int orientation4 = rand.nextInt(2); //orientation will either be 0 (horizontal) or 1(verticle)
      if (orientation4 == 0){ //horizontal
        int x4 = rand.nextInt(9);
        int y4 = rand.nextInt(10);
        for (int iter=x4; iter < x4+2; iter++){
          gameBd[y4][iter]="X";
        }
      }
      if (orientation4 == 1){ //verticle
        int x4 = rand.nextInt(10);
        int y4 = rand.nextInt(9);
        for (int iter=y4; iter < y4+2; iter++){
          gameBd[iter][x4]="X";
        }
      }
      for (int iter1=0; iter1 <10; iter1++){
        for (int iter2=0; iter2 <10; iter2++){
          if (gameBd[iter1][iter2]== "X"){
            numberOfXs ++;
          }
        }
      }
    }
  }

  void btsGuess(String input){
    // Places an X or O at the location that is guessed depending on if it is a ship or not
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
    //main function that runs the battleship game
    Boolean winner = false; //allows the game to continue until the player wins or gives up
    System.out.printf("This game implements a single player version of Battleship. There are 5 ships of \nlengths 5, 4, 3, 3, and 2. Your hit chart is shown after each round.  Enter a \ncoordinate in the form of a letter (A-J) followed by a number (0-9), e.g. E8. \nThe game ends when all ships have been sunk. Good luck!\n('/endgame' will reveal all ship locations)\n\n");
    Battleship bts = new Battleship(); //new instance of battleship
    bts.makeBoard(); //greates the locations of all the ships
    bts.printBoard(); //prints the player board
    while (winner == false){
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a coord (eg. E8): ");
      String input_guess = scan.next();
      if (input_guess.equals("/endgame")){ //if the player gives up then the game board will be printed
        bts.giveUpPrintBoard();
        System.out.println("Game ended. Ships revealed!");
        winner=true; //not really hahaha :)
      }
      else{ //if the game is not done it keeps updating the board and takign more guesses
        bts.btsGuess(input_guess);
        bts.printBoard();
        if (bts.number_of_hits == 17){ //if you win
          System.out.println("You Won! Number of attempts: " + bts.numOG);
          winner=true; //this time they actually did!
        }
      }
    }
  }
}
