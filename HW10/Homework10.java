/**********************************************************
 *
 * Author: Kevin Ewing
 * Middlebury College
 * Data Structures Fall 2020
 *
 * Homework 10: Car Dealership Hash Table
 * Compilation: javac Homework10.java
 * Running: java Homework10 Cars.txt CarsSold.txt
 *
***********************************************************/
import java.util.Scanner;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;

class Car{
  //car class variables
  int month;
  String vin;
  int year;
  String manufact;
  String vtype;


  Car (String _month, String _vin, String _year, String _manufact, String _vtype){ //creating a new car
    month = Integer.parseInt(_month);
    vin = _vin;
    year = Integer.parseInt(_year);
    manufact = _manufact;
    vtype = _vtype;
  }

  public String toString(){ // will be used when printing the cars
    String formatedString = String.format("%s %d %s %s\n", vin,year,manufact,vtype);
    return formatedString;
  }
}

class HashTable{
  class Node{
    //node class in the hash table class
    Car car;
    boolean deleted;

    public Node(Car _data){
      //stores values of car data type
      car = _data;
      deleted = false;
    }

    public void delete(){
      deleted = true;
    }

    public boolean isDeleted(){
      //getter function
      return deleted;
    }
  }

  //HashTable definitions
  public int size;
  public Node[] inventory;
  public int capacity;

  HashTable(){
    //initializing hash table
    capacity = 100;
    inventory = new Node[capacity];
    size = 0;
    for(int iter = 0; iter < capacity; iter++){
      inventory[iter] = null;
    }
  }

  public long hashCode(String s){
    //given code to create hash code
    long hash = 0;
    for(int i = 0; i<s.length(); i++){
        hash = hash * 31 + s.charAt(i);
    }
    return hash;
  }

  public void add(Car car){
    //add function
    Node newNode = new Node(car);
    long hashcode = hashCode(car.vin);
    int index = (int) Math.abs(hashcode % capacity); //adjusting key

    int linProbe = 0; //setting up
    boolean done = false;
    while (done == false){
      int iterIndex = (index + linProbe) % capacity; //adjusting key

      if (inventory[iterIndex] == null || inventory[iterIndex].isDeleted()==true){
        inventory[iterIndex] = newNode;
        done = true;
        size++;
      }
      linProbe++;
      if (linProbe == capacity) //resize if necisary
        resize();
    }
  }

  public int delete(String vin){
    //delete function
    long hashcode = hashCode(vin);
    int index = (int) Math.abs(hashcode % capacity);//adjusting key

    int linProbe = 0; //setting up
    boolean done = false;

    while (done == false && linProbe<capacity){
      int iterIndex = (index +linProbe) % capacity;//adjusting key

      //if null
      if (inventory[iterIndex] == null){
        System.out.println("Item not found.");
        return linProbe + 1;
      }
      else if (inventory[iterIndex] != null && inventory[iterIndex].car.vin.equals(vin)){
        //Lazy deleted
        if(inventory[iterIndex].isDeleted()){
          System.out.println("Item already deleted.");
          return linProbe + 1;
        }
        //main case
        else{
          inventory[iterIndex].delete();
          size--;
          return linProbe + 1;
        }
      }
     linProbe++;
    }
    return linProbe +1;
  }

  public void resize(){
    capacity = 2*capacity;
    //have to completely redo the hash table and adjust keys according to new length
    Node[] temperary = new Node[capacity];

    for(int iter = 0; iter < capacity; iter++){
      temperary[iter] = null; //initializing new table;
    }

    for (int index1 = 0; index1 < capacity/2; index1++){ //go though old hash table
      if(inventory[index1] != null){
        long hashcode = hashCode(inventory[index1].car.vin);
        int index = (int) Math.abs(hashcode % capacity);//adjusting key

        if (temperary[index] == null){
          temperary[index] = inventory[index1];
        }
        else if (inventory[index1].isDeleted() == false){
          int linProbe = 1;
          boolean done = false;
          while (done == false){
            int iterIndex = (index + linProbe) % capacity;//adjusting key

            if (temperary[iterIndex] == null || temperary[iterIndex].isDeleted()==true){
              temperary[iterIndex] = inventory[index1];
              done = true;
            }
            linProbe++;
          }
        }
      }
    }
    inventory = temperary.clone(); //finalize new hash table;
  }

  public void print(){
    //print function for the hash table
    int iter = 0;
    while (iter < capacity){
      if (inventory[iter] != null && inventory[iter].deleted == false){
        System.out.printf("%03d: %s %d %s %s \n",iter,inventory[iter].car.vin,inventory[iter].car.year,inventory[iter].car.manufact, inventory[iter].car.vtype);
      }
      iter ++;
    }
  }
}

enum Months{
  January,
  February,
  March,
  April,
  May,
  June,
  July,
  August,
  September,
  October,
  November,
  December;
}

class Homework10{
  public static void main (String[] args){

    //readinng files
    Scanner carsFileScan;
    Scanner carsSoldFileScan;
    File carsFile = new File(args[0]);
    File carsSoldFile = new File(args[1]);

    try{ //try reading files
      carsFileScan = new Scanner(carsFile);
      carsSoldFileScan = new Scanner(carsSoldFile);
    }
    catch(Exception e){
      return;
    }

    ArrayList<Car> carsIn = new ArrayList<Car>();
    ArrayList<Car> carsOut = new ArrayList<Car>();
    HashTable hashTable = new HashTable();

    while(carsFileScan.hasNextLine()){
      String line = carsFileScan.nextLine();
      String[] temp = line.split(" ");

      Car tempCar = new Car(temp[0], temp[1], temp[2], temp[3], temp[4]);
      carsIn.add(tempCar);
    }

    // Read in the cars that customers have purchased
    while(carsSoldFileScan.hasNextLine()){
      String line = carsSoldFileScan.nextLine();
      String[] temp = line.split(" ");

      Car tempCar = new Car(temp[0], temp[1], temp[2], temp[3], temp[4]);
      carsOut.add(tempCar);
    }

    System.out.printf("Welcome to the CS 201 New/Used Car Dealership!\n");

    int month = 1;
    while(month <= 12){
      int monthIn = 0;
      int monthOut = 0;

      while(!carsIn.isEmpty() && carsIn.get(0).month == month){
        hashTable.add(carsIn.get(0));
        monthIn++;
        carsIn.remove(0);
      }

      double retreival = 0;
      while(!carsOut.isEmpty() && carsOut.get(0).month == month){
        retreival += hashTable.delete(carsOut.get(0).vin);

        monthOut++;
        carsOut.remove(0);
      }

      double average = retreival / (double) monthOut;

      System.out.printf("\n%s Inventory\n", Months.values()[month-1]);
      System.out.printf("New: %02d, Sold: %2d, Total: %03d, Capacity: %03d\n", monthIn, monthOut, hashTable.size, hashTable.capacity);
      System.out.printf("Avg num. of keys indexed for retreival: %04.2f\n",average);

      month++;
    }

    Scanner scanLine = new Scanner(System.in);
    System.out.printf("\nPrint Inventory (y/n): ");
    String input = scanLine.nextLine();
    if (input.toLowerCase().equals("y")){
      hashTable.print();
    }
  }
}
//Thank you for a great semester! I really enjoyed this class!
