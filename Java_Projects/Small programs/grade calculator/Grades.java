/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author Cwhit
 */
public class Grades {
   private TreeSet<SaveClass> saveData;
   private Map<Integer, String> gradeScale;
    public Grades() {
        this.saveData = new TreeSet<>();
        this.gradeScale = new HashMap<>();
        // Initialize the gradeScale map with default values
        initializeGradeScale();
    }
    
     // Method to initialize the gradeScale map with default values
    private void initializeGradeScale() {
        // Populate the gradeScale map with default grade scale values
        gradeScale.put(93, "A");
        gradeScale.put(90, "A-");
        gradeScale.put(87, "B+");
        gradeScale.put(83, "B");
        gradeScale.put(80, "B-");
        gradeScale.put(77, "C+");
        gradeScale.put(73, "C");
        gradeScale.put(70, "C-");
        gradeScale.put(67, "D+");
        gradeScale.put(63, "D");
        gradeScale.put(60, "D-");
    }

    // Method to set or update the gradeScale map
    public void setGradeScale(Map<Integer, String> gradeScale) {
        this.gradeScale = gradeScale;
    }

    // Method to get the gradeScale map
    public Map<Integer, String> getGradeScale() {
        return gradeScale;
    }


    public void saveClass(SaveClass saveClass) {
        saveData.add(saveClass);
    }

    public TreeSet<SaveClass> getSaveData() {
        return saveData;
    }

    public void printSavedClasses() {
        System.out.println("Saved Classes:");
        for (SaveClass savedClass : saveData) {
            System.out.println(savedClass.getClassName());
        }
    }
    
    
    
    public void printCategoriesWithTotals(SaveClass savedClass, Map<Integer, String> gradeScale) {
    if (savedClass != null) {
        float totalFinalWeight = 0.0f;
        String className = savedClass.getClassName();
        Course classData = savedClass.getClassData();
        System.out.println(className);
        System.out.println("Categories and their totals for class: " + className);
        for (TypeOfAssignmentCategory category : classData.getAssignmentCategories()) {
            System.out.println("Category: " + category.getCategoryName() + " " + category.getCategoryWeight() + " / " + category.calculateTotalFinalWeight());
            for (Assignment assignment : category.getAssignments()) {
                System.out.println(assignment.getAssignmentName() + " " + assignment.getWeight() + " " + assignment.getCurrentGrade() +" "+ assignment.getfinalweight());
            }
            totalFinalWeight += category.calculateTotalFinalWeight();
            System.out.println("-------------------------");
        }
        System.out.println("Total " + totalFinalWeight + " " + calculateLetterGrade(totalFinalWeight, gradeScale));
    } else {
        System.out.println("Class not found.");
    }
 }
    
  public void saveCategoriesWithTotalsToFile(SaveClass savedClass, String filePath, Map<Integer, String> gradeScale) {
    if (savedClass != null) {
        float totalFinalWeight = 0.0f;
        String className = savedClass.getClassName();
        Course classData = savedClass.getClassData();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(className + "\n");
            writer.write("Categories and their totals for class: " + className + "\n");
            
            for (TypeOfAssignmentCategory category : classData.getAssignmentCategories()) {
                writer.write("Category: " + category.getCategoryName() + " " + category.getCategoryWeight() + " / " + category.calculateTotalFinalWeight() + "\n");
                
                for (Assignment assignment : category.getAssignments()) {
                    writer.write(assignment.getAssignmentName() + " " + assignment.getWeight() + " " + assignment.getCurrentGrade() +" "+ assignment.getfinalweight() + "\n");
                }
                totalFinalWeight += category.calculateTotalFinalWeight();
                writer.write("-------------------------\n");
            }
            writer.write("Total " + totalFinalWeight + " " + calculateLetterGrade(totalFinalWeight, gradeScale)+ "\n");
            
            System.out.println("Output saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    } else {
        System.out.println("Class not found.");
    }
}
  
  // Helper method to calculate letter grade based on total weight
// Helper method to calculate letter grade based on total weight and controller instance
public String calculateLetterGrade(float totalScore, Map<Integer, String> gradeScale) {
    for (Map.Entry<Integer, String> entry : gradeScale.entrySet()) {
        if (totalScore >= entry.getKey()) {
            return entry.getValue();
        }
    }
    return "F"; // Default grade if no matching grade is found
}

public void printCurrentGrades(SaveClass savedClass) {
        if (savedClass != null) {
            System.out.println("Current grades for class: " + savedClass.getClassName());
            Course classData = savedClass.getClassData();
            for (TypeOfAssignmentCategory category : classData.getAssignmentCategories()) {
                System.out.println("Category: " + category.getCategoryName());
                for (Assignment assignment : category.getAssignments()) {
                    System.out.println("Assignment: " + assignment.getAssignmentName() + ", Current Grade: " + assignment.getCurrentGrade());
                }
            }
        } else {
            System.out.println("Class not found.");
        }
    }
} 


