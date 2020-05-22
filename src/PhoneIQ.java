import sun.jvm.hotspot.utilities.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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




}
