import javax.swing.*;
public class InitLife2
{
    public static void main(String[]args)
    {
        JFrame frame = new JFrame();
        //frame.setLayout(null);
        Window window = new Window();
        frame.add(window);
        frame.setVisible(true);
        frame.setSize(500,500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // To maximize a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Of Life");
    }
}