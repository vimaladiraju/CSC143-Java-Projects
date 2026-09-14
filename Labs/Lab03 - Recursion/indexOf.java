public static int indexOf(String s1, String s2) {
    if (s2.isEmpty()) return 0;
    if (s1.length() < s2.length()) return -1;
    if (s1.startsWith(s2)) return 0;

    int result = indexOf(s1.substring(1), s2);
    if (result != -1) return 1 + result;
    return -1;
}


