package com.example.lib.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标记观察者的方法
 *
 *
 * Created by liuguangli on 17/3/9.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Succ {//这个接口名，就是要用的注解名，使用方式：@Succ
}
