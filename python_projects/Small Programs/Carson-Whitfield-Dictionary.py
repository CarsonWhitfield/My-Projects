# --------------------------------------
# CSCI 127, Lab 8                      |
# 2023/23/03                           |
# Carson Whitfield                     |
# --------------------------------------

# The missing functions go here.

# Below are some hints for the first one to get you started.
# Familiarize yourself with the csv file by opening it in an editor first...

def create_dictionary(file): # call for the file
    file = open(file, 'r') # open the file
    dictionary = {} # creat empty dictionary
    for i in file: # run through the file
        l = i.split(',') # split the file
        l[1]=str(l[1]).replace('\n','') # replace \n with a space
        if l[1] == 'comma':                 # replace comma, space and quote with there corret notation
            l[1]= l[1].replace('comma',',')
        if l[1] == 'space':
            l[1]= l[1].replace('space',' ')
        if l[1] == 'quote':
            l[1]= l[1].replace('quote','"')
        if i not in dictionary:
           d = l[0]
           c = l[1]
           dictionary[c] = d # add to dictionary
    file.close() # close file
    return dictionary # return dictionary
           
def translate(s, d, f): # rename the parameters as you wish
    file = open(f,'w') # open output file
    char = [] # empty list
    for i in s: # run through the sentence
        char.append(str(i))  # add each character , space and notation to char list

    for i in char: # run through char list 
        if i in d: # check if i is in the dictionary
            l = d.get(i) # grabe the item that represents i in dictionary
            file.write(l)  # write the grabed item form the dictionary to file  
        else: # if i is not in the dictionary write UNDIFINED on its own line
            file.write('\nUNDEFINED\n')
    file.close()   # close the file   
      

# --------------------------------------
def main():
    
    dictionary = create_dictionary("ascii-codes.csv")
    sentence = "A long time ago in a galaxy far, far away..."
    translate(sentence, dictionary, "output-1.txt")
    sentence = "Montana State University (406) 994-0211"
    translate(sentence, dictionary, "output-2.txt")
    sentence = "“True friends stab you in the front.” —Wilde"
    translate(sentence, dictionary, "output-3.txt")
    
# --------------------------------------
main()
