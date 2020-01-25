import java.util.ArrayList;
import java.util.LinkedList;

public class Tree {

    public TreeNode root;
    public Tree (int rootVal){
        root = new TreeNode(rootVal);
    }
    public Tree () {
        root = new TreeNode(Integer.MIN_VALUE); // creating a tree but the root value is not init
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void createBinaryTree (int arr[]) {
        if (arr.length == 0) {
            System.out.println("Array size zero");
        }
        int mid = arr.length/2;
        root.data = arr [mid];
        root.leftChild = createBinaryTreeRecurse(0, mid-1, arr);
        root.rightChild = createBinaryTreeRecurse(mid+1, arr.length-1,arr);

    }

    public TreeNode createBSTree (int low , int high, int arr []){
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.leftChild = createBSTree(low, mid-1, arr);
        node.rightChild = createBSTree(mid+1, high, arr);
        return node;
    }

    private TreeNode createBinaryTreeRecurse (int low, int high, int arr []) {
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.leftChild = createBinaryTreeRecurse(low, mid-1, arr);
        node.rightChild = createBinaryTreeRecurse(mid+1, high, arr);
        return node;
    }

    public void inOrderTraversal (TreeNode node) {
        if ( node != null) {
            inOrderTraversal(node.leftChild);
            System.out.print(node.data + " ");
            inOrderTraversal(node.rightChild);
        }
    }

    public void preOrderTraversal (TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderTraversal(node.leftChild);
            preOrderTraversal(node.rightChild);
        }
    }

    public TreeNode search(int data, TreeNode node) {
        if (node == null) {
            System.out.println("Data not found");
            return null;
        }
        if (data == node.data) {
            return node;
        }
        else if (data < node.data) {
            return search(data, node.leftChild);
        } else {
            return search(data, node.rightChild);
        }
    }

    public TreeNode searchBiggest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.rightChild == null)
            return node;
        return searchBiggest(node.rightChild);

    }

    public TreeNode searchSmallest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild == null)
            return node;
        return searchSmallest(node.leftChild);

    }

    public TreeNode inOrderPredecessorWithoutParent (TreeNode node) {
        if (node.leftChild != null) {
            return searchBiggest(node.leftChild);
        }
        TreeNode pred = null;
        TreeNode rt = root;

        while (rt != null) {
            if (node.data < rt.data) {
                rt = rt.leftChild;
            } else if (node.data > rt.data) {
                pred = rt;
                rt = rt.rightChild;
            } else {
                break;
            }
        }
        return pred;
    }

    public TreeNode inOrderSuccesorWithParent (TreeNode node) {
        if (node.rightChild != null) {
            return searchSmallest(node.rightChild);
        }
        TreeNode pnode = node.parent;
        while(pnode != null && pnode.rightChild == node) {
            pnode = pnode.parent;
            node = node.parent;
        }
        return pnode;
    }
    public TreeNode inOrderSuccessorWithoutParent (TreeNode node) {
        TreeNode rt = root;
        TreeNode succ = null;

        if (node.rightChild != null) {
            return searchSmallest(node.rightChild);
        }

        while (rt != null) {
            if (node.data < rt.data) {
                succ = rt;
                rt = rt.leftChild;
            } else if (node.data > rt.data) {
                rt = rt.rightChild;
            } else
                break;
        }
        return succ;

    }

    public void levelOrderTraversalAdd2List () {
        ArrayList<LinkedList<TreeNode>> listArray = new ArrayList<>();
        int level = 0;
        LinkedList<TreeNode> list  = new LinkedList();
        list.add(root);
        listArray.add(level,list);

        while (true) {
            list = new LinkedList<>();
            for (int i = 0; i < listArray.get(level).size(); i++) {
                TreeNode node = (TreeNode) listArray.get(level).get(i);
                if (node.leftChild != null)
                    list.add(node.leftChild);
                if (node.rightChild != null)
                    list.add(node.rightChild);
            }
            if (list.size() == 0) {
                break;
            }
            level++;
            listArray.add(level,list);

        }

    }
    public boolean covers (int data, TreeNode node) {
        if (node == null) {
            return false;
        }
        if (node.data == data)
            return true;
        else
            return covers(data, node.leftChild) | covers(data, node.rightChild);
    }
    public TreeNode commonAncestor(TreeNode nodeA, TreeNode nodeB ) {
        TreeNode root = this.root;
        if (nodeA == null || nodeB == null) {
            return root;
        }
        if (isAncestor(nodeA , nodeB))
            return nodeA;
        if (isAncestor(nodeB,nodeA)) {
            return nodeB;
        }
        return commonAncestor(root, nodeA.data, nodeB.data);

    }
    private TreeNode commonAncestor(TreeNode ancestor, int nodeAData, int nodeBData) {
        boolean aOnLeft = covers(nodeAData, ancestor.leftChild);
        boolean bOnleft = covers(nodeBData, ancestor.leftChild);
        //System.out.println("aOnLeft " + aOnLeft + " bOnleft " + bOnleft);

        if (aOnLeft != bOnleft)
            return ancestor;
        return aOnLeft ? commonAncestor(ancestor.leftChild, nodeAData, nodeBData) :
                         commonAncestor(ancestor.rightChild, nodeAData, nodeBData);


    }
    public boolean isAncestor (TreeNode ancestor, TreeNode child) {
        TreeNode node = ancestor;
        return covers(child.data, ancestor.leftChild) | covers(child.data, ancestor.rightChild);
    }

    public boolean getPathFromNode (ArrayList<Integer> path, TreeNode src, TreeNode dest) {
        if (src == null)
            return false;

        if (src.data == dest.data) {
            path.add(dest.data);
            return true;
        }
        if ( getPathFromNode(path,src.leftChild, dest) ||
                getPathFromNode(path, src.rightChild, dest)) {
            path.add(src.data);
            return true;
        } else {
            return false;
        }
    }
    /*
    *  This will give the common ancestor of two nodes using the getPathFromNode method
    *  If we can get the path from root to nodeA and nodeB then the point where the path diverges
    *  is the first common ancestor
    * */
    public TreeNode getCommonAncestor(TreeNode nodeA, TreeNode nodeB) {

        if (nodeA == null || nodeB == null)
            return getRoot();

        TreeNode ancestor = null;
        ArrayList<Integer> pathNodeA = new ArrayList<>();
        ArrayList<Integer> pathNodeB = new ArrayList<>();

        if (getPathFromNode(pathNodeA , getRoot(), nodeA) &&
                getPathFromNode(pathNodeB, getRoot(), nodeB)) {

            int i = pathNodeA.size() - 1;
            int j = pathNodeB.size() - 1 ;
            while (i>= 0 && j >= 0 &&
                    (pathNodeA.get(i) == pathNodeB.get(j))) {
                i--;
                j--;
            }
            ancestor = search(pathNodeA.get(i+1),getRoot());
        } else {
            System.out.println("One of the node is not in the tree");
        }
        return ancestor;
    }
}
