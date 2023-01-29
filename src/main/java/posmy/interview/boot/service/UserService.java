package posmy.interview.boot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import posmy.interview.boot._enum.Role;
import posmy.interview.boot.entity.Book;
import posmy.interview.boot.entity.User;
import posmy.interview.boot.repository.UserRepository;
import posmy.interview.boot.request.CreateUserRequest;
import posmy.interview.boot.request.LoginRequest;
import posmy.interview.boot.request.RegisterRequest;
import posmy.interview.boot.request.UpdateUserRequest;
import posmy.interview.boot.response.AuthenticationResponse;
import posmy.interview.boot.response.UserResponse;
import posmy.interview.boot.util.CommonMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CommonMessage commonMessage;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String create(CreateUserRequest req) {
        var user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.valueOf(req.getRole()))
                .build();
        userRepository.save(user);
        return commonMessage.USER_CREATE_SUCCESS;
    }

    public String update(UpdateUserRequest req) {
        Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(req.getPassword() != null) user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setRole(Role.valueOf(req.getRole()));
            userRepository.save(user);
            return commonMessage.USER_UPDATE_SUCCESS;
        }
        return commonMessage.USER_IS_NOT_FOUND;
    }

    public String deleteById(Integer userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        if(optionalUser.isPresent()){
            User currentUser = optionalUser.get();
            if((currentUser.getRole().equals(Role.LIBRARIAN))
                    || (currentUser.getRole().equals(Role.MEMBER) && currentUser.getUsername().equals(currentUsername))){
                userRepository.deleteById(userId);
                return commonMessage.USER_DELETE_SUCCESS;
            }else{
                return commonMessage.USER_DELETE_NO_PRIVILEGE;
            }
        }
        return commonMessage.USER_IS_NOT_FOUND;
    }

    public List<UserResponse> showAll() {
        List<UserResponse> result = new ArrayList<UserResponse>();
        List<User> userList = new ArrayList<User>();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentUsername);
        if(optionalUser.isPresent()){
            User currentUser = optionalUser.get();
            userList = (currentUser.getRole().equals(Role.LIBRARIAN)) ? userRepository.findAll() : List.of(currentUser);
            for(User user : userList){
                result.add(UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .build());
            }
        }
        return result;
    }
}
