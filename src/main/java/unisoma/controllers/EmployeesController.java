package unisoma.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unisoma.dto.EmployeeDTO;
import unisoma.dto.TaxEmployeeDTO;
import unisoma.entities.Employee;
import unisoma.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    // End-Point para cadastrar novo funcionário
    @PostMapping("/newRegister")
    public ResponseEntity<EmployeeDTO> register(@Valid @RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.register(employee));
    }

    // End-Point para cadastrar novo funcionário
    @PostMapping("/calculateSalary")
    public EmployeeDTO Calculate(@RequestBody Employee employee) {
        return EmployeeDTO.convertCalculateToDTO(employeeService.calculate(employee));
    }

    // End-Point para calcular imposto do funcionário
    @GetMapping("/calculaTaxa/cpf/{cpf}")
    public TaxEmployeeDTO CalculateTax(@PathVariable String cpf) {
        return employeeService.calculateTax(cpf);
    }

    // End-Point para buscar todos os funcionário
    @GetMapping("/listAll")
    public List<EmployeeDTO> GetAll() {
        List<Employee> employeeList = employeeService.findAll();
        return employeeList.stream().map(employee -> EmployeeDTO.convertToDTO(employee)).collect(Collectors.toList());
    }

    // End-Point para buscar funcionários por nome
    @GetMapping("/findByName/{name}")
    public List<EmployeeDTO> GetByName(@PathVariable String name) {
        List<Employee> employeeList = employeeService.findAllByNameContainingIgnoreCase(name);
        return employeeList.stream().map(employee -> EmployeeDTO.convertToDTO(employee)).collect(Collectors.toList());
    }

    // End-Point para buscar funcionários por cpf
    @GetMapping("/findByCpf/{cpf}")
    public ResponseEntity<Employee> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(employeeService.findByCpf(cpf));
    }

}
