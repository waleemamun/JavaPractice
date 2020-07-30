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
    // The coin change problem to find the minimum possible coin combination to reach the amount
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
    // Note when dealing with mins in an array its often easier to set the whole array to max
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
    // (0,0), (0,1), (1,1), (1,2) and so on... These are non overlapping sub problem. So we can use DP. The
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
    // For example for 1 2 3 we pick 1 as guess find the cost then pick 2 as guess and find the cost and then pick 2 as
    // guess and find the cost we keep track of the min.
    // Note when there is only one item that is if our range is (1,1) or (2,2) or (3,3) there is only one choice and we
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

    //LeetCode :: 329. Longest Increasing Path in a Matrix

    private int longestIncreasingPathRectSum (int [][]memo , int matrix[][], int i, int j, int lastVal) {
        if (i < 0 || j < 0 ||
                i >= matrix.length ||
                j >= matrix[0].length ||
                matrix[i][j] <= lastVal) {
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
        int [][] memo = new int [matrix.length][matrix[0].length];
        for (int i =0 ; i< matrix.length; i++)
            Arrays.fill(memo[i],0);
        int longestPath = Integer.MIN_VALUE;
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



}
