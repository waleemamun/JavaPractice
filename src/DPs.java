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
}
