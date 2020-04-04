import javafx.geometry.Pos;
import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Array;
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
    // and reverse rest of the array  from right of high pointer
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

    // 33. Search in Rotated Sorted Array
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

    // LeetCode 34:: Find First and Last Position of Element in Sorted Array
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

    // 35. Search Insert Position
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
    public void placeAllQueens() {
        int queen = 1;
        int [] columns = new int[8];
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

    // LeetCode :: 47 Permutation II with duplicate.
    // Use the sam logic as permutation backtrack algo.
    // To identify the duplicate use a boolean to keep track of each used postion in nums,
    // We only add to list if the position is not used before, we also skip the duplicates processing
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> resList = new ArrayList<>();
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

















}


