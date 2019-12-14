package Controller;

import Model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectController {

    public Subject deepCopySubject(Subject original, Subject copy) {
        copy.setLabel(original.getLabel());
        copy.setWeight(original.getWeight());
        Subject copyParent = new Subject();
        List<Subject> copyChildren = new ArrayList<>();
        copy.setParent(deepCopySubject(original.getParent(), copyParent));
        for (Subject child: original.getChildren()) {
            Subject copyChild = new Subject();
            copyChildren.add(deepCopySubject(child, copyChild));
        }
        copy.setChildren(copyChildren);
        copy.setMaxScore(original.getMaxScore());
        return copy;
    }

}
