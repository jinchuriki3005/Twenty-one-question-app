//Max Tran, CSE 143, May 26, 2016
//TA: Rebecca Yuen, Section: BL

//this class create a QuestionNode that stores a single node of string
import java.util.*;

public class QuestionNode {
   public String data;		// data stored at this node
	public QuestionNode left; // reference to the left subtree
	public QuestionNode right; // reference to the right sub
   
   //construct a node with the data passed in
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   //construct a node with given data and the link of "left" and "right"
   public QuestionNode(String data, QuestionNode left, QuestionNode right){
      this.data = data;
      this.left = left;
      this.right = right;
   }
}