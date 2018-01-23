package com.scut.originsystem.config;

import com.scut.originsystem.enums.ErrorCode;
import com.scut.originsystem.util.ResultUtil;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidHandler(HttpServletRequest request,
                                                MethodArgumentNotValidException exception) throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        for(ObjectError e:exception.getBindingResult().getAllErrors()){
            if(e instanceof FieldError){
                FieldError fieldError = (FieldError)e;
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
        }
        return ResultUtil.resultBadReturner(ErrorCode.PARAM_ERR_COMMON.getMsg(), map);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Object NullPointerExceptionHandler(HttpServletRequest request,
                                              NullPointerException exception) throws Exception{
        exception.printStackTrace();
        return ResultUtil.resultBadReturner(exception.getClass().getCanonicalName());
    }

    @ExceptionHandler(value = Exception.class)
    public Object ExceptionHandler(HttpServletRequest request,
                                   Exception exception) throws Exception{
        exception.printStackTrace();
        return ResultUtil.resultBadReturner(exception.getMessage());
    }
}
