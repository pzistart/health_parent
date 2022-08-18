package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author Pzi
 * @create 2022-08-02 15:59
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
//        System.out.println("定时任务触发");
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        String redisConstant = RedisConstant.SETMEAL_PIC_RESOURCES.getRedisConstant();
        Set<String> set =
                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES.getRedisConstant(),
                        RedisConstant.SETMEAL_PIC_DB_RESOURCES.getRedisConstant());
        if (set != null) {
            for (String picName : set) {
                System.out.println("删除" + picName + "成功");
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().
                        srem(RedisConstant.SETMEAL_PIC_RESOURCES.getRedisConstant(), picName);
            }
        }
    }
}
