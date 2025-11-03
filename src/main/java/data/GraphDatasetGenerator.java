package data;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Generates random directed graph datasets for SCC, TopoSort, and DAGSP.
 */
public class GraphDatasetGenerator {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Random rand = new Random();

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class GraphData {
        boolean directed = true;
        int n;
        List<Edge> edges;
        String type;
        String weight_model = "edge";
    }

    public static void main(String[] args) throws IOException {
        generateGraphs("data/small", 6, 10, 3);
        generateGraphs("data/medium", 10, 20, 3);
        generateGraphs("data/large", 20, 50, 3);
        System.out.println(" Datasets successfully generated!");
    }

    private static void generateGraphs(String folder, int minNodes, int maxNodes, int count) throws IOException {
        Files.createDirectories(Paths.get(folder));

        for (int i = 1; i <= count; i++) {
            int n = rand.nextInt(maxNodes - minNodes + 1) + minNodes;
            List<Edge> edges = new ArrayList<>();

            int density = rand.nextInt(40) + 30; // 30–70% chance of edge per pair
            boolean cyclic = rand.nextBoolean();

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (u != v && rand.nextInt(100) < density) {
                        int w = rand.nextInt(9) + 1;
                        edges.add(new Edge(u, v, w));
                    }
                }
            }

            GraphData graph = new GraphData();
            graph.n = n;
            graph.edges = edges;
            graph.type = cyclic ? "cyclic" : "DAG";

            String fileName = String.format("%s/graph_%d.json", folder, i);
            try (FileWriter writer = new FileWriter(fileName)) {
                gson.toJson(graph, writer);
            }

            System.out.printf("Generated %s (%d vertices, %d edges, %s)%n",
                    fileName, n, edges.size(), graph.type);
        }
    }
}
