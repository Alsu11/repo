package ru.itis.filters;

import ru.itis.dto.UserDto;
import ru.itis.excptions.ValidationException;
import ru.itis.services.SignInService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private SignInService signInService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        this.signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().startsWith("/resources")) {
            filterChain.doFilter(request, response);
            return;
        }

        // берем сессию у запроса
        // берем только существующую, если сессии не было - то вернет null
        HttpSession session = request.getSession(false);
        // флаг, аутентифицирован ли пользователь
        boolean isAuthenticated = false;
        // существует ли сессия вообще?
        boolean sessionExists = session != null;
        // идет ли запрос на страницу входа или регистрации?
        boolean isRequestOnAuthPage = request.getRequestURI().equals("/sign-in") ||
                request.getRequestURI().equals("/sign-up");

        // если сессия есть
        if (sessionExists) {
            // проверим, есть ли атрибует user?
            isAuthenticated = session.getAttribute("user") != null;
        }

        if(!isAuthenticated) {
            Optional<Cookie> optionalEmailCookie = Arrays.stream(request.getCookies())
                    .filter(item -> item.getName().equals("email"))
                    .findFirst();
            if(optionalEmailCookie.isPresent()) {
                String email = optionalEmailCookie.get().getValue();
                try {
                    UserDto userDto = signInService.signIn(email);
                    session = request.getSession(true);
                    session.setAttribute("user", userDto);
                    isAuthenticated = true;
                } catch (ValidationException ignored) {}
            }
        }

        // если авторизован и запрашивает не открытую страницу или если не авторизован и запрашивает открытую
        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
            // отдаем ему то, что он хочет
            filterChain.doFilter(request, response);
        } else if (isAuthenticated) {
            // пользователь аутенцифицирован и запрашивает страницу входа
            // - отдаем ему профиль
            response.sendRedirect("/profile");
        } else {
            // если пользователь не аутенцицицирован и запрашивает другие страницы
            response.sendRedirect("/sign-in");
        }*/

        // преобразуем запросы к нужному виду
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        // берем сессию у запроса
        // берем только существующую, если сессии не было - то вернет null
        HttpSession session = request.getSession(false);
        //Проверка на админа


        // флаг, аутентифицирован ли пользователь
        boolean isAuthenticated = false;
        // существует ли сессия вообще?
        boolean sessionExists = session != null;
        // идет ли запрос на страницу входа или регистрации?
        boolean isRequestOnAuthPage = request.getRequestURI().startsWith(request.getContextPath()+"/sign-in") ||
                request.getRequestURI().startsWith(request.getContextPath()+"/sign-up") ;

        // если сессия есть
        if (sessionExists) {
        // проверим, есть ли атрибует user?
            isAuthenticated = session.getAttribute("user")!= null;
        }


        // если авторизован и запрашивает не открытую страницу или если не авторизован и запрашивает открытую
        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
        // отдаем ему то, что он хочет
            filterChain.doFilter(request, response);
            return;
        } else if (isAuthenticated && isRequestOnAuthPage) {
        // пользователь аутенцифицирован и запрашивает страницу входа
        // - отдаем ему профиль
            response.sendRedirect(request.getContextPath()+"/profile");
        } else {
        // если пользователь не аутенцицицирован и запрашивает другие страницы
            response.sendRedirect(request.getContextPath()+"/sign-in");
        }

    }

    @Override
    public void destroy() {

    }
}
