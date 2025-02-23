package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.UserRegistration;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
@Value("${image.upload.dir}")
    private String uploadDir;

   //List All Data show//
    public List<UserRegistration> getAllUserRegistrations() {
        return userRepository.findAll();
    }

    public UserRegistration getUserRegistrationById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Registration not found with ID: " + id)
        );
    }


    //save Registation //
    @Transactional
    public void saveRegistration(UserRegistration userRegistration, MultipartFile imageFile) throws IOException {


        // Check if password and confirm password match
        if (!userRegistration.getPassword().equals(userRegistration.getConfirmpassword())) {
            throw new RuntimeException("Password and Confirm Password do not match!");
        }


        // Validate that either NID or Passport is set, but not both
        if ((userRegistration.getNidnumber() != null && !userRegistration.getNidnumber().isEmpty()) &&
                (userRegistration.getPassport() != null && !userRegistration.getPassport().isEmpty())) {
            throw new RuntimeException("Either NID number or Passport should be provided, but not both.");
        }





        //Image Use//
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = saveImage(imageFile);
            userRegistration.setImage(imageFileName); // Save the first image filename
        }


        userRepository.save(userRegistration);
    }


  //save image and image new name add//
    public String saveImage(MultipartFile file) throws IOException{
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }
//Generate a unique filename//
    String filename =  UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
    Path filePath = uploadPath.resolve(filename);
//save the file//
    Files.copy(file.getInputStream(), filePath);


    return filename;
}


//Update Registation data //

    @Transactional
    public void updateUserRegistation(int id,UserRegistration userRegistration,MultipartFile imageFile)throws IOException{
        UserRegistration existingUserRegistration=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with this ID"));


      //at first data set then data get update//
        existingUserRegistration.setAddress(userRegistration.getAddress());
        existingUserRegistration.setDob(userRegistration.getDob());
        existingUserRegistration.setCountry(userRegistration.getCountry());
        existingUserRegistration.setImage(userRegistration.getImage());


        // Handle image upload for "imageFile"
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = saveImage(imageFile);
            existingUserRegistration.setImage(imageFileName);
        }

        // Save updated registration
        userRepository.save(existingUserRegistration);
    }
    public UserRegistration findByid(int id) {
        return userRepository.findById(id).get();
    }




    public  UserRegistration findById(int id){
        return   userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Registration Not Found by this Id"));
    }



    //find By Email JWT//
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(()->  new UsernameNotFoundException("User Not Found With this Email Address"));

    }
}
