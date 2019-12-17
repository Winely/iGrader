package Controller;

import Database.DAO;
import Model.Section;
import Model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * Import students from csv file to a specific section
 */
public class ImportStudentController {
    public static void importFromCSV(String filePath, Section section){
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                Student student = Student.fromCSVLine(line);
                if (student == null) continue;
                student.setSection(section);
                students.add(student);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        new DAO().saveMany(students);
    }
}
