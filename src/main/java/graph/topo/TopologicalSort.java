package graph.topo;

import java.util.*;
import graph.metrics.BasicMetrics;

public class TopologicalSort {

    // Returns a list of vertices in topological order using Kahn's algorithm
    public static List<Integer> kahnSort(List<List<Integer>> adj) {
        BasicMetrics metrics = new BasicMetrics();
        metrics.startTimer();

        int n = adj.size();
        int[] indegree = new int[n];
        for (List<Integer> edges : adj) {
            for (int v : edges) {
                indegree[v]++;
                metrics.increment("indegree_count");
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                metrics.increment("queue_push");
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            metrics.increment("queue_pop");
            order.add(u);

            for (int v : adj.get(u)) {
                indegree[v]--;
                metrics.increment("edge_relax");
                if (indegree[v] == 0) {
                    queue.offer(v);
                    metrics.increment("queue_push");
                }
            }
        }

        metrics.stopTimer();
        metrics.print();

        if (order.size() != n)
            throw new IllegalStateException("Graph has cycles — topological sort not possible");

        return order;
    }
}
