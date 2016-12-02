#cs56-games-simon-says

project history
===============
```
YES | mastergberry | hzpkk520,paranoia1120 | A "Simon Says" game where the player must replicate a series of actions
```
```
 W14 | bkiefer13 5pm | hzpkk520,paranoia1120 | A "Simon Says" game where the player must replicate a series of actions
```


 - __Navigation__: 
   - [Introduction](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#introduction)
   - [Interface](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#interface)
      - [New Game](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#new-game)
      - [Choose Version](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#choose-version)
	  - [Rules](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#rules)
	  - [Exit](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#exit)
   - [Documentation](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#documentation)
   - [How to run](https://github.com/PARanOiA1120/cs56-games-simon-says/blob/master/README.md#how-to-run)


##Introduction

 The idea of the game is that a number of colored buttons will flash in some sequence, and the user must then click on the buttons in that same sequence, with one new flash being added to the sequence at the start of each round.  In this implementation, there will be four buttons, a number of which will "flash" in a randomized sequence at the start of each round. Then, the user must click on the buttons in the same order that the buttons flashed. Then for each subsequent round, one more button flash is added to then end of that sequence. The player loses when they click on a button that is out-of-order, and their score will be based on how many rounds.
 
 - There are three levels for the game: Amateur, Intermediate and Professional.
   - Amateur: 4-button configuration with normal flash speed.(The same as "New Game" mode)
   - Intermediate: 4-button configuration with fast flash speed.
   - Professional: 6-button configuration with normal flash speed.


##Interface

* The game starts with a window that has a start screen like this:

![](http://i.imgur.com/Xf5B1lY.png)


###New Game

* A player can directly start a new game of 'Amateur' level by clicking on "New Game" button
* The basic interface of the game consists of four colored buttons on the screen, a "Start" button to start the game, an "Exit" button to go back to the main menu, and also a "Score:" lable at the top right corner of the screen that shows the highest score the player has got. Like this:

![](http://i.imgur.com/OlnZek7.png)

* Also, regardless of what level the player is currently playing, if the player gets a new high score while playing, the "Score:" label at the corner will turn red to indicate that current score is the highest score recorded:

![](http://i.imgur.com/N04a0kb.png)


###Choose Version
* The "Choose Version" button on the main menu will let player select what level they want to play:

![](http://i.imgur.com/sUST0nA.png)

* Professional level looks like this: 

![](http://i.imgur.com/2om18pt.png?1)

* Intermediate and Amateur level has the same layout as the basic layout when player clicks on "New Game" button on the main menu:

![](http://i.imgur.com/OlnZek7.png)

* And the "Back" button on "Choose Version" interface just brings player back to the main menu.


###Rules
* The "Rules" button shows the player the rules of the game:

![](http://i.imgur.com/Wi5zycs.png)

* The "Back" button brings player back to the main menu.


###Exit
* The "Exit" button closes the game.


## Documentation

* `SimonAmFlash` class constructs the main game thread for Amateur level that switches turns between the computer and the user to correctly let the game proceed.

* `SimonAmFrame` class constructs the main frame of the game for Amateur level: four buttons, "Start" button, "Exit" button and "Score:" label, as well as the listeners for the buttons.

* `SimonInterL` class class constructs the main game thread for Intermediate level that switches turns between the computer and the user to correctly let the game proceed.

* `SimonInterLF` class constructs the main frame of the game for Intermediate level: four buttons, "Start" button, "Exit" button and "Score:" label, as well as the listeners for the buttons.

* `SimonProFlash` class class constructs the main game thread for Professional level that switches turns between the computer and the user to correctly let the game proceed.

* `SimonProL` class constructs the main frame of the game for Professional level: six buttons, "Start" button, "Exit" button and "Score:" label, as well as the listeners for the buttons.



* `SimonButton` is a subclass of `JButton` that defines the buttons that are clicked by player. It has two constructors:

-- A no-argument constructor that set the preferred size of the button to 100 by 100:

```java
    public SimonButton() {
	  this.setPreferredSize(new Dimension(100,100));
	}
```
-- A Color-parameter constructor that calls no-arg constructor to set size and then sets button color to Color passed in:

```java
      public SimonButton(Color color) { 
	      this();
	      this.setBackground(color);
	      this.setBorderPainted(false);
	      this.setOpaque(true);
	  }
```

* `SimonLevel` is a class that defines the interface of "Choose Version" with 4 buttons that let players select different levels they want to play and a "Back" button that leads players back to main menu. 

* `SimonRules` is a class that shows the rules of the game to players. It reads the contents of a text file "Rules.txt" and put them on the screen.

* `SimonMenu` is a class that defines the layout of the start(main) menu of the game: "New Game" button, "Choose Version" button, "Rules" button and "Exit" button.

* `SimonSaysGame` under *model* folder is part of the code refactoring. It is the model part of a MVC structure of the game. It defines purely the game logic with some useful methods to help the game run correctly.

* `SimonSaysTest` under *model* folder is the JUnit test for model SimonSaysGame.

* `SimonSaysGameOld` under *model* folder is essentially the same as `SimonFlash` where the game logic are mostly defined. It is the source from which `SimonSaysGame` is derived.

* The high score that a players gets for three different levels are stored in three different files: `HighScore.txt` for Amateur level, `HighScoreInterLevel.txt` for Intermediate level, and `HighScoreProLevel.txt` for Professional level.


## How to run 
To start the game, use `ant run`. 


## W16 Final Remarks
To the students that next work on this project, please read this note before looking at the aforementioned code as it will help speed the process up of understanding the logic going on in the project. This note is written in a format which I wish I could have viewed before tackling the issues present in the code.

For starters, the code is organized in such a way that each class file designates a "screen" in the game. The important screens to note are: SimonMenu.java (Main Menu), SimonLevel.java (List of Levels), SimonRules.java (Rules), and SimonHighScores.java (High Scores). Each level of the game has two files. The logic for the amateur level can be found in SimonFlash.java and the framework in SimonFrame.java. The intermediate level is switched around for whatever reason so that the logic can be found in SimonInterL.java and its framework in SimonInterLF.java. The professional level is similar to the amateur in that its logic can be found in SimonProFlash.java and its framework in SimonProL.java. I hope this knowledge can be as useful to you as it was to my partner and I.

Second, from what we can tell the files in Model have no effect on the current state of the game. We believe this folder is for the issue labelled refactoring the game into a MVC design pattern. This needs a lot of work which is why it is worth 300 points.

In terms of the actual code of the project, it seems to work fine although pressing the buttons too fast results in errors. We believe this issue is from the thread.sleep() functions which tend to vary through out the code (but also affect the difficulty of the game). A lot of the issues that we believe are still present in this project, something we disregarded, is the lack of organization and code clean-up that it needs. The specifics of these can be found under the issues tab. Something else that can also be cleaned up is this README file which we did not take the time to update in terms of what is presently in the game. A statement that must be noted however is, how much can be added to this project until it is too much? We think this project could use a lot more refinements rather than additions.

With that being said, we wish you the best of luck in continuing this legacy project. 

## F16 Final Remarks
To the next group of students working on this project, there are a few things that would be helpful to know. Each of the 3 levels have 2 classes, the SimonFrame and SimonFlash. The Frame is what sets up the the view for the game, and the actual game logic is in the flash. All of the other classes should have a pretty apparent function. I would make sure you fully understand all of the classes and how they fit together before trying to tackle any of the issues.

The amateur and intermediate level are the normal Simon Says games just at varying speeds. The Expert level has 6 buttons, 3 lives, and a timer for each round. 

If I was just starting this project, I would probably start by refactoring the code. Once you understand what all of the classes are doing it shouldn't be that hard, and it will make the code a lot more DRY and easier to work with, for both you and future students working on this project.

Also don't wait until the end of the quarter to start writing issues. If you notice a bug in the game or if you have any idea that could make the game better/ more fun/ anything like that, then create the issues then. It will make your life a lot easier, because you will be doing this while all of the information is still fresh in your mind, and you won't have to go back and do it later during dead week.

Good luck with your legacy code project and have fun!

