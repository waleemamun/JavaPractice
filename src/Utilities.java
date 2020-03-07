public class Utilities {

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

    public static int binSearchNext(int []arr, int low, int high, int value) {
        return 0;
    }
    private static int makeArrayCopy (int nums[] , int buffer[], int start, int end) {
        int entry = 0;
        for (int i = start ; i<= end; i++){
            buffer[entry] = nums[i];
            entry++;
        }
        return entry;
    }




}
