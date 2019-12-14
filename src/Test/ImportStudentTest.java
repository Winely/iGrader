package Test;

import Controller.ImportStudentController;
import Database.DAO;
import Model.Section;
import Model.Student;

public class ImportStudentTest {
    public static void main(String[] args) {
        DAO dao = new DAO();
        Section section = dao.findById(Section.class, 1);
        ImportStudentController.importFromCSV("src/Test/students.csv", section);
        Student alice = dao.findById(Student.class, "U0000001");
        Student bob = dao.findById(Student.class, "U0000002");
        assert alice.getName().getFirstName().equals("Alice");
        assert bob.getName().getFirstName().equals("Bob");
        dao.delete(Student.class, alice.getId());
        dao.delete(Student.class, bob.getId());
    }
}
