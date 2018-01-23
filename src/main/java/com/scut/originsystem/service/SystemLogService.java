package com.scut.originsystem.service;

import com.scut.originsystem.entity.SystemLog;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.mapper.SystemLogMapper;
import com.scut.originsystem.mapper.UserMapper;
import com.scut.originsystem.service.TokenSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SystemLogService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public SystemLogMapper systemLogMapper;

    @Transactional
    public void insert(SystemLog systemLog){
        systemLogMapper.insertSystemLog(systemLog);
    }
}
