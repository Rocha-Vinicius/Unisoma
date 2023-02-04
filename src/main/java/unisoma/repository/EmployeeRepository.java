package unisoma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import unisoma.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public List<Employee> findAll();

    public List<Employee> findAllByNameContainingIgnoreCase(String name);

    public Employee findByCpf(String cpf);

    public Employee save(Employee employee);
}
