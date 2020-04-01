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
}
