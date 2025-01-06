package org.babagroup.services;

import org.babagroup.models.GoogleLogin;
import org.babagroup.models.Otp;
import org.babagroup.models.User;
import org.babagroup.resreq.UserRegistrationReq;
import org.babagroup.resreq.UserResponse;

public interface UserServices {
    User getUserByEmail();
    User getAdminByRestaurant();
    UserResponse updateUserDto(User user);
    Otp userReqOTP(GoogleLogin login);
    User mapToObject(UserRegistrationReq registrationReq);
    UserResponse mapToDto(User user);
    String getOtp(String OTP);
    String redirectToRegis();
    String sendEmailOtp(Otp otp);
}
