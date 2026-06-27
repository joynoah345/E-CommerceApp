package org.ecomapp.app.Service;

import org.ecomapp.app.Dto.AddressDTO;
import org.ecomapp.app.Dto.UserRequest;
import org.ecomapp.app.Dto.UserResponse;
import org.ecomapp.app.Entity.Address;
import org.ecomapp.app.Entity.SavedDeliveryAddresses;
import org.ecomapp.app.Entity.User;
import lombok.RequiredArgsConstructor;
import org.ecomapp.app.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public UserResponse addUser(UserRequest userRequest) {
        User user = new User();
        mapRequestToUser(user,userRequest);
        return mapToUserResponse(userRepository.save(user));
    }



    public String updateUser(Long id,UserRequest updatedUser) {
        User user = new User();
        System.out.println(userRepository.findAll());
        user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            if(updatedUser.getAddress() != null) {
                Address address = new Address();
                address.setCity(updatedUser.getAddress().getCity());
                address.setCountry(updatedUser.getAddress().getCountry());
                address.setStreet(updatedUser.getAddress().getStreet());
                address.setZipCode(updatedUser.getAddress().getZipCode());
                user.setAddress(address);
            }
            userRepository.save(user);
            return "User Updated Successfully";
        }
        return "User Not Found";
    }

    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return "User Not Found";
        }
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }

    public UserResponse fetchUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        if (user.getAddress() != null){
            AddressDTO address = new AddressDTO();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZipCode(user.getAddress().getZipCode());
            response.setAddress(address);
        }
        return response;
    }

    private void mapRequestToUser(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }
    }

    public Boolean addDeliveryAddress(Long userId, SavedDeliveryAddresses address) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return false;
        }
        address.setUserId(userId);
        user.getSavedDeliveryAddresses().add(address);//setSavedDeliveryAddresses(List.of(address));
        userRepository.save(user);
        return true;
    }

    public List<SavedDeliveryAddresses> fetchSavedAddresses(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return null;
        }
        return user.get().getSavedDeliveryAddresses();
    }
}
