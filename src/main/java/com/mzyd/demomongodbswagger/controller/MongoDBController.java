package com.mzyd.demomongodbswagger.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mzyd.demomongodbswagger.model.User;
import com.mzyd.demomongodbswagger.service.MongoDBService;
import io.swagger.annotations.Api;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * mongodb controller类
 *
 * @author 李奕@megvii.com
 * @date 2021-06-22 10:29
 */
@RestController
@Api(tags = "mongodb增删改查")
public class MongoDBController {
    
    @Autowired
    private MongoDBService mongoDBService;


    /**
     * 增加用户
     * @param user
     */
    @PostMapping("insertUser")
    public void insert(@RequestBody User user){
        User insert = mongoDBService.insert(user);

        System.out.println(insert);
    }


    /**
     * 根据id删除
     * @param id
     */
    @DeleteMapping("deleteUserById/{id}")
    public void delete(@PathVariable String id){

        Query query=new Query(Criteria.where("id").is(id));
        DeleteResult remove = mongoDBService.remove(query, User.class);

        System.out.println(remove);
    }


    /**
     * 根据id查询并更新用户
     * @param id
     * @param user
     */
    @PostMapping("updateUser/{id}")
    public void update(@PathVariable String id,@RequestBody User user){

        Query query=new Query(Criteria.where("id").is(id));

        Update updateUser=new Update();
        //updateUser.set("id",user.getId());
        updateUser.set("name",user.getName());
        updateUser.set("age",user.getAge());
        updateUser.set("email",user.getEmail());
        updateUser.set("createDate",user.getCreateDate());


        UpdateResult updateResult = mongoDBService.updateFirst(query, updateUser, User.class);
        System.out.println(updateResult);
    }


    /**
     * 查找所有
     * @return
     */
    @GetMapping("findAll")
    public List findAll(){
        List<User> all = mongoDBService.findAll(User.class);


        return all;
    }

    /**
     * 根据id查询
     * @param id
     */
    @GetMapping("find/{id}")
    public void find(@PathVariable String id){
        User byId = mongoDBService.findById(id, User.class);
        System.out.println(byId);
    }

    /**
     * 根据姓名和年龄条件查找
     * @param name
     * @param age
     * @return
     */
    @GetMapping("find/{name}/{age}")
    public List findUserList(@PathVariable String name,@PathVariable Integer age){

        Query query=new Query();
        query.addCriteria(Criteria.where("name").is(name).and("age").is(age));

        List<User> users = mongoDBService.find(query, User.class);
        return users;
    }


    /**
     * 根据名字删除
     * @param name
     */
    @DeleteMapping("delete/{name}")
    public void deleteByName(@PathVariable String name){

        Query query=new Query();
        query.addCriteria(Criteria.where("name").is(name));

        mongoDBService.remove(query,User.class);
    }


    /**
     * 根据条件模糊查询，分页显示
     * @param condition
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("findRegex/{condition}/{page}/{limit}")
    public Map<String, Object> findRegex(@PathVariable String condition,
                          @PathVariable int page,
                          @PathVariable int limit){
        String name = condition;
        int pageNo = page;
        int pageSize = limit;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoDBService.count(query, User.class);
        List<User> userList = mongoDBService.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount",totalCount);
        System.out.println(pageMap);

        return pageMap;


    }
    

}
