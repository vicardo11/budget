package it.sosinski.accountbalance.controller;

import it.sosinski.accountbalance.dto.ExpenseCreateRequestDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDto;
import it.sosinski.accountbalance.dto.ExpenseResponseDtoList;
import it.sosinski.accountbalance.service.ExpenseService;
import it.sosinski.aspectdirectory.logger.LogMethodAround;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.sosinski.accountbalance.configuration.UriConstants.URI_CREATE;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_EXPENSES;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_LIST;

@RestController
@RequestMapping(URI_EXPENSES)
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Get list of expenses of the current user
     *
     * @param email - email of current user
     * @return - list of all expenses of current user
     */
    @GetMapping(URI_LIST)
    @LogMethodAround
    public ResponseEntity<ExpenseResponseDtoList> getExpenseList(@RequestHeader final String email) {
        final ExpenseResponseDtoList expensesList = expenseService.getExpensesList(email);
        return new ResponseEntity<>(expensesList, HttpStatus.OK);
    }

    /**
     * Create new income of current user
     *
     * @param email                   - email of current user
     * @param expenseCreateRequestDto - expense to be created
     * @return created expense
     */
    @PostMapping(URI_CREATE)
    @LogMethodAround
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestHeader final String email,
            @RequestBody @Valid final ExpenseCreateRequestDto expenseCreateRequestDto) {
        final ExpenseResponseDto expenseResponseDto = expenseService.createExpense(email, expenseCreateRequestDto);
        return new ResponseEntity<>(expenseResponseDto, HttpStatus.CREATED);
    }

}
