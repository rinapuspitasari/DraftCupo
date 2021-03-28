package com.example.cupodraft.api.helper;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {
    private String token;
    private static final String KEY = "apikey";

    public AuthenticationInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", token)
//                .header("X-API-KEY", KEY)
                .method(original.method(), original.body());
        Request request = builder.build();
        return chain.proceed(request);
    }
}
