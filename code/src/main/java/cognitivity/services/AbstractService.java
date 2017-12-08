package cognitivity.services;

import cognitivity.dao.AbstractRepository;

/**
 * Created by ophir on 08/12/17.
 */
abstract public class AbstractService<RepoType extends AbstractRepository> {

    protected final RepoType repository;

    protected AbstractService(RepoType repository) {
        this.repository = repository;
    }


}
