🎲 Yahtzee Hand Simulator (Java)

This Java program simulates 5,000 random Yahtzee-style dice rolls (five 6-sided dice per roll) and calculates how often specific hand types occur. After each simulated roll, the program classifies the result as a Yahtzee, Full House, Large Straight, Four of a Kind, Three of a Kind, or “Loser” (none of the tracked categories). It then prints both the count and percentage for each category.

    What It Does

      Rolls five dice for each trial using Math.random()

      Uses a frequency array (count[6]) to track how many times each face (1–6) appears

    Detects and counts:

      Yahtzee (in this code: specifically all 3’s)

      Full House (2 of one value + 3 of another)

      Large Straight (1–5 or 2–6)

      Four of a Kind

      Three of a Kind

      Loser (none of the above)

      Prints a summary report with formatted percentages (2 decimal places)

    Key Concepts Demonstrated

      Monte Carlo simulation (repeated randomized trials)

      Arrays and 2D arrays for storing outcomes

      Frequency counting with a histogram-style array

      Conditional logic to classify hands
  
      Basic statistics (counts + percentages)
