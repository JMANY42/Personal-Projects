public class Art
{
    private static String p1Name;
    private static String p2Name;
    private static String p1NS;
    private static String p2NS;
    private static String question;
    private static String question2;
    private static Player playerOne;
    private static Player playerTwo;
    private static String hostAsk;
    private static String hostAsk2;
    private static String p1Guess;
    private static String p2Guess;

    public static void setp1Name(String x)
    {
        int length = x.length();
        if(length>26)
        {
            x=x.substring(0,26);
            length = x.length();
        }
        p1Name = x;
    
        if(length%2==1)
        {
            x=" "+x;
            p1Name+=" ";
            if(x.length()>26)
            {
                x=x.substring(0,26);
            }

        }
        for(int i=0;i<(26-length)/2;i++)
        {
            p1Name=" "+p1Name+" ";
        }
        x+="'s Cards ";
        while(x.length()!=59)
        {
            x=" "+x+" ";
        }
        p1NS = x;    
        
    }
    public static void setp2Name(String x)
    {
        int length = x.length();
        if(length>26)
        {
            x=x.substring(0,26);
        }
        p2Name = x;
        if(length%2==1)
        {
            p2Name+=" ";
            x=" "+x;
            if(x.length()>26)
            {
                x=x.substring(0,26);
            }

        }
        for(int i=0;i<(26-length)/2;i++)
        {
            p2Name=" "+p2Name+" ";
        }
        x+="'s Cards ";
        while(x.length()!=59)
        {
            x=" "+x+" ";
        }
        p2NS = x;
    }
    public static void setPlayerOneGuess(String x)
    {
        p1Guess = x;
        while(p1Guess.length()!=14&&p1Guess.length()!=13)
        {
            p1Guess=" "+p1Guess+" ";
        }
        if(p1Guess.length()==13)
        {
            p1Guess+=" ";
        }
    }
    public static void setPlayerTwoGuess(String x)
    {
        p2Guess = x;
        while(p2Guess.length()!=14&&p2Guess.length()!=13)
        {
            p2Guess=" "+p2Guess+" ";
        }
        if(p2Guess.length()==13)
        {
            p2Guess+=" ";
        }
    }
    public static void setPlayerOne(Player x)
    {
        playerOne = x;
        setp1Name(playerOne.getName());
    }
    public static void setPlayerTwo(Player x)
    {
        playerTwo = x;
        setp2Name(playerTwo.getName());
    }
    public static void setQuestion(String x)
    {
        int length = x.length();
        question2 = "";
        if(length>89)
        {
            for(int i=89;i>0;i--)
            {
                if(x.substring(i,i+1).equals(" "))
                {

                    question2 = x.substring(i+1);
                    x = x.substring(0,i);
                    i=0;
                }
            }
        }
        question = x;

        while(question.length()!=89)
        {
            question+=" ";
        }
        while(question2.length()!=89)
        {
            question2+=" ";
        }
        drawScreen();
    }

    public static void setHostText(String x)
    {
        int length = x.length();
        hostAsk2 = "";
        //System.out.println(length);
        if(length>89)
        {
            for(int i=89;i>0;i--)
            {
                if(x.substring(i,i+1).equals(" "))
                {

                    hostAsk2 = x.substring(i+1);
                    x = x.substring(0,i);
                    i=0;
                }
            }
        }
        hostAsk = x;
        //System.out.println(hostAsk);

        while(hostAsk.length()!=89)
        {
            hostAsk+=" ";
        }
        while(hostAsk2.length()!=89)
        {
            hostAsk2+=" ";
        }
        drawScreen();
    }
    public static void drawScreen()
    {
        System.out.println("\n\n\n\n\n\n                                                                            -------------------------------------------------------------------------------------------");
        System.out.println("┌───────────────────────────────────────────────────────────┐               |"+question+"|");
        System.out.println("│"+p1NS+"│               │"+question2+"│");
        System.out.println("│┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐│               |                                                                                         |");
        System.out.println("│"+playerOne.getHLOCL(0,1)+" "+playerOne.getHLOCL(1,1)+" "+playerOne.getHLOCL(2,1)+" "+playerOne.getHLOCL(3,1)+" "+playerOne.getHLOCL(4,1)+"│               |"+hostAsk+"|");
        System.out.println("│"+playerOne.getHLOCL(0,2)+" "+playerOne.getHLOCL(1,2)+" "+playerOne.getHLOCL(2,2)+" "+playerOne.getHLOCL(3,2)+" "+playerOne.getHLOCL(4,2)+"│               |"+hostAsk2+"|");
        System.out.println("│"+playerOne.getHLOCL(0,3)+" "+playerOne.getHLOCL(1,3)+" "+playerOne.getHLOCL(2,3)+" "+playerOne.getHLOCL(3,3)+" "+playerOne.getHLOCL(4,3)+"│               -------------------------------------------------------------------------------------------");
        System.out.println("│"+playerOne.getHLOCL(0,4)+" "+playerOne.getHLOCL(1,4)+" "+playerOne.getHLOCL(2,4)+" "+playerOne.getHLOCL(3,4)+" "+playerOne.getHLOCL(4,4)+"│                             /");
        System.out.println("│"+playerOne.getHLOCL(0,5)+" "+playerOne.getHLOCL(1,5)+" "+playerOne.getHLOCL(2,5)+" "+playerOne.getHLOCL(3,5)+" "+playerOne.getHLOCL(4,5)+"│                            /");
        System.out.println("│└─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘│                           /");
        System.out.println("│  "+playerOne.getBaseCardArray(0)+"      "+playerOne.getBaseCardArray(1)+"      "+playerOne.getBaseCardArray(2)+"      "+playerOne.getBaseCardArray(3)+"      "+playerOne.getBaseCardArray(4)+"   │                          /");
        System.out.println("│"+p2NS+"│             ┌─────────┐ /                                       ┌─────────┐                ┌─────────┐");
        System.out.println("│┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐│             │( ͡° ͜ʖ ͡° )│/                                        │( ͡° ͜ʖ ͡° )│                │( ͡° ͜ʖ ͡° )│");
        System.out.println("│"+playerTwo.getHLOCL(0,1)+" "+playerTwo.getHLOCL(1,1)+" "+playerTwo.getHLOCL(2,1)+" "+playerTwo.getHLOCL(3,1)+" "+playerTwo.getHLOCL(4,1)+"│             └────┬────┘                                         └────┬────┘                └────┬────┘");
        System.out.println("│"+playerTwo.getHLOCL(0,2)+" "+playerTwo.getHLOCL(1,2)+" "+playerTwo.getHLOCL(2,2)+" "+playerTwo.getHLOCL(3,2)+" "+playerTwo.getHLOCL(4,2)+"│                  │                                                   │                          │");
        System.out.println("│"+playerTwo.getHLOCL(0,3)+" "+playerTwo.getHLOCL(1,3)+" "+playerTwo.getHLOCL(2,3)+" "+playerTwo.getHLOCL(3,3)+" "+playerTwo.getHLOCL(4,3)+"│                  │                                                   │                          │");
        System.out.println("│"+playerTwo.getHLOCL(0,4)+" "+playerTwo.getHLOCL(1,4)+" "+playerTwo.getHLOCL(2,4)+" "+playerTwo.getHLOCL(3,4)+" "+playerTwo.getHLOCL(4,4)+"│                  │                                                   │                          │");
        System.out.println("│"+playerTwo.getHLOCL(0,5)+" "+playerTwo.getHLOCL(1,5)+" "+playerTwo.getHLOCL(2,5)+" "+playerTwo.getHLOCL(3,5)+" "+playerTwo.getHLOCL(4,5)+"│                  │                                      ┌────────────┴─────────────┬────────────┴─────────────┐");
        System.out.println("│└─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘│        ┌─────────┴─────────┐                            │"+p1Name+"│"+p2Name+"│");
        System.out.println("│  "+playerTwo.getBaseCardArray(0)+"      "+playerTwo.getBaseCardArray(1)+"      "+playerTwo.getBaseCardArray(2)+"      "+playerTwo.getBaseCardArray(3)+"      "+playerTwo.getBaseCardArray(4)+"   │        │       HOST        │                            │                          │                          │");
        System.out.println("└───────────────────────────────────────────────────────────┘        │                   │                            │     ┌──────────────┐     │     ┌──────────────┐     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │"+p1Guess+"│     │     │"+p2Guess+"│     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     │              │     │     │              │     │");
        System.out.println("                                                                     │                   │                            │     └──────────────┘     │     └──────────────┘     │");
        System.out.println("                                                                     │                   │                            │                          │                          │");
        System.out.println("                                                                     │                   │                            │                          │                          │");
        System.out.println("                                                                     └───────────────────┘                            └──────────────────────────┴──────────────────────────┘");

    }
}