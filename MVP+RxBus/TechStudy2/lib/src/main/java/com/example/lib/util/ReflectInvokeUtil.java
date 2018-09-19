package com.example.lib.util;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectInvokeUtil {
    private static final String TAG = "ReflectInvokeUtilTag";

    /**
     * 反射调用我指定的某个注解方法
     *
     * @param myAnnotation 我预设的注解方法
     * @param target       执行反射调用的对象
     * @param data         反射调用时传递的实参
     */
    public static void callMethodByAnnotation(Class myAnnotation, Object target, Object data) throws InvocationTargetException, IllegalAccessException {
        Method[] methodArray = target.getClass().getDeclaredMethods();//找到目标类的所有方法
        //注意解析
        for (int i = 0; i < methodArray.length; i++) {
            //找到我预设的那个方法
            boolean flag1 = methodArray[i].isAnnotationPresent(myAnnotation);//使用className找到我要的那個方法
            Class[] paraTypes = methodArray[i].getParameterTypes();//取参数数组
            if (paraTypes.length == 0) continue;//参数都没得，你拿什么去取[0]。

            Class paramType = paraTypes[0];// 不只是方法名字要一样，
            boolean flag2 = data.getClass().getName().equals(paramType.getName());//参数类型也要一样

            if (flag1 && flag2) {//两个条件同时满足
                methodArray[i].invoke(target, new Object[]{data});//才去执行目标对象的指定方法,使用data作为实参
                return;
            }

        }
    }
}
