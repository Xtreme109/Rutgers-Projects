public class LetterCount {
    

    public static int count(String str) {

        int TotalLetters = 0;

        for (int i = 0; i<str.length(); i++) {
            if (str.charAt(i) == 'a') {
                TotalLetters++;
            }
        }
        System.out.print(TotalLetters);
        return TotalLetters;
    }

    

    public static void main(String[] args) {
     count("graaaah");
    }
}
