package com.practice.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.batch.Employee;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeItemProcessor.class);

    @Override
    public Employee process(Employee emp) throws Exception {
        final String firstName = emp.getFirstName().toUpperCase();
        final String lastName = emp.getLastName().toUpperCase();

        final Employee transformedEmployee = new Employee(firstName, lastName);

        log.info("Converting (" + emp + ") into (" + transformedEmployee + ")");

        return transformedEmployee;
    }

}
