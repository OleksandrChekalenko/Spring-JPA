package com.example.pet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String body;
    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Note person = (Note) o;
        return id != null && Objects.equals(id, person.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}



