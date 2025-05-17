/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_7;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cwhit
 */
public class TypeOfAssignmentCategory {
    private String categoryName;
    private List<Assignment> assignments;
    private float totalWeight;
    private float weightOfCategory;

    public TypeOfAssignmentCategory(String categoryName, float weightOfCategory) {
        this.categoryName = categoryName;
        this.weightOfCategory = weightOfCategory;
        this.assignments = new ArrayList<>();
        this.totalWeight = 0.0f;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public float getCategoryWeight() {
        return weightOfCategory;
    }

    public void addAssignment(String assignmentName, float weight, float currentGrade) {
        float finalWeight = weight * (currentGrade / 100.0f); // Calculate the final weight
        Assignment assignment = new Assignment(assignmentName, weight, currentGrade, finalWeight);
        assignments.add(assignment);
    }

    

    public float getTotalWeight() {
        return totalWeight;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    } 
    
    public float calculateTotalFinalWeight() {
        float totalFinalWeight = 0.0f;
        for (Assignment assignment : assignments) {
            totalFinalWeight += assignment.getfinalweight();
        }
        return totalFinalWeight;
    }
    
    
}