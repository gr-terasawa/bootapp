package bootapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import bootapp.domain.Customer;
import bootapp.domain.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer selectCustomer(Long id) {
		return customerRepository.getOne(id);
	}

	@Override
	public Page<Customer> getAllCustomers(Pageable pageable) {
		Page<Customer> customerList = customerRepository.findAll(pageable);
		return customerList;
	}
}
