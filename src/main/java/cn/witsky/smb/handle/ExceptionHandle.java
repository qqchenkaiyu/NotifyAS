package cn.witsky.smb.handle;

import cn.witsky.smb.Constant.Field;
import cn.witsky.smb.util.AsUtil;
import cn.witsky.smb.exception.QueryException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e) {
        StringBuilder err = new StringBuilder();
        err.append("\t err    " +  e.getMessage()+"\n");
        for (StackTraceElement traceElement : e.getStackTrace())
            err.append("\tat " + traceElement+"\n");
        logger.error(err.toString());
        if(e instanceof QueryException){
           // QueryException
            JSONObject Data = new JSONObject();
            Data.put(Field.CODE, "1");
            Data.put(Field.MESSAGE, e.getMessage());
            Data.put(Field.SUCCESS, false);
            return  Data;
        }
       return AsUtil.getExceptionHandleRes(e.getMessage());
    }
}
