package com.softserve.sprint14.dto;

import com.softserve.sprint14.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringArrayToUserListConverter implements Converter<String[], List<User>> {

    @Autowired
    public StringArrayToUserListConverter() {
    }

    @Override
    public List<User> convert(MappingContext<String[], List<User>> context) {
        List<User> users = new ArrayList<>();
        String[] userStrings = context.getSource();
        for (int i = 0; i < userStrings.length; i++) {
            User user = new User();
            Pattern pattern = Pattern.compile("(?<=id=).+(?=,)");
            Matcher matcher = pattern.matcher(userStrings[i]);
            if (matcher.find()) {
                user.setId(Long.getLong(matcher.group(0)));
            }
            pattern = Pattern.compile("(?<=email=).+(?=,)");
            matcher = pattern.matcher(userStrings[i]);
            if (matcher.find()) {
                user.setEmail(matcher.group(0));
            }
            pattern = Pattern.compile("(?<=firstName=).+(?=,)");
            matcher = pattern.matcher(userStrings[i]);
            if (matcher.find()) {
                user.setFirstName(matcher.group(0));
            }
            pattern = Pattern.compile("(?<=lastName=).+(?=,)");
            matcher = pattern.matcher(userStrings[i]);
            if (matcher.find()) {
                user.setLastName(matcher.group(0));
            }
            pattern = Pattern.compile("(?<=role=).+(?=,)");
            matcher = pattern.matcher(userStrings[i]);
            if (matcher.find()) {
                user.setRole(User.Role.valueOf(matcher.group(0)));
            }
            users.add(user);
        }
        return users;
    }
}