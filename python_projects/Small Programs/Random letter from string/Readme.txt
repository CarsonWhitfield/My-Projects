🔤 Random Letter Selector (Python, Menu-Driven)

This Python program is a menu-driven text utility that allows users to work with a string and randomly select a letter from it. The program repeatedly prompts the user for actions until they choose to exit, demonstrating input validation, string processing, and random selection.

What It Does

  Maintains a current string that the user can modify

  Filters the string to include alphabetic characters only

  Randomly selects a letter from the string and reports:

  The selected letter

  Its index in the original string

  Provides a looping menu interface for repeated interaction

Menu Options

  C – Change the current string

  L – Randomly choose and display a letter from the string

  R – Reprint the menu and current string

  E – Exit the program

Key Functions

  print_random_letter(current_string)

  Extracts alphabetic characters using isalpha()

  Randomly selects one letter using random.choice

  Handles error cases when no letters are present

  main()

  Displays the menu

  Handles user input and validation

  Controls program flow until exit

  Concepts Demonstrated

  Menu-driven program design

  Input validation

  String manipulation

  Random selection

  Loops and conditionals
