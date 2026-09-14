public static int digitSum(int n) {
    if (n < 0) {
        return digitSum(-n) * -1;
    }

    if (n < 10) {
        return n;
    }

    return (n % 10) + digitSum(n / 10);

}
