package graph.scc;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCTest {

    @Test
    public void testSimpleSCC() {
        TarjanSCC scc = new TarjanSCC(5);
        scc.addEdge(0, 1);
        scc.addEdge(1, 2);
        scc.addEdge(2, 0);
        scc.addEdge(1, 3);
        scc.addEdge(3, 4);

        List<List<Integer>> result = scc.getSCCs();
        assertEquals(3, scc.getSccCount());
    }

    @Test
    public void testCondensationGraph() {
        TarjanSCC scc = new TarjanSCC(5);
        scc.addEdge(0, 1);
        scc.addEdge(1, 2);
        scc.addEdge(2, 0);
        scc.addEdge(1, 3);
        scc.addEdge(3, 4);

        List<List<Integer>> components = scc.getSCCs();

        // Build adjacency for original graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 5; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);
        adj.get(1).add(3);
        adj.get(3).add(4);

        CondensationGraph cg = new CondensationGraph(components, adj);
        List<List<Integer>> condensed = cg.getCondensedAdj();

        assertTrue(condensed.size() > 0);
        cg.printCondensation();
    }
}
