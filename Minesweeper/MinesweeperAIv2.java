import java.util.*;
public class MinesweeperAIv2
{
    //idea for AIv2
    //kinda like gaussian blur, but different
    //take a 3x3 grid and move it over every cell
    //run through every possible permutation of bombs and check what solutions work and which violate rules based on the numbers
    //at max, 2^9 or 512 permutations
    //keep track of how many times a square has a bomb in it in order for the solutin to be correct
    //the probability of the square being a bomb is the number of solutions with a bomb in it / number of total correct solutions

    //only run on squares that are unveiled and have at least one neighbor with a number
    //brings down number of permutations to 2^8 or 256 at max
    //also cuts down on number of cells that algorithm runs on
    //should be relatively fast

    //put every probability into a 2D array
    //0 means no 0% chance of bomb, 100 means 100% chance of bomb
    

    //potential problems:
    //the only cell being checked is the center cell of the grid
    //the other cells are assumed at being a 50-50 chance of being a bomb or not
    //that isn't always the case
    //in a valid solution by the algorithm, an edge cell might be assumed to be a bomb when in reality
    //  there is no way it could be a bomb
    //that would cause the solution to be counted as valid when in reality it should be invalid

    //potential solutions:
    //use a bigger grid like 5x5
    //however this would be much much slower
    //and it has the same problem, just one layer removed
    
    //incorporate already calculated probabilities in the grid
    //sounds complicated
    //would base new calculations on old calculations that could potentially be wrong


    //for now, I'll implement the original idea and fix problems later


    private static int[][] probBoard;
    private static int numBombs;
    private static int numFlags;
    private static int width;
    private static int height;
    private static int numSpotsRevealed;

    public static int[] makeMove(int[][] board)
    {
        //resets probBoard
        probBoard = new int[board.length][board[0].length];
        for(int r=0;r<probBoard.length;r++)
        {
            for(int c=0;c<probBoard[r].length;c++)
            {
                probBoard[r][c]=-2;
            }
        }

        numFlags=0;
        numSpotsRevealed=0;
        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                //get num of flags
                if(board[r][c]==-3)
                {
                    numFlags++;
                }

                //get number of spots revealed
                if(board[r][c]!=-2)
                {
                    numSpotsRevealed++;
                }
            }
        }

        //loops through board and calculates probability with the grid method
        for(int r=0;r<probBoard.length;r++)
        {
            for(int c=0;c<probBoard[r].length;c++)
            {
                calculateProbability(board,r,c);
            }
        }
        return new int[0];
    }

    public static void calculateProbability(int[][] board,int r,int c)
    {
        //get number of neighbors that are revealed
        boolean[] unveiledNeighborsArray = getNeighborsEqual(board,r,c,-2,-2);
        int numRevealedNeighbors = 0;
        for(boolean b:unveiledNeighborsArray)
        {
            if(!b)
            {
                numRevealedNeighbors++;
            }
        }




        if(numRevealedNeighbors>0)
        {
            //do grid method
            int[][] grid = new int[3][3];
            ArrayList<int[]> pointsToSkip = new ArrayList<int[]>();            
            for(int i=0;i<Math.pow(2,numRevealedNeighbors);i++)
            {
                mapIntToGrid(i,grid,pointsToSkip);
                
            }
        }
        else
        {
            probBoard[r][c] = (numBombs-numFlags)*100/(width*height-numSpotsRevealed);
        }
    }

    public static void mapIntToGrid(int i,int[][] grid,ArrayList<int[]> pointsToSkip)
    {
        for(int r=0;r<grid.length;r++)
        {
            for(int c=0;c<grid[r].length;c++)
            {
                if(!IntArrArrayListContains(pointsToSkip,new int[]{r,c}))
                {
                    grid[r][c] = getBit(i,r*grid[r].length+c);
                }
            }
        }
        for(int[] arr:grid)
        {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println();
    }

    public static int getBit(int n, int k)
    {
        return (n >> k) & 1;
    }   

    public static boolean IntArrArrayListContains(ArrayList<int[]> intaral,int[] arr)
    {
        for(int i=0;i<intaral.size();i++)
        {
            if(intaral.size()!=arr.length)
            {
                return false;
            }
            for(int j=0;j<arr.length;j++)
            {
                if(intaral.get(i)[j]!=arr[j])
                {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean[] getNeighborsEqual(int[][] b,int r,int c,int num1,int num2)
    {
        boolean[] unveiledNeighborsArray = new boolean[8];
        if(r>0&&(b[r-1][c]==num1||b[r-1][c]==num2))
        {
            unveiledNeighborsArray[0]=true;
        }
        if(r<b.length-1&&(b[r+1][c]==num1||b[r+1][c]==num2))
        {
            unveiledNeighborsArray[1]=true;
        }
        if(c>0&&(b[r][c-1]==num1||b[r][c-1]==num2))
        {
            unveiledNeighborsArray[2]=true;
        }
        if(c<b[0].length-1&&(b[r][c+1]==num1||b[r][c+1]==num2))
        {
            unveiledNeighborsArray[3]=true;
        }
        if(r>0&&c>0&&(b[r-1][c-1]==num1||b[r-1][c-1]==num2))
        {
            unveiledNeighborsArray[4]=true;
        }
        if(r<b.length-1&&c<b[0].length-1&&(b[r+1][c+1]==num1||b[r+1][c+1]==num2))
        {
            unveiledNeighborsArray[5]=true;
        }
        if(r<b.length-1&&c>0&&(b[r+1][c-1]==num1||b[r+1][c-1]==num2))
        {
            unveiledNeighborsArray[6]=true;
        }
        if(r>0&&c<b[0].length-1&&(b[r-1][c+1]==num1||b[r-1][c+1]==num2))
        {
            unveiledNeighborsArray[7]=true;
        }
        return unveiledNeighborsArray;
    }

    public static void setPIVs(int n, int w, int h)
    {
        numBombs = n;
        width = w;
        height = h;
    }
}