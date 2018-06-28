package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Exception.GestionRoleException;
import com.pictimegroupe.FrontVendeur.testWebservice.User;

import javax.json.JsonArrayBuilder;

public interface UserServices {
    JsonArrayBuilder connect(String login, String password)throws GestionRoleException;
    void addUser(User user) throws GestionRoleException;
}

