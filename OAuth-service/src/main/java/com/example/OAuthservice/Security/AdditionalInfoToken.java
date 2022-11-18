package com.example.OAuthservice.Security;

import com.example.OAuthservice.Service.IUserService;
import com.example.commonslibrary.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

    @Autowired
    private IUserService serv;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        Map<String, Object> info = new HashMap<String, Object>();

        User user = serv.findByUsername(oAuth2Authentication.getName());
        info.put("name", user.getName());
        info.put("lastname", user.getLastName());
        info.put("email", user.getEmail());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);

        return oAuth2AccessToken;
    }
}
