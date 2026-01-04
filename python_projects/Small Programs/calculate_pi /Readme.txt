🎯 Monte Carlo π Estimator Using Random Points (Python)

This Python program uses a Monte Carlo simulation to estimate the value of π (pi) by generating random points inside a unit square and measuring how many fall within a quarter-circle of radius 1.

🔹 How It Works

A Point class represents a point (x, y) and can compute its distance from the origin using:

𝑥
2
+
𝑦
2
x
2
+y
2
	​


The program generates random points where:

x and y are uniformly random in [0, 1)

A point is considered inside the quarter-circle if its distance from the origin is less than 1:

distance < 1

Because the area of a quarter-circle of radius 1 is 
𝜋
/
4
π/4, the ratio:

points inside
total points
≈
𝜋
4
total points
points inside
	​

≈
4
π
	​


So π can be estimated as:

𝜋
≈
4
⋅
points inside
total points
π≈4⋅
total points
points inside
	​

🔹 Program Output

Prints 5 sample points and their distances from the origin

Runs 1,000,000 random trials to estimate π

Displays:

How many points landed inside the quarter-circle

The actual value of π (math.pi)

The estimated value

The error (difference from actual π)

🔹 Key Concepts Demonstrated

Object-oriented programming with a Point class

Random number generation (random.random)

Mathematical computation using math.sqrt

Monte Carlo simulation techniques

Estimation accuracy and error measurement
