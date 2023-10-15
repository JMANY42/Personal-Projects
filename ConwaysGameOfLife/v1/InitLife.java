import javax.swing.*;
public class InitLife
{
    public static void main(String[]args)
    {
        JFrame frame = new JFrame();
        GameOfLife gol = new GameOfLife();
        frame.setVisible(true);
        //frame.setSize(gol.getPreferredSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // To maximize a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Of Life");
        frame.add(gol);
        System.out.println("frame" + frame.getWidth());       
    }
}