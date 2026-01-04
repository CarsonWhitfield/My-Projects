Wordle-Style Game (Python, File-Based Word List)

This project is a Wordle-inspired command-line game written in Python. It loads a list of valid words from a file, randomly selects a hidden answer, and gives the player up to 6 attempts to guess the word. After each guess, the game generates a hint that shows which letters are correct, present, or absent.

🔹 Features

Loads valid 5-letter words from a word list file (knuth5letterwords.csv)

Randomly selects an answer each game

Accepts player guesses with validation:

Must be 5 letters

Must be in the approved word list

Provides hints after each guess:

Uppercase letter = correct letter in the correct position

Lowercase letter = letter exists in the answer but different position

- = letter not in the answer

Game ends when:

Player guesses correctly, or

Player reaches 6 guesses

Includes special commands:

? (cheat) reveals the answer without using a turn

! (test) lets you manually set the answer (useful for debugging)

🔹 How It Works

Wordle.__init__() loads the word list and picks a random answer

get_player_guess() handles input, word validation, and cheat/test modes

generate_hint(guess) compares the guess to the answer and returns a formatted hint string

main() runs the game loop and prints different end messages based on how quickly the player solves it

🔹 Concepts Demonstrated

Object-oriented programming (class-based design)

File handling and parsing (read().splitlines())

Input validation and control flow

Random selection

String comparison and hint generation logic

Basic game loop structure
