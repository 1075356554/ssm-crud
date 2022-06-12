package com.atguigu.crud.controller;

import com.atguigu.crud.pojo.Emp;
import com.atguigu.crud.pojo.Msg;
import com.atguigu.crud.service.EmpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @ClassName EmpController
 * @Description 处理员工CRUD请求
 * @Author chenzongyang
 * @date: Created in 2022/6/9 17:29
 * @version: 1.0
 */
@Controller
public class EmpController {

    @Autowired
    EmpService empService;

    /**
     * @Author chenzongyang
     * @Description 单个删除和批量删除二合一
     *  批量删除：1-2-3
     *  单个删除：1
     * @Date 14:53 2022/6/11
     * @Param [empId]
     * @return com.atguigu.crud.pojo.Msg
     */
    @RequestMapping(value = "emp/{empIds}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmpById(@PathVariable("empIds") String empIds){
        if(empIds.contains("-")){//批量删除
            String[] str_empIds = empIds.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for(String str : str_empIds){
                del_ids.add(Integer.parseInt(str));
            }
            empService.deleteBatch(del_ids);
        }else{//单个删除
            empService.deleteEmp(Integer.parseInt(empIds));
        }
        return Msg.success();
    }

    /**
     * @Author chenzongyang
     * @Description 我们要能直接发送PUT之类的请求还要封装请求体中的数据
     *  配置上HttpPutFormContentFilter:
     *  它的作用：将请求体中的数据解析包装成一个map，request被重新包装，
     *  getParameter()被重写，从map中取数据
     * @Date 10:36 2022/6/11
     * @Param [emp, result]
     * @return com.atguigu.crud.pojo.Msg
     */
    @RequestMapping(value = "/emp", method= RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(@Valid Emp emp, BindingResult result){
        if(result.hasErrors()){
            //校验失败，返回失败，在模态框中显示校验失败的错误信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError error: fieldErrors){
                System.out.println("错误的字段名：" + error.getField());
                System.out.println("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);

        }else {
            empService.update(emp);
            return Msg.success();
        }
    }

    /**
     * @Author chenzongyang
     * @Description 根据id查询员工信息
     * @Date 9:22 2022/6/11
     * @Param [id]
     * @return com.atguigu.crud.pojo.Msg
     */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Emp emp = empService.getEmp(id);
        return Msg.success().add("emp",emp);
    }

    /**
     * @Author chenzongyang
     * @Description 检查用户名是否可用
     * @Date 17:42 2022/6/10
     * @Param [empName]
     * @return com.atguigu.crud.pojo.Msg
     */
    @RequestMapping("/checkEmpName")
    @ResponseBody
    public Msg checkEmpName(@RequestParam("empName") String empName){
        //先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        boolean matches = empName.matches(regx);
        if(matches){
            //数据库用户名重复校验，若不重复，则返回true
            boolean isUseFul = empService.checkEmpName(empName);
            if(isUseFul){
                return Msg.success();
            }
            //用户名重复
            return Msg.fail().add("va_msg","用户名不可用");
        }
        //用户名不合规
        return Msg.fail().add("va_msg",
                "用户名必须是6-16位数字和字母的组合或2-5位中文");
    }

    /**
     * @Author chenzongyang
     * @Description 员工保存
     *  1.支持JSR303校验
     *  2.导入Hibernate-Validator的依赖
     * @Date 8:00 2022/6/11
     * @Param [emp]
     * @return com.atguigu.crud.pojo.Msg
     */
    @RequestMapping(value = "/emp", method= RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Emp emp, BindingResult result){
        if(result.hasErrors()){
            //校验失败，返回失败，在模态框中显示校验失败的错误信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError error: fieldErrors){
                System.out.println("错误的字段名：" + error.getField());
                System.out.println("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);

        }else {
            empService.saveEmp(emp);
            return Msg.success();
        }
    }

    /**
     * @Author chenzongyang
     * @Description 导入jackson包以处理json数据
     * @Date 9:13 2022/6/10
     * @Param [pn]
     * @return com.github.pagehelper.PageInfo
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn){
        //这不是一个分页查询
        //引入PageHelper分页插件
        //在查询之前调用startPage,传入页码及每页数据量
        PageHelper.startPage(pn, 5);
        //startPage后的查询就是分页查询
        List<Emp> emps = empService.getAll();
        //使用PageInfo包装查询的结果，其中封装了详细的分页详细，包括数据和页码导航等
        PageInfo page = new PageInfo(emps,5);//5代表页码导航栏中显示5页

        return Msg.success().add("pageInfo",page);
    }

    /*
    查询员工数据(分页查询)
     */
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //这不是一个分页查询
        //引入PageHelper分页插件
        //在查询之前调用startPage,传入页码及每页数据量
        PageHelper.startPage(pn, 5);
        //startPage后的查询就是分页查询
        List<Emp> emps = empService.getAll();
        //使用PageInfo包装查询的结果，其中封装了详细的分页详细，包括数据和页码导航等
        PageInfo page = new PageInfo(emps,5);//5代表页码导航栏中显示5页
        model.addAttribute("pageInfo", page);
        return "list";
    }
}
