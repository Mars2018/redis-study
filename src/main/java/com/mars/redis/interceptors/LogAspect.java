package com.mars.redis.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录APO实现
 * Created by mars on 16-9-29.
 */
@Aspect
public class LogAspect {

    private final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private String requestUrl = null;//请求地址
    private String userName = null;//用户名
    private Map<?, ?> inputParam = null;//传入参数
    private Map<String, java.lang.Object> outputParam = null;//传入参数
    private long startTimeMillis = 0; //开始时间
    private long endTimeMillis = 0; //结束时间


    /**
     * 方法调用之前触发，记录开始时间
     * @param joinPoint
     */
    @Before("execution(* com.mars.redis.controller..*.*(..))")
    public void doBeforeInserviceLayer(JoinPoint joinPoint){
        startTimeMillis = System.currentTimeMillis();
    }

    @After("execution(* com.mars.redis.controller..*.*(..))")
    public void doAfterInserviceLayer(JoinPoint joinPoint){
        endTimeMillis = System.currentTimeMillis();
        this.printLog();
    }

    @Around("execution(* com.mars.redis.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        inputParam = request.getParameterMap();
        requestUrl = request.getRemoteHost();
        outputParam = new HashMap<>();
        Object result = pjp.proceed();
        outputParam.put("result",result);
        return result;

    }

    private void printLog() {
        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        LOG.info("url:" + requestUrl + "; op_time:" + optTime +
                " pro_time:" + (endTimeMillis-startTimeMillis) + "ms ;");
    }
}
