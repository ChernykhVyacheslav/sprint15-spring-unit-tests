package com.softserve.sprint14.validation;

import com.softserve.sprint14.entity.Sprint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

public class StartBeforeEndDateValidator implements ConstraintValidator<StartBeforeEndDateValidation, Sprint> {

    @Override
    public void initialize(StartBeforeEndDateValidation annotation) {
    }

    @Override
    public boolean isValid(Sprint bean, ConstraintValidatorContext context) {
        final Instant startDate = bean.getStartDate();
        final Instant endDate = bean.getFinishDate();

        return !startDate.isAfter(endDate);
    }
}
