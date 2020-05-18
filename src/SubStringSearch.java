public class SubStringSearch {

    /**
     *  Source code from:
     *  https://github.com/mission-peace/interview/blob/master/src/com/interview/string/SubstringSearch.java
     *  Explanation : https://www.youtube.com/watch?v=GTJr8OvyEVQ
     */

    /**
     * Slow method of pattern matching
     */
    public boolean hasSubstring(char[] text, char[] pattern){
        int i=0;
        int j=0;
        int k = 0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                j=0;
                k++;
                i = k;
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
    }

    /**
     * Compute temporary array to maintain size of suffix which is same as prefix
     * Time/space complexity is O(size of pattern)
     * Example : pattern:   a a b a a b a a a
     *           index_pos: 0 1 2 3 4 5 6 7 8
     *           lps:       0 1 0 1 2 3 4 5 2
     */
    private int[] computeTemporaryArray(char pattern[]){
        int [] lps = new int[pattern.length];
        int index =0;
        for(int i = 1 ; i < pattern.length;) {
            // we found a matching; meaning we found a suffix which is also a prefix
            // lets store the len by adding one to the index;
            // index is keeping count of size of the prefix len (the +1 is due to 0 base index).
            // In the above example calc for position 4: i = 4 index = 1 and pattern[4] == pattern[1].
            // So, lps[4] = 1(index) + 1 = 2; In the pattern aabaa the longest prefix which is also a suffix is aa (sp lps[4] = 2)
            // The pattern to consider at pos4 is aabaa not the whole pattern.
            if(pattern[i] == pattern[index]) {
                lps[i] = index + 1;
                index++;
                i++;
            }else {
                // We did find a match so we go back in the lps array to find the  a match
                // and use the lps value from there. First we check lpts[index-1],
                // this points to len of the prefix and we use it as an index to go back & match.
                // This will allow us to go back in the array and find an entry (if any ) and
                // we can use that size as prefix len.
                // In the above example the for position 8 we fist jump back to lps[3-1] == 2.
                // so now  index = 2  which is  (b) and as b != a
                // We move one backward lps[2-1] == 1 so index = 1 which is 1 now pattern[1] = a
                // so in this case pattern[i ==8] == pattern[index ==1]
                // so we use the above if condition to get lps[8] = 1(index) + 1 = 2
                if(index != 0) {
                    index = lps[index-1];
                }else {
                    lps[i] =0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * KMP algorithm of pattern matching.
     * To do KMP we need to built lps array. This is done in the method above.
     * For the search to have O(m+n) execution time we never go back in the text array,
     * we only move in pattern array the lps value tells use where from the pattern array to start.
     * The lps value holds the prefix length that is also a suffix using this idea we skip position in the
     * pattern array. For example; text =  abxabcabcaby & pattern = abcaby. i is a pointer text & j is pointer to pattern
     * abx abc c & x does not match so next we start from pos 0 in pattern and compare with x
     * for abxabcabc abcaby y & c does not match now we start from pattern pos 2 so from c we
     * only match caby part of the pattern amd reduce the execution
     */
    public boolean KMP(char []text, char []pattern){

        int lps[] = computeTemporaryArray(pattern);
        int i=0;
        int j=0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
    }

    // get first index of the first occurances of pattern in text
    public int kmpIndex(char []text, char []pattern){

        int lps[] = computeTemporaryArray(pattern);
        int i=0;
        int j=0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        if(j == pattern.length){
            return i-j;
        }
        return -1;
    }

    // strStr: substr
    public int strStr(String haystack, String needle) {
        return kmpIndex(haystack.toCharArray(),needle.toCharArray());
    }


    /** Rabin Karp algorith for substring matching.
     *
     * Time complexity in worst case O(n^2)(depends on hash function)
     * Space complexity O(1)
     *
     * References
     * * https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorithm
    **/
    private int prime = 101;

    public int patternSearch(char[] text, char[] pattern){
        int m = pattern.length;
        int n = text.length;
        long patternHash = createHash(pattern, m - 1);
        long textHash = createHash(text, m - 1);
        for (int i = 1; i <= n - m + 1; i++) {
            if(patternHash == textHash && checkEqual(text, i - 1, i + m - 2, pattern, 0, m - 1)) {
                return i - 1;
            }
            if(i < n - m + 1) {
                textHash = recalculateHash(text, i - 1, i + m - 1, textHash, m);
            }
        }
        return -1;
    }
    private long recalculateHash(char[] str,int oldIndex, int newIndex,long oldHash, int patternLen) {
        long newHash = oldHash - str[oldIndex];
        newHash = newHash/prime;
        newHash += str[newIndex]*Math.pow(prime, patternLen - 1);
        return newHash;
    }

    private long createHash(char[] str, int end){
        long hash = 0;
        for (int i = 0 ; i <= end; i++) {
            hash += str[i]*Math.pow(prime,i);
        }
        return hash;
    }

    private boolean checkEqual(char str1[],int start1,int end1, char str2[],int start2,int end2){
        if(end1 - start1 != end2 - start2) {
            return false;
        }
        while(start1 <= end1 && start2 <= end2){
            if(str1[start1] != str2[start2]){
                return false;
            }
            start1++;
            start2++;
        }
        return true;
    }
}
