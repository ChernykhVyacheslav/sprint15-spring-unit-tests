package com.softserve.sprint14.dto;

import com.softserve.sprint14.entity.Sprint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MarathonDTO {

    private Long id;
    private String title;
    private boolean isClosed;
    private String[] users;
    private List<Sprint> sprints;

    public MarathonDTO() {
    }

    public MarathonDTO(Long id, String title, boolean isClosed, String[] users, List<Sprint> sprints) {
        this.id = id;
        this.title = title;
        this.isClosed = isClosed;
        this.users = users;
        this.sprints = sprints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id != null)
            this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getUsers() {
        List<Long> usersId = Arrays.stream(users).map(s -> {
            Pattern pattern = Pattern.compile("(?<=id=).+(?=,)");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                return Long.getLong(matcher.group(0));
            }
            return -1L;
        }).collect(Collectors.toList());
        return usersId;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public List<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        if (id != null)
            isClosed = closed;
    }
}
