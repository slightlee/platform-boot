package com.demain.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper 层 选装件
 * 选装件位于 com.baomidou.mybatisplus.extension.injector.methods 包下
 * 需要配合 Sql 注入器使用 {@link com.demain.framework.mybatis.injector.MyLogicSqlInjector}
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    
    // 以下定义的 3 个 method，是mybatisPlus内置的选装件（升级到3.3之后才有的）
    
    /**
     * 批量新增数据
     * <p>
     * 注：官方说明
     * 批量新增数据,自选字段 insert
     * <p> 不同的数据库支持度不一样!!!  只在 mysql 下测试过!!!  只在 mysql 下测试过!!!  只在 mysql 下测试过!!! </p>
     * <p> 除了主键是 <strong> 数据库自增的未测试 </strong> 外理论上都可以使用!!! </p>
     * <p> 如果你使用自增有报错或主键值无法回写到entity,就不要跑来问为什么了,因为我也不知道!!! </p>
     * <p>
     * 自己的通用 mapper 如下使用:
     * <pre>
     * int insertBatchSomeColumn(List<T> entityList);
     * </pre>
     * </p>
     *
     * <li> 注意: 这是自选字段 insert !!,如果个别字段在 entity 里为 null 但是数据库中有配置默认值, insert 后数据库字段是为 null 而不是默认值 </li>
     *
     * <p>
     * 常用的  {@link Predicate}:
     * </p>
     *
     * <li> 例1: t -> !t.isLogicDelete() , 表示不要逻辑删除字段 </li>
     * <li> 例2: t -> !t.getProperty().equals("version") , 表示不要字段名为 version 的字段 </li>
     * <li> 例3: t -> t.getFieldFill() != FieldFill.UPDATE) , 表示不要填充策略为 UPDATE 的字段 </li>
     */
    int insertBatchSomeColumn(List<T> entityList);
    
    /**
     * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     *
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
    
    /**
     * 根据 id 逻辑删除数据,并带字段填充功能
     * <p>注意入参是 entity !!! ,如果字段没有自动填充,就只是单纯的逻辑删除</p>
     *
     * @deprecated 3.5.0 {@link com.baomidou.mybatisplus.core.injector.methods.DeleteById}
     */
    int deleteByIdWithFill(T entity);
    
}