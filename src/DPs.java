import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    // root there is 1 tree so total of 5. Now  1 ways--->()1 (2,3)<--2 ways , 1 ways -->(1)2(3)<-- 1 ways
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
            int max = i;
            int min = 1;
            for (int j = 1; j <= max; j++) {
                bstCount[i] += bstCount[j-min] * bstCount[max-j];
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
    // the optimised eqn  path[i] = min(path[i] + path[i+1]) + triangle[k][i] (we are overwriting the path array)
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
                // Note actual dp eqn path[k-1][i] = min(path[k][i] + path[k][i+1]) + triangle[k][i]
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
    // The idea is very similar to the problem of "377. Combination Sum IV" in this file
    // We calc ways[i] given a coin set for example [2,5,10 ....] by combining the ways[i-2], ways[i-5], ways[i-10]
    // and so on ...! Try to look  at this in this way when we reach ways[i-5]
    // we can ways[i-5] + 5 to get to ways[i] similarly for the rest of the coins.
    // equation DP[i] = DP[i] + DP(i - nums[j]) for all j in set of coins
    // Now we come to our 1st observation comparing it to version 1 in the method 'int change(int amount, int[] coins)'
    // in this file we used a 2D array notice the  2D array is not needed as at each step for in version we use
    // ways[i][j] =  ways[i-1][j] + ways[i][j-coins[i-1]] but we never used anything past i-1 position so we are
    // interested only in the  last value hence the following equation is good ways[i] on the right of equal
    // operator holds the last value :) !!!
    // ways[i]  = ways[i] + ways[j-coins[i]]
    // Now we come to our 2nd observation where the method 'combinationSum4' calc uses similar approach in that api
    // we dont look for a combination rather look for permutation of ways we can combine the sum. But in this 'changeV2'
    // function when counting coins set we are more interested in combination
    // The reasons is  the inner & outer loops. In this case we process for each coin how many ways to get amount so
    // outer loops accounts for coins and when we look up the last value ways[i-1][j] we are looking up coins[i-1]
    // how many ways
    // But in case of earlier 'combinationSum4' the outer loops is ways so we are checking for each target how many
    // ways coin can be used in this case ways[i-1][j] refers to all the ways i-1 the entry is handled now to handle
    // ith entry we check for each coin to increase the result. Hence we get the permutation
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

    // The DP eqn for NCK = n-1Ck-1 + n-1Ck we use DP to get nck so that overflow does not happen
    private int nckMemoisation(int n, int k, int dp[][]) {
        if (n==0 || k==0)
            return 1;
        if(dp[n][k] == 0) {
            int x = nckMemoisation(n-1,k-1,dp); // n-1Ck-1
            int y = nckMemoisation(n-1,k,dp);      // n-1Ck
            dp[n][k] = x + y;
        }
        return dp[n][k];
    }

    public int nck (int n, int k){
        return nckMemoisation(n,k, new int[n+1][k+1]);
    }









}
