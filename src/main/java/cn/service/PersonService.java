package cn.service;

import cn.dto.PersonExecution;
import cn.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PersonService接口
 */

public interface PersonService {

    PersonExecution addPerson(Person person);

    PersonExecution login(Person person);

    Person getPersonById(Integer userId);

    boolean updatePerson(Person person);
}
