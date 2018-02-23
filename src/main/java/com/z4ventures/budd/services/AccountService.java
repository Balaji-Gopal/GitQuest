package com.z4ventures.budd.services;

import com.z4ventures.budd.models.Account;
import com.z4ventures.budd.models.Role;
import com.z4ventures.budd.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername( s );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
        }
    }

    public Account findAccountByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername( username );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }
    
    public Account findAccountById(Long userId) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findById( userId );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("User ID [%d] not found", userId));
        }
    }

    public Account registerUser(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.grantAuthority(Role.ROLE_USER);
        return accountRepo.save( account );
    }
    
   /* public Account updateBasicInfo(Account account) {
        return accountRepo.saveOrUpdate( account );
    }*/

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public boolean removeAuthenticatedAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account acct = findAccountByUsername(username);
        Long del = (long) accountRepo.deleteAccountById(acct.getId());
        return del > 0;
    }
    
    @Transactional
    public boolean updatePassword(String password,String username) {
	 	Account acct = findAccountByUsername(username);
	 	acct.setPassword(passwordEncoder.encode(password));
	 	return true;
    }
    
    @Transactional
    public boolean updateFirebaseToken(String firebase) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	//String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	Account account = (Account)authentication.getPrincipal();
    	Long userId = account.getId();
    	System.out.println("User ID::"+userId);
    	Account acct = findAccountById(userId);
    	acct.setFirebase(firebase);
    	return true;
    }

    @Transactional
	public boolean updateBasicInfo(Long userId,Account account) {
    	/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Account accountId = (Account)authentication.getPrincipal();
    	Long userId = accountId.getId();*/
    	Account accountDb = findAccountById(userId);
    	System.out.println("User firstname %s"+account.getFirstName());
		accountDb.setFirstName(account.getFirstName());
		accountDb.setLastName(account.getLastName());
		accountDb.setUsername(account.getUsername());
		accountRepo.save(accountDb);
		return true;
	}
}
