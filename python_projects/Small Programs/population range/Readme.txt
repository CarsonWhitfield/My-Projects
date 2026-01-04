🇺🇸 U.S. State Population Range Filter (Python)

This Python program reads 2020 U.S. census population data from a text file and allows users to list states whose populations fall within a specified range (in millions). The results are formatted and displayed in a clean, readable report.

🔹 What It Does

Reads state population data from census2020.txt

Stores each state and its population in a list

Prompts the user to enter a lower and upper population bound

Filters states whose populations fall within that range

Prints:

The number of states in the range

A formatted list of matching states and populations

🔹 Key Functions

get_list_from_file()

Reads the census file line by line

Splits each line into fields and stores them in a list

get_listing_in_range(lower, upper, state_list)

Converts population values to millions

Filters states based on the user-specified range

Formats output using aligned state names

Returns a printable listing string

main()

Displays helpful context about U.S. population extremes

Handles user input

Prints the filtered results

🔹 Concepts Demonstrated

File input/output

List manipulation and traversal

Numeric conversion and rounding

User-driven filtering

Formatted console output
