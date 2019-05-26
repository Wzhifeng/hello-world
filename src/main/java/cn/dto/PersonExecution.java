package cn.dto;

import cn.entity.Person;
import cn.enums.PersonStateEnum;

import java.util.List;

public class PersonExecution {
    //状态码
    private int state;

    //状态标识
    private String stateInfo;

    //操作的Person
    private Person person;

    //操作的Person列表
    private List<Person> personList;

    public PersonExecution() {
    }

    //以下为各个状态的构造器
    public PersonExecution(PersonStateEnum personStateEnum) {
        this.state = personStateEnum.getState();
        this.stateInfo = personStateEnum.getStateInfo();
    }

    public PersonExecution(PersonStateEnum personStateEnum, Person person) {
        this.state = personStateEnum.getState();
        this.stateInfo = personStateEnum.getStateInfo();
        this.person = person;
    }

    public PersonExecution(PersonStateEnum personStateEnum, List<Person> personList) {
        this.state = personStateEnum.getState();
        this.stateInfo = personStateEnum.getStateInfo();
        this.personList = personList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
