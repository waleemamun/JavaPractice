public class Bits {

    // NUmber of set bits count O(s) where s  is the number bits
    public static int countNumberOfSetBits (int x) {
        int count = 0;
        int y = 0;
        while ( x != 0) {
            // this  will set the value of y to the lowest bit position in x
            // for example if x is 6 (0110) y wil have value 2 (0010)
            y = x & ~(x-1);
            // this will unset the y bit position in x, we exit loop when x becomes zero
            x = x ^ y;
            count++;

        }
        return count;
    }
    public static int isSetBit (int x, int index) {
        return x & (1 << index);
    }

    public static int getOnes (int numbers) {
        return (1 << numbers) - 1;
    }

    public static int getZeros (int numbers) {
        return ~((1 << numbers) - 1);
    }

    public static int getNextSetBits (int num) {
        if (num <= 0)
            return 0;

        int numOnes = 0;
        int numZeros =0;
        int pos = 0;
        //find the first one and count number of zeros before that
        while (isSetBit(num,pos) == 0){
            pos++;
            numZeros++;
        }

        // find the next zero so 0 <- 1 transition
        // count number of ones
        while (isSetBit(num,pos) != 0) {
            numOnes++;
            pos++;
        }

        // swap bit position pos and pos-1
        num = num | (1 << pos);        // set the the 0 bit
        num = num & ~(1 << (pos -1));  // unset the bit before pos
        numOnes--;                     // decrement the count of one as we unset the but in prev line
        int ones = getOnes(numOnes);   // get the masks for 1s
        num = num | ones;              // push the 1s to the right so the number becomes smaller
        int zeros = getOnes(numZeros); // get the 0s
        zeros = zeros << numOnes;
        num  = num & ~zeros;           // push the zeros just on the right of swap to make the number smaller

        return num;

    }

    public static int getPrevSetBits (int num) {

        if (num <= 0)
            return 0;
        int pos = 0 ;
        int numOnes = 0;
        int numZeros = 0;
        while ( isSetBit(num,pos) !=0) {
            numOnes++;
            pos++;
        }
        while (isSetBit(num,pos) == 0) {
            pos++;
            numZeros++;
        }
        num = num & ~(1 << pos);
        num = num | (1 << (pos-1));
        numZeros--;
        int zeros = getZeros(numZeros);
        num = num & zeros;
        int ones = getOnes(numOnes);
        num = num | (ones << numZeros);

        return num;
    }
}
