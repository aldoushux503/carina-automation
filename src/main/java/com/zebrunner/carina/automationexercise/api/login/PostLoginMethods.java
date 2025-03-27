package com.zebrunner.carina.automationexercise.api.login;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url = "${base_url}/verifyLogin", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PostLoginMethods extends AbstractApiMethodV2 {
    private PostLoginMethods(String requestTemplate, String responseTemplate, String propertiesPath) {
        super(requestTemplate, responseTemplate, propertiesPath);
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }


    public static PostLoginMethods createValidLoginMethod() {
        return new PostLoginMethods(
                "api/login/_post/valid_login_rq.properties",
                "api/login/_post/valid_login_rs.json",
                "api/login/login.properties"
        );
    }

    public static PostLoginMethods createLoginWithoutEmailMethod() {
        return new PostLoginMethods(
                "api/login/_post/no_email_rq.properties",
                "api/login/_post/no_email_rs.json",
                "api/login/login.properties"
        );
    }

    public static PostLoginMethods createInvalidLoginMethod() {
        return new PostLoginMethods(
                "api/login/_post/invalid_login_rq.properties",
                "api/login/_post/invalid_login_rs.json",
                "api/login/login.properties"
        );
    }


}