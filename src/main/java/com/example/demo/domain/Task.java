package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Table(name = "tasks")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column( nullable = false)
    String name;

    @Column(name = "start_time")
    LocalDate startTime;

    @Column(name = "end_time")
    LocalDate endTime;

    @Column(columnDefinition = "TEXT")
    String description;

    Integer priority;
    Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;

}
