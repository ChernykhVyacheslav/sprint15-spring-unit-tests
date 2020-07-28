package com.softserve.sprint14.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Progress {

    public enum TaskStatus {
        NEW, PASS, FAIL, PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant startDate;

    @UpdateTimestamp
    private Instant updateDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private Task task;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "trainee_id")
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private User trainee;
}
