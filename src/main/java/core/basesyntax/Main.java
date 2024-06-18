package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final int POOL_SIZE = 5;
    private static final int NUMBER_OF_THREADS = 20;
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        List<Future<String>> futures = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int taskDuration = 100 + random.nextInt(400);
            MyThread task = new MyThread(taskDuration);
            Future<String> future = executorService.submit(task);
            futures.add(future);
        }

        for (Future<String> future: futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
