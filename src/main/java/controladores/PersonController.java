package br.com.orbitall.hackathon2025.controllers;

import br.com.orbitall.hackathon2025.canonicals.PersonInput;
import br.com.orbitall.hackathon2025.canonicals.PersonOutput;
import br.com.orbitall.hackathon2025.models.Person;
import br.com.orbitall.hackathon2025.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@@ -14,23 +18,28 @@ public class PersonController {
    private PersonService service;

    @PostMapping
    public Person create(@RequestBody Person person) {
        return service.create(person);
        public PersonOutput create(@Valid @RequestBody PersonInput input) {
            return service.create(input);
        }

        @GetMapping("/{id}")
        public Person retrieve(@PathVariable UUID id) {
            public PersonOutput retrieve(@PathVariable UUID id) {
                return service.retrieve(id);
            }

            @PutMapping("/{id}")
            public Person update(@PathVariable UUID id, @RequestBody Person person) {
                return service.update(id, person);
                public PersonOutput update(@PathVariable UUID id, @RequestBody PersonInput input) {
                    return service.update(id, input);
                }

                @DeleteMapping("/{id}")
                public Person delete(@PathVariable UUID id) {
                    public PersonOutput delete(@PathVariable UUID id) {
                        return service.delete(id);
                    }

                    @GetMapping
                    public List<PersonOutput> findAll() {
                        return service.findAll();
                    }

                }