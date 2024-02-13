package com.fastcampus.projectboardadmin.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class AdminErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(
            HttpServletRequest request,
            ModelMap model
            ) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("request", request);
        model.addAttribute("response", status);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/4xx";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/5xx";
            }
        }
        return "error";
    }
}
