import javafx.geometry.Pos;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.omg.PortableInterceptor.INACTIVE;
import sun.jvm.hotspot.utilities.Interval;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class Recursions {
// This set of problems are from CTCI
    public int countWays(int n) {
        if (n < 0)
            return 0;
        if (n == 0)
            return 1;
        else
            return countWays(n-1) + countWays(n-2) + countWays(n-3);

    }

    /*
     *  Count for 1 steps 2 steps and 3 steps - the stairs
     *  as the previous position already accounted for n-1, n-2 n-3 steps
     *  at each position x we store the ways to climb ways[x] using the last 3 steps
     * */
    public int countWaysDP(int n) {
        int [] ways = new int[n+1];
        ways[0] = 0;
        ways[1] = 1;
        ways[2] = 2;
        ways[3] = 4;
        for (int i = 4; i <= n; i++){
            ways[i] = ways[i-1] + ways[i-2] + ways[i-3];
        }
        return ways[n];
    }

    public int getMagic(int arr[], int low, int high) {
        int mid = (low + high) / 2;
        if (low > high)
            return -1;
        if (arr[mid] == mid)
            return mid;
        else if (arr[mid] > mid)
            return getMagic(arr, low, mid-1);
        else
            return getMagic(arr, mid+1, high);

    }

    //This version works in case of non distinct case
    public int getMagicV2(int arr[], int low, int high) {
        if (low > high)
            return -1;
        int mid = (low+high) / 2;
        if (arr[mid] == mid)
            return mid;
        /*search left */
        int leftIndex = Math.min(mid -1, arr[mid]);
        // if already found in left no need to go to right
        int left = getMagicV2(arr,low, leftIndex);
        // this actually skipping all the calls for which left -1
        // This is interesting way of storing the recursive result
        // and only return if a certain condition is met
        if (left >= 0)
            return left;
        // did not find in left go search in right
        int rightIndex = Math.max(arr[mid],mid + 1);
        int right = getMagicV2(arr,rightIndex,high);

        return right;

    }

    public int magicShow(int arr[]) {
        int mid = getMagic(arr,0,arr.length-1);
        return mid;
    }

    public int magicShowV2(int arr[]) {
        int mid = getMagicV2(arr,0,arr.length-1);
        return mid;
    }
    // The idea is to start with a empty set, then add the last item of the set to the resultSet
    // For example for [1,2,3] we first have [[]], then add [[] , 3] next we move to the previous item (2)
    // and get [(), 2, 3 (2,3)] and so on.
    public ArrayList<ArrayList<Integer>> getSubset (ArrayList<Integer> set, int index) {
        ArrayList<ArrayList<Integer>> allSubset;
        if (index == set.size()) {
            // base case, iff the set is empty just add empty set,
            // we can use this empty to add more entries up the recursion chain
            allSubset = new ArrayList<ArrayList<Integer>>();
            allSubset.add(new ArrayList<>());
            System.out.println(allSubset.size());

        } else {
            // get the subset for index + 1
            allSubset = getSubset(set,index + 1);
            System.out.println(allSubset.size());
            int item = set.get(index);
            ArrayList<ArrayList<Integer>> moreSet = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> subset : allSubset) {
                ArrayList<Integer> newSet = new ArrayList<>();
                // we add all the element of the current subset.
                newSet.addAll(subset);
                newSet.add(item);
                moreSet.add(newSet);
            }
            allSubset.addAll(moreSet);

        }
        return allSubset;
    }

    public void printAllpermutation(String str) {

        StringBuilder strB = new StringBuilder(str);
        System.out.println("---Start---");
        printPermutation(strB,0);
        System.out.println("---END----");
    }

    private void printPermutation (StringBuilder str,  int index) {
        if (index == str.length() -1) {
            System.out.println(str);
            return;
        }
        for (int i = index; i < str.length(); i++) {
            if (i != index) {
                // if its not the first call for this str we swap with next char
                char tempC = str.charAt(i);
                str.setCharAt(i,str.charAt(index));
                str.setCharAt(index,tempC);

            }
            // increase index by 1, after a swap we need to increase the index not i,
            // because index + 1 points to the position

            printPermutation(str,index +1);
            // revert back the changes made earlier after the recursive call
            // this will ensure that when we start the next recursion we start
            // from the initial position. For example abc wil produce {abc,acb}
            // so current string will acb but before going to the next step we need to make it abc
            // This will make sure when we swap next time we perform
            // the swap on abc (which generate bac) instead of acb (which generates cab)
            if (i != index) {
                char tempC = str.charAt(i);
                str.setCharAt(i,str.charAt(index));
                str.setCharAt(index,tempC);

            }

        }
    }

    public int minProduct (int a, int b){
        int smaller = (a <= b) ? a : b;
        int bigger = (a > b) ? a : b;
        return minProductHelper(smaller,bigger);
    }
    // to calc mupliply we can add the (bigger + bigger) number recursively
    // with each recursion the number will increase by double compared to the last one
    // we need smaller/2 steps.
    private int minProductHelper (int smaller, int bigger) {
        // base case if one of the number is zero product is zero
        if (smaller == 0)
            return 0;
        else if(smaller == 1)
            return bigger;
        // devide by 2 , here we pass the recursive variable s but keep actual var to use for later calc
        // we save the recursive result in a temp var and later work on it and return it.
        int s = smaller >> 1;
        int halfprod = minProductHelper(s,bigger);

        if (smaller % 2 == 0)
            return halfprod + halfprod;
        else // for even we need to add bigger for example 21*25 = 2 * 10 * 25 + 25
            return halfprod + halfprod +bigger;
    }

    public void towerOfHanoi(int nDisk, int srcPeg, int destPeg, int midPeg) {

        if (nDisk == 1 ) {
            System.out.println("Move disk " +nDisk + " from "+ srcPeg +" to "+ destPeg);
            return;
        }
        towerOfHanoi(nDisk-1,srcPeg,midPeg,destPeg);
        System.out.println("Move disk " +nDisk + " from "+ srcPeg +" to "+ destPeg);
        towerOfHanoi(nDisk-1,midPeg,destPeg,srcPeg);
    }

    // Leetcode 22 Generate Parentheses
    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> list = new ArrayList<>();
        char [] str = new char[2*n];
        addParenthesis(list, n, n, str,0);
        return  list;
    }

    private void addParenthesis(ArrayList<String> list, int leftRem,
                                        int rightRem, char [] str, int index) {
        // invalid case we need to stop nothing needs to be added to list
        if (leftRem < 0 || rightRem < leftRem)
            return;
        // we exhausted all left & right parenthesis,
        // str now has a valid combination lets add it to the list
        if (leftRem == 0 && rightRem == 0) {
            list.add(String.copyValueOf(str));
        } else {
            // add the left parenthesis & go in the deep of recursion to add
            // the next parenthesis (which could be a left ot right parenthesis)
            // str will store all the parenthesis possible in this recursion depth,
            // when the first call reaches the depth of recursion
            // we move to the 2nd call with by reducing the right parenthesis,
            // this allows us to have different combination of left & right; ie for left = 3 & right = 3,
            // The possible values are at every recursive  (3,3) (2,3) (1,3) (0,3), (2,2) (2,1) ... (1,1)

            str[index] = '(';
            addParenthesis(list, leftRem-1, rightRem,str,index +1);
            str[index] = ')';
            addParenthesis(list, leftRem, rightRem-1, str, index+1);
        }
    }

    public void reverserArray (int arr[], int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    // LeetCode 31::  Next Permutation
    // The basic idea is to use two pointers low & high to scan from the right and found a place and stop
    // we found the value pointed by high is less than low. Nest we need to find a value (on the right of high pointer)
    // which is greater than the value pointed by high. If we find such entry we swap value of high with that entry
    // and reverse rest of the array  from right of high pointer. The reverse  part is the most interesting it
    // makes the algo very simple.
    public void nextPermutation(int[] nums) {
        int [] buffer = new int[nums.length];

        // array size 0 or 1 dont need to process
        if (nums.length <= 1 )
            return;

        int low = nums.length-1;
        int high = nums.length -2;

        // one optimization if the  last two entries can be swapped
        if (nums[low] > nums[high]) {
            int temp = nums[low];
            nums[low] = nums[high];
            nums[high] = temp;
            return;
        }
        int lowStart = low;
        while (low >= 0 && high >= 0 &&
                nums[low] <= nums[high]){
            low--;
            high--;
        }

        // if the whole array is of the form 4 3 2 1 , we can just reverse it.
        if (high < 0 && low == 0) {
            reverserArray(nums,0,nums.length-1);
            return;
        }

        // found the breakpoint in ascending order from right to left
        // low is pointing to the last entry in ascending order (highest entry) and
        // high points to our entry of interest

        int end = lowStart;

        if (nums[high] >= nums[lowStart]) {


            // if array is of type 1 3 7 6 5 4 4 4 4 4 1
            // lowStart < high we need to find end > high
            // if array is of type 1 4 7 6 5 4 4 4 4 4
            // lowStart == high we need to find end > high
            while (nums[end] <= nums[high])
                end--;
            // non special case like 1 2 3 7 6 5 4  we already found end > high
        }

        int temp = nums[high];
        nums[high] = nums[end];
        nums[end] = temp;
        //Arrays.sort(nums,low,lowStart+1);
        // Just simply reverse the remaining array no need to sort
        reverserArray(nums, low, lowStart);
    }

    // concise version same algo as avobe this is may be easy to read
    public void nextPermutationV2(int[] nums) {
        int k = nums.length - 2;
        while(k>=0 && nums[k] >= nums[k+1]) {
            k--;
        }
        if (k < 0){
            reverserArray(nums,0, nums.length -1);
            return;
        }
        int l = nums.length -1;
        while (l>= 0 && nums[l] <= nums[k]) {
            l--;
        }
        int tmp = nums[k];
        nums[k] = nums[l];
        nums[l] = tmp;
        reverserArray(nums,k+1, nums.length -1);
    }

    // LeetCode :: 33. Search in Rotated Sorted Array
    public int search(int[] nums, int target) {
        return rotatedBinSearch(nums,0, nums.length -1, target);
    }

    private int rotatedBinSearch (int []nums, int low, int high, int target){
        if (low > high) {
            return -1;
        }
        //System.out.println("low " + low +", " + nums[low] +" ::high " + high +"," + nums[high] +" target " + target);
        int mid = (low + high)/2;
        if (nums[mid] == target)
            return mid;
        // if the portion between low to high is sorted in ascending order we can use the original bin search
        if (nums[low] <= nums[high]) {
            if (target < nums[mid])
                return rotatedBinSearch(nums, low, mid - 1, target);
            else
                return rotatedBinSearch(nums, mid + 1, high, target);
        } else { // there exist a rotation between low to high
            // One side of mid either left or right will  have ascending order.
            if (nums[low] < nums[mid]
                    && target < nums[mid] && target >= nums[low])  // left side of mid is ascending ; target in left
                return rotatedBinSearch(nums, low, mid - 1, target);
            else if (nums[mid] < nums[high]
                    && target > nums[mid] && target <= nums[high]) // right side of mid is ascending; target in right
                return rotatedBinSearch(nums, mid + 1, high, target);
            else if (nums[low] > nums[mid]
                    && (target < nums[mid] || target >= nums[low])) // left side not ascending; target in left
                return rotatedBinSearch(nums, low, mid - 1, target);
            else if (nums[mid] > nums[high]
                    && (target > nums[mid] || target <= nums[high])) // right side not ascending; target in right
                return rotatedBinSearch(nums, mid + 1, high, target);
            else
                return rotatedBinSearch(nums, mid + 1, high, target); // check if target in right; most likely dead code
        }
    }
    // The version 2 easier & neat
    // The idea is to do a binary search to find the split/rotation point in the array
    // we use another binary search to find the target but this time while calculating mid
    // we use the splitIndex to get the actual mid
    // we can then use the split index to find the actual mid
    public int rotatedBinSearchV2 (int []nums, int target){
        int mid = 0;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            mid = (low + high)/2;
            if (nums[mid] > nums[high])
                low = mid + 1;
            else
                high = mid;
        }
        int splitIdx = high; // we could have assigned low to splitIdx too, as the loop breaks at (low == high)
        System.out.println("SplitIdx = " + splitIdx);
        low = 0;
        high = nums.length - 1;
        int realMid = 0;
        while (low <= high){
            mid = (low + high)/2;
            // calculate the actual mid using the spiltIdx
            // we dont need to translate the low or high as
            // our goal is ot get mid so we translate mid
            // also in the calculation of mid  =(low+high)/2 both side of the equation
            // requires translation in other word it cancels out from both side of the equation
            realMid = (mid + splitIdx) % nums.length;
            if (nums[realMid] == target)
                return realMid;
            else if (target < nums[realMid])
                high = mid-1;
            else
                low = mid + 1;
        }

        return -1;
    }


    // LeetCode 34:: Find First and Last Position of Element in Sorted Array
    // Check the V3 its easy to read
    public int[] searchRange(int[] nums, int target) {
        int [] range = {-1,-1};
        binarySearchRng(nums, 0, nums.length-1, target, range);
        return range;
    }

    // Search the range in the ascending sorted array. We do a regular binary search to
    // reach the target we are looking for. After finding the target == nums[mid] we check the
    // left side of the array and the right side of the for the same target
    private void binarySearchRng (int [] nums, int low , int high, int target, int [] range) {

        if(low > high){
            range [0] = range[1] = -1;
            return;
        }
        int mid = (low + high)/2;
        // found the mid now check for the range on both on the left & the right side
        if (nums[mid] == target) {
            // found target at the leftmost corner, update the left range
            if (mid == 0 && range[0] == -1)
                range[0] = mid;
            // found target at the right most corner, update the right range
            if (mid == nums.length -1 && range[1] == -1)
                range[1] = mid;
            // target is not the same as the prev entry, so this is the left range
            if (mid - 1 >= 0 && nums[mid] != nums[mid-1] && range[0] == -1)
                range[0] = mid;
            // target is not the same as the next entry, so this is the right range
            if (mid + 1 <= nums.length -1 && nums[mid] != nums[mid +1] && range[1] == -1)
                range[1] = mid;
            // found both left & right range, we are done
            if (range[0] != -1 && range[1]!= -1)
                return;
            // search for the left range on the left side of mid,
            // we need to consider the whole left side (0 to mid-1)
            if (range[0] == -1)
                binarySearchRng(nums, 0, mid-1, target, range);
            // search for the right range on the right side of mid,
            // we need to consider the whole right side (mid+1 to length of array)
            if (range[1] == -1)
                binarySearchRng(nums, mid+1, nums.length-1, target, range);

        } else if (target < nums[mid]) { // regular bin search
            binarySearchRng(nums, low, mid -1, target, range);
        } else {                         // regular bin search
            binarySearchRng(nums, mid+1, high, target, range);
        }

    }
    // LeetCode 34 :: Search Range (This is easy to read)
    // returns leftmost (or rightmost) index at which `target` should be
    // inserted in sorted array `nums` via binary search.
    // check the next one
    private int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            }
            else {
                lo = mid+1;
            }
        }

        return lo;
    }

    public int[] searchRangeV2(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false)-1;

        return targetRange;
    }
    // LeetCode 34 :: Search Range (This is very easy to read :))
    // search for the first index of an element in a sorted array
    public  int searchFirsIndexOf(int arr[], int target) {
        int low = 0;
        int high = arr.length -1;

        int result = -1;
        while (low <= high) {
            int mid = low + (high-low)/2;
            if (target == arr[mid]) {
                result = mid;
                high = mid -1;
            } else if (target < arr[mid]) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        return result;
    }
    // search for the last index of an element in a sorted array
    public  int searchForLastIdx(int arr[], int target) {
        int low = 0;
        int high = arr.length -1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high -low)/2;
            if (arr[mid] == target) {
                result = mid;
                low = mid+1;
            } else if ( target < arr[mid]) {
                high = mid -1;
            } else {
                low = mid +1;
            }
        }
        return result;
    }
    public int[] searchRange3(int[] nums, int target) {
        int leftIdx = searchFirsIndexOf(nums,target);
        int rightIdx = searchForLastIdx(nums,target);
        int []range = {leftIdx,rightIdx};
        return range;

    }

    // LeetCode :: 35. Search Insert Position
    // The idea is to do binary search to find the entry, if found return index, if not found (low > high)
    // low will the index where this entry could be inserted
    public int searchInsert(int[] nums, int target) {

        return binarySearchInsert(nums, 0, nums.length-1, target);
    }

    private int binarySearchInsert(int[] nums, int low, int high, int target) {

        // item not found, lets find the position it can be inserted.
        // as low > high in this case that means the item is bigger than the item high-1,
        // it does not matter if we search the left side or right side,
        // low will point to the index where we want to insert the 'not found' the entry.
        // Even in corner cases where low == 0 high = -1 we are trying to found
        // the entry which ise smaller then the lowest item in array. Same goes for low == nums.length.
        // Why return low ?
        // Cause think about the basic idea of insertion, we insert at a point & right shift everything by one
        // so the value that becomes bigger between low & high is our answer and at the following recursion break
        // low is always greater than high. Hence low is the right choice
        if (low > high) {
            return low;
        }

        int mid = (low + high)/2;
        // just do binary search for the entry and if found return the position
        if (nums[mid] == target)
            return mid;
        else if (target < nums[mid])
            return binarySearchInsert(nums, low, mid -1, target);
        else
            return binarySearchInsert(nums, mid+1, high, target);


    }

    // Eight Queen Problem
    public void placeNPrintQueens() {
        int queen = 1;
        int [] columns = new int[8];
        char [][]board = new char[8][8];
        Arrays.fill(columns, -1);
        for(int i=0; i<8;i++)
            for(int j=0; j< 8;j++)
                board[i][j]= '.';
        placeQueenPrint(0, columns, board);

    }

    public void placeAllQueens() {

        int [] columns = new int[8];
        char [][]board = new char[8][8];
        Arrays.fill(columns, -1);
        placeQueen(0, columns);

    }

    // check if the row, col is available for an queen placement, We main a column array which is indexed by the row
    // and stores the column used by each row. if the current columns we are considering the.
    // While placing a queen need to consider in each iteration only about columns as we place 8 queens on 8 rows
    // so no need to worry about rows.
    boolean checkAvailable(int [] queenColumns, int rowUpTo, int col){
        for (int row1 = 0; row1 < rowUpTo; row1++) {
            int colCurr = queenColumns[row1];
            // if the current columns we are considering the is already
            // used by other queen in a previous row we cannot use it
            if (colCurr == col)
                return false;
            // Check if the column is not diagonally covered by queens in the previous row.
            // To be diagonally covered by a queen in previous row. The difference of columns and row has to ve equal
            int colDiff = Math.abs(col - colCurr);
            int rowDiff = rowUpTo - row1;
            // difference of row & columns are equal, its diagonally covered. Hence cant use this column
            if (rowDiff == colDiff)
                return false;

        }
        // This columns can be used to place a queen
        return true;
    }

    // While placing 8 queen we need to put one queen per row,
    // now lets figure out which column each queen will go into
    private void placeQueen (int row, int [] columns) {
        if (row == 8) {
            // We placed 8 queens successfully lets print the columns
            for (int i = 0; i<8; i++){
                System.out.print(" " + columns[i]);
            }
            System.out.println();
            return;
        }
        // We check all possible scenario to place 8 queens.
        for (int col = 0; col < 8; col++) {
            if (checkAvailable(columns,row,col)){
                columns[row] = col;
                placeQueen(row + 1, columns);
            }
        }

    }

    private void placeQueenPrint (int row, int [] columns, char [][] board) {
        if (row == 8) {
            // We placed 8 queens successfully lets print the columns
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++){
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            return;
        }
        // We check all possible scenario to place 8 queens.
        for (int col = 0; col < 8; col++) {
            if (checkAvailable(columns,row,col)){
                columns[row] = col;
                board[row][col] = 'Q';
                placeQueenPrint(row + 1, columns, board);
                board[row][col] = '.';
            }
        }

    }

    // 51. N-Queens solve this similar to 8 Queen problem above using backtracking
    // Use the columns array to build  2d solution
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> resList = new ArrayList<>();
        int [] columns = new int[n];
        Arrays.fill(columns, -1);
        placeNQueen(0, columns, n, resList);
        return resList;
    }

    private void placeNQueen (int row, int [] columns, int n, List<List<String>> resList) {
        if (row == n) {
            // We placed n queens successfully lets create the board from the column array
            ArrayList<String> tmpList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int queen = columns[i];
                String s = "";
                for (int j = 0; j < n; j++){
                    if(j == queen)
                        s+= "Q";
                    else
                        s+= ".";
                }
                tmpList.add(s);
            }
            resList.add(new ArrayList<>(tmpList));

            return;
        }
        // We check all possible scenario to place 8 queens.
        for (int col = 0; col < n; col++) {
            if (checkAvailable(columns,row,col)){
                columns[row] = col;
                placeNQueen(row + 1, columns, n, resList);

            }
        }

    }

    // 52. N-Queens II, solve the same we would solve 8 Queen using backtracking
    // if are able place n queen we increase the global count variable by 1
    int queenCount;

    public int totalNQueens(int n) {
        int [] columns = new int[n];
        Arrays.fill(columns, -1);
        queenCount = 0;
        counNQueenSolution(0,columns,n);
        return queenCount;
    }

    private void counNQueenSolution(int row, int [] columns, int n) {
        // reached a solution, increase the solution count by one 
        if(row == n){
            queenCount++;
            return;
        }
        // We check all possible scenario to place 8 queens.
        for (int col = 0; col < n; col++) {
            if (checkAvailable(columns,row,col)){
                columns[row] = col;
                counNQueenSolution(row + 1, columns, n);

            }
        }

    }


    // 37. Sudoku Solver (Hard) Solve the sudoku given the board
    // We are using a backtraciing approach similar to 8 queen problem.
    // We solve each position at a time, by moving left to right in the board.
    public void solveSudoku(char[][] board) {
        // Solve the sudoku board
        solveSudokuBoard(board,0,0);
        //System.out.println(isSolved);
        /*for (int i =0; i<9;i++){
            for(int j=0;j<9;j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }*/
    }
    private boolean isValidSuBoard (char [][] board , int row, int col, int num) {
        // Get the sub block row & col start
        int blkrow = (row / 3) * 3, blkcol = (col / 3) * 3;
        for (int i = 0; i < 9; i++) {
            // check if the row or  column or block has the number
            if(board[row][i] == ('0' + num) || board[i][col] == ('0' + num) ||
               board[blkrow + i/3][blkcol + i%3] == ('0' + num)) { // i/3 gives rows in each block & i%3 gives column
                return false;
            }
        }
        return true;

    }
    private boolean solveSudokuBoard(char [][] board, int row, int col) {

        for (int i = row; i < 9; i++) {
            for (int j = col; j < 9; j++) {

                // found an empty postion to fill
                if(board[i][j] == '.') {
                    for (int k = 1; k <= 9; k++){
                        if(isValidSuBoard(board, i, j, k)) {
                            board[i][j] =  (char)('0' + k);
                            if(solveSudokuBoard(board, i , j + 1))
                                return true;
                            // so the current k value is not going to give us the solution,
                            // revert it back and try k +1
                            board[i][j] = '.';
                        }
                    }
                    // we checked for 1-9 and could not find a solution lets we have to backtrack
                    // and go back prev call to solve the problem. Note board[i][j] is reverted back to '.'
                    // before exiting the above for loop
                    return false;
                }
            }
            col = 0;
        }

        return true;
    }

    public void solveSudokuV2(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] box = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    row[i][num] = true;
                    col[j][num] = true;
                    box[i / 3 * 3 + j / 3][num] = true;
                }
            }
        }

        boolean isSolved = solveSudokuBetter(board, 0, 0, row, col, box);
        System.out.println(isSolved);
        for (int i =0; i<9;i++){
            for(int j=0;j<9;j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
    }

    private boolean solveSudokuBetter(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] box) {
        // reached the end, solved all the rows. we solved the board return
        if (i == board.length) {
            return true;
        }
        // Reached the end of this row , goto next row to solve the next row
        if (j == board[0].length) {
            return solveSudokuBetter(board, i + 1, 0, row, col, box);
        }
        // this is not an empty position, move to the next column to solve
        if (board[i][j] != '.') {
            return solveSudokuBetter(board, i, j + 1, row, col, box);
        }
        // calculate the box index;
        int boxIndex = i / 3 * 3 + j / 3;
        for (int k = 0; k < 9; k++) {
            // if the number k is not in the row , col & box lets put this k in this position and solve for the next position
            if (!row[i][k] && !col[j][k] && !box[boxIndex][k]) {
                row[i][k] = true;
                col[j][k] = true;
                box[boxIndex][k] = true;
                board[i][j] = (char)(k + '1');

                // solved for the next
                if (solveSudokuBetter(board, i, j + 1, row, col, box)) {
                    return true;
                }
                // we checked for 1-9 and could not find a solution we have to backtrack
                // and go back prev call to solve the problem. Revert the changes we made for this before leaving
                board[i][j] = '.';
                row[i][k] = false;
                col[j][k] = false;
                box[boxIndex][k] = false;
            }
        }

        return false;
    }


    // LeetCode:: 39. Combination Sum
    // Please check the following links for same type of backtracking problems
    // https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
    // The basic idea is to recursively solve this problem, more like 8 Queen.
    // We start with a number from the combination and keep subtracting it from the target until the target becomes zero.
    // We the take the next number from the combination and subtract.

    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> resList = new ArrayList<>();
        // sorting to optimise; we can skip bigger values than target also stop duplication,
        // by never looking back into the array. As the previous position has already solved
        // the problem for this position.
        // so for (3,5) and target 8, 3 has solved the 3 + 5 = 8 , we dont need to 5+3 = 8.
        Arrays.sort(candidates);
        for (int i = 0; i < candidates.length && candidates[i] <= target; i++) {
            // This list keeps track of the current solution for candidate[i],
            // remember this list would get overwritten in the recursion multiple times
            // hence in the recursion we need to make a copy of this list
            // Note: By just changing the LinkedList to ArrayList the runtime is improved from 3ms to 2ms.
            // So for all List lets use ArrayList as much as possible
            ArrayList<Integer> tempList = new ArrayList<>();
            tempList.add(candidates[i]);
            // This take care of each item in the set of candidates, so for [2,3,5]
            // it take cares of 2, 3 & 5 separately
            combinationSumRecurse(candidates, i, target - candidates[i], tempList, resList);
        }

        return resList;
    }

    private void combinationSumRecurse(int [] candidates, int index, int target,
                                       ArrayList<Integer> tList,
                                       List<List<Integer>> resultList) {
        //  Note: By just using this condition we reduced the number of recursion and have better runtime
        // of 3ms compared to 4ms without this condition
        if(target < candidates[index] && target != 0)
            return;
        // base case
        if (target == 0) {
            // only store success case in the result,
            // look by doing this we are adding the current list to the result List,
            // the tlist on this recursion path will contain valid entries,
            // in other recursion path it may contain invalid entries
            // but that is not our concern as we add only the valid list on to the result list
            resultList.add(new ArrayList<>(tList));
            return;
        }

        for (int i = index; i < candidates.length && candidates[i] <= target; i++) {
            // we are not using true path of return like we do for sudoku solver as that one search for one solution.
            // Here we go for all path and only store the success case in the result
            tList.add(candidates[i]);
            // we done need increase index here as we need to try to subtract the same item as much as possible
            combinationSumRecurse(candidates, i, target - candidates[i], tList, resultList);
            tList.remove(tList.size() -1);

        }

    }

    // This is another version of combination sum using the same approach
    // as the previous one only reducing the loop in non recursive function
    // although this version is very slow compared to the previous one
    // This version is not slow the issue was the presence of "System.out.println(resultList);" <-- not commented out
    public List<List<Integer>> combinationSumAlt(int[] candidates, int target) {
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(candidates);
        combSumRecAlt(candidates,0,target,new ArrayList<>(),resList);
        return resList;
    }

    private void combSumRecAlt(int [] candidates, int index, int target,
                                       ArrayList<Integer> tList,
                                       List<List<Integer>> resultList) {
        if (target < 0 )
            return;
        if (target == 0) {
            resultList.add(new ArrayList<>(tList));
            //System.out.println(resultList);
            return;
        }
        for (int i = index; i<candidates.length && candidates[i] <= target; i++) {
            tList.add(candidates[i]);
            // we done need increase i here as we need to try to subtract the same item as much as possible
            // not i + 1 because we can reuse same elements
            combSumRecAlt(candidates,i, target - candidates[i], tList, resultList);
            tList.remove(tList.size()-1);
        }

    }

    // LeetCode::40 Combination Sum II
    // Both v1 & v2 has same runtime lets use the V2 version that is more clean
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(candidates);
        for (int i = 0; i < candidates.length && candidates[i] <= target; i++) {
            ArrayList<Integer> tempList = new ArrayList<>();
            if ((i != 0 && candidates[i] == candidates[i-1]))
                continue;
            tempList.add(candidates[i]);
            combinationSum2Recurse(candidates, i +1, target - candidates[i], tempList, resList);

        }
        return resList;

    }

    // This has the same run time as the one above but it looks easy to read.
    // In both case runtime was very low 2ms. The loop in the above case is not needed.
    // Its better to use this version
    public List<List<Integer>> combinationSum2V2(int[] candidates, int target) {
        List<List<Integer>> resList = new ArrayList<>();
        // lets sort to skip processing for larger numbers than target & also to remove duplicate
        Arrays.sort(candidates);
        ArrayList tempList = new ArrayList();
        combinationSum2Recurse(candidates, 0, target, tempList, resList);
        return resList;
    }

    // We uses exactly the same approach as used in the first combinationSum problem
    // using the method "combinationSum". Dont worry about the loop in that problem that was not necessary.
    // The only difference here is we need ot skip the duplicates.
    // So in the recursion we check if the current number matches the previous number.
    // Iff yes then we can skip processing for the current item as it will lead to duplicate entries.
    // The sorted Array also helps in this case
    private void combinationSum2Recurse (int [] candidates, int index, int target,
                                         ArrayList<Integer> tempList,
                                         List<List<Integer>> resList) {
        if (target < 0)
            return;
        // we found the result, lets add to the result list
        if (target == 0) {
            resList.add(new ArrayList<>(tempList));
            //System.out.println(resList);
            return;
        }
        for (int i = index; i<candidates.length && candidates[i] <= target; i++){
            // skip the duplicates, for example in case of 2, 2, 5, 7
            // we dont need to process for 2 at postion 1 as we already considered 2 at psotion 0
            if ((i != index && candidates[i] == candidates[i-1]))
                continue;
            // lets use the generic back tracking approach
            tempList.add(candidates[i]);
            // lets use i + 1 as we do not want to the substract the same value multiple time,
            // this is another key difference with the first combination sum problem
            // check 'combinationSum' or 'combinationSumAlt' where we used 'i'
            combinationSum2Recurse(candidates,i +1,target - candidates[i], tempList, resList);
            tempList.remove(tempList.size()-1);
        }

    }

    // LeetCode :: 78. Subsets
    // backtrack to solve the all subset problem,
    // we start with a empty set and keep adding the subsets.
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(nums);
        subsetsBacktrack(nums, 0, new ArrayList<>(), resList);
        return resList;
    }

    private void subsetsBacktrack(int []nums, int index,
                                  ArrayList<Integer> tempList,
                                  List<List<Integer>> resultList) {
        // we add to the result on each call at first add the empty set [], next add [1], then [1,2] then [1,2,3]
        // then [1,3] (removed 2 from here and added the next item after 2  which is 3 )
        // and so on. The set created in this call will added to the result in the next call.
        resultList.add(new ArrayList<>(tempList));
        System.out.println(index + " " + resultList);
        for (int i = index; i <nums.length; i++){
            // use the backtrack approach add the current item and recurse,
            // remove the current item and add the next from the list
            // [  using generic backtrack approach,
            //    the next three lines is a very generic backtrack approach
            //    we used this same approach in other recursion problems
            //    like permutation, palindrom partition , combination sum
            // ]
            tempList.add(nums[i]);
            subsetsBacktrack(nums, i + 1, tempList,resultList);
            tempList.remove(tempList.size()-1);
        }

    }

    // AdnanAziz Subset Recursion with k length
    // GENERATE ALL SUBSETS OF SIZE k
    // The idea is count the remaining items to add and direct/ reduce the recursion calls as much as possible
    private void subsetKSizeHelper (int []nums, int index, int k,
                                    ArrayList<Integer> tempList,
                                    List<List<Integer>> rList) {

        if(tempList.size() == k){
            rList.add(new ArrayList<>(tempList));
            return;
        }
        // we calc the remaining items to add to the list
        int remainIng = k - tempList.size();
        // now depending on the remaining items to collect we reduce the number of recursion calls
        for (int i = index; i < nums.length && remainIng <= nums.length -i; i++) {
            tempList.add(nums[i]);
            subsetKSizeHelper(nums, i + 1, k, tempList, rList);
            tempList.remove(tempList.size()-1);
        }
    }
    public List<List<Integer>> subsetKSize (int [] nums, int k) {
        List<List<Integer>> resList = new ArrayList<>();
        subsetKSizeHelper(nums, 0, k, new ArrayList<>(), resList);
        return resList;
    }

    //LeetCode :: 90 Subset With Duplicate
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDupBacktrack(nums, 0, new ArrayList<>(), resList);
        return resList;
    }

    private void subsetsWithDupBacktrack(int[] nums, int index,
                                         ArrayList<Integer> tempList,
                                         List<List<Integer>> resultList) {
        resultList.add(new ArrayList<>(tempList));
        System.out.println(index + " : " + resultList);
        for (int i = index; i < nums.length; i++) {
            tempList.add(nums[i]);
            subsetsWithDupBacktrack(nums, i + 1, tempList, resultList);
            int x = tempList.remove(tempList.size()-1);
            // avoid the processing the duplicates,
            // we pop the item and then if the item matches the next item, skip all of matching item processing
            while (i+1 <nums.length && x == nums[i + 1])
                i++;
        }

    }

    // Version 2 of duplicate subset
    public List<List<Integer>> subsetsWithDupV2(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDupBacktrackV2(nums, 0, new ArrayList<>(), resList);
        return resList;
    }

    // The difference is we are checking if the previous item is a duplicate of the current item
    // and skip processing for them. Note: the two algo have same runtime, so it deos not matter
    // if we check the next item or previous item
    private void subsetsWithDupBacktrackV2(int[] nums, int index,
                                           ArrayList<Integer> tempList,
                                           List<List<Integer>> resultList) {
        resultList.add(new ArrayList<>(tempList));
        System.out.println(index + " : " + resultList);
        for (int i = index; i < nums.length; i++) {
            // avoid the processing the duplicates
            if(i != index && nums[i-1] == nums[i])
                continue;
            // using generic backtrack approach
            tempList.add(nums[i]);
            subsetsWithDupBacktrackV2(nums, i + 1, tempList, resultList);
            tempList.remove(tempList.size()-1);

        }

    }

    // solve nC3 combination
    public List<List<Integer>> combineThree(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        combineThreeRec(nums, 0, new ArrayList<>(), resultList);
        return resultList;
    }
    private void combineThreeRec(int []nums, int index,
                                 ArrayList<Integer> tmpList,
                                 List<List<Integer>> rList ) {
        // we use nC3 here so comparing with size == 3 if we want nCr then compare with size == r
        if(tmpList.size() == 3) {
            rList.add(new ArrayList<>(tmpList));
            return;
        }
        for (int i = index; i < nums.length; i++){
            tmpList.add(nums[i]);
            combineThreeRec(nums, i +1,tmpList, rList);
            tmpList.remove(tmpList.size()-1);
        }


    }

    // Leetcode:: 46 Permutation
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        permutateBacktrack(nums, 0, new ArrayList<>(), resList);
        return resList;
    }

    private void permutateBacktrack(int[] nums, int index,
                                    ArrayList<Integer> tempList,
                                    List<List<Integer>> rList) {
        // when the list size equals the total number of entries
        // we reached our solution lets add it to the result list
        if(index == nums.length) {
            rList.add(new ArrayList<>(tempList));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            if (index != i) {
                int tmp = nums[index];
                nums[index] = nums[i];
                nums[i] = tmp;
                tempList.add(nums[index]);
            }
            else
                tempList.add(nums[i]);
            // Note that we need to send index +1 in the recursion instead of i+1
            permutateBacktrack(nums,  index+ 1, tempList, rList);
            tempList.remove(tempList.size()-1);
            if (index!=i) {
                int tmp = nums[index];
                nums[index] = nums[i];
                nums[i] = tmp;
            }

        }

    }

    // This is more simpler version
    public List<List<Integer>> permuteV2(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        permutateBacktrackV2(nums, new ArrayList<>(), resList);
        return resList;
    }

    // we keep adding to the list if the entry is not in the list
    // until the list size equals the total number of element
    private void permutateBacktrackV2(int[] nums,
                                      ArrayList<Integer> tempList,
                                    List<List<Integer>> rList) {
        // when the list size equals the total number of entries
        // we reached our solution lets add it to the result list
        if(tempList.size() == nums.length){
            rList.add(new ArrayList<>(tempList));
        } else{
            // This way we start with each position in the array and try the numbers is other position,
            // so we dont need to do a swap revert the swap as done in version 1.
            // For example {1,2,3} in pos 0 we will try 1, 2, 3
            for(int i = 0; i < nums.length; i++){
                // this list search will be linear search
                if(tempList.contains(nums[i]))
                    continue; // element already exists, skip

                // using generic backtrack approach
                tempList.add(nums[i]);
                permutateBacktrackV2(nums, tempList, rList);
                tempList.remove(tempList.size() - 1);
            }
        }

    }
    // More space but faster
    private void permutateBacktrackV3(int[] nums,
                                      ArrayList<Integer> tempList,
                                      List<List<Integer>> rList, boolean []map) {
        if(tempList.size() == nums.length){
            rList.add(new ArrayList<>(tempList));
            return;
        }
        for(int i = 0; i<nums.length;i++) {
            if (map[i])
                continue;
            tempList.add(nums[i]);
            map[i] = true;
            permutateBacktrackV3(nums,tempList,rList,map);
            tempList.remove(tempList.size()-1);
            map[i] = false;
        }

    }

    // LeetCode :: 47 Permutation II with duplicate.
    // Use the sam logic as permutation backtrack algo.
    // To identify the duplicate use a boolean to keep track of each used postion in nums,
    // We only add to list if the position is not used before, we also skip the duplicates processing
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
        // the sort is necessary to handle the duplicates
        Arrays.sort(nums);
        boolean []used = new boolean[nums.length];
        Arrays.fill(used,false);
        permutateUniqueBackTrk(nums, new ArrayList<>(), resList , used);
        return resList;

    }

    private void permutateUniqueBackTrk(int [] nums, ArrayList<Integer> tempList,
                                        List<List<Integer>> rList,
                                        boolean []used) {
        if (tempList.size() == nums.length) {
            rList.add(new ArrayList<>(tempList));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // iff the current number has been used skip this,
            // otherwise check for duplicate numbers and skip the duplicates.
            // during duplicate check , we verify that the number the current number has not been used
            // but the previous number is also not used. If the previous number is used then we can
            // add the current number to the list.
            // the condition after || handles the case when we start with the duplicate item in the next iteration
            //  for example in [1,1,2] when in 2nd iteration nums[1]  was being considered at position 0 its skipped
            //  as it would give same result as nums[0] at position 0
            if(used[i] ||
                    (i != 0 && nums[i] == nums[i-1]
                            && used[i-1] == false)) {
                continue;
            }
            // using generic backtrack approach
            tempList.add(nums[i]);
            used[i] = true;
            permutateUniqueBackTrk(nums, tempList, rList, used);
            tempList.remove(tempList.size()-1);
            used[i] = false;


        }


    }

    //LeetCode:: 131. Palindrome Partitioning.
    // The basic idea is to use backtracking to check all possible partition
    // and only add the valid parttion in the list. For example in case of abcba we start with a,bcba
    // then a,b,cba, then a,b,c,ba then a,b,c,b,a (only partion that gives valid palindrome so we add it to the list).
    // Next we check a,b,cb, a and so on

    public List<List<String>> partition(String s) {
        List<List<String>> resList = new ArrayList<>();
        partitionPalindrome(s, 0, new ArrayList<String>(), resList);
        return resList;
    }

    private boolean checkPalindrome (String s) {

        int start = 0;
        int end = s.length() - 1;
        while (start <= end && s.charAt(start) == s.charAt(end)) {
            start++;
            end--;
        }
        if (start>end)
            return true;
        return false;
    }

    private void partitionPalindrome(String s, int index,
                                     ArrayList<String> tempList,
                                     List<List<String>> rList) {
        // base condition: we reached the end of the string
        // lets add the the valid partitions to our list.
        // This will have partitions that are valid palindrome as
        // we dont put non valid palindrome in templist;
        // check the condition in the for loop below
        if(index == s.length()){
            rList.add(new ArrayList<>(tempList));
            System.out.println(rList);
            return;
        }

        for (int i = index + 1 ; i <= s.length(); i++){
            // Note we start with i = index + 1  so that in the substring method
            // we can start with substring that is  between (index, i).
            // For that reasons i <= s.length to include the last character of the string
            // as substring method's 2nd parameter is exclusive
            String substr = s.substring(index, i);
            // Skip the invalid partition; if the current partition is not a valid palindrome
            // we dont need to process this partition and move to the next partition.
            if (!checkPalindrome(substr))
                continue;
            // using generic backtrack approach
            tempList.add(substr);
            // we need to send i in the next iteration as i is the next position
            // cause we start from i = index +1 so now i points to the next position in recursion
            partitionPalindrome(s, i, tempList,rList);
            tempList.remove(tempList.size()-1);
        }

    }
    // This approach also uses the same backtrack idea
    // only difference instead of i = index + 1 we start with i = index in the main for loop

    public List<List<String>> partitionV2(String s) {
        List<List<String>> resList = new ArrayList<>();
        partitionPalindromeV2(s, 0, new ArrayList<String>(), resList);
        return resList;
    }
    private void partitionPalindromeV2(String s, int index,
                                     ArrayList<String> tempList,
                                     List<List<String>> rList) {
        if(index == s.length()){
            rList.add(new ArrayList<>(tempList));
            System.out.println(rList);
            return;
        }
        for (int i = index; i < s.length(); i++) {
            // get the substr between i , i+1
            // dont worry about i + 1 breaking s.length as i+1 is  exclusive in subString method
            String substr = s.substring(index,i+1);
            // skip processing invalid partitions
            if(!checkPalindrome(substr))
                continue;
            // using generic backtrack approach
            tempList.add(substr);
            // we use i +1 as the next iteration as i +1 will be the position in the partition
            partitionPalindromeV2(s,i +1, tempList,rList);
            tempList.remove(tempList.size()-1);
        }

    }


    // Leetcode 718 :: Maximum Length of Repeated Subarray (DP) same as longest common substring
    public int findLength(int[] A, int[] B) {
        int result = 0;
        int m = A.length;
        int n = B.length;
        int [][]lcs = new int [n+1][m+1];

        // init the lcs array the 1st row & columns should be zero
        for (int i = 0; i<=n; i++)
            lcs[i][0] = 0;
        for (int i = 0; i <= m; i++)
            lcs[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                // to determine longest common substring, if the current char from str1 & str2 matches
                // then we increments the size by 1, by adding 1 with the previous substring
                // so the DP solution is Lcs (i,j) = lcs(i-1, j-1) + 1  if the char at i & j position matches
                // otherwise lcs(i,j) = 0
                // For this we dont need to consider any other position for example (i-1,j) or(i,j-1)
                // cause the substring length at (i,j) is important
                if (A[i-1] == B[j-1]) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    result = Math.max(result,lcs[i][j]);
                }
                else
                    lcs[i][j] = 0;
            }
        }

        return result;
    }

    // this uses rolling hash & robin karp algo
    public int findLengthV2(int[] A, int[] B) {
        int la = A.length;
        int lb = B.length;

        int p = 119;
        int len = Math.max(la, lb)+1;
        int[] ps = new int[len];
        ps[0] = 1;
        for(int i = 1; i < len; i++) {
            ps[i] = ps[i-1]*p;
        }

        int[] hashA = new int[la+1];
        for(int i = 1; i <= la; i++) {
            hashA[i] = hashA[i-1] + A[i-1] * ps[i];
        }

        int[] hashB = new int[lb+1];
        for(int i = 1; i <= lb; i++) {
            hashB[i] = hashB[i-1] + B[i-1]*ps[i];
        }

        int lo = 1;
        int hi = Math.min(la, lb);
        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            HashSet<Integer> set = new HashSet<>();
            for(int i = 1; i+mid-1 <= la; i++) {
                int hashVal = (hashA[i+mid-1]-hashA[i-1])*ps[len-mid-i+1];
                set.add(hashVal);
            }
            boolean found = false;
            for(int i = 1; i+mid-1 <= lb; i++) {
                int hashVal = (hashB[i+mid-1]-hashB[i-1])*ps[len-mid-i+1];
                if(set.contains(hashVal)) {
                    found = true;
                    break;
                }
            }

            if(found) {
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        }

        return hi;
    }

    // solve nCk combination
    // LeetCode :: 77. Combinations
    public List<List<Integer>> combineNCK(int[] nums, int k) {
        List<List<Integer>> resultList = new ArrayList<>();
        combineNCKRec(nums, 0, new ArrayList<>(), resultList, k);
        return resultList;
    }

    public List<List<Integer>> combine(int n, int k) {
        int []nums = new int[n];
        for (int i = 0; i<n; i++)
            nums[i] = i+1;
        return combineNCK(nums,k);

    }
    private void combineNCKRec(int []nums, int index,
                                 ArrayList<Integer> tmpList,
                                 List<List<Integer>> rList , int k) {
        // we use nC3 here so comparing with size == 3 if we want nCr then compare with size == r
        if(tmpList.size() == k) {
            rList.add(new ArrayList<>(tmpList));
            return;
        }
        for (int i = index; i < nums.length; i++){
            tmpList.add(nums[i]);
            combineNCKRec(nums, i +1,tmpList, rList, k);
            tmpList.remove(tmpList.size()-1);
        }
    }

    // LeetCode :: 79  Words Search
    // The idea is to recursively  all board position to find the desired output using a backtracking approach
    // Use backtracking to solve the problem same as 8-Queen or subset or permutation
    public boolean exist(char[][] board, String word) {
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                // check for all position in board
                if (traverseBoard(board, i, j, word, 0))
                    return true;
            }
        }
        return false;

    }

    private boolean traverseBoard (char [][]board, int row, int col, String word, int idx) {
        //  we found all the chars in board it means we found the word in the board
        if (idx == word.length())
            return true;
        // we exceeded the board boundary lets return false
        if (row == board.length || row < 0 || col == board[0].length || col < 0)
            return false;
        // the current char is not found in board lets return false
        if(word.charAt(idx) != board[row][col])
            return false;
        // mark this position as visited for backtracking, if this visited the during our
        // dfs traversal we will not visit this again hence avoid a loop.
        // Note how we ^= with 256 this is done as the all chars are between 0 - 255
        // xor ing 256 with char sets the char same but the 9th bit becomes 1
        board[row][col] ^= 256;
        boolean isFound = traverseBoard(board, row, col +1, word, idx+1) ||
                traverseBoard(board, row, col - 1, word, idx+1) ||
                traverseBoard(board, row - 1, col, word, idx+1)||
                traverseBoard(board, row + 1, col, word, idx+1);
        // after our recursive call we revert the visited
        // lets make this not visited as backtracking is done
        board[row][col] ^= 256;
        return isFound;

    }

    //Leetcode :: 81  Search in Rotated Sorted Array II
    // The idea is to do a binary search to find the split/rotation point in the array
    // we use another binary search to find the target but this time while calculating mid
    // we use the splitIndex to get the actual mid
    // we can then use the split index to find the actual mid
    // if we have dupleicate items in the array then while searching for splitIndex
    // we have to modify the binary search for splitIndex to do a O(n)
    // search [when nums[mid] == nums[high] meaning the split can be between (mid,high)
    // or its on the left of mid hence o(n) search]
    public int rotatedBinSearchDup (int []nums, int target){
        int mid = 0;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            mid = (low + high)/2;

            if (nums[mid] > nums[high])
                low = mid + 1;
            else if (nums[mid] == nums[high]) {
                // this means that the spilt can be in between (mid & high)
                // or even it can exist beyond left of mid, so we dont have
                // any other way but a O(n) search to find the split
                int cur = high;
                while (cur > 0 && nums[cur] == nums[mid])
                    cur--;
                // spilt is beyond left of mid & cur is now pointing to left of
                // spit point so increase cur by 1 to move to split point
                if (nums[cur] > nums[mid])
                    cur++;
                high = cur;
                //break;
            }
            else
                high = mid;
        }
        int splitIdx = high;
        //System.out.println("SplitIdx = " + splitIdx);
        low = 0;
        high = nums.length - 1;
        int realMid = 0;
        while (low <= high){
            mid = (low + high)/2;
            // calculate the actual mid using the spiltIdx
            // we dont need to translate the low or high as
            // our goal is ot get mid so we translate mid
            realMid = (mid + splitIdx) % nums.length;
            if (nums[realMid] == target)
                return realMid;
            else if (target < nums[realMid])
                high = mid-1;
            else
                low = mid + 1;
        }

        return -1;
    }
    public boolean searchRotateDup(int[] nums, int target) {
        int x = rotatedBinSearchDup(nums, target);
        return (x == -1)? false: true;
    }

    // LeetCode :: 91. Decode Ways, This is not the right solution check the DP solution in the DP file
    int countDecode = 0;
    private void numDecodingsRec(int idx, String s) {
        if (idx >= s.length())
            return;
        if(s.length() - 1  == idx){
            countDecode++;
            return;
        }
        if (idx + 1 < s.length()) {
            if(s.charAt(idx+1) !='0')
                numDecodingsRec(idx+1, s);
            else{
                if(idx +1 == s.length() -1){
                    countDecode++;
                    return;
                }
                numDecodingsRec(idx+2, s);

            }
        }
        if (idx + 1 < s.length()) {
            if ((s.charAt(idx) == '1' && s.charAt(idx +1) != '0')||
                    (s.charAt(idx) == '2' &&
                            s.charAt(idx +1)>='1' &&s
                            .charAt(idx+1) <='6')) {
                if (idx + 2 >= s.length()) {
                    countDecode++;
                    return;
                }
                numDecodingsRec(idx+2, s);
            }
        }
    }
    public int numDecodings(String s) {

        int i = 1;
        if(s.charAt(0) == '0')
            return 0;
        while (i < s.length()){
            if (s.charAt(i) == '0' &&
                    (s.charAt(i-1) != '1' && s.charAt(i-1) != '2')) {
                countDecode = -1;
                break;
            }
            i++;
        }
        if (countDecode == -1)
            return 0;

        numDecodingsRec(0,s);
        return countDecode;
    }

    // 93. Restore IP Addresses
    // Simply found the postions where putting the dot makes it a valid IP and add them int the list
    private boolean isValidIP(String s, int fIdx, int sIdx, int tIdx) {
        String fQd = s.substring(0, fIdx);
        String sQd = s.substring(fIdx+1, sIdx);
        String tQd = s.substring(sIdx+1, tIdx);
        String lQd = s.substring(tIdx+1, s.length());
        if((fQd.charAt(0) == '0' && fQd.length() > 1) ||
                (sQd.charAt(0) == '0' && sQd.length() > 1) ||
                (tQd.charAt(0) == '0' && tQd.length() > 1) ||
                (lQd.charAt(0) == '0' && lQd.length() > 1) )
                return false;

        if(Integer.parseInt(fQd) > 255  ||
                Integer.parseInt(sQd) > 255 ||
                Integer.parseInt(tQd) > 255 ||
                Integer.parseInt(lQd) > 255)
            return false;
        return true;
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> rList = new ArrayList<>();
        if (s.length() < 4 || s.length() > 12) {
            return rList;
        }

        for (int i = 1; i <=3; i++){
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k<=3; k++){
                    StringBuilder str = new StringBuilder(s);
                    int sIdx = i+1+j;
                    int tIdx = sIdx + 1 + k;
                    if (i < s.length() && sIdx < s.length() + 1 && tIdx < s.length() +2) {
                        str.insert(i,'.');
                        str.insert(sIdx,'.');
                        str.insert(tIdx,'.');

                        if (isValidIP(str.toString(), i, sIdx, tIdx))
                            rList.add(str.toString());
                    }
                }

            }
        }
        return rList;
    }

    // The version2 solutions is more interesting, It uses a DFS/ backtracking approach to the problem.
    // We start with a empty string add substring to it from  the main string and check if this substring part
    // is valid  & then add the next part check if that is valid when we have found all the four valid parts
    // then its a valid solution
    public List<String> restoreIpAddressesV2(String s) {
        List<String> result = new ArrayList<>();
        doRestore(result, "", s, 0);
        return result;
    }

    private void doRestore(List<String> result, String path, String s, int k) {
        if (s.isEmpty() || k == 4) {
            if (s.isEmpty() && k == 4)
                result.add(path.substring(1));
            return;
        }
        for (int i = 1; i <= (s.charAt(0) == '0' ? 1 : 3) && i <= s.length(); i++) { // Avoid leading 0
            String part = s.substring(0, i);
            // if this part is valid go to the next part, if this is not valid
            // lets do nothing and check the next option by incrementing 1
            if (Integer.valueOf(part) <= 255)
                doRestore(result, path + "." + part, s.substring(i), k + 1);
        }
    }

    // The solution for letterCombinations Problem
    // Leetcode 17 :: letterCombinations
    Map<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};

    List<String> output = new ArrayList<String>();

    public void backtrack(String combination, String next_digits) {
        // if there is no more digits to check
        if (next_digits.length() == 0) {
            // the combination is done
            output.add(combination);
        }
        // if there are still digits to check
        else {
            // iterate over all letters which map
            // the next available digit
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = phone.get(digit).substring(i, i + 1);
                // append the current letter to the combination
                // and proceed to the next digits
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() != 0)
            backtrack("", digits);
        return output;
    }

    // Adnan Aziz 12.2 :: Search a Sorted Array for entry equal to index
    // no duplicate in the array. The reason its same to basic binary search is the asumption of no duplicate
    public int searchNetryEqualToIndx(int []nums) {
        int low = 0;
        int high = nums.length-1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high-low)/2;
            if (nums[mid] == mid){
                result = mid;
                break;
            } else if (nums[mid] > mid ){
                high = mid - 1;
            } else {
                low = mid + 1;
            }

        }
        return result;
    }

    public int searchEntryEqualToIndxWithDup(int []nums, int low, int high) {
        if (low > high)
            return -1;
        int mid = low + (high-low)/2;
        if (nums[mid] == mid)
            return mid;
        int left = searchEntryEqualToIndxWithDup(nums, low, Math.min(nums[mid],mid-1));
        if (left >= 0)
            return left;
        return searchEntryEqualToIndxWithDup(nums, Math.max(mid+1,nums[mid]),high);
    }

    // LeetCode :: 153. Find Minimum in Rotated Sorted Array
    // This use the same approach of searching in rotated array, we need to find the split index or the rotation
    // point using a modified binary search. The rotation point is the lowest index
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        while (low < high) {
            int mid = low + (high -low)/2;
            if (nums[mid] > nums[high]) {
                low = mid +1;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }

    // LeetCode :: 154. Find Minimum in Rotated Sorted Array II (Hard)
    // This is use the same approach as searching in a rotated array, We need to find the rotation index.
    // The one trick her is the array can contains duplicate numbers. So if an array contains duplicate after
    // finding a nums[mid] that is equal to nums[high] we dont know whether the rotation is on the left or
    // right of mid so we just decrease the upper bound and search again
    //
    // Note: In this approach we always find the min value but not necessarily the splitIndex
    // consider the example of 1 1 1 1 1 1 1 2 2 1 1 1 1 here the nums[low] value will result in 1 at the end which
    // is our desired result but it low or high WILL NOT point to splitIndex.To get the actual split index us
    // the aproach used in rotatedBinary search  with dup in 'rotatedBinSearchDup'
    public int findMinWithDup(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        while (low < high) {
            int mid = low + (high-low)/2;
            if (nums[mid] > nums[high]) {
                low = mid+1;
            } else if (nums[mid] < nums[high]) {
                high = mid;
            } else {
                // when nums[mid] == nums[high], we dont know which side of the
                // mid rotation will end up so we just reduce the upper bound by
                // one expecting the nums[high-1] may be smaller tha nums[mid]
                high--;
            }
        }
        return nums[low];
    }


    //LeetCode :: 200. Number of Islands
    private void numIslandsRec(char[][] grid, int count, int r, int c) {
        if(r < 0 || c < 0 ||
                r >= grid.length ||
                c >= grid[0].length ||
                grid[r][c]!='1')
            return;

        grid[r][c] = (char)('A' + count);
        numIslandsRec(grid, count, r+1, c);
        numIslandsRec(grid, count, r-1, c);
        numIslandsRec(grid, count, r, c+1);
        numIslandsRec(grid, count, r, c-1);
    }
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i<grid.length;i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '1') {
                    count++;
                    numIslandsRec(grid,count, i,j);
                }

            }
        }
        return count;
    }

    // LeetCode :: 240. Search a 2D Matrix II
    // The idea is to start with the Top Right corner and move to left if the target is smaller
    // than the value at matrix [i][j] if the target is bigger we go down
    // check the iterative version below its faster
    private boolean searchMatRec( int [][]matrix, int target, int i, int j) {
        if (j < 0 || i < 0 || i >= matrix.length || j >= matrix[0].length)
            return false;
        if (matrix[i][j] == target)
            return true;
        else if(target > matrix[i][j]){
            return searchMatRec(matrix, target,i +1,j);
        } else {
            return searchMatRec(matrix, target, i, j-1);
        }
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0)
            return false;
        return searchMatRec(matrix, target, 0, matrix[0].length);
    }

    // Same idea just using iterative solution vs recursive
    public boolean searchMatrixIter(int[][] matrix, int target) {
        if (matrix.length == 0)
            return false;
        int i = 0;
        int j = matrix[0].length - 1;
        while( i< matrix.length || j>=0) {
            if(matrix[i][j] == target)
                return true;
            else if(target > matrix[i][j]) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    // LeetCode :: 680. Valid Palindrome II
    private boolean validPalindromeRec(String s, int i , int j, int skip) {
        if(i >= j)
            return true;
        if (s.charAt(i) == s.charAt(j))
            return validPalindromeRec(s, i+1, j-1 , skip);
        else
            return skip > 0 && (validPalindromeRec(s, i+1, j, skip-1) ||
                    validPalindromeRec(s, i,j-1, skip -1));
    }
    public boolean validPalindrome(String s) {
        return validPalindromeRec(s, 0, s.length()-1, 1);
    }

    // LeetCode :: 306. Additive Number
    // The idea is to get the last & cur values and add it to get the result and
    // check if the result matches next sequence. We need to use BigInteger to reduce
    // the complexity of handling overflow issues. The runtime for the recursion is O(n)
    // So we can turn into an iterative solution. The total runtime for this problem is
    // O(n^3 * num.length)
    private boolean isAdditiveNumberRec(String num, int idx,
                                        BigInteger last, BigInteger cur) {
        if (idx >= num.length())
            return true;
        if (num.charAt(idx) == '0')
            return false;
        BigInteger sum = last.add(cur);
        int len = sum.toString().length();
        if (idx + len > num.length())
            return false;

        BigInteger temp = new BigInteger(num.substring(idx, idx+len));
        return (temp.equals(sum) && isAdditiveNumberRec(num, idx + len, cur, temp));

    }

    public boolean isAdditiveNumber(String num) {
        int k = 0;
        while (k < num.length() && num.charAt(k++)=='0');
        if (k > 2 && k == num.length()) return true;
        for (int i = 1; i<num.length(); i++) {
            for (int j = i+1; j < num.length(); j++) {
                if (num.charAt(0) == '0' || num.charAt(i) =='0')
                    continue;
                BigInteger last = new BigInteger(num.substring(0, i));
                BigInteger cur = new BigInteger(num.substring(i, j));
                if (isAdditiveNumberRec(num, j, last, cur))
                    return true;
            }
        }
        return false;
    }

    // LeetCode :: 216. Combination Sum III
    // This idea is the same as combination sum we recurse & backtrack to find the solution
    // The range of number is 1 to 9 so in the for loop we used startIdx to 9
    // We used a boolean map to keep track of the used items in this recursion to avoid duplication
    private void combinationSum3Rec(int k, int target, int sum,
                                    List<List<Integer>> resList,
                                    ArrayList<Integer> tempList,
                                    boolean[] used,
                                    int startIdx) {
        if (tempList.size() > k)
            return;
        // The list can have only k elements if that is the case & our sum is equal to the given target
        // then we have our solution
        if (tempList.size() == k && sum == target) {
            resList.add(new ArrayList<>(tempList));
            return;
        }
        for (int i = startIdx; i <= 9; i++) {
            // if this number is not used so far in the recursion we can use it
            // we back track to find a solution
            if (!used[i]) {
                tempList.add(i);
                used[i] = true;
                // we increase our sum by i for the next recursion
                combinationSum3Rec(k, target, sum + i, resList, tempList, used, i+1);
                used[i] = false;
                tempList.remove(tempList.size() -1);
            }

        }

    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> resList = new ArrayList<>();
        boolean []used = new boolean[10];
        combinationSum3Rec(k, n,0,resList, new ArrayList<>(),used, 1);
        return resList;
    }

    // LeetCode :: 934. Shortest Bridge
    // Recursion + DFS + BFS
    // The idea is to do a DFS to color one of the island 2 so that the other Island remain color 1
    // Then we start from the color 2 island a bfs to island 1 the bfs shortest distant from island 2
    // to island 1 is the solution. To optimise when we do a dfs to color the island 2 we store the each
    // item of island 2 in our bfs queue. So next we expand along the breadth when doing BFS
    class IntPair {
        Integer x;
        Integer y;
        public IntPair (int a, int b){
            x = a;
            y = b;
        }
    }
    // do a dfs to color island 2 and also store the island to indicies in queue
    private void markIsland(int [][]A, int r, int c, Queue <IntPair> queue) {
        if (r < 0 || c < 0 || r >= A.length || c >= A[0].length || A[r][c] != 1)
            return;
        if (A[r][c] == 1) {
            A[r][c] = 2;
            queue.add(new IntPair(r,c));
            markIsland(A, r +1, c, queue);
            markIsland(A, r -1, c, queue);
            markIsland(A, r, c-1, queue);
            markIsland(A, r, c+1, queue);
        }

    }
    // just  expand the island 2 we will color(store shortes path len) along the bfs border
    private int expandIsland(int [][]A, int r, int c, Queue <IntPair> queue) {
        if (r+1 < A.length) {
            if (A[r+1][c] == 0) {
                A[r+1][c] = A[r][c]+1;
                queue.add(new IntPair(r+1,c));
            }
            if (A[r+1][c] == 1)
                return 1;
        }
        if (r -1 >= 0) {
            if (A[r-1][c] == 0) {
                A[r-1][c] = A[r][c]+1;
                queue.add(new IntPair(r-1,c));
            }
            if(A[r-1][c] == 1)
                return 1;
        }
        if (c+1 < A[0].length ) {
            if (A[r][c+1] == 0) {
                A[r][c+1] = A[r][c]+1;
                queue.add(new IntPair(r,c+1));
            }
            if (A[r][c+1] == 1)
                return 1;
        }
        if (c-1 >= 0) {
            if (A[r][c-1] == 0) {
                A[r][c-1] = A[r][c]+1;
                queue.add(new IntPair(r,c-1));
            }
            if (A[r][c-1] == 1)
                return 1;

        }
        return 0;
    }

    public int shortestBridge(int[][] A) {
        Queue<IntPair> queue = new LinkedList<>();
        int i = 0;
        int j = 0;
        // mark one island to 2, now we have one island marked by 2 & another by 1
        for (i = 0; i< A.length; i++){
            for (j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1) {
                    markIsland(A,i,j, queue);
                    break;
                }
            }
            if (j != A[0].length)
                break;
        }

        IntPair pair = new IntPair(i,j);
        // Do a BFS to get the shortest path from Island 2 to Island 1
        while (!queue.isEmpty()) {
            pair = queue.remove();
            if (expandIsland(A, pair.x, pair.y, queue) == 1) {
                break;
            }
        }
        // the result is shortest path len - 2 (we subtract 1 for each island)
        return A[pair.x][pair.y] - 2;
    }

    // LeetCode :: 301. Remove Invalid Parentheses (Not submitted)
    // Too complicated solution
    public List<String> removeInvalidParentheses(String s) {
        List<String> output = new ArrayList<>();
        removeHelper(s, output, 0, 0, '(', ')');
        return output;
    }

    public void removeHelper(String s, List<String> output, int iStart, int jStart, char openParen, char closedParen) {
        int numOpenParen = 0, numClosedParen = 0;
        for (int i = iStart; i < s.length(); i++) {
            if (s.charAt(i) == openParen) numOpenParen++;
            if (s.charAt(i) == closedParen) numClosedParen++;
            if (numClosedParen > numOpenParen) { // We have an extra closed paren we need to remove
                for (int j = jStart; j <= i; j++) // Try removing one at each position, skipping duplicates
                    if (s.charAt(j) == closedParen && (j == jStart || s.charAt(j - 1) != closedParen))
                        // Recursion: iStart = i since we now have valid # closed parenthesis thru i. jStart = j prevents duplicates
                        removeHelper(s.substring(0, j) + s.substring(j + 1, s.length()), output, i, j, openParen, closedParen);
                return; // Stop here. The recursive calls handle the rest of the string.
            }
        }
        // No invalid closed parenthesis detected. Now check opposite direction, or reverse back to original direction.
        String reversed = new StringBuilder(s).reverse().toString();
        if (openParen == '(')
            removeHelper(reversed, output, 0, 0, ')','(');
        else
            output.add(reversed);
    }

    // This much easier to understand but requires the use of Set to store the unique items which is little expensive
    // The idea here is to first find out how many invalid left/right parentheses can be deleted. We need to check
    // all possible options but we can reduce the recursion calls by only performing delete for the allowed number of
    // leftRem & rightRem we can also further guarantee the the final solution by restricting that right parentheses
    // cannon be more than left parentheses

    HashSet<String> parenthesesList = new HashSet<>();

    private void removeParentheses(String s, int index, int left, int right,
                                   int leftRem, int rightRem, StringBuilder sb){
        if(index == s.length()) {
            // when leftRem & rightRem both are zero and we have processed the whole string it means that we have
            // correctly removed all the invalid parentheses. Parentheses will be valid if and only if the left &
            // right count are equal if both leftRem & rightRem is zero we have our solution
            if(leftRem ==0 && rightRem == 0) {
                parenthesesList.add(sb.toString());
            }
            return;
        }
        char ch = s.charAt(index);
        int len = sb.length();
        // we delete a parenthesis if only we are allowed to delete

        if (s.charAt(index) ==  '(' && leftRem > 0 ||
                s.charAt(index) ==')' && rightRem > 0) {
            removeParentheses(s, index +1, left,
                    right, ch =='('? leftRem -1 : leftRem,
                    ch==')' ? rightRem -1: rightRem, sb);
        }
        sb.append(ch);
        if (ch != '(' && ch != ')') {
            removeParentheses(s, index+1, left,right, leftRem, rightRem, sb);
        } else if (ch == '(') {
            removeParentheses(s,index+1,
                    left+1, right, leftRem , rightRem, sb);
        } else if( right < left) { // ')' count is lower than '('
            removeParentheses(s, index +1, left,
                    right+1, leftRem, rightRem, sb);
        }
        sb.deleteCharAt(len);

    }
    
    public List<String> removeInvalidParenthesesV2(String s) {
        int leftCount = 0;
        int rightCount = 0;
        for (int i =0; i< s.length(); i++){
            if(s.charAt(i)=='(') {
                leftCount++;
            } else if (s.charAt(i)==')') {
                leftCount--;
                if(leftCount < 0) {
                    leftCount = 0;
                    rightCount++;
                }
            }
        }
        System.out.println(leftCount + " " + rightCount);
        removeParentheses(s,0,0,0, leftCount,rightCount , new StringBuilder());
        List<String> resList = new ArrayList<>(parenthesesList);
        return resList;

    }

    // LeetCode :: 267 Palindrome permutation - II
    // We have to use a backtracking approach
    // The idea is to find build palindrome from middle to outward. First we check if a palindrome can be formed from
    // this if no we just return. Otherwise we generate the palindromes from middle to outwards. First we need to find
    // if there is odd length palindrome and find the middle char. if middle char exist we put the middle char as our
    // string en expand on both side of it for example abcDcba  so D is the middle char we start with a string "D" and
    // expand outward "cDc" -->" bcDcb" --> "abcDcba". The we use "bDb" -->" cbDbc" --> "acbDbca" and so on ...
    // This approach actually ensures that we dont recurse too much and we only generate valid results when we reach
    // the end. Look how the frequency is reduced in the recursion to ensure we have valid palindrome at the end
    private void genPalindromeHelper (int len, String palStr, List<String> resList, HashMap<Character, Integer> freqMap) {
        if(palStr.length() == len){
            resList.add(palStr);
            return;
        }
        for(Character ch : freqMap.keySet()) {
            int count = freqMap.get(ch);
            // if count is positive we can put this char on both side of our current string and recurse
            if(count > 0) {
                count -= 2;
                freqMap.put(ch, count);
                String newStr = ch + palStr + ch;
                genPalindromeHelper(len, newStr, resList, freqMap);
                // backtrack
                count += 2;
                freqMap.put(ch, count);
            }
        }

    }

    public List<String> generatePalindrome(String s) {
        HashMap<Character, Integer> freq = new HashMap<>();
        List<String> resList = new ArrayList<>();
        int odd =0;
        // get the frequency and check if valid palindrome possible
        for (int i =0; i<s.length(); i++) {
            freq.put(s.charAt(i), freq.getOrDefault(s.charAt(i), 0) + 1);
            if(freq.get(s.charAt(i)) % 2 == 1)
                odd++;
            else
                odd--;
        }
        // valid palindrome not possible
        if(odd > 1)
            return resList;
        // start with a empty string if the palindrome is of odd length then add the middle char as the starting string
        String palStr ="";
        for (Character ch : freq.keySet()) {
            if(freq.get(ch)%2 == 1) {
                // we found a middle char lets put it in start string &
                // also decrease the middle char count by 1 otherwise it
                // will incorrectly counted during recursion
                palStr+=ch;
                freq.put(ch, freq.get(ch) -1);
                break;
            }
        }
        // call the helper function to generate our list
        genPalindromeHelper(s.length(), palStr, resList, freq);

        return resList;
    }

    // LeetCode :: 395. Longest Substring with At Least K Repeating Characters
    // The idea is to recursively solve this problem. We find one char that has not been repeated k-times and split the
    // string at that char and recusively check if the sub string has a solution or not for example abbacad & k = 2
    // we split at 'c' and check if 'abba' has valid solution
    public int longestSubstring(String s, int k) {
        char [] map = new char[26];
        boolean allRepeatKTimes = true;
        // count the frequency
        for (int i =0; i < s.length(); i++) {
            map[s.charAt(i) -'a']++;
        }
        // check if all chars in this string has k repeated items
        for (int i = 0; i<26; i++) {
            if(map[i] > 0 && map[i] < k) {
                allRepeatKTimes = false;
                break;
            }
        }
        // all char has k repeated items we have our solution
        if (allRepeatKTimes)
            return s.length();
        int st = 0;
        int cur = 0;
        int max = 0;
        // find the char that appears less than k times and call solve everything before it recursively
        // for example abbacaaaadbb we spilt at c so that 'abba' can be checked the we split at d to check 'aaaa'
        for (int i = 0; i < s.length(); i++) {
            if (map[s.charAt(i) -'a'] < k) {
                max = Math.max(max, longestSubstring(s.substring(st,cur), k));
                st = cur + 1;
            }
            cur++;
        }
        // consider the case when the last part of the string has a result so we deal with the last part here
        // for example abcdefghoooooooo in this case when the loop above is over st points to the first 'o' so we need
        // to pass recusively check for subtring tarting with 'o'
        max = Math.max(max, longestSubstring(s.substring(st),k));
        return max;
    }

    // LeetCode :: 212. Word Search II
    // The idea is to use a Trie data struct to build the dictionary. We should not use a hashset to build the
    // dictionary. if we have a trie we can search the board and walk the trie from root if at any point we find
    // the word in the trie we can add that word in our result. We start with every char in board and the trie root
    // and search in the board and walk from the trie root to next nodes to see if this char sequence/ word is in trie.
    //
    // Note1: in the recursion we save the position of the current trie node so the next recursion can start from there,
    //        no need to start from root node inside recursion
    //
    // Note2: Its possible to find the same word in board from different start point. But we don't need a HashSet to
    //        store the unique word found. We can take advantage of the TrieNode and store a boolean for the current
    //        word found in 'alreadyFound'. If the word is alreadyFound lets not add it to our list
    public class TrieNode {
        char ch;
        TrieNode []child;
        boolean isWord;
        boolean alreadFound;
        public TrieNode (char node){
            ch = node;
            child = new TrieNode[26];
            isWord = false;
            alreadFound = false;
        }
    }
    public class Trie {
        TrieNode root;
        public Trie() {
            root = new TrieNode('@');
        }
        public void addWord (String word) {
            int i = 0;
            TrieNode node = root;
            while (i < word.length()) {
                char ch = word.charAt(i);
                // the char does not exist add it to trie
                if (node.child[ch -'a'] == null) {
                    node.child[ch -'a'] = new TrieNode(ch);
                }
                // this is the last char set the isWord
                if (i == word.length() -1)
                    node.child[ch -'a'].isWord = true;
                // move to the next node in trie
                node = node.child[ch-'a'];
                i++;
            }
        }
        public TrieNode walk (TrieNode node, char ch) {
            TrieNode next = null;
            if (node != null && ch != '*'
                    && node.child[ch -'a'] != null) {
                next = node.child[ch-'a'];
            }
            return next;
        }
    }

    private void findWordHelper (char [][]board, int r, int c, StringBuilder sb,
                                    List<String> rList, TrieNode node, Trie trie) {
        // invalid cases return
        if (r < 0 || c < 0 || r >= board.length
                || c >= board[0].length || board[r][c] == '*')
            return;

        // save the current trienode
        TrieNode next = trie.walk(node, board[r][c]);

        // no entry for current char position in a word in Trie dictionary,
        // so this is an invalid case
        if (next == null)
            return;

        // found the current char in trie so add to the current word (sb)
        // and store the index of stringbuilder for backtracking
        int sbIdx = sb.length();
        sb.append(board[r][c]);

        // The word is in dictionary, Its possible to add this word to our list.
        // But if the word is already Found lets not add it to our list this
        // reduces the need for hashtable to store unique entries
        if (next.isWord && !next.alreadFound) {
            rList.add(sb.toString());
            next.alreadFound = true;
        }

        // backtrack to find the result
        char temp = board[r][c];
        board[r][c] = '*';

        // search horizontally & vertically
        findWordHelper(board, r + 1, c, sb, rList, next, trie);
        findWordHelper(board, r - 1, c, sb, rList, next, trie);
        findWordHelper(board, r, c + 1, sb, rList, next, trie);
        findWordHelper(board, r, c - 1, sb, rList, next, trie);

        // revert the backtracking changes so far
        board[r][c] = temp;
        sb.delete(sbIdx, sb.length());
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> resList = new ArrayList<>();
        Trie trie = new Trie();

        for (String w : words) {
            trie.addWord(w);
        }
        TrieNode root = trie.root;

        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (root.child[board[i][j] - 'a'] == null)
                    continue;
                findWordHelper(board, i, j, new StringBuilder(), resList, root, trie);
                if (resList.size() == words.length) {
                    return resList;
                }

            }
        }


        return resList;
    }











}


