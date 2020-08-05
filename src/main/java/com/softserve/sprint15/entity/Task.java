package com.softserve.sprint15.entity;

import com.softserve.sprint15.exception.CannotDeleteOwnerWithElementsException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate createDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    @NotBlank(message = "Task title cannot be empty")
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "sprint_id")
    @EqualsAndHashCode.Include
    @ToString.Exclude
    private Sprint sprint;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "task",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Progress> progressList = new ArrayList<>();

    @PreRemove
    public void checkProgressAssociationBeforeRemoval() {
        if (!this.progressList.isEmpty()) {
            throw new CannotDeleteOwnerWithElementsException("Can't remove a task that has progress entities.");
        }
    }
}
