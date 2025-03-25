package Graph;

import java.util.ArrayList;

public class Dijkstra
{
    int[][] g;
    int s;
    boolean[] visited;
    String[] paths;
    ArrayList<Solution> solutions = new ArrayList<>();

    public Dijkstra(int[][] graph , int source, boolean sign)
    {
        if (check(graph, source))
        {
            this.g = new int[graph.length][graph.length];
            if (sign)
            {
                for (int i = 0; i < graph.length; i++)
                {
                    System.arraycopy(graph[i],0,g[i],0,graph.length);
                }
            }
            else
            {
                g = graph;
            }
            this.s = source;
            this.visited = new boolean[graph.length];
            this.paths = new String[graph.length];
            this.paths[source] = String.valueOf(source);
            for (int i=0; i<paths.length;i++)
            {
                update(source,i);
            }
            addToSolution(source);
            while (!allNodesIn())
            {
                int small = getSmallest();
                addToSolution(small);
                for (int i = 0; i < g.length; i++)
                {
                    if (!visited[i] && (getSum(g[source][small], g[small][i])) < g[source][i])
                    {
                        update(small,i);
                    }
                }
            }
        }
        else
        {
            throw new RuntimeException("Entered Parameters are not correct.");
        }
    }
    public static boolean check(int[][] graph, int source)
    {
        int rowLength = graph[0].length;
        int currRowLength;
        for (int i = 1; i < graph.length; i++)
        {
            currRowLength = graph[i].length;
            if (rowLength != currRowLength || currRowLength != graph.length)
            {
                return false;
            }
        }
        return(source < graph.length && source >= 0);
    }

    private boolean allNodesIn()
    {
        for (boolean b : visited) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private void addToSolution(int index)
    {
        visited[index] = true;
        int weight = privateGetWeight(index);
        solutions.add(new Solution(index,weight));
    }

    private int privateGetWeight(int index)
    {
        int weight = 0;
        if (index != this.s)
        {
            String[] path = paths[index].split(",");
            for (int i = 0; i< path.length -1; i++)
            {
                int j = i +1;
                weight += g[Integer.parseInt(path[i])][Integer.parseInt(path[j])];
            }
        }
        return weight;
    }

    private void update(int source, int destination)
    {
        if (source != destination)
        {
            paths[destination] = (paths[source] + "," + destination);
            g[this.s][destination] = privateGetWeight(destination);
        }
    }

    private int getSmallest()
    {
        int small = Integer.MAX_VALUE;
        for (int i = 0; i < visited.length; i++)
        {
            if (!visited[i])
            {
                small = i;
                break;
            }
        }
        for (int i = 0; i < g.length; i++)
        {
            if (!visited[i] && g[s][i] < g[s][small])
            {
                small = i;
            }
        }
        return small;
    }

    private int getSum(int x, int y)
    {
        if (x == Integer.MAX_VALUE || y == Integer.MAX_VALUE)
        {
            return Integer.MAX_VALUE;
        }
        return x+y;
    }

    public void showSolution()
    {
        System.out.println("Graph.Solution: "+this.solutions);
    }

    public void showPath(int index)
    {
        System.out.println("Path to the Vertex " + index + " is: " + paths[index]);
    }
    public String getPath(int index)
    {
        return paths[index];
    }

    public void showWeight(int index)
    {
        System.out.println("Weight of Vertex " + index + " is: " + g[s][index]);
    }
    public int getWeight(int index)
    {
        return g[s][index];
    }

    public void showAllWeight()
    {
        for (int i = 0; i < g.length; i++)
        {
            System.out.print(g[s][i] + "\t");
        }
        System.out.print("\n");
    }

    public void showDetails()
    {
        System.out.println("Source: " + s);
        showSolution();
        System.out.println("Paths: ");
        for (int i = 0; i < paths.length; i++)
        {
            showPath(i);
        }
        System.out.println("Weight to evey vertex:");
        showAllWeight();
    }
}
class Solution
{
    int weight;
    int vertex;
    Solution(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
    public String toString()
    {
        return ("(V:" + vertex + ",W:" + weight + ')');
    }
}