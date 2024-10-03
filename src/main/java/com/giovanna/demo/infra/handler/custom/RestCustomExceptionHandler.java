package com.giovanna.demo.infra.handler.custom;

import com.giovanna.demo.dto.error.RestErrorRecordDto;
import com.giovanna.demo.infra.exception.product.NoProductsFoundException;
import com.giovanna.demo.infra.exception.product.ProductNotFoundException;
import com.giovanna.demo.infra.exception.role.NoRolesFoundException;
import com.giovanna.demo.infra.exception.role.RoleNotFoundException;
import com.giovanna.demo.infra.exception.stock.InviableStockOperationException;
import com.giovanna.demo.infra.exception.stock.NoStockOperationsFoundException;
import com.giovanna.demo.infra.exception.stock.StockOperationNotFoundException;
import com.giovanna.demo.infra.exception.store.StoreNotFoundException;
import com.giovanna.demo.infra.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestCustomExceptionHandler {
    @ExceptionHandler({NoProductsFoundException.class, ProductNotFoundException.class, NoRolesFoundException.class, RoleNotFoundException.class, NoStockOperationsFoundException.class, StockOperationNotFoundException.class, StoreNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<RestErrorRecordDto> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorRecordDto(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
    }

    @ExceptionHandler({UsernameAlreadyTakenException.class, EmailAlreadyTakenException.class})
    public ResponseEntity<RestErrorRecordDto> handleConflictException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestErrorRecordDto(HttpStatus.CONFLICT.toString(), ex.getMessage()));
    }

    @ExceptionHandler(InviableStockOperationException.class)
    public ResponseEntity<RestErrorRecordDto> handleBadRequestException(InviableStockOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorRecordDto(HttpStatus.CONFLICT.toString(), ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedCredentialsException.class)
    public ResponseEntity<RestErrorRecordDto> handleUnauthorizedException(UnauthorizedCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorRecordDto(HttpStatus.UNAUTHORIZED.toString(), ex.getMessage()));
    }
}
