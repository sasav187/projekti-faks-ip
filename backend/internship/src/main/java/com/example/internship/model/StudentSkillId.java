package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSkillId implements Serializable {

    private Long studentId;
    private Long skillId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentSkillId that = (StudentSkillId) o;
        return studentId.equals(that.studentId) && skillId.equals(that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, skillId);
    }
}