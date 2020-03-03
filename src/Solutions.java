import javafx.print.Collation;

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
    // if no the move to the next left/right position that can support this height
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

    // LeetCode 28: strStr
    public int strStr(String haystack, String needle) {
        int index = 0;
        return index;
    }











}
