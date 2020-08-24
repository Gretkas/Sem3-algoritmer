package oving1;

public class Palindrom {

    public static void main(String[] args) {
        System.out.println(isPalindrom("tut"));
        System.out.println(isPalindrom("Tut"));
        System.out.println(isPalindrom("Heei"));
        System.out.println(isPalindrom("madam"));
        System.out.println(isPalindrom("Redder"));
    }

    public static boolean isPalindrom(String word) {
        StringBuffer s = new StringBuffer(word.toLowerCase());
        return checkPalindrom(s, 0, s.length());
    }

    private static boolean checkPalindrom(StringBuffer s, int progress,
                                          int length) {
        int backProgress = length-1-progress;
        if (progress < length/2) {
            if (s.charAt(backProgress) != s.charAt(progress)) return false;
            checkPalindrom(s, ++progress, length);
        }
        return true;
    }
}