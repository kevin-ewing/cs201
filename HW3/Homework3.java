/*
Compilation:  javac Homework3.java
Execution:    java Homework3

CS201 Homework Assignment 3
Author: Kevin Ewing

Implements a two player version of connect four that uses ArrayLists to decide if a player has won.

Note. My drop method was easier if I created it in the ConnectFour class rather than the player class.
It is found at line 58.
*/
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;



class ConnectFour{
  // fields
  public String[][] playBd = new String[6][7]; //play board; sets the play board size
  public int row = 0;

  ConnectFour(){
    //initialize the game board
    System.out.printf("Let's play Connect Four! Player order chosen at \nrandom. Player 1's moves are denoted by 'X' and \nPlayer 2's moves are denoted by 'O'. Good luck!");
    for (int iter1=0; iter1 <6; iter1++){
      for (int iter2=0; iter2 <7; iter2++){
        playBd[iter1][iter2]= "-";
      }
    }
  }

  void printBoard(){
    //prints the board
    System.out.printf("\n");
    for (int iter1=0; iter1 <6; iter1++){
      for (int iter2=0; iter2 <7; iter2++){
        System.out.printf(playBd[iter1][iter2]+"\t");
      }
      System.out.printf("\n\n");
    }
    System.out.printf("0\t1\t2\t3\t4\t5\t6\n");
  }

  Boolean validPiece(String input_guess){
    //decides if an eneterd piece is vailid (IE. not being placed on an already filled column)
    int inputNum = Integer.parseInt(input_guess);
    String indexAtInputNum = playBd[0][inputNum];
    if (indexAtInputNum.equals("O") || indexAtInputNum.equals("X")){
      return false;
    }
    else{
      return true;
    }
  }

  void dropPiece(String playerPiece, String input_guess){ //returns the row number
    //inputs are a piece and a column string, places that player piece at the lowest position of the column.
    Integer inputNum = Integer.parseInt(input_guess);
    String indexAtInputNum = playBd[0][inputNum];
    for (int ii = 5; ii >= 0; ii--){
      String tempIndex = playBd[ii][inputNum];
      if (tempIndex.equals("-")){
        playBd[ii][inputNum]= playerPiece;
        row = ii;
        break;
      }
    }
  }

  Boolean checkWin(ArrayList inputArray){
    //inputs are the players ArrayList, decides if there is four in a row starting from any of the pieces.
    //Also if there is a win it tells you what direction the win was in!
    if (inputArray.size() < 4){ //can not win unless you place four pieces
      return false;
    }

    //preparing to iterate through the array list and trying every array.
    for (int iter = 0; iter<inputArray.size();iter++){
      int[] checkTempCoord = (int[])inputArray.get(iter);
      int row = checkTempCoord[0];
      int column = checkTempCoord[1];

      //Checking every move from the history of moves if there is four in a row starting there
      if((column+3)<7){ //if the column plus the four in a row still is on the board
        if (playBd[row][column] == playBd[row][column+1] && playBd[row][column+1] == playBd[row][column+2] && playBd[row][column+2] == playBd[row][column+3]){
          System.out.println("Horizontal win!");
          return true;
        }
      }

      if((row-3)>=0){ //if the rows do not go out of the bottom
        if (playBd[row][column] == playBd[row-1][column] && playBd[row-1][column] == playBd[row-2][column] && playBd[row-2][column] == playBd[row-3][column]){
          System.out.println("Vertical win!");
          return true;
        }
      }

      if(((column+3)<7) && ((row-3)>=0)){ //if the column and rows plus the four in a row is still on the board
        if (playBd[row][column] == playBd[row-1][column+1] && playBd[row-1][column+1] == playBd[row-2][column+2] && playBd[row-2][column+2] == playBd[row-3][column+3]){
          System.out.println("Diagonal win!");
          return true;
        }
      }

      if(((row-3)>=0) && ((column-3)>=0)){ // if the rows do not go out of the bottom or off the side.
        if (playBd[row][column] == playBd[row-1][column-1] && playBd[row-1][column-1] == playBd[row-2][column-2] && playBd[row-2][column-2] == playBd[row-3][column-3]){
          System.out.println("Diagonal win!");
          return true;
        }
      }
    }
    return false;
  }
}

class Player{
  public ArrayList<int[]> moves = new ArrayList<int[]>();; //will be used to store length two arrays
  public String piece = "-";
  public int[] coordinates;

  Player(int number){
    //player constructor
    if (number == 1){
      piece = "X";
    }
    if (number == 2){
      piece = "O";
    }
  }

  String playerPiece(){
    //returns the piece of the player
    return piece;
  }

  ArrayList storeMoves(String input_guess, int row){
    //stores move coordinates in an array in an ArrayList
    coordinates = new int[2];
    int inputNum = Integer.parseInt(input_guess);
    coordinates[0] = row;
    coordinates[1] = inputNum;
    moves.add(coordinates);
    return moves;
  }

  int numberMoves(){
    //returns the number of moves made
    return moves.size();
  }
}


class Homework3{
  public static void main(String[] args){
    //creates instances of the players and the game
    ConnectFour cntf = new ConnectFour();
    Random rand = new Random();
    int whoGoesFirst = rand.nextInt(2); //random selection of which player goes first
    Scanner scan = new Scanner(System.in);
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    Boolean winner = false;
    while (winner == false){
      if (whoGoesFirst == 0){ //in this scenerio player one goes first

        //first player
        cntf.printBoard();
        System.out.println("Player 1's Turn!");
        System.out.print("Enter a column: ");
        String input_guess = scan.next();
        while (cntf.validPiece(input_guess) == false){
          System.out.println("Enter a valid guess.");
          System.out.print("Enter a column: ");
          input_guess = scan.next();
        }

        //decision making part
        cntf.dropPiece(p1.playerPiece(),input_guess);

        //decides if there is a winner
        if(cntf.checkWin(p1.storeMoves(input_guess, cntf.row)) == true){
          cntf.printBoard();
          System.out.println("Player 1 Wins!");
          winner = true;
          break;
        }

        //decides if there is a tie.
        if (p1.numberMoves()+p2.numberMoves()==42){
          cntf.printBoard();
          System.out.println("Game ends in a tie...");
          winner = true; // haha not really
        }

        //second player
        cntf.printBoard();
        System.out.println("Player 2's Turn!");
        System.out.print("Enter a column: ");
        input_guess = scan.next();
        while (cntf.validPiece(input_guess) == false){
          System.out.println("Enter a valid guess.");
          System.out.print("Enter a column: ");
          input_guess = scan.next();
        }

        //decision making part
        cntf.dropPiece(p2.playerPiece(),input_guess);

        //decides if there is a winner
        if(cntf.checkWin(p2.storeMoves(input_guess, cntf.row)) == true){
          cntf.printBoard();
          System.out.println("Player 2 Wins!");
          winner = true;
        }

        //decides if there is a tie.
        if (p1.numberMoves()+p2.numberMoves()==42){
          cntf.printBoard();
          System.out.println("Game ends in a tie...");
          winner = true; // haha not really
        }

      }
      if (whoGoesFirst == 1){ //in this scenerio player two goes first

        //second player
        cntf.printBoard();
        System.out.println("Player 2's Turn!");
        System.out.print("Enter a column: ");
        String input_guess = scan.next();
        while (cntf.validPiece(input_guess) == false){
          System.out.println("Enter a valid guess.");
          System.out.print("Enter a column: ");
          input_guess = scan.next();
        }

        //decision making part
        cntf.dropPiece(p2.playerPiece(),input_guess);

        //decides if there is a winner
        if(cntf.checkWin(p2.storeMoves(input_guess, cntf.row)) == true){
          cntf.printBoard();
          System.out.println("Player 2 Wins!");
          winner = true;
          break;
        }

        //decides if there is a tie.
        if (p1.numberMoves()+p2.numberMoves()==42){
          cntf.printBoard();
          System.out.println("Game ends in a tie...");
          winner = true; // haha not really
        }

        //first player
        cntf.printBoard();
        System.out.println("Player 1's Turn!");
        System.out.print("Enter a column: ");
        input_guess = scan.next();
        while (cntf.validPiece(input_guess) == false){
          System.out.println("Enter a valid guess.");
          System.out.print("Enter a column: ");
          input_guess = scan.next();
        }

        //decision making part
        cntf.dropPiece(p1.playerPiece(),input_guess);

        //decides if there is a winner
        if(cntf.checkWin(p1.storeMoves(input_guess, cntf.row)) == true){
          cntf.printBoard();
          System.out.println("Player 1 Wins!");
          winner = true;
        }

        //decides if there is a tie.
        if (p1.numberMoves()+p2.numberMoves()==42){
          cntf.printBoard();
          System.out.println("Game ends in a tie...");
          winner = true; // haha not really
        }
      }
    }
  }
}
