package com.onesuo.app.common.base;

import java.util.LinkedList;
import java.util.List;

/**
 * 对象转换
 */
public class BaseDataConvert<E, T> {
	public T entity2dto(E entity) {
		return null;
	}

	public E dto2entity(T dto) {
		return null;
	}

	/**
	 * entities列表转dtos列表
	 * @param entities
	 * @return
	 */
	public List<T> entities2dtos(List<E> entities) {
		if (null == entities || entities.size() == 0) {
			return new LinkedList<T>();
		}
		List<T> dtos = new LinkedList<T>();
		for (E entity : entities) {
			dtos.add(entity2dto(entity));
		}
		return dtos;
	}

	/**
	 * dtos列表转entities列表
	 * @param dtos
	 * @return
	 */
	public List<E> dtos2entities(List<T> dtos) {
		if (null == dtos || dtos.size() == 0) {
			return new LinkedList<E>();
		}
		List<E> entities = new LinkedList<E>();
		for (T dto : dtos) {
			entities.add(dto2entity(dto));
		}
		return entities;
	}
}