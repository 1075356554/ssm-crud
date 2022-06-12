import com.atguigu.crud.mapper.DeptMapper;
import com.atguigu.crud.mapper.EmpMapper;
import com.atguigu.crud.pojo.Emp;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @ClassName MapperTest
 * @Description 测试持久层的工作 使用spring的单元测试
 * 1、导入springTest模块
 * 2、@ContextConfiguration指定Spring配置文件的位置
 * 3、直接Autowired要使用的组件即可
 * @Author chenzongyang
 * @date: Created in 2022/6/9 16:22
 * @version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest  {

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    EmpMapper empMapper;

    @Autowired
    SqlSession sqlSession;

    /*
    *  测试DeptMapper
    *
    * */
    @Test
    public void testGRUD(){
       /* //1.创建SpringIOC容器
         ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从容器中获取mapper
        DeptMapper bean = ioc.getBean(DeptMapper.class);*/
//        System.out.println(deptMapper);

        //1、插入几个部门
//        deptMapper.insertSelective(new Dept(null,"开发部"));
//        deptMapper.insertSelective(new Dept(null,"测试部"));

        //2、插入员工数据
//        empMapper.insertSelective(new Emp(null,"Jerry","M","Jerry@atguigu.com",1));

        //3、批量插入多个员工：批量，使用执行批量操作的sqlSession
       /* for(){
            empMapper.insertSelective(new Emp(null,"Jerry","M","Jerry@atguigu.com",1));
        }*/
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        for(int i = 0; i < 1000; i++){
            String uuid = UUID.randomUUID().toString().substring(0,5) + i;
            mapper.insertSelective(new Emp(null,uuid,"M",uuid + "@atguigu.com",1));
        }
        System.out.println("批量完成");

    }
}
