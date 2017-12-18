package cognitivity.services;

import cognitivity.repositories.AbstractRepository;

/**
 * Created by ophir on 08/12/17.
 */
public abstract class AbstractService<RepoType extends AbstractRepository> {

    protected final RepoType repository;

    protected AbstractService(RepoType repository) {
        this.repository = repository;
    }


}
