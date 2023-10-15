public class DeckOfCards
{
    private Card[] deck = new Card[52];
    private String[] suits = {"S","D","C","H"};

    public DeckOfCards()
    {
        newDeck();
    }

    public void newDeck()
    {
        for(int i=0;i<4;i++)
        {
            for(int q=1;q<deck.length/4+1;q++)
            {
                deck[i*13+q-1]=new Card(q, suits[i]);
            }
        }
    }

    public void shuffleDeck()
    {
        Card temp;
        int x;
        for(int i=0;i<deck.length;i++)
        {
            x = (int)(Math.random()*52);
            temp=deck[i];
            deck[i]=deck[x];
            deck[x]=temp;
        }        
    }

    public Card accessDeck(int i)
    {
        return deck[i];
    }
    

    public String toString()
    {
        String temp = "";
        for(int i=0;i<deck.length;i++)
        {
        temp+=deck[i].toString();
        }
        return temp;
    }
}