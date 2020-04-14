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

    //Leetcode :: 53  Maximum SubArray
    // if the value of the ith position is greater than the sum of the left side + the current val
    // the we can just discard the left side of the array.
    // We keep track of the max sum on each update of current sum which gurantees that we wend up with max SUm
    public int maxSubArray(int[] nums) {

        int maxSum = nums[0];
        int currSum = nums[0];

        for(int i = 1; i < nums.length; i++) {
            currSum += nums[i];
            // the current number is actually greater than the cumulative sum so discard everything
            // on the left and update the current sum to current value as its bigger
            if (nums[i] > currSum ) {
                currSum = nums[i];
            }
            maxSum = Math.max(maxSum,currSum);
        }
        return  maxSum;
    }
}
