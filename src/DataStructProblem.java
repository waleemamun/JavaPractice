import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;

import java.util.*;

public class DataStructProblem {
    // LeetCode :: 381. Insert Delete GetRandom O(1) - Duplicates allowed
    /**
     * We will keep a list to store all our elements. In order to make finding the index of elements we want
     * to remove O(1)O(1), we will use a HashMap or dictionary to map values to all indices that have those values.
     * To make this work each value will be mapped to a set of indices.
     * The idea is to have a HasHMap + HashSet + ArrayList
     * To store the val , we use the val as the key of the hashmap and in the set we store the index of that value in
     * the arraylist
     * HashMap store the unique val and the set holds all the indices of that unique val in the ArrayList
     * To hold the O(1) getRandom when we delete a val we need to update the index properly in both map/set & Arraylist
     * to delete from arraylist and not to upset any index we can swap the value with the last value and delete it from
     * there
     * */
    class RandomizedCollection {
        HashMap<Integer, HashSet<Integer>> map;
        ArrayList<Integer> alist;
        Random rand = new java.util.Random();
        /** Initialize your data structure here. */
        public RandomizedCollection() {
            map = new HashMap<>();
            alist = new ArrayList();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            HashSet<Integer> set = map.getOrDefault(val, new HashSet<Integer>());
            boolean first = false;
            if (set.size() == 0) {
                first = true;
            }
            set.add(alist.size());
            map.put(val, set);
            alist.add(val);
            return first;

        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if(!map.containsKey(val))
                return false;
            // the value exist delete the first index for this value
            HashSet<Integer> set = map.get(val);
            Iterator<Integer> itr = set.iterator();
            int idx = itr.next();
            set.remove(idx);
            // set is empty remove the entry from map
            if(set.size() == 0)
                map.remove(val);
            // the removed index for the value is not the last index so we swap it with the last entry in the
            // arraylist, update the index properly for the swapped val
            if (idx < alist.size()-1) {
                Collections.swap(alist,idx,alist.size()-1);
                int swapVal = alist.get(idx);
                map.get(swapVal).add(idx);
                map.get(swapVal).remove(alist.size()-1);
            }
            // finally delete the last item from the arraylist
            alist.remove(alist.size()-1);
            return true;

        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return alist.get(rand.nextInt(alist.size()));
        }
    }

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

    // LeetCode :: 380. Insert Delete GetRandom O(1)
    // The idea is to use and HashMap for O(1) insert/remove and an ArrayList for O(1) random
    // We keep the same copies of the items in hashmap & arraylist, hashmap key is the item and
    // value is the index of the item in the arrayList
    // In this case inser should be easy we just need to insert in hashmap and arraylist and
    // store the index of the value in hashmap
    // Delete/remove needs a special trick we remove an item from hashmap we get the index of the
    // item in arraylist swap it with the last item in the arraylist & update the last items index
    // in hash map with the new swapped index. Finally delete the last item from arrayList
    // This help us run a O(1) getrandom cause we can easily call math.random on the size of the arraylist
    class RandomizedSet {

        /** Initialize your data structure here. */
        HashMap<Integer,Integer> randSet;
        ArrayList <Integer> aList;
        public RandomizedSet() {
            randSet = new HashMap<Integer,Integer>();
            aList = new ArrayList<Integer> ();

        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(!randSet.containsKey(val)) {
                randSet.put(val,aList.size());
                aList.add(val);
                return true;
            } else
                return false;

        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(randSet.containsKey(val)) {
                // get the index of the val in array list
                int idx = randSet.get(val);
                // swap this val with the last index of the ArrayList
                Collections.swap(aList, idx, aList.size() -1);
                // get the value at this index after swap
                int swapVal = aList.get(idx);
                // update the  hashmap with the new index for swapVal
                randSet.put(swapVal,idx);
                // remove value from hasmap & ArrayList
                randSet.remove(val);
                aList.remove(aList.size()-1);
                return true;
            } else
                return false;

        }

        /** Get a random element from the set. */
        public int getRandom() {
            int size = randSet.size();
            int rand = (int)(Math.random()*size);
            return aList.get(rand);
        }
    }

        /**
         * Your RandomizedSet object will be instantiated and called as such:
         * RandomizedSet obj = new RandomizedSet();
         * boolean param_1 = obj.insert(val);
         * boolean param_2 = obj.remove(val);
         * int param_3 = obj.getRandom();
         */

    // 729. My Calendar I
    // The idea is to use a TreeSet to have a Balanced tree, When adding an interval the TreeSet comparator will check
    // if this value falls into any of the interval if not then add it ot the treeSet
    // if yes then dont add anything and return false
    class MyCalendar {
        TreeSet<int []> calendarSet;
        public MyCalendar() {
            calendarSet = new TreeSet<>(new Comparator<int[]>() {
                @Override
                // This checks if the new interval  o1 is completely or partially with an interval or not
                public int compare(int[] o1, int[] o2) {

                    if(o1[1] <= o2[0])            // o1[1] i.e. newInterval end smaller than o2 start
                        return -1;
                    else if (o1[0] >= o2[1])      // o1[0] i.e. newInterval start bigger than o2 start
                        return 1;
                    // newInterval o1 completely within o2
                    // o1[0] i.e. newInterval start within o2
                    // o1[1] i.e. newInterval end within o2
                    else return 0;
                }
            });


        }

        public boolean book(int start, int end) {
            int [] interval= {start,end};
            return calendarSet.add(interval);

        }
    }
    // another implementation of the Calendar-I problem above
    // This uses the a TreeMap with start interval as the key and value is the end interval
    class MyCalendarI {
        TreeMap<Integer, Integer> calendar;

        MyCalendarI() {
            calendar = new TreeMap();
        }

        public boolean book(int start, int end) {
            // get the start interval that is smaller or equal to this newInteraval start
            Integer prev = calendar.floorKey(start);
            // get the start interval that is just bigger or equal to this newInteraval start
            Integer next = calendar.ceilingKey(start);
            // now if the new interval start is greater than its prev intervals's end
            // and the newInterval's end is smaller than the start of its next bigger
            // start interval then add it to list otherwise return false;
            if ((prev == null || calendar.get(prev) <= start) &&
                    (next == null || end <= next)) {
                calendar.put(start, end);
                return true;
            }
            return false;
        }
    }

    // LeetCode ::208. Implement Trie (Prefix Tree)
    // Implement a trie data structure with capability to check if a word exist or startwith
    // we assume the root node with value "."
    class TrieNode {
        Character val;                         // node val
        HashMap<Character, TrieNode> children; // Children nodes
        boolean hasWord; // indicates the wors is present in trie
        TrieNode [] mapChild;
        public TrieNode (Character ch){
            val = ch;
            children = new HashMap<>();
            hasWord = false;
            mapChild = new TrieNode[26];
        }

    }
    class Trie {
        TrieNode root;
        /** Initialize your data structure here. */
        public Trie() {
            root = new TrieNode('@');

        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            int i = 0;
            TrieNode node = root;
            while (i < word.length()) {
                node.children.putIfAbsent(word.charAt(i), new TrieNode(word.charAt(i)));
                node = node.children.get(word.charAt(i));
                if (i == word.length() -1)
                    node.hasWord = true;
                i++;
            }
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            int i = 0;
            TrieNode node = root;
            while (i < word.length()) {
                node = node.children.getOrDefault(word.charAt(i), null);
                // char noy found in trie word does not exist
                if (node == null)
                    return false;
                // we found the word
                if (node.hasWord && i == word.length() -1)
                    return true;
                i++;
            }
            return false;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            int i = 0;
            TrieNode node = root;
            while (i < prefix.length()) {
                node = node.children.getOrDefault(prefix.charAt(i), null);
                // char noy found in trie word does not exist
                if (node == null)
                    return false;
                // we found the word
                if (node.hasWord && i == prefix.length() -1)
                    return true;
                i++;
            }
            // the word is not found but the prefix exist
            return i==prefix.length();
        }
    }

    //LeetCode :: 284. Peeking Iterator
    // Simply store the  current value in the top. All calls to peek will return top
    // We update top during initialization once and then top is update in next call we
    // return the top value and update top with the nextValue
    class PeekingIterator implements Iterator<Integer> {
        Iterator<Integer> peekingIterator;
        Integer top = null;
        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            peekingIterator = iterator;
            if (peekingIterator.hasNext())
                top = peekingIterator.next();
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return top;

        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            Integer res = top;
            if (peekingIterator.hasNext())
                top = peekingIterator.next();
            else
                top = null;
            return res;

        }

        @Override
        public boolean hasNext() {
            return peekingIterator.hasNext();
        }
    }

    // LeetCode :: 304. Range Sum Query 2D - Immutable
    // The idea is to think about the how do get a specific rectangle given all the rectangle from top,left
    class NumMatrix {
        int cache[][] = null;

        public NumMatrix(int[][] matrix, boolean skip) {
            if (matrix.length == 0) {
                cache = null;
                return;
            }
            cache = new int [matrix.length][matrix[0].length];
            cache[0][0] = matrix[0][0];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j< matrix[0].length; j++){
                    cache[i][j] = (j == 0? 0: cache[i][j-1]) + matrix[i][j];
                }
            }
            System.out.println(Arrays.deepToString(matrix));

            for (int i = 1; i< matrix.length; i++) {
                for (int j = matrix[0].length -1; j >= 0; j--) {
                    cache[i][j] = cache[i-1][j] + (j == 0 ? 0:cache[i][j-1]) + matrix[i][j];
                }
            }

            System.out.println(Arrays.deepToString(cache));

        }

        // create the cache table building on the same idea of getting a bounded region using region starting from zero
        // Sum(ABCD) =  Sum(OB)+Sum(OC) + matrix[i][j] - Sum(OA)
        // Think how you can get you region by subtracting rectangles from the  bottom right rectangle
        public NumMatrix (int [][] matrix) {
            if (matrix.length == 0) {
                cache = null;
                return;
            }
            cache = new int [matrix.length + 1][matrix[0].length + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    cache[i+1][j+1] = cache[i+1][j] + cache[i][j+1] + matrix[i][j] - cache[i][j];
                }
            }

        }
        // This uses the more complicated version look at the constructor public NumMatrix(int[][] matrix, boolean skip)
        // lets not do this as it just makes the cacl complicated I think I was using top right corner here
        // The better version is below
        public int sumRegion2(int row1, int col1, int row2, int col2) {
            int x = cache[row2][col2];
            int y = (col1 == 0 ? 0 : cache[row2][col1-1]);
            int z = (row1 == 0 ? 0:cache[row1-1][col2]);
            int a = (row1 == 0 || col1 == 0) ? 0:cache[row1 -1][col1-1];
            return x- y -z +a;
        }
        // This version is faster & easier check the constructor public NumMatrix (int [][] matrix)
        // Sum(ABCD) = Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA)
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return cache[row2+1][col2+1] - cache[row2+1][col1] - cache[row1][col2+1] + cache[row1][col1];
        }
    }

    // LeetCode :: 211. Add and Search Word - Data structure design
    // The idea is to use Trie to solve this problem. The add is simple, its just regular try implementation.
    // The search requires to match '.' as any so requires special handling for example b.d will match bad in trie
    // so for search whenever we get '.' we need to look at all the children of the current node which is equivalent
    // to skipping '.' so we visit all the children's subtree for the result
    class WordDictionary {
        TrieNode root;
        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TrieNode('@');
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            int i = 0;
            TrieNode node = root;
            while(i < word.length()) {
                char ch = word.charAt(i);
                if (node.mapChild[ch -'a'] == null)
                    node.mapChild[ch -'a'] = new TrieNode(ch);
                node = node.mapChild[ch -'a'];
                if(i == word.length() -1)
                    node.hasWord = true;
                i++;
            }
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        private boolean search (String word, int index, TrieNode node) {
            if (index == word.length() || node == null)
                return false;
            char ch = word.charAt(index);
            if (ch != '.') {
                return node.mapChild[ch-'a'] != null && node.mapChild[ch-'a'].hasWord && index == word.length() -1
                        || (search(word, index+1, node.mapChild[ch-'a']));
            } else {
                // found a '.' check all the valid children sub tree for the result
                for (int i = 0; i < 26; i++) {
                    if (node.mapChild[i] != null && node.mapChild[i].hasWord && index== word.length() -1
                            || (search(word, index + 1, node.mapChild[i])))
                        return true;
                }
                return false;
            }

        }

        public boolean search(String word) {
            int i = 0;
            return search(word, 0, root);
        }
    }


    // LeetCode :: 341. Flatten Nested List Iterator
    // The idea is to use a stack of iterator for the nested Iterators.
    // For example if NestedInteger is [1, [4, [6, [7]]], 8] then for each inner list we need an iterator so we keep
    // this iterators at each level in a stack We start with the top level iterator in the stack
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * This is just to know what the NestedInteger interface will look like
     * */
     public interface NestedInteger {

          // @return true if this NestedInteger holds a single integer, rather than a nested list.
          public boolean isInteger();

          // @return the single integer that this NestedInteger holds, if it holds a single integer
          // Return null if this NestedInteger holds a nested list
          public Integer getInteger();

          // @return the nested list that this NestedInteger holds, if it holds a nested list
          // Return null if this NestedInteger holds a single integer
          public List<NestedInteger> getList();
     }
    public class NestedIterator implements Iterator<Integer> {
        Iterator<NestedInteger> itr;
        LinkedList <Iterator<NestedInteger>> stack = new LinkedList<>();
        NestedInteger nint;

        public NestedIterator(List<NestedInteger> nestedList) {
            itr = nestedList.iterator();
            // push the top level iterator in stack
            stack.push(itr);
        }

        @Override
        // All the effort for finding the next item is actually done in hasNext and the result
        // Integer is already stored in nint so we just return the integer
        public Integer next() {
            return nint.getInteger();
        }

        @Override
        // This function does all the work it checks if the next item exist if yes grab the first item and
        // put it in nint. We peek at the top of the stack and start readin item using that iterator.
        // if the top iterator is empty we peek the next iterator from the stack. If the top iterator is pointing to
        // nonempty list we just push another iterator for the non empty list  to the stack. We keep doing this until
        // we get to list which has integer item, if no such item is found and we exhaust our stack of iterator
        // then no item exist in the nested list and we return false
        public boolean hasNext() {
            // keep lookling  for nested list iterator in stack
            while(!stack.isEmpty()){
                // the top item point to valid entry
                if(stack.peek().hasNext()){
                    Iterator<NestedInteger> tempItr = stack.peek();
                    nint = tempItr.next();
                    // the item is a single int store it in our nint so the call to 'next' can return this
                    if (nint.isInteger())
                        return true;
                    // we got another nested non empty list lets get an iterator for this and push it to the stack for
                    // next processing
                    if(nint.getList().size() != 0) {
                        stack.push(nint.getList().iterator());
                    }

                } else {
                    // no valid entry pop and look for next nested list iterator
                    stack.pop();
                }
            }
            return false;
        }
    }

    // LeetCode :: 146. LRU Cache
    // The idea is to use hashmap to store the key and a doubly linked list reference as value to make the access O(1)
    // We use a double linked list manage the LRU cache new items are add to the head, when we access an item using get
    // the recently accessed item needs to move to the head. This items closer to head are recently accessed and the
    // items closer to tail ar least recently accessed and candidates for removal when the capacity exceeds.
    // As the capacity exceeds we remove the leas recently used item from tail.
    // We use a dummy head & a dummy tail to make the code more concise and easy to manage.
    public class DblLinkList {
         int val;
         int key;
         DblLinkList prev;
         DblLinkList next;
         public DblLinkList(int k,int v){
             val = v;
             key = k;
             prev = next = null;
         }
    }

    class LRUCacheDList {
        int currCapcity;
        DblLinkList head;
        DblLinkList tail;
        HashMap<Integer, DblLinkList> cache;

        public LRUCacheDList(int capacity) {
            currCapcity = capacity;
            cache = new HashMap<>();
            head = new DblLinkList(Integer.MIN_VALUE,Integer.MIN_VALUE); // dummy head
            tail = new DblLinkList(Integer.MIN_VALUE, Integer.MIN_VALUE); // dummy tail
            head.next = tail;
            tail.prev = head;
            head.prev = tail;
            tail.next = head;
        }

        // move the current node to the head of the doubly linked list
        private void moveToHead (DblLinkList node){
            if (node.next == tail && node.prev == head)
                return;
            // remove value from current position
            node.prev.next = node.next;
            node.next.prev = node.prev;
            // move value after head
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // add a node after the dummy head of the doubly linked list
        private void addToHead (DblLinkList node){
            // insert the new item to head
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // delelte the last node that is the node before the dummy tail from the doubly linked list
        private  void deleteFromTail() {
            DblLinkList dlNode = tail.prev;
            tail.prev = dlNode.prev;
            dlNode.prev.next = tail;
            // remove from the hashmap
            cache.remove(dlNode.key);
        }

        public int get(int key) {
            DblLinkList value = cache.get(key);
            if (value == null)
                return  -1;
            if (value.next == tail && value.prev == head)
                return value.val;
            // move the recently accessed value to the head
            moveToHead(value);
            return value.val;

        }

        public void put(int key, int value) {
            // get the value if it already exist otherwise create a new one
            DblLinkList node = cache.getOrDefault(key, null);
            if (node == null)  {
                node = new DblLinkList(key, value);
                // insert the new item to head
                addToHead(node);
                // put the entry on hashmap
                cache.put(key, node);
            } else {
                // We already have the node in cache so move the node to the head & update the value
                moveToHead(node);
                node.val = value;
            }
            // if we exceeded the capacity remove the oldest one,
            // the oldest one is at the tail so we remove from tail
            if (cache .size() > currCapcity) {
                deleteFromTail();
            }

        }
    }
    // This is another way to implement the LRU cache using Java's LinkedHashMap
    // LinkedHashMap is ordered map and we just need to overwrite the removeEldestEntry
    // to implement the cache.
    //
    // LinkedHashMap :
    // Hash table and linked list implementation of the Map interface, with predictable iteration order.
    // This implementation differs from HashMap in that it maintains a doubly-linked list running through all of
    // its entries. This linked list defines the iteration ordering, which is normally the order in which keys were
    // inserted into the map (insertion-order). Note that insertion order is not affected if a key is re-inserted
    // into the map.
    public class CacheMap extends LinkedHashMap<Integer, Integer> {
         final int capacity;
         public CacheMap(int cap){
             super(cap, 0.75f, true);
             capacity = cap;

         }
        @Override
        // we just override this function if the map size  is greater capacity this overwrite wil delete the LRU item
        // in the map. This function internally gets called followed by a put or putAll method call
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
             System.out.println("map size " + size() + " capacity "+ capacity);
             return size() >capacity;
        }

    }

    class LRUCache {
        CacheMap cache;
        int cap;
        public LRUCache(int capacity) {
            cap = capacity;
            cache = new CacheMap(cap);
        }

        public int get(int key) {
            return cache.getOrDefault(key , -1);
        }

        public void put(int key, int value) {
            cache.put(key,value);
        }
    }
    // 355. Design Twitter
    // The main idea is to use k sorted list merge using max heap to get the top 10 items
    class Tweet {
         Integer user;
         Integer tweet;
         int time;
         public Tweet (int u , int tw, int tm){
             user = u;
             tweet = tw;
             time = tm;
         }
    }
    // This class is required for the maxheap. It is used to select the next item from the k sorted list when
    // the top item was removed. The top removed item points out the next array (listNum) and an index into that
    // array (idx) to insert/add to the max heap
    class QueueItem {
         Tweet tw;
         int listNum;
         int idx;
         public QueueItem(Tweet t, int l, int i) {
             tw = t;
             listNum = l;
             idx = i;
         }
    }
    class Twitter {
        HashMap<Integer, LinkedList<Tweet>> timeLineMap; // per user timeline
        HashMap<Integer,HashSet<Integer>> followMap;     // per user followList

        int time;
        /** Initialize your data structure here. */
        public Twitter() {
            time = 0;
            timeLineMap = new HashMap<>();
            followMap = new HashMap<>();
        }

        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            time++;
            Tweet tw = new Tweet(userId, tweetId, time);
            // add tweet to user's own TimeLine
            timeLineMap.putIfAbsent(userId, new LinkedList<>());
            timeLineMap.get(userId).addFirst(tw);
        }

        /** Retrieve the 10 most recent tweet ids in the user's news feed.
         *  Each item in the news feed must be posted by users who the user followed or by the user herself.
         *  Tweets must be ordered from most recent to least recent. */
        // The idea here is to use K sorted list merge to get 10 max items
        // All the user timelines are time sorted (descending order)
        // So we first get the followlist from the follow list we form the array of timelIne list of (following user)
        // these list are sorted so to get the top 10 we use a max heap and use he K sorted list merge option
        public List<Integer> getNewsFeed(int userId) {
            LinkedList<Tweet> userTL = timeLineMap.getOrDefault(userId, null);
            HashSet<Integer> followList = followMap.getOrDefault(userId, null);
            LinkedList<Integer> newsFeed = new LinkedList<>();
            if (userTL == null && followList == null)
                return newsFeed;

            // create min heap create the most recent tweets from the followers
            PriorityQueue<QueueItem> maxHeap = new PriorityQueue<>(new Comparator<QueueItem>() {
                @Override
                public int compare(QueueItem o1, QueueItem o2) {
                    return o2.tw.time - o1.tw.time;
                }
            });

            // From the follower list get the timeline for each follow and add an iterator to the timeLine list
            if (followList != null) {
                LinkedList <Tweet> [] listArr = new LinkedList[followList.size()+1];
                // add the user time line to the k sorted list too so during our merge we can easily merge them
                listArr[0] = userTL;
                if (userTL != null)
                    maxHeap.add(new QueueItem(userTL.get(0), 0, 0));
                int i = 1;
                for (Integer u : followList) {
                    listArr[i] = timeLineMap.getOrDefault(u, null);
                    if (listArr[i] != null) {
                        QueueItem qi = new QueueItem(listArr[i].get(0), i, 0);
                        maxHeap.add(qi);
                    }
                    i++;
                }
                int count = 10;
                while(!maxHeap.isEmpty() && count !=0 ){
                    QueueItem qi = maxHeap.remove();
                    newsFeed.add(qi.tw.tweet);
                    if (listArr[qi.listNum].size() > qi.idx +1) {
                        qi.tw = listArr[qi.listNum].get(qi.idx+1);
                        qi.idx++;
                        maxHeap.add(qi);
                    }
                    count--;
                }

            } else {
                for (Tweet tw : userTL){
                    newsFeed.add(tw.tweet);
                    if (newsFeed.size() == 10)
                        break;
                }
            }
            return newsFeed;

        }

        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            if (followeeId == followerId)
                return;
            followMap.putIfAbsent(followerId, new HashSet<>());
            followMap.get(followerId).add(followeeId);
        }

        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            if (followeeId == followerId)
                return;
            HashSet<Integer> followList = followMap.getOrDefault(followerId, null);
            if (followList != null)
                followList.remove(followeeId);
        }
    }

    // LeetCode :: 519. Random Flip Matrix
    // The idea is to use "Fisher–Yates shuffle" modern algorithm to choose the random permutation
    // The modern Algo:
    // Store the numbers from 1 through N in an array arr[N]
    //   1. Pick a random number k between (0 - curLen)
    //   2. swap the arr[k] with arr[curLen],
    //   3. Decrease curLen by 1.
    //   4. Repeat step 1 - 4 until the curLen is zero
    // Here we also use further space optimisation by using a hashmap. Instead of using an array of len = M*N
    // we use a hashmap to store the swapped items in the proper index in the hashmap.
    // This way we dont need the whole array but just need swapped items to store in the hashmap
    // In worst case the hashmap may grow upto O(m*n) space but average case should be better
    class SolutionFlipMatrix {
         public HashMap<Integer, Integer> map;
         int total;
         Random rand;
         int totR;
         int totC;
        public SolutionFlipMatrix(int n_rows, int n_cols) {
            totR = n_rows;
            totC = n_cols;
            map = new HashMap<>();
            total = n_cols * n_rows;
            rand = new Random();
        }

        public int[] flip() {
            // get the next random index  between zero to total (exclusive)
            int randomIdx = rand.nextInt(total);
            total--;
            int randVal = map.getOrDefault(randomIdx, randomIdx);
            map.put(randomIdx, map.getOrDefault(total, total));
            return new int[] {randVal/totC, randVal % totC};
        }

        public void reset() {
            map.clear();
            total = totR * totC;

        }
    }

    // LeetCode :: 470. Implement Rand10() Using Rand7()
    /**
     * The rand7() API is already defined in the parent class SolBase.
     * public int rand7();
     * @return a random integer in the range 1 to 7
     */
    class Rand10 {
        public int rand7(){
           return new Random().nextInt(7) +1;
        }

        // The idea is to use rejection sampling to generate random10 using random7
        // we create a n*n 2d space where the numbers (1 to 10) are mapped for 7*7
        // we can map only upto 40, and 41 - 49 will not be mapped.
        // We first gen ran7 for row and rand7 for col this gives
        // a true random distribution of the row & col option after that
        // if we found a number using the row we return it if we found an empty space
        // (41 - 49 are empty space & 1 - 40 has numbers 1 - 10 sequentially for times hence 40)
        // we retry to get row & col that maps to a number 1 to 40
        public int rand10() {
            int row;
            int col;
            int idx =0;
            // repeativly try to map the row & col to a 2d space where the value is between 1 to 40
            // as row & col max is 7 so possible values are 1 - 49
            while (true) {
                row= rand7();
                col = rand7();
                idx = (row-1) * 7 + col;
                // found row col that maps to 40 we can use this as our result
                if (idx <= 40)
                    break;
            }
            if (idx%10 == 0)
                return 10;
            return idx%10;
        }
    }



}
