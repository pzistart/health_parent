package com.itheima.constant;

/**
 * @author Pzi
 * @create 2022-08-02 16:27
 */
public enum RedisConstant {
    //由于三个常量都是本类对象,直接往括号中写值,就相当于利用有参构造赋值
    SETMEAL_PIC_RESOURCES("setmealPciResources"),
    SETMEAL_PIC_DB_RESOURCES("setmealPicDbResources");

    //定义一个空参构造
    private RedisConstant() {

    }

    private String redisConstant;

    private RedisConstant(String redisConstant) {
        this.redisConstant = redisConstant;
    }

    public String getRedisConstant() {
        return redisConstant;
    }
}
