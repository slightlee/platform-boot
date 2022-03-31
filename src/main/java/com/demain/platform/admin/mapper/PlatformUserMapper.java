package com.demain.platform.admin.mapper;

import com.demain.platform.admin.entity.PlatformUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 明天
 * @since 2021-12-14
 */
public interface PlatformUserMapper extends BaseMapper<PlatformUser> {

    /**
     * 用户角色标识
     * @param userId 用户ID
     * @return list
     */
    List<String> roleKeyList(Long userId);
}
