package com.aniu.aspectjeasy;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ActivityAspect {

    private static final String TAG = ActivityAspect.class.getCanonicalName();

    @Around("execution( * com.aniu.aspectjeasy.MainActivity.onCreate(..))")
    public void aopActivityAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();

        Log.i("helloAOP", "aspect:::" + "------------>>>>>MainActivity.onCreate");


    }
}
