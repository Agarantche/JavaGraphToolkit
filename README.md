# Java Graph Algorithm Toolkit

This command-line Java application provides a toolkit for analyzing weighted undirected graphs. It can load graph data from a specified file format and allows users to perform various fundamental graph algorithms. The project serves as a practical implementation of key graph theory concepts.

## Features

* **Graph Loading:** Loads graph structures (nodes and weighted edges) from a text file.
* **Connectivity Check:** Determines if the graph is connected using Depth First Search (DFS).
* **Minimum Spanning Tree (MST):** Calculates and displays the MST of a connected graph using Prim's algorithm.
* **Shortest Paths:** Finds and displays the shortest paths from a user-specified source node to all other nodes using Dijkstra's algorithm.
* **Metric Property Check:** Verifies if the graph adheres to the triangle inequality (is a metric graph).
* **Make Metric:** Transforms a connected graph into its metric completion using an all-pairs shortest path approach (conceptually similar to Floyd-Warshall).
* **Traveling Salesman Problem (TSP) Approximation:** Provides an approximate solution to the TSP using a nearest neighbor heuristic for connected, metric graphs.
* **Interactive CLI:** Allows users to choose which algorithm/operation to perform on the loaded graph.

## Technologies Used

* **Java:** Core programming language.
* **Data Structures:** Adjacency Matrix for graph representation, arrays, and helper data structures for algorithms.
* **Algorithms:** Depth First Search (DFS), Prim's Algorithm, Dijkstra's Algorithm, Nearest Neighbor Heuristic.
* **File I/O:** `java.io.File` and `java.util.Scanner` for reading graph data from files.
* **Command-Line Interface (CLI):** Interaction managed via `java.util.Scanner`.

## How to Compile and Run

1.  **Prerequisites:**
    * Java Development Kit (JDK) installed (e.g., JDK 8 or later).

2.  **Download Files:**
    * Ensure you have all necessary `.java` files in a single directory:
        * `Graph.java` (Contains the graph logic and algorithms)
        * `Graphs.java` (Contains the `main` method and CLI interaction)
    * You will also need a graph data file (e.g., `graph.txt`) in the format expected by the `Graph(String fileName)` constructor. The format typically starts with the number of nodes, followed by lines for each node specifying the number of edges and then pairs of (targetNode weight).

3.  **Compile:**
    * Open a terminal or command prompt.
    * Navigate to the directory where you saved the `.java` files.
    * Compile the Java files:
        ```bash
        javac Graph.java Graphs.java
        ```
        (Or `javac *.java` if they are the only Java files)

4.  **Run:**
    * After successful compilation, run the main class (`Graphs.java`):
        ```bash
        java Graphs
        ```
    * The program will then prompt you for the name of your graph file. Enter the filename (e.g., `graph.txt`) and press Enter.
    * Follow the on-screen menu to perform operations.

## Input File Format (Example)

The program expects a graph file in a specific format. Here's a conceptual example:

<number_of_nodes><edges_for_node_0> <target_node_1> <weight_1> <target_node_2> <weight_2> ...<edges_for_node_1> <target_node_1> <weight_1> <target_node_2> <weight_2> ......
For example:
32 1 10 2 51 2 20This represents a graph with 3 nodes (0, 1, 2).
* Node 0 has 2 edges: to node 1 (weight 10) and to node 2 (weight 5).
* Node 1 has 1 edge: to node 2 (weight 2). (Note: the constructor assumes undirected, so 0-1 is also 1-0).
* Node 2 has 0 explicitly listed outgoing edges in this format (but is connected via edges from 0 and 1).

## Project Structure

* `Graph.java`: Contains the core graph representation, algorithm implementations, and file parsing logic.
* `Graphs.java`: Provides the command-line user interface and drives the program.

---

*Developed by Adam Garantche.
