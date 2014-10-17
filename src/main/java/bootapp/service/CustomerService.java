package bootapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bootapp.domain.Customer;

public interface CustomerService {
	Customer save(Customer customer);

	Customer selectCustomer(Long id);

	Page<Customer> getAllCustomers(Pageable pageable);
}
