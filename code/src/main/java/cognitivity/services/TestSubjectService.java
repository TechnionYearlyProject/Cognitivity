package cognitivity.services;

import cognitivity.dao.TestSubject;
import cognitivity.repositories.TestSubjectRepository;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for test subject related operations.
 *
 */

@Service
public class TestSubjectService extends AbstractService<TestSubjectRepository>  {


    protected TestSubjectService(TestSubjectRepository repository) {
        super(repository);
    }

    public void addSubject(TestSubject s) {

    }

    public TestSubject[] getSubjects() {
        return new TestSubject[0];
    }

    public TestSubject getSubject(String id) {
        return null;
    }
}
