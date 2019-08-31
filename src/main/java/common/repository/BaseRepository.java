package common.repository;

import common.model.BaseTimeEntity;
import common.model.PageResult;

import java.util.List;

/**
 * @author 周林
 * @Description 基础数据访问组件
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
public interface BaseRepository {
    void flush();

    <T> T get(Class<T> clazz, Long id);

    <T> T get(Class<T> clazz, String where, Object...  parameters);

    <T> List find(Class<T> clazz, String where, Object...  parameters);

    <T> List<T> find(Class<T> clazz, Integer firstResult, Integer maxResults, String where, Object...  parameters);

    <T> PageResult<T> page(Class<T> clazz, Integer pageNumber, Integer pageSize, String where, Object...  parameters);

    <T extends BaseTimeEntity> T save(T model, Long operatorId);

    <T extends BaseTimeEntity> T update(T model, Long operatorId);

    <T> long count(Class<T> clazz, String where, Object... parameters);

    <T> void delete(T entity);

    <T> T save(T model);

    <T> T update(T model);
}
