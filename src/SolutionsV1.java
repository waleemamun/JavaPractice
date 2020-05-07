import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;

import java.awt.image.AreaAveragingScaleFilter;
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

    // LeetCode :: 65 Valid Number (Hard)
    private boolean isDigit(char ch) {
        if (ch >='0' && ch <= '9')
            return true;
        else
            return false;
    }

    public boolean isNumber(String s) {
        int i = 0;
        int j = s.length()-1;

        // remove leading & trailing spaces so that we dont need to handle space in the main loop
        // & any space in the main loop will indicate invalid number.

        while (i < s.length() && s.charAt(i) == ' ')
            i++;
        while ( j>= 0 && s.charAt(j) == ' ')
            j--;
        if(i > j)
            return false;
        // handle leading +/-, the first one is fine anything after this in the main loop is invalid
        // only exception is presence of e, that needs special treatment
        if(i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-'))
            i++;

        // this the main deciding loop to scan the whole string, only valid chars here is
        // 0-9, '.' & 'e' anything else indicates invalid number
        // i & j will point to 1st non space char
        int dotCount = 0;
        int eCount = 0;
        int startI = i;
        boolean validE = false;

        while( i <= j) {
            // 'e' found this needs special treatment
            if(s.charAt(i) == 'e') {
                eCount++;
                if (eCount == 1) {
                    if(i - 1 >= startI && i+1 <= j &&
                            isDigit(s.charAt(i-1)) &&
                            isDigit(s.charAt(i+1))) {
                        i+=2;
                        validE = true;
                    } else if (i - 1 >= startI && i+2 <= j &&
                            isDigit(s.charAt(i-1)) &&
                            isDigit(s.charAt(i+2)) &&
                            (s.charAt(i+1) == '+' || s.charAt(i+1) == '-')){
                        i+=3;
                        validE = true;
                    } else if (i - 2 >= startI &&
                            isDigit(s.charAt(i-2)) &&
                            s.charAt(i-1) == '.') {
                            System.out.println(i + " " + j);
                        if (i+1 <=j &&
                                isDigit(s.charAt(i+1)))
                            i+=2;
                        else if (i+2 <=j && (isDigit(s.charAt(i+2)) && s.charAt(i+1) == '-' || s.charAt(i+1)== '+') )
                            i+=3;
                        validE = true;

                    } else {
                        return false;
                    }

                } else {
                    return  false;
                }

            } else if (s.charAt(i) == '.') {
                dotCount++;
                // '.' found only 1 is allowed and the char cannot be a '.'
                if (dotCount > 1 || validE || (startI == j) ||
                        (i-1 >= startI && !isDigit(s.charAt(i-1)) ||
                                (i+1 <= j && !isDigit(s.charAt(i+1)) && s.charAt(i+1) != 'e')))
                    return false;
                i++;
            } else if (isDigit(s.charAt(i))){
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    // LeetCode 66 plus one
    public int[] plusOne(int[] digits) {
        if (digits.length == 0)
            return digits;
        int j = digits.length-1;
        int []result;
        if(digits[j] !=9){
            digits[j]++;
            return digits;
        }
        if(digits[0] == 9)
            result = new int[digits.length+1];
        else
            result = digits;

        while (j >= 0 && digits[j]== 9){
            result[j] = 0;
            j--;
        }
        if (j < 0)
            result[0] = 1;
        else
            result[j]++;

        return result;
    }

    //Leetcode :: 68. Text Justification (Hard)
    // The basic idea is to scan through the array and find out how many words
    // fit per line and  how many space per line in O(n). To do that we create and additional array which store
    // word count per line & another array that store total space per line. The we scan through the words array again
    // & build the line by getting  the word from words array using wordCount per line from strCount array also used
    // the space count per line to distribute the spaces
    private String createSpace(int count){
        String s= "";
        for(int i = 0; i < count; i++)
            s+=" ";
        return s;
    }
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> resultList = new ArrayList<String>();


        int []strCount = new int[words.length]; //word per Line
        int []spaceUnfilled = new int[words.length];
        int currLen = 0;
        int line = 0;
        int wCount = 0;
        for(int i = 0; i< words.length; i++){
            currLen+= words[i].length();

            if( currLen >= maxWidth) {
                if (currLen == maxWidth) {
                    wCount++;
                } else {
                    currLen--; // remove the last space from current length
                    currLen -= words[i].length(); // remove the current word length
                    spaceUnfilled[line] = maxWidth - currLen; // calc the space unused after the last word removal
                    i--; // decrement i to remove or reprocess the last in the next iteration
                }
                strCount[line] = wCount;
                spaceUnfilled[line] += wCount -1;
                System.out.println(line +" " +strCount[line] + " " +  spaceUnfilled[line]);
                // we found the words for this line
                // reset the word count and current length for next line
                wCount = 0;
                currLen = 0;
                line++;
            } else{
                currLen++; // add the space after a word
                wCount++;  // increase the word count for this line
                if ( i == words.length - 1) {
                    strCount[line] = wCount;
                    spaceUnfilled[line] = maxWidth - (currLen - 1);

                    //System.out.println(line +" " +strCount[line] + " " +  spaceUnfilled[line]);
                    line++;
                }

            }

        }

        int totLine = line;
        line = 0;
        int len = 0;
        int j = 0;
        int evenSpace = 0;
        int extrSpace = 0;
        while (line < totLine){

            StringBuilder lineSb = new StringBuilder();
            j = len;
            len += strCount[line];

            if (strCount[line] == 1) {
                evenSpace = maxWidth - words[j].length();
                extrSpace = 0;
            }else {
                evenSpace = spaceUnfilled[line] / (strCount[line] -1);
                extrSpace = spaceUnfilled[line] % (strCount[line] -1);
            }

            //System.out.println(line +" "+ evenSpace +" "+ extrSpace);
            while (j < len) {
                lineSb.append(words[j]);
                if (j != len-1) {
                    if (line == totLine -1) {
                        lineSb.append(" ");
                        j++;
                        continue;
                    }
                    else
                        lineSb.append(createSpace(evenSpace));
                    if (extrSpace != 0) {
                        lineSb.append(" ");
                        extrSpace--;
                    }
                } else {
                    if (strCount[line] == 1 && line != totLine -1)
                        lineSb.append(createSpace(evenSpace));
                }
                j++;
            }
            if (line == totLine - 1 && spaceUnfilled[line] != (strCount[line ] -1))
                lineSb.append(createSpace(spaceUnfilled[line]));
            resultList.add(lineSb.toString());
            line++;
        }


        return resultList;
    }

    //LeetCode :: 71 Simplify Path

    private boolean isEqStr(String str1 , String str2) {
        if (str1.length() != str2.length())
            return false;
        for (int i = 0; i< str1.length(); i++){
            if(str1.charAt(i) != str2.charAt(i))
                return false;
        }
        return true;
    }
    // Check V2 its easy to read
    // The idea is to split the strings using the delim '/', and get a list of strings,
    // Now scan through the list of strings to get and put them in a stack if its not (".", "..")
    public String simplifyPath(String path) {
        if (path.length() == 0)
            return path;
        StringBuilder canonicalPathSb = new StringBuilder();
        Stack<String> dir = new Stack<>();
        // Creating an iterator

        if(path.charAt(0) == '/')
            dir.push("/");
        else
            return path;
        String [] dirList = path.split("\\/");
        for (int i = 1 ; i< dirList.length; i++){
            if (dirList[i].length() != 0) {
                if (isEqStr(dirList[i],"."))
                    continue;
                else if (isEqStr(dirList[i],"..")) {

                    if(!isEqStr(dir.peek(),"/")){
                        dir.pop();
                    }
                }else {
                    dir.push(dirList[i]);
                }
            }
        }
        Iterator itr = dir.iterator();
        if(itr.hasNext())
            canonicalPathSb.append(itr.next());
        while (itr.hasNext()) {
            canonicalPathSb.append(itr.next());
            canonicalPathSb.append("/");
        }
        if(canonicalPathSb.length()>1)
            canonicalPathSb.deleteCharAt(canonicalPathSb.length()-1);
        return canonicalPathSb.toString();
    }

    // The idea is to split the strings using the delim '/', and get a list of strings,
    // Now scan through the list of strings to get and put them in a stack if its not (".", "..")
    public String simplifyPathV2(String path) {
        if (path.length() == 0)
            return path;
        StringBuilder canonicalPathSb = new StringBuilder();
        Stack<String> dir = new Stack<>();
        // Creating an iterator

        if(path.charAt(0) == '/')
            dir.push("/");
        else
            return path;
        ArrayList<String> strList = Utilities.splitStr(path,'/');
        for (String str : strList){
            // .. encountered need to move up one dir, so pop from the stack
            if (isEqStr(str,"..")){
                if(!isEqStr(dir.peek(), "/"))
                    dir.pop();
            } else {
                // ignore doing anything for current dir
                if (isEqStr(str,"."))
                    continue;
                // new dir lets push it on the stack
                dir.push(str);
            }
        }
        // The result dir list is in the stack, create stack iterator
        // and add the dirs from the stack to the string
        Iterator itr = dir.iterator();
        canonicalPathSb.append(itr.next());

        while (itr.hasNext()) {
            canonicalPathSb.append(itr.next());
            canonicalPathSb.append("/");
        }
        if(canonicalPathSb.length()>1)
            canonicalPathSb.deleteCharAt(canonicalPathSb.length()-1);

        return canonicalPathSb.toString();
    }

    // LeetCode :: 73. Set Matrix Zeroes
    // The idea is to scan &use row 0 & col 0 to store the row & col that have zeros,
    // if there is 0 in row 0 or col 0  then save that info in the boolean value,
    // at the ene we can set the row 0 & col 0 if the bo0lean value are set
    // in the next scan we only scan row 0 & col 0 and set zero in row/col depending zero value in row 0 & col0
    // set all the entry in the col to zero
    private void setColZero (int col, int [][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            matrix[i][col] = 0;
        }
    }
    // set all entry in the row to zero
    private void setRowZero (int row, int [][]matrix) {
        for (int i = 1; i < matrix[0].length; i++){
            matrix[row][i] = 0;
        }
    }

    public void setZeroes(int[][] matrix) {
        boolean isColZero = false;
        boolean isRowZero = false;

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    if (i == 0)
                        isRowZero = true;
                    if (j == 0)
                        isColZero = true;
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }


        for (int i = 1; i < matrix[0].length; i++){
            if (matrix[0][i] == 0) {
                setColZero(i, matrix);
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                setRowZero(i, matrix);
            }
        }
        if (isRowZero) {
            for (int i = 0; i < matrix[0].length; i++)
                matrix[0][i] = 0;
        }

        if (isColZero) {
            for (int i = 0; i < matrix.length; i++)
                matrix[i][0] = 0;
        }
        /*for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print( matrix[i][j] + " ");
            }
            System.out.println();
        }*/

    }

    // Leetcode :: 74. Search a 2D Matrix
    // The basic idea is to do binary search on column zero to find  the row where the value might be
    // then again do a binary search on row to search for the target
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return false;
        // optimization if the value is beyond the boundaries lets no even search
        if (target < matrix[0][0] ||
                target > matrix[matrix.length-1][matrix[0].length -1])
            return false;

        int low = 0;
        int high = matrix.length - 1;

        while (low <= high) {
            int mid = (low + high)/2;
            if (matrix[mid][0] == target)
                return true;
            else if (target < matrix[mid][0])
                high = mid - 1;
            else
                low = mid + 1;
        }

        if (high < 0)
            return false;
        int pos = high;
        low = 0;
        high = matrix[pos].length - 1;

        while (low <= high) {
            int mid = (low + high)/2;
            if (matrix[pos][mid] == target)
                return true;
            else if (target < matrix[pos][mid])
                high = mid - 1;
            else
                low = mid + 1;
        }
        return false;
    }

    // Leetcode :: 75. Sort Colors
    public void sortColors(int[] nums) {
        int z = -1;            // pointer for placing zero
        int t = nums.length;   // pointer for placing two
        int i = 0;
        int temp = 0;

        while(i < t){
            if (nums [i] == 0) {
                z++;
                temp = nums[i];
                nums[i] = nums[z];
                nums[z] = temp;
                if (nums[i] == 2)
                    continue;
            }
            else if (nums[i] == 2) {
                while (t -1 >= 0 && nums[t-1] == 2) t--;
                if (t != 0) t--;
                if ( t < i) break;
                temp = nums[i];
                nums[i] = nums[t];
                nums[t] = temp;
                if (nums[i] == 0)
                    continue;
            }
            i++;
        }
        for (i = 0; i< nums.length; i++)
            System.out.print( nums[i] + " ");
        System.out.println();

    }

    // LeetCode :: 209. Minimum Size Subarray Sum
    // The idea is to use two pointers (low & cur) to make a sliding window.
    // The distance between cur & low continues to increase until we reach the sum s.
    // After the any next addition to the window we discard the low as long as the
    // total sum is still >= s, and we reduce the current currentSum to adjust the window (cur <-> low gives the sum)
    // we keep track of the min window Size.
    public int minSubArrayLen(int s, int[] nums) {
        int low = 0;
        int cur = 0;
        int minSize = Integer.MAX_VALUE;
        int curSum = 0;
        while (cur < nums.length) {
            curSum += nums[cur];
            if(curSum >= s){
                // lets discard item from the left side of the window
                // as long as the condition holds
                // only adding the current element we reached the desired sum
                // hence we check low < cur & not low <= cur
                while (low < cur
                        && curSum  - nums[low] >= s){
                    curSum-= nums[low];
                    low++;
                }
                minSize = Math.min(minSize, cur - low +1);
            }
            cur++;
        }

        if(minSize == Integer.MAX_VALUE)
            minSize = 0;
        return minSize;
    }

    //LeetCode :: 76. Minimum Window Substring (Hard)
    // The idea is to count the freq for the word we are searching,
    // and use a sliding window in the main string to compare frequency
    // of the chars in the window with the 'searching string' frequency
    // Run time 13 MS
    // Check version 4 that's the best solution
    private boolean isEqualOrGrtFreq (int []fs, int []ft) {
        for (int i = 0 ; i < 53; i++) {
            if (fs[i] < ft[i])
                return false;
        }
        return true;
    }
    private int convertCharToIndex(char ch) {
        int idx = 0;
        if (ch >='A' && ch <= 'Z')
            idx = ch -'A';
        else
            idx = ch - 'a' + 26;
        return idx;
    }
    public String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0)
            return "";

        int []freqS = new int[53];
        int []freqT = new int[53];

        for (int i = 0; i < t.length(); i++){
            freqT[convertCharToIndex(t.charAt(i))]++;
        }
        // no solution return empty string
        if (s.length() < t.length()){
            return "";
        }
        int start = 0;
        int cur = 0;
        int minSt = 0;
        int minEnd = 0;
        int minSize = Integer.MAX_VALUE;

        while (cur < s.length()) {
            freqS[convertCharToIndex(s.charAt(cur))]++;
            if (cur - start + 1 >= t.length()
                    && isEqualOrGrtFreq(freqS,freqT)) {
                freqS[convertCharToIndex(s.charAt(start))]--;
                while (start <= cur &&
                        isEqualOrGrtFreq(freqS, freqT)) {
                    start++;
                    freqS[convertCharToIndex(s.charAt(start))]--;

                }
                freqS[convertCharToIndex(s.charAt(start))]++;
                if (cur - start + 1 < minSize) {
                    minSize = cur - start + 1;
                    minSt = start;
                    minEnd = cur;
                }

            }
            cur++;

        }
        if (minSize == Integer.MAX_VALUE)
            return "";
        return s.substring(minSt,minEnd +1);
    }
    // The version2 uses a cleaver approach of using a hashmap to store the frequency
    // This way dont need to loop through the whole 52 size frequncy for A-Z to a-z rather
    // we can check only unique chars. We also make use of formed unique chars so when we found
    // the desired number of char is our sliding window only then we start shrinking our sliding window

    public String minWindowV2(String s, String t) {
        if (s.length() == 0 || t.length() == 0)
            return "";

        HashMap<Character, Integer> freqT = new HashMap<>();
        HashMap<Character, Integer> freqS = new HashMap<>();

        for (int i = 0; i < t.length(); i++){
            int val = freqT.getOrDefault(t.charAt(i), 0);
            freqT.put(t.charAt(i),val + 1);
        }
        // Number of unique characters in t, which need to be present in the desired window.
        int desiredUniqueChar = freqT.size();
        // no solution return empty string
        if (s.length() < t.length()){
            return "";
        }
        int left = 0;
        int right = 0;
        int minSt = 0;
        int minEnd = 0;
        int minSize = Integer.MAX_VALUE;
        // formed is used to keep track of how many unique characters in t
        // are present in the current window in its desired frequency.
        // e.g. if t is "AABC" then the window must have two A's, one B and one C.
        // Thus formed would be = 3 when all these conditions are met.
        int formed = 0;

        while (right < s.length()) {
            char ch = s.charAt(right);
            int count = freqS.getOrDefault(ch, 0);
            freqS.put(ch, count +1);

            if(freqT.containsKey(ch) && freqT.get(ch).intValue() == freqS.get(ch).intValue()) {
                formed++;
            }
            // found exactly the same char count in S that matches T so lest try to shrink the sliding window
            //
            while (formed == desiredUniqueChar && left <= right) {
                char leftCh = s.charAt(left);
                int cnt = freqS.get(leftCh);
                freqS.put(leftCh, cnt -1);
                if(freqT.containsKey(leftCh) && freqT.get(leftCh).intValue() > freqS.get(leftCh).intValue())
                    formed--;
                if (minSize > right - left +1) {
                    minSize = right - left +1;
                    minSt = left;
                    minEnd = right;
                }
                left++;
            }


            right++;

        }
        if (minSize == Integer.MAX_VALUE)
            return "";
        return s.substring(minSt,minEnd +1);
    }

    // The only difference in this approach is we use a filter_s from version 2 is
    // We create a list called filtered_s,  filtered_S which has all the characters
    // from string S along with their indices in S, but these characters should be present in T.
    // This reduces the checking of unwanted chars in S that does not appears in T
    public String minWindowV3(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<Character, Integer>();

        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        // Number of unique characters in t, which need to be present in the desired window.
        int required = dictT.size();

        // Filter all the characters from s into a new list along with their index.
        // The filtering criteria is that the character should be present in t.
        // For example:
        //   S = "ABCDDDDDDEEAFFBC" T = "ABC"
        //  filtered_S = [(0, 'A'), (1, 'B'), (2, 'C'), (11, 'A'), (14, 'B'), (15, 'C')]
        //  Here (0, 'A') means in string S character A is at index 0.
        List<Pair<Integer, Character>> filteredS = new ArrayList<Pair<Integer, Character>>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (dictT.containsKey(c)) {
                filteredS.add(new Pair<Integer, Character>(i, c));
            }
        }

        // Left and Right pointer
        int l = 0, r = 0;

        // formed is used to keep track of how many unique characters in t
        // are present in the current window in its desired frequency.
        // e.g. if t is "AABC" then the window must have two A's, one B and one C.
        // Thus formed would be = 3 when all these conditions are met.
        int formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();
        int[] ans = {-1, 0, 0};

        // Look for the characters only in the filtered list instead of entire s.
        // This helps to reduce our search.
        // Hence, we follow the sliding window approach on as small list.
        while (r < filteredS.size()) {
            char c = filteredS.get(r).getValue();
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS.get(l).getValue();

                // Save the smallest window until now.
                int end = filteredS.get(r).getKey();
                int start = filteredS.get(l).getKey();
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1;
                    ans[1] = start;
                    ans[2] = end;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
    // The basic algo is of this type
    // 1. Use two pointers: start and end to represent a window.
    // 2. Move end to find a valid window.
    // 3. When a valid window is found, move start to find a smaller window.
    // This is the most optimal solution.
    // The idea is to only build the freq count for string T, slide left to right.
    // The smart thing is we decrement the frequency while scanning S so the charcter that are not in T will have negative frequency.
    // The character that are in T but occured more times (freq in S is greater than freq in T) in the window will also have negative
    // We also maintain a count of desired number of chars in T when that desired
    // Count becomes zero it means the window contain all the char in T
    public String minWindowV4(String s, String t) {
        int[] hash = new int[256];
        // initialize the hash map here
        for(int i = 0; i< t.length(); i++){
            hash[t.charAt(i)]++;
        }
        int desiderCount = t.length(); // check whether the substring is valid
        int left = 0; // two pointers, one point to tail and one  head
        int right = 0;
        int minSize = Integer.MAX_VALUE; // min length of  the substring
        int minL = 0; // store min left & min right
        int minR = 0;

        while(right < s.length()){
            // decrement freq count in S
            hash[s.charAt(right)]--;
            // if the the char was in T lets decrement the desired char count of T by 1
            if( hash[s.charAt(right)] >= 0){
                desiderCount--;
            }
            // we have found the desired chars of T in the window bounded by (left, right)
            // so lets try to shrink the window as we need to find a minimum window.
            while(desiderCount == 0){

                hash[s.charAt(left)]++;
                // note that only the hash of chars in T can become positive,
                // when found such positive it means the desired chars has appeared
                // more than once while shrinking the window so we can use the newest (current)
                // and discard everything on the left of it.
                // Hence make desired count increase by one to break the loop updating minSize
                // This desired count increase also allow us to to go back to main loop and
                // increase our right pointer to find an even smaller window if it exist
                if(hash[s.charAt(left)] > 0){
                    desiderCount++;
                }
                /* update min here if finding min*/
                if(minSize > right - left +1){
                    minSize = right - left + 1;
                    minL = left;
                    minR = right;
                }
                //increase left to make it invalid/valid again
                left++;
            }
            right++;

        }
        if (minSize == Integer.MAX_VALUE)
            return "";
        return s.substring(minL,minR+1);
    }

    //LeetCode 80 :: Remove Duplicates from Sorted Array II
    // In this approach we check if this duplicate is seend for the first time or not
    // if first time we add it to our new forming array, if not we just skip it
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;
        int cur = 1;
        boolean firstTime = true;
        for (int i = 1; i < nums.length; i++){
            if (nums[i] == nums[i-1] && firstTime) {
                nums[cur++] = nums[i];
                firstTime = false;
            } else if (nums[i] != nums[i-1]) {
                firstTime = true;
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }
    //  version 2 looks more cleaner, the trick here is to check back two step on the 'cur' position,
    //  cause cur point to the end of the new array at each iteration so we need to check back 2 steps on from the cur,
    // value gets overwritten so the cur gives you the correct position
    public int removeDuplicatesV2(int[] nums) {
        if (nums.length == 0)
            return 0;
        int cur = 0;
        for (int i = 0; i < nums.length; i++){
            if (cur < 2 || nums[i] != nums[cur-2])
                nums[cur++] = nums[i];
        }
        return cur;
    }

    // LeetCode :: 84. Largest Rectangle in Histogram (Hard)
    // This problem is different from trap Water & max area problem in Leetcode where
    // we could use left & right pointer to get a O(n) solution. This problem requires
    // us to calc the rectangle and for that the histogram bars needs to be adjacent.
    // So we need to use a different approach.
    // The idea is to find out for each i position the max left & max right we can span or cover
    // to create the rectangle. Max left is where height[leftMost] >= height[i] and for max right
    // height[rightMost] >= height [i]. We store the  leftmost & right most postion for i in two arrays left & right.
    // We only update left & right of i if the left & right bars are taller or equals bar i
    // Then we can calculate the area covered by the rectangle using (height[i] *  right[i] -left[i] +1).
    // We can iterate faster in left & right array if we iterate using the values of left & right array.
    public int largestRectangleArea(int[] heights) {
        // validate input
        if(heights == null || heights.length == 0) {
            return 0;
        }
        int []left = new int [heights.length]; // stores the max leftMost position spanned for each i
        int []right = new int [heights.length]; // stores the max rightMost position spanned for each i
        left[0] = 0;
        for (int i = 1; i < heights.length; i++) {
            int leftMost = i -1;
            // lets find such leftMost pos for i so that the leftMost bar >= bar i
            while (leftMost >= 0 &&
                    heights[leftMost] >= heights[i]){
                // note we are using the left arrays values to jump to the right
                // to find the leftMost this way er can iterate very fast
                leftMost = left[leftMost]  - 1;
            }
            // update the left array with leftMost for ith position so that for some
            // other i on the right we dont need to recalculate & jump faster
            left[i] = leftMost + 1;
        }
        right[heights.length -1] = heights.length -1;
        for (int i = heights.length -2 ; i >= 0; i--){
            int rightMost = i +1;
            // lets find such rightMost pos for i so that the rightMost bar >= bar i
            while (rightMost < heights.length &&
                    heights[rightMost] >= heights[i]){
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
        for (int i = 0; i < heights.length; i++){
            max = Math.max(max, heights[i] * (right[i] - left[i] +1));
        }

        return max;
    }


    // LeetCode :; 88. Merge Sorted Array
    // The idea is to compare entries from m-1 in nums1 & n-1 in nums2 positions
    // and put them in m + n - 1 in nums1
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int pos = m + n - 1;
        m --; n--;
        while ( pos >= 0) {
            if (m >= 0 && n >= 0 && nums1[m] >= nums2[n]){
                nums1[pos] = nums1[m--];
            } else {
                if (n >= 0)
                    nums1[pos] = nums2[n--];
            }
            // reduce pos here cause & not in array subscript
            // cause in that case pos after becoming zero wont decrement
            pos--;
        }
    }










}