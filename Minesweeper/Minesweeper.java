import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class Minesweeper extends JPanel
{
    private int width;
    private int height;
    private int numCellsX;
    private int numCellsY;
    private int[][] board;
    private int[][] visableBoard;
    private int cellWidth;
    private int cellHeight;
    private int numBombs;
    private int topBarHeight;
    private int gapX;
    private int gapY;
    private boolean win;
    private boolean lose;
    private boolean firstClick;
    private int numFlagsPlaced;
    
    public Minesweeper(int cellW, int cellH)
    {
        setBackground(Color.LIGHT_GRAY);
        numCellsX = cellW;
        numCellsY = cellH;
        width = getWidth();
        height = getHeight();
        System.out.println(width);
        System.out.println(height);
        numBombs = numCellsX*numCellsY/7;
        numBombs = 99;
        board = new int[numCellsY][numCellsX];
        visableBoard = new int[numCellsY][numCellsX];       

        for(int r=0;r<visableBoard.length;r++)
        {
            for(int c=0;c<visableBoard[r].length;c++)
            {
                visableBoard[r][c] = -2;
            }
        }


        addMouseListener(new MouseAdapter() { 
        public void mousePressed(MouseEvent e)
        {
            if(win||lose)
            {
                return;
            }
            System.out.println(getWidth());
            System.out.println(getHeight());
            int mouseX = (e.getX()-gapX)/cellWidth;
            int mouseY = (e.getY()-topBarHeight-gapY)/cellHeight;

            if(mouseY<0)
            {
                mouseY = 0;
            }
            else if(mouseY>=numCellsY)
            {
                mouseY=numCellsY-1;
            }
            if(mouseX<0)
            {
                mouseX = 0;
            }
            else if(mouseX>=numCellsX)
            {
                mouseX=numCellsX-1;
            }

            System.out.println("mouseX: "+mouseX);
            System.out.println("mouseY: "+mouseY);

            if(!firstClick)
            {
                firstClick = true;
                topBarHeight = height/numCellsY;
                int[] bombArr = new int[numCellsX*numCellsY];

                for(int i=0;i<numBombs;i++)
                {
                    bombArr[i] = 1;
                }
                shuffleArray(bombArr);
                System.out.println(Arrays.toString(bombArr));

                System.out.println("BR");
                System.out.println(mouseY*numCellsX+mouseX);
                System.out.println(bombArr[mouseY*numCellsX+mouseX]);

                while(bombArr[mouseY*numCellsX+mouseX]==1)
                {
                    System.out.println("regen");
                    int rand = (int)(Math.random()*bombArr.length);
                    bombArr[mouseY*numCellsX+mouseX] = bombArr[rand];
                    bombArr[rand] = 1;

                    System.out.println("BR loop");
                    System.out.println(mouseY*numCellsX+mouseX);
                    System.out.println(bombArr[mouseY*numCellsX+mouseX]);
                }

                for(int r=0;r<board.length;r++)
                {
                    for(int c=0;c<board[r].length;c++)
                    {
                        if(bombArr[r*board[r].length+c]==1)
                        {
                            board[r][c] = -1;
                        }
                    }
                }

                for(int r=0;r<board.length;r++)
                {
                    for(int c=0;c<board[r].length;c++)
                    {
                        if(board[r][c]!=-1)
                        {
                            board[r][c] = getBombNeighbors(board,r,c);
                        }
                    }
                }


                for(int[] r:board)
                {
                    for(int n:r)
                    {
                        System.out.print(n+" ");
                    }
                    System.out.println();
                }
            }

            if(e.getButton() == MouseEvent.BUTTON1)
            {
                if(visableBoard[mouseY][mouseX]==-3)
                {
                    numFlagsPlaced--;
                }
                visableBoard[mouseY][mouseX] = board[mouseY][mouseX];
                if(visableBoard[mouseY][mouseX]==0)
                {
                    int[][] tempBoard = new int[board.length][board[0].length];
                    for(int r=0;r<board.length;r++)
                    {
                        for(int c=0;c<board[r].length;c++)
                        {
                            tempBoard[r][c] = board[r][c];
                        }
                    }
                    for(int[] q:visableBoard)
                    {
                        System.out.println(Arrays.toString(q));
                    }
                    checkZeroTiles(mouseY,mouseX,tempBoard);
                }
            }
            else if(e.getButton() == MouseEvent.BUTTON3)
            {
                //set flag
                if(visableBoard[mouseY][mouseX] == -3)
                {
                    visableBoard[mouseY][mouseX] = -2;
                    numFlagsPlaced--;
                }
                else
                {
                    visableBoard[mouseY][mouseX] = -3;
                    numFlagsPlaced++;
                }
            }
                checkGameOver();
                paintImmediately(getVisibleRect());
        }});
    }

    public Dimension getPreferredSize() {
        return new Dimension(750,500);
    }

    public void paintComponent(Graphics g)
    {  
        Graphics2D g2d = (Graphics2D)g;
        width = getWidth();
        height = getHeight();
        g2d.clearRect(0,0,width,height);
        g2d.setColor(new Color(240,236,236));
        g2d.fill(new Rectangle(0,0,width,height));

        //System.out.println("width: "+width);
        //System.out.println("numCellsX: "+numCellsX);


        topBarHeight = height/numCellsY;
        cellWidth = width/numCellsX;
        cellHeight = (height-topBarHeight)/numCellsY;
        //System.out.println("cw"+cellWidth);


        //shows flags remaining
        try{
            g.drawImage(resizeImage(toBufferedImage(new ImageIcon("images/flagGB.png").getImage()),topBarHeight,topBarHeight),width*1/152,0,this);
        } catch(IOException e)
        {
            System.out.println(e);
        }

        Font big = new Font("Helvetica", Font.BOLD, topBarHeight);
        FontMetrics metr2 = getFontMetrics(big);

        g.setFont(big);
        g.setColor(Color.BLACK);

        g2d.setColor(Color.LIGHT_GRAY);
        g.setColor(Color.BLACK);
        g.drawString((numBombs-numFlagsPlaced)+"", topBarHeight+cellWidth, big.getSize());





        //draws the grid
        //autoformats to an adjusted window size
        System.out.println((width-numCellsX*cellWidth)/2);
        gapX = (width-30*cellWidth)/2;
        gapY = (height-topBarHeight-numCellsY*cellHeight)/2;
        for(int r=topBarHeight+gapY;r<height-(height%cellHeight);r+=cellHeight)
        {
            for(int c=gapX;c<width-(width%cellWidth);c+=cellWidth)
            {
                //System.out.println(visableBoard[r/cellHeight-1][c/cellWidth]);
                String imgPath = "images/unopened.png";
                switch(visableBoard[r/cellHeight-1][c/cellWidth])
                {
                    case -4: //grey bomb
                        imgPath = "images/bombGB.png";
                        break;
                    case -3://flag
                        //System.out.println("flag");
                        imgPath = "images/flag.png";
                        break;
                    case -1://bomb
                        //System.out.println("bomb");
                        imgPath = "images/bomb.png";
                        break;
                    case 0://empty
                        imgPath = "images/empty.png";
                        //System.out.println("empty");
                        Rectangle rect = new Rectangle(c,r,cellWidth,cellHeight);
                        g2d.draw(rect);
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fill(rect);
                        break;
                    case 1://1 bomb
                        //System.out.println("one");
                        imgPath = "images/one.png";
                        break;
                    case 2://2 bombs
                        //System.out.println("two");
                        imgPath = "images/two.png";
                        break;
                    case 3://3 bombs
                        //System.out.println("three");
                        imgPath = "images/three.png";
                        break;
                    case 4://4 bombs
                        //System.out.println("four");
                        imgPath = "images/four.png";
                        break;
                    case 5://5 bombs
                        //System.out.println("five");
                        imgPath = "images/five.png";
                        break;
                    case 6://6 bombs
                        //System.out.println("six");
                        imgPath = "images/six.png";
                        break;
                    case 7://7 bombs
                        //System.out.println("seven");
                        imgPath = "images/seven.png";
                        break;
                    case 8://8 bombs
                        //System.out.println("eight");
                        imgPath = "images/eight.png";
                        break;
                }

                if(!imgPath.equals("images/empty.png"))
                {
                    try{
                        g.drawImage(resizeImage(toBufferedImage(new ImageIcon(imgPath).getImage()),cellWidth,cellHeight),c,r,this);
                    } catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
                
            }
        }

        g.setColor(Color.BLACK);
        if(win)
        {
            String str = "Congratulations! You win!";
            g.drawString(str, (width - metr2.stringWidth(str)) / 2, (height + big.getSize()) / 2);
        }
        else if(lose)
        {
            String str = "Unlucky...";
            g.drawString(str, (width - metr2.stringWidth(str)) / 2, (height + big.getSize()) / 2);
        }
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException
    {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_REPLICATE);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    public void checkGameOver()
    {
        int numOpenSpots = 0;
        for(int r=0;r<visableBoard.length;r++)
        {
            for(int c=0;c<visableBoard[r].length;c++)
            {
                if(visableBoard[r][c]==-1)
                {
                    lose = true;
                    for(int r2 = 0;r2<visableBoard.length;r2++)
                    {
                        for(int c2=0;c2<visableBoard[r2].length;c2++)
                        {
                            if(board[r2][c2]==-1&&!(r2==r&&c2==c))
                            {
                                visableBoard[r2][c2] = -4;
                            }
                        }
                    }
                }
                if(!(visableBoard[r][c]==-2||visableBoard[r][c]==-3))
                {
                    numOpenSpots++;
                }
            }
        }

        if(numOpenSpots==numCellsX*numCellsY-numBombs)
        {
            win = true;
        }
    }

    public void checkZeroTiles(int r,int c, int[][] tempBoard)
    {
        if(r<0||c<0||r>=board.length||c>=board[r].length||tempBoard[r][c]!=0)
        {
            return;
        }

        visableBoard[r][c] = board[r][c];
        
        System.out.println("visBoard: "+visableBoard[r][c]);
        if(visableBoard[r][c]==-3)
        {
            numFlagsPlaced--;
        }
        tempBoard[r][c] = 1000;

        if(r>0&&visableBoard[r-1][c]==-3)visableBoard[r-1][c] = numFlagsPlaced--;
        if(r<board.length-1&&visableBoard[r+1][c]==-3)visableBoard[r+1][c] = numFlagsPlaced--;
        if(r>0&&c>0&&visableBoard[r-1][c-1]==-3)visableBoard[r-1][c-1] = numFlagsPlaced--;
        if(r<board.length-1&&c>0&&visableBoard[r+1][c-1]==-3)visableBoard[r+1][c-1] = numFlagsPlaced--;
        if(r>0&&c<board[0].length-1&&visableBoard[r-1][c+1]==-3)visableBoard[r-1][c+1] = numFlagsPlaced--;
        if(r<board.length-1&&c<board[0].length-1&&visableBoard[r+1][c+1]==-3)visableBoard[r+1][c+1] = numFlagsPlaced--;
        if(c>0&&visableBoard[r][c-1]==-3)visableBoard[r][c-1] = numFlagsPlaced--;
        if(c<board[0].length-1&&visableBoard[r][c+1]==-3)visableBoard[r][c+1] = numFlagsPlaced--;

        if(r>0)visableBoard[r-1][c] = board[r-1][c];
        if(r<board.length-1)visableBoard[r+1][c] = board[r+1][c];
        if(r>0&&c>0)visableBoard[r-1][c-1] = board[r-1][c-1];
        if(r<board.length-1&&c>0)visableBoard[r+1][c-1] = board[r+1][c-1];
        if(r>0&&c<board[0].length-1)visableBoard[r-1][c+1] = board[r-1][c+1];
        if(r<board.length-1&&c<board[0].length-1)visableBoard[r+1][c+1] = board[r+1][c+1];
        if(c>0)visableBoard[r][c-1] = board[r][c-1];
        if(c<board[0].length-1)visableBoard[r][c+1] = board[r][c+1];
        
        
        checkZeroTiles(r-1,c-1,tempBoard);
        checkZeroTiles(r-1,c,tempBoard);
        checkZeroTiles(r-1,c+1,tempBoard);
        checkZeroTiles(r,c+1,tempBoard);
        checkZeroTiles(r+1,c+1,tempBoard);
        checkZeroTiles(r+1,c,tempBoard);
        checkZeroTiles(r+1,c-1,tempBoard);
        checkZeroTiles(r,c-1,tempBoard);
    }

    public void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    public int getBombNeighbors(int[][] b, int r, int c)
    {
        int bombCount = 0;
        if(r>0&&b[r-1][c]==-1)
        {
            bombCount++;
        }
        if(r<numCellsY-1&&b[r+1][c]==-1)
        {
            bombCount++;
        }
        if(c>0&&b[r][c-1]==-1)
        {
            bombCount++;
        }
        if(c<numCellsX-1&&b[r][c+1]==-1)
        {
            bombCount++;
        }
        if(r>0&&c>0&&b[r-1][c-1]==-1)
        {
            bombCount++;
        }
        if(r<numCellsY-1&&c<numCellsX-1&&b[r+1][c+1]==-1)
        {
            bombCount++;
        }
        if(r<numCellsY-1&&c>0&&b[r+1][c-1]==-1)
        {
            bombCount++;
        }
        if(r>0&&c<numCellsX-1&&b[r-1][c+1]==-1)
        {
            bombCount++;
        }
        return bombCount;
    }
}