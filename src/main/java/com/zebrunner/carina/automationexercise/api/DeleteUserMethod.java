package com.zebrunner.carina.automationexercise.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.Header;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url = "${base_url}/deleteAccount", methodType = HttpMethodType.DELETE)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class DeleteUserMethod extends AbstractApiMethodV2 {

    public DeleteUserMethod() {
        super("api/users/_delete/rq.properties", "api/users/_delete/rs.json", "api/users/user.properties");
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
