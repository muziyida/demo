package com.mzyd.demomongodbswagger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 这里是User实体类
 *
 * @author 李奕@megvii.com
 * @date 2021-06-22 10:24
 */
@Data
@Document("User")
public class User {

    @Id
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String createDate;
}
