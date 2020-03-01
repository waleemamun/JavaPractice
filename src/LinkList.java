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
    public void printList() {
        LinkList ls = this;
        while(ls!=null) {
            System.out.print(ls.data + "->");
            ls = ls.next;
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



}
