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
    public static int binSearcPos2D (int interval[][], int low, int high, int value) {
        if(low > high) {
            if (low == interval.length)
                return low;
            return high;
        }
        int mid = (low + high)/2;
        if (value == interval[mid][0]) {
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




}
