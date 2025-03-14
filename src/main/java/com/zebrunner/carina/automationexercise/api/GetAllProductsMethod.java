package com.zebrunner.carina.automationexercise.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${api_url}/api/productsList", methodType=HttpMethodType.GET)
@ResponseTemplatePath(path = "api/products/_getAll/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetAllProductsMethod extends AbstractApiMethodV2 {

    public GetAllProductsMethod() {
        replaceUrlPlaceholder("api_url", Configuration.getRequired("api_url"));
    }
}
