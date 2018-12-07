package com.vietxongxoa.data.remote;

import com.vietxongxoa.model.ApiError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                ApiHelper.getClient().responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error;
        try {
            assert response.errorBody() != null;
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
        return error;
    }
}
