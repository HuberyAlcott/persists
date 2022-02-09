package dandelion.persists.defines;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/** @author Marcus */
@NoRepositoryBean
public interface GeneralPersist<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {}
