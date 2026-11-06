package kh.edu.istad.platform.iam.config;

import kh.edu.istad.platform.iam.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var existing = userRepository.findByUsernameOrEmail(username, username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)

        );

        if (existing == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return existing;


    }
}
