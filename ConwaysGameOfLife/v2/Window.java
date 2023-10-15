import javax.swing.*;
import java.awt.*;
public class Window extends JPanel
{
    private JButton iterateOnce, iterate10, iterate100, iterateForever, stopIterate;
    private GOL game;
    public Window() 
    {
        setBackground(Color.PINK);
        setLayout(new GridBagLayout());
        iterateOnce = new JButton("Iterate x1");
        iterate10 = new JButton("Iterate x10");
        iterate100 = new JButton("Iterate x100");
        iterateForever = new JButton("Iterate Forever");
        stopIterate = new JButton("Stop Iteration");



        game = new GOL(iterateOnce,iterate10,iterate100,iterateForever,stopIterate);
        //add(iterateOnce);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;

        c.gridwidth = 5;
        c.gridheight = 5;

        add(game,c);
        game.paintImmediately(getVisibleRect());
        paintImmediately(getVisibleRect());



        c.weightx = .01;
        c.weighty = .01;
        c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(iterateOnce,c);
        iterateOnce.addActionListener(game);


        c.gridx = 6;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(iterate10,c);
        iterate10.addActionListener(game);

        c.gridx = 6;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(iterate100,c);
        iterate100.addActionListener(game);

        c.gridx = 6;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(iterateForever,c);
        iterateForever.addActionListener(game);
        
        c.gridx = 6;
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(stopIterate,c);
        stopIterate.addActionListener(game);

    }
}