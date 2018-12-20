package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UiFormValidator implements Validator {
    @Autowired
    UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo checked = (UserTo) target;
        //Try to find a user with tested email
        User user = repository.getByEmail(checked.getEmail().toLowerCase());
        if (user != null) {
            errors.rejectValue("email", "User with such email already exists");
        }
    }
}
