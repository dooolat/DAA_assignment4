package graph.metrics;

/**
 * Common interface for collecting algorithmic performance metrics.
 * Used by SCC, TopoSort, and DAG algorithms.
 */
public interface Metrics {

    void startTimer();
    void stopTimer();
    long getElapsedTime(); // in nanoseconds

    void increment(String key);
    long getCount(String key);

    void reset();
    void print();
}
