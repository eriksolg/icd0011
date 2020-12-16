package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import validation.ValidationErrors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.List;

@RestController
public class ValidationController {

    @PostMapping("manual-validation")
    public ResponseEntity<Object> manualValidation(@RequestBody Post post) {

        ValidationErrors errors = new ValidationErrors();
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(post);

        // validation code goes here
        for (ConstraintViolation<Post> violation : violations) {
            errors.addErrorMessage(violation.getMessage());
        }
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("validation")
    public void validation(@RequestBody @Valid Post post) {
        System.out.println(post);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors handleValidationError(
            MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        System.out.println(errors);

        ValidationErrors result = new ValidationErrors();

        for (FieldError error : errors) {
            result.addFieldError(error);
        }
        // add errors to the result

        return result;
    }

}