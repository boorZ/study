package common.repository.impl;

import common.model.BaseTimeEntity;
import common.model.PageResult;
import common.repository.BaseRepository;
import common.util.Assert;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author 周林
 * @Description 基础数据访问组件实现
 * @email prometheus@noask-ai.com
 * @date 2019/8/30
 */
@Repository
public class BaseRepositoryImpl implements BaseRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void flush() {
        this.em.flush();
    }

    @Override
    public <T> T get(Class<T> clazz, Long id) {
        return this.em.find(clazz, id);
    }

    @Override
    public <T> T get(Class<T> clazz, String where, Object... parameters) {
        Query query = this.commonGetQuery(clazz, where, parameters);
        List results = query.getResultList();
        return (results != null && !results.isEmpty()) ? (T)results.get(0) : null;
    }

    private <T> String commonGetEntityName (Class<T> clazz) {
        Entity entity = clazz.getAnnotation(Entity.class);
        String entityName = entity.name().trim();
        entityName = (entityName.isEmpty()) ? clazz.getSimpleName() : entityName;
        return entityName;
    }

    private <T> Query commonGetQuery (Class<T> clazz, String where, Object... parameters) {
        String entityName = this.commonGetEntityName(clazz);
        return this.commonGetQuery(clazz, entityName, where, parameters);
    }
    private <T> Query commonGetQuery (Class<T> clazz, String entityName, String where, Object... parameters) {
        Query query = this.em.createQuery(" FROM "+entityName + " WHERE " + where , clazz);
        if(parameters != null){
            for(int i = 0, len = parameters.length;i < len;i++){
                Object parameter = parameters[i];
                query.setParameter(i, parameter);
            }
        }
        return query;
    }

    @Override
    public <T> List<T> find(Class<T> clazz, String where, Object... parameters) {
        Query query = this.commonGetQuery(clazz, where, parameters);
        return query.getResultList();
    }

    @Override
    public <T> List<T> find(Class<T> clazz, Integer firstResult, Integer maxResults, String where, Object... parameters) {
        Query query = this.commonGetQuery(clazz, where, parameters);
        // 设置从第几条记录开始查起
        query.setFirstResult(firstResult);
        // 设置查多少条记录
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public <T> PageResult<T> page(Class<T> clazz, Integer pageNumber, Integer pageSize, String where, Object... parameters) {
        String entityName = this.commonGetEntityName(clazz);
        Query query = this.commonGetQuery(clazz, entityName, where, parameters);
        // 设置从第几条记录开始查起
        query.setFirstResult((pageNumber - 1) * pageSize);
        // 设置查多少条记录
        query.setMaxResults(pageSize);
        Query countQuery = this.em.createQuery("SELECT COUNT(*) FROM "+entityName + " WHERE " + where);
        if(parameters != null){
            for(int i = 0, len = parameters.length;i < len;i++){
                Object parameter = parameters[i];
                countQuery.setParameter(i, parameter);
            }
        }
        List resultList = query.getResultList();
        Long totalCount =  (Long)countQuery.getSingleResult();
        return new PageResult<T>(pageNumber, pageSize, resultList, totalCount);
    }

    @Transactional
    @Override
    public <T> T save(T model) {
        this.em.merge(model);
        return model;
    }

    @Transactional
    @Override
    public <T extends BaseTimeEntity> T save(T model, Long operatorId) {
        model.setCreateAt(new Date(System.currentTimeMillis()));
        model.setCreateBy(operatorId);
        this.em.persist(model);
        return model;
    }

    @Transactional
    @Override
    public <T> T update(T model) {
        this.em.merge(model);
        return model;
    }

    @Transactional
    @Override
    public <T extends BaseTimeEntity> T update(T model, Long operatorId) {
        model.setUpdateAt(new Date(System.currentTimeMillis()));
        model.setUpdateBy(operatorId);
        this.em.merge(model);
        return model;
    }

    @Override
    public <T> long count(Class<T> clazz, String where, Object... parameters) {
        String entityName = this.commonGetEntityName(clazz);
        Query query = this.commonGetQuery(clazz,entityName, where, parameters);
        return (Long)query.getSingleResult();
    }

    @Override
    public <T> void delete(T entity) {
        this.em.remove(entity);
    }

}
