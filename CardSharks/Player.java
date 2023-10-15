public class Player
{
    private String name;
    private DeckOfCards deck;
    private Card[] cards;
    private int baseCard;
    private int index;
    private boolean turnOver;
    private boolean win;
    private int oldBaseCard;
    private boolean frozen;
    private int guess;
    private String hol; 
    private String[] baseCardArray;


    public Player(String x)
    {
        name = x;
        deck = new DeckOfCards();
        deck.shuffleDeck();
        cards = new Card[5];
        for(int i=0;i<cards.length;i++)
        {
            cards[i]=deck.accessDeck(i);
        }
        baseCard = 0;
        index = cards.length;
        turnOver = false;
        baseCardArray = new String[5];
    }

    public DeckOfCards getDeck()
    {
        return deck;
    }

    public String getName()
    {
        return name;
    }

    public Card getCards(int x)
    {
        return cards[x];
    }
    public Card[] getCardArray()
    {
        return cards;
    }

    public int getBaseCard()
    {
        return baseCard;
    }
    public void setBaseCard(int x)
    {
        baseCard = x;
    }
    public String getBaseCardArray(int x)
    {
        for(int i=0;i<baseCardArray.length;i++)
        {
            if(i!=oldBaseCard)
            {
                baseCardArray[i]="      ";
            }
            else
            {
                baseCardArray[i]="FREEZE";
            }
        }
        return baseCardArray[x];
    }


    public String getHLOCL(int cardNumber, int lineNumber)
    {
        if(cardNumber<=baseCard)
        {
            return cards[cardNumber].getCardLine(lineNumber);
        }
        return cards[cardNumber].getHiddenLine(lineNumber);
    }

    public int turn0()
    {
        yourTurn();
        frozen = false;
        String swapCard = "";
        oldBaseCard = baseCard;

        //System.out.println("Congratulations "+name+", you won the trivia question!");
        Art.setQuestion("Congratulations "+name+", you won the trivia question!");
        //printCards();
        //System.out.println("Would you like to swap your base card?");
        Art.setHostText("Would you like to swap your base card?");
        swapCard = CardSharksv2.SysIn();
        while(!swapCard.equals("yes")&&!swapCard.equals("no"))
        {
            System.out.println("Please enter \"yes\" or \"no\".");
            swapCard = CardSharksv2.SysIn();
        }
        if(swapCard.equals("yes"))
        {
            swapCard();
            Art.drawScreen();
        }
        return turn();
    }

    public int turn()
    {        
        frozen = false;
        oldBaseCard = baseCard;
        String freezeOrGuess = "";
        //int guessNumber = 0;
        turnOver = false;
        while(!turnOver)
        {
            //for(Card q:cards)
             //   System.out.println(q);
            //System.out.println(turnOver);
            //System.out.println("Would you like to guess higher, guess lower, or freeze?");
            CardSharksv2.ClearHostText();
            Art.setQuestion(name+", would you like to guess higher, guess lower, or freeze?");
            freezeOrGuess = CardSharksv2.SysIn();
            while(!freezeOrGuess.equals("freeze")&&!freezeOrGuess.equals("higher")&&!freezeOrGuess.equals("lower"))
            {
                System.out.println("Please enter \"freeze\", \"lower\", or \"higher\".");
                freezeOrGuess = CardSharksv2.SysIn();
            }

            if(freezeOrGuess.equals("freeze"))
            {
                freeze();
            }
            else
            {
                guess(freezeOrGuess);
            }

        }

        if(win)
        {
            //System.out.println("Win");
            return 2;
        }
        else if(frozen)
        {
            return 1;
        }
        return 0;
    }

    public void swapCard()
    {
        cards[baseCard]=deck.accessDeck(index);
        index++;
    }

    public void freeze()
    {
        turnOver = true;
        frozen = true;
        oldBaseCard = baseCard;
        hol="";
        guess=-1;
    }

    public void guess(String x)
    {
        int guessNumber = baseCard;
        String guess2 = "";
        boolean nextCardIsLower = false;

        //System.out.println("Will the next card be higher or lower?");
        guess2 = x;

        /*while(!guess.equals("higher")&&!guess.equals("lower"))
        {
            System.out.println("Please enter \"higher\" or \"lower\".");
            guess=CardSharksv2.SysIn();
        }*/
        //System.out.println(cards[guessNumber]);
        //System.out.println(cards[guessNumber+1]);

        nextCardIsLower = cards[guessNumber+1].getNumber() < cards[guessNumber].getNumber();

        if(((nextCardIsLower&&guess2.equals("lower"))||(!nextCardIsLower&&guess2.equals("higher")))&&(cards[guessNumber+1].getNumber() != cards[guessNumber].getNumber()))
        {
            //System.out.println("\nGood Job!\n");
            baseCard++;
            guessNumber++;
            Art.setQuestion("Good Job!");
            CardSharksv2.Sleep(2);
            //Art.drawScreen();
            
            //System.out.println(guessNumber);
            if(guessNumber == 4)
            {
                turnOver = true;
                win = true;
            }
        }
        else
        {
            //System.out.println("Unlucky...");
            baseCard++;
            Art.setQuestion("Unlucky...");
            CardSharksv2.Sleep(3);
            
            turnOver = true;
            baseCard=oldBaseCard;
            replaceCards(guessNumber);
            guessNumber = baseCard;
            //System.out.println(turnOver);
            hol="";
            guess=-1;
            
        }
    }

    public void replaceCards(int guessNumber)
    {
        if(guessNumber+2>5)
            guessNumber=3;
        for(int i=baseCard+1;i<guessNumber+2;i++)
        {
            cards[i]=deck.accessDeck(index);
            index++;
        }
    }

    public void setGuessNumber()
    {
        boolean success = true;
        do{
            success = true;
            try{
                guess = Integer.valueOf(CardSharksv2.SysIn());
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a number");
                success = false;
            }
        } while(!success);
    }

    public int getGuessNumber()
    {
        return guess;
    }

    public void setGuessHOL(String x)
    {

        while(!x.equals("higher")&&!x.equals("lower"))
        {
            System.out.println("Please enter \"higher\" or \"lower\"");
            x = CardSharksv2.SysIn();
        }
        hol = x;
    }
    public String getGuessHOL()
    {
        return hol;
    }
    public void yourTurn()
    {
        System.out.println("================"+name+"'s turn================\n");
    }

    public void youWon()
    {
        Art.setQuestion("Congratulations "+name+", you won!");
    }
}