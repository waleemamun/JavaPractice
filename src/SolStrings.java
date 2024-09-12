import java.util.*;
public class SolStrings {
    // LC:: 647. Palindromic Substrings
    // It would be a mistake to generate all the palindrome we just need to find out the number of palindromes.
    // The idea is to use the same approach as the leetcode's longest palindrome string problem
    // we take a character and expands on both side to see how ig of palindrome we can make using
    // this character as a center or one of the center char. Everytime we can expand gives us one new palindrome
    // hence we can count the palindrome this way. In the LC::5 problems we found out the length and here we count
    // the length.
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
}
