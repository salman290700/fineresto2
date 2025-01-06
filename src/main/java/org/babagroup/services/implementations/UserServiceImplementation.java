package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.GoogleLogin;
import org.babagroup.models.Otp;
import org.babagroup.models.User;
import org.babagroup.repository.OtpRepository;
import org.babagroup.repository.UserRepository;
import org.babagroup.resreq.UserRegistrationReq;
import org.babagroup.resreq.UserResponse;
import org.babagroup.services.UserServices;
import org.babagroup.utils.AddressUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImplementation implements UserServices {
    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    UserRepository userRepository;

    @Inject
    OtpRepository otpRepository;

    AddressUtils addressService = new AddressUtils();

    String user_not_found = "User not found";
    String user_error = "Please input the right email and password";
    String Email_error = "Email not found";
    String user_error_exists = "Email is already used by another account";

    @Override
    public User getUserByEmail() {
        return userRepository.findByEmail(jsonWebToken.getSubject()).orElseThrow(() -> new DataError(user_not_found));
    }

    @Override
    public User getAdminByRestaurant() {
        return null;
    }

    @Override
    public UserResponse updateUserDto(User user) {
        return new UserResponse(user.getEmail(), addressService.dtoToList(user.getAddresses()), user.getContactInformation());
    }

    @Override
    public Otp userReqOTP(GoogleLogin login) {
        int otp = new Random().nextInt(6);
//        Save otp to DB with expiration date containing redirect link
        Otp sendOtp = new Otp();
        sendOtp.setEmail(login.getEmail());
//        Set Expiration for 5 minutes
//        sendOtp.setExpiration_date();
//        Sending email
        return sendOtp;
    }

    @Override
    public User mapToObject(UserRegistrationReq registrationReq) {
        return null;
    }

    @Override
    public UserResponse mapToDto(User user) {
        return new UserResponse(user.getEmail(), addressService.dtoToList(user.getAddresses()), user.getContactInformation());
    }

    @Override
    public String getOtp(String OTP) {
        Otp getOtp = otpRepository.findByOtp(OTP).orElseThrow(() -> new DataError("Otp not found"));
//        return redirect Link
        return "/auth/create-resto-owner";
    }

    @Override
    public String redirectToRegis() {
        return "";
    }

    @Override
    public String sendEmailOtp(Otp otp) {

        return "";
    }
}
