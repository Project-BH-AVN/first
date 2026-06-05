package utils;

import java.util.Scanner;

public class Base {
    static Scanner scanner=new Scanner(System.in);

    public static void pl(Object o) {
        System.out.println(o);
    }
    public static void p(Object o) {
        System.out.print(o);
    }

    public static int checkYN() {
    p("(y/n): ");  

    char c = Character.toLowerCase(scanner.next().charAt(0));

    if (c == 'y') return 0;
    if (c == 'n') return 1;
    return -1;
}

public static boolean isNumeric(String str) {
    if (str == null) return false;
    try {
        Double.parseDouble(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
}
