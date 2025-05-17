# -----------------------------------------+
# Carson Whitfield                         | 
# CSCI 127, Lab 6                          |
# Last Updated: (mm/dd/yyyy)               |
# Name:                                    |
# -----------------------------------------|
# Get states in a population range         |
# -----------------------------------------+


def get_list_from_file(census_data):
    
    census_file = open("census2020.txt", "r")
    states = []
    for i in census_file:
        new_data = i.split()
        states.append(new_data)
    return states

def get_listing_in_range(lower, upper, state_list):
    
    
    listing = ""
    count = 0
    state_list.reverse()

    for i in state_list:
        population = float(i[1])
        converted_population = population/1000000
        new_population = round(converted_population,1)
        if lower <= new_population <= upper:
           count = count + 1
           st = i[0].ljust(20)
           listing = listing + st + " " + str(i[1]) + "\n"

    
    print(count, "States have a population between", lower, "and", upper, "million:")
    return listing
    
def main():
    
    states = get_list_from_file("census2020.txt")
    print("\n ***first state in list:", states[0][0], '*** \n') 
    
    print("The least populous U.S. state: Wyoming with just over 0.5 million")
    print("The most populous U.S. state: California with almost 40 million")
    print("Enter two numbers between 0.5 and 40 to list states in that range.")
    lo = float(input("Enter lower bound: "))
    hi = float(input("Enter upper bound: "))
    
    
    listing = get_listing_in_range(lo, hi, states)
    print(listing)
    print("\n ***first state in list:", states[0][0], '*** \n') 

main()
