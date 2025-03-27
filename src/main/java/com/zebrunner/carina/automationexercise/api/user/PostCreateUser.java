package com.zebrunner.carina.automationexercise.api.user;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.Header;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;
import org.apache.ibatis.io.Resources;


@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url = "${base_url}/createAccount", methodType = HttpMethodType.POST)
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PostCreateUser extends AbstractApiMethodV2 {
    public PostCreateUser(){
        super("api/users/_post/rq.properties", "api/users/_post/rs.json", "api/users/user.properties");
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
