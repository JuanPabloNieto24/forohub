package com.kev.forohub.infra.errors;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.kev.forohub.helper.ResponseMessage;
import com.kev.forohub.helper.Type;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseMessage> error404(EntityNotFoundException e){
        var response = new ResponseMessage(Type.ERROR,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseMessage> error404(UsernameNotFoundException e){
        var response = new ResponseMessage(Type.ERROR,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ResponseMessage> errorAlgenerarToken(RuntimeException e){
        var response = new ResponseMessage(Type.ERROR,e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseMessage> runtimeExceptions(RuntimeException e){
        var response = new ResponseMessage(Type.ERROR,e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseMessage> runtimeExceptions(DataIntegrityViolationException e){
        var response = new ResponseMessage(Type.ERROR,e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }



}
