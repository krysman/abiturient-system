package com.sevsu.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.contrib.hibernate.PersistentDateTime;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ENROLLEES")
public class Enrollee {

    @Id
    @Column(name = "enrollee_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "firstName")
    private String firstName;
    private String lastName;
    private String middleName;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private LocalDateTime birthDate;

    private String educationForm; // очная/заочная

    //@ManyToMany
    //@JoinTable(name="enrollee_specialization", joinColumns=@JoinColumn(name="enrollee_id"), inverseJoinColumns=@JoinColumn(name="specialization_id"))
    //@OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name="id")
    //@JoinColumn(name="_ID", referencedColumnName="EMP_ID")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="enrollee_specialization", joinColumns=@JoinColumn(name="enrollee_id"), inverseJoinColumns=@JoinColumn(name="specialization_id"))
    private Set<Specialization> specializations;

    private String appliedAcademicDegree;
    private String previousEducation;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private LocalDateTime previousEducationYear;

    private String address;

    public Enrollee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations = specializations;
    }

    public String getAppliedAcademicDegree() {
        return appliedAcademicDegree;
    }

    public void setAppliedAcademicDegree(String appliedAcademicDegree) {
        this.appliedAcademicDegree = appliedAcademicDegree;
    }

    public String getPreviousEducation() {
        return previousEducation;
    }

    public void setPreviousEducation(String previousEducation) {
        this.previousEducation = previousEducation;
    }

    public LocalDateTime getPreviousEducationYear() {
        return previousEducationYear;
    }

    public void setPreviousEducationYear(LocalDateTime previousEducationYear) {
        this.previousEducationYear = previousEducationYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Enrollee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", educationForm='" + educationForm + '\'' +
                ", specializations=" + specializations +
                ", appliedAcademicDegree='" + appliedAcademicDegree + '\'' +
                ", previousEducation='" + previousEducation + '\'' +
                ", previousEducationYear=" + previousEducationYear +
                ", address='" + address + '\'' +
                '}';
    }
}
