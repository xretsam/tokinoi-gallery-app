package ru.meinone.tokinoi_gallery_app.util.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import ru.meinone.tokinoi_gallery_app.util.validation.constraint.ImageContentConstraint;

public class ImageContentValidator implements ConstraintValidator<ImageContentConstraint, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null) return true;
        if(!multipartFile.isEmpty()) {
            if(multipartFile.getContentType() != null && !multipartFile.getContentType().isEmpty()) {
                return multipartFile.getContentType().contains("image");
            }
        }
        return false;
    }
}
