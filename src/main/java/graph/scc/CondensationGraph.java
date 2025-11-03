package graph.scc;

import java.util.*;

public class CondensationGraph {
    private final List<List<Integer>> sccs;
    private final List<List<Integer>> adj;
    private final int[] nodeToComponent;
    private final List<Set<Integer>> condensedAdj;

    public CondensationGraph(List<List<Integer>> sccs, List<List<Integer>> adj) {
        this.sccs = sccs;
        this.adj = adj;
        this.nodeToComponent = new int[adj.size()];
        this.condensedAdj = new ArrayList<>();

        buildMapping();
        buildCondensedGraph();
    }

    private void buildMapping() {
        for (int i = 0; i < sccs.size(); i++) {
            for (int node : sccs.get(i)) {
                nodeToComponent[node] = i;
            }
        }
    }

    private void buildCondensedGraph() {
        int n = sccs.size();
        for (int i = 0; i < n; i++) condensedAdj.add(new HashSet<>());

        for (int u = 0; u < adj.size(); u++) {
            int compU = nodeToComponent[u];
            for (int v : adj.get(u)) {
                int compV = nodeToComponent[v];
                if (compU != compV) {
                    condensedAdj.get(compU).add(compV);
                }
            }
        }
    }

    public List<List<Integer>> getCondensedAdj() {
        List<List<Integer>> result = new ArrayList<>();
        for (Set<Integer> set : condensedAdj) {
            result.add(new ArrayList<>(set));
        }
        return result;
    }

    public void printCondensation() {
        System.out.println("Condensation DAG:");
        for (int i = 0; i < condensedAdj.size(); i++) {
            System.out.println("Component " + i + " -> " + condensedAdj.get(i));
        }
    }

    public int[] getNodeToComponent() {
        return nodeToComponent;
    }
}