import java.util.*;

public class AI
{
    public static int[] returnPath(int[] x, int[] y, int dots, int apple_x, int apple_y, int size)
    {
        //System.out.println("new");
        ArrayList<Point> points = new ArrayList<Point>();

        for(int r=0;r<size;r++)
        {
            for(int c=0;c<size;c++)
            {
                points.add(new Point(r,c));
            }
        }
        //System.out.println(Arrays.toString(x));
        //System.out.println(Arrays.toString(y));
        for(int i=1;i<dots;i++)
        {
            //System.out.println("index: "+points.indexOf(new Point(x[i],y[i])));
            int index = points.indexOf(new Point(x[i],y[i]));
            points.remove(index);
        }
        Point apple = new Point(apple_x,apple_y);
        Point head = new Point(x[0],y[0]);

        ArrayList<Point> path = new ArrayList<Point>();
        path.add(head);

        int[] fromArr = new int[points.size()];
        ArrayList<Point> pointCopy = new ArrayList<Point>();
        for(Point q:points)
        {
            pointCopy.add(q);
        }

        ArrayList<Point> rPath = new ArrayList<Point>();
        boolean finished = false;
        Point lastPoint = new Point(-1,-1);
        while(path.size()>0&&!finished)
        {
            for(int i=0;i<points.size();i++)
            {
                if(path.get(0).adjacent(apple))
                {
                    finished = true;
                    lastPoint = path.get(0);
                    break;
                }
                if(path.get(0).adjacent(points.get(i)))
                {
                    path.add(points.get(i));
                    points.set(i,new Point(-1,-1));
                    fromArr[i]=pointCopy.indexOf(path.get(0));
                }
            }
            path.remove(0);
        }
        if(!finished)
        {
            return new int[1];
        }
        /*//System.out.println(Arrays.toString(fromArr));
        //System.out.println(pointCopy);
        //System.out.println("apple "+apple);
        //System.out.println("lastPoint "+lastPoint);
        //System.out.println(head.equals(new Point(x[0],y[0])));
        //System.out.println("head "+head);*/
        //System.out.println("apple "+apple);
        //System.out.println("head "+head);

        Point otherPoint = new Point(-1,-1);
        rPath.add(lastPoint);
        

        if(Math.abs(head.getX()-apple.getX())+Math.abs(head.getY()-apple.getY())==2)
        {
            rPath.clear();
            rPath.add(head);
            if(head.getX()==apple.getX()&&head.getY()>apple.getY())
            {
                rPath.add(new Point(head.getX(),head.getY()-1));
            }
            else if(head.getX()==apple.getX()&&head.getY()<apple.getY())
            {
                rPath.add(new Point(head.getX(),head.getY()+1));
            }
            else if(head.getX()>apple.getX()&&head.getY()==apple.getY())
            {
                rPath.add(new Point(head.getX()-1,head.getY()));
            }
            else if(head.getX()<apple.getX()&&head.getY()==apple.getY())
            {
                rPath.add(new Point(head.getX()+1,head.getY()));
            }
            rPath.add(apple);
        }  
        else if(Math.abs(head.getX()-apple.getX())+Math.abs(head.getY()-apple.getY())==1)
        {
            rPath.clear();
            rPath.add(head);
            rPath.add(apple);
        }
        else if(Math.abs(head.getX()-apple.getX())+Math.abs(head.getY()-apple.getY())==0)
        {
            return new int[1];
        }
        else
        {
            try{
                otherPoint = pointCopy.get(fromArr[pointCopy.indexOf(lastPoint)]);
            } catch(Exception e)
            {
                //System.out.println(e);
            }
            while(!head.adjacent(otherPoint))
            {
                rPath.add(otherPoint);
                try{
                    ////System.out.println("here");
                    otherPoint = pointCopy.get(fromArr[pointCopy.indexOf(otherPoint)]);
                } catch(Exception e)
                {
                    //System.out.println(e);
                    break;
                }                
                ////System.out.println(otherPoint);
                ////System.out.println(rPath);
            }
            rPath.add(otherPoint);
            rPath.add(head);
            Collections.reverse(rPath);
            rPath.add(apple);
        }


        //System.out.println(rPath);

        int[] dPath = new int[rPath.size()-1];
        //left 1
        //right 2
        //up 3
        //down 4

        for(int i=0;i<rPath.size()-1;i++)
        {
            if(rPath.get(i).getX()>rPath.get(i+1).getX()){
                dPath[i]=1;
            }
            else if(rPath.get(i).getX()<rPath.get(i+1).getX()){
                dPath[i]=2;
            }
            else if(rPath.get(i).getY()>rPath.get(i+1).getY()){
                dPath[i]=3;
            }
            else if(rPath.get(i).getY()<rPath.get(i+1).getY()){
                dPath[i]=4;
            }
        }
        //System.out.println("asdfasfdasfd"+Arrays.toString(dPath));
        ////System.out.println
        return dPath;
    }
}

class Point{
    private int x;
    private int y;

    public Point(int xx, int yy)
    {
        x=xx;
        y=yy;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean adjacent(Point o)
    {
        return (Math.abs(x-o.getX())==1 && y==o.getY())||(Math.abs(y-o.getY())==1 && x==o.getX());
    }
    @Override
    public boolean equals(Object obj) {
        Point p = (Point)obj;
        if (p == null) return false;
        if (p == this) return true;
        if (p.getX()==x&&p.getY()==y) return true;
        return false;
    }
    public String toString()
    {
        return "("+x+","+y+")";
    }
}