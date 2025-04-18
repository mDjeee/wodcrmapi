package com.example.wodcrmapi.resolver;

import com.example.wodcrmapi.aop.PaginationParams;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PaginationParamsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PaginationParams.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        PaginationParams annotation = parameter.getParameterAnnotation(PaginationParams.class);


        int page = parseWithDefault(webRequest.getParameter("page"), annotation.pageDefault());
        int size = parseWithDefault(webRequest.getParameter("size"), annotation.sizeDefault());
        String sortBy = getWithDefault(webRequest.getParameter("sortBy"), annotation.sortByDefault());
        String sortDirection = getWithDefault(webRequest.getParameter("sortDirection"), annotation.sortDirectionDefault());
        String search = webRequest.getParameter("search");

        return new PaginationRequest(page, size, sortBy, sortDirection, search);
    }

    private int parseWithDefault(String value, int defaultValue) {
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    private String getWithDefault(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }
}
