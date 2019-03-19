package com.sharmaxz.controller;

import com.sharmaxz.model.Hobby;
import com.sharmaxz.model.Person;
import com.sharmaxz.repository.HobbyRepository;
import com.sharmaxz.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class PersonController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    HobbyRepository hobbyRepository;


    @RequestMapping(value = "/person/create", method = RequestMethod.GET)
    public String createPage(Map<String, Object> model) {
        Iterable<Hobby> hobbies = hobbyRepository.findAll();
        model.put("hobbyList", hobbies);
        return "person/create";
    }

    @RequestMapping(value = "/person/create", method = RequestMethod.POST)
    public void save(@RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("idHobby") Long idHobby,
                     Map<String, Object> model) {
        Hobby hobby = hobbyRepository.findOne(idHobby);
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setFavoriteHobby(hobby);
        personRepository.save(person);

        Iterable<Hobby> hobbies = hobbyRepository.findAll();
        model.put("hobbyList", hobbies);
        model.put("message", "Person " + name + " created");
    }


    @RequestMapping(value = "/person/list", method = RequestMethod.GET)
    public String listPage(Map<String, Object> model) {
        Iterable<Person> personList = personRepository.findAll();
        model.put("personList", personList);
        return "person/list";
    }

    @RequestMapping(value = "/person/edit", method = RequestMethod.GET)
    public String editPage(@RequestParam("id") Long id, Map<String, Object> model) {
        Person person = personRepository.findOne(id);
        model.put("person", person);

        Iterable<Hobby> hobbies = hobbyRepository.findAll();
        model.put("hobbyList", hobbies);
        return "person/edit";
    }





    @RequestMapping(value = "person/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        personRepository.delete(id);
        return "redirect:/person/list";
    }

}

