package com.ceramicthree.deadInsideSN.services;

import com.ceramicthree.deadInsideSN.models.User;
import com.ceramicthree.deadInsideSN.repos.UserRepository;
import org.ietf.jgss.Oid;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service
public class OidcUserService extends org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService {
    final UserRepository userRepository;

    public OidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        User user = userRepository.findByEmail((String) attributes.get("email"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'в' HH:mm");
        Date date = new Date(System.currentTimeMillis());

        if(user == null){
            User newUser = new User();
            newUser.setEmail((String) attributes.get("email"));
            newUser.setGender((String) attributes.get("gender"));
            newUser.setId((String) attributes.get("sub"));
            newUser.setUserpic((String) attributes.get("picture"));
            newUser.setName((String) attributes.get("name"));
            newUser.setLastVisit(dateFormat.format(date));
            newUser.setLocale((String) attributes.get("locale"));
            userRepository.save(newUser);
        }else{
            user.setLastVisit(dateFormat.format(date));
            userRepository.save(user);
        }
        return oidcUser;
    }

}
