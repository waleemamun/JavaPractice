

import java.util.*;

public class Ctci {

    // CTCI:: 16.16 Sub-Sort
    // find the mid indices that will sort the whole arraya
    // for example 1, 2, 4, 7. 10, 11, 8, 12, 6, 9, 16, 17, 18
    // Find the left sorting & right sorting ends and findle the middle that is unsorted
    // after that find the max of left & middle and find the proper sorted index for that max value on the rightEnd
    // similarly find the min of right & middle and find the proper sorted index for that min value on the lefttEnd
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
    // swap values CTCI 16.21
    int[] findSwapValues(int[] nums1, int[] nums2) {
        HashSet<Integer> lookUpTable = new HashSet<>();
        int sum1 = 0;
        int sum2 = 0;
        int []res = new int[2];

        for (int n : nums1) {
            sum1+= n;
            lookUpTable.add(n);
        }

        for (int n : nums2)
            sum2+= n;

        int diff = Math.abs(sum1 -sum2);

        for (int n : nums2) {
            if (n <= diff && lookUpTable.contains(diff - n)) {
                res[0] = n;
                res[1] = diff - n;
                break;
            }
        }
        return res;

    }

    ArrayList<Pair<Integer,Integer>> printPairSum(int []nums, int target){
        ArrayList<Pair<Integer,Integer>> pairs = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            int comp = target - n;
            if (map.getOrDefault(comp, 0) > 0) {
                pairs.add(new Pair<>(n,comp));
                map.put(comp, map.getOrDefault(comp, 0)-1);
            } else
                map.put(n, map.getOrDefault(n, 0) +1);
        }
        return pairs;
    }

    /**
     * Create an empty stack called opstack for keeping operators. Create an empty list for output.
     *
     * Scan the String/token list from left to right.
     *
     * If the token is an operand, append it to the end of the output list.
     *
     * If the token is a left parenthesis, push it on the opstack.
     *
     * If the token is a right parenthesis, pop the opstack until the corresponding left parenthesis is removed.
     * Append each operator to the end of the output list.
     *
     * If the token is an operator, *, /, +, or -, push it on the opstack.
     * However, first remove any operators already on the opstack that have higher or
     * equal precedence and append them to the output list.
     *
     * When the input expression has been completely processed, check the opstack.
     * Any operators still on the stack can be removed and appended to the end of the output list.
     * */
    public String inFixtoPostFix(String str) {
        int i = 0;
        HashMap<Character, Integer> precedence = new HashMap<>();
        Stack<Character> operatorStack = new Stack<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('^', 3);
        precedence.put('(', 0);
        StringBuilder sb = new StringBuilder();
        while (i < str.length()) {
            char ch = str.charAt(i);

            if (Character.isDigit(ch)) {
                int val = 0;
                while(i < str.length() && Character.isDigit(str.charAt(i))) {
                    val *= 10;
                    val += (str.charAt(i) - '0');
                    i++;
                }
                sb.append(val);
                continue;
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while(!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    sb.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {

                while(!operatorStack.isEmpty() &&
                        precedence.get(operatorStack.peek()) >= precedence.get(ch)) {
                    sb.append(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
            i++;

        }
        while(!operatorStack.isEmpty()){
            sb.append(operatorStack.pop());
        }
        return sb.toString();
    }

    // CTCI 17.5 longest consecutive subarray with equal numbers of digit & letter count
    // The problem should not be solved with a sliding window as the appending the next item changes the calc
    // When we are checking for equal number of digit & letter in a subarray we are actually requiring the lower
    // count of digit & letter. The lower count of the two is the number that we are interested in. So we need to
    // find the char count for digit/letter as we scan the char array. And calculate the current diff of letter/digit
    // count. Notice that the diff repeats and when the diff repeats it means we have seen same number of letter &
    // digit count between the repeating diff. So we keep track of the max distance between repeating diffs and
    // that max is our solution.
    char [] findLongestSubArray (char []str) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int letterCount = 0;
        int digitCount = 0;
        int st = 0;
        int end = 0;
        int maxLen = 0;
        // This initial value setup is required to handle the corner case when the solution index starts from zero
        // & the repeating diff is zero for example: abcde123345
        map.put(0,-1);
        for (int i = 0; i < str.length; i++) {
            if (Character.isDigit(str[i]))
                digitCount++;
            if (Character.isLetter(str[i]))
                letterCount++;
            int diff = Math.abs(letterCount - digitCount);
            int oldIdx = map.getOrDefault(diff, -2);
            if (oldIdx != -2) {
                int len = i -oldIdx;
                if(len >= maxLen){
                    maxLen = len;
                    // Note we are interested in the index between the repeating diff so the start index
                    // start with last diff index + 1
                    st = oldIdx +1;
                    end = i;
                }
            }
            map.putIfAbsent(diff, i);
        }
        return Arrays.copyOfRange(str, st, end +1);
    }
    // CTCI :: 17.19 missing two
    // Same idea as two number exactly once and all numbers twice
    // see LeetCode :: 260. Single Number III (same idea)
    // we are using the xor property that two similar number xor to zero
    int [] missingTwo (int []nums, int N) {
        int res[] = new int [2];
        int xor1toN = 0;
        // xor 1 to N
        for (int i = 1; i<= N; i++) {
            xor1toN ^= i;
        }
        int xorArrval = 0;
        // xor all number in array
        for (int n : nums){
            xorArrval ^= n;
        }
        // get the xor of the missing two number & get the last set bit of this number
        int xorMissing = xor1toN ^ xorArrval;
        int lstSetBit = xorMissing & ~(xorMissing -1);
        // as we have the last set bit it will put number in two buckets for 1 to N
        for (int i =1 ;i<=N; i++){
            if ((i & lstSetBit) == 0){
                res[0] ^= i;
            } else {
                res[1] ^= i;
            }
        }
        // as we have the last set bit it will put number in two buckets for all number in array
        // notice at this point all number from 1 to N that is present in the array will become zero due to xor property
        // of sam number xor twice becoming zero. So the only remaining numbers are the missing two numbers
        for (int n : nums){
            if ((n & lstSetBit) == 0){
                res[0] ^= n;
            } else {
                res[1] ^= n;
            }
        }
        return res;
    }
}
