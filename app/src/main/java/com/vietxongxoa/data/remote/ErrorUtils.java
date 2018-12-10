package com.vietxongxoa.data.remote;

import com.vietxongxoa.model.Error;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static Error parseError(Response<?> response) {
        Converter<ResponseBody, Error> converter =
                ApiHelper.getClient().responseBodyConverter(Error.class, new Annotation[0]);
        Error error;
        try {
            assert response.errorBody() != null;
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Error();
        }
        return error;
    }
}
