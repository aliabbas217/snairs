package Graph;

import java.util.Arrays;

public class GraphGenerator
{
    private int vertices;
    private int[][] graph;
    private Snake[] snakes;
    private Stair[] stairs;
    private boolean[] dedicateVertices;
    public GraphGenerator(int vertices)
    {
        this.vertices = vertices;
        graph = new int[vertices][vertices];
        int root = (int) Math.pow(vertices, .5);
        dedicateVertices = new boolean[vertices];
        snakes = new Snake[(int) ((Math.random() * 4) + (root - 4))];
        stairs = new Stair[(int) ((Math.random() * 4) + (root - 4))];
        for (int i = 0; i < snakes.length; i++)
            snakes[i] = new Snake();
        for (int i = 0; i < stairs.length; i++)
            stairs[i] = new Stair();
        for (int i = 0; i < vertices; i++)
        {
            for (int j = 0; j < vertices; j++)
            {
                if (j - i >= 1 && j - i <= 6 )
                    graph[i][j] = j - i;
                else
                    graph[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < snakes.length; i++)
        {
            for (int j = 0; j < vertices; j++)
            {
                graph[snakes[i].from][j] = Integer.MAX_VALUE;
            }
            graph[snakes[i].from][snakes[i].to] = 0;
        }
        for (int i = 0; i < stairs.length; i++)
        {
            for (int j = 0; j < vertices; j++)
            {
                graph[stairs[i].from][j] = Integer.MAX_VALUE;
            }
            graph[stairs[i].from][stairs[i].to] = 0;
        }
    }

    public static int[][] getVertexOrderMatrix(int nodes)
    {
        int number = nodes-1;
        int sideLength = (int) Math.pow(nodes,.5);
        int[][] graph = new int[sideLength][sideLength];
        while (number >= 0)
        {
            for (int i = 0; i < sideLength; i++)
            {
                if (i%2==0)
                {
                    for (int j = 0; j < sideLength; j++)
                    {
                        graph[i][j] = number;
                        number--;
                    }
                }
                else
                {
                    for (int j = sideLength-1; j >=0 ; j--)
                    {
                        graph[i][j] = number;
                        number--;
                    }
                }
            }
        }
        return graph;
    }

    public static void main(String[] args)
    {
        GraphGenerator generator = new GraphGenerator(100);
        int[][] graph = generator.getAdjacencyMatrix();
        System.out.println(generator.snakes.length);
        System.out.println(Arrays.toString(generator.snakes));
        System.out.println(generator.stairs.length);
        System.out.println(Arrays.toString(generator.stairs));
        for (int i = 0; i < graph.length; i++)
        {
            System.out.println(Arrays.toString(graph[i]));
        }
        Dijkstra dijkstra = new Dijkstra(graph,0,true);
    }
    public boolean isASnake(int vertex)
    {
        for (Snake snake : snakes) {
            if (snake.from == vertex) {
                return true;
            }
        }
        return false;
    }
    public int getSnake(int vertex)
    {
        for (int i = 0; i < snakes.length; i++)
        {
            if (snakes[i].from == vertex)
                return i;
        }
        return -1;
    }

    public boolean isAStair(int vertex)
    {
        for (Stair stair : stairs) {
            if (stair.from == vertex) {
                return true;
            }
        }
        return false;
    }

    public int getStair(int vertex)
    {
        for (int i = 0; i < stairs.length; i++)
        {
            if (stairs[i].from == vertex)
                return i;
        }
        return -1;
    }
    public int[][] getAdjacencyMatrix() {
        return this.graph;
    }
    public Snake[] getSnakes() {
        return snakes;
    }
    public Stair[] getStairs() {
        return stairs;
    }
    public class Snake {
        int from,to;
        Snake()
        {
            int min = (int) Math.pow(vertices,.5) * 2;
            do
            {
                from = (int) (Math.random() * (vertices-2-min))+min;
            }
            while (dedicateVertices[from]);
            dedicateVertices[from] = true;
            do
            {
                to = (int) (Math.random() * (vertices-2-1))+1;
            }
            while (dedicateVertices[to] || to > from);
            dedicateVertices[to] = true;
        }
        public int getFrom() {
            return from;
        }
        public void setFrom(int from) {
            this.from = from;
        }
        public int getTo() {
            return to;
        }
        public void setTo(int to) {
            this.to = to;
        }
        @Override
        public String toString() {
            return "Snake{" + "from=" + from + ", to=" + to + '}';
        }
    }
    public class Stair {
        int from,to;
        Stair()
        {
            int min = (int) Math.pow(vertices,.5) * 2;
            do
            {
                to = (int) (Math.random() * (vertices-2-min))+min;
            }
            while (dedicateVertices[to]);
            dedicateVertices[to] = true;
            do
            {
                from = (int) (Math.random() * (vertices-2-1))+1;
            }
            while (dedicateVertices[from] || from > to);
            dedicateVertices[from] = true;
        }
        public int getFrom() {
            return from;
        }
        public void setFrom(int from) {
            this.from = from;
        }
        public int getTo() {
            return to;
        }
        public void setTo(int to) {
            this.to = to;
        }
        @Override
        public String toString() {
            return "Stair{" + "from=" + from + ", to=" + to + '}';
        }
    }
}