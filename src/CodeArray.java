
import java.util.HashMap;
import java.util.Hashtable;

public class CodeArray {

    // Leetcode 1
    public int[] twoSum(int[] nums, int target) {
        int [] elem = new int[2];
        elem[0]  = elem[1] = -1;
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i<nums.length; i++ ) {
            if (target == 2 * nums[i] && elem[0] < 0 ) {
                elem[0] = i;
                nums[i] = target*2;
            } else if (target == 2 * nums[i] && elem[0] >= 0) {
                elem[1] = i;
                return elem;
            }
        }
        for (int i = 0; i<nums.length; i++ ) {
            map.put(new Integer(nums[i]), new Integer(i));

        }
        for (int i = 0; i < nums.length; i++) {
            int reminder = target - nums[i];
            //System.out.println(nums[i]  +" "+ i +" " + target + " " + reminder);
            if (map.containsKey(reminder)) {
                int r = map.get(reminder) ;
                elem[0] = i<=r ? i:r;
                elem[1] = i>r ? i:r;
                return elem;
            }
        }
        return elem;
    }
    public int[] twoSumV2(int[] nums, int target) {
        int [] elem = new int[2];
        elem[0]  = elem[1] = -1;
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0; i<nums.length; i++ ) {
            map.put(new Integer(nums[i]), new Integer(i));
        }
        System.out.println("ola " + map.get(3));
        for (int i = 0; i < nums.length; i++) {
            int reminder = target - nums[i];
            System.out.println(nums[i]  +" "+ i +" " + target + " " + reminder);
            if (map.containsKey(reminder) && map.get(reminder) != i) {

                int r =  map.get(reminder);
                System.out.println(i +" "+ r);
                elem[0] = i<=r ? i:r;
                elem[1] = i>r ? i:r;
                return elem;
            }
        }
        return elem;
    }
    // We maintain a hash table for all the entries in the array Hash key is the
    // value of array position i and value is array index i. We walk through the array
    // to find target - nums[i] and look up in the hash table for key nums[i] if the entry is there
    // then we found our desired entries and if not we put the value in hash table and move forward.
    public int[] twoSum3(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /* This solution works only with sorted nums array for unsorted arrays
     *  use the hashmap solution*/

    public int [] twoSumWithSortedArray (int[] nums, int target) {
        int i = 0;
        int j = nums.length-1;
        while (i < j ) {
            if (nums[i] + nums[j] < target)
                i++;
            else if (nums[i] + nums[j] > target)
                j--;
            else
                return new int [] {nums[i],nums[j]};
        }
        return new int [] {0,0};

    }

    // LeetCode 3
    public int lengthOfLongestSubstringV2(String s) {
        Freuency [] freuency = new Freuency[128];
        int max_len = 0;
        int count = 0;
        int space_count = 0;

        String str = s;

        int pos = 0;

        for (int i=0; i< freuency.length;i++ ) {
            freuency[i] = new Freuency();
        }

        for (int i = 0;i <str.length(); i++) {
            pos  = str.charAt(i);
            freuency[pos].freq++;
            count++;
            /*System.out.println(" i " + i +" char "+ str.charAt(i)+ " Count " + count + " max_len " + max_len + " pos " + pos  + " freq " + freuency[pos].freq
                      + " lastpos "  + freuency[pos].lastPos );*/
            if (freuency[pos].freq > 1 ) {
                if (count > max_len ) {
                    max_len = count - 1;

                }
                count = 0;
                // i will increase at the end of loop
                i = freuency[pos].lastPos;
                // reset frequerncy
                for (int j =0 ;j< freuency.length; j++) {
                    freuency[j].freq = 0;
                    freuency[j].lastPos = 0;
                }
                continue;
            }
            freuency[pos].lastPos = i;
        }
        if (count >= max_len + 1)
            max_len = count;
        return max_len + space_count;

    }

    // LeetCode :: 3 Longest Substring Without Repeating Characters
    // Use Sliding window to solve the problem, This solution is easy to read
    public int lengthOfLongestSubstringV1(String s) {
        HashMap <Character,Integer> map= new HashMap<Character,Integer>();
        int maxLen = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            left = Math.max(left, map.getOrDefault(s.charAt(right),-2) + 1);
            map.put(s.charAt(right),right);
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;

    }

    // this is the best solutions
    // when solving string problem of this type we use a sliding window technique
    // we start with a minimal sliding window and keep increasing it
    // ie: (abcdefa)ghk <- a is detected twice now abcdef(aghk).
    // in this case we use a integer array as hashmap for 255 chars
    // i denotes left side and j denotes right side of the sliding window on each pass
    // we keep i in its position and increase j until we found a repeated char
    // we update i to be the max of the hash value and i itself
    // in the above example at 2nd a i would be max(0,1)/
    public int lengthOfLongestSubstring(String s) {
        int [] index = new int [128];
        int n = s.length();
        int j = 0;
        int max_len = 0;
        int i = 0;
        for (i =0; j < n; j++) {
            i = Math.max(index[s.charAt(j)],i);
            max_len = Math.max(max_len,j-i+1);
            index[s.charAt(j)]= j + 1 ;
        }
        return max_len;
    }

    public class Freuency {
        public int freq;
        public int lastPos;
    }

    // Leetcode :: 4. Median of Two Sorted Arrays (Hard)
    // Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
    public double findMedianSortedArraysV2(int[] nums1, int[] nums2) {


        if (nums1.length > nums2.length){
            return findMedianSortedArraysV2(nums2,nums1);
        }
        int x = nums1.length; // length of array x (nums1 here)
        int y = nums2.length; // length of array y (nums2 here)

        int start = 0;
        int end = x;

        while (start <= end) {
            int partitionX = (start + end)/2;
            int partitionY = (x+y+1)/2 - partitionX;

            // if partitionX has shifted so much to the left then use -Infinity
            // if partitionX has shifte so much to the right then us +Infinity
            // In both case the partition left or right of array X would be empty
            int maxLeftX = partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX-1];
            int minRightX = partitionX == x ? Integer.MAX_VALUE : nums1[partitionX];
            // if partitionY has shifted so much to the left then use -Infinity
            // if partitionY has shifte so much to the right then us +Infinity
            // In both case the partition left or right of array Y would be empty
            int maxLeftY = partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY-1];
            int minRightY = partitionY == y ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX ) {
                // we have partitioned the array in the right place
                // if total length is even get the avg of the value
                if ( (x+y)%2 == 0 ) {
                     return (double)((Math.max(maxLeftX,maxLeftY) + Math.min(minRightX,minRightY))/2.0);
                }
                else {
                    return Math.max(maxLeftX,maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                end = partitionX - 1;
            } else {
                start = partitionX + 1;
            }


        }
     throw new IllegalArgumentException();
    }
    // say we have tow arrays A and B, we have to partition them in such a way that
    // A[0].. A[i-1] | A[i]... A[m] and B[0]...B[j-1] | B[j]....B[n]
    //   leftA       |   right A         left B       |   right B
    // also  leftA.length + leftB.length == rightA.length + rightB.length or (rightA.length + rightB.length+1)
    // so i+j = m-i + n-j +1
    // or i+j = (m+n+1)/2
    // so j = (m+n+1)/2 - i; in the code i = partiionX and j = partitionY
    // the we binary search the smaller array to find a partion that will satisfy that pariton is equal size on both side
    public double findMedianSortedArrays(int input1[], int input2[]) {
        //if input1 length is greater than switch them so that input1 is smaller than input2.
        if (input1.length > input2.length) {
            return findMedianSortedArrays(input2, input1);
        }
        int x = input1.length;
        int y = input2.length;

        int low = 0;
        int high = x;
        while (low <= high) {
            int partitionX = (low + high)/2;
            int partitionY = (x + y + 1)/2 - partitionX;

            //if partitionX is 0 it means nothing is there on left side. Use -INF for maxLeftX
            //if partitionX is length of input then there is nothing on right side. Use +INF for minRightX
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : input1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : input1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : input2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : input2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                //We have partitioned array at correct place
                // Now get max of left elements and min of right elements to get the median in case of even length combined array size
                // or get max of left for odd length combined array size.
                if ((x + y) % 2 == 0) {
                    return ((double)Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY))/2;
                } else {
                    return (double)Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) { //we are too far on right side for partitionX. Go on left side.
                high = partitionX - 1;
            } else { //we are too far on left side for partitionX. Go on right side.
                low = partitionX + 1;
            }
        }

        //Only we we can come here is if input arrays were not sorted. Throw in that scenario.
        throw new IllegalArgumentException();
    }
    // from each position i and i + 1 we have to get the palindrome len considering i or i+1 as middle
    // get the max len palindrome and then if its getter than prev max len update the maxlen and the indices
    // The reason behind choosing i & i+1 is considering even & odd len palindrom
    public String longestPalindrome(String s) {
        int maxLen = Integer.MIN_VALUE;
        int start = 0;
        int end = 0;
        int len = 0;
        int len1 = 0;
        int len2 = 0;

        if(s.length() == 0) {
            return "";
        }

        for (int i = 0; i < s.length(); i++) {
            len1 = palindromeLen(s, i, i); // odd len palindrome  at i
            // even len palindrome at i so we consider i to left & i+1 to right
            len2 = palindromeLen(s, i, i+1);
            len = Math.max(len1,len2);
            if (len > maxLen) {
                maxLen = len;
                start = i - (len-1)/2;
                end = i + len/2;
            }


        }
        return s.substring(start,end+1);

    }
    public int palindromeLen(String s, int l , int r) {
        int len = 0;
        while( l>=0 && r<s.length()
                && s.charAt(l) == s.charAt(r)) {
            len = r-l +1;
            l--;
            r++;
        }
        return len;

    }

    public String longestPalindromeV2(String s) {
        int maxLen = Integer.MIN_VALUE;
        int start = 0;
        int end = 0;
        int count = 0;
        boolean first = true;
        boolean is_repeat = false;
        boolean isPalindrome = false;

        if(s.length() == 0) {
            return "";
        }
        if (s.length() == 1 || s.length() == 2) {
            return s;
        }
        int midL = 0;
        int midR = 0 ;
        for (int i = 2; i < s.length(); i++) {
            System.out.println(s.charAt(i) + " " + i +" ");
            if ((s.charAt(i) == s.charAt(i-1) || s.charAt(i) == s.charAt(i-2))
                    && (isPalindrome == false || is_repeat) ) {
                //isPalindrome = true;
                if (s.charAt(i) == s.charAt(i-1) &&
                        s.charAt(i) != s.charAt(i-2)) {
                    count = 2;
                    midR = i;
                    midL = i-1;
                    isPalindrome = true;
                    is_repeat = true;
                } else if (s.charAt(i) == s.charAt(i-2) &&
                        s.charAt(i) != s.charAt(i-1)) {
                    count = 3;
                    midR = midL = i-1;
                    isPalindrome = true;

                } else {

                    is_repeat = true;
                    if (first) {
                        count = 3;
                        first = false;
                    }
                    else
                        count++;
                    System.out.println("repeat " + count);

                }
                continue;
            }
            if (is_repeat) {
                isPalindrome = true;
                is_repeat = false;
                if (count%2 ==0 ) {
                    midR = i-1;
                    midL = i - count;
                }
            }

            if (isPalindrome) {
                int x = i - midR;
                System.out.println(x + ":(" + midL+","+midR +") "+ count + " "+ maxLen);
                // this char is also part of palindrome
                int left = midL - x ;
                int right = midR + x;
                if ((left >= 0 && right < s.length())
                        && s.charAt(left) == s.charAt(right)){
                    count += 2;
                    if (maxLen < count) {
                        maxLen = count;
                        if (midL == midR) {
                            start = midL - count/2;
                            end = midL + count/2 +1; //adding +1 so that end points to endindex+1 for str.substing()

                        } else {
                            start = midL - count/2 +1;
                            end = midR + count/2;
                        }
                    }
                } else { // this char is not part of palindrome

                    count = 0;
                    isPalindrome = false;
                }
                System.out.println("st "+ start + " end "+end + " " + isPalindrome);
            }

        }
        if (s.length() == 3 && isPalindrome)
            return s;
        return s.substring(start,end);

    }

}
