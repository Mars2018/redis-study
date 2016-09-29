package com.mars.redis.controller;

import com.mars.redis.model.Student;
import com.mars.redis.service.StuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mars on 16-9-28.
 */
@Controller
@RequestMapping(value = "/stu")
public class StuSvcController {
    private static final Logger  LOGGER = LoggerFactory.getLogger(StuSvcController.class);

    @Autowired
    private StuInfoService stuInfoService;

    @RequestMapping(value = "/get", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> queryStus(@RequestParam(value = "id", required = false) Integer id){
        return stuInfoService.getStuById(id);
    }
}
