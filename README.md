# DAA Assignment 4
Algorithms:
- Strongly Connected Components (Tarjan / Kosaraju)
- Topological Sorting (Kahn / DFS)
- Shortest & Longest Paths in DAG

This repository contains implementation, datasets, and analysis for Assignment 4.

## 📊 Dataset Documentation

All graph datasets used in this project are stored under the `/data/` directory and are generated automatically using the `GraphDatasetGenerator` class.

### 🗂️ Structure

data/
- small/ # Simple cases (6–10 nodes), 1–2 cycles or pure DAG
- medium/ # Mixed structures (10–20 nodes), multiple SCCs
- large/ # Performance and timing tests (20–50 nodes)


### 📘 Description
| Category | Nodes (n) | Description | Variants | Type Examples |
|-----------|------------|--------------|-----------|----------------|
| Small     | 6–10       | Simple graphs, few edges | 3 | DAG / Cyclic |
| Medium    | 10–20      | Mixed structures, several SCCs | 3 | Cyclic |
| Large     | 20–50      | Stress test graphs, timing analysis | 3 | Mixed |

Each generated JSON file includes:
```json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2}
  ],
  "weight_model": "edge",
  "type": "DAG"
}
```
🧠 Notes
- Edge weights are randomly assigned between 1 and 9.
- Some datasets include cycles to test SCC compression.
- Use these datasets for testing TarjanSCC, TopologicalSort, and DAGShortestPath algorithms.