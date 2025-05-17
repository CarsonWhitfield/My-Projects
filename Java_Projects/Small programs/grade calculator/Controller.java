/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_7;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cwhit
 */
public class Controller {
    private Grades grades;
    private String courseName;
    private Map<Integer, String> gradeScale;

    public Controller(Grades grades, String courseName) {
        this.grades = grades;
        this.courseName = courseName;
        
        // Hardcoded grade scale
        this.gradeScale = new HashMap<>();
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

    public Grades getGrades() {
        return grades;
    }

    public String getCourseName() {
        return courseName;
    }

    public Map<Integer, String> getGradeScale() {
        return gradeScale;
    }
}
