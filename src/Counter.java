import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Counter implements Runnable {

    private static final Map<Integer, Integer> sizeToFreq = new HashMap<>();//заполняю мапу по условию
    private static int maxCount = 1;//ищу максимальное количество повторений
    private static int key;//сохраняю ключу от максимального количества повторений
    private final String letters;
    private final int length;

    public Counter(String letters, int length) {
        this.letters = letters;
        this.length = length;
    }

    public static int getKey() {
        return key;
    }

    public static int getMaxCount() {
        return maxCount;
    }

    public static Map<Integer, Integer> getSizeToFreq() {
        return sizeToFreq;
    }


    @Override
    public void run() {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        String str = route.toString();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'R') {
                count++;
            }
        }
        synchronized (sizeToFreq) {//Ограничиваю доступ к мапе в момент работы с ней
            if (sizeToFreq.containsKey(count)) {
                int temp = sizeToFreq.get(count);
                sizeToFreq.put(count, temp + 1);
                if (temp > maxCount) {//поля maxCount и key не нужно синхронизировать,
                    //чтобы работать с ними нужно иметь доступ к мапе, а она синхронизирована
                    maxCount = temp;
                    key = count;
                }
            } else {
                sizeToFreq.put(count, 1);
            }
        }
    }
}
