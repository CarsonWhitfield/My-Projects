🧾 ASCII Encoder / Translator (Python)

This Python program reads a CSV file of character-to-code mappings (such as ASCII codes), builds a lookup dictionary, and then translates input sentences into their encoded representations, writing the results to output text files.

    What It Does

      create_dictionary(file)

      Opens a CSV mapping file (ex: ascii-codes.csv)

      Splits each line on commas to extract a code and a character

Handles special tokens in the CSV like:

  comma → ,

  space →

  quote → "

Builds and returns a dictionary where:

  key = character

  value = code

  translate(s, d, f)

  Takes a string s, a dictionary d, and an output filename f

  Writes the encoded version of the string to the output file

  If a character is not found in the dictionary, it writes:

  UNDEFINED on its own line

  main()

  Loads the dictionary once

  Encodes three sample sentences and writes them to:

  output-1.txt

  output-2.txt

  output-3.txt

Features:

  Converts text into encoded values using a CSV-driven mapping

  Supports punctuation and whitespace via special CSV keywords

  Outputs results to separate files for each test sentence

  Includes basic error reporting for unmapped characters (UNDEFINED)

Concepts Demonstrated:

  File input/output (open, read/write, close)

  Dictionaries for fast lookups

  String parsing and cleaning (split, replace)

Iteration over strings character-by-character
