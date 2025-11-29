package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialMapper credentialMapper;


    public CredentialsService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Creating CredentialsService bean");
    }


    public int AddCredential(Credential credential){
        return credentialMapper.addCredential(credential);
    }

    public int updateCredential(Credential credential){
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(Integer credentialId){
        return this.credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getAllCredential (Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }


}
