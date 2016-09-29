package com.mars.redis.service.Impl;

import com.mars.redis.dao.StudentMapper;
import com.mars.redis.model.Student;
import com.mars.redis.service.StuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mars on 16-9-28.
 */
@Service(value = "stuInfoService")
public class StuInfoServiceImpl implements StuInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(StuInfoServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> getStuById(Integer id) {
        List<Student> studentList = new ArrayList<>();
        if (id == null)
            studentList.addAll(studentMapper.selectAllStudents());
        else
            studentList.add(studentMapper.selectByPrimaryKey(id));
        return studentList;
    }
}
