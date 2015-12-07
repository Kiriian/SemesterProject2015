/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Pernille
 */
public class NoSuchFlightFoundException extends Exception 
{
    public NoSuchFlightFoundException(String s){
       super(s);
   }

}
