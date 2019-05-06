package common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * @author zhoulin
 * @date 2019/1/15
 * JpaRepository接口（SpringDataJPA提供的简单数据操作接口）
 * JpaSpecificationExecutor（SpringDataJPA提供的复杂查询接口)
 * Serializable（序列化接口）
 **/
@NoRepositoryBean
public interface CommonRepository<T, PK> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T>, Serializable, PagingAndSortingRepository<T, PK> {
}
