package com.sevsu.model;

import javax.persistence.*;

@Entity
@Table(name = "SPECIALIZATIONS")
public class Specialization {

    @Id
    @Column(name = "specialization_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "institute")
    private String institute;
    private String department;
    private String cipher; // шифр

    public Specialization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", institute='" + institute + '\'' +
                ", department='" + department + '\'' +
                ", cipher='" + cipher + '\'' +
                '}';
    }
}
