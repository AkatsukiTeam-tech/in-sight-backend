package com.app.insight.util;

import com.app.insight.web.rest.errors.ApiErrors;
import com.app.insight.web.rest.errors.ErrorDto;
import java.util.Locale;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String getLocalizedMessage(MessageSource messageSource, String message) {
        return getLocalizedMessage(messageSource, message, LocaleContextHolder.getLocale());
    }

    public static String getLocalizedMessage(MessageSource messageSource, String message, Locale locale) {
        return messageSource.getMessage(message, null, locale);
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static ErrorDto creatErrorDto(ApiErrors apiErrors, String errorDetail) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(apiErrors.getCode());
        errorDto.setDetail(errorDetail != null ? errorDetail : apiErrors.getDefaultDescription());
        errorDto.setStatus(apiErrors.getStatus());

        return errorDto;
    }
}
