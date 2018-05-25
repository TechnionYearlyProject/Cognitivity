package cognitivity.dao;

import cognitivity.entities.TestManager;
/**
 * Created by Guy on 20/1/18.
 */
public interface TestManagerDAO {

    /**
     *
     * @Note! API documentation of get, delete, add and update is in the AbstractDAO
     *
     */
    public TestManager get(Long id);
    public void delete(Long id);
    public long add(TestManager data);
    public long update(TestManager data);

    /**
     *
     * this function returns the id of the testManager by his email
     *  (the email addresses are unique)
     *
     * @param email the email string of the manager
     * @return the id of the testManager
     *
     * @throws :
     *  NoResultException - if there is no result
     *  NonUniqueResultException - if more than one result
     *
     */
    public long getIdFromEmail(String email);


    /**
     * Check if a manager with id exists in the db.
     */
    boolean managerWithIdExists(long managerId);
}
