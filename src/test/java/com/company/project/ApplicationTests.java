package com.company.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.company.project.entity.User;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  @Resource
  private RedisTemplate redisTemplate; //操作对象

  @Resource
  private StringRedisTemplate stringRedisTemplate; //操作字符串

  /**
   * redis常见五大数据类型
   *
   * String(字符串) List(列表) Set(集合) ZSet(有序集合) Hash(散列)
   */
  @Test
  public void testStringRedisTemplate() {
    stringRedisTemplate.opsForValue().set("string", "stringValue");//redis存值
    String str = stringRedisTemplate.opsForValue().get("str");//redis获取值

    stringRedisTemplate.opsForList().leftPush("list", "listValue");
    stringRedisTemplate.opsForSet().add("set", "setValue", "3", "2");
    stringRedisTemplate.opsForZSet().add("zset", "zvalue", 2.2);
    stringRedisTemplate.opsForHash().put("hash", "hashKey", "hashValue");
  }


  @Test
  public void testRedisTemplate() throws Exception {
    /**
     *  value是对象的时候，这里注意一定要将该对象实现 序列化接口，不然会报错
     *  运行发现：
     *  key：\xAC\xED\x00\x05t\x00\x09redisUser
     *  value：\xAC\xED\x00\x05sr\x00\x1Fcom.company.project.entity.UserG\xE4\xEEC\xD6\xCDL\xB9\x02\x00\x04L\x00\x03aget\x00\x13Ljava/lang/Integer;L\x00\x04namet\x00\x12Ljava/lang/String;L\x00\x09testFieldq\x00~\x00\x02L\x00\x03uIdq\x00~\x00\x01xpsr\x00\x11java.lang.Integer\x12\xE2\xA0\xA4\xF7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xAC\x95\x1D\x0B\x94\xE0\x8B\x02\x00\x00xp\x00\x00\x00\x17t\x00\x09redisNamet\x00\x0EredisTestFieldp
     *
     *  redis中的 key 和 value 并不是我们想要的结果。不直观
     *  原因是：使用了jdk序列化机制。 JdkSerializationRedisSerializer
     *
     *  我们想要的结果是 value 以 json 的方式保存。
     *  解决方案：
     *    1.自己将对象转换为json 比如使用 JSONObject.parseObject(JSON.toJSONString(user,SerializerFeature.WriteMapNullValue))
     *      //WriteMapNullValue代表将null值也写入(默认是去掉null值的)
     *    2.更改 RedisTemplate 默认的序列化规则
     *
     */

    /**
     * 此时存的是 value 是对象 user
     *
     * 注意：这里就一定要将 User 对象实现序列化接口 Serializable 。否则报错
     * 这里建议更改 redis 默认的序列化方式(JdkSerializationRedisSerializer).方便在 redis客户端查看
     */
    //    redisTemplate.opsForValue().set("redisUser",
    //        User.builder().name("redisName").age(23).testField("redisTestField").birthday(new Date()).build());
    User redisUser = JSON.parseObject(
        JSON.toJSONString(redisTemplate.opsForValue().get("redisUser23"),
            SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat),
        User.class);
    System.out.println(redisUser);

//    JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";//设置指定日期格式
    System.out.println(JSON.toJSONString(redisUser, SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteDateUseDateFormat));
  }

}
