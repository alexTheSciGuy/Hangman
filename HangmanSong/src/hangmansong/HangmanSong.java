/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmansong;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author kattz
 */
public class HangmanSong {

public static final String[] WORDS = {
  "GTFOOMF", "IYKYK", "TMIYWT"  
};   

    public static final Random RANDOM = new Random(); 
        //number of lives
    public static final int LIVES = 5;
        //Phrase
    private String phraseToFigureOut;
        //Phrase found in a char array to show progress of player
    private char[] phraseFiguredOut;
    private int weirdErrors;
    //phrases entered by player
    private ArrayList<String> letters = new ArrayList<>();
    
    //method returning randomly next phrase to find
    private String nextPhraseToFigureOut() {
    return WORDS[RANDOM.nextInt(WORDS.length)];
}
    
    //starts a new game
public void startGame() {
    weirdErrors = 0;
    letters.clear();
    phraseToFigureOut = nextPhraseToFigureOut();
    
    //phrase found initialization
    phraseFiguredOut = new char[phraseToFigureOut.length()];
    
    for (int i = 0; i < phraseFiguredOut.length; i++) {
        phraseFiguredOut[i] = '_';
    }
}

//returns true if phrase is found by user
public boolean phraseFiguredOut() {
    return phraseToFigureOut.contentEquals(new String(phraseToFigureOut));
}

//Method refreshing the phrase found after player enters a letter
    
private void enter( String c) {
    //we update only if c jas not already been entered
    if (!letters.contains(c)) {
        //checks if phrase to find contains c
        if (phraseToFigureOut.contains(c)){
            //if so, replace _ by the character c
            int index = phraseToFigureOut.indexOf(c);
            
            while(index >= 0) {
                phraseFiguredOut[index] = c.charAt(0);
                index = phraseToFigureOut.indexOf(c, index + 1);
            }
        }
        else{
            //c not in the phrase => error
            weirdErrors++;
        }
        
        // c is now a phrase entered
        letters.add(c);
        }
    }
    //Method returning the state of the phrase
    private String phraseFiguredOutContent(){
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < phraseFiguredOut.length; i++) {
            builder.append(phraseFiguredOut[i]);
            
            if (i < phraseFiguredOut.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
    //Play method for our HangmanSong Game
    public void play() {
        try (Scanner input = new Scanner(System.in)) {
            //we play while weirdErrors is lower than max errors
            while (weirdErrors < LIVES) {
                System.out.println("\nEnter a phrase : ");
                //get next inputfrom user
                String str = input.next();
                
                //we keep just first letter
                if (str.length() > 1) {
                    str = str.substring(0, 1);
                }
                //update phrase found
                enter(str);
                
                //display current state
                System.out.println("\n" + phraseFiguredOutContent());
                
                //check if phrase is found
                if (phraseFiguredOut()){
                    System.out.println("\nYou Win!");
                    break;
                } else {
                    //Display number of lives left for the player
                    System.out.println("\n=> Lives remaining : " + (LIVES - weirdErrors));
                }
            }
            
            if (weirdErrors == LIVES) {
                //player lost
                System.out.println("\nYou Lose!");
                System.out.println("=> Phrase to find was : " + phraseToFigureOut);
            }
        }
    }
        public static void main(String[] args) {
            HangmanSong hangmanSong = new HangmanSong();
            hangmanSong.startGame();
            hangmanSong.play();
        }
    }

