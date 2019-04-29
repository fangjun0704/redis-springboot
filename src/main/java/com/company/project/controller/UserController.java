package com.company.project.controller;

import com.alibaba.fastjson.JSON;
import com.company.project.entity.User;
import com.company.project.mapper.UserMapperXML;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

  @Resource
  private UserMapperXML userMapperXML;


  @Resource
  private RedisTemplate redisTemplate; //操作对象

  /**
   * 增加一个用户
   */
  @RequestMapping(value = "/insert")
  public User insertUser(User user) {
    //存入redis缓存
    user.setBirthday(new Date());
    redisTemplate.opsForValue().set("redisUser" + user.getUId(), user, 1, TimeUnit.HOURS);
    //存入数据库
    userMapperXML.insertUser(user);
    return user;
  }

  /**
   * 删除一个用户
   */
  //  @RequestMapping(value = "/delete/{pathId}")
  //  public String deleteUser(@PathVariable("pathId") Integer uId) {
  //    int i = userMapperXML.deleteUserById(uId);
  //    if (i != 0) {
  //      return "删除成功";
  //    } else {
  //      return "删除失败";
  //    }
  //  }

  /**
   * 更新一个用户
   */
  //  @RequestMapping(value = "/update/{pathId}")
  //  public User updatetUser(@PathVariable("pathId") Integer uId) {
  //    User user = userMapperXML.getUserById(uId);
  //    user.setName("名字" + new Random().nextInt(10));
  //    user.setAge(new Random().nextInt(100));
  //    user.setTestField(UUID.randomUUID().toString());
  //    userMapperXML.updateUser(user);
  //    return userMapperXML.getUserById(uId);
  //  }

  /**
   * 查询一个用户
   */
  @GetMapping(value = "/get/{pathId}")
  public User getUser(@PathVariable("pathId") Integer uId) {
    User redisResult = JSON
        .parseObject(JSON.toJSONString(redisTemplate.opsForValue().get("redisUser" + uId)),
            User.class);
    if (null != redisResult) {
      return redisResult;
    } else {
      return userMapperXML.getUserById(uId);
    }
  }

}
