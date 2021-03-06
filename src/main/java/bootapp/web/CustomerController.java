package bootapp.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import bootapp.domain.Customer;
import bootapp.service.CustomerService;

@Controller
public class CustomerController {
	protected static Logger log = LoggerFactory.getLogger( CustomerController.class );
	
	@Autowired
	private CustomerService customerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
    public String selectUser(@PathVariable Long id, Model model) {
    	Customer customer = customerService.selectCustomer(id);
    	model.addAttribute("customer", customer);
    	log.info(customer.getName());
        return "index";
    }

    @RequestMapping(value = "/customer/add" ,method = RequestMethod.POST)
    public String create(@Valid Customer customer, BindingResult result,
            SessionStatus status) {
        if (result.hasErrors()) {
            return "add";
        } else {
            this.customerService.save(customer);
            status.setComplete();
            return "redirect:/customer";
        }
    }

    @RequestMapping(value = "/customer" ,method = RequestMethod.GET)
    public String allUser(Model model, Pageable pageable) {
        PageWrapper<Customer> page = new PageWrapper<Customer>(customerService.getAllCustomers(pageable), "/customer");
    	model.addAttribute("page", page);
        List<Customer> customers = page.getContent();
        model.addAttribute("customers", customers);
        return "page";
    }

    @RequestMapping(value = "customer/add", method = RequestMethod.GET)
    public String add(Model model) {
    	log.info("/customer/add");
    	Customer customer = new Customer();
    	model.addAttribute("customer", customer);
        return "add";
    }

    @RequestMapping(value = "customer/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
    	Customer customer = customerService.selectCustomer(id);
    	model.addAttribute("customer", customer);
        return "add";
    }

    @RequestMapping(value = "customer/{id}/edit", method = RequestMethod.PUT)
    public String update(@PathVariable Long id, @Valid Customer customer, BindingResult result,
            SessionStatus status) {
        if (result.hasErrors()) {
            return "add";
        } else {
        	customer.setId(id);
            this.customerService.save(customer);
            status.setComplete();
            return "redirect:/customer";
        }
    }

}
