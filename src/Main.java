import java.util.*;

public class Main {

    public static final List<Thread> threads = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {

        //Запускаю 1000 потоков и кладу их в список потоков
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Counter("RLRFR", 100));//реализовал интерфейс Runnable
            thread.start();
            threads.add(thread);
        }
        //Жду когда потоки завершат работу, чтобы забрать конечную мапу
        for (Thread thread : threads) {
            thread.join();
        }
        //Получаю мапу и нужные мне максимсальные значения
        Map<Integer, Integer> map = Counter.getSizeToFreq();
        System.out.println("Самое частое количество повторений " + Counter.getKey() + " (встретилось " + Counter.getMaxCount() + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> kv : map.entrySet())
            if (kv.getKey() != Counter.getKey())
                System.out.println("- " + kv.getKey() + "(" + kv.getValue() + " раз)");

    }
}
