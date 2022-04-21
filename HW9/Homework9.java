/**********************************************************
 *
 * Author: Kevin Ewing
 * Middlebury College
 * Data Structures Fall 2020
 *
 * Homework 9: Emergency Room min-heap implementation
 * Compilation: javac Homework9.java
 * Running: java Homework9 Patients.txt SymptomInfo.txt
 *
***********************************************************/

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

class Patient{
  String name;
  String symptom;
  int priority; //lower is more urgent;
  int timeIn;
  int timeCare; //time it takes for that symptom


  Patient (String _name, String _symptom, String _priority, String _timeIn, String _timeCare){ //creating a new patient
    name = _name;
    symptom = _symptom;
    priority = Integer.parseInt(_priority);
    timeIn = Integer.parseInt(_timeIn);
    timeCare = Integer.parseInt(_timeCare);
  }

  public String toString(){ // will be used when printing the patients (not in final product)
    int timeOut = timeCare+timeIn;
    String formatedString = String.format("Patient: %-10s Reason: %-15s Arrival: %04d | Current: %04d\n", name,symptom,timeIn,timeCare);
    return formatedString;
  }
}

class Doctor{ //doctor class
  public int idNum;
  private boolean available;
  public int endCare;
  public int totalTime; //needed for the optional part

  Doctor(int _id){
    idNum=_id;
    available=true;
  }

  public Boolean isAvailable(){ //getter function for availability
    return available;
  }

  public void updateAvailable(int time){ //with input time it decides if the doctor is avaialbe yet
    if (endCare <= time){
      available = true;
    }
    else{
      available = false;
    }
  }

  public void seePatient(int curr, Patient pat){ //returns time doctor is back available at
    totalTime+=pat.timeCare; //needed for the optional part
    if (((curr+pat.timeCare)%100)<60){ //this is needed in order to keep the times real
      endCare=curr+pat.timeCare;
    }
    else{
      endCare=curr+pat.timeCare+40;
    }
  }
}

class Homework9{
  public static void main(String[] args){
    //readinng files
    File file1 = new File(args[0]);
    Scanner scan1;
    File file2 = new File(args[1]);
    Scanner scan2;

    try{ //try reading files
      scan1 = new Scanner(file1);
      scan2 = new Scanner(file2);
    }
    catch(Exception e){
      return;
    }

    String line1;
    String[] info1;
    String line2;
    String[] info2;
    ArrayList<String[]> symptomInfo = new ArrayList<String[]>(); //list of all the symptom info
    ArrayList<Patient> inputPatientList = new ArrayList<Patient>(); //temperary list of all patients

    while(scan2.hasNext()){
      line2 = scan2.nextLine();
      info2 = line2.split(" ");
      symptomInfo.add(info2);
    }

    while(scan1.hasNext()){
      line1 = scan1.nextLine();
      info1 = line1.split(" ");
      String tempPriority;
      String tempTimeCare;
      for (int i = 0; i<symptomInfo.size(); i++){
        if (symptomInfo.get(i)[0].equals(info1[1])){
          tempPriority= symptomInfo.get(i)[1];
          tempTimeCare= symptomInfo.get(i)[2];
          Patient pat = new Patient(info1[0],info1[1],tempPriority,info1[2],tempTimeCare); //creating the pateints and adding them to the temparary list
          inputPatientList.add(pat); //temp list
        }
      }
    }

    ArrayList<Doctor> docArray = new ArrayList<Doctor>(); //all doctors
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to the CSCI 201 Emergency Room.");
    System.out.print("Enter the number of doctors on call: ");
    String StrNumDoc = sc.next();
    int numDoc = Integer.parseInt(StrNumDoc);


    for (int i = 0; i < numDoc; i++){ //creating doctors and adding them to the docArray
      Doctor doc = new Doctor(i);
      docArray.add(doc);
    }

    MinHeap schedule = new MinHeap(); //Created the min heap and am ready to go

    //now we will iterate though 'time'
    for (int timeHour = 00; timeHour<24; timeHour++){
      for (int timeMin = 00; timeMin < 60; timeMin = timeMin+5){ //move though time in 5 minute intervals
        int time = 0;
        if (timeMin<10){
          time = Integer.parseInt(timeHour + "0" + timeMin);
        }
        else{
          time = Integer.parseInt(timeHour + "" + timeMin);
        }

        for (int ii = 0; ii<inputPatientList.size(); ii++){
          //adding the appointments in time
          if (time == inputPatientList.get(ii).timeIn){
            schedule.add(inputPatientList.get(ii));
          }
        }


        if (schedule.size()>0){ //checking the availability of each doctor
          for (int i = 0; i < docArray.size(); i++){
            Doctor tempDoc = docArray.get(i);
            tempDoc.updateAvailable(time); //update available
            if (tempDoc.isAvailable() && schedule.size()>0){ //if is available then take patient
              tempDoc.seePatient(time,schedule.mh.get(1)); //see patient
              System.out.printf("Dr.%02d Patient: %-10s Reason: %-15s Arrival: %04d | Current: %04d\n",tempDoc.idNum, schedule.mh.get(1).name,schedule.mh.get(1).symptom,schedule.mh.get(1).timeIn,time);
              schedule.remove(); //remove the patient in spot 1 of the ArrayList
            }
          }
        }
      }
    }

    //for the extra chalange part
    System.out.println("");
    for (int i = 0; i < docArray.size(); i++){
      System.out.printf("Dr.%02d Time Worked: %02d hours %02d minutes\n",docArray.get(i).idNum,docArray.get(i).totalTime/60,docArray.get(i).totalTime%60);
    }
  }
}


class MinHeap{
  public ArrayList<Patient> mh;
  private int size;

  MinHeap(){
    //initializing the min heap + adding the null value to the index 0
    mh = new ArrayList<Patient>();
    mh.add(null);
    size = 0;
  }

  public void add(Patient newP){
    //adds and calls percolate up
    mh.add(newP);
    size ++;
    percolateUp(mh.size()-1); //last item
  }

  public void remove(){
    //remove and calls percolate down
    mh.remove(1);
    size --;
    percolateDown(1);
  }

  public void percolateUp(int index){
    //recursive function to percolateUp
    if(index > 1 && mh.get(index).priority < mh.get(index/2).priority){
      Patient tempPatient = mh.get(index/2);
      mh.set(index/2, mh.get(index)); //switching values around
      mh.set(index, tempPatient); //switching back into place
      percolateUp(index/2);
    }
  }

  public void percolateDown(int index){
    //recursive function to percolateDown
    if (index*2 > mh.size()-1){ //if the deleted item has no children
      return;
    }
    else if (index*2+1 > mh.size()-1){ //only left branch
      if(mh.get(index*2).priority<mh.get(index).priority){
        Patient tempPatient = mh.get(index*2);
        mh.set(index*2, mh.get(index)); //switching values around
        mh.set(index, tempPatient); //switching back into place
        percolateDown(index*2);
      }
    }
    else{ //both branches
      if (mh.get(index*2).priority < mh.get(index*2+1).priority){ //decide that left is bigger
        Patient tempPatient = mh.get(index*2);
        mh.set(index*2, mh.get(index)); //switching values around
        mh.set(index, tempPatient); //switching back into place
        percolateDown(index*2);
      }

      else{ //decide that right is bigger
        Patient tempPatient = mh.get(index*2+1);
        mh.set(index*2+1, mh.get(index)); //switching values around
        mh.set(index, tempPatient); //switching back into place
        percolateDown(index*2+1);
      }
    }
  }

  public void print(){
    for (int i = 1; i<mh.size(); i++){ //print function (not used in final product)
      System.out.print(mh.get(i));
    }
  }

  public int size(){ //getter size function
    return size;
  }
}
