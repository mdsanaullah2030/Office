package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column( nullable = false)
    private  String userid;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNo;
    private String address;
    private  String country;
    private Date dob;



    private String referralCode;


    private String nidnumber;

    private String passport;




    @PrePersist
    private void generateUserId() {
        if (this.userid == null || this.userid.isEmpty()) {
            this.userid = generateRandomUserId();
        }
    }



    private String generateRandomUserId() {
        int length = 6;
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder userIdBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            userIdBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return userIdBuilder.toString();
    }







    @Column(nullable = false)
    private boolean active;

    @Column()
    private boolean isLock;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    // ✅ Add these methods manually
    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
