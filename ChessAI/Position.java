import java.util.*;

public class Position implements Comparable<Position>
{
    private Piece[][] parr;
    private int[][] pNumArr;
    private boolean[] whiteEnPassantPositions;
    private boolean[] blackEnPassantPositions;
    private boolean[] whiteCastleConditions;
    private boolean[] blackCastleConditions;
    private boolean[] blackPawnPromotion;
    private boolean[] whitePawnPromotion;
    private Board board;
    private boolean evaluated;
    private boolean whiteCastled;
    private boolean blackCastled;
    private double mwv;
    private double mbv;
    private double wppv;
    private double bppv;

    private double eval;


    public Position(Board b,Piece[][] p, boolean[] wepp, boolean[] bepp, boolean[] wcc, boolean[] bcc,boolean wjc,boolean bjc)
    {
        board = b;
        parr = p;
        pNumArr = board.getPNumArrCopy(); 
        blackPawnPromotion = new boolean[4];
        whitePawnPromotion = new boolean[4];
        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        whiteCastleConditions = wcc;
        blackCastleConditions = bcc;
        whiteCastled = wjc;
        blackCastled = bjc;
    }
    public void setBlackPawnPromotion(int x)
    {
        blackPawnPromotion[x] = true;
    }
    public void setWhitePawnPromotion(int x)
    {
        whitePawnPromotion[x] = true;
    }
    public boolean[] getBlackPawnPromotion()
    {
        return blackPawnPromotion;
    }    
    public boolean[] getWhitePawnPromotion()
    {
        return whitePawnPromotion;
    }
    public Piece[][] getPieceArr()
    {
        return parr;
    }
    public boolean[] getWhiteEnPassantPositions()
    {
        return whiteEnPassantPositions;
    }
    public boolean[] getBlackEnPassantPositions()
    {
        return blackEnPassantPositions;
    }
    public boolean[] getWhiteCastleConditions()
    {
        return whiteCastleConditions;
    }
    public boolean[] getBlackCastleConditions()
    {
        return blackCastleConditions;
    }
    public Piece[][] getParr()
    {
        return parr;
    }
    public int[][] getPNumArr()
    {
        return pNumArr;
    }
    public Board getBoard()
    {
        return board;
    }

    public void evaluate()
    {
        board.checkGameOver();
        int whitePieceValues = 0;
        int blackPieceValues = 0;

        if(board.getWhiteCheckMate())
        {
            eval = Double.MAX_VALUE*-1;

            //System.out.println("white in checkmate");
            return;
        }
        if(board.getBlackCheckMate())
        {
            eval = Double.MAX_VALUE;
            //System.out.println("black in checkmate");
            return;
        }
        if(board.getDraw())
        {
            eval = 0.0;
            return;
        }


        int whitePiecesNoPawns = 0;
        int blackPiecesNoPawns = 0;


        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(((pNumArr[i][j]&16)==16))
                {
                    whitePieceValues+=parr[i][j].getPieceValue();
                    if(!((pNumArr[i][j]^16)==0||(pNumArr[i][j]^8)==0))
                    {
                        whitePiecesNoPawns++;
                    }
                }
                else if(((pNumArr[i][j]&8)==8))
                {
                    blackPieceValues+=parr[i][j].getPieceValue();
                    if(!((pNumArr[i][j]^16)==0||(pNumArr[i][j]^8)==0))
                    {
                        blackPiecesNoPawns++;
                    }
                }
            }
        }

        double whiteMiddleControl = 0;
        double blackMiddleControl = 0;

        int whitePawnPromotionValue = 0;
        int blackPawnPromotionValue = 0;

        ArrayList<int[]> whiteMoves = getBoard().getWhiteMoves2(false,true);
        ArrayList<int[]> blackMoves = getBoard().getBlackMoves2(false,true);

        if(whitePiecesNoPawns+blackPiecesNoPawns>6)
        {
        

            for(int[] q:whiteMoves)
            {
                if((q[2]==3||q[2]==4)&&(q[3]==3||q[3]==4))
                {
                    whiteMiddleControl+=.2;
                }
                else if((q[2]==2||q[2]==5)&&(q[3]==2||q[3]==5))
                {
                    whiteMiddleControl+=.1;
                }

            }
            for(int[] q:blackMoves)
            {
                if((q[2]==3||q[2]==4)&&(q[3]==3||q[3]==4))
                {
                    blackMiddleControl+=.2;
                }
                else if((q[2]==2||q[2]==5)&&(q[3]==2||q[3]==5))
                {
                    blackMiddleControl+=.1;
                }
            }
        }
        else
        {
            //endgame
            //int whiteNumPawns = whitePieces-whitePiecesNoPawns;
            //int blackNumPawns = blackPieces-blackPiecesNoPawns;

            //whitePieceValues += whiteNumPawns;
            //blackPieceValues += blackNumPawns;



            for(int i=1;i<7;i++)
            {
                int numWPawnsOnRow = 0;
                int numBPawnsOnRow = 0;
                for(int j=0;j<8;j++)
                {
                    if(((pNumArr[i][j]&16)==16)&&((pNumArr[i][j]^16)==0||(pNumArr[i][j]^8)==0))
                    {
                        numWPawnsOnRow++;
                    }
                    else if(((pNumArr[i][j]&8)==8)&&((pNumArr[i][j]^16)==0||(pNumArr[i][j]^8)==0))
                    {
                        numBPawnsOnRow++;
                    }
                }
                whitePawnPromotionValue+=((7-i)*numWPawnsOnRow)/3.0;
                blackPawnPromotionValue+=((i)*numBPawnsOnRow)/3.0;
                wppv = whitePawnPromotionValue;
                bppv = blackPawnPromotionValue;
            }
        }
        double whitePiecesNotInOriginalSpot = 0;
        double blackPiecesNotInOriginalSpot = 0;

        //development
        if(board.getMoves()<15)
        {   
            if(!(((pNumArr[0][1]^17)==0||(pNumArr[0][1]^9)==0)&&((pNumArr[0][1]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[0][2]^18)==0||(pNumArr[0][2]^10)==0)&&((pNumArr[0][2]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[0][5]^18)==0||(pNumArr[0][5]^10)==0)&&((pNumArr[0][5]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[0][6]^17)==0||(pNumArr[0][6]^9)==0)&&((pNumArr[0][6]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[1][3]^16)==0||(pNumArr[1][3]^8)==0)&&((pNumArr[1][3]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
                if(((pNumArr[3][3]^16)==0||(pNumArr[3][3]^8)==0)&&((pNumArr[3][3]&8)==8))
                {
                    blackPiecesNotInOriginalSpot++;
                }
            }
            if(!(((pNumArr[1][4]^16)==0||(pNumArr[1][4]^8)==0)&&((pNumArr[1][4]&8)==8)))
            {
                blackPiecesNotInOriginalSpot++;
                if(((pNumArr[3][4]^16)==0||(pNumArr[3][4]^8)==0)&&((pNumArr[3][4]&8)==8))
                {
                    blackPiecesNotInOriginalSpot++;
                }
            }
            
            if(!(((pNumArr[7][1]^17)==0||(pNumArr[7][1]^9)==0)&&((pNumArr[7][1]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[7][2]^18)==0||(pNumArr[7][2]^10)==0)&&((pNumArr[7][2]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[7][5]^18)==0||(pNumArr[7][5]^10)==0)&&((pNumArr[7][5]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[7][6]^17)==0||(pNumArr[7][6]^9)==0)&&((pNumArr[7][6]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
            }
            if(!(((pNumArr[7][3]^16)==0||(pNumArr[7][3]^8)==0)&&((pNumArr[7][3]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
                if(((pNumArr[4][3]^16)==0||(pNumArr[4][3]^8)==0)&&((pNumArr[4][3]&16)==16))
                {
                    whitePiecesNotInOriginalSpot++;
                }
            }
            if(!(((pNumArr[7][4]^16)==0||(pNumArr[7][4]^8)==0)&&((pNumArr[7][4]&16)==16)))
            {
                whitePiecesNotInOriginalSpot++;
                if(((pNumArr[4][4]^16)==0||(pNumArr[4][4]^8)==0)&&((pNumArr[4][4]&16)==16))
                {
                    whitePiecesNotInOriginalSpot++;
                }
            }
        }
        double miscWhiteValue = 0;
        double miscBlackValue = 0;

        //castleing

        
        mwv = miscWhiteValue;
        mbv = miscBlackValue;


        //king saftey
        //every square around the king defended by enemy piece is +.1 to the corresponding king's color danger
        double whiteKingDanger = 0.0;
        double blackKingDanger = 0.0;

        int wkx = 0;
        int wky = 0;
        int bkx = 0;
        int bky = 0;

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&16)==16))
                {
                    wkx = j;
                    wky = i;
                }
                else if(((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&8)==8))
                {
                    bkx = j;
                    bky = i;
                }
            }
        }

        int[][] wkcheck = {{wkx-1,wky},{wkx-1,wky-1},{wkx-1,wky+1},{wkx+1,wky},{wkx+1,wky-1},{wkx+1,wky+1},{wkx,wky+1},{wkx,wky-1}};
        int[][] bkcheck = {{bkx-1,bky},{bkx-1,bky-1},{bkx-1,bky+1},{bkx+1,bky},{bkx+1,bky-1},{bkx+1,bky+1},{bkx,bky+1},{bkx,bky-1}};

        for(int i=0;i<wkcheck.length;i++)
        {
            if(wkcheck[i][0]<0||wkcheck[i][0]>=8||wkcheck[i][1]<0||wkcheck[i][1]>=8)
            {
                continue;
            }
            if(inList(blackMoves,wkcheck[i]))
            {
                whiteKingDanger+=.1;
            }
        }

        for(int i=0;i<bkcheck.length;i++)
        {
            if(bkcheck[i][0]<0||bkcheck[i][0]>=8||bkcheck[i][1]<0||bkcheck[i][1]>=8)
            {
                continue;
            }
            if(inList(whiteMoves,bkcheck[i]))
            {
                blackKingDanger+=.1;
            }
        }


        int pieceValue = whitePieceValues-blackPieceValues;
        double middleControl = whiteMiddleControl-blackMiddleControl;
        double developmentValue = whitePiecesNotInOriginalSpot*.5-blackPiecesNotInOriginalSpot*.5;
        double miscValue = miscWhiteValue-miscBlackValue;
        double pawnPromotionValue = whitePawnPromotionValue-blackPawnPromotionValue;
        double kingDanger = whiteKingDanger - blackKingDanger;


        eval = pieceValue+middleControl+developmentValue+miscValue+pawnPromotionValue+kingDanger;

        eval = Math.round(eval*10)/10.0;
        //eval = pieceValue;
        evaluated = true;
    }

    public double getEval()
    {
        if(evaluated)
        {
            return eval;
        }
        evaluate();
        return eval;
    }
    public void setEval(double x)
    {
        eval=x;
    }
    public void updateEval(double x)
    {
        eval+=x;
    }
    public int compareTo(Position op)
    {
        return (int)(eval*1000 - op.getEval()*1000);
    }

    public boolean equals(Object o)
    {
        Position p = (Position) o;
        Piece[][] oparr = p.getPieceArr();

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                /*System.out.println(parr[i][j].getType());
                System.out.println(oparr[i][j].getType());
                System.out.println(parr[i][j].getColor());
                System.out.println(oparr[i][j].getColor());
                System.out.println();*/
                if(parr[i][j].getType().equals(oparr[i][j].getType())&&parr[i][j].getColor().equals(oparr[i][j].getColor()))
                {

                }
                else
                {
                    return false;
                }
            }
        }
        
        for(int i=0;i<8;i++)
        {
            if(p.getWhiteEnPassantPositions()[i]!=whiteEnPassantPositions[i]||p.getBlackEnPassantPositions()[i]!=blackEnPassantPositions[i])
            {
                return false;
            }
        }
        for(int i=0;i<2;i++)
        {
            if(p.getWhiteCastleConditions()[i]!=whiteCastleConditions[i]||p.getBlackCastleConditions()[i]!=blackCastleConditions[i])
            {
                return false;
            }
        }
        return true;
    }

    public String toString()
    {
        String output = "";
        for(Piece[] q:parr)
        {
            output+=Arrays.toString(q);
            output+="\n";
        }
        //output+="WEPP: "+Arrays.toString(whiteEnPassantPositions)+"\n";
        //output+="WCC: "+Arrays.toString(whiteCastleConditions)+"\n";
        //output+="BEPP: "+Arrays.toString(blackEnPassantPositions)+"\n";
        //output+="BCC: "+Arrays.toString(blackCastleConditions)+"\n";
        output+="EVAL: "+eval+"\n";
        output+="BC: "+blackCastled+"   "+mbv+"\n";
        output+="WC: "+whiteCastled+"   "+mwv+"\n";
        output+="wppv: "+wppv+"\n";
        output+="bppv: "+bppv+"\n";
        return output;
    }

    public boolean inList(ArrayList<int[]> al, int[] x)
    {
        outerLoop: for(int[] q:al)
        {
            if(q.length!=x.length) continue;

            for(int i=0;i<q.length;i++)
            {
                if(q[i]!=x[i])
                {
                    continue outerLoop;
                }
            }
            return true;
        }
        return false;
    }
}