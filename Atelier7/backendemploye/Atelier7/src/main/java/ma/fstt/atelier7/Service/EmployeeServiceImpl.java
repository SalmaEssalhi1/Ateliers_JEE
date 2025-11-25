package ma.fstt.atelier7.Service;


import ma.fstt.atelier7.entities.Employee;
import ma.fstt.atelier7.Repository.EmployeeRepository;
import ma.fstt.atelier7.Service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        // Optionnel : vérifier unicité email
        repository.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Email déjà utilisé");
        });
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec id: " + id));
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setSalary(employee.getSalary());
        return repository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
