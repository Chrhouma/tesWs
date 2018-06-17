package com.pictimegroupe.FrontVendeur.testWebservice.services;
import com.pictimegroupe.FrontVendeur.testWebservice.User;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.UserRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public  class UserServicesImpl implements UserServices {
    @Autowired
    public HttpSession httpSession;

    @Autowired
    UserRepository userRepository;


    public UserServicesImpl() {

    }


    /**
     *
     *implementation de la méthode se conencter de l'utilisateur
     */
    @Override
    public void connect(String login, String pw) {
        List<User>ListUsers=(List<User>) userRepository.findAll();
        for(User user: ListUsers) {
            if(user.getLogin().equals(login) && user.getPassword().equals(pw)) {
                httpSession.setAttribute("userr", user.getLogin());
                httpSession.setAttribute("roleUser",user.getRole().getRights());
                System.out.println("je teset"+ user.getLogin()+user.getPassword());
                System.out.println("session"+httpSession.getAttribute("userr"));
                System.out.println("session"+httpSession.getAttribute("roleUser"));
            }
        }
    }
    /**
     * l'ajout d'un nouveau utilisateur est accessible si l'utilsateur possède
     *  la role add
     * @param login
     * @param password
     */
    @Override
    public void addUser(User user)  {
       if(httpSession.getAttribute("roleUser").equals("add")) {

            userRepository.save(user);
            System.out.println("je rajoute");
      }
      //  else{
       //  throw  new GestionRoleException("vous n'avez pas le droit d'ajouter un utilisateur");
     //  }

    }
}
