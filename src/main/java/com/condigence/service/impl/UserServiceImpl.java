package com.condigence.service.impl;


import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.AddressDTO;
import com.condigence.dto.ImageDTO;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.*;
import com.condigence.repository.*;
import com.condigence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setContact(user.getContact());
            userDTO.setUsername(user.getUsername());
            userDTO.setRegistered(user.isRegistered());
            userDTO.setActive(user.isActive());
            userDTO.setRegistered(user.isRegistered());
            Profile profile = profileRepository.findByUserId(user.getId());
            if (profile != null) {
                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setFirstName(profile.getFirstName());
                if (profile.getImageId() != null) {
                    Optional<Image> image = imageRepository.findById(profile.getImageId());
                    if (image.isPresent()) {
                        ImageDTO imageDTO = new ImageDTO();
                        imageDTO.setPic(image.get().getPic());
                        imageDTO.setId(image.get().getId());
                        profileDTO.setImage(imageDTO);
                    }
                }

                profileDTO.setId(profile.getId());
                userDTO.setProfile(profileDTO);
            }
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO getUserByContact(String contact) {
        User user = userRepository.findByContact(contact).get();
        UserDTO userDTO = new UserDTO();
        ProfileDTO profileDTO = new ProfileDTO();
        Profile profile = null;
        Image image = null;
        if (user != null) {

            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setContact(user.getContact());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            profile = profileRepository.findById(user.getId()).get();
            profileDTO.setFullName(profile.getFullName());
            profileDTO.setId(profile.getId());
            if (profile.getImageId() != null) {
                image = imageRepository.findById(profile.getImageId()).get();
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setPic(image.getPic());
                imageDTO.setId(image.getId());
                profileDTO.setImage(imageDTO);
            }
            userDTO.setProfile(profileDTO);
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> userData = userRepository.findById(userId);
        UserDTO userDTO = new UserDTO();
        Profile profile = null;
        if (userData.isPresent()) {
            User user = userData.get();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setContact(user.getContact());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setRegistered(user.isRegistered());
            userDTO.setActive(user.isActive());
            userDTO.setProfileUpdated(user.isProfileCompleted());
            if (userData.get().getId() != null) {
                profile = profileRepository.findByUserId(userData.get().getId());
                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setFirstName(profile.getFirstName());
                profileDTO.setId(profile.getId());
                if (profile.getImageId() != null) {
                    Image image = imageRepository.findById(profile.getImageId()).get();
                    ImageDTO imageDTO = new ImageDTO();
                    imageDTO.setPic(image.getPic());
                    imageDTO.setId(image.getId());
                    profileDTO.setImage(imageDTO);
                }
                // check if user Has Address
                List<Address> addresses = getAllAddressesByUserId(user.getId());
                if (addresses.size() > 0) {
                    profileDTO.setAddresses(addresses);
                }
                userDTO.setProfile(profileDTO);
            }

            return userDTO;
        } else {
            return UserDTO.builder().id("0").build();
        }
    }

    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }


    @Override
    public void addBalance(String userId, int amount) {
        Wallet wallet = getBalance(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }

    @Override
    public Wallet getBalance(String userId) {
        return walletRepository.getByUserId(userId);
    }


    @Override
    public UserDTO updateUserProfile(ProfileBean profileBean) {

        User user = userRepository.findById(profileBean.getId()).get();
        Profile profile = new Profile();
        Image image = new Image();

        if (profileBean.getImageId() != null || profileBean.getImageId() != "") {
            image = imageRepository.findById(profileBean.getImageId()).get();
            profile.setImageId(image.getId());
        }
        if (profileBean.getEmail() != null || profileBean.getEmail() != "") {
            user.setEmail(profileBean.getEmail());
        }

        if (profileBean.getContact() != null || profileBean.getContact() != "") {
            user.setContact(profileBean.getContact());
        }

//        if (profileBean.getName() != null || profileBean.getName() != "") {
//            user.setFirstName(profileBean.getName());
//        }

        if (profileBean.getId() != null) {  // it means profile is already exist for user
            profile = profileRepository.findById(user.getId()).get();
            profile.setImageId(image.getId());

        }

        profile = profileRepository.save(profile);
        // user.setProfileId(profile.getId());
        user = userRepository.save(user);

        // Now prepare User View Object

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setContact(user.getContact());
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        if (profile.getImageId() != null) {
            image = imageRepository.findById(profile.getImageId()).get();
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setPic(image.getPic());
            profileDTO.setImage(imageDTO);
        }
        profileDTO.setFullName(profile.getFullName());
        userDTO.setProfile(profileDTO);

        return userDTO;
    }

    @Override
    public ProfileDTO saveProfile(ProfileBean bean) {
        Profile profile = new Profile();
//        if (bean.getAddressId() != null) {
//            profile.setAddressId(bean.getAddressId());
//        }
        if (bean.getImageId() != null) {
            profile.setImageId(bean.getImageId());
        }
        Profile savedProfile = profileRepository.save(profile);

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId(savedProfile.getId());
        if (profile.getImageId() != null) {
            Image image = imageRepository.findById(savedProfile.getImageId()).get();
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setPic(image.getPic());
            profileDTO.setImage(imageDTO);
        }
        return profileDTO;
    }

    @Override
    public ProfileDTO getProfileById(String id) {
        Profile profile = profileRepository.findById(id).get();
        if (profile == null) {
            return null;
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setNickName(profile.getNickName());
        profileDTO.setSecondaryContact(profile.getSecondaryContact());
        profileDTO.setSecondaryEmail(profile.getSecondaryEmail());

        AddressDTO addressDTO = null;
        if (profile.getId() != null) {
            Address address = addressRepository.findById(profile.getUserId()).get();
            addressDTO = new AddressDTO();
            // profileDTO.setAddress(addressDTO);
        }
        if (profile.getImageId() != null) {
            ImageDTO imageDTO = new ImageDTO();
            Image image = imageRepository.findById(profile.getImageId()).get();
            imageDTO.setImageId(image.getId());
            imageDTO.setId(image.getId());
            imageDTO.setPic(image.getPic());
            imageDTO.setName(image.getName());
            profileDTO.setImage(imageDTO);
        }
        return profileDTO;
    }

    @Override
    public void deleteProfileById(String id) {
        Profile profile = profileRepository.findById(id).get();
        profileRepository.deleteById(profile.getId());
    }


    @Override
    public User findByUserContact(String contact) {
        return userRepository.findByContact(contact).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO saveUser(UserBean userBean) {

        User user = new User();
        user.setEmail(userBean.getEmail());
        user.setContact(userBean.getContact());
        //  user.setFirstName(userBean.getFirstName());

        Profile profile = new Profile();
        profile.setImageId(userBean.getImageId());
//        if (userBean.getId() != null) {
//            profile = profileRepository.findById(userBean.getId()).get();
//          //  user.setProfileId(profile.getId());
//        } else {
//            profile = profileRepository.save(profile);
//          //  user.setProfileId(profile.getId());
//        }
        user.setOtp("1234");


        user.setUsername("BrahmaShakti");
        user.setPassword("password");
//        user.setPassword(bCryptPasswordEncoder
//                .encode("condigence"));
        User userObj = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userObj.getId());
        userDTO.setContact(userObj.getContact());
        userDTO.setEmail(userObj.getEmail());
        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setId(userObj.getProfileId());
//        profileDTO.setFullName(userObj.getFirstName());
        Image image = imageRepository.findById(profile.getImageId()).get();
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setPic(image.getPic());
        imageDTO.setName(image.getName());
        profileDTO.setImage(imageDTO);
        userDTO.setProfile(profileDTO);

        return userDTO;

    }


    @Override
    public boolean isUserExists(String contact) {
        return userRepository.findByContact(contact) != null;
    }

    @Override
    public boolean isUserIdExists(String id) {
        return userRepository.findById(id).isPresent();
    }

    /////////////////////////////
    @Override
    public Address saveAddress(AddressDTO dto) {
        Address address = new Address();
        List<Address> addresses = getAllAddressesByUserId(dto.getUserId());
        if (addresses.size() == 0) {
            // if user has no address then make this as default
            address.setIsDefault("Y");
        } else {
            address.setIsDefault("N");
        }
        address.setType(dto.getType());
        address.setLine1(dto.getLine1());
        address.setLine2(dto.getLine2());
        address.setPin(dto.getPin());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setUserId(dto.getUserId());
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getAllAddressesByUserId(String id) {
        return addressRepository.findByUserId(id);
    }

    @Override
    public List<Address> getAllAddressesByContact(String contact) {
        return null;
    }

    @Override
    public Optional<Address> getAddressById(String id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> getAddressByUserId(String id) {
        return addressRepository.findOneByUserId(id);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddressById(String id) {
        addressRepository.deleteById(id);
    }

}