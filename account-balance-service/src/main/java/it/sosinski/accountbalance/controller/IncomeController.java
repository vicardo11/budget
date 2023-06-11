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
import org.springframework.web.bind.annotation.*;

import static it.sosinski.accountbalance.configuration.UriConstants.*;

@RestController
@RequestMapping(URI_INCOME)
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping(URI_LIST)
    @LogMethodAround
    public ResponseEntity<IncomeResponseDtoList> getIncomeList(@RequestHeader final String email) {
        final IncomeResponseDtoList incomeResponseDtoList = incomeService.getIncomeList(email);
        return new ResponseEntity<>(incomeResponseDtoList, HttpStatus.OK);
    }

    @PostMapping(URI_CREATE)
    @LogMethodAround
    public ResponseEntity<IncomeResponseDto> createIncome(@RequestHeader final String email,
            @RequestBody @Valid final IncomeCreateRequestDto incomeCreateRequestDto) {
        final IncomeResponseDto incomeResponseDto = incomeService.createIncome(email, incomeCreateRequestDto);
        return new ResponseEntity<>(incomeResponseDto, HttpStatus.CREATED);
    }

}
