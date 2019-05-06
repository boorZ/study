package common.service;

import common.repository.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author zhoulin
 * @date 2019/1/16
 * @param <T>   POJO类型
 * @param <PK>
 * @param <F>   查询条件
 * @param <I>   添加或修改条件
 */
public interface CommonService<T, PK, F, I> {
    CommonRepository<T, PK> getCommonRepository();

    int DEFAULT_PAGE_NUM = 1;

    int DEFAULT_PAGE_SIZE = 10;

    T create(I input);

    T update(I input);

    Specification<T> buildQuery(F filter);

    default Pageable buildPageable(Integer pageNum, Integer pageSize){
        pageNum = pageNum == null ? DEFAULT_PAGE_NUM : pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        return PageRequest.of(pageNum - 1, pageSize);
    }
    /**
     *  default 虚拟扩展方法
     *  Default方法是指，在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，这打破了Java之前版本对接口的语法限制），
     *      从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码。
     */
    /** 分页带查询 **/
    default Page<T> findAll(Integer pageNum, Integer pageSize, F filter) {

        return getCommonRepository().findAll(buildQuery(filter), buildPageable(pageNum,pageSize));
    }
    /** 分页不带查询 **/
    default Page<T> findAll(Integer pageNum, Integer pageSize) {
        return getCommonRepository().findAll(buildPageable(pageNum,pageSize));
    }
    /** 无分页带查询 **/
    default List<T> findAll(F filter) {
        return getCommonRepository().findAll(buildQuery(filter));
    }
    /** 无分页不带查询 **/
    default List<T> findAll() {
        return getCommonRepository().findAll();
    }

    /** 单个查询 **/
    default T findById(PK id) {
        return this.getCommonRepository().findById(id).get();
    }
    /** 单个修改 **/
    default T updateById(PK id) {
        return this.updateById(id);
    }
    /** 单个删除 **/
    default T deleById(PK id) {
        return this.deleById(id);
    }
}
