package it.sosinski.accountbalance.controller;

import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.service.ExpenseService;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_EXPENSES;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_LIST;

@RestController
@RequestMapping(URI_EXPENSES)
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping(URI_LIST)
    @LogMethodAround
    public ResponseEntity<ExpenseResponseDtoList> getExpenseList(@RequestHeader String email) {
        ExpenseResponseDtoList expensesList = expenseService.getExpensesList(email);
        return new ResponseEntity<>(expensesList, HttpStatus.OK);
    }
}
