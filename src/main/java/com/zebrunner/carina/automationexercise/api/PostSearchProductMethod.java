package com.zebrunner.carina.automationexercise.api;


import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.*;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Header(key = "Content-Type", value = "application/x-www-form-urlencoded")
@Endpoint(url = "${base_url}/searchProduct", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/products/_post/rq.properties")
@ResponseTemplatePath(path = "api/products/_post/rs.schema")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class PostSearchProductMethod extends AbstractApiMethodV2 {
    public PostSearchProductMethod() {
        super("api/products/_post/rq.properties", "api/products/_post/rs.schema", "api/products/product.properties");
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
    }
}
