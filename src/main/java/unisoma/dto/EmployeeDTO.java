package unisoma.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import unisoma.entities.Employee;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    private String name;
    private String cpf;
    private Date birthDate;
    private String phone;
    private String address;
    private Double salary;
    private Double newSalary;
    private String percentage;
    private Double readjustment;

    public static EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setCpf(employee.getCpf());
        employeeDTO.setBirthDate(employee.getBirthDate());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
    }

    public static EmployeeDTO convertCalculateToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setCpf(employee.getCpf());
        employeeDTO.setNewSalary(employee.getNewSalary());
        employeeDTO.setPercentage(employee.getPercentage());
        employeeDTO.setReadjustment(employee.getReadjustment());
        return employeeDTO;
    }

}
