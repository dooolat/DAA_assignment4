package graph.dagsp;

import java.util.*;

public class DAGLongestPath {

    static class Edge {
        int to, weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    private final int n;
    private final List<List<Edge>> adj;

    public DAGLongestPath(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public int[] longestPaths(int source) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[source] = 0;

        List<Integer> topoOrder = topologicalSort();

        for (int u : topoOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge e : adj.get(u)) {
                    if (dist[e.to] < dist[u] + e.weight) {
                        dist[e.to] = dist[u] + e.weight;
                    }
                }
            }
        }
        return dist;
    }

    private List<Integer> topologicalSort() {
        int[] indegree = new int[n];
        for (List<Edge> edges : adj)
            for (Edge e : edges)
                indegree[e.to]++;

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indegree[i] == 0)
                queue.add(i);

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (Edge e : adj.get(u)) {
                indegree[e.to]--;
                if (indegree[e.to] == 0)
                    queue.add(e.to);
            }
        }
        return order;
    }

    public void printLongestPath(int[] dist) {
        System.out.println("Longest path distances from source:");
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MIN_VALUE)
                System.out.println(i + ": unreachable");
            else
                System.out.println(i + ": " + dist[i]);
        }
    }

    // пример запуска
    public static void main(String[] args) {
        DAGLongestPath g = new DAGLongestPath(6);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 6);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 2);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, -1);
        g.addEdge(4, 5, -2);

        int[] dist = g.longestPaths(1);
        g.printLongestPath(dist);
    }
}
