import javax.swing.*;
public class initMinesweeper
{
    public static void main(String[]args)
    {
        int cellsX = 30;
        int cellsY = 16;
        JFrame frame = new JFrame();
        Minesweeper minesweeper = new Minesweeper(cellsX,cellsY);
        frame.setVisible(true);
        frame.setSize(minesweeper.getPreferredSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // To maximize a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Minesweeper");
        frame.add(minesweeper);

        //System.out.println(frame.getWidth());
        //System.out.println(frame.getHeight());
    }
}