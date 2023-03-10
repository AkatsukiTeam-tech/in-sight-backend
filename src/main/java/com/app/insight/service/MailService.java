package com.app.insight.service;

import com.app.insight.domain.User;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final String LOCALE = LocaleContextHolder.getLocale().getLanguage();

    private static final String USER = "user";
    private String BASE_URL;

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendAdminPasswordMail(AppUserDTO user, String adminPassword) {
        sendEmail(user.getEmail(), Utils.getLocalizedMessage(messageSource, "email.send.admin.password.header"),
            toTemplate(
                LOCALE,
                Utils.getLocalizedMessage(messageSource, "email.send.admin.password.title"),
                MessageFormat.format(Utils.getLocalizedMessage(messageSource, "email.send.admin.password.body"), user.getLogin(), adminPassword)
            ), false, true);
    }

    @Async
    public void sendCreationEmail(AppUserDTO user, String userPassword) {
        sendEmail(user.getEmail(), Utils.getLocalizedMessage(messageSource, "email.create.account.header"),
            toTemplate(
                LOCALE,
                Utils.getLocalizedMessage(messageSource, "email.create.account.title"),
                MessageFormat.format(Utils.getLocalizedMessage(messageSource, "email.create.account.body"), user.getLogin(), userPassword)
            ),
            false, true);
    }

    @ReadOnlyProperty
    private String toTemplate(String locale, String title, String body) {
        String template = "<!DOCTYPE html>\n\n<html lang=\"" +
            locale + "\">\n\n  <head>\n\n" +
            title + "\n\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n\n  <link rel=\"icon\" href=\"http://127.0.0.1:8080/favicon.ico\" />\n\n  </head>\n\n  <body>\n\n" +
            body + "\n\n</body>\n\n</html>";
        log.debug("toTemplate '{}'", template);
        return template;
    }
}
