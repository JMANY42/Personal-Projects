import java.util.*;
public class RandomAI //randomly select move
{
    public static int[] getNextMove(Board b,boolean white)
    {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        if(white)
        {
            moves = b.getWhiteMoves2(false,false);
        }
        else
        {
            moves = b.getBlackMoves2(false,false);
        }
        return moves.get((int)(Math.random()*moves.size()));
    }
}