package com.atguigu.crud.service.impl;

import com.atguigu.crud.mapper.EmpMapper;
import com.atguigu.crud.pojo.Emp;
import com.atguigu.crud.pojo.EmpExample;
import com.atguigu.crud.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EmpServiceImpl
 * @Description
 * @Author chenzongyang
 * @date: Created in 2022/6/9 17:32
 * @version: 1.0
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    EmpMapper empMapper;

    @Override
    public List<Emp> getAll() {
        EmpExample example = new EmpExample();
        //按员工id升序排列
        example.setOrderByClause("emp_id asc");
//        EmpExample.Criteria criteria = example.createCriteria();
//        criteria.andDIdEqualTo(1);
        return empMapper.selectByExampleWithDept(example);
    }

    @Override
    public void saveEmp(Emp emp) {
        empMapper.insertSelective(emp);
    }

    @Override
    public boolean checkEmpName(String empName) {
        EmpExample example = new EmpExample();
        EmpExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        int count = empMapper.countByExample(example);
        return count == 0;
    }

    @Override
    public Emp getEmp(Integer id) {
        return empMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Emp emp) {
        empMapper.updateByPrimaryKeySelective(emp);
    }

    @Override
    public void deleteEmp(Integer empId) {
        empMapper.deleteByPrimaryKey(empId);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        EmpExample example = new EmpExample();
        EmpExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        empMapper.deleteByExample(example);
    }
}
