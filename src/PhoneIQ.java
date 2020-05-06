import sun.jvm.hotspot.utilities.Interval;

import java.util.HashMap;

public class PhoneIQ {
    /*
     * FB PHIQ
     * Let us say that we already have an Api drawPoint(int x, int y),
     * which can draw a point at position(x,y) on screen.
     * I would like you to implement drawCircle(int a, int b, int r)
     * which should draw a circle whose center is (a,b) and radius is r
     * */
    // based on: x = a + r * cos t; y = b + r * sin t
    public static void drawPoint(int x, int y) {
        // dummy function
    }
    public static void drawCircle(int a, int b, int r)
    {
        for (int angle = 0; angle < 360; ++angle)
        {
            int x = (int)(a + (r * Math.cos(angle)));
            int y = (int)(b + (r * Math.sin(angle)));
            drawPoint(x, y);
        }
    }
    // based on: r*r = (x-a)*(x-a) + (y-b)*(y-b)
    public static void drawCircle2(int a, int b, int r) {
	    int r_sqr = r * r;
        for (int x = 0; x <= r; ++x)
        {
            int y = (int)(Math.sqrt(r_sqr - x*x));
            drawPoint(a + x, b + y);
            drawPoint(a + x, b - y);
            drawPoint(a - x, b + y);
            drawPoint(a - x, b - y);
        }
    }
    // Given two arrays with equal length we need to find if the first array can be converted
    // to second array by any number of subarray reversal of the 2nd array
    public static boolean equalArrays(int []nums1, int []nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums1.length; i++) {
            int count = map.getOrDefault(nums1[i], 0);
            map.put(nums1[i],count +1);
        }
        for (int i = 0 ; i < nums2.length; i++) {
            if(map.containsKey(nums2[i])) {
                int count = map.get(nums2[i]);
                if (count == 0)
                    return false;
                map.put(nums2[i],count -1);

            } else
                return false;

        }
        return true;
    }




}
