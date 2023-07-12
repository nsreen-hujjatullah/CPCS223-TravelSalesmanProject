/*
==============================
CPCS-223-Project Coding
==============================
Tsp Using Branch And Bound 
==============================
The code is from the next web
Traveling Salesman Problem using Branch And Bound. (2016, October 13). GeeksforGeeks.
https://www.geeksforgeeks.org/traveling-salesman-problem-using-branch-and-bound-2/
==============================
--------Group Member---------
--------Section B2A ---------

 */
package tspusingbranchandbound;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TspUsingBranchAndBound {

    /**
     * @param args the command line arguments
     */
    //=============================================================================
    public static void main(String[] args) {
        
        int[][] adj = genratedistancesCites(c, 10, 100);
        
        System.out.println("     All paths with they costs     ");
        System.out.println("-----------------------------------");
        long startTime, endTime;
        startTime = System.nanoTime(); // start time 
        TSP(adj);
        endTime = System.nanoTime(); // end time 
        System.out.println("-----------------------------------");
        System.out.printf("Minimum cost : %d\n", final_res); // print the minimum cost
        System.out.printf("Path Taken : "); // print the path taken
        for (int i = 0; i <= N; i++) {
            System.out.printf("%d ", final_path[i]);
        }
        endTime = System.nanoTime(); // end time 
        printADJ(adj);// call method print Adjacency matrix
        System.out.println("-----------------------------------");
        System.out.println("Time taken :" + ((endTime - startTime) / 1000000) + " Millisecond");// print the time 
        System.out.println("-----------------------------------");
    }
//=============================================================================
    //method to genrate cites distances randomly

    public static int[][] genratedistancesCites(int n, int min, int max) {
        int distances[][] = new int[n][n];
        Random rand = new Random(100); //genrate randomly
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                //genrate cites distances randomly
                int value = Math.abs((min + rand.nextInt((max - min) + 1)));
                distances[i][j] = value;
                distances[j][i] = value;
            }
        }
        return distances;
    }
//=============================================================================
    // Define a scanner to ask the user to enter the number of cities
    static Scanner input = new Scanner(System.in);

    static {
        System.out.print("Enter the number of cities :");// Ask the user to enter the number of cities
    }
    static int c = input.nextInt(); // Store the number of cities we have requested from the user in a variable c

    static {
        System.out.println("-----------------------------------");
    }
    static int N = c;
    // final_path[] stores the final solution ie, the
    // path of the salesman.
    static int final_path[] = new int[N + 1];
    // visited[] keeps track of the already visited nodes
    // in a particular path
    static boolean visited[] = new boolean[N];
    // Stores the final minimum weight of shortest tour.
    static int final_res = Integer.MAX_VALUE;
//=============================================================================
    // Function to copy temporary solution to
    // the final solution

    static void copyToFinal(int curr_path[]) {
        for (int i = 0; i < N; i++) {
            final_path[i] = curr_path[i];
        }
        final_path[N] = curr_path[0];
    }
//=============================================================================
    // Function to find the minimum edge cost
    // having an end at the vertex i

    static int firstMin(int adj[][], int i) {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++) {
            if (adj[i][k] < min && i != k) {
                min = adj[i][k];
            }
        }
        return min;
    }
//=============================================================================
    // function to find the second minimum edge cost
    // having an end at the vertex i

    public static int secondMin(int adj[][], int i) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j) {
                continue;
            }
            if (adj[i][j] <= first) {
                second = first;
                first = adj[i][j];
            } else if (adj[i][j] <= second && adj[i][j] != first) {
                second = adj[i][j];
            }
        }
        return second;
    }
//=============================================================================
    // function that takes as arguments:
    // curr_bound -> lower bound of the root node
    // curr_weight-> stores the weight of the path so far
    // level-> current level while moving in the search
    //         space tree
    // curr_path[] -> where the solution is being stored which
    //             would later be copied to final_path[]

    public static void TSPRec(int adj[][], int curr_bound, int curr_weight,
            int level, int curr_path[]) {
        // base case is when we have reached level N which means we have covered all the nodes once
        if (level == N) {
            // check if there is an edge from last vertex in path back to the first vertex
            if (adj[curr_path[level - 1]][curr_path[0]] != 0) {
                // curr_res has the total weight of the solution we got
                int curr_res = curr_weight + adj[curr_path[level - 1]][curr_path[0]];
                // Update final result and final path if current result is better.
                if (curr_res < final_res) {
                    copyToFinal(curr_path);
                    for (int i = 0; i <= N; i++) {
                        // print the paths
                        System.out.printf("%d ", curr_path[i]);
                        curr_path[N] = curr_path[0];
                    }
                    System.out.println("");
                    final_res = curr_res;
                    System.out.println("Cost this path = " + curr_res);
                }
            }
            return;
        }
        // for any other level iterate for all vertices to build the search space tree recursively
        for (int i = 0; i < N; i++) {
            // Consider next vertex if it is not same (diagonal entry in adjacency matrix and not visited already)
            if (adj[curr_path[level - 1]][i] != 0 && visited[i] == false) {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];
                // different computation of curr_bound for level 2 from the other levels
                if (level == 1) {
                    curr_bound -= ((firstMin(adj, curr_path[level - 1]) + firstMin(adj, i)) / 2);
                } else {
                    curr_bound -= ((secondMin(adj, curr_path[level - 1]) + firstMin(adj, i)) / 2);
                }
                // curr_bound + curr_weight is the actual lower bound for the node that we have arrived on
                // If current lower bound < final_res, we need to explore the node further
                if (curr_bound + curr_weight < final_res) {
                    curr_path[level] = i;
                    visited[i] = true;
                    // call TSPRec for the next level
                    TSPRec(adj, curr_bound, curr_weight, level + 1, curr_path);
                }
                // Else we have to prune the node by resetting
                // all changes to curr_weight and curr_bound
                curr_weight -= adj[curr_path[level - 1]][i];
                curr_bound = temp;
                // Also reset the visited array
                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++) {
                    visited[curr_path[j]] = true;
                }
            }
        }
    }
//=============================================================================
    // This function sets up final_path[]

    public static void TSP(int adj[][]) {
        int curr_path[] = new int[N + 1];
        // Calculate initial lower bound for the root node
        // using the formula 1/2 * (sum of first min + second min) for all edges.
        // Also initialize the curr_path and visited array
        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);
        // Compute initial bound
        for (int i = 0; i < N; i++) {
            curr_bound += (firstMin(adj, i) + secondMin(adj, i));
        }
        // Rounding off the lower bound to an integer
        curr_bound = (curr_bound == 1) ? curr_bound / 2 + 1 : curr_bound / 2;
        // We start at vertex 1 so the first vertex
        // in curr_path[] is 0
        visited[0] = true;
        curr_path[0] = 0;
        // Call to TSPRec for curr_weight equal to
        // 0 and level 1
        TSPRec(adj, curr_bound, 0, 1, curr_path);
    }
//=============================================================================
    // method to print Adjacency matrix

    public static void printADJ(int[][] adj) {
        System.out.println("\n-----------------------------------");
        System.out.println("Adjacency matrix ");
        System.out.println("-----------------------------------");
        for (int i = 0; i < adj.length; i++) {
            for (int j = 0; j < adj[i].length; j++) {
                System.out.print(adj[i][j] + "   ");
            }
            System.out.println();
        }
    }
}
