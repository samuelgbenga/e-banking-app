package ng.niger_bank.ebanking.infrastructure.controller;


import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.payload.request.CreditRequest;
import ng.niger_bank.ebanking.payload.request.DebitRequest;
import ng.niger_bank.ebanking.payload.request.EnquiryRequest;
import ng.niger_bank.ebanking.payload.request.TransferRequest;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/balance-enquiry")
    public BankResponse accountEnquiry(@RequestBody EnquiryRequest enquiryRequest){

        return userService.balanceEnquiry(enquiryRequest);

    }


    @GetMapping("/name-enquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){

        return userService.nameEnquiry(enquiryRequest);

    }

    @PostMapping("/credit-account")
    public BankResponse creditAccount(@RequestBody  CreditRequest creditRequest){

        return userService.creditAccount(creditRequest);
    }

    @PostMapping("/debit-account")
    public BankResponse debitAccount(@RequestBody DebitRequest debitRequest){

        return userService.debitAccount(debitRequest);
    }

    @PostMapping("/transaction")
    public BankResponse creditOtherAccount(@RequestBody TransferRequest transferRequest){

        return userService.transfer(transferRequest);
    }

}
