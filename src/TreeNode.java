import java.util.ArrayList;

public class TreeNode {
    int data;
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode parent;
    ArrayList<TreeNode> listOfChild; //non binary tree case
    public TreeNode( int val) {
        data = val;
        left = null;
        right = null;
        parent = null;
        this.val  = data;
    }
    public void print() {
        System.out.print("node "+data);
        System.out.print(" (" + (left !=null ? left.data : " null") );
        System.out.print(" , " + (right !=null ? right.data : " null") );
        System.out.println(")");
    }

}
