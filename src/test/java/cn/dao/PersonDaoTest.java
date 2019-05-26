package cn.dao;

import cn.BaseTest;
import cn.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * PersonDao 测试类
 */
public class PersonDaoTest extends BaseTest {
    @Autowired
    private PersonDao personDao;

    /**
     * 测试PersonDao查询用户列表
     */
    @Test
    public void testQueryPerson() {
        List<Person> personList = personDao.queryPersonList();
        System.out.println(personList.size());
    }


    /**
     * 测试PersonDao插入用户
     */
    @Test
    public void testInsertPerson(){
        Person person = new Person();
        person.setAccount("account1");
        person.setUsername("username1");
        person.setPassword("password1");
        person.setEmail("phone1");
        person.setPhone("3308388");
        person.setGender("男");
        person.setQuestion("question1");
        person.setAnswer("answer1");
        person.setUserType(1);

        int effectednum = personDao.insertPerson(person);
        System.out.println(effectednum);
    }

    /**
     * 测试PersonDao查询用户账号密码是否存在
     */
    @Test
    public void testCheckPersonByAccountAndPassword(){

        Person person = new Person();
        person.setAccount("admin");
        person.setPassword("admin");

        int userId = personDao.checkPersonByAccountAndPassword(person);
        System.out.println(userId);
    }

    /**
     * 测试PersonDao根据用户序号查询用户实体
     */
    @Test
    public void testSelectPersonById(){
        Person person = personDao.selectPersonById(1);
        System.out.println(person);
    }
}
