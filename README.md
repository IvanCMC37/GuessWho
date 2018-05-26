# GuessWho
Design and implement Guess Who guessing algorithms.

To compile the fles, run the following command from the root directory (the directory that Guess-
Who.java is in):

javac -cp .:jopt-simple-5.0.2.jar *.java

Note that for Windows machine, remember to replace ':' with ';' in the classpath.

To run the Guess Who framework:

java -cp .:jopt-simple-5.0.2.jar GuessWho [-l <game log file>] <game configuration file> <chosen person file>
<player 1 type> <player 2 type>
where

game log fle: name of the file to write the log of the game.

game configuration file: name of the file that contains the attributes, values and persons in the Guess Who game.

chosen person file: name of the file that specispecifieses which person that each player have chosen.

player 1 type: specifies which type of player to use for first player, one of [random | binary],
with random is the random guessing player and binary is the binary-search based guessing player.

player 2 type: specifieses which type of player to use for second player, one of [random | binary].
The jar file contains a library for reading command line options.
We next describe the contents of the game configuration and chosen person files.
