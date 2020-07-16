package com.onesuo.app.common.utils;

import java.util.List;

/**
 * List 工具类
 */
public class ListUtil {
	/**
	 * 判断list是否为空, 不为空返回true
	 * @param list
	 * @return
	 */
	public static Boolean isNotEmpty(List list) {
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断list是否为空, 空返回true
	 * @param list
	 * @return
	 */
	public static Boolean isEmpty(List list) {
		return !ListUtil.isNotEmpty(list);
	}
}