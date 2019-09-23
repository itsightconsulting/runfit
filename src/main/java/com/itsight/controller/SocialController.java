package com.itsight;

import com.itsight.configuration.SSLClientFactory;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.dto.UserSsoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2/redirect")
public class RestDemoController {

    @Value(value = "${bs.sso.server.url}")
    private String BASE_SOCIAL_SSO_URL;

    @GetMapping("")
    public @ResponseBody
    ModelAndView listaPX(@RequestParam(required=false) String token, @RequestParam(required=false) String error) {
        if(error != null && error.length()>0){
            return new ModelAndView(ViewConstant.MAIN_INF_N, "msg", error);
        }
        RestTemplate restTemplate = new RestTemplate(SSLClientFactory.getClientHttpRequestFactory(SSLClientFactory.HttpClientType.HttpClient));
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entities = new HttpEntity<>(headers);
        //heroku url http://127.0.0.1:8080/user/me
        ResponseEntity<UserSsoDTO> responseObj = restTemplate.exchange("https://127.0.0.1:8081/user/me", HttpMethod.GET, entities, UserSsoDTO.class);
        UserSsoDTO userBySso = responseObj.getBody();
        ModelAndView mav = new ModelAndView(ViewConstant.LOGIN);
        mav.addObject("usso", userBySso);
        return mav;
    }
}
