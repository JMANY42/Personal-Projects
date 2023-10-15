import java.util.*;
public class MinesweeperAIv1
{
    private static int numBombs;
    private static int numFlags;
    private static int width;
    private static int height;
    private static int numSpotsRevealed;
    //return [row,column,reveal(0)/flag(1)]
    public static int[] makeMove(int[][] board)
    {
        //step 1: calcuate probability of each square being a bomb
        //step 2: return value of least probable bomb square

        numSpotsRevealed = 0;
        numFlags = 0;
        int[][] probBoard = new int[board.length][board[0].length];
        int probMin = 100;
        int probMax = 0;

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

        for(int[] arr:probBoard)
        {
            Arrays.fill(arr,-2);
        }

        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                if(board[r][c]!=-2&&board[r][c]!=-3)
                {
                    probBoard[r][c] = calculateProbability(board,r,c,probBoard);
                    probBoard[r][c] = 0;
                }
                if(probBoard[r][c]==-2)
                {
                    probBoard[r][c] = calculateProbability(board,r,c,probBoard);
                }
            }
        }
        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                if(probBoard[r][c]>=0&&probBoard[r][c]<=100)
                {
                    probMin = Math.min(probMin,probBoard[r][c]);
                }
                if(probBoard[r][c]>=0&&probBoard[r][c]<=100)
                {
                    probMax = Math.max(probMax,probBoard[r][c]);
                }
            }
        }

        for(int[] arr:probBoard)
        {
            System.out.println(Arrays.toString(arr));
        }

        ArrayList<int[]> clearList = new ArrayList<int[]>();
        ArrayList<int[]> flagList = new ArrayList<int[]>();

        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                if(probBoard[r][c] == probMin)
                {
                    clearList.add(new int[]{r,c,0});
                }
                else if(probBoard[r][c] == probMax)
                {
                    flagList.add(new int[]{r,c,1});
                }
            }
        }
        System.out.println("probmax: "+probMax);

        //flagList pruning
        for(int i=0;i<flagList.size();i++)
        {
            if(board[flagList.get(i)[0]][flagList.get(i)[1]]==-3)
            {
                flagList.remove(i);
                i--;
            }
        }
        System.out.println("flagList size: "+flagList.size());

        //clearList pruning
        for(int i=0;i<clearList.size();i++)
        {
            if(board[clearList.get(i)[0]][clearList.get(i)[1]]!=-2)
            {
                clearList.remove(i);
                i--;
            }
        }
        System.out.println("clearList size: "+clearList.size());

        if(clearList.isEmpty())
        {
            int newProbMin = 100;
            for(int r=0;r<board.length;r++)
            {
                for(int c=0;c<board[r].length;c++)
                {
                    if(probBoard[r][c]!=probMin)
                    {
                        newProbMin = Math.min(newProbMin,probBoard[r][c]);
                    }
                }
            }

            probMin = newProbMin;

            System.out.println("newProbMin: "+newProbMin);
            for(int r=0;r<board.length;r++)
            {
                for(int c=0;c<board[r].length;c++)
                {
                    if(probBoard[r][c] == newProbMin)
                    {
                        clearList.add(new int[]{r,c,0});
                    }
                }
            }
        }
        System.out.println("clearList size: "+clearList.size());

        //returns flags before returning clears
        if(!flagList.isEmpty()&&probMin!=probMax)
        {
            return flagList.get(0);
        }

        return clearList.get((int)(Math.random()*clearList.size()));
    }

    public static int calculateProbability(int[][] board,int r,int c,int[][] probBoard)
    {
        //check to see if square is unveiled yet

        //if square > 0 and number of unveiled squares == square
        //all unveiled squares must be bombs
        //
        //if square > 0 and number of flagged squares == square
        //all unveiled squares must be safe
        //these two rules work pretty well

        //next:
        //calculate probabilty aparty from 0 or 100 to try to get better guesses when the first two rules fail
        //I'm going to implement my idea for this in AIv2 because it should render everything in this class obsolete if it works
        

        boolean[] unveiledNeighborsArray = getNeighborsEqual(board,r,c,-2,-3);
        int numUnveiledNeighbors = 0;
        for(boolean b:unveiledNeighborsArray)
        {
            if(b)
            {
                numUnveiledNeighbors++;
            }
        }
        //System.out.println("row: "+r+" col: "+c+" board: "+board[r][c]+" num: "+numUnveiledNeighbors);
        if(board[r][c]>0&&numUnveiledNeighbors==board[r][c])
        {
            if(unveiledNeighborsArray[0])
            {
                probBoard[r-1][c]=100;
            }
            if(unveiledNeighborsArray[1])
            {
                probBoard[r+1][c]=100;
            }
            if(unveiledNeighborsArray[2])
            {
                probBoard[r][c-1]=100;
            }
            if(unveiledNeighborsArray[3])
            {
                probBoard[r][c+1]=100;
            }
            if(unveiledNeighborsArray[4])
            {
                probBoard[r-1][c-1]=100;
            }
            if(unveiledNeighborsArray[5])
            {
                probBoard[r+1][c+1]=100;
            }
            if(unveiledNeighborsArray[6])
            {
                probBoard[r+1][c-1]=100;
            }
            if(unveiledNeighborsArray[7])
            {
                probBoard[r-1][c+1]=100;
            }
        }

        boolean [] numFlagsArray = getNeighborsEqual(board,r,c,-3,-3);
        int numFlagNeighbors = 0;
        for(boolean b:numFlagsArray)
        {
            if(b)
            {
                numFlagNeighbors++;
            }
        }
        //System.out.println("row: "+r+" col: "+c+" board: "+board[r][c]+" num: "+numUnveiledNeighbors);
        if(board[r][c]>0&&numFlagNeighbors==board[r][c])
        {
            for(int i=0;i<numFlagsArray.length;i++)
            {
                numFlagsArray[i]=!numFlagsArray[i];
            }


            if(r>0&&numFlagsArray[0])
            {
                probBoard[r-1][c]=0;
            }
            if(r<height-1&&numFlagsArray[1])
            {
                probBoard[r+1][c]=0;
            }
            if(c>0&&numFlagsArray[2])
            {
                probBoard[r][c-1]=0;
            }
            if(c<width-1&&numFlagsArray[3])
            {
                probBoard[r][c+1]=0;
            }
            if(r>0&&c>0&&numFlagsArray[4])
            {
                probBoard[r-1][c-1]=0;
            }
            if(r<height-1&&c<width-1&&numFlagsArray[5])
            {
                probBoard[r+1][c+1]=0;
            }
            if(r<height-1&&c>0&&numFlagsArray[6])
            {
                probBoard[r+1][c-1]=0;
            }
            if(r>0&&c<width-1&&numFlagsArray[7])
            {
                probBoard[r-1][c+1]=0;
            }
        }
        //return bombs remaining/squares remaining
        return (numBombs-numFlags)*100/(width*height-numSpotsRevealed);
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