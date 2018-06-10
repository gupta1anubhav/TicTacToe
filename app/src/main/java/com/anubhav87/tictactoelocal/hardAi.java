package com.anubhav87.tictactoelocal;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class hardAi {


    public hardAi(){

    }


    class Move
    {
       public int row, col;

        Move(int x, int y){
            row = x;
            col = y;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

    };

    char player = 'x', opponent = 'o';

    Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (board[i][j]=='_')
                    return true;
        return false;
    }

    // This is the evaluation function as discussed
// in the previous article ( http://goo.gl/sJgv68 )
    int evaluate(char b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row<3; row++)
        {
            if (b[row][0]==b[row][1] &&
                    b[row][1]==b[row][2])
            {
                if (b[row][0]==player)
                    return +10;
                else if (b[row][0]==opponent)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (b[0][col]==b[1][col] &&
                    b[1][col]==b[2][col])
            {
                if (b[0][col]==player)
                    return +10;

                else if (b[0][col]==opponent)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2])
        {
            if (b[0][0]==player)
                return +10;
            else if (b[0][0]==opponent)
                return -10;
        }

        if (b[0][2]==b[1][1] && b[1][1]==b[2][0])
        {
            if (b[0][2]==player)
                return +10;
            else if (b[0][2]==opponent)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
    int minimax(char board[][], int depth, Boolean isMax)
    {
        int score = evaluate(board);

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft(board)==false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='_')
                    {
                        // Make the move
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = max( best,
                                minimax(board, depth+1, !isMax) );

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='_')
                    {
                        // Make the move
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = min(best,
                                minimax(board, depth+1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    // This will return the best possible move for the player
    Integer findBestMove(char board[][])
    {
        int bestVal = -1000;
        Move bestMove = new Move(-1,-1);
        bestMove.row = -1;
        bestMove.col = -1;
        int ans;

        // Traverse all cells, evalutae minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                // Check if celll is empty
                if (board[i][j]=='_')
                {
                    // Make the move
                    board[i][j] = player;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = '_';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        int x = bestMove.row;
        int y = bestMove.col;
        //get CellId from position
        if (x == 0 && y ==0){
            return 1;
        }
        else if (x == 0 && y == 1){
            return 2;
        }
        else if (x == 0 && y == 2){
            return 3;
        }
        else if (x == 1 && y == 0){
            return 4;
        }
        else if (x == 1 && y == 1){
            return 5;
        }
        else if (x == 1 && y == 2){
            return 6;
        }
        else if (x == 2 && y == 0){
            return 7;
        }
        else if (x == 2 && y == 1){
            return 8;
        }
        else if (x == 2 && y == 2){
            return 9;
        }
        return null;
    }

    int constructBoard(ArrayList<Integer> player1, ArrayList<Integer> comp){
        //Return cellid and construct board
        //fill board with player1 'X'
        char board[][] = new char[3][3];
        for (int cellId = 1; cellId < 10 ; cellId++){
            if (player1.contains(cellId)){
             switch (cellId){
                 case 1: board[0][0] = 'x';
                 break;
                 case 2: board[0][1] = 'x';
                     break;
                 case 3: board[0][2] = 'x';
                     break;
                 case 4: board[1][0] = 'x';
                     break;
                 case 5: board[1][1] = 'x';
                     break;
                 case 6: board[1][2] = 'x';
                     break;
                 case 7: board[2][0] = 'x';
                     break;
                 case 8: board[2][1] = 'x';
                     break;
                 case 9: board[2][2] = 'x';
                     break;
             }
            }
            else if (comp.contains(cellId)){
                switch (cellId){
                    case 1: board[0][0] = 'o';
                        break;
                    case 2: board[0][1] = 'o';
                        break;
                    case 3: board[0][2] = 'o';
                        break;
                    case 4: board[1][0] = 'o';
                        break;
                    case 5: board[1][1] = 'o';
                        break;
                    case 6: board[1][2] = 'o';
                        break;
                    case 7: board[2][0] = 'o';
                        break;
                    case 8: board[2][1] = 'o';
                        break;
                    case 9: board[2][2] = 'o';
                        break;

                }
            }
            else {
                switch (cellId){
                    case 1: board[0][0] = '_';
                        break;
                    case 2: board[0][1] = '_';
                        break;
                    case 3: board[0][2] = '_';
                        break;
                    case 4: board[1][0] = '_';
                        break;
                    case 5: board[1][1] = '_';
                        break;
                    case 6: board[1][2] = '_';
                        break;
                    case 7: board[2][0] = '_';
                        break;
                    case 8: board[2][1] = '_';
                        break;
                    case 9: board[2][2] = '_';
                        break;

                }
            }
        }
        return findBestMove(board);

    }
}
