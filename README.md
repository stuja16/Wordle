# Wordle in Java

This project is a recreation of the viral Wordle NYT game in Java. It is not intended for distribution, please support the original game.

## Description

This program has many features of the original game, including the ability to type words using either the keyboard or mouse.
Rather than new daily, handpicked words, you can immediately play again and the game will randomly select a word from the dictionary of over 4.5k 5-letter words.
Personal statistics are tracked and are automatically displayed after every game, but you can also manually check them from the game menu.

This was one of my first attempts at implementing a GUI in Java, and working through many of the quirks of the language was a challenge.

## Executing program

Since this project is not intended for distribution, it has not been configured to run outside of a development environment.

The program was originally written in the [Apache NetBeans IDE](https://netbeans.apache.org/front/main/index.html), and I have included the related project files in this repository for convenience.
If you wish to run the program in another environment, you will have to delete this file or copy the java/fxml files into a new project folder.

## Game in Action

Upon starting the program or starting a new game, you're greeted with a blank game board.
![Image](https://github.com/user-attachments/assets/dc2f46aa-c17e-4523-ac9a-0e8b1baf3d2b)

You can either type on your computer's keyboard or use your mouse to click on the keys on the GUI to enter your guesses.
Upon entering a guess, it will first be checked to ensure that you have entered a valid word.
![Image](https://github.com/user-attachments/assets/1f3a7098-147b-4427-aa31-94a0ae270fa6)

Like in the real game, if you enter a valid word it will light up the keys and cells to show you how close your guesses are to the secret word.
As you can see, there is also proper checking for words with double letters!!
![Image](https://github.com/user-attachments/assets/40bbe2cf-9349-4964-b6c1-169559f3690a)

When you guess correctly or if you select the "Statistics" option in the menu, this screen appears.
It emulates the NYT official stat page which was not originally behind a paywall as it is now.
Your statistics are stored in the project folder, so they are persistent even if you close and reopen the game.
![Image](https://github.com/user-attachments/assets/bfac8d69-cf58-4aae-8017-92d3999c05c3)

## License

Do not distribute this project in part or in whole. This project may not be used for any commercial purposes.
This project is only intended as an academic and explorative exercise of trying to adapt software into a different format.

## Acknowledgments

This project was originally created for an intermediate programming course, so the credit for the idea goes to my professor. I sought to go above an beyond in making this idea come to life.
