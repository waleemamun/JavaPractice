import java.util.HashMap;

public class SolutionsV3 {

    // LeetCode :: 273. Integer to English Words (Hard)
    public String get100s (int num) {
        String [] oneMap = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String [] tensMap = {"","", "Twenty", "Thirty", "Forty", "Fifty",
                "Sixty", "Seventy", "Eighty", "Ninety",
                "Ten", "Eleven" ,"Twelve", "Thirteen", "Fourteen","Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        StringBuilder sb = new StringBuilder();
        if (num/100 > 0) {
            sb.append(oneMap[num/100]);
            sb.append("Hundred");
            num %= 100;
        }
        if (num >= 10 && num <= 19)
            sb.append(tensMap[num]);
        else if (num >= 20 && num <100) {
            sb.append(tensMap[num/10]);
            sb.append((num % 10 == 0)? "":" ");
            sb.append(oneMap[num%10]);
        } else {
           sb.append(oneMap[num%10]);
        }
        return sb.toString();

    }
    public String numberToWords(int num) {
        StringBuilder sb = new StringBuilder();
        int [] divider = {1000000000,
                          1000000,
                          1000,
                          1};
        String [] suffix = {" Billion ", " Million " ," Thousand ", ""};
        int i = 0;
        while (num > 0 && i < divider.length) {
            if (num/divider[i] > 0) {

                sb.append(get100s(num/divider[i]));
                sb.append(suffix[i]);
                num %= divider[i];

            }
            i++;
        }
        if (sb.charAt(sb.length() -1) == ' '){
            sb.deleteCharAt(sb.length() -1);
        }
        return sb.toString();
    }

    // LeeCode :: 340. Longest Substring with At Most K Distinct Characters (Hard)
    // This is also sliding window problem. It is solved similar to LeetCode :: 1004. Max Consecutive Ones III
    // In this type of sliding window problem we are asked to find 'At Most K' when we are looking for at most k we can
    // increase the sliding window to grow k+1 size instead of k size. That we we can cover the consecutive elements for
    // example bacccde and at most k =2  when we consider k + 1 = 2 +1 = 3 size window we can take 'acccd' as our window
    // thus covering th consequtive 'c'.
    // Check the version 2 also int exactly same just better performance because of char map intead of hashmap and char
    // array instead of String s
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int max  = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int unique = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            char ch = s.charAt(right);
            int count = map.getOrDefault(ch, 0);
            if (count == 0)
                unique++;
            map.put(ch, count +1);
            // increase sliding window to k+1 instead of k as we need to support at most k items
            while (unique == k + 1) {
                char ch2 = s.charAt(left);
                int cnt = map.get(ch2);
                // removed onde unique char lets break sliding window
                if(cnt == 1) {
                    unique--;
                }
                map.put(ch2, cnt - 1);
                // update length using right - left as we consider k+1 item
                // so we dont need to use right - left +1
                max = Math.max(max, right - left);
                left++;
            }
            right++;
        }
        // this handles corner case when we reached the end of the string but did not handle the last window
        // so check if last window is bigger
        if (unique <= k)
            max = Math.max(max,right -left);
        return max;
    }

    public int lengthOfLongestSubstringKDistinctV2(String s, int k) {
        int max  = 0;
        char []map = new char[128];
        int unique = 0;
        int left = 0;
        int right = 0;
        char []str = s.toCharArray();
        while (right < str.length) {
            char ch = str[right];
            if (map[ch] == 0)
                unique++;
            map[ch]++;
            while (unique == k + 1) {
                char ch2 = str[left];
                if(map[ch2] == 1) {
                    unique--;
                }
                map[ch2]--;
                max = Math.max(max, right - left);
                left++;
            }
            right++;
        }
        if (unique <= k)
            max = Math.max(max,right -left);
        return max;
    }


}
