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
    public List<Integer> spiralOrderV2(int[][] matrix) {
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

    // The main idea is to create a a direction array for for row & col
    // that will traverse the array in spiral order clockwise we use the direction arrays to
    // determine the next direction when we hit a boundary or already visited position we change
    // our direction according to the  row & col directin arrays
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> spList = new ArrayList<>();
        if (matrix.length == 0)
            return spList;

        int R = matrix.length;
        int C = matrix[0].length;
        int i = 1;
        boolean [][]visited = new boolean[R][C];
        int []rowDir = {0, 1, 0, -1};
        int []colDir = {1, 0, -1, 0};
        int r = 0, c = 0;
        int idx = 0;
        int nextR = 0;
        int nextC = 0;
        while (i <= R*C) {
            spList.add(matrix[r][c]);
            visited[r][c] = true;
            nextR = r + rowDir[idx];
            nextC = c + colDir[idx];
            if (nextR < 0 || nextR >= R || nextC < 0 || nextC >= C || visited[nextR][nextC]){
                idx = (idx+1) % 4;
                nextR = r + rowDir[idx];
                nextC = c + colDir[idx];
            }
            r = nextR;
            c = nextC;
            i++;
        }
        return spList;
    }

    // LeetCode :: 59. Spiral Matrix II
    // Same approach as above
    public int[][] generateMatrix(int n) {
        int []rDir = {0, 1, 0, -1};
        int []cDir = {1, 0, -1, 0};
        int [][]res = new int[n][n];
        int i = 1;
        int r = 0;
        int c = 0;
        int idx = 0;
        while (i <= n*n) {
            res[r][c] = i;
            int nextR = r + rDir[idx];
            int nextC = c + cDir[idx];
            if (nextR < 0 || nextC < 0 || nextR >= n || nextC >= n || res[nextR][nextC] != 0) {
                idx = (idx +1) % 4;
                nextR = r + rDir[idx];
                nextC = c + cDir[idx];
            }
            r = nextR;
            c = nextC;
            i++;
        }
        return res;

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
            if (nums[i] == 0 && !isZero) {
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
    // easy to read O(n) solution, we are checking if any previous value can cover the zeros for jump
    public boolean canJump2 (int []nums) {
        if (nums.length == 1)
            return true;
        int maxCover = 0;
        int cover = 0;
        for (int i = 0; i < nums.length; i++) {
            cover = i + nums[i];
            maxCover = Math.max(cover, maxCover);
            // check if any positive value before zero can cover the ith position
            // we keep i < nums.length -1 because last item dont need to cover.
            // We just need to reach for the last element [2,0,0] <-- true
            if (maxCover <= i && i < nums.length -1)
                return false;
        }
        return true;
    }

    // Leetcode 56:: Merge Intervals
    // FB phone interview problem
    // The idea is to sort the intervals first O(nlgn)
    // The we can just scan the intervals, if the right of the current interval can cover
    // the next interval we can absorb it in current interval. If not the we either update
    // the right if right is in between  the next interval. Other wise we got a new interval
    // NOTE:: *** Check The Implementation in PhoneIQ.java its more compact *******
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
    // NOTE:: *** Check The Implementation in PhoneIQ.java its more compact *******
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
    // We are to find the kth permutation in the sorted order
    // For 1 2 3 4 we can consider {1, (2, 3,4) perm } where (2 ,3 ,4) gives total 6  = 3! permutation
    // we can have the same for {2 , (1 ,3 ,4)} = 6 perm
    // we can use this idea to get to the kth perm
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
    // Check the version 2 its more clean
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
    // The idea is to create a state machine each state is defined by each options. We have four states here number, e,
    // dot, and (+/-). We are checking validity so we should concer ourslef about the invalid state transintions.
    // If any in valid state transition happens we return false. So for each state lets figure our invalid transitions
    // The number state is unique as it may not need any invalid transition cause we added another catch all state at the
    // end by using the default state (look at 'else'). The 'e' state is invalid if another e is seen before or no number
    // has been seen before. Similarly dot is invalid if dot is seen once berfore or dot is seen after e state.
    // So for problems with so many cases its often a good idea to create a state machine and handle all cases using
    // that. This make the code easy to read less error prone look at how v2 is way less clumsy then v1
    public boolean isNumberV2(String s) {
        boolean numSeen = false;
        boolean eSeen = false;
        boolean pointSeen = false;
        // trim the trailing and beginning whitespaces
        s = s.trim();
        int i = 0;

        while(i < s.length()) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                // number observed lets store this info
                numSeen = true;
            } else if (c == 'e') {
                // if e seen more than once or number not seen before e its and invalid e
                if (eSeen || !numSeen)
                    return false;
                eSeen = true;
                // we are doing this because we need to see atleast one number after 'e' to get a
                // valid string so when we exit the loop if numseen is false we observed something like
                // '1234e' which is not valid, a valid would be '1234e5'
                numSeen = false;
            } else if (c == '.'){
                // seen multiple dot or 'e' before .
                if (pointSeen || eSeen)
                    return false;
                pointSeen = true;
            } else if (c == '+' || c== '-') {
                // +/- is valid at the begining or just after valid e
                if (i != 0 && s.charAt(i-1) != 'e')
                    return false;
            } else {
                // seen anything else is invalid
                return false;
            }
            i++;
        }
        return numSeen;
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
    // Note :: Check the V2 version its more concise and better way to address the problem,
    //          Interviewers are looking for a solution of this kind.
    //          The basic idea is simple but how you organise your code is important.
    // The basic idea is to scan through the array and find out how many words
    // fit per line and  how many space per line in O(n). To do that we create and additional array which store
    // word count per line & another array that store total space per line. The we scan through the words array again
    // & build the line by getting  the word from words array using wordCount per line from strCount array also used
    // the space count per line to distribute the spaces
    // Note :: Check the V2 version its more concise and better way to address the problem,
    //          Interviewers are looking for a solution of this kind.
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

    //This version is mpre concise and I think interviewers would look for an approach like this for this problem.
    public List<String> fullJustifyV2(String[] words, int maxWidth) {
        int left = 0; List<String> result = new ArrayList<>();

        while (left < words.length) {
            int right = findRight(left, words, maxWidth);
            result.add(justify(left, right, words, maxWidth));
            left = right + 1;
        }

        return result;
    }

    private int findRight(int left, String[] words, int maxWidth) {
        int right = left;
        int sum = words[right++].length();

        while (right < words.length && (sum + 1 + words[right].length()) <= maxWidth)
            sum += 1 + words[right++].length();

        return right - 1;
    }

    private String justify(int left, int right, String[] words, int maxWidth) {
        if (right - left == 0) return padResult(words[left], maxWidth);

        boolean isLastLine = right == words.length - 1;
        int numSpaces = right - left;
        int totalSpace = maxWidth - wordsLength(left, right, words);

        String space = isLastLine ? " " : blank(totalSpace / numSpaces);
        int remainder = isLastLine ? 0 : totalSpace % numSpaces;

        StringBuilder result = new StringBuilder();
        for (int i = left; i <= right; i++)
            result.append(words[i])
                    .append(space)
                    .append(remainder-- > 0 ? " " : "");

        return padResult(result.toString().trim(), maxWidth);
    }

    private int wordsLength(int left, int right, String[] words) {
        int wordsLength = 0;
        for (int i = left; i <= right; i++) wordsLength += words[i].length();
        return wordsLength;
    }

    private String padResult(String result, int maxWidth) {
        return result + blank(maxWidth - result.length());
    }

    private String blank(int length) {
        return new String(new char[length]).replace('\0', ' ');
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

        dir.push("/");
        String [] dirList = path.split("\\/");
        for (int i = 0 ; i< dirList.length; i++){
            if (dirList[i].length() != 0) {
                if (dirList[i].equalsIgnoreCase("."))
                    continue;
                else if (dirList[i].equalsIgnoreCase("..")) {
                    if (!dir.peek().equalsIgnoreCase("/"))
                        dir.pop();
                }else {
                    dir.push(dirList[i]);
                }
            }
        }

        while (!dir.empty()&&dir.peek().equalsIgnoreCase("/")) {
            canonicalPathSb.append(dir.pop());
            canonicalPathSb.append("/");
        }
        return canonicalPathSb.reverse().toString();
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
    // sort color using the dutch national flag problem with using
    // the qsort pivot-partitioning approach. The idea is to use two pointer small & large
    // small puts the values smaller than pivot in proper position & large does the same for lareger values
    public void sortColorsV2(int[] nums) {
        int small = 0;
        int equal = 0;
        int large = nums.length -1;
        int pivot = 1;
        // the condition needs to equal <=  larger instead of equal < large to handle edge cases like 2 0 1
        while (equal <= large) {
            if(nums[equal] < pivot) {
                int temp = nums[equal];
                nums[equal] = nums[small];
                nums[small] = temp;
                small++;
                equal++;
            } else if (nums[equal] > pivot) {
                int temp = nums[equal];
                nums[equal] = nums[large];
                nums[large] = temp;
                large--;
            } else {
                equal++;
            }

        }
    }

    // LeetCode :: 209. Minimum Size Subarray Sum
    // The idea is to use two pointers (low & cur) to make a sliding window.
    // The distance between cur & low continues to increase until we reach the sum s.
    // After the any next addition to the window we discard the low as long as the
    // total sum is still >= s, and we reduce the current currentSum to adjust the window (cur <-> low gives the sum)
    // we keep track of the min window Size.
    // Here we can use a sliding window because "The array contains all positive numbers"
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

    // Same idea here we use sliding window same runtime & space.
    // But this version is more concise and easy to read.
    // It is written using the sliding window tempplate
    public int minSubArrayLen2(int s, int[] nums) {
        int left =0;
        int right = 0;
        int curSum = 0;
        int minLen = Integer.MAX_VALUE;
        while (right < nums.length) {
            curSum+= nums[right];
            while (curSum >= s) {
                minLen = Math.min(minLen, right -left +1);
                curSum -= nums[left];
                left++;
            }
            right++;
        }
        if (minLen == Integer.MAX_VALUE)
            minLen = 0;
        return minLen;
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
            char c = filteredS.get(r).getSecond();
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS.get(l).getSecond();

                // Save the smallest window until now.
                int end = filteredS.get(r).getFirst();
                int start = filteredS.get(l).getFirst();
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

    public String minWindowV5(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character ch : t.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        int left = 0, right = 0;
        int desiredCount = t.length();
        int min = Integer.MAX_VALUE;
        int []index = {-1, -1};
        while (right < s.length()) {
            Character ch = s.charAt(right);
            Integer count = map.get(ch);
            if (count != null) {
                if (count > 0)
                    desiredCount--;
                map.put(ch, count - 1);
            }
            while (desiredCount == 0) {
                ch = s.charAt(left);
                count = map.get(ch);
                if (count != null) {
                    if (count == 0)
                        desiredCount++;
                    count += 1;
                    map.put(ch, count);

                }
                if (min >= right -left +1) {
                    min = right -left +1;
                    index[0] = left;
                    index[1] = right;
                }
                left++;
            }
            right++;

        }
        if (index[0] == -1)
            return "";
        return s.substring(index[0],index[1] + 1);

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
    // This can also be solved by monotonic queue. But this one performs better than monotonic queue although
    // both having the same runtime. In interview it may be easy to explain monotonic queue and its runtime
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
            System.out.print(left[i] + " ");
        }
        System.out.println();
        // keep the rightmost index on to the rightmost entry
        right[heights.length -1] = heights.length -1;
        // now build the right array
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
            System.out.print(right[i] + " ");
        }
        int max = Integer.MIN_VALUE;
        // now find the max are covered using the left & right array to find the
        // width covered by each position i, height will be the heights[i]
        for (int i = 0; i < heights.length; i++){
            max = Math.max(max, heights[i] * (right[i] - left[i] +1));
        }

        return max;
    }
    // This time we solved this using the increasing monotonic Queue,
    // Note the it can also be solved using monotonic stack
    // Monotonic queue will also give amortized O(n) runtime
    public int largestRectangleAreaMonotonicQue(int[] heights) {
        if (heights.length == 0)
            return 0;
        int maxArea = Integer.MIN_VALUE;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i<heights.length; i++) {
            while(!deque.isEmpty() && heights[i] < heights[deque.peekLast()]) {
                int rightBoundIdx = deque.removeLast();
                // this is just index before our current max height position
                // from right index to i-1 everything can be consider in this iteration
                int leftBoundIdx = deque.isEmpty() ? -1: deque.peekLast();
                int width = i - 1 - leftBoundIdx;
                int area =  width * heights[rightBoundIdx];
                maxArea = Math.max(maxArea, area);
            }
            deque.addLast(i);
        }
        while (!deque.isEmpty()) {
            int rightBoundIdx = deque.removeLast();
            int leftBoundIdx = deque.isEmpty() ? -1: deque.peekLast();
            int width = heights.length - 1 - leftBoundIdx;
            int area =  width * heights[rightBoundIdx];
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    // FB practice question: contigious subArray starting or ending at at index,
    // find the total count  of such subarray per index
    public int[] maxContagiousSubArray(int[] heights) {

        int []left = new int [heights.length]; // stores the max leftMost position spanned for each i
        int []right = new int [heights.length]; // stores the max rightMost position spanned for each i
        left[0] = 0;
        for (int i = 1; i < heights.length; i++) {
            int leftMost = i -1;
            // lets find such leftMost pos for i so that the leftMost bar <= bar i
            while (leftMost >= 0 &&
                    heights[leftMost] <= heights[i]){
                // note we are using the left arrays values to jump to the right
                // to find the leftMost this way er can iterate very fast
                leftMost = left[leftMost]  - 1;
            }
            // update the left array with leftMost for ith position so that for some
            // other i on the right we dont need to recalculate & jump faster
            left[i] = leftMost + 1;
            System.out.print(left[i] + " ");
        }
        System.out.println();
        right[heights.length -1] = heights.length -1;
        for (int i = heights.length -2 ; i >= 0; i--){
            int rightMost = i +1;
            // lets find such rightMost pos for i so that the rightMost bar >= bar i
            while (rightMost < heights.length &&
                    heights[rightMost] <= heights[i]){
                // note we are using the right arrays values to jump to the right
                // to find the rightMost this way er can iterate very fast
                rightMost = right[rightMost] + 1;
            }
            // update the right array with rightMost for ith position so that for some
            // other i on the left we dont need to recalculate & jump faster
            right[i] = rightMost -1;
            System.out.print(right[i] + " ");
        }
        int max = Integer.MIN_VALUE;
        // now find the max are covered using the left & right array to find the
        // width covered by each position i, height will be the heights[i]
        int res[] = new int[heights.length];
        System.out.println();
        for (int i = 0; i < heights.length; i++){
            res[i] = right[i] - left[i] +1;
            System.out.print(res[i] + " ");
        }

        return res;
    }
    // The main function to find the maximum rectangular area under given
    // histogram with n bars
    static int getMaxArea(int hist[], int n)
    {
        // Create an empty stack. The stack holds indexes of hist[] array
        // The bars stored in stack are always in increasing order of their
        // heights.
        Stack<Integer> s = new Stack<>();

        int max_area = 0; // Initialize max area
        int tp; // To store top of stack
        int area_with_top; // To store area with top bar as the smallest bar

        // Run through all bars of given histogram
        int i = 0;
        while (i < n)
        {
            // If this bar is higher than the bar on top stack, push it to stack
            if (s.empty() || hist[s.peek()] <= hist[i])
                s.push(i++);

                // If this bar is lower than top of stack, then calculate area of rectangle
                // with stack top as the smallest (or minimum height) bar. 'i' is
                // 'right index' for the top and element before top in stack is 'left index'
            else
            {
                tp = s.peek(); // store the top index
                s.pop(); // pop the top

                // Calculate the area with hist[tp] stack as smallest bar

                area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);
                if(!s.empty())
                    System.out.println(tp + " " +  (i - s.peek() - 1) + " area " + area_with_top + " " +s.size());

                // update max area, if needed
                if (max_area < area_with_top)
                    max_area = area_with_top;
            }
        }

        // Now pop the remaining bars from stack and calculate area with every
        // popped bar as the smallest bar
        while (s.empty() == false)
        {
            tp = s.peek();
            s.pop();
            area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);
            if(!s.empty())
                System.out.println(tp + " " +  (i - s.peek() - 1) + " area " + area_with_top + " " +s.size());
            if (max_area < area_with_top)
                max_area = area_with_top;
        }

        return max_area;

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
    public int secondLargest (int nums[]) {
        int high = Integer.MIN_VALUE;
        int secondHigh = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++){
            if (nums[i] > high) {
                secondHigh = high;
                high = nums[i];
            } else if (nums[i] < high){
                secondHigh = Math.max(secondHigh, nums[i]);
            }
        }
        return secondHigh;
    }

    // LeetCode :: 414. Third Maximum Number
    public int thirdMax(int[] nums) {
        double high = -Double.MAX_VALUE;
        double secondHigh = -Double.MAX_VALUE;
        double thirdHigh = -Double.MAX_VALUE;
        for (int i = 0; i < nums.length; i++){

            if (nums[i] > high) {
                thirdHigh = secondHigh;
                secondHigh = high;
                high = nums[i];
            } else if (nums[i] < high && nums[i] > secondHigh){
                thirdHigh = secondHigh;
                secondHigh = nums[i];
            } else if (nums[i] < secondHigh) {
                thirdHigh = Math.max(thirdHigh, nums[i]);
            }
        }
        if (thirdHigh == -Double.MAX_VALUE)
            return (int)high;
        return (int)thirdHigh;
    }

    // LeetCode :: 118. Pascal's Triangle
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> rList = new ArrayList<>();
        if (numRows == 0)
            return rList;
        ArrayList <Integer> tempList = new ArrayList<>();
        tempList.add(1);
        rList.add(tempList);
        for (int i = 1 ; i< numRows; i++) {
            tempList = new ArrayList<>();
            for ( int j = 0; j <= rList.get(i-1).size(); j++) {
                if (j == 0 || j == rList.size())
                    tempList.add(1);
                else
                    tempList.add(rList.get(i-1).get(j-1) + rList.get(i-1).get(j));
            }
            rList.add(tempList);
        }

        return rList;
    }

    //LeetCode :: 121. Best Time to Buy and Sell Stock
    // This is the same as maxDiffInArrayLeft2Right in Solutions
    // Basically while scanning the array we have to find maxdiff so we need to keep track of current low
    // and if we see any val lower than current low we i[date the low, we also calc the diff for each item
    // with low and update the max diff
    public int maxProfitSingle(int[] prices) {
        if (prices.length == 0)
            return 0;
        int low = prices[0];
        int maxDiff = 0;
        for (int i = 1 ; i < prices.length; i++) {
            int diff = prices[i] - low;
            maxDiff = Math.max(maxDiff, diff);
            low = Math.min(low, prices[i]);
        }
        return maxDiff;
    }

    // LeetCode :: 122. Best Time to Buy and Sell Stock II
    // check the next implementation
    public int maxProfitMultuple(int[] prices) {

        if (prices.length == 0)
            return 0;
        int low = prices[0];
        int maxDiff = 0;
        int sumDiff = 0;
        boolean isUp = false;
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - low;
            if (maxDiff <= diff) {
                maxDiff = diff;
                isUp = true;
            } else {
                low = prices[i];
                if (isUp == true ){
                    sumDiff+= maxDiff;
                    isUp = false;
                    maxDiff = 0;
                }

            }
        }
        sumDiff += maxDiff;
        return sumDiff;
    }
    // same complexity but concise & easier to read more neat
    // for max profit  if the price increases at ith day compared
    // to i -1 th  day we can just sum them up. So if we want total
    // positive sum in an array we could use this approach of summing
    // up the positive increments
    public int maxProfitMultiple(int[] prices) {
        int sumDiff = 0;
        for (int i =1; i<prices.length; i++){
            if (prices[i] > prices[i-1])
                sumDiff+= prices[i] - prices[i-1];
        }
        return sumDiff;
    }
    // LeetCode :: 123. Best Time to Buy and Sell Stock III (Hard)
    // First assume that we have no money, so oneBuy means that we have to borrow money from others,
    // we want to borrow less so that we have to make our balance as max as we can(because this is negative).
    // oneBuyOneSell means we decide to sell the stock, after selling it we have price[i] money and we have to give back
    // the money we owed, so we have prices[i] + oneBuy, we want to make this max.
    // twoBuy means we already buy & sale once now buy 2nd time hence so the 2ndBuy will be OneBuyOneSell - price[i]
    // cause we can use some profit from the oneBuyOneSell.
    // twoBuyTwoSell means we want to sell stock2, we can have price[i] money after selling it, and we have
    // twoBuy money left before, so twoBuyTwoSell = twoBuy + prices[i]
    public int maxProfit(int[] prices) {
        int oneBuy = Integer.MIN_VALUE;
        int oneBuyOneSell = 0; // one buy and sell should never be negative we dont want negative profit
        int twoBuy = Integer.MIN_VALUE;
        int twoBuyTwoSell = 0; // 2nd buy & sale should never be negative as we dont want negative profit
        for(int i = 0; i < prices.length; i++){
            // we set prices to negative, so the calculation of profit will be convenient,
            // negative max means positive low
            oneBuy = Math.max(oneBuy, -prices[i]);
            // we already buy the stock so we sale only if it maximizes the profit,
            // we store max to get max profit
            oneBuyOneSell = Math.max(oneBuyOneSell, prices[i] + oneBuy);
            // we can buy the second only after first is sold,
            // use the profit from first buy & sale
            twoBuy = Math.max(twoBuy, oneBuyOneSell - prices[i]);
            // we bought twice so lets sale only if maximizes profit,
            // we store max to get max profit
            twoBuyTwoSell = Math.max(twoBuyTwoSell, twoBuy + prices[i]);
        }
        return Math.max(oneBuyOneSell, twoBuyTwoSell);
    }

    // LeetCode :: 215. Kth Largest Element in an Array
    public int findKthLargest(int[] nums, int k) {
        int item = nums.length - k;
        // we pass item here as the k-1 th smallest item because for kth smallest we pass k-1
        // so item = nums.length - k + 1 and item -1 == nums.length - k
        return Utilities.quickSelect(nums, 0, nums.length-1, item);
    }

    // LeetCode :: 128. Longest Consecutive Sequence (Hard)
    // The idea is to use a hashset to store all the numbers so that we can do a O(1) lookup,
    // we start with a number from nums array and check if the next number exist in the array using the hasset
    // if yes we increase the length. The hashset makes the lookup O(1), we also use another optimization;
    // if the prev number is already  in the array then we must have accounted for the current number already
    // so no need to process this number
    public int longestConsecutivev1(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        // create the hashset
        for (int i = 0; i <nums.length; i++)
            hashSet.add(nums[i]);
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int len = 0;
            // if the prev number is the hashset then we have accounted for this number already
            if (!hashSet.contains(current -1)){
                // lookup if the next numbers exist in the array/hashSet
                while (hashSet.contains(current)) {
                    current++;
                    len++;
                }
            }
            // update the max len
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    // LeetCode :: 130. Surrounded Regions
    private void solveRec(char [][] board, int row, int col) {
        if( row == board.length || col == board[0].length
                || row < 0 || col < 0 ||
                board[row][col] != 'O')
            return;
        board[row][col] = 'B';

        solveRec(board,row -1, col);
        solveRec(board,row +1, col);
        solveRec(board,row , col-1);
        solveRec(board,row , col+1);
    }
    public void solve(char[][] board) {
        if (board.length == 0)
            return;
        int numRows = board.length;
        int numCols = board[0].length;
        // mark the border O in column
        for (int i = 0; i<numRows; i++) {
            if (board[i][0] == 'O')
                solveRec(board,i,0);
            if (board[i][numCols -1] == 'O')
                solveRec(board,i,numCols -1);

        }
        // mark the border O in rows
        for (int i = 0; i<numCols; i++) {
            if (board[0][i] == 'O')
                solveRec(board,0,i);
            if (board[numRows -1][i] == 'O')
                solveRec(board,numRows -1,i);
        }
        // mark the Os to Xs & Bs to Os
        for (int i = 0; i<numRows; i++) {
            for (int j = 0; j<numCols; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if (board[i][j] == 'B')
                    board[i][j] = 'O';
            }
        }
    }

    // LeetCode :: 134. Gas Station
    // This is an interesting problem, the main catch here is there exist only one solution
    // So first we check if there is a solution exist or not if the total sum of the diff of
    // gas[i] & cost[i] is negative then there is no solution. If its positive there
    // exist only one solution. TO find the solution index we just start checking positive
    // sum(actually diff of gas(i) & cost(i)) from index 0 if at any index the sum becomes negative then we can discard
    // all index prior to the negative sum index also we can discard this negative sum
    // index and start our search from the nex index this brings the solution to O(n).
    // We dont need any kind of circular search in the array.
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int sum = 0;
        int max = 0;
        //check the sum of the diff if the sum is negative no solution
        for (int i = 0; i<gas.length; i++) {
            sum+= gas[i] - cost[i];
        }
        // negative sum no solution
        if (sum < 0)
            return -1;
        // so we have a positive sum of diff there exist only 1 solution
        sum = 0; // reuse sum
        int j = 0;
        int firstPositive = -1;
        // starting from index zero search the start of continuous
        // positive index till the end of the array
        while (j < gas.length) {
            sum+= gas[j] - cost[j];
            // sum is positive record this index & move forward to check
            // if all the next items are positive
            if (sum >= 0) {
                if (firstPositive == -1)
                    firstPositive = j;
            }
            else { // sum is negative, so discard everything up to this point
                firstPositive = -1;
                sum = 0; // sum was negative so discard this from our candidate starting index
            }
            j++;
        }
        return firstPositive;
    }

    // LeetCode ::  480. Sliding Window Median (Hard)
    public double[] medianSlidingWindow(int[] nums, int k) {
        double [] result = new double[nums.length -k +1];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i<nums.length; i++) {
            if (minHeap.size() <= maxHeap.size()) {
                maxHeap.add(nums[i]);
                minHeap.add(maxHeap.poll());
            } else {
                minHeap.add(nums[i]);
                maxHeap.add(minHeap.poll());
            }
            if(minHeap.size() + maxHeap.size() == k) {
                double median;
                if(minHeap.size() == maxHeap.size())
                    median = (minHeap.peek()+maxHeap.peek())/2.0;
                else
                    median = minHeap.peek();
                int start = i -k+1;
                result[start] = median;
                // we need to remove the start element of this K block of numbers as the sliding
                // window needs to move to the right, in the next iteration we add the next item in the
                // sliding window so remove the first/start item of the sliding window
                if (!minHeap.remove(nums[start])) {
                    maxHeap.remove(nums[start]);
                }

            }
        }

        return result;
    }

    // LeetCode :: 848. Shifting Letters
    // Check the solution v2
    public String shiftingLetters(String S, int[] shifts) {
        long[] lShifts = new long[shifts.length];
        if (shifts.length == 0)
            return "";
        lShifts[shifts.length - 1] = shifts[shifts.length - 1];
        for (int i = shifts.length - 2; i >= 0; i--) {
            lShifts[i] = shifts[i];
            lShifts[i] = lShifts[i] + lShifts[i + 1];
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (i = 0; i < S.length() && i < shifts.length; i++) {
            long rotate = lShifts[i] % 26l;
            sb.append((char) (((S.charAt(i) - 'a' + rotate) % 26) + 'a'));
        }

        return sb.toString();


    }

    // faster solution no need calc the cumulative count we can traverse
    // from the right and use a single var to calc cumulative shift
    private char getTheChar(char c, int shift) {
        return (char)(((c-'a'+shift)%26)+'a');
    }
    public String shiftingLettersV2(String S, int[] shifts) {
        char[] ans = S.toCharArray();
        int prevStep = 0;

        for(int i = ans.length-1; i >= 0; i--) {
            prevStep += (shifts[i]%26);
            ans[i] = getTheChar(ans[i],prevStep);
        }

        return new String(ans);
    }

    // Adnan Aziz 14.1
    public  ArrayList<Integer> intersectTwoSortedArrays(int []nums1, int []nums2) {
        int i = 0;
        int j = 0;
        ArrayList<Integer> resList = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]){
                i++;
            } else {
                if (resList.size() == 0)
                    resList.add(nums1[i]);
                else if(resList.get(resList.size()-1) != nums1[i]) {
                    resList.add(nums1[i]);
                }
                i++;
                j++;
            }
        }
        return resList;
    }
    public  ArrayList<Integer> mergeTwoSortedArrays(int []nums1, int []nums2){
        int i = 0;
        int j = 0;
        ArrayList<Integer> resList = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                resList.add(nums1[i]);
                i++;
            } else {
                resList.add(nums2[j]);
                j++;
            }

        }
        if (i == j)
            return resList;
        if (i == nums1.length) {
            while(j<nums1.length) resList.add(nums1[j++]);
        } else if (j == nums2.length) {
            while(i < nums1.length) resList.add(nums1[i++]);
        }

        return resList;
    }

    // LeetCode ::150. Evaluate Reverse Polish Notation
    // The idea is to push operands in stack until we get a operator,
    // then we remove two item from the top of the stack use the operator to perform the operation
    // and put the result in stack we never put the operator in stack
    public int evalRPN(String[] tokens) {
        int rightOprnd =0;
        int leftOprnd = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        for (String tk : tokens) {
            int result = 0;
            if (tk.equals("+")) {
                rightOprnd = stack.pop();
                leftOprnd = stack.pop();
                result = leftOprnd + rightOprnd;
            }
            else if (tk.equals("-")) {
                rightOprnd = stack.pop();
                leftOprnd = stack.pop();
                result = leftOprnd - rightOprnd;
            }
            else if (tk.equals("*")) {
                rightOprnd = stack.pop();
                leftOprnd = stack.pop();
                result = leftOprnd*rightOprnd;
            }
            else if (tk.equals("/")) {
                rightOprnd = stack.pop();
                leftOprnd = stack.pop();
                result = leftOprnd/rightOprnd;
            }
            else {
                result = Integer.parseInt(tk);
            }
            stack.push(result);

        }
        return stack.isEmpty() ? 0 :stack.peek();

    }

    //LeetCode :: 151. Reverse Words in a String
    private void revreseCharArray(char str[], int start, int end) {
        while (start < end) {
            char ch = str[start];
            str[start] = str[end];
            str[end] = ch;
            start++;
            end--;
        }
    }
    private void reverseStringBuilder (StringBuilder sb, int start, int end) {
        while (start < end) {
            char ch = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, ch);
            start++;
            end--;
        }
    }
    public String reverseWords(String s) {

        StringBuilder sb = new StringBuilder();
        int end = s.length() -1;
        int start = 0;
        while (start < s.length() && s.charAt(start) ==' ')
            start++;
        while (end >=0 && s.charAt(end) == ' ')
            end--;
        for (int i = end; i>= start; i--) {
            if (s.charAt(i) != ' ')
                sb.append(s.charAt(i));
            else {
                if (i - 1>= start && s.charAt(i-1) != s.charAt(i)) {
                    sb.append(' ');
                }
            }
        }

        int right = 0;
        int left = 0;
        while (right < sb.length()) {
            while (right < sb.length() && sb.charAt(right) != ' ')
                right++;
            reverseStringBuilder(sb, left, right -1);
            right++;
            left = right;
        }
        return sb.toString();
    }

    // LeetCode :: 152. Maximum Product Subarray
    // The idea is if there is even number of negative numbers we include them all. if there are odd number negative
    // number we have to give one up, so which one to give up we can only give up the first or the last thats why we
    // need two passes in one pass we give up last and in another pass we give up the first. Consider you have a zero
    // in the middle it will reset the the max product so zero will actually partition the array in smaller sub arrays
    // which could have odd/even neagative number but they are handled in the same way in two passes.
    // We will store the max on each subarray even if they are separated using zeros
    public int maxProduct(int[] nums) {
        int curProd = 1;
        int maxProd = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            curProd *= nums[i];
            maxProd = Math.max(maxProd, curProd);
            if (curProd == 0)
                curProd = 1;
        }
        curProd =1;
        for (int i = nums.length -1; i>=0; i--) {
            curProd*= nums[i];
            maxProd = Math.max(maxProd, curProd);
            if (curProd == 0)
                curProd =1;
        }

        return maxProd;

    }
    // This is another interesting solution  interesting here we just need one pass
    // we save imax & imin for for upto A[i], when A[i] is negative we swap them so on even
    // number of negative numbers we store the total product including the last negatives
    // Try an example by hand
    int maxProductV2(int A[], int n) {
        // store the result that is the max we have found so far
        int r = A[0];

        // imax/imin stores the max/min product of
        // subarray that ends with the current number A[i]
        for (int i = 1, imax = r, imin = r; i < n; i++) {
            // multiplied by a negative makes big number smaller, small number bigger
            // so we redefine the extremums by swapping them
            if (A[i] < 0) {
                int temp = imax;
                imax = imin;
                imin = temp;
            }

            // max/min product for the current number is either the current number itself
            // or the max/min by the previous number times the current one
            imax = Math.max(A[i], imax * A[i]);
            imin = Math.min(A[i], imin * A[i]);

            // the newly computed max value is a candidate for our global result
            r = Math.max(r, imax);
        }
        return r;
    }

    // LeetCode :: 165. Compare Version Numbers
    // Use the  str.split("\\.") to split the strings in to tokens then just compare them
    //
    public int compareVersion(String version1, String version2){
        // if one of them is empty we can just return as below
        if (version1.length()== 0 || version2.length()== 0)
            return version1.length() - version2.length();
        // split the version strings
        String [] vs1 = version1.split("\\.");
        String [] vs2 = version2.split("\\.");
        int i = 0;
        int x1 =0, x2 =0;

        while (i < vs1.length || i < vs2.length) {
            // if we reach the end of vs1 or vs2 we just  get zero value
            // for those index as the other string may still have values
            x1 = i < vs1.length ? Integer.parseInt(vs1[i]) :0;
            x2 = i <vs2.length ?Integer.parseInt(vs2[i]) :0;
            if (x1 < x2)
                return -1;
            else if (x1 > x2)
                return 1;
            i++;
        }
        return 0;
    }

    // LeetCode :: 166. Fraction to Recurring Decimal
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0)
            return "0";

        if (denominator == 0)
            return "NaN";

        StringBuilder number = new StringBuilder();

        long numr = Math.abs((long)numerator);
        long  denom = Math.abs((long)denominator);
        if (numr  < 0 ^ denom < 0)
            number.append("-");


        int decimal = numerator/denominator;
        number.append(decimal);

        if (numr% denom == 0)
            return number.toString();

        number.append(".");

        long remainder = (long) (numr % denom);
        HashMap<Long, Integer> map = new HashMap<>();

        while (remainder > 0) {
            int idx = map.getOrDefault(remainder, -1);
            if (idx != -1) {
                number.insert(idx, "(");
                number.append(")");
                break;
            }
            System.out.println(remainder +" "+ denom);
            map.put(remainder,number.length());
            remainder *= 10;
            number.append(remainder/denom);
            remainder %=denom;
        }

        return number.toString();
    }

    // LeetCode :: 187. Repeated DNA Sequences
    public List<String> findRepeatedDnaSequences(String s) {
        HashMap<String,Integer> dnaMap = new HashMap<>();
        List<String> resList = new ArrayList<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String subStr = s.substring(i,i+10);
            int count = dnaMap.getOrDefault(subStr, 0);
            if (count == 1)
                resList.add(subStr);
            dnaMap.put(subStr,count +1);
        }
        return resList;
    }

    private int nextHash (int []hashCode, int i, char []str, int len) {
        int oldHash = hashCode[i+len-2];
        int oldChar = str[i-1] -'A' +1;
        int newChar = (str[i+len -1] -'A' + 1);
        int newHash = (oldHash - oldChar)/3 ;
        newHash += newChar * (int)Math.pow(3,len -1);
        return newHash;
    }
    // Using Rolling Hash the Prime is P = 3 if we want to increase
    // the prime we need to change the hashcode array to long otherwise overflow will happen
    public List<String> findRepeatedDnaSequencesV2(String s) {
        List<String> resList = new ArrayList<>();
        if (s.length() < 10)
            return resList;
        char[]str = s.toCharArray();
        int [] hashCode = new int [str.length];
        HashMap<Integer,Integer> dnaMap = new HashMap<>();

        hashCode[0] = str[0] -'A' +1;
        for (int i = 1; i< 10;i++){
            hashCode[i] += (str[i] -'A' + 1)* (int) Math.pow(3, i) + hashCode[i-1];
        }

        dnaMap.put(hashCode[9],1);
        for (int i = 1; i <= s.length() - 10; i++) {
            int newHash = nextHash(hashCode,i,str,10);
            hashCode[i+10-1] = newHash;
            int count = dnaMap.getOrDefault(newHash, 0);
            if (count == 1)
                resList.add(s.substring(i, i+10));
            dnaMap.put(newHash, count +1);
        }
        return resList;
    }













}