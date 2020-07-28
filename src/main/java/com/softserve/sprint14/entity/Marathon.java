package com.softserve.sprint14.entity;

import com.softserve.sprint14.exception.CannotDeleteOwnerWithElementsException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Marathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marathon title cannot be empty")
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;

    private boolean isClosed = false;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "marathon_user",
            joinColumns = @JoinColumn(name = "marathon_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "marathon_id")
    @ToString.Exclude
    private List<Sprint> sprints;

    @PreRemove
    public void checkSprintAssociationBeforeRemoval() {
        if (!this.sprints.isEmpty()) {
            throw new CannotDeleteOwnerWithElementsException("Can't remove a marathon that has sprints.");
        }
    }
}