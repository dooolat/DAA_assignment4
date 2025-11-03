package graph.dagsp;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DAGPathTest {

    @Test
    public void testShortestPath() {
        DAGShortestPath g = new DAGShortestPath(6);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 6);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 2);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, -1);
        g.addEdge(4, 5, -2);

        int[] dist = g.shortestPaths(1);
        assertEquals(0, dist[1]);
        assertTrue(dist[5] < 10);
    }

    @Test
    public void testLongestPath() {
        DAGLongestPath g = new DAGLongestPath(6);
        g.addEdge(0, 1, 5);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 6);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 2);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, -1);
        g.addEdge(4, 5, -2);

        int[] dist = g.longestPaths(0);
        assertEquals(5, dist[1]);
        assertTrue(dist[5] > 0);
    }

    @Test
    public void testPathReconstruction() {
        DAGPathReconstruction g = new DAGPathReconstruction(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 3);

        DAGPathReconstruction.Result result = g.findPaths(0, false);
        List<Integer> path = g.reconstructPath(result.parent, 4);

        assertEquals(List.of(0, 2, 3, 4), path);
        assertTrue(result.dist[4] < 10);
    }
}
