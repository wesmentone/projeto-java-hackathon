‎src/main/java/br/com/orbitall/hackathon2025/services/PersonService.java‎
+95
        -13
Linhas alteradas: 95 adições e 13 exclusões
Número da linha do arquivo original	Número da linha de comparação	Mudança de linha de comparação
@@ -1,51 +1,133 @@
        package br.com.orbitall.hackathon2025.services;

import br.com.orbitall.hackathon2025.canonicals.PersonInput;
import br.com.orbitall.hackathon2025.canonicals.PersonOutput;
import br.com.orbitall.hackathon2025.exceptions.ResourceNotFoundException;
import br.com.orbitall.hackathon2025.models.Person;
import br.com.orbitall.hackathon2025.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person create(Person person) {
        public PersonOutput create(PersonInput input) {
            LocalDateTime now = LocalDateTime.now();

            Person person = new Person();
            person.setFullName(input.fullName());
            person.setAge(input.age());
            person.setDescription(input.description());
            person.setId(UUID.randomUUID());
            person.setCreatedAt(now);
            person.setUpdatedAt(now);
            person.setActive(true);

            return repository.save(person);
            repository.save(person);
            return new PersonOutput(
                    person.getId(),
                    person.getFullName(),
                    person.getAge(),
                    person.getDescription(),
                    person.getCreatedAt(),
                    person.getUpdatedAt(),
                    person.isActive()
            );
        }

        public Person retrieve(UUID id) {
            return repository.findById(id).get();
            public PersonOutput retrieve(UUID id) {
                Person person = repository
                        .findById(id)
                        .filter(Person::isActive)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found the resource (id: " + id + ")"));
                PersonOutput output = new PersonOutput(
                        person.getId(),
                        person.getFullName(),
                        person.getAge(),
                        person.getDescription(),
                        person.getCreatedAt(),
                        person.getUpdatedAt(),
                        person.isActive());
                return output;
            }

            public Person update(UUID id, Person person) {
                Person fetched = repository.findById(id).get();
                public PersonOutput update(UUID id, PersonInput input) {
                    Person fetched = repository
                            .findById(id)
                            .filter(Person::isActive)
                            .orElseThrow(() -> new ResourceNotFoundException("Not found the resource (id: " + id + ")"));

                    fetched.setFullName(person.getFullName());
                    fetched.setAge(person.getAge());
                    fetched.setDescription(person.getDescription());
                    fetched.setFullName(input.fullName());
                    fetched.setAge(input.age());
                    fetched.setDescription(input.description());
                    fetched.setUpdatedAt(LocalDateTime.now());

                    return repository.save(fetched);
                    repository.save(fetched);
                    PersonOutput output = new PersonOutput(
                            fetched.getId(),
                            fetched.getFullName(),
                            fetched.getAge(),
                            fetched.getDescription(),
                            fetched.getCreatedAt(),
                            fetched.getUpdatedAt(),
                            fetched.isActive());
                    return output;
                }

                public Person delete(UUID id) {
                    Person fetched = repository.findById(id).get();
                    public PersonOutput delete(UUID id) {
                        Person fetched = repository
                                .findById(id)
                                .filter(Person::isActive)
                                .orElseThrow(() -> new ResourceNotFoundException("Not found the resource (id: " + id + ")"));

                        fetched.setUpdatedAt(LocalDateTime.now());
                        fetched.setActive(false);

                        return repository.save(fetched);
                        repository.save(fetched);
                        return new PersonOutput(
                                fetched.getId(),
                                fetched.getFullName(),
                                fetched.getAge(),
                                fetched.getDescription(),
                                fetched.getCreatedAt(),
                                fetched.getUpdatedAt(),
                                fetched.isActive());
                    }
                    public List<PersonOutput> findAll() {
                        List<PersonOutput> list = new ArrayList<>();
                        repository.findAll().forEach( person -> {
                            if(person.isActive()) {
                                PersonOutput output = new PersonOutput(
                                        person.getId(),
                                        person.getFullName(),
                                        person.getAge(),
                                        person.getDescription(),
                                        person.getCreatedAt(),
                                        person.getUpdatedAt(),
                                        person.isActive());
                                list.add(output);
                            }
                        });
                        return list;
                    }

                }