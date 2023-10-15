import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class GOL extends JPanel implements ActionListener
{
    //second iteration
    //good things: can move around, can zoom in, and resize; works
    //bad things: nothing really its pretty much perfect for my goals

    private int scale = 10;
    private int centerX = 0;
    private int centerY = 0;
    private int xGap = 0;
    private int yGap = 0;
    private boolean running;
    HashSet<Cell> cellSet;

    private JButton iterateOnce, iterate10, iterate100, iterateForever, stopIterate;

    public GOL(JButton ionce,JButton i10,JButton i100,JButton ifor,JButton si)
    {
        running = false;
        iterateOnce = ionce;
        iterate10 = i10;
        iterate100 = i100;
        iterateForever = ifor;
        stopIterate = si;
        cellSet = new HashSet<Cell>();
        //initPattern();

        addMouseListener(new MouseAdapter() { 
        public void mousePressed(MouseEvent e)
        { 
            xGap = (getWidth()%scale);
            yGap = (getHeight()%scale);

            //System.out.println("xGap: "+xGap);
            //System.out.println("yGap: "+yGap);
            int x = (e.getX()/scale);
            int y = (e.getY()/scale);

            int numCellsInRow = getWidth()/scale;
            int numCellsInColumn = getHeight()/scale;

            x-=numCellsInRow/2;
            y-=numCellsInColumn/2;

            x-=centerX;
            y-=centerY;
            //System.out.println("ncir: "+numCellsInRow);
            
            //System.out.println("width: "+getWidth());
            //System.out.println("height: "+getHeight());

            if(Math.abs(x)<getWidth()&&Math.abs(y)<getHeight())
            {
                Cell tempCell = new Cell(x,y);
                if(cellSet.contains(tempCell))
                {
                    cellSet.remove(tempCell);
                    //System.out.println("remove cell: "+tempCell);
                }
                else
                {
                    cellSet.add(tempCell);
                    //System.out.println("add cell: "+tempCell);
                }
            }
            paintImmediately(getVisibleRect());
        }}); 

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (GOL.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_UP) {
                            scale++;
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                            scale--;
                            if(scale<1)
                            {
                                scale = 1;
                            }
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            centerY++;
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_S) {
                            centerY--;
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                            centerX++;
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_D) {
                            centerX--;
                            repaint();
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_P) {
                            
                            for(Cell c:cellSet)
                            {
                                System.out.println("cellSet.add(new Cell("+c.getX()+","+c.getY()+"));");
                            }
                            repaint();
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

    public void initPattern()
    {
        cellSet.add(new Cell(1,-1));
        cellSet.add(new Cell(91,91));
        cellSet.add(new Cell(-5,2));
        cellSet.add(new Cell(-7,3));
        cellSet.add(new Cell(0,-1));
        cellSet.add(new Cell(2,0));
        cellSet.add(new Cell(-4,1));
        cellSet.add(new Cell(-8,3));
        cellSet.add(new Cell(1,-2));
        cellSet.add(new Cell(91,92));
        cellSet.add(new Cell(-3,0));
        cellSet.add(new Cell(4,-4));
        cellSet.add(new Cell(0,-2));
        cellSet.add(new Cell(-19,0));
        cellSet.add(new Cell(-4,0));
        cellSet.add(new Cell(4,0));
        cellSet.add(new Cell(1,-3));
        cellSet.add(new Cell(91,93));
        cellSet.add(new Cell(-20,0));
        cellSet.add(new Cell(4,-5));
        cellSet.add(new Cell(2,-4));
        cellSet.add(new Cell(-9,2));
        cellSet.add(new Cell(0,-3));
        cellSet.add(new Cell(92,93));
        cellSet.add(new Cell(-19,-1));
        cellSet.add(new Cell(-4,-1));
        cellSet.add(new Cell(4,1));
        cellSet.add(new Cell(-6,0));
        cellSet.add(new Cell(-20,-1));
        cellSet.add(new Cell(-10,1));
        cellSet.add(new Cell(14,-3));
        cellSet.add(new Cell(-5,-2));
        cellSet.add(new Cell(15,-3));
        cellSet.add(new Cell(-10,0));
        cellSet.add(new Cell(14,-2));
        cellSet.add(new Cell(15,-2));
        cellSet.add(new Cell(-10,-1));
        cellSet.add(new Cell(89,90));
        cellSet.add(new Cell(-7,-3));
        cellSet.add(new Cell(-9,-2));
        cellSet.add(new Cell(90,90));
        cellSet.add(new Cell(-8,-3));
        cellSet.add(new Cell(89,91));
    }

    public void paintComponent(Graphics g)
    {
        //System.out.println("gol width: "+getWidth());
        //System.out.println("gol height: "+getHeight());
        Graphics2D g2d = (Graphics2D) g;
        Rectangle rect = new Rectangle(0,0,getWidth(),getHeight());
        g2d.setColor(Color.PINK);
        g2d.draw(rect);
        g2d.fill(rect);
        rect = new Rectangle(0,0,getWidth()-xGap,getHeight()-yGap);
        g2d.setColor(Color.BLACK);
        g2d.draw(rect);
        g2d.fill(rect);
        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);
        for(Cell c:cellSet)
        {
            Rectangle rec = new Rectangle((width/2/scale+c.getX()+centerX)*scale,(height/2/scale+c.getY()+centerY)*scale,scale,scale);
            g2d.draw(rec);
            g2d.fill(rec);
        }
    }

    public void iterate()
    {
        HashSet<Cell> cellSet2 = new HashSet<Cell>();
        HashSet<Cell> checkedDeadCells = new HashSet<Cell>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for(Cell c:cellSet)
        {
            minX = Math.min(minX,c.getX());
            maxX = Math.max(maxX,c.getX());
            minY = Math.min(minY,c.getY());
            maxY = Math.max(maxY,c.getY());

            int numNeighbors = getNumberOfNeighbors(c);
            if(numNeighbors==2||numNeighbors==3)
            {
                cellSet2.add(c);
            }

            //run alive check on dead neighbors

            Cell tempCell1 = new Cell(c.getX(),c.getY()-1);
            if(!cellSet.contains(tempCell1)&&!checkedDeadCells.contains(tempCell1)&&getNumberOfNeighbors(tempCell1)==3)
            {
                cellSet2.add(tempCell1);
                checkedDeadCells.add(tempCell1);
            }
            if(!checkedDeadCells.contains(tempCell1))
            {
                checkedDeadCells.add(tempCell1);
            }
            Cell tempCell2 = new Cell(c.getX(),c.getY()+1);
            if(!cellSet.contains(tempCell2)&&!checkedDeadCells.contains(tempCell2)&&getNumberOfNeighbors(tempCell2)==3)
            {
                cellSet2.add(tempCell2);
                checkedDeadCells.add(tempCell2);
            }
            if(!checkedDeadCells.contains(tempCell2))
            {
                checkedDeadCells.add(tempCell2);
            }

            Cell tempCell3 = new Cell(c.getX()-1,c.getY());
            if(!cellSet.contains(tempCell3)&&!checkedDeadCells.contains(tempCell3)&&getNumberOfNeighbors(tempCell3)==3)
            {
                cellSet2.add(tempCell3);
                checkedDeadCells.add(tempCell3);
            }
            if(!checkedDeadCells.contains(tempCell3))
            {
                checkedDeadCells.add(tempCell3);
            }
            Cell tempCell4 = new Cell(c.getX()+1,c.getY());
            if(!cellSet.contains(tempCell4)&&!checkedDeadCells.contains(tempCell4)&&getNumberOfNeighbors(tempCell4)==3)
            {
                cellSet2.add(tempCell4);
                checkedDeadCells.add(tempCell4);
            }
            if(!checkedDeadCells.contains(tempCell4))
            {
                checkedDeadCells.add(tempCell4);
            }

            Cell tempCell5 = new Cell(c.getX()-1,c.getY()-1);
            if(!cellSet.contains(tempCell5)&&!checkedDeadCells.contains(tempCell5)&&getNumberOfNeighbors(tempCell5)==3)
            {
                cellSet2.add(tempCell5);
                checkedDeadCells.add(tempCell5);
            }
            if(!checkedDeadCells.contains(tempCell5))
            {
                checkedDeadCells.add(tempCell5);
            }
            Cell tempCell6 = new Cell(c.getX()+1,c.getY()-1);
            if(!cellSet.contains(tempCell6)&&!checkedDeadCells.contains(tempCell6)&&getNumberOfNeighbors(tempCell6)==3)
            {
                cellSet2.add(tempCell6);
                checkedDeadCells.add(tempCell6);
            }
            if(!checkedDeadCells.contains(tempCell6))
            {
                checkedDeadCells.add(tempCell6);
            }

            Cell tempCell7 = new Cell(c.getX()-1,c.getY()+1);
            if(!cellSet.contains(tempCell7)&&!checkedDeadCells.contains(tempCell7)&&getNumberOfNeighbors(tempCell7)==3)
            {
                cellSet2.add(tempCell7);
                checkedDeadCells.add(tempCell7);
            }
            if(!checkedDeadCells.contains(tempCell7))
            {
                checkedDeadCells.add(tempCell7);
            }
            Cell tempCell8 = new Cell(c.getX()+1,c.getY()+1);
            if(!cellSet.contains(tempCell8)&&!checkedDeadCells.contains(tempCell8)&&getNumberOfNeighbors(tempCell8)==3)
            {
                cellSet2.add(tempCell8);
                checkedDeadCells.add(tempCell8);
            }
            if(!checkedDeadCells.contains(tempCell8))
            {
                checkedDeadCells.add(tempCell8);
            }

        }
        cellSet = cellSet2;
    }

    public int getNumberOfNeighbors(Cell cell)
    {
        int x = cell.getX();
        int y = cell.getY();
        int numNeighbors = 0;
        for(Cell c:cellSet)
        {
            if(c.getX()==x&&c.getY()==y+1)
            {
                numNeighbors++;
            }
            else if(c.getX()==x&&c.getY()==y-1)
            {
                numNeighbors++;
            }
            else if(c.getX()==x+1&&c.getY()==y)
            {
                numNeighbors++;
            }
            else if(c.getX()==x-1&&c.getY()==y)
            {
                numNeighbors++;
            }
            else if(c.getX()==x+1&&c.getY()==y-1)
            {
                numNeighbors++;
            }
            else if(c.getX()==x-1&&c.getY()==y-1)
            {
                numNeighbors++;
            }
            else if(c.getX()==x+1&&c.getY()==y+1)
            {
                numNeighbors++;
            }
            else if(c.getX()==x-1&&c.getY()==y+1)
            {
                numNeighbors++;
            }
        }
        return numNeighbors;
    }

    public void actionPerformed(ActionEvent ae) {
        //System.out.println(ae);
        //System.out.println(ae.paramString());
        if(ae.getSource().equals(stopIterate))
        {
            running = false;
            return;
        }
        if(running)
        {
            return;
        }
        if(ae.getSource().equals(iterateOnce)) {
            iterate();
            //System.out.println("iterate");
        }
        else if(ae.getSource().equals(iterate10)) {
            running = true;
            new Thread() {

                public void run() {
                    for(int i=0;i<10;i++)
                    {
                        if(!running)
                        {
                            break;
                        }
                        
                        iterate();
                        paintImmediately(getVisibleRect());
                        try{Thread.sleep(200);}
                        catch(Exception e){}
                    }
                    running = false;
                }
    
            }.start();

        }
        else if(ae.getSource().equals(iterate100)) {
            running = true;
            new Thread() {
                public void run() {
                    for(int i=0;i<100;i++)
                    {
                        if(!running)
                        {
                            break;
                        }
                        iterate();
                        paintImmediately(getVisibleRect());
                        try{Thread.sleep(10);}
                        catch(Exception e){}
                    }
                    running = false;
                }
    
            }.start();
        }

        else if(ae.getSource().equals(iterateForever)) {
            running = true;
            new Thread() {
                public void run() {
                    while(running)
                    {
                        iterate();
                        paintImmediately(getVisibleRect());
                        try{Thread.sleep(10);}
                        catch(Exception e){}
                    }
                }
    
            }.start();
        }
        paintImmediately(getVisibleRect());
     }
}
