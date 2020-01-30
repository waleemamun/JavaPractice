import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        /*int [] arr  = {10,2,5,6,8,1,15};
        LinkList ls = new LinkList();
        ls = ls.createList(arr);
        ls.printList();
        ls.removeDuplicate();
        ls.printList();

        StackNode sk = new StackNode(arr.length);
        for (int i = 0; i < arr.length;i++) {
            sk.push(arr[i]);
        }
        sk.printStack();
        System.out.println("Now min " + sk.minimumVal());
        Solutions sl  = new Solutions();
        sl.towerOfHanoi(3,1,3,2);
        System.out.println("10 fibonacci "+ sl.fibonacci(10));
        ArrayList <Solutions.Path> pathArrayList = new ArrayList<>();
        sl.robotMove(pathArrayList,0,0);

        int [] dataArr = {1,2,3,4,5,6,7,8,9};
        Tree tree = new Tree();
        tree.createBinaryTree(dataArr);
        tree.preOrderTraversal(tree.root);
        System.out.println();

        TreeNode nodeA = tree.search(4, tree.root);
        TreeNode nodeB = tree.search(1, tree.root);

        TreeNode ancestor = tree.commonAncestor(nodeA, nodeB);
        if (ancestor != null)
            ancestor.print();
        TreeNode anc = tree.getCommonAncestor(nodeA,nodeB);
        if (anc != null)
            anc.print();*/


//        if (node != null)
//            System.out.println(" Searching  node  : " + node.data );
//        TreeNode node1 = tree.inOrderPredecessorWithoutParent(node);
//        if (node1 != null) {
//            System.out.println("Pred node " + node1.data);
//        } else {
//            System.out.println("No Pred!");
//        }
        Solutions s1 = new Solutions();
//        String instr = "PAYPALISHIRING";
//        String outstr = s1.convert(instr,4);
//        System.out.println(instr+ "  " + outstr);
//        int num = s1.myAtoi("   2997483647");
//        System.out.println("num = " +num);
//        System.out.println(" Palindrome = " + s1.isPalindrome(125000521));
//        int [] arr  = {10,2,5,6,8,9,15};
//        int maxDiff = s1.maxDiffInArrayLeft2Right(arr);
//        System.out.println("max diff = " + maxDiff);
//        System.out.println("Number of set bits " + s1.numberOfSetBits(159) );
//
//        System.out.println( "Next Bits " +  Bits.getNextSetBits(30));
//        System.out.println( "prev Bits " +  Bits.getPrevSetBits(30));
//        System.out.println("Ugly Number " + s1.getNthUglyNumber(150));

//        int[] arr = {1,8,6,2,5,4,8,3,7};
//        System.out.println("Max1 Container Area = " + s1.maxArea(arr));
        int [] arr = {-1, 0, 1, 2, -1, -4};
        int [] arr1 = {-1,0,1,0};
        // -4 -1 -1 0 1 2
        System.out.println("Git WOW!!");
        List<List<Integer>> result = s1.threeSum(arr);
        System.out.println(result);

    }

}
