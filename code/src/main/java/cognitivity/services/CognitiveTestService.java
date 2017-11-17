package cognitivity.services;

import cognitivity.model.CognitiveTest;
import org.springframework.stereotype.Service;

/**
 *
 * Business service for cognitive test related operations.
 *
 */

@Service
public class CognitiveTestService {


    public void addTestForTM(CognitiveTest t, String testManagerID) {

    }

    public CognitiveTest[] getTestsForTM(String testManagerID) {
        return new CognitiveTest[0];
    }

    public CognitiveTest getTestForTM(String testID, String testManagerID) {
        return null;
    }
}
