package dbryzz.services.auth.security.service;


import dbryzz.services.auth.model.User;
import dbryzz.services.auth.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> dbUser = userRepository.findByEmail(email);
        logger.info("Fetched user : " + dbUser + " by " + email);
        return dbUser.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user email in the database for " + email));
    }

    public UserDetails loadUserById(Long id) {
        Optional<User> dbUser = userRepository.findById(id);
        logger.info("Fetched user : " + dbUser + " by " + id);
        return dbUser.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + id));
    }
}
