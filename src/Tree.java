import java.util.*;

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
        root.left = createBinaryTreeRecurse(0, mid-1, arr);
        root.right = createBinaryTreeRecurse(mid+1, arr.length-1,arr);

    }

    public TreeNode createBSTree (int low , int high, int arr []){
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.left = createBSTree(low, mid-1, arr);
        node.right = createBSTree(mid+1, high, arr);
        return node;
    }

    private TreeNode createBinaryTreeRecurse (int low, int high, int arr []) {
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.left = createBinaryTreeRecurse(low, mid-1, arr);
        node.right = createBinaryTreeRecurse(mid+1, high, arr);
        return node;
    }

    public void inOrderTraversalRec (TreeNode node) {
        if ( node != null) {
            inOrderTraversalRec(node.left);
            System.out.print(node.data + " ");
            inOrderTraversalRec(node.right);
        }
    }

    public void preOrderTraversalRec (TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderTraversalRec(node.left);
            preOrderTraversalRec(node.right);
        }
    }

    public void postOrderTraversalRec(TreeNode node) {
        if (node != null) {
            postOrderTraversalRec(node.left);
            postOrderTraversalRec(node.right);
            System.out.print(node.data + " ");
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
            return search(data, node.left);
        } else {
            return search(data, node.right);
        }
    }

    public TreeNode searchBiggest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.right == null)
            return node;
        return searchBiggest(node.right);

    }

    public TreeNode searchSmallest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null)
            return node;
        return searchSmallest(node.left);

    }

    public TreeNode inOrderPredecessorWithoutParent (TreeNode node) {
        if (node.left != null) {
            return searchBiggest(node.left);
        }
        TreeNode pred = null;
        TreeNode rt = root;

        while (rt != null) {
            if (node.data < rt.data) {
                rt = rt.left;
            } else if (node.data > rt.data) {
                pred = rt;
                rt = rt.right;
            } else {
                break;
            }
        }
        return pred;
    }

    public TreeNode inOrderSuccesorWithParent (TreeNode node) {
        if (node.right != null) {
            return searchSmallest(node.right);
        }
        TreeNode pnode = node.parent;
        while(pnode != null && pnode.right == node) {
            pnode = pnode.parent;
            node = node.parent;
        }
        return pnode;
    }
    public TreeNode inOrderSuccessorWithoutParent (TreeNode node) {
        TreeNode rt = root;
        TreeNode succ = null;

        if (node.right != null) {
            return searchSmallest(node.right);
        }

        while (rt != null) {
            if (node.data < rt.data) {
                succ = rt;
                rt = rt.left;
            } else if (node.data > rt.data) {
                rt = rt.right;
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
                if (node.left != null)
                    list.add(node.left);
                if (node.right != null)
                    list.add(node.right);
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
            return covers(data, node.left) | covers(data, node.right);
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
        boolean aOnLeft = covers(nodeAData, ancestor.left);
        boolean bOnleft = covers(nodeBData, ancestor.left);
        //System.out.println("aOnLeft " + aOnLeft + " bOnleft " + bOnleft);

        if (aOnLeft != bOnleft)
            return ancestor;
        return aOnLeft ? commonAncestor(ancestor.left, nodeAData, nodeBData) :
                         commonAncestor(ancestor.right, nodeAData, nodeBData);


    }
    public boolean isAncestor (TreeNode ancestor, TreeNode child) {
        TreeNode node = ancestor;
        return covers(child.data, ancestor.left) | covers(child.data, ancestor.right);
    }

    public boolean getPathFromNode (ArrayList<Integer> path, TreeNode src, TreeNode dest) {
        if (src == null)
            return false;

        if (src.data == dest.data) {
            path.add(dest.data);
            return true;
        }
        if ( getPathFromNode(path,src.left, dest) ||
                getPathFromNode(path, src.right, dest)) {
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

    // LeetCode :: 94. Binary Tree Inorder Traversal
    // The basic idea is to use a stack to do the traversal, We dont need a complicated stack push pop.
    // Just consider the inroder traversal order we need to keep pushing to stack when there is a left child
    // if there is no more left child we pop & push the right child in a iterative manner
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        // we start with a empty stack but node is not null so we will push the first node anyways
        while (!stack.isEmpty() || node != null){
            // if the node is not null we push this node
            if (node != null) {
                stack.push(node);
                // move to the left node so that we can push it next time
                node = node.left;
            } else {
                // no more left sub tree we visit the node & move node to right for next push
                node = stack.pop();
                rList.add(node.data);
                node = node.right;
            }
        }

        return rList;
    }

    // LeetCode :: 144. Binary Tree Preorder Traversal
    // use a stack visit node first then stack push the right child before the left child
    // check version2 its easy to read; but same as this one
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            if(node != null) {
                rList.add(node.data);
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (node.right != null)
                    node = node.right;
                else
                    node = null;
            }
        }
        return rList;
    }
    // version2 same logic as before but easy to read
    public List<Integer> preorderTraversalV2(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = null;
        // push the root node
        stack.push(root);
        while (!stack.isEmpty()) {
            // preoder visit the node
            node = stack.pop();
            rList.add(node.data);
            // node lets put the right child on the stack first then the left child so that we visit right subtree after
            // we have visited the left subtree, because we are suing stack we use
            // this reverse order of right child before left child
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        return rList;
    }

    // LeetCode :: 145. Binary Tree Postorder Traversal (Hard)
    // The postorder traversal is similar to iterative inorder or preorder traversal
    // The only difference is we have to keep track if the right subtree has been visited
    // for the current node if yes then we can pop this node from stack and visit current node
    // otherwise we have not visited the right substree so move node to the right to put the right
    // substree in the stack
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastNode = null;
        TreeNode node = root;
        int count = 0;

        while (!stack.isEmpty() || node != null) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                // check if right subtree has been visited, if yes lets visit current node
                if (node.right == null ||
                        node.right == lastNode) {
                    lastNode = stack.pop();
                    rList.add(lastNode.data);
                    // this node has been visited, lets make it null so that we can process
                    // the next node from the stack
                    node = null;
                } else {
                    // right subtree not visited lets go to right to stack right subtree
                    node = node.right;
                }
            }
        }
        return rList;
    }
    // LeetCode :: 590. N-ary Tree Postorder Traversal
    // The nary post order traversal is easy because we have the list of node children.
    // At each step we take a node and put its children into the stack, and put the node in a list.
    // The children are popped from the stack in LIFO so the rightmost child will get pooped first,
    // so we will in next iteration process the right child's subtree this allow as us to visit the
    // subtrees of children from right to left. We add node in the list in the same order so to get our
    // result we just need to revere the fina list
    public List<Integer> postorder(Node root) {
        List<Integer> rList = new ArrayList<>();
        if (root == null)
            return rList;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        stack.push(node);
        while (!stack.empty()) {
            node = stack.pop();
            rList.add(node.val);
            // add children into the stack left to right so
            // when popped they will be popped right to left
            for (Node n : node.children) {
                stack.push(n);
            }
        }
        // we have the result in reverse order so lets fix it
        Collections.reverse(rList);
        return rList;
    }

    // LeetCode :: 589. N-ary Tree Preorder Traversal
    // The same approach as the Postorder traversal, only difference is adding the children
    // into the stack in right to left order so when we pop them the come out as left to right.
    // This allows traversing the children list from left to right as needed by preorder
    public List<Integer> preorder(Node root) {
        List<Integer> rList = new ArrayList<>();
        if (root == null)
            return rList;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        stack.push(node);
        while (!stack.empty()) {
            node = stack.pop();
            rList.add(node.val);
            int i = node.children.size() - 1;
            // add the children from right to left so that
            // we can traverse the tree from left to right
            for (;i>= 0;i--) {
                stack.push(node.children.get(i));
            }
        }
        return rList;
    }





}
