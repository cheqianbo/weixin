package com.onesuo.app.common.base.ext;

import com.onesuo.app.common.base.BaseModel;

public interface BaseExtService<E extends BaseModel> {
	/**
	 * 验证对象
	 */
	void verification(E model);
}