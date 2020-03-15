import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<ArrayList<Integer>> getSubset (ArrayList<Integer> set, int index) {
        ArrayList<ArrayList<Integer>> allSubset;
        if (index == set.size()) { // base case
            allSubset = new ArrayList<ArrayList<Integer>>();
            allSubset.add(new ArrayList<>());
            System.out.println(allSubset.size());

        } else {
            allSubset = getSubset(set,index + 1);
            System.out.println(allSubset.size());
            int item = set.get(index);
            ArrayList<ArrayList<Integer>> moreSet = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> subset : allSubset) {
                ArrayList<Integer> newSet = new ArrayList<>();
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



}


