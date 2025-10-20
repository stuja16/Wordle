package edu.lawrence.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class Statistics {
    private int gamesPlayed,
                gamesWon,
                currentStreak,
                maxStreak,
                oneGuess,       // Keep track of wins in a certain number of guesses
                twoGuesses,
                threeGuesses,
                fourGuesses,
                fiveGuesses,
                sixGuesses,
                maxGuesses,     // Keeps track of the category with the largest number of guesses
                currentGuess;   // # of guesses in the last game if won and in the current session
    private Rectangle rect;     // Background for displaying statistics
    private ArrayList<Text> texts;
    private ArrayList<Rectangle> bars;
    
    public Statistics() {
        readSave();
        texts = new ArrayList<Text>();
        bars = new ArrayList<Rectangle>();
    }
    
    private void readSave(){
        File saveFile = new File("statistics.txt");
        if(saveFile.exists()) {
            try {
                Scanner in = new Scanner(saveFile);
                gamesPlayed = in.nextInt();
                gamesWon = in.nextInt();
                currentStreak = in.nextInt();
                maxStreak = in.nextInt();
                oneGuess = in.nextInt();
                twoGuesses = in.nextInt();
                threeGuesses = in.nextInt();
                fourGuesses = in.nextInt();
                fiveGuesses = in.nextInt();
                sixGuesses = in.nextInt();
                maxGuesses = in.nextInt();
                System.out.println(gamesPlayed + ", " + gamesWon + ", " + currentStreak + ", " + maxStreak);
                System.out.println(oneGuess + ", " + twoGuesses + ", " + threeGuesses + ", " + fourGuesses + ", " + fiveGuesses + ", " + sixGuesses);
            } catch(FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Save file not found");

            // Set defaults if saveFile isn't found
            gamesPlayed = 0;
            gamesWon = 0;
            currentStreak = 0;
            maxStreak = 0;
            oneGuess = 0;
            twoGuesses = 0;
            threeGuesses = 0;
            fourGuesses = 0;
            fiveGuesses = 0;
            sixGuesses = 0;
            maxGuesses = 0;
        }
    }
    
    public void save() {
        try {
            PrintWriter out = new PrintWriter(new File("statistics.txt"));
            
            out.println(gamesPlayed);
            out.println(gamesWon);
            out.println(currentStreak);
            out.println(maxStreak);
            out.print(oneGuess + " ");
            out.print(twoGuesses + " ");
            out.print(threeGuesses + " ");
            out.print(fourGuesses + " ");
            out.print(fiveGuesses + " ");
            out.println(sixGuesses);
            out.println(maxGuesses);
            System.out.println(gamesPlayed + ", " + gamesWon + ", " + currentStreak + ", " + maxStreak);
            
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    // Centers "text" in the middle of a string of length "totalSize"
    // Controls for both odd and even length inputs
    private String centerText(String text, int totalSize) {
        int padding = (totalSize - text.length()) / 2;
        
        // String length and desired size have identical polarity
        if ((totalSize - text.length())%2 == 0) {
            if (padding == 0) { return text; }
            return String.format("%" + padding + "s%s%" + padding + "s", "", text, "");
        }
        
        // String length and desired size have opposite polarity, so adjust by 1
        if (padding == 0) { return String.format("%" + 1 + "s%s", "", text); }
        int startPadding = padding + 1;
        return String.format("%" + startPadding + "s%s%" + padding + "s", "", text, "");
    }
    
    public void displayStatistics(GamePane pane) {
        int winPercentage = (int)(((double)gamesWon)/((double)gamesPlayed)*100.0); // Calculate current win percentage
        int top = 175;
        rect = new Rectangle(275, top, 450, 400);
        rect.setFill(Color.BLACK);
        
        Text title = new Text(455, top+45, "Statistics");
        title.setFont(new Font("Arial Bold", 20));
        title.setFill(Color.WHITE);
        texts.add(title);
        
        final int valuesX = 400;
        final int scoreWidth = 3; // Stat values above 3-figures will break display
        String numericalStats = centerText(Integer.toString(gamesPlayed), scoreWidth);
        numericalStats += centerText(Integer.toString(winPercentage), scoreWidth);
        numericalStats += " ";
        numericalStats += centerText(Integer.toString(currentStreak), scoreWidth);
        numericalStats += centerText(Integer.toString(maxStreak), scoreWidth);
        Text values = new Text(valuesX-10, top+90, numericalStats);
        values.setFont(new Font("Arial", 40));
        values.setFill(Color.WHITE);
        texts.add(values);
        
        int labelOffset = 5;
        Text valueLabels = new Text(valuesX + labelOffset, top+110, "Played       Win %        Current         Max");
        valueLabels.setFont(new Font("Arial", 11));
        valueLabels.setFill(Color.WHITE);
        texts.add(valueLabels);
        
        int labelOffset2 = 113;
        Text valueLabels2 = new Text(valuesX + labelOffset + labelOffset2, top+121, "Streak        Streak");
        valueLabels2.setFont(new Font("Arial", 11));
        valueLabels2.setFill(Color.WHITE);
        texts.add(valueLabels2);
        
        Text title2 = new Text(412, top+165, "Guess Distribution");
        title2.setFont(new Font("Arial Bold", 20));
        title2.setFill(Color.WHITE);
        texts.add(title2);
        
        int labelX = 325;
        Text oneBarLabel = new Text(labelX, top+205, "1");
        oneBarLabel.setFont(new Font("Arial", 15));
        oneBarLabel.setFill(Color.WHITE);
        texts.add(oneBarLabel);
        
        // Text offset within the bar is optimized for 2-charater values
        // 3-charater values will slightly overflow bar-space
        int maxBarLength = 340;
        double oneBarLength = (double)oneGuess/maxGuesses * maxBarLength;
        if (oneBarLength < 30) { oneBarLength = 30; }
        Text oneBarValue = new Text(labelX+oneBarLength-10, top+205, "" + oneGuess);
        oneBarValue.setFont(new Font("Arial Bold", 15));
        oneBarValue.setFill(Color.WHITE);
        texts.add(oneBarValue);
        
        Rectangle oneBar = new Rectangle(labelX+10, top+190, oneBarLength, 18);
        if (currentGuess == 1) {oneBar.setFill(Color.GREEN);
        } else {oneBar.setFill(Color.DARKSLATEGREY);}
        bars.add(oneBar);
        
        Text twoBarLabel = new Text(labelX, top+230, "2");
        twoBarLabel.setFont(new Font("Arial", 15));
        twoBarLabel.setFill(Color.WHITE);
        texts.add(twoBarLabel);
        
        double twoBarLength = (double)twoGuesses/maxGuesses * maxBarLength;
        if (twoBarLength < 30) { twoBarLength = 30; }
        Text twoBarValue = new Text(labelX+twoBarLength-10, top+230, "" + twoGuesses);
        twoBarValue.setFont(new Font("Arial Bold", 15));
        twoBarValue.setFill(Color.WHITE);
        texts.add(twoBarValue);
        
        Rectangle twoBar = new Rectangle(labelX+10, top+215, twoBarLength, 18);
        if (currentGuess == 2) {twoBar.setFill(Color.GREEN);
        } else {twoBar.setFill(Color.DARKSLATEGREY);}
        bars.add(twoBar);
        
        Text threeBarLabel = new Text(labelX, top+255, "3");
        threeBarLabel.setFont(new Font("Arial", 15));
        threeBarLabel.setFill(Color.WHITE);
        texts.add(threeBarLabel);
        
        double threeBarLength = (double)threeGuesses/maxGuesses * maxBarLength;
        if (threeBarLength < 30) { threeBarLength = 30; }
        Text threeBarValue = new Text(labelX+threeBarLength-10, top+255, "" + threeGuesses);
        threeBarValue.setFont(new Font("Arial Bold", 15));
        threeBarValue.setFill(Color.WHITE);
        texts.add(threeBarValue);
        
        Rectangle threeBar = new Rectangle(labelX+10, top+240, threeBarLength, 18);
        if (currentGuess == 3) {threeBar.setFill(Color.GREEN);
        } else {threeBar.setFill(Color.DARKSLATEGREY);}
        bars.add(threeBar);
        
        Text fourBarLabel = new Text(labelX, top+280, "4");
        fourBarLabel.setFont(new Font("Arial", 15));
        fourBarLabel.setFill(Color.WHITE);
        texts.add(fourBarLabel);
        
        double fourBarLength = (double)fourGuesses/maxGuesses * maxBarLength;
        if (fourBarLength < 30) { fourBarLength = 30; }
        Text fourBarValue = new Text(labelX+fourBarLength-10, top+280, "" + fourGuesses);
        fourBarValue.setFont(new Font("Arial Bold", 15));
        fourBarValue.setFill(Color.WHITE);
        texts.add(fourBarValue);
        
        Rectangle fourBar = new Rectangle(labelX+10, top+265, fourBarLength, 18);
        if (currentGuess == 4) {fourBar.setFill(Color.GREEN);
        } else {fourBar.setFill(Color.DARKSLATEGREY);}
        bars.add(fourBar);
        
        Text fiveBarLabel = new Text(labelX, top+305, "5");
        fiveBarLabel.setFont(new Font("Arial", 15));
        fiveBarLabel.setFill(Color.WHITE);
        texts.add(fiveBarLabel);
        
        double fiveBarLength = (double)fiveGuesses/maxGuesses * maxBarLength;
        if (fiveBarLength < 30) { fiveBarLength = 30; }
        Text fiveBarValue = new Text(labelX+fiveBarLength-10, top+305, "" + fiveGuesses);
        fiveBarValue.setFont(new Font("Arial Bold", 15));
        fiveBarValue.setFill(Color.WHITE);
        texts.add(fiveBarValue);
        
        Rectangle fiveBar = new Rectangle(labelX+10, top+290, fiveBarLength, 18);
        if (currentGuess == 5) {fiveBar.setFill(Color.GREEN);
        } else {fiveBar.setFill(Color.DARKSLATEGREY);}
        bars.add(fiveBar);
        
        Text sixBarLabel = new Text(labelX, top+330, "6");
        sixBarLabel.setFont(new Font("Arial", 15));
        sixBarLabel.setFill(Color.WHITE);
        texts.add(sixBarLabel);
        
        double sixBarLength = (double)sixGuesses/maxGuesses * maxBarLength;
        if (sixBarLength < 30) { sixBarLength = 30; }
        Text sixBarValue = new Text(labelX+sixBarLength-10, top+330, "" + sixGuesses);
        sixBarValue.setFont(new Font("Arial Bold", 15));
        sixBarValue.setFill(Color.WHITE);
        texts.add(sixBarValue);
        
        Rectangle sixBar = new Rectangle(labelX+10, top+315, sixBarLength, 18);
        if (currentGuess == 6) {sixBar.setFill(Color.GREEN);
        } else {sixBar.setFill(Color.DARKSLATEGREY);}
        bars.add(sixBar);
        
        pane.getChildren().add(rect);
        for (Rectangle r: bars) {
            pane.getChildren().add(r);
        }
        for (Text t: texts) {
            pane.getChildren().add(t);
        }
    }
    
    public void removeStatistics(GamePane pane) {
        pane.getChildren().remove(rect);
        for (Text t: texts) {
            pane.getChildren().remove(t);
        }
        for (Rectangle r: bars) {
            pane.getChildren().remove(r);
        }
        texts = new ArrayList<Text>();
        bars = new ArrayList<Rectangle>();
    }
    
    public void gameLost() {
        gamesPlayed++;
        currentStreak = 0;
        save();
    }
    
    public void gameWon(int g) {
        gamesPlayed++;
        gamesWon++;
        currentStreak++;
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
        
        if (g == 0) {
            oneGuess++;
            currentGuess = 1;
            if (oneGuess > maxGuesses) { maxGuesses++; }
        } else if (g == 1) {
            twoGuesses++;
            currentGuess = 2;
            if (twoGuesses > maxGuesses) { maxGuesses++; }
        } else if (g == 2) {
            threeGuesses++;
            currentGuess = 3;
            if (threeGuesses > maxGuesses) { maxGuesses++; }
        } else if (g == 3) {
            fourGuesses++;
            currentGuess = 4;
            if (fourGuesses > maxGuesses) { maxGuesses++; }
        } else if (g == 4) {
            fiveGuesses++;
            currentGuess = 5;
            if (fiveGuesses > maxGuesses) { maxGuesses++; }
        } else {
            sixGuesses++;
            currentGuess = 6;
            if (sixGuesses > maxGuesses) { maxGuesses++; }
        }
        
        save();
    }
}