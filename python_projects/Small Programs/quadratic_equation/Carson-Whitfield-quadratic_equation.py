import math
import random

def quadratic_equation(a,b,c2):
    x = -c2
    B = math.sqrt(b**2 - 4*a*c2)
    ex1 = x + B/2*a
    ex2 = x - B/2*a
    print(a,"x^2+",round(b,2),"x",c2)
    print("x = ", ex1 ,"and", ex2)


def main():
    a = random.randint(1,10)
    b = random.uniform(0,10)
    print("a is ", a)
    print("b is ", round(b,2))
    c = input("Enter c: ",)
    c1 = int(c)
    if c1 > 0: # checks if negtive 
        c2 = -1 * c1 # converts c to negtive
        print("c is ", c2)
    else:
        c2 = int(c1) # if negtive continues on
        print("c has to be negtive number")
        print("c is ", c2)
    quadratic_equation(a,b,c2)

main()
