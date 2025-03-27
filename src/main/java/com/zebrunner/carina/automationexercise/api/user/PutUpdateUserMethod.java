package com.zebrunner.carina.automationexercise.api.user;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.Header;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url="${base_url}/updateAccount", methodType = HttpMethodType.PUT)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PutUpdateUserMethod extends AbstractApiMethodV2 {
    public PutUpdateUserMethod(){
        super("api/users/_put/rq.properties", "api/users/_put/rs.json", "api/users/user.properties");
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
