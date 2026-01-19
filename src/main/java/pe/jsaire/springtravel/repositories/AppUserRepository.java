package pe.jsaire.springtravel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.jsaire.springtravel.models.entity.documents.AppUserDocument;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUserDocument, String> {

    Optional<AppUserDocument> findByUsername(String username);
}
