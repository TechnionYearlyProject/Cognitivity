package cognitivity.services;

import cognitivity.dao.AbstractEntity;

/**
 * Created by ophir on 08/12/17.
 */
public abstract class AbstractService<RepoType extends AbstractEntity> {

    protected final RepoType repository;

    protected AbstractService(RepoType repository) {
        this.repository = repository;
    }


}
