package SnairsUI;

import Files.Classes.Requests;
import Graph.Dijkstra;
import Graph.GraphGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UI extends JFrame
{
    int[][] graph;
    JPanel[] sections = new JPanel[5];
    JLabel[] steps;
    JLabel[] nodes;
    JLabel currentNode;
    int vertices;
    int current;
    GraphGenerator generator;
    JLabel moves;
    int movesCounter;
    JLabel weight;
    JLabel show;
    JTextField record;
    JButton diceRoll;
    JLabel diceNum;
    JScrollPane scrollPane;

    UI(int vertices)
    {
        this.vertices = vertices;
        int sideLength = (int) Math.pow(vertices,.5);
        generator = new GraphGenerator(vertices);
        int[][] graph = generator.getAdjacencyMatrix();
        this.graph = graph;
        int[][] representation = GraphGenerator.getVertexOrderMatrix(vertices);
        GraphGenerator.Snake[] snakes = generator.getSnakes();
        GraphGenerator.Stair[] stairs = generator.getStairs();

        Font step = new Font("Ariel",Font.BOLD,30);
        Font button = new Font("Ariel", Font.ITALIC,15);
        Font diceFont = new Font("Ariel", Font.ITALIC,240);

        steps = new JLabel[vertices];
        setSize(1600,900);
        for (int i = 0; i < sections.length; i++)
        {
            sections[i] = new JPanel();
            sections[i].setBackground(new Color(i*20,i*30,i*40));
            sections[i].setOpaque(true);
        }
        add(sections[0],BorderLayout.CENTER);
        sections[0].setBackground(Color.BLACK);
        sections[0].setLayout(new GridLayout(sideLength,sideLength,2,2));


        add(sections[1],BorderLayout.NORTH);
        sections[1].setLayout(new FlowLayout());
//        JButton newGame = new JButton("New Game");
//        JButton mainMenu = new JButton("Main Menu");
//        JButton exit = new JButton("Exit");
//        sections[1].add(newGame);
//        sections[1].add(mainMenu);
//        sections[1].add(exit);


        add(sections[2],BorderLayout.SOUTH);
        sections[2].setLayout(new FlowLayout());
        JButton requestButton = new JButton("Request A Game: ");
        requestButton.setBackground(sections[2].getBackground());
        requestButton.setForeground(Color.white);
        requestButton.setBorderPainted(false);
        requestButton.setFont(button);
        JTextField requestName = new JTextField();
        requestName.setText("Enter Name Here.");
        sections[2].add(requestButton);
        sections[2].add(requestName);
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requestName.getText().equals("Enter Name Here."))
                {
                }
                else
                {
                    try {
                        Requests.addRequest(new Requests(requestName.getText()));
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
            }
        });


        add(sections[3],BorderLayout.EAST);
        sections[3].setLayout(new BoxLayout(sections[3],BoxLayout.Y_AXIS));
        JLabel[] legends = new JLabel[4];
        for (int i = 0; i < legends.length ; i++)
        {
            legends[i] = new JLabel();
            legends[i].setFont(step);
            sections[3].add(legends[i]);
        }
        legends[0].setText("LEGENDS");
        legends[0].setForeground(Color.WHITE);
        legends[1].setText("SNAKE");
        legends[1].setForeground(Color.RED);
        legends[2].setText("STAIRS");
        legends[2].setForeground(Color.GREEN);
        legends[3].setText("2PIECES");
        legends[3].setForeground(Color.ORANGE);
        Dijkstra dijkstra = new Dijkstra(graph,0,true);
        moves = new JLabel(dijkstra.getPath(vertices-1));
        moves.setForeground(Color.ORANGE);
        moves.setFont(new Font("Ariel", Font.BOLD, 20));
        JLabel minWeight = new JLabel("Min Weight");
        minWeight.setFont(step);
        minWeight.setForeground(Color.WHITE);
        weight = new JLabel();
        weight.setText(dijkstra.getWeight(vertices-1)+"");
        weight.setFont(new Font("Ariel", Font.BOLD, 20));
        weight.setForeground(Color.ORANGE);
        JLabel minMoves = new JLabel("Min Moves");
        minMoves.setFont(step);
        minMoves.setForeground(Color.WHITE);
        record = new JTextField("Record");
        record.setFont(new Font("Ariel", Font.BOLD, 20));
        record.setForeground(Color.ORANGE);
        record.setBackground(sections[3].getBackground());
        record.setForeground(Color.WHITE);
        sections[3].add(minMoves);
        minMoves.setVisible(false);
        sections[3].add(moves);
        moves.setVisible(false);
        sections[3].add(minWeight);
        sections[3].add(weight);
        sections[3].add(record);



        add(sections[4],BorderLayout.WEST);
        sections[4].setLayout(new BoxLayout(sections[4],BoxLayout.Y_AXIS));
        diceNum = new JLabel(0+"");
        JLabel movesNum = new JLabel("No. of Moves");
        movesNum.setFont(step);
        show = new JLabel("");
        show.setFont(diceFont);
        diceNum.setFont(diceFont);
        diceRoll = new JButton("Roll the Dice");
        diceRoll.setBackground(sections[4].getBackground());
        diceRoll.setFont(step);
        diceRoll.setBorderPainted(false);
        sections[4].add(diceRoll);
        sections[4].add(diceNum);
        sections[4].add(movesNum);
        sections[4].add(show);

        for (int i= 0; i < steps.length; i++)
        {
            steps[i] = new JLabel();
            steps[i].setLayout(new BorderLayout());
            steps[i].setOpaque(true);
            steps[i].setFont(step);
            sections[0].add(steps[i]);
        }
        int k =0;
        while (k < vertices)
        {
            for (int i = 0; i < representation.length; i++)
            {
                for (int j = 0; j < representation.length; j++)
                {
                    steps[k].setText(representation[i][j]+"");
                    k++;
                }
            }
        }


        nodes = new JLabel[vertices];
        for (int i =0; i < nodes.length;i++)
        {
            nodes[Integer.parseInt(steps[i].getText())] = steps[i];
        }
        for (int i = 0; i < snakes.length; i++)
        {
            nodes[snakes[i].getFrom()].setText(nodes[snakes[i].getFrom()].getText() + "->"+snakes[i].getTo());
            nodes[snakes[i].getFrom()].setBackground(Color.RED);
            nodes[snakes[i].getFrom()].setForeground(Color.WHITE);
        }
        for (int i = 0; i < stairs.length; i++)
        {
            nodes[stairs[i].getFrom()].setText(nodes[stairs[i].getFrom()].getText() + "->"+stairs[i].getTo());
            nodes[stairs[i].getFrom()].setBackground(Color.GREEN);
            nodes[stairs[i].getFrom()].setForeground(Color.WHITE);
        }
        currentNode = nodes[0];
        movesCounter = 0;

        diceRoll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int steps = rollDice();
                diceNum.setText(steps+"");
                moveSteps(steps);
            }
        });
        this.current = 0;


        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void moveSteps(int steps)
    {
        movesCounter++;
        show.setText(movesCounter+"");
        currentNode.setForeground(nodes[vertices-1].getForeground());
        currentNode.setBackground(nodes[vertices-1].getBackground());
        current += steps;
        if (current < vertices)
        {
            Dijkstra dijkstra = new Dijkstra(graph,current,true);
            if (generator.isASnake(current))
            {
                int to = generator.getSnakes()[generator.getSnake(current)].getTo();
                record.setText("Bit by Snake: " + current + "->" + to + "  (At move: " + movesCounter + ")");
                current = to;
                currentNode = nodes[current];
                moves.setText(dijkstra.getPath(vertices-1));
                weight.setText(dijkstra.getWeight(vertices-1)+"");
                currentNode.setBackground(Color.BLUE);
                currentNode.setForeground(Color.WHITE);
                System.out.println("Bit By Snake");
            }
            else if( generator.isAStair(current))
            {
                int to = generator.getStairs()[generator.getStair(current)].getTo();
                record.setText("Yep, Stair: " + current + "->" + to + "  (At move: " + movesCounter + ")");
                current = to;
                currentNode = nodes[current];
                moves.setText(dijkstra.getPath(vertices-1));
                weight.setText(dijkstra.getWeight(vertices-1)+"");
                currentNode.setBackground(Color.BLUE);
                currentNode.setForeground(Color.WHITE);
                System.out.println("Up to Ladder");
            }
            else
            {
                record.setText("Normal Progression: " + (current-steps) + "->" + current);
                currentNode = nodes[current];
                moves.setText(dijkstra.getPath(vertices-1));
                weight.setText(dijkstra.getWeight(vertices-1)+"");
                currentNode.setBackground(Color.BLUE);
                currentNode.setForeground(Color.WHITE);
                System.out.println("Progression");
            }
        }
        if (current == vertices-1 )
        {
            record.setText("!!!!!!VICTORY!!!!!!");
            System.out.println("Won!!!!!");
            diceRoll.setVisible(false);
            diceNum.setVisible(false);
            sections[0].setVisible(false);
//            System.exit(0);
        }
        if (current >= vertices)
        {
            current -= steps;
            currentNode.setBackground(Color.BLUE);
            currentNode.setForeground(Color.WHITE);
        }
    }

    public static int rollDice()
    {
        int[] dice = {2,6,3,1,4,5,2,3,6,4,5,1};
        return dice[(int) (Math.random()*(12))];
    }

    public static void main(String[] args)
    {
        UI ui = new UI(100);
    }
}