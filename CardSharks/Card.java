public class Card
{
    private int number;
    private String stringNumberBottom;
    private String stringNumberTop;
    private String suit;
    private String[][] pictureCard;
    private String[][] hiddenCard;
    
    public Card(int x, String y)
    {
        number = x;
        //stringNumber = String.valueOf(x);
        suit = y;
        String symbol = "";

        if(number<9)
        {
            stringNumberTop = String.valueOf(number+1)+" ";
            stringNumberBottom = " "+String.valueOf(number+1);
        }
        else if(number==9)
        {
            stringNumberTop = "10";
            stringNumberBottom = "10";
        }
        else if(number==10)
        {
            stringNumberTop = "J ";
            stringNumberBottom = " J";
        }
        else if(number==11)
        {
            stringNumberTop = "Q ";
            stringNumberBottom = " Q";
        }
        else if(number==12)
        {
            stringNumberTop = "K ";
            stringNumberBottom = " K";
        }
        else if(number==13)
        {
            stringNumberTop = "A ";
            stringNumberBottom = " A";
        }



        if(suit.equals("D"))
        {
            symbol = "\u2666";
        }
        else if(suit.equals("S"))
        {
            symbol = "\u2660";
        }
        else if(suit.equals("H"))
        {
            symbol = "\u2665";
        }
        else if(suit.equals("C"))
        {
            symbol = "\u2663";
        }
        String[][] temp =  {{"┌─────────┐"},
                            {"│"+stringNumberTop+"       │"},
                            {"│         │"},
                            {"│    "+symbol+"    │"},
                            {"│         │"},
                            {"│       "+stringNumberBottom+"│"},
                            {"└─────────┘"}};
        pictureCard = temp;

        String[][] temp2 = {{"┌─────────┐"},
                            {"│░░░░░░░░░│"},
                            {"│░░░░░░░░░│"},
                            {"│░░░░░░░░░│"},
                            {"│░░░░░░░░░│"},
                            {"│░░░░░░░░░│"},
                            {"└─────────┘"} };
        hiddenCard = temp2;
    }

    public int getNumber()
    {
        return number;
    }
    public String getSuit()
    {
        return suit;
    }
    public void setNumber(int x)
    {
        number = x;
    }
    public void setSuit(String y)
    {
        suit = y;
    }
    public void printCard()
    {
        for(String[]q:pictureCard)
        {

            System.out.println(q[0]);
        }
    }
    public String getCardLine(int x)
    {
        return pictureCard[x][0];
    }
    public String getHiddenLine(int x)
    {
        return hiddenCard[x][0];
    }

    public String[][] getStringArray()
    {
        return pictureCard;
    }
    public String[][] getHiddenArray()
    {
        return hiddenCard;
    }
    public String toString()
    {
        return suit + number+" "; 
    }
}