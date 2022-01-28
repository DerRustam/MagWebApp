package com.rustam.magbackend.controller;

import com.rustam.magbackend.dto.AccountDetailsDTO;
import com.rustam.magbackend.dto.user.SignUpRequestPackage;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/token")
    @ResponseStatus(HttpStatus.OK)
    public String returnToken(){
        return "BOB";
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignUpRequestPackage requestPackage){
        return ResponseEntity.ok(userService.registerUser(requestPackage));
    }

    @GetMapping(value = "/inst/{username}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable @NotEmpty String username){
       return ResponseEntity.ok(userService.getAccountDetails(username));
    }

    @ExceptionHandler(DataServiceException.class)
    @ResponseBody
    ResponseEntity<String> errorResult(DataServiceException ex){
        switch (ex.getConflict()){
            case NOT_FOUND: {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }
            case DUPLICATE: {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
            default: {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
            }
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    String internalErrorResult(RuntimeException ex){
        return ex.getMessage();
    }

}
