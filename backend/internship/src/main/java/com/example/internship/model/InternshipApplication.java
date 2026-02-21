package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "internship_application",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "internship_id"})
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InternshipApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = Status.PENDING;
        }
        appliedAt = LocalDateTime.now();
    }
}
