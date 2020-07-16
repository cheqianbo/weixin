package com.onesuo.app.common.base;

import com.onesuo.app.common.base.ext.BaseExtDao;
import com.onesuo.app.common.base.ext.BaseExtService;

import java.util.List;

public interface BaseService<E extends BaseModel,Q extends BaseQuery> {
    /**
     * 查询对象
     */
    E findOne(Integer id);

    /**
     * 查询多个对象
     */
    List<E> findAll(Q query);

    /**
     * 查询多个对象
     */
    <S extends BaseSerializable> List<S> findAll(Q query, BaseExtDao<S> baseExtDao);

    /**
     * 查询多个对象（分页）
     */
    Page<E> findAllByPage(Q query);

    /**
     * 查询多个对象（分页）
     */
    <S extends BaseSerializable> Page<S> findAllByPage(Q query, BaseExtDao<S> baseExtDao);

    /**
     * 查询数量
     */
    int count(Q query);

    /**
     * 验证对象
     */
    void verify(E model);

    /**
     * 保存对象返回id
     */
    int save(E model);

    /**
     * 保存对象返回id
     */
    int save(E model, BaseExtService<E> baseExtService);

    /**
     * 保存多个对象
     */
    int saveAll(List<E> models);

    /**
     * 保存多个对象
     */
    int saveAll(List<E> models, BaseExtService<E> baseExtService);

    /**
     * 保存或更新
     */
    int saveOrUpdate(E model);

    /**
     * 保存或更新
     */
    int saveOrUpdate(E model, BaseExtService<E> baseExtService);

    /**
     * 更新对象
     */
    int update(E model);

    /**
     * 更新对象
     */
    int update(E model, BaseExtService<E> baseExtService);

    /**
     * 更新多个对象
     */
    int updateAll(List<E> models);

    /**
     * 更新多个对象
     */
    int updateAll(List<E> models, BaseExtService<E> baseExtService);

    /**
     * 删除对象
     */
    int delete(Integer id);

    /**
     * 删除多个对象
     */
    int deleteAll(List<Integer> ids);
}
