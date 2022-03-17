package com.keralapolice.projectk.mongotest.vo;

import com.keralapolice.projectk.mongotest.vo.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Student {

    @Id
    private String id;
    private String firstName;
    private String secondName;
    private List<Address> studentAddress;

    public Student(String id, String firstName, String secondName, List<Address> studentAddress) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.studentAddress = studentAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Address> getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(List<Address> studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
