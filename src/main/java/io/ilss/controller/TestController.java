package io.ilss.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.ilss.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : feng
 * @description: DruidStatController
 * @date : 2019-05-13 11:42
 * @version: : 1.0
 */
@RestController
public class TestController {

    @Autowired
    UserDAO userDAO;

    @GetMapping("/druid/stat")
    public Object druidStat(){
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping("/list/user")
    public Object listUser() {
        return userDAO.listAll();
    }
}