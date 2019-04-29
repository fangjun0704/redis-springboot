package com.company.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

  private static final long serialVersionUID = -2470913076446177390L;
  /**
   * {
   *   "uId": 1,
   *   "name": "redisName",
   *   "testField": "redisTestField",
   *   "age": 23
   * }
   */
  private Integer uId;
  private String name;
  private Integer age;
  private String testField;
  /**
   *  注意这里不要使用 isStudent 这样的boolean字段。避免序列化/反序列化找不到该字段
   *  因为 isStudent 和 student 的 get set方法都是一样的
   */
  private boolean studentIs;
  private Date birthday;

}
