import java.util.*;
public class SolStrings {
    // LC:: 647. Palindromic Substrings
    // It would be a mistake to generate all the palindrome we just need to find out the number of palindromes.
    // The idea is to use the same approach as the leetcode's longest palindrome string problem
    // we take a character and expands on both side to see how ig of palindrome we can make using
    // this character as a center or one of the center char. Everytime we can expand gives us one new palindrome
    // hence we can count the palindrome this way. In the LC::5 problems we found out the length and here we count
    // the length.
    // O(n^2)
    public int palindromeCount(String s, int l, int r){
        int count = 0;
        while(l>=0 && r <s.length() && s.charAt(l--) == s.charAt(r++)){
            count++;
        }
        return count;

    }
    public int countSubstrings(String s) {
        int count = 0;
        // Because of this loop we will correctly count for 'aaa' as we will do it for all the index one at a time
        for (int i = 0; i < s.length(); i++){
            count += palindromeCount(s, i, i);
            count += palindromeCount(s, i, i + 1);
        }
        return count;
    }
    // LC :: 5. Longest Palindromic Substring
    // O(n^2)
    public int palindromeLength(String s, int l, int r){
        int len = 0;
        while(l>=0 && r <s.length() && s.charAt(l) == s.charAt(r)){
            len = r - l + 1;
            l--;
            r++;
        }
        return len;
    }
    public String longestPalindrome(String s) {
        int maxLen = 0;
        int st = 0, end = 0;
        for (int i = 0; i < s.length(); i++){
            int odd = palindromeLength(s, i , i);
            int even = palindromeLength(s, i , i+1);
            int len = Math.max(odd, even);
            if (maxLen <= len) {
                end = i + len/2;
                st = i - (len -1)/2;
            }
        }
        return s.substring(st, end + 1);
    }

    public String repeatLimitedString(String s, int repeatLimit) {
        int [] freq = new int[27];
        int p = 26;
        for (int i = 0; i <s.length(); i++) {
            freq[s.charAt(i)-'a']++;
        }
        while(p>=0 && freq[p--]==0);
        p++;
        StringBuilder res  = new StringBuilder();

        while(res.length() <= s.length()) {
            int left = 26, right = 0;
            int len = Math.min(freq[p], repeatLimit);
            freq[p]-=len;
            for (int i = 0; i<len; i++)
                res.append(p+'a');
            int lastp = p;
            p++;
            while (p < left && freq[p] == 0)
                p++;
            if (p==left) {
                p = lastp - 1;
                while(p>=0 && freq[p] == 0)
                    p--;
            }
            if (p == 0)
                break;

        }
        return res.toString();

    }

}
