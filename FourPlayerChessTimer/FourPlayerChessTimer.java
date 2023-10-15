import javax.swing.*;
public class FourPlayerChessTimer 
{
    public static void main(String[]args)
    {
        JFrame frame = new JFrame();
        String[] names = {"name1","name2","name3","name4"};
        ChessTimer x = new ChessTimer(30,2,names);
        frame.setVisible(true);
        frame.setSize(x.getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chess");
        frame.add(x);
    }
}