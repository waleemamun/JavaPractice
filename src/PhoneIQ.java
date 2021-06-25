import java.util.*;

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
    // The idea is to check if both are anagrams
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
    boolean areTheyEqual(int[] array_a, int[] array_b) {
        // Write your code here
        if (array_a.length != array_b.length)
            return false;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i<array_a.length;i++) {
            int freq = map.getOrDefault(array_a[i], 0);
            map.put(array_a[i], freq +1);
        }
        for (int i = 0; i < array_b.length; i++) {
            if (map.containsKey(array_b[i])){
                int count = map.get(array_b[i]);
                if (count == 0)
                    return false;
                map.put(array_b[i],count-1);
            }
        }
        return true;
    }


    /**
     * For a given list of integers and integer K, find the number of non-empty subsets S such that min(S) + max(S) <= K
     * Example 1:
     *
     * nums = [2, 4, 5, 7]
     * k = 8
     * Output: 5
     * Explanation: [2], [4], [2, 4], [2, 4, 5], [2, 5] (here the min + max of each subset <= K
     * for (2,4,5) the 2 + 5 = 7 <= 8
     * The problem ask us to find the of subset  where the min element of each subset + max element of each subset <= K
     *
     * The idea is to not generate any subset. Note subset of sorted array is the same as unsorted array
     * in our case if we sort the array first then it will be easier to process the min max in the subsets
     * So first sort the array. Now we use the idea similar to twoSum problems where we use two pointer left
     * & right and try to find the two poistion for which the nums[left] + nums[right] <=k , if we find such a
     * point then we calc the subset in between nums[left](inclusive) & nums[right] (inclusive).
     * The reason for using the count += 1 << (hi - lo); is because think how mane number of sets
     * for n element 2^n == 1 << n so we will have 1 << ( hi-low +1) but we have to drop one item every time to
     * handle duplicate that's why we have 1 less (1 << high - low)
     *
     * [1,2,3, 9] with k = 10
     * If you went ahead and built the subsets you would have the following case:
     * When you get the 1:
     * With min 1: [1] (just 1 subarray)
     * When you get the 2:
     * With min 1: [1],[1,2] (2 here)
     * With min 2:[2] (1 here)
     * When you get the 3:
     * With min 1: [1],[1,2],[1,3],[1,2,3] (4 here, 2 * 2, notice we keep the same arrays from before and actually
     *                                      double the total on this case)
     * With min 2: [2],[2,3] (Again, we double the amount that were starting in 2 from the previous step)
     * With min 3: [3] (1 here, only added because 3+3 < 10)
     * When you get the 9:
     * Only double the amount with min 1, because you cannot create any other subset other than the ones with min 1.
     *
     * So, since the array is sorted, we know that we will have 2^(j-i) total subsets having a minimum number of i
     * (because we will double it for each of the indexes in between)
     * */
    public static int countSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        for (int lo = 0, hi = nums.length - 1; lo <= hi; ) {
            if (nums[lo] + nums[hi] > k) {
                hi--;
            } else {
                count += 1 << (hi - lo);
                lo++;
            }
        }
        return count;
    }

    // count the number of pair sum in an unsorted array where duplicates number are possible
    // The idea is to have the frequency count of all the numbers
    // Next when we do one pass on the array we lookup if the target number is in the map if its there we
    // calc the count for the pair. If the entries in the pair is unique then we mupltiply their freq count
    // If the number in pair is same then we calc nc2 using n*(n-1)/2
    public int numberOfWays(int[] arr, int k){
        HashMap<Integer,Integer> map = new HashMap<>();

        int count = 0;
        for (int i =0 ;i<arr.length;i++){
            int freq = map.getOrDefault(arr[i], 0);
            map.put(arr[i],freq +1);
        }

        for (int i = 0; i<arr.length;i++) {
            int target = k-arr[i];
            if(map.containsKey(target)) {
                if(target == arr[i]) {
                    int n = map.get(target);
                    n = n * (n - 1)/2;
                    count += n;
                } else {
                    count += map.get(target) * map.get(arr[i]);
                    map.put(arr[i],0);
                }
                map.put(target,0);
            }
        }
        return count;
    }
    // Another simpler version would be to do this twice count the numbers and return the result divided by two
    // one case needs to be handled where pair is (arr[i], arr[i]) we do not need to calc this hence we need to
    // decrement the count
    public int numberOfWaysV2(int[] arr, int k){
        HashMap<Integer,Integer> map = new HashMap<>();

        int count = 0;
        for (int i =0 ;i<arr.length;i++){
            int freq = map.getOrDefault(arr[i], 0);
            map.put(arr[i],freq +1);
        }

        for (int i = 0; i<arr.length;i++) {
            int target = k-arr[i];
            if(map.containsKey(target)) {
                count += map.get(target);

                if(arr[i]== target)
                    count--;
            }
        }
        return count/2;
    }

    // Find the contigious subarray len where starting or ending with arr[i] as the max
    int[] countSubarrays(int[] arr) {

        int []left = new int [arr.length]; // stores the max leftMost position spanned for each i
        int []right = new int [arr.length]; // stores the max rightMost position spanned for each i
        left[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            int leftMost = i -1;
            // lets find such leftMost pos for i so that the leftMost bar >= bar i
            while (leftMost >= 0 &&
                    arr[leftMost] <= arr[i]){
                // note we are using the left arrays values to jump to the right
                // to find the leftMost this way er can iterate very fast
                leftMost = left[leftMost]  - 1;
            }

            left[i] = leftMost + 1;

        }

        right[arr.length -1] = arr.length -1;
        for (int i = arr.length -2 ; i >= 0; i--){
            int rightMost = i +1;
            // lets find such rightMost pos for i so that the rightMost bar >= bar i
            while (rightMost < arr.length &&
                    arr[rightMost] <= arr[i]){
                // note we are using the right arrays values to jump to the right
                // to find the rightMost this way er can iterate very fast
                rightMost = right[rightMost] + 1;
            }
            // update the right array with rightMost for ith position so that for some
            // other i on the left we dont need to recalculate & jump faster
            right[i] = rightMost -1;

        }
        int max = Integer.MIN_VALUE;
        // now find the max are covered using the left & right array to find the
        // width covered by each position i, height will be the heights[i]
        int res[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++){
            res[i] = right[i] - left[i] +1;
        }

        return res;
    }


    public String rotationalCipher(String input, int rotationFactor) {
        char [] chStr = input.toCharArray();
        for (int i = 0; i < chStr.length; i++) {
            if(chStr[i] >= 'a' && chStr[i] <='z') {
                int rotate = rotationFactor % 26;
                chStr[i] = (char)(((chStr[i] - 'a' + rotate) % 26) + 'a');
            } else if (chStr[i] >= 'A' && chStr[i] <='Z') {
                int rotate = rotationFactor % 26;
                chStr[i] = (char)(((chStr[i] - 'A' + rotate) % 26) + 'A');
            } else if (chStr[i] >= '0' && chStr[i] <='9'){
                int rotate = rotationFactor % 10;
                chStr[i] = (char)(((chStr[i] - '0' + rotate) % 10) + '0');
            }

        }

        return new String(chStr);
    }

    // The idea is to hash the pair from two string for example if two strings are abkcd & ackbd, when we find mismatch
    // say for pos1 we have b & c. So we hash three entries b, c & most importantly the reverse pair of (b,c) which is
    // (c,b), so when we reach position 3 we lookup the pair (c,b) its in the hash table that tells us that (b,c) pair
    // was previously seen so we can increase our count to 2. We also put b & c separately in the hash because its
    // possible that after a swap we can only match one value for example abcdek & abcdkm here swapping k with m
    // increase the value to 1
    //
    int matchingPairs(String s, String t) {
        // Write your code here
        int match = 0;
        HashMap <Integer,Integer> pairMap = new HashMap<>();
        int max = 0;
        for (int i =0; i<s.length();i++) {
            if (s.charAt(i) == t.charAt(i)){
                match++;
            } else {
                // lookup pair like (b,c)
                int lookUpPairKey = (int)((int)s.charAt(i) * Math.pow(101,1) + (int)t.charAt(i)* Math.pow(101,2));
                max = Math.max(max, pairMap.getOrDefault((int)s.charAt(i),0));
                max = Math.max(max, pairMap.getOrDefault((int)t.charAt(i),0));
                max = Math.max(max, pairMap.getOrDefault(lookUpPairKey,0));
                // encode reverse pair (c,b)
                int newPairKey = (int)((int)s.charAt(i) * Math.pow(101,2) + (int)t.charAt(i)* Math.pow(101,1));
                pairMap.put(newPairKey, 2);
                pairMap.put((int)t.charAt(i),1);
                pairMap.put((int)s.charAt(i),1);
            }
        }
        if (match == s.length())
            return match -2;
        match += max;
        return match;
    }

    public char getOpposit(char ch) {
        if (ch ==')')
            return '(';
        else if (ch == '}')
            return '{';
        else if (ch == ']')
            return '[';
        else
            return 'a';
    }

    boolean isBalanced(String s) {
        // Write your code here
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[' ){
                stack.push(ch);
            } else {
                if (stack.empty()) {
                    return false;
                }
                char popChar = stack.pop();
                if(popChar != getOpposit(ch))
                    return false;
            }
        }
        if (!stack.empty())
            return false;

        return true;
    }

    int[] findMaxProduct(int[] arr) {
        // Write your code here
        int []result = new int[arr.length];
        if (arr.length < 3) {
            Arrays.fill(result, -1);
            return result;
        }
        result[0] = result[1] = -1;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // pre-processing
        minHeap.add(arr[0]);
        minHeap.add(arr[1]);
        minHeap.add(arr[2]);
        result[2] = arr[0] * arr[1] * arr[2];
        for (int i = 3; i< arr.length; i++) {

            if (minHeap.peek() < arr[i]) {
                minHeap.remove();
                minHeap.add(arr[i]);
            }
            Iterator<Integer> itr = minHeap.iterator();
            int prod = 1;
            while (itr.hasNext() ) {
                prod*= itr.next();
            }
            result[i] = prod;

        }

        return result;

    }
    int[] findMaxProductV2(int[] arr) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int []res = new int[arr.length - 2];
        int k = 0;
        int prod = 1;
        for (int i = 0;  i<arr.length; i++) {
            minHeap.add(arr[i]);
            prod *= arr[i];
            if (i >= 3){
                prod /= minHeap.remove();
            }
            if (i >=2)
                res[k++] = prod;
        }
        return res;

    }

    // Longest subarray not having more than K distinct elements
    // this implementation is not right, check the leetcode version below
    public int kDistinctMinSubArr(String s, int k) {
        HashMap <Character,Integer> map= new HashMap<Character,Integer>();
        int maxLen = 0;
        int left = 0;
        int right = 0;
        int disticCount = 0;
        while (right < s.length()) {
            int idx = map.getOrDefault(s.charAt(right), 0);
            if( idx == 0)
                disticCount++;
            map.put(s.charAt(right),idx+1);
            if (disticCount == k) {
                while (left < right) {
                    int count = map.get(s.charAt(left));
                    if (count > 1)
                        map.put(s.charAt(left), count -1);
                    else  {
                        disticCount--;
                        break;
                    }
                    left++;
                }
                System.out.println(left + " - " + right + " max "+ (right-left +1));
                maxLen = Math.max(maxLen, right - left +1);
                left++;
            }
            right++;
        }
        return maxLen;

    }
    // rewriting it same as above
    public int kDistinctMinSubArrV2(String s, int k) {
        HashMap<Character,Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        int max = Integer.MIN_VALUE;
        int distictCount = 0;
        while(right < s.length()) {
            char ch = s.charAt(right);
            int count = map.getOrDefault(ch, 0);
            if (count == 0)
                distictCount++;
            map.put(ch,count+1);
            while (distictCount == k) {
                char tempCh = s.charAt(left);
                int cnt = map.get(tempCh);
                if (cnt == 1) {
                    distictCount--;
                    max = Math.max(max, right -left +1);
                    System.out.println(left);
                }
                map.put(tempCh, cnt-1);
                left++;

            }
            right++;
        }
        return max;
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


    // count the number of node in a tree that are visible from the left side so
    // if we are looking at the tree from left count the  number of nodes. This is basically asking to check the
    // 1st node left to right in a level order more easily its asking for the number of level in a tree or
    // the height of the tree so the answer is simple just get the height of the tree
    int visibleNodes(TreeNode root) {
        // Write your code here
        if (root == null)
            return 0;
        return 1 + Math.max(visibleNodes(root.left), visibleNodes(root.right));
    }

    // listing the left nodes visible from the left side requires level order traversal
    // and storing in a list the first node on each level
    ArrayList<Integer> listLeftVisibleNodeId(TreeNode root) {
        ArrayList<Integer> nodeList = new ArrayList<>();
        if (root == null)
            return nodeList;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++){
                TreeNode node = queue.poll();
                if (i == 0)
                    nodeList.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
        return nodeList;
    }

    class Query {
        int v;
        char c;
        Query(int v, char c) {
            this.v = v;
            this.c = c;
        }
    }


    private HashMap<Integer, HashMap<Character,Integer>> map = new HashMap<>();
    // we are doing a preorder traversal tree to visit the nodes rooted at the tree and find our the chars &
    // their count rooted at that node
    private String buildMap (Node node, String traverseString) {
        if (node == null)
            return "";
        StringBuilder sb = new StringBuilder();

        for (Node n : node.children) {
            sb.append(buildMap(n,traverseString));
        }
        sb.append(traverseString.charAt(node.val -1));
        HashMap<Character, Integer> tempMap = new HashMap<>();
        for (int i = 0; i < sb.length(); i++) {
            int count = tempMap.getOrDefault(sb.charAt(i),0);
            tempMap.put(sb.charAt(i), count +1);
        }
        map.put(node.val,tempMap);
        return sb.toString();
    }
    // Given a N ary tree where each node is repsented by 1 to N & a string which is 1-based indexed
    // each node in tree points to each char in string, given a set of node, char pair find the number
    // of char rooted at that node
    // WE need to use preprocessing to solve this
    int[] countOfNodes(Node root, ArrayList<Query> queries, String s) {
        // Write your code here
        int []res = new int[queries.size()];

        //build the map
        buildMap(root, s);
        int j = 0;
        for (Query q : queries) {
            HashMap<Character,Integer> freqMap = map.getOrDefault(q.v, null);
            if (freqMap != null)
                res [j++] = freqMap.getOrDefault(q.c, -1);
        }
        return res;
    }

    // Given an array of integers (which may include repeated integers), determine if there's a way to split the array
    // into two subarrays A and B such that the sum of the integers in both arrays is the same, and all of the integers
    // in A are strictly smaller than all of the integers in B.
    // Note: Strictly smaller denotes that every integer in A must be less than, and not equal to, every integer in B.
    boolean balancedSplitExists(int[] arr) {
        // Write your code here
        Arrays.sort(arr);
        int leftSum = 0;
        int rightSum =0;
        for (int i = 0; i < arr.length; i++) {
            leftSum+= arr[i];
        }
        for (int i = arr.length -1 ; i>0; i--) {
            leftSum -= arr[i];
            rightSum += arr[i];
            // the reason arr[i] != arr[i-1] is that array A has to be strictly smaller than array B that's if two
            // adjacent items are same value they must belong to only one array
            if (leftSum == rightSum && arr[i] != arr[i-1])
                return true;
        }
        return false;
    }

    // Encrypt String abcxcba to xbacbca
    public String findEncryptedWord(String s) {
        // Write your code here
        int start = 0;
        int end = s.length()-1;
        StringBuilder sb = new StringBuilder();
        encryptWordRec(sb, s, start, end);
        return sb.toString();
    }

    private void encryptWordRec(StringBuilder sb, String str, int start, int end) {
        if (start > end)
            return;
        int mid = start + (end-start)/2;
        sb.append(str.charAt(mid));
        encryptWordRec(sb, str, start, mid-1);
        encryptWordRec(sb, str, mid+1, end);
    }

    // Magical Candy Bag
    int maxCandies(int[] arr, int k) {
        // Write your code here
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (Integer n : arr) {
            maxHeap.add(n);
        }
        int sum = 0;
        while(!maxHeap.isEmpty() && k > 0) {
            int candyCount = maxHeap.remove();
            sum += candyCount;
            candyCount /= 2;
            maxHeap.add(candyCount);
            k--;
        }
        return sum;
    }

    // Median Stream, get the rolling median of an Array get median for all entries in an Unsorted Array
    // So we need to get the median for 1 element then 2 elements then 3 elements then 4 elements and so on ...
    // This does not require K median for a sliding window. We manage a min & max heap and as we scan the array
    // left to right we update the heap and calculate median
    int[] findMedian(int[] arr) {
        // Write your code here
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int [] result = new int[arr.length];
        int j=0;
        for (Integer n : arr) {
            if(minHeap.size() <= maxHeap.size()) {
                maxHeap.add(n);
                minHeap.add(maxHeap.remove());
            } else {
                minHeap.add(n);
                maxHeap.add(minHeap.remove());
            }
            if ((minHeap.size() + maxHeap.size()) % 2 == 0) {
                result[j++] = (minHeap.peek() + maxHeap.peek())/2;
            } else {
                result[j++] =  minHeap.peek();
            }
        }
        return result;
    }

    public LinkList revertList (LinkList startNode, LinkList endNode) {

        LinkList curr = startNode;
        LinkList reverseNode = curr.next;
        // we already saved the startNode & its next node in curr & reverseNode,
        // so its safe to make startNode next point to the endNode next
        startNode.next = endNode.next;
        // scan the list and reverse it
        while (curr != endNode && reverseNode!= null) {
            LinkList nextNode = reverseNode.next;
            reverseNode.next = curr;
            curr = reverseNode;
            reverseNode = nextNode;
        }
        // the last node is the first node now
        return endNode;
    }

    public LinkList reverseConsecutiveEvenNodes(LinkList head) {
        LinkList curr = head;
        LinkList prev = null;
        LinkList start = null;
        LinkList end = null;
        LinkList lastPrev = null;
        while (curr != null) {
            if ((curr.val % 2) != 0) {
                prev = curr;
                // we found an even consecutive list in the last iteration
                if (start != null && start != end){
                    LinkList tempHead = revertList(start,end);
                    if (lastPrev != null)
                        lastPrev.next = tempHead;
                    else
                        head = tempHead;
                    curr = start;
                    start = null;
                }
            } else {
                // even node
                if (start == null) {
                    lastPrev = prev;
                    start = curr;
                }
                end = curr;
            }
            curr = curr.next;
        }
        if (start!=null && end.next == null) {
            LinkList tempHead = revertList(start, end);
            if (prev != null)
                prev.next = tempHead;
            else
                head = tempHead;
        }
        return head;
    }
    // this version is simpler as we use two loops but note the its still O(n) because after completing
    // the inner loop  update curr pointer base on the 2nd loop iteration this will do a 2n iteration
    // which is same as O(n)
    public LinkList reverseConsecutiveEvenNodesSimple(LinkList head){
         if (head == null || head.next == null)
             return head;
        LinkList curr = head;
        LinkList prev = null;
        while (curr != null) {
            // odd entry found save it as prevNode
            if ((curr.val % 2) != 0) {
                prev = curr;
                curr = curr.next;
            } else {
                // even entry
                LinkList start = curr;
                while (curr.next != null && (curr.next.val %2) == 0)
                    curr = curr.next;
                LinkList nextNode = curr.next;
                LinkList tempHead = revertList(start,curr);
                if (prev !=null)
                    prev.next = tempHead;
                else
                    head = tempHead;
                curr = nextNode;
            }
        }
        return head;

    }

    // This is also O(n) with actual n iteration 1 - pass
    public LinkList reverseConsecutiveEvenNodesSimpleV2(LinkList head){
        if (head == null || head.next == null)
            return head;
        LinkList curr = head;
        LinkList prev = null;
        while (curr != null) {
            // odd entry found save it as prevNode
            if ((curr.val % 2) != 0) {
                prev = curr;

            } else {
                // even entry
                LinkList start = curr;
                LinkList reverseNode = curr.next;

                while (reverseNode!= null && (reverseNode.val %2) == 0){
                    LinkList nextNode = reverseNode.next;
                    reverseNode.next = curr;
                    curr = reverseNode;
                    reverseNode = nextNode;
                }
                start.next = reverseNode;
                if (prev != null)
                    prev.next = curr;
                else
                    head = curr;
                curr = start;
            }
            curr = curr.next;
        }
        return head;

    }

    // Slow Sums : Greedy approach chose the two largest element add their sum as the penalty
    // The idea is to choose the biggest two element and remove them from the list and add their sum to the list
    // and pick the next two
    int getTotalTime(int[] arr) {
        // Write your code here
        int penalty = 0;
        // this is more like a stack
        LinkedList<Integer> arrList = new LinkedList<>();
        for (int n : arr) {
            arrList.add(n);
        }
        Collections.sort(arrList,Collections.reverseOrder());
        while (arrList.size() != 1) {
            //remove two items from the stack
            int sum = arrList.remove(0);
            sum+= arrList.remove(0);
            // insert the sum at the front of the stack
            arrList.addFirst(sum);
            penalty += sum;
        }
        return penalty;
    }

    // Similar strings ("face", "eacf") returns true if only 2 positions in the strings are swapped.
    // Here 'f' and 'e' are swapped in the example
    public boolean isSimilar (String s1 , String s2) {
        if(s1.length() != s2.length())
            return false;
        int count = 0;
        char s_1 ='\0';
        char s_2 = '\0';
        for (int i = 0; i<s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
                s_1 = s2.charAt(i);
                s_2 = s1.charAt(i);

            }
        }
        if (count == 0) return true;
        if (count != 2) return false;

        for (int i = 0; i<s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i) && s_1 == s1.charAt(i) &s_2 == s2.charAt(i))
                return true;
            else
                break;
        }
        return false;

    }
    // works for positive arr only only, needs to be verified
    public boolean checkSubarraySum(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int tempSum = 0;
        while (right < nums.length) {
            tempSum+= nums[right];
            while (tempSum >= k) {
                if (tempSum == k){
                    System.out.println(left +" " + right);
                    return true;
                }
                tempSum -= nums[left];
                left++;
            }
            right++;
        }
        return false;
    }

    // LeetCOde :: 56. Merge Intervals
    // The idea is to sort the intervals based on the start of the intervals and then merge the overlapping intervals
    private class IntervalComparator implements Comparator<int []> {
        @Override
        // sort by start interval first then end interval
        public int compare(int[] o1, int[] o2) {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            else
                return o1[0] - o2[0];
        }
    }
    public int[][] merge(int[][] intervals) {
        Collections.sort(Arrays.asList(intervals), new IntervalComparator());
        ArrayList<int[]> mergelist = new ArrayList<>();
        int []lastInv = intervals[0];
        for (int i = 1;i<intervals.length;i++) {
            int []curIntv = intervals[i];
            if (lastInv[1] < curIntv[1]) {
                if(lastInv[1] < curIntv[0]) {
                    mergelist.add(lastInv);
                    lastInv = curIntv;
                } else {
                    lastInv[1] = curIntv[1];
                }
            }

        }
        // remember to add the final to the list
        mergelist.add(lastInv);
        return mergelist.toArray(new int[mergelist.size()][]);

    }

    // We need ot find the intersect & union of sorted intervals (if the intervals are not sorted we sort
    // them according to start time)
    // The merge part is similar to the method above 'merge'
    // For the current intersect interval we use the last merged intervals & current interval to find the
    // current intersection interval. Now we need to check if we can use the last intersect interval & current
    // intersect interval to merge them to a bigger intersect interval
    public int[][] mergeAndInterSect(int[][] intervals) {
        Collections.sort(Arrays.asList(intervals), new IntervalComparator());
        ArrayList<int[]> mergelist = new ArrayList<>();
        ArrayList<int[]> interSectlist = new ArrayList<>();
        int []lastInv = intervals[0];
        int []lastInterSect = {0,0};
        for (int i = 1;i<intervals.length;i++) {
            int []curIntv = intervals[i];
            // intersection case
            if(lastInv[1] >= curIntv[0]) { // intersecting intervals
                // as these are overlapping get the intersection
                // use the last merged interval to get the intersection
                int []intersect = {Math.max(curIntv[0], lastInv[0]), Math.min(curIntv[1],lastInv[1])};
                if (lastInterSect[1] < intersect[0]) { // intersection are disjoint add the last one to intersect list
                    if(intersect[0] != intersect[1]){
                        interSectlist.add(lastInterSect);
                        lastInterSect = intersect;
                    }
                } else {                               // intersections are overlapping, lets merge the last & current
                    lastInterSect[0] = Math.min(lastInterSect[0],intersect[0]);
                    lastInterSect[1] = Math.max(lastInterSect[1],intersect[1]);
                }
            }
            // merge/union case
            if (lastInv[1] < curIntv[1]) {
                if(lastInv[1] < curIntv[0]) { // disjoint
                    mergelist.add(lastInv);
                    lastInv = curIntv;
                } else {                      // overlapping
                    // merge the overlapping list (Union)
                    lastInv[1] = curIntv[1];
                }
            }

        }
        mergelist.add(lastInv);
        interSectlist.remove(0);
        interSectlist.add(lastInterSect);
        int [][]iList = interSectlist.toArray(new int [interSectlist.size()][]);
        System.out.println(Arrays.deepToString(iList));
        return mergelist.toArray(new int[mergelist.size()][]);

    }

    // LeetCode :: 57 Insert Intervals
    public int[][] insert(int[][] intervals, int[] newInterval) {
        LinkedList<int []> resList = new LinkedList<>();
        int i = 0;
        // add everything before this interval
        while(i < intervals.length && intervals[i][1] < newInterval[0]){
            resList.add(intervals[i]);
            i++;
        }
        // add the intervals that can be merged with the new inserted intervals
        // Here we compare the left end of the intervals with the right end of the new intervals.
        // The right end of the interval could come before the right end of the current interval.
        // In that case the new interval is covered by the right end of current interval.
        // Or if the new interval right end of the new interval covers the right end of the
        // current interval than we have more intervals to cover.
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            // we update the left to cover the lower left
            newInterval[0] = Math.min(newInterval[0],intervals[i][0]);
            // we update the max to cover the right, but note if the right is update with the
            // current interval right the next iteration will break as the whole array is sorted
            newInterval[1] = Math.max(newInterval[1],intervals[i][1]);
            i++;
        }
        // add merged interval to the list
        resList.add(newInterval);
        // add rest of the intervals right of the merged intervals
        while (i < intervals.length)
            resList.add(intervals[i++]);

        return resList.toArray(new int[resList.size()][]);

    }

    // LeetCode :: 252 Meeting Rooms II
    /**
     * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
     * find the minimum number of conference rooms required.
     * For example, Given [[0, 30],[5, 10],[15, 20]], return 2.
     * */
    // The idea is to use the same approach as the most populdation count problem in a Year or the Max concurrent event
    // of AdnanAziz.
    // This approach uses and O(times) solution we find the minStart & max endTime. We maintain a count array of
    // event per time in the times array. Then we go through each interval whenever we see a start interval we do +1 for
    // start interval and -1 for end interval. Finally while scanning the times array from start to end time we count
    // the concurrent even per time and have a running sum. The running su gives the count of concurrent event at that
    // time so we keep track of max running sum.
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0)
            return 0;
        int roomCount = 0;
        int maxCount = 0;
        int startTime = Integer.MAX_VALUE;
        int endTime = 0;
        for (int [] inv : intervals){
            startTime = Math.min(startTime, inv[0]);
            endTime = Math.max(endTime, inv[1]);
        }
        // we are using the start time to reduce the space & execution time for example
        // if the start time is not zero we can have less storage & execution time
        int []times = new int[endTime -startTime + 1];
        for (int [] inv : intervals) {
            times[inv[0]-startTime]++;
            times[inv[1]-startTime]--;
        }
        // the running sum at time i gives the total concurrent event at time i, as the running sum can become less in
        // subsequent ith time we keep track of max rof running sum to track the max concurrent event
        for(int i = 0; i < times.length; i++) {
            roomCount+= times[i];
            maxCount = Math.max(maxCount, roomCount);
        }

        return maxCount;
    }
    // meeting room -II version 2
    // this version is faster O(nlgn)
    private class IntervalComparatorSimple implements Comparator<int []> {
        @Override
        // sort by start interval
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }
    }
    public int minMeetingRoomsV2(int[][] intervals) {
        if (intervals.length == 0)
            return 0;
        int maxCount = 0;
        // sort the array based on the start interval
        Arrays.sort(intervals, new IntervalComparatorSimple());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // add the first meetings end to the priority queue
        minHeap.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++){
            // pick the next start time and see if the star time is
            // after the peek queue this meeting can ber removed
            // from the queue more like this room is now available.
            if (minHeap.peek() <= intervals[i][0])
                minHeap.remove();
            // add the end interval so when we get a start interval
            // after this we can remove this and occupy this room
            minHeap.add(intervals[i][1]);
        }

        return minHeap.size();
    }


    // Google Phone Interview Question Faulty keyboard
    // There is a broken keyboard in which space gets typed when you type the letter 'e'. Given an input string which is
    // the output from the keyboard. A dictionary of possible words is also provided as an input parameter of the
    // method. Return a list of possible actual input typed by the user.
    // Example Input: String: "can s r n " Dictionary: ["can", "canes", "serene", "rene", "sam"]
    // Expected Output: ["can serene", "canes rene"]
    // The idea is to use backtracking to solve this problem with smart trick we check the valid solution and
    // create the result when we have processed the whole string. This way the complexity of checking if the current
    // words is valid or not moves to the end from choosing part of backtracking
    // For backtracking we have a set of choice (use space or use 'e') we pick one of them and backtrack
    // For looking up valid results we look up each word in the dictionary if all of them exist we have a result string
    // with all valid words from the dictionary. We are using a hashtable for dictionary for faster lookup
    private void faultyKeyboardRec (char []str, int index, HashSet<String> dict,
                                    List<String> resList) {
        if (index >= str.length) {
            String []tokens = new String(str).split("\\s+");
            System.out.println(Arrays.toString(tokens));
            int i;
            String output="";
            for (i =0; i<tokens.length; i++) {
                if(!dict.contains(tokens[i]))
                    break;
                output+= tokens[i] + " ";
            }
            if (i == tokens.length)
                resList.add(output.trim());
            return;
        }
        faultyKeyboardRec(str,index +1, dict,resList);
        if (str[index] == ' '){
            str[index] = 'e';
            faultyKeyboardRec(str, index +1, dict, resList);
            str[index] = ' ';
        }

    }
    public List<String> faultyKeyboard(String str, String []words) {
        List<String> resList = new ArrayList<>();
        HashSet<String> dict = new HashSet<>(Arrays.asList(words));

        faultyKeyboardRec(str.toCharArray(),0, dict, resList);
        return resList;
    }

    public int getSpaceIdx(char []str, int idx){

        while(idx <str.length && str[idx]!=' ')
            idx++;
        return idx;
    }

    class TrieNode {
        char ch;
        TrieNode [] child;
        boolean isWord;
        public TrieNode (char c) {
            ch = c;
            isWord = false;
            child = new TrieNode[26];
        }
    }
    class Trie {

        TrieNode root;
        boolean oneSkipAllowed;
        public Trie(){
            root = new TrieNode('@');
            oneSkipAllowed = false;
        }
        public void addWord(String s) {
            TrieNode node = root;
            int i = 0;
            while (i < s.length()) {
                char ch = s.charAt(i);
                if (node.child[ch-'a'] == null)
                    node.child[ch-'a'] = new TrieNode(ch);
                node = node.child[ch-'a'];
                if (i==s.length() -1)
                    node.isWord = true;
                i++;
            }
        }
        public boolean search(String s){
            TrieNode node = root;
            int i = 0;
            while (i < s.length()) {
                char ch = s.charAt(i);
                if(node.child[ch-'a'] == null)
                    return false;
                node = node.child[ch -'a'];
                if (node.isWord && i == s.length()-1)
                    return true;
                i++;
            }
            return false;

        }
        public boolean oneSkipSearch(String s) {
            oneSkipAllowed = true;
            TrieNode node =root;
            boolean isFound = skipSearch2(s, 0, node);
            oneSkipAllowed = false;
            return isFound;
        }
        private boolean skipSearch(String s, int idx, TrieNode node){
            if (idx >= s.length())
                return false;
            char ch = s.charAt(idx);
            TrieNode currNode = node.child[ch -'a'];
            if (currNode != null) {
                return  (idx == s.length() -1 && currNode.isWord) || skipSearch(s, idx+ 1, currNode);
            } else {
                if(oneSkipAllowed) {
                    oneSkipAllowed = false;
                    for (int i = 0; i<26; i++)
                        if(node.child[i] !=null && (idx == s.length()-1 || skipSearch(s, idx +1, node.child[i])))
                            return true;
                }
                return false;
            }

        }

        // The idea is to use a modified Trie search. We keep a tracking boolean variable oneSkippedAllowed for tracking
        // the first skip. This problem creates an unique edge case if the first char is needed to e skipped.
        private boolean skipSearch2(String s, int idx, TrieNode node){
            if (idx >= s.length())
                return false;
            char ch = s.charAt(idx);
            // handle the special case first char is needed to be skipped, we have to check all the children of the root
            if (node == root) {
                for (int i =0; i<26; i++) {
                    // need to set the boolean to true for all the children of root as the will take different subtrees
                    if (node.child[i]!= null){
                        if (i == (ch-'a'))
                            oneSkipAllowed = true;
                        if (skipSearch2(s,idx+1,node.child[i]))
                            return true;
                    }
                }
                return false;

            } else {
                // if node is found just process as normal
                TrieNode currNode = node.child[ch -'a'];
                if (currNode != null) {
                    return  (idx == s.length() -1 && currNode.isWord) || skipSearch2(s, idx+ 1, currNode);
                } else { // if node is not found and we still can skip one item
                    if(oneSkipAllowed) {
                        // set the boolean so that we dont skip it any more
                        oneSkipAllowed = false;
                        for (int i = 0; i<26; i++)
                            if(node.child[i] !=null && (idx == s.length()-1 || skipSearch2(s, idx +1, node.child[i])))
                                return true;
                    }
                    return false;
                }
            }

        }
    }


    /**
     *  Google Phone Interview
     *  Design a vocabulary class that allows for a maximum of one typo.
     *  It has one method: given a word, it verifies if the word can be found in the
     *  vocabulary with at most one character substitution.
     *
     * */
    public boolean oneType (String str, String []words) {
        Trie trie= new Trie();
        for (String s: words)
            trie.addWord(s);
        return trie.oneSkipSearch(str);
    }

    // Amazon | OA 2019 | Substrings of size K with K distinct chars
    public List<String> kSubstring(String s, int k) {
        char []str  = s.toCharArray();
        HashSet<Character> set = new HashSet<>();
        HashSet<String> resList = new HashSet<>();
        for (int i = 0; i <str.length; i++) {
            if (i >= k) {
                set.remove(str[i-k]);
                //System.out.println(str[i-k] + " " + set.size());
            }
            set.add(str[i]);
            if (set.size() == k) {
                resList.add(s.substring(i- k +1, i + 1));
            }
        }
        List <String> rList = new ArrayList<>(resList);
        return rList;
    }

    // Amazon OA
    // Given an int array nums and an int target, find how many unique pairs in the
    // array such that their sum is equal to target. Return the number of pairs.

    public int uniquePairs(int[] nums, int target){
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> invalid = new HashSet<>();
        int count = 0;
        for (int n: nums) {
            if(set.contains(target -n)) {
                set.remove(target - n);
                invalid.add(target -n);
                invalid.add(n);
                count++;
            } else {
                if (!invalid.contains(n))
                    set.add(n);
            }
        }
        return count;
    }











    }
