import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SolutionsV2 {

    //LeetCode :: 220. Contains Duplicate III
    // The idea is to use a TreeSet which is a sorted set consider the case [10,15,18,24] k=3 t=3
    // if we use the sliding window and shrink it keeping right fixed we have a problem while shrinking we cover
    // [15,18,24] and [18,24] but never cover [15,18] as right is fixed. We have to keep right fixed so to solve
    // this we can use a TreeSet store all the value in sliding window in TreeSet (which is a sorted Set) Now we make
    // use of the floor & ceiling method TreeSet.
    // floor​(E e): This method returns the greatest element in this set less than or equal to the given element,
    // or null if there is no such element.
    // ceiling​(E e): This method returns the least element in this set greater than or equal to the given element,
    // or null if there is no such element.
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }

        TreeSet<Integer> values = new TreeSet<>();
        for (int ind = 0; ind < nums.length; ind++) {
            // search for values at most t greater than current value of nums[ind]
            Integer floor = values.floor(nums[ind] + t);
            // search for values at most t less than current value of nums[ind]
            Integer ceil = values.ceiling(nums[ind] - t);
            // check if found value in the range if yes return true
            System.out.println(nums[ind] +" "+ t + " "+ k);
            if ((floor != null && floor >= nums[ind])
                    || (ceil != null && ceil <= nums[ind])) {
                return true;
            }
            // add the current value to the TreeSet
            values.add(nums[ind]);
            // The sliding window boundary has reached remove the leftmost item
            if (ind >= k) {
                values.remove(nums[ind - k]);
            }
        }

        return false;

    }

    // same idea as above just rewritten in easy to read format
    public boolean containsNearbyAlmostDuplicateV2(int[] nums, int k, int t) {
        if (k <= 0) {
            return false;
        }
        TreeSet<Long> sortedSet = new TreeSet<>();
        int right = 0;
        while (right < nums.length) {
            // search for values at most t greater than current value of
            Long floor = sortedSet.floor((long)nums[right] + t);
            // search for values at most t less than current value of
            Long ceil = sortedSet.ceiling((long)nums[right] - t);
            // check if found value in the range if yes return true
            if ((floor != null && floor >= nums[right]) ||
                    (ceil != null && ceil <= nums[right])) {
                return true;
            }
            sortedSet.add((long)nums[right]);
            System.out.println(nums[right] +" "+ sortedSet.size());
            // we have discovered k items so to move forward remove
            // the last item as we include the next item
            // The sliding window boundary has reached remove the leftmost item
            // leftmost item can be identified by right - k index
            if (right >= k)
                sortedSet.remove((long)nums[right -k]);
            right++;
        }
        return false;
    }
    // LeetCode :: 219. Contains Duplicate II
    // ******* This is a wrong solution **********
    // It will fail test cases like [1,2,2,2,4,14,19,20,2,7,8,] k = 4
    // We Cannot solve this problem without a "HashSet"
    // Check the version 2 solution which use a hashset to solve this
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        boolean isFound = false;
        if (k ==0)
            return false;

        int left = 0;
        int right = 0;
        while (right< nums.length) {
            if( left < right && Math.abs(right -left) <= Math.abs(k) &&
                    nums[left] == nums[right]){
                isFound = true;
                break;
            } else if (Math.abs(right -left) >  Math.abs(k)){
                while (left < right){
                    if(nums[left] == nums[right] &&
                            Math.abs(right -left) <= Math.abs(k))
                        return true;
                    left++;
                }
            } else {
                right++;
            }
        }
        return isFound;
    }
    // Using sliding window 1-Pass using a hashSet
    public boolean containsNearbyDuplicateV2(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++){
            if(i > k) set.remove(nums[i-k-1]);
            if(!set.add(nums[i])) return true;
        }
        return false;
    }
    // LeetCode :: 217. Contains Duplicate
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int n:nums){
            if(set.contains(n))
                return true;
            set.add(n);
        }
        return false;
    }


}
