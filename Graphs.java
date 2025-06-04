import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
/**
 * @Author: Adam Garantche
 *
 *
 * This class provides a user interface for interacting with a graph.
 * It allows users to load a graph from a file and perform various operations on it,
 * such as checking connectivity, finding the minimum spanning tree,
 * finding the shortest paths, checking if the graph is metric,
 * making the graph metric, and solving the Traveling Salesman Problem (TSP).
 */
public class Graphs {

    /**
     * Main method that runs the graph interface.
     *
     * @param args Command line arguments (not used).
     * @throws FileNotFoundException If the specified graph file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("What's the name of your graph file?: ");
        String input = scanner.next();
        Graph graph = new Graph(input);


        while (true) {
            // Display menu options
            System.out.println("1. Is Connected");
            System.out.println("2. Minimum Spanning Tree");
            System.out.println("3. Shortest Path");
            System.out.println("4. Is Metric");
            System.out.println("5. Make Metric");
            System.out.println("6. Traveling Salesman Problem");
            System.out.println("7. Approximate TSP");
            System.out.println("8. Quit");
            System.out.print("Make your choice (1 - 8): ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Check if the graph is connected
                    System.out.println(graph.isConnected() ? "Graph is connected." : "Graph is not connected.");
                    break;
                case 2:
                    // Find the minimum spanning tree if the graph is connected
                    if (graph.isConnected()) {
                        graph.minimumSpanningTree();
                    } else {
                        System.out.println("Error: Graph is not connected.");
                    }
                    break;
                case 3:
                    // Find the shortest paths from a specified starting node
                    System.out.print("From which node would you like to find the shortest paths (0 - " + (graph.getNodes() - 1) + "): ");
                    int startNode = scanner.nextInt();
                    graph.findShortestPaths(startNode);
                    break;
                case 4:
                    // Check if the graph is metric
                    System.out.println(graph.isMetric() ? "The Graph is metric." : "Graph is not metric.");
                    break;
                case 5:
                    // Make the graph metric
                    try {
                        graph.makeMetric();
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    // This seems like a duplicate of case 7.  It should probably be removed.
                    System.out.println(graph.approximateTSP());

                    break;
                case 7:
                    // Approximate the solution to the Traveling Salesman Problem
                    if (graph.hasTravelingSalesmanProblem()) {
                        System.out.println("TSP Approximate tour: " + graph.approximateTSP());
                    } else {
                        System.out.println("Error: Graph is not metric. ");
                    }
                    break;
                case 8:
                    // Exit the program
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
            }
        }
    }
}