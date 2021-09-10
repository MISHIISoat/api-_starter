package com.assets_france.api.shared.helper.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SharedExceptionsHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<String> on(ConstraintViolationException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status, WebRequest request) {
//        Map<String, String> errorsMap = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errorsMap.put(fieldName, errorMessage);
//        });
//
//        String errors = getErrors(errorsMap);
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    private String getErrors(Map<String, String> errorsMap) {
//        try {
//            return errorsMap.isEmpty()
//                    ? ""
//                    : new ObjectMapper().writeValueAsString(errorsMap);
//        } catch (JsonProcessingException ignored) {
//        }
//
//        return "";
//    }
}
