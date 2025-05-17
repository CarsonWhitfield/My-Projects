/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package yahtzee;

/**
 *
 * @author Cwhit
 */

public class Yahtzee {
     public static void printData(String field, int count, int total)
     {
         System.out.println("Number of "+field+":"+count); 
         double percent = (count / (double)total)*100.0; // divide count over total times the 100 to get percent
         System.out.print(field + " Percent: "+String.format("%.2f",percent)+"%");
         System.out.println();
     }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int yahtzees = 0; 
        int fullHouse = 0;
        int largeStraights = 0;
        int fourOfkind = 0;
        int threeOfkind = 0;
        int loser = 0;
        int numRolls = 5000;
        int[][] diceResults = new int[numRolls][5]; 
        for (int numRoll = 0; numRoll < numRolls; numRoll++)
{
for (int diceNum = 0; diceNum < 5; diceNum++)
{
int diceOutcome = (int) (Math.random() * 6) + 1; // roll a 6 sided dice
diceResults[numRoll][diceNum] = diceOutcome; // out come of roll
}
int[] count = new int[6]; 
boolean isYahtzees = true;
for (int i = 0; i < 5; i++)
{
count[diceResults[numRoll][i] - 1]++; 
if (diceResults[numRoll][i] != 3) 
isYahtzees = false;
}
if (isYahtzees)
{
yahtzees++;
continue;
}
boolean has2ofkind = false;
boolean has3ofkind = false;
for (int i = 0; i < 6; i++)
{
if (count[i] == 2) // check for two of kind
has2ofkind = true;
if (count[i] == 3) // check for three of a kind
has3ofkind = true;
}
if (has2ofkind && has3ofkind) // if has 2 of a kind and three of kind fullhouse
{
fullHouse++;
continue;
}
boolean isZero = count[0] == 0 || count[5] == 0;
boolean allOne = true;
for (int i = 1; i < 5; i++) // check for large straight
{
if (count[i] != 1)
allOne = false;
}
if (isZero && allOne) // if its zero and all one kind largest straight
{
largeStraights++;
continue;
}
boolean hasFourOfKind = false;
for (int i = 0; i < 6; i++) // check for four of kind
{
if (count[i] == 4)
hasFourOfKind = true;
}
if (hasFourOfKind) // four of kind
{
fourOfkind++;
continue;
}
boolean hasThreeOfKind = false;
for (int i = 0; i < 6; i++)
{
if (count[i] == 3) // cehck for three of kind
hasThreeOfKind = true; 
}
if (hasThreeOfKind) // three of kind
{
threeOfkind++;
continue;
}
loser++; // nothing add to loser
}
System.out.println("Number of Rolls: " + numRolls);
System.out.println("---------------------------------");
printData("Yahtzees", yahtzees, numRolls);
System.out.println("\n");
printData("Full Houses", fullHouse, numRolls);
System.out.println("\n");
printData("Large Straights", largeStraights, numRolls);
System.out.println("\n");
printData("Four of a Kind", fourOfkind, numRolls);
System.out.println("\n");
printData("Three of a Kind", threeOfkind, numRolls);
System.out.println("\n");
printData("Losers", loser, numRolls);
}
    }


