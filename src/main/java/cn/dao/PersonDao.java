package cn.dao;

import cn.entity.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PersonDao接口
 */

public interface PersonDao {

    /**
     * 查询用户列表
     */
    List<Person> queryPersonList();

    /**
     * 新增用户
     *
     * @param person
     * @return
     */
    int insertPerson(@Param("person") Person person);


    /**
     * 通过密码与账号查询用户序号
     * @param person
     * @return 用户序号
     */
    int checkPersonByAccountAndPassword(@Param("person") Person person);

    /**
     * 通过序号查询用户
     * @param userId
     * @return
     */
    Person selectPersonById(Integer userId);

    int updatePerson(@Param("person") Person person);
}
