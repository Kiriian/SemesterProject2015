/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Role;
import entity.User;
import facades.RequestFacade;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Jeanette
 */
public class Test
{
    public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        UserFacade uf = new UserFacade();
        
        User u = new User();
        u.setPassword("test");
        u.setUserName("test");
        System.out.println(uf.saveUser(u));
    }
}
