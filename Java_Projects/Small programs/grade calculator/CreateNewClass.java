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
public class CreateNewClass {
private String className;
    private List<TypeOfAssignmentCategory> assignmentCategories;

    public CreateNewClass(String className) {
        this.className = className;
        this.assignmentCategories = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public TypeOfAssignmentCategory createCategory(String categoryName, float weightOfCategory) {
        TypeOfAssignmentCategory category = new TypeOfAssignmentCategory(categoryName, weightOfCategory);
        assignmentCategories.add(category);
        return category;
    }

    public void createAssignment(TypeOfAssignmentCategory category, String assignmentName, float weight, float currentGrade) {
        
        category.addAssignment(assignmentName, weight, currentGrade);
        // Recalculate total weight whenever an assignment is added
       
    }

    public List<TypeOfAssignmentCategory> getClassData() {
        return assignmentCategories;
    }
}
