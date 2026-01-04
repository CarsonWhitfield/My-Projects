def main():
    menu = '''
    ***** MENU *****
    Brewed     $2.75
    Americano   3.25
    Cappuccino  3.50
    Latte       4.00
    Extra shot  0.75
    *****************
    '''
    drinks = { 'brewed':2.75, 'americano': 3.25, 'cappuccino':3.50, 'latte':4.00, 'extra shot':0.75 }
    print(menu)
    
    order = []
    total = 0.00
    coffee = input("What would you like to order? ").lower()
    while coffee != "no":
        if coffee in drinks: #check if the drink is in the dictionary if yes add drink to order and add price to total
            order.append(coffee) # add to order 
            total = drinks[coffee] + total #add price to the total
            coffee = input("would you like to order anything else? ").lower() #ask user if they would like to order anything else
        else: # if drink is not in the dictionary ask the user if they want anything else
            coffee = input("Sorry we do not have that item, would you like to order somthing else? ").lower()

            
    print("Great! Your order is: ")
    number = 0
    for item in order: # loop through the items print them on seprit lines
        number = number + 1
        print(number , item,sep=' - ', end='\n')
    print("Your total is: ",total,sep='$')
main()
