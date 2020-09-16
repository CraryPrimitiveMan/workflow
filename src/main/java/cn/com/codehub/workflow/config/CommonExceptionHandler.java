package cn.com.codehub.workflow.config;

import cn.com.codehub.workflow.entity.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    public CommonExceptionHandler() {
    }

    @ExceptionHandler({CommonRuntimeException.class})
    public ResponseEntity<ResponseVO> handleRRException(CommonRuntimeException e) {
        log.error("{}","error_400", e);
        return new ResponseEntity<>(new ResponseVO(e.getCode(),e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseVO> handleException(RuntimeException e) {
        log.error("{}","error_500", e);
        String errorMsg="";
        return new ResponseEntity<>(new ResponseVO(1000,errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  /*  @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleException(Exception e) {
        log.error("{}","error_400", e);
        String errorMsg=e.getLocalizedMessage();
        return new ResponseEntity<>(new Response(1000
                ,errorMsg), HttpStatus.BAD_REQUEST);
    }*/
}