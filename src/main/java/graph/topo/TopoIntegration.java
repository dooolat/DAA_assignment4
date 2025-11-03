package graph.topo;

import graph.scc.CondensationGraph;
import graph.scc.TarjanSCC;
import java.util.*;

public class TopoIntegration {

    public static void main(String[] args) {
        // Example graph
        int n = 8;
        TarjanSCC scc = new TarjanSCC(n);
        scc.addEdge(0, 1);
        scc.addEdge(1, 2);
        scc.addEdge(2, 0);
        scc.addEdge(3, 4);
        scc.addEdge(4, 5);
        scc.addEdge(5, 3);
        scc.addEdge(2, 3);
        scc.addEdge(5, 6);
        scc.addEdge(6, 7);

        // Find SCCs
        List<List<Integer>> components = scc.getSCCs();
        System.out.println("SCCs:");
        for (int i = 0; i < components.size(); i++)
            System.out.println("Component " + i + ": " + components.get(i));

        // Build adjacency for the original graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);
        adj.get(3).add(4);
        adj.get(4).add(5);
        adj.get(5).add(3);
        adj.get(2).add(3);
        adj.get(5).add(6);
        adj.get(6).add(7);

        // Build condensed graph
        CondensationGraph cg = new CondensationGraph(components, adj);
        List<List<Integer>> condensedAdj = cg.getCondensedAdj();
        cg.printCondensation();

        // Perform Topological Sort on condensation DAG
        List<Integer> topoOrder = TopologicalSort.kahnSort(condensedAdj);

        System.out.println("\nTopological order of SCC components:");
        System.out.println(topoOrder);
    }
}
