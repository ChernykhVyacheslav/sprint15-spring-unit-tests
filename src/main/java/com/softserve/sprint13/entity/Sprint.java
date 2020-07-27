package com.softserve.sprint13.entity;

import com.softserve.sprint13.exception.CannotDeleteOwnerWithElementsException;
import com.softserve.sprint13.validation.StartBeforeEndDateValidation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@StartBeforeEndDateValidation(message = "Start date should be before finish date.")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant finishDate;

    @NotBlank(message = "Sprint title cannot be empty")
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "marathon_id")
    @EqualsAndHashCode.Include
    @ToString.Exclude
    private Marathon marathon;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "sprint",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Task> tasks;

    @PreRemove
    public void checkTaskAssociationBeforeRemoval() {
        if (!this.tasks.isEmpty()) {
            throw new CannotDeleteOwnerWithElementsException("Can't remove a sprint that has tasks.");
        }
    }
}
