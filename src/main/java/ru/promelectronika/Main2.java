package ru.promelectronika;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main2 {
    public static void main(String[] args) {
        Employee employee = new Employee("i","i",2);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(employee);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }
}
 class Employee {
    private String firstName;
    private String lastName;
    private int age;

     public Employee(String firstName, String lastName, int age) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.age = age;
     }

     public String getFirstName() {
         return firstName;
     }

     public String getLastName() {
         return lastName;
     }

     public int getAge() {
         return age;
     }
 }
