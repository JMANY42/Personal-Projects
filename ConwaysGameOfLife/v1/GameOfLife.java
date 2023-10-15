import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GameOfLife extends JPanel implements ActionListener
{

    //first iteration
    //good things: it works with a gui
    //bad things: limited size of board, can't zoom in or move around 
    //
    private boolean[][] board;
    private int WIDTH = 100;
    private int HEIGHT = 100;
    int squareWidth;
    int squareHeight;    
    JButton iterateButton, loop10IterateButton, loop100IterateButton;


    public GameOfLife()
    {
        setLayout(null);
        board = new boolean[HEIGHT][WIDTH];
        squareWidth = getWidth()/WIDTH;
        squareHeight = getHeight()/HEIGHT;
        squareWidth = squareHeight;

        iterateButton = new JButton("Iterate");
        add(iterateButton);
        iterateButton.setBounds(1000,100,200,100);
        iterateButton.addActionListener(this);

        loop10IterateButton = new JButton("Iterate x10");
        add(loop10IterateButton);
        loop10IterateButton.setBounds(1000,300,200,100);
        loop10IterateButton.addActionListener(this);

        loop100IterateButton = new JButton("Iterate x100");
        add(loop100IterateButton);
        loop100IterateButton.setBounds(1000,500,200,100);
        loop100IterateButton.addActionListener(this);


        addMouseListener(new MouseAdapter() { 
        public void mousePressed(MouseEvent e)
        { 
            int x = e.getX()/squareWidth;
            int y = e.getY()/squareHeight;
            System.out.println("x: "+x);
            System.out.println("y: "+y);



            if(x<WIDTH&&x>=0&&y<HEIGHT&&y>=0)
            {
                board[y][x] = !board[y][x];
            }
            //iterate();
            paintImmediately(getVisibleRect());
        }}); 

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (GameOfLife.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            System.out.println("w");
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                        }
                        break;
                    }
                    return false;
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(iterateButton)) {
            iterate();
        }
        else if(ae.getSource().equals(loop10IterateButton)) {
            for(int i=0;i<10;i++)
            {
                iterate();
                paintImmediately(getVisibleRect());
                try{Thread.sleep(200);}
                catch(Exception e){}
            }
        }
        else if(ae.getSource().equals(loop100IterateButton)) {
            for(int i=0;i<100;i++)
            {
                iterate();
                paintImmediately(getVisibleRect());
                try{Thread.sleep(1);}
                catch(Exception e){}
            }
        }
        paintImmediately(getVisibleRect());
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        squareWidth = getWidth()/WIDTH;
        squareHeight = getHeight()/HEIGHT;
        squareWidth = squareHeight;

        System.out.println(getWidth());

        for(int r=0;r<HEIGHT;r++)
        {
            for(int c=0;c<WIDTH;c++)
            {
                //System.out.println(r+c);
                Rectangle rec = new Rectangle(r*squareWidth,c*squareHeight,squareWidth,squareHeight);

                if(board[c][r])
                {
                    g2d.setColor(Color.WHITE);
                }
                else
                {
                    g2d.setColor(Color.BLACK);
                }
                g2d.draw(rec);
                g2d.fill(rec);
            }
        }
    }

    public void iterate()
    {
        boolean[][] board2 = new boolean[board.length][board[0].length];

        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                board2[r][c] = board[r][c];
            }
        }


        for(int r=0;r<board.length;r++)
        {
            for(int c=0;c<board[r].length;c++)
            {
                int numAliveNeighbors = getNumAliveNeighbors(r,c);
                if(!board[r][c]&&numAliveNeighbors==3)
                {
                    board2[r][c] = true;
                }
                if(board[r][c]&&(numAliveNeighbors<2||numAliveNeighbors>3))
                {
                    board2[r][c] = false;
                }
            }
        }

        board = board2;
    }

    public int getNumAliveNeighbors(int r, int c)
    {
        int numAliveNeighbors = 0;
        
        if(r>0&&board[r-1][c])
        {
            numAliveNeighbors++;
        }
        if(r<board.length-1&&board[r+1][c])
        {
            numAliveNeighbors++;
        }
        if(c>0&&board[r][c-1])
        {
            numAliveNeighbors++;
        }
        if(c<board[r].length-1&&board[r][c+1])
        {
            numAliveNeighbors++;
        }
        if(r>0&&c>0&&board[r-1][c-1])
        {
            numAliveNeighbors++;
        }
        if(r>0&&c<board[r].length-1&&board[r-1][c+1])
        {
            numAliveNeighbors++;
        }
        if(r<board.length-1&&c>0&&board[r+1][c-1])
        {
            numAliveNeighbors++;
        }
        if(r<board.length-1&&c<board[r].length-1&&board[r+1][c+1])
        {
            numAliveNeighbors++;
        }

        return numAliveNeighbors;
    }
}