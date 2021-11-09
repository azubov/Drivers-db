package ru.lanit.driversdb.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountryFilter extends OncePerRequestFilter {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017/COUNTRY?readPreference=primary";
    private static final String COUNTRY_REPLACEMENT = "COUNTRY";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String country = getDbName(request);

        if (country == null || country.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String dbConnectionString = CONNECTION_STRING.replace(COUNTRY_REPLACEMENT, country);
            ConnectionStorage.setConnection(dbConnectionString);
            filterChain.doFilter(request,response);
            ConnectionStorage.clear();
        }
    }

    private String getDbName(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String searchWord = "/db/";
        int startIndex = url.indexOf(searchWord) + 4;
        int endIndex = url.indexOf(searchWord) + 6;
        return url.substring(startIndex, endIndex);
    }
}