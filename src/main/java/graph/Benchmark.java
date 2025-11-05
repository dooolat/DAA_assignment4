package graph;

import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPath;
import java.util.*;

public class Benchmark {
    public static void main(String[] args) {
        System.out.println("===== Benchmark: Graph Algorithms =====");

        // 8 vertex
        runSCC(8, 12);
        runTopo(8, 12);
        runDAGSP(8, 12);

        // 20 vertex
        runSCC(20, 40);
        runTopo(20, 40);
        runDAGSP(20, 40);

        // 50 vertex
        runSCC(50, 150);
        runTopo(50, 150);
        runDAGSP(50, 150);
    }

    private static void runSCC(int n, int edges) {
        TarjanSCC scc = new TarjanSCC(n);
        Random rand = new Random();
        for (int i = 0; i < edges; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            scc.addEdge(u, v);
        }

        long start = System.nanoTime();
        var components = scc.getSCCs();
        long end = System.nanoTime();
        System.out.printf("SCC | Nodes: %d | Edges: %d | Components: %d | Time: %.3f ms%n",
                n, edges, components.size(), (end - start) / 1e6);
    }

    private static void runTopo(int n, int edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        Random rand = new Random();
        for (int i = 0; i < edges; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            if (u != v) adj.get(u).add(v);
        }

        long start = System.nanoTime();
        try {
            TopologicalSort.kahnSort(adj);
        } catch (IllegalStateException ignored) {}
        long end = System.nanoTime();
        System.out.printf("Topo | Nodes: %d | Edges: %d | Time: %.3f ms%n",
                n, edges, (end - start) / 1e6);
    }

    private static void runDAGSP(int n, int edges) {
        DAGShortestPath dag = new DAGShortestPath(n);
        Random rand = new Random();
        for (int i = 0; i < edges; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            int w = rand.nextInt(10) + 1;
            if (u != v) dag.addEdge(u, v, w);
        }

        long start = System.nanoTime();
        int[] dist = dag.shortestPaths(0);
        long end = System.nanoTime();

        System.out.printf("DAGSP | Nodes: %d | Edges: %d | Time: %.3f ms%n",
                n, edges, (end - start) / 1e6);
    }
}
