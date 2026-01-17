package pe.jsaire.springtravel.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRequest implements Serializable {

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 20)
    private String dni;

    @NotBlank
    @Size(max = 50)
    private String fullName;

    @NotBlank
    @Size(max = 20)
    private String phoneNumber;
}
