package com.fitnessapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fitnessapp.dto.UserResponseDTO;
import com.fitnessapp.model.User;
import com.fitnessapp.repository.UserRepository;

@Service  
public class UserService {

    //Dependency Injection (@Autowired)
    //ubaci mi instancu UserRepository-ja ovde automatski.
    //Ne kreiraš je ručno sa new, Spring to radi za tebe
    @Autowired 
    private UserRepository userRepository;

   
    
    public List<UserResponseDTO> getAllUsers (){
        
        //Vraća listu DTO-ova (ne direktno User-e


         List<User> users = userRepository.findAll();
    //findAll() dolazi automatski iz JpaRepository 
    // vraća sve redove iz users tabele.

        return users.stream().map( //Za svaki element uradi 
             user -> { //Uzmi jednog user-a i uradi ovo
            UserResponseDTO dto = new UserResponseDTO();

            dto.setUserId(user.getUserId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setDateOfBirth(user.getDateOfBirth());
            dto.setGender(user.getGender());
            dto.setLastLogin(user.getLastLogin());
            dto.setAccountStatus(user.getAccountStatus());
            dto.setRegistrationDate(user.getRegistrationDate());
            return dto;
        }
        ).collect(Collectors.toList());

        /*
        Šta znači .stream().map().collect()?
        .stream() — pretvori listu u tok podataka
        .map() — za svaki element uradi transformaciju (User → DTO)
        .collect(Collectors.toList()) — skupi rezultate nazad u listu
        */



    }

    public UserResponseDTO getCurrentUser() {
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
    
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
    return convertToDTO(user);
}



private UserResponseDTO convertToDTO(User user) {
    UserResponseDTO dto = new UserResponseDTO();
    dto.setUserId(user.getUserId());
    dto.setFullName(user.getFullName());
    dto.setEmail(user.getEmail());
    dto.setDateOfBirth(user.getDateOfBirth());
    dto.setGender(user.getGender());
    dto.setLastLogin(user.getLastLogin());
    dto.setAccountStatus(user.getAccountStatus());
    dto.setRegistrationDate(user.getRegistrationDate());
    return dto;
}
   

}
