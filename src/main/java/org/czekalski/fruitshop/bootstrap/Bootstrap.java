package org.czekalski.fruitshop.bootstrap;

import org.czekalski.fruitshop.domain.Category;
import org.czekalski.fruitshop.domain.Customer;
import org.czekalski.fruitshop.repositories.CategoryRepository;
import org.czekalski.fruitshop.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();


    }

    private void loadCustomers() {
        Customer customer1=new Customer();
        customer1.setLastName("Xinski");
        customer1.setFirstName("X");

        Customer customer2=new Customer();
        customer2.setLastName("Yski");
        customer2.setFirstName("Y");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        System.out.println("Data Loaded Customers = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + categoryRepository.count());
    }


}