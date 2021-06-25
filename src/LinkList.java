import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;


public class LinkList {
    int data;
    LinkList next;
    public int val;

    public LinkList() {
        int data = 0;
        val = 0;
        next = null;
    }

    public LinkList (int data){
        this.data = data;
        val = data;
        next = null;
    }

    public LinkList createList(int []arr) {
        LinkList head = null;

        for (int i = arr.length-1 ; i>=0 ;i--) {
            LinkList elem = new LinkList(arr[i]);
            elem.next = head;
            head = elem;

        }
        return head;
    }

    public int getSize(){
        LinkList ls = this;
        int count = 0;
        while(ls!=null) {
            count++;
            ls = ls.next;
        }
        return count;

    }
    public static void printList(LinkList head) {
        LinkList ls = head;
        int count = 0;
        while(ls!=null) {
            count++;
            System.out.print(ls.data + "->");
            ls = ls.next;
        }
        System.out.println("null ::size = "+count);
    }

    public void printList(int size) {
        LinkList ls = this;
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

    public LinkList getNode(int val) {
        LinkList ls = this;
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
        LinkList ls = this;
        LinkList prev = ls;
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

    // Leetcode 19
    public LinkList removeNthFromEnd(LinkList head, int n) {
        LinkList prevN = head;
        LinkList fast = head;
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

    // Leetcode :: 21  Merge Two Sorted Lists
    // Check the V2 its the better implementation uses O(1) space
    public LinkList mergeTwoLists(LinkList l1, LinkList l2) {

        LinkList head = null;
        LinkList curr = null;
        if (l1 ==null)
            return l2;
        if (l2 == null)
            return l1;
        while (l1 != null  && l2 != null){
            LinkList tmpElem = new LinkList();

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
            LinkList tmpElem = new LinkList();
            tmpElem.data = l1.data;
            curr.next = tmpElem;
            curr = tmpElem;
            l1 = l1.next;
        }
        while (l2 != null) {
            LinkList tmpElem = new LinkList();
            tmpElem.data = l2.data;
            curr.next = tmpElem;
            curr = tmpElem;
            l2 = l2.next;
        }

        return head;
    }

    // lets use the two list to buld the sorted list without using any additional space
    // Use dummy node which will point to the new list
    public LinkList mergeTwoListsV2(LinkList l1, LinkList l2){
        LinkList dummy = new LinkList();
        LinkList curr = dummy;
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

    public LinkList mergeKLists(LinkList[] lists) {
        if (lists == null || lists.length == 0)
            return new LinkList();

        LinkList resultList = new LinkList(-1); // create a dummy list node as the result list
        // Create the MIN Heap/Priority Queue
        PriorityQueue<LinkList> pQueue = new PriorityQueue<>(new Comparator<LinkList>() {
            @Override
            public int compare(LinkList o1, LinkList o2) {
                if (o1.data < o2.data)
                    return -1;
                else if (o1.data > o2.data)
                    return 1;
                else
                    return 0;
            }
        });

        for (LinkList list : lists) {
            if (list != null)
                pQueue.add(list);
        }
        // create curr pointer for the result list we will increase the curr pointer
        // to add items at the end of the list as we remove them from the MIN Heap
        // each item added will be the smallest item on the Heap
        LinkList curr = resultList;
        while (!pQueue.isEmpty()) {
            LinkList tempList = pQueue.remove();
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
    public LinkList swapPairs(LinkList head) {
        LinkList resultList = new LinkList(-1);
        if (head == null)
            return null;
        if (head.next == null)
            return head;

        LinkList node1 = head;
        LinkList node2 = head.next;
        resultList.next = head.next;
        LinkList prev = node1;
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
    // Try solving with recursion
    private LinkList swapPairHelper (LinkList head, LinkList first) {
        if(first == null || first.next == null)
            return first;
        LinkList sec = first.next;
        first.next = swapPairHelper(head, sec.next);
        sec.next = first;
        return sec;
    }
    public LinkList swapPairsV2(LinkList head) {
        if(head == null)
            return head;
        LinkList newHead = swapPairHelper(head, head);
        return newHead;
    }



    // LeetCode :: 206. Reverse Linked List
    // reverse a List recursively, This passed.
    // The trick is to handle the first node specially
    private LinkList revListV2 (LinkList nodeC, LinkList nodeN, LinkList head) {
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
        LinkList savedNode = nodeN.next;
        nodeN.next = nodeC;
        return revListV2(nodeN, savedNode, head);
    }

    public LinkList reverseListV2(LinkList head) {
        if (head == null)
            return null;
        head = revListV2(head,head.next,head);
        return head;

    }

    //This is version 1 both version works its just a different way to handle recursion in this case
    private void revList(LinkList nodeP, LinkList nodeC, LinkList newhead, LinkList head) {
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
    public LinkList reverseList(LinkList head) {
        if (head == null || head.next == null)
            return head;
        LinkList newHead = new LinkList();
        revList(head,head.next, newHead, head);
        return newHead.next;
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
    public LinkList reverseKGroup(LinkList head, int k) {
        // if k = 1 we dont need to process
        if (k == 1)
            return head;

        LinkList curr = head;      // get a pointer to head
        int count = 1;             // count for k
        boolean isFirst = true;
        LinkList fromNode =null, toNode = null;
        LinkList tempHead = null, prevLastNode = null;

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
    private LinkList revListFromTo(LinkList nodeC, LinkList nodeN,
                                   LinkList fromNode, LinkList toNode) {

        // from & to pointing to the same no change required
        if (fromNode == toNode)
            return fromNode;
        // swap case: fromNode & toNode are adjacent
        if (fromNode == nodeC && toNode == nodeN){
            LinkList saveNext = toNode.next;
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
        LinkList savedNode = nodeN.next;
        nodeN.next = nodeC;
        return revListFromTo(nodeN, savedNode, fromNode, toNode);

    }
    // LeetCode :: 61 Rotate List, The idea is to find which node will become the new start node,
    // after that make the end node point to the first (node pointed by head) node.
    // The node before the new start node points to null
    // for example 1->2->3->4->5->null becomes 2->3->4->5->1->null
    public LinkList rotateRight(LinkList head, int k) {
        if(head == null || head.next == null)
            return head;

        LinkList cur = head;
        LinkList end = null;
        LinkList prev = null;
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
    public LinkList deleteDuplicates(LinkList head) {
        if (head == null)
            return head;
        LinkList unique =  head;
        LinkList cur = unique.next;
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
    public LinkList deleteDuplicatesKeepDistinct(LinkList head) {
        if (head == null)
            return null;
        LinkList newHead = null; // we need a newhead if the first entry pointed by head is duplicated
        LinkList cur = head;
        LinkList unq = null;
        while (cur != null) {
            int len = 0;
            LinkList prev = cur;
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

    // Leetcode :: 86 Partition List
    // The idea is to use two heads frontHead & tailHead, frontHead points to the list of lower values
    // & tailHead points to the head of greater or equal values. Then we use two moving pointers front
    // & tail two add element in the fronthead list & tailHead List. At the end we make frontHead list
    // last element points to the tailHead and have our desired list.
    // Note we dont need to create any new list we use the current list
    // pointer & move them around to create the desired list
    public LinkList partition(LinkList head, int x) {
        // list has one or zero entries
        if (head == null || head.next == null)
            return head;
        LinkList cur = head;
        LinkList front = null; // moving pointer in frontHead list
        LinkList tail = null;  // moving pointer in tailHead list
        LinkList frontHead = null; // points to the head of list with values < x
        LinkList tailHead = null;  // points to the head of list with values > x
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

    // 92. Reverse Linked List II
    // The idea is to use a stack to reverse the List
    // This require O(n) time & O(n) space
    public LinkList reverseBetween(LinkList head, int m, int n) {
        if (head == null || head.next == null)
            return head;
        LinkList curr = head;
        LinkList mPrevNode = null; // node before m node
        LinkList nNodeNext = null; // node after n node, if its null its fine
        Stack<LinkList> listStack = new Stack<>();
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
            LinkList ls = listStack.pop();

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
    public LinkList reverseBetweenV2(LinkList head, int m, int n) {
        if (head == null || head.next == null || m == n )
            return head;
        LinkList curr = head;
        LinkList mPrev = null;
        LinkList prev = null;
        LinkList nextNode = null;
        LinkList mNode = null;
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
    public LinkList midNode(LinkList head){
        if(head == null || head.next == null)
            return head;
        LinkList slow = head;
        LinkList fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }
    // LeetCode :: 141. Linked List Cycle
    public boolean hasCycle(LinkList head) {
        if (head == null || head.next == null)
            return false;
        LinkList slow = head;
        LinkList fast = head;

        while (fast!= null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    // Leetcode :: 142. Linked List Cycle II
    public LinkList detectCycle(LinkList head) {
        if (head == null || head.next == null)
            return null;
        LinkList slow = head;
        LinkList fast = head;
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
    private LinkList mergeSortList(LinkList l1) {

        if (l1 == null || l1.next == null)
            return l1;
        LinkList slow = l1;
        LinkList fast = l1;
        LinkList preSlow = null;
        while (fast!=null && fast.next != null) {
            fast = fast.next.next;
            preSlow = slow;
            slow = slow.next;
        }
        // make the first list end point to null & return a pointer to the middle of the list
        if (preSlow != null)
            preSlow.next = null;
        // recursive sort the left half of the list
        LinkList ls1= mergeSortList(l1);
        // recursive sort the right half of the list
        LinkList ls2 = mergeSortList(slow);
        // merge the left & right sorted list to a single merge list
        LinkList mergeList = mergeTwoListsV2(ls1,ls2);

        return mergeList;
    }

    // LeetCode :: 148. Sort List
    // look at the mergeSortList as done for Adnan Aziz 14.9
    public LinkList sortList(LinkList head) {
        return mergeSortList(head);
    }

    // LeetCode :: 160. Intersection of Two Linked Lists
    public LinkList getIntersectionNode(LinkList headA, LinkList headB) {
        LinkList lsA = headA;
        LinkList lsB = headB;
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
    public LinkList oddEvenList(LinkList head) {
        if (head == null)
            return head;
        LinkList oddHead = new LinkList();
        LinkList evenHead = new LinkList();
        LinkList odd = oddHead;
        LinkList even = evenHead;
        LinkList curr = head;
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
    private int plusOneHelper(LinkList node){
        if (node == null)
            return 1;
        int sum = node.data + plusOneHelper(node.next);
        node.data = sum % 10;
        return sum/10;
    }

    public LinkList plusOne(LinkList head) {
        if (head == null)
            return head;
        int carry = plusOneHelper(head);
        if (carry == 1) {
            LinkList top = new LinkList(1);
            top.next = head;
            head = top;
        }
        return head;
    }








}
