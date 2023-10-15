//Before running type chcp 65001 into the terminal
//Also make sure the terminal takes up the entire screen
//Otherwise a bunch of things will be messed up

import java.util.*;
import java.io.*;
public class CardSharksv2
{
    public static Scanner in = new Scanner(System.in);
    public static void main(String[]args) throws IOException
    {
        File questionFile = new File("questions.txt");
        Scanner readFile = new Scanner(questionFile);

        String[] questions = new String[116];
        int spot = 0;

        while(readFile.hasNextLine())
        {
            questions[spot] = readFile.nextLine();
            spot++;
        }
        readFile.close();
        
        //System.out.println(Arrays.toString(questions));
        System.out.println("Player one enter your name: ");
        String p1n = in.nextLine();
        System.out.println("Player two enter your name: ");
        String p2n = in.nextLine();
        

        Player playerOne = new Player(p1n);
        Player playerTwo = new Player(p2n);
        Art.setPlayerOne(playerOne);
        Art.setPlayerTwo(playerTwo);
        Art.setPlayerOneGuess("");
        Art.setPlayerTwoGuess("");

        /*System.out.println(playerOne.getDeck());
        playerOne.printCards();
        playerTwo.printCards();
        playerOne.swapBaseCard();
        playerOne.printCards();*/
        //playerOne.printCards();

        boolean winner = false;
        boolean p1WTQ = true;
        int result = 0;
        String ask = "";
        boolean isQuestion = false;
        ArrayList<Integer> askedQuestionNumbers = new ArrayList<Integer>();
        int questionLine = -1;
        int answerNumber = 0;
        boolean p1G = true;
        while(!winner)
        {
            while(!isQuestion)
            {
                questionLine = (int)(Math.random()*116);
                if(!askedQuestionNumbers.contains(questionLine))
                {
                    isQuestion=true;
                    askedQuestionNumbers.add(questionLine);
                }
            }
            isQuestion = false;
            ask=questions[questionLine];
            try{
                answerNumber = Integer.valueOf(ask.substring(ask.length()-2,ask.length()));
            }
            catch(Exception e)
            {
                System.out.println("Something's wrong with the question.");
            }
            ask=ask.substring(0,ask.length()-2);
            
            //System.out.println("\n============================================================\n");
            Art.setQuestion(ask);
            ClearHostText();
            Sleep(5);
            
            //System.out.println("\nThe question is: "+ask+"\n");
            //System.out.println(answerNumber);
            if(p1G)
            {
                //System.out.println(playerOne.getName()+", what do you think the answer is?");
                Art.setHostText(playerOne.getName()+", what do you think the answer is?");
                playerOne.setGuessNumber();
                Art.setPlayerOneGuess(""+playerOne.getGuessNumber());

                //System.out.println(playerTwo.getName()+", do you think the answer is higher or lower than "+playerOne.getGuessNumber());
                Art.setHostText(playerTwo.getName()+", do you think the answer is higher or lower than "+playerOne.getGuessNumber()+"?");
                playerTwo.setGuessHOL(in.nextLine());
                Art.setPlayerTwoGuess(playerTwo.getGuessHOL());

                if((playerTwo.getGuessHOL().equals("higher")&&answerNumber>playerOne.getGuessNumber())||(playerTwo.getGuessHOL().equals("lower")&&answerNumber<playerOne.getGuessNumber()))
                {
                    p1WTQ = false;
                }
                else
                {
                    p1WTQ = true;
                }
            }
            else
            {
                //System.out.println(playerTwo.getName()+", what do you think the answer is?");
                Art.setHostText(playerTwo.getName()+", what do you think the answer is?");
                playerTwo.setGuessNumber();
                Art.setPlayerTwoGuess(""+playerTwo.getGuessNumber());

                //System.out.println(playerOne.getName()+", do you think the answer is higher or lower than "+playerTwo.getGuessNumber());
                Art.setHostText(playerOne.getName()+", do you think the answer is higher or lower than "+playerTwo.getGuessNumber()+"?");
                playerOne.setGuessHOL(in.nextLine());
                Art.setPlayerOneGuess(playerOne.getGuessHOL());

                if((playerOne.getGuessHOL().equals("higher")&&answerNumber>playerTwo.getGuessNumber())||(playerOne.getGuessHOL().equals("lower")&&answerNumber<playerTwo.getGuessNumber()))
                {
                    p1WTQ = true;
                }
                else
                {
                    p1WTQ = false;
                }
            }
            p1G=!p1G;

            //DO NEXT
            //PLAYERS GUESS NUMBERS
            /*while(!winner)
                System.out.println((int)((Math.random())*2)==0);*/
            //trivia question here

            //System.out.println("The answer is......\n"+answerNumber);
            Art.setQuestion("The answer is......");
            ClearHostText();
            Sleep(3);
            Art.setHostText(""+answerNumber);
            Sleep(3);
            Art.setPlayerOneGuess("");
            Art.setPlayerTwoGuess("");

            if(p1WTQ)
            {
                result = playerOne.turn0();
                if(result==2)
                {
                    winner = true;
                    playerOne.youWon();
                }
                else if(result==0)
                {
                    Art.setQuestion(playerTwo.getName()+", it is now your turn.");
                    Sleep(2);
                    playerTwo.yourTurn();
                    result = playerTwo.turn();
                    if(result==2)
                    {
                        winner = true;
                        playerTwo.youWon();
                    }
                }
            }
            else            
            {
                result = playerTwo.turn0();
                if(result == 2)
                {
                    winner = true;
                    playerTwo.youWon();
                }
                else if(result == 0)
                {   
                    Art.setQuestion(playerOne.getName()+", it is now your turn.");
                    Sleep(2);
                    playerOne.yourTurn();
                    result = playerOne.turn();
                    if(result==2)
                    {
                        winner = true;
                        playerOne.youWon();
                    }
                }
            }
            
        }
        


    }

    public static String SysIn()
    {
        return in.nextLine();
    }
    public static void Sleep(int seconds) 
    {
        try{
            Thread.sleep(seconds*1000);
        }
        catch(InterruptedException x){
            
        }
    }
    public static void ClearQuestion()
    {
        Art.setQuestion("                                                                                          ");
    }
    public static void ClearHostText()
    {
        Art.setHostText("                                                                                          ");
    }
}