package com.tzh.newspring;

import cn.hutool.core.bean.BeanException;
import com.sun.istack.internal.Nullable;

public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException{
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException{
        return bean;
    }
}
