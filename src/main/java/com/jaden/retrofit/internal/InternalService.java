package com.jaden.retrofit.internal;

import com.jaden.retrofit.external.RetrofitApi;
import com.jaden.retrofit.model.Person;
import com.jaden.retrofit.external.RetrofitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalService {

    private final Logger logger = LoggerFactory.getLogger(InternalService.class);

    private RetrofitApi personAPI;

    @PostConstruct
    public void init() {
        personAPI = RetrofitUtil.createService(RetrofitApi.class);
    }

    public Optional<List<Person>> getInternalPersonList() {
        return RetrofitUtil.requestSync(personAPI.getPersonList());
    }

    public Mono<List<Person>> getInternalPersonListAsync() {
        logger.info(">> InternalApiService.getInternalPersonListAsync start");
        Mono<List<Person>> mono = Mono.create(sink -> {
            RetrofitUtil.requestAsync(personAPI.getPersonList(), new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                    if (!response.isSuccessful()) {
                        logger.info("  ## Async Response = " + response);
                        sink.error(new Exception("Response is empty"));
                        return;
                    }
                    sink.success(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(Call<List<Person>> call, Throwable t) {
                    logger.info("  ## InternalApiService.onFailure, " + t);
                    sink.error(t);
                }
            });
        });
        logger.info("<< InternalApiService.getInternalPersonListAsync end");
        return mono;
    }

    public Person getInternalPerson(String name) {
        Optional<Person> person = RetrofitUtil.requestSync(personAPI.getPerson(name));
        return person.orElse(null);
    }

    public Mono<Person> getInternalPersonAsync(String name) {
        logger.info(">> InternalApiService.getInternalPersonAsync start");
        logger.info("name = " + name);
        Mono<Person> mono = Mono.create(sink -> {
            RetrofitUtil.requestAsync(personAPI.getPerson(name), new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if (!response.isSuccessful()) {
                        logger.info("  ## Async Response = " + response);
                        sink.error(new IllegalAccessError("invalid parameter"));
                        return;
                    }
                    sink.success(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    logger.info("  ## InternalApiService.onFailure, " + t);
                    sink.error(t);
                }
            });
        });
        logger.info("<< InternalApiService.getInternalPersonAsync end");
        return mono;
    }
}
