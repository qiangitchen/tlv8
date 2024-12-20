package com.tlv8.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tlv8.system.pojo.SaOppermission;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("system")
public interface SaOppermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sa_oppermission
     *
     * @mbg.generated Wed Dec 20 16:45:11 CST 2023
     */
    int deleteByPrimaryKey(String sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sa_oppermission
     *
     * @mbg.generated Wed Dec 20 16:45:11 CST 2023
     */
    int insert(SaOppermission row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sa_oppermission
     *
     * @mbg.generated Wed Dec 20 16:45:11 CST 2023
     */
    SaOppermission selectByPrimaryKey(String sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sa_oppermission
     *
     * @mbg.generated Wed Dec 20 16:45:11 CST 2023
     */
    List<SaOppermission> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sa_oppermission
     *
     * @mbg.generated Wed Dec 20 16:45:11 CST 2023
     */
    int updateByPrimaryKey(SaOppermission row);
    
    List<SaOppermission> selectPermissionByPerson(Map<String, String> param);
}