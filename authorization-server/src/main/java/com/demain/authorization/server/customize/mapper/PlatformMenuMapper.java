package com.demain.authorization.server.customize.mapper;

import com.demain.authorization.server.customize.entity.PlatformMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author demain_lee
 * @since 2024-12-23
 */
public interface PlatformMenuMapper extends BaseMapper<PlatformMenu> {
    
    /**
     * 菜单列表
     *
     * @param userId 用户ID
     * @return list
     */
    @Select("SELECT\n" +
            "            m.*\n" +
            "        FROM\n" +
            "            platform_menu m\n" +
            "                LEFT JOIN platform_role_menu rm ON m.id = rm.menu_id\n" +
            "                LEFT JOIN platform_user_role ur ON rm.role_id = ur.role_id\n" +
            "        WHERE\n" +
            "            ur.user_id = #{userId}")
    List<PlatformMenu> menuListByUserId(@Param("userId") Long userId);
}
