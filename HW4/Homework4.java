/*
Compilation:  javac Homework4.java
Execution:    java Homework4

CS201 Homework Assignment 4
Author: Kevin Ewing

Implements three types of sorting, bubble, insertion, and selection.
*/

import java.util.Scanner;
import java.util.Random;


class Sort{

  public int[] bubbleSort(int[] arr ){

    int bubbleComparisons = 0;
    int bubbleAssignments = 0;
    int size = arr.length;
    int numsorted = 0;
    int temp = 0;
    int[] results = new int[2];

    for(int i = 1; i < size; i++){
      for (int j = 1; j < (size - numsorted); j++){
        bubbleComparisons++;

        // if the number on the left arr[j-1] is greater than the
        // number on the right arr[j], switch the two
        if(arr[j-1] > arr[j]){
          temp = arr[j];
          arr[j] = arr[j-1];
          arr[j-1] = temp;
          bubbleAssignments=bubbleAssignments+3; //there are three assingments done above
        }
      }
      // For each pass of the loop, at least one more number is sorted
      numsorted++;
    }
    results[0]=bubbleComparisons;
    results[1]=bubbleAssignments;
    System.out.printf("\n\nBubble Sort: \n");
    for (int ii = 0; ii<size;ii++){
      System.out.printf(arr[ii]+" ");
    }

    return results;
  }

  public int[] selectionSort(int[] arr){

    int selectionComparisons = 0;
    int selectionAssignments = 0;
    int pivot = arr.length - 1;
    int maxval = arr[pivot];
    int maxindex = pivot;
    int[] results = new int[2];

    for (int i = 1; i <arr.length; i++){
      for (int j = pivot -1; j >= 0; j--){
        selectionComparisons++;
        if (arr[j] > arr[pivot]){
          maxval = arr[j];
          maxindex = j;
        }
      }

      int temp = arr[pivot]; //#1
      arr[pivot] = maxval;   //#2
      arr[maxindex] = temp;  //#3
      //three assignments were done

      pivot--;
      maxval = arr[pivot];
      maxindex = pivot;
      selectionAssignments=selectionAssignments+3;
    }

    results[0]=selectionComparisons;
    results[1]=selectionAssignments;
    System.out.printf("\n\nSelection Sort: \n");
    for (int ii = 0; ii<arr.length;ii++){
      System.out.printf(arr[ii]+" ");
    }
    return results;
  }

  public int[] insertionSort(int[] arr){

    int insertionAssignments = 0;
    int insertionComparisons = 0;
    int[] results = new int[2];
    int size = arr.length;

    for (int ii = 1; ii < size; ii++){ //index through all the entire list
      int tempNumber = arr[ii];
      int jj=ii-1;

      while (jj>=0 && arr[jj]>tempNumber ){ //if the number is not in the right spot then move it
        arr[jj+1]=arr[jj];
        jj--;

        insertionComparisons++; //one comaprison
        insertionAssignments++;
      }
      arr[jj+1]=tempNumber; //next goes through
    }

    results[0]=insertionComparisons;
    results[1]=insertionAssignments;
    System.out.printf("\n\nInsertion Sort: \n");
    for (int ii = 0; ii<arr.length;ii++){
      System.out.printf(arr[ii]+" ");
    }

    return results;
  }

  public int [] returnArrayInsertionSort(int[] arr){ //for the two D array sorting i needed a sorting method to return the sorted array not the stats of the array
    int size = arr.length;

    for (int ii = 1; ii < size; ii++){ //index through all the entire list
      int tempNumber = arr[ii];
      int jj=ii-1;

      while (jj>=0 && arr[jj]>tempNumber ){ //if the number is not in the right spot then move it
        arr[jj+1]=arr[jj];
        jj--;

        insertionComparisons++; //one comaprison
        insertionAssignments++;
      }
      arr[jj+1]=tempNumber; //next goes through
    }
    Return arr;
  }

  public int[][] twoDInsertionSort(int[][] arr){




  }

}

class Homework4{
  public static void main (String[] args){
    Scanner scan = new Scanner(System.in);
    Random rand = new Random();
    Sort srt = new Sort();
    System.out.printf("Part 1\nEnter an array length:  ");
    String stringArrayLength = scan.next();
    System.out.printf("Enter an the maximum value:  ");
    String stringMaximumValue = scan.next();
    int arrayLength = Integer.parseInt(stringArrayLength);
    int maximumValue = Integer.parseInt(stringMaximumValue);
    int[] unsortedArray = new int[arrayLength];
    int[] bubbleStats = new int[2];
    int[] selectionStats = new int[2];
    int[] insertionStats = new int[2];

    //creating the unsorted array
    for (int ii = 0; ii<arrayLength;ii++){
      unsortedArray[ii] = rand.nextInt(maximumValue);
    }

    //printing the unsorted array
    System.out.printf("\nUnsorted Array: \n");
    for (int ii = 0; ii<arrayLength;ii++){
      System.out.printf(unsortedArray[ii]+" ");
    }

    selectionStats = srt.selectionSort(unsortedArray.clone());
    bubbleStats = srt.bubbleSort(unsortedArray.clone());
    insertionStats = srt.insertionSort(unsortedArray.clone());

    //Printing the table of stats
    System.out.printf("\n\tComparisons \tAssignments");
    System.out.printf("\nSelection:\t%d\t\t%d",insertionStats[0],insertionStats[1]);
    System.out.printf("\nSelection:\t%d\t\t%d",selectionStats[0],selectionStats[1]);
    System.out.printf("\nBubble:\t\t%d\t\t%d",bubbleStats[0],bubbleStats[1]);


    //Part 2
    // Two dimentional array
    System.out.printf("\n\nPart 2\nEnter an array height (rows):  ");
    String stringArrayHeight = scan.next();
    System.out.printf("Enter an array width (cols):  ");
    String stringArrayWidth = scan.next();
    System.out.printf("Enter an the maximum value:  ");
    String stringMaximumValue2 = scan.next();
    int arrayHeight = Integer.parseInt(stringArrayHeight);
    int arrayWidth = Integer.parseInt(stringArrayWidth);
    int maximumValue2 = Integer.parseInt(stringMaximumValue2);
    int[][] unsorted2DArray = new int[arrayHeight][arrayWidth];

    //creating the unsorted 2D array
    for (int ii = 0; ii<arrayHeight;ii++){ //height  2D[height][width]
      for (int jj = 0; jj<arrayWidth;jj++){ //width
        unsorted2DArray[ii][jj] = rand.nextInt(maximumValue2);
      }
    }

    //printing the unsorted 2D array
    System.out.printf("\nUnsorted:\n");
    for (int ii = 0; ii<arrayHeight;ii++){ //height
      for (int jj = 0; jj<arrayWidth;jj++){ //width
        System.out.printf("%4d", unsorted2DArray[ii][jj]);
      }
      System.out.printf("\n");
    }



  }
}
