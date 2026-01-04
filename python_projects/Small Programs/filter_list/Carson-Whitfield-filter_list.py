
def filter_int_list(user_lo, user_hi, user_list): # filters through the list
    new_user_list = [] # new list
    for num in user_list:
        if user_lo <= num <= user_hi: #checks ever int in the list and checks if the in is greater than the or equel the lo and less than or equal to the high if true keep int else remove
            new_user_list.append(num) #adds approved int two new_list
    return new_user_list # return the aporved int to the new list


def main():
    user_nums = input("Enter some integers seperated by spaces: ")
    user_lo = int(input("Lower bound: "))
    user_hi = int(input("Upper bound "))
    user_split = user_nums.split() #creats list
    user_list = [] # empty list
    for i in user_split: #checks the values in the list
        i = int(i) #converts the values into an int
        user_list.append(i) #Adds int to the lsit
    user_list.sort() #sorts the user_list form least to greates
    print("Sorted intgers: ",user_list)

    fliterd_list = filter_int_list(user_lo, user_hi, user_list) 
    print("filter list: ", fliterd_list)
    
main()

