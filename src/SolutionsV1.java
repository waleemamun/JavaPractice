import java.util.*;

public class SolutionsV1 {
    // Commpare two double numbers
    public int compareDoubles(String version1, String version2) {
        int i = 0;
        int j = 0;
        while (i < version1.length() && version1.charAt(i) == '0')
            i++;
        int v1 = i;
        while (i < version1.length() && version1.charAt(i) != '.') {
            i++;
        }
        while (j < version2.length() && version2.charAt(j) == '0')
            j++;
        int v2 = j;
        while (j < version2.length() && version2.charAt(j) != '.') {
            j++;
        }
        int len1 = i - v1;
        int len2 = j - v2;

        if (len1 > len2) {
            return 1;
        } else if (len1 < len2) {
            return -1;
        }
        i = v1;
        j = v2;
        char ch1, ch2;
        while (i < version1.length() || j < version2.length()) {

            if (version1.length() == i) {
                ch1 = '0';
            } else {
                ch1 = version1.charAt(i++);
            }
            if (j == version2.length()) {
                ch2 = '0';
            } else {
                ch2 = version2.charAt(j++);
            }
            if (ch1 == '.' || ch2 == '.') continue;
            else if (ch1 > ch2)
                return 1;
            else if (ch1 < ch2) {
                return -1;
            }
        }

        return 0;
    }

    //Leetcode :: 53  Maximum SubArray
    // if the value of the ith position is greater than the sum of the left side + the current val
    // the we can just discard the left side of the array.
    // We keep track of the max sum on each update of current sum which gurantees that we wend up with max SUm
    public int maxSubArray(int[] nums) {

        int maxSum = nums[0];
        int currSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currSum += nums[i];
            // the current number is actually greater than the cumulative sum so discard everything
            // on the left and update the current sum to current value as its bigger
            if (nums[i] > currSum) {
                currSum = nums[i];
            }
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }

    // Leetcode :: 54 Spiral Matrix
    // The answer will be all the elements in clockwise order from the first-outer layer,
    // followed by the elements from the second-outer layer, and so on.
    public List<Integer> spiralOrder(int[][] matrix) {
        List ans = new ArrayList();
        if (matrix.length == 0)
            return ans;
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++)
                ans.add(matrix[r1][c]);
            for (int r = r1 + 1; r <= r2; r++)
                ans.add(matrix[r][c2]);
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--)
                    ans.add(matrix[r2][c]);
                for (int r = r2; r > r1; r--)
                    ans.add(matrix[r][c1]);
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ans;

    }

    // Leetcode :: 55. Jump Game
    // The basic idea is if there is a zero in the array there is a chance we may not reach the end
    // so we have to check anything on the left of zero that can jump over the zero, for example 3 1 0;
    // so 3 can jump over 0. We need to check the same thing for all zeros.
    public boolean canJump(int[] nums) {
        if (nums.length == 1)
            return true;
        // no need to consider the last element the value in it does not matter
        // start from the right side as it make more sense cause we need
        // to check on the left
        int i = nums.length-2;
        int j = -1;
        boolean isZero = false;
        boolean zeroCovered = true;
        while (i >= 0 ){
            if (nums[i] == 0 && isZero == false) {
                j = i;
                isZero = true;
                zeroCovered = false;
                continue;
            }
            if(isZero) {
                if(j-i < nums[i]) {
                   while(i >= 0 && nums[i] != 0)
                       i--;
                    isZero = false;
                    zeroCovered = true;
                    continue;
                }
            }
            i--;
        }
        return zeroCovered;
    }

    // Leetcode 56:: Merge Intervals
    // FB phone interview problem
    // The idea is to sort the intervals first O(nlgn)
    // The we can just scan the intervals, if the right of the current interval can cover
    // the next interval we can absorb it in current interval. If not the we either update
    // the right if right is in between  the next interval. Other wise we got a new interval
    class Intervals {
        int left;
        int right;
        public Intervals(int l, int r){
            left = l;
            right = r;
        }
    }
    public int[][] merge(int[][] intervals) {
        if (intervals.length <=1)
            return intervals;
        List<Intervals> iList = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++){
            iList.add(new Intervals(intervals[i][0],intervals[i][1]));
        }
        // sort the intervals
        iList.sort(new Comparator<Intervals>(){
            @Override
            public int compare(Intervals o1, Intervals o2) {
                return o1.left - o2.left;
            }
        });
        int i = 0;
        int left = iList.get(0).left;
        int right = iList.get(0).right;
        intervals[0][0] = left;
        intervals[0][1] = right;

        for (Intervals il : iList ) {
            // we can cover this interval with the previous interval, so skip this
            if(right >= il.right)
                continue;
            else {
                // previous interval ends in between this interval, lets increase the right of the interval
                if(right >= il.left ) {
                    right = il.right;
                    intervals[i][1] = right;
                } else { // we cannot cover this interval using the previous interval, create a new interval
                    i++;
                    left = intervals[i][0] = il.left;
                    right = intervals[i][1] = il.right;

                }
            }
        }
        int [][] result = new int [i+1][2];
        for (int j = 0; j<result.length ;j++){
            result[j][0] = intervals[j][0];
            result[j][1] = intervals[j][1];
        }
        return result;

    }

    // LeetCode :: 57 Insert Intervals
    // The idea is to search the left & right side of the newInterval in the sorted intervals
    // when such positions are found then merge the intervals into one interval.
    // This will create three parts in general, leftside of merge interval, merge interval itself,
    // the right side of the merge interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];
        int [][] result;
        int leftNew = left;
        int rightNew = right;
        ArrayList<Intervals> rList = new ArrayList<>();
        // search the intervals for the left value,
        // if not found then get the position where we can fit he new interval
        int leftInt = Utilities.binSearcPos2D(intervals,0,intervals.length-1, left);
        // search the intervals for the right value,
        // if not found then get the position where we can fit he new interval
        int rightInt = Utilities.binSearcPos2D(intervals,0, intervals.length -1, right);
        // found the left value inside existing interval
        if (leftInt < intervals.length) {
            if(leftInt >=0)
                leftNew = intervals[leftInt][0] < left ? intervals[leftInt][0] : left;

        } else if (leftInt > intervals.length){
            // did not find in existing interval leftInt
            // now points to new pos of insertion
            leftInt = leftInt - intervals.length;
        }
        // add everything before leftInterval
        for (int i = 0; i < leftInt; i++) {
            rList.add(new Intervals(intervals[i][0], intervals[i][1]));
        }
        // the right value falls into one of the intervals
        if (rightInt < intervals.length) {
            if (rightInt >= 0)
            rightNew = intervals[rightInt][1] > right ? intervals[rightInt][1] : right;
            // increase rightInt by 1, as this is part of existing interval
            // so when we process right of rightInt it points to the correct value
            rightInt++;

        } else if (rightInt > intervals.length){
            rightInt = rightInt -intervals.length;
        }
        // add the merger new interval
        rList.add(new Intervals(leftNew, rightNew));
        // add everything on the right of right interval
        for (int i = rightInt; i<intervals.length; i++) {
            rList.add(new Intervals(intervals[i][0], intervals[i][1]));
        }

        // copy result into the new array
        result = new int[rList.size()][2];
        int i = 0;
        for (Intervals il : rList) {
            result[i][0] = il.left;
            result[i][1] = il.right;
            i++;
        }

        return result;
    }

    // LeetCode 58:: last word length
    private boolean isChar(char ch){
        if ((ch >= 'a' && ch <='z') || (ch>='A' && ch <='Z'))
            return true;
        return false;
    }
    public int lengthOfLastWord(String s) {
        if(s.length() == 0)
            return 0;
        int count = 0;
        int i = s.length() - 1;
        while (i>= 0 && !isChar(s.charAt(i)))
            i--;
        while(i >=0 && isChar(s.charAt(i))) {
            count++;
            i--;
        }

        return count;
    }

    // Leetcode :: 60 Permutation Sequence
    public String getPermutation(int n, int k) {
        int pos = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        int[] factorial = new int[n+1];
        StringBuilder sb = new StringBuilder();

        // create an array of factorial lookup
        int sum = 1;
        factorial[0] = 1;
        for(int i=1; i<=n; i++){
            sum *= i;
            factorial[i] = sum;
        }
        // factorial[] = {1, 1, 2, 6, 24, ... n!}

        // create a list of numbers to get indices
        for(int i=1; i<=n; i++){
            numbers.add(i);
        }
        // numbers = {1, 2, 3, 4}

        k--;

        for(int i = 1; i <= n; i++){
            // devide k by the next closest factorial for example k = 16 then factorial[n-i] = 6
            // & index becomes 2 , now remove the index item from the list and add it our permutation
            int index = k/factorial[n-i];
            sb.append(numbers.remove(index));
            // we need to decrease k by the value of of the earlier division so factorial[n-i] * index
            k -= index*factorial[n-i];
        }

        return String.valueOf(sb);
    }










}