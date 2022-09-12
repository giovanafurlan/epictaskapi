package br.com.fiap.epictaskapi.config.security;

import br.com.fiap.epictaskapi.dto.request.LoginDto;
import br.com.fiap.epictaskapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
        try{
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filter,
                                            Authentication auth) throws IOException, ServletException{
        String email = ((User) auth.getPrincipal()).getEmail();
        String token = jwtUtil.generateToken(email);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
