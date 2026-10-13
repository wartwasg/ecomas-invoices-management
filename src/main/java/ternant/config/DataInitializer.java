package ternant.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ternant.userModule.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner createRolesInDbAndAddSystemAdministrator(){
        return args -> {
            Roles topManager = roleRepository.findByRoleName(AppRole.ROLE_TOP_MANAGER)
                    .orElseGet(()->{
                        Roles top_manager = Roles.builder()
                                .roleName(AppRole.ROLE_TOP_MANAGER)
                                .build();
                        return roleRepository.save(top_manager);
                    });
            Roles regularManager = roleRepository.findByRoleName(AppRole.ROLE_REGULAR_MANAGER)
                    .orElseGet(()->{
                        Roles reg_manager = Roles.builder()
                                .roleName(AppRole.ROLE_REGULAR_MANAGER)
                                .build();
                        return roleRepository.save(reg_manager);
                    });

            List<Roles> roles = new ArrayList<>();

            roles.add(topManager);

            User adminInDb = userRepository.findByEmail("superadmin@ledger.com")
                    .orElseGet(() -> {
                                User admin = User.builder()
                                        .email("superadmin@ledger.com")
                                        .password(passwordEncoder.encode("SuperAdmin"))
                                        .invoices(null)
                                        .roles(roles)
                                        .build();

                                return userRepository.save(admin);
                            }
                    );
        };
    }
}
