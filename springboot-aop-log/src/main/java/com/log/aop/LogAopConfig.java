package com.log.aop;

import com.alibaba.fastjson.JSON;
import com.log.annotation.SystemLog;
import com.log.util.IpAdrressUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author halo.l
 * @date 2019-12-26
 */
@Aspect
@Component
@Slf4j
public class LogAopConfig {

    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.log.annotation.SystemLog)")
    public void controllerAspect() {}

    /**
     * 操作异常记录
     * @param point
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        log.info("异常*************************************");
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param point 切点
     */
    @Around("controllerAspect()")
    public Object doController(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestParams = getRequestParams(point, request);

        log.info("成功*************************************" + requestParams);

        // 获取ip
        String ip = IpAdrressUtil.getIpAdrress(request);
        log.info("ip:" + ip);

        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        //获取类路径名：包名 + 类名
        String targetName = point.getTarget().getClass().getName();
        log.info("类路径:" + targetName);

        //获取类名
        String className = point.getTarget().getClass().getSimpleName();
        log.info("类名:" + className);

        //获取请求的方法名
        String methodName = methodSignature.getName();
        log.info("方法名:" + methodName);

        //获取请求方法的详情： public com.log.model.User com.log.controller.LogController.getOne(java.lang.Integer,java.lang.String)
        Method method = methodSignature.getMethod();
        log.info("方法详情:" + method);

        //通过method获取方法名
        String name = method.getName();
        log.info("通过method获取方法名:" + name);

        //通过方法所有的注解
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            log.info("方法的注解:" + annotation.getClass().getSimpleName());
        }

        //通过method对应的注解信息
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        log.info("注解属性module:" + systemLog.module());
        log.info("注解属性description:" + systemLog.description());

        //获取请求参数的类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            log.info("请求参数类型:" + parameterType.getSimpleName());
        }

        //获取请求参数的名称
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            //getType(): 返回包名 + 类名
            //getName(): 返回类名
            log.info("请求参数名称:" + parameter.getName());
        }

        //获取请求参数值: [123, 李四]
        String parameter = Arrays.toString(point.getArgs());
        log.info("请求参数值:" + parameter);

        //获取类中的所有方法： 包括Object中的方法
        /*Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            log.info("类中的方法名:" + method.getName());
        }*/
        return point.proceed();
    }

    /**
     * 入参数据， 查的方法， 发现不好用， 自己写了getRequestParams
     * @param joinPoint
     * @param request
     * @return
     */
    private String preHandle(ProceedingJoinPoint joinPoint,HttpServletRequest request) {
        String reqParam = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations = targetMethod.getAnnotations();
        for (Annotation annotation : annotations) {
            //此处可以改成自定义的注解
            if (annotation.annotationType().equals(RequestMapping.class)) {
                reqParam = JSON.toJSONString(request.getParameterMap());
                break;
            }
        }
        return reqParam;
    }

    /**
     * 返回数据, 对于返回数据， 很多场景都是封装的对象， 可以获取一个code或者msg作为是成功或失败
     * @param ret
     * @return
     */
    private String resultHandle(Object ret) {
        if(null == ret){
            return "";
        }
        return JSON.toJSONString(ret);
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public String getRequestParams(ProceedingJoinPoint point, HttpServletRequest request){
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        String queryString = null;
        String requestMethod = request.getMethod();
        if(requestMethod.equals(HttpMethod.POST.name())) {
            //参数值
            Object[] args = ArrayUtils.toArray(point.getArgs());
            queryString = JSON.toJSONString(args);
        }else if(requestMethod.equals(HttpMethod.GET.name())){
            //参数名
            Parameter[] parameters = method.getParameters();
            //参数值
            Object[] args = ArrayUtils.toArray(point.getArgs());
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                map.put(parameters[i].getName() , args[i]);
            }
            queryString = JSON.toJSONString(map);
        }
        return queryString;
    }

}