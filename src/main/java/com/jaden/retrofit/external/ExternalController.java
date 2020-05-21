package com.jaden.retrofit.external;

import com.jaden.retrofit.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external")
public class ExternalController {

    private final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    private final ExternalService personService;

    public ExternalController(ExternalService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> personList() {
        List<Person> personList = personService.getPersonList();
        logger.info("## ExternalController.personList = " + personList);
        return personList;
    }

    @GetMapping(value = "/person/{name}")
    public Person getPerson(@PathVariable String name) {
        logger.info("## ExternalController.getPerson:name = " + name);
        return personService.getPerson(name);
    }
}
