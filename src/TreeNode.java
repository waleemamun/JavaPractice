import java.util.ArrayList;

public class TreeNode {
    int data;
    TreeNode leftChild;
    TreeNode rightChild;
    TreeNode parent;
    ArrayList<TreeNode> listOfChild; //non binary tree case
    public TreeNode( int val) {
        data = val;
        leftChild = null;
        rightChild = null;
        parent = null;
    }
    public void print() {
        System.out.print("node "+data);
        System.out.print(" (" + (leftChild !=null ? leftChild.data : " null") );
        System.out.print(" , " + (rightChild !=null ? rightChild.data : " null") );
        System.out.println(")");
    }
}
