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

}
