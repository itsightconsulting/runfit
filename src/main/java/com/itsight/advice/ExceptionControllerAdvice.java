package com.itsight.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
        return new ModelAndView(ViewConstant.MAIN_INF_P, "msg", ex.getMessage());
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody ErrorResponse handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ConstraintViolationException exx = (ConstraintViolationException) ex.getCause();
        LOGGER.warn(ex.getMostSpecificCause());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        String sqlStateCode = exx.getSQLException().getSQLState();
        if(!sqlStateCode.equals("23505")){
            return new ErrorResponse(EX_SQL_EXCEPTION.get(), sqlStateCode);
        }else{
            return new ErrorResponse("No puede insertar nombres ya registrados", sqlStateCode);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLGrammarException.class)
    public @ResponseBody
    ResponseEntity<String> handlerSQLGrammarException(SQLGrammarException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ResponseEntity<>(EX_SQL_GRAMMAR_EXCEPTION.get(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public @ResponseBody ErrorResponse handleErrorByMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ErrorResponse("1 El archivo que ha intentado subir excede al límite permitido, por favor suba un archivo menor a.... 1", EX_MAX_UPLOAD_SIZE.get());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public @ResponseBody
    ErrorResponse handleErrorByFileSizeLimitExceededException(MultipartException ex) {
        LOGGER.warn(ex.getMessage());
        for(int i = 0; i<10;i++){
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ErrorResponse("2 El archivo que ha intentado subir excede al límite permitido, por favor suba un archivo menor a.... 3", EX_MAX_SIZE_MULTIPART.get());
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
    public ModelAndView handleErrorByGenericException(MissingServletRequestParameterException ex) {
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

