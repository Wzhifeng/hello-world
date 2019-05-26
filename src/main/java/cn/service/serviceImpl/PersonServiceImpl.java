package cn.service.serviceImpl;


import cn.dao.PersonDao;
import cn.dto.PersonExecution;
import cn.entity.Person;
import cn.enums.PersonStateEnum;
import cn.exceptions.PersonException;
import cn.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * PersonService实现类
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    /**
     * 添加用户
     *
     * @param person
     * @return
     */
    @Override
    public PersonExecution addPerson(Person person) {

        //由于前端传来的是数组1/2，其中1为男，2为女
        if (person.getGender().equals("1")) {
            person.setGender("男");
        } else {
            person.setGender("女");
        }

        try {
            int effectedNum = personDao.insertPerson(person);

            if (effectedNum <= 0) {
                //添加用户操作失败
                return new PersonExecution(PersonStateEnum.ADD_PERSON_FAIL);
            } else {
                //添加用户操作成功
                return new PersonExecution(PersonStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new PersonException(e.toString());
        }
    }

    /**
     * 登陆用户
     *
     * @param person
     * @return
     */
    @Override
    public PersonExecution login(Person person) {
        //检查数用户的账号与密码是否一致
        int userId = personDao.checkPersonByAccountAndPassword(person);

        if (userId <= 0) {
            return new PersonExecution(PersonStateEnum.LOGIN_FAIL);
        } else {
            //查取用户信息
            person = personDao.selectPersonById(userId);

            //将密码置空，避免将密码暴露与前端
            person.setPassword("");
            PersonExecution personExecution = new PersonExecution(PersonStateEnum.SUCCESS, person);
            return personExecution;
        }
    }

    /**
     * 根据用户序号返回用户信息
     * @param userId
     * @return
     */
    @Override
    public Person getPersonById(Integer userId) {
        return personDao.selectPersonById(userId);
    }

    /**
     * 更新用户
     * @param person
     * @return
     */
    @Override
    public boolean updatePerson(Person person) {
        if (person.getPassword().equals("")) {
            person.setPassword(null);
        }

        person.setUpdateTime(new Date());

        int effectedNum = personDao.updatePerson(person);
        if (effectedNum > 0) {
            return true;
        }
        return false;
    }
}
