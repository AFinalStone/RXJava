package com.example.apprxbus.bean;

/**
 * Created by Administrator on 2017/12/1.
 */

public class School {
    private String name;
    private int age;

    public School(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
