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
public class ErrorObject
{

    private int httpError;
    private int errorCode;
    private String message;

    public ErrorObject(int httpError, int errorCode, String message)
    {
        this.httpError = httpError;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getHttpError()
    {
        return httpError;
    }

    public void setHttpError(int httpError)
    {
        this.httpError = httpError;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
