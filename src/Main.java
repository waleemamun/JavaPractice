import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import sun.awt.image.ImageWatched;

import java.util.*;


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
//        Solutions s1 = new Solutions();
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
//        int [] arr = {0,1,2};
//        int [] arr1 = {-1,0,1,0};
//        // -4 -1 -1 0 1 2
//        //List<List<Integer>> result = s1.threeSum(arr);
//        //System.out.println(result);

//        //System.out.println("Closest Sum " + s1.threeSumClosest(arr,3));
//        List <String> ls = s1.letterCombinations("23");
//        System.out.println("LetterCombo ("+ls.size()+") :" + ls);
//        int [] arr  = {10,2,5,6,8,1,15};
//        LinkList ls = new LinkList();
//        ls = ls.createList(arr);
//        ls.printList();
//        LinkList tempList = ls.removeNthFromEnd(ls,2);
//        System.out.println("After");
//        tempList.printList();

        //System.out.println(" res" + s1.fourSum(arr,0));
//        System.out.println("Parenthesis " + s1.isValid("([)]"));
//        int [] arr1 = {-40, -20, -1, 1, 2, 3, 5, 7, 9, 12, 13};
//        int [] arr2 = {-10, -5, 2, 2, 2, 3, 4, 8, 9, 12, 13};
//        LinkList l1 = new LinkList();
//        LinkList l2 = new LinkList();
//        l1 = l1.createList(arr1);
//        l2 = l2.createList(arr2);
//        //l1.printList();
//        //l2.printList();
//        LinkList res = new LinkList();
//        LinkList resx = res.mergeTwoLists(l1,l2);
//        System.out.println("My list");
//        resx.printList();

//    System.out.println("Subsets " + r1.getSubset(set,0));
//    System.out.println("Magic pos " + r1.magicShowV2(arr2));
//    System.out.println("ways to climb: " + r1.countWaysDP(5));
//    Recursions r1 = new Recursions();
//    r1.printAllpermutation("abcd");
//    System.out.println(r1.generateParenthesis(3));

//    int [] arr3 = {2, 6};
//    LinkList l1 = new LinkList();
//    LinkList l2 = new LinkList();
//    LinkList l3 = new LinkList();
//    l1 = l1.createList(arr1);
//    System.out.println("List Size " + l1.getSize());
//
//    l3 = l3.createList(arr3);
//    LinkList [] arrList = {l1,l2,l3};
//    LinkList l4 = new LinkList();
//    LinkList [] empltyList = {};
//    l4 = l4.mergeKLists(arrList);
//    l4.printList();
//    LinkList l2 = l1.reverseKGroup(l1, 3);
//    LinkList l2 = l1.swapPairs(l1);
//
//    l2.printList();

//        System.out.println("Git WOW!");
//        int [] arr1 = {0, 0, 0, 1, 1, 2 ,2, 2, 2, 3,3, 3, 3, 4, 4, 5};
//        int [] arr2 = {0,1,2,2,3,0,4,2};
//        Solutions s1 = new Solutions();
//        int size = s1.removeElement(arr2,2);
//        System.out.println("Arr Len = " + size);
//        System.out.println("Index " + s1.strStr("waleeMamun", "n"));
//        SubStringSearch sb1 = new SubStringSearch();
//        System.out.println("Index Sub " + sb1.strStr("waleeMamun", "ema"));
//        System.out.println(" Res " + Bits.multiply(7,9));
//        System.out.println(" Res " + Bits.add(7,9));
//        System.out.println("Res " + Bits.divide(-2147483648,-1));

//        Solutions s1 = new Solutions();
//        String mystr = "abcwordgoodgoodgoodbestwordwordolaf";
//        String mystr1 = "foobarthefoobarfoofoo" ;
//        String []words1 = {"foo", "bar", "foo"};
//        String [] words = {"word","good","best","word", "word"};
//        List <Integer> ls = s1.findSubstring(mystr1,words1);
//        System.out.println("Indices =  " + ls);

//        int []arr = {1 ,2 ,3, 7, 6, 5, 4, 4, 4, 4};
//        int [] arr1 = {2,3,1};
//        Recursions r1 = new Recursions();
//        r1.nextPermutation(arr);
//        for (int i =0 ; i<arr.length; i++){
//            System.out.print(" " + arr[i]);
//        }
//        System.out.println();

//        Recursions s1 = new Recursions();
//        String parenthesisStr = "()((())";
//        String parenthesisStr1 = "()(())";
//        System.out.println(s1.longestValidParenthesesV3(parenthesisStr));
//        int [] arr = {1,3,5,7};
//        int  rng = s1.searchInsert(arr,6) ;

//        char [][]brd = {{'5','3','.','.','7','.','.','.','.'}, // 0
//                        {'6','.','.','1','9','5','.','.','.'}, // 1
//                        {'.','9','8','.','.','.','.','6','.'}, // 2
//                        {'8','.','.','.','6','.','.','.','3'}, // 3
//                        {'4','.','.','8','.','3','.','.','1'}, // 4
//                        {'7','.','.','.','2','.','.','.','6'}, // 5
//                        {'.','6','.','.','.','.','2','8','.'}, // 6
//                        {'.','.','.','4','1','9','.','.','5'}, // 7
//                        {'.','.','.','.','8','.','.','7','9'}};// 8
//        Recursions r1 = new Recursions();
//        r1.solveSudokuV2(brd);

//        Solutions s1 = new Solutions();
//        System.out.println(s1.findDuplicate(arr));
//        Recursions r1 = new Recursions();
//        System.out.println("res = " + r1.combinationSum2V2(arr,8));
//        System.out.println("Missing Positive = " + s1.firstMissingPositive(arr1));
//        Solutions s1 = new Solutions();
//        System.out.println("Trap Water = " + s1.trapV2(arr));
//        DPs dp1 = new DPs();
//        System.out.println(dp1.isWildCardMatchV2("aaaa","****a"));
//        System.out.println("Jump count = " + s1.jumpV2(arr1));
//        Recursions r1 = new Recursions();
//        int [] nums = {1,3,5,7,9,11,13,15};
//        System.out.println(r1.combineThree(nums));
        int [] arr =  {1,2,3,4,5};
        int [] arr1 = {30,10,50,70,20};

        int [][] arr2d = {
                {1, 0, 1, 0, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0}



        };
        char [][] board = {
                {'1','1','1','1'},
                {'1','1','1','1'},
                {'1','1','1','1'},
                {'0','0','0','1'}
        };

        String [] words = {"my","momma","always","said,","life","was","like","a","box","of","chocolates.",
                "you","never","know","what","you're","gonna","get."};
        String tokens[] = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        String word1 = "    amar   sonar    bangla ami    tomay valobashi    ";
        String word2 = "abcdkmL";
        String str1 = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        String str2 = "1.0.0.0.0.1";
        SolutionsV1 sv1 = new SolutionsV1();
        SolutionsV2 sv2 = new SolutionsV2();
        Recursions r1 = new Recursions();
        LinkList ls1 = new LinkList();
        LinkList ls2 = new LinkList();
        DPs dp1 = new DPs();
        Bits bt1 = new Bits();
        Tree tr1 = new Tree();
        TreeNode treeNode = tr1.createBSTree(0,arr.length-1,arr);
        PhoneIQ p1 = new PhoneIQ();
        //System.out.println(" Result " + p1.numberOfWays(arr,6));
        CodeArray c1  =new CodeArray();

        //System.out.println(sv1.findRepeatedDnaSequencesV2(str1));
        System.out.println(Arrays.toString(sv2.productExceptSelf(arr)));



























    }

}
