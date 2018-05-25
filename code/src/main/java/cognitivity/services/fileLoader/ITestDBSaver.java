package cognitivity.services.fileLoader;

import cognitivity.dto.TestWrapper;
import cognitivity.exceptions.DBException;

/**
 * Created by ophir on 25/05/18.
 */
public interface ITestDBSaver {

    TestDBSaver convert() throws Exception;

    void writeToMySql() throws DBException;

    TestWrapper getWrapper();
}
