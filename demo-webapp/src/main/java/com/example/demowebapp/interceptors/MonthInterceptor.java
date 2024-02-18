package com.example.demowebapp.interceptors;

import com.example.demowebapp.entities.Month;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MonthInterceptor implements HandlerInterceptor {

    private final static List<Month> months = new ArrayList<>(Arrays.asList(
            new Month(1, "January", "Gennaio", "Januar"),
            new Month(1, "February", "Febbraio", "Februar"),
            new Month(1, "March", "Marzo", "Marsch"),
            new Month(1, "April", "Aprile", "April"),
            new Month(1, "May", "Maggio", "Mai"),
            new Month(1, "June", "Giugno", "Jani")));
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURL().toString().contains("/months")) {
            String monthNumberString = request.getHeader("monthNumber");
            if(monthNumberString == null || monthNumberString.isEmpty()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            Integer monthNumber = Integer.parseInt(monthNumberString);
            Optional<Month> month = months.stream().filter(foundMonth->{
                return foundMonth.getMonthNumber() == monthNumber;
            }).findFirst();
            if(month.isPresent()){
                request.setAttribute("MonthInterceptor-getmonth", month.get());
            } else {
                Month monthNull = new Month(monthNumber,"nope","nope","nope");
                request.setAttribute("MonthInterceptor-getmonth", monthNull);
                //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
