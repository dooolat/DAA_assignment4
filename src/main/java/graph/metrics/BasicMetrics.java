package graph.metrics;

import java.util.*;

/**
 * Basic implementation of Metrics interface.
 * Stores operation counters and timing.
 */
public class BasicMetrics implements Metrics {

    private final Map<String, Long> counters = new HashMap<>();
    private long startTime;
    private long elapsed;

    @Override
    public void startTimer() {
        startTime = System.nanoTime();
    }

    @Override
    public void stopTimer() {
        elapsed = System.nanoTime() - startTime;
    }

    @Override
    public long getElapsedTime() {
        return elapsed;
    }

    @Override
    public void increment(String key) {
        counters.put(key, counters.getOrDefault(key, 0L) + 1);
    }

    @Override
    public long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    @Override
    public void reset() {
        counters.clear();
        elapsed = 0;
    }

    @Override
    public void print() {
        System.out.println("----- Metrics -----");
        counters.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("Execution time: " + (elapsed / 1_000_000.0) + " ms");
    }

    // Example usage
    public static void main(String[] args) throws InterruptedException {
        BasicMetrics m = new BasicMetrics();
        m.startTimer();

        for (int i = 0; i < 1000000; i++) {
            m.increment("dfs_calls");
        }

        Thread.sleep(100);
        m.stopTimer();

        m.print();
    }
}
