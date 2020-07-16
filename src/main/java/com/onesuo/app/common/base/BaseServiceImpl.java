package com.onesuo.app.common.base;

import com.onesuo.app.common.base.ext.BaseExtDao;
import com.onesuo.app.common.base.ext.BaseExtService;
import com.onesuo.app.common.exception.ParamsException;
import com.onesuo.app.common.exception.ServerException;
import com.onesuo.app.common.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BaseServiceImpl<D extends BaseDao<E>,E extends BaseModel ,Q extends BaseQuery> implements BaseService<E,Q> {
    @Autowired
    protected D dao;
    /**
     * 查询对象
     *
     * @param id
     * @return
     */
    @Override
    public E findOne(Integer id) {
        // 验证
        if (null== id)
            throw new ParamsException();
        E result = dao.findOne(id);
        return result;
    }
    /**
     * 查询全部对象列表
     *
     * @param query
     * @return
     */
    @Override
    public List<E> findAll(Q query) throws ServerException {
        return findAll(query, new BaseExtDao<E>() {
            @Override
            public List<E> findAll(BaseQuery query) {
                return dao.findAll(query);
            }

            @Override
            public Integer count(BaseQuery query) {
                return null;
            }
        });
    }

    /**
     * 查询全部对象列表
     *
     * @param query
     * @return
     */
    @Override
    public <S extends BaseSerializable> List<S> findAll(Q query, BaseExtDao<S> baseExtDao) throws ServerException {
        if (null == baseExtDao)
            throw new ServerException();
        return baseExtDao.findAll(query);
    }

    /**
     * 查询对象列表（分页）
     *
     * @param query
     * @return
     */
    @Override
    public Page<E> findAllByPage(Q query) throws ServerException {
        return findAllByPage(query, new BaseExtDao<E>() {
            @Override
            public List<E> findAll(BaseQuery query) {
                return dao.findAll(query);
            }

            @Override
            public Integer count(BaseQuery query) {
                return dao.count(query);
            }
        });
    }

    /**
     * 查询对象列表（分页）
     *
     * @param query
     * @return
     */
    @Override
    public <S extends BaseSerializable> Page<S> findAllByPage(Q query, BaseExtDao<S> baseExtDao) throws ServerException {
        // 验证
        if (null == query)
            throw new ParamsException();
        if (null == baseExtDao)
            throw new ServerException();
        // 组织PageDTO的数据
        Page<S> page = new Page<>();
        page.index = query.getPgIndex();
        page.size = query.getPgCount();
        Integer count = baseExtDao.count(query);
        if (null == count || count == 0) {
            page.totalElements = 0;
            return page;
        }
        page.totalElements = count;
        page.elements = baseExtDao.findAll(query);
        return page;
    }

    /**
     * 查询数量
     *
     * @param query
     * @return
     */
    @Override
    public int count(Q query) {
        // 验证
        if (null == query)
            throw new ParamsException();
        int count = dao.count(query);
        return count;
    }

    /**
     * 验证对象
     */
    @Override
    public void verify(E model) {
        return;
    }

    /**
     * 保存对象返回id
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int save(E model) {
        return save(model, new BaseExtService<E>() {
            @Override
            public void verification(E model) {
                verify(model);
            }
        });
    }

    /**
     * 保存对象返回id
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int save(E model, BaseExtService<E> baseExtService) {
        // 验证
        if (null == model)
            throw new ParamsException();
        if (null == baseExtService)
            throw new ServerException();
        baseExtService.verification(model);
        dao.save(model);
        return model.getId();
    }

    /**
     * 保存对象列表
     *
     * @param models
     */
    @Transactional
    @Override
    public int saveAll(List<E> models) {
        return saveAll(models, new BaseExtService<E>() {
            @Override
            public void verification(E model) {
                verify(model);
            }
        });
    }

    /**
     * 保存对象列表
     *
     * @param models
     */
    @Transactional
    @Override
    public int saveAll(List<E> models, BaseExtService<E> baseExtService) {
        // 验证
        if (ListUtil.isEmpty(models))
            throw new ParamsException();
        if (null == baseExtService)
            throw new ServerException();
        for (E model : models) {
            baseExtService.verification(model);
        }
        return dao.saveAll(models);
    }

    /**
     * 保存或更新
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int saveOrUpdate(E model) {
        return saveOrUpdate(model, new BaseExtService<E>() {
            @Override
            public void verification(E model) {
                verify(model);
            }
        });
    }

    /**
     * 保存或更新
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int saveOrUpdate(E model, BaseExtService<E> baseExtService) {
        // 验证
        if (null == model)
            throw new ParamsException();
        if (null == baseExtService)
            throw new ServerException();
        baseExtService.verification(model);
        return dao.saveOrUpdate(model);
    }

    /**
     * 更新对象
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int update(E model) {
        return update(model, new BaseExtService<E>() {
            @Override
            public void verification(E model) {
                verify(model);
            }
        });
    }

    /**
     * 更新对象
     *
     * @param model
     * @return
     */
    @Transactional
    @Override
    public int update(E model, BaseExtService<E> baseExtService) {
        // 验证
        if (null == model)
            throw new ParamsException();
        if (null == baseExtService)
            throw new ServerException();
        baseExtService.verification(model);
        return dao.update(model);
    }

    /**
     * 更新对象列表
     *
     * @param models
     */
    @Transactional
    @Override
    public int updateAll(List<E> models) {
        return updateAll(models, new BaseExtService<E>() {
            @Override
            public void verification(E model) {
                verify(model);
            }
        });
    }

    /**
     * 更新对象列表
     *
     * @param models
     */
    @Transactional
    @Override
    public int updateAll(List<E> models, BaseExtService<E> baseExtService) {
        // 验证
        if (ListUtil.isEmpty(models))
            throw new ParamsException();
        if (null == baseExtService)
            throw new ServerException();
        for (E model : models) {
            baseExtService.verification(model);
        }
        return dao.updateAll(models);
    }

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int delete(Integer id) {
        // 验证
        if (null == id || id < 1)
            throw new ParamsException();
        return dao.delete(id);
    }

    /**
     * 删除多个对象
     *
     * @param ids
     */
    @Transactional
    @Override
    public int deleteAll(List<Integer> ids) {
        // 验证
        if (null == ids || ids.size() == 0)
            throw new ParamsException();
        return dao.deleteAll(ids);
    }
}
