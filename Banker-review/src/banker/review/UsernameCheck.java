/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.review;

/**
 *
 * @author victor
 */
public class UsernameCheck {
    String username;
    
    public UsernameCheck(String username){
         this.username = username;
    }
    
    public Boolean isValid() {
        // regulqren izraz za proverka na validnost na email
        return this.username.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}");
    }
}
