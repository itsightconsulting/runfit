package com.itsight.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.itsight.constants.ViewConstant;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import static com.itsight.util.Enums.ResponseCode.*;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NumberFormatException.class)
    public @ResponseBody
    String handlerNumberFormatoExcception(NumberFormatException ex) {
        ex.printStackTrace();
        return EX_NUMBER_FORMAT.get();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public @ResponseBody
    String handleErrorByMaxUploadSizeExceededException(MultipartException ex) {
        ex.printStackTrace();
        return EX_MAX_UPLOAD_SIZE.get();
    }

    @ExceptionHandler(MultipartException.class)
    public @ResponseBody
    String handleErrorByFileSizeLimitExceededException(MultipartException ex) {
        ex.printStackTrace();
        return EX_MAX_SIZE_MULTIPART.get();
    }

    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody
    String handleErrorByNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        return EX_NULL_POINTER.get();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public @ResponseBody
    String handleErrorByInvalidFormatException(InvalidFormatException ex) {
        System.out.println(ex.getMessage());
        return EX_JACKSON_INVALID_FORMAT.get();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleErrorByGenericException(Exception ex) {
        System.out.println(ex.getMessage());
        return new ModelAndView(ViewConstant.ERROR404PARAMEXCEP);
    }
}

