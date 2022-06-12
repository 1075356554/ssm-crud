package com.atguigu.crud.service.impl;

import com.atguigu.crud.mapper.DeptMapper;
import com.atguigu.crud.pojo.Dept;
import com.atguigu.crud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DeptServiceImpl
 * @Description
 * @Author chenzongyang
 * @date: Created in 2022/6/10 15:48
 * @version: 1.0
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> getDepts() {
        List<Dept> depts = deptMapper.selectByExample(null);
        return depts;
    }
}
