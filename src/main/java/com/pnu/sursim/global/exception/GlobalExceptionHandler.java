package com.pnu.sursim.global.exception;

import com.pnu.sursim.global.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGeneralException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.fail(exception));
    }

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleGeneralException(CustomException customException) {
        return ResponseEntity.status(customException.getHttpStatus())
                .body(CustomResponse.fail(customException));
    }
}
