package bootapp.web;

import javax.validation.Valid;

import net.spy.memcached.tapmessage.RequestMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.research.ws.wadl.Request;

import bootapp.domain.Customer;
import bootapp.service.CustomerService;

@Controller
public class CustomerController {
	protected static Logger log = LoggerFactory.getLogger( CustomerController.class );
	
	@Autowired
	private CustomerService customerService;

    @RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
    public String selectUser(@PathVariable Long id, Model model) {
    	Customer customer = customerService.selectCustomer(id);
    	model.addAttribute("customer", customer);
    	log.info(customer.getName());
        return "index";
    }

    @RequestMapping(value = "/customer" ,method = RequestMethod.POST)
    public String createUser(@RequestParam @Valid String name) {
    	Customer customer = new Customer();
    	customer.setName(name);
    	Customer savedCustomer = customerService.save(customer);
    	log.info(savedCustomer.getId() + ":" + savedCustomer.getName());

        return "index";
    }

    @RequestMapping(value = "/customer" ,method = RequestMethod.GET)
    public String allUser(Model model, Pageable pageable) {
        PageWrapper<Customer> page = new PageWrapper<Customer>(customerService.getAllCustomers(pageable), "/customer");
        model.addAttribute("page", page);
        return "page";
    }
}