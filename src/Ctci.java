import java.util.Arrays;

public class Ctci {

    // CTCI:: 16.16 Sub-Sort
    // find the mid indices that will sort the whole arraya
    // for example 1, 2, 4, 7. 10, 11, 8, 12, 6, 9, 16, 17, 18
    int [] findUnsortedIndex(int []nums) {
        int [] res = new int [2];
        int leftEnd = 0;
        for(int i = 1; i< nums.length; i++) {
            if (nums[i-1] > nums[i]){
                leftEnd = i-1;
                break;
            }

        }
        int rightEnd = 0;
        for (int i = nums.length-2; i >= 0; i--) {
            if (nums[i+1] < nums[i]){
                rightEnd = i+1;
                break;
            }
        }
        int maxInd = -1;
        int minInd = rightEnd;
        System.out.println(leftEnd + " "  + rightEnd);
        int max = nums[leftEnd];
        int min = nums[rightEnd];
        for (int i = leftEnd + 1; i<rightEnd;i++) {
            if(nums[i] > max) {
                max= nums[i];
                maxInd = i;
            }
            if (nums[i] < min) {
                min = nums[i];
                minInd = i;
            }
        }
        System.out.println(nums[maxInd] + " " + nums[minInd]);
        for (int i = leftEnd; i >= 0; i--) {
            if (nums[minInd] >= nums[i]) {
                res[0] =i+1;
                break;
            }
        }
        for(int i =rightEnd; i< nums.length; i++) {
            if (nums[maxInd] <= nums[i]) {
                res[1] = i-1;
                break;
            }
        }
        System.out.println(Arrays.toString(res));
        return res;
    }

}
