import java.util.*;
public class AIv1 //find piece, take piece
{
    public static int[] getNextMove(Position p,boolean white)
    {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        if(white)
        {
            moves = p.getBoard().getWhiteMoves2(false,false);
        }
        else
        {
            moves = p.getBoard().getBlackMoves2(false,false);
        }

        ArrayList<Position> alp = new ArrayList<Position>();


        for(int[] q:moves)
        {
            System.out.println(Arrays.toString(q));
            alp.add(p.getBoard().getPositionAfterMove(q[0], q[1], q[2], q[3]));
        }
        
        Position best = alp.get(0);
        int index = 0;
        //System.out.println(alp);
        
        boolean allEqual = true;
        for(int i=1;i<alp.size();i++)
        {
            //System.out.println(alp.get(i).compareTo(best));
            if(alp.get(i).compareTo(best)!=0)
            {
                allEqual = false;
            }
            if(alp.get(i).compareTo(best)<0)
            {
                best = alp.get(i);
                index = i;
            }
        }

        if(allEqual)
        {
            return moves.get((int)(Math.random()*moves.size()));
        }
        return moves.get(index);
    }
}