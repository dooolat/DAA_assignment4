package graph.scc;

import java.util.*;

public class TarjanSCC {
    private final int n;
    private final List<List<Integer>> adj;
    private final int[] ids, low;
    private final boolean[] onStack;
    private final Deque<Integer> stack;
    private int id = 0, sccCount = 0;
    private final List<List<Integer>> components = new ArrayList<>();

    public TarjanSCC(int n) {
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        ids = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        Arrays.fill(ids, -1);
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    public List<List<Integer>> getSCCs() {
        for (int i = 0; i < n; i++) {
            if (ids[i] == -1) dfs(i);
        }
        return components;
    }

    private void dfs(int at) {
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = id++;

        for (int to : adj.get(at)) {
            if (ids[to] == -1) dfs(to);
            if (onStack[to]) low[at] = Math.min(low[at], low[to]);
        }

        // Start of SCC
        if (ids[at] == low[at]) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                component.add(node);
                low[node] = ids[at];
                if (node == at) break;
            }
            components.add(component);
            sccCount++;
        }
    }

    public int getSccCount() {
        return sccCount;
    }
}
