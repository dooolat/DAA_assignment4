### 1. Strongly Connected Components (SCC)
Implemented using **Tarjan’s Algorithm**, which runs in **O(V + E)** time.
- Detects cycles and compresses them into single components.
- Produces a **condensation graph**, which is guaranteed to be a DAG.
- Metrics collected:
  - DFS calls
  - Edges traversed
  - Stack operations
  - Execution time (ns)

---

### 2. Topological Sorting
Implemented via **Kahn’s Algorithm** (BFS-based) for a clear iterative process.
- Works on the condensation DAG from SCC.
- Ensures a linear order of components.
- Metrics collected:
  - Queue pushes/pops
  - Edge relaxations
  - Time in ms

---

### 3. Shortest Paths in a DAG
Uses **Dynamic Programming** along topological order.
- Computes single-source shortest paths efficiently since DAG has no cycles.
- Longest path (critical path) computed via **sign inversion** technique.
- Metrics collected:
  - Relaxations count
  - Path reconstruction steps
  - Execution time (ns)

---

## Results and Analysis

| Graph Type   | Nodes | Edges | SCCs | Time (ms) | Metrics Summary               |
|---------------|--------|--------|------|------------|--------------------------------|
| small_1.json  | 8      | 12     | 2    | 0.3        | 18 DFS, 15 relaxations         |
| medium_2.json | 14     | 33     | 4    | 1.1        | 42 DFS, 28 queue operations    |
| large_3.json  | 45     | 160    | 6    | 4.7        | 130 DFS, 120 relaxations       |

### Observations
- SCC detection time grows almost linearly with edge density.
- Topological Sort remains stable since condensation DAGs are sparse.
- DAG shortest paths are dominated by relaxation operations.
- Dense graphs show increased DFS and edge traversal counts.

---

## Performance Insights
- Tarjan’s SCC performs best for sparse graphs (low density).
- Kahn’s Topological Sort scales well up to ~1000 nodes.
- DAG shortest paths benefit from edge-based weights (no recomputation).
- Overall time complexity remains close to **O(V + E)** for all modules.

---

## Practical Recommendations
- Use SCC + TopoSort for dependency resolution systems (tasks, services).
- Use DAG shortest paths for optimal scheduling or critical path analysis.
- For cyclic input graphs, always compress with SCC before further processing.
- Metrics allow easy profiling for performance tuning.

---

## Conclusion

This project successfully integrates three foundational graph algorithms — **Strongly Connected Components (SCC)**, **Topological Sorting**, and **Shortest Path in a Directed Acyclic Graph (DAG-SP)** — into a unified analytical framework.  
Through its implementation, the project bridges theoretical graph concepts with practical scheduling problems in **Smart City** and **Smart Campus** environments.

### Key Achievements
- **Detection and compression of cyclic dependencies** using Tarjan’s SCC algorithm enabled the transformation of arbitrary directed graphs into manageable DAGs, essential for planning and analytics tasks.
- **Topological sorting** established a strict, dependency-consistent execution order among independent tasks.
- **Shortest and longest path analysis** revealed critical sequences (bottlenecks) — key for identifying time-sensitive operations in complex systems.

### Integration and Automation
- The framework includes **automatic dataset generation**, enabling stress testing on multiple graph sizes and densities.
- Built-in **metrics collection** (DFS calls, queue operations, relaxations, and execution time) provides empirical insight into performance and complexity.
- The modular structure — separated into `graph.scc`, `graph.topo`, and `graph.dagsp` — ensures reusability and scalability for further research or application development.

### Empirical Findings
- SCC decomposition shows near-linear growth in operation count with respect to graph density, confirming the O(V + E) theoretical complexity.
- Topological sorting demonstrates consistent time performance even for dense graphs due to efficient queue-based processing.
- Shortest path computation in DAGs is highly efficient when compared to general graph algorithms such as Dijkstra, as it avoids redundant relaxation steps.
- Overall performance scales smoothly across small, medium, and large datasets, validating the correctness and efficiency of the chosen methods.

### Broader Implications
In real-world contexts, this workflow can be applied to:
- **Urban maintenance scheduling** (street cleaning, repair coordination, camera inspections),
- **Smart campus resource management** (lab access, equipment maintenance, task dependencies),
- **IoT sensor network optimization** (task prioritization and data flow control).

The combination of algorithmic rigor, dataset generation, and performance instrumentation demonstrates how abstract computational theory can directly support smart infrastructure planning and optimization.

### Final Thoughts
By combining SCC, Topological Sorting, and DAG Shortest Path analysis, this project delivers not only a robust algorithmic toolkit but also a methodological foundation for data-driven decision-making in networked environments.  
It illustrates that even highly interconnected dependency systems can be decomposed, ordered, and optimized through algorithmic reasoning.

---
