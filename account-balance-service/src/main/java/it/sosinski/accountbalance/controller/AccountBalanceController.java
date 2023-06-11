package it.sosinski.accountbalance.controller;

import it.sosinski.accountbalance.dto.AccountBalanceResponseDto;
import it.sosinski.accountbalance.service.AccountBalanceService;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_ACCOUNT_BALANCE;

@RestController
@RequestMapping(URI_ACCOUNT_BALANCE)
@RequiredArgsConstructor
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;

    /**
     * Get account balance of current user
     *
     * @param email - email of current user
     * @return account balance of the user
     */
    @GetMapping
    @LogMethodAround
    public ResponseEntity<AccountBalanceResponseDto> getAccountBalance(@RequestHeader final String email) {
        final AccountBalanceResponseDto accountBalanceResponseDto = accountBalanceService.getAccountBalance(email);
        return new ResponseEntity<>(accountBalanceResponseDto, HttpStatus.OK);
    }

}
