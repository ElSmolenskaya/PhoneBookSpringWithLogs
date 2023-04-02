package ru.academits.phonebook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.academits.model.ErrorInfo;

@ControllerAdvice
public class ErrorController {
    private final Logger logger = LogManager.getLogger(ErrorController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo processException(Exception e) {
        logger.error(e.getMessage());

        return new ErrorInfo(e.getMessage());
    }
}