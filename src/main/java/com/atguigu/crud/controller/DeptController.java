package com.atguigu.crud.controller;

import com.atguigu.crud.pojo.Dept;
import com.atguigu.crud.pojo.Msg;
import com.atguigu.crud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName DeptController
 * @Description 处理和部门有关的请求
 * @Author chenzongyang
 * @date: Created in 2022/6/10 15:46
 * @version: 1.0
 */
@Controller
public class DeptController {

    @Autowired
    private DeptService deptService;

    /*
     返回所有的部门信息
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){
        //查出的所有部门信息
        List<Dept> list = deptService.getDepts();
        return Msg.success().add("depts",list);
    }
}
