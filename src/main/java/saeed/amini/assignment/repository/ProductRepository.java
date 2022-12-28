package saeed.amini.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import saeed.amini.assignment.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
