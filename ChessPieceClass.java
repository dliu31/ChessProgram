import java.awt.image.BufferedImage;

public class ChessPieceClass {
    private int pieceID;
    private boolean moved;
    private int x;
    private int y;
    private BufferedImage image;

    // Chess Piece Constructor
    public ChessPieceClass(int pieceID, int x, int y, boolean b, BufferedImage image) {
        this.pieceID = pieceID;
        this.x = x;
        this.y = y;
        this.moved = b;
        this.image = image;
    }

    // Setters
    public void setPieceID(int newPieceID) {
        this.pieceID = newPieceID;
    }
    public void setMoved(boolean newMoved) {
        this.moved = newMoved;
    }
    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }
    public void setImage(BufferedImage newImage) {
        this.image = newImage;
    }

    // Getters
    public int getPieceID() {
        return this.pieceID;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public boolean getMoved() {
        return this.moved;
    }
    public BufferedImage getImage() {
        return this.image;
    }

    // Finds available moves
    public int[][] availableMoves(int turn, int[][] boardArray) {
        int[][] moveArray;
        int moveX = 0;
        int moveY = 0;
        int index = 0;
        int direction = 0;
        boolean blocked = false;
        
        // Pawn Moves
        if ((this.pieceID == 1) || (this.pieceID == 7)) {
            if (this.moved == false) {
                moveArray = new int[4][2];
                for (int i = 0; i < 2; i++) {
                    moveY = turn * (i + 1);
                    if (blocked(boardArray, this.y + moveY, this.x + 0) || blocked == true) {
                        moveArray[i][0] = 0;
                        moveArray[i][1] = 0;
                        blocked = true;
                    }
                    else {
                        moveArray[i][0] = 0;
                        moveArray[i][1] = moveY;
                    }
                }
            }
            else {
                moveArray = new int[3][2];
                for (int i = 0; i < 1; i++) {
                    moveY = turn * (i + 1);
                    if (blocked(boardArray, this.y + moveY, this.x + 0) || blocked == true) {
                        moveArray[i][0] = 0;
                        moveArray[i][1] = 0;
                    }
                    else {
                        moveArray[i][0] = 0;
                        moveArray[i][1] = moveY;
                    }
                }
            }
            direction = 1;
            for (int i = moveArray.length - 2; i < moveArray.length; i++) {
                moveX = direction;
                moveY = turn * 1;
                if (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[i][0] = moveX;
                        moveArray[i][1] = moveY;
                    }
                    else {
                        moveArray[i][0] = 0;
                        moveArray[i][1] = 0;
                    }
                }
                else {
                    moveArray[i][0] = 0;
                    moveArray[i][1] = 0;
                }
                direction = -1;
            }
            return moveArray;
        }
        // Rook Moves
        else if ((this.pieceID == 2) || (this.pieceID == 8)) {
            moveArray = new int[14][2];
            while (this.x + direction < 7) {
                moveX = direction + 1;
                if (blocked(boardArray, this.y, this.x + moveX)) {
                    if (capture(boardArray, this.y, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = 0;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = 0;
                index++;
                direction++;
            }
            direction = 0;
            while (this.x - direction > 0) {
                moveX = -direction - 1;
                if (blocked(boardArray, this.y, this.x + moveX)) {
                    if (capture(boardArray, this.y, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = 0;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = 0;
                index++;
                direction++;
            }
            direction = 0;
            while (this.y + direction < 7) {
                moveY = direction + 1;
                if (blocked(boardArray, this.y + moveY, this.x)) {
                    if (capture(boardArray, this.y + moveY, this.x, turn)) {
                        moveArray[index][0] = 0;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = 0;
                moveArray[index][1] = moveY;
                index++;
                direction++;
            }
            direction = 0;
            while (this.y - direction > 0) {
                moveY = -direction - 1;
                if (blocked(boardArray, this.y + moveY, this.x)) {
                    if (capture(boardArray, this.y + moveY, this.x, turn)) {
                        moveArray[index][0] = 0;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = 0;
                moveArray[index][1] = moveY;
                index++;
                direction++;
            }
            while (index < 14) {
                moveArray[index][0] = 0;
                moveArray[index][1] = 0;
                index++;
            }
            return moveArray;
        } 
        // Knight Moves
        else if ((this.pieceID == 3) || (this.pieceID == 9)) {
            int x = 1;
            int y = 2;
            moveArray = new int[8][2];

            for (int j = 0; j < 2; j++) {
                moveX = x;
                moveY = -y;
                if ((notOutOfBounds(this.y + moveY, this.x + moveX) && !blocked(boardArray, this.y + moveY, this.x + moveX))) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else if (notOutOfBounds(this.y + moveY, this.x + moveX) && capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else {
                    moveArray[index][0] = 0;
                    moveArray[index][1] = 0;
                }

                moveX = x + x;
                moveY = -y + x;
                index++;
                if ((notOutOfBounds(this.y + moveY, this.x + moveX) && !blocked(boardArray, this.y + moveY, this.x + moveX))) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else if (notOutOfBounds(this.y + moveY, this.x + moveX) && capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else {
                    moveArray[index][0] = 0;
                    moveArray[index][1] = 0;
                }

                moveX = x;
                moveY = y;
                index++;
                if ((notOutOfBounds(this.y + moveY, this.x + moveX) && !blocked(boardArray, this.y + moveY, this.x + moveX))) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else if (notOutOfBounds(this.y + moveY, this.x + moveX) && capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else {
                    moveArray[index][0] = 0;
                    moveArray[index][1] = 0;
                }

                moveX = x + x;
                moveY = y - x;
                index++;
                if ((notOutOfBounds(this.y + moveY, this.x + moveX) && !blocked(boardArray, this.y + moveY, this.x + moveX))) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else if (notOutOfBounds(this.y + moveY, this.x + moveX) && capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                    moveArray[index][0] = moveX;
                    moveArray[index][1] = moveY;
                }
                else {
                    moveArray[index][0] = 0;
                    moveArray[index][1] = 0;
                }
                index++;
                y *= -1;
                x *= -1;
            }
            return moveArray;
        }
        // Bishop Moves
        else if ((this.pieceID == 4) || this.pieceID == 10) {
            moveArray = new int[16][2];
            direction = 0 + 1;
            moveX = direction;
            moveY = -direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = direction;
                moveY = -direction;
            }
            direction = 0 + 1;
            moveX = -direction;
            moveY = direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = -direction;
                moveY = direction;
            }
            direction = 0 + 1;
            moveX = direction;
            moveY = direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = direction;
                moveY = direction;
            }
            direction = 0 + 1;
            moveX = -direction;
            moveY = -direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = -direction;
                moveY = -direction;
            }
            while (index < 16) {
                moveArray[index][0] = 0;
                moveArray[index][1] = 0;
                index++;
            }
            return moveArray;
        }
        // Queen Moves
        else if ((this.pieceID == 5) || (this.pieceID == 11)) {
            moveArray = new int[30][2];
            while (this.x + direction < 7) {
                moveX = direction + 1;
                if (blocked(boardArray, this.y, this.x + moveX)) {
                    if (capture(boardArray, this.y, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = 0;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = 0;
                index++;
                direction++;
            }
            direction = 0;
            while (this.x - direction > 0) {
                moveX = -direction - 1;
                if (blocked(boardArray, this.y, this.x + moveX)) {
                    if (capture(boardArray, this.y, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = 0;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = 0;
                index++;
                direction++;
            }
            direction = 0;
            while (this.y + direction < 7) {
                moveY = direction + 1;
                if (blocked(boardArray, this.y + moveY, this.x)) {
                    if (capture(boardArray, this.y + moveY, this.x, turn)) {
                        moveArray[index][0] = 0;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = 0;
                moveArray[index][1] = moveY;
                index++;
                direction++;
            }
            direction = 0;
            while (this.y - direction > 0) {
                moveY = -direction - 1;
                if (blocked(boardArray, this.y + moveY, this.x)) {
                    if (capture(boardArray, this.y + moveY, this.x, turn)) {
                        moveArray[index][0] = 0;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = 0;
                moveArray[index][1] = moveY;
                index++;
                direction++;
            }
            direction = 0 + 1;
            moveX = direction;
            moveY = -direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = direction;
                moveY = -direction;
            }
            direction = 0 + 1;
            moveX = -direction;
            moveY = direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = -direction;
                moveY = direction;
            }
            direction = 0 + 1;
            moveX = direction;
            moveY = direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = direction;
                moveY = direction;
            }
            direction = 0 + 1;
            moveX = -direction;
            moveY = -direction;
            while (notOutOfBounds(this.y + moveY, this.x + moveX)) {
                if (blocked(boardArray, this.y + moveY, this.x + moveX)) {
                    if (capture(boardArray, this.y + moveY, this.x + moveX, turn)) {
                        moveArray[index][0] = moveX;
                        moveArray[index][1] = moveY;
                        index++;
                    }
                    break;
                }
                moveArray[index][0] = moveX;
                moveArray[index][1] = moveY;
                index++;
                direction++;
                moveX = -direction;
                moveY = -direction;
            }
            while (index < 30) {
                moveArray[index][0] = 0;
                moveArray[index][1] = 0;
                index++;
            }
            return moveArray;
        }
        // King Moves
        else if ((this.pieceID == 6) || (this.pieceID == 12)) {
            moveArray = new int[9][2];
            moveX = 1;
            moveY = 1;
            for (int i = 0; i <= 2; i+=2) {
                if ((notOutOfBounds(this.y, this.x + moveX)) && (!blocked(boardArray, this.y, this.x + moveX) || capture(boardArray, this.y, this.x + moveX, turn))) {
                    moveArray[i][0] = moveX;
                    moveArray[i][1] = 0;
                }
                if (notOutOfBounds(this.y + moveY, this.x) && (!blocked(boardArray, this.y + moveY, this.x) || capture(boardArray, this.y + moveY, this.x, turn))) {
                    moveArray[i + 1][0] = 0;
                    moveArray[i + 1][1] = moveY;
                }
                moveX *= -1;
                moveY *= -1;
            }
            moveX = 1;
            moveY = 1;
            for (int i = 0; i < 4; i+=2) {
                if (notOutOfBounds(this.y + moveY, this.x + moveX) && ((!blocked(boardArray, this.y + moveY, this.x + moveX) || capture(boardArray, this.y + moveY, this.x + moveX, turn)))) {
                    moveArray[i + 4][0] = moveX;
                    moveArray[i + 4][1] = moveY;
                }
                if (notOutOfBounds(this.y + moveY, this.x - moveX) && ((!blocked(boardArray, this.y + moveY, this.x - moveX) || capture(boardArray, this.y + moveY, this.x - moveX, turn)))) {
                    moveArray[i + 5][0] = -moveX;
                    moveArray[i + 5][1] = moveY;
                }
                moveX *= -1;
                moveY *= -1;
            }
            moveArray[8][0] = 0;
            moveArray[8][1] = 0;
            return moveArray;
        }
        return null;
    }
    // Determines if the chess piece is blocked by another piece.
    private boolean blocked(int[][] boardArray, int y, int x) {
        if (boardArray[y][x] > 0) {
            return true;
        }
        return false;
    }
    // Determines if the chess piece can make a capture on that square.
    private boolean capture(int[][] boardArray, int y, int x, int turn) {
        if (turn == -1) {
            if (boardArray[y][x] >= 7) {
                return true;
            }
            return false;
        }
        else {
            if (boardArray[y][x] <= 6 && boardArray[y][x] > 0){
                return true;
            }
            return false;
        }
    }
    // Determines if the move is out of bounds.
    private boolean notOutOfBounds(int y, int x) {
        if ((x <= 7) && (x >= 0) && (y <= 7) && (y >= 0)) {
            return true;
        }
        return false;
    }
}
