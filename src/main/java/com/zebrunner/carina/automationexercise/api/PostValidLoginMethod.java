package com.zebrunner.carina.automationexercise.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url = "${base_url}/verifyLogin", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/users/_post/rq.properties")
@ResponseTemplatePath(path = "api/users/_post/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PostValidLoginMethod extends AbstractApiMethodV2 {
    public PostValidLoginMethod() {
        super("api/users/_post/rq.properties","api/users/_post/rs.json", "api/users/user.properties");
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
