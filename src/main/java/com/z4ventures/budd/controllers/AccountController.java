package com.z4ventures.budd.controllers;

import com.z4ventures.budd.models.Account;
import com.z4ventures.budd.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    //@PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/generic/account/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        account = accountService.registerUser(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
    
    @PostMapping("/generic/account/updatepwd")
    public ResponseEntity<Boolean> updatePassword(@RequestParam("username") String username, @RequestParam("password") String password){
		boolean updatedSatus = accountService.updatePassword(password, username);
		return new ResponseEntity<>(updatedSatus,HttpStatus.OK);
    }
    
    @PostMapping("/api/account/firebase")
    public ResponseEntity<Boolean> updateFirebase(@RequestParam("firebase") String firebase){
		boolean updatedSatus = accountService.updateFirebaseToken(firebase);
		return new ResponseEntity<>(updatedSatus,HttpStatus.OK);
    }
    
    @PostMapping("/api/account/updateBasicInfo/{userId}")
    public ResponseEntity<Boolean> updateBasicInfo(@RequestBody Account account,@PathVariable Long userId){
    	boolean updateBasicInfoStatus= accountService.updateBasicInfo(userId,account);
        return new ResponseEntity<>(updateBasicInfoStatus, HttpStatus.CREATED);
    }
    
    
    @PreAuthorize("isFullyAuthenticated()")
    @DeleteMapping("/api/account/remove")
    public ResponseEntity<GeneralController.RestMsg> removeAccount(Principal principal){
        boolean isDeleted = accountService.removeAuthenticatedAccount();
        if ( isDeleted ) {
            return new ResponseEntity<>(
                    new GeneralController.RestMsg(String.format("[%s] removed.", principal.getName())),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<GeneralController.RestMsg>(
                    new GeneralController.RestMsg(String.format("An error ocurred while delete [%s]", principal.getName())),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
