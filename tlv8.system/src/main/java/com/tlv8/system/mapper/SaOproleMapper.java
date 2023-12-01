package com.tlv8.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tlv8.system.pojo.SaOprole;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("system")
public interface SaOproleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SA_OPROLE
     *
     * @mbg.generated Fri Dec 01 10:00:32 CST 2023
     */
    int deleteByPrimaryKey(String sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SA_OPROLE
     *
     * @mbg.generated Fri Dec 01 10:00:32 CST 2023
     */
    int insert(SaOprole row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SA_OPROLE
     *
     * @mbg.generated Fri Dec 01 10:00:32 CST 2023
     */
    SaOprole selectByPrimaryKey(String sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SA_OPROLE
     *
     * @mbg.generated Fri Dec 01 10:00:32 CST 2023
     */
    List<SaOprole> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SA_OPROLE
     *
     * @mbg.generated Fri Dec 01 10:00:32 CST 2023
     */
    int updateByPrimaryKey(SaOprole row);
}