// Graphics Libraries
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ChessGUI extends JPanel {
    public static int[][] boardArray = new int[8][8]; // A board that tracks the position of all the pieces on the chess board.
    public static ChessPieceClass[] pieces = new ChessPieceClass[32]; // Creates an array to hold all the chess pieces.

    public static int turn = -1;
    public static boolean pieceSelected = false;
    public static int numPieceSelected;
    public static JLabel status = new JLabel("Nothing Happened");
    public static int moved2ID = -1;
    public static int movesUntilDraw = 50;

    public static int newX;
    public static int newY;
    public static int oldX;
    public static int oldY;
    private final int SQUARE_CONSTANT = 90;
    private final int PIECE_OFFSET_X = 30;
    private final int PIECE_OFFSET_Y = 28;
    private final Color BROWN_COLOR = new Color(102, 51, 0);  
    private int squareStartingX;
    private int squareStartingY;
    private int tileColor;

    
    public static void main(String[] args) {
        createPieceObjects(); 
        updateBoard();
        board();
    }

    // Uses the ChessPieceClass constructor to create all the pieces on a chess board
    public static void createPieceObjects() { 
        BufferedImage whitePawnIcon = null;
        BufferedImage whiteBishopIcon = null;
        BufferedImage whiteKnightIcon = null;
        BufferedImage whiteRookIcon = null;
        BufferedImage whiteQueenIcon = null;
        BufferedImage whiteKingIcon = null;
        BufferedImage blackPawnIcon = null;
        BufferedImage blackBishopIcon = null;
        BufferedImage blackKnightIcon = null;
        BufferedImage blackRookIcon = null;
        BufferedImage blackQueenIcon = null;
        BufferedImage blackKingIcon = null;
        {
            try {
                whitePawnIcon = ImageIO.read(new File("images/WhitePawn.png"));
                whiteBishopIcon = ImageIO.read(new File("images/WhiteBishop.png"));
                whiteKnightIcon = ImageIO.read(new File("images/WhiteKnight.png"));
                whiteRookIcon = ImageIO.read(new File("images/WhiteRook.png"));
                whiteQueenIcon = ImageIO.read(new File("images/WhiteQueen.png"));
                whiteKingIcon = ImageIO.read(new File("images/WhiteKing.png"));
                blackPawnIcon = ImageIO.read(new File("images/BlackPawn.png"));
                blackBishopIcon = ImageIO.read(new File("images/BlackBishop.png"));
                blackKnightIcon = ImageIO.read(new File("images/BlackKnight.png"));
                blackRookIcon = ImageIO.read(new File("images/BlackRook.png"));
                blackQueenIcon = ImageIO.read(new File("images/BlackQueen.png"));
                blackKingIcon = ImageIO.read(new File("images/BlackKing.png"));
            }
            catch (IOException e) {
            }
        }
        for (int i = 0; i < 8; i++) {
            pieces[i] = new ChessPieceClass(1, i, 6, false, whitePawnIcon);
            pieces[i + 16] = new ChessPieceClass(7, i, 1, false, blackPawnIcon);
        }
        for (int i = 0; i < 2; i++) {
            pieces[i + 8] = new ChessPieceClass(2, i * 7, 7, false, whiteRookIcon);
            pieces[i + 10] = new ChessPieceClass(3, i * 5 + 1, 7, false, whiteKnightIcon);
            pieces[i + 12] = new ChessPieceClass(4, i * 3 + 2, 7, false, whiteBishopIcon);
            pieces[i + 24] = new ChessPieceClass(8, i * 7, 0, false, blackRookIcon);
            pieces[i + 26] = new ChessPieceClass(9, i * 5 + 1, 0, false, blackKnightIcon);
            pieces[i + 28] = new ChessPieceClass(10, i * 3 + 2, 0, false, blackBishopIcon);
        }
        pieces[14] = new ChessPieceClass(5, 3, 7, false, whiteQueenIcon);
        pieces[15] = new ChessPieceClass(6, 4, 7, false, whiteKingIcon);
        pieces[30] = new ChessPieceClass(11, 3, 0, false, blackQueenIcon);
        pieces[31] = new ChessPieceClass(12, 4, 0, false, blackKingIcon);
    }
    // Updates the board
    public static void updateBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                boardArray[y][x] = 0;
            }
        }
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPieceID() != -1) {
                boardArray[pieces[i].getY()][pieces[i].getX()] = pieces[i].getPieceID();
            }
        }
    }
    // Checks to see whether the piece chosen by the player is valid or not.
    public static boolean selectPiece(int x){
        if (turn == -1) {
            if (x >= 0 && x <= 15 && pieces[x].getPieceID() != -1) {
                return true;
            }
        }
        else if (turn == 1) {
            if (x >= 16 && x <= 31 && pieces[x].getPieceID() != -1) {
                return true;
            }
        }
        return false;
    }
    // Determines the x-coordinate of the position of the board the player clicked on.
    public static int parseX(int x) {
        x = x - 30;
        if (x < 90 && x > 0) {return 0;}
        else if (x < 180 && x > 90) {return 1;}
        else if (x < 270 && x > 180) {return 2;}
        else if (x < 360 && x > 270) {return 3;}
        else if (x < 450 && x > 360) {return 4;}
        else if (x < 540 && x > 450) {return 5;}
        else if (x < 630 && x > 540) {return 6;}
        else if (x < 720 && x > 630) {return 7;}
        else {return -1;}
    }
    // Determines the y-coordinate of the position of the board the player clicked on.
    public static int parseY(int y) {
        y = y - 100;
        if (y < 90 && y > 0) {return 0;}
        else if (y < 180 && y > 90) {return 1;}
        else if (y < 270 && y > 180) {return 2;}
        else if (y < 360 && y > 270) {return 3;}
        else if (y < 450 && y > 360) {return 4;}
        else if (y < 540 && y > 450) {return 5;}
        else if (y < 630 && y > 540) {return 6;}
        else if (y < 720 && y > 630) {return 7;}
        else {return -1;}
    }
    // Determines what piece the pawn is promoted to based on where the player clicked on the board.
    public static int parsePromotion(int x, int y) {
        if (turn == -1) {
            if (y < 810 && y > 720 && x < 810 && x > 720) {return 2;}
            else if (y < 720 && y > 630 && x < 810 && x > 720) {return 3;}
            else if (y < 630 && y > 540 && x < 810 && x > 720) {return 4;}
            else if (y < 540 && y > 450 && x < 810 && x > 720) {return 5;}
        }
        else if (turn == 1) {
            y -= 100;
            if (y < 90 && y > 0 && x < 810 && x > 720) {return 8;}
            else if (y < 180 && y > 90 && x < 810 && x > 720) {return 9;}
            else if (y < 270 && y > 180 && x < 810 && x > 720) {return 10;}
            else if (y < 360 && y > 270 && x < 810 && x > 720) {return 11;}
        }
        return -1;
    }
    // Based on x, determines what icon the promoted pawn will use.
    public static BufferedImage parseImage(int x) throws IOException {
        if (x == 2) {return ImageIO.read(new File("images/WhiteRook.png"));}
        else if (x == 3) {return ImageIO.read(new File("images/WhiteKnight.png"));}
        else if (x == 4) {return ImageIO.read(new File("images/WhiteBishop.png"));}
        else if (x == 5) {return ImageIO.read(new File("images/WhiteQueen.png"));}
        else if (x == 8) {return ImageIO.read(new File("images/BlackRook.png"));}
        else if (x == 9) {return ImageIO.read(new File("images/BlackKnight.png"));}
        else if (x == 10) {return ImageIO.read(new File("images/BlackBishop.png"));}
        else if (x == 11) {return ImageIO.read(new File("images/BlackQueen.png"));}

        return null;
    }
    // Based on the x and y coordinate, returns the chess piece on that location.
    public static int pieceOnLocation(int x, int y) {
        for (int i = 0; i < 32; i++) {
            if (pieces[i].getX() == x && pieces[i].getY() == y) {
                return i;
            }
        }
        return -1;
    }
    // Checks if the piece still exists on the board.
    public static boolean captured(int pieceID) { 
        if (pieces[pieceID].getPieceID() == -1) {
            return true;
        }
        return false;
    }
    // Determines if the player can castle.
    public static int canCastle() {
        int[][] moves;
        if (turn == -1 && numPieceSelected == 15) {
            if (pieces[15].getMoved() == false && pieces[8].getMoved() == false && boardArray[7][1] == 0 && boardArray[7][2] == 0 && boardArray [7][3] == 0) {
                for (int i = 0; i < 16; i++) {
                    if (captured(i + 16)) {
                        continue;
                    }
                    moves = pieces[i + 16].availableMoves(1, boardArray);
                    for (int j = 0; j < moves.length; j++) {
                        if ((pieces[i + 16].getX() + moves[j][0] == 1 || pieces[i + 16].getX() + moves[j][0] == 2 || pieces[i + 16].getX() + moves[j][0] == 3) && (pieces[i + 16].getY() + moves[j][1] == 7)) {
                            return -1;
                        }
                    }
                }
                return 1;
            }
            else if (pieces[15].getMoved() == false && pieces[9].getMoved() == false && boardArray[7][5] == 0 && boardArray[7][6] == 0) {
                for (int i = 0; i < 16; i++) {
                    if (captured(i + 16)) {
                        continue;
                    }
                    moves = pieces[i + 16].availableMoves(1, boardArray);
                    for (int j = 0; j < moves.length; j++) {
                        if ((pieces[i + 16].getX() + moves[j][0] == 5 || pieces[i + 16].getX() + moves[j][0] == 6) && (pieces[i + 16].getY() + moves[j][1] == 7)) {
                            return -1;
                        }
                    }
                }
                return 2;
            }
        }
        else if (turn == 1 && numPieceSelected == 31) {
            if (pieces[31].getMoved() == false && pieces[24].getMoved() == false && boardArray[0][1] == 0 && boardArray[0][2] == 0 && boardArray [0][3] == 0) {
                for (int i = 0; i < 16; i++) {
                    if (captured(i)) {
                        continue;
                    }
                    moves = pieces[i].availableMoves(-1, boardArray);
                    for (int j = 0; j < moves.length; j++) {
                        if ((pieces[i].getX() + moves[j][0] == 1 || pieces[i].getX() + moves[j][0] == 2 || pieces[i].getX() + moves[j][0] == 3) && (pieces[i].getY() + moves[j][1] == 0)) {
                            return -1;
                        }
                    }
                }
                return 3;
            }
            else if (pieces[31].getMoved() == false && pieces[25].getMoved() == false && boardArray[0][5] == 0 && boardArray[0][6] == 0) {
                for (int i = 0; i < 16; i++) {
                    if (captured(i)) {
                        continue;
                    }
                    moves = pieces[i].availableMoves(-1, boardArray);
                    for (int j = 0; j < moves.length; j++) {
                        if ((pieces[i].getX() + moves[j][0] == 5 || pieces[i].getX() + moves[j][0] == 6) && (pieces[i].getY() + moves[j][1] == 0)) {
                            return -1;
                        }
                    }
                }
                return 4;
            }
        }
        return -1;
    } 
    // Sets the position of the rook when castling.
    public static void castleRook(int x, int y) {
        if (x == 2 && y == 7) {
            pieces[8].setX(3);
        }
        else if (x == 6 && y == 7) {
            pieces[9].setX(5);
        }
        else if (x == 2 && y == 0) {
            pieces[24].setX(3);
        }
        else if (x == 6 && y == 0) {
            pieces[25].setX(5);
        }
    }
    // Determines if a piece is a pawn and reached the opposite end of the board. If both are true, it can promote.
    public static boolean canPromote() {
        if ((turn == -1) && (pieces[numPieceSelected].getPieceID() == 1) && (pieces[numPieceSelected].getY() == 0)) {
            return true;
        }
        else if (turn == 1 && pieces[numPieceSelected].getPieceID() == 7 && pieces[numPieceSelected].getY() == 7) {
            return true;
        }
        return false;
    }
    // Determines if a pawn was moved forward two spaces last turn.
    public static int didPawnMove2(int moveY) {
        boolean moved2 = false;
        if (pieces[numPieceSelected].getY() + (turn * 2) == moveY) {
            moved2 = true;
        }
        if (numPieceSelected >= 0 && numPieceSelected <= 7 && turn == -1 && moved2) {
            return numPieceSelected;
        } 
        else if (numPieceSelected >= 16 && numPieceSelected <= 23 && turn == 1 && moved2) {
            return numPieceSelected;
        } 
        return -1;
    }
    // Determines whether en passant is a valid move.
    public static boolean canEnPassant() {
        if (moved2ID > -1) {
            if (turn == -1) {
                if (((pieces[moved2ID].getX() == pieces[numPieceSelected].getX() - 1) || (pieces[moved2ID].getX() == pieces[numPieceSelected].getX() + 1)) && pieces[numPieceSelected].getPieceID() == 1) {
                    if (boardArray[pieces[numPieceSelected].getY() - 1][pieces[moved2ID].getX()] == 0) {
                        if (pieces[numPieceSelected].getY() == pieces[moved2ID].getY()) {
                            return true;
                        }
                    }
                }
            }
            else if (turn == 1) {
                if (((pieces[moved2ID].getX() == pieces[numPieceSelected].getX() - 1) || (pieces[moved2ID].getX() == pieces[numPieceSelected].getX() + 1)) && pieces[numPieceSelected].getPieceID() == 7) {
                    if (boardArray[pieces[numPieceSelected].getY() + 1][pieces[moved2ID].getX()] == 0) {
                        if (pieces[numPieceSelected].getY() == pieces[moved2ID].getY()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    // Determines whether the move a player makes is valid.
    public static boolean validMove(int newX, int newY, int numPieceSelected) {
        int[][] moves = pieces[numPieceSelected].availableMoves(turn, boardArray);
        int oldID;
        int oldPiece = 0;
        oldX = pieces[numPieceSelected].getX();
        oldY = pieces[numPieceSelected].getY();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i][0] == 0 && moves[i][1] == 0) {}
            else {
                // Checks whether a move made would result in the player being in check. If not, it's valid.
                if (((oldX + (moves[i][0])) == newX) && ((oldY + (moves[i][1])) == newY)) {
                    oldID = boardArray[newY][newX];
                    if (oldID > 0) {
                        oldPiece = pieceOnLocation(newX, newY);
                        pieces[oldPiece].setX(9);
                        pieces[oldPiece].setY(6);
                        pieces[oldPiece].setPieceID(-1);
                    }
                    pieces[numPieceSelected].setX(newX);
                    pieces[numPieceSelected].setY(newY);
                    boardArray[oldY][oldX] = 0;
                    boardArray[newY][newX] = pieces[numPieceSelected].getPieceID();
                    if (!inCheck(boardArray)) {
                        if (oldID > 0) {
                            pieces[oldPiece].setX(newX);
                            pieces[oldPiece].setY(newY);
                            pieces[oldPiece].setPieceID(oldID);
                        }
                        pieces[numPieceSelected].setX(oldX);
                        pieces[numPieceSelected].setY(oldY);
                        boardArray[oldY][oldX] = pieces[numPieceSelected].getPieceID();
                        boardArray[newY][newX] = oldID;
                        return true;
                    }
                    if (oldID > 0) {
                        pieces[oldPiece].setX(newX);
                        pieces[oldPiece].setY(newY);
                        pieces[oldPiece].setPieceID(oldID);
                    }
                    pieces[numPieceSelected].setX(oldX);
                    pieces[numPieceSelected].setY(oldY);
                    boardArray[oldY][oldX] = pieces[numPieceSelected].getPieceID();
                    boardArray[newY][newX] = oldID;
                }
            }
        }
        if (canCastle() == 1 || canCastle() == 2) {
            if (numPieceSelected == 15) {
                return true;
            }
        }
        if (canCastle() == 3 || canCastle() == 4) {
            if (numPieceSelected == 31) {
                return true;
            }
        }
        if (canEnPassant() && newX == pieces[moved2ID].getX() && newY == (pieces[numPieceSelected].getY() + (turn))) {
            return true;
        }
        return false;
    }
    // Checks to see if either white or black is in check.
    public static boolean inCheck(int[][] boardArray) {
        int[][] moves;
        if (turn == -1) {
            for (int i = 0; i < 16; i++) {
                if (captured(i + 16)) {
                    continue;
                }
                moves = pieces[i + 16].availableMoves(1, boardArray);
                for (int j = 0; j < moves.length; j++) {
                    if (pieces[i + 16].getX() + moves[j][0] == pieces[15].getX() && pieces[i + 16].getY() + moves[j][1] == pieces[15].getY()) {
                        status.setText("White is checked.");
                        return true;
                    }
                }
            }
        }
        else if (turn == 1) {
            for (int i = 0; i < 16; i++) {
                if (captured(i)) {
                    continue;
                }
                moves = pieces[i].availableMoves(-1, boardArray);
                for (int j = 0; j < moves.length; j++) {
                    if (pieces[i].getX() + moves[j][0] == pieces[31].getX() && pieces[i].getY() + moves[j][1] == pieces[31].getY()) {
                        status.setText("Black is checked.");
                        return true;
                    }
                }
            }
        }
        return false;
    } 
    // Makes the piece captured no longer relevant on the board.
    public static void capture() {
        if (turn == -1) {
            for (int i = 0; i < 16; i++) {
                if (pieces[numPieceSelected].getX() == pieces[i + 16].getX() && pieces[numPieceSelected].getY() == pieces[i + 16].getY()) {
                    pieces[i + 16].setX(9);
                    pieces[i + 16].setY(1);
                    pieces[i + 16].setPieceID(-1);
                    break;
                }
            }
        }
        else if (turn == 1) {
            for (int i = 0; i < 16; i++) {
                if (pieces[numPieceSelected].getX() == pieces[i].getX() && pieces[numPieceSelected].getY() == pieces[i].getY()) {
                    pieces[i].setX(9);
                    pieces[i].setY(6);
                    pieces[i].setPieceID(-1);
                    break;
                }
            }
        }
        resetMovesUntilDraw();
    }
    // Switch turns and determines the state of the game.
    public static void gameState() { 
        if (turn == -1) {
            turn = 1;
        }
        else {
            turn = -1;
            movesUntilDraw -= 1;
        } 
        if (pieces[numPieceSelected].getPieceID() == 1 || pieces[numPieceSelected].getPieceID() == 7) {
            resetMovesUntilDraw();
        }
        if (gameStatus() == 1) {
            status.setText("Black Wins! You can now close out of the game.");
        }
        else if (gameStatus() == 2) {
            status.setText("White Wins! You can now close out of the game.");
        }
        else if (gameStatus() == 0) {
            status.setText("Draw! You can now close out of the game.");
        }
    }
    // Determines what the status of the game is.
    public static int gameStatus() {
        int[][] moves;
        if (inCheck(boardArray) && turn == -1) {
            if (canEnPassant()) {return -1;}
            for (int j = 0; j < 16; j++) {
                if (captured(j)) {continue;}
                moves = pieces[j].availableMoves(turn, boardArray);
                for (int i = 0; i < moves.length; i++) {
                    if ((moves[i][0] == 0) && (moves[i][1] == 0)) {}   
                    else {
                        if (validMove(pieces[j].getX() + moves[i][0], pieces[j].getY() + moves[i][1], j)){
                            return -1;
                        }
                    }
                }
            }
            return 1; 
        }
        else if (inCheck(boardArray) && turn == 1) {
            if (canEnPassant()) {return -1;}
            for (int j = 0; j < 16; j++) {
                if (captured(j + 16)) {continue;}
                moves = pieces[j + 16].availableMoves(turn, boardArray);
                for (int i = 0; i < moves.length; i++) {
                    if ((moves[i][0] == 0) && (moves[i][1] == 0)) {}   
                    else {
                        if (validMove(pieces[j + 16].getX() + moves[i][0], pieces[j + 16].getY() + moves[i][1], j + 16)){
                            return -1;
                        }
                    }
                }
            }
            return 2;
        }
        else if(movesUntilDraw == 0) {
            return 0;
        }
        return -1;
    }
    public static void resetMovesUntilDraw() {
        movesUntilDraw = 50;
    }

    public ChessGUI() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBounds(0, 50, 1000, 1000);
    }

    // Board Frame
    public static void board() {
        
        JFrame frame = new JFrame("");

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chess");
        frame.addMouseListener(new MouseListener(){ // Checks what the mouse clicks on.
            int moveX;
            int moveY;
            BufferedImage image;
            public void mousePressed(MouseEvent event) {
                // If the game has not ended in a draw or win for a player, mouse presses will register on the board.
                if (gameStatus() == -1) {
                    // Determines what piece the player chose.
                    if (pieceSelected == false) { 
                        moveX = parseX(event.getX());
                        moveY = parseY(event.getY());
                        if (selectPiece(pieceOnLocation(moveX, moveY))) {
                            numPieceSelected = pieceOnLocation(moveX, moveY);
                            status.setText("Selected a piece");
                            pieceSelected = true;
                            frame.repaint();
                        }
                    }
                    // Determines whether the move the player makes is valid and makes it and switches turns
                    else if (pieceSelected == true) { 
                        moveX = parseX(event.getX());
                        moveY = parseY(event.getY());
                        if (canPromote()) {
                            moveX = parsePromotion(event.getX(), event.getY());
                            try {
                                image = parseImage(moveX);
                            }
                            catch (IOException e) {
                            }
                            if (moveX > -1) {
                                pieces[numPieceSelected].setPieceID(moveX);
                                pieces[numPieceSelected].setImage(image);
                                pieceSelected = false;
                                if (turn == -1) {
                                    status.setText("White made a move");
                                }
                                else {
                                    status.setText("Black made a move");
                                }
                                frame.repaint();
                                updateBoard();
                                gameState();
                            }
                        }
                        else if (validMove(moveX, moveY, numPieceSelected)) {
                            if (canCastle() > 0) {
                                castleRook(moveX, moveY);
                            }
                            if (canEnPassant() && moveX == pieces[moved2ID].getX() && moveY == (pieces[numPieceSelected].getY() + (turn))) {
                                if (turn == -1) {
                                    pieces[moved2ID].setX(9);
                                    pieces[moved2ID].setY(1);
                                }
                                if (turn == 1) {
                                    pieces[moved2ID].setX(9);
                                    pieces[moved2ID].setY(6);
                                }
                                pieces[moved2ID].setPieceID(-1);
                            }
                            moved2ID = didPawnMove2(moveY); // To check how far up the pawn moved
                            pieces[numPieceSelected].setX(moveX);
                            pieces[numPieceSelected].setY(moveY);
                            pieces[numPieceSelected].setMoved(true);
                            if ((boardArray[moveY][moveX] > 6 && turn == -1) || (boardArray[moveY][moveX] < 7 && boardArray[moveY][moveX] > 0 && turn == 1)) {
                                capture();
                            }
                            if (canPromote()) {
                                pieceSelected = true;
                                frame.repaint();
                            }
                            else {
                                pieceSelected = false;
                                if (turn == -1) {
                                    status.setText("White made a move");
                                }
                                else {
                                    status.setText("Black made a move");
                                }
                                frame.repaint();
                                updateBoard();
                                gameState();
                            }
                        }
                        else {
                            pieceSelected = false;
                            status.setText("Deselected");
                            frame.repaint();
                        }
                    }
                }
                else {
                    status.setText("The game is finished, restart the program to play again.");
                }
            }
            public void mouseExited(MouseEvent event) {}
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
        });

        status.setBounds(0, 0, 500, 35);
        frame.add(status);
        frame.pack();

        frame.add(new ChessGUI());
        frame.pack();
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
    // Board graphics
    public void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        // Draws the markings on the side of the board.  
        g.drawString("This is my chess program!",350,10); 
        g.drawString("1", 10, 710);
        g.drawString("2", 10, 620);
        g.drawString("3", 10, 530);
        g.drawString("4", 10, 440);
        g.drawString("5", 10, 350);
        g.drawString("6", 10, 260);
        g.drawString("7", 10, 170);
        g.drawString("8", 10, 80);
        g.drawString("A", 70, 775);
        g.drawString("B", 160, 775);
        g.drawString("C", 250, 775);
        g.drawString("D", 340, 775);
        g.drawString("E", 430, 775);
        g.drawString("F", 520, 775);
        g.drawString("G", 610, 775);
        g.drawString("H", 700, 775);
        // Draws the board.
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tileColor = x + y;
                squareStartingY = 30 + SQUARE_CONSTANT * y;
                squareStartingX = 30 + SQUARE_CONSTANT * x;
                if (tileColor % 2 == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                    g.setColor(Color.BLACK);
                    g.drawRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                }
                else {
                    g.setColor(BROWN_COLOR);
                    g.fillRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                    g.setColor(Color.BLACK);
                    g.drawRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                }
            }
        }
        // Draws the available moves for that piece.
        if (pieceSelected == true && !canPromote()) {
            int[][] moves = pieces[numPieceSelected].availableMoves(turn, boardArray);
            if (canCastle() == 1) {
                g.setColor(Color.GREEN);
                g.fillRect(2 * SQUARE_CONSTANT + 30, 7 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                g.setColor(Color.BLACK);
                g.drawRect(2 * SQUARE_CONSTANT + 30, 7 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
            }
            if (canCastle() == 2) {
                g.setColor(Color.GREEN);
                g.fillRect(6 * SQUARE_CONSTANT + 30, 7 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                g.setColor(Color.BLACK);
                g.drawRect(6 * SQUARE_CONSTANT + 30, 7 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
            }
            if (canCastle() == 3) {
                g.setColor(Color.GREEN);
                g.fillRect(2 * SQUARE_CONSTANT + 30, 0 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                g.setColor(Color.BLACK);
                g.drawRect(2 * SQUARE_CONSTANT + 30, 0 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
            }
            if (canCastle() == 4) {
                g.setColor(Color.GREEN);
                g.fillRect(6 * SQUARE_CONSTANT + 30, 0 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                g.setColor(Color.BLACK);
                g.drawRect(6 * SQUARE_CONSTANT + 30, 0 * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
            }
            if (canEnPassant()) {
                g.setColor(Color.GREEN);
                g.fillRect(pieces[moved2ID].getX() * SQUARE_CONSTANT + 30, (pieces[numPieceSelected].getY() + turn) * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                g.setColor(Color.BLACK);
                g.drawRect(pieces[moved2ID].getX() * SQUARE_CONSTANT + 30, (pieces[numPieceSelected].getY() + turn) * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
            } 
            for (int i = 0; i < moves.length; i++) {
                if ((moves[i][0] == 0) && (moves[i][1] == 0)) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect((pieces[numPieceSelected].getX() + moves[i][0]) * SQUARE_CONSTANT + 30, (pieces[numPieceSelected].getY() + moves[i][1]) * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                }   
                else {
                    if (validMove(pieces[numPieceSelected].getX() + moves[i][0], pieces[numPieceSelected].getY() + moves[i][1], numPieceSelected)){
                        g.setColor(Color.GREEN);
                        g.fillRect((pieces[numPieceSelected].getX() + moves[i][0]) * SQUARE_CONSTANT + 30, (pieces[numPieceSelected].getY() + moves[i][1]) * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);   
                        g.setColor(Color.BLACK);
                        g.drawRect((pieces[numPieceSelected].getX() + moves[i][0]) * SQUARE_CONSTANT + 30, (pieces[numPieceSelected].getY() + moves[i][1]) * SQUARE_CONSTANT + 30, SQUARE_CONSTANT, SQUARE_CONSTANT);    
                    }
                }
            }
        }
        // Draws the promoting moves.
        if (canPromote()) {
            squareStartingX = 30 + SQUARE_CONSTANT * 8;
            if (turn == -1) {
                for (int i = 0; i < 4; i++) {
                    squareStartingY = 30 + SQUARE_CONSTANT * (i + 4);
                    g.setColor(Color.WHITE);
                    g.fillRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                    g.setColor(Color.BLACK);
                    g.drawRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                }
                squareStartingY = 720;
                for (int i = 8; i < 16; i+=2) {
                    squareStartingY -= SQUARE_CONSTANT;
                    g.drawImage(pieces[i].getImage(), 720 + PIECE_OFFSET_X, squareStartingY + PIECE_OFFSET_Y, pieces[8].getImage().getHeight(), pieces[8].getImage().getWidth(), null);
                }
            }
            else if (turn == 1) {
                for (int i = 0; i < 4; i++) {
                    squareStartingY = 30 + SQUARE_CONSTANT * (i);
                    g.setColor(Color.WHITE);
                    g.fillRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                    g.setColor(Color.BLACK);
                    g.drawRect(squareStartingX, squareStartingY, SQUARE_CONSTANT, SQUARE_CONSTANT);
                }
                squareStartingY = -90;
                for (int i = 24; i < 32; i+=2) {
                    squareStartingY += SQUARE_CONSTANT;
                    g.drawImage(pieces[i].getImage(), 720 + PIECE_OFFSET_X, squareStartingY + PIECE_OFFSET_Y, pieces[8].getImage().getHeight(), pieces[8].getImage().getWidth(), null);
                }
            }
        }
        // Draws the pieces on the board.
        for (int i = 0; i < pieces.length; i++) {
            squareStartingX = pieces[i].getX() * SQUARE_CONSTANT + PIECE_OFFSET_X;
            squareStartingY = pieces[i].getY() * SQUARE_CONSTANT + PIECE_OFFSET_Y;
            g.drawImage(pieces[i].getImage(), squareStartingX, squareStartingY, pieces[i].getImage().getHeight(), pieces[i].getImage().getWidth(), null);
        }
    }
    // Test specific game conditions
    // Run before updateBoard() in main.
    public static void testBoard() {
        pieces[16].setY(2);
        pieces[17].setY(2);
        pieces[31].setX(0);
        pieces[31].setY(1);
        pieces[26].setY(1);
        pieces[28].setX(1);
        turn = -1;
    }
}   