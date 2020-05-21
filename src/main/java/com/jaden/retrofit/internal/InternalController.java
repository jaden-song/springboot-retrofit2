package com.jaden.retrofit.internal;

import com.jaden.retrofit.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/internal")
public class InternalController {
    private final Logger logger = LoggerFactory.getLogger(InternalController.class);

    private final InternalService internalService;

    public InternalController(InternalService internalService) {
        this.internalService = internalService;
    }

    // Sync
    @GetMapping("/persons")
    public List<Person> getPersonListSync() {
        logger.info("## InternalController.getPersonListSync");
        return internalService.getInternalPersonList()
                .orElse(new ArrayList<>());
    }

    @GetMapping("/person/{name}")
    public Person getPersonSync(@PathVariable String name) {
        logger.info("## InternalController.getPersonSync: name = " + name);
        return internalService.getInternalPerson(name);
    }

    // Async
    @GetMapping("/persons/async")
    public Mono<List<Person>> getPersonListAsync() {
        logger.info("## InternalController.getPersonListAsync");
        return internalService.getInternalPersonListAsync()
                .onErrorResume(error -> {
                    logger.info("## InternalController.getPersonListAsync: error = " + error);
                    return Mono.empty();
                });
    }

    @GetMapping("/person/async/{name}")
    public Mono<Person> getPersonAsync(@PathVariable String name) {
        logger.info("## InternalController.getPersonAsync: name = " + name);
        return internalService.getInternalPersonAsync(name)
                .onErrorResume(error -> {
                    logger.info("## InternalController.getPersonAsync: error = " + error);
                    return Mono.empty();
                });
    }
}
