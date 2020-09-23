public class SolutionsV3 {

    // LeetCode :: 273. Integer to English Words
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

}
