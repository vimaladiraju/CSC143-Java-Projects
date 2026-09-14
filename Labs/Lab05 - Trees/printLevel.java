public void printLevel(TreeNode root, int n) {
    if (n < 1) {
        throw new IllegalArgumentException("Level must be at least 1.");
    }
    if (root == null) {
        return;
    }
    if (n == 1) {
        System.out.println(root.data);
    } else {
        printLevel(root.left, n-1);
        printLevel(root.right, n-1);
    }

}

