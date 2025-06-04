import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class represents a weighted undirected graph.
 * It provides methods for constructing the graph from a file,
 * checking connectivity, finding the minimum spanning tree,
 * finding shortest paths, checking if the graph is metric,
 * making the graph metric, and solving the Traveling Salesman Problem (TSP).
 */
public class Graph {
    private int[][] edges; // Adjacency matrix to store edge weights
    private int nodes; // Number of nodes in the graph

    /**
     * Constructor to initialize an empty graph with a given number of nodes.
     *
     * @param nodes The number of nodes in the graph.
     */
    public Graph(int nodes) {
        this.nodes = nodes;
        this.edges = new int[nodes][nodes];
    }

    /**
     * Constructor to build a graph from a file.
     *
     * @param fileName The name of the file containing the graph data.
     * @throws FileNotFoundException If the specified file is not found.
     */
    public Graph(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        nodes = in.nextInt();
        edges = new int[nodes][nodes];
        for (int i = 0; i < nodes; i++) {
            int edgeCount = in.nextInt();
            for (int j = 0; j < edgeCount; j++) {
                int targetNode = in.nextInt();
                int weight = in.nextInt();
                edges[i][targetNode] = weight;
                edges[targetNode][i] = weight; // Assuming undirected graph
            }
        }
        in.close();
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return The number of nodes.
     */
    public int getNodes() {
        return nodes;
    }

    /**
     * Checks if the graph is connected using Depth First Search (DFS).
     *
     * @return True if the graph is connected, false otherwise.
     */
    public boolean isConnected() {
        int[] visited = new int[nodes + 1];
        visited[nodes] = 1;
        dfs(0, visited);
        for (int i = 0; i < nodes; ++i) {
            if (visited[i] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs Depth First Search (DFS) to traverse the graph.
     *
     * @param currentNode The current node being visited.
     * @param visited An array to keep track of visited nodes.
     */
    private void dfs(int currentNode, int[] visited) {
        visited[currentNode] = 1;
        for (int i = 0; i < nodes; i++) {
            if (edges[currentNode][i] != 0 && visited[i] == 0) {
                dfs(i, visited);
            }
        }
    }

    /**
     * Computes and prints the Minimum Spanning Tree (MST) of the graph using Prim's algorithm.
     */
    public void minimumSpanningTree() {
        if (!isConnected()) {
            System.out.println("Error: Graph is not connected.");
            return;
        }

        boolean[] visited = new boolean[nodes];
        int[] minEdge = new int[nodes];
        int[] parent = new int[nodes];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        minEdge[0] = 0;

        for (int count = 0; count < nodes - 1; count++) {
            int u = findMinEdge(minEdge, visited);
            visited[u] = true;

            for (int v = 0; v < nodes; v++) {
                if (edges[u][v] != 0 && !visited[v] && edges[u][v] < minEdge[v]) {
                    parent[v] = u;
                    minEdge[v] = edges[u][v];
                }
            }
        }

        printMST(parent);
    }

    /**
     * Finds the vertex with the minimum edge weight among the unvisited vertices.
     *
     * @param minEdge An array storing the minimum edge weights.
     * @param visited An array indicating whether a vertex has been visited.
     * @return The index of the vertex with the minimum edge weight.
     */
    private int findMinEdge(int[] minEdge, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < nodes; v++) {
            if (!visited[v] && minEdge[v] < min) {
                min = minEdge[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    /**
     * Prints the Minimum Spanning Tree (MST) in the required format.
     *
     * @param parent An array representing the parent nodes in the MST.
     */
    private void printMST(int[] parent) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodes).append("\n");

        int[] edgeCount = new int[nodes];
        for (int i = 1; i < nodes; i++) {
            edgeCount[parent[i]]++;
        }

        for (int i = 0; i < nodes; i++) {
            sb.append(edgeCount[i]).append(" ");
            for (int j = 0; j < nodes; j++) {
                if (parent[j] == i) {
                    sb.append(j).append(" ").append(edges[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    /**
     * Computes and prints the shortest paths from a given starting node to all other nodes
     * using Dijkstra's algorithm.
     *
     * @param start The starting node for finding shortest paths.
     */
    public void findShortestPaths(int start) {
        int[] distances = new int[nodes];
        int[] previous = new int[nodes];
        boolean[] visited = new boolean[nodes];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[start] = 0;

        for (int count = 0; count < nodes - 1; count++) {
            int closestNode = -1;
            int closestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < nodes; i++) {
                if (!visited[i] && distances[i] < closestDistance) {
                    closestNode = i;
                    closestDistance = distances[i];
                }
            }
            visited[closestNode] = true;

            for (int i = 0; i < nodes; ++i) {
                if (edges[closestNode][i] != 0 && !visited[i]) {
                    int newDistance = distances[closestNode] + edges[closestNode][i];
                    if (newDistance < distances[i]) {
                        distances[i] = newDistance;
                        previous[i] = closestNode;
                    }
                }
            }
        }

        // Print the results
        for (int i = 0; i < nodes; i++) {
            StringBuilder path = new StringBuilder();
            for (int at = i; at != -1; at = previous[at]) {
                path.insert(0, at + (path.length() > 0 ? " -> " : ""));
            }
            System.out.println(i + ": (" + (distances[i] == Integer.MAX_VALUE ? "Infinity" : distances[i]) + ")\t" + path);
        }
    }

    /**
     * Checks if the graph satisfies the triangle inequality property of a metric space.
     *
     * @return True if the graph is metric, false otherwise.
     */
    public boolean isMetric() {
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                for (int k = 0; k < nodes; k++) {
                    if (edges[i][j] != Integer.MAX_VALUE &&
                            edges[j][k] != Integer.MAX_VALUE &&
                            edges[i][k] != Integer.MAX_VALUE) {

                        if (edges[i][k] > edges[i][j] + edges[j][k]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Transforms the graph into a metric graph by applying the Floyd-Warshall algorithm.
     *
     * @throws IllegalStateException if the graph is not connected.
     */
    public void makeMetric() {
        if (!isConnected()) {
            throw new IllegalStateException("Graph is not connected.");
        }

        for (int k = 0; k < nodes; ++k) {
            for (int i = 0; i < nodes; ++i) {
                for (int j = 0; j < nodes; ++j) {
                    if (edges[i][k] != 0 && edges[k][j] != 0) {
                        int newDistance = edges[i][k] + edges[k][j];
                        if (edges[i][j] == 0 || newDistance < edges[i][j]) {
                            edges[i][j] = newDistance;
                        }
                    }
                }
            }
        }

        // Print the modified graph
        for (int i = 0; i < nodes; i++) {
            int edgeCount = 0;
            for (int j = 0; j < nodes; j++) {
                if (edges[i][j] != 0) {
                    edgeCount++;
                }
            }
            System.out.print(edgeCount + " ");
            for (int j = 0; j < nodes; j++) {
                if (edges[i][j] != 0) {
                    System.out.print(j + " " + edges[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks if the graph has a Traveling Salesman Problem (TSP)
     * (i.e., if it is connected and metric).
     *
     * @return True if the graph has a TSP, false otherwise.
     */
    public boolean hasTravelingSalesmanProblem() {
        return isConnected() && isMetric();
    }

    /**
     * Approximates the solution to the Traveling Salesman Problem (TSP)
     * using a nearest neighbor heuristic.
     *
     * @return A string representing the approximate TSP tour and its length.
     */
    public String approximateTSP() {
        if (!isConnected()) {
            System.out.println("Error: Graph is not connected.");
            return "";
        }

        int[] tour = new int[nodes];
        boolean[] visited = new boolean[nodes];
        int currentCity = 0;
        tour[0] = currentCity;
        visited[currentCity] = true;

        int tourLength = 0;

        for (int i = 1; i < nodes; i++) {
            int nextCity = findNearestNeighbor(currentCity, visited);
            tourLength += edges[currentCity][nextCity];
            tour[i] = nextCity;
            visited[nextCity] = true;
            currentCity = nextCity;
        }

        tourLength += edges[currentCity][tour[0]];

        return tourLength + ": " + formatTour(tour);
    }

    /**
     * Finds the nearest unvisited neighbor of a given city.
     *
     * @param currentCity The current city in the TSP tour.
     * @param visited An array indicating whether a city has been visited.
     * @return The index of the nearest unvisited neighbor.
     */
    private int findNearestNeighbor(int currentCity, boolean[] visited) {
        int nearestNeighbor = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < nodes; i++) {
            if (!visited[i] && edges[currentCity][i] != 0 && edges[currentCity][i] < minDistance) {
                minDistance = edges[currentCity][i];
                nearestNeighbor = i;
            }
        }

        return nearestNeighbor;
    }

    /**
     * Formats the TSP tour as a string.
     *
     * @param tour An array representing the TSP tour.
     * @return A formatted string representation of the tour.
     */
    private String formatTour(int[] tour) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tour.length; i++) {
            sb.append(tour[i]);
            if (i < tour.length - 1) {
                sb.append(" -> ");
            }
        }
        sb.append(" -> ").append(tour[0]);
        return sb.toString();
    }
}