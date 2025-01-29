package com.wia.implementation.handler;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.wia.implementation.util.LeptonApiUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class PerformanceInterceptor {

    private static final String MSG_LOG = "Execution: {}.{} -> Elapsed Time: ({})";

    @SuppressWarnings("deprecation")
    @Around("@annotation(com.wia.implementation.annotation.PerformanceLog)")
    public Object performanceMetric(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
	var stw = StopWatch.createStarted();
	var className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
	var method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
	Object retorno = proceedingJoinPoint.proceed();
	stw.stop();
	log.info(MSG_LOG, className, method.getName(), LeptonApiUtil.tempoDecorrido(stw.getTime()));
	return retorno;
    }
}
