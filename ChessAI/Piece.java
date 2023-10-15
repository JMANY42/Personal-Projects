import javax.swing.*;
import java.awt.*;
public class Piece
{
    private String type;
    private String color;
    private String imgPath;
    private int moved;
    private int movesSinceLastMove;
    private int pieceValue;

    public Piece(String t, String c)
    {
        movesSinceLastMove = 0;
        moved = 0;
        type = t;
        color = c;
        imgPath = color.substring(0,1)+type.substring(0,1).toUpperCase()+type.substring(1)+".png";

        pieceValue = 0;
        switch(type)
        {
            case "pawn":
                pieceValue = 1;
                break;
            case "knight":
                pieceValue = 3;
                break;
            case "bishop":
                pieceValue = 3;
                break;  
            case "rook":
                pieceValue = 5;
                break;
            case "queen":
                pieceValue = 9;
                break;
        }
    }

    public Piece(String t)
    {
        moved = 0;
        type = t;
        color = "";
        imgPath = "empty.png";
    }

    public Piece(String t, String c,int m)
    {
        movesSinceLastMove = 0;
        moved = m;
        type = t;
        color = c;
        imgPath = color.substring(0,1)+type.substring(0,1).toUpperCase()+type.substring(1)+".png";

        pieceValue = 0;
        switch(type)
        {
            case "pawn":
                pieceValue = 1;
                break;
            case "knight":
                pieceValue = 3;
                break;
            case "bishop":
                pieceValue = 3;
                break;  
            case "rook":
                pieceValue = 5;
                break;
            case "queen":
                pieceValue = 9;
                break;
        }
    }


    public String getType()
    {
        return type;
    }
    public String getColor()
    {
        return color;
    }
    public String getImgPath()
    {
        return imgPath;
    }
    public String toString()
    {
        return imgPath;
    }
    public Image getImage()
    {
        return new ImageIcon(imgPath).getImage();
    }
    public int getMoves()
    {
        return moved;
    }
    public int getPieceValue()
    {
        return pieceValue;
    }
    public void increaseMoved()
    {
        moved++;
    }
    public void updateMovesSinceLastMove()
    {
        movesSinceLastMove++;
    }
    public void resetMovesSinceLastMove(){
        movesSinceLastMove = 0;
    }
    public int getMOvesSinceLastMove()
    {
        return movesSinceLastMove;
    }
}