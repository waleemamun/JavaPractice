import java.util.*;

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

    // LeetCode :: 227. Basic Calculator II (not submitted)
    // this works only if the signs are + - * /
    // if more operator like brackets & power are introduced we have to use
    // infix to postfix conversion using stack, then use postfix to generate solution
    public int calculate(String s) {
        if (s == null) return 0;
        s = s.trim().replaceAll(" +", "");
        int length = s.length();

        int res = 0;
        long preVal = 0; // initial preVal is 0
        char sign = '+'; // initial sign is +
        int i = 0;
        while (i < length) {
            long curVal = 0;
            while (i < length && (int)s.charAt(i) <= 57 && (int)s.charAt(i) >= 48) { // int
                curVal = curVal*10 + (s.charAt(i) - '0');
                i++;
            }
            if (sign == '+') {
                res += preVal;  // update res
                preVal = curVal;
            } else if (sign == '-') {
                res += preVal;  // update res
                preVal = -curVal;
            } else if (sign == '*') {
                preVal = preVal * curVal; // not update res, combine preVal & curVal and keep loop
            } else if (sign == '/') {
                preVal = preVal / curVal; // not update res, combine preVal & curVal and keep loop
            }
            if (i < length) { // getting new sign
                sign = s.charAt(i);
                i++;
            }
        }
        res += preVal;
        return res;
    }

    // LeetCode :: 228. Summary Ranges

    public List<String> summaryRanges(int[] nums) {
        List<String> strList = new ArrayList<>();
        if (nums.length == 0)
            return strList;
        StringBuilder sb = new StringBuilder();
        sb.append(nums[0]);
        int lastVal = nums[0];
        for (int i =1; i< nums.length; i++) {
            if (nums[i] == (nums[i-1] +1)) {
                lastVal = nums[i];
            } else {
                if ( Integer.parseInt(sb.toString()) > lastVal || lastVal == nums[0]){
                    strList.add(sb.toString());
                }
                else {
                    sb.append("->");
                    sb.append(lastVal);
                    strList.add(sb.toString());
                }
                sb = new StringBuilder();
                sb.append(nums[i]);
            }
        }
        if ( Integer.parseInt(sb.toString()) > lastVal || lastVal == nums[0])
            strList.add(sb.toString());
        else {
            sb.append("->");
            sb.append(lastVal);
            strList.add(sb.toString());
        }
        return strList;
    }

    // LeetCode :: 229. Majority Element II
    // This uses the same majority Algorithm used in the AdnanAziz (one majority item covering more than half the array)
    // Known as the Boyer-Moore Majority Vote Algorithm
    // Here a modified version of the alog is used with two majority items
    // Here we have two majority candidate we select two candidates in the first pass and in the second pass verify
    // if they are actual majority
    // Check out this explanation here :
    // https://leetcode.com/problems/majority-element-ii/discuss/63537/My-understanding-of-Boyer-Moore-Majority-Vote
    public List<Integer> majorityElementTwoMajority(int[] nums) {
        List<Integer> elemList = new ArrayList<>();
        int candidate1 = 0;
        int candidate2 = 0;
        int count1 = 0;
        int count2 = 0;

        for (int n : nums) {
            if(candidate1 == n) {
                count1++;
            } else if (candidate2 == n) {
                count2++;
            } else if(count1 == 0) {
                candidate1 = n;
                count1 = 1;
            } else if(count2 == 0) {
                candidate2 = n;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        // verify if our candidates are majority
        count1 =0;
        count2 =0;
        for (int n: nums) {
            if (candidate1 == n){
                count1++;
            } else if (candidate2 == n){
                count2++;
            }

        }
        if (count1 > nums.length/3)
            elemList.add(candidate1);
        if (count2 > nums.length/3)
            elemList.add(candidate2);
        return elemList;

    }

    // LeetCode :: 169. Majority Element
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        for (int n : nums) {
            if (candidate == n) {
                count++;
            } else if (count == 0) {
                candidate = n;
                count = 1;
            } else {
                count--;
            }
        }
        count = 0;
        for (int n : nums) {
            if (candidate ==n)
                count++;
        }
        if (count > nums.length/2)
            return candidate;
        else
            return Integer.MIN_VALUE;

    }

    // Leetcode :: 238. Product of Array Except Self
    // create a prefix & suffix array prefix array hold the product of all prefix for pos i & suffix array
    // does it for suffixes.  for example 1,2,3,4, 5
    // prefix   1  1  2 6 24
    // orig     1  2  3 4  5
    // suffix 120 60 20 5  1
    // so we need to build both the suffix & prefix array and then multiply output(i) = prefix(i) * suffix(i)
    // But if we look closely we can reuse output array first as our prefix array & then as our suffix array
    // This clever trick would make this O(1) space
    public int[] productExceptSelf(int[] nums) {
        int []output = new int[nums.length];
        output[0] = 1;
        for (int i =1 ; i < nums.length ; i++) {
            output[i] = output[i-1] * nums[i-1];
        }
        int lastProd = 1;
        for (int i = nums.length -2; i>=0 ; i--){
            int tmp = lastProd * nums[i+1];
            output[i] *= tmp;
            lastProd = tmp;
        }
        return output;
    }

    //LeetCode :: 242. Valid Anagram
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length())
            return false;
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character c: s.toCharArray()){
            int count = map.getOrDefault(c, 0);
            map.put(c,count+1);
        }
        for (Character c: t.toCharArray()) {
            int count = map.getOrDefault(c, -10);
            if (count <= 0)
                return false;
            map.put(c, count -1);
        }
        return true;
    }

    // LeetCode :: 438. Find All Anagrams in a String
    // We are given as search string & pattern string we need to find the anagram of pattern in search
    // We will use a generic sliding window for this.
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> resList = new ArrayList<>();
        if (s.length() ==0 || p.length() ==0) {
            return resList;
        }
        // Create the frequency for the pattern string
        HashMap<Character,Integer> map = new HashMap<>();
        for (int i =0; i< p.length(); i++) {
            int count = map.getOrDefault(p.charAt(i),0);
            map.put(p.charAt(i), count+1);
        }
        // get the total counter, this indicates we have found desired number of entries in of pattern
        // in the search string
        int totCount = p.length();
        // reset the window counters
        int left = 0;
        int right = 0;

        while (right < s.length()) {

            char ch = s.charAt(right);
            // increase check if we found a char that contains in the pattern
            if(map.containsKey(ch)) {
                int count = map.get(ch);
                map.put(ch, count -1);
                // found a char in pattern lets check if the char can
                // be used to lower the totCount condition
                if(count > 0)
                    totCount--;
            }
            // reached the condition the sliding window contains all the char in pattern now.
            // Lets try to move the left side of the window to shrink it.
            while (totCount == 0) {
                char tempCh = s.charAt(left);
                if(map.containsKey(tempCh)) {
                    int count = map.get(tempCh);
                    map.put(tempCh, count +1);
                    // we have discard all we can on the left
                    if (count >= 0) {
                        totCount++;
                    }
                }
                // update the solution
                if (right - left +1 == p.length()) {
                    resList.add(left);
                }
                // move to left
                left++;
            }
            // move to right
            right++;
        }

        return resList;
    }

    // LeetCode :: 567. Permutation in String
    // Using the same generic sliding window as the previous problem check 'findAnagrams'
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() ==0 || s2.length() ==0)
            return false;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            int count = map.getOrDefault(s1.charAt(i), 0);
            map.put(s1.charAt(i), count +1);
        }
        int totCount = s1.length();
        int left = 0;
        int right = 0;
        while (right < s2.length()) {
            char ch = s2.charAt(right);
            if (map.containsKey(ch)) {
                int count = map.get(ch);
                map.put(ch, count -1);
                if (count > 0)
                    totCount--;
            }
            while(totCount == 0) {
                char tempCh = s2.charAt(left);
                if (map.containsKey(tempCh)) {
                    int count = map.get(tempCh);
                    map.put(tempCh, count + 1);
                    if (count >= 0)
                        totCount++;
                }
                if (right - left + 1 == s1.length())
                    return true;
                left++;
            }
            right++;
        }
        return false;
    }



}
