// SKILLMEA ACADEMY COURSE SKL002
// TASK #1 - TIC TAC TOE GAME PROGRAMMING
// AUTHOR: Branislav Kristak
// DATE: 10.4.2024
// repository: https://github.com/bkristak/task1-tic-tac-toe
// description of the task:
//      the program shall present active playing grid 3x3, two players, all conditions shall be managed
//      conditions: invalid inputs, taken position, winner, draw, quit
//      optional: scanning player names or any other features, grid and input format up to the dev

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
// will keep the main method first, other methods (if used) are on the bottom

    // just wanted to practice enum in the code...
    // it was useful, finding out enum cannot start with number
    // it led me to additional condition to cover and to implement new feature changing order of characters in string
    enum Combinations {
        A1, A2, A3, B1, B2, B3, C1, C2, C3
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // just initializing scanner for later
        boolean status = true; // status should be used in the while logic later
        boolean validInput = false; // using in the inner while loop
        boolean emptyFields; // using later to check if there is draw
        String playChar = " X "; // startingPlayer will start first loop, can use as nominal to start, later dynamically changed with player change

        // Initliaze 2D matrix paramters and outline
        // Matrix is defined such as chess grid with x coordinates 1 to 3 and y coordinates A to B (8 x 8)
        String[][] arrayMatrix = new String[8][8];

        // game introduction for the players understanding
        System.out.println("                        WELCOME TO THE GAME");
        System.out.println("                           TIC TAC TOE");
        System.out.println("=================================================================");

        // Want to use method to build the initialized matrix to keep main method with less lines
        // calling the method:
        String[][] initializedArrayMatrix = initializeArrayMatrix(arrayMatrix);

        // Using for loop to print the initialized matrix from the method initializeArrayMatrix
        // from now on all values are stored in the initializedMatrix parameter
        System.out.println();
        System.out.println("You will use the following 2D matrix (chess like grid) in the game (size 3 x 3):");
        for (String[] row : initializedArrayMatrix) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }

        // information for players
        System.out.println("Players need to use matrix coordinates as input to make entry.");
        System.out.println("E.g. - allowed user entries are: A1 or 1a, A2 or 2a, A3, B1, B2, etc.");
        System.out.println("Note: Entries are not case sensitive, and order sensitive.");
        System.out.println("If you want to end the game, enter char 'q' instead of user entry.");
        System.out.println("Now, when you are ready, let's start the TIC TAC TOE game.");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("The game will have tow players. Let's assign name to the players.");

        // identify players with names
        System.out.println("> > > Enter name of the first player:");
        String player1 = scanner.nextLine();
        System.out.println("> > > Enter name of the second player:");
        String player2 = scanner.nextLine();
        System.out.println();
        System.out.println("We have our players: Welcome " + player1 + " & " + player2 + "!");
        System.out.println();
        System.out.println("Lets toss a coin who will start first!");
        System.out.println();

        // Wanted to have some feauture to randomly choose who starts first
        // calling the method:
        String startingPlayer = tossCoinMethod(player1, player2);

        // I needed to find this out on the web, did not know before if can be used this way in string definition...
        // found that ternary operator is widely used - String followingPlayer = (startingPlayer.equals(player1)) ? player2 : player1;
        // but I am more comfortable with this:
        String followingPlayer;
        if (startingPlayer.equals(player1)) {
            followingPlayer = player2;
        } else {
            followingPlayer = player1;
        }

        System.out.println("The random toss coin results are:");
        System.out.println(" > > > Starting player is " + startingPlayer.toUpperCase() + " < < <");
        System.out.println("Starting player will use X, and following player will use 0 in the play.");
        System.out.println();
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        System.out.println("= = = = = = =  TIC   TAC   TOE   B A T T L E   S T A R T S    = = = = = = =");

        // parameter to dynamically change players in one try-catch exception logic
        String currentPlayer = startingPlayer;

        // using nested loops to iterate through the game logic
        while (status) {

            // inner while will use try-catch exception
            while (!validInput) {

                try {
                    emptyFields = true; // need to reset to true at the beginning of each round, line 338 to 361

                    System.out.println("Player " + currentPlayer + " enter your coordinates for this play round:");
                    String currentPlayerInput = scanner.nextLine();
                    currentPlayerInput = currentPlayerInput.toUpperCase(); // to avoid additional conditions, useful to change all to upper case, not affecting input numbers in string

                    // if q or Q is entered, the program ends immediately
                    // yes/no selection to confirm not implemented
                    if (currentPlayerInput.equalsIgnoreCase("q")) {
                        status = false; // changin first while to false = ending the game
                        break; // breaking the try-catch
                    }

                    // expected usr input length is 2, if different, exception should be thrown
                    // try-catch exception shall re-iterate again asking same user to enter input
                    if (currentPlayerInput.length() != 2) {
                        throw new IllegalArgumentException("Invalid input, please try again. Remember to insert valid grid coordinates.");
                        // logic works keeping the try-catch active, but does nto print the message in "" in the throw new line
                    }

                    // check if correct format is entered by user (see enum for expected input)
                    if (!Character.isDigit(currentPlayerInput.charAt(1)) && Character.isDigit(currentPlayerInput.charAt(0))) {
                        currentPlayerInput = currentPlayerInput.charAt(1) + "" + currentPlayerInput.charAt(0);
                        System.out.println("Coordinates entered by player " + currentPlayer + " = " + currentPlayerInput + ".");
                    } else if (!Character.isDigit(currentPlayerInput.charAt(0)) && Character.isDigit(currentPlayerInput.charAt(1))) {
                        System.out.println("Coordinates entered by player " + currentPlayer + " = " + currentPlayerInput + ".");
                    } else {
                        // this shall cover when both are letters or both are numbers
                        throw new IllegalArgumentException("Invalid input, please try again. Remember to insert valid grid coordinates.");
                        // logic works keeping the try-catch active, but does nto print the message in ""
                    }

                    // was not sure how output of new exception, break or playChar would all be managed to be passed from method back to main
                    // decided to keep switch in main and not call a method
                    switch (Combinations.valueOf(currentPlayerInput)) {
                        case A1:
                            if (!initializedArrayMatrix[2][2].equals("___")) {
                                System.out.println("> ! Value in position A1 is already taken by " + initializedArrayMatrix[2][2] + ".");
                                // was trying to use this throw new exception as first directly with message
                                // throw new IllegalArgumentException(""> ! Value in position A1 is already taken....");
                                // cannot figure out why the message after throw new is not printed
                                // message above implemented
                                // the throw new at least detects there's conflict with taken position and returns the code flow to the beginning of try-catch
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[2][2] = playChar;
                            }
                            break;

                        case A2:
                            if (!initializedArrayMatrix[2][4].equals("___")) {
                                System.out.println("> ! Value in position A2 is already taken by " + initializedArrayMatrix[2][4] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[2][4] = playChar;
                            }
                            break;

                        case A3:
                            if (!initializedArrayMatrix[2][6].equals("___")) {
                                System.out.println("> ! Value in position A3 is already taken by " + initializedArrayMatrix[2][6] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[2][6] = playChar;
                            }
                            break;

                        case B1:
                            if (!initializedArrayMatrix[4][2].equals("___")) {
                                System.out.println("> ! Value in position B1 is already taken by " + initializedArrayMatrix[4][2] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[4][2] = playChar;
                            }
                            break;

                        case B2:
                            if (!initializedArrayMatrix[4][4].equals("___")) {
                                System.out.println("> ! Value in position B2 is already taken by " + initializedArrayMatrix[4][4] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[4][4] = playChar;
                            }
                            break;

                        case B3:
                            if (!initializedArrayMatrix[4][6].equals("___")) {
                                System.out.println("> ! Value in position B3 is already taken by " + initializedArrayMatrix[4][6] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[4][6] = playChar;
                            }
                                break;

                        case C1:
                            if (!initializedArrayMatrix[6][2].equals("___")) {
                                System.out.println("> ! Value in position C1 is already taken by " + initializedArrayMatrix[6][2] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[6][2] = playChar;
                            }
                            break;

                        case C2:
                            if (!initializedArrayMatrix[6][4].equals("___")) {
                                System.out.println("> ! Value in position C2 is already taken by " + initializedArrayMatrix[6][4] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[6][4] = playChar;
                            }
                            break;

                        case C3:
                            if (!initializedArrayMatrix[6][6].equals("___")) {
                                System.out.println("> ! Value in position C3 is already taken by " + initializedArrayMatrix[6][6] + ".");
                                throw new IllegalArgumentException();
                            } else {
                                initializedArrayMatrix[6][6] = playChar;
                            }
                            break;


                        default:
                            System.out.println("! ! ! User " + currentPlayer + " entered invalid coordinate value " + currentPlayerInput + ".");
                            throw new IllegalArgumentException();
                    }


                    // inform users how the matrix was updated
                    // lines below processed only when value in switch was accepted, new exception not threw, default case not returned
                    System.out.println();
                    System.out.println("Updated matrix based on user " + currentPlayer + " input " + currentPlayerInput + " is:");
                    for (String[] row : initializedArrayMatrix) {
                        for (String element : row) {
                            System.out.print(element);
                        }
                        System.out.println();
                    }

                    // evaluate if we have winner only if value in switch is accepted, new exception not threw
                    // decided to use nested if and not nested switch
                    // not sure if other than if or switch could be used
                    // also decided not to use for-loop in this case but provide each line manually
                    // in addition decided not to use and call method
                    // (personal preference only)
                    // maybe could work only with playChar and currentPlayer and not needed to check X and 0 after each round
                    if ((initializedArrayMatrix[2][2].equals(" X ")
                    && initializedArrayMatrix[2][4].equals(" X ")
                    && initializedArrayMatrix[2][6].equals(" X "))
                    || (initializedArrayMatrix[4][2].equals(" X ")
                        && initializedArrayMatrix[4][4].equals(" X ")
                        && initializedArrayMatrix[4][6].equals(" X "))
                            || (initializedArrayMatrix[6][2].equals(" X ")
                            && initializedArrayMatrix[6][4].equals(" X ")
                            && initializedArrayMatrix[6][6].equals(" X "))
                    ) { // startingPlayer evaluation per rows
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < " );
                        validInput = true; // ending the inner while loop
                        break; // this shall break the try-catch if condition is met

                    } else if ((initializedArrayMatrix[2][2].equals(" X ")
                    && initializedArrayMatrix[4][2].equals(" X ")
                    && initializedArrayMatrix[6][2].equals(" X "))
                        || (initializedArrayMatrix[2][4].equals(" X ")
                        && initializedArrayMatrix[4][4].equals(" X ")
                        && initializedArrayMatrix[6][4].equals(" X "))
                            || (initializedArrayMatrix[2][6].equals(" X ")
                            && initializedArrayMatrix[4][6].equals(" X ")
                            && initializedArrayMatrix[6][6].equals(" X "))
                    ) { // starting player evaluation per columns
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < ");
                        validInput = true;
                        break;

                    } else if ((initializedArrayMatrix[2][2].equals(" X ")
                    && initializedArrayMatrix[4][4].equals(" X ")
                    && initializedArrayMatrix[6][6].equals(" X "))
                        || (initializedArrayMatrix[2][6].equals(" X ")
                        && initializedArrayMatrix[4][4].equals(" X ")
                        && initializedArrayMatrix[6][2].equals(" X "))
                    ) { // starting player grid diagonals evaulation
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < ");
                        validInput = true;
                        break;

                    }

                    if ((initializedArrayMatrix[2][2].equals(" 0 ")
                    && initializedArrayMatrix[2][4].equals(" 0 ")
                    && initializedArrayMatrix[2][6].equals(" 0 "))
                    || (initializedArrayMatrix[4][2].equals(" 0 ")
                        && initializedArrayMatrix[4][4].equals(" 0 ")
                        && initializedArrayMatrix[4][6].equals(" 0 "))
                        || (initializedArrayMatrix[6][2].equals(" 0 ")
                            && initializedArrayMatrix[6][4].equals(" 0 ")
                            && initializedArrayMatrix[6][6].equals(" 0 "))
                    ) { // followingPlayer evaluation per rows
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < ");
                        validInput = true;
                        break;

                    } else if ((initializedArrayMatrix[2][2].equals(" 0 ")
                    && initializedArrayMatrix[4][2].equals(" 0 ")
                    && initializedArrayMatrix[6][2].equals(" 0 "))
                    || (initializedArrayMatrix[2][4].equals(" 0 ")
                        && initializedArrayMatrix[4][4].equals(" 0 ")
                        && initializedArrayMatrix[6][4].equals(" 0 "))
                        || (initializedArrayMatrix[2][6].equals(" 0 ")
                            && initializedArrayMatrix[4][6].equals(" 0 ")
                            && initializedArrayMatrix[6][6].equals(" 0 "))
                    ) { // following player evaluation per columns
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < ");
                        validInput = true;
                        break;


                    } else if ((initializedArrayMatrix[2][2].equals(" 0 ")
                    && initializedArrayMatrix[4][4].equals(" 0 ")
                    && initializedArrayMatrix[6][6].equals(" 0 "))
                        || (initializedArrayMatrix[2][6].equals(" 0 ")
                        && initializedArrayMatrix[4][4].equals(" 0 ")
                        && initializedArrayMatrix[6][2].equals(" 0 "))
                    ) { // following player grid diagonals evaulation
                        System.out.println("We have a winner!");
                        System.out.println("> > > ! ! ! The winner is " + startingPlayer.toUpperCase() + ", CONGRATULATIONS ! ! ! < < < ");
                        validInput = true;
                        break;

                    }


                    // check if any blank fields are in the grid
                    for (int row = 2; row < initializedArrayMatrix.length - 1; row += 2) {
                        for (int col = 2; col < initializedArrayMatrix.length - 1; col += 2) {
                            if (initializedArrayMatrix[row][col].equals("___")) {
                                emptyFields = false;
                                // if no empty fields are present, change logic to true for below if
                                break; // need to break the for loops as last input might change back to false
                           }
                        }
                        if (!emptyFields) { // !emptyFields expects status false to break - blank field detected
                            break; // breaks both for loops if empty is detected in the row, no need to check further rows
                        }
                    }
                    // If any cell is dtected as empty, the game continues (emptyFields = false)
                    // emptyFileds => if return true, the if conditions evalutes as true (no empty cell = draw)
                    // emptyFileds => if return false, the if conditions evalutes as false and not trigerred (empty cell detected, play continues)
                    if (emptyFields) {
                        // All cells are filled but no winner is present, empty filed not detected
                        System.out.println("We have a draw!");
                        System.out.println("> > > WE HAVE NO WINNER FOR THIS GAME < < <");
                        System.out.println("Congratulations to both players for a tough battle.");
                        validInput = true; // Set validInput to true to end the inner while loop
                        break; // breaking the try-catch
                    }


                    // this logic is processed only if none of the above winner or draw conditions are met
                    // preparing for next play round
                    System.out.println();
                    if (currentPlayer.equals(startingPlayer)) {
                        System.out.println("Changing player from " + startingPlayer + " to " + followingPlayer + ".");
                    } else {
                        System.out.println("Changing player from " + followingPlayer + " to " + startingPlayer + ".");
                    }
                    System.out.println("# % ˆ % * ( @ { @ # $ ) ) $ # $ % ^ { [ # @ ) > # = DONE!"); // :)


                     // change player
                     if (currentPlayer.equals(startingPlayer)) {
                         currentPlayer = followingPlayer;
                         } else {
                         currentPlayer = startingPlayer;
                      }
                    // printing the player change
                    System.out.println("> > > New active player is " + currentPlayer.toUpperCase() + " < < <");

                    // changing the playChar as the players changed
                    if (currentPlayer.equals(startingPlayer)) {
                        playChar = " X ";
                    } else {
                        playChar = " 0 ";
                    }

                // if conditions for input are not met, exception should be initiated
                } catch (Exception e) {
                    System.out.println("! ! ! Invalid input by player " + currentPlayer + ". Try again.");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    validInput = false;
                }


            } // end of inner while loop


            System.out.println("-----------------------------------------------------------------");
            System.out.println("              Thank you for playing the game!");
            break;

        } // end of main while loop


        System.out.println();
        System.out.println("Program ended. Have a good one.");
    }




//-------------------------------------------------------------------------------------
    // Methods used in the program:
//-------------------------------------------------------------------------------------
    public static String[][] initializeArrayMatrix(String[][] arrayMatrix) {
        // this method just initialize the matrix at the beginning of program
        // then later in the code, the initializedMatrix variable shall be used
        // the idea is to provide the matrix baseline values to construct the matrix
        // fields for the players are left as blank spaces
        // the for loops below shall save repeated manual entries
        // note: it helped me a lot to draw down the matrix with indexes
        // building the matrix shall help to get rid of null values in the matrix printing
        String[][] initializedMatrix = new String[arrayMatrix.length][arrayMatrix.length];

        // havent figured out if this can be automatized, since only 7 lines, filled manually...
        // since I used '---' three dashed for rows separation, each charater is preceeded and succeeded by blank
        // this is to keep the matrix aligned when printed
        // same approach to be used later for user fileds
        initializedMatrix[0][0] = " T ";
        initializedMatrix[0][2] = " 1 ";
        initializedMatrix[0][4] = " 2 ";
        initializedMatrix[0][6] = " 3 ";
        initializedMatrix[2][0] = " A ";
        initializedMatrix[4][0] = " B ";
        initializedMatrix[6][0] = " C ";

            // for loop to fill fixed columns (to represent columns lines)
            for (int row = 0; row < arrayMatrix.length; row++) {
                for(int col = 1; col < arrayMatrix.length; col += 2) {
                    initializedMatrix[row][col] = "|";
                }
            }

            // for loop to fill fixed rows (to represent rows lines)
            for (int row = 1; row < arrayMatrix.length; row += 2) {
                for (int col = 0; col < arrayMatrix.length; col += 2) {
                    initializedMatrix[row][col] = "---";
                }
            }

            // for loop to fill blanks in the user fields (variable field)
            for (int row = 2; row < arrayMatrix.length - 1; row += 2) {
                for (int col = 2; col < arrayMatrix.length - 1; col += 2) {
                    initializedMatrix[row][col] = "___"; // 3 blank spaces to align matrix
                }
            }
            // returning the matrix back to main
            // from now on, initializedMatrix variable will be used to print matrix
            return initializedMatrix;
    }

// -------------------------------------------------------------------------------------
    public static String tossCoinMethod(String player1, String player2) {
        // I know we were not suppose to google, but I wanted to have this feautre...
        // so I googled how to randomly assign who starts the game... :(
        Random tossCoin = new Random();
        int randomNum = tossCoin.nextInt(2);

            if (randomNum == 0) {
                return player1; // player 1 starts
            } else {
                return player2; // player 2 starts
            }

    }
// -------------------------------------------------------------------------------------

}