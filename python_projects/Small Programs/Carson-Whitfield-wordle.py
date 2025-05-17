import random

# -------------------------------------------------
# CSCI 127, Program 5: WORDLE
# Month 04, 2023
# Carson Whitfield
# -------------------------------------------------

class Wordle:

    def __init__(self, letters_in_word, file_of_words):
        self.num_letters = letters_in_word
        self.word_list = open(file_of_words, 'r').read().upper().splitlines()
        self.answer = self.word_list[random.randint(0, len(self.word_list)-1)]
        self.cheat_code = "?"
        self.test_code = "!"
        self.turn_num = 0

    def change_answer(self, new_answer):
        self.answer = new_answer.upper()

    def get_player_guess(self):
        self.turn_num += 1
        print(self.answer)
        key = ['!',"?"]
        user_word = input("Enter your guess: ").upper()
        while(user_word not in key): # chek if users imput is in key
            if(len(user_word) == 5 and user_word in self.word_list): # checks the user imput if the leth of the str 5 and is in the word list
                break # if true break the while loop
            elif(user_word not in self.word_list):
                print(user_word, "is't in the list of valid words.")
                user_word = input("Enter your guess: ").upper()
            else: # else prompt the user inter a word that has fice letter.
                print("Your word must have 5 letters.")
                user_word = input("Enter your guess: ").upper()
        if(user_word == self.cheat_code):
            self.turn_num -= 1
            print("\tPsst. Answer is", self.answer)
            user_word = self.get_player_guess()
        if(user_word == self.test_code):
            self.turn_num -= 1
            new_answer = input("\tOkay. Enter the new answer: ").upper()
            while new_answer not in self.word_list:
                new_answer = input("\tEnter a valid " + str(self.num_letters) + " letter word: ").upper()
            self.change_answer(new_answer)
            print("\tAnswer set to " + new_answer.upper())
            user_word = self.get_player_guess()


        return (user_word.upper())

    def generate_hint(self, guess):
        print('\n\t' + guess)
        hint = ""
        if(guess == self.answer):
            hint = hint + guess
            return guess # Game over!
        x = 0
        for i in guess: # if the letter is the same as the aswer add the catple letter
            if(i == self.answer[x]):
                hint += i.upper()
                x += 1
            elif(i in self.answer): # if the letter is not the same as the one in the aswer but is in the asnwer add lower case
                if(i not in hint or self.answer[x]): #if the same letter is alread in hint dont add agen or in case word with to same letter add letter
                    hint += i.lower()
                    x += 1
            else: # else if letter is not in asnwer replace letter with -
                hint = hint + '-'
                x += 1
            
                    
        return(hint)

def main(file):

    game = Wordle(5, file)
    print("\nWelcome to WORDLE!\n")
    guess = ""
    
    while(guess != game.answer and game.turn_num < 6):
        print(game.turn_num+1, end='. ')
        guess = game.get_player_guess()
        print('\t'+ game.generate_hint(guess) + '\n')
    if(guess == game.answer and game.turn_num == 1):
        print("Wow! You're either very lucky, or you got some insider information.")
        print("Thanks for playing.\n")
    elif(guess == game.answer and  2<= game.turn_num <=5):
        print("GREAT! You got it in",game.turn_num,"tries.")
        print("Thanks for playing.\n")
    elif(guess == game.answer and game.turn_num == 6):
        print("Phew! You got it on the last try!")
        print("Thanks for playing.\n")
    else:
        game_over = input("Game Over. Reveal answer? y/n: ").upper()
        if game_over == 'Y':
            print("Answer was", game.answer)
            print("Thanks for playing.\n")
        else:
            print("Thanks for playing.\n")
    
    
    
if __name__ == "__main__":
    main("knuth5letterwords.csv")
