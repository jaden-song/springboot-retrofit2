package com.jaden.retrofit.external;

import com.jaden.retrofit.model.Person;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RetrofitApi {

    @GET("persons")
    Call<List<Person>> getPersonList();

    @GET("person/{name}")
    Call<Person> getPerson(@Path("name") String name);
}
