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











}