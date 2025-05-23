package com.branches.utils;

import com.branches.model.*;
import com.branches.request.EmployeePostRequest;
import com.branches.request.EmployeePutRequest;
import com.branches.response.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeUtils {

    public static List<Employee> newEmployeeList() {
        List<Person> personList = PersonUtils.newPersonList();
        Person person1 = personList.getFirst();
        Person person2 = personList.get(1);
        Person person3 = personList.getLast();

        Category category = CategoryUtils.newCategoryList().getFirst();

        Employee employee1 = Employee.builder().id(1L).person(person1).category(category).build();

        Employee employee2 = Employee.builder().id(2L).person(person2).category(category).build();

        Employee employee3 = Employee.builder().id(3L).person(person3).category(category).build();

        return new ArrayList<>(List.of(employee1, employee2, employee3));
    }

    public static List<EmployeeGetResponse> newEmployeeGetResponseList() {
        List<Person> personList = PersonUtils.newPersonList();
        Person person1 = personList.getFirst();
        Person person2 = personList.get(1);
        Person person3 = personList.getLast();
        CategoryGetResponse categoryGetResponse = CategoryUtils.newCategoryGetResponse();

        EmployeeGetResponse employee1 = EmployeeGetResponse.builder().id(1L).person(person1).category(categoryGetResponse).build();

        EmployeeGetResponse employee2 = EmployeeGetResponse.builder().id(2L).person(person2).category(categoryGetResponse).build();

        EmployeeGetResponse employee3 = EmployeeGetResponse.builder().id(3L).person(person3).category(categoryGetResponse).build();

        return new ArrayList<>(List.of(employee1, employee2, employee3));
    }

    public static EmployeePostRequest newEmployeePostRequest() {
        Address address = AddressUtils.newAddressToSave();

        EmployeePostRequest employee = EmployeePostRequest.builder().name("Chispirito").lastName("Costa").categoryId(1L).address(address).build();
        Phone phone = PhoneUtils.newPhone(null);
        employee.setPhones(List.of(phone));

        return employee;
    }

    public static Employee newEmployeeToSave() {
        Person person = PersonUtils.newPersonToSave();
        Category category = CategoryUtils.newCategoryList().getFirst();

        return Employee.builder().person(person).category(category).build();
    }

    public static Employee newEmployeeSaved() {
        return newEmployeeToSave().withId(4L);
    }

    public static EmployeePostResponse newEmployeePostResponse() {
        Person person = PersonUtils.newPersonSaved();
        Category category = CategoryUtils.newCategoryList().getFirst();

        return EmployeePostResponse.builder().id(4L).person(person).category(category).build();
    }

    public static EmployeeByRepairResponse newEmployeeByRepairPostResponse() {
        PersonDefaultResponse person = PersonUtils.newPersonDefaultResponse();
        Category category = CategoryUtils.newCategoryList().getFirst();

        return EmployeeByRepairResponse.builder()
                .id(1L)
                .person(person)
                .category(category)
                .build();
    }

    public static EmployeeByRepairResponse newEmployeeByRepairByAddEmployee() {
        Category category = CategoryUtils.newCategoryList().getFirst();
        PersonDefaultResponse person = PersonUtils.newPersonDefaultResponse();

        return EmployeeByRepairResponse.builder()
                .id(1L)
                .person(person)
                .category(category)
                .build();
    }

    public static EmployeeByRepairResponse newEmployeeByRepairGetEmployees() {
        Category category = CategoryUtils.newCategoryList().getFirst();
        PersonDefaultResponse person = PersonUtils.newPersonDefaultResponse();

        return EmployeeByRepairResponse.builder()
                .id(1L)
                .person(person)
                .category(category)
                .build();
    }

    public static EmployeePutRequest newEmployeePutRequest() {
        Person personToUpdate = PersonUtils.newPersonList().getFirst().withAddress(AddressUtils.newAddressToSave());
        personToUpdate.getPhones().forEach(phone -> {
            phone.setId(null);
            phone.setPerson(null);
        });

        Category category = CategoryUtils.newCategoryList().get(1);

        return EmployeePutRequest.builder()
                .id(1L)
                .name("Novo Nome")
                .lastName("Novo Sobrenome")
                .categoryId(category.getId())
                .phones(personToUpdate.getPhones())
                .address(personToUpdate.getAddress())
                .build();
    }

    public static Employee newEmployeeToUpdate() {
        Person personToUpdate = PersonUtils.newPersonToUpdate();
        Category categoryToUpdate = CategoryUtils.newCategoryList().get(1);

        return newEmployeeList().getFirst().withPerson(personToUpdate).withCategory(categoryToUpdate);
    }
}
