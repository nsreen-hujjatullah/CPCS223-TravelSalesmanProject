# CPCS223-TravelSalesmanProject
In this report, we will discuss The Traveling Salesman Problem (TSP). It is an 
algorithmic problem that seeks the shortest non-overlapping path through all points in each list 
while visiting each point exactly once. It is an NP-hard problem, meaning that the time required 
to solve the problem increases rapidly as the number of points increases. 
Therefore, we will empirically analyze the effectiveness of two algorithms (Traveling 
salesman using Brute Force Algorithm & TSP using Branch and Bound Algorithm) to find a path 
at the lowest cost to solve the problem of the shipment delivery representative which is our 
problem in this project. 
The Brute Force Algorithm considers all possible permutations of the cities and thus 
guarantees the shortest route. However, it is very inefficient, as the number of permutations 
increases exponentially as the number of cities increases. For this reason, it is not suitable for 
large problems. The steps for solving the TSP using this algorithm are as follows:

(i) Generate the graph for all cities.

(ii) Calculate the distance for all cities.

(iii) Create all possible permutations for all cities.

(iv) Calculate the total distance for each permutation.

(v) Finally, select the permutation with the shortest total distance.

In the Branch and Bound Algorithm, this algorithm divides the problem to be solved into 
several sub-problems. To solve the TSP using the Branch and Bound algorithm by following this step:

(i) select a start node.

(ii) set the bound to a very large value (say, infinity).

(iii) choose the shortest path between the unvisited and current nodes. 

(iv) add the distance to the current distance. 

When the current distance is less than the bound, we have successfully completed the algorithm.
