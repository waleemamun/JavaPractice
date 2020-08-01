import java.util.*;

public class SolutionsV2 {

    //LeetCode :: 220. Contains Duplicate III
    // The idea is to use a TreeSet which is a sorted set consider the case [10,15,18,24] k=3 t=3
    // if we use the sliding window and shrink it keeping right fixed we have a problem while shrinking we cover
    // [15,18,24] and [18,24] but never cover [15,18] as right is fixed. We cannot use a left shrinking sliding
    // window here rather use a fixed sliding window of k-Size. WE also have to lookup values in the range of +t & -t
    // from current val.So the perfect candiate would be a TreeSet. We have to keep right fixed so to solve
    // this we can use a TreeSet store all the value in sliding window in TreeSet (which is a sorted Set) Now we make
    // use of the floor & ceiling method TreeSet.
    // floor​(E e): This method returns the greatest element in this set less than or equal to the given element,
    // or null if there is no such element.
    // ceiling​(E e): This method returns the least element in this set greater than or equal to the given element,
    // or null if there is no such element.
    // Check the version2 its more easy to read
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
    // The set size can be atmost k+1 where k = j-1 for example
    // 3-0 = 3 = k so set size atmost is 4 having (0 , 1, 2, 3)
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

    public boolean containsDuplicateV2(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int n:nums){
            if(!set.add(n))
                return true;
        }
        return false;
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

    // LeetCode :: 415. Add Strings
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
        if (carry != 0)
            sb.append(carry);
        return sb.reverse().toString();
    }

    // LeetCode :: 67. Add Binary
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        int sum = 0;
        StringBuilder sb = new StringBuilder ();
        while(i >= 0 || j >= 0){
            sum = carry;
            sum += i >= 0  ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) -'0' : 0;
            carry = sum / 2;
            sum = sum % 2;
            sb.append(sum);
            i--;
            j--;
        }

        if (carry != 0)
            sb.append(carry);
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
    public boolean isPalindromeOnePass(String s) {
        int l = 0;
        int r = s.length()-1;
        while (l <= r) {
            char lch = s.charAt(l);
            char rch = s.charAt(r);
            System.out.println(lch +" " + rch);
            if(!Character.isLetter(lch) && !Character.isDigit(lch))
                l++;
            else if (!Character.isLetter(rch) && !Character.isDigit(rch))
                r--;
            else {
                if(Character.toLowerCase(lch) == Character.toLowerCase(rch)) {
                    l++;
                    r--;
                } else
                    return false;
            }

        }
        return true;
    }

    //LeetCode :: 953. Verifying an Alien Dictionary

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
            if (first.length() > last.length())
                return false;

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

    // LeetCode :: 189. Rotate Array
    // We need to use two interesting trick here first to find how many times to rotate we can use mod to get the
    // actual rotation. Say 2 rotation makes 1 2 3 4 5 to 4 5 | 1 2 3
    // Second wwe can use the reversal to solve this problem in an array for 1 2 3 4 5 we reverse it full to
    // 5 4 3 2 1 then we reverse last 2 (for rotation of 2) so it becomes 5 4 3 | 1 2 then we rotate the 1st part
    // so we get 3 4 5 | 1 2
    private void reverseArr(int []nums, int low, int high) {
        while (low < high) {
            int tmp = nums[low];
            nums[low] = nums [high];
            nums[high] = tmp;
            low++;
            high--;
        }
    }

    public void rotate(int[] nums, int k) {
        int rot = k % nums.length;
        if (rot == 0)
            return;
        // reverse the whole array
        reverseArr(nums, 0, nums.length -1);
        int low = rot;
        // reverse the last part
        reverseArr(nums, low, nums.length -1);
        // reverse the first part
        reverseArr(nums, 0, low -1);
    }

    // LeetCode :: 973. K Closest Points to Origin
    private class Points{
        double dst;
        int x;
        int y;
        public Points(double dst, int x , int y) {
            this.dst = dst;
            this.x = x;
            this.y = y;
        }
    }

    private int partitionQSList(ArrayList <Points> P, int left, int right){
        int pivot = right;
        double pivotVal = P.get(right).dst;
        int i =left;
        int j = left;
        while (i < right) {
            if (P.get(i).dst - pivotVal < Double.MIN_VALUE) {
                Collections.swap(P,j,i);
                j++;
            }
            i++;
        }
        Collections.swap(P,j,right);
        return j;

    }
    private void qSelectList(ArrayList<Points> P, int K, int start, int end) {
        int pivot = partitionQSList(P, start, end);
        if (pivot == K)
            return;
        else if (pivot < K)
            qSelectList(P,K, pivot +1 ,end);
        if(pivot > K)
            qSelectList(P,K,start, pivot -1);
    }

    public int[][] kClosest2(int[][] points, int K) {
        ArrayList<Points> pointList = new ArrayList<>();
        int [][]resPoint = new int[K][2];
        for (int i =0; i<points.length; i++) {
            double dst =  (Math.sqrt(points[i][0] * points[i][0] + points[i][1]*points[i][1]));
            Points ps = new Points(dst, points[i][0], points[i][1]);
            pointList.add(ps);
        }
        qSelectList(pointList, K-1, 0, pointList.size() -1);
        for (int i=0; i < K && i < pointList.size(); i++){
            resPoint[i][0] = pointList.get(i).x;
            resPoint[i][1] = pointList.get(i).y;
        }

        return resPoint;
    }

    private boolean compareDst (int x1, int y1, int x2, int y2) {
        double dst1 = Math.sqrt(x1*x1 + y1*y1);
        double dst2 = Math.sqrt(x2*x2 + y2*y2);
        if (dst1 < dst2)
            return true;
        else
            return false;
    }
    private int partitionQS(int [][]P, int left, int right) {
        int i = left;
        int j = left;
        while (i < right) {
            if (compareDst(P[i][0],P[i][1],P[right][0],P[right][1])) {
                int [] temp = P[j];
                P[j] = P[i];
                P[i] = temp;
                j++;
            }
            i++;

        }
        int []temp = P[right];
        P[right] = P[j];
        P[j] = temp;
        return j;
    }
    private void qSelect (int [][]points, int K, int start, int end) {
        int pivot = partitionQS(points, start, end);
        if (pivot == K)
            return;
        else if(pivot < K)
            qSelect(points, K, pivot +1, end);
        else
            qSelect(points, K, start, pivot -1);


    }
    public int[][] kClosest(int[][] points, int K) {
        qSelect(points,K-1, 0, points.length -1);
        return Arrays.copyOfRange(points, 0, K);
    }


    // LeetCode :: 560. Subarray Sum Equals K
    // This problem is very interesting. We cannot use a sliding window as numbers can be negative.
    // One observation is if we have cumulative Sum for each i position in nums[i] in sums[i] then
    // for any j>i the sum between j & i  can be calc using sums[j-i] = sum[j] - sum[i-1]
    // So now if we have sum array we can look up the diff k between all its element which can be done in O(n^2)
    // but if we look closer when we have this sum array this problem is looking for a target sum in an array which
    // is basically the unsorted two sum problem. So we can use this approach of storing all the current sum in a
    // HashMap, and look up if the (currentSum - k) matches any previously stored sum in the HashTable if so the
    // we found sum[j-i] == k so update oru result
    // for example 3 4 3  4  3  and k =7
    //        sum  3 7 10 14 17
    // now as we store  (3,1) (7,1) in the hastable when we reach 10, we look 10 -7 == 3  so for 10 there exist
    // a sum in the array equals to k (7)
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // WE init map with (0,1) for values that sums to k from index 0,
        map.put(0,1);
        int curSum = 0;
        int result = 0;
        for (int i=0; i<nums.length; i++) {
            curSum += nums[i];
            if (map.containsKey(curSum - k)) {
                result+= map.get(curSum-k);
            }
            map.put(curSum, map.getOrDefault(curSum, 0) +1);
        }
        return result;
    }

    // LeetCode :: 325 Maximum Size Subarray Sum Equals k
    // The idea is to use same idea as the one above. We calc a running sum for all the arr index.
    // for any j>i the sum between j & i  can be calc using the running  sums[j-i] = sum[j] - sum[i-1]
    // We store the running sum in a map key and value is the index
    // if the running sum is not present. We have to check if the entry already
    // exists in the map or not if it exist we dont need to update the index  as we want to keep using the oldest index
    // Note we have to consider the i-1 postions running sum not i postion. So the subarray size can
    // be calculated  using j- (i-1). Hence we start with map entry (0,-1)
    // Consider the example of {5,10,-5,-10,15,30,-30} the result is 7 = (6- (-1))
    public int maxSubArrayLen(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int currSum = 0;
        map.put(0,-1);
        int maxSize = -1;
        for (int i = 0; i<nums.length; i++) {
            currSum+= nums[i];
            if (map.containsKey(currSum -k)) {
                maxSize = Math.max(maxSize, i - map.get(currSum -k));
            }
            // we use putIfAbsent cause we only update if the runningSum we observed is a new runningSum
            // if we already have the same runningSum we want to use the oldest idx cause our goal is to
            // find the max size subarray
            map.putIfAbsent(currSum,i);
        }
        return maxSize;
    }

    // LeetCode :: 283. Move Zeroes
    public void moveZeroes(int[] nums) {
        int j =0;
        while ( j<nums.length && nums[j] != 0)
            j++;
        int i = j+1;
        while (i< nums.length) {
            if (nums[i]!= 0) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                j++;
            }
            i++;
        }
    }

    // LeetCode :: 791. Custom Sort String
    // The idea is to create a map of the String T and then construct a new string following the order of S.
    // To follow the order of S we scan S from left to right pick each item from S and get the count from the T map
    // and add them in the new String. Finally we add the remaining char In T which does not appear in S in
    // normal/natural sorted order
    public String customSortString(String S, String T) {
        int []map = new int[26];
        // build the map
        for (int i = 0; i < T.length(); i++) {
            char ch = T.charAt(i);
            map[ch-'a']++;
        }
        // Add the char using the S order
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            // add all the char in T using for this char in S.
            while(map[ch-'a'] != 0) {
                sb.append(ch);
                map[ch-'a']--;
            }
        }
        // Add rest of the char in T not appearing in S
        for (int i = 0; i < 26; i++) {
            while(map[i]!=0) {
                char ch = (char)('a' + i);
                sb.append(ch);
                map[i]--;
            }
        }
        return sb.toString();
    }

    // LeetCode :: 1004. Max Consecutive Ones III
    // Use a sliding window approach one catch is we need to look for K + 1 zeros to stop expanding
    // the right window hence at the end we need to check if we have covered the last window length
    public int longestOnes(int[] A, int K) {
        int desiredCount = K+1;
        int left = 0;
        int right = 0;
        int max = 0;
        while (right < A.length) {
            if (A[right] == 0) {
                if (desiredCount != 0)
                    desiredCount--;
            }

            while (desiredCount == 0) {
                if (A[left] == 0) {
                    desiredCount++;

                }
                max = Math.max(max, right -left);
                left++;
            }
            right++;
        }
        // check if the last window is covered as we check for k+1 zeros
        if(desiredCount != 0)
            max = Math.max(max,right-left);
        return max;

    }

    // LeetCode :: 636. Exclusive Time of Functions (not Submitted)
    public int[] exclusiveTime(int n, List<String> logs) {
        int []res = new int[n];
        Stack <Integer> fIdStack = new Stack<Integer>();
        String []str = logs.get(0).split(":");
        Integer prevTime = Integer.parseInt(str[2]);
        fIdStack.push(Integer.parseInt(str[0]));
        int i = 0;
        while (i < logs.size()) {
            str = logs.get(i).split(":");
            Integer fId = Integer.parseInt(str[0]);
            Integer curTime = Integer.parseInt(str[2]);
            if (str[1].equals("start")) {
                if (!fIdStack.empty())
                    res[fIdStack.peek()] += curTime - prevTime;
                fIdStack.push(fId);
                prevTime = curTime;

            } else {
                res[fIdStack.peek()] += curTime - prevTime + 1;
                fIdStack.pop();
                prevTime = curTime + 1;

            }
        }
        return res;

    }

    // LeetCode :: 227. Basic Calculator II (not submitted)
    // this works only if the signs are + - * /
    // if more operator like brackets & power are introduced we have to use
    // infix to postfix conversion using stack, then use postfix to generate solution
    // The version 1 is a stack implementation this version 2 is optimising the stack
    // implementation as we need store cumulative sum for + & - but got * & / we need to
    // keep calculating the sum
    public int calculateV2(String s) {
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

    public int calculate(String s) {
        if (s.length() ==0)
            return 0;

        int i = 0;
        int sum = 0;
        int val = 0;
        char sign = '+';
        Stack <Integer>  stack = new Stack<>();
        while (i < s.length()) {

            if (Character.isDigit(s.charAt(i))) {
                val = val*10 + s.charAt(i) -'0';
            }
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == s.length()-1) {
                if (sign == '+') {
                    stack.push(val);
                } else if (sign == '-') {
                    stack.push(-val);
                } else if (sign == '*') {
                    stack.push(stack.pop()*val);
                } else if (sign == '/'){
                    stack.push(stack.pop()/val);
                }
                val = 0;
                sign = s.charAt(i);

            }
            i++;
        }
        while (!stack.empty()){
            sum+= stack.pop();
        }
        return sum;

    }

    // LeetCode :: 205. Isomorphic Strings
    public boolean isIsomorphicv2(String s, String t) {
        HashMap<Character,Character> map1 = new HashMap<>();
        HashMap<Character,Character> map2 = new HashMap<>();
        int i = 0;
        while (i < s.length()){
            Character ch1 = map1.putIfAbsent(s.charAt(i),t.charAt(i));
            Character ch2 = map2.putIfAbsent(t.charAt(i),s.charAt(i));
            if ((ch1 != null && ch1 != t.charAt(i))
                    || (ch2!=null && ch2 != s.charAt(i)))
                return false;
            i++;
        }
        return true;
    }
    // The use of primitive type to create the character map improves the runtime performance significantly
    // although the Big O remains same
    public boolean isIsomorphic(String s, String t) {
        char [] map1 = new char[128];
        char [] map2 = new char[128];
        int i = 0;
        while (i < s.length()){
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);
            if (map1[ch1] == 0)
                map1[ch1] = ch2;
            if (map2[ch2] == 0)
                map2[ch2] = ch1;
            if (map1[ch1] != ch2 || map2[ch2] != ch1)
                return false;
            i++;


        }
        return true;
    }

    // LeetCode :: 239. Sliding Window Maximum (Hard)
    // This solutions is amortized O(n) as elements are removed and added to the queue only once
    // We build on the idea that if the newest value is better than the older value in queue we can
    // discard the older values, as we slide to the right  the older value becomes less important.
    // The idea is to maintain a Double Ended Queue (Deque) to store the most promising max values
    // so far. We also need to make sure the queue never gets bigger than K (window size). When we
    // store values in to the queue we store the most promising max value, so we take the current nums[i]
    // value and check if its bigger than the last (tail) value of the queue if yes we remove all value < nums[i]
    // from the tail until the we find a bigger value or the queue is empty. This process ensures that the
    // front value of the queue is always the maximum. So we can add the front value of the queue as to the
    // result list for the current window.
    // Note1 : we actually store the index of the array rather than the actual value, it helps us remove from
    // the front of the queue when we exceed the window size
    // Note2: In this case using a LinkedList for deque give better runtime in LeetCode may be because
    // expanding ArrayDeque is expensive.
    // Usually ArrayDeque gives better performance
    // Note3 : This concept is also known as monotonic Queue
    public int[] maxSlidingWindow(int[] nums, int k) {
        int [] res = new int [nums.length -k +1];
        // deque holds the most promising max values in the queue so far
        // Deque <Integer> deque = new ArrayDeque<>(); // not using this but usually ArrayDeque gives better performance
        Deque <Integer> deque = new LinkedList<>();
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            // remove from the front of the queue if the queue exceeds the window size
            // Note this loop will execute not more than once, so we can change the loop
            // to a if condition
            while(!deque.isEmpty() && deque.peek() < i -k +1){
                deque.removeFirst();
            }
            // We found a bigger value. Remove from the tail of the queue all smaller values
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.removeLast();
            }
            // add to the end of the queue
            deque.add(i);
            // We skipped the first k -2 items now for each increment of i we add the result to the result array
            // The front of the queue wil have the max item as per our algo so store it in the result
            // for this window
            if (i >= k-1)
                res[j++] = nums[deque.peekFirst()];

        }
        return res;
    }

    //LeetCode :: 739. Daily Temperatures
    // Using the monotonic queue to find the nearest bigger value.
    // Using a monotonic decreasing queue as we scan through the array a new item removes all smaller
    // item from the the queue while removing the smaller item we calc the distance between new item
    // and the popped item. This queue is a double ended queue and we pop item from the end not the
    // beginning
    // LinkedList performs better
    // Note : A nice read for Monotonic queue: https://medium.com/@gregsh9.5/monotonic-queue-notes-980a019d5793
    // https://medium.com/algorithms-and-leetcode/monotonic-queue-explained-with-leetcode-problems-7db7c530c1d6
    public int[] dailyTemperatures(int[] T) {
        int []result = new int[T.length];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i<T.length; i++) {
            // remove item from tail of the queue
            while (!deque.isEmpty() && T[deque.peekLast()] < T[i]) {
                result[deque.peekLast()] = i - deque.peekLast();
                deque.removeLast();
            }
            // add to the end of queue
            deque.add(i);
        }
        return result;

    }

    // This version is faster run time in leetcode but both this  & prev version uses a amortized O(n)
    // This uses the similar approach as the longest rectangle histogram problem we find the nearest
    // bigger item on the right and use that to find our result
    public int[] dailyTemperaturesV2(int[] T) {
        int []result = new int[T.length];
        int []right = new int [T.length];

        for (int i = T.length - 1; i >= 0; i--) {
            int j = i+1;
            while (j < T.length && T[j] <= T[i]) {
                j = right[j];
            }
            right[i] = j;
            result[i] = right[i] == T.length ? 0 : right[i] -i;
        }

        return result;

    }

    // LeetCode :: 496. Next Greater Element I
    // Using decreasing monotonic queue, adding only index to the Double Ended Queue
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if (nums2.length ==0) {
            Arrays.fill(nums1, -1);
            return nums1;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        int [] nearestElem = new int[nums2.length];
        Deque <Integer> deque = new LinkedList<>();
        for (int i = 0; i< nums2.length; i++) {
            map.put(nums2[i],i);
            nearestElem[i] =-1;
        }
        for (int i = 0; i < nums2.length; i++) {
            while (!deque.isEmpty() && nums2[deque.peekLast()] < nums2[i]) {
                nearestElem[deque.removeLast()] = nums2[i];
            }
            deque.addLast(i);
        }
        for (int i = 0; i< nums1.length; i++) {
            nums1[i] = nearestElem[map.get(nums1[i])];
        }
        return nums1;
    }
    // This uses the nearestElement algo as used in largestRectangleArea
    // It looks like montonic queue's performance is little slower than this approach
    // but monotonic queue & its runtime may be easy to explain
    public int[] nextGreaterElementV2(int[] nums1, int[] nums2) {
        if (nums2.length ==0) {
            Arrays.fill(nums1, -1);
            return nums1;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        int [] nearestElem = new int[nums2.length];
        for (int i = nums2.length -1; i>=0; i--) {
            map.put(nums2[i],i);
            int j = i+1;
            while (j < nums2.length && nums2[j] < nums2[i]) {
                j = nearestElem[j];
            }
            nearestElem[i] = j;
        }

        for (int i = 0; i< nums1.length; i++) {
            int idx = map.get(nums1[i]);
            if (nearestElem[idx] != nums2.length)
                nums1[i] = nums2[nearestElem[idx]];
            else
                nums1[i] = -1;
        }
        return nums1;
    }

    // LeetCode :: 456. 132 Pattern
    // The idea is to use a decreasing monotonic queue.
    // we start from the right and form a decreasing MQ. Sometime its easier to  build a MQ from right
    // In this case we need a 1 3 2 pattern so a decreasing MQ from right is a better choice as when we remove lower
    // values from the  queue the front will have the largest item prev will have the second largest item. The largest
    // item can thought of as aj at position j 2nd largest ak at pos k so for  any i pos on the left if ai < prev (ak)
    // we have our solution
    public boolean find132pattern(int[] nums) {

        Deque <Integer> deque = new LinkedList<>();
        int prev = Integer.MIN_VALUE;
        for (int i = nums.length -1; i >= 0; i--) {
            //  For this decreasing MQ ak is prev & queue front is aj so we check if ai (nums[i]) < ak(prev)
            if(prev > nums[i])
                return true;
            while (!deque.isEmpty() && nums[i] > deque.peekLast()) {
                prev = deque.removeLast();
            }
            deque.addLast(nums[i]);
        }
        return false;
    }

    // LeetCode :: 401. Binary Watch
    // Simply generate all the time hour * min & then put hour << 6 | min and count the bits for that
    // if count bits equals the num we have a our result add it to the list.
    // Note : Dont try to solve it recursively
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (Integer.bitCount((int) (i << 6 | j)) == num)
                    res.add(String.format("%d:%02d",i,j));
            }
        }
        return res;
    }

    // LeetCode :: 357. Count Numbers with Unique Digits
    // The idea is to use combinations. How many ways we can generate unique digits for n -digit
    // There are 10 possible digits 0-9 and the first number cannot be zero so there are 9 ways to get the first digit
    // the second digit can be created using 9 * 9 (cause out of 10 digits(0 -9) 1 is use by the first position) so
    // for the third digit ( 9 *9 * 8) so for k digit f(k) = 9 * 9 * 8 * 7 ... * (10 -k + 1) (for all k > 1).
    // So f(k) gives the count for k digit (for example if k = 3 it gives count for 101 to 999)
    // So we can use a dp approach to store all the prev f(i) count and to get the  f(k) we simply add the
    // prev f(i) to f(k)
    public int countNumbersWithUniqueDigits(int n) {
        int []uniqueCounts = new int[n +1];
        uniqueCounts[0] = 1;
        for (int i = 1; i <= n; i++) {
            uniqueCounts[i] = 9;
            for (int k = i; k > 1; k--) {
                uniqueCounts[i] *= (10 - k +1);
            }
            uniqueCounts[i]+= uniqueCounts[i-1];
        }
        return uniqueCounts[n];
    }

    // LeetCode :: 324. Wiggle Sort II
    // This is a very interesting problem the problem becomes difficult due to the requirement of O(1) space.
    // First we need to use quickSelect to partition the array into two equal half left half contains bigger & right
    // half contains smaller items, without the o(1) sapce requirement we could have picked one item from left half and
    // one item from right half and merge it to another array which is easy.
    // Now for the O(1) space require ment we have to use like a virtual addressing and the dutch national
    // flag pivoting using the median
    private int partition(int [] nums, int start, int end) {
        int pivotVal = nums[end];
        int i = start;
        int j = start;
        while (i< end) {
            if (nums[i] > pivotVal) {
                int tmp = nums[j];
                nums[j] = nums[i];
                nums[i] = tmp;
                j++;
            }
            i++;
        }
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
        return j;

    }

    private void quickselectKlargest(int[] nums, int k, int start, int end) {
        int pivot = partition(nums, start, end);
        if (pivot == k)
            return;
        else if (pivot < k)
            quickselectKlargest(nums, k, pivot + 1, end);
        else
            quickselectKlargest(nums, k , start, pivot -1);
    }
    private int convertIndex(int i, int n) {
        return (2*i + 1) % (n | 1);
    }
    private void swapArrIdx (int []nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public void wiggleSort(int[] nums) {
        int mid = (nums.length-1)/2 ;
        quickselectKlargest(nums, mid, 0, nums.length -1);
        int n = nums.length;

        int left = 0, i = 0, right = n - 1;
        int median = nums[mid];
        // dutch national flag pivoting based on the median
        //   (1) elements smaller than the 'median' are put into the last even slots
        //   (2) elements larger than the 'median' are put into the first odd slots
        //   (3) the medians are put into the remaining slots.
        while (i <= right) {
            if (nums[convertIndex(i,n)] > median) {
                swapArrIdx(nums, convertIndex(left++,n), convertIndex(i++,n));
            }
            else if (nums[convertIndex(i,n)] < median) {
                swapArrIdx(nums, convertIndex(right--,n), convertIndex(i,n));
            }
            else {
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));

    }






    }
