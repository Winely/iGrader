package Test;

import Database.DAO;
import Model.Subject;

public class DatabaseTest {
    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.setComment("Hello");
        subject.save();
        subject.setComment("world");
        subject.refresh();
        assert subject.getComment().equals("Hello");
        new DAO().delete(Subject.class, subject.getId());
    }
}
