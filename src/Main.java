import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger cntOf_3 = new AtomicInteger();
    public static AtomicInteger cntOf_4 = new AtomicInteger();
    public static AtomicInteger cntOf_5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


        Thread palindrom = new Thread(() -> {
            for (String text : texts){
                if (isPalindrom(text)){changeCount(text.length());}
            }
        });
        palindrom.start();

        Thread allSame = new Thread(() -> {
            for (String text : texts){
                if (isAllSame(text)){changeCount(text.length());}
            }
        });
        allSame.start();

        Thread increasing = new Thread(() -> {
            for (String text : texts){
                if (isIncreasing(text)) {changeCount(text.length());}}
        });
        increasing.start();

        palindrom.join();
        allSame.join();
        increasing.join();

        System.out.println("Красивых слов длинной 3: " + cntOf_3 + " шт");
        System.out.println("Красивых слов длинной 4: " + cntOf_4 + " шт");
        System.out.println("Красивых слов длинной 5: " + cntOf_5 + " шт");

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void changeCount(int wordLength){
        if (wordLength == 3){cntOf_3.getAndIncrement();}
        if (wordLength == 4){cntOf_4.getAndIncrement();}
        if (wordLength == 5){cntOf_5.getAndIncrement();}
    }

    public static boolean isPalindrom(String word){
        return word.equals(new StringBuilder(word).reverse().toString());
    }

    public static boolean isAllSame(String word){
        for (int i = 0; i < word.length()-1; i++){
            if (word.charAt(i) != word.charAt(i+1)){ return false; }
        }
        return true;
    }

    public static boolean isIncreasing(String word){
        for (int i = 0; i < word.length()-1; i++){
            if (word.charAt(i) > word.charAt(i+1)){ return false; }
        }
        return true;
    }
}