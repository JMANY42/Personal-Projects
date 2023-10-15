import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel
{
    private Piece[][] parr;
    private int size = 60;
    private int prevClickX;
    private int prevClickY;
    private boolean whiteMove;
    private boolean whiteInCheckMate;
    private boolean blackInCheckMate;
    private boolean gameOver;

    private boolean whiteEnPassantLeft;
    private boolean whiteEnPassantRight;
    private boolean blackEnPassantLeft;
    private boolean blackEnPassantRight;

    private boolean whiteShortCastle;
    private boolean whiteLongCastle;
    private boolean blackShortCastle;
    private boolean blackLongCastle;
    private boolean whiteCastled;
    private boolean blackCastled;

    private int pawnPromotionSpot;

    private boolean whitePawnPromotion;
    private boolean blackPawnPromotion;

    private int drawMove;
    private boolean[] whiteEnPassantPositions;
    private boolean[] blackEnPassantPositions;
    private ArrayList<Position> positions;

    private int moves;
    private boolean draw;

    private boolean aiIsWhite;

    private int[][] pNumArr;


    public Board(Piece[][] p,int s,int pcX,int pcY,boolean wm,boolean wicm,boolean bicm,boolean go,boolean wepl,
    boolean wepr,boolean bepl, boolean bepr,boolean wsc,boolean wlc,boolean bsc,boolean blc,int pps,boolean wpp,
    boolean bpp,int dm,boolean[] wepp,boolean[] bepp, ArrayList<Position> pos,int mov,boolean aiw, int[][] pna)
    {
        parr = p;
        pNumArr = pna;
        size = s;
        prevClickX = pcX;
        prevClickY = pcY;
        whiteMove = wm;
        whiteInCheckMate = wicm;
        blackInCheckMate = bicm;
        gameOver = go;

        whiteEnPassantLeft = wepl;
        whiteEnPassantRight = wepr;
        blackEnPassantLeft = bepl;
        blackEnPassantRight = bepr;

        whiteShortCastle = wsc;
        whiteLongCastle = wlc;
        blackShortCastle = bsc;
        blackLongCastle = blc;

        pawnPromotionSpot = pps;

        whitePawnPromotion = wpp;
        blackPawnPromotion = bpp;

        drawMove = dm;
        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        positions = pos;
        moves = mov;
        aiIsWhite = aiw;
        //System.out.println("in constructor: "+Arrays.toString(whiteEnPassantPositions));
    }
    
    public Board(boolean aiW)
    {
        pawnPromotionSpot = -1;
        whiteMove = true;
        drawMove = 0;
        parr = new Piece[8][8];
        pNumArr = new int[8][8];
        aiIsWhite = aiW;

        whiteEnPassantPositions = new boolean[8];
        blackEnPassantPositions = new boolean[8];

        positions = new ArrayList<Position>();



        setVisible(true);
        setPreferredSize(new Dimension(size*8, size*8));


        initBoard();



        if(aiIsWhite)
        {
            //flipParr();
            whiteMove = !whiteMove;
            //aiTurn();
            paintImmediately(getVisibleRect());
        }

        
        prevClickX = -1;
        prevClickY = -1;
        addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent e) { 
                if(!gameOver)
                {
                    int y = e.getY()/size;
                    int x = e.getX()/size;
                    if(y>7)
                    {
                        y=7;
                    }
                    if(y<0)
                    {
                        y=0;
                    }
                    if(x>7)
                    {
                        x=7;
                    }
                    if(x<0)
                    {
                        x=0;
                    }

                    if(whitePawnPromotion)
                    {
                        if(x==3&&y==3)
                        {
                            parr[0][pawnPromotionSpot] = new Piece("knight","white");
                            pNumArr[0][pawnPromotionSpot] = 17;
                        }
                        else if(x==4&&y==3)
                        {
                            parr[0][pawnPromotionSpot] = new Piece("bishop","white");
                            pNumArr[0][pawnPromotionSpot] = 18;
                        }
                        else if(x==3&&y==4)
                        {
                            parr[0][pawnPromotionSpot] = new Piece("rook","white");
                            pNumArr[0][pawnPromotionSpot] = 19;

                        }
                        else if(x==4&&y==4)
                        {
                            parr[0][pawnPromotionSpot] = new Piece("queen","white");
                            pNumArr[0][pawnPromotionSpot] = 20;

                        }
                        if((x==3||x==4)&&(y==3||y==4))
                        {
                            whitePawnPromotion = false;
                            pawnPromotionSpot=-1;
                            paintImmediately(getVisibleRect());
                            aiTurn();
                        }
                        checkGameOver();
                    }
                    /*else if(blackPawnPromotion&&(x==3||x==4)&&(y==3||y==4))
                    {
                        if(x==3&&y==3)
                        {
                            parr[7][pawnPromotionSpot] = new Piece("knight","black");
                        }
                        else if(x==4&&y==3)
                        {
                            parr[7][pawnPromotionSpot] = new Piece("bishop","black");
                        }
                        else if(x==3&&y==4)
                        {
                            parr[7][pawnPromotionSpot] = new Piece("rook","black");
                        }
                        else if(x==4&&y==4)
                        {
                            parr[7][pawnPromotionSpot] = new Piece("queen","black");
                        }
                        if((x==3||x==4)&&(y==3||y==4))
                        {
                            whitePawnPromotion = false;
                            pawnPromotionSpot=-1;
                            paintImmediately(getVisibleRect());
                            aiTurn();
                        }
                        checkGameOver();
                    }*/
                    if(!gameOver)
                    {
                        if(prevClickX==-1)
                        {
                            prevClickX=x;
                            prevClickY=y;
                            paintImmediately(getVisibleRect());

                            for(int[] q:legalMove(x,y,false,false))
                            {
                                System.out.println(Arrays.toString(q));
                            }
                        }
                        else
                        {
                            int[] tempArrMove = {y,x};

                            if(inList(legalMove(prevClickX,prevClickY,false,false),tempArrMove)&&!inCheckAfterMove(prevClickX,prevClickY,x,y,true))
                            {
                                System.out.println(prevClickX);
                                System.out.println(prevClickY);

                                System.out.println(x);
                                System.out.println(y);
                                legalMove(0,0,false,false);
                                //System.out.println(whiteLongCastle);

                                move(prevClickX,prevClickY,x,y);
                                prevClickX=-1;
                                prevClickY=-1;
                                paintImmediately(getVisibleRect());

                                //AI Moves
                                //System.out.println(gameOver);
                                if(!gameOver&&(!whitePawnPromotion||blackPawnPromotion))
                                {
                                    aiTurn();
                                }
                            }
                            else{
                                prevClickX=-1;
                                prevClickY=-1;
                                paintImmediately(getVisibleRect());
                            }
                            //System.out.println("x: "+x);
                            //System.out.println("y: "+y);
                            //System.out.println(e);
                        }
                    }
                }
            } 
        }); 
    }
    
    public void initBoard()
    {
        //standard setup
        parr[0][0] = new Piece("rook","black");
        parr[0][1] = new Piece("knight","black");
        parr[0][2] = new Piece("bishop","black");
        parr[0][3] = new Piece("queen","black");
        parr[0][4] = new Piece("king","black");
        parr[0][5] = new Piece("bishop","black");
        parr[0][6] = new Piece("knight","black");
        parr[0][7] = new Piece("rook","black");

        for(int i=0;i<parr[0].length;i++)
        {
            parr[1][i] = new Piece("pawn","black");
        }
        for(int i=0;i<parr[0].length;i++)
        {
            parr[6][i] = new Piece("pawn","white");
        }
        parr[7][0] = new Piece("rook","white");
        parr[7][1] = new Piece("knight","white");
        parr[7][2] = new Piece("bishop","white");
        parr[7][3] = new Piece("queen","white");
        parr[7][4] = new Piece("king","white");
        parr[7][5] = new Piece("bishop","white");
        parr[7][6] = new Piece("knight","white");
        parr[7][7] = new Piece("rook","white");

        if(aiIsWhite)
        {
            //flipParr();
            parr[0][4] = new Piece("queen","black");
            parr[0][3] = new Piece("king","black");
            parr[7][4] = new Piece("queen","white");
            parr[7][3] = new Piece("king","white");

        }

        pNumArr[0][0] = 8+3;
        pNumArr[0][1] = 8+1;
        pNumArr[0][2] = 8+2;
        pNumArr[0][3] = 8+4;
        pNumArr[0][4] = 8+5;
        pNumArr[0][5] = 8+2;
        pNumArr[0][6] = 8+1;
        pNumArr[0][7] = 8+3;

        for(int i=0;i<pNumArr[0].length;i++)
        {
            pNumArr[1][i] = 8;
        }
        for(int i=0;i<pNumArr[0].length;i++)
        {
            pNumArr[6][i] = 16;
        }
        pNumArr[7][0] = 16+3;
        pNumArr[7][1] = 16+1;
        pNumArr[7][2] = 16+2;
        pNumArr[7][3] = 16+4;
        pNumArr[7][4] = 16+5;
        pNumArr[7][5] = 16+2;
        pNumArr[7][6] = 16+1;
        pNumArr[7][7] = 16+3;
        

        //endgame setup
        /*parr[0][0] = new Piece("rook","black");
        parr[0][4] = new Piece("king","black");
        parr[0][6] = new Piece("knight","black");

        for(int i=0;i<parr[0].length;i++)
        {
            parr[1][i] = new Piece("pawn","black");
        }
        for(int i=0;i<parr[0].length;i++)
        {
            parr[6][i] = new Piece("pawn","white");
        }
        parr[7][0] = new Piece("rook","white");
        parr[7][4] = new Piece("king","white");
        parr[7][6] = new Piece("knight","white");*/

        //mate in 2 puzzle
        /*parr[0][0] = new Piece("rook","black");
        parr[0][4] = new Piece("rook","black");
        parr[0][6] = new Piece("king","black");
        parr[1][0] = new Piece("pawn","black");
        parr[1][1] = new Piece("pawn","black");
        parr[1][2] = new Piece("pawn","black");
        parr[1][4] = new Piece("bishop","black");
        parr[1][5] = new Piece("pawn","black");
        parr[1][6] = new Piece("pawn","black");
        parr[2][2] = new Piece("bishop","black");
        parr[2][3] = new Piece("pawn","black",1);
        parr[2][7] = new Piece("pawn","black",1);
        parr[3][4] = new Piece("pawn","white",1);
        parr[4][3] = new Piece("bishop","white");
        parr[4][4] = new Piece("knight","black");
        parr[4][5] = new Piece("pawn","white",1);
        parr[5][3] = new Piece("bishop","white");
        parr[5][7] = new Piece("pawn","white",1);
        parr[6][0] = new Piece("pawn","white");
        parr[6][1] = new Piece("pawn","white");
        parr[6][2] = new Piece("pawn","white");
        parr[6][7] = new Piece("pawn","white");
        parr[7][0] = new Piece("rook","white");
        parr[7][3] = new Piece("queen","white");
        parr[7][5] = new Piece("rook","white");
        parr[7][7] = new Piece("king","white");*/

        //different mate in 2 puzzle
        /*parr[7][0] = new Piece("king","white");
        parr[5][3] = new Piece("bishop","white");
        parr[7][2] = new Piece("king","black");
        parr[6][0] = new Piece("pawn","white");
        parr[6][1] = new Piece("pawn","white");
        parr[5][1] = new Piece("pawn","black");
        parr[0][0] = new Piece("rook","black");*/


        //underpromotion puzzle
        /*parr[2][0] = new Piece("pawn","black");
        parr[3][0] = new Piece("bishop","white");
        parr[3][3] = new Piece("pawn","black");
        parr[3][5] = new Piece("rook","black");
        parr[4][2] = new Piece("king","black");
        parr[4][3] = new Piece("pawn","white");
        parr[4][7] = new Piece("pawn","white");
        parr[5][4] = new Piece("king","white");
        parr[5][6] = new Piece("knight","black");
        parr[6][0] = new Piece("pawn","white");
        parr[6][2] = new Piece("pawn","white");
        parr[6][5] = new Piece("pawn","black");
        parr[6][1] = new Piece("rook","white");*/



        //antoino position
        /*parr[0][0] = new Piece("rook","black");
        parr[2][4] = new Piece("bishop","black");
        parr[0][4] = new Piece("king","black");
        parr[0][7] = new Piece("rook","black");
        parr[1][0] = new Piece("pawn","black");
        parr[1][1] = new Piece("pawn","black");
        parr[1][2] = new Piece("pawn","black");
        parr[1][5] = new Piece("queen","black");
        parr[1][6] = new Piece("pawn","black");
        parr[1][7] = new Piece("pawn","black");
        parr[2][2] = new Piece("knight","black");
        parr[3][3] = new Piece("pawn","black");
        parr[4][5] = new Piece("pawn","white");
        parr[5][2] = new Piece("queen","white");
        parr[5][5] = new Piece("knight","white");
        parr[6][0] = new Piece("pawn","white");
        parr[6][1] = new Piece("pawn","white");
        parr[6][2] = new Piece("pawn","white");
        parr[6][5] = new Piece("pawn","white");
        parr[5][6] = new Piece("pawn","white");
        parr[6][7] = new Piece("pawn","white");
        parr[7][3] = new Piece("rook","white");
        parr[7][2] = new Piece("king","white");
        parr[7][5] = new Piece("bishop","white");
        parr[7][7] = new Piece("rook","white");*/
      
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(parr[i][j]==null)
                {
                    parr[i][j] = new Piece("empty");
                }
            }
        } 
            
        positions.add(new Position(this,getParrCopy(),new boolean[8],new boolean[8],new boolean[2], new boolean[2],false,false));
        printParr();  
    }
    
    //only really worry about this for making the chessboard appear
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;


        if(aiIsWhite)
        {
            invertParr();
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {

                if(prevClickY==i&&prevClickX==j)
                {
                    g2d.setColor(Color.GREEN);
                } 
                /*else if(!aiIsWhite&&(detectCheck(true,true)&&parr[i][j].getType().equals("king")&&parr[i][j].getColor().equals("white"))||(aiIsWhite&&detectCheck(false,true)&&parr[i][j].getType().equals("king")&&parr[i][j].getColor().equals("black")))
                {
                    g2d.setColor(Color.RED);
                }
                else if(aiIsWhite&&(detectCheck(true,true)&&parr[i][j].getType().equals("king")&&parr[i][j].getColor().equals("white"))||(!aiIsWhite&&detectCheck(false,true)&&parr[i][j].getType().equals("king")&&parr[i][j].getColor().equals("black")))
                {
                    g2d.setColor(Color.RED);
                } */
                else if(!aiIsWhite&&(detectCheck(true,true)&&((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&16)==16)||(aiIsWhite&&detectCheck(false,true)&&((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&8)==8))))
                {
                    g2d.setColor(Color.RED);
                }
                else if(aiIsWhite&&(detectCheck(true,true)&&((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&16)==16))||(!aiIsWhite&&detectCheck(false,true)&&((pNumArr[i][j]^21)==0||(pNumArr[i][j]^13)==0)&&((pNumArr[i][j]&8)==8)))
                {
                    g2d.setColor(Color.RED);
                }
                else if((i+j)%2==0)
                {
                    g2d.setColor(Color.WHITE);
                }
                else
                {
                    g2d.setColor(Color.MAGENTA);
                }

                Rectangle r = new Rectangle(j*size,i*size,size,size);
                g2d.draw(r);
                g2d.fill(r);

                String imgPath = "";
                switch(pNumArr[i][j])
                {
                    case 0:
                        imgPath = "images/empty.png";
                        break;
                    case 8:
                        imgPath = "images/bPawn.png";
                        break;
                    case 9:
                        imgPath = "images/bKnight.png";
                        break;
                    case 10:
                        imgPath = "images/bBishop.png";
                        break;
                    case 11:
                        imgPath = "images/bRook.png";
                        break;
                    case 12:
                        imgPath = "images/bQueen.png";
                        break;
                    case 13:
                        imgPath = "images/bKing.png";
                        break;
                    case 16:
                        imgPath = "images/wPawn.png";
                        break;
                    case 17:
                        imgPath = "images/wKnight.png";
                        break;
                    case 18:
                        imgPath = "images/wBishop.png";
                        break;
                    case 19:
                        imgPath = "images/wRook.png";
                        break;
                    case 20:
                        imgPath = "images/wQueen.png";
                        break;
                    case 21:
                        imgPath = "images/wKing.png";
                        break;
                }


                g.drawImage(new ImageIcon(imgPath).getImage(),j*size,i*size,this);
                
                if((whitePawnPromotion)&&(i==3||i==4)&&(j==3||j==4))
                {
                    g2d.setColor(Color.CYAN);
                    g2d.draw(r);
                    g2d.fill(r);
                }
                String color = "w";
                if(aiIsWhite)
                {
                    color = "b";
                }

                if(whitePawnPromotion)
                {
                    g.drawImage(new ImageIcon(color+"Knight.png").getImage(),size*3,size*3,this);
                    g.drawImage(new ImageIcon(color+"Bishop.png").getImage(),size*4,size*3,this);
                    g.drawImage(new ImageIcon(color+"Rook.png").getImage(),size*3,size*4,this);
                    g.drawImage(new ImageIcon(color+"Queen.png").getImage(),size*4,size*4,this);
                }
                /*else if(blackPawnPromotion)
                {
                    g.drawImage(new ImageIcon("bKnight.png").getImage(),size*3,size*3,this);
                    g.drawImage(new ImageIcon("bBishop.png").getImage(),size*4,size*3,this);
                    g.drawImage(new ImageIcon("bRook.png").getImage(),size*3,size*4,this);
                    g.drawImage(new ImageIcon("bQueen.png").getImage(),size*4,size*4,this);
                }*/
            }
        }

        if(aiIsWhite)
        {
            invertParr();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(size*8+17,size*8+40);
    }

    public void move(int x, int y, int x2, int y2)
    {
        //check color moved
        //check if legal move
        //make move
        int[] tempArr = {y2,x2};
        //System.out.println(Arrays.toString(tempArr));
        //System.out.println("legal moves: ");
        //System.out.println("in check: "+detectCheck(whiteMove));
        positions.add(new Position(this,getParrCopy(),whiteEnPassantPositions,blackEnPassantPositions,getWhiteCastleConditions(),getBlackCastleConditions(),whiteCastled,blackCastled));

        if((whiteMove&&((pNumArr[y][x]&16)==16)||!whiteMove&&((pNumArr[y][x]&8)==8)&&inList(legalMove(x,y,false,false),tempArr)))
        {
            if(!inCheckAfterMove(x,y,x2,y2,whiteMove))
            {
                if(((pNumArr[y][x]^16)==0||(pNumArr[y][x]^8)==0)||(((pNumArr[y2][x2]&8)==8)&&whiteMove)||(((pNumArr[y2][x2]&16)==16)&&!whiteMove))
                {
                    drawMove=0;
                    if(whiteMove)
                    {
                        drawMove = -1;
                    }
                }
                else
                {
                    drawMove++;
                }


                if(whiteEnPassantLeft&&y2==y-1&&x2==x-1)
                {
                    parr[y][x-1] = new Piece("empty");
                    pNumArr[y][x-1] = 0;
                }
                else if(whiteEnPassantRight&&y2==y-1&&x2==x+1)
                {
                    parr[y][x+1] = new Piece("empty");
                    pNumArr[y][x+1] = 0;
                }
                else if(blackEnPassantLeft&&y2==y+1&&x2==x-1)
                {
                    parr[y][x-1] = new Piece("empty");
                    pNumArr[y][x-1] = 0;
                }
                else if(blackEnPassantRight&&y2==y+1&&x2==x+1)
                {
                    parr[y][x+1] = new Piece("empty");
                    pNumArr[y][x+1] = 0;
                }

                if(!aiIsWhite)
                {
                    if(whiteShortCastle&&x2==x+2&&y2==7&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&16)==16))
                    {
                        parr[7][7] = new Piece("empty");
                        parr[7][5] = new Piece("rook","white");
                        pNumArr[7][7] = 0;
                        pNumArr[7][5] = 16+3;
                        whiteCastled = true;
                    }
                    
                    if(whiteLongCastle&&x2==x-2&&y2==7&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&16)==16))
                    {
                        parr[7][0] = new Piece("empty");
                        parr[7][3] = new Piece("rook","white");
                        pNumArr[7][0] = 0;
                        pNumArr[7][3] = 16+3;
                        whiteCastled = true;
                    }
                    if(blackShortCastle&&x2==x+2&&y2==0&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8))
                    {
                        parr[0][7] = new Piece("empty");
                        parr[0][5] = new Piece("rook","black");
                        pNumArr[0][7] = 0;
                        pNumArr[0][5] = 8+3;
                        blackCastled = true;
                    }
                    if(blackLongCastle&&x2==x-2&&y2==0&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8))
                    {
                        parr[0][0] = new Piece("empty");
                        parr[0][3] = new Piece("rook","black");
                        pNumArr[0][0] = 0;
                        pNumArr[0][3] = 8+3;
                        blackCastled = true;
                    }
                }
                else
                {
                    if(whiteShortCastle&&x2==x-2&&y2==7&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&16)==16))
                    {
                        parr[7][0] = new Piece("empty");
                        parr[7][2] = new Piece("rook","white");
                        pNumArr[7][0] = 0;
                        pNumArr[7][2] = 16+3;
                        whiteCastled = true;
                    }
                    if(whiteLongCastle&&x2==x+2&&y2==7&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&16)==16))
                    {
                        parr[7][7] = new Piece("empty");
                        parr[7][4] = new Piece("rook","white");
                        pNumArr[7][7] = 0;
                        pNumArr[7][4] = 16+3;
                        whiteCastled = true;
                    }
                    if(blackShortCastle&&x2==x-2&&y2==0&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8))
                    {
                        parr[0][0] = new Piece("empty");
                        parr[0][2] = new Piece("rook","black");
                        pNumArr[0][0] = 0;
                        pNumArr[0][2] = 8+3;
                        blackCastled = true;
                    }
                    if(blackLongCastle&&x2==x+2&&y2==0&&((pNumArr[y][x]^21)==0||(pNumArr[y][x]^13)==0)&&((pNumArr[y][x]&8)==8))
                    {
                        parr[0][7] = new Piece("empty");
                        parr[0][4] = new Piece("rook","black");
                        pNumArr[0][7] = 0;
                        pNumArr[0][4] = 8+3;
                        blackCastled = true;
                    }
                }


                Piece temp = parr[y][x];
                temp.increaseMoved();
                parr[y][x] = new Piece("empty");
                parr[y2][x2] = temp;

                

                int tempN = pNumArr[y][x];
                pNumArr[y][x] = 0;
                pNumArr[y2][x2] = tempN;

                for(int r=0;r<8;r++)
                {
                    for(int c=0;c<8;c++)
                    {
                        if(r==y2&&c==x2)
                        {
                            parr[r][c].resetMovesSinceLastMove();
                        }
                        else{
                            parr[r][c].updateMovesSinceLastMove();
                        }
                    }
                };

                checkPawnPromotion();


                checkGameOver();
                whiteEnPassantLeft = false;
                whiteEnPassantRight= false;
                blackEnPassantLeft = false;
                blackEnPassantRight = false;

                whiteShortCastle = false;
                whiteLongCastle = false;
                blackShortCastle = false;
                blackLongCastle = false;

                whiteEnPassantPositions = new boolean[8];
                blackEnPassantPositions = new boolean[8];

            
                whiteMove = !whiteMove;
                moves++;
            }
        }
    }

    public void checkPawnPromotion()
    {
        for(int i=0;i<8;i++)
        {
            if(((pNumArr[0][i]^16)==0||(pNumArr[0][i]^8)==0)&&((pNumArr[0][i]&16)==16))
            {
                whitePawnPromotion = true;
                pawnPromotionSpot = i;
            }
            if(((pNumArr[7][i]^16)==0||(pNumArr[7][i]^8)==0)&&((pNumArr[7][i]&8)==8))
            {
                blackPawnPromotion = true;
                pawnPromotionSpot = i;
            }
        }
    }
    
    public ArrayList<int[]> legalMove(int x, int y,boolean skipKingCastle,boolean includePieceDefense)
    {
        //System.out.println(parr[y][x]);
        //System.out.println("from: "+x+", "+y);
        ArrayList<int[]> legalMovesList = new ArrayList<int[]>();
        int testNum = pNumArr[y][x];

        if(pNumArr[y][x]>=16)
        {
            testNum-=16;
        }
        else
        {
            testNum-=8;
        }

        switch(testNum)
        {
            case 0:
                
                if(y==0||y==7)
                {
                    break;
                }
                if(((pNumArr[y][x]&16)==16))
                {
                    //System.out.println("white pawn");
                    if(((pNumArr[y-1][x]&31)==0))
                    {
                        int[] temp = {y-1,x};
                        legalMovesList.add(temp);
                    }
                    if(y>1)
                    if(((pNumArr[y-2][x]&31)==0)&&parr[y][x].getMoves()==0&&((pNumArr[y-1][x]&31)==0))
                    {
                        int[] temp = {y-2,x};
                        legalMovesList.add(temp);
                    }
                    if(x!=0)
                    if(((pNumArr[y-1][x-1]&8)==8))
                    {
                        int[] temp = {y-1,x-1};
                        legalMovesList.add(temp);
                    }
                    if(x!=7)
                    if(((pNumArr[y-1][x+1]&8)==8))
                    {
                        int[] temp = {y-1,x+1};
                        legalMovesList.add(temp);
                    }

                    //check en passant
                    //System.out.println("x: "+x+"\ny: "+y);
                    if(y==3)
                    {
                        if(x!=0)
                        if(((pNumArr[y][x-1]^16)==0||(pNumArr[y][x-1]^8)==0)&&((pNumArr[y][x-1]&8)==8)&&parr[y][x-1].getMoves()==1&&parr[y][x-1].getMOvesSinceLastMove()==0)
                        {
                            int[] temp = {y-1,x-1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                                whiteEnPassantLeft = true;
                                whiteEnPassantPositions[x]=true;
                            }
                        }
                        if(x!=7)
                        {
                            

                        if(((pNumArr[y][x+1]^16)==0||(pNumArr[y][x+1]^8)==0)&&((pNumArr[y][x+1]&8)==8)&&parr[y][x+1].getMoves()==1&&parr[y][x+1].getMOvesSinceLastMove()==0)
                        {
                            int[] temp = {y-1,x+1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                                whiteEnPassantRight = true;
                                whiteEnPassantPositions[x]=true;
                            }
                        }
                    }
                    }

                    if(includePieceDefense)
                    {
                        if(x!=0)
                        {
                            int[] temp = {y-1,x-1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                            }
                        }
                        if(x!=7)
                        {
                            int[] temp = {y-1,x+1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                            }
                        }
                    }
                }
                else
                {
                    //System.out.println("black pawn");
                    if(((pNumArr[y+1][x]&31)==0))
                    {
                        int[] temp = {y+1,x};
                        legalMovesList.add(temp);
                    }
                    if(y<6)
                    if(((pNumArr[y+2][x]&31)==0)&&parr[y][x].getMoves()==0&&((pNumArr[y+1][x]&31)==0))
                    {
                        int[] temp = {y+2,x};
                        legalMovesList.add(temp);
                    }
                    if(x!=0)
                    if(((pNumArr[y+1][x-1]&16)==16))
                    {
                        int[] temp = {y+1,x-1};
                        legalMovesList.add(temp);
                    }
                    if(x!=7)
                    if(((pNumArr[y+1][x+1]&16)==16))
                    {
                        int[] temp = {y+1,x+1};
                        legalMovesList.add(temp);
                    }

                    //check en passant
                    if(y==4)
                    {
                        if(x!=0)
                        if(((pNumArr[y][x-1]^16)==0||(pNumArr[y][x-1]^8)==0)&&((pNumArr[y][x-1]&16)==16)&&parr[y][x-1].getMoves()==1&&parr[y][x-1].getMOvesSinceLastMove()==0)
                        {
                            int[] temp = {y+1,x-1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                                blackEnPassantLeft = true;
                                blackEnPassantPositions[x]=true;
                            }
                        }
                        if(x!=7)
                        if(((pNumArr[y][x+1]^16)==0||(pNumArr[y][x+1]^8)==0)&&((pNumArr[y][x+1]&16)==16)&&parr[y][x+1].getMoves()==1&&parr[y][x+1].getMOvesSinceLastMove()==0)
                        {
                            int[] temp = {y+1,x+1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                                blackEnPassantRight = true;
                                blackEnPassantPositions[x]=true;
                            }
                        }
                    }

                    if(includePieceDefense)
                    {
                        if(x!=0)
                        {
                            int[] temp = {y+1,x-1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                            }
                        }
                        if(x!=7)
                        {
                            int[] temp = {y+1,x+1};
                            if(!inList(legalMovesList,temp))
                            {
                                legalMovesList.add(temp);
                            }
                        }
                    }
                }
                break;
            case 1:
            //knight
                int[] move1 = {y+2,x+1};
                legalMovesList.add(move1);
                int[] move2 = {y+2,x-1};
                legalMovesList.add(move2);
                int[] move3 = {y-2,x+1};
                legalMovesList.add(move3);
                int[] move4 = {y-2,x-1};
                legalMovesList.add(move4);
                int[] move5 = {y+1,x+2};
                legalMovesList.add(move5);
                int[] move6 = {y+1,x-2};
                legalMovesList.add(move6);
                int[] move7 = {y-1,x+2};
                legalMovesList.add(move7);
                int[] move8 = {y-1,x-2};
                legalMovesList.add(move8);

                for(int i=0;i<legalMovesList.size();i++)
                {
                    int movex = legalMovesList.get(i)[1];
                    int movey = legalMovesList.get(i)[0];

                    if(movex<0||movex>7||movey<0||movey>7)
                    {
                        legalMovesList.remove(i);
                        i--;
                        continue;
                    }
                    if((pNumArr[y][x]>>3==pNumArr[movey][movex]>>3)&&!includePieceDefense)
                    {
                        legalMovesList.remove(i);
                        i--;
                    }
                }
                break;
            case 2:
                //bishop
                legalMovesList = calculateBishop(legalMovesList, x,y,includePieceDefense);
                break;

            case 3:
                //rook
                legalMovesList = calculateRook(legalMovesList,x,y,includePieceDefense);
                break;
            case 4:
                //queen
                legalMovesList = calculateBishop(legalMovesList, x,y,includePieceDefense);
                legalMovesList = calculateRook(legalMovesList, x,y,includePieceDefense);
                break;
            case 5:
                int[] kmove1 = {y,x+1};
                legalMovesList.add(kmove1);
                int[] kmove2 = {y,x-1};
                legalMovesList.add(kmove2);
                int[] kmove3 = {y-1,x};
                legalMovesList.add(kmove3);
                int[] kmove4 = {y+1,x};
                legalMovesList.add(kmove4);
                int[] kmove5 = {y+1,x+1};
                legalMovesList.add(kmove5);
                int[] kmove6 = {y+1,x-1};
                legalMovesList.add(kmove6);
                int[] kmove7 = {y-1,x+1};
                legalMovesList.add(kmove7);
                int[] kmove8 = {y-1,x-1};
                legalMovesList.add(kmove8);

                for(int i=0;i<legalMovesList.size();i++)
                {
                    int movex = legalMovesList.get(i)[1];
                    int movey = legalMovesList.get(i)[0];

                    if(movex<0||movex>7||movey<0||movey>7)
                    {
                        legalMovesList.remove(i);
                        i--;
                        continue;
                    }
                    if((pNumArr[y][x]>>3==pNumArr[movey][movex]>>3)&&!includePieceDefense)
                    {
                        legalMovesList.remove(i);
                        i--;
                    }
                }




                //I don't really like this method but it seems to work
                if(skipKingCastle||(((pNumArr[y][x]&16)==16)&&whiteCastled)||(((pNumArr[y][x]&8)==8)&&blackCastled))
                {
                    break;
                }
                //check for castleing
                if(!aiIsWhite)
                {
                    if(x==4)
                    if(((pNumArr[y][x]&16)==16)&&parr[y][x].getMoves()==0&&((pNumArr[y][x+1]&31)==0)&&((pNumArr[y][x+2]&31)==0)&&((pNumArr[y][x+3]^19)==0||(pNumArr[y][x+3]^11)==0)&&((pNumArr[y][x+3]&16)==16)&&parr[y][x+3].getMoves()==0&&!detectCheck(true,true))
                    {
                        //white short castle
                        //check casteling through check
                        ArrayList<int[]> blackMoves = getBlackMoves(true);
                        //System.out.println("white short castle");

                        int[] checkOne = {y,x+1};
                        int[] checkTwo = {y,x+2};
                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(blackMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(blackMoves,checkTwo));

                        whiteShortCastle = !(inList(blackMoves,checkOne)||inList(blackMoves,checkTwo));
                        if(whiteShortCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==4)
                    if(((pNumArr[y][x]&16)==16)&&parr[y][x].getMoves()==0&&((pNumArr[y][x-1]&31)==0)&&((pNumArr[y][x-2]&31)==0)&&((pNumArr[y][x-3]&31)==0)&&((pNumArr[y][x-4]^19)==0||(pNumArr[y][x-4]^11)==0)&&((pNumArr[y][x-4]&16)==16)&&parr[y][x-4].getMoves()==0&&!detectCheck(true,true))
                    {
                        //white long castle
                        //check casteling through check
                        //System.out.println("whiteLongCastle");

                        ArrayList<int[]> blackMoves = getBlackMoves(true);
                        //System.out.println("blackMoves: ");

                        int[] checkOne = {y,x-1};
                        int[] checkTwo = {y,x-2};
                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(blackMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(blackMoves,checkTwo));

                        whiteLongCastle = !(inList(blackMoves,checkOne)||inList(blackMoves,checkTwo));

                        if(whiteLongCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==4)
                    if(((pNumArr[y][x]&8)==8)&&parr[y][x].getMoves()==0&&((pNumArr[y][x+1]&31)==0)&&((pNumArr[y][x+2]&31)==0)&&((pNumArr[y][x+3]^19)==0||(pNumArr[y][x+3]^11)==0)&&((pNumArr[y][x+3]&8)==8)&&parr[y][x+3].getMoves()==0&&!detectCheck(false,true))
                    {
                        //black short castle
                        //check casteling through check
                        //System.out.println("black short castle");

                        ArrayList<int[]> whiteMoves = getWhiteMoves(true);

                        int[] checkOne = {y,x+1};
                        int[] checkTwo = {y,x+2};

                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(whiteMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(whiteMoves,checkTwo));



                        blackShortCastle = !(inList(whiteMoves,checkOne)||inList(whiteMoves,checkTwo));

                        if(blackShortCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==4)
                    if(((pNumArr[y][x]&8)==8)&&parr[y][x].getMoves()==0&&((pNumArr[y][x-1]&31)==0)&&((pNumArr[y][x-2]&31)==0)&&((pNumArr[y][x-3]&31)==0)&&((pNumArr[y][x-4]^19)==0||(pNumArr[y][x-4]^11)==0)&&((pNumArr[y][x-4]&8)==8)&&parr[y][x-4].getMoves()==0&&!detectCheck(false,true))
                    {
                        //black long castle
                        //check casteling through check

                        ArrayList<int[]> whiteMoves = getWhiteMoves(true);

                        int[] checkOne = {y,x-1};
                        int[] checkTwo = {y,x-2};

                        blackLongCastle = !(inList(whiteMoves,checkOne)||inList(whiteMoves,checkTwo));

                        if(blackLongCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }
                }
                else
                {
                    if(x==3)
                    if(((pNumArr[y][x]&16)==16)&&parr[y][x].getMoves()==0&&((pNumArr[y][x-1]&31)==0)&&((pNumArr[y][x-2]&31)==0)&&((pNumArr[y][x-3]^19)==0||(pNumArr[y][x-3]^11)==0)&&((pNumArr[y][x-3]&16)==16)&&parr[y][x-3].getMoves()==0&&!detectCheck(true,true))
                    {
                        //white short castle
                        //check casteling through check
                        ArrayList<int[]> blackMoves = getBlackMoves(true);
                        //System.out.println("white short castle");

                        int[] checkOne = {y,x-1};
                        int[] checkTwo = {y,x-2};
                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(blackMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(blackMoves,checkTwo));

                        whiteShortCastle = !(inList(blackMoves,checkOne)||inList(blackMoves,checkTwo));
                        if(whiteShortCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==3)
                    if(((pNumArr[y][x]&16)==16)&&parr[y][x].getMoves()==0&&((pNumArr[y][x+1]&31)==0)&&((pNumArr[y][x+2]&31)==0)&&((pNumArr[y][x+3]&31)==0)&&((pNumArr[y][x+4]^19)==0||(pNumArr[y][x+4]^11)==0)&&((pNumArr[y][x+4]&16)==16)&&parr[y][x+4].getMoves()==0&&!detectCheck(true,true))
                    {
                        //white long castle
                        //check casteling through check
                        //System.out.println("whiteLongCastle");

                        ArrayList<int[]> blackMoves = getBlackMoves(true);
                        //System.out.println("blackMoves: ");

                        int[] checkOne = {y,x+1};
                        int[] checkTwo = {y,x+2};
                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(blackMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(blackMoves,checkTwo));

                        whiteLongCastle = !(inList(blackMoves,checkOne)||inList(blackMoves,checkTwo));

                        if(whiteLongCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==3)
                    if(((pNumArr[y][x]&8)==8)&&parr[y][x].getMoves()==0&&((pNumArr[y][x-1]&31)==0)&&((pNumArr[y][x-2]&31)==0)&&((pNumArr[y][x-3]^19)==0||(pNumArr[y][x-3]^11)==0)&&((pNumArr[y][x-3]&8)==8)&&parr[y][x-3].getMoves()==0&&!detectCheck(true,true))
                    {
                        //black short castle
                        //check casteling through check
                        //System.out.println("black short castle");

                        ArrayList<int[]> whiteMoves = getWhiteMoves(true);

                        int[] checkOne = {y,x-1};
                        int[] checkTwo = {y,x-2};

                        //System.out.println(Arrays.toString(checkOne));
                        //System.out.println(Arrays.toString(checkTwo));


                        //System.out.println("checkOne: "+inList(whiteMoves,checkOne));
                        //System.out.println("checkTwo: "+inList(whiteMoves,checkTwo));



                        blackShortCastle = !(inList(whiteMoves,checkOne)||inList(whiteMoves,checkTwo));

                        if(blackShortCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }

                    if(x==3)
                    if(((pNumArr[y][x]&8)==8)&&parr[y][x].getMoves()==0&&((pNumArr[y][x+1]&31)==0)&&((pNumArr[y][x+2]&31)==0)&&((pNumArr[y][x+3]&31)==0)&&((pNumArr[y][x+4]^19)==0||(pNumArr[y][x+4]^11)==0)&&((pNumArr[y][x+4]&8)==8)&&parr[y][x+4].getMoves()==0&&!detectCheck(true,true))
                    {
                        //black long castle
                        //check casteling through check

                        ArrayList<int[]> whiteMoves = getWhiteMoves(true);

                        int[] checkOne = {y,x+1};
                        int[] checkTwo = {y,x+2};

                        blackLongCastle = !(inList(whiteMoves,checkOne)||inList(whiteMoves,checkTwo));

                        if(blackLongCastle)
                        {
                            legalMovesList.add(checkTwo);
                        }
                    }
                
                }
                break;
            
            case 6:
                break;
        }
        return legalMovesList;
    }

    public boolean inCheckAfterMove(int x,int y,int x2,int y2,boolean white)
    {
        //if true, white's move
        Piece enPassantPiece = new Piece("empty");
        int enPassantNum = 0;
        if(whiteEnPassantLeft&&y2==y-1&&x2==x-1)
        {
            enPassantPiece = parr[y][x-1];
            parr[y][x-1] = new Piece("empty");

            enPassantNum = pNumArr[y][x-1];
            pNumArr[y][x-1] = 0;
        }
        else if(whiteEnPassantRight&&y2==y-1&&x2==x+1)
        {
            enPassantPiece = parr[y][x+1];
            parr[y][x+1] = new Piece("empty");
            
            enPassantNum = pNumArr[y][x+1];
            pNumArr[y][x+1] = 0;

        }
        else if(blackEnPassantLeft&&y2==y+1&&x2==x-1)
        {
            enPassantPiece = parr[y][x-1];
            parr[y][x-1] = new Piece("empty");
        }
        else if(blackEnPassantRight&&y2==y+1&&x2==x+1)
        {
            enPassantPiece = parr[y][x+1];
            parr[y][x+1] = new Piece("empty");

            enPassantNum = pNumArr[y][x+1];
            pNumArr[y][x+1] = 0;
        }


        int pieceMoved = pNumArr[y][x];
        int pieceReplaced = pNumArr[y2][x2];
        pNumArr[y2][x2] = pieceMoved;
        pNumArr[y][x] = 0;
        boolean inCheckAfterMove = detectCheck(white,false);
        pNumArr[y][x] = pieceMoved;
        pNumArr[y2][x2] = pieceReplaced;

        if(whiteEnPassantLeft&&y2==y-1&&x2==x-1)
        {
            pNumArr[y][x-1] = enPassantNum;
        }
        else if(whiteEnPassantRight&&y2==y-1&&x2==x+1)
        {
            pNumArr[y][x+1] = enPassantNum;
        }
        else if(blackEnPassantLeft&&y2==y+1&&x2==x-1)
        {
            pNumArr[y][x-1] = enPassantNum;
        }
        else if(blackEnPassantRight&&y2==y+1&&x2==x+1)
        {
            pNumArr[y][x+1] = enPassantNum;
        }
        return inCheckAfterMove;
    }

    public boolean detectCheck(boolean white,boolean skipKingCastle)
    {
        int kx = -1;
        int ky = -1;

        ArrayList<int[]> legalMoves = new ArrayList<int[]>();

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if((((pNumArr[j][i]&16)==16)&&white||((pNumArr[j][i]&8)==8)&&!white)&&((pNumArr[j][i]^21)==0||(pNumArr[j][i]^13)==0))
                {
                    kx = i;
                    ky = j;
                }
                if((((pNumArr[j][i]&16)==16)&&!white||((pNumArr[j][i]&8)==8)&&white))
                {
                    legalMoves.addAll(legalMove(i,j,skipKingCastle,false));
                }
            }
        }
        int[] karr = {ky,kx};
        return inList(legalMoves,karr);
    }

    public boolean detectCheckMate(boolean white)
    {
        //if white is true, white is being detected for checkmate

        ArrayList<int[]> moves = new ArrayList<int[]>();
        if(white)
        {
            //System.out.println("white");
            moves = getWhiteMoves2(true,false);
        }
        else
        {
            //System.out.println("black");
            moves = getBlackMoves2(true,false);
        }

        for(int i=0;i<moves.size();i++)
        {
            //see if in check after each move
            if(inCheckAfterMove(moves.get(i)[0], moves.get(i)[1], moves.get(i)[2], moves.get(i)[3], white))
            {
                moves.remove(i);
                i--;
            }
        }

        if(moves.size()==0&&detectCheck(white,true))
        {
            return true;
        }
        
        return false;
    }

    public boolean detectDraw()
    {

        //stalemate
        ArrayList<int[]> whiteMoves = getWhiteMoves2(true,false);
        ArrayList<int[]> blackMoves = getBlackMoves2(true,false);

        for(int i=0;i<whiteMoves.size();i++)
        {
            //see if in check after each move
            //if(inCheckAfterMove(wkx, wky, whiteMoves.get(i)[1], whiteMoves.get(i)[0], true))
            if(inCheckAfterMove(whiteMoves.get(i)[0],whiteMoves.get(i)[1],whiteMoves.get(i)[2],whiteMoves.get(i)[3],true))
            {
                whiteMoves.remove(i);
                i--;
            }
        }
        for(int i=0;i<blackMoves.size();i++)
        {
            //see if in check after each move
            //if(inCheckAfterMove(bkx, bky, blackMoves.get(i)[1], blackMoves.get(i)[0], false))
            if(inCheckAfterMove(blackMoves.get(i)[0],blackMoves.get(i)[1],blackMoves.get(i)[2],blackMoves.get(i)[3],true))
            {
                blackMoves.remove(i);
                i--;
            }
        }
        if(whiteMoves.size()==0||blackMoves.size()==0)
        {
            System.out.println("Draw by stalemate.");
            return true;
        }

        //draw by 50 moves
        if(drawMove==100)
        {
            System.out.println("Draw by 50 moves without pawn move or piece capture.");
            return true;
        }

        //draw by insufficient material
        boolean insufficientMmaterial = true;
        int whiteNumKnights = 0;
        int blackNumKnights = 0;
        int whiteNumBishop = 0;
        int blackNumBishop = 0;

outsideLoop: for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(((pNumArr[j][i]^20)==0||(pNumArr[j][i]^12)==0)||((pNumArr[j][i]^19)==0||(pNumArr[j][i]^11)==0)||((pNumArr[j][i]^16)==0||(pNumArr[j][i]^8)==0))
                {
                    insufficientMmaterial = false;
                    break outsideLoop;
                }
                if(((pNumArr[j][i]^18)==0||(pNumArr[j][i]^10)==0)&&((pNumArr[j][i]&16)==16))
                {
                    whiteNumBishop++;
                }
                if(((pNumArr[j][i]^18)==0||(pNumArr[j][i]^10)==0)&&((pNumArr[j][i]&8)==8))
                {
                    blackNumBishop++;
                }
                if(((pNumArr[j][i]^17)==0||(pNumArr[j][i]^9)==0)&&((pNumArr[j][i]&16)==16))
                {
                    whiteNumKnights++;
                }
                if(((pNumArr[j][i]^17)==0||(pNumArr[j][i]^9)==0)&&((pNumArr[j][i]&8)==8))
                {
                    blackNumKnights++;
                }
            }
        }

        int whiteMinorPiece = whiteNumBishop+whiteNumKnights;
        int blackMinorPiece = blackNumBishop+blackNumKnights;

        if(whiteMinorPiece<=1&&blackMinorPiece<=1&&insufficientMmaterial)
        {
            System.out.println("Draw by insufficient material.");
            return true;
        }
        if(((whiteNumKnights==2&&blackMinorPiece==0)||(blackNumKnights==2&&whiteMinorPiece==0))&&insufficientMmaterial)
        {
            System.out.println("Draw by insufficient material.");
            return true;
        }

        //draw by repition

        //check if all three are the same

        boolean[] whiteCastleConditions = new boolean[2];
        whiteCastleConditions[0] = whiteShortCastle;
        whiteCastleConditions[1] = whiteLongCastle;

        boolean[] blackCastleConditions = new boolean[2];
        blackCastleConditions[0] = blackShortCastle;
        blackCastleConditions[1] = blackLongCastle;

        //positions.add(new Position(this,getParrCopy(),whiteEnPassantPositions,blackEnPassantPositions,whiteCastleConditions,blackCastleConditions));

        //System.out.println("\n\n\n\n");
        //System.out.println(positions);
        /*for(int i=0;i<positions.size();i++)
        {
            for(int j=i+1;j<positions.size();j++)
            {
                for(int k=j+1;k<positions.size();k++)
                {
                    if(positions.get(i).equals(positions.get(j))&&positions.get(j).equals(positions.get(k)))
                    {
                        /*System.out.println("i: "+i);
                        System.out.println("j: "+j);
                        System.out.println("k: "+k);
                        System.out.println("size: "+positions.size());
                        System.out.println(positions);
                        System.out.println("Draw by repition.");
                        return true;
                    }
                }
            }
        }*/
        return false;
    }
    
    public ArrayList<int[]> calculateBishop(ArrayList<int[]> legalMovesList,int x, int y,boolean includePieceDefense)
    {
        //top right
        for(int i=1;i<8;i++)
        {
            if(y-i<0||x+i>7)
            {
                break;
            }
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y-i][x+i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y-i][x+i]&16)==16)))
            {
                int[] octemp = {y-i,x+i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y-i][x+i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y-i,x+i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] btemp = {y-i,x+i};
            legalMovesList.add(btemp);    
        }
        //bottom right
        for(int i=1;i<8;i++)
        {
            if(y+i>7||x+i>7)
            {
                break;
            }
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y+i][x+i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y+i][x+i]&16)==16)))
            {
                int[] octemp = {y+i,x+i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y+i][x+i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y+i,x+i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] btemp = {y+i,x+i};
            legalMovesList.add(btemp);    
        }
        //botton left
        for(int i=1;i<8;i++)
        {
            if(y+i>7||x-i<0)
            {
                break;
            }
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y+i][x-i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y+i][x-i]&16)==16)))
            {
                int[] octemp = {y+i,x-i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y+i][x-i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y+i,x-i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] btemp = {y+i,x-i};
            legalMovesList.add(btemp);    
        }
        //top left
        for(int i=1;i<8;i++)
        {
            if(y-i<0||x-i<0)
            {
                break;
            }
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y-i][x-i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y-i][x-i]&16)==16)))
            {
                int[] octemp = {y-i,x-i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y-i][x-i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y-i,x-i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] btemp = {y-i,x-i};
            legalMovesList.add(btemp);    
        }
        return legalMovesList;
    }

    public ArrayList<int[]> calculateRook(ArrayList<int[]> legalMovesList,int x, int y,boolean includePieceDefense)
    {
        for(int i=x+1;i<8;i++)
        {
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y][i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y][i]&16)==16)))
            {
                int[] octemp = {y,i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y][i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y,i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] rtemp = {y,i};
            legalMovesList.add(rtemp);    
        }
        for(int i=x-1;i>=0;i--)
        {
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[y][i]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[y][i]&16)==16)))
            {
                int[] octemp = {y,i};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[y][i]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {y,i};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] rtemp = {y,i};
            legalMovesList.add(rtemp);    
        }
        for(int i=y+1;i<8;i++)
        {
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[i][x]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[i][x]&16)==16)))
            {
                int[] octemp = {i,x};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[i][x]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {i,x};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] rtemp = {i,x};
            legalMovesList.add(rtemp);    
        }
        for(int i=y-1;i>=0;i--)
        {
            //System.out.println("here");
            if((((pNumArr[y][x]&16)==16)&&((pNumArr[i][x]&8)==8))||(((pNumArr[y][x]&8)==8)&&((pNumArr[i][x]&16)==16)))
            {
                int[] octemp = {i,x};
                legalMovesList.add(octemp);
                break;
            }
            if((pNumArr[y][x]>>3==pNumArr[i][x]>>3))
            {
                if(includePieceDefense)
                {
                    int[] octemp = {i,x};
                    legalMovesList.add(octemp);
                }
                break;
            }
            int[] rtemp = {i,x};
            legalMovesList.add(rtemp);    
        }
        return legalMovesList;
    }
    
    public ArrayList<int[]> getWhiteMoves(boolean skipKingCastle)
    {
        boolean[] wepp = whiteEnPassantPositions;
        boolean[] bepp = blackEnPassantPositions;
        boolean wepl = whiteEnPassantLeft;
        boolean wepr = whiteEnPassantRight;
        boolean bepl = blackEnPassantLeft;
        boolean bepr = blackEnPassantRight;
        boolean wsc = whiteShortCastle;
        boolean wlc = whiteLongCastle;
        boolean bsc = blackShortCastle;
        boolean blc = blackLongCastle;
        ArrayList<int[]> al = new ArrayList<int[]>();
        for(int r=0;r<8;r++)
        {
            for(int c=0;c<8;c++)
            {
                if(((pNumArr[c][r]&16)==16))
                {
                    al.addAll(legalMove(r,c,skipKingCastle,false));
                }
            }
        }
        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        whiteEnPassantLeft = wepl;
        whiteEnPassantRight = wepr;
        blackEnPassantLeft = bepl;
        blackEnPassantRight = bepr;

        whiteShortCastle = wsc;
        whiteLongCastle = wlc;
        blackShortCastle = bsc;
        blackLongCastle = blc;
        return al;
    }

    public ArrayList<int[]> getBlackMoves(boolean skipKingCastle)
    {
        boolean[] wepp = whiteEnPassantPositions;
        boolean[] bepp = blackEnPassantPositions;
        boolean wepl = whiteEnPassantLeft;
        boolean wepr = whiteEnPassantRight;
        boolean bepl = blackEnPassantLeft;
        boolean bepr = blackEnPassantRight;
        boolean wsc = whiteShortCastle;
        boolean wlc = whiteLongCastle;
        boolean bsc = blackShortCastle;
        boolean blc = blackLongCastle;
        ArrayList<int[]> al = new ArrayList<int[]>();
        for(int r=0;r<8;r++)
        {
            for(int c=0;c<8;c++)
            {
                if(((pNumArr[c][r]&8)==8))
                {
                    al.addAll(legalMove(r,c,skipKingCastle,false));
                }
            }
        }
        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        whiteEnPassantLeft = wepl;
        whiteEnPassantRight = wepr;
        blackEnPassantLeft = bepl;
        blackEnPassantRight = bepr;

        whiteShortCastle = wsc;
        whiteLongCastle = wlc;
        blackShortCastle = bsc;
        blackLongCastle = blc;
        return al;
    }
   
    public ArrayList<int[]> getWhiteMoves2(boolean skipKingCastle,boolean includePieceDefense)
    {
        boolean[] wepp = whiteEnPassantPositions;
        boolean[] bepp = blackEnPassantPositions;
        boolean wepl = whiteEnPassantLeft;
        boolean wepr = whiteEnPassantRight;
        boolean bepl = blackEnPassantLeft;
        boolean bepr = blackEnPassantRight;
        boolean wsc = whiteShortCastle;
        boolean wlc = whiteLongCastle;
        boolean bsc = blackShortCastle;
        boolean blc = blackLongCastle;
        ArrayList<int[]> al = new ArrayList<int[]>();
        for(int r=0;r<8;r++)
        {
            for(int c=0;c<8;c++)
            {
                if(((pNumArr[c][r]&16)==16))
                {
                    ArrayList<int[]> legalMoves = legalMove(r,c,skipKingCastle,includePieceDefense);

                    for(int i=0;i<legalMoves.size();i++)
                    {
                        int[] temptemp = new int[4];
                        temptemp[0] = r;
                        temptemp[1] = c;
                        temptemp[2] = legalMoves.get(i)[1];
                        temptemp[3] = legalMoves.get(i)[0];
                        al.add(temptemp);
                    }

                }
            }
        }

        for(int i=0;i<al.size();i++)
        {
            if(inCheckAfterMove(al.get(i)[0], al.get(i)[1], al.get(i)[2], al.get(i)[3], true))
            {
                al.remove(i);
                i--;
            }
        }
        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        whiteEnPassantLeft = wepl;
        whiteEnPassantRight = wepr;
        blackEnPassantLeft = bepl;
        blackEnPassantRight = bepr;

        whiteShortCastle = wsc;
        whiteLongCastle = wlc;
        blackShortCastle = bsc;
        blackLongCastle = blc;
        return al;
    }

    public ArrayList<int[]> getBlackMoves2(boolean skipKingCastle,boolean includePieceDefense)
    {
        boolean[] wepp = whiteEnPassantPositions;
        boolean[] bepp = blackEnPassantPositions;
        boolean wepl = whiteEnPassantLeft;
        boolean wepr = whiteEnPassantRight;
        boolean bepl = blackEnPassantLeft;
        boolean bepr = blackEnPassantRight;
        boolean wsc = whiteShortCastle;
        boolean wlc = whiteLongCastle;
        boolean bsc = blackShortCastle;
        boolean blc = blackLongCastle;
        ArrayList<int[]> al = new ArrayList<int[]>();
        for(int r=0;r<8;r++)
        {
            for(int c=0;c<8;c++)
            {
                if(((pNumArr[c][r]&8)==8))
                {
                    ArrayList<int[]> legalMoves = legalMove(r,c,skipKingCastle,includePieceDefense);
                    for(int i=0;i<legalMoves.size();i++)
                    {
                        int[] temptemp = new int[4];
                        temptemp[0] = r;
                        temptemp[1] = c;
                        temptemp[2] = legalMoves.get(i)[1];
                        temptemp[3] = legalMoves.get(i)[0];
                        al.add(temptemp);
                    }
                }
            }
        }

        for(int i=0;i<al.size();i++)
        {
            if(inCheckAfterMove(al.get(i)[0], al.get(i)[1], al.get(i)[2], al.get(i)[3], false))
            {
                al.remove(i);
                i--;
            }
        }

        whiteEnPassantPositions = wepp;
        blackEnPassantPositions = bepp;
        whiteEnPassantLeft = wepl;
        whiteEnPassantRight = wepr;
        blackEnPassantLeft = bepl;
        blackEnPassantRight = bepr;

        whiteShortCastle = wsc;
        whiteLongCastle = wlc;
        blackShortCastle = bsc;
        blackLongCastle = blc;
        return al;
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
   
    public void printParr()
    {
        for(Piece[] q:parr)
        {
            System.out.println(Arrays.toString(q));
        }
        System.out.println();

        System.out.println("pNumArr:");
        for(int[] row:pNumArr)
        {
            for(int num:row)
            {
                switch(num)
                {
                    case 0:
                        System.out.print("empty ");
                        break;
                    case 8:
                        System.out.print("bPawn ");
                        break;
                    case 9:
                        System.out.print("bKnight ");
                        break;
                    case 10:
                        System.out.print("bBishop ");
                        break;
                    case 11:
                        System.out.print("bRook ");
                        break;
                    case 12:
                        System.out.print("bQueen ");
                        break;
                    case 13:
                        System.out.print("bKing ");
                        break;
                    case 16:
                        System.out.print("wPawn ");
                        break;
                    case 17:
                        System.out.print("wKnight ");
                        break;
                    case 18:
                        System.out.print("wBishop ");
                        break;
                    case 19:
                        System.out.print("wRook ");
                        break;
                    case 20:
                        System.out.print("wQueen ");
                        break;
                    case 21:
                        System.out.print("wKing ");
                        break;
                }   
            }
            System.out.println();
        }
    }
    
    public Piece[][] getParrCopy()
    {
        Piece[][] parrCopy = new Piece[8][8];
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                try{
                    parrCopy[i][j] = new Piece(parr[i][j].getType(),parr[i][j].getColor(),parr[i][j].getMoves());
                } catch (Exception e){
                    parrCopy[i][j] = new Piece("empty");
                }
            }
        }
        return parrCopy;
    }

    public int[][] getPNumArrCopy()
    {
        int[][] pna = new int[8][8];
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                pna[i][j] = pNumArr[i][j];
            }
        }
        return pna;
    }

    public void checkGameOver()
    {
        if(detectCheck(true,false))
        {
            whiteInCheckMate = detectCheckMate(true);
        }
        if(detectCheck(false,false))
        {
            blackInCheckMate = detectCheckMate(false);
        }

        if(blackInCheckMate)
        {
            System.out.println("Congratulations player. You win!");
            gameOver = true;
        }
        else if(whiteInCheckMate)
        {
            System.out.println("Loser human lmao get rekt");
            gameOver = true;
        }
        else if(detectDraw())
        {
            gameOver = true;
            draw = true;
        }
        else
        {
            draw = false;
        }
    }

    public void aiTurn()
    {
        //int[] aiMove = RandomAI.getNextMove(board,false);
        //int[] aiMove = AIv1.getNextMove(positions.get(0),false);
        if(aiIsWhite)
        {
            //flipParr();
            //whiteMove = !whiteMove;
            //invertParr();
        }
        printParr();
        

        int[] aiMove = AIv2.getNextMove(new Position(this,getParrCopy(),whiteEnPassantPositions,blackEnPassantPositions,getWhiteCastleConditions(),getBlackCastleConditions(),whiteCastled,blackCastled),false);


        System.out.println(whiteMove);
        move(aiMove[0],aiMove[1],aiMove[2],aiMove[3]);

   
        if(aiIsWhite)
        {
            //flipParr();
            //whiteMove = !whiteMove;
            //invertParr();
        }

        paintImmediately(getVisibleRect());

        printParr();
        System.out.println(whiteMove);
        System.out.println("aiMove: "+Arrays.toString(aiMove));

        if(aiMove.length==5)
        {
            switch(aiMove[4])
            {
                case 0:
                    //queen
                    parr[aiMove[3]][aiMove[2]] = new Piece("queen","black");
                    break;
                case 1:
                    //rook
                    parr[aiMove[3]][aiMove[2]] = new Piece("rook","black");
                    break;
                case 2:
                    //bishop
                    parr[aiMove[3]][aiMove[2]] = new Piece("bishop","black");
                    break;
                case 3:
                    //knight
                    parr[aiMove[3]][aiMove[2]] = new Piece("knight","black");
                    break;
            }

            pNumArr[aiMove[3]][aiMove[2]] = 12-aiMove[4];
        }

   
        paintImmediately(getVisibleRect());
        checkGameOver();
    }

    public Board getCopyOfBoard()
    {
        Piece[][] parr2 = getParrCopy();

        int[][] pNumArr2 = getPNumArrCopy();

        int size2 = size;
        int prevClickX2 = prevClickX;
        int prevClickY2 = prevClickY;
        boolean whiteMove2 = whiteMove;
        boolean whiteInCheckMate2 = whiteInCheckMate;
        boolean blackInCheckMate2 = blackInCheckMate;
        boolean gameOver2 = gameOver;

        boolean whiteEnPassantLeft2 = whiteEnPassantLeft;
        boolean whiteEnPassantRight2 = whiteEnPassantRight;
        boolean blackEnPassantLeft2 = blackEnPassantLeft;
        boolean blackEnPassantRight2 = blackEnPassantRight;

        boolean whiteShortCastle2 = whiteShortCastle;
        boolean whiteLongCastle2 = whiteLongCastle;
        boolean blackShortCastle2 = blackShortCastle;
        boolean blackLongCastle2 = blackLongCastle;

        int pawnPromotionSpot2 = pawnPromotionSpot;

        boolean whitePawnPromotion2 = whitePawnPromotion;
        boolean blackPawnPromotion2 = blackPawnPromotion;

        int drawMove2 = drawMove;
        boolean[] whiteEnPassantPositions2 = new boolean[8];
        boolean[] blackEnPassantPositions2 = new boolean[8];
        for(int i=0;i<8;i++)
        {
            whiteEnPassantPositions2[i] = whiteEnPassantPositions[i];
            blackEnPassantPositions2[i] = blackEnPassantPositions[i];
        }
        ArrayList<Position> positions2 = new ArrayList<Position>();
        for(Position q:positions)
        {
            positions2.add(q);
        }

        int moves2 = moves;



        return new Board(parr2,size2,prevClickX2,prevClickY2,whiteMove2,whiteInCheckMate2,blackInCheckMate2,gameOver2,whiteEnPassantLeft2,
        whiteEnPassantRight2,blackEnPassantLeft2, blackEnPassantRight2,whiteShortCastle2,whiteLongCastle2,blackShortCastle2,blackLongCastle2,pawnPromotionSpot2,whitePawnPromotion2,
        blackPawnPromotion2,drawMove2,whiteEnPassantPositions2,blackEnPassantPositions2, positions2,moves2,aiIsWhite,pNumArr2);

    // Board board = this;
    }
    
    public Position getPositionAfterMove(int x1,int y1,int x2,int y2)
    {
        Board editBoard = getCopyOfBoard();
        /*for(Piece[] q:editBoard.getParrCopy())
        {
            for(Piece qq:q)
            {
                qq.resetMovesSinceLastMove();
            }
        }*/
        //System.out.println("editBoard gwepp before: "+Arrays.toString(editBoard.getWhiteEnPassantPositions()));

        if(y1==6&&y2==7&&editBoard.getParrCopy()[y1][x1].getColor().equals("black")&&editBoard.getParrCopy()[y1][x1].getType().equals("pawn"))
        {
            Board board1 = editBoard;

            Board board2 = getCopyOfBoard();

            Board board3 = getCopyOfBoard();
            
            Board board4 = getCopyOfBoard();

            /*tempParr1[y1][x1] = new Piece("empty");
            tempParr2[y1][x1] = new Piece("empty");
            tempParr3[y1][x1] = new Piece("empty");
            tempParr4[y1][x1] = new Piece("empty");*/

            board1.move(x1,y1,x2,y2);
            board2.move(x1,y1,x2,y2);
            board3.move(x1,y1,x2,y2);
            board4.move(x1,y1,x2,y2);

            Piece[][] tempParr1 = board1.getParrCopy();
            Piece[][] tempParr2 = board2.getParrCopy();
            Piece[][] tempParr3 = board3.getParrCopy();
            Piece[][] tempParr4 = board4.getParrCopy();


            tempParr1[y2][x2] = new Piece("queen","black");
            board1.setParr(tempParr1);
            Position queen = new Position(board1,tempParr1,board1.getWhiteEnPassantPositions(),board1.getBlackEnPassantPositions(),board1.getWhiteCastleConditions(),board1.getBlackCastleConditions(),board1.getWhiteCastled(),board1.getBlackCastled());
            
            tempParr2[y2][x2] = new Piece("rook","black");
            board2.setParr(tempParr2);
            Position rook = new Position(board2,tempParr2,board2.getWhiteEnPassantPositions(),board2.getBlackEnPassantPositions(),board2.getWhiteCastleConditions(),board2.getBlackCastleConditions(),board2.getWhiteCastled(),board2.getBlackCastled());
            
            tempParr3[y2][x2] = new Piece("bishop","black");
            board3.setParr(tempParr3);
            Position bishop = new Position(board3,tempParr3,board3.getWhiteEnPassantPositions(),board3.getBlackEnPassantPositions(),board3.getWhiteCastleConditions(),board3.getBlackCastleConditions(),board3.getWhiteCastled(),board3.getBlackCastled());
            
            tempParr4[y2][x2] = new Piece("knight","black");
            board4.setParr(tempParr4);
            Position knight = new Position(board4,tempParr4,board4.getWhiteEnPassantPositions(),board4.getBlackEnPassantPositions(),board4.getWhiteCastleConditions(),board4.getBlackCastleConditions(),board4.getWhiteCastled(),board4.getBlackCastled());

            Position [] posArr = {queen,rook,bishop,knight};

            int index = 0;
            double value = posArr[0].getEval();

            for(int i=1;i<posArr.length;i++)
            {
                System.out.println("eval: "+posArr[i].getEval());
                System.out.println(posArr[i]);
                if(posArr[i].getEval()<=value)
                {
                    index = i;
                    value = posArr[i].getEval();
                }
            }
            posArr[index].setBlackPawnPromotion(index);
            System.out.println("return index: "+index);
            return posArr[index];
        }

        if(y1==1&&y2==0&&editBoard.getParrCopy()[y1][x1].getColor().equals("white")&&editBoard.getParrCopy()[y1][x1].getType().equals("pawn"))
        {
            Piece[][] tempParr = editBoard.getParrCopy();
            tempParr[y1][x1] = new Piece("empty");
            tempParr[y2][x2] = new Piece("queen","black");
            editBoard.setParr(tempParr);
            Position queen = new Position(editBoard,tempParr,editBoard.getWhiteEnPassantPositions(),editBoard.getBlackEnPassantPositions(),editBoard.getWhiteCastleConditions(),editBoard.getBlackCastleConditions(),editBoard.getWhiteCastled(),editBoard.getBlackCastled());
            
            tempParr[y2][x2] = new Piece("rook","black");
            editBoard.setParr(tempParr);
            Position rook = new Position(editBoard,tempParr,editBoard.getWhiteEnPassantPositions(),editBoard.getBlackEnPassantPositions(),editBoard.getWhiteCastleConditions(),editBoard.getBlackCastleConditions(),editBoard.getWhiteCastled(),editBoard.getBlackCastled());
            
            tempParr[y2][x2] = new Piece("bishop","black");
            editBoard.setParr(tempParr);
            Position bishop = new Position(editBoard,tempParr,editBoard.getWhiteEnPassantPositions(),editBoard.getBlackEnPassantPositions(),editBoard.getWhiteCastleConditions(),editBoard.getBlackCastleConditions(),editBoard.getWhiteCastled(),editBoard.getBlackCastled());
            
            tempParr[y2][x2] = new Piece("knight","black");
            editBoard.setParr(tempParr);
            Position knight = new Position(editBoard,tempParr,editBoard.getWhiteEnPassantPositions(),editBoard.getBlackEnPassantPositions(),editBoard.getWhiteCastleConditions(),editBoard.getBlackCastleConditions(),editBoard.getWhiteCastled(),editBoard.getBlackCastled());

            Position [] posArr = {queen,rook,bishop,knight};

            int index = 0;
            double value = posArr[0].getEval();

            for(int i=1;i<posArr.length;i++)
            {
                if(posArr[i].getEval()>value)
                {
                    index = i;
                    value = posArr[i].getEval();
                }
            }

            posArr[index].setWhitePawnPromotion(index);

            return posArr[index];
        }

        //System.out.println("editBoard gwepp after: "+Arrays.toString(editBoard.getWhiteEnPassantPositions()));

        editBoard.move(x1,y1,x2,y2);

        return new Position(editBoard,editBoard.getParrCopy(),editBoard.getWhiteEnPassantPositions(),editBoard.getBlackEnPassantPositions(),editBoard.getWhiteCastleConditions(),editBoard.getBlackCastleConditions(),editBoard.getWhiteCastled(),editBoard.getBlackCastled());

    }
    
    public void flipParr()
    {
        for(int i=0;i<4;i++)
        {
            Piece[] temp = parr[i];
            parr[i] = parr[7-i];
            parr[7-i] = temp;
        }
    }

    public void invertParr()
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(parr[i][j].getColor().equals("white"))
                {
                    parr[i][j] = new Piece(parr[i][j].getType(),"black",parr[i][j].getMoves());
                }
                else if(parr[i][j].getColor().equals("black"))
                {
                    parr[i][j] = new Piece(parr[i][j].getType(),"white",parr[i][j].getMoves());
                }
            }
        }
    }

    public boolean[] getWhiteEnPassantPositions() {
        return whiteEnPassantPositions;
    }
    public boolean[] getBlackEnPassantPositions() {
        return blackEnPassantPositions;
    }
    public boolean[] getWhiteCastleConditions() {
        boolean[] whiteCastleConditions = new boolean[2];
        whiteCastleConditions[0] = whiteShortCastle;
        whiteCastleConditions[1] = whiteLongCastle;
        return whiteCastleConditions;
    }
    public boolean[] getBlackCastleConditions()
    {
        boolean[] blackCastleConditions = new boolean[2];
        blackCastleConditions[0] = blackShortCastle;
        blackCastleConditions[1] = blackLongCastle;
        return blackCastleConditions;
    }

    public boolean getWhiteCheckMate()
    {
        return whiteInCheckMate;
    }
    public boolean getBlackCheckMate()
    {
        return blackInCheckMate;
    }
    
    public int getMoves()
    {
        return moves;
    }
    public boolean getGameOver()
    {
        return gameOver;

    }
    public String toString()
    {
        String output = "";

        for(Piece[] q:parr)
        {
            output+=Arrays.toString(q)+"\n";
        }

        return output;
    }
    public boolean getDraw()
    {
        return draw;
    }
    public void setParr(Piece[][] p)
    {
        parr = p;
    }
    public boolean getWhiteCastled()
    {
        return whiteCastled;
    }
    public boolean getBlackCastled()
    {
        return blackCastled;
    }
    public void setAIColor(boolean white)
    {
        aiIsWhite = white;
    }
}