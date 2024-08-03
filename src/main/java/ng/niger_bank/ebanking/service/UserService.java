package ng.niger_bank.ebanking.service;

import ng.niger_bank.ebanking.payload.request.CreditRequest;
import ng.niger_bank.ebanking.payload.request.DebitRequest;
import ng.niger_bank.ebanking.payload.request.EnquiryRequest;
import ng.niger_bank.ebanking.payload.request.TransferRequest;
import ng.niger_bank.ebanking.payload.response.BankResponse;

public interface UserService {

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount (CreditRequest creditRequest);

    BankResponse debitAccount(DebitRequest debitRequest);

    BankResponse transfer(TransferRequest request);

}
