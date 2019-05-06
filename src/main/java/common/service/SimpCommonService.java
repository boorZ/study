package common.service;

import common.repository.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zhoulin
 * @date 2019/1/16
 * @param <T>   POJO类型
 * @param <PK>
 * @param <F>   查询条件
 * @param <I>   添加或修改条件
 */
public interface SimpCommonService<T, PK> {
    CommonRepository<T, PK> getCommonRepository();

    int DEFAULT_PAGE_NUM = 1;

    int DEFAULT_PAGE_SIZE = 10;

    default Pageable buildPageable(Integer pageNum, Integer pageSize){
        pageNum = pageNum == null ? DEFAULT_PAGE_NUM : pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        return PageRequest.of(pageNum - 1, pageSize);
    }
    /** 分页不带查询 **/
    default Page<T> findAll(Integer pageNum, Integer pageSize) {
        return getCommonRepository().findAll(buildPageable(pageNum,pageSize));
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
