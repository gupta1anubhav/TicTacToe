package com.anubhav87.tictactoe

import java.util.ArrayList
import java.util.Random

import java.lang.Math.max
import java.lang.Math.min


class HardAI {

    internal var player = 'x'
    internal var opponent = 'o'


    internal inner class Move(var row: Int, var col: Int)

    internal fun isMovesLeft(board: Array<CharArray>): Boolean? {
        for (i in 0..2)
            for (j in 0..2)
                if (board[i][j] == '_')
                    return true
        return false
    }

    // This is the evaluation function as discussed
    // in the previous article ( http://goo.gl/sJgv68 )
    internal fun evaluate(b: Array<CharArray>): Int {
        // Checking for Rows for X or O victory.
        for (row in 0..2) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +10
                else if (b[row][0] == opponent)
                    return -10
            }
        }

        // Checking for Columns for X or O victory.
        for (col in 0..2) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == player)
                    return +10
                else if (b[0][col] == opponent)
                    return -10
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player)
                return +10
            else if (b[0][0] == opponent)
                return -10
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player)
                return +10
            else if (b[0][2] == opponent)
                return -10
        }

        // Else if none of them have won then return 0
        return 0
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    internal fun minimax(board: Array<CharArray>, depth: Int, isMax: Boolean?): Int {
        val score = evaluate(board)

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft(board) == false)
            return 0

        // If this maximizer's move
        if (isMax!!) {
            var best = -1000

            // Traverse all cells
            for (i in 0..2) {
                for (j in 0..2) {
                    // Check if cell is empty
                    if (board[i][j] == '_') {
                        // Make the move
                        board[i][j] = player

                        // Call minimax recursively and choose
                        // the maximum value
                        best = max(best,
                                minimax(board, depth + 1, !isMax))

                        // Undo the move
                        board[i][j] = '_'
                    }
                }
            }
            return best
        } else {
            var best = 1000

            // Traverse all cells
            for (i in 0..2) {
                for (j in 0..2) {
                    // Check if cell is empty
                    if (board[i][j] == '_') {
                        // Make the move
                        board[i][j] = opponent

                        // Call minimax recursively and choose
                        // the minimum value
                        best = min(best,
                                minimax(board, depth + 1, !isMax))

                        // Undo the move
                        board[i][j] = '_'
                    }
                }
            }
            return best
        }// If this minimizer's move
    }

    // This will return the best possible move for the player
    internal fun findBestMove(board: Array<CharArray>): Int? {
        var bestVal = -1000
        val bestMove = Move(-1, -1)
        bestMove.row = -1
        bestMove.col = -1
        val ans: Int

        // Traverse all cells, evalutae minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (i in 0..2) {
            for (j in 0..2) {
                // Check if celll is empty
                if (board[i][j] == '_') {
                    // Make the move
                    board[i][j] = player

                    // compute evaluation function for this
                    // move.
                    val moveVal = minimax(board, 0, false)

                    // Undo the move
                    board[i][j] = '_'

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.row = i
                        bestMove.col = j
                        bestVal = moveVal
                    }
                }
            }
        }

        val r = Random()
        val x = bestMove.row
        val y = bestMove.col
        //get CellId from position
        if (x == 0 && y == 0) {
            return 1
        } else if (x == 0 && y == 1) {
            return 2
        } else if (x == 0 && y == 2) {
            return 3
        } else if (x == 1 && y == 0) {
            return 4
        } else if (x == 1 && y == 1) {
            return 5
        } else if (x == 1 && y == 2) {
            return 6
        } else if (x == 2 && y == 0) {
            return 7
        } else if (x == 2 && y == 1) {
            return 8
        } else if (x == 2 && y == 2) {
            return 9
        }
        return null
    }

    internal fun constructBoard(player1: ArrayList<Int>, comp: ArrayList<Int>): Int {
        //Return cellid and construct board
        //fill board with player1 'X'
        val board = Array(3) { CharArray(3) }
        for (cellId in 1..9) {
            if (player1.contains(cellId)) {
                when (cellId) {
                    1 -> board[0][0] = 'x'
                    2 -> board[0][1] = 'x'
                    3 -> board[0][2] = 'x'
                    4 -> board[1][0] = 'x'
                    5 -> board[1][1] = 'x'
                    6 -> board[1][2] = 'x'
                    7 -> board[2][0] = 'x'
                    8 -> board[2][1] = 'x'
                    9 -> board[2][2] = 'x'
                }
            } else if (comp.contains(cellId)) {
                when (cellId) {
                    1 -> board[0][0] = 'o'
                    2 -> board[0][1] = 'o'
                    3 -> board[0][2] = 'o'
                    4 -> board[1][0] = 'o'
                    5 -> board[1][1] = 'o'
                    6 -> board[1][2] = 'o'
                    7 -> board[2][0] = 'o'
                    8 -> board[2][1] = 'o'
                    9 -> board[2][2] = 'o'
                }
            } else {
                when (cellId) {
                    1 -> board[0][0] = '_'
                    2 -> board[0][1] = '_'
                    3 -> board[0][2] = '_'
                    4 -> board[1][0] = '_'
                    5 -> board[1][1] = '_'
                    6 -> board[1][2] = '_'
                    7 -> board[2][0] = '_'
                    8 -> board[2][1] = '_'
                    9 -> board[2][2] = '_'
                }
            }
        }
        return findBestMove(board)!!

    }
}
