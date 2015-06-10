package com.sevsu.model;

import javax.persistence.*;


@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String login;

    @Column(name = "password", nullable = false)
    private String passwordMd5;

    @Column(name = "salt", nullable = false)
    private String saltForPassword;

    @ManyToOne // no cascade type because cascading only (well ALMOST ) makes sense only for Parent – Child associations
    private Role role;



    public User() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public String getSaltForPassword() {
        return saltForPassword;
    }

    public void setSaltForPassword(String saltForPassword) {
        this.saltForPassword = saltForPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordMd5='" + passwordMd5 + '\'' +
                ", saltForPassword='" + saltForPassword + '\'' +
                ", role=" + role +
                '}';
    }
}
