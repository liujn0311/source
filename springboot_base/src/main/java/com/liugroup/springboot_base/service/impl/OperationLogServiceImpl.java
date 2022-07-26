package com.liugroup.springboot_base.service.impl;

import com.liugroup.springboot_base.mapper.OperationLogMapper;
import com.liugroup.springboot_base.pojo.OperationLog;
import com.liugroup.springboot_base.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    public static final Logger LOGGER = LoggerFactory.getLogger(OperationLogServiceImpl.class);

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public void insert(OperationLog operlog) {
        LOGGER.info("插入操作日志表");
        operationLogMapper.insert(operlog);
    }
}
