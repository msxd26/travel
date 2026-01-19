package pe.jsaire.springtravel.models.entity.documents;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "app_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AppUserDocument implements Serializable {

    @Id
    private String id;
    private String dni;
    private String username;
    @ToString.Exclude
    private String password;
    private Role role;
    private boolean enabled;

}
