package bootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootapp.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
