package com.employee.pms.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.employee.pms.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

@Repository
public class H2EmployeeRepository {

    private final SessionFactory sessionFactory;

    public H2EmployeeRepository(
            @Qualifier("h2SessionFactory")
            SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }
    public Employee saveEmployee(Employee employee) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                session.persist(employee);

                transaction.commit();

                return employee;

            } catch (RuntimeException e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
    public Employee getEmployeeById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.find(Employee.class, id);
        }
    }
    public List<Employee> getAllEmployees() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Employee", Employee.class
            ).getResultList();
        }
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Employee existingEmployee =
                        session.find(Employee.class, id);

                if (existingEmployee == null) {
                    throw new IllegalArgumentException(
                            "Employee not found with ID: " + id
                    );
                }

                existingEmployee.setName(updatedEmployee.getName());
                existingEmployee.setEmail(updatedEmployee.getEmail());
                existingEmployee.setDepartment(updatedEmployee.getDepartment());
                existingEmployee.setSalary(updatedEmployee.getSalary());

                transaction.commit();

                return existingEmployee;

            } catch (RuntimeException e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
    public void deleteEmployee(Long id) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Employee employee = session.find(Employee.class, id);

                if (employee == null) {
                    throw new IllegalArgumentException(
                            "Employee not found with ID: " + id
                    );
                }

                session.remove(employee);

                transaction.commit();

            } catch (RuntimeException e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
}
