package com.divyanshu.parkSpotter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  @NotBlank(message = "First Name Cannot be Blank")
  String firstName;
  @NotBlank(message = "Last Name Cannot be Blank")
  String lastName;
  @NotBlank(message = "Email Cannot be Blank")
  String email;
  @NotBlank(message = "Password Cannot be Blank")
  String password;
  @NotBlank(message = "Phone Number Cannot be Blank")
  String phoneNumber;

  boolean isOwner;
}
