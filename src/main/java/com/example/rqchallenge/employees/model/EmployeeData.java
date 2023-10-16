package com.example.rqchallenge.employees.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EmployeeData {
    private Long id;

    @JsonAlias({ "employee_name", "name" })
    private String name;

    @JsonAlias({ "employee_salary", "salary" })
    private Integer salary;
    @JsonAlias({ "employee_age", "age" })
    private Long age;
    @JsonAlias({"profile_image"})
    private String profileImage;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSalary() {
        return salary;
    }

    public Long getAge() {
        return age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
