package pe.jsaire.springtravel.repositories;


import org.springframework.data.repository.CrudRepository;
import pe.jsaire.springtravel.models.entity.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
