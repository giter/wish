package com.wish.dao

import com.wish.bean.RoleBean
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 *
 * create by ff on 2018/7/26
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration("classpath:spring/spring-*.xml")
class RoleDAOTest extends GroovyTestCase {

    @Resource
    private RoleDAO roleDAO;


    @Test
    void testFindById() {
        RoleBean roleBean = roleDAO.findById(1);
        println (roleBean);
    }

    void testFindAll() {
    }

    void testQuery() {
    }

    void testSave() {
    }

    void testUpdate() {
    }

    void testDeletes() {
    }
}
