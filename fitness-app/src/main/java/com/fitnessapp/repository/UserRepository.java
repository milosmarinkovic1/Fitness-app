package com.fitnessapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnessapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
Optional<User> findByEmail(String email);
//Zašto Optional<User>? Zato što korisnik možda ne postoji (null)
//Sa Optional<User> — dobiješ "kutiju" koja može biti prazna ili puna. 

//Spring će automatski napraviti SQL:
// SELECT * FROM users WHERE email = ?

List<User> findByFullName(String fullName);
// Lista -zato što više ljudi može imati isto ime

/*
Spring čita ime metode i deli je na delove 
svaki put kad naiđe na veliko slovo.
Zato moraš tačno da pratiš pravilo:
findBy + ImePolja (prvo slovo veliko!)
*/

List<User> findByAccountStatus(String accountStatus);   

List<User> findByDateOfBirth(LocalDate dateOfBirth );
//Spring će automatski napraviti SQL:
//SELECT * FROM users WHERE date_of_birth = ?


List<User> findByGenderAndAccountStatus(String gender, String accountStatus);
// SELECT * FROM users WHERE gender = ? AND account_status = ?

List<User> findByDateOfBirthGreaterThanAndAccountStatus(
    LocalDate dateOfBirth, 
    String accountStatus
);
/*
Spring čita ovo kao:
findBy → pronađi po
DateOfBirth → polje dateOfBirth
GreaterThan → veće od (>)
And → i
AccountStatus → polje accountStatus
*/

List<User> findByFullNameContaining(String text);
//SELECT * FROM users WHERE full_name LIKE '%Mark%'
//On automatski doda % pre i posle tvog teksta!
// Pronaći će: "Marko", "Marković", "Darko Markić"...

List<User> findByEmailEndingWith(String text);
//SELECT * FROM users WHERE email LIKE '%@gmail.com'

List<User> findByRegistrationDateBetween(LocalDate pocetak, LocalDate kraj);

}