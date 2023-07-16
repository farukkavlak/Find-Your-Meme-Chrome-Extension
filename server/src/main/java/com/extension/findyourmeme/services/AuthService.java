package com.extension.findyourmeme.services;

import com.extension.findyourmeme.converter.UserConverter;
import com.extension.findyourmeme.dao.UserDao;
import com.extension.findyourmeme.dto.AuthRequestDto;
import com.extension.findyourmeme.entity._User;
import com.extension.findyourmeme.generic.enums.ErrorMessage;
import com.extension.findyourmeme.generic.exceptions.BusinessException;
import com.extension.findyourmeme.generic.service.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService extends BaseEntityService<_User, UserDao> {
    public AuthService(UserDao dao) {
        super(dao);
    }
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean register(AuthRequestDto authRequestDto) {
        if (authRequestDto.getUsername() == null || authRequestDto.getPassword() == null)
            throw new BusinessException(ErrorMessage.PARAMETER_CANNOT_BE_NULL);
        if (this.getDao().existsByUsername(authRequestDto.getUsername()))
            throw new BusinessException(ErrorMessage.USERNAME_ALREADY_EXISTS);
        if(!isPasswordStrengthEnough(authRequestDto.getPassword()))
            throw new BusinessException(ErrorMessage.PASSWORD_NOT_STRONG_ENOUGH);
        //Build user
        _User user = userConverter.convertToUser(authRequestDto);
        String password = passwordEncoder.encode(authRequestDto.getPassword());
        user.setPassword(password);
        user = this.save(user);
        return true;
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
    public String login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(),
                        authRequestDto.getPassword()
                )
        );
        if(authentication.isAuthenticated()){
            //If user role is not admin, throw exception, only admin login
            if(!authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")))
                throw new BusinessException(ErrorMessage.USER_NOT_ADMIN);
            return jwtService.generateToken(authRequestDto.getUsername());
        }else {
            throw new BusinessException(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
        }
    }
    //Check if password contains symbols
    private boolean isContainSymbol(String password) {
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(password);
        return hasSpecial.find();
    }
    //Check if password contains number
    private boolean isContainNumber(String password) {
        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(password);
        return hasDigit.find();
    }
    //Check if password contains upper case
    private boolean isContainUpperCase(String password) {
        for(int letterNo=0;letterNo<password.length();letterNo++){
            if(password.charAt(letterNo)>=65&&password.charAt(letterNo)<=90){
                return true;
            }
        }
        return false;
    }
    //Check if password contains lower case
    private boolean isContainLowerCase(String password) {
        for(int letterNo=0;letterNo<password.length();letterNo++){
            if(password.charAt(letterNo)>=97&&password.charAt(letterNo)<=122){
                return true;
            }
        }
        return false;
    }
    private boolean isPasswordStrengthEnough(String password) {
        return isContainUpperCase(password)
                && isContainLowerCase(password)
                && isContainNumber(password)
                && isContainSymbol(password);
    }

}
