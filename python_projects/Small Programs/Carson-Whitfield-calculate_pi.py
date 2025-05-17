import random
import math

class Point():

    def __init__(self, x , y): # creat the point
        self.x = x  # creat x
        self.y = y  # creat y
        
    def get_x(self):
        return self.x # retrun x
    
    def get_y(self):
        return self.y # return y

    def get_dist_from_origin(self):
        dist_from_origin = math.sqrt((self.x**2)+(self.y**2)) # Monte Carlo fromula
        return dist_from_origin
    
        

def main():
    count = 0 # empty number count
    for i in range(5):
        x = random.random() # random x
        y = random.random() # random y
        p = Point(x,y) # assign random x and y to class point
        print("Sample",count,"is: (", float(x),",",float(y),")")
        print("Distance from origin:",p.get_dist_from_origin())
        count = count + 1 # add number to count
      
    count_2 = 0  # empty number count
    for i in range(1000000):
        x = random.random() # random x
        y = random.random() # random y
        p = Point(x,y) # assign random x and y to class point
        z = p.get_dist_from_origin() # Monte Carlo fromula output
        if z < 1: # if formula output is less than 0 add 1 to the count
            count_2 = count_2 + 1
        m = (count_2 * 4) / 1000000 # esitmated pi using the Monte Carlo fromula
    print()        
    print(count_2,"of the the 1000000 are inside.")
    print("actual pi is about",math.pi)
    print("estimated pi is about",m)
    print("monte carlo simulation is off by", m - math.pi)
main()
