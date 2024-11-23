package com.tlv8.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tlv8.system.pojo.SaOpperson;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("system")
public interface SaOppersonMapper {
	/**
	 * 根据主键获取数据对象
	 *
	 * @param sid
	 * @return
	 */
	SaOpperson selectByPrimaryKey(String sid);

	SaOpperson selectByCode(String scode);

	/**
	 * 插入数据到数据库
	 *
	 * @param saOpPerson
	 * @return
	 */
	int insertData(SaOpperson saOpPerson);

	/**
	 * 更新数据
	 *
	 * @param saOpPerson
	 * @return
	 */
	int updateData(SaOpperson saOpPerson);

	/**
	 * 删除对象对应的数据
	 *
	 * @param saOpPerson
	 * @return
	 */
	int deleteData(SaOpperson saOpPerson);

	/**
	 * 删除指定主键的数据
	 *
	 * @param sid
	 * @return
	 */
	int deleteDataByPrimaryKey(String sid);

	/**
	 * 逻辑删除指定主键的数据
	 *
	 * @param sid
	 * @return
	 */
	int logicDeleteDataByPrimaryKey(String sid);

	/**
	 * 逻辑恢复指定主键的数据
	 *
	 * @param sid
	 * @return
	 */
	int logicRecoveryByPrimaryKey(String sid);
}