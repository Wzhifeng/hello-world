package cn.service;

import cn.BaseTest;
import cn.dto.PersonExecution;
import cn.entity.Person;
import cn.enums.PersonStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * PersonService 测试类
 */
public class PersonServiceTest extends BaseTest {
    @Autowired
    private PersonService personService;

    /**
     * 测试PersonService查询用户列表
     */
    @Test
    public void testQueryGetPersonList(){
//        List<Person> personList = personService.getPersonList();
//        System.out.println(personList.get(0).getUsername());
    }

    /**
     * 测试PersonService添加用户
     */
    @Test
    public void testAddPerson(){
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
        PersonExecution personExecution = personService.addPerson(person);
        System.out.println(personExecution.getState() == PersonStateEnum.SUCCESS.getState());
    }

    /**
     * 测试PersonService的登陆
     */
    @Test
    public void testLogin(){
        Person person = new Person();
        person.setAccount("admin");
        person.setPassword("admin");
        PersonExecution personExecution = personService.login(person);
        System.out.println(personExecution.getStateInfo());
        System.out.println(personExecution.getPerson());
    }
}
