package com.demain.server.test.admin.mapper;

import com.demain.server.admin.entity.PlatformRole;
import com.demain.server.admin.mapper.PlatformRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class PlatformRoleMapperTest {

    @Autowired
    PlatformRoleMapper roleMapper;

    /**
     * 测试单个数据插入
     */
    @Test
    void insertData() {
        PlatformRole platformRole = new PlatformRole();
        platformRole.setRoleName("test_01");
        roleMapper.insert(platformRole);
    }

    /**
     * 测试批量数据插入
     */
    @Test
    void inserttBatchData() {
        PlatformRole platformRole1 = new PlatformRole();
        platformRole1.setRoleName("test_02");
        PlatformRole platformRole2 = new PlatformRole();
        platformRole2.setRoleName("test_03");
        List<PlatformRole> list = Arrays.asList(platformRole1, platformRole2);
        roleMapper.insertBatchSomeColumn(list);
    }

}