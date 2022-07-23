package com.gl.employeesapi.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gl.employeesapi.model.Role;
import com.gl.employeesapi.model.Employee;
import com.gl.employeesapi.model.User;
import com.gl.employeesapi.repository.RoleRepository;
import com.gl.employeesapi.repository.EmployeeRepository;
import com.gl.employeesapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootStrapAppData implements ApplicationListener<ApplicationReadyEvent>{
	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		for(int i=0; i<5; i++) {
			Employee employee = new Employee();
			employee.setFirstName("Ravi" + i);
			employee.setLastName("kumar" + i);
			employee.setEmail("raviEmail@gmail.com" + i);
			employeeRepository.save(employee);
		}
		
		
		Role userRole=new Role();
		userRole.setRoleName("ROLE_USER");
		
		Role adminRole=new Role();
		adminRole.setRoleName("ROLE_ADMIN");
		
		
		User user=new User();
		user.setUserName("ravi");
		user.setPassword(this.passwordEncoder.encode("test"));
		
		User admin=new User();
		admin.setUserName("raja");
		admin.setPassword(this.passwordEncoder.encode("admin"));
				
		admin.addRole(adminRole);
		admin.addRole(userRole);
		
		user.addRole(userRole);
		
		userRepository.save(user);
		userRepository.save(admin);
		roleRepository.save(userRole);
		roleRepository.save(adminRole);
		
	}

}
