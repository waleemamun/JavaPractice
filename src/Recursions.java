import java.lang.reflect.Array;
import java.util.ArrayList;

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

}
