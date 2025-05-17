/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_7;

/**
 *
 * @author Cwhit
 */
public class Assignment {
    private String assignmentName;
    private float weight;
    private float currentGrade;
    private float finalweight;
    

    public Assignment(String assignmentName, float weight, float currentGrade, float finalweight) {
        this.assignmentName = assignmentName;
        this.weight = weight;
        this.currentGrade = currentGrade;
        this.finalweight = finalweight;
    }

    public float getCurrentGrade() {
        return currentGrade;
    }
    
     public void setCurrentGrade(float currentGrade) {
        this.currentGrade = currentGrade;
    }

    
    public float getWeight() {
        return weight;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
    
    public float getfinalweight(){
        return finalweight; 
    }
    
    public String toString() {
        return "Assignment: " + assignmentName + ", Weight: " + weight + ", Current Grade: " + currentGrade + ", Final Weight: " + finalweight;
    }
}
