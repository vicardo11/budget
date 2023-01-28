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
import org.springframework.web.bind.annotation.*;

import static it.sosinski.accountbalance.configuration.UriConstants.*;

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

    @PostMapping(URI_CREATE)
    @LogMethodAround
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestHeader String email,
                                                            @RequestBody @Valid ExpenseCreateRequestDto expenseCreateRequestDto) {
        ExpenseResponseDto expenseResponseDto = expenseService.createExpense(email, expenseCreateRequestDto);
        return new ResponseEntity<>(expenseResponseDto, HttpStatus.CREATED);
    }
}
