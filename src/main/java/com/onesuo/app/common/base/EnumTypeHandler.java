package com.onesuo.app.common.base;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumTypeHandler<E extends Enum<?> & CodeBaseEnum> extends BaseTypeHandler<CodeBaseEnum> {
	private Class<E> clazz;

	public EnumTypeHandler(Class<E> enumType) {
		if (enumType == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.clazz = enumType;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, CodeBaseEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return codeOf(clazz, rs.getInt(columnName));
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return codeOf(clazz, rs.getInt(columnIndex));
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return codeOf(clazz, cs.getInt(columnIndex));
	}

	@SuppressWarnings("hiding")
	public <E extends Enum<?> & CodeBaseEnum> E codeOf(Class<E> enumClass, int code) {
		E[] enumConstants = enumClass.getEnumConstants();
		for (E e : enumConstants) {
			if (e.getCode() == code)
				return e;
		}
		return null;
	}
}