/*
Compilation: javac Homework6.java
Execution: java Homework6 Cargo.txt Destinations.txt

CS201 Homework 6
Author: Kevin Ewing

Reads two files entered from the command line. Organizes the entries based on location and cargo type
Then removes the entries if the city is contained in the second file
*/

import java.io.File;
import java.util.Scanner;

class LinkedList{
  //linked list will contain the list of all the cargo entries
  class Node{
    TrainData data; //the data stored will be of TrainData type
    Node prev;
    Node next;

    public Node(TrainData d){ //constrotor takes in this data and creates a 'blank' node
      data = d;
      prev = null;
      next = null;
    }
  }

  Node head;
  Node tail;

  public LinkedList(){
    head = null;
    tail = null;
  }

  public void sortAdd(TrainData input){
    //modified add function that organizes the entries before addint them in the list
    //unlike a normal add this function does not just add to the tail
    Node newnode = new Node(input);
    Node iterNode = head;
    Node prevNode = null;

    if (head == null){
      //if the list is not empty
      head = newnode;
      tail = newnode;
    }
    else{
      //city
      //iterate though the list until you find a city in the list that equals the new entry city
      while (iterNode != null && !iterNode.data.city.equals(newnode.data.city)){
        prevNode = iterNode;
        iterNode = iterNode.next;
      }

      //the city was not found so it adds onto the end (how it would normally add)
      if (iterNode == null){
        tail.next = newnode;
        newnode.prev = tail;
        tail = newnode;
      }

      else{
        //cargo type
        while (iterNode != null && iterNode.data.city.equals(newnode.data.city) && !iterNode.data.type.equals(newnode.data.type)){
          prevNode = iterNode;
          iterNode = iterNode.next;
        }

        //if the city and type is not found it adds to the end
        if (iterNode == null){
          tail.next = newnode;
          newnode.prev = tail;
          tail = newnode;
        }

        //if the city dosnt equal the input city add it inside
        else if (!iterNode.data.city.equals(newnode.data.city)) {
          iterNode.prev = newnode;
          newnode.next = iterNode;
          prevNode.next = newnode;
          newnode.prev = prevNode;
        }

        else{
          Node next = iterNode.next;

          newnode.prev = iterNode;
          newnode.next = next;
          iterNode.next = newnode;
          next.prev = newnode;
        }
      }
    }
  }

  public boolean remove(String input){ //will be true if it has removed it
    Node prev = null;
    Node curr = null;

    //head is item we want to delete
    if (head != null && head.data.city.equals(input)){
      System.out.printf("Unloading   %03d: \t%-15s %-15s\n", head.data.idNum, head.data.city, head.data.type);

      //is head the only item in the list
      if (head == tail){
        head = null;
        tail = null;
      }
      else{ //if the head is the desired value
        head=head.next;
        head.prev = null;
      }
    }

    else{ //the head is not the desired value
      curr = head.next; //we can do this because we already checked if the head was the desired value
      prev = head;

      while (curr != null && !curr.data.city.equals(input)){ //iterate though the enitre LinkedLIst
        prev = curr;
        curr = curr.next;
      }
      if (curr != null){ //once we have reached the value
        if (curr ==tail){ //if the value desired is at the tail
          System.out.printf("Unloading   %03d: \t%-15s %-15s\n", tail.data.idNum, tail.data.city, tail.data.type);
          tail = prev;
          tail.next = null;
        }
        //**(general case)**
        else{ //if the desired value is just in the list
          System.out.printf("Unloading   %03d: \t%-15s %-15s\n", curr.data.idNum, curr.data.city, curr.data.type);
          prev.next = curr.next;
          curr.next.prev = prev;
        }
      }
      else{ //if curr == null (we have reached the end of the lsit)
        //System.out.println("Item not found");
        return false;
      }
    }
    return true;
  }

  public void print(){ //prints the linked list
    Node curr = head;
    while (curr != null){
      System.out.println(curr.data);
      curr=curr.next;
    }
  }
}

class TrainData{
  //creating all varables for the voting data
  public int idNum;
  public String city;
  public String type;


  public TrainData(int id, String[] input){ //function that takes in a string array and assigns the different variables to each index
    if(input.length != 2){ //if the index is not the right length throw error
      throw new IllegalArgumentException("Entry does not have correct number of inputs.");
    }
    idNum = id;
    city = input[0];
    type = input[1];
  }

  public String toString(){ //this will be used when printing the allData arrayList
    String formatedString = String.format ("%03d: \t%-15s %-15s", idNum, city, type);
    return formatedString;
  }
}

class Homework6{
  public static void main(String[] args){

    //takes in command line arguments as args[0] and args[1]
    for (int ii=0; ii < args.length; ii++ ){
      System.out.println(args[ii]);
    }

    System.out.println("--Cargo Manifest--");
    File file1 = new File(args[0]); //opens file as object 'file1'
    Scanner fileScan1;

    try {
      fileScan1 = new Scanner(file1);; //uses the scanner to look at the file
    }
    catch (Exception e){ //if the file can not be opened give error
      System.out.println(e);
      return;
    }

    LinkedList trainManafest = new LinkedList(); //initializing the trainManafest
    int idNum=-1;

    while(fileScan1.hasNextLine()){ //index though every line of the file
      String inputLine = fileScan1.nextLine(); //look at one line at a time
      String[] input = inputLine.split(","); //as mentioned in the lab this allows us to save each entry separated by a comma to each part of the array
      idNum++;

      try{ //attempt to put the data in each variable at each index using the votingData function above
        TrainData trainData = new TrainData(idNum,input);
        trainManafest.sortAdd(trainData);
      }
      catch(Exception e){ //if error is thrown describe the error and sprint the line
        System.out.println(e);
      }
    }
    trainManafest.print();

    //Part 2 ------------------------------
    System.out.printf("\n\n\nThe train is departing!\n___________________________________________________________\n\n");
    File file2 = new File(args[1]);
    Scanner fileScan2;

    try {
      fileScan2 = new Scanner(file2);; //useing the scanner to look at the file
    }
    catch (Exception e){ //if the file can not be opened print error
      System.out.println(e);
      return;
    }

    while(fileScan2.hasNextLine()){ //go though every line of the mannafest
      String input = fileScan2.nextLine();
      System.out.printf("\n\nNow arriving in %s. Unloading cargo.\n",input); //print the location

      try{ //attempt to put the data in each variable at each index using the votingData function above
        while (trainManafest.remove(input)); //remove all those parts form the linked list until there are no more of that city
      }
      catch(Exception e){ //if error is thrown describe the error and sprint the line
        System.out.println(e);
      }
    }
    System.out.printf("\n\nWhat's left on the train?\n");
    trainManafest.print(); //all the items that have not been removed from the train
  }
}
