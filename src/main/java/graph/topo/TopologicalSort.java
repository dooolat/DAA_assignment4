package graph.topo;

import java.util.*;

public class TopologicalSort {

    // Returns a list of vertices in topological order using Kahn's algorithm
    public static List<Integer> kahnSort(List<List<Integer>> adj) {
        int n = adj.size();
        int[] indegree = new int[n];
        for (List<Integer> edges : adj)
            for (int v : edges)
                indegree[v]++;

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indegree[i] == 0) queue.offer(i);

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (int v : adj.get(u)) {
                indegree[v]--;
                if (indegree[v] == 0)
                    queue.offer(v);
            }
        }

        if (order.size() != n)
            throw new IllegalStateException("Graph has cycles — topological sort not possible");

        return order;
    }
}
