package cognitivity.dao;

import cognitivity.entities.CognitiveTest;

/**
 * Data Access Object
 */
public class CognitiveTestDAO extends AbstractDAO<CognitiveTest> {

    public CognitiveTest get(Long id){
        return super.get(id, CognitiveTest.class);
    }

    public void delete(Long id){
        super.delete(id, CognitiveTest.class);
    }
}
