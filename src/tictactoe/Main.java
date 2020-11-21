package tictactoe;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Mack_TB
 * @version 2.1
 * @since 11/21/2020
 */

public class Main {

    static Scanner sc;
    static char[][] fields = new char[3][3];
    static boolean xWin, oWin;
    static int numEmpty;
    static int numX, numO;
    static int firstCoord, secondCoord;
    static char[] players = {'X', 'O'};
    static char aiPlayer = players[0], huPlayer = players[1];
    static String[] states = {"Game not finished", "Draw", "X wins", "O wins", "Impossible"};
    static String[] inputCommands;
    static String level = null;
    static boolean isPlayerComputer = false;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String[] commands = new String[]{"start", "exit"};
        String[] start = new String[]{"user", "easy", "medium", "hard"};
        String input;

        resetFields();

        while (true) {
            System.out.print("Input command: ");
            input = sc.nextLine();
            inputCommands = input.split("\\s");
            if (inputCommands[0].equals(commands[0]) && inputCommands.length == 3) {
                if ((inputCommands[1].equals(start[0]) || inputCommands[1].equals(start[1]) || inputCommands[1].equals(start[2]) || inputCommands[1].equals(start[3])) &&
                    (inputCommands[2].equals(start[0]) || inputCommands[2].equals(start[1]) || inputCommands[2].equals(start[2]) || inputCommands[2].equals(start[3]))) {

                    if (input.contains("easy") && input.contains("medium")) {
                        isPlayerComputer = true;
                        level = inputCommands[1].equals("easy") ? "easy" : "medium";
                    } else if (input.contains("medium") && input.contains("hard")) {
                        isPlayerComputer = true;
                        level = inputCommands[1].equals("medium") ? "medium" : "hard";
                    } else if (input.contains("easy") && input.contains("hard")) {
                        isPlayerComputer = true;
                        level = inputCommands[1].equals("easy") ? "easy" : "hard";
                    } else { // same level
                        if (input.contains("easy")) {
                            level = "easy";
                        } else if (input.contains("medium")) {
                            level = "medium";
                        } else if (input.contains("hard")) {
                            level = "hard";
                        }
                        if (inputCommands[1].equals(level)) {
                            isPlayerComputer = true;
                        }
                    }

                    showCells();

                    playGame();

                } else System.out.println("Bad parameters!1");

            } else if (inputCommands[0].equals(commands[1])) {
                return;
            } else {
                System.out.println("Bad parameters!2");
            }
        }
    }


    private static void playGame() {
        String status = "";
        while (!status.equals(states[1]) && !status.equals(states[2]) &&
                !status.equals(states[3])) {

            // Since the game always start with X
            selectCoordinates(numX == numO ? players[0] : players[1]);

            showCells();
            winning(fields, null);
            status  = getStatus();
        }
        System.out.println(status+"\n");
        resetFields();
    }

    // Fill the cells with blank
    private static void resetFields() {
        xWin = false;
        oWin = false;
        numX = 0;
        numO = 0;
        level = null;
        isPlayerComputer = false;
        for (int j = fields.length - 1; j >= 0; j--) { // row
            for (int i = 0; i < fields.length; i++) { // col
                fields[i][j] = ' ';
            }
        }
    }

    private static String getStatus() {
        String status;
        int difference = (numX > numO) ? numX - numO : numO - numX;

        if (((oWin) && (xWin)) || (difference >= 2)) { // Impossible
            status = states[4];
        } else if (oWin) {
            status = states[3];
        } else if (xWin) {
            status = states[2];
        } else if (numEmpty != 0) { // Game not finished
            status = states[0];
        } else { // numEmpty == 0   // Draw
            status = states[1];
        }
        return status;
    }

    private static void showCells() {
        System.out.println("---------");
        for (int j = fields.length - 1; j >= 0; j--) { // row
            System.out.print("|");
            for (int i = 0; i < fields.length; i++) { // col
                System.out.print(" "+fields[i][j]);
            }
            System.out.println(" |");
        }
        System.out.println("---------");
    }

    private static boolean winning(char[][] fields, String player) {
        numEmpty = 0;
        String playerLined = null;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        // First step: check the winner in the rows (---)

        for (int j = fields.length - 1; j >= 0; j--) { // row
            for (int i = 0; i < fields.length; i++) { // col
                setter(fields, player, sb, sb2, j, i);
            }
            if (player == null) {
                if (sb2.toString().equals("XXX")) {
                    xWin = true;
                    return true;
                } else if (sb2.toString().equals("OOO")) {
                    oWin = true;
                    return true;
                }
            } else {
                playerLined = (player+"").repeat(3);
                if (sb.toString().equals(playerLined)) {
                    return true;
                }
            }
            sb = new StringBuilder();
            sb2 = new StringBuilder();
            numEmpty = 0;
        }

        // second step: check the winner in the cols and diagonals
        StringBuilder firstDiag = new StringBuilder();  // \
        StringBuilder secondDiag = new StringBuilder(); // /
        int p = -1, q = fields.length;
        for (int i = 0; i < fields.length; i++) { // col
            firstDiag.append(fields[++p][--q]);
            secondDiag.append(fields[i][i]);
            for (int j = fields.length - 1; j >= 0; j--) { // row
                setter(fields, player, sb, sb2, j, i);
            }
            if (player == null) {
                if (sb2.toString().equals("XXX")) {
                    xWin = true;
                    return true;
                } else if (sb2.toString().equals("OOO")) {
                    oWin = true;
                    return true;
                }
            } else {
                if (sb.toString().equals(playerLined)) {
                    return true;
                }
            }
            sb = new StringBuilder();
            sb2 = new StringBuilder();
        }

        if (player == null) {
            if (firstDiag.toString().equals("XXX") || secondDiag.toString().equals("XXX")) {
                xWin = true;
                return true;
            } else if (firstDiag.toString().equals("OOO") || secondDiag.toString().equals("OOO")) {
                oWin = true;
                return true;
            }
            return false;
        } else {
            return firstDiag.toString().equals(playerLined)
                    || secondDiag.toString().equals(playerLined);
        }
    }

    private static void setter(char[][] fields, String player, StringBuilder sb, StringBuilder sb2, int j, int i) {
        if (player != null) {
            if (fields[i][j] == player.charAt(0)) {
                sb.append(player);
            } else if (fields[i][j] == ' ') {
                numEmpty++;
            }
        } else {
            if (fields[i][j] == ' ') {
                numEmpty++;
            } else {
                sb2.append(fields[i][j]);
            }
        }
    }

    private static void selectCoordinates(char player) {
        if (level != null){
            if ((inputCommands[1].equals("easy") || inputCommands[1].equals("medium") || inputCommands[1].equals("hard")) &&
                (inputCommands[2].equals("easy") || inputCommands[2].equals("medium") || inputCommands[2].equals("hard"))) { // match between two AI (easy|medium|hard) & (easy|medium|hard)
                findComputer(player);
            } else if (inputCommands[1].equals("user") && inputCommands[2].equals(level)) { // match between [human & (easy|medium|hard) computer]
                if (player == 'X') playAsUser();
                else findComputer(player);
            } else { // match between [(easy|medium|hard) computer & human]
                if (player == 'X') findComputer(player);
                else playAsUser();
            }

        } else { // match between two humans
            playAsUser();
        }

        affectation(player);
    }

    private static void findComputer(char player) {
        switch (level) {
            case "hard":
                System.out.println("Making move level \"hard\"");
                playAsComputer(fields);
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                playAsComputer(player);
                break;
            default:
                System.out.println("Making move level \"easy\"");
                playAsComputer();
                break;
        }
    }

    // Assignment to the matching cell
    private static void affectation(char player) {
        boolean okay = false;
        for (int i = 0; i < fields.length; i++) { // col
            for (int j = fields.length - 1; j >= 0; j--) { // row
                if ((i == (firstCoord - 1)) && (j == (secondCoord - 1))) {
                    if (String.valueOf(fields[i][j]).equals("_") ||
                            String.valueOf(fields[i][j]).equals(" ")) {
                        fields[i][j] = player; // affectation
                        if (fields[i][j] == 'X') {
                            numX++;
                        } else { // 'O'
                            numO++;
                        }
                        if (level != null) {
                            if (!((inputCommands[1].equals("easy") || inputCommands[1].equals("medium") || inputCommands[1].equals("hard")) &&
                                  (inputCommands[2].equals("easy") || inputCommands[2].equals("medium") || inputCommands[2].equals("hard")))) {
                                isPlayerComputer = !isPlayerComputer;
                            }

                            if (inputCommands[1].equals("easy") && inputCommands[2].equals("medium")
                             || inputCommands[1].equals("medium") && inputCommands[2].equals("easy")) {
                                level = level.equals("easy") ? "medium" : "easy";
                            }
                            if (inputCommands[1].equals("medium") && inputCommands[2].equals("hard")
                             || inputCommands[1].equals("hard") && inputCommands[2].equals("medium")) {
                                level = level.equals("medium") ? "hard" : "medium";
                            }
                            if (inputCommands[1].equals("easy") && inputCommands[2].equals("hard")
                             || inputCommands[1].equals("hard") && inputCommands[2].equals("easy")) {
                                level = level.equals("easy") ? "hard" : "easy";
                            }
                        }

                    } else {
                        if (level == null) { // user user
                            System.out.println("This cell is occupied! Choose another one!");
                        } else {
                            if (!isPlayerComputer) {
                                System.out.println("This cell is occupied! Choose another one!");
                            }
                        }
                        selectCoordinates(player);
                    }
                    okay = true;
                    break;
                }
            }
            if (okay) {
                break;
            }
        }
    }

    /**
     * Difficulty level: easy.
     * Make a random move
     */
    private static void playAsComputer() {
        Random random = new Random();
        firstCoord = random.nextInt(3) + 1;
        secondCoord = random.nextInt(3) + 1;
    }


    /**
     * Difficulty level: medium.
     * 1. If it can win in one move (if it has two in a row), it places a third to get three in a row and win.
     * 2. If the opponent can win in one move, it plays the third itself to block the opponent to win.
     * 3. Otherwise, it makes a random move.
     * @param player the player
     */
    private static void playAsComputer(char player) {
        firstCoord = 0;
        secondCoord = 0;
        numEmpty = 0;
        StringBuilder subStr = new StringBuilder();

        int m = 1;
        String xx = "XX";
        String oo = "OO";

        while (m <= 2) {
            // First step: checking in the rows
            for (int j = fields.length - 1; j >= 0; j--) { // row
                for (int i = 0; i < fields.length; i++) { // col
                    if (fields[i][j] == 'X' || fields[i][j] == 'O') {
                        subStr.append(fields[i][j]);
                    } else {
                        ++numEmpty;
                        firstCoord = i;
                        secondCoord = j;
                    }
                }
                if (coordinatesResult(player, xx, oo, subStr)) return;
                subStr = new StringBuilder();
            }

            // second step: checking in the cols
            for (int i = 0; i < fields.length; i++) { // col
                for (int j = fields.length - 1; j >= 0; j--) { // row
                    if (fields[i][j] == 'X' || fields[i][j] == 'O') {
                        subStr.append(fields[i][j]);
                    } else {
                        ++numEmpty;
                        firstCoord = i;
                        secondCoord = j;
                    }
                }
                if (coordinatesResult(player, xx, oo, subStr)) return;
                subStr = new StringBuilder();
            }

            // third step : checking in the first diagonal
            StringBuilder firstDiag = new StringBuilder();  // '\'
            int p = -1, q = fields.length;
            for (int i = 0; i < fields.length; i++) {
                p++;
                --q;
                if (fields[p][q] == 'X' || fields[p][q] == 'O') {
                    firstDiag.append(fields[p][q]);
                } else {
                    ++numEmpty;
                    firstCoord = p;
                    secondCoord = q;
                }
            }
            if (coordinatesResult(player, xx, oo, firstDiag)) return;

            // fourth step: checking in the second diagonals
            StringBuilder secondDiag = new StringBuilder(); // '/'
            for (int i = 0; i < fields.length; i++) {
                if (fields[i][i] == 'X' || fields[i][i] == 'O') {
                    secondDiag.append(fields[i][i]);;
                } else {
                    ++numEmpty;
                    firstCoord = i;
                    secondCoord = i;
                }
            }
            if (player == 'X') {
                if (secondDiag.toString().equals(xx) && numEmpty == 1) {
                    firstCoord++;
                    secondCoord++;
                    return;
                }
            } else {
                if (secondDiag.toString().equals(oo) && numEmpty == 1) {
                    firstCoord++;
                    secondCoord++;
                    return;
                }
            }

            xx = oo;
            oo = xx;
            m++;
        }

        playAsComputer();
    }

    private static boolean coordinatesResult(char player, String xx, String oo, StringBuilder firstDiag) {
        if (player == 'X') {
            if (firstDiag.toString().equals(xx) && numEmpty == 1) {
                firstCoord++;
                secondCoord++;
                return true;
            }
        } else {
            if (firstDiag.toString().equals(oo) && numEmpty == 1) {
                firstCoord++;
                secondCoord++;
                return true;
            }
        }
        firstCoord = 0;
        secondCoord = 0;
        numEmpty = 0;
        return false;
    }

    /**
     * Difficulty level: hard.
     * We use the algorithm Minimax.
     * It's a brute force algorithm that maximizes the value of the own position and minimizes the value of the opponent's position.
     * It's not only an algorithm for Tic-Tac-Toe, but for every game with two players with alternate move order, for example, chess.
     * @param fields the board
     */
    private static void playAsComputer(char[][] fields) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestSpot = new int[2];
        if (inputCommands[1].equals("user") && inputCommands[2].equals(level)) {
            huPlayer = 'X';
            aiPlayer = 'O';
        } else if (inputCommands[1].equals(level) && inputCommands[2].equals("user")) {
            huPlayer = 'O';
            aiPlayer = 'X';
        }
        for (int j = fields.length - 1; j >= 0; j--) { // row
            for (int i = 0; i < fields.length; i++) { // col
                if (fields[i][j] == ' ') {
                    fields[i][j] = aiPlayer;
                    int score = minimax(fields,false);
                    fields[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestSpot = new int[]{i, j};
                    }
                }
            }
        }
        firstCoord = bestSpot[0] + 1;
        secondCoord = bestSpot[1] + 1;
    }

    // find the optimal spot
    private static int minimax(char[][] newFields, boolean isMaximizing) {

        // checks for the terminal states such as win, lose, and tie
        //and returning a value accordingly
        if (winning(newFields, String.valueOf(huPlayer))) {
            return -1;
        } else if (winning(newFields, String.valueOf(aiPlayer))) {
            return 1;
        } else if (numEmpty == 0){
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int j = newFields.length - 1; j >= 0; j--) { // row
                for (int i = 0; i < newFields.length; i++) { // col
                    if (newFields[i][j] == ' ') {
                        newFields[i][j] = aiPlayer;
                        int score = minimax(newFields, false);
                        newFields[i][j] = ' ';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int j = newFields.length - 1; j >= 0; j--) { // row
                for (int i = 0; i < newFields.length; i++) { // col
                    if (newFields[i][j] == ' ') {
                        newFields[i][j] = huPlayer;
                        int score = minimax(newFields, true);
                        newFields[i][j] = ' ';
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }


    private static void playAsUser() {
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                firstCoord = Integer.parseInt(sc.next());
                secondCoord = Integer.parseInt(sc.next());
            } catch (NumberFormatException nfe) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (firstCoord <= 0 || firstCoord > 3 || secondCoord <= 0 || secondCoord > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                break;
            }
        }
    }
}
