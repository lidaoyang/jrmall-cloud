package com.jrmall.cloud.common.web.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;


/**
 * 自定义Request
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/12 16:54
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders = new HashMap<>();
    private final Map<String, String[]> customParameters = new HashMap<>();

    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    public void addParameter(String name, String value) {
        customParameters.put(name, new String[]{value});
    }

    public void putAllParameters(Map<String, String[]> parameters) {
        customParameters.putAll(parameters);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>(customHeaders.keySet());
        Enumeration<String> originalNames = super.getHeaderNames();
        while (originalNames.hasMoreElements()) {
            names.add(originalNames.nextElement());
        }
        return Collections.enumeration(names);
    }

    @Override
    public String getParameter(String name) {
        if (customParameters.containsKey(name)) {
            return customParameters.get(name)[0];
        }
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        if (customParameters.containsKey(name)) {
            return customParameters.get(name);
        }
        return super.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new HashMap<>(super.getParameterMap());
        map.putAll(customParameters);
        return map;
    }
}