package com.itheima.vo;

import java.io.Serializable;

/**
 * @author Pzi
 * @create 2022-08-14 11:23
 */
public class SetmealVo implements Serializable {

    private Integer value;

    private String  name;

    public SetmealVo() {
    }

    public SetmealVo(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SetmealVo{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
