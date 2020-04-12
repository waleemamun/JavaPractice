public class SolutionsV1 {
    // Commpare two double numbers
    public int compareDoubles(String version1, String version2) {
        int i = 0;
        int j = 0;
        while (i < version1.length() && version1.charAt(i) == '0')
            i++;
        int v1=i;
        while (i < version1.length() && version1.charAt(i) != '.') {
            i++;
        }
        while (j < version2.length() && version2.charAt(j) == '0')
            j++;
        int v2 = j;
        while (j < version2.length() && version2.charAt(j) != '.') {
            j++;
        }
        int len1 = i - v1;
        int len2 = j - v2;

        if(len1 > len2) {
            return 1;
        }
        else if(len1 < len2) {
            return -1;
        }
        i = v1;
        j = v2;
        char ch1, ch2;
        while (i < version1.length() || j < version2.length()) {

            if (version1.length() == i) {
                ch1 = '0';
            }
            else {
                ch1 = version1.charAt(i++);
            }
            if (j == version2.length()) {
                ch2 = '0';
            }
            else {
                ch2 = version2.charAt(j++);
            }
            if(ch1 =='.' || ch2 =='.') continue;
            else if (ch1 > ch2)
                return  1;
            else if (ch1 < ch2){
                return -1;
            }
        }

        return 0;
    }
}
