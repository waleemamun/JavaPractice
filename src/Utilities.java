import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.Comparator;

public class Utilities {

    // Remember to call with high == arr.length - 1 as high is
    // the highest index of the array not the length
    public static int binSearch (int []arr, int low, int high, int value) {
        if (low > high)
            return -1;

        int mid = (low + high)/2;

        if (arr[mid] == value)
            return mid;
        else if (arr[mid] > value) {
            return binSearch(arr, low, mid -1, value);
        } else {
            return binSearch(arr, mid+1, high,value);
        }
    }

    // Remember to call with high == arr.length - 1 as high is
    // the highest index of the array not the length
    public static int binSearchPos(int []arr, int low, int high, int value) {
        if(low > high) {
            if (low == arr.length)
                return low;
            return high;
        }

        int mid = (low + high)/2;
        //System.out.println(low + " " + mid + " " + high);
        if(value == arr[mid]) {
            return mid;
        } else if (value < arr[mid]) {
            return binSearchPos(arr, low, mid -1, value);
        } else
            return binSearchPos(arr,mid+1, high, value);

    }
    // Remember to call with high == arr.length - 1 as high is
    // the highest index of the array not the length
    // search in the interval array for a value, if value found return that interval index
    // if not found return where the value can be inserted, corner cases are
    // if the value is smaller the lowest interval return -1,
    // If value is greater than highest interval then return length of the array
    public static int binSearcPos2D (int interval[][], int low, int high, int value) {
        if(low > high) {
            if (low == interval.length)
                return low;
            if (high < 0)
                return  high;
            return interval.length + low;
        }
        int mid = (low + high)/2;
        if (value >= interval[mid][0] && value <= interval[mid][1]) {
            return mid;
        } else if (value < interval[mid][0]) {
            return binSearcPos2D(interval, low, mid-1, value);
        } else
            return binSearcPos2D(interval, mid + 1, high, value);

    }
    private static int makeArrayCopy (int nums[] , int buffer[], int start, int end) {
        int entry = 0;
        for (int i = start ; i<= end; i++){
            buffer[entry] = nums[i];
            entry++;
        }
        return entry;
    }
    public static void sortStringList (ArrayList<String> strList) {
        strList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

    }

    // split the string by the delim, if you have repeated delim that will be skipped
    // hence all string size in the returned ArrayList is greater than zero

    public static ArrayList<String> splitStr(String str, char delim){
        ArrayList<String>  listStr = new ArrayList<>();
        int i = 0;
        StringBuilder tempSb = new StringBuilder();
        boolean inWord = true;
        while (i < str.length()) {
            if (inWord && str.charAt(i) == delim) {
                if(tempSb.length() != 0)
                    listStr.add(tempSb.toString());
                inWord = false;
            } else if (inWord && str.charAt(i)!= delim) {
                tempSb.append(str.charAt(i));
            } else {
                inWord = true;
                tempSb = new StringBuilder();
                if (str.charAt(i) != delim)
                    tempSb.append(str.charAt(i));
            }
            i++;
        }
        if(str.charAt(str.length() -1) != delim)
            listStr.add(tempSb.toString());
        return listStr;

    }

    // This compute the Longest Common Substring Size. remember this is for substring not sub sequence
    // So the DP eqn  lcs[i][j] = lcs[i-1][j-1] + 1 (if str1[i] == str2[j]) otherwise lcs[i][j] = 0
    // cause we are interested in substring so the substring has to appear in both string hence if
    // not math its should be zero
    public static int getLongestCommonSubstrSize(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int [][]lcs = new int [n+1][m+1];

        // init the lcs array the 1st row & columns should be zero
        for (int i = 0; i<=n; i++)
            lcs[i][0] = 0;
        for (int i = 0; i <= m; i++)
            lcs[0][i] = 0;
        int result = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                // to determine longest common substring, if the current char from str1 & str2 matches
                // then we increments the size by 1, by adding 1 with the previous substring
                // so the DP solution is Lcs (i,j) = lcs(i-1, j-1) + 1  if the char at i & j position matches
                // otherwise lcs(i,j) = 0
                // For this we dont need to consider any other position for example (i-1,j) or(i,j-1)
                // cause the substring length at (i,j) is important
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    result = Math.max(result,lcs[i][j]);
                }
                else
                    lcs[i][j] = 0;
            }
        }

        return result;
    }
    public static int getLCSSize(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int [][]lcs = new int [n+1][m+1];

        // init the lcs array the 1st row & columns should be zero
        for (int i = 0; i<=n; i++)
            lcs[i][0] = 0;
        for (int i = 0; i <= m; i++)
            lcs[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // we match if the current chars on both string are same,
                // if yes then we can increase the LCS by add 1 to the previous LCS size at i-1, j-1
                // dont get confused by the str1.charAt(i-1) == str2.charAt(j-1) the string index are starting
                // from zero hence we need to use i-1 & j-1, cause LCS (i,j) start with index 1.
                // In the LCS array 0 index means null string, so when we init we set col 0 & row 0 to zero
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                }
                else
                    lcs[i][j] = Math.max(lcs[i][j-1],lcs[i-1][j]);
            }
        }

        return lcs[n][m];
    }

    public static String getLCS(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int [][]lcs = new int [n+1][m+1];
        int [][]path = new int[n+1][m+1];



        // init the lcs array the 1st row & columns should be zero
        for (int i = 0; i<=n; i++) {
            lcs[i][0] = 0;
            path[i][0] = 0;
        }
        for (int i = 0; i <= m; i++){
            lcs[0][i] = 0;
            path[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // we match if the current chars on both string are same,
                // if yes then we can increase the LCS by add 1 to the previous LCS size at i-1, j-1
                // dont get confused by the str1.charAt(i-1) == str2.charAt(j-1) the string index are starting
                // from zero hence we need to use i-1 & j-1, cause LCS (i,j) start with index 1.
                // In the LCS array 0 index means null string, so when we init we set col 0 & row 0 to zero
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    lcs[i][j] = lcs[i-1][j-1] + 1;
                    path[i][j] = 3;
                }
                else {
                    lcs[i][j] = Math.max(lcs[i][j-1],lcs[i-1][j]);
                    path[i][j] = lcs[i][j-1] >= lcs[i-1][j] ? 1 : 2 ;
                }
            }
        }
        int i = n;
        int j = m;
        StringBuilder lcsStr = new StringBuilder();
        while (i > 0 && j >0 ) {
            //System.out.println(i+" " +j + " " + path[i][j]);
            if(path[i][j] == 3) {
                lcsStr.append(str1.charAt(i-1));
                i--;
                j--;
            }
            else if (path[i][j] == 1) {
                j--;
            } else if (path[i][j] == 2) {
                i--;
            } else
                break;
        }
        lcsStr.reverse();
        return lcsStr.toString();
    }

    public static int partition (int nums[], int left, int right) {
        int pivot = left + (int)(Math.random() % (right -left +1));
        int pivotVal = nums[pivot];
        nums[pivot] = nums[right];
        nums[right] = pivotVal;
        int i = left;
        int j = left;
        while (i <= right) {
            if (nums[i] < pivotVal) {
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
            i++;
        }
        nums[right] = nums[j];
        nums[j] = pivotVal;

        return j;
    }

    public static int quickSelect (int []nums, int left, int right, int k) {
        if (left == right)
            return nums[left];
        int pivotIndex = partition(nums, left, right);
        if (k == pivotIndex)
            return nums[k];
        else if (k < pivotIndex)
            return quickSelect(nums, left, pivotIndex -1, k);
        else
            return quickSelect(nums, pivotIndex+1, right, k);
    }

    public static int quickSelectKthSmallest (int []nums, int k) {
        return quickSelect(nums, 0, nums.length-1, k-1);
    }

    public static int quickSelectKthLargest (int []nums, int k) {
        int item = nums.length - k;
        // we pass item here as the k-1 th smallest item because for kth smallest we pass k-1
        // so item = nums.length - k + 1 and item -1 == nums.length - k
        return quickSelect(nums, 0, nums.length-1, item);
    }

    public static double medianOfUnsortedArray(int []nums) {
        int low = 0;
        int high = nums.length-1;
        int mid = low + (high-low)/2;
        double res = 0.0;
        System.out.println(mid);
        res = quickSelectKthSmallest(nums,mid +1);
        if(nums.length % 2 == 0) {
            int min = Integer.MAX_VALUE;
            for (int i = mid+1 ;i <nums.length;i++) {
                min = Math.min(min, nums[i]);

            }
            System.out.println(res+" "+ min);
            res = (res+min)/2.0;
        }
        return res;
    }





}
