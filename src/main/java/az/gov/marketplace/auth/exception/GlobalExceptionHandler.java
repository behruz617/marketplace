package az.gov.marketplace.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>>handleIllegalArgument(IllegalArgumentException e){
        Map<String,Object>body=new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>>handleGeneral(Exception e){
        Map<String,Object>body=new HashMap<>();
        body.put("timestamp",Instant.now().toString());
        body.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error","General error");
        body.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);

    }

}
