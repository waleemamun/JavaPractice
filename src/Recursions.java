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

    public int search(int[] nums, int target) {
        return rotatedBinSearch(nums,0, nums.length -1, target);
    }

}


