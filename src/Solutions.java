import javafx.print.Collation;

import java.lang.reflect.Array;
import java.util.*;

public class Solutions {

    int grid [][]  = {{0, 0, 0, 0},
                      {0, 1, 0, 1},
                      {0, 1, 0, 1},
                      {0, 0, 0, 2}};
    public class Path{
        int i;
        int j;
        public Path(int i , int j){
            this.i = i;
            this.j= j;
        }
    }

    public void robotMove (ArrayList<Path>  moveList , int i , int j) {

        if( i < grid[0].length && j < grid.length ) {
            if (grid[i][j] == 1) {
                return;
            } else if (grid[i][j] == 2) {
                // reached destination print the path
                System.out.println("Reached dest");
                for (int ix = 0; ix<moveList.size(); ix++) {
                    System.out.print("(" + moveList.get(ix).i +"," + moveList.get(ix).j+") ->");
                }
                System.out.println("("+i + "," +j+ ")");
            } else {

                moveList.add(new Path(i,j));
                //move right
                ArrayList<Path> right = (ArrayList<Path>) moveList.clone();
                ArrayList<Path> down = (ArrayList<Path>) moveList.clone();
                robotMove(right,i+1, j);
                robotMove(down, i,j+1);

            }
        }
    }

    public int fibonacci (int n) {
        if (n == 0 || n == 1  || n==2){
            return 1;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }

    // LeetCode problem 6
    public String convert(String s, int numRows) {

        StringBuilder [] sbArray = new StringBuilder[numRows];
        StringBuilder output = new StringBuilder();
        if (numRows == 1)
            return s;
        // init the String array
        for (int i = 0; i < numRows ; i++) {
            sbArray[i] = new StringBuilder();
        }
        int midCharsCount = numRows - 2;
        int rows = 0;
        boolean isVertical = true;
        for (int i = 0; i < s.length(); i++) {

            if (rows == 0) {
                isVertical = true;
            }
            if (rows == numRows) {
                rows-=2;
                if (rows != 0)
                    isVertical = false;
            }
            //System.out.println("char "+s.charAt(i) + "  rows " + rows );
            sbArray[rows].append(s.charAt(i));
            if (isVertical == true && rows < numRows) {
                rows++;
            }
            if (isVertical == false && rows > 0 ) {
                rows--;
            }

        }

        for (int i = 0; i < sbArray.length; i++) {
            output.append(sbArray[i]);
        }

        return output.toString();
    }


    public boolean isDigit(char ch) {
        return ch >= '0' && ch <='9' ? true: false;
    }
    // atoi leetcode 7
    // converts string to integer handle overflow and underflow
    // handle whitespace and leading zero and now whitespcae char
    // returns zero if the string is like "   wpr 9032"
    // returns the number if string is like "   9032 wpr"
    public int myAtoi(String str) {
        int number = 0;
        int start =0 , end = 0;
        boolean positive = true;
        int tens = 1;
        int i = 0;

        // skip the leading whitespaces
        while (i < str.length() && str.charAt(i) == ' ')
            i++;
        // all whitespace so return 0
        if (i == str.length())
            return number;


        if (!isDigit(str.charAt(i)) &&
                (str.charAt(i) != '+' && str.charAt(i) != '-')) { // start with non digit char after whitespace so return 0
            return number;
        } else {
            // starts with + or -
            if (str.charAt(i) == '+' || str.charAt(i) == '-') {
                positive = str.charAt(i) == '+' ? true : false;
                i++;
            }
            // start with digit after whitespace
            // skip all the leading zeros in the digit
            while ( i < str.length() &&  str.charAt(i) == '0')
                i++;
            // if only all zeros then return zero
            if (i == str.length())
                return  number;
            start = i;
            System.out.println("start" + start);
            while (i < str.length() && isDigit(str.charAt(i))) {
                i++;
            }
            end = i;
            //System.out.println("start " + start + " end " + end);
        }

        // if we have more than 10 digit or the 10 th digit is bigger than 2
        // then we have either underflow or overflow
        if (end - start > 10 ||
                ((end - start == 10) && str.charAt(start) - '0' > 2))
            return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        // between start and end we have our desired digits
        // we calculate the digit value from reverse direction
        for (i = end - 1 ; i >= start ; i--) {
            if (positive) {
                // overflow detected return MAX
                if ( number + ((int)(str.charAt(i) - '0') * tens) < 0) {
                    return Integer.MAX_VALUE;
                }
                number +=  (int)(str.charAt(i) - '0') * tens;

            } else {
                //underflow detected return min
                if ( number  - ((int)(str.charAt(i) - '0') * tens) > 0) {
                    return Integer.MIN_VALUE;
                }
                number -=  (int)(str.charAt(i) - '0') * tens;

            }
            tens *= 10;
        }

    return number;
    }

    //LeetCode 8

    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        String str = new Integer(x).toString();
        int i = 0;
        int j = str.length()-1;
        while ( i <= j && str.charAt(i) == str.charAt(j)) {
            i++;
            j--;
        }
        return j < i;
    }

    /*
     * The basic idea here is to start with a difference and a low_point
     * an move along the array to find a bigger difference, if at any time we
     * found a point lower than the low_point we update the low and hope to find
     * a bigger difference in the rest of the array. Note that after updating the low_point
     * with a lower value we have to continue search for  a bigger difference than
     * the current difference if not the older difference was the biggest difference.
     * The following input arr can be used as the input to this function.
     * */
    //    int [] arr  = {10,2,5,6,8,9,15};
//        int maxDiff = s1.maxDiffInArrayLeft2Right(arr);
//        System.out.println("max diff = " + maxDiff);
    public int maxDiffInArrayLeft2Right (int[] arr) {
        int maxDiff = 0;
        int low = arr[0];

        for (int i = 0 ; i < arr.length; i++) {
            maxDiff = Math.max(arr[i] - low, maxDiff);
            low = Math.min(low, arr[i]);
        }
        return maxDiff;
    }

    // NUmber of set bits count O(s) where s  is the number bits
    public int numberOfSetBits (int x) {
        int count = 0;
        int y = 0;
        while ( x != 0) {
            // this  will set the value of y to the lowest bit position in x
            // for example if x is 6 (0110) y wil have value 2 (0010)
            y = x & ~(x-1);
            // this will unset the y bit position in x, we exit loop when x becomes zero
            x = x ^ y;
            count++;

        }
        return count;


    }
    //get nth Ugly number, ugly number is a number which can be created using only 3 prime factors 2, 3 and 5
    // first few ugly numbers 1, 2, 3, 4, ,5 ,6 ,8, 9, 10, 12, 15 ....
    public int getNthUglyNumber(int n) {
        int i2 = 0;
        int i3 = 0;
        int i5 = 0;
        int [] uglyNumbers = new int[n];
        int nextMultOf2, nextMultOf3, nextMultOf5;
        int nextNumber;
        uglyNumbers[0] = 1;
        nextMultOf2 = uglyNumbers[0] * 2;
        nextMultOf3 = uglyNumbers[0] * 3;
        nextMultOf5 = uglyNumbers[0] * 5;
        // uglyNumber[n-1] is your solution as array subscript is starting from zeero
        for (int i = 1; i < n; i++) {
            nextNumber = (nextMultOf2 < nextMultOf3) ? (nextMultOf2 < nextMultOf5 ? nextMultOf2 : nextMultOf5)
                                                     : (nextMultOf3 < nextMultOf5 ? nextMultOf3 : nextMultOf5 );
            //System.out.println(nextNumber);
            uglyNumbers[i] = nextNumber;
            if (nextNumber == nextMultOf2) {
                i2 = i2 +1;
                nextMultOf2 = uglyNumbers[i2] * 2;
            }
            if (nextNumber == nextMultOf3) {
                i3 = i3 +1;
                nextMultOf3 = uglyNumbers[i3] * 3;
            }
            if (nextNumber == nextMultOf5) {
                i5 = i5 +1;
                nextMultOf5 = uglyNumbers[i5] * 5;
            }


        }
        return uglyNumbers[n-1];

    }
    // Problem 11: container with most water
    // The basic idea is to find the largest rectangle.
    // we can have two pointers left/right and update them
    // The width will be the longest when left = 0 & right = arr.len
    // Start with the a minimal height that gives max rectangle at that height.
    // From this point update the height by one and
    // check if the current height[left] or height[right] is large enough.
    // if yes the rectangle can be used and
    // if no then move to the next left/right position that can support this height
    // This approach will significantly reduce the complexity to O(n) as the unnecessary area calc is avoided
    public int maxArea(int[] height) {
        int maxAr = Integer.MIN_VALUE; // the max area covered by the rectangle
        int left = 0;                  // left pointer
        int right = height.length - 1; // right pointer
        int currentHeight = Integer.min(height[left],height[right]); // the starting height of the rectangle

        while (left < right) {
            // if currentHeight can be supported on both left/right we calculate the rectangle area
            // otherwise we need to update lef/right pointers.
            if (currentHeight <= height[left] && currentHeight <= height[right]) {
                int width = right - left;
                int area = width * currentHeight;
                maxAr = Integer.max(maxAr, area);
                currentHeight++;
            }
            if (currentHeight > height[left])
                left++;
            if (currentHeight > height[right])
                right--;
        }
        return maxAr;
    }

    // Leetcode 15
    // 1. sort the array
    // 2. take the first item from the sorted array as the target_sum i.e -targetx as a+b+c = 0 so b+c = -a
    // 3. in the sorted fine two items that adds up to -targetx
    public List<List<Integer>> threeSum(int[] nums) {
        List <List<Integer>> results = new ArrayList();
        List <List<Integer>> resultUn = new ArrayList();
        if (nums.length <= 2)
            return resultUn;

        // sort the inputs
        Arrays.sort(nums);
        // we now loop through each element targetx in array make it -targetx
        // and search for a pair in the sorted array which is equal to -targetx


        for (int i = 0 ; i < nums.length; i++){
            if (i == 0 || (i>0 && nums[i]!=nums[i-1])) {
                // start from the next item it will ensure uniqueness as this is a sorted array
                // cause if we have already discovered all the pairs that is equal to ith entry
                // the previous ones already accounted for ie already in the list hence no need add them again
                // this is how uniqueness  is stored
                int start = i + 1;
                int end = nums.length - 1;
                int targetx = -nums[i];

                while (start < end ) {

                    if (nums[start] + nums[end] == targetx) {
                        ArrayList<Integer> ilist = new ArrayList<>();
                        ilist.add(nums[start]);
                        ilist.add(nums[end]);
                        ilist.add(nums[i]);
                        //skip the same occurences to maintain uniqueness
                        while (start < end && nums[start] == nums[start+1])
                            start++;
                        while(start < end && nums[end] == nums[end-1])
                            end--;
                        results.add(ilist);
                        start++;
                        end--;
                    }
                    else if (nums[start] + nums[end] > targetx)
                        end--;
                    else
                        start++;
                }
            }
        }

        return results;

    }

    //Leetcode 16
    public int threeSumClosest(int[] nums, int target) {

        int sumClosest = 0;
        int  sDiff = Integer.MAX_VALUE;

        // 1. sort the array so that if we use two pointer we can determine sum of two numbers in O(n)
        // 2. use start = i + 1 in every iteration because the i th position was
        //    considered already in the previous loop
        // 3. pick nums[i], and search for two numbers in the array,
        //    track the smallestDiff (target - sum_of_3) & the tempSum
        //    at the end return the closest sum tracked in the whole loop
        // 4. At any point if sum_of_3 == target return the target or sum_of_3
        Arrays.sort(nums);

        for (int i = 0; i<nums.length; i++){
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int tempSum = nums[i] + nums[start] + nums[end];

                if (Math.abs(target - tempSum ) < sDiff) {
                    sDiff = Math.abs (target - tempSum );
                    sumClosest = tempSum;
                }

                if (tempSum == target) {
                    return tempSum;
                } else if (tempSum > target) {
                    end--;
                } else {
                    start++;
                }

            }
        }
        return sumClosest;

    }

    // The solution for letterCombinations Problem
    // Leetcode 17
    Map<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};

    List<String> output = new ArrayList<String>();

    public void backtrack(String combination, String next_digits) {
        // if there is no more digits to check
        if (next_digits.length() == 0) {
            // the combination is done
            output.add(combination);
        }
        // if there are still digits to check
        else {
            // iterate over all letters which map
            // the next available digit
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = phone.get(digit).substring(i, i + 1);
                // append the current letter to the combination
                // and proceed to the next digits
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() != 0)
            backtrack("", digits);
        return output;
    }

    // KSum Solution

    public ArrayList<List<Integer>> kSum(int[] nums, int target, int k, int index) {
        ArrayList <List<Integer>> results = new ArrayList();
        int kSumLen = nums.length;
        if (k == 2) {
            int start = index;
            int end = kSumLen - 1;

            while (start < end) {

                if( nums[start] + nums[end] == target) {
                    List <Integer> tempList = new ArrayList<>();
                    tempList.add(nums[start]);
                    tempList.add(nums[end]);
                    results.add(tempList);
                    // skip the duplicates if any from both sides
                    while (start < end && nums[start] == nums[start + 1])
                        start++;
                    while (start < end && nums[end] == nums[end - 1])
                        end--;
                    start++;
                    end--;
                } else if (nums[start] + nums[end] > target)
                    end--;
                else
                    start++;

            }
        } else {
            // start with index as we want to skip the previously processed ones
            for (int i = index; i < kSumLen - k + 1; i++) {
                //use current number to reduce ksum into k-1sum
                if (i > index && nums[i] == nums[i-1])
                    continue;
                ArrayList <List<Integer>> tempRes = kSum(nums,target - nums[i], k-1,i+1);
                if(tempRes != null){
                    //add previous results
                    for (List<Integer> t: tempRes) {
                        // update the each list entry in tempRes
                        // note the t is used as a reference  so it modifies each list entry inplace
                        t.add(0,nums[i]);
                    }
                    results.addAll(tempRes);
                }

            }
        }

        return results;
    }
    // Leetcode 18
    public List <List<Integer>> fourSum(int[] nums, int target) {

        // Sort the array
        Arrays.sort(nums);
        return kSum(nums,target,4, 0);

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

    //Leetcode 20 valid parethesis
    public boolean isValid(String s) {

        Stack<Character> ptStack = new Stack<>();
        int i = 0;
        for( i = 0; i<s.length() ; i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[' )
                ptStack.push(ch);
            else {
                if (ptStack.empty())
                    break;
                else {
                    ch = getOpposit(ch);
                    char popCh = ptStack.pop();
                    //System.out.println("ch = " + ch + " popCh = " + popCh);
                    if(popCh != ch)
                        break;
                }

            }
        }
        //System.out.println("i =" +i + " isempty" + ptStack.empty());
        if (ptStack.empty() && i == s.length())
            return true;
        else
            return false;
    }

    //LeetCode 26: Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {

        int curr = 1; // curr is the pointer to unique item start with position 1 as pos 0 will always be unique

        // Keep one running pointer i to check if the entry in the ith position
        // matches the i-1 th position, if not we copy the unique item in the position pointed by curr and
        // update curr top point to the nest pos
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[i-1])
                nums[curr++] = nums[i];
        }
        return curr;
    }

    // LeetCode 27: Remove Element val from an unsorted array O(n)
    // Have two pointer one starting from left (I) another starting from right (J). If we find the 'val' while scanning
    // from left. We swap the item(val) with the item pointed by the right pointer J.
    // if we encounter item (val) from right, we just skip it
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0)
            return 0;

        int j = nums.length - 1;
        int i = 0;

        while (i < j) {
            // found val from right side skip this cause we need j to point to an item != val, so that it can be
            // swapped with nums[i] (when nums[i] == val)
            if (nums[j] == val) {
                j--;
                continue;
            }
            if (nums[i] == val) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j--;
            }
            i++;

        }
        return nums[i] == val ? i : (i + 1);
    }

    // LeetCode 28: strStr Look at the KMP search which is O(m +n )
    // This is O(mn) algo
    public int strStr(String haystack, String needle) {

        if (haystack.length() == 0 && needle.length() == 0)
            return 0;
        int index = -1;
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < haystack.length() && j < needle.length()) {
            if(haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                j = 0;
                k++;
                i = k;
            }
            if (j == needle.length()){
                index = i - j;
                break;
            }
        }
        return index;
    }

    public class Counts{
        int fixedCount;
        int currenCount;

        public Counts(int count) {
            fixedCount = count;
            currenCount = count;
        }
        public void reset() {
            currenCount = fixedCount;
        }
        public void incrementAll() {
            fixedCount++;
            currenCount++;
        }

    }

    // LeetCode 30:: Substring with Concatenation of All Words
    public List<Integer> findSubstringV1(String s, String[] words) {
        List <Integer> indicies = new LinkedList<>();
        HashMap <String,Counts> hashMap = new HashMap<>();
        int totalWordCount  = words.length;
        if (words.length == 0) {
            return indicies;
        }

        int wordSize = words[0].length();
        int currIdx = 0;
        List <String> listStr = new LinkedList<>();
        int k = 0;
        boolean isInSearch = false;
        boolean isReset = false;

        // setup the hashtable for the words;
        for (String word : words) {
            if(hashMap.containsKey(word)) {
                hashMap.get(word).incrementAll();
            } else {
                hashMap.put(word, new Counts(1));
                listStr.add(word);
            }
        }

        while ((currIdx + wordSize) <= s.length()) {
            String lookUpStr = s.substring(currIdx, currIdx + wordSize);
//            System.out.println(lookUpStr + " tc " + totalWordCount + " search "
//             + isInSearch + " idx " + currIdx + " k " +k );
            if (hashMap.containsKey(lookUpStr)) {
                Counts strCount = hashMap.get(lookUpStr);
                if (isInSearch == false) {
                    isInSearch = true;
                    k = currIdx;
                }

                if (strCount.currenCount != 0) {
                    strCount.currenCount--;
                    currIdx += wordSize;
                    totalWordCount--;
                } else {
                    // reset count for word
                    isReset = true;
                    currIdx = k+1;
                }

                // found the entry add it to the index list
                if (totalWordCount == 0 && isInSearch) {
                    indicies.add(k);
                    // reset to check from the next word
                    currIdx = k+1;
                    isReset = true;
                }

            } else {
                if (isInSearch) {
                    isReset = true;
                    currIdx = k;
                }
                currIdx++;
                // reset count for words
            }
            if (isReset) {
                for (String word : listStr) {
                    hashMap.get(word).reset();
                }
                totalWordCount = words.length;
                isInSearch = false;
                isReset = false;
            }
        }

        return indicies;
    }

    // This is a best solution for LeetCode 30::  Substring with Concatenation of All Words 5ms runtime
    public List<Integer> findSubstringV3(String s, String[] words) {
        int N = s.length();
        List<Integer> indexes = new ArrayList<Integer>(s.length());
        if (words.length == 0) {
            return indexes;
        }
        int M = words[0].length();
        if (N < M * words.length) {
            return indexes;
        }
        int last = N - M + 1;

        //map each string in words array to some index and compute target counters
        Map<String, Integer> mapping = new HashMap<String, Integer>(words.length);
        int [][] table = new int[2][words.length];
        int failures = 0, index = 0;
        for (int i = 0; i < words.length; ++i) {
            Integer mapped = mapping.get(words[i]);
            if (mapped == null) {
                ++failures;
                mapping.put(words[i], index);
                //System.out.println(words[i] + " " + index);
                mapped = index++;
            }
            ++table[0][mapped];
            //System.out.println("ind " + mapped + " ent " + table[0][mapped]);
        }

        //find all occurrences at string S and map them to their current integer, -1 means no such string is in words array
        int [] smapping = new int[last];
        for (int i = 0; i < last; ++i) {
            String section = s.substring(i, i + M);
            Integer mapped = mapping.get(section);
            if (mapped == null) {
                smapping[i] = -1;
            } else {
                smapping[i] = mapped;
            }
            //System.out.println("i " + i + " " +smapping[i]);
        }

        //fix the number of linear scans
        for (int i = 0; i < M; ++i) {
            //reset scan variables
            int currentFailures = failures; //number of current mismatches
            int left = i, right = i;
            Arrays.fill(table[1], 0);
            //here, simply solve the minimum-window-substring problem
            while (right < last) {
                while (currentFailures > 0 && right < last) {
                    int target = smapping[right];
                    //System.out.println(target);
                    if (target!=-1)
                        //System.out.println("Bef : target "+ target+" table[0]" + table[0][target] + " table[1] " + table[1][target]);
                    if (target != -1 && ++table[1][target] == table[0][target]) {
                        //System.out.println("target "+ target+" table[0]" + table[0][target] + " table[1] " + table[1][target]);
                        --currentFailures;
                        //System.out.println("current failuers" + currentFailures);
                    }
                    right += M;
                }
                while (currentFailures == 0 && left < right) {
                    int target = smapping[left];
                    if (target != -1 && --table[1][target] == table[0][target] - 1) {
                        int length = right - left;
                        //instead of checking every window, we know exactly the length we want
                        if ((length / M) ==  words.length) {
                            indexes.add(left);
                        }
                        ++currentFailures;
                    }
                    left += M;
                }
            }

        }
        return indexes;
    }

    // This is better than V1 run time 13ms
    public List<Integer> findSubstring(String s, String[] words) {
        List <Integer> indicies = new ArrayList<>();
        int wordsLen = words.length;
        if (wordsLen == 0) {
            return indicies;
        }
        int N = s.length();
        HashMap <String,Integer> map = new HashMap<>(wordsLen);
        int [][] wordsTable = new int[2][wordsLen];
        int subStrSize = words[0].length();
        int last = N - subStrSize + 1;
        int [] sMapping = new int[last];
        int totalWordCount  = words.length;
        int index = 0;

        if ( N < wordsLen * subStrSize)
            return indicies;

        for (int i = 0; i < wordsLen; i++){
            Integer idx = map.get(words[i]);
            if (idx == null) {
                map.put(words[i],index);
                idx = index++;
            }
            ++wordsTable[0][idx];
        }

        for (int i = 0; i < last; i++){
            String lookUpStr = s.substring(i, i + subStrSize);
            //System.out.println(lookUpStr);
            Integer idx = map.get(lookUpStr);
            if (idx == null)
                sMapping[i] = -1;
            else
                sMapping[i] = idx;
            //System.out.print(" " + sMapping[i]);
        }
        //System.out.println();

        for (int i = 0; i< last; i++) {
            Arrays.fill(wordsTable[1],0);
            int currrentCount = totalWordCount;
            int k = i;

            while(currrentCount > 0  && k < last) {
                int target = sMapping[k];
                if (target != -1) {
                        wordsTable[1][target]++;
                        if (wordsTable[0][target] >= wordsTable[1][target])
                            currrentCount--;
                        else
                            break;
                } else {
                    break;
                }
                k += subStrSize;
            }
            if (currrentCount == 0) {
                indicies.add(i);
            }

        }
        return indicies;
    }

    // LeetCode 32 (Hard):: Longest Valid Parentheses This is a 3ms solution. This requires O(n) solution.
    // It requires 3 n passes in the array. But there is a DP solution to this problem which requires 1 n pass.
    // The basic idea here is to maintain an countArray and a stack. The stack stores idx of the
    // left parenthesis. We store the index so that after we scanned the whole string we can find the idx
    // where there was no matching parenthesis. We put -1 in countArray for such index.
    // In the countArr we store the count of parenthesis at a specific point. for Left parenthesis
    // we always store zero in countArray. for right parenthesis if stack is empty we store -1, other wise 1.
    // Finally scan through countArray to find the longest uninterrupted substing;
    // a -1 in countArray means interruption
    public int longestValidParentheses(String s) {
        if (s.length() <= 1)
            return 0;
        int maxLen = Integer.MIN_VALUE;
        Stack <Integer> ptStack = new Stack<>();
        int [] countArr = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                ptStack.push(i);
                countArr[i] = 0;

            } else {
                if (!ptStack.empty()) {
                    ptStack.pop();
                    countArr[i] = 1;

                } else {
                    countArr[i] = -1;
                }

            }
        }

        while (!ptStack.empty()) {
            int i = ptStack.pop();
            countArr[i] = -1;
        }

        int tempCount = 0;
        for (int i = 0; i < countArr.length; i++) {
            if (countArr[i] != -1) {
                tempCount += countArr[i];
            } else {
                maxLen = Math.max(maxLen,tempCount);
                tempCount = 0;
            }

        }
        maxLen = Math.max(maxLen,tempCount);

        return maxLen * 2;
    }

    /**
     *
     * if s[i] = ')' & s[i-1] ='('
     *    dp[i] = dp[i-1] +2
     *
     * if s[i- dp[i-1]-1] = '('
     *    dp[i] = dp[i-1] + dp[i - d[i-1] - 2] + 2
     *
     * */
    public int longestValidParenthesesV2(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    // This is the best solution . Time : O(n) Space: O(1)
    // The idea is to first scan from left and have left counter for ( and right counter for )
    // Increase left for ( & right for ). when left == right update the current subString length. when right > left
    // reset left & right. Now scan from left & do the same only difference is reset when left >= right.
    // Think of scanning from right is reversing the string. We count the valid substring from left & right
    // when going from right '(' looks as if ')' in the reverse string. Thats why we need to go from right.
    public int longestValidParenthesesV3( String s) {
        if (s.length() <= 1)
            return 0;
        int maxLen = 0;
        int left = 0;
        int right = 0;

        for (int i = 0 ; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                left++;
            else
                right++;
            if (left == right)
                maxLen = Math.max(maxLen, right * 2);
            else if (right > left)
                left = right = 0;
        }
        //System.out.println(maxLen);
        left = right = 0;

        for (int i = s.length() -1; i >=0 ; i--) {
            if (s.charAt(i) == '(') {
                left++;
            }
            else {
                right++;
            }
            if (left == right) {
                maxLen = Math.max(maxLen, left * 2);
            }
            else if (right <= left) {
                left = right = 0;
            }

        }
        //System.out.println(maxLen);

        return  maxLen;
    }

    // Leetcode ::36. Valid Sudoku
    // This version is slower due to the use of string in set,
    // the next method uses Integer instead of String and performance has been improved significantly
    public boolean isValidSudokuV2(char[][] board) {

        HashSet <String> set = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                int boxNum = (i/3)* 3 + (j/3);
                //System.out.println(" "+ i + " "+ j+ " " + boxNum);
                if(board[i][j] != '.') {
                    String row = "r:" + i + ":" + board[i][j];
                    String col = "c:" + j + ":" + board[i][j];
                    String box = "b:" + boxNum + ":" + board[i][j];

                    if(!set.contains(row) && !set.contains(col) && !set.contains(box)) {
                        set.add(row);
                        set.add(col);
                        set.add(box);
                    } else
                        return  false;
                }

            }
        }

        return true;
    }


    // This is the better solution, instead of using a String in Set just
    // by using a number we can get a very fast run time only 2ms. We maintain a set of row/col/board for each entry.
    // Each entry is unique for row/col/board. So if we found the same entry while scanning then its not valid sudoku.
    public boolean isValidSudoku(char[][] board) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != '.') {
                    int boxNum = 1000 * ((i/3)* 3 + (j/3) + 1) + board[i][j] - '0';
                    int row = 10 * (i+1) + board[i][j] - '0';
                    int col = 100 * (j+1) + board[i][j] - '0';
                    if(!set.contains(row) && !set.contains(col) && !set.contains(boxNum)) {
                        set.add(row);
                        set.add(col);
                        set.add(boxNum);
                    } else
                        return  false;
                }
            }
        }
        return true;
    }




    // 38. Count and Say
    public String countAndSay(int n) {

        ArrayList<String> result = new ArrayList<>();
        result.add("1");
        result.add("11");
        result.add("21");
        result.add("1211");
        result.add("111221");

        if (n > 5) {
            int curr = 5;
            while (curr <= n) {
                String s = result.get(curr-1);
                int count = 1;
                int i = 0;
                StringBuilder strB = new StringBuilder();
                for (i = 0; i< s.length() -1; i++) {

                    if (s.charAt(i) != s.charAt(i+1)) {
                        strB.append(count);
                        strB.append(s.charAt(i));
                        count = 1;
                    } else {
                        count++;
                    }
                }
                strB.append(count);
                strB.append(s.charAt(i));
                result.add(strB.toString());
                curr++;

            }
        }
        return result.get(n-1);
    }

    // Get the duplicate number in an array  of n where numbers are between 1 to  n -1.
    // Find a solution that is O(n) and O(1) space, You are allowed to modify the array elements.
    // If we are allowed to modify the array elements, we can use an interesting approach
    // of marking the array position with negative values as we discovers them in the array.
    // For example say in 1 2 3 1 1 4 ; the value 1 appears more than once,
    // when we first see 1 in arr[0] we mark element @ array_index 1 negative,
    // now when we encounter 1 another time we find the index is marked negative so 1 is repeated
    //  One issue with this approach is it prints multiple occurrences say
    //  if 1 appears 3 time it will print out 1 twice,
    //  so basically it assumes repeated number is there twice
    public void printDuplicateValues(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            if( nums[Math.abs(nums[i])] >= 0) {

                    nums[Math.abs(nums[i])] = -nums[Math.abs(nums[i])];


            } else {

                System.out.print(" " + Math.abs(nums[i]));
            }
        }
        System.out.println();

    }
    private int decode (int x, int offset) {
        if (x < offset)
            return x;
        else
            return x-offset;

    }
    public void printDuplicateValuesV2(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            //System.out.println(nums[i]);
            if( nums[decode(nums[i], nums.length)] < nums.length) {

                nums[decode(nums[i],nums.length)] = nums[decode(nums[i],nums.length)] + nums.length;

            } else {

                System.out.print(" " + decode(nums[i],nums.length));
            }

        }

        System.out.println();
    }

    // 287. Find the Duplicate Number. We have to find the duplicate number using O(n) time and O(1) space.
    // Now we use the floyd's tortoise & hare algorithm to detect a loop in linked list.
    // The numbers in the array is between 1 to N for an array of N+1 size.
    // so each value in the array can be consider as a pointer to the next array element.
    // For example 3 2 1 2 4 index 0 points to index 3 as arr[0] value is '3'. So now considering
    // the array as linked list with loop we use the tortoise & hare algo.
    // The algorithm is to use to pointers one slow and another fast.
    // Slow increase by 1 where fast increase by double that speed.
    // So in case of a loop the fast will catch up with the slow. The point slow and fast meet inside
    // the loop is the same distance from the start(position 0) to the loop start.
    // So now we move fast to the beginning (position 0). Then increase slow & fast at the same speed.
    // The two pointer will meet at loop start
    public int findDuplicate(int[] nums) {
        if (nums.length <= 1)
            return -1;

        int slow = 0;
        int fast = 0;

        slow = nums[slow];
        fast = nums[nums[fast]];
        // move fast @ double spped than slow until they meet.
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        // fast & slow met @ a point from which the distant to the beginning
        // of the loop is same as it its from the start (pos 0). Move fast to start
        fast = 0;
        // increase slow & fast @ same speed (slow speed), they will meet at the loop start
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        // slow & fast both points to loop start
        return slow;
    }

    // LeetCode 41 :: First Missing Positive (Hard)
    // The algo needs to be O(n) time & O(1) space, so we cannot use any kind of special data struct features.
    // We can mark the array and using couple N passes figure out a solution.
    // First we nove the positive numbers to the left of array.
    // The idea is to check the presence of consecutive numbers starting from 1 in the array,
    // so we mark the array indexes for example if 1 is present we mark nums[0] if 3 is present we mark num[2]
    // during marking we just make th number negative

    public int firstMissingPositive(int[] nums) {
        int missPv = 1;
        int i = 0;

        // move the positive numbers on left side of the array,
        // we dont care if we override negative numbser & zero
        int nextPositive = 0;
        for( i = 0; i < nums.length; i++){
            if(nums[i] > 0) {
                nums[nextPositive] = nums[i];
                nextPositive++;
            }
        }


        // Up to nextPositve everything is positive number. Now lets mark the array up to nextPositive.
        // We mark nums[0] with a negative if we found 1 in the array ,
        // we mark nums[2] negative if we found 3 in the array and so on.
        // After this the array indices will be marked negative, for the entries present in the array.
        for ( i = 0; i < nextPositive; i++){

            if(Math.abs(nums[i]) <= nextPositive) {
                // before marking negative lets check if we already marked i negative or not
                // to avoid double negative making the value positive,
                // this could happen if dupleicates number is in the  array
                if(nums[Math.abs(nums[i])-1] > 0)
                    nums[Math.abs(nums[i])-1] = -nums[Math.abs(nums[i])-1];
            }

        }
        // Now the array is marked and we scane from left to right upto nextPositve
        // to find the first array position  that have a positive value.
        // the index of the first positive position is the missing number
        // we need to add +1 to indexes start from zero
        for ( i = 0; i < nextPositive; i++) {

            if(nums[i] > 0) {
                missPv = i + 1;
                break;
            }

        }
        // we reached the end so we have consecutive 1 to nextPositve in the array,
        // missing number will be  nextPositive +1
        if (i == nextPositive)
            missPv = nextPositive +1;
        return missPv;
    }

    // LeetCode :: 42 Trapping Rain Water
    // Use the same idea of max containter problem LeetCode 11 see maxArea method in this file
    // The idea is to use two pointers left & right starting from two ends.
    // We calculate the water trap per height. Then increase the height and calculate for that height.
    // In a regular case we increase height by 1, but if we found a entry where
    // the left and right entry is big enough we can Increase the currentHeight by more(min of two heights)
    //
    // *************** NOTE: There is a better solution for this problem *******************

    public int trap(int[] height) {
        if( height.length <= 2)
            return 0;
        int totWater = 0;
        int left = 0;
        int right = height.length - 1;

        // skip the zeros for both left & right, lets move left right pointer to first non zero entries
        while (left < height.length && height[left] == 0)
            left++;
        while (right >= 0 &&height[right] == 0)
            right--;

        // get a head start on current height,
        // Update currentHeight to the lower of the two rather than starting with 1.
        // In worst case this could very well be 1
        int currentHeight = Integer.min(height[left], height[right]);



        while (left + 1 < right) {
            if (currentHeight <= height[left] && currentHeight <= height[right]) {
                int curTotal = 0;
                // calculate between left & right, no need to consider left or right
                for(int i = left + 1; i <= right - 1; i++) {
                    if (height[i] < currentHeight){
                        // store the count per height
                        curTotal += (currentHeight - height[i]);
                        // update the height of this pos to current consider height to avoid double counting
                        height[i] = currentHeight;
                    }
                }
                totWater += curTotal;

                //System.out.println( left+ " " +right + " curHgt "+ currentHeight+" curTot " + curTotal);
                currentHeight++;
            }
            // move left to skip the lower values as the are irrelevant (already counted)
            while ( left < right && currentHeight > height[left])
                left++;
            // move right to skip the lower values as the are irrelevant (already counted)
            while (left < right && currentHeight > height[right])
                right--;
            // update the height if its possible to increase by more the 1
            currentHeight = Integer.min(height[left], height[right]);

        }

        return totWater;

    }

    // This is the better O(n) & O(1) space solution; Easy to read solution.
    // The idea is to use two pointers left & right starting from two ends.
    // Also keep track of current leftMax & rightMax.
    // The idea is we always will be able to store/trap rain water covered by
    // the rectangle whose height is MIn(leftMax,rightMax). Now if we find such leftMax or rightMax
    // we move from that side counting the trap water size.
    // we add height[left] to the count if there is  a rightMax taller than height[left] or height[leftMax]
    // so for example height[left] = 1 is between height[leftMax] = 2  & height[rightMax] = 3
    // so a taller bar exist on the right side of height[left], hence its safe to add height[left]'s value
    // the value would be leftMax - height[left], as we also already stored the tallest left bar.
    // Note that for the bar position the count will be zero and thats ok
    // for example in [0, 3,1,2, 6] in position 1 bar size = 3  and left max = 3
    // so for position 1 totwater is zero but for position 2 it will be 2
    //
    public int trapV2(int[] height) {
        // sanity check
        if( height.length <= 2)
            return 0;
        int totWater = 0;
        int left = 0;
        int right = height.length-1;
        int leftMax = 0;
        int rightMax = 0;

        while (left < right) {
            if (height[left] > leftMax)
                leftMax = height[left];
            if (height[right] > rightMax)
                rightMax = height[right];
            // as we know a rightMax exist which is taller than current left & leftMax,
            // so it is safe to add this postion's count to the trap water
            // this height[i] should covered  by leftMax  & rightMax
            if (leftMax < rightMax) {
                totWater += (leftMax - height[left]);
                left++;
            } else { // same we know a taller leftMax exist so safe to add this postion's count
                totWater += (rightMax-height[right]);
                right--;
            }
        }

        return totWater;

    }

    // LeetCode 45:: Jump Game 2 (Hard)
    // This solution is not good at all, very bad runtime
    // This is very bad cause consider example 5 5 5 5 1 1 1 1 1 almost becomes exponential run time
    class Node {
        int edgeCount;
        int index;
        public Node(int e, int idx){
            index = idx;
            edgeCount = e;
        }
    }

    public int jump(int[] nums) {
        int jmpCount = Integer.MAX_VALUE;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.edgeCount - o2.edgeCount;
            }
        });

        int [] jumpDist = new int [nums.length];
        Arrays.fill(jumpDist, Integer.MAX_VALUE);
        priorityQueue.add(new Node(0,0));
        jumpDist[0] = 0;

        while(!priorityQueue.isEmpty()){
            Node u = priorityQueue.poll();
            //System.out.println(u.index +" " + u.edgeCount);

            if (u.index == nums.length-1) {
                jmpCount = Math.min(jmpCount,u.edgeCount);
            }

            for (int edg = 1; edg <= nums[u.index]  && u.index + edg < nums.length ; edg++) {

                if(jumpDist[u.index + edg] > u.edgeCount + 1 ){
                    priorityQueue.add(new Node(u.edgeCount +1,u.index +edg));
                    jumpDist[u.index + edg] = u.edgeCount + 1;
                }

            }

        }


        return jmpCount;
    }

    // Lets try a different solution for LeetCode 45:: Jump Game 2 (Hard)
    // This is an interesting problem at first it seems we need to use shortest path but thats not true
    // This requires a greedy approach, one each turn we figure out the max range cover by an index.
    // For example if nums[0] == 2 then the range cover by num[0] is (1,2) so we check the array elements in (1,2)
    // and try to find out which gives a better greedy choice.
    // The greedy choice here is nums[i] + i <-- the max index covered by nums[i] for
    //  example num[2] == 3 actaully allows a jump of nums[2] + 3 == 5. On each range evaluation we increase
    //  the jump count
    public int jumpV2(int[] nums) {
        int jmpCount = 0;

        if(nums.length ==1)
            return 0;
        for(int i = 0; i< nums.length; ) {
            // lets get the range in curSt & curEnd
            int curSt =  i + 1;
            int currEnd = i + nums[i];
            // max to store the max range covered by current choice
            int max = 0;
            // already the range covers the destination
            // increase the jump count we found our solution
            if (currEnd >= nums.length -1){
                i = currEnd;
                jmpCount++;
                break;
            } else {
                // lets look for the max covered by this range
                for (int j = curSt; j <= currEnd; j++) {
                    if(j >= nums.length) break;
                    if(j + nums[j] > max) {
                        max = j + nums[j];
                        // update the i to be new greedy choice we will start from this postion in the next iteration
                        i = j;
                    }
                }
            }

            jmpCount++;
        }

        return jmpCount;
    }

    //LeetCode :: 48 Rotate Image
    // we need to rotate the 2d array 90 degree clockwise
    public void rotate(int[][] matrix) {
        // we process the 2d matrix in layers, starting with the first layer and
        // we only need to process half of the size, as that will cover the whole array
        // first ->  1  2  3  4 <--- layer 0
        //           5  6  7  8
        //           9 10 11 12
        // last ->  13 14 15 16
        // we need to maintain first and last pointer for row & column
        for (int layer = 0; layer < matrix.length/2; layer++) {

            int first = layer; // points to start for this layer
            int last = matrix.length - first - 1; // points to last for this layer
            // scan through the whole layer and in each iteration rotate  4 elements
            // Note that we need to process first to last (exclusive),
            // because while processing the first one we already processed the last one
            for (int i = first; i < last; i++) {
                int offset = i - first;
                // for elem 1 the first row is fixed, store elem 1 in tmp
                int tmp = matrix[first][i];
                // copy the elem 4 in elem 1 pos, for elem 4 the column is fixed
                matrix[first][i] = matrix[last - offset][first];
                // copy elem 3 in elem 4; for elem 3 the row is fixed (last row)
                matrix[last-offset][first] = matrix[last][last - offset];
                // copy elem 2 in elem 3;  for elem 2 the column is fixed (last column)
                matrix[last][last - offset] = matrix[i][last];
                // copy the elem 1 stored in tmp to elem 2
                matrix[i][last] = tmp;
            }
        }

    }

    //LeetCode :: 49 Group Anagrams
    // The basic idea is to create a frequency counter for each string
    // and store it in a hash table. We scan the array of string and
    // if we discover a freq count that does exist in the hastable
    // we add it to hashtable and the list. If it exist in the hastable
    // we get the list index to group it with other anagrams

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> rList = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap();
        int []freq = new int[26];
        int index = 0;

        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];

            for (int j = 0; j < s.length(); j++){
                freq[s.charAt(j)-'a']++;
            }
            StringBuilder key = new StringBuilder();
            for (int j = 0; j < freq.length; j++) {
                if (freq[j] > 0) {
                    char ch = (char) ('a' + j);
                    char num = (char) ('0' + freq[j]);
                    key.append(ch);
                    key.append(num);
                    freq[j] = 0;
                }

            }
            String keyStr = key.toString();
            //System.out.println(strs[i] + " " + key.toString());
            if (hashMap.containsKey(keyStr)) {
                rList.get(hashMap.get(keyStr)).add(strs[i]);

            } else {
                hashMap.put(keyStr, index);
                List<String> tmp = new ArrayList<>();
                tmp.add(strs[i]);
                rList.add(tmp);
                index++;

            }

        }

        return rList;
    }

    // This is the version 2 of this group Anagram problem,
    // here we used sorting instead of frequency counting,
    // its runtime is actually better than version 1 but  for version 2  the Big O is O(nklgk)
    // where as in version 1 BigO is O(nk), most like this is happening due to the test cases.
    // This algo uses sorting instead of using a frequency counter.
    // lets us the version 1 as ist BigO is better.
    // If the test cases had longer string we would have seen better performance with the version 1

    public List<List<String>> groupAnagramsV2(String[] strs){
        List<List<String>> rList = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap();
        int index = 0;
        for(String str: strs){
            String sortedStr = sortStr(str);
            //System.out.println( str + " " + sortedStr);
            if(hashMap.containsKey(sortedStr)) {
                rList.get(hashMap.get(sortedStr)).add(str);
            } else {
                List <String> tmp = new ArrayList<>();
                tmp.add(str);
                rList.add(tmp);
                hashMap.put(sortedStr,index);
                index++;
            }

        }
        return rList;
    }

    private String sortStr( String str){
        char [] cArray = str.toCharArray();
        Arrays.sort(cArray);
        return new String(cArray);
    }








}
