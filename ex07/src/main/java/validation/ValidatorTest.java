package validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public class ValidatorTest {

    public static void main(String[] args) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        Post post = new Post(1L, "dd", "d");

        var violations = validator.validate(post);

        ValidationErrors errors = new ValidationErrors();

        for (ConstraintViolation<Post> violation : violations) {
            errors.addErrorMessage(violation.getMessage());
        }

        try {
            String errorsJson = new ObjectMapper().writeValueAsString(errors);
            System.out.println(errorsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



    }


}
