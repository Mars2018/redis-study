package com.mars.redis.service.Impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.redis.dao.StudentMapper;
import com.mars.redis.model.Student;
import com.mars.redis.service.StuInfoService;
import com.mars.redis.utils.RandomUtil;
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

    /**
     * 向数据库中插入测试用例
     * @param num：插入测试用例数量
     * @return
     */
    @Override
    public Integer testAdd(Integer num) {
        if (num == null)
            num = 10;
        int res  = 0;
        for(int i = 0; i < num; ++i){
            Student stu = new Student();
            initStu(stu);
            if (studentMapper.selectByPrimaryKey(stu.getId()) == null) {
                if (studentMapper.insertSelective(stu) > 0) {
                    LOG.info("insert record: " + JSON.toJSONString(stu) + "  into test table");
                    res++;
                }
            }
        }
        return res;
    }

    private void initStu(Student stu) {
        stu.setId(Integer.parseInt(RandomUtil.generateNumberString(6)));
        stu.setName(RandomUtil.generateLetterString(6));
        stu.setAge(Integer.parseInt(RandomUtil.generateNumberString(2)));
        stu.setGrade(Integer.parseInt(RandomUtil.generateNumberString(1)));
        stu.setSex(stu.getId()%2 == 0 ? "female":"male");
    }

}
