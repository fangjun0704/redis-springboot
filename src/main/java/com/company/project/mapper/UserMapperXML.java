package com.company.project.mapper;


import com.company.project.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 这是操作数据库的mapper
 * 如果Mapper类特别多的话 可以省略这个注解，但是一定需要在 Application 中使用@MapperScan代替，否则mapper接口无效
 */
@Mapper
public interface UserMapperXML {

  void insertUser(User user);

  int deleteUserById(Integer uId);

  void updateUser(User user);

  User getUserById(Integer uId);

}
