# -----------------------------------------+
# Your name                                |
# CSCI 127, Lab 4                          |
# Last Updated: (mm/dd/yyyy)               |
# Name: Carson Whitfield                   |
# -----------------------------------------|
# Random letter from string                |
# -----------------------------------------+


import random
import string

def print_random_letter(current_string):
    string = [current for current in current_string if current.isalpha()]

    if len(string) == 0:
        print("Error: The current string has no letters in it.")
    else:
        random_letter = random.choice(string)
        print("randomly chose the ",random_letter,"at index ",current_string.index(random_letter))
        
def main(): 
    menu = '''
Thank you for running Lab Assignment 4
Please read carefully as our menu options may have changed:

Please press:
'C' - to change the string of text
'L' - to randomly choose a letter from the text
'R' - to repeat this menu again
'E' - to end this program

Current string:
012345....10...15...20...25...30...
↓↓↓↓↓↓↓↓↓↓↓‾   ↓‾   ↓‾   ↓‾   ↓‾'''
    
    current_string = ("Gotta start with something!")
    
    print(menu)
    print(current_string)
    print()
    over = False
    while(not over):
        x = input("Enter your choice: ").upper()
        while (x != 'C') and (x != 'L') and (x != 'R') and (x != 'E'):
            x = input("You must enter C, L, R, or E: ").upper()
        if x == 'C':
            current_string = input("Enter the new string: ")
            print("Current string set to: ", current_string)
        elif x == 'L':
                   print_random_letter(current_string)
        elif x == 'R':
            print(menu)
            print(current_string)
            print()
        else:
            over = True
            print("Goodbye.")
        
main()
