import sun.awt.image.ImageWatched;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class LinkList {
    int data;
    LinkList next;

    public LinkList() {
        int data = 0;
        next = null;
    }

    public LinkList (int data){
        this.data = data;
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
    public void printList() {
        LinkList ls = this;
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

    public void removeDuplicate () {
        HashSet <Integer> map = new HashSet<>();
        LinkList ls = this;
        LinkList prev = ls;
        while (ls != null) {
            // found the entry time to delete
            if (map.add(ls.data) == false) {
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

    // Leetcode 21
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
   // reverse a List recursively
    private LinkList revList (LinkList nodeC, LinkList nodeN, LinkList head) {
        // reached the end, get head to point the last node or first node in rev order
        if (nodeN.next == null) {
            nodeN.next = nodeC;
            head = nodeN;
            return head;
        }
        // nodeC is the current node & nodeN is the next node. At call
        // (some other node)<-2(nodeC)  (nodeN) 3->(some other node) now lets make it 2<-3
        if (head == nodeC)
            nodeC.next = null;
        LinkList savedNode = nodeN.next;
        nodeN.next = nodeC;
        return revList(nodeN, savedNode, head);

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

    public LinkList reverseList(LinkList head) {
        if (head == null)
            return null;
        head = revList(head,head.next,head);
        return head;

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



}
