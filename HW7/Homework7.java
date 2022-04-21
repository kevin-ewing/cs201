/*
Compilation: javac Homework7.java
Execution: java Homework7

CS201 Homework 7
Author: Kevin Ewing

Creates a priority queue based on an input file then assigns CPU recourses to proccess that queue
*/
import java.io.File;
import java.util.Scanner;

class PriorityQueue {
  //priority queue class (does not extend linked list)
  class Node{
    Process data; //the data stored will be of Proccess type
    Node prev;
    Node next;

    public Node(Process d){ //constrotor takes in this data and creates a 'blank' node
      data = d;
      prev = null;
      next = null;
    }
  }

  Node head;
  Node tail;
  int size;

  public PriorityQueue(){
    //innitializing
    head = null;
    tail = null;
  }

  public void add(Process d){
    //add function
    size++; //so size function will work
    Node newNode = new Node(d);
    Node iterNode = head;
    Node prevNode = null;

    // list is empty
    if (head == null){
      head = newNode;
      tail = newNode;
    }

    //if the new node priory is lower
    else if(iterNode.data.priority>newNode.data.priority){
      newNode.next = head;
      head.prev = newNode;
      head = newNode;
    }

    //if the node priority isnt lower then iterate though
    else{
      while (iterNode != null && iterNode.data.priority<=newNode.data.priority){
        prevNode = iterNode;
        iterNode = iterNode.next;
      }

      //if you reach the end
      if (iterNode == null){
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
      }

      //general case (ish)
      else{
        iterNode.prev = newNode;
        newNode.next = iterNode;
        prevNode.next = newNode;
        newNode.prev = prevNode;
      }
    }
  }

  public int remove(){
    size--; //so size function will work
    //if this is the last term
    if (head.next == null){
      return head.data.time;
    }
    //general case
    else{
      int processTime = head.data.time;
      head = head.next;
      return processTime;
    }
  }

  public void print(){
    //print function
    Node curr = head;
    while(curr != null){
      System.out.println(curr.data);
      curr = curr.next;
    }
  }

  public int size(){
    //getter function for size
    return size;
  }
}

class Process{
  //variables for the process class
  public int pid;
  public String task;
  public int time;
  public int priority;

  public Process(int _pid, String[] input){ //function that takes in a string array and assigns the different variables to each index
    if(input.length == 2  || input.length ==3){ //if the index is not the right length throw error
      pid = _pid;
      if (input.length == 2){
        task = input[0];
        time = Integer.parseInt(input[1]);
        priority = 100000;
      }
      if (input.length == 3){
        task = input[0];
        time = Integer.parseInt(input[1]);
        priority = Integer.parseInt(input[2]);
      }
    }
    else{
      throw new IllegalArgumentException("Entry does not have correct number of inputs.");
    }
  }

  public String toString(){ //allows the print() to work correctly
    String formatedString = String.format ("PID: %03d | %18s | %4d | Priority: %-15d", pid, task, time, priority);
    return formatedString;
  }
}


class Homework7{
  public static void main(String[] args){
    System.out.println("Queued Jobs.");
    File file = new File("Processes.txt"); //opens file as object 'file1'
    Scanner fileScan;

    try {
      fileScan = new Scanner(file);; //uses the scanner to look at the file
    }
    catch (Exception e){ //if the file can not be opened give error
      System.out.println(e);
      return;
    }

    PriorityQueue prique = new PriorityQueue(); //initializing the Priority Queue
    int pidNum=-1;

    while(fileScan.hasNextLine()){ //index though every line of the file
      String inputLine = fileScan.nextLine(); //look at one line at a time
      String[] input = inputLine.split(","); //as mentioned in the lab this allows us to save each entry separated by a comma to each part of the array
      pidNum++;

      try{ //attempt to put the data in each variable at each index using the votingData function above
        Process pc = new Process(pidNum,input);
        prique.add(pc);
      }
      catch(Exception e){ //if error is thrown describe the error and sprint the line
        System.out.println(e);
      }
    }
    prique.print();

    System.out.printf("\nExecuting Jobs.\n");
    int cpu1 = 0; //CPU 1
    int cpu2 = 0; //CPU 2
    while (prique.size()>0){ //iterate though the entire queue
      if (cpu1<=cpu2){ // whichever CPU is "done" first
        System.out.printf("CPU1: Processing PID: %03d | %18s | %4d \n", prique.head.data.pid, prique.head.data.task, prique.head.data.time);
        cpu1 += prique.remove();
      }
      else{
        System.out.printf("CPU2: Processing PID: %03d | %18s | %4d \n", prique.head.data.pid, prique.head.data.task, prique.head.data.time);
        cpu2 += prique.remove();
      }
    }
    System.out.println("Finished."); // all done :)
  }
}
