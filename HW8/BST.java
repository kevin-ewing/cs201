/**********************************************************
 *
 * Author: Kevin Ewing
 * Middlebury College
 * Data Structures Fall 2020
 *
 * BST: Use Hoework8.java file to test this file.
 * Implements a BST of books to form a sorted library
 *
***********************************************************/

public class BST{
  private int size;
  public Node root;

  class Node{
    Book data;
    Node parent;
    Node left, right;

    public Node(Book book){
      data = book;
      parent = null;
      left = null;
      right = null;
    }
  }

  public BST(){
    root = null;
    size = 0;
  }

  public void print(){ //not the "true" printing method (needed one with recursive ability) see: printTree
    if (root == null){
      System.out.println("The library is empty");
    }
    else{
      printTree(root); //calls the recursive function
    }
  }

  private void printTree(Node r){ //recursive function to print the tree
    if (r.left != null){
      printTree(r.left);
    }
    System.out.printf("Call Number: %.3f, %s, \"%s\"\n",r.data.ddn,r.data.author,r.data.title);
    if (r.right !=null){
      printTree(r.right);
    }
  }

  public int size(){ //getter size function
    return size;
  }

  public void add(Book book){
    Node iterNode = root;
    Node newNode = new Node(book);
    boolean more = true;

    if (iterNode == null){ //if the tree is empty
      root = newNode;
      size++;
      more = false;
    }

    while (more == true){ //iterate though the tree until you find te spot to add the node (book)
      if (newNode.data.ddn < iterNode.data.ddn){ //try to go left
        if (iterNode.left == null){ //if cant go left add new node left
          iterNode.left = newNode;
          newNode.parent = iterNode;
          size++;
          more = false;
        }
        else{
          iterNode = iterNode.left; //if can go left... go left... duhhhhh
        }
      }
      else{ //try to go right
        if (iterNode.right == null){ //if cant go right add new node right
          iterNode.right = newNode;
          newNode.parent = iterNode;
          size++;
          more = false;
        }
        else{
          iterNode = iterNode.right; //if can go right... go right... duhhhh
        }
      }
    }
  }

  public Book remove(double ddn){
    //method that deletes the node with the input ddn
    Node iterNode = root;
    Book deletedBook = null; //book associated with the deleted node

    if (iterNode == null){ //if the tree is empty say that the book isnt available
      System.out.println("This title is not available.");
      return null;
    }
    boolean more = true;
    while (more == true){ //iterate though the tree until you get to the right ddn
      if (ddn < iterNode.data.ddn){ //if the ddn is too small go left
        if (iterNode.left == null){
          System.out.println("This title is not available.");
          break;
        }
        else{
          iterNode = iterNode.left; //go left
        }
      }
      else if (ddn > iterNode.data.ddn){ //if the ddn is too large go right
        if (iterNode.right == null){
          System.out.println("This title is not available.");
          break;
        }
        else{
          iterNode = iterNode.right; //go right
        }
      }
      else if(ddn == iterNode.data.ddn){ //if you have reached the node with the correct ddn
        size--; //size goes down
        deletedBook = iterNode.data; //data of the node is the book. This book is then returned

        //First zero case (I keep finding new cases whoops) this is the last node
        if (iterNode == root && iterNode.left == null && iterNode.right == null){
          root= null;
          break;
        }

        //Zero case: node is a leaf
        if (iterNode.right==null && iterNode.left==null){
          if (iterNode.parent.data.ddn<iterNode.data.ddn){
            iterNode.parent.right = null;
            System.out.println("Right Leaf");
          }
          if (iterNode.parent.data.ddn>iterNode.data.ddn){
            iterNode.parent.left = null;
            System.out.println("Left Leaf");
          }
          break;
        }

        //First case: no right subtree of node
        if (iterNode.right == null){
          iterNode.data=iterNode.left.data;
          iterNode.left=iterNode.left.left;
          try{
            iterNode.right=iterNode.left.right;
          }
          catch(Exception e){}
          System.out.println("No Right Subtree");
          break;
        }

        //Second case: no left subtree of node
        else if (iterNode.left == null){
          iterNode.data=iterNode.right.data;
          iterNode.right=iterNode.right.right;
          try{
            iterNode.left=iterNode.right.left;
          }
          catch(Exception e){}
          System.out.println("No Left Subtree");
          break;
        }

        //Third case: left subtree has no right child
        else if (iterNode.left.right == null){
          iterNode.data=iterNode.left.data;
          iterNode.left=iterNode.left.left;
          System.out.println("Left Subtree has no right child");
          break;
        }

        //Fourth case: right subtree has no left child
        else if (iterNode.right.left == null){
          iterNode.data=iterNode.right.data;
          iterNode.right=iterNode.right.right;
          System.out.println("Right Subtree has no left child");
          break;
        }

        //final case: both left and right subtree have children
        else {
          Node iterNode2 = root.left;
          while(iterNode2.right != null){
            iterNode2 = iterNode2.right;
          }
          iterNode2.parent.right=iterNode2.left;
          iterNode.data=iterNode2.data;
          System.out.println("Final Case");
          break;
        }
      }
    }
    return deletedBook; //return the book that has been deleted
  }

  public boolean isAvailable(double ddn){
    //method that returns true if the book is available and false otherwise
    Node iterNode = root;
    if (iterNode == null){
      return false;
    }
    boolean more = true;
    while (more == true){
      if (ddn == iterNode.data.ddn){
        return true;
      }
      else if (ddn < iterNode.data.ddn){
        if (iterNode.left == null){
          return false;
        }
        else{
          iterNode = iterNode.left;
        }
      }
      else{
        if (iterNode.right == null){
          return false;
        }
        else{
          iterNode = iterNode.right;
        }
      }
    }
    return false;
  }
}

class Book{
  //book class (book objects will be stored in the BST)
  public double ddn; // Dewey Decimal Number
  public String author;
  public String title;

  public Book(double _ddn, String _author, String _title){
    ddn = _ddn;
    author = _author;
    title = _title;
  }

  public String toString(){ //this will be used when printing the Book (when they have been checked out)
    String formatedString = String.format("Call Number: %.3f, %s, \"%s\"",ddn,author,title);
    return formatedString;
  }
}
