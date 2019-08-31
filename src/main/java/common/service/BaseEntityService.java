package common.service;

import common.model.BaseEntity;
import common.model.BaseTimeEntity;
import common.model.PageResult;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 周林
 * @Description 基础服务
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
public interface BaseEntityService {

    <T> T get(Class<T> clazz, Long id);

    <T> T get(Class<T> clazz, String where, Object... parameters);

    <T extends BaseEntity> List findAll(Class<T> klass);

    <T> List<T> find(Class<T> clazz, String where, Object... parameters);

    /*
    *
    * @firstIndex 从0开始。
    * */
    <T> List<T> find(Class<T> clazz, Long firstIndex, Long limit, String where, Object... parameters);

    <T> List<T> find(Class<T> clazz, Long limit, String where, Object... parameters);

    /*
    *
    * @pageNumber 从1开始  firstIndex = (pageNumber - 1) * pageSize。
    * */
    <T> PageResult<T> page(Class<T> clazz, Long pageNumber, Long pageSize, String where, Object... parameters);

    <T> long count(Class<T> clazz, String where, Object... parameters);

    <T extends BaseTimeEntity> T save(T entity, Long operatorId);

    <T extends BaseTimeEntity> T update(T entity, Long operatorId);

    <T extends BaseTimeEntity> void delete(T entity, Long operatorId);

    <T extends BaseEntity> void delete(T entity);

    <T extends BaseTimeEntity> void delete(Class<T> klass, Long id, Long operatorId);

    <T extends BaseEntity> void delete(Class<T> klass, Long id);

    <T extends BaseTimeEntity> void batchDelete(Class<T> klass, List<Long> entities, Long operatorId);

    <T extends BaseEntity> void batchDelete(Class<T> klass, List<Long> entities);

    <T extends BaseTimeEntity> void delete(T entity, Long operatorId, Consumer<T> handle);

    <T extends BaseEntity> void delete(T entity, Consumer<T> handle);

    <T extends BaseTimeEntity> void delete(Class<T> klass, Long id, Long operatorId, Consumer<T> handle);

    <T extends BaseEntity> void delete(Class<T> klass, Long id, Consumer<T> handle);

    <T extends BaseTimeEntity> void batchDelete(Class<T> klass, List<Long> entities, Long operatorId, Consumer<T> handle);

    <T extends BaseEntity> void batchDelete(Class<T> klass, List<Long> entities, Consumer<T> handle);

    <T> void delete(T entity);

    <T> T save(T entity);

    <T> T update(T entity);
}
