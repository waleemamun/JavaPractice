import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;
import sun.jvm.hotspot.utilities.Interval;

import java.util.*;

public class PhoneIQ {
    /*
     * FB PHIQ
     * Let us say that we already have an Api drawPoint(int x, int y),
     * which can draw a point at position(x,y) on screen.
     * I would like you to implement drawCircle(int a, int b, int r)
     * which should draw a circle whose center is (a,b) and radius is r
     * */
    // based on: x = a + r * cos t; y = b + r * sin t
    public static void drawPoint(int x, int y) {
        // dummy function
    }
    public static void drawCircle(int a, int b, int r)
    {
        for (int angle = 0; angle < 360; ++angle)
        {
            int x = (int)(a + (r * Math.cos(angle)));
            int y = (int)(b + (r * Math.sin(angle)));
            drawPoint(x, y);
        }
    }
    // based on: r*r = (x-a)*(x-a) + (y-b)*(y-b)
    public static void drawCircle2(int a, int b, int r) {
	    int r_sqr = r * r;
        for (int x = 0; x <= r; ++x)
        {
            int y = (int)(Math.sqrt(r_sqr - x*x));
            drawPoint(a + x, b + y);
            drawPoint(a + x, b - y);
            drawPoint(a - x, b + y);
            drawPoint(a - x, b - y);
        }
    }
    // Given two arrays with equal length we need to find if the first array can be converted
    // to second array by any number of subarray reversal of the 2nd array
    public static boolean equalArrays(int []nums1, int []nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums1.length; i++) {
            int count = map.getOrDefault(nums1[i], 0);
            map.put(nums1[i],count +1);
        }
        for (int i = 0 ; i < nums2.length; i++) {
            if(map.containsKey(nums2[i])) {
                int count = map.get(nums2[i]);
                if (count == 0)
                    return false;
                map.put(nums2[i],count -1);

            } else
                return false;

        }
        return true;
    }
    boolean areTheyEqual(int[] array_a, int[] array_b) {
        // Write your code here
        if (array_a.length != array_b.length)
            return false;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i<array_a.length;i++) {
            int freq = map.getOrDefault(array_a[i], 0);
            map.put(array_a[i], freq +1);
        }
        for (int i = 0; i < array_b.length; i++) {
            if (map.containsKey(array_b[i])){
                int count = map.get(array_b[i]);
                if (count == 0)
                    return false;
                map.put(array_b[i],count-1);
            }
        }
        return true;
    }


    /**
     * For a given list of integers and integer K, find the number of non-empty subsets S such that min(S) + max(S) <= K
     * Example 1:
     *
     * nums = [2, 4, 5, 7]
     * k = 8
     * Output: 5
     * Explanation: [2], [4], [2, 4], [2, 4, 5], [2, 5]
     * The idea is to not generate any subset. Note subset of sorted array is the same as unsorted array
     * in our case if we sort the array first then it will be easier to process the min max in the subsets
     * So first sort the array. Now we use the idea similar to twoSum problems where we use two pointer left
     * & right and try to find the two poistion for which the nums[left] + nums[right] <=k , if we find such a
     * point then we calc the subset in between nums[left](inclusive) & nums[right] (inclusive).
     * The reason for using the count += 1 << (hi - lo); is exlained by the example below
     * [1,2,3, 9] with k = 10
     * If you went ahead and built the subsets you would have the following case:
     * When you get the 1:
     * With min 1: [1] (just 1 subarray)
     * When you get the 2:
     * With min 1: [1],[1,2] (2 here)
     * With min 2:[2] (1 here)
     * When you get the 3:
     * With min 1: [1],[1,2],[1,3],[1,2,3] (4 here, 2 * 2, notice we keep the same arrays from before and actually
     *                                      double the total on this case)
     * With min 2: [2],[2,3] (Again, we double the amount that were starting in 2 from the previous step)
     * With min 3: [3] (1 here, only added because 3+3 < 10)
     * When you get the 9:
     * Only double the amount with min 1, because you cannot create any other subset other than the ones with min 1.
     *
     * So, since the array is sorted, we know that we will have 2^(j-i) total subsets having a minimum number of i
     * (because we will double it for each of the indexes in between)
     * */
    public static int countSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        for (int lo = 0, hi = nums.length - 1; lo <= hi; ) {
            if (nums[lo] + nums[hi] > k) {
                hi--;
            } else {
                count += 1 << (hi - lo);
                lo++;
            }
        }
        return count;
    }

    // count the number of pair sum in an unsorted array where duplicates number are possible
    // The idea is to have the frequency count of all the numbers
    // Next when we do one pass on the array we lookup if the target number is in the map if its there we
    // calc the count for the pair. If the entries in the pair is unique then we mupltiply their freq count
    // If the number in pair is same then we calc nc2 using n*(n-1)/2
    public int numberOfWays(int[] arr, int k){
        HashMap<Integer,Integer> map = new HashMap<>();

        int count = 0;
        for (int i =0 ;i<arr.length;i++){
            int freq = map.getOrDefault(arr[i], 0);
            map.put(arr[i],freq +1);
        }

        for (int i = 0; i<arr.length;i++) {
            int target = k-arr[i];
            if(map.containsKey(target)) {
                if(target == arr[i]) {
                    int n = map.get(target);
                    n = n * (n - 1)/2;
                    count += n;
                } else {
                    count += map.get(target) * map.get(arr[i]);
                    map.put(arr[i],0);
                }
                map.put(target,0);
            }
        }
        return count;
    }
    // Another simpler version would be to do this twice count the numbers and return the result divided by two
    // one case needs to be handled where pair is (arr[i], arr[i]) we do not need to calc this hence we need to
    // decrement the count
    public int numberOfWaysV2(int[] arr, int k){
        HashMap<Integer,Integer> map = new HashMap<>();

        int count = 0;
        for (int i =0 ;i<arr.length;i++){
            int freq = map.getOrDefault(arr[i], 0);
            map.put(arr[i],freq +1);
        }

        for (int i = 0; i<arr.length;i++) {
            int target = k-arr[i];
            if(map.containsKey(target)) {
                count += map.get(target);

                if(arr[i]== target)
                    count--;
            }
        }
        return count/2;
    }

    // Find the contigious subarray len where starting or ending with arr[i] as the max
    int[] countSubarrays(int[] arr) {

        int []left = new int [arr.length]; // stores the max leftMost position spanned for each i
        int []right = new int [arr.length]; // stores the max rightMost position spanned for each i
        left[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            int leftMost = i -1;
            // lets find such leftMost pos for i so that the leftMost bar >= bar i
            while (leftMost >= 0 &&
                    arr[leftMost] <= arr[i]){
                // note we are using the left arrays values to jump to the right
                // to find the leftMost this way er can iterate very fast
                leftMost = left[leftMost]  - 1;
            }

            left[i] = leftMost + 1;

        }

        right[arr.length -1] = arr.length -1;
        for (int i = arr.length -2 ; i >= 0; i--){
            int rightMost = i +1;
            // lets find such rightMost pos for i so that the rightMost bar >= bar i
            while (rightMost < arr.length &&
                    arr[rightMost] <= arr[i]){
                // note we are using the right arrays values to jump to the right
                // to find the rightMost this way er can iterate very fast
                rightMost = right[rightMost] + 1;
            }
            // update the right array with rightMost for ith position so that for some
            // other i on the left we dont need to recalculate & jump faster
            right[i] = rightMost -1;

        }
        int max = Integer.MIN_VALUE;
        // now find the max are covered using the left & right array to find the
        // width covered by each position i, height will be the heights[i]
        int res[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++){
            res[i] = right[i] - left[i] +1;
        }

        return res;
    }


    public String rotationalCipher(String input, int rotationFactor) {
        char [] chStr = input.toCharArray();
        for (int i = 0; i < chStr.length; i++) {
            if(chStr[i] >= 'a' && chStr[i] <='z') {
                int rotate = rotationFactor % 26;
                chStr[i] = (char)(((chStr[i] - 'a' + rotate) % 26) + 'a');
            } else if (chStr[i] >= 'A' && chStr[i] <='Z') {
                int rotate = rotationFactor % 26;
                chStr[i] = (char)(((chStr[i] - 'A' + rotate) % 26) + 'A');
            } else if (chStr[i] >= '0' && chStr[i] <='9'){
                int rotate = rotationFactor % 10;
                chStr[i] = (char)(((chStr[i] - '0' + rotate) % 10) + '0');
            }

        }

        return new String(chStr);
    }

    // The idea is to hash the pair from two string for example if two strings are abkcd & ackbd, when we find mismatch
    // say for pos1 we have b & c. So we hash three entries b, c & most importantly the reverse pair of (b,c) which is
    // (c,b), so when we reach position 3 we lookup the pair (c,b) its in the hash table that tells us that (b,c) pair
    // was previously seen so we can increase our count to 2. We also put b & c separately in the hash because its
    // possible that after a swap we can only match one value for example abcdek & abcdkm here swapping k with m
    // increase the value to 1
    //
    int matchingPairs(String s, String t) {
        // Write your code here
        int match = 0;
        HashMap <Integer,Integer> pairMap = new HashMap<>();
        int max = 0;
        for (int i =0; i<s.length();i++) {
            if (s.charAt(i) == t.charAt(i)){
                match++;
            } else {
                // lookup pair like (b,c)
                int lookUpPairKey = (int)((int)s.charAt(i) * Math.pow(101,1) + (int)t.charAt(i)* Math.pow(101,2));
                max = Math.max(max, pairMap.getOrDefault((int)s.charAt(i),0));
                max = Math.max(max, pairMap.getOrDefault((int)t.charAt(i),0));
                max = Math.max(max, pairMap.getOrDefault(lookUpPairKey,0));
                // encode reverse pair (c,b)
                int newPairKey = (int)((int)s.charAt(i) * Math.pow(101,2) + (int)t.charAt(i)* Math.pow(101,1));
                pairMap.put(newPairKey, 2);
                pairMap.put((int)t.charAt(i),1);
                pairMap.put((int)s.charAt(i),1);
            }
        }
        if (match == s.length())
            return match -2;
        match += max;
        return match;
    }

    public char getOpposit(char ch) {
        if (ch ==')')
            return '(';
        else if (ch == '}')
            return '{';
        else if (ch == ']')
            return '[';
        else
            return 'a';
    }

    boolean isBalanced(String s) {
        // Write your code here
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[' ){
                stack.push(ch);
            } else {
                if (stack.empty()) {
                    return false;
                }
                char popChar = stack.pop();
                if(popChar != getOpposit(ch))
                    return false;
            }
        }
        if (!stack.empty())
            return false;

        return true;
    }




    }
