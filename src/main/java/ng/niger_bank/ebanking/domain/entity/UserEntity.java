package ng.niger_bank.ebanking.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ng.niger_bank.ebanking.domain.enums.Role;

import java.math.BigDecimal;

@Entity
@Table(name ="users_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends Base {

   private String firstName;

   private String lastName;

   private String otherName;

   private String email;

   private String password;

   private String gender;

   private String address;

   private String stateOfOrigin;

   private String phoneNumber;

   private String BVN;

   private String pin;

   private String accountNumber;

   private BigDecimal accountBalance;

   private String bankName;

   private String profilePicture;

   private String status;

   private Role role;



}
