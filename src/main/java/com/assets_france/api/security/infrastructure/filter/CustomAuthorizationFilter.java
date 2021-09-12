package com.assets_france.api.security.infrastructure.filter;

//
//@Slf4j
//public class CustomAuthorizationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
//            filterChain.doFilter(request, response);
//        } else {
//            String authorizationHeader = request.getHeader(AUTHORIZATION);
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                try {
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decodedJWT = verifier.verify(token);
//                    String username = decodedJWT.getSubject();
//                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    Arrays.stream(roles).forEach(role -> {
//                        authorities.add(new SimpleGrantedAuthority(role));
//                    });
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    filterChain.doFilter(request, response);
//                } catch (Exception exception) {
//                    log.error("Error logging in : {}", exception.getMessage());
//                    response.setHeader("error", exception.getMessage());
//                    //response.sendError(FORBIDDEN.value());
//                    response.setStatus(FORBIDDEN.value());
//                    Map<String, String> error = new HashMap<>();
//                    error.put("error_message", exception.getMessage());
//                    response.setContentType(APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), error);
//                }
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        }
//    }
//}

import com.assets_france.api.security.infrastructure.TokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

public class CustomAuthorizationFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    public CustomAuthorizationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var token = tokenProvider.resolveToken(httpRequest);
        if (hasText(token) && tokenProvider.validateToken(token)) {
            var authentication = tokenProvider.getAuthentication(token);
            System.out.println("in doFilter authentication : " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("in doFilter after security context holder");
        }

        chain.doFilter(request, response);
    }
}