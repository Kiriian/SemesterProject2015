/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import facades.RequestFacade;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Jeanette
 */
public class Test
{
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        RequestFacade rf = new RequestFacade();
                
        System.out.println(rf.getFlights("CPH","STN","2016-01-04T23:00:00.000Z", 1));
    }
}
