package com.liugroup.springboot_base.service.impl;

import com.liugroup.springboot_base.mapper.ExceptionLogMapper;
import com.liugroup.springboot_base.pojo.ExceptionLog;
import com.liugroup.springboot_base.service.ExceptionLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionLogServiceImpl.class);

    @Resource
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public void insert(ExceptionLog excepLog) {
        LOGGER.info("插入异常日志表");
        exceptionLogMapper.insert(excepLog);
    }
}
