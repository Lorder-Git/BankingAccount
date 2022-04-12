package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.dto.OpenAccountResponse;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/open-account")
public class OpenAccountController {

    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping(path = "/create")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand openAccount) {
        var id = UUID.randomUUID().toString();
        openAccount.setId(id);

        try {
            commandDispatcher.send(openAccount);
            return new ResponseEntity<BaseResponse>(new OpenAccountResponse(("Account created successfully"), id), HttpStatus.CREATED);
        } catch (IllegalStateException ise) {
            logger.log(Level.WARNING, String.valueOf(new StringBuilder().append(MessageFormat.format("IllegalStateException for the Bank Account - {0}: "
                    , ise.getMessage()))));
            return new ResponseEntity<BaseResponse>(new OpenAccountResponse(("Account creation failed"), id), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = String.valueOf(new StringBuilder().append(MessageFormat.format("Exception for the Bank Account - {0}: "
                    + e.getMessage(), e.getMessage())));
            logger.log(Level.WARNING, errorMessage);
            return new ResponseEntity<BaseResponse>(new OpenAccountResponse(("Account creation failed"), id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
