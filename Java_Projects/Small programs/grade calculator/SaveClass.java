/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_7;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Cwhit
 */
public class SaveClass implements Comparable<SaveClass> {
    private String className;
    private Course classData;

    public SaveClass(String className, Course classData) {
        this.className = className;
        this.classData = classData;
    }

    public String getClassName() {
        return className;
    }

    public Course getClassData() {
        return classData;
    }

    @Override
    public int compareTo(SaveClass other) {
        return this.className.compareTo(other.className);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveClass saveClass = (SaveClass) o;
        return Objects.equals(className, saveClass.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className);
    }
}
