import javax.swing.*;
public class Test
{
    public static void main(String[]args)
    {
        boolean aiIsWhite = false;
        JFrame frame = new JFrame();
        Board x = new Board(aiIsWhite);
        frame.setVisible(true);
        frame.setSize(x.getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chess");
        frame.add(x);
        if(aiIsWhite)
        {
            x.aiTurn();
        }
    }
}