package graph.topo;

import graph.scc.CondensationGraph;
import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSortTest {

    @Test
    public void testSimpleDAG() {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) adj.add(new ArrayList<>());

        adj.get(5).add(2);
        adj.get(5).add(0);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        List<Integer> order = TopologicalSort.kahnSort(adj);

        // Verify topological order property
        assertTrue(isValidTopologicalOrder(adj, order));
    }

    @Test
    public void testTopoOnCondensationGraph() {
        // Build a cyclic graph with SCCs
        TarjanSCC scc = new TarjanSCC(6);
        scc.addEdge(0, 1);
        scc.addEdge(1, 2);
        scc.addEdge(2, 0); // SCC1
        scc.addEdge(3, 4);
        scc.addEdge(4, 5);
        scc.addEdge(5, 3); // SCC2
        scc.addEdge(2, 3); // edge between SCC1 -> SCC2

        List<List<Integer>> components = scc.getSCCs();

        // Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);
        adj.get(3).add(4);
        adj.get(4).add(5);
        adj.get(5).add(3);
        adj.get(2).add(3);

        // Build condensation graph
        CondensationGraph cg = new CondensationGraph(components, adj);
        List<List<Integer>> condensedAdj = cg.getCondensedAdj();

        List<Integer> order = TopologicalSort.kahnSort(condensedAdj);

        assertEquals(2, order.size());
        assertTrue(isValidTopologicalOrder(condensedAdj, order));
    }

    private boolean isValidTopologicalOrder(List<List<Integer>> adj, List<Integer> order) {
        int n = adj.size();
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[order.get(i)] = i;
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                if (pos[u] > pos[v]) return false;
            }
        }
        return true;
    }
}