package integration;

import com.google.gson.*;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPath;

import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for SCC → TopoSort → DAGShortestPath pipeline.
 */
public class GraphPipelineTest {

    static class Edge {
        int u, v, w;
    }

    static class GraphData {
        boolean directed;
        int n;
        List<Edge> edges;
        int source;
        String weight_model;
    }

    @Test
    public void testFullPipeline() throws Exception {
        Path filePath = Paths.get("data/small/graph_1.json");
        assertTrue(Files.exists(filePath), "Dataset file not found!");

        // Load dataset
        Gson gson = new Gson();
        GraphData data;
        try (FileReader reader = new FileReader(filePath.toFile())) {
            data = gson.fromJson(reader, GraphData.class);
        }

        // Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < data.n; i++) adj.add(new ArrayList<>());
        for (Edge e : data.edges) adj.get(e.u).add(e.v);

        // Step 1: SCC
        TarjanSCC scc = new TarjanSCC(data.n);
        for (Edge e : data.edges) scc.addEdge(e.u, e.v);
        var components = scc.getSCCs();
        assertNotNull(components);
        System.out.println("SCC count: " + scc.getSccCount());

        // Step 2: Topological Sort
        List<Integer> topoOrder = TopologicalSort.kahnSort(adj);
        assertEquals(data.n, topoOrder.size(), "Topo sort size mismatch!");
        System.out.println("Topological order: " + topoOrder);

        // Step 3: DAG Shortest Path
        DAGShortestPath dag = new DAGShortestPath(data.n);
        for (Edge e : data.edges) dag.addEdge(e.u, e.v, e.w);
        int source = data.source >= 0 && data.source < data.n ? data.source : 0;
        int[] dist = dag.shortestPaths(source);
        assertNotNull(dist);
        System.out.println("Distances from source " + source + ": " + Arrays.toString(dist));

        // Simple check: at least one reachable node
        boolean reachable = Arrays.stream(dist).anyMatch(d -> d != Integer.MAX_VALUE);
        assertTrue(reachable, "No reachable nodes found — check dataset connectivity!");
    }
}
