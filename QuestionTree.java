//Max Tran, CSE 143, May 26, 2016
//TA: Rebecca Yuen, Section: BL

//  Manages and executes the game of 20 Questions; saves games for future playing.
import java.util.*;
import java.io.*;

public class QuestionTree {
   private int gamesPlayed;
   private int gamesWon;
   private QuestionNode	overallRoot;
   private UserInterface uiPlayer;
   
   //pre: ui != null, throw IllegalArgumentException otherwise
   // post: creates QuestionTree that has exactly one answer called 'computer'.
   public QuestionTree(UserInterface ui){
      if	(ui == null) {
			throw	new IllegalArgumentException();
		}
		overallRoot	= new	QuestionNode("computer");
		uiPlayer = ui;
      gamesPlayed = 0;
      gamesWon = 0;
   }
   
   //Plays a complete guessing game with the user by asking yes or 
   //no questions until it reaches an object to guess.
   public void play(){
      overallRoot = play(overallRoot);
      gamesPlayed++;
   }
   
   //Plays a complete guessing game with the user by asking yes or 
   //no questions until it reaches an object to guess.
   private QuestionNode play(QuestionNode currentRoot){
      if(currentRoot == null){
         throw new IllegalArgumentException();
      }
      if (currentRoot.left == null && currentRoot.right ==	null)	{
		   uiPlayer.print("Would your object happen to be " +	currentRoot.data + "?");
		   if (uiPlayer.nextBoolean()) {
			   uiPlayer.println("I win!");
            gamesWon++;
		   } else	{
            uiPlayer.print("I lose. What is your object?");
			   String object = uiPlayer.nextLine();
			   uiPlayer.print("Type a yes/no question to distinguish your item from " 
                           + currentRoot.data + ":");
			   String question =	uiPlayer.nextLine();
			   QuestionNode temp	= new	QuestionNode(question);
            // add a new question and an answer to the tree
			   uiPlayer.print("And what is the answer for your object?");
            if	(uiPlayer.nextBoolean())	{
				   temp.left =	new QuestionNode(object);
				   temp.right = currentRoot;
			   } else {
				   temp.left =	currentRoot;
				   temp.right = new QuestionNode(object);
			   }
            return temp;
         }
      }else {    
         uiPlayer.print(currentRoot.data);
         if (uiPlayer.nextBoolean()) {
            currentRoot.left = play(currentRoot.left);
         } else {
            currentRoot.right = play(currentRoot.right);
         }
      }
      return currentRoot;  
   }
   
   //writes and saves the current QuestionTree onto a given output file 
   public void save(PrintStream output){
      save(output, overallRoot);
   }
   
   /*pre: output != null && root != null, 
   throw IllegalArgumentException otherwise
   Store the current QuestionTree starting with specified root 
   to a given output file*/
   private void save(PrintStream output, QuestionNode root){
      if (output == null || root == null) {
         throw new IllegalArgumentException();
      }
      if (root.left == null && root.right == null) {
         output.println("A:" + root.data); // saves answers
      } else {
         output.println("Q:" + root.data); // saves questions
         save(output, root.left);
         save(output, root.right);
      } 
   }
   
   //replace the current tree with another tree from 'input'
   // pre: 'input' is not null and in proper standard format; 
	// otherwise, throws IllegalArgumentException.
   // post: load questions and answers into a new tree
   public void load(Scanner input){
      if (input == null) {
         throw new IllegalArgumentException("input is null");
      }
      overallRoot = newTreeHelper(input);
   }
   
   // Pre: Takes in a scanner and a question node. 
   // Post: Loads questions and answers into a tree.
   // returns the state of current question node.
   private QuestionNode newTreeHelper(Scanner input) {
      String line = input.nextLine();
		String[] contents = line.split(":");
		QuestionNode root = new QuestionNode(contents[1]);
		if (contents[0].equals("Q")) {
			root.left = newTreeHelper(input);
			root.right = newTreeHelper(input);
		}
		return root;
   }
   
   //return the number of total games played
   public int totalGames(){
      return gamesPlayed;
   }
   
   //return the number of total games won
   public int gamesWon(){
      return gamesWon;
   }


}