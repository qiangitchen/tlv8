package com.tlv8.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tlv8.system.pojo.SaOporg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("system")
public interface SaOporgMapper {
	/**
     * 查询所有数据
     *
     * @return
     */
    List<SaOporg> selectList();

    /**
     * 查询根数据（父节点为空）
     *
     * @return
     */
    List<SaOporg> selectRootList();

    /**
     * 根据父id获取数据列表
     *
     * @param parent
     * @return
     */
    List<SaOporg> selectListByParentID(String parent);
    List<SaOporg> selectAllByParentID(String parent);

    /**
     * 根据主键获取数据对象
     *
     * @param sid
     * @return
     */
    SaOporg selectByPrimaryKey(String sid);

    /**
     * 插入数据到数据库
     *
     * @param obj
     * @return
     */
    int insertData(SaOporg obj);

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    int updateData(SaOporg obj);

    /**
     * 删除对象对应的数据
     *
     * @param obj
     * @return
     */
    int deleteData(SaOporg obj);

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

    List<SaOporg> selectRecycleList();
    
    SaOporg selectByParentIdPersonId(Map<String, String> param);
}