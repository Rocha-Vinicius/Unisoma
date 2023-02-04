package unisoma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unisoma.dto.EmployeeDTO;
import unisoma.dto.TaxEmployeeDTO;
import unisoma.entities.Employee;
import unisoma.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllByNameContainingIgnoreCase(String name) {
        return employeeRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Employee findByCpf(String cpf) {
        return employeeRepository.findByCpf(cpf);
    }

    // Validação de cadastro de novo Funcionário
    public EmployeeDTO register(Employee employee) {
        Employee existsEmployee = employeeRepository.findByCpf(employee.getCpf());

        if (existsEmployee != null) {
            throw new Error("Cpf de Funcionário já cadastrado!");
        }

        Employee registerEmployee = employeeRepository.save(employee);

        return EmployeeDTO.convertToDTO(registerEmployee);

    }

    // Método de calculo do novo Salario
    public Employee calculate(Employee employee) {
        Employee employeeData = employeeRepository.findByCpf(employee.getCpf());
        var salary = employeeData.getSalary();
        if (salary >= 0.00 && salary <= 400.00) {
            employeeData.setNewSalary(salary * 1.15);
            employeeData.setReadjustment(employeeData.getNewSalary() - employeeData.getSalary());
            employeeData.setPercentage("15%");
        } else if (salary >= 400.01 && salary <= 800.00) {
            employeeData.setNewSalary(salary * 1.12);
            employeeData.setReadjustment(employeeData.getNewSalary() - employeeData.getSalary());
            employeeData.setPercentage("12%");
        } else if (salary >= 800.01 && salary <= 1200.00) {
            employeeData.setNewSalary(salary * 1.10);
            employeeData.setReadjustment(employeeData.getNewSalary() - employeeData.getSalary());
            employeeData.setPercentage("10%");

        } else if (salary >= 1200.01 && salary <= 2000.00) {
            employeeData.setNewSalary(salary * 1.07);
            employeeData.setReadjustment(employeeData.getNewSalary() - employeeData.getSalary());
            employeeData.setPercentage("7");
        } else {
            employeeData.setNewSalary(salary * 1.04);
            employeeData.setReadjustment(employeeData.getNewSalary() - employeeData.getSalary());
            employeeData.setPercentage("4%");
        }
        employeeData.setSalary(employeeData.getNewSalary());

        employeeRepository.save(employeeData);

        return employeeData;

    }

    // Método de calculo do Imposto
    public TaxEmployeeDTO calculateTax(String cpf) {
        Employee employeeData = employeeRepository.findByCpf(cpf);
        var salary = employeeData.getSalary();
        var taxEmployeeDTO = new TaxEmployeeDTO();
        taxEmployeeDTO.setCpf(cpf);
        if (salary >= 0.00 && salary <= 2000.00) {
            taxEmployeeDTO.setPercentTax("Isento");
        } else if (salary >= 2000.01 && salary <= 3000.00) {
            taxEmployeeDTO.setTaxValue(salary * 0.08);
        } else if (salary >= 3000.01 && salary <= 4500.00) {
            taxEmployeeDTO.setTaxValue(salary * 0.18);
        } else {
            taxEmployeeDTO.setTaxValue(salary * 0.28);
        }

        return taxEmployeeDTO;

    }

}
