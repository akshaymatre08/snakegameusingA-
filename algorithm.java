import java.util.ArrayList;

public class algorithm {
    public  class Node
    {
        int x; int y;
        int g; int h; int f;
        boolean blocked;
         Node parent;

        Node(int X, int Y)
        {
            x = X;
            y = Y;
        }
    }

    public  String a_star(ArrayList<Integer> arrayx, ArrayList<Integer> arrayy, int foodx, int foody)
    {
        ArrayList<int[]> body = new ArrayList<int[]>();

        for(int i = 0; i < arrayx.size(); i++)
        {
            int[] temp = new int[2];
            temp[0] = arrayx.get(0);
            temp[1] = arrayy.get(1);
            body.add(temp);
        }

        int n = 101;
        Node[][] grid = new Node[n][n];

        for (int y = 0; y < n; y++)
        {
            for (int x = 0; x < n; x++)
            {
                if (x == 0 || y == 0 || x == n-1 || y == n-1)
                {
                    grid[x][y] = new Node(x, y);
                    grid[x][y].blocked = true;
                } else
                {
                    grid[x][y] = new Node(x, y);
                }

                for(int[] xy : body){
                    if(xy[0] == x && xy[1] == y){
                        grid[x][y].blocked = true;
                    }
                }
            }
        }

        ArrayList<Node> open = new ArrayList<Node>();
        ArrayList<Node> closed = new ArrayList<Node>();


        boolean left;
        if(body.get(0)[0] < foodx)
            left = true;
        else if(body.get(0)[0] > foodx)
            left = false;
        else{
            if(body.get(0)[1] < foody)
                return "down";
            else
                return "up";
        }


        Node start;
        Node end;
        start = grid[body.get(0)[0]/5][body.get(0)[1]/5];
        end = grid[foodx/5][foody/5];


        open.add(start);

        Node current;
        while (open.size() > 0)
        {
            current = lowestf(open);

            open.remove(current);
            closed.add(current);

            if (current == end)
            {
                break;
            } else
            {
                grid = findnbors(current, end, grid, open, closed);
            }
        }

        ArrayList<Node> path = new ArrayList<Node>();
        Node temp = end;
        while (temp != start)
        {
            path.add(path.size(), temp);
            if (temp.parent != null)
            {
                temp = temp.parent;
            }
            else
            {
                break;
            }
        }


        int[] temp2 = new int[2];
        temp2[0] = 5*path.get(path.size()-2).x - body.get(0)[0];
        temp2[1] = 5*path.get(path.size()-2).y - body.get(0)[0];


        if(temp2[0] < 0)
            return "left";
        else
            return "right";


    }


     Node lowestf(ArrayList<Node> open)
    {
        Node least = open.get(0);
        for (int i = 0; i < open.size(); i++)
        {
            if (least.f > open.get(i).f)
            {
                least = open.get(i);
            }
        }
        return least;
    }

     Node[][] findnbors(Node current, Node endn, Node[][] grid, ArrayList<Node> open, ArrayList<Node> closed)
    {
        int side = 1;
        int cx = current.x;
        int cy = current.y;

        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j<= 1; j++)
            {
                if (true) // i*j == 0
                {
                    Node temp = grid[cx+i][cy+j];
                    if (temp.blocked || closed.contains(temp))
                    {
                        //INVALID NBOR
                    } else  //VALID NEIGHBOUR
                    {
                        if (temp.h <= Math.abs(endn.x - temp.x) + Math.abs(endn.y - temp.y))  //NEW PATH SHORTER or TEMP NOT IN OPEN
                        {
                            temp.g = current.g + side;
                            temp.h = Math.abs(endn.x - temp.x) + Math.abs(endn.y - temp.y);
                            temp.f = temp.g + temp.h;
                            temp.parent = current;

                            if (temp == endn)
                            {
                                //System.out.print("END FOUND");
                                endn.parent = current;
                            }

                            if (!open.contains(temp))
                            {
                                open.add(temp);
                            }
                        }
                    }
                }
            }
        }
        return grid;
    }
}