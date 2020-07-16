package com.onesuo.app.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<E extends BaseModel> {
    /**
     * 查询对象
     */
    E findOne(Integer id);
    /**
     * 查询多个对象
     * @param query
     * @return
     */
    List<E> findAll(BaseQuery query);
    /**
     * 查询数量
     * @param query
     * @return
     */
    int count(BaseQuery query);
    /**
     * 保存对象返回id
     * @param entity
     * @return
     */
    int save(E entity);

    /**
     * 保存多个对象
     * @param entities
     */
    int saveAll(List<E> entities);
    /**
     * 保存或更新
     * @param entity
     * @return
     */
    int saveOrUpdate(E entity);
    /**
     * 更新对象
     * @param entity
     * @return
     */
    int update(E entity);
    /**
     * 更新多个对象
     * @param entities
     */
    int updateAll(List<E> entities);
    /**
     * 删除对象
     * @param id
     * @return
     */
    int delete(Integer id);
    /**
     * 删除多个对象
     * @param ids
     */
    int deleteAll(@Param("ids") List<Integer> ids);
}
