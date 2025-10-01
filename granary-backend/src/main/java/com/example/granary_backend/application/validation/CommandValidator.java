package com.example.granary_backend.application.validation;

import com.example.granary_backend.application.exception.InvalidCommandException;

public interface CommandValidator<T> {

  void validate(T command) throws InvalidCommandException;
}
