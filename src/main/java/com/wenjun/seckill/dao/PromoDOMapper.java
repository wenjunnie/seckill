package com.wenjun.seckill.dao;

import com.wenjun.seckill.dataobject.PromoDO;
import org.springframework.stereotype.Component;

@Component
public interface PromoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    int insert(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    int insertSelective(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    PromoDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    int updateByPrimaryKeySelective(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Sun Dec 22 12:06:37 CST 2019
     */
    int updateByPrimaryKey(PromoDO record);

    PromoDO selectByItemId(Integer itemId);
}