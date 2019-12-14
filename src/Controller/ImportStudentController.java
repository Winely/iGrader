package Controller;

import Model.Section;
import Model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/***
 * Import students from csv file to a specific section
 */
public class ImportStudentController {
    public static void importFromCSV(String filePath, Section section){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                Student student = Student.fromCSVLine(line);
                if (student == null) continue;
                student.setSection(section);
                student.save();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
