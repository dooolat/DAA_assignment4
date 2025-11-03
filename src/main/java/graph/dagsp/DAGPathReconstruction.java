package graph.dagsp;

import java.util.*;

public class DAGPathReconstruction {

    static class Edge {
        int to, weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    private final int n;
    private final List<List<Edge>> adj;

    public DAGPathReconstruction(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    // Finds shortest or longest paths depending on "maximize" flag
    public Result findPaths(int source, boolean maximize) {
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        if (maximize) Arrays.fill(dist, Integer.MIN_VALUE);
        else Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        List<Integer> topo = topologicalSort();

        for (int u : topo) {
            if ((maximize && dist[u] != Integer.MIN_VALUE) || (!maximize && dist[u] != Integer.MAX_VALUE)) {
                for (Edge e : adj.get(u)) {
                    int newDist = dist[u] + e.weight;
                    if (maximize && newDist > dist[e.to]) {
                        dist[e.to] = newDist;
                        parent[e.to] = u;
                    } else if (!maximize && newDist < dist[e.to]) {
                        dist[e.to] = newDist;
                        parent[e.to] = u;
                    }
                }
            }
        }

        return new Result(dist, parent);
    }

    private List<Integer> topologicalSort() {
        int[] indegree = new int[n];
        for (List<Edge> edges : adj)
            for (Edge e : edges)
                indegree[e.to]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (Edge e : adj.get(u)) {
                indegree[e.to]--;
                if (indegree[e.to] == 0) q.add(e.to);
            }
        }
        return order;
    }

    public List<Integer> reconstructPath(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = parent[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }

    public static class Result {
        public final int[] dist;
        public final int[] parent;
        public Result(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    public static void main(String[] args) {
        DAGPathReconstruction g = new DAGPathReconstruction(7);
        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 6);
        g.addEdge(3, 5, 2);
        g.addEdge(4, 6, 1);
        g.addEdge(5, 6, 3);

        // Shortest path from node 0
        Result shortest = g.findPaths(0, false);
        g.printResults("Shortest", shortest.dist, shortest.parent, 6);

        // Longest path from node 0
        Result longest = g.findPaths(0, true);
        g.printResults("Longest", longest.dist, longest.parent, 6);
    }

    public void printResults(String type, int[] dist, int[] parent, int target) {
        System.out.println("\n" + type + " path distances from source:");
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE || dist[i] == Integer.MIN_VALUE)
                System.out.println(i + ": unreachable");
            else
                System.out.println(i + ": " + dist[i]);
        }
        System.out.println(type + " path to target " + target + ": " + reconstructPath(parent, target));
    }
}
