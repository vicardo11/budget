package it.sosinski.accountbalance.controller;

import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.service.IncomeService;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_INCOME;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_LIST;

@RestController
@RequestMapping(URI_INCOME)
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping(URI_LIST)
    @LogMethodAround
    public ResponseEntity<IncomeResponseDtoList> getIncomeList(@RequestHeader String email) {
        IncomeResponseDtoList incomeResponseDtoList = incomeService.getIncomeList(email);
        return new ResponseEntity<>(incomeResponseDtoList, HttpStatus.OK);
    }

}
