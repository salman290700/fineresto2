package org.babagroup.controllers;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.babagroup.enums.USER_ROLE;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Address;
import org.babagroup.models.Role;
import org.babagroup.models.User;
import org.babagroup.repository.RoleRepository;
import org.babagroup.repository.UserRepository;
import org.babagroup.resreq.AuthRes;
import org.babagroup.resreq.LoginReq;
import org.babagroup.resreq.RestoOwnerCreateReq;
import org.babagroup.resreq.UserRegistrationReq;
import org.babagroup.services.JwtServices;
import org.babagroup.services.UserServices;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.*;

@ApplicationScoped
@Path("/auth")
public class AuthController {
    @Inject
    UserRepository userRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    JsonWebToken jsonWebToken;
    @Inject
    JwtServices jwtServices;

    @Inject
    UserServices userServices;

    String user_error = "User not found";
    String Email_error = "Email not found";
    String user_error_exists = "Email is already used by another account";
    String otp_exp = "Your Otp is Expired";
    String otp_false = "False Otp";

    @Path("/create-user-test")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response createUser(UserRegistrationReq userData) {
//        Checking email exists
        if(userRepository.existsByEmail(userData.getEmail())) {
            return Response.noContent().build();
        }
        Log.info(userData);
        String uuid = UUID.randomUUID().toString();
        Log.info(uuid);
        User user = new User();
        user.setCreatedAt(new Date());
        Log.info(user);
        user.setEmail(userData.getEmail());
        Log.info(user.getEmail());
        user.setPassword(userData.getPassword());
        Log.info(user.getPassword());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        user.setCreatedAt(new Date());
        user.setId(uuid);
        user.setUpdatedAt(new Date());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        Log.info(user.getUpdatedBy());
//        Role User
        Role newRole = new Role();
        newRole.setName(USER_ROLE.user.toString());
        newRole.setCreatedBy(uuid);
        newRole.setCreatedAt(new Date());
        newRole.setUpdatedBy(uuid);
        newRole.setUpdatedAt(new Date());
        Log.info(newRole);
        Role role = roleRepository.findByName(USER_ROLE.user.toString()).orElse(roleRepository.save(newRole));
        Log.info(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
//        User's address
        Address address = new Address();
        address.setUpdatedBy(uuid);
        address.setCreatedBy(uuid);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());
        address.setCountry(userData.getCountry());
        address.setState(userData.getState());
        address.setStreet(userData.getStreet());
        Log.info(address);
        List<Address> addressList =  new ArrayList<>();
        addressList.add(address);
        user.setAddresses(addressList);
        user.setContactInformation(userData.getContactInformation());
        user.setOrders(new ArrayList<>());
        User savedUser = userRepository.save(user);
        return Response.ok(savedUser).build();
    }

    @Path("/create-resto-owner")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response createRestoOwner(UserRegistrationReq userData) {
        if(userRepository.existsByEmail(userData.getEmail())) {
            return Response.noContent().build();
        }
        Log.info(userData);
        String uuid = UUID.randomUUID().toString();
        Log.info(uuid);
        User user = new User();
        user.setCreatedAt(new Date());
        Log.info(user);
        user.setEmail(userData.getEmail());
        Log.info(user.getEmail());
        user.setPassword(userData.getPassword());
        Log.info(user.getPassword());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        user.setCreatedAt(new Date());
        user.setId(uuid);
        user.setUpdatedAt(new Date());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        Log.info(user.getUpdatedBy());
//        Role User
        Role newRole = new Role();
        newRole.setName(USER_ROLE.owner.toString());
        newRole.setCreatedBy(uuid);
        newRole.setCreatedAt(new Date());
        newRole.setUpdatedBy(uuid);
        newRole.setUpdatedAt(new Date());
        Log.info(newRole);
        Role role = roleRepository.findByName(USER_ROLE.user.toString()).orElse(roleRepository.save(newRole));
        Log.info(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
//        User's address
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setUpdatedBy(uuid);
        address.setCreatedBy(uuid);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());
        address.setCountry(userData.getCountry());
        address.setState(userData.getState());
        address.setStreet(userData.getStreet());
        Log.info(address);
        List<Address> addressList =  new ArrayList<>();
        addressList.add(address);
        user.setAddresses(addressList);
        user.setContactInformation(userData.getContactInformation());
        user.setOrders(new ArrayList<>());
        User savedUser = userRepository.save(user);
        return Response.ok(userServices.mapToDto(savedUser)).build();
    }

    @Path("/create-resto-admin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response createRestoAdmin(UserRegistrationReq userData) {
        if(userRepository.existsByEmail(userData.getEmail())) {
            return Response.noContent().build();
        }
        Log.info(userData);
        String uuid = UUID.randomUUID().toString();
        Log.info(uuid);
        User user = new User();
        user.setCreatedAt(new Date());
        Log.info(user);
        user.setEmail(userData.getEmail());
        Log.info(user.getEmail());
        user.setPassword(userData.getPassword());
        Log.info(user.getPassword());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        user.setCreatedAt(new Date());
        user.setId(uuid);
        user.setUpdatedAt(new Date());
        user.setCreatedBy(uuid);
        user.setUpdatedBy(uuid);
        Log.info(user.getUpdatedBy());
//        Role User
        Role newRole = new Role();
        newRole.setName(USER_ROLE.admin.toString());
        newRole.setCreatedBy(uuid);
        newRole.setCreatedAt(new Date());
        newRole.setUpdatedBy(uuid);
        newRole.setUpdatedAt(new Date());
        Log.info(newRole);
        Role role = roleRepository.findByName(USER_ROLE.user.toString()).orElse(roleRepository.save(newRole));
        Log.info(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
//        User's address
        Address address = new Address();
        address.setUpdatedBy(uuid);
        address.setCreatedBy(uuid);
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());
        address.setCountry(userData.getCountry());
        address.setState(userData.getState());
        address.setStreet(userData.getStreet());
        Log.info(address);
        List<Address> addressList =  new ArrayList<>();
        addressList.add(address);
        user.setAddresses(addressList);
        user.setContactInformation(userData.getContactInformation());
        user.setOrders(new ArrayList<>());
        User savedUser = userRepository.save(user);
        return Response.ok(savedUser).build();
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(LoginReq loginReq) {
        Log.info(BcryptUtil.bcryptHash(loginReq.getPassword()));

        User user = userRepository.findByEmail(loginReq.getEmail()).orElseThrow(() -> new DataError("Please put the right email or password"));
        if(!BcryptUtil.matches(loginReq.getPassword(),  user.getPassword())) {
            return Response.ok().build();
        }
        AuthRes res = new AuthRes(jwtServices.generateJwt(user), "Login Success");
        return Response.ok(res).build();
    }

    @Path("/forgetting-user-password/{email}/{token}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response forgettingUserPassword(@PathParam("email") String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataError(user_error));
        user.setPassword(BcryptUtil.bcryptHash(password));
        userRepository.save(user);
        String message = "Password already changed";
        String redirect = "http://localhost:8081/auth/login";
        HashMap<String, String> forget_auth = new HashMap<>();
        forget_auth.put(message, redirect);
        return Response.ok(forget_auth).build();
    }

    @Path("/get-otp-forget-password/{email}/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOtpForgetPassword(@PathParam("email") String email) {
//        Sending link to email with link that contain email & token
        if(!userRepository.existsByEmail(email)) {
            return Response.noContent().build();
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataError(user_error));
        String link = "http://localhost:8080/auth/forgetting-user-password/" + user.getEmail();
        return Response.ok(link).build();
    }
}
