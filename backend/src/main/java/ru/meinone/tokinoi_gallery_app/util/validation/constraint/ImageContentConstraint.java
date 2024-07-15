package ru.meinone.tokinoi_gallery_app.util.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.meinone.tokinoi_gallery_app.util.validation.validator.ImageContentValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageContentValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageContentConstraint {
    String message() default "File should be a valid image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
