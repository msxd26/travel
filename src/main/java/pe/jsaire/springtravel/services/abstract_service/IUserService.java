package pe.jsaire.springtravel.services.abstract_service;

import java.util.List;
import java.util.Map;

public interface IUserService {


    Map<String, Boolean> enabled(String username);

    Map<String, List<String>> addRole(String username, String role);

    Map<String, List<String>> removeRole(String username, String role);
}
