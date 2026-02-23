package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInterestId implements Serializable {

    private Long studentId;
    private Long interestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInterestId that = (StudentInterestId) o;
        return studentId.equals(that.studentId) && interestId.equals(that.interestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, interestId);
    }
}