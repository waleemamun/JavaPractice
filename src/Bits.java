import java.util.ArrayList;
import java.util.List;

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

    // multiply without multiplication operator
    // use multiplication method learned in school (choto bela :))
    public static long multiply (long x, long y) {
        long result = 0;
        int k = 0;

        while (x != 0) {
            if ((x & 0x1) == 1) {
                result += (y << k);
            }
            k++;
            x >>>= 1; // we are doing right shift lets do unsigned shift using '>>>'
        }
        return result;
    }

    // add without addition operation
    public static long add (long a, long b) {
        long sum = 0;
        long carryin = 0;
        long carryout = 0;
        long tempA = a;
        long tempB = b;
        int k = 1;

        while (tempA != 0 || tempB != 0){
            long ak = a & k;          // get kth pos in a
            long bk = b & k;          // get kth pos in b
            carryout = (ak & bk) | (ak & carryin) | (bk & carryin);
            sum |= (ak ^ bk ^ carryin);
            carryin = carryout << 1;  // need to shift we need to move carryin to be kth position too
            k <<= 1;
            tempA >>>= 1;
            tempB >>>= 1;
        }

        return sum | carryin;
    }

    // LeetCode 29 :: divide two number
    public static int divide (long x, long y) {


        // both same no need to calc more
        if (x == y)
            return 1;
        // if divisor is 1 return dividend
        if (y == 1)
            return (int)x;
        // This is a very special case
        if (y == -1 && x == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        // make the number positive
        // we keep track of the +/- and handle it in return
        boolean isNeg = false;
        // can replace the following logic with
        // if ((x<0) ^ (y<0))
        // isNeg = true
        if (y < 0) {
            y = -y;
            isNeg = true;
        }
        if (x < 0) {
            x =-x;
            if (isNeg == true)
                isNeg = false;
            else
                isNeg = true;
        }

        long quotient = 0;
        long reminder = 0;
        int power = 32;
        long ypower = y << power; // 2^ky

        // idea is to find  2^ky <=x then add 2^K to the quotient
        // and update reduce x - 2^ky. For the news k again find 2^Ky <= x

        while (x >= y) {
            while (ypower > x) {
                ypower >>>= 1; // 2^ky/2 == (2^k-1)y
                power--;
            }
            quotient += 1L << power;
            x -= ypower;

        }
        reminder = x;
        System.out.println("reminder " + reminder);

        return (int) (isNeg ? -quotient: quotient);
    }

    // LeetCode :: 89. Gray Code
    // The grey code has an interesting property, if you know n bits grey code then
    // for n+1 bit its just  (1<<n | mirrored nbits) for example 2 bit is 00 01 11 10
    // so 3 bits 100 101 111 110 000 001 011 010. Now if we want ot generate grey code for n bits
    // we can start adding 0 & 1 to a list and visit the list in reverse order and while visiting
    // add those entry with i+1 bit set to 1.
    public List<Integer> grayCode(int n) {
        List <Integer> rList = new ArrayList<>();
        int i = 2;
        if (n == 0) {
            rList.add(0);
            return rList;
        }
        // add the basic 0 & for 1 bit option
        rList.add(0);rList.add(1);
        // outer loops just iterates for each bit position
        while (i <= n) {
            int j = rList.size() - 1;
            // set the msb for ith bit to 1 and add all
            // the items from the list for (i-1) bit
            int x = 1 << (i-1);
            while (j >= 0) {
                rList.add(x | rList.get(j--));
            }
            i++;
        }
        return rList;
    }

    // LeetCode :: 136. Single Number
    // Find the only single number in array where all other numbers exactly twice
    // We can use the property of xor to find the single number we xor all the entries
    // in the array the final result will our single number as xor of a number with
    // itself makes it zero so the number that is not repeated remains at the end
    public int singleNumberI(int[] nums) {
        int single = 0;
        for (int i = 0; i <nums.length; i++){
            single ^= nums[i];
        }
        return single;
    }

    // LeetCode :: 137. Single Number II
    // The
    public int singleNumberv2(int[] nums) {
        int single = 0;
        for (int i = 0; i<32; i++) {
            int sum = 0;
            for (int j = 0; j <nums.length; j++){
                if ((nums[j] & (1<<i)) != 0) {
                    sum++;
                }

            }
            if (sum % 3 == 1) {
                single |= (1<<i);
            }
        }
        return single;
    }
    // Best solution

    public int singleNumber(int[] nums) {
        int x1 = 0, x2 = 0, mask = 0;

        for (int i : nums) {
            x2 ^= x1 & i;
            x1 ^= i;
            mask = ~(x1 & x2);
            x2 &= mask;
            x1 &= mask;
        }

        return x1;  // Since p = 1, in binary form p = '01', then p1 = 1, so we should return x1.
        // If p = 2, in binary form p = '10', then p2 = 1, and we should return x2.
        // Or alternatively we can simply return (x1 | x2).
    }

    // Adnan-Aziz 5.1
    // Check parity return 1 if odd numbers of 1 in a number
    public int checkParity (int x){
        int result = 0;
        while (x!=0) {
            result ^= 1; // odd number 1 set it even number of 1 unset it due to xor
            x &= x -1; // drop the last 1 bit
        }
        return result;
    }

    // LeetCode :: 231 Power of Two
    public boolean isPowerOfTwo(int n) {
        int x = n&(n-1);
        return (n>0 && x==0)?true:false;
    }

    // Adnan-Aziz 5.7 pow(x,y)
    // use the binary representation of y
    public double computePower(double x, int y) {
        double result = 1.0;
        if (y < 0){
            x = 1.0/x;
        }
        while (y != 0) {
            // if the bit position is 1 we store in result
            if((y&1) != 0) {
                result *= x;
            }
            // otherwise we increase x's power for example x^4 becomes x^8
            x *= x;
            y >>>= 1;
        }
        return result;
    }
    //LeetCode :: 201. Bitwise AND of Numbers Range
    // Check V2 that is the Better Solution
    // We need to find the pattern here Look closely at 1100 & 1111
    // The idea here is consecutive odd & even creates a zero in last bit position
    // The pattern is while the two numbers are not equal they very in last bit postion so get rid of the
    // last bit position until they are same (1100,1111) -> (110,111) -> (11,11)< same so stop>
    public int rangeBitwiseAnd(int m, int n) {
        int zerBitCount = 0;
        while (m != n) {
            zerBitCount++;
            m >>=1;
            n >>=1;
        }
        return m << zerBitCount;
    }

    // The idea is to use the basic logic of n = n & (n-1) this sets the last bit of n to zero
    // We can use this idea and start from the highest number and start dropping last bit from
    // the highest number until the highest become smaller than or equal to lowest
    public int rangeBitwiseAndV2(int m, int n) {
        while (n > m) {
            n&=(n-1);
        }
        // now we have dropped as many bits as possible from the higher value n,
        // so now m & n will give the actual number as n is already less than m.
        return m & n;
    }

    //LeetCode :: 260. Single Number III
    // each number appear twice & two numbers appear once. We need to find the two unique numbers
    public int[] singleNumberTwoUnique(int[] nums) {
        int []uniqeNum = {0,0};
        int xorAll = 0;
        // xor all numbers to get xor of two unique numbers
        for (int n: nums) {
            xorAll ^= n;
        }
        // this gets the last set bit,
        xorAll &= -xorAll;
        // Now xorAll has 1 bit set and that bit is set in one unique number
        // and unset in another unique number. So we group all the numbers from
        // the array in to two groups based on the 1 set bit of xorAll. Note that
        // the duplicate numbers will fall into the same groups so XORing them will
        // cancel the duplicates on each group and at the end we end up with only
        // the unique numbers in the two groups
        for (int n: nums) {
            if ((xorAll & n) == 0) { // that bit is not set for this grp
                uniqeNum[0] ^= n;
            } else                   // that bit is set for this grp
                uniqeNum[1] ^= n;
        }

        return uniqeNum;
    }

    // LeetCode :: 405. Convert a Number to Hexadecimal (not submitted)
    public String toHex(int num) {
        char []hashChar = {'0','1','2','3', '4', '5','6','7','8','9', 'a', 'b','c','d', 'e','f' };
        int mask = 0xF;
        StringBuilder sb = new StringBuilder();
        while(num != 0) {
            int key = num & mask;
            sb.append(hashChar[key]);
            // do a unsigned shift
            // Note: when  reducing the value inside a loop always do an unsigned shift;
            num >>>= 4;
        }
        return  sb.reverse().toString();
    }

    //leetCode :: 50 pow
    public double myPow(double x, int n) {
        if (n == 0)
            return 1.0;
        if (x == 1)
            return 1.0;
        if (x == -1) {
            if(n%2 == 0)
                return 1.0;
            else
                return -1.0;
        }
        if (n == Integer.MIN_VALUE)
            return 0;

        double result = 1.0;

        if (n < 0){
            n = -n;
            x = 1.0/x;
        }
        while (n != 0) {
            if( (n & 1) != 0){
                result *= x;
            }
            x *=x;
            n >>>= 1;
        }

        return result;
    }

}
