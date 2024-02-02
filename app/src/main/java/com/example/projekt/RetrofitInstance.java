package com.example.projekt;






import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    public static final String PONS_API_URL = "https://api.pons.com/v1/";

    // Dodaj swój X-Secret tutaj
    private static final String X_SECRET = "9506aa7f46da5cfed86efef7d8f3014cfffaf3f006f81be844d35e92f3778e6d";

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("X-Secret", X_SECRET)
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(PONS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}



/*
public class RetrofitInstance {
    private static Retrofit retrofit;
    public static final String BOOK_API_URL =  "https://api.pons.com/v1/";

    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BOOK_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        return retrofit;
    }
}
*/