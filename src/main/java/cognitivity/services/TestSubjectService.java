package cognitivity.services;

import org.springframework.stereotype.Service;
import cognitivity.model.TestSubject;

/**
 *
 * Business service for test subject related operations.
 *
 */

@Service
public class TestSubjectService {


    public void addSubject(TestSubject s) {

    }

    public TestSubject[] getSubjects() {
        return new TestSubject[0];
    }

    public TestSubject getSubject(String id) {
        return null;
    }
}
