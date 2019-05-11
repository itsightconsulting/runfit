package com.itsight.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import static com.itsight.util.Enums.ResponseCode.*;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomValidationException.class)
    public @ResponseBody ErrorResponse handlerCustomValidationException(CustomValidationException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<2;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ErrorResponse(ex.getMessage(), ex.getInternalCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SecCustomValidationException.class)
    public ModelAndView handlerSecCustomValidationException(SecCustomValidationException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<2;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ModelAndView(ViewConstant.P_ERROR404);
    }

    @ExceptionHandler(NumberFormatException.class)
    public @ResponseBody
    ResponseEntity<String> handlerNumberFormatoExcception(NumberFormatException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ResponseEntity<>(EX_NUMBER_FORMAT.get(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody
    ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ResponseEntity<>(EX_SQL_EXCEPTION.get(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLGrammarException.class)
    public @ResponseBody
    ResponseEntity<String> handlerSQLGrammarException(SQLGrammarException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ResponseEntity<>(EX_SQL_GRAMMAR_EXCEPTION.get(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public @ResponseBody
    String handleErrorByMaxUploadSizeExceededException(MultipartException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return EX_MAX_UPLOAD_SIZE.get();
    }

    @ExceptionHandler(MultipartException.class)
    public @ResponseBody
    String handleErrorByFileSizeLimitExceededException(MultipartException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return EX_MAX_SIZE_MULTIPART.get();
    }

    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody
    String handleErrorByNullPointerException(NullPointerException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return EX_NULL_POINTER.get();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public @ResponseBody
    String handleErrorByInvalidFhandlerormatException(InvalidFormatException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return EX_JACKSON_INVALID_FORMAT.get();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleErrorByGenericException(Exception ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ModelAndView(ViewConstant.ERROR404PARAMEXCEP);
    }
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody String handlerMethodArgumentNotValidException(HttpServletRequest req, Exception ex) {
        logger.warn(req.getRequestURL());
        logger.warn(ex.getMessage());
        return Utilitarios.customErrorResponse(Enums.ResponseCode.EX_VALIDATION_FAILED.get(), ex.getMessage());//;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public @ResponseBody String handlerArrayIndexOutOfBoundsException(HttpServletRequest req, Exception ex) {
        logger.warn(req.getRequestURL());
        logger.warn("Resolved exception caused by Handler execution: java.lang.ArrayIndexOutOfBoundsException: "+ex.getMessage());
        return EX_ARRAY_INDEX_OUT.get();
    }*/
}

