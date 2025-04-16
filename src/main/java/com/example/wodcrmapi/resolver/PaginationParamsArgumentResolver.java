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

        int page = Integer.parseInt(webRequest.getParameter("page") != null ?
                webRequest.getParameter("page") : annotation.pageDefault());

        int size = Integer.parseInt(webRequest.getParameter("size") != null ?
                webRequest.getParameter("size") : annotation.sizeDefault());

        String sortBy = webRequest.getParameter("sortBy") != null ?
                webRequest.getParameter("sortBy") : annotation.sortByDefault();

        String sortDirection = webRequest.getParameter("sortDirection") != null ?
                webRequest.getParameter("sortDirection") : annotation.sortDirectionDefault();

        return new PaginationRequest(page, size, sortBy, sortDirection);
    }
}
