import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;


public class DPs {

    /***
     * Notes: Donot confuse divide & conquer with DP. If we divide a problem into two halves and solve
     * the problem in halves its actually divide & conquer.
     * For DP we need overlapping sub problems, & combinning the subproblem creates a solution to the orginal problem
     * DP can be solved using top down memoization approach or bottom UP approach
     *
     ***/

    //Leetcode :: 53  Maximum SubArray
    // if the value of the ith position is greater than the sum of the left side + the current val
    // the we can just discard the left side of the array.
    // We keep track of the max sum on each update of current sum which gurantees that we wend up with max SUm
    // This is also a DP problem notice in here we based our solution of ith element based on the solution of i-1
    // element hence using overlapping sub problems
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

    // CTCI :: 17.23 Max Sub matrix
    // We need to find the maximum submatrix sum in a 2D array.
    // We use the maximum sub array sum  to get the submatrix sum
    // here we go by each row and then calc max subarray sum for it
    // To cover all sorts of matrix we try all combination rows for example
    // we use row 0 only then row 0 and row1 ,then row 0, 1 and 2 and so on.
    // Next we try row 1 only then row 1 & 2  then row 1 ,2 , 3 and so on.
    // Next we try row 2 only then row 2 & 3 and so on this way we cover all
    // combination to get the maximum sum sub matrix
    public int maxSubMatrixSum (int [][] matrix) {
        int currSum = Integer.MIN_VALUE;
        for (int rowSt = 0; rowSt < matrix.length; rowSt++) {
            int []partialSum = new int[matrix[0].length];
            for(int rowCur = rowSt; rowCur < matrix.length; rowCur++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    partialSum[col] += matrix[rowCur][col];
                }
                currSum = Math.max(currSum, maxSubArray(partialSum));
            }
        }
        return currSum;
    }

    public int maxSubArrayWithIndex(int[] nums) {

        int maxSum = nums[0];
        int currSum = nums[0];
        int startIdx = 0;
        int endIdx = 0;
        int tid = 0;

        for (int i = 1; i < nums.length; i++) {
            currSum += nums[i];
            // the current number is actually greater than the cumulative sum so discard everything
            // on the left and update the current sum to current value as its bigger
            if (nums[i] >= currSum) {
                currSum = nums[i];
                tid = i;
            }
            if (maxSum < currSum) {
                maxSum = currSum;
                startIdx = tid;
                endIdx = i;

            }
        }
        return maxSum;
    }

    // LeetCode :: 10. Regular Expression Matching
    // The idea is to use a DP solution similar to the wild card matching but here the case is little different than
    // wildcard matching. Here we are matching regular expression so 'a*' can match empty string '' or 'a' or 'aaaaa'
    // but 'a*' cannot match any char other than a's repeat or empty sequence.
    // The DP equation is:
    // dp [i][j] = dp[i-1][j-1] iff s(i) == p(j) or p(j) == '.'
    // dp [i][j] = dp[i][j-2] | (dp[i-1][j] && s(i) == p(j-1)) iff p(j) == '*'
    public boolean isMatch(String s, String p) {
        boolean [][]dp  = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 2; i <= p.length(); i++) {
            if (p.charAt(i-1) == '*')
                dp[0][i] = dp [0][i-2];
        }

        for (int i = 1; i < dp.length; i++){
            for (int j = 1; j < dp[0].length; j++) {
                // the string and pattern char matches or pattern has '.' (dot matches any single char)
                if ((s.charAt(i-1) == p.charAt(j-1)) || p.charAt(j-1) == '.')
                    dp[i][j] = dp [i-1][j-1];
                // the pattern has * so we need special processing
                else if (p.charAt(j-1) == '*') {
                    // dp[i][j-2] presents an empty sequence for 'a*' we have to do j-2 as 'a*' is two chars
                    // the rest of the half represents 'a*' as single 'a' or multiple a's 'aaaaa'
                    // the dp[i-1][j] considers a non empty sequence of chars for '*' but we need to  make sure the char
                    // before '*' in pattern matches the current char in S.
                    // first we check if the char before * matches the current char in string s by s(i-1) == p(j-2)
                    // or p(i-2) is a dot which matches any char

                    dp[i][j] = dp[i][j-2] || (dp[i-1][j] &&
                            (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.'));
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // LeetCode:: 44 WildCard Matching (Hard)
    // The Solution we will be using is  DP solution to the problem

    public boolean isWildCardMatch(String s, String p) {
        char [] text = s.toCharArray();
        char [] pattern = p.toCharArray();

        // skip the multiple occurrences of * in patter for example a***b***c becomes a*b*c
        int reWrite = 0;
        for(int i = 0; i < pattern.length; i++) {
            if ( i + 1 <pattern.length && pattern[i] == '*' && pattern[i+1] == pattern[i])
                continue;
            pattern[reWrite++] = pattern[i];

        }
        // Create the boolean array
        // The dpTable rows represent text & column represents pattern.
        // The taxt & pattern will start at position 1.
        // So row 0 represents empty text & column zero represents empty pattern.
        // At any time dpTable[i][j] represents  if text & pattern is a 'valid match'
        // for text of length i & pattern of length j.
        // Next we need to build up the dpTable.
        boolean [][] dpTable = new boolean[text.length + 1][reWrite + 1];

        // empty text & pattern will always be valid match so dpTable[0][0] is true
        dpTable[0][0] = true;
        // if the pattern start with * it can match and empty string so mark that in the dpTable[0][1].
        if (reWrite > 0 && pattern[0] == '*')
            dpTable[0][1] = true;

        for (int i = 1; i < dpTable.length; i++) {
            for(int j = 1; j < dpTable[0].length; j++){
                // if the text char mathces  the pattern char or the pattern char is '?'
                // so this is already a match we just need to check recursively
                // the previous one so check dptable[i-1][j-1]
                // if the pattern is '*' there are two options one is '*'
                // represents zero char wo we pick dpTable[i][j-1] or
                // we take dptable[i-1][j] where * takes i-1 value meaning * is non zero sequence and
                // we can match the the current char with * so lets check if we can match * with i-1 dpTable[i-1][j]
                if (text[i-1] == pattern[j-1] || pattern[j-1] == '?') {
                    dpTable[i][j] = dpTable[i-1][j-1];
                } else if(pattern[j-1] == '*') {
                    dpTable[i][j] = dpTable[i-1][j] || dpTable[i][j-1];
                }
            }
        }

        return dpTable[text.length][reWrite];

    }

    // using Yu's method to solve the wildcard problem, This is faster than the DP solution
    // http://yucoding.blogspot.com/2013/02/leetcode-question-123-wildcard-matching.html
    // The idea is to use two pointer for text &  pointers for pattern.
    // When we see a match or '?' we move the regular pointes s & p.
    // But when we see '*' it means we can match zero or more so we store the '*' position in another pointer
    // starIdx & advance p pointer by 1 we also store the matched s pointer position
    boolean isWildCardMatchV2(String str, String pattern) {
        // lets create two pointers one for pattern & 1 for string
        int s = 0, p = 0, match = 0, starIdx = -1;
        while (s < str.length()){
            //System.out.println(s+ " " + p);
            // advancing both pointers
            if (p < pattern.length()  && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
                s++;
                p++;
            }
            // * found, only advancing pattern pointer
            // but before that store current pattern pointer position.
            // This pattern pointer position will be saved so that
            // we can match next zero or multiple chars of text with the '*'
            else if (p < pattern.length() && pattern.charAt(p) == '*'){
                starIdx = p++;
                match = s;
            }

            // current characters didn't match, last pattern pointer was *,
            // current pattern pointer is not '*'
            // last pattern pointer was '*',
            // advancing string pointer & reset patter pointer to point to position after '*'
            else if (starIdx != -1){
                p = starIdx + 1;
                s = ++match;
            }
            // current pattern pointer is not star, last pattern pointer was not *.
            // so the characters do not match hence we found invalid case
            else {
                return false;
            }
        }
        //System.out.println(s+ " " + p);
        // we already reached the end of text
        // check for remaining characters in pattern.
        // If all of them are * then we will find a valid solution; for example
        // pattern is a**b**c***** and text is abc; the below code check for trailing '*'
        // otherwise the pattern has more unmatched char no valid solution; for example
        // if pattern is a**b**c****k & text abc ; then we wont find valid match
        while (p < pattern.length() && pattern.charAt(p) == '*')
            p++;

        return p == pattern.length();
    }

    //LeetCode 62 Unique Path
    int pathCount = 0;
    private void uniquePathRec(int curRow,int  curCol, int maxRow, int maxCol) {

        if(curRow == maxRow && curCol == maxCol) {
            pathCount++;
            //System.out.println(pathCount);
            return;
        }
        if (curRow < maxRow && curCol <= maxCol)
            uniquePathRec(curRow+1, curCol, maxRow, maxCol);
        if (curCol < maxCol && curRow <= maxRow)
            uniquePathRec(curRow, curCol + 1, maxRow, maxCol);
    }

    public int uniquePaths(int m, int n) {
        if(m == 0 || n ==0)
            return 0;
        uniquePathRec(1,1, m, n);
        return pathCount;
    }

    // UniquePath V2 using a DP solution
    // pathcount at position i,j can be calculated by adding
    // the ways we can reach the up & left grids so the DP solution will as follows
    // pathcount(i,j) = patchcount(i-1,j) + pathcount(i,j-1)
    public int uniquePathsV2(int m, int n) {
        int [][] pathCount= new int[n][m];
        // set the 1st row & col to 1as there only 1 ways to reach
        for (int i = 0; i<m; i++)
            pathCount[0][i] = 1;
        for (int i = 0; i<n; i++)
            pathCount[i][0] = 1;
        // calculate the pathcount for all other positions using the DP
        for(int i = 1; i < n; i++){
            for(int j = 1; j < m; j++){
                pathCount[i][j] = pathCount[i-1][j] + pathCount[i][j-1];
            }
        }
        return pathCount[n-1][m-1];
    }

    // LeetCode 63 :: Unique Path II
    // The basic idea is same as the Unique Path algo,
    // pathcount at position i,j can be calculated by adding
    // the ways we can reach the up & left grids so the DP solution will as follows
    // pathcount(i,j) = patchcount(i-1,j) + pathcount(i,j-1) (if the there is no obstacle)
    // if there is an obstacle at (i,j) we can block that in pathCount(i,j) = 0,
    // so that position will always be skipped by the  to tal pathcount
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid.length == 1 && obstacleGrid[0].length == 1)
            return 0;
        int m = obstacleGrid[0].length;
        int n = obstacleGrid.length;
        int [][] pathCount= new int[n][m];

        // set starting position as based on the obstacle
        pathCount[0][0] = obstacleGrid[0][0] == 1? 0 : 1;
        // set the 1st row & col count based on the obstacle,
        // if the we observed a block in previous position (i-1)
        // then we cannot actually access i so we set pathCount to zero
        for (int i = 1; i < m; i++){
            if (obstacleGrid[0][i] == 1 || pathCount[0][i-1] == 0)
                pathCount[0][i] = 0;
            else
                pathCount[0][i] = 1;
        }
        // repeat the same for rows
        for (int i = 1; i < n; i++) {
            if (obstacleGrid[i][0] == 1 || pathCount[i-1][0] == 0)
                pathCount[i][0] = 0;
            else
                pathCount[i][0] = 1;
        }

        // calculate the pathcount for all other positions using the DP
        // pathcount will be zero if there is an obstacle at (i,j)
        for(int i = 1; i < n; i++){
            for(int j = 1; j < m; j++){
                if (obstacleGrid[i][j] == 1)
                    pathCount[i][j] = 0;
                else
                    pathCount[i][j] = pathCount[i-1][j] + pathCount[i][j-1];
            }
        }


        return pathCount[n-1][m-1];
    }

    // LeetCode 64 :: Minimum Path Sum
    // Same approach similar to unique path in grid problem
    // We create a DP array misSum that holds the minSum value at postion (i,j)
    // So that, minSum(i,j) = grid(i,j) + Min(minSum(i-1,j) , misSUm(i,j-1))
    // Here grid(i,j) is the pathcost at (i,j) its supplied as an input in param as grid array
    public int minPathSum(int[][] grid) {
        int [][]minSum = new int [grid.length][grid[0].length];
        // set the starting postion as the grid value
        minSum[0][0] = grid[0][0];
        // init the first row & column, the value at position i will be the sum of its
        // value on grid + the value of minSum of prev position
        for (int i = 1; i < grid[0].length; i++) {
            minSum[0][i] = grid[0][i] + minSum[0][i-1];
        }
        // do the same for the row
        for (int i = 1; i < grid.length; i++) {
            minSum[i][0] = grid[i][0] + minSum[i-1][0];
        }
        // lets build the minSum array based on our DP property,
        // minSum(i,j) = grid(i,j) + Min(minSum(i-1,j) , misSUm(i,j-1))
        // remember the only valid moves are right & down from a position.
        // But we are considering how to reach (i,j) hence the valid positions
        // to consider at (i,j) is coming from Up & left
        // so for position (i,j) we consider (i-1,j) up & (i,j-1) left
        for (int i = 1; i < grid.length; i++) {
            for(int j = 1; j <grid[0].length; j++){
                minSum[i][j] = grid[i][j] + Math.min(minSum[i-1][j], minSum[i][j-1]);
            }
        }
        return minSum[grid.length -1][grid[0].length-1];

    }

    //LeetCode :: 70 climb stairs
    // Simple DP solution the current step can be calculated by the last possible steps
    // here the option is to take 1 & 2 steps so to calc number of ways at n step using the
    // dp is ways[n] = ways[n-1] + ways [n-2]
    public int climbStairs(int n) {
        int []ways = new int[n +1];
        ways[0] = 0;
        ways[1] = 1;
        ways[2] = 2;
        for (int i = 3; i <= n; i++) {
            // the ways at current step is the sum of the last two steps
            ways[i] = ways[i-1] + ways[i-2];
        }
        return ways[n];

    }
    // optimized it to match the fibinacci optimization
    // one interesting point specially for this problem its similar to fibonacci as
    // we make w(i) = w(i-1) + w(i-2)
    public int climbStairsOptimized(int n) {
        int last = 1;
        int lastLast = 0;
        for (int i = 0; i <= n; i++) {
            // the ways at current step is the sum of the last two steps
            int tmp = last;
            last = last + lastLast;
            lastLast = tmp;
        }
        return last;

    }


    // Leetcode :: 72 Edit Distance (Hard)
    // This is also know as the THE LEVENSHTEIN DISTANCE PROBLEM
    // This requires a DP solution. The DP solution is  if the the current character from word1 & word2
    // is same then we dont need to anything so we get the dp value from the previous distanceDP
    // as if this matching characters were not there. If the chars do not match then there are three options
    // add/delete/replace. add is i-1, j delete i, j-1 & replace i-1,j-1 , we need to pick the minimum of
    // the three and add 1 to it.
    // The dps eqn dp(i,j) = dp (i-1,j-1) if s[i] = r[j]
    //             dp (i,j) = min(dp(i-1,j-1), dp(i,j-1), dp(i-1,j)) +1 if s[i]!=r[j]
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int [][] distanceDP = new int [m+1][n+1];
        // init the distanceDP array
        for (int i = 0; i <= m; i++)
            distanceDP[i][0] = i;
        for (int i = 0; i <= n; i++)
            distanceDP[0][i] = i;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // string are 0 index based that's why i-1 j-1 used in chatAt
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    distanceDP[i][j] = distanceDP[i-1][j-1];
                } else {
                    int tempMin = Math.min(distanceDP[i-1][j-1], distanceDP[i][j-1]);
                    distanceDP[i][j] = Math.min(tempMin, distanceDP[i-1][j]) + 1;
                }
            }
        }

        return distanceDP[m][n];
    }

    // LeetCode :: 91. Decode Ways
    // The idea is to use a DP solution similar to climb ways, this one has more constrains
    // so we need to take all of them in consideration. Constrains are the curr & prev has to be between  1 -26
    // if the constrain is met the solutions is similar to climb ways dp [i] = dp[i-1] + dp[i-2]
    public int numDecodings(String s) {
        int []decodeCount = new int[s.length() + 1];
        if(s.length() == 0 || s.charAt(0) == '0')
            return 0;
        decodeCount[0] = 1;
        decodeCount[1] = 1;
        int j = 2;
        for (int i = 1; i < s.length(); i++) {
            int curr = s.charAt(i) - '0';
            int prev = s.charAt(i - 1) - '0';
            // the value is of the form 00 or 30 or 40 ... so basically only 10 & 20 is supported
            if ((prev == 0 && curr == 0)  || (curr == 0 && prev >2)){
                return 0;
            } else if(curr == 0) {
                // value is either 10 or 20 so we get decodeCount
                // from two positions back as 10 or 20 are two chars,
                // as no new decoding is possible
                decodeCount[j] = decodeCount[j-2];
            } else if (prev == 0 || (prev*10 + curr > 26)){
                // value is of the form 35 or 105 , so we can decode from the
                // last position as no new decoding is possible
                decodeCount[j] = decodeCount[j-1];
            } else {
                // constrain met so we can use our dp eqn for constrain met
                // new decoding is possible value is of the form 11-26 except 20
                // so we get the decode by adding the last two decodes
                decodeCount[j] = decodeCount[j-1] + decodeCount[j-2];
            }
            j++;

        }
        return decodeCount[s.length()];
    }

    // LeetCode :: 96. Unique Binary Search Trees
    // The idea is to use a DP solution here.
    // The base case for bstCount[0] = 1 && bstCount[1] == 1 (if 1 node there is only 1 tree possible)
    // Next we need to figure for 1 to n for selecting each of n items as the root how many tree are possible
    // for example lets do it for 3 , if 1 is root then there are 2 trees, 2 is root there is 1 tree, if 3 is
    // root there is 2 tree so total of 5. Now  1 ways--->()1 (2,3)<--2 ways , 1 ways -->(1)2(3)<-- 1 ways
    // 2 ways --> (1,2)3()<--- 1 ways, so if we know for 0 to n-1 how many trees we can use that to calc nth tree
    // by iterative selecting each item from 1 to n as a root an calc there bstCount and the combined sum would be
    // bstCount[n] = sum1Ton-1( bstCount[j-1] * bstCount[n-j])
    // This is actually the catalan numbers we can use the same code to count the number of n pair valid parenthesis

    public int numTrees(int n) {
        int [] bstCount = new int[n+1];
        // the base case for zero node is 1
        // we could have init bstCount[1] too but thus would be handled in the loop hence no need
        bstCount[0] = 1;

        for (int i = 1; i <=n; i++){
            for (int j = 1; j <= i; j++) {
                bstCount[i] += bstCount[j-1] * bstCount[i-j];
            }

        }
        return bstCount[n];
    }

    // LeetCOde :: 120. Triangle
    // The idea is to use a bottom Up DP approach
    // We can start with the bottom layer as path then on each layer we check each item we check minimum
    // path length and store it in current path. As the for the bottom layer there is nothing to calc so for other layer
    // the dp eqn is path[k-1][i] = min(path[k][i] + path[k][i+1]) + triangle[k][i] (for k = size - 2 to zero )
    // look how we are using  a 2d array to store all the path info but we can optimize this and use single array with
    // the optimised eqn  path[i] = min(path[i] + path[i+1]) + triangle[k-1][i] (we are overwriting the path array)
    // We could have used a top-down approach but in that case we needed to do another O(n) search at the end to
    // find the smallest in path array also we will need a 2D array to solve the overwriting problem
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 0)
            return 0;
        List <Integer> ls = triangle.get(triangle.size()-1);
        int []path = new int[ls.size()];
        // set up the initial path array with the last layer of triangle
        for (int i = 0 ; i<ls.size(); i++)
            path[i] = ls.get(i);
        // we can start with size -2 as for the last layer does not change path is the layer itself
        int k = triangle.size() - 2;
        // calc the path len in a bottom up approach
        while (k >= 0) {
            List<Integer> list = triangle.get(k);
            for (int i = 0; i < list.size(); i++) {
                // Note actual dp eqn path[k-1][i] = min(path[k][i] + path[k][i+1]) + triangle[k-1][i]
                // optimized dp eqn path[i] = min(path[i] + path[i+1]) + triangle[k][i]
                path[i] = Math.min(path[i],path[i+1]) + list.get(i);
            }
            k--;
        }
        // we calc the path in a bottom up manner hence path[0] will have
        // the result as at the top there us one element
        return path[0];
    }

    // LeetCode :: 377. Combination Sum IV
    // This is the same as climb  number of ways problem check climbStairs method in this file
    // The DP eqn is to calc number of ways to reach a target give a set of numbers
    // like climb stairs here also we can reach ways[i] by combining the ways[i-2], ways[i-5], ways[i-10] and so on ...
    // given that the set numbers is [2, 5, 10, .....]. Try to look  at this in this way when we reach ways[i-5]
    // we can ways[i-5] + 5 to get to ways[i] similarly for the rest of nums in the set so the DP
    // equation DP[i] = DP[i] + DP(i - nums[j]) for all j in set of nums
    public int combinationSum4(int[] nums, int target) {
        int []ways = new int[target + 1];
        ways[0] = 1;
        for (int i = 1; i < ways.length; i++) {
            for (int j = 0; j < nums.length;j++) {
                // we don't want to process if the current value is smaller the nums[j]
                if (i >= nums[j]) {
                    ways[i]+= ways[i -nums[j]];
                }
            }
        }
        return ways[target];
    }

    // LeetCode :: 518. Coin Change 2 ; find number of ways the set of coins can add up to the target
    // Coin change problem; use DP approach to solve this check the version2 we discuss both this & v2 there
    // version 2 is the space optimised version
    public int change(int amount, int[] coins) {
        int [][]ways = new int [coins.length+1][amount + 1];
        for (int i=0; i<= coins.length; i++) {
            ways[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if(j >= coins[i-1]) {
                    ways[i][j] =  ways[i-1][j] + ways[i][j-coins[i-1]];
                } else {
                    ways[i][j] = ways[i-1][j];
                }
            }
        }
        return ways[coins.length][amount];
    }
    // The idea in version 1 is similar to other DP approach we have a set of coins and a certain amount we want to
    // reach so we create a 2D DP table with rows as coin & col as amount for each coin we want to check how many
    // ways we can achieve an amount. Finally for all the set of coins we get the total sum in ways[coin.len][amount]
    // The reason we can use optimised version is we only need to care about the previous row when considering the
    // current row so this can be space optimised to a one D array.
    // The version 2 uses space optimised DP
    // We calc ways[i] given a coin set for example [2,5,10 ....] by combining the ways[i-2], ways[i-5], ways[i-10]
    // and so on ...! Try to look  at this in this way when we reach ways[i-5]
    // we can ways[i-5] + 5 to get to ways[i] similarly for the rest of the coins.
    // At each amount j we have to consider two parts 1st if we dont have coin[i] so w[i-1][j] 2nd if we can use coin[i]
    // then we need to see how many ways we can get the last item with this coin[i] that is ways[i][j-coin[i]]. So DP
    // equation DP[i] = DP[i] + DP(i - nums[j]) for all j in set of coins
    // Now we come to our 1st observation comparing it to version 1 in the method 'int change(int amount, int[] coins)'
    // in this file we used a 2D array notice the  2D array is not needed as at each step for in version we use
    // ways[i][j] =  ways[i-1][j] + ways[i][j-coins[i-1]] but we never used anything past i-1 position so we are
    // interested only in the  last value hence the following equation is good. ways[i] on the right of equal
    // operator holds the last value :) !!!
    // ways[i]  = ways[i] + ways[j-coins[i]]
    // Now we come to the 2nd observation although this problem looks similar to 'combinationSum4' method in this file
    // this is not the same notice how the inner/outer loop is reversed in these cases. Its because for this problem
    // we first count how may ways for coins 1 - i we can achieve our amount, then we add the coin[i+1] to figure out
    // the number of ways. Look at the implementation of version 1 and the reason for using the 2d array before space
    // optimisation.
    public int changeV2(int amount, int[] coins) {
        int []ways = new int[amount+1];
        ways[0]=1;
        for (int cn : coins) {
            for (int i = cn; i<= amount; i++) {
                ways[i]+= ways[i - cn];
            }
        }
        return ways[amount];
    }

    //LeetCode :: 322. Coin Change
    // All the version have same O(coins * amount)
    // The coin change problem to find the minimum possible coin combination count to reach the amount
    // Note: here that we need to get the minimum count of coin not ways ot reach the amount that's
    // why we have the +1 in the dp eqn
    // The idea is very similar to the above problem here we need to find minumum ways
    // At every step we have few  options if the value of coin is bigger than the amount we use the previous coins value
    // for this amount. Otherwise we check if we dont take this coin so ways[i-1][j] is a better choice or if we take
    // this coin ways[i-1][j - coin[i-1]] + 1 is a better choice (minimum).
    // So based on that our Dp eqn ways[i][j] = Math.min (ways[i-1][j], ways[i][j-coins[i]) +1)
    // Here we are using -1 to denote no result so we set the firs row to -1
    // we set first column to 0 as this would give a value 1 for ways[2][2], ways[4]4], ways[5][5] when coins are 2,4,5
    // Now lets look at the problem carefully and inspect if we really need a 2D array or not, We use only the value of
    // ways[i-1] in each iterration so we dont need the whole 2D array and we can use the below space optimised
    // coinChangeSpaceOptimised version
    public int coinChange(int[] coins, int amount) {
        int [][]ways = new int[coins.length +1][amount+1];

        for (int i = 0; i< ways[0].length; i++)
            ways[0][i] = -1;
        for (int i = 0; i<ways.length; i++ )
            ways[i][0] = 0;

        for (int i = 1; i < ways.length; i++) {
            for(int j = 1; j < ways[0].length; j++) {
                if (coins[i-1] > j)
                    ways[i][j] = ways[i-1][j];
                else {
                    int x = ways[i][j-coins[i-1]];
                    int y = ways[i-1][j];

                    if (x == -1 && y == -1)
                        ways[i][j] = -1;
                    else if (y == -1)
                        ways[i][j] =  x+1;
                    else if (x == -1)
                        ways[i][j] = y;
                    else
                        ways[i][j] = Math.min(x+1,y);
                }
            }
        }
        System.out.println(Arrays.deepToString(ways));
        return ways[coins.length][amount];
    }

    // same idea as above we are using a space optimised version here so reducing the 2D array to 1D array
    public int coinChangeSpaceOptimised(int[] coins, int amount) {
        int []ways = new int[amount+1];
        Arrays.fill(ways, -1);
        // set the 0th item to zero so we can use it for each coin for example a coin 2 will give output 1 for
        // amount 2 similarly coin 4 will give 1 for amount 4 and so on ....in  the loop
        ways[0] = 0;

        for (int cn : coins) {
            for (int i = 1; i<ways.length; i++) {
                if (cn <= i) {
                    int x = ways[i -cn];
                    int y = ways[i];
                    if (x == -1 && y == -1)
                        ways[i] = -1;
                    else if (y == -1)
                        ways[i] = x + 1;
                    else if (x == -1)
                        ways[i] = y;
                    else
                        ways[i] = Math.min(x+1,y);
                }
            }
        }

        return ways[amount];
    }

    // Here we do another further optimisation by using a Max value to set the dp array,
    // we feel the dp array with max val which helps to get over multiple if & else condition
    // in the previous cases. So its be come less clumssy.
    // Note: when dealing with mins in an array its often easier to set the whole array to max
    // Note: here that we need to get the minimum count of coin not ways ot reach the amount that's
    // why we have the +1 in the dp eqn
    // DP eqn : ways[i] = Min(ways[i], ways[ i- coin[j]] + 1)
    public int coinChangeSpaceOptimisedUsingMax(int[] coins, int amount) {
        int []ways = new int[amount+1];
        int max = amount  +1;
        // we are using max to fill the whole array this max the cal easy as we are interested in MIN
        Arrays.fill(ways, max);
        // set the 0th item to zero so we can use it for each coin for example a coin 2 will give output 1 for
        // amount 2 similarly coin 4 will give 1 for amount 4 and so on ....in  the loop
        ways[0] = 0;

        for (int cn : coins) {
            for (int i = 1; i<ways.length; i++) {
                if (cn <= i) {
                    ways[i] = Math.min(ways[i], ways[i - cn] +1);
                }
            }
        }

        return ways[amount] > amount? -1 : ways[amount];
    }



    // The DP eqn for NCK = n-1Ck-1 + n-1Ck we use DP to get nck so that overflow does not happen
    private int nckMemoisation(int n, int k, int dp[][]) {
        // base case if n = 0  or k = 0 the value will be 1
        if (n==0 || k==0)
            return 1;
        if(dp[n][k] == 0) {
            int x = nckMemoisation(n-1,k-1,dp); // n-1Ck-1
            int y = nckMemoisation(n-1,k,dp);      // n-1Ck
            dp[n][k] = x + y;
        }
        return dp[n][k];
    }

    // Find NCK using DP to avoid overflow, Note we cannot use the mathematical option
    // here as overflow can happen if we use the eqn
    public int nck (int n, int k){
        return nckMemoisation(n,k, new int[n+1][k+1]);
    }

    // LeetCode :: 213. House Robber II
    // This uses the same logic as House Robber problem below,
    // here we are using the optimized DP approach used in fibonacci.
    // The optimised approach can be used when we just need to keep the
    // last 2 values for the current ith position.
    // This solution also uses the exact same DP equation : The dp equation is dp[i] = max(dp[i-1],dp[i-2] + p(i)).
    // The optimised approach is a very generic approach to similar DP problems where we just need to uses the last
    // n values for the n+1 th  value, in that case  we will need n last variables
    // We need to run the dp calc twice as its an circular array and in the first pass we cal starting i = 0
    // and 2nd pass we start i = 1 then we compare the max of two passes
    public int robCircular(int[] nums) {
        int lastLast = 0;
        int last = 0;
        int max = 0;
        for (int i =0;i <nums.length-1 ;i++) {
            int tmp = last;
            last = Math.max(lastLast + nums[i], last);
            lastLast = tmp;
        }
        max = last;
        last = 0;
        lastLast = 0;
        for (int i =1; i <nums.length; i++) {
            int tmp = last;
            last = Math.max(lastLast + nums[i], last);
            lastLast = tmp;
        }
        max = Math.max(last, max);
        return max;

    }


    // LeetCode :: 198. House Robber
    // The dp equation is dp[i] = max(dp[i-1],dp[i-2] + p(i))
    // At each step we make a decision whether to use
    // the last value (i-1)th dp or this value P(i) + lastLast (i-2)th DP value
    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;
        int []dp = new int[nums.length +1];
        dp [0] = 0;
        dp [1] = nums[0];
        for (int i = 2; i<dp.length; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] +nums[i-1]);
        }
        return dp[nums.length];
    }

    // optimizing to remove the requirement of O(n) space this is O(1) space solution,
    // this uses the following idea of Fibonacci check the fibonacci implementation below,
    // its a very nice technique that is commonly used to solve dp problem
    public int rob2(int[] nums) {
        if (nums.length == 0)
            return 0;
        int last = 0;
        int lastLast = 0;
        int max = 0;
        for (int i = 0; i<nums.length; i++) {
            int temp = last;
            last = Math.max(last, lastLast + nums[i]);
            lastLast = temp;
        }
        return last;
    }

    // optimizing the fibonacci to O(1) space
    public int fibonacci (int n) {
        int last =1;
        int lastLast = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = last;
            last = lastLast + last;
            lastLast =tmp;
        }
        return last;
    }

    // LeetCode :: 337. House Robber III
    public int robTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int max = 0;
        int last = 0;
        int lastLast = 0;
        int lastLevelSum = 0;
        LinkedList<Integer> parentNodeList = new LinkedList<>();
        while(!queue.isEmpty()) {
            int size = queue.size();
            int nodeSum = 0;
            LinkedList<Integer> tempList = new LinkedList<>();
            for (int i = 0; i < size;i++) {
                TreeNode node = queue.poll();
                tempList.addFirst(node.val);
                nodeSum+= node.val;
                if (node.left != null)
                    queue.add(node.left);
                if(node.right!=null)
                    queue.add(node.right);

            }
            int tmp = last;
            if (parentNodeList.size() == 0) {
                parentNodeList.addAll(tempList);

            } else {
                if(parentNodeList.size() == 2) {
                    nodeSum = Math.max(nodeSum + parentNodeList.remove(), nodeSum + parentNodeList.remove());
                }
                parentNodeList.addAll(tempList);
            }
            last = Math.max(lastLast + nodeSum, last);
            lastLast = tmp;
            lastLevelSum = nodeSum;

        }
        return last;
    }

    // LeetCode :: 221. Maximal Square
    // The idea is to recursively calculate the square size we  start from 0,0 and recursively find all
    // size of square at (0,0) and move forward to next position. Consider how can you build a 2*2 square using a
    // 1 size square, by going along the border of 1 square considering this 1 size square as the top left point
    // of 2*2 square. So at each point we consider the minimum of its three adjacent (i,j+1 i+1,j & i+1,j+1) squares
    // the current sqaure length will min + 1 if this square has '1' in it. We recursively solve this for the
    // whole array. As we are trying to recursively solve it for the whole array we repeat the same computation
    // of calculating same square multiple time for example the square rooted at (4,4) needs to calculated for
    // (0,0), (0,1), (1,1), (1,2) and so on... These are  overlapping sub problem. So we can use DP. The
    // easier option is to go by top Down memoization approach. Hence we introduce the dp array in the code
    private int maximalSquareRec(char[][] matrix, int i, int j, int dp[][]){
        if (i < 0 || j <0 ||
                i >= matrix.length ||
                j >= matrix[0].length ||
                matrix[i][j]=='0') {
            return 0;
        }
        if (dp[i][j] != -1)
            return dp[i][j];
        // pick the min of the three adjacent block right down and bottom right (i+1,j+1)
        int min1 = Math.min(maximalSquareRec(matrix, i,j+1,dp) ,maximalSquareRec(matrix, i+1,j,dp));
        dp[i][j] = Math.min(min1, maximalSquareRec(matrix,i+1,j+1,dp)) + 1;
        return dp[i][j];
    }
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0)
            return 0;
        int [][]dpSquare = new int[matrix.length][matrix[0].length];
        int max =0;
        // init dp array to -1/invalid values as zero is valid result for this problem
        // if we init to zero we giveup the dp benefit and waste time recalc
        for (int i = 0; i<matrix.length;i++) {
            for (int j =0 ; j<matrix[0].length; j++) {
                dpSquare[i][j] = -1;
            }
        }
        for (int i = 0; i<matrix.length;i++) {
            for (int j =0 ; j<matrix[0].length; j++) {
                max = Math.max(max, maximalSquareRec(matrix, i,j,dpSquare));
            }
        }
        return max * max;
    }

    // LeetCode :: 264. Ugly Number II
    // The idea is to cal the multiple of 2 3 5 with the lowest ugly numbers and pick the min of
    // three as the new ugly number. One thing to remember is to move along the lowest boundary so
    // on every iteration we pick the lowest last numbers multiply by to 2 , 3 ,5
    // for example  for 1 we get  2,3 ,5 we pick 2. Next options are 3x1 and 5x1 and 2x2
    // next 5x1, 2x2 ,3x2 next 3x2 2x4 5x2 and so on .....
    public int nthUglyNumber(int n) {
        int [] uglyNumber = new int[n+1];
        // calc the first ugly number
        uglyNumber[0] = 1;
        int nextMult2 = uglyNumber[0] * 2;
        int nextMult3 = uglyNumber[0] * 3;
        int nextMult5 = uglyNumber[0] * 5;
        int idx2 = 0;
        int idx3 = 0;
        int idx5 = 0;
        // here we calc upto n -1 cause we started with 0 based index so n-1th
        // number is actually the nth ugly number
        for (int i = 1; i < n; i++) {
            // get the smallest of the three possible ugly number calculated in previous step
            // and choose the minimum the minimum will be the new ugly number
            int nextNum = Math.min(Math.min(nextMult2, nextMult3), nextMult5);
            // update the ith ugly number
            uglyNumber[i] = nextNum;
            // calc the next 3 possible ugly numbers
            if (nextNum == nextMult2) {
                nextMult2 = uglyNumber[++idx2] * 2;
            }
            if (nextNum == nextMult3) {
                nextMult3 = uglyNumber[++idx3] * 3;
            }
            if (nextNum == nextMult5) {
                nextMult5 = uglyNumber[++idx5] * 5;
            }
        }
        return uglyNumber[n-1];
    }

    // LeetCode :: 300. Longest Increasing Subsequence
    // We have to find the longest increasing sequence they don't have to be contagious. Array can have negative numbers
    // so if we use a dp table where we store all tge previous seq length before the ith item then we can look back at
    // the i-1 items and calc the longest sequence for i. Note when checking the i-1 items we take in to consideration
    // only the items that are samller then the ith item
    // hence dp eqn dp[i] = max(dp[i-1]) + 1 {when dp[i-1] < dp[i] for 0 to i-1 items}
    public int lengthOfLIS(int[] nums) {
        int []seqLen = new int [nums.length];
        int maxLen = 0;

        for(int i = 0; i < nums.length; i++) {
            int max = 0;
            for (int j = 0; j<i; j++) {
                if (nums[i] > nums[j])
                    max = Math.max(max, seqLen[j]);
            }
            seqLen[i] = max + 1;
            // we need to store the max for the whole seqlen Array in a separate variable cause
            // its possible that the nth seqlen is not the max seqlen
            maxLen = Math.max(maxLen, seqLen[i]);
        }
        return maxLen;
    }

    // This is a better solution to find the longest increasing subsequence its O(nlgn) compared to the O(n^2)
    // solution above. The idea is little complicated the dp array does not store leg but the len that we can fill up in
    // the dp array is actually the result. The dp array is storing the longest seq so far, The dp array is sorted so
    // we can insert the next item in lgn time. Consider an example where the input is 3, 4, 5, 2, 7 so dp array becomes
    // [3 4 5] after idx 2 but when we get to idx 3, the value 2 does not contribute to the length of longest seq so
    // we dont put it at the end of the dp array rather in its proper position which is index 0 so dp array [2, 4, 5]
    // Next if we get a value greater than 5 the say 7 the dp array length increases as the sub seq len increases
    // so dp array will be [2 ,4 ,5, 7]
    public int lengthOfLISV2(int []nums) {
        int []dp = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = (low + high)/2;
                if (dp[mid] < x)
                    low = mid +1;
                else
                    high = mid;
            }
            dp[low] = x;
            if (low == size)
                size++;
        }
        return size;
    }

    // LeetCode :: 334. Increasing Triplet Subsequence
    // The idea here is the same as the longest increasing sequence problem. The approach we use is the same as O(nlgn)
    // But here the dp array size is fixed its 3 so the lgn search becomes O(1) and space requirement is also O(1) so
    // this is O(n) time  O(1) space solution for special case.
    // We put the smaller item in dp [0] & dp [1] as soon as we get an item which is greater than d[1] we have our
    // solution if no item is greater than dp[1] then we return false
    // Smallest items are put in dp[0] 2nd samller items are in dp[1] anytime we get something smaller
    // than dp [0] or dp[1] we update them accordingly
    public boolean increasingTriplet(int[] nums) {
        int []dp = new int [3];
        int size = 0;
        for (int x : nums) {
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = (low + high)/2;
                if (dp[mid] < x)
                    low = mid +1;
                else
                    high = mid;
            }
            dp[low] = x;
            if (low == size)
                size++;
            if (size == 3) return true;

        }
        return false;

    }

    // Based on the idea above we further simplify this problem as there is only three numbers to consider
    // if we just keep track of smaller and 2nd smaller using the same idea above and as soon as we get the
    // item that is bigger than 2nd smaller we have our solution, if no such item then we return false
    // This is also O(N) time  & O(1) space but performs little better as we have less checks & mem usage
    public boolean increasingTripletV2(int[] nums) {
        int smallest = Integer.MAX_VALUE;
        int secSmalllest = Integer.MAX_VALUE;

        for (int x : nums) {
            if (x < smallest) {
                smallest = x;
            } else if (x > smallest && x <= secSmalllest){
                secSmalllest = x;
            } else {
                return true;
            }

        }
        return false;

    }

    // LeetCode :: 368. Largest Divisible Subset
    // The idea is to similar approach to LIS. We need to sort the array first then we apply the LIS O(n^2) version to
    // find the longest divisible subset. While calculating the longest divisible subset we also store the prev item in
    // the subset in prev array this can be later use to reconstruct the subset. We need ot sort the input so that we
    // can only check for divisors before the current item in the array.
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int []subSetlen = new int[nums.length];
        int []prev = new int [nums.length];
        List<Integer> resList = new LinkedList<>();
        if (nums.length == 0)
            return resList;
        Arrays.fill(prev, -1);
        Arrays.sort(nums);
        int index = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[i] % nums[j] == 0) {
                    if (subSetlen[i] < subSetlen[j] +1) {
                        subSetlen[i] = subSetlen[j] + 1;
                        prev[i] = j;
                    }
                }
            }
            // store the max items index so that we can use the index to
            // generate the list of subset using the prev array
            if (max < subSetlen[i]) {
                max = subSetlen[i];
                index = i;
            }
        }
        
        while (index != -1) {
            resList.add(0,nums[index]);
            index = prev[index];
        }
        return resList;

    }


    // LeetCode :: 309. Best Time to Buy and Sell Stock with Cooldown (not submitted)
    // There are three state rest, buy, sell. we start with a rest state from rest we can only go to buy
    // At buy the next step is to rest or sell. From sell we need to go to rest directly
    // Based on this the equation is
    // rest[i] = Max(rest[i-1], sell[i-1])
    // buy [i] = Max(rest[i-1] - p(i), buy[i-1])
    // sell[i] = buy[i-1] + p(i)
    // The following is the optimised dp equation as only need the last step, we optimised the same way fibonacci
    public int maxProfitCoolDown(int[] prices) {
        int buy = Integer.MIN_VALUE;
        int rest = 0; // rest value should never less than zero so making sure mis is zero
        int sold = 0; // sold value should never be less than zero, so making sure the min is zero
        for (int i = 0; i < prices.length; i++) {
            int tempSold = sold;
            sold = buy + prices[i];
            buy = Math.max(rest - prices[i], buy);
            rest = Math.max(rest, tempSold);
        }
        return Math.max(sold, rest);
    }

    // LeetCode :: 123. Best Time to Buy and Sell Stock III (Hard)
    // This is also in SolutionsV1 but here we put it to as this is a DP approach try to think of the DP eqn using the
    // method above
    // First assume that we have no money, so oneBuy means that we have to borrow money from others,
    // we want to borrow less so that we have to make our balance as max as we can(because this is negative).
    // oneBuyOneSell means we decide to sell the stock, after selling it we have price[i] money and we have to give back
    // the money we owed, so we have prices[i] + oneBuy, we want to make this max.
    // twoBuy means we already buy & sale once now buy 2nd time hence so the 2ndBuy will be OneBuyOneSell - price[i]
    // cause we can use some profit from the oneBuyOneSell.
    // twoBuyTwoSell means we want to sell stock2, we can have price[i] money after selling it, and we have
    // twoBuy money left before, so twoBuyTwoSell = twoBuy + prices[i]
    // WE can think of this also as the one before with mupltiple state here we have 4 states, oneBuy, onSell,
    // twoBuy, twoSell. We can use the similar DP approach as above in 'maxProfitCoolDown'. The DP equations will be
    // oneBuy[i] = Max(oneBuy[i-1], -p(i))
    // oneSell[i] = Max(oneSell[i-1], oneBuy[i-1] + p(i))
    // twoBuy[i] = Max(twoBuy[i-1], oneSell[i-1] - p(i))
    // twoSell[i] = Max(twoSell[i-1], twoBuy[i-1] + p(i))
    // If we look closer we only care about the last val so we can use the optimised DP
    // Below we are going the reverse order two hold last val properly in each variable, Note we could have gone same
    // order but this make more sense as we want to hold the last val
    public int maxProfitTwice(int[] prices) {
        int oneBuy = Integer.MIN_VALUE;
        int oneBuyOneSell = 0; // one buy and sell should never be negative we dont want negative profit
        int twoBuy = Integer.MIN_VALUE;
        int twoBuyTwoSell = 0; // 2nd buy & sale should never be negative as we dont want negative profit
        for(int i = 0; i < prices.length; i++){
            // we bought twice so lets sale only if maximizes profit,
            // we store max to get max profit
            twoBuyTwoSell = Math.max(twoBuyTwoSell, twoBuy + prices[i]);
            // we can buy the second only after first is sold,
            // use the profit from first buy & sale
            twoBuy = Math.max(twoBuy, oneBuyOneSell - prices[i]);
            // we already buy the stock so we sale only if it maximizes the profit,
            // we store max to get max profit
            oneBuyOneSell = Math.max(oneBuyOneSell, prices[i] + oneBuy);
            // we set prices to negative, so the calculation of profit will be convenient,
            // negative max means positive low
            oneBuy = Math.max(oneBuy, -prices[i]);
        }
        return Math.max(oneBuyOneSell, twoBuyTwoSell);
    }

    // LeetCode :: 188. Best Time to Buy and Sell Stock IV (not Submitted)
    // The idea is to use the state machine DP as above
    // Here we have two state that can occur k times
    // buy & sell, in case of two buy two sell there were two (buy & sale) states but here we have K
    // (buy & sale states)
    // so for two state the optimised eqn is
    // sell = Max(sell, buy + p(i))
    // buy = Max(buy, prevSell - p(i))
    // Now we extend the above equations to k times
    // so the eqn becomes
    // buy[i] = Max(buy[i], sell[i-1] - p(i)) #for (i =1 to K)
    // sell[i] = Max(sell[i], buy[i] + p(i))  #for (i =1 to K)
    // the max result is propagated to the kth item so final result is sell[k]
    public int maxProfit(int k, int[] prices) {
        // if k >= n/2, then you can make maximum number of transactions
        // the its easy we can only pick the increasing values buy & sale.
        if (k >= prices.length / 2) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++)
                if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
            return profit;
        }
        // now lets handle the sceanario when K is less than the len/2
        // we based our solution on the idea of state machine
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        Arrays.fill(buy, Integer.MIN_VALUE);

        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                buy[i] = Math.max(buy[i], sell[i - 1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        return sell[k];
    }

    // LeetCode :: 375. Guess Number Higher or Lower II
    // The idea is to pick each number as the first guess and determine the cost associate for choosing the as the guess
    // We recursive pick each number as guess and update the cost for choosing it. Then pick the min as our solution.
    // For example for 1 2 3 we pick 1 as guess find the cost then pick 2 as guess and find the cost and then pick 3 as
    // guess and find the cost we keep track of the min.
    // Note when there is only one item that is in our range is (1,1) or (2,2) or (3,3) there is only one choice and we
    // pick the correct number in the first try so cost will be zero
    // As we are using recursion we can also use a memoization technique here caching the repeated recursion in a table
    // thus using DP to solve the issue.
    private int getMoneyAmountRec(int [][]dp , int start, int end) {
        // if start & end is same then only one item & we can pick it with zero cost
        if (start >= end)
            return 0;
        if (dp[start][end] != 0)
            return dp[start][end];
        int result = Integer.MAX_VALUE;
        for (int i = start; i<=end; i++) {
            // recursively check the cost for selecting i as guess
            int tmp = i + Math.max(getMoneyAmountRec(dp,start, i-1), getMoneyAmountRec(dp,i+1, end));
            result = Math.min(result, tmp);
        }
        dp[start][end] = result;
        return result;
    }
    public int getMoneyAmount(int n) {
        int [][]dp = new int[n+1][n+1];
        return getMoneyAmountRec(dp, 1, n);
    }

    // Leetcode 718 :: Maximum Length of Repeated Subarray (DP) same as longest common substring
    public int findLength(int[] A, int[] B) {
        int result = 0;
        int m = A.length;
        int n = B.length;
        int [][]lcs = new int [n+1][m+1];

        // init the lcs array the 1st row & columns should be zero
        for (int i = 0; i<=n; i++)
            lcs[i][0] = 0;
        for (int i = 0; i <= m; i++)
            lcs[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                // to determine longest common substring, if the current char from str1 & str2 matches
                // then we increments the size by 1, by adding 1 with the previous substring
                // so the DP solution is Lcs (i,j) = lcs(i-1, j-1) + 1  if the char at i & j position matches
                // otherwise lcs(i,j) = 0
                // For this we dont need to consider any other position for example (i-1,j) or(i,j-1)
                // cause the substring length at (i,j) is important
                if (A[i-1] == B[j-1]) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    result = Math.max(result,lcs[i][j]);
                }
                else
                    lcs[i][j] = 0;
            }
        }

        return result;
    }

    // this uses rolling hash & robin karp algo
    public int findLengthV2(int[] A, int[] B) {
        int la = A.length;
        int lb = B.length;

        int p = 119;
        int len = Math.max(la, lb)+1;
        int[] ps = new int[len];
        ps[0] = 1;
        for(int i = 1; i < len; i++) {
            ps[i] = ps[i-1]*p;
        }

        int[] hashA = new int[la+1];
        for(int i = 1; i <= la; i++) {
            hashA[i] = hashA[i-1] + A[i-1] * ps[i];
        }

        int[] hashB = new int[lb+1];
        for(int i = 1; i <= lb; i++) {
            hashB[i] = hashB[i-1] + B[i-1]*ps[i];
        }

        int lo = 1;
        int hi = Math.min(la, lb);
        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            HashSet<Integer> set = new HashSet<>();
            for(int i = 1; i+mid-1 <= la; i++) {
                int hashVal = (hashA[i+mid-1]-hashA[i-1])*ps[len-mid-i+1];
                set.add(hashVal);
            }
            boolean found = false;
            for(int i = 1; i+mid-1 <= lb; i++) {
                int hashVal = (hashB[i+mid-1]-hashB[i-1])*ps[len-mid-i+1];
                if(set.contains(hashVal)) {
                    found = true;
                    break;
                }
            }

            if(found) {
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        }

        return hi;
    }

    // LeetCode :: 329. Longest Increasing Path in a Matrix
    // The idea is calculate all the increasing path and find out the max increasing path using recursion,
    // But during recursion we can also use memoization to make the process faster. Note we are basically doing a
    // dfs in the recusive function, we do not visit the already visited node because of property
    // matrix[i][j] <= lastVal, for example if go from 6 to 9 we wont come back to 6 from 9 because 9>6.
    // we only travel along the increasing path this way decreasing or equal paths are nevertravelled

    private int longestIncreasingPathRectSum (int [][]memo , int matrix[][], int i, int j, int lastVal) {
        if (i < 0 || j < 0 ||
                i >= matrix.length ||
                j >= matrix[0].length ||
                matrix[i][j] <= lastVal) { // travel increasing path
            return 0;
        }
        if (memo[i][j] != 0)
            return memo[i][j];
        int up = longestIncreasingPathRectSum(memo, matrix, i-1,j, matrix[i][j]);
        int down = longestIncreasingPathRectSum(memo, matrix, i+1,j, matrix[i][j]);
        int left = longestIncreasingPathRectSum(memo, matrix, i,j-1, matrix[i][j]);
        int right = longestIncreasingPathRectSum(memo, matrix, i,j+1, matrix[i][j]);
        int max1 = Math.max(left, right);
        int max2 = Math.max(up,down);
        memo[i][j] = Math.max(max1,max2) + matrix[i][j];
        return memo[i][j];

    }

    private int longestIncreasingPathCount (int [][]memo , int matrix[][], int i, int j, int lastVal) {
        if (i < 0 || j < 0 ||
                i >= matrix.length ||
                j >= matrix[0].length ||
                matrix[i][j] <= lastVal) {
            return 0;
        }
        if (memo[i][j] != 0)
            return memo[i][j];
        int up = longestIncreasingPathCount(memo, matrix, i-1,j, matrix[i][j]);
        int down = longestIncreasingPathCount(memo, matrix, i+1,j, matrix[i][j]);
        int left = longestIncreasingPathCount(memo, matrix, i,j-1, matrix[i][j]);
        int right = longestIncreasingPathCount(memo, matrix, i,j+1, matrix[i][j]);
        int max1 = Math.max(left, right);
        int max2 = Math.max(up,down);
        memo[i][j] = Math.max(max1,max2) + 1;
        return memo[i][j];

    }
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0)
            return 0;
        int [][] memo = new int [matrix.length][matrix[0].length];
        for (int i =0 ; i< matrix.length; i++)
            Arrays.fill(memo[i],0);
        int longestPath = 0;
        for (int i=0; i< matrix.length; i++) {
            for (int j =0; j<matrix[0].length; j++) {
                longestPath = Math.max(longestPath, longestIncreasingPathCount(memo, matrix, i,j, Integer.MIN_VALUE));
                //System.out.println(Arrays.deepToString(memo));
            }
        }
        return longestPath;
    }

    // LeetCode :: 241. Different Ways to Add Parentheses
    // This is the same as Catalan number generation or all possible binary tree generation
    // We scan through the string to find  a operator, once we get the operator we split the string in two halves and
    // recursively compute the result for left & right half. Next we merge the result from left & right halves to a
    // new list. We make use of memoization using a hashmap of input string & list of integer. We are sacrificing
    // memory for faster performance
    // Note we could use the same technique for binary tree generation problem, we could use memoization
    // to make it faster
    public List<Integer> diffWaysToCompute(String input) {
        HashMap<String, List<Integer>> memoParetheses = new HashMap<>();
        return diffWaysToComputeRec(input,  memoParetheses);

    }
    private List<Integer> diffWaysToComputeRec(String input,
                                               HashMap<String, List<Integer>> memoParetheses) {
        List <Integer> resList = new ArrayList<>();
        // check if the result is already calculated in the memoization table if yes return the result
        if (memoParetheses.containsKey(input))
            return memoParetheses.get(input);
        // we have not found the result in memoization table lets proceed with our algo
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            // found an operator split the string in two halves and recursively solve both halves
            // note we need to store the result list for both halves as we will need the me to
            // merge to create the new list for this input string
            if(ch == '+' || ch == '-' || ch == '*') {
                // left half from the operator
                List<Integer> leftList = diffWaysToComputeRec(input.substring(0,i), memoParetheses);
                // right half from the operator
                List<Integer> rightList = diffWaysToComputeRec(input.substring(i+1), memoParetheses);
                // merge the results
                for (int left : leftList) {
                    for (int right : rightList) {
                        int res  = 0;
                        if (ch == '+')
                            res = left + right;
                        else if (ch == '-')
                            res = left - right;
                        else
                            res = left * right;
                        resList.add(res);
                    }
                }
            }
        }
        // our reslist is empty this is a base case we got a input string that only have digits and no operator
        // so lets just convert the String to Integer and return it as the result list
        if (resList.isEmpty()) {
            resList.add(Integer.parseInt(input));
        }
        // update the memoization table before returning from the func
        memoParetheses.put(input, resList);
        return resList;
    }

    // *****(This memoization becomes very slow) ********
    // LeetCode :: 95. Unique Binary Search Trees II
    // The Problems requires us to generate all BST possible for 1 to n. So we actually have to generate those tree.
    // The idea is to go by each node from 1 to n as root and create a tree. So for each node as root we need to
    // build a left subtree & a right subtree and then add the left subtree & right subtree as to the root.
    // We use two lists to store the left subtrees & right subtrees, Then for root we take one node from left subtree
    // & one node from rightSubtree. Finally return the list
    private List<TreeNode> genBSTree (int start, int end,
                                      HashMap<Pair<Integer, Integer>, List<TreeNode>> mapTreeNode) {
        List<TreeNode> rList = new ArrayList<>();
        if (start > end) {
            rList.add(null);
            return rList;
        }
        if (mapTreeNode.containsKey(new Pair<>(start, end))) {
            return mapTreeNode.get(new Pair<>(start, end));
        }
        if (start == end) {
            rList.add( new TreeNode(start));
            mapTreeNode.put(new Pair<>(start, end), rList);
            return rList;
        }

        for (int i = start; i <= end; i++) {
            // left subtree list holds all the possible left subtrees
            List<TreeNode> leftSubTreeList = genBSTree(start, i - 1, mapTreeNode);
            // right subtree list holds all the possible right subtrees
            List<TreeNode> rightSubTreeList = genBSTree(i+1, end, mapTreeNode);
            for (TreeNode leftSubTree : leftSubTreeList) {
                for (TreeNode rightSubTree : rightSubTreeList) {
                    // for this root pick a left subtree & right subtree and the to the list
                    // we need to add root to the list multiple time with multiple options of
                    // left & right subtree, we will have total pf leftSubtree.size * rightSubtree.size
                    // for example (1,2)  3 (4,5) ; for root 3 there are 4 distinct trees with 3 as root
                    TreeNode root  = new TreeNode(i);
                    root.left = leftSubTree;
                    root.right= rightSubTree;
                    rList.add(root);
                }
            }
        }
        mapTreeNode.put(new Pair<>(start,end),rList);
        return rList;
    }
    public List<TreeNode> generateTrees(int n) {
        HashMap<Pair<Integer, Integer>, List<TreeNode>> mapTreeNode  = new HashMap<>();
        if (n == 0)
            return new ArrayList<TreeNode>();
        return genBSTree(1,n, mapTreeNode);
    }

    // LeetCode :: 313. Super Ugly Number
    // Same logic as Ugly number, just extending the case from 3 primes (2,3, 5) to k primes
    // we use and indexes array to keep the current point of prime[i] pointing to uglyNumber[i] fror nextMult calc
    // we aslo keep an array of nextMult[i] to keep the next multiplication of primes [i]
    private int minOfArr (int []a) {
        int min = Integer.MAX_VALUE;
        for (int i : a){
            if (min > i)
                min = i;
        }
        return min;
    }
    public int nthSuperUglyNumber(int n, int[] primes) {
        int [] indexes = new int [primes.length];
        int [] nextMult = new int [primes.length];
        int [] uglyNumber = new int [n];
        uglyNumber[0] = 1;
        for (int i =0; i< primes.length; i++) {
            nextMult[i] = primes[i];
        }
        for (int i = 1; i<n; i++) {
            int nextUglyNum = minOfArr(nextMult);
            uglyNumber[i] = nextUglyNum;
            for (int j =0; j<primes.length; j++) {
                if (nextMult[j] == nextUglyNum) {
                    nextMult[j] = primes[j] * uglyNumber[++indexes[j]];
                }
            }

        }
        return uglyNumber[n-1];

    }

    // LeetCode :: 343. Integer Break
    // The idea is to represent every number as a factor of 2s and/or 3s. For example 7 is (2+2+3) so now (2*2*3) gives
    // 12 which the largest product from its factors. This factors of 2 & 3 will be greater than any other combination
    // as we can get that combination using 2s & 3s. So any x+y =z we can make x  or y using factors of 2 hence the
    // result will be valid using factors of only 2 & 3. We use a dp approach to generate all the products upto n
    // to get the max product of a integer break we need to look ack at i-2 and i-3 as we are only using 2 & 3 as factors
    // so the max product will be dp[i] = max(dp[i-2]*2, dp[i-3]*3);

    public int integerBreak(int n) {
        // we need to handle some special case for 1 to 3
        if (n <=2)
            return 1;
        if (n==3)
            return 2;
        // lets handle another special case the result for 2 is 1 and for 3 is 2
        // but to generate the dp table we need to set dp[2] to 2
        int [] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++){
            dp[i] = Math.max(dp[i-2]*2, dp[i-3] * 3);
        }

        return dp[n];
    }

    // space optimised version of the above problem
    public int integerBreak2(int n) {
        // we need to handle some special case for 1 to 3
        if (n <=2)
            return 1;
        if (n==3)
            return 2;
        // lets handle another special case the result for 2 is 1 and for 3 is 2
        // but to generate the dp table we need to set dp[2] to 2
        int ls3 = 1;
        int ls2 = 1;
        int ls1 = 2;

        for (int i = 3; i <= n; i++){
            int tmp = ls1;
            ls1 = Math.max(ls2*2, ls3*3);
            int tmpx = ls2;
            ls2 = tmp;
            ls3 =tmpx;
        }
        return ls1;
    }

    // 0 - 1 Knapsack Implementation
    // itemsVal contains the value of the items i
    // itemsW contains items i weight
    // W is the max knapsack wieght,
    // return : we have to return the maxProfit;
    // The idea is following at any item  if the item weight is bigger than the current knapsack capacity we cannot use
    // this item and we get our best DP value(profit) without this item so we look at values V[i-1][w] for wi > w
    // if the items weight is small enough to fit in knapsack the we have two options to choose from we pick the max
    // of the two options. First option is not to use this item at all so we get our value without this item so V[i-1][w]
    // Second option is to use this item so we have to make a space of weight wi in the knapsack so we can check the
    // price at [w-wi] which is V[i-1][w-wi] and add the price of wi which is vi to it so V[i-1][w-wi] + vi
    // we look at i-1 pos cause i -1 denotes not including this item
    // The max of the two options is our result. So the DP equation is as follows
    // V[i][w] = Max(V[i-1][w] + V[i-1][w-wi] + vi) if wi<=w
    // V[i][w] = V[i-1][w] if wi > w
    // Note:  0-1 knapsack wants to find out a subset in an array that adds upto sum number W and with a profit
    // maximization dictated by the array P(i)
    public int zeroOneKnapSack (int []itemsVal, int [] itemsW, int W){
        int[][]Values = new int [itemsVal.length+1][W+1];
        // set the first row & cols to zero
        for (int i = 0 ; i<= itemsVal.length; i++)
            Values[i][0] = 0;
        for (int i = 0; i<=W; i++)
            Values[0][i] = 0;
        for (int i = 1; i<Values.length; i++) {
            for (int w = 1; w <Values[0].length; w++) {
                if(itemsW[i -1] <= w) {
                    // here in the 2nd part , we check if in our weight array there exist an weight = w - items[i-1]
                    // and if such an weigh exist than we add the profit for that with ith items profit
                    Values[i][w] = Math.max(Values[i-1][w],
                                            Values[i-1][ w - itemsW[i-1]] + itemsVal[i-1]); //2nd part
                } else
                    Values[i][w] = Values[i-1][w];
            }
        }
        System.out.println(Arrays.deepToString(Values));
        return Values[itemsVal.length][W];
    }

    // This is the space optimised version of 0-1 knapsack we can usee this beacause at each iteration of the
    // inner loop we only depend on the value of last item V[i-1]
    public int zeroOneKnapSackSpaceOpt (int []itemsVal, int [] itemsW, int W){
        int []Values = new int [W +1];
        Values[0] = 0;
        for (int i = 1; i<=itemsVal.length; i++) {
            for (int w = 1; w <Values.length; w++) {
                if(itemsW[i -1] <= w) {
                    Values[w] = Math.max(Values[w],
                            Values[ w - itemsW[i-1]] + itemsVal[i-1]);
                } else
                    Values[w] = Values[w];
            }
            System.out.println(Arrays.toString(Values));
        }
        return Values[W];

    }


    // LeetCode :: 416. Partition Equal Subset Sum
    // This is the same as knapsack problem here similar to knapsack we want to select or find a combination from
    // the array that sums up to a weight (here total sum /2). In knapsack our goal was to maximize profit but here we
    // just need to check if the combination exist or not
    public boolean canPartition(int[] nums) {
        int sum = 0;
        // get the total sum
        for (int n : nums) {
            sum += n;
        }
        // sum is not even so no result
        if ((sum %2) != 0) {
            return false;
        }
        // for sums to be equal the sum for each subset has to be half of the total sum
        sum /= 2;

        boolean [][]knps = new boolean[nums.length +1][sum +1];
        // init the first rows &cols
        // rows will be false no items but positive sum should be false
        for (int i = 0 ; i <=sum; i++) {
            knps[0][i] = false;
        }
        // but col needs to be true, any items no sum is also true
        // knps[0][0] neesd to be true as no item no sum is true
        for(int i =0; i<=nums.length; i++) {
            knps[i][0] = true;
        }
        // we use the same knapsack dp equation
        for (int i = 1; i <knps.length; i++) {
            for (int j = 1; j <sum +1; j++) {
                // if the item value is lower than sum
                if (nums[i-1] <=j) {
                    // think like if we have the value (j - nums[i]) in our nums array
                    knps[i][j] = knps[i-1][j] || knps[i-1][j-nums[i-1]];
                } else { // if item is bigger than sum we discard this
                    knps[i][j] = knps[i-1][j];
                }
            }
        }
        return knps[nums.length][sum];
    }



    // LeetCode :: 139. Word Break (this takes 3 ms)
    // Memoisation used with recursion to cache the failed cases and not try for failed cases
    // for example str = leetcode  dict = [leet, code] when are depth of recursion last 'e' or 'de' is not in the dictionary
    // so when ever we reach the len = 6 (leetco) we know from our memo cache that failed len 6 exist in memo cache
    // so we dont go any further same for len =5 (leetc)  'ode' is not in dict so len 5 goes to memo failed cache
    // This one performs better as break as soon as we ge the result
    //
    private boolean wordBreakRec (HashSet <String> dict, String s,
                                  int index,
                                  HashSet <Integer> memoFailedLen){
        if(index == s.length())
            return true;
        for (int i = index+1; i <=s.length(); i++) {
            if(memoFailedLen.contains(i))
                continue;
            // upto i is found in the dictionary
            if(dict.contains(s.substring(index,i))){
                // lets check if rest is also in dict
                if(wordBreakRec(dict, s, i,memoFailedLen))
                    return true;
                // 0, i -1 is in dict but at len i we failed to find a solution
                // lets cache this in the memoization table
                memoFailedLen.add(i);
            }
        }
        return false;


    }

    public boolean wordBreakV2(String s, List<String> wordDict) {
        HashSet <String> dict = new HashSet<>(wordDict);
        HashSet <Integer> memoFailedLen = new HashSet<>();
        return wordBreakRec(dict, s, 0, memoFailedLen);

    }

    // DP (non memo) version of word break ((this takes 6 ms))
    // Uses the same DP approach of LIS
    // This one is little slower O(s^3)
    // We use an approach similar to LIS where for each current char we check previous valid breaks in the string
    // if we find a previous valid break at position j then we check if the current substr (j,i) is in the dictionary
    // if yes then we found another valid break at i so update the valid break and keep going on until we reach the end
    // of our input
    public boolean wordBreak3(String s, List<String> wordDict) {
        // denotes if the string is breakable at pos i+1
        boolean validBreaks[] = new boolean[s.length() +1];
        HashSet <String> dict = new HashSet<>(wordDict);
        validBreaks[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j<i; j++) {
                // check if the previous string & the current sub string is breakable
                // if so we can say the string is breakable at this poition
                if (validBreaks[j] && dict.contains(s.substring(j,i))) {
                    validBreaks[i] = true;
                    break;
                }
            }
        }

        return validBreaks[s.length()];
    }

    // LeetCode :: 140. Word Break II
    // The idea is to use the same approach as wordbreak problem, we need to generate all possible word break
    // ie words with space. Hence its better to use the memo approach as it will use DP + recursive approach so
    // it will be easier to generate the result list. We use memoization to reduce the number of recursions.
    // The memo stores the index in the string  where the recursion failed so that we can skip the same index
    // processing during another recursive call. We only memo the failed index so we have to handle storing it.

    public boolean wordBreakIIHelper (String s, int index, HashSet<String> dict,
                                      StringBuilder sb, List<String> rList,
                                      HashSet<Integer> memo){
        if (index == s.length()) {
            rList.add(sb.deleteCharAt(sb.length() -1).toString());
            return true;
        }
        boolean found = false;
        for (int i = index+1; i <= s.length(); i++) {
            if (memo.contains(i)) {
                continue;
            }
            String subStr = s.substring(index, i);
            if (dict.contains(subStr)) {
                int sbIdx = sb.length();
                sb.append(subStr);
                sb.append(" ");
                // Look how the recursive call result is stored in found this is done so that we remember the last
                // result when we return from this function call otherwise if we just return false in the end of
                // the function we will hit a bug. Consider we found "dog" so found is true and if g is the last
                // char and we return false instead of found in the end og the loop we will incorrectly report
                // false always
                found = wordBreakIIHelper(s, i, dict, sb, rList, memo);
                if (!found) {
                    memo.add(i);
                }
                sb.delete(sbIdx, sb.length());
            }

        }
        // we cannot return false here we need to return the result of the last function call which could be true
        // or false blindly returning false will be a bug
        return found;
    }

    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> resList = new ArrayList<>();
        HashSet<String> dict = new HashSet<>(wordDict);
        HashSet<Integer> memo = new HashSet<>();
        wordBreakIIHelper(s, 0, dict, new StringBuilder(), resList, memo);
        return resList;
    }


    // ctci 17.13 :: Re-Space
    // We need to use the same appraoch as the previous problem "LeetCode :: 139. Word Break (this takes 3 ms)"
    // We apply a DP approach similar to LIS O(n^2)
    public String bestSplit(String s, List<String> wordDict) {
        HashSet<String> dict = new HashSet<>(wordDict);
        int []prev = new int[s.length()+1];
        int []cost = new int [s.length()+1];
        cost[0] = 0;
        for(int i = 1; i <= s.length(); i++){
            int tempCost = s.length() +1;
            for(int j = 0; j < i; j++){
                int curCost = (dict.contains(s.substring(j,i)) ? 0 : i-j) + cost[j];
                if (tempCost > curCost){
                    tempCost = curCost;
                    prev[i] = j;
                }
            }
            cost[i] = tempCost;
        }
        System.out.println("Cost:" + Arrays.toString(cost));
        System.out.println("Prev:" + Arrays.toString(prev));
        StringBuilder sb = new StringBuilder(s);
        int i = prev.length -1;
        while(prev[i] != 0){
            i = prev[i];
            sb.insert(i," ");
        }
        return sb.toString();
    }
    // A queue / bfs approach to this problem (This is not a dp solution) its the slowest....
    // this may not be the best solution
    /**
     *     Idea is to try to chop off prefix of s that is in the dict
     *     enqueue the left-over of each chop off
     *     if there is a time the left over happens to be in the dict as well
     *         we know word is breakable, b/c all the previous chops are all in the dict
     *     otherwise the original world is not breakable.
     *
     *     we can use a set to store all the leftovers that we have tried, to avoid enqueue the
     *     same leftover multiple times.
     * */
    public boolean wordBreak(String s, List<String> wordDict) {
        Queue<String> queue = new LinkedList<>();
        HashSet <String> dict = new HashSet<>(wordDict);
        HashSet<String> visited = new HashSet<>();
        queue.add(s);
        visited.add(s);
        while (!queue.isEmpty()) {
            String rest = queue.remove();
            if(dict.contains(rest))
                return true;
            for(int i = 0; i < rest.length(); i++) {
                String chop = rest.substring(0,i);
                String next = rest.substring(i,rest.length());
                if(!visited.contains(next) && dict.contains(chop)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        return false;

    }

    // LeetCode :: 279. Perfect Squares
    // The idea is to use a DP solution. if number is perfect square the count is 1.
    // So to minimise for any number j we check all j - perfect square i*i counts so the dp eqn
    // is dp[j] = Min(dp[j], dp [j - i*i] + 1) in the this term the + 1 is for the perfect square
    // for example 12 = 8 +4 ; 4 is a perfect square so we add +1 and 8 is not perfect sqr
    // so dp[12-4] = dp[8]  value is 2 so for dp[12] = dp[4] + dp[8] = 1 +2 = 3
    public int numSquares2(int n) {
        int [] sqrCounts = new int[n+1];
        sqrCounts[0] = 0;
        for (int j = 1; j <=n; j++) {
            sqrCounts[j] = Integer.MAX_VALUE;
            for (int i = 1; i*i <= j; i++) {
                sqrCounts[j] = Math.min(sqrCounts[j], sqrCounts[j  - i*i] +1);
            }
        }
        System.out.println(Arrays.toString(sqrCounts));
        return sqrCounts[n];
    }
    // Based on Lagrange's Four Square theorem, there
    // are only 4 possible results: 1, 2, 3, 4.
    public int numSquares(int n) {
        // The result is 4 if and only if n can be written in the
        // form of 4^k*(8*m + 7). Please refer to
        // Legendre's three-square theorem.
        while (n % 4 == 0)
            n /= 4;
        if (n % 8 == 7)
            return 4;
        // Check whether 2 is the result or if its a perfect square
        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n)
                return a == 0 ? 1 : 2; //if a == 0 we have perfect square then result is 1
        }
        return 3;

    }

    // Cutting the rod problem
    // The idea here is the same as LIS problem we approach this the same we we approach LIS
    // for each i size rod we check if the whole rode or pieces of it brings more profit.
    // For pieces of it we compare all 1 to i-1 cuts (we can actually check half of i/2) so for len 5
    // we check the cut (1,4) (2,3) we dont need to check (3,2) or (4,1) thats why the 2nd loop has
    // i/2+1 as breaking condition. So after check all the cuts smalller than 5 we check the whole
    // rod which is 5's price and store the max i our dp array
    // dp eqn dp[i] = max (dp[i-j] +dp[j]) for all j =1 to j = i-1 (or i/2+1)

    public int curRod (int []nums, int n) {
        int []dp = new int[n+1];
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i <=n; i++) {
            // we start with j = 1 cause the lowest length of rod = 1
            for (int j = 1; j< i/2+1; j++) {
                dp[i] = Math.max(dp[i], dp[i-j] + dp[j]);
            }
            dp[i] = Math.max(dp[i], nums[i-1]);
            max = Math.max(dp[i], max);
        }
        return max;
    }

    // LeetCode :: 689. Maximum Sum of 3 Non-Overlapping Subarrays (Hard) (not submitted)
    // This is a hard DP problem. We have to find non-overlapping k size sub-array (three sub array) that gives max sum
    // First we get the range sum at each index for then next k items. We store it in rangeSum array. Now the problem
    // becomes finding three position i,j,l in the rangeSum array where i + k <= j and j + k <= l (i&j and j&l are k apart)
    // and (rangeSum[i] + rangeSum[j] + rangeSum[l]) is maximized. This i,j,l can be found dynamically.
    // Now assume if j is fixed we can easily find i  on the left & l on the right in rangeSum that maximizes the above
    // mentioned sum. To find the max on the left & right we use left & right array.
    // We need to store the max of range sum from left in left array and max of range sum from right in right array
    // So now for every possible j we check  the max value index of i in left & for l we get in right
    // See the exxample below to better understand the scenario
    // nums      [1, 2, 1, 2, 6, 7, 5, 1] (input array)
    // rangeSum  [3, 3, 3, 8, 13, 12, 6]  (holds range sum at index i for next k elements)
    // left      [0, 0, 0, 3, 4, 4, 4]    (holds the index of maximum range sum from left at index i)
    // right     [4, 4, 4, 4, 4, 5, 6]    (holds the index of maximum range sum from right at index i)
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int rangeSum[] = new int [nums.length - k + 1];
        int left[] = new int[nums.length - k + 1];
        int right[] = new int[nums.length - k + 1];

        int curSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curSum += nums[i];
            if (i >= k)
                curSum -= nums[i-k];
            if (i - k +1 >= 0)
                rangeSum[i - k + 1]  = curSum;
        }
        // get the so far max rangeSum position from the left in left array
        int idx = 0;
        for (int i = 0; i < rangeSum.length; i++) {
            if(rangeSum[i] > rangeSum[idx]) {
                idx = i;
            }
            left[i] = idx;
        }
        // get the so far max rangeSum position from the right in right array
        idx = rangeSum.length -1;
        for (int i = rangeSum.length -1; i >= 0; i--) {
            if(rangeSum[i] >= rangeSum[idx]) {
                idx = i;
            }
            right[i] = idx;
        }
        // now for each j find i & l such that it maximizes (rangeSum[i] + rangeSum[j] + rangeSum[l])
        int res[] = {-1,-1,-1};
        for (int j = k; j < rangeSum.length - k;j++) {
            int i = left[j - k];
            int l = right[j + k];
            //System.out.println((rangeSum[i] + rangeSum[j] + rangeSum[l]));
            if (res[0] == -1 ||
                    (rangeSum[i] + rangeSum[j] + rangeSum[l])
                            > (rangeSum[res[0]] + rangeSum[res[1]] + rangeSum[res[2]])) {
                res[0] = i;
                res[1] = j;
                res[2] = l;
            }
        }

        return res;
    }

    // Amazon | OA 2020 | Ways to Split String Into Prime Numbers
    // This is the same approach as LIS same way wordbreak is done we dont have dixtionary so we build using sieve
    // prime a primce dictinary
    private boolean waysToSplitPrimeHelper(String s, int idx ,boolean []notPrime, HashSet<Integer> failedIdxMemo) {
        if (idx == s.length()) {
            primeCount++;
            primeCount %= 1000000007;
            return true;
        }
        boolean found = false;
        if(s.charAt(idx) == '0')
            return false;
        for (int i = idx +1; i <= s.length(); i++) {
            String str = s.substring(idx,i);
            Integer n = Integer.parseInt(str);

            if (n <= 1000000 && notPrime[n] || failedIdxMemo.contains(i))
                continue;
            found = waysToSplitPrimeHelper(s, i, notPrime, failedIdxMemo);
            if (!found) {
                failedIdxMemo.add(i);
            }
        }

        return found;
    }
    int primeCount = 0;
    public int waysToSplitPrime (String s) {
        HashSet<Integer> failedIdxMemo = new HashSet<>();
        boolean []notPrime = Utilities.sievePrime(1000000);
        waysToSplitPrimeHelper(s, 0, notPrime, failedIdxMemo);
        return primeCount;
    }
    // This is the same approach as LIS
    public int waysToSplitPrimeV2(int n) {
        int mod = (int)1e9 + 7;
        boolean[] isPrime = new boolean[(int)1e6 + 1];
        Arrays.fill(isPrime, true);
        // get sieve's prime until 1e6
        for(int i=2;i*i<=(int)1e6;i++) {
            if(isPrime[i]) {
                for(int j=i; j*i<=(int)1e6; j++) {
                    isPrime[i*j] = false;
                }
            }
        }
        isPrime[1] = false;
        isPrime[0] = false;
        // convert the integer to string
        String s = String.valueOf(n);
        // dp array for prime count
        int[] dp = new int[s.length() + 1];
        dp [0] =1;
        // Apply the similar approach as LIS
        for (int i = 1; i<=s.length(); i++) {
            for (int j = 0; j<i ; j++) {
                // handles number starting with zero & numbers greater than 10^6
                // we dont need process in the above two case
                // we can make it even faster by detecting a zero in the number if there is a zer there is no solution
                // this can be handle even before the dp calc
                if (s.charAt(j) == '0' || i - j > 6)
                    continue;
                String str = s.substring(j,i);
                Integer num = Integer.parseInt(str);
                if (dp[j] != 0 && isPrime[num]) {
                    if (j==0)
                        dp[i] = 1;
                    else
                        dp[i] = (dp[i] + dp[j]) % mod;
                }
            }
        }

        return dp[s.length()] ;
    }





    }
