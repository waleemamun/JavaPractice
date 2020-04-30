public class DPs {

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






}
