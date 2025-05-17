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
public class Course {
     private List<TypeOfAssignmentCategory> assignmentCategories;

    public Course() {
        this.assignmentCategories = new ArrayList<>();
    }

    public void addAssignmentCategory(TypeOfAssignmentCategory category) {
        assignmentCategories.add(category);
    }

    public Iterable<TypeOfAssignmentCategory> getAssignmentCategories() {
        return assignmentCategories;
    }

}
