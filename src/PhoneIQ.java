import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;
import sun.jvm.hotspot.utilities.Interval;

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
     * Explanation: [2], [4], [2, 4], [2, 4, 5], [2, 5]
     * The idea is to not generate any subset. Note subset of sorted array is the same as unsorted array
     * in our case if we sort the array first then it will be easier to process the min max in the subsets
     * So first sort the array. Now we use the idea similar to twoSum problems where we use two pointer left
     * & right and try to find the two poistion for which the nums[left] + nums[right] <=k , if we find such a
     * point then we calc the subset in between nums[left](inclusive) & nums[right] (inclusive).
     * The reason for using the count += 1 << (hi - lo); is exlained by the example below
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

    // Longest subarray not having more than K distinct elements
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
    // each node in tree points to each char in string given a set if node, char pair find the number
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
            if (leftSum == rightSum && arr[i] != arr[i-1])
                return true;
        }
        return false;
    }

    // Encrypt String abcxbca to xbacbca
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

    // This is also O(n) with actual n iteration
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
    int getTotalTime(int[] arr) {
        // Write your code here
        int penalty = 0;
        LinkedList<Integer> arrList = new LinkedList<>();
        for (int n : arr) {
            arrList.add(n);
        }
        Collections.sort(arrList,Collections.reverseOrder());
        while (arrList.size() != 1) {
            int sum = arrList.remove(0);
            sum+= arrList.remove(0);
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
        if (count == 1) return false;
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












    }
