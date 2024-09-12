import java.util.*;


public class ListNode {
    int data;
    ListNode next;
    public int val;

    public ListNode() {
        int data = 0;
        val = 0;
        next = null;
    }

    public ListNode(int data){
        this.data = data;
        val = data;
        next = null;
    }

    public ListNode createList(int []arr) {
        ListNode head = null;

        for (int i = arr.length-1 ; i>=0 ;i--) {
            ListNode elem = new ListNode(arr[i]);
            elem.next = head;
            head = elem;

        }
        return head;
    }

    public int getSize(){
        ListNode ls = this;
        int count = 0;
        while(ls!=null) {
            count++;
            ls = ls.next;
        }
        return count;

    }
    public static void printList(ListNode head) {
        ListNode ls = head;
        int count = 0;
        while(ls!=null) {
            count++;
            System.out.print(ls.data + "->");
            ls = ls.next;
        }
        System.out.println("null ::size = "+count);
    }

    public void printList(int size) {
        ListNode ls = this;
        int count = 0;
        while(ls != null) {
            System.out.print(ls.data + "->");
            ls = ls.next;
            count++;
            if (count == size)
                break;
        }
        System.out.println("null");
    }

    public ListNode getNode(int val) {
        ListNode ls = this;
        while(ls != null && ls.val != val)
            ls = ls.next;
        return ls;
    }

    // Remove duplicate from a unsorted LIst in O(n) time with O(n) space
    // We use two pointers one (prev) to point to the unique  elem so far &
    // another running pointer (ls) to move through the list
    // We traverse the list when we find a unique value we put in Set, if we see a duplicate
    // we use the prev pointer to point to the next elem
    public void removeDuplicate () {
        HashSet <Integer> map = new HashSet<>();
        ListNode ls = this;
        ListNode prev = ls;
        while (ls != null) {
            // found the entry time to delete
            if (!map.add(ls.data)) {
                prev.next = ls.next;
                ls = ls.next;
                continue;
            }
            prev = ls;
            ls = ls.next;
        }


    }

    // Leetcode 19 :: Remove Nth Node From End of List
    public ListNode removeNthFromEndV2(ListNode head, int n) {
        ListNode prevN = head;
        ListNode fast = head;
        int count = 0;
        if (head == null)
            return null;
        // now fast points to the nth element from start
        while (fast != null && count < n) {
            count++;
            fast = fast.next;
        }
        if (fast == null) {

            if (head.next != null)
                return head.next;
            else
                return null;
        }
        // now move fast to the prev last item
        while(fast.next != null) {
            fast = fast.next;
            prevN = prevN.next;
        }
        // now prevN point to the previous entry of the nth last entry
        if (prevN.next != null)
            prevN.next = prevN.next.next;

        return head;
    }

    // this version has a cleaner code
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode();
        ListNode slow = dummy;
        ListNode fast = dummy;
        dummy.next = head;

        while (fast != null &&  n >= 0) {
            fast = fast.next;
            n--;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
    // Leetcode :: 21  Merge Two Sorted Lists
    // Check the V2 its the better implementation uses O(1) space
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode head = null;
        ListNode curr = null;
        if (l1 ==null)
            return l2;
        if (l2 == null)
            return l1;
        while (l1 != null  && l2 != null){
            ListNode tmpElem = new ListNode();

            if (l1.data <= l2.data) {
                tmpElem.data = l1.data;
                l1 = l1.next;
            }
            else {
                tmpElem.data = l2.data;
                l2 = l2.next;
            }
            if (head == null) {
                head = tmpElem;
                curr = tmpElem;
            } else {
                curr.next = tmpElem;
                curr = tmpElem;
            }
            tmpElem.next = null;
            //System.out.println(" cur " + curr.data + " temp "+ tmpElem.data);

        }

        while (l1 != null) {
            ListNode tmpElem = new ListNode();
            tmpElem.data = l1.data;
            curr.next = tmpElem;
            curr = tmpElem;
            l1 = l1.next;
        }
        while (l2 != null) {
            ListNode tmpElem = new ListNode();
            tmpElem.data = l2.data;
            curr.next = tmpElem;
            curr = tmpElem;
            l2 = l2.next;
        }

        return head;
    }

    // lets use the two list to buld the sorted list without using any additional space
    // Use dummy node which will point to the new list
    public ListNode mergeTwoListsV2(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        while (l1 != null && l2 != null){

            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;

            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = (l1!=null)?l1:l2;
        return dummy.next;
    }

    /* Leetcode 23 Merge K-sorted lists.
     * An efficient solution is to use Min Heap.
     * This Min Heap based solution has same time complexity which is O(nk Log k).
     *
     * Following is detailed algorithm.
     *  1. Get the first item of all k lists and put it into a Min Heap/ Priority Queue.
     *     Use a linked list type Priorty Queue. The Min Heap would be based on the data of the node
     *     while the next ptr of this list can be used in the next step.
     *  2. Repeat following steps until the Queue is empty.
     *      a) Get minimum element from heap (minimum is always at root) and add it at the end of the resultList.
     *      b) Replace heap root with next element from the linkList (the next pointer of the removed list still points
     *         to the next Item in the original list) from which the element is extracted.
     *         Add the item pointed by the next ptr due to heapify call internally it will go to its proper position
     *
     */

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return new ListNode();

        ListNode resultList = new ListNode(-1); // create a dummy list node as the result list
        PriorityQueue<ListNode> pQueue = new PriorityQueue<>((o1, o2)->(o1.data -o2.data));
        pQueue.addAll(Arrays.asList(lists));
        // create curr pointer for the result list we will increase the curr pointer
        // to add items at the end of the list as we remove them from the MIN Heap
        // each item added will be the smallest item on the Heap
        ListNode curr = resultList;
        while (!pQueue.isEmpty()) {
            ListNode tempList = pQueue.remove();
            if (tempList.next != null)
                pQueue.add(tempList.next);
            curr.next = tempList;
            curr = curr.next; // curr now points to tempList

        }
        if (curr != null)
            curr.next = null;

        return resultList.next;
    }

    // LeetCode 24
    public ListNode swapPairs(ListNode head) {
        ListNode resultList = new ListNode(-1);
        if (head == null)
            return null;
        if (head.next == null)
            return head;

        ListNode node1 = head;
        ListNode node2 = head.next;
        resultList.next = head.next;
        ListNode prev = node1;
        boolean isfirst = true;
        while (node1 != null && node1.next != null) {
            //swap nodes by changing the next pointers of the nodes
            node1.next = node2.next;    // first node1 points to the next pointer of node2
            node2.next = node1;          // now 2nd node points to first node swap done
            if (!isfirst) {
                // the node2 has been swapped with node 1 we need the prev to point to node2
                // so that the linklist remain connected.
                // 2 -> 1 -> 3 -> 4 -> 5 need to become 2 -> 1 > 4 -> 3 - >5 not 2 -> 1 -> 3 <-4
                //                                                                         |
                //                                                                         V
                //                                                                         5
                prev.next = node2;
            } else {
                isfirst = false;
            }
            // store the prev node for the next iteration as we need to update it to point to the new node2
            // Useful Tips: to save prev node skip using it in the first iteration & init  after using the first time
            prev = node1;
            // update node1 & node2 to point to the next pair to be swapped
            node1 = node1.next;
            if (node1 != null)
                node2 = node1.next;



        }
        return resultList.next;

    }

    // This is more concise version of the solution
    public ListNode swapPairs2(ListNode head) {
        if (head == null)
            return head;
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode n1, n2, prev;
        prev = dummy;
        n1= head;
        n2 = head.next;
        while(n1!=null && n1.next!=null){
            //swap
            n1.next = n2.next;
            n2.next = n1;
            prev.next = n2;
            //advance
            prev = n1;
            n1 = n1.next;
            if (n1!= null)
                n2 = n1.next;
        }
        return dummy.next;
    }

    // Try solving with recursion
    private ListNode swapPairHelper2 (ListNode first) {
        if(first == null || first.next == null)
            return first;
        ListNode sec = first.next;
        first.next = swapPairHelper2(sec.next);
        sec.next = first;
        return sec;
    }

    private ListNode swapPairHelper (ListNode head, ListNode first) {
        if(first == null || first.next == null)
            return first;
        ListNode sec = first.next;
        first.next = swapPairHelper(head, sec.next);
        sec.next = first;
        return sec;
    }
    public ListNode swapPairsV2(ListNode head) {
        if(head == null)
            return head;
        ListNode newHead = swapPairHelper(head, head);
        return newHead;
    }



    // LeetCode :: 206. Reverse Linked List
    // reverse a List recursively, This passed.
    // The trick is to handle the first node specially
    private ListNode revListV2 (ListNode nodeC, ListNode nodeN, ListNode head) {
        // this is  the first node, make it point to null
        // as in the reverse list it needs to point to NULL
        if (head == nodeC)
            nodeC.next = null;
        // reached the end, get head to point the last node or first node in rev order
        if (nodeN.next == null) {
            nodeN.next = nodeC;
            head = nodeN;
            return head;
        }
        // nodeC is the current node & nodeN is the next node. At call
        // (some other node)<-2(nodeC)  (nodeN) 3->(some other node) now lets make it 2<-3
        ListNode savedNode = nodeN.next;
        nodeN.next = nodeC;
        return revListV2(nodeN, savedNode, head);
    }

    public ListNode reverseListV2(ListNode head) {
        if (head == null)
            return null;
        head = revListV2(head,head.next,head);
        return head;

    }

    //This is version 1 both version works its just a different way to handle recursion in this case
    private void revList(ListNode nodeP, ListNode nodeC, ListNode newhead, ListNode head) {
        if (nodeC.next == null) {
            newhead.next = nodeC;
            nodeC.next = nodeP;
            if (nodeP == head)
                nodeP.next = null;
            return;
        }
        revList(nodeP.next, nodeC.next, newhead, head);
        nodeC.next = nodeP;
        if (nodeP == head)
            nodeP.next = null;


    }
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode newHead = new ListNode();
        revList(head,head.next, newHead, head);
        return newHead.next;
    }
    // more simpler version easy to read
    public void revList(ListNode p, ListNode c, ListNode nh){
        if (c==null) {
            nh.next = p;
            return;
        }
        revList(c, c.next, nh);
        c.next = p;

    }
    public ListNode reverseListV3(ListNode head) {
        if (head == null)
            return head;
        ListNode newHead = new ListNode();
        revList(head,head.next,newHead);
        head.next = null;
        return newHead.next;
    }

    public ListNode reverseList4(ListNode head) {
        if (head == null)
            return head;
        ListNode dummy = new ListNode();
        ListNode c = revLs(head, dummy);
        return dummy.next;

    }
    public ListNode revLs(ListNode node, ListNode nh) {
        if (node.next == null){
            nh.next = node;
            return node;
        }
        ListNode c = revLs(node.next, nh);
        c.next = node;
        node.next = null;
        return node;
    }

    // LeetCode 25:  Reverse Nodes in k-Group
    // The idea is to call the api (revListFromTo) multiple times.
    // The api reverses all the node between fromNode & toNode.
    // So we move along the list and pick group of size K and call the revListFromTo.
    // We need to consider few things here
    //    1.In the first call to revListFromTo save list in head
    //    2. Subsequent call save the list in tempHead
    //    3. We need to save the last node (this is the fromNode which is now after reverse became last Node)
    //       of the reversed k size list in prevLastNode. This will be used link the list properly after each reverse.
    //       The prevLastNode will create the link between the original list and the newly reversed k size list.
    //       Example 1->2->3->4->5->6 in first step 3->2->1->4->5->6 now prevLastNode = 1 and
    //       the new reversed group is 6->5->4, so prevLastNode now links it by 3->2->1-> 6->5->4
    public ListNode reverseKGroup(ListNode head, int k) {
        // if k = 1 we dont need to process
        if (k == 1)
            return head;

        ListNode curr = head;      // get a pointer to head
        int count = 1;             // count for k
        boolean isFirst = true;
        ListNode fromNode =null, toNode = null;
        ListNode tempHead = null, prevLastNode = null;

        while (curr != null) {
            if (count % k == 1) {
                fromNode = curr;
            }
            if (count % k == 0) {
                toNode = curr;
                if (isFirst) {
                    // use head only once as head needs to point to the start of the list
                    head = revListFromTo(fromNode, fromNode.next, fromNode, toNode);
                    isFirst = false;
                } else {
                    // temphead will point to the newly reversed (only k items reversed rest are same) list
                    tempHead = revListFromTo(fromNode, fromNode.next, fromNode, toNode);
                    // link the original list to the newly reversed list
                    // we saved the prevLastNode in the previous iteration
                    prevLastNode.next = tempHead;
                }
                // save the current fromNode to be used in the next iteration for linking
                prevLastNode = fromNode;
                // update curr to point to the currently processed list,
                // This is a very crucial step curr needs to be updated properly
                // because the list is changing with every call to revListFromTo , we need to update curr accordingly.
                // No need to point curr to curr.next here as it will be done just below this.
                curr = fromNode;
            }
            count++;
            curr = curr.next;
        }

        /* Ignore: Test code for the revListFromTo
        fromNode = head.next.next.next;
        toNode = fromNode.next.next;
        head = revListFromTo(fromNode,fromNode.next,fromNode,toNode);*/

        return head;
    }
    // reverse a List recursively between nodes fromNode to toNode,
    // return  a pointer the points to the reversed list
    private ListNode revListFromTo(ListNode nodeC, ListNode nodeN,
                                   ListNode fromNode, ListNode toNode) {

        // from & to pointing to the same no change required
        if (fromNode == toNode)
            return fromNode;
        // swap case: fromNode & toNode are adjacent
        if (fromNode == nodeC && toNode == nodeN){
            ListNode saveNext = toNode.next;
            nodeN.next = nodeC;
            nodeC.next = saveNext;
            return toNode;
        }
        // either we reached the end, or we reached toNode and sould stop processing any more node
        // get frmonNode to point the last node or first node in rev order
        if (nodeN.next == null || nodeN == toNode) {
            nodeN.next = nodeC;
            fromNode = nodeN;
            return fromNode;
        }
        // make the first node point to the next node of the last_node/toNode
        // example:: 1->2->3->4 if fromNode = 1 and toNode = 3 after this we have 1->4
        // later this will help us achieve 3->2->1->4
        if (fromNode == nodeC)
            nodeC.next = toNode.next;
        ListNode savedNode = nodeN.next;
        nodeN.next = nodeC;
        return revListFromTo(nodeN, savedNode, fromNode, toNode);

    }
    // LeetCode :: 61 Rotate List, The idea is to find which node will become the new start node,
    // after that make the end node point to the first (node pointed by head) node.
    // The node before the new start node points to null
    // for example 1->2->3->4->5->null becomes 2->3->4->5->1->null
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null)
            return head;

        ListNode cur = head;
        ListNode end = null;
        ListNode prev = null;
        int count = 0;

        // count the size of the list, end points to the last list
        while (cur != null) {
            count++;
            end = cur;
            cur = cur.next;
        }

        // calc the actual rotation needed
        int rotateCount = k % count;

        // if rotatecount zero then no rotation needed
        if (rotateCount == 0)
            return head;

        // move cur back to head
        cur = head;
        //System.out.println(count + " " + rotateCount);
        while (cur !=null &&(count - rotateCount) != 0) {
            prev = cur;
            rotateCount++;
            cur = cur.next;
        }
        // now cur points to the start of the list and prev point the node before cur
        // end still points to end of the list, we move end.next to point the first node
        end.next = head;
        prev.next = null;
        return cur;

    }

    //LeetCode :: 83. Remove Duplicates from Sorted List
    // The list is sorted so we store the first unique value and then conitnue
    // to scan until we found anothe unique value, when we have found such a
    // value we point to the new unique
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return head;
        ListNode unique =  head;
        ListNode cur = unique.next;
        while (cur != null) {
            if(cur.data != unique.data) {
                unique.next = cur;
                unique = cur;
            }
            cur = cur.next;
        }
        unique.next = cur;
        return  head;
    }

    // 82. Remove Duplicates from Sorted List II
    // The idea is to have two pointers prev & cur we move cur to the right and match
    // with prev value the distance between prev & cur determines whether this value
    // is unique or not. If the distance is 1 then its unique otherwise it not unique
    // and we can skip this value
    public ListNode deleteDuplicatesKeepDistinctv2(ListNode head) {
        if (head == null)
            return null;
        ListNode newHead = null; // we need a newhead if the first entry pointed by head is duplicated
        ListNode cur = head;
        ListNode unq = null;
        while (cur != null) {
            int len = 0;
            ListNode prev = cur;
            while (cur != null && cur.data == prev.data){
                len++;
                cur = cur.next;
            }
            // len == 1 so this value is unique
            if (len == 1) {
                // lets get a newHead to handle sceanrio like head->1->1->1->2->3
                if(newHead == null)
                    newHead = prev;
                // lets use last unq to point to new unq thus
                // deleting non-unique values from the list
                if (unq != null)
                    unq.next = prev;
                unq = prev;
            }
        }
        // unq next need to point to the null to handle
        // the scenario 4->5->5->5->5->5->NULL
        if (unq != null) {
            unq.next = null;
        }

        return newHead;
    }

    // just little optimization
    public ListNode deleteDuplicatesKeepDistinct2(ListNode head) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(); // we need a newhead if the first entry pointed by head is duplicated
        ListNode cur = head;
        ListNode unq = dummy;
        while (cur != null) {
            int len = 0;
            ListNode prev = cur;
            while (cur != null && cur.data == prev.data){
                len++;
                cur = cur.next;
            }
            // len == 1 so this value is unique
            if (len == 1) {
                unq.next = prev;
                unq = unq.next;
            }
        }

        unq.next = null;

        return dummy.next;
    }

    // Leetcode :: 86 Partition List
    // Note the version 2 exactly same idea but more concise coding
    // The idea is to use two heads frontHead & tailHead, frontHead points to the list of lower values
    // & tailHead points to the head of greater or equal values. Then we use two moving pointers front
    // & tail two add element in the fronthead list & tailHead List. At the end we make frontHead list
    // last element points to the tailHead and have our desired list.
    // Note we dont need to create any new list we use the current list
    // pointer & move them around to create the desired list
    public ListNode partition(ListNode head, int x) {
        // list has one or zero entries
        if (head == null || head.next == null)
            return head;
        ListNode cur = head;
        ListNode front = null; // moving pointer in frontHead list
        ListNode tail = null;  // moving pointer in tailHead list
        ListNode frontHead = null; // points to the head of list with values < x
        ListNode tailHead = null;  // points to the head of list with values > x
        while (cur != null) {
            if (cur.data < x) {
                // first elem in frontHead lets the frontHead point ot the first elem
                if (frontHead == null)
                    frontHead = cur;
                // new element can be added to front List so
                // use front pointer to add it to the list
                if(front != null)
                    front.next = cur;
                front = cur;
            } else {
                // first elem in tailHead lets the tailHead point ot the first elem
                if (tailHead == null)
                    tailHead = cur;
                // new element can be added to tail List so
                // use tail pointer to add it to the list
                if(tail != null)
                    tail.next = cur;
                tail = cur;
            }

            cur = cur.next;
        }
        // frontHead null means we do not found any entry < x,
        // so we can return the whole list as is
        if (frontHead == null)
            return head;
        else // make the end of front List point to the tailHead so that both list joined
            front.next = tailHead;
        // make sure the list end points to null
        if (tail != null)
            tail.next = null;
        return frontHead;
    }

    public ListNode partitionV2(ListNode head, int x) {
        ListNode dm1 = new ListNode();
        ListNode dm2 = new ListNode();
        ListNode d1 = dm1, d2 = dm2 , cur = head;
        while (cur!= null) {
            if(cur.val < x){
                d1.next = cur;
                d1 = d1.next;
            } else {
                d2.next = cur;
                d2 = d2.next;
            }
            cur = cur.next;
        }
        d2.next = null;
        d1.next = dm2.next;
        return dm1.next;
    }

    // 92. Reverse Linked List II
    // The idea is to use a stack to reverse the List
    // This require O(n) time & O(n) space
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null)
            return head;
        ListNode curr = head;
        ListNode mPrevNode = null; // node before m node
        ListNode nNodeNext = null; // node after n node, if its null its fine
        Stack<ListNode> listStack = new Stack<>();
        int pos = 1;
        // scan the array to build the stack
        while (curr != null) {
            if(pos == m-1)
                mPrevNode = curr;
            // push all node between m & n to a stack
            if (pos >= m && pos <= n) {
                if (pos == n)
                    nNodeNext = curr.next;
                listStack.push(curr);
            }
            curr = curr.next;
            pos++;
        }
        // remove the node from the stack and add them back between mPrevNode & nNextNode
        while (!listStack.empty()) {
            ListNode ls = listStack.pop();

            if (mPrevNode != null) {
                mPrevNode.next = ls;
                mPrevNode = mPrevNode.next;
            } else {
                // mprevNode is null it means m == 1 so we need
                // our head to point to the new head entry
                head = ls;
                mPrevNode = ls;
            }
        }
        // let the last node from the revese part point to the node next of N
        mPrevNode.next = nNodeNext;

        return head;
    }
    // The version two dont use any stack so ideally uses constant space run time is O(n)
    // The idea is to use three pointer prev, cur & nextNode , we want to reverse between m & n,
    // so when we start prev == m curr == m+1 & nexNode = m+2, now we make curr point to prev
    // but before that save curr's next pointer in nextNode, we keep doing it till node n
    // after that we just need to fix the tail(nexNode) & head(mPrev or head) pointers
    public ListNode reverseBetweenV2(ListNode head, int m, int n) {
        if (head == null || head.next == null || m == n )
            return head;
        ListNode curr = head;
        ListNode mPrev = null;
        ListNode prev = null;
        ListNode nextNode = null;
        ListNode mNode = null;
        int pos = 1;

        while (curr != null) {
            if(pos == m - 1)
                mPrev = curr;
            if (pos >= m && pos <= n){
                if (pos == m) {
                    prev = curr;
                    mNode = curr;
                } else {
                    nextNode = curr.next;
                    curr.next = prev;
                    prev = curr;
                    if(pos == n) {
                        // position is n so curr points to node n hence break and use curr as out node n
                        // also node nextNode is already saved so we dont need worry about the next node after n
                        break;
                    }
                    curr = nextNode;
                    pos++;
                    continue;
                }
            }
            pos++;
            curr = curr.next;
        }
        if (mPrev != null)
            mPrev.next = curr;
        else // m == 1 so the head needs to pint to the start of the reverse
            head = curr;
        mNode.next = nextNode;
        return head;
    }

    // LeetCode :: 876. Middle of the Linked List
    // Consider the scenario for even number of nodes
    // 1,2,3,4,5,6 here 4 is the middle node not 3
    public ListNode midNode(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }
    // LeetCode :: 141. Linked List Cycle
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode slow = head;
        ListNode fast = head;

        while (fast!= null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    // Leetcode :: 142. Linked List Cycle II
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode slow = head;
        ListNode fast = head;
        // check if fast & fast.next is not null, checking fast !=null also make sure
        // that in corner case slow is not null
        while (fast != null &&
                fast.next != null ) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                break;
        }
        // no cycle we exited the loop after hitting null
        if (slow != null && slow != fast)
            return null;
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }



    // Adnan Aziz 14.9 sort a linked list
    // We need to use a merge sort. First we split the list into two halves and
    // recursively sort the left & right half then merge the two sorted halves
    private ListNode mergeSortList(ListNode l1) {

        if (l1 == null || l1.next == null)
            return l1;
        ListNode slow = l1;
        ListNode fast = l1;
        ListNode preSlow = null;
        while (fast!=null && fast.next != null) {
            fast = fast.next.next;
            preSlow = slow;
            slow = slow.next;
        }
        // make the first list end point to null & return a pointer to the middle of the list
        preSlow.next = null;
        // recursive sort the left half of the list
        ListNode ls1= mergeSortList(l1);
        // recursive sort the right half of the list
        ListNode ls2 = mergeSortList(slow);
        // merge the left & right sorted list to a single merge list
        ListNode mergeList = mergeTwoListsV2(ls1,ls2);

        return mergeList;
    }

    // LeetCode :: 148. Sort List
    // look at the mergeSortList as done for Adnan Aziz 14.9
    public ListNode sortList(ListNode head) {
        return mergeSortList(head);
    }

    // LeetCode :: 160. Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode lsA = headA;
        ListNode lsB = headB;
        int lenA = 0;
        int lenB = 0;
        while (lsA != null) {
            lenA++;
            lsA = lsA.next;
        }
        while (lsB != null){
            lenB++;
            lsB = lsB.next;
        }
        lsA = headA;
        lsB = headB;
        int diff = Math.abs(lenA -lenB);
        if (lenA > lenB) {
            while (diff != 0) {
                lsA =lsA.next;
                diff--;
            }
        } else  {
            while (diff != 0) {
                lsB = lsB.next;
                diff--;
            }
        }
        while (lsA!=lsB) {
            lsA = lsA.next;
            lsB = lsB.next;
        }
        return lsA;

    }

    // LeetCode :: 328. Odd Even Linked List
    public ListNode oddEvenList(ListNode head) {
        if (head == null)
            return head;
        ListNode oddHead = new ListNode();
        ListNode evenHead = new ListNode();
        ListNode odd = oddHead;
        ListNode even = evenHead;
        ListNode curr = head;
        boolean isOdd = true;
        while (curr != null) {
            if (isOdd) {
                odd.next = curr;
                odd = curr;
            } else {
                even.next = curr;
                even = curr;
            }
            isOdd = !isOdd;
            curr = curr.next;
        }
        even.next = null;
        odd.next = evenHead.next;
        return head;
    }

    // LeetCode ::  Plus One Linked List
    private int plusOneHelper(ListNode node){
        if (node == null)
            return 1;
        int sum = node.data + plusOneHelper(node.next);
        node.data = sum % 10;
        return sum/10;
    }

    public ListNode plusOne(ListNode head) {
        if (head == null)
            return head;
        int carry = plusOneHelper(head);
        if (carry == 1) {
            ListNode top = new ListNode(1);
            top.next = head;
            head = top;
        }
        return head;
    }








}
