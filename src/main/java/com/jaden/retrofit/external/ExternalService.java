package com.jaden.retrofit.external;

import com.jaden.retrofit.model.Person;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalService {

    private List<Person> personList;

    @PostConstruct
    public void init() {
        personList = new ArrayList<>();
        personList.add(new Person("maybe", 42));
        personList.add(new Person("elly", 22));
        personList.add(new Person("guava", 30));
        personList.add(new Person("betty", 20));
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Person getPerson(String name) {
        for (Person person : personList) {
            if (name.equals(person.getName())) {
                return person;
            }
        }
        return null;
    }
}
