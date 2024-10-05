package com.example.tugasteori1

// MainActivity.kt
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = 'X'
    private lateinit var gridLayout: GridLayout
    private lateinit var statusTextView: TextView
    private var board = Array(3) { CharArray(3) { ' ' } }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        statusTextView = findViewById(R.id.statusTextView)

        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetGame()
        }

        // Loop through the grid buttons and set click listeners
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                onTileClicked(button, i / 3, i % 3)
            }
        }
    }

    private fun onTileClicked(button: Button, row: Int, col: Int) {
        // If the game is not active or the cell is already occupied, return
        if (!gameActive || board[row][col] != ' ') return

        // Update the board and button text with the current player's symbol
        board[row][col] = currentPlayer
        button.text = currentPlayer.toString()

        // Check if there's a win or a draw
        if (checkWin()) {
            statusTextView.text = "Player $currentPlayer wins!"
            gameActive = false
        } else if (isBoardFull()) {
            statusTextView.text = "It's a draw!"
            gameActive = false
        } else {
            // Switch to the other player
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            statusTextView.text = "Player $currentPlayer's Turn"
        }
    }

    // Check for winning conditions
    private fun checkWin(): Boolean {
        for (i in 0..2) {
            // Check rows and columns
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true
            }
        }
        // Check diagonals
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
    }

    // Check if the board is full (draw condition)
    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == ' ') return false
            }
        }
        return true
    }

    // Reset the game to the initial state
    private fun resetGame() {
        currentPlayer = 'X'
        gameActive = true
        board = Array(3) { CharArray(3) { ' ' } }

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
        }
        statusTextView.text = "Player X's Turn"
    }
}
