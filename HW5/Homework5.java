/*
Compilation:  javac Homework5.java
Execution:    java Homework5

CS201 Homework Assignment 5
Author: Kevin Ewing

Implements three parts of the assingment:
Part 1: Finds the area and perimeter of an input shape
Part 2: Asks the user for numbers of randomly generated shapes and then finds the total area and perimeter
Part 3: Sorts the shapes generated in part two baced on the area
*/

import java.util.Scanner;
import java.lang.Math;
import java.util.Random;

class Shape{
  //parent function
  public Double area;
  public Double perimeter;
  public String type;
  Scanner scan = new Scanner(System.in);
  Random rand = new Random();

  //only static function that is allowed in this assingment
  public static Shape[] sort(Shape[] shapes){
    //implements bubble sort to sort the array of shapes baced on their areas
    int size = shapes.length;
    int numsorted = 0;
    Shape temp;
    Shape[] results = new Shape[size];

    for(int i = 1; i < size; i++){
      for (int j = 1; j < (size - numsorted); j++){
        if(shapes[j-1].area > shapes[j].area){ //comparison of shape areas
          temp = shapes[j];
          shapes[j] = shapes[j-1];
          shapes[j-1] = temp;
        }
      }
      numsorted++;
    }
    return shapes;
  }
}

class Triangle extends Shape{
  //creates a triangle

  public Triangle(){
    //first constructor is used in part 1
    System.out.printf("Enter the base of the triangle: ");
    Double base = scan.nextDouble();
    System.out.printf("Enter the height of the triangle: ");
    Double height = scan.nextDouble();

    area = (height*base)/2;
    perimeter = height + base + Math.sqrt((height*height)+(base*base)); //last term is the length of the hypot

    System.out.printf("Triangle: Area = %.2f, Perimeter = %.2f",area,perimeter);
  }

  public Triangle(int maxLength){
    //second constructor is used in part 2
    Double base = Double.valueOf(rand.nextInt(maxLength) + 1);
    Double height = Double.valueOf(rand.nextInt(maxLength) + 1);

    type = "Triangle";
    area = (height*base)/2;
    perimeter = height + base + Math.sqrt((height*height)+(base*base)); //last term is the length of the hypot
    System.out.printf("Triangle:\tb:%.2f\th:%.2f\ta:%.2f\tp:%.2f\n",base,height,area,perimeter);
  }
}

class Circle extends Shape{
  //creates a circle

  public Circle(){
    //first constructor is used in part 1
    System.out.printf("Enter the diameter length of the circle: ");
    Double diameter = scan.nextDouble();

    area = Math.PI*((diameter/2)*(diameter/2));
    perimeter = 2*Math.PI*(diameter/2);

    System.out.printf("Circle: Area = %.2f, Perimeter = %.2f",area,perimeter);
  }

  public Circle(int maxLength){
    //second constructor is used in part 2
    Double diameter = Double.valueOf(rand.nextInt(maxLength) + 1);

    type = "Circle    ";//need extra spaces so the tab lines them up best in part 3
    area = Math.PI*((diameter/2)*(diameter/2));
    perimeter = 2*Math.PI*(diameter/2);
    System.out.printf("Circle:\t\td:%.2f\ta:%.2f\tp:%.2f\n",diameter,area,perimeter);
  }

}

class Square extends Shape{
  //creates a square

  public Square(){
    //first constructor is used in part 1
    System.out.printf("Enter the side length of the square: ");
    Double side = scan.nextDouble();

    area = side*side;
    perimeter = 4*side;

    System.out.printf("Square: Area = %.2f, Perimeter = %.2f",area,perimeter);
  }

  public Square(int maxLength){
    //second constructor is used in part 2
    Double side = Double.valueOf(rand.nextInt(maxLength) + 1);

    type="Square    ";//need extra spaces so the tab lines them up best in part 3
    area = side*side;
    perimeter = 4*side;
    System.out.printf("Square:\t\tw:%.2f\th:%.2f\ta:%.2f\tp:%.2f\n",side,side,area,perimeter);
  }
}

class Rectangle extends Shape{
  //creates a rectangle

  public Rectangle(){
    //first constructor is used in part 1
    System.out.printf("Enter the width of the rectangle: ");
    Double width = scan.nextDouble();
    System.out.printf("Enter the height of the rectangle: ");
    Double height = scan.nextDouble();

    area = height*width;
    perimeter = 2*height + 2*width;

    System.out.printf("Rectangle: Area = %.2f, Perimeter = %.2f",area,perimeter);
  }

  public Rectangle(int maxLength){
    //second constructor is used in part 2
    Double height = Double.valueOf(rand.nextInt(maxLength) + 1);
    Double width = Double.valueOf(rand.nextInt(maxLength) + 1);

    type="Rectangle";
    area = height*width;
    perimeter = 2*height + 2*width;
    System.out.printf("Rectangle:\tw:%.2f\th:%.2f\ta:%.2f\tp:%.2f\n",width,height,area,perimeter);
  }

}

class Trapezoid extends Shape{
  //creates a trap

  public Trapezoid(){
    //first constructor is used in part 1
    System.out.printf("Enter the top base length of the trapezoid: ");
    Double topBase = scan.nextDouble();
    System.out.printf("Enter the bottom base length of the trapezoid: ");
    Double bottomBase = scan.nextDouble();
    System.out.printf("Enter the height of the trapezoid: ");
    Double height = scan.nextDouble();

    area = ((topBase+bottomBase)/2)*height;
    perimeter = 2*Math.sqrt((height*height)+(((topBase-bottomBase)/2)*((topBase-bottomBase)/2)))+topBase+bottomBase;

    System.out.printf("Trapezoid: Area = %.2f, Perimeter = %.2f",area,perimeter);
  }

  public Trapezoid(int maxLength){
    //second constructor is used in part 2
    Double topBase = Double.valueOf(rand.nextInt(maxLength) + 1);
    Double bottomBase = Double.valueOf(rand.nextInt(maxLength) + 1);
    Double height = Double.valueOf(rand.nextInt(maxLength) + 1);

    type = "Trapezoid";
    area = ((topBase+bottomBase)/2)*height;
    perimeter = 2*Math.sqrt((height*height)+(((topBase-bottomBase)/2)*((topBase-bottomBase)/2)))+topBase+bottomBase;
    System.out.printf("Trapezoid:\tb1:%.2f\tb2:%.2f\th:%.2f\ta:%.2f\tp:%.2f\n",topBase,bottomBase,height,area,perimeter);
  }

}

class Homework5{
  public static void main(String[] args){
    //main function
    Scanner scan = new Scanner(System.in);


    //PART 1:
    System.out.printf("Part 1\nSelect a shape\n1: Triangle\n2: Rectangle\n3: Square\n4: Circle\n5: Trapezoid\nEnter a value: ");
    int desiredShape = scan.nextInt();

    //following switch creates an instance of a shape depending on which number was selected. The constructor with no inputs (IE the ones called bellow) will prompt the user for the dimentions of the shape
    switch(desiredShape){
      case 1:
        Shape triangle = new Triangle();
        break;
      case 2:
        Shape rectangle = new Rectangle();
        break;
      case 3:
        Shape square = new Square();
        break;
      case 4:
        Shape circle = new Circle();
        break;
      case 5:
        Shape trapezoid = new Trapezoid();
        break;
      default:
        System.out.println("Invalid entry");
    }


    //PART 2:
    System.out.printf("\n\nPart 2\nEnter the number of shapes to create for shape.\nTriangle: "); //not sure what this means haha his program said this though
    int numOfTriangles = scan.nextInt();
    System.out.printf("Rectangle: ");
    int numOfRectangles = scan.nextInt();
    System.out.printf("Square: ");
    int numOfSquares = scan.nextInt();
    System.out.printf("Circle: ");
    int numOfCircles = scan.nextInt();
    System.out.printf("Trapezoid: ");
    int numOfTrapezoids = scan.nextInt();
    System.out.printf("Enter a max side length: ");
    int maxSideLength = scan.nextInt();

    System.out.printf("\nThe following shapes were created.\n");
    int arraySize = numOfTriangles+numOfRectangles+numOfSquares+numOfCircles+numOfTrapezoids;
    Shape[] listOfShapes = new Shape[arraySize]; //creates the array for the shapes
    int indexThroughShapeList=0;
    for (int ii=0; ii<numOfTriangles;ii++){ //adds the triangles to the shape array
      listOfShapes[indexThroughShapeList]=new Triangle(maxSideLength);
      indexThroughShapeList++;
    }
    for (int ii=0; ii<numOfRectangles;ii++){//adds the rectangles to the shape array
      listOfShapes[indexThroughShapeList]=new Rectangle(maxSideLength);
      indexThroughShapeList++;
    }
    for (int ii=0; ii<numOfSquares;ii++){//adds the squares to the shape array
      listOfShapes[indexThroughShapeList]=new Square(maxSideLength);
      indexThroughShapeList++;
    }
    for (int ii=0; ii<numOfCircles;ii++){//adds the circles to the shape array
      listOfShapes[indexThroughShapeList]=new Circle(maxSideLength);
      indexThroughShapeList++;
    }
    for (int ii=0; ii<numOfTrapezoids;ii++){//adds the traps to the shape array
      listOfShapes[indexThroughShapeList]=new Trapezoid(maxSideLength);
      indexThroughShapeList++;
    }

    Double totalArea = 0.0;
    Double totalPerim = 0.0;
    for (int iter = 0; iter<arraySize;iter++){ //iterates though and adds the areas and perimeters
      totalArea+=listOfShapes[iter].area;
      totalPerim+=listOfShapes[iter].perimeter;
    }
    System.out.printf("\nTotal Area: %.2f\nTotal Perimeter: %.2f\n",totalArea,totalPerim);


    //PART 3
    System.out.printf("\nPart 3\nShapes sorted by area.\n");
    Shape[] sortedShapes= new Shape[arraySize];//creates a second arraw that is the same as the first
    sortedShapes = Shape.sort(listOfShapes);//assigns the sortted values to the new array
    for (int iter = 0; iter<arraySize;iter++){
      System.out.printf("%s\ta:%.2f\tp:%.2f\n",sortedShapes[iter].type,sortedShapes[iter].area,sortedShapes[iter].perimeter); //final printing of sorted Shape array
    }
  }
}
