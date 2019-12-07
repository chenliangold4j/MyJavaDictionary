package self.liang.leetcode.algorithm.tree;

import java.util.Stack;

/**
 * 给定一个二叉树，原地将它展开为链表。
 *
 * 例如，给定二叉树
 *
 *     1
 *    / \
 *   2   5
 *  / \   \
 * 3   4   6
 * 将其展开为：
 *
 * 1
 *  \
 *   2
 *    \
 *     3
 *      \
 *       4
 *        \
 *         5
 *          \
 *           6
 *
 */
public class TreeMedium1 {


    /**
     * 问题是怎么想到这个思路：
     *      还是看了答案：
     *          应该是从叶子节点开始。
     *          将某个节点的左子树和右子树都转为 只有右节点的子树
     *          然后在将他们拼接成链表。
     */
    public void flatten(TreeNode root) {

        //核心逻辑是从叶子开始转链表
        if(root == null)return; //到了空节点返回。之后处理的都是有左右节点的节点。
        flatten(root.left);
        flatten(root.right);

        //最简单的,假设只有节点及其左右节点
//        TreeNode temporary = root.right;
//        root.right = root.left;
//        root.left = null;
//        root.right.right = temporary;

        //第二步，既然是左右子树先转再拼接，，典型的后序遍历。那么递归的结果，要处理节点的左右子树都会
        //右偏。只需要处理左子树转右之后的有多个右节点的情况。
        //这里可以有两种方式：一种把左子树接到右子树的尾巴，或者是把右子树接到左子树的尾巴。
        //答案是第二种，我这边用第一种。

        //虽然是链表了，但是和题目顺序不符合。提交的还是答案。但是不用顺序的的话，这个代码还少一行。
        TreeNode temporary = root.left;
        root.left = null;
        while(root.right != null) root = root.right;
        root.right = temporary;
    }


    private void method1(TreeNode root){
        if(root == null) return;
        method1(root.left);
        method1(root.right);
        TreeNode tmp = root.right;
        root.right = root.left;
        root.left = null;
        while(root.right != null) root = root.right;
        root.right = tmp;
    }

    public void method2(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }

            if (!stack.isEmpty()){
                TreeNode node = stack.pop();
                TreeNode tmp = node.right;
                node.right = node.left;
                node.left = null;

                while(node.right != null) node = node.right;
                node.right = tmp;
                root = tmp;
            }
        }
    }




}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}