package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 100)
    private String jobName;

    @NotNull
    @Column(nullable = false, length = 1000)
    private String jobDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Capability capability;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Band band;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
