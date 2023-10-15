import java.util.*;
public class AIv2 //minimax algorithm with alpha beta pruning
{
    private static ArrayList<Position> depth1pos;
    private static ArrayList<Double> depth1posEvals;
    private static int depth = 3;
    private static int depth1positions = 0;
    private static ArrayList<int[]> moves;
    private static int numComparisons = 0;
    public static int[] getNextMove(Position p,boolean white)
    {
        //run minimax on p
        //get all moves in the position
        //find the positions that have the same value of the orignal position
        //pick randomly from those moves



        depth1pos = new ArrayList<Position>();
        depth1posEvals = new ArrayList<Double>();

        moves = p.getBoard().getBlackMoves2(false,false);


        //detect checkmate
        for(int[] q:moves)
        {
            Position temp = p.getBoard().getPositionAfterMove(q[0],q[1],q[2],q[3]);
            System.out.println(Arrays.toString(q)+" "+temp.getEval());

            boolean[] bpp = temp.getBlackPawnPromotion();            

            for(int i=0;i<bpp.length;i++)
            {
                if(bpp[i])
                {
                    int[] pparr = new int[5];
                    pparr[0] = q[0];
                    pparr[1] = q[1];
                    pparr[2] = q[2];
                    pparr[3] = q[3];
                    pparr[4] = i;
                    Piece[][] tempParr = temp.getBoard().getParrCopy();
                    tempParr[pparr[1]][pparr[0]] = new Piece("empty");

                    switch(i)
                    {
                        case 0:
                            tempParr[pparr[3]][pparr[2]] = new Piece("queen","black");
                            break;
                        case 1:
                            tempParr[pparr[3]][pparr[2]] = new Piece("rook","black");
                            break;
                        case 2:
                            tempParr[pparr[3]][pparr[2]] = new Piece("bishop","black");
                            break;
                        case 3:
                            tempParr[pparr[3]][pparr[2]] = new Piece("knight","black");
                            break;
                    }

                    Position temp2 = new Position(temp.getBoard().getCopyOfBoard(),tempParr,temp.getBoard().getWhiteEnPassantPositions(),temp.getBoard().getBlackEnPassantPositions(),temp.getBoard().getWhiteCastleConditions(),temp.getBoard().getBlackCastleConditions(),false,false);

                    System.out.println("temp2 eval: "+temp2.getEval());
                    System.out.println("i: "+i);
                    System.out.println("temp 2: "+temp2);
                    if(temp2.getEval()==Double.MAX_VALUE*-1)
                    {
                        return pparr;
                    }
                }
            }            
            

            if(temp.getEval()==Double.MAX_VALUE*-1)
            {
                return q;
            }
        }


        long startTime = System.currentTimeMillis();
        double value = minimax(p,depth,Double.MAX_VALUE*-1,Double.MAX_VALUE,false,new int[depth][4]);




        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("value: "+value);

        ArrayList<Position> positions = new ArrayList<Position>();

        positions = depth1pos;

        System.out.println("sizes");
        System.out.println(positions.size());
        System.out.println(moves.size());

        for(int[] q:moves)
        {
            System.out.println(Arrays.toString(q));
        }

        

        System.out.println("depth 1 positions: "+depth1positions);

        for(int i=0;i<positions.size();i++)
        {
            System.out.println(Arrays.toString(moves.get(i)));
            System.out.println(positions.get(i));
            System.out.println();
        }

        for(int i=0;i<moves.size();i++)
        {
            System.out.println(Arrays.toString(moves.get(i))+" "+positions.get(i).getEval());
        }

        for(int i=0;i<positions.size();i++)
        {
            //System.out.println(depth1posEvals.get(i)+" "+Arrays.toString(moves.get(i)));

            //maybe if early in game, add a small range to get some variety
        
            if(positions.get(i).getEval()!=value)
            {
                positions.remove(i);
                depth1posEvals.remove(i);
                moves.remove(i);
                i--;
            }
        }
        System.out.println();
        System.out.println(moves.size());
        System.out.println(positions.size());
        System.out.println(depth1posEvals.size());

        while(moves.size()!=positions.size()&&moves.size()>1)
        {
            moves.remove(moves.size()-1);
        }
        for(int i=0;i<moves.size();i++)
        {
            System.out.println(Arrays.toString(moves.get(i))+" "+positions.get(i).getEval());
        }

        System.out.println((System.currentTimeMillis()-startTime)/1000.0+" seconds");
        System.out.println("comparisons: "+numComparisons);

        return moves.get((int)(Math.random()*moves.size()));
    }

    public static double minimax(Position pos, int d,double alpha, double beta,boolean max, int[][] movesx)
    {
        numComparisons++;
        int[][] movesw = new int[depth][4];

        for(int i=0;i<depth;i++)
        {
            for(int j=0;j<4;j++)
            {
                movesw[i][j] = movesx[i][j];
            }
        }
        //System.out.println(pos);
        if(d==depth-1)
        {
            //depth1pos.add(pos);
            //depth1positions++;
        }
        if(d==0/*||pos.getBoard().getGameOver()*/)
        {
            //System.out.println("depth 0 eval: "+pos.getEval());
            //System.out.println(pos);
            //System.out.println();
            //System.out.println("d0 moves: "+Arrays.toString(moves[0])+Arrays.toString(moves[1])+Arrays.toString(moves[2])+" "+pos.getEval()+" "+pos.getBoard().detectCheck(true,false));
            return pos.getEval();
        }
        if(max)
        {
            double maxEval = Double.MAX_VALUE*-1;

            ArrayList<Position> childrenArr = new ArrayList<Position>();

            ArrayList<int[]> whiteMoves = pos.getBoard().getWhiteMoves2(false,false);
            System.out.println("white: depth "+d+" "+whiteMoves.size()+" eval: "+pos.getEval()+" "+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" alpha: "+alpha+" beta: "+beta);             

            for(int[] q:whiteMoves)
            {
                childrenArr.add(pos.getBoard().getPositionAfterMove(q[0],q[1],q[2],q[3]));
            }

            if(d==depth||d==depth-1||d==depth-2)
            {
                //sort childrenArr from highest eval to lowest
                for(int i=0;i<childrenArr.size()-1;i++)
                {
                    for(int j=0;j<childrenArr.size()-i-1;j++)
                    {
                        if(childrenArr.get(j).getEval()<childrenArr.get(j+1).getEval())
                        {
                            Position temp = childrenArr.get(j);
                            childrenArr.set(j,childrenArr.get(j+1));
                            childrenArr.set(j+1,temp);
                            
                            int[] wmarr = whiteMoves.get(j);
                            whiteMoves.set(j,whiteMoves.get(j+1));
                            whiteMoves.set(j+1,wmarr);

                            if(d==depth)
                            {
                                int[] temparr = moves.get(j);
                                moves.set(j,moves.get(j+1));
                                moves.set(j+1,temparr);
                            }
                        }
                    }
                }
            }

            /*for(Position q:childrenArr)
            {
                System.out.println(q.getEval());
            }*/

            int i = 0;
            for(Position q:childrenArr)
            {
                movesw[d-1] = whiteMoves.get(i);
                //System.out.println("before white: depth "+d+" "+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" max eval: "+maxEval+" alpha: "+alpha+" beta: "+beta);
                double eval = minimax(q,d-1,alpha,beta,false,movesw);

                int[][] pNumArr = pos.getBoard().getPNumArrCopy();
                int x = whiteMoves.get(i)[0];
                int y = whiteMoves.get(i)[1];
                int x2 = whiteMoves.get(i)[2];        

                if(((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8)&&(x2==x+2||x2==x-2))
                {
                    eval+=.5;
                    q.setEval(eval);
                }
                maxEval = Math.max(maxEval,eval);
                alpha = Math.max(alpha,eval);
                //System.out.println("after white: depth "+d+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" max eval: "+maxEval+" alpha: "+alpha+" beta: "+beta);
                if(beta<alpha)
                {
                    break;
                }
                i++;
            }

            if(d==depth-1)
            {
                System.out.println("addPosW");
                depth1posEvals.add(maxEval);
                depth1pos.add(pos);
                pos.setEval(maxEval);
                depth1positions++;            
            }
            return maxEval;
        }
        else
        {
            double minEval = Double.MAX_VALUE;

            ArrayList<Position> childrenArr = new ArrayList<Position>();

            ArrayList<int[]> blackMoves = pos.getBoard().getBlackMoves2(false,false);
            System.out.println("black: depth "+d+" "+blackMoves.size()+" eval: "+pos.getEval()+" "+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" alpha: "+alpha+" beta: "+beta);

            for(int k=0;k<blackMoves.size();k++)
            {
                if(blackMoves.get(k)[1]==6&&blackMoves.get(k)[3]==7&&pos.getBoard().getParrCopy()[blackMoves.get(k)[1]][blackMoves.get(k)[0]].getColor().equals("black")&&pos.getBoard().getParrCopy()[blackMoves.get(k)[1]][blackMoves.get(k)[0]].getType().equals("pawn"))
                {
                    Position temp = pos.getBoard().getPositionAfterMove(blackMoves.get(k)[0],blackMoves.get(k)[1],blackMoves.get(k)[2],blackMoves.get(k)[3]);

                    boolean[] bpp = temp.getBlackPawnPromotion();
                    for(int i=0;i<bpp.length;i++)
                    {
                        if(bpp[i])
                        {
                            int[] tempArr = new int[5];
                            tempArr[0] = blackMoves.get(k)[0];
                            tempArr[1] = blackMoves.get(k)[1];
                            tempArr[2] = blackMoves.get(k)[2];
                            tempArr[3] = blackMoves.get(k)[3];
                            tempArr[4] = i;
                            blackMoves.set(k, tempArr);

                            if(d==depth)
                            {
                                moves.set(k, tempArr);
                            }
                        }
                    }
                }

                childrenArr.add(pos.getBoard().getPositionAfterMove(blackMoves.get(k)[0],blackMoves.get(k)[1],blackMoves.get(k)[2],blackMoves.get(k)[3]));
            }

            if(d==depth||d==depth-1||d==depth-2)
            {
                //sort childrenArr from lowest eval to highest
                for(int i=0;i<childrenArr.size()-1;i++)
                {
                    for(int j=0;j<childrenArr.size()-i-1;j++)
                    {
                        if(childrenArr.get(j).getEval()>childrenArr.get(j+1).getEval())
                        {
                            Position temp = childrenArr.get(j);
                            childrenArr.set(j,childrenArr.get(j+1));
                            childrenArr.set(j+1,temp);

                            int[] bmarr = blackMoves.get(j);
                            blackMoves.set(j,blackMoves.get(j+1));
                            blackMoves.set(j+1,bmarr);
                            
                            if(d==depth)
                            {
                                int[] temparr = moves.get(j);
                                moves.set(j,moves.get(j+1));
                                moves.set(j+1,temparr);
                            }
                        }
                    }
                }
            }

            int i=0;
            for(Position q:childrenArr)
            {
                //q.getBoard().checkGameOver();
                movesw[d-1] = blackMoves.get(i);
                //System.out.println("before black: depth "+d+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" min eval: "+minEval+" alpha: "+alpha+" beta: "+beta);

                double eval = minimax(q,d-1,alpha,beta,true,movesw);


                int[][] pNumArr = pos.getBoard().getPNumArrCopy();
                int x = blackMoves.get(i)[0];
                int y = blackMoves.get(i)[1];
                int x2 = blackMoves.get(i)[2];        

                if(((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8)&&(x2==x+2||x2==x-2))
                {
                    eval-=.5;
                    q.setEval(eval);
                }
                minEval = Math.min(minEval,eval);
                beta = Math.min(beta,eval);
                //System.out.println("after black: depth "+d+Arrays.toString(movesw[depth-3])+Arrays.toString(movesw[depth-2])+Arrays.toString(movesw[depth-1])+" min eval: "+minEval+" alpha: "+alpha+" beta: "+beta);

                if(beta<alpha)
                {
                    break;
                }
                i++;
            }
            if(d==depth-1)
            {
                System.out.println("addPos");
                depth1posEvals.add(minEval);
                depth1pos.add(pos);
                pos.setEval(minEval);
                depth1positions++;         
            }
            return minEval;
        }
    }

    public int getDepth()
    {
        return depth;
    }
}