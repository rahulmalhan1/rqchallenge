package com.example.rqchallenge.employees.model;

public class Employee {
    private Long id;

    private String name;

    private Integer salary;

    private Long age;

    public static Employee fromData(EmployeeData data) {
        Employee e = new Employee();
        e.setId(data.getId());
        e.setName(data.getName());
        e.setSalary(data.getSalary());
        e.setAge(data.getAge());

        return e;
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
}
