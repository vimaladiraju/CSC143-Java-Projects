public static int checkBalance(String code) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < code.length(); i++) {
        char ch = code.charAt(i);
        if (ch == '(' || ch == '{') {
            stack.push(ch);
        }

        else if (ch == ')' || ch == '}') {
            if (stack.isEmpty()) {
                return i;
            }
            char top = stack.pop();
            if ((ch == ')' && top != '(') || (ch == '}' && top != '{')) {
                return i;
            }   
        }
    }
    if (!stack.isEmpty()) {
        return code.length();
    }
    return -1;
}

