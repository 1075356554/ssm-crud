package com.atguigu.crud.service;

import com.atguigu.crud.pojo.Emp;

import java.util.List;

/**
 * @ClassName EmpService
 * @Description
 * @Author chenzongyang
 * @date: Created in 2022/6/9 17:31
 * @version: 1.0
 */
public interface EmpService {

    /*
    查询所有员工
     */
    List<Emp> getAll();

    /*
    员工保存
     */
    void saveEmp(Emp emp);

    /*
    检验用户名是否可用
    返回值为true，代表姓名可用  false代表不可用
     */
    boolean checkEmpName(String empName);

    /*
    按照员工id查询员工
     */
    Emp getEmp(Integer id);

    /*
    员工更新
     */
    void update(Emp emp);

    /*
    删除员工
     */
    void deleteEmp(Integer empId);

    /*
    批量删除员工
     */
    void deleteBatch(List<Integer> ids);
}
