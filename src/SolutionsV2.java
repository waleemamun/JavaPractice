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

    // LeetCode :: 274. H-Index
    // The idea is to use a kind of bucket sort, so we have divide the numbers into buckets.
    // Each index is a bucket for the array, so any numbers in the array that is between 0 to n-1
    // falls into those 0 - n-1 bucket for all numbers >= n we have a bucket n+1th. So the hindex
    // will be distributed over these buckets. Now if sum the total count starting from n+1 bucket
    // the point where the sum >= bucket index is our solution, because at that point we have exactly
    // that numbers many number of items greater than the bukcet index.
    //
    // Note : The sum (cumulative from the highest bucket) at each bucket index tells us
    //        how many greater values are present than this number (bucket index).
    //
    public int hIndexUnSorted(int[] citations) {
        int hid = 0;
        int [] bucket  = new int[citations.length +1];
        for (int i = 0 ; i<citations.length ; i++) {
            if(citations[i] >= citations.length)
                bucket[citations.length]++;
            else
                bucket[citations[i]]++;
        }

        int count = 0;
        for (int i = bucket.length -1; i>=0; i--) {
                count+= bucket[i];
                if(count >= i) {
                    hid = i;
                    break;
                }
        }
        return hid;
    }

    //LeetCode :: 275. H-Index II The same problem as above now the array is given as sorted in increasing  order
    // The idea to use a binary search, when doing the search we have to consider the size of the array from mid
    // position so if the size of the array from mid position is equal to the value of mid we found our solution
    // but if the size is bigger we need to move to the right to find a bigger value for h-index, the array is
    // sorted so on the right there will be one value which is equal to or greater than the desired h-index value.
    // We move to the left of the array if the len from mid is smaller than the citation[mid] value.
    public int hIndex(int[] citations) {
        int high = citations.length -1;
        int low = 0;
        int len = citations.length;
        while (low < high) {
            int mid = low + (high -low)/2;
            // at this point we have exactly same number of entries in the array which
            // are >= citation[mid] so this is our solution for h-index
            if (citations[mid] == (len - mid))
                return citations[mid];
            // len from mid is bigger so we can move to the right, there must exist one such number
                // between len & mid +1 for which we will found same number of entries in the array
            else if (citations[mid] < (len - mid))
                low = mid + 1;
            else
                high = mid -1;
        }
        return len - low;
    }

    //LeetCode :: 415. Add Strings (not submitted)
    public String addStrings(String num1, String num2) {
        int sum = 0;
        int carry = 0;
        int i = num1.length()-1;
        int j = num2.length()-1;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0) {
            sum = carry;
            sum += (i < 0) ? 0: num1.charAt(i) -'0';
            sum += (j < 0) ? 0: num2.charAt(j) - '0';
            carry = sum /10;
            sum %= 10;
            sb.append(sum);
            i--;
            j--;
        }
        return sb.reverse().toString();
    }

    // LeetCode :: 125. Valid Palindrome (not submitted)
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<s.length();i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch) || Character.isLetter(ch)) {
                sb.append(Character.toLowerCase(ch));
            }
        }
        int l = 0;
        int r = sb.length() -1;
        while (l<=r && sb.charAt(l) == sb.charAt(r)) {
            l++;
            r--;
        }
        if(l > r)
            return true;
        else
            return false;

    }

    //LeetCode :: 953. Verifying an Alien Dictionary (not submitted)

    public boolean isAlienSorted(String[] words, String order) {
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i =0; i< order.length(); i++) {
            map.put(order.charAt(i), i);
        }
        for (int i =  1; i< words.length; i++) {
            String first = words[i-1];
            String last = words[i];
            int f = 0;
            int l = 0;
            while (f < first.length() && l < last.length()) {
                int diff = map.get(first.charAt(f)) - map.get(last.charAt(l));
                if (diff == 0){
                    l++;
                    f++;
                } else if (diff > 0)
                    return false;
                else
                    break;

            }

        }
        return true;
    }

    // LeetCode :: 986. Interval List Intersections (not submitted)
    // The idea is to merge the two sorted interval list interval,
    // Note we dont need to actually merge the to another array but we can use similar idea
    // to merge and at each iteration find intersection points of the interval add them
    // to the result array and process the next shorter interval
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int i = 0;
        int j = 0;
        List<int[]> resList = new ArrayList<>();

        while(i < A.length && j < B.length) {
            // get the intersection of the intervals for the low we need
            // to pick the max and for the high we need to pick the min
            int low = Math.max(A[i][0], B[j][0]);
            int high = Math.min((A[i][1]),B[j][1]);
            // if the low point is smaller or equal to high point then there exist an interval
            if (low <= high)
                resList.add(new int[] {low,high});
            // find the smaller of the two interval based on their endpoint and process
            // the next item for that array cause the bigger interval can still cover the
            // next item in the array where the samller interval belongs to
            if(A[i][1] <= B[j][1])
                i++;
            else
                j++;

        }
        // interesting way to convert an List of 2D arrays to a 2D array
        return resList.toArray(new int[resList.size()][]);
    }

    // LeetCode :: 289. Game of Life
    // We want to do it in O(1) space notice that the values are currently 0 & 1 which requires just the first bit
    // This is an int array so we can easily save the next state in bit position 9 (using bit position 9 makes masking
    // easier). So if some thing become dead->alive in the next state the bits will be 0000000100000001
    // if it becomes alive to dead  or dead to dead no need to change anything in the 9 bit positon as
    // by default its zero
    // Note here we use the 9 th bit position to store the next state we could have use the 3rd bit position to
    // with mask = 0x3 newSetbit = 1 << shift & shift = 2
    public void gameOfLife(int[][] board) {
        // we use the neighbor array to get the adjacent neighbors this way code is less messy we dont need
        // to write the 8 adjacent neigbor
        int []neighbor = {-1,0,1};
        // 8 bit mask to get the current state
        int mask = 0xFF;
        // set the 9th bit position
        int shift = 8;
        int newSetbit = 1 << shift;
        for (int r = 0; r <board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {

                // get the neighbors
                int count = 0;
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (!(neighbor[i] == 0 && neighbor[j] == 0)) {
                            int  rowX = r + neighbor[i];
                            int  colX = c + neighbor[j];
                            if(!(rowX <0 || colX < 0 || rowX >= board.length || colX >= board[0].length)
                                    && (board[rowX][colX] & mask) == 1) {
                                count++;
                            }
                        }
                    }
                }
                // we only need to update if in the next state some thing becomes alive by default
                // next state is zero or dead so we only handle alive cases
                if (count == 3 || (count == 2 && (board[r][c] & mask) == 1))
                    board[r][c] = board[r][c] | newSetbit;

            }
        }
        for (int r = 0; r <board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] >>>= shift;
            }
        }

    }



    }
