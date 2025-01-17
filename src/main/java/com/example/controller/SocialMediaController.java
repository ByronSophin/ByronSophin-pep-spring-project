package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account account){
        if(account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4){
            return ResponseEntity.status(400).body(null);
        }
        else if(accountService.matchAccount(account) != null){
            return ResponseEntity.status(409).body(null);
        }
        else{
            Account persistedAccount = accountService.addAccount(account);
            return ResponseEntity.status(200).body(persistedAccount);
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<Account> verifyLogin(@RequestBody Account account){
        Account returnedAccount = accountService.matchAccount(account);
        if(returnedAccount == null){
            return ResponseEntity.status(401).body(null);
        }
        else{
            return ResponseEntity.status(200).body(returnedAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        if(message.getMessageText().isBlank() || message.getMessageText() == null || message.getMessageText().length() > 255 || accountService.matchAccountId(message.getPostedBy()) == null){
            return ResponseEntity.status(400).body(null);
        }
        else{
            Message persistedMessage = messageService.addMessage(message);
            return ResponseEntity.status(200).body(persistedMessage);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> allMessages = messageService.getAllMessage();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable String messageId){
        Integer id = Integer.valueOf(messageId);
        Message returnedMessage = messageService.getMessage(id);
        return ResponseEntity.status(200).body(returnedMessage);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId){
        if(messageService.getMessage(Integer.valueOf(messageId)) == null){
            return ResponseEntity.status(200).body(null);
        }
        else{
            return ResponseEntity.status(200).body("1");
        }
        
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable String messageId, @RequestBody Message message){
        if(messageService.getMessage(Integer.valueOf(messageId)) == null || message.getMessageText().isBlank() || message.getMessageText() == null || message.getMessageText().length() > 255){
            return ResponseEntity.status(400).body(null);
        }
        else{
            Message oldMessage = messageService.getMessage(Integer.valueOf(messageId));
            oldMessage.setMessageText(message.getMessageText());
            messageService.addMessage(oldMessage);
            return ResponseEntity.status(200).body("1");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> retrieveMessagesByUser(@PathVariable String accountId){
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(Integer.valueOf(accountId)));
    }
}
