package com.scut.originsystem.aspect;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.Result;
import com.scut.originsystem.entity.SystemLog;
import com.scut.originsystem.entity.User;
import com.scut.originsystem.enums.SystemLogType;
import com.scut.originsystem.mapper.UserMapper;
import com.scut.originsystem.service.SystemLogService;
import com.scut.originsystem.service.TokenSessionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class SystemLogAspect {
    @Autowired
    SystemLogService systemLogService;
    @Autowired
    TokenSessionService tokenSessionService;
    @Autowired
    UserMapper userMapper;

    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);

    @Pointcut("@annotation(com.scut.originsystem.annotation.SystemLogControllerAnno)")
    public void controllerAspect(){

    }

    @AfterReturning(pointcut = "controllerAspect()",returning = "result")
    public void afterRetrun(JoinPoint joinPoint,Object result){
        SystemLog systemLog = deal_system_log_helper(joinPoint);
        if(result instanceof Result){
            Result ret = (Result)result;
            systemLog.setLog_message(systemLog.getLog_message()+deal_result(ret));
        }
        systemLogService.insert(systemLog);
    }

    @AfterThrowing(pointcut = "controllerAspect()",throwing = "error")
    public void deal_system_log_throw(JoinPoint joinPoint, Throwable error) throws Throwable {
        SystemLog systemLog = deal_system_log_helper(joinPoint);
        systemLog.setLog_message(systemLog.getLog_message()+deal_result(error));
        systemLogService.insert(systemLog);
        throw error;
    }

    private String deal_result(Result ret){
        StringBuilder message = new StringBuilder("\n操作结果:");
        if(ret.getErrCode()==0){
            message.append("成功");
        }else{
            message.append("失败\n");
            message.append("原因:");
            message.append(ret.getMsg());
        }
        return message.toString();
    }

    private String deal_result(Throwable e){
        StringBuilder message = new StringBuilder("\n操作结果:");
        message.append("失败\n");
        message.append("原因:");
        message.append(e.getMessage());
        return message.toString();
    }


    private SystemLog deal_system_log_helper(JoinPoint joinPoint) {
        SystemLog systemLog = new SystemLog();
        try {
            systemLog.setLog_time(format.format(new Date()));

            SystemLogControllerAnno anno = getAnnotation(joinPoint);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            User user = null;
            if(anno==null)
                return systemLog;
            switch (anno.type()){
                case COMMON:{
                    user = getUser_common(request);
                    break;
                }
                case LOGIN:{
                    user = getUser_login(joinPoint);
                    break;
                }
                default:{
                    break;
                }
            }

            systemLog.setUsername(user.getUsername());
            systemLog.setRole(user.getRole());

            String ip = request.getRemoteAddr();
            systemLog.setIp(ip);
            StringBuilder message = new StringBuilder();

            //方法描述
            message.append(anno.description()+"\n");
//            if (anno.type()!= SystemLogType.LOGIN)
//                message.append("参数:"+getControllerMethodParams(joinPoint)+"\n");

            systemLog.setLog_message(message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return systemLog;
        }
    }

    /**
     * 获取common用户信息
     * @param request 请求
     * @return User
     */
    public User getUser_common(HttpServletRequest request){
        User user = null;
        try{
            user = new TokenSessionService().getUser(request);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (user==null){
                user = new User();
                user.setRole("MERCHANT");
                user.setUsername("访问时，token丢失");
            }
            return user;
        }
    }

    /**
     * 获取登录时用户信息
     * @param joinPoint 切点
     * @return User
     */
    public User getUser_login(JoinPoint joinPoint){
        User user = null;
        String username = "";
        try{
            Object[] arguments = joinPoint.getArgs();
            if (arguments.length!=0){
                Object object = arguments[0];
                if(object instanceof Map){
                    Map<String,Object> map = (Map<String,Object>)object;
                    if (map.containsKey("username")){
                        username = (String)map.get("username");
                        user = userMapper.findUser(username);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (user==null){
                user = new User();
                user.setRole("MERCHANT");
                user.setUsername("此用户不存在:"+username);
            }
            return user;
        }
    }

    /**
     * 获取当前方法的注解,用于Controller层记录
     * Class[] clazzs = method.getParameterTypes(); 可以用来获取参数的类型和长度
     */
    public static SystemLogControllerAnno getAnnotation(JoinPoint joinPoint){
        SystemLogControllerAnno anno = null;
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            Class targetClass = Class.forName(targetName);
            String methodName = joinPoint.getSignature().getName();
            Method[] methods = targetClass.getMethods();
            Object[] arguments = joinPoint.getArgs();

            for (Method method : methods) {
                if (method.getName().equals(methodName) &&
                        arguments.length==method.getParameterCount()) {
                    anno = method.getAnnotation(SystemLogControllerAnno.class);
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return anno;
        }
    }

    /**
     * 获取方法的参数信息 用于Controller层
     * @param joinPoint 切点
     * @return 方法参数描述
     */
    public  static String getControllerMethodParams(JoinPoint joinPoint) {
        StringBuilder description = new StringBuilder();
        try {
            Object[] arguments = joinPoint.getArgs();
            if(arguments.length!=0){
                description.append(":");
                for(Object object : arguments){
                    if(object instanceof Map){
                        description.append("{");
                        Map<String,Object> map = (Map<String,Object>)object;
                        for(Map.Entry<String,Object> entry:map.entrySet()){
                            description.append(entry.getKey());
                            description.append(":");
                            description.append(entry.getValue());
                            description.append(",");
                        }
                        description.append("},");
                    }else if(object instanceof List){
                        description.append("[");
                        List<Object> list = (List<Object>)object;
                        for(Object l: list){
                            description.append(l);
                            description.append(",");
                        }
                        description.append("],");
                    }else if(object instanceof HttpServletRequest || object instanceof HttpServletResponse){

                    }else if(object instanceof MultipartFile){
                        MultipartFile file = (MultipartFile)object;
                        description.append("file[name:"+file.getOriginalFilename()+",size:"+file.getSize()+"],");
                    }else{
                        description.append(object+",");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return description.toString();
        }
    }
}
