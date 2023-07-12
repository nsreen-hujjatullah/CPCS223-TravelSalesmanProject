/*
=================================
CPCS-223-Project Coding
=================================
Tsp Using Brute Force Algoraithm 
=================================
The code is from the next web
Traveling Salesman Problem (TSP) Implementation. (2017, November 11). GeeksforGeeks.
https://www.geeksforgeeks.org/traveling-salesman-problem-tsp-implementation/
=================================
--------Group Member---------
--------Section B2A ---------

 */
package tspusingbruteforcealgoraithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TspUsingBruteForceAlgoraithm {

    /**
     * @param args the command line arguments
     */
    //=============================================================================
    public static void main(String[] args) {
        
        int[][] arrayDistances = genratedistancesCites(n, 10, 100);
        
        int s = 0;// start node
        long startTime, endTime;
        startTime = System.nanoTime(); // start time 
        System.out.println("\nThe optimal cost is: " + travllingSalesmanProblem(arrayDistances, s));
        endTime = System.nanoTime(); // end time 
        printADJ(arrayDistances); // call method print Adjacency matrix
        System.out.println("-----------------------------------");
        System.out.println("Time taken :" + ((endTime - startTime) / 1000000) + " Millisecond");// print the time
        System.out.println("-----------------------------------");
    }
//=============================================================================
    //method to genrate cites distances randomly

    public static int[][] genratedistancesCites(int n, int min, int max) {
        int distances[][] = new int[n][n];
        Random rand = new Random(100);//genrate randomly
        //for loop to genrate randomly distances to store distances in 2d array 
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
    static int n = input.nextInt(); // Store the number of cities we have requested from the user in a variable n
    static int V = n;
//=============================================================================
    // implementation of traveling
    // Salesman Problem

    static int travllingSalesmanProblem(int graph[][], int s) {
        // store all vertex apart
        // from source vertex
        ArrayList<Integer> vertex = new ArrayList<Integer>();
        for (int i = 0; i < V; i++) {
            if (i != s) {
                vertex.add(i);
            }
        }
        // store minimum weight
        int min_path = Integer.MAX_VALUE;
        // array to save the value of optimal path 
        int[] g = new int[V];
        int[] g1 = new int[V];
        int[] d = new int[V];
        int[] d1 = new int[V];
        do {
            // store current Path weight(cost)
            int current_pathweight = 0;
            // compute current path weight
            int k = s; //start point
            System.out.print(s + "->");
            for (int i = 0; i < vertex.size(); i++) { //use the 1d array to cal all possible paths distances
                //here we calculate the path between start node and next point 
                current_pathweight += graph[k][vertex.get(i)];//use 2d array to find the distance btw the start and other cities
                k = vertex.get(i);
                System.out.print(k + "->");//this print the node
                g[i]= k;// save value of k to array 
                d[i]=k; // save value of k to array  
                
            }
            //here we calculate the path between start node and final point 
            current_pathweight += graph[k][s];
            //here in the start we print s which we should end with 
            System.out.println(s + "  The cost for this path = " + current_pathweight);  
            //save the optimal path nodes
            if (current_pathweight < min_path) {
                // update minimum
                min_path = Math.min(min_path, current_pathweight);
                //save the optimal path nodes
                System.arraycopy(g, s, g1, s, vertex.size() - s);
            }else if (current_pathweight == min_path){    
                System.arraycopy(d, s, d1, s, vertex.size() - s);
            }

        } while (findNextPermutation(vertex));
        
        // print the optimal path
        System.out.println("-----------------------------------");
        System.out.println("The optimal path : ");
        System.out.print(s + "->");
        for (int i = s; i < vertex.size(); i++)//use the 1d array to cal all possible paths distances
        {
            System.out.print(g1[i] + "->");
        }
        System.out.print(s);
        System.out.println("");
        System.out.print(s + "->");
        for (int i = s; i < vertex.size(); i++)//use the 1d array to cal all possible paths distances
        {
            System.out.print(d1[i] + "->");
        }
        System.out.print(s);

        return min_path; // return the cost of min_path
    }
//=============================================================================
// Function to swap the data
// present in the left and right indices

    public static ArrayList<Integer> 
        swap(ArrayList<Integer> data, int left, int right) {
        // Swap the data
        int temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);
        // Return the updated array
        return data;
    }
//=============================================================================
// Function to reverse the sub-array
// starting from left to the right
// both inclusive

    public static ArrayList<Integer> 
        reverse(ArrayList<Integer> data, int left, int right) {
        // Reverse the sub-array
        while (left < right) {
            int temp = data.get(left);
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }
        // Return the updated array
        return data;
    }
//=============================================================================
// Function to find the next permutation
// of the given integer array

    public static boolean findNextPermutation(ArrayList<Integer> data) {
        // If the given dataset is empty
        // or contains only one element
        // next_permutation is not possible
        if (data.size() <= 1) {
            return false;
        }
        int last = data.size() - 2;
        // find the longest non-increasing suffix and find the pivot
        while (last >= 0) {
            if (data.get(last) < data.get(last + 1)) {
                break;
            }
            last--;
        }
        // If there is no increasing pair there is no higher order permutation
        if (last < 0) {
            return false;
        }
        int nextGreater = data.size() - 1;
        // Find the rightmost successor to the pivot
        for (int i = data.size() - 1; i > last; i--) {
            if (data.get(i) > data.get(last)) {
                nextGreater = i;
                break;
            }
        }
        // Swap the successor and the pivot
        data = swap(data, nextGreater, last);
        // Reverse the suffix
        data = reverse(data, last + 1, data.size() - 1);
        // Return true as the next_permutation is done
        return true;
    }
//=============================================================================
    // method to print Adjacency matrix

    public static void printADJ(int[][] adj) {
        System.out.println("-----------------------------------");
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
