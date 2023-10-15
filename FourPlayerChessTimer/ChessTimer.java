import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessTimer extends JPanel implements ActionListener
{
    private int player;
    private int time;
    private int increment;
    private Timer timer;
    private int[] playerTimeArr;
    private String[] names;
    private boolean gameOver;
    private int DELAY = 1000;
    public ChessTimer(int t, int inc,String[] na)
    {
        player = 0;
        time = t;
        increment = inc;
        names = na;
        playerTimeArr = new int[4];
        for(int i=0;i<4;i++)
        {
            playerTimeArr[i] = time;
        }
        playerTimeArr[0]+=0;


        timer = new Timer(DELAY,this);
        timer.setInitialDelay(DELAY);
        timer.start();


        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (Timer.class) {
                    if(gameOver)
                    {
                        return false;
                    }
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            playerTimeArr[player]+=increment;
                            player++;
                            if(player==4)
                            {
                                player=0;
                            }
                            timer.stop();
                            repaint();
                            timer.start();
                    }
                    break;

                    case KeyEvent.KEY_RELEASED:
                        break;
                    }
                    return false;
                }
            }
        });   
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        Rectangle r = new Rectangle(0,0,0,0);
        Rectangle reset = new Rectangle(0,0,500,500);

        g2d.setColor(Color.WHITE);
        g2d.draw(reset);
        g2d.fill(reset);

        switch(player)
        {
            case 0:
                r = new Rectangle(0,0,250,250);
                g2d.setColor(Color.RED);
                break;
            case 1:
                r = new Rectangle(250,0,250,250);
                g2d.setColor(Color.BLUE);
                break;
            case 2:
                r = new Rectangle(250,250,250,250);
                g2d.setColor(Color.YELLOW);
                break;
            case 3:
                r = new Rectangle(0,250,250,250);
                g2d.setColor(Color.GREEN);
                break;
        }

        g2d.draw(r);
        g2d.fill(r);


        Font big = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr2 = getFontMetrics(big);

        g.setFont(big);
        g.setColor(Color.BLACK);
        String msg0 = playerTimeArr[0]+"";
        String msg1 = playerTimeArr[1]+"";
        String msg2 = playerTimeArr[2]+"";
        String msg3 = playerTimeArr[3]+"";

        //g.drawString(msg0, (250 - metr2.stringWidth(msg0)) / 2, 125);
        //g.drawString(msg1, (750 - metr2.stringWidth(msg1)) / 2, 125);
        //g.drawString(msg2, (750 - metr2.stringWidth(msg2)) / 2, 375);
        //g.drawString(msg3, (250 - metr2.stringWidth(msg3)) / 2, 375);


        g.drawString(names[0], 125 - (metr2.stringWidth(names[0]) / 2), 83 + (big.getSize() / 2));
        g.drawString(names[1], 375 - (metr2.stringWidth(names[1]) / 2), 83 + (big.getSize() / 2));
        g.drawString(names[2], 375 - (metr2.stringWidth(names[2]) / 2), 333 + (big.getSize() / 2));
        g.drawString(names[3], 125 - (metr2.stringWidth(names[3]) / 2), 333 + (big.getSize() / 2));


        g.drawString(msg0, 125 - (metr2.stringWidth(msg0) / 2), 166 + (big.getSize() / 2));
        g.drawString(msg1, 375 - (metr2.stringWidth(msg1) / 2), 166 + (big.getSize() / 2));
        g.drawString(msg2, 375 - (metr2.stringWidth(msg2) / 2), 416 + (big.getSize() / 2));
        g.drawString(msg3, 125 - (metr2.stringWidth(msg3) / 2), 416 + (big.getSize() / 2));




        if(gameOver)
        {
            String playerName = names[player]+" loses!";
            //g.drawString("GAMEOVER",133,250);
            g.drawString("GAMEOVER", getWidth()/2 - (metr2.stringWidth("GAMEOVER") / 2), 460/2 + (big.getSize() / 2));
            g.drawString(playerName, getWidth()/2 - (metr2.stringWidth(playerName) / 2), 540/2 + (big.getSize() / 2));

            //g.drawString("Player "+player+" loses",115,300);
            //g.drawString(playerName, 250 - (metr2.stringWidth(playerName) / 2), 250);

        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,540);
    }

    public void gameOver()
    {
        gameOver=true;
        System.out.println("You Lose!");
    }

    public void actionPerformed(ActionEvent e) {
        if(gameOver)
        {
            return;
        }
        playerTimeArr[player]--;
        System.out.println(player);
        System.out.println(playerTimeArr[player]);
        if(playerTimeArr[player]<=0)
        {
            gameOver();
            timer.stop();
        }
        repaint();
    }
}
