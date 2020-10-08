import java.lang.reflect.Array;
import java.util.*;

public class SolutionsV3 {

    // LeetCode :: 273. Integer to English Words (Hard)
    public String get100s (int num) {
        String [] oneMap = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String [] tensMap = {"","", "Twenty", "Thirty", "Forty", "Fifty",
                "Sixty", "Seventy", "Eighty", "Ninety",
                "Ten", "Eleven" ,"Twelve", "Thirteen", "Fourteen","Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        StringBuilder sb = new StringBuilder();
        if (num/100 > 0) {
            sb.append(oneMap[num/100]);
            sb.append("Hundred");
            num %= 100;
        }
        if (num >= 10 && num <= 19)
            sb.append(tensMap[num]);
        else if (num >= 20 && num <100) {
            sb.append(tensMap[num/10]);
            sb.append((num % 10 == 0)? "":" ");
            sb.append(oneMap[num%10]);
        } else {
           sb.append(oneMap[num%10]);
        }
        return sb.toString();

    }
    public String numberToWords(int num) {
        StringBuilder sb = new StringBuilder();
        int [] divider = {1000000000,
                          1000000,
                          1000,
                          1};
        String [] suffix = {" Billion ", " Million " ," Thousand ", ""};
        int i = 0;
        while (num > 0 && i < divider.length) {
            if (num/divider[i] > 0) {

                sb.append(get100s(num/divider[i]));
                sb.append(suffix[i]);
                num %= divider[i];

            }
            i++;
        }
        if (sb.charAt(sb.length() -1) == ' '){
            sb.deleteCharAt(sb.length() -1);
        }
        return sb.toString();
    }

    // LeeCode :: 340. Longest Substring with At Most K Distinct Characters (Hard)
    // This is also sliding window problem. It is solved similar to LeetCode :: 1004. Max Consecutive Ones III
    // In this type of sliding window problem we are asked to find 'At Most K' when we are looking for at most k we can
    // increase the sliding window to grow k+1 size instead of k size. That we we can cover the consecutive elements for
    // example bacccde and at most k =2  when we consider k + 1 = 2 +1 = 3 size window we can take 'acccd' as our window
    // thus covering th consequtive 'c'.
    // Check the version 2 also int exactly same just better performance because of char map intead of hashmap and char
    // array instead of String s
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int max  = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int unique = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            char ch = s.charAt(right);
            int count = map.getOrDefault(ch, 0);
            if (count == 0)
                unique++;
            map.put(ch, count +1);
            // increase sliding window to k+1 instead of k as we need to support at most k items
            while (unique == k + 1) {
                char ch2 = s.charAt(left);
                int cnt = map.get(ch2);
                // removed onde unique char lets break sliding window
                if(cnt == 1) {
                    unique--;
                }
                map.put(ch2, cnt - 1);
                // update length using right - left as we consider k+1 item
                // so we dont need to use right - left +1
                max = Math.max(max, right - left);
                left++;
            }
            right++;
        }
        // this handles corner case when we reached the end of the string but did not handle the last window
        // so check if last window is bigger
        if (unique <= k)
            max = Math.max(max,right -left);
        return max;
    }

    public int lengthOfLongestSubstringKDistinctV2(String s, int k) {
        int max  = 0;
        char []map = new char[128];
        int unique = 0;
        int left = 0;
        int right = 0;
        char []str = s.toCharArray();
        while (right < str.length) {
            char ch = str[right];
            if (map[ch] == 0)
                unique++;
            map[ch]++;
            while (unique == k + 1) {
                char ch2 = str[left];
                if(map[ch2] == 1) {
                    unique--;
                }
                map[ch2]--;
                max = Math.max(max, right - left);
                left++;
            }
            right++;
        }
        if (unique <= k)
            max = Math.max(max,right -left);
        return max;
    }
    // LeetCode :: 670. Maximum Swap
    // The idea is to find the a bigger number on the right if found swap
    public int maximumSwap(int num) {
        String number = Integer.toString(num);
        char []str = number.toCharArray();

        int idx = 0;
        char max = 0;
        int i =0;
        boolean found = false;
        for(i = 0; i< str.length; i++) {
            max = str[i];
            for (int j = i + 1; j < str.length; j++) {
                if (max <= str[j]) {
                    idx = Math.max(idx, j);
                    max = str[j];
                    found = true;
                }
            }
            if (found)
                break;

        }
        if(i != str.length) {
            char temp = str[idx];
            str[idx] = str[i];
            str[i] = temp;
        }

        return Integer.parseInt(new String(str));
    }
    // this also nice solution keep track of the last index of a digit. Duirng comparison we compare last index with
    // current index for each digit higher than the current digit
    public int maximumSwapV2(int num) {
        char[] A = Integer.toString(num).toCharArray();
        int[] last = new int[10];
        for (int i = 0; i < A.length; i++) {
            last[A[i] - '0'] = i;
        }

        for (int i = 0; i < A.length; i++) {
            for (int d = 9; d > A[i] - '0'; d--) {
                if (last[d] > i) {
                    char tmp = A[i];
                    A[i] = A[last[d]];
                    A[last[d]] = tmp;
                    return Integer.valueOf(new String(A));
                }
            }
        }
        return num;
    }

    // LeetCode :: 311. Sparse Matrix Multiplication
    // The idea is to find out the non zero set for B and when doing a multiply only consider the non-zero values
    public int[][] multiply(int[][] A, int[][] B) {
        List<ArrayList<Integer>> columnList = new ArrayList<>();
        for (int j = 0; j < B[0].length; j++) {
            ArrayList<Integer> nonZeroSet = new ArrayList<>();
            for (int i = 0 ; i < B.length; i++) {
                if (B[i][j] != 0)
                    nonZeroSet.add(i);
            }
            columnList.add(nonZeroSet);
        }
        System.out.println(columnList);
        int [][]C = new int[A.length][B[0].length];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                ArrayList<Integer> tList = columnList.get(j);
                for (Integer n : tList) {
                    C[i][j] += A[i][n] * B[n][j];
                }
            }
        }
        return C;
    }

    // This is very nice way to solve this problem  look how row k is handles before j it means we first try to figure
    // how much one element (A[i][k]) contribute to the partial sum of C[i][j]
    public int[][] multiplyV2(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] C = new int[m][nB];

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < nB; j++) {
                        if (B[k][j] != 0) C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;
    }
    // This is the most optimal solution
    // The idea is to store one of the sparse matrix as a list of column,non-zero-value pair
    // and use that list to perform the multiplication, This is also calculating the how each element of A is
    // contributing to the sum of C[i][j] as before
    public int[][] multiplyV3(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] result = new int[m][nB];
        // First we need to calc the column, non-zero-valu pair of each row of sparse matrix A
        // We store this as tuple (pos i (column), pos i +1 (val)) in a list
        List[] indexA = new List[m];
        for(int i = 0; i < m; i++) {
            List<Integer> numsA = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                if(A[i][j] != 0){
                    // store the column
                    numsA.add(j);
                    // store the value at this row,col so we have two consecutive entries (col, non-zero-val)
                    numsA.add(A[i][j]);
                }
            }
            indexA[i] = numsA;
        }

        for(int i = 0; i < m; i++) {
            List<Integer> numsA = indexA[i];
            for(int p = 0; p < numsA.size() - 1; p += 2) {
                // get the column from the list
                int colA = numsA.get(p);
                // get the value at that column
                int valA = numsA.get(p + 1);
                for(int j = 0; j < nB; j ++) {
                    int valB = B[colA][j];
                    result[i][j] += valA * valB;
                }
            }
        }

        return result;
    }

    // LeetCode :: 692. Top K Frequent Words
    public class WordFreq {
        String str;
        int frequency;
        public WordFreq(String s, int f){
            str = s;
            frequency = f;
        }
    }
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        List<String> aList = new LinkedList<>();
        for (String w : words) {
            map.put(w, map.getOrDefault(w, 0) +1);
        }
        PriorityQueue<WordFreq> minHeap = new PriorityQueue<>(new Comparator<WordFreq>() {
            @Override
            public int compare(WordFreq o1, WordFreq o2) {
                if (o1.frequency == o2.frequency) {
                    // this has to be reverse order than the frequency cause we need sort
                    // lexicographically but as we build minHeap we need reverse lexicographical order hence we put the
                    // - before the compare below. String is big to small in minHeap, now when we will remove from
                    // minHeap the big will be removed before small
                    return -o1.str.compareTo(o2.str);
                } else {
                    // frequency is small to big in minHeap
                    return o1.frequency - o2.frequency;
                }
            }
        });
        for (String s : map.keySet()) {
            int f = map.get(s);
            minHeap.add(new WordFreq(s, f));
            if(minHeap.size() > k)
                minHeap.remove();
        }
        while (!minHeap.isEmpty()) {
            // we need to remove the item from the top of the priority
            // queue add at front of the linked list as we need
            // top K element in Big to small sorted order
            aList.add(0,minHeap.remove().str);
        }
        return aList;

    }
    // The same approach as above only optimisation is we dont need the WordFreq Class as we already have the map
    // storing the frequency per string so we can use that info to build our priority queue of string based on their
    // frequency from the map. So need for a WordFreq Object
    public List<String> topKFrequentV2(String[] words, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        List<String> aList = new LinkedList<>();
        for (String w : words) {
            map.put(w, map.getOrDefault(w, 0) +1);
        }
        PriorityQueue<String> minHeap = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (map.get(o1) == map.get(o2)) {
                    // this has to be reverse order than the frequency cause we need sort
                    // lexicographically but as we build minHeap we need reverse lexicographical order hence we put the
                    // - before the compare below. String is big to small in minHeap, now when we will remove from
                    // minHeap the big will be removed before small
                    return -o1.compareTo(o2);
                } else
                    // frequency is small to big in minHeap
                    return map.get(o1) - map.get(o2);

            }
        });
        for (String s : map.keySet()) {
            //int f = map.get(s);
            minHeap.add(s);
            if(minHeap.size() > k)
                minHeap.remove();
        }
        while (!minHeap.isEmpty()) {
            // we need to remove the item from the top of the priority
            // queue add at front of the linked list as we need
            // top K element in Big to small sorted order
            aList.add(0,minHeap.remove());
        }
        return aList;

    }

    // LeetCode :: 763. Partition Labels
    // The idea is to do a merged intervals approach to the problem so we start with find out the start & end
    // position of a char in the string we store it for all chars in string this gives unsorted over lapping intervals
    // we sort the intervals constant time (only 26 entries). The use the merge overlapping intervals technique
    // The V2 uses arrays list but v1 uses simple 2D array which is a better implementation
    public List<Integer> partitionLabelsV2(String S) {
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            map.putIfAbsent(S.charAt(i), new ArrayList<>());
            if (map.get(S.charAt(i)).size() == 0) {
                map.get(S.charAt(i)).add(i);
                map.get(S.charAt(i)).add(i);
            }
            else {
                map.get(S.charAt(i)).set(1,i);
            }
        }
        ArrayList<ArrayList<Integer>> intervals = new ArrayList<>();
        intervals.addAll(map.values());
        intervals.sort(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if (o1.get(0) == o2.get(0))
                    return o1.get(1) - o2.get(1);
                return o1.get(0) - o2.get(0);
            }
        });
        System.out.println(intervals);
        ArrayList<ArrayList<Integer>> mergedIntervals = new ArrayList<>();
        List<Integer> rList = new ArrayList<>();
        ArrayList<Integer> lastInv = intervals.get(0);
        for (ArrayList<Integer> curInv : intervals) {
            if (lastInv.get(1) < curInv.get(1)) {
                if (curInv.get(0) <= lastInv.get(1)) {
                    lastInv.set(1, curInv.get(1));
                } else {
                    //disjoint case
                    mergedIntervals.add(lastInv);
                    lastInv = curInv;
                }
            }
        }
        mergedIntervals.add(lastInv);
        for (ArrayList<Integer> lst : mergedIntervals) {
            rList.add(lst.get(1) - lst.get(0) +1);
        }

        System.out.println(mergedIntervals);
        return rList;
    }
    // This is done using simple 2D array. The runtime is O(n) as the sort is done on 26 size array which is constant
    // time  = 26log26.
    public List<Integer> partitionLabels(String S) {
        int[][] intervals = new int[26][2];
        for (int[] inv: intervals){
            inv[0] = S.length();
            inv[1] = S.length();
        }
        List<Integer> rList = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            if (intervals[ch-'a'][0] == S.length()) {
                intervals[ch-'a'][0] = i;
                intervals[ch-'a'][1] = i;
            } else {
                intervals[ch-'a'][1] = i;
            }
        }
        Collections.sort(Arrays.asList(intervals), new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int [] lastInv = intervals[0];
        ArrayList<int[]> mergeList = new ArrayList<>();
        for (int []curInv : intervals) {
            if (curInv[0] == S.length())
                break;
            if (lastInv[1] < curInv[1]) {
                if ( curInv[0] <= lastInv[1])
                    lastInv[1] = curInv[1];
                else{
                    mergeList.add(lastInv);
                    lastInv = curInv;
                }
            }
        }
        mergeList.add(lastInv);
        for (int []inv : mergeList) {
            rList.add(inv[1] - inv[0] +1);
        }
        System.out.println(Arrays.deepToString(intervals));
        return rList;
    }
    // A greedy approach to the problem we save the last index of each char and used the last
    public List<Integer> partitionLabelsV3(String S) {
        int [] last = new int[26];
        for (int i = 0; i<S.length(); i++) {
            char ch = S.charAt(i);
            last[ch-'a'] = i;
        }
        List <Integer> rList = new ArrayList<>();
        int lastId = 0;
        int startId= 0;
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            lastId = Math.max(lastId, last[ch -'a']);
            if (lastId == i) {
                rList.add(lastId - startId + 1);
                startId = i + 1;
            }

        }
        return rList;
    }
    // LeetCode :: 1152. Analyze User Website Visit Pattern
    // This problem is very badly described may be the purpose is to ask the interviewer a lot of requirement
    // gathering question. The main goal is to find out the requirement implementation is pure brute force.
    // Basically the intent of the problem is to find the most common 3 sequence web site access
    // sequence. So the only option is to brute force and generate per user all possible 3 sequence website visit
    // sequence  and put a count of the sequence frequency in a hashmap and do the same for all user. The seq that has
    // highest count is the most common user access sequence. This would require N^3 to generate  the 3 seq counts
    public class WebAccess {
        String web;
        int time;
        public WebAccess(String s, int t) {
            web = s;
            time = t;
        }
    }

    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        List <String> rList = new ArrayList<>();
        HashMap<String, ArrayList<WebAccess>> userVisitMap = new HashMap<>();

        for (int i = 0; i < username.length; i++) {
            userVisitMap.putIfAbsent(username[i], new ArrayList<>());
            userVisitMap.get(username[i]).add(new WebAccess(website[i], timestamp[i]));
        }
        HashMap<String, Integer> seqFrequency = new HashMap<>();
        String res = "";
        for (String user : userVisitMap.keySet()) {
            // this will remove duplicate seq
            HashSet<String> set = new HashSet<>();
            ArrayList<WebAccess> userSeq = userVisitMap.get(user);
            Collections.sort(userSeq, (a,b)->(a.time - b.time));
            for (int i = 0; i<userSeq.size(); i++) {
                for (int j = i+1; j < userSeq.size(); j++) {
                    for (int k = j+1; k < userSeq.size(); k++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(userSeq.get(i).web);
                        sb.append(" ");
                        sb.append(userSeq.get(j).web);
                        sb.append(" ");
                        sb.append(userSeq.get(k).web);
                        String str = sb.toString();
                        if(set.contains(str))
                            continue;
                        set.add(str);
                        seqFrequency.put(str, seqFrequency.getOrDefault(str, 0) + 1);
                        if (res == "" ||  seqFrequency.get(res) < seqFrequency.get(str)
                                || (seqFrequency.get(res) ==seqFrequency.get(str) && str.compareTo(res) < 0) ) {

                            res = str;
                        }

                    }
                }
            }
        }
        String[] webs = res.split(" ");
        rList.addAll(Arrays.asList(webs));
        return rList;
    }

    // LeetCode :: 957. Prison Cells After N Days
    // The idea is that the pattern repeats as its only 8 cell so we can use the info to fast forward the N size loop
    // for big enough N
    public int[] prisonAfterNDays(int[] cells, int N) {

        HashMap<Integer, Integer> seen = new HashMap<>();
        boolean isFastForwarded = false;

        // step 1). convert the cells to bitmap
        int stateBitmap = 0x0;
        for (int cell : cells) {
            stateBitmap <<= 1;
            stateBitmap = (stateBitmap | cell);
        }

        // step 2). run the simulation with hashmap
        while (N > 0) {
            if (!isFastForwarded) {
                if (seen.containsKey(stateBitmap)) {
                    // the length of the cycle is seen[state_key] - N
                    N %= seen.get(stateBitmap) - N;
                    isFastForwarded = true;
                } else
                    seen.put(stateBitmap, N);
            }
            // check if there is still some steps remained,
            // with or without the fast forwarding.
            if (N > 0) {
                N -= 1;
                stateBitmap = this.nextDay(stateBitmap);
            }
        }

        // step 3). convert the bitmap back to the state cells
        int ret[] = new int[cells.length];
        for (int i = cells.length - 1; i >= 0; i--) {
            ret[i] = (stateBitmap & 0x1);
            stateBitmap = stateBitmap >> 1;
        }
        return ret;
    }

    protected int nextDay(int stateBitmap) {
        // use the xnor property
        stateBitmap = ~(stateBitmap << 1) ^ (stateBitmap >> 1);
        // set the head and tail to zero
        stateBitmap = stateBitmap & 0x7e;
        return stateBitmap;
    }

    // LeetCode :: 1167. Minimum Cost to Connect Sticks
    // The idea is to use a greedy approach to pick the smallest two items from the pile add/merge them and put them
    // back into the pile and oick the next two smallest item until all the stick/items are merged
    // The idea is to use a minHeap, first we add all the sticks in the minheap & we remove two items at a time from
    // the heap and add them. We put the result back into the heap for so that this merged stick can be use with some
    // smaller stick.
    // After pulling two item form the heap if the heap is empty we dont need to put the result back as we have the
    // final stick
    public int connectSticks(int[] sticks) {
        if (sticks.length <= 1)
            return 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int s : sticks) {
            minHeap.add(s);
        }
        int cost = 0;
        while (!minHeap.isEmpty()){
            int z = minHeap.remove() + minHeap.remove();
            cost += z;
            // heap is empty so we got our final result we are left with only one stick now
            if (minHeap.isEmpty())
                break;
            minHeap.add(z);
        }
        return cost;
    }

    // LeetCode :: 819. Most Common Word

    public String mostCommonWord(String paragraph, String[] banned) {
        HashSet<String> banList = new HashSet<>(Arrays.asList(banned));
        HashMap<String, Integer> map = new HashMap<>();
        String []words = paragraph.split(" ");
        String res = "";
        int max = 0;
        for (String w : words) {
            String lw  = w.toLowerCase();
            if(!Character.isLetter(lw.charAt(lw.length() -1)))
                lw = lw.substring(0,lw.length() -1);
            if (!banList.contains(lw.toLowerCase())) {
                int count = map.getOrDefault(lw, 0) + 1;
                map.put(lw, count);
                if (max < count) {
                    max = count;
                    res = lw;
                }
            }
        }
        return res;
    }

    // LeetCode :: 1268. Search Suggestions System
    // we dont need to use a trie for this problem
    // The idea is interesting if the words list are sorted and we are looking for prefix if we find the first index of
    // the prefix then the following indexes will most likely to match
    private int comparePrefix(String str, String prefix) {
        if (prefix.length() > str.length())
            return  -1;
        if(str.startsWith(prefix))
            return 0;
        else {
            int i = 0;
            while (i < str.length() && i< prefix.length()
                    && str.charAt(i) == prefix.charAt(i))
                i++;

            return str.charAt(i) - prefix.charAt(i);

        }
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> rList = new ArrayList<>();
        Arrays.sort(products);
        for (int i = 1; i <= searchWord.length();i++) {
            String prefix = searchWord.substring(0,i);
            int low = 0;
            int high = products.length - 1;
            int idx = 0;
            while (low < high) {
                int mid = low + (high - low)/2;
                int res = comparePrefix(products[mid], prefix);
                if (res == 0) {
                    idx = mid;
                    high = mid - 1;
                } else if (res < 0){
                    low = mid + 1;
                } else {
                    high = mid -1;
                }
            }
            ArrayList<String> tList = new ArrayList<>();
            int count = 3;
            while (idx < products.length && count > 0) {
                if (products[idx].startsWith(prefix))
                    tList.add(products[idx++]);
                count--;
            }
            rList.add(tList);

        }
        return rList;
    }

    // LeetCode :: 992. Subarrays with K Different Integers
    // This problem is interesting it uses sliding window approach but its easy to get stuck only about sliding window
    // We can use the at most k distinct char sub array sliding window finding approach to solve this problem.
    // The idea is 'Exactly k distinct' = 'at most K distinct' - 'at most (k-1) distinct' this will give use the result
    // See the sliding window implementation below
    public int subarraysWithKDistinct(int[] A, int K) {
        return subarraysAtMostK(A, K) - subarraysAtMostK(A, K-1);
    }

    // calc the number of at most K distinct chars sub array
    private int subarraysAtMostK(int []nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        int unique = 0;
        int subArrayCount = 0;
        while (right < nums.length) {
            int count = map.getOrDefault(nums[right], 0);
            if (count == 0)
                unique++;
            map.put(nums[right], count + 1);
            // we are looking for at most that's why we have k+1
            while (unique == k+1) {
                int tc = map.get(nums[left]);
                if ((tc -1) == 0) {
                    unique--;
                }
                map.put(nums[left], tc -1);
                left++;
            }
            // why adding up (right - left + 1) works ?
            // The idea is to how many sub arrays we can have with at most k chars once we found the k the k value does
            // not matter what matters is the length of the subarray , given a len n for a subarray we can have
            // n*(n+1)/2 different contigious sub arrays. as 1 + 2 + 3 + .... + n = n*(n+1)/2 so basically
            // for len 4 we can get 1 + 2 + 3 + 4 = 10 sub arrays
            subArrayCount += (right - left + 1);
            right++;
        }
        return subArrayCount;
    }




}
