package net.example.finance.mybank.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserObject;

import net.example.finance.mybank.model.entity.User;
import net.example.finance.mybank.model.repository.UserRepository;
import net.example.finance.mybank.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserObject postLogin(LoginObject login) {
        User user = userRepository.findByUsername(login.getUsername());

        if(user == null || !(user.getPassword().equals(login.getPassword())))
            return null;
        
        return modelMapper.map(user,UserObject.class);
    }  
}
