public class Cell
{
    private int x;
    private int y;
    public Cell(int xx, int yy)
    {
        x=xx;
        y=yy;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    @Override
    public int hashCode()
    {
        int code = 17*x+31*y;
        return code;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell c = (Cell) obj;
        return x==c.getX() && y==c.getY();
    }

    public String toString()
    {
        return "x: "+x+" y: "+y;
    }
}