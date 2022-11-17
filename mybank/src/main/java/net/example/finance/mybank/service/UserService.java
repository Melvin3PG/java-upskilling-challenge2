package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.User;

public interface UserService {

	User findByUsername(String username);
}
