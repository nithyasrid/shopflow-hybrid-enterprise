package com.shopflow.controller;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestControllerAdvice
public class ExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) Map<String,String> bad(IllegalArgumentException e){return Map.of("error",e.getMessage());}
 @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class) @ResponseStatus(HttpStatus.FORBIDDEN) Map<String,String> forbidden(Exception e){return Map.of("error","Forbidden");}
 @ExceptionHandler(Exception.class) @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) Map<String,String> error(Exception e){return Map.of("error","Internal server error","detail",e.getMessage()==null?"":e.getMessage());}
}
