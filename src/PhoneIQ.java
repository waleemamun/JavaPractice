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
}
