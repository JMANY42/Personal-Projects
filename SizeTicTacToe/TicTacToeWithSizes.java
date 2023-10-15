import java.util.*;
public class TicTacToeWithSizes
{
    public static void main(String[]args)
    {
        int[][] board = new int[3][3];
        for(int[] q:board)
        {
            Arrays.fill(q,0);
        }
        System.out.println("7 8 9\n4 5 6\n1 2 3");

        boolean p1Turn = true;
        boolean finished = false;

        int[] p1Array = {3,3,3};
        int[] p2Array = {3,3,3};
        draw(board, p1Array, p2Array);
        while(!finished)
        {
            if(p1Turn)
            {
                //gets spot and size of piece
                //makes sure its in the correct parameters as well
                System.out.println("Player 1, enter spot you want to put a piece");
                int spot = getSpot();
                System.out.println("Enter size of piece");
                int size = getSize();



                //converts the spot number into matrix spot
                //checks to make sure the matrix isn't already full at that spot
                //sets the matrix at that spot to the size
                if(Math.abs(board[(8-(spot-1))/3][(spot-1)%3])<size&&p1Array[size-1]>0)
                {
                    board[(8-(spot-1))/3][(spot-1)%3]=size;
                    p1Turn = !p1Turn;
                    p1Array[size-1]--;
                }
                else
                {
                    System.out.println("Enter a valid number.");
                }



                draw(board, p1Array, p2Array);

                //checks for a win and for a tie
                if(checkP1Win(board))
                {
                    System.out.println("Player 1 wins!");
                    break;
                }
                else if(checkTie(board,p1Array,p2Array)||(p2Array[0]==0&&p2Array[1]==0&&p2Array[2]==0))
                {
                    System.out.println("Tie");
                    break;
                }
            }


            else
            {
                //gets spot and size of piece
                //makes sure its in the correct parameters as well
                System.out.println("Player 2, enter spot you want to put a piece");
                int spot = getSpot();
                System.out.println("Enter size of piece");
                int size = getSize();


                //converts the spot number into matrix spot
                //checks to make sure the matrix isn't already full at that spot
                //sets the matrix at that spot to the size
                if(Math.abs(board[(8-(spot-1))/3][(spot-1)%3])<Math.abs(size)&&p2Array[size-1]>0)
                {
                    board[(8-(spot-1))/3][(spot-1)%3]=0-size;
                    p1Turn = !p1Turn;
                    p2Array[size-1]--;
                }
                else
                {
                    System.out.println("Enter a valid number.");
                }

                draw(board, p1Array, p2Array);


                //checks for a win and for a tie
                if(checkP2Win(board))
                {
                    System.out.println("Player 2 wins!");
                    break;
                }
                else if(checkTie(board,p1Array,p2Array)||(p1Array[0]==0&&p1Array[1]==0&&p1Array[2]==0))
                {
                    System.out.println("Tie");
                    break;
                }
            }
        }
        
    }
    public static int getSize()
    {
        Scanner in = new Scanner(System.in);
        int size = -1;
        while(size<1||size>3)
        {
            System.out.println("Enter a number 1-3");
            try{
                size = Integer.valueOf(in.nextLine());
            } catch(Exception e){}
        }
        return size;
    }

    public static int getSpot()
    {
        Scanner in = new Scanner(System.in);
        int spot = -1;
        while(spot<1||spot>9)
        {
            System.out.println("Enter a number 1-9");
            try{
                spot = Integer.valueOf(in.nextLine());
            } catch(Exception e){}
        }
        return spot;
    }

    public static void draw(int[][] board, int[] p1, int[] p2)
    {
        String[][] template = {{"OOO","OOO","OOO"},{"OO ","OO ","OO "},{" O "," O "," O "},{"   ","   ","   "},{" X "," X "," X "},{"XX ","XX ","XX "},{"XXX","XXX","XXX"}};
        System.out.println(template[board[0][0]+3][0]+"|"+template[board[0][1]+3][0]+"|"+template[board[0][2]+3][0]+"          Player one:");
        System.out.println(template[board[0][0]+3][1]+"|"+template[board[0][1]+3][1]+"|"+template[board[0][2]+3][1]+"          Small pieces: "+p1[0]);
        System.out.println(template[board[0][0]+3][2]+"|"+template[board[0][1]+3][2]+"|"+template[board[0][2]+3][2]+"          Medium pieces: "+p1[1]);
        System.out.println("---|---|---          Large pieces: "+p1[2]);
        System.out.println(template[board[1][0]+3][0]+"|"+template[board[1][1]+3][0]+"|"+template[board[1][2]+3][0]);
        System.out.println(template[board[1][0]+3][1]+"|"+template[board[1][1]+3][1]+"|"+template[board[1][2]+3][1]);
        System.out.println(template[board[1][0]+3][2]+"|"+template[board[1][1]+3][2]+"|"+template[board[1][2]+3][2]);
        System.out.println("---|---|---          Player two:");
        System.out.println(template[board[2][0]+3][0]+"|"+template[board[2][1]+3][0]+"|"+template[board[2][2]+3][0]+"          Small pieces: "+p2[0]);
        System.out.println(template[board[2][0]+3][1]+"|"+template[board[2][1]+3][1]+"|"+template[board[2][2]+3][1]+"          Medium pieces: "+p2[1]);
        System.out.println(template[board[2][0]+3][2]+"|"+template[board[2][1]+3][2]+"|"+template[board[2][2]+3][2]+"          Large pieces: "+p2[2]);
    }

    public static boolean checkP1Win(int[][] x)
    {
        return x[0][0]>0&&x[0][1]>0&&x[0][2]>0 || x[1][0]>0&&x[1][1]>0&&x[1][2]>0 || x[2][0]>0&&x[2][1]>0&&x[2][2]>0 || x[0][0]>0&&x[1][0]>0&&x[2][0]>0 || x[0][1]>0&&x[1][1]>0&&x[2][1]>0 || x[0][2]>0&&x[1][2]>0&&x[2][2]>0 || x[0][0]>0&&x[1][1]>0&&x[2][2]>0 || x[0][2]>0&&x[1][1]>0&&x[2][0]>0;
    }

    public static boolean checkP2Win(int[][] x)
    {
        return x[0][0]<0&&x[0][1]<0&&x[0][2]<0 || x[1][0]<0&&x[1][1]<0&&x[1][2]<0 || x[2][0]<0&&x[2][1]<0&&x[2][2]<0 || x[0][0]<0&&x[1][0]<0&&x[2][0]<0 || x[0][1]<0&&x[1][1]<0&&x[2][1]<0 || x[0][2]<0&&x[1][2]<0&&x[2][2]<0 || x[0][0]<0&&x[1][1]<0&&x[2][2]<0 || x[0][2]<0&&x[1][1]<0&&x[2][0]<0;
    }

    public static boolean checkTie(int[][] x, int[] p1, int[]p2)
    {
        for(int i=0;i<x.length;i++)
        {
            for(int j=0;j<x[0].length;j++)
            {
                if(Math.abs(x[i][j])==3)
                {
                    continue;
                }
                else if(Math.abs(x[i][j])==2)
                {
                    if(p1[2]>0||p2[2]>0)
                    {
                        return false;
                    }
                }
                else if(Math.abs(x[i][j])==1)
                {
                    if(p1[1]>0||p2[1]>0)
                    {
                        return false;
                    }
                }
                else if(Math.abs(x[i][j])==1)
                {
                    if(p1[1]>0||p2[1]>0)
                    {
                        return false;
                    }
                }
                else if(x[i][j]==0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}