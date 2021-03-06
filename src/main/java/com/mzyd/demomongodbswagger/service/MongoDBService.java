package com.mzyd.demomongodbswagger.service;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * 继承springboot data 的 mongotemplate类
 *
 * @author 李奕@megvii.com
 * @date 2021-06-22 10:26
 */
@Service
public class MongoDBService extends MongoTemplate {


    public MongoDBService(MongoDatabaseFactory mongoDbFactory) {
        super(mongoDbFactory);
    }
}
