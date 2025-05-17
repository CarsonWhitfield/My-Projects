/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author Cwhit
 */
public class Assignment_7 {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        String courseName = null;
        boolean courseEntered = false;
       

        
         // Create a Controller and Grades instance
        Controller controller = new Controller(new Grades(), courseName);
        // Create a new class
        CreateNewClass createNewClass = new CreateNewClass(courseName);
        
        while(!courseEntered){
         System.out.println("Assignment Management System Menu:");
         System.out.println("1. Enter the name of the course");
         System.out.println("2. View courses");
         System.out.println("3. Save course");
         System.out.println("4. Future grades");
         System.out.println("5. Exit");
         System.out.print("Choose an option: ");
         
         int option = scanner.nextInt();
         scanner.nextLine(); // Consume newline after reading integer input
         
         switch(option){
         case 1:
              System.out.print("Enter the name of the course: ");
              courseName = scanner.nextLine();
              boolean creatingCategory = true;
    
              while (creatingCategory) {
                System.out.println("\n1. Create a new category");
                createCategory(scanner, createNewClass);
                boolean creatingAssignmentsInCategory = true;
        
              while (creatingAssignmentsInCategory) {
                createAssignment(scanner, createNewClass);
                System.out.println("Do you want to create another assignment in this category? (yes/no)");
                String userInput = scanner.nextLine();
            
              if (userInput.equalsIgnoreCase("no")) {
                System.out.println("Do you want to create another course in this category? (yes/no)");
                String input = scanner.nextLine();
                creatingAssignmentsInCategory = false;
                if (input.equalsIgnoreCase("no")) {
                    creatingCategory = false; // Set flag to exit outer loop
                }
            }
        }
    }
    break;
         case 2:
             if (controller != null) {
        Grades grades = controller.getGrades();
        TreeSet<SaveClass> saveData = grades.getSaveData();

        // Print out the names of the courses
        System.out.println("\nList of Courses:");
        int i = 1;
        for (SaveClass savedClass : saveData) {
            System.out.println(i + ". " + savedClass.getClassName());
            i++;
        }

        // Read the course name as a string
        System.out.print("Enter the name of the course to view grades: ");
        String courseN = scanner.nextLine();

        // Find the SaveClass object corresponding to the selected courseName
        SaveClass selectedSaveClass = null;
        for (SaveClass savedClass : saveData) {
            if (savedClass.getClassName().equalsIgnoreCase(courseN)) {
                selectedSaveClass = savedClass;
                break;
            }
        }

        // Check if the selectedSaveClass is found
        if (selectedSaveClass != null) {
            // Now, when you call printCategoriesWithTotals, pass the course name and the gradeScale map as arguments
            grades.printCategoriesWithTotals(selectedSaveClass, grades.getGradeScale());
        } else {
            System.out.println("Course not found.");
        }
    } else {
        System.out.println("Please enter the course name first.");
    }
    break;
         case 3:
               if (controller != null && createNewClass != null) {
               // Ask the user for the course name
               System.out.print("Enter the name of the course to save: ");
               String courseToSave = scanner.nextLine();
        
               // Create a Course object and add assignment categories to it
               Course course = new Course();
               for (TypeOfAssignmentCategory category : createNewClass.getClassData()) {
               course.addAssignmentCategory(category);
        }

        // Save class data with the entered course name
        SaveClass saveClass = new SaveClass(courseToSave, course); // Use the entered course name
        Grades grades = controller.getGrades();
        grades.saveClass(saveClass);
        grades.saveCategoriesWithTotalsToFile(saveClass, courseToSave,grades.getGradeScale()); // Use the entered course name for file saving
    } else {
        System.out.println("Please enter the course name first.");
    }
    break;
         case 4:
            if (controller != null) {
        Grades grades = controller.getGrades();
        TreeSet<SaveClass> saveData = grades.getSaveData();

        // Print out the names of the courses
        System.out.println("\nList of Courses:");
        int i = 1;
        for (SaveClass savedClass : saveData) {
            System.out.println(i + ". " + savedClass.getClassName());
            i++;
        }

        // Read the course name as a string
        System.out.print("Enter the number of the course to add future grades: ");
        String cName = scanner.nextLine();

        // Find the SaveClass object corresponding to the selected courseName
        SaveClass selectedSaveClass = null;
        for (SaveClass savedClass : saveData) {
            if (savedClass.getClassName().equalsIgnoreCase(cName)) {
                selectedSaveClass = savedClass;
                break;
            }
        }

        // Check if the selectedSaveClass is found
        if (selectedSaveClass != null) {
            Course course = selectedSaveClass.getClassData();
            for (TypeOfAssignmentCategory category : course.getAssignmentCategories()) {
                System.out.println("Category: " + category.getCategoryName());
                for (Assignment assignment : category.getAssignments()) {
                    System.out.print("Enter future grade for assignment '" + assignment.getAssignmentName() + "': ");
                    float futureGrade = scanner.nextFloat();
                    assignment.setCurrentGrade(futureGrade); // Update current grade with future grade
                }
            }
            // Print temporary changes
            grades.printCurrentGrades(selectedSaveClass);
            System.out.println("Future grades added successfully for course: " + selectedSaveClass.getClassName());
        } else {
            System.out.println("Course not found.");
        }
    } else {
        System.out.println("Please enter the course name first.");
    }
    break;
         case 5:
                    System.out.println("Exiting...");
                    break;
               
            }
         
         }

        scanner.close();
    }

    private static void createCategory(Scanner scanner, CreateNewClass createNewClass) {
        System.out.print("\nEnter the name of the category: ");
        String categoryName = scanner.nextLine();
        System.out.print("Enter the weight of the category: ");
        float weightOfCategory = scanner.nextFloat();
        scanner.nextLine(); // consume newline

        TypeOfAssignmentCategory category = createNewClass.createCategory(categoryName, weightOfCategory);
        System.out.println("Category '" + categoryName + "' created with weight " + weightOfCategory);
    }

    private static void createAssignment(Scanner scanner, CreateNewClass createNewClass) {
        System.out.print("\nEnter the name of the category to add the assignment to: ");
        String categoryName = scanner.nextLine();
        TypeOfAssignmentCategory category = null;
        for (TypeOfAssignmentCategory cat : createNewClass.getClassData()) {
            if (cat.getCategoryName().equals(categoryName)) {
                category = cat;
                break;
            }
        }
        if (category == null) {
            System.out.println("Category not found!");
            return;
        }

        System.out.print("Enter the name of the assignment: ");
        String assignmentName = scanner.nextLine();
        System.out.print("Enter the weight of the assignment: ");
        float weight = scanner.nextFloat();
        System.out.print("Enter the current grade for the assignment: ");
        float currentGrade = scanner.nextFloat();
        scanner.nextLine(); // consume newline

        createNewClass.createAssignment(category, assignmentName, weight, currentGrade);
        System.out.println("Assignment '" + assignmentName + "' added to category '" + categoryName + "'");
    }
    
   public void addFutureGrades(SaveClass savedClass, Scanner scanner) {
    if (savedClass != null) {
        System.out.println("Adding future grades for class: " + savedClass.getClassName());
        Course classData = savedClass.getClassData();
        for (TypeOfAssignmentCategory category : classData.getAssignmentCategories()) {
            System.out.println("Category: " + category.getCategoryName());
            for (Assignment assignment : category.getAssignments()) {
                System.out.print("Enter future grade for assignment '" + assignment.getAssignmentName() + "': ");
                float futureGrade = scanner.nextFloat();
                assignment.setCurrentGrade(futureGrade); // Update current grade with future grade
            }
        }
        System.out.println("Future grades added successfully for course: " + savedClass.getClassName());
    } else {
        System.out.println("Course not found.");
    }
}
}
