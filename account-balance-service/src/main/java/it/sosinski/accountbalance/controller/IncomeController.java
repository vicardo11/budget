package it.sosinski.accountbalance.controller;

import it.sosinski.accountbalance.dto.IncomeCreateRequestDto;
import it.sosinski.accountbalance.dto.IncomeResponseDto;
import it.sosinski.accountbalance.dto.IncomeResponseDtoList;
import it.sosinski.accountbalance.service.IncomeService;
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
import static it.sosinski.accountbalance.configuration.UriConstants.URI_INCOME;
import static it.sosinski.accountbalance.configuration.UriConstants.URI_LIST;

@RestController
@RequestMapping(URI_INCOME)
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    /**
     * Get list of incomes of current user
     *
     * @param email - email of current user
     * @return - list of all incomes of current user
     */
    @GetMapping(URI_LIST)
    @LogMethodAround
    public ResponseEntity<IncomeResponseDtoList> getIncomeList(@RequestHeader final String email) {
        final IncomeResponseDtoList incomeResponseDtoList = incomeService.getIncomeList(email);
        return new ResponseEntity<>(incomeResponseDtoList, HttpStatus.OK);
    }

    /**
     * Create new income of current user
     *
     * @param email                  - email of current user
     * @param incomeCreateRequestDto - income to be created
     * @return created income
     */
    @PostMapping(URI_CREATE)
    @LogMethodAround
    public ResponseEntity<IncomeResponseDto> createIncome(@RequestHeader final String email,
            @RequestBody @Valid final IncomeCreateRequestDto incomeCreateRequestDto) {
        final IncomeResponseDto incomeResponseDto = incomeService.createIncome(email, incomeCreateRequestDto);
        return new ResponseEntity<>(incomeResponseDto, HttpStatus.CREATED);
    }

}
