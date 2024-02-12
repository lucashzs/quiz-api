package com.api.quiz.services;

import com.api.quiz.dtos.RegisterUserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VerificationsService {

    static boolean verifyDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public static void validate(String password) throws Exception {
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new Exception("The password must have a minimum of 8 characters, 1 uppercase letter, 1 lowercase letter and must not contain blanks!");
        }
    }

    public static int calculatorAge(RegisterUserDto registerUserDto, LocalDate currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if ((registerUserDto.birthDate() != null) && (currentDate != null)) {
            LocalDate birthDate = LocalDate.parse(registerUserDto.birthDate(), formatter);
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
