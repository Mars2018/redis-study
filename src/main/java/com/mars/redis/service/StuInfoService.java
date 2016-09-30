package com.mars.redis.service;

import com.mars.redis.model.Student;

import java.util.List;

/**
 * Created by mars on 16-9-28.
 */
public interface StuInfoService {


    List<Student> getStuById(Integer id);

    Integer testAdd(Integer num);
}
