package common.service.impl;

import common.model.BaseEntity;
import common.model.BaseTimeEntity;
import common.model.PageResult;
import common.repository.BaseRepository;
import common.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 周林
 * @Description 基础服务实现
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
@Service
public class BaseEntityServiceImpl implements BaseEntityService {

    @Autowired
    private BaseRepository baseRepository;

    @Override
    public <T> T get(Class<T> clazz, Long id) {
        return baseRepository.get(clazz, id);
    }

    @Override
    public <T> T get(Class<T> clazz, String where, Object... parameters) {
        return baseRepository.get(clazz, where, parameters);
    }

    @Override
    public <T extends BaseEntity> List<T> findAll(Class<T> klass) {
        return baseRepository.find(klass,"1 = 1", null);
    }

    @Override
    public <T> List<T> find(Class<T> clazz, String where, Object... parameters) {
        return baseRepository.find(clazz, where, parameters);
    }

    @Override
    public <T> List<T> find(Class<T> clazz, Long firstIndex, Long limit, String where, Object... parameters) {
        return null;
    }

    @Override
    public <T> List<T> find(Class<T> clazz, Long limit, String where, Object... parameters) {
        return null;
    }

    @Override
    public <T> PageResult<T> page(Class<T> clazz, Long pageNumber, Long pageSize, String where, Object... parameters) {
        return null;
    }

    @Override
    public <T> long count(Class<T> clazz, String where, Object... parameters) {
        return 0;
    }

    @Override
    public <T extends BaseTimeEntity> T save(T entity, Long operatorId) {
        return null;
    }

    @Override
    public <T extends BaseTimeEntity> T update(T entity, Long operatorId) {
        return null;
    }

    @Override
    public <T extends BaseTimeEntity> void delete(T entity, Long operatorId) {

    }

    @Override
    public <T extends BaseEntity> void delete(T entity) {

    }

    @Override
    public <T extends BaseTimeEntity> void delete(Class<T> klass, Long id, Long operatorId) {

    }

    @Override
    public <T extends BaseEntity> void delete(Class<T> klass, Long id) {

    }

    @Override
    public <T extends BaseTimeEntity> void batchDelete(Class<T> klass, List<Long> entities, Long operatorId) {

    }

    @Override
    public <T extends BaseEntity> void batchDelete(Class<T> klass, List<Long> entities) {

    }

    @Override
    public <T extends BaseTimeEntity> void delete(T entity, Long operatorId, Consumer<T> handle) {

    }

    @Override
    public <T extends BaseEntity> void delete(T entity, Consumer<T> handle) {

    }

    @Override
    public <T extends BaseTimeEntity> void delete(Class<T> klass, Long id, Long operatorId, Consumer<T> handle) {

    }

    @Override
    public <T extends BaseEntity> void delete(Class<T> klass, Long id, Consumer<T> handle) {

    }

    @Override
    public <T extends BaseTimeEntity> void batchDelete(Class<T> klass, List<Long> entities, Long operatorId, Consumer<T> handle) {

    }

    @Override
    public <T extends BaseEntity> void batchDelete(Class<T> klass, List<Long> entities, Consumer<T> handle) {

    }

    @Override
    public <T> void delete(T entity) {

    }

    @Override
    public <T> T save(T entity) {
        return null;
    }

    @Override
    public <T> T update(T entity) {
        return null;
    }
}
