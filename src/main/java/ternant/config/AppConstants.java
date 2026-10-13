package ternant.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ternant.userModule.AppRole;
import ternant.userModule.RoleRepository;
import ternant.userModule.Roles;

@Component
public class AppConstants {

    public static final String pageNumber = "0";
    public static final String pageSize = "10";
    public static final String orderBy = "amount";
    public static final String order = "ASC";
}
