/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parttwo;

/**
 *
 * @author Student
 */
public class Login extends registration{//start of class
    
    public boolean loginUser(String name, String password){
        return name.equals(userName) && password.equals(passWord); 
    }
    
    public String returnLoginStatus(boolean loginStatus){
        if(loginStatus){
            return "Login success";
        }else{
            return "Login failed";
        }
    }
}//end of class
 
