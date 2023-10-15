import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;


public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 20;
    private final int ALL_DOTS = B_WIDTH*B_WIDTH/DOT_SIZE/DOT_SIZE;
    private final int RAND_POS = (B_WIDTH)/DOT_SIZE;
    private final int DELAY = 7;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    private TAdapter listner;
    private int moves;
    private int[] dPath;
    private int preMove;

    private int addDots;

    public Board() {
        
        initBoard();
    }
    
    private void initBoard() {
        Arrays.fill(x,-1);
        Arrays.fill(y,-1);
        dPath = new int[1];
        moves = 1;
        addDots = 0;
        inGame = true;
        dPath = new int[0];
        listner = new TAdapter();
        addKeyListener(listner);
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("resources/bodyTest.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("resources/bodyTest.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 60 - z * DOT_SIZE;
            y[z] = 60;
        }
        
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            timer.stop();
            gameOver(g);
            
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e){}
            //if(loop) initBoard();
            //loop = !loop;
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        //Font small = new Font("Helvetica", Font.BOLD, 14);
        //FontMetrics metr = getFontMetrics(small);
        Font big = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr2 = getFontMetrics(big);

        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(msg, (B_WIDTH - metr2.stringWidth(msg)) / 2, B_HEIGHT / 2-100);

        String msg2 = "length: "+dots;
        g.setFont(big);
        g.drawString(msg2, (B_WIDTH - metr2.stringWidth(msg2)) / 2, B_HEIGHT/2+100);
        
        
        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){}
        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){}

        RunSnakeAI.runSnake();
        //System.out.println("hello?");
        //initBoard();
    }


    private void checkApple() {

        /*//System.out.println("apple x: "+apple_x);
        //System.out.println("apple y: "+apple_y);
        //System.out.println("head x: "+x[0]);
        //System.out.println("head y: "+y[0]);*/
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            addDots+=1;
            locateApple();
        }
    }

    private void move(int direction) {
        //System.out.println("d dPath: "+Arrays.toString(dPath));
        /*//System.out.println("before");
        //System.out.println(Arrays.toString(x));
        //System.out.println(Arrays.toString(y));*/
        int add = 0;
        boolean temp = false;
        if(addDots>0)
        {
            add=1;
            addDots--;
            temp = true;
        }
        //System.out.println("x: "+Arrays.toString(x));
        //System.out.println("y: "+Arrays.toString(y));


        for (int z = dots+add; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if(temp)
        {
            dots++;
        }
        //System.out.println("x: "+Arrays.toString(x));
        //System.out.println("y: "+Arrays.toString(y));
        /*//System.out.println("after");
        //System.out.println(Arrays.toString(x));
        //System.out.println(Arrays.toString(y));*/

        boolean safe1 = true;
        boolean safe2 = true;
        boolean safe3 = true;
        boolean safe4 = true;

        for(int i=0;i<x.length;i++)
        {
            if(x[i]==x[0]-DOT_SIZE&&y[i]==y[0]||x[0]==0)
            {
                safe1 = false;
            }
            if(x[i]==x[0]+DOT_SIZE&&y[i]==y[0]||x[0]==B_WIDTH-DOT_SIZE)
            {
                safe2 = false;
            }
            if(y[i]==y[0]-DOT_SIZE&&x[i]==x[0]||y[0]==0)
            {
                safe3 = false;
            }
            if(y[i]==y[0]+DOT_SIZE&&x[i]==x[0]||y[0]==B_HEIGHT-DOT_SIZE)
            {
                safe4 = false;
            }
        }
        //System.out.println("x: "+x[0]+"\ny: "+y[0]);
        //System.out.println("dir: "+direction);
        //System.out.println("safe1: "+safe1);
        //System.out.println("safe2: "+safe2);
        //System.out.println("safe3: "+safe3);
        //System.out.println("safe4: "+safe4);
        //System.out.println("e dPath: "+Arrays.toString(dPath));

        if (direction == 1&&safe1) {
            x[0] -= DOT_SIZE;
            preMove = 1;
            return;
        }
        else if (direction == 2&&safe2) {
            x[0] += DOT_SIZE;
            preMove = 2;
            return;
        }
        else if (direction == 3&&safe3) {
            y[0] -= DOT_SIZE;
            preMove = 3;
            return;
        }
        else if (direction == 4&&safe4) {
            y[0] += DOT_SIZE;
            preMove = 4;
            return;
        }
        else if(direction == 0)
        {
            if(safe1)
            {
                x[0] -= DOT_SIZE;
                preMove = 1;
                return;
            }
            else if(safe2)
            {
                x[0] += DOT_SIZE;
                preMove = 2;
                return;
            }
            else if(safe3)
            {
                y[0] -= DOT_SIZE;
                preMove = 3;
                return;
            }
            else if(safe4)
            {
                y[0] += DOT_SIZE;
                preMove = 4;
                return;
            }
            else
            {
                inGame = false;
            }
        }
        else
        {
            //System.out.println("\n\n\nhere\n\n\n");
            //getPath();
            moves++;
            return;
        }

        if (preMove == 1&&x[0]!=0) {
            x[0] -= DOT_SIZE;
            preMove = 1;
        }
        else if (preMove == 1&&x[0]==0) {
            boolean moved = false;
            for(int i=0;i<x.length;i++)
            {
                if(x[i]==0&&y[i]==y[0]-1)
                {
                    y[0]+=DOT_SIZE;
                    moved = true;
                    break;
                }
            }
            if(!moved)
            {
                y[0]-=DOT_SIZE;
            }
        }

        else if (preMove == 2&&x[0]!=B_WIDTH) {
            x[0] += DOT_SIZE;
            preMove = 2;
        }
        else if (preMove == 1&&x[0]==B_WIDTH) {
            boolean moved = false;
            for(int i=0;i<x.length;i++)
            {
                if(x[i]==B_WIDTH&&y[i]==y[0]-1)
                {
                    y[0]+=DOT_SIZE;
                    moved = true;
                    break;
                }
            }
            if(!moved)
            {
                y[0]-=DOT_SIZE;
            }
        }

        else if (preMove == 3&&y[0]!=0) {
            y[0] -= DOT_SIZE;
            preMove = 3;
        }
        else if (preMove == 1&&y[0]==0) {
            boolean moved = false;
            for(int i=0;i<x.length;i++)
            {
                if(y[i]==0&&x[i]==x[0]-1)
                {
                    x[0]+=DOT_SIZE;
                    moved = true;
                    break;
                }
            }
            if(!moved)
            {
                x[0]-=DOT_SIZE;
            }
        }

        else if (preMove == 4&&y[0]!=B_HEIGHT) {
            y[0] += DOT_SIZE;
            preMove = 4;
        }
        else if (preMove == 1&&y[0]==B_HEIGHT) {
            boolean moved = false;
            for(int i=0;i<x.length;i++)
            {
                if(y[i]==B_HEIGHT&&x[i]==x[0]-1)
                {
                    x[0]+=DOT_SIZE;
                    moved = true;
                    break;
                }
            }
            if(!moved)
            {
                x[0]-=DOT_SIZE;
            }
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        boolean inSnake = true;
        while(inSnake)
        {
            int r = (int) (Math.random() * RAND_POS);
            apple_x = ((r * DOT_SIZE));

            r = (int) (Math.random() * RAND_POS);
            apple_y = ((r * DOT_SIZE));
            boolean found = false;
            for(int i=0;i<x.length;i++)
            {
                if(apple_x==x[i]&&apple_y==y[i])
                {
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                break;
            }
        }



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e);
        if (inGame) {
            checkApple();
            checkCollision();
            moves--;
            if(moves==0)
            {
                getPath();
            }
            //System.out.println("length: "+dPath.length);
            //System.out.println("moves: "+moves);
            //System.out.println("a dPath: "+Arrays.toString(dPath));
            //System.out.println("b dPath: "+Arrays.toString(dPath));
            //System.out.println("c dPath: "+Arrays.toString(dPath));
            //System.out.println("index: "+(dPath.length-moves));
            //System.out.println("spot: "+dPath[dPath.length-moves]);
            move(dPath[dPath.length-moves]);
            //System.out.println("f dPath: "+Arrays.toString(dPath));

            listner.resetPress();
        }

        repaint();
    }

    public void getPath()
    {
        int[] sendx = new int[dots];
        int[] sendy = new int[dots];

        for(int i=0;i<dots;i++)
        {
            sendx[i]=x[i]/DOT_SIZE;
            sendy[i]=y[i]/DOT_SIZE;
        }
        dPath = AI.returnPath(sendx,sendy,dots,apple_x/DOT_SIZE,apple_y/DOT_SIZE,B_WIDTH/DOT_SIZE);
        moves = dPath.length;
    }

    private class TAdapter extends KeyAdapter {

        private boolean alreadyPressed;

        public TAdapter()
        {
            alreadyPressed = false;
        }
        public void resetPress()
        {
            alreadyPressed = false;
        }
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(!alreadyPressed)
            {
                if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                    upDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                    downDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }
                alreadyPressed = true;
            }
        }
    }
}
