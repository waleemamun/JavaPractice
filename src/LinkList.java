import sun.awt.image.ImageWatched;

import java.util.HashSet;

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



}
