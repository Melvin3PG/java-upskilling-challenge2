package net.example.finance.mybank.serviceimpl;

import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserObject;
import net.example.finance.mybank.model.repository.LoginRepository;
import net.example.finance.mybank.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserObject authenticate(String username, LoginObject accDetails) {
        return loginRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Account not available for Username : "+username));
    }
}
