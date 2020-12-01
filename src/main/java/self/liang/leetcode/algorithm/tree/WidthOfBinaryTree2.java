package self.liang.leetcode.algorithm.tree;

import java.util.Deque;
import java.util.LinkedList;

public class WidthOfBinaryTree2 {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);
        widthOfBinaryTree(root);
    }

    // 最先想到的就应该是层序遍历
//    std::queue<TreeNode *> q;
//    TreeNode *front;
//
//    if (root == NULL)return;
//
//    q.push(root);
//
//    while (!q.empty())
//    {
//        front = q.front();
//        q.pop();
//
//        if (front->left)
//            q.push(front->left);
//
//        if (front->right)
//            q.push(front->right);
//
//        printf("%c ", front->data);
//    }
//  在层序遍历的基础上，获取当前层的宽度
    public static int widthOfBinaryTree(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode front;
        if (root == null) return 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            front = queue.poll();
            if (front.left != null) queue.add(front.left);
            if (front.right != null) queue.add(front.right);
            System.out.println(front.val);
        }
        return 0;
    }


    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
