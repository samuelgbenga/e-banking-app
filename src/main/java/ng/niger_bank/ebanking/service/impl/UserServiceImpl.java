package ng.niger_bank.ebanking.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.domain.entity.UserEntity;
import ng.niger_bank.ebanking.payload.request.*;
import ng.niger_bank.ebanking.payload.response.AccountInfoDto;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.repository.UserRepository;
import ng.niger_bank.ebanking.service.EmailService;
import ng.niger_bank.ebanking.service.UserService;
import ng.niger_bank.ebanking.utils.AccountUtils;
import ng.niger_bank.ebanking.utils.EmailBody;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

   private final EmailService emailService;

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_MESSAGE)
                    .build();
        }

        UserEntity foundUserAccount = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_NUMBER_EXIST_CODE)
                .responseMessage(AccountUtils.ACCOUNT_NUMBER_EXIST_MESSAGE)
                .accountInfo(AccountInfoDto.builder()
                        .accountBalance(foundUserAccount.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUserAccount.getFirstName() + " " + foundUserAccount.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists){
            return AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_MESSAGE;
        }

        UserEntity foundUserAccount = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return foundUserAccount.getFirstName() + " "+ foundUserAccount.getLastName()
                +" "+ AccountUtils.ACCOUNT_EXIST_MESSAGE;
    }

    @Override
    public BankResponse creditAccount(CreditRequest creditRequest) {

        boolean isAccountExists = userRepository.existsByAccountNumber(creditRequest.getAccountNumber());

        if(!isAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_MESSAGE)
                    .build();
        }

        UserEntity userToCredit = userRepository.findByAccountNumber(creditRequest.getAccountNumber());

        userToCredit.setAccountBalance(userToCredit.getAccountBalance()
                .add(creditRequest.getAmount())
        );

        userRepository.save(userToCredit);

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Credit Alert")
                .recipient(userToCredit.getEmail())
                .messageBody(EmailBody.getBalanceAlert(userToCredit.getFirstName(), userToCredit.getLastName(), userToCredit.getAccountBalance(), "CREDIT"))
                .build();

      emailService.sendMimeEmailAlert(creditAlert);

      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESSFULLY_CODE)
              .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESSFULLY_MESSAGE)
              .accountInfo(AccountInfoDto.builder()
                      .accountName(userToCredit.getFirstName())
                      .accountBalance(userToCredit.getAccountBalance())
                      .accountNumber(userToCredit.getAccountNumber())
                      .build())
              .build();
    }

    @Override
    public BankResponse debitAccount(DebitRequest debitRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(debitRequest.getAccountNumber());

        if(!isAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NUMBER_DOES_NOT_EXIST_MESSAGE)
                    .build();
        }

        UserEntity userToDebit = userRepository.findByAccountNumber(debitRequest.getAccountNumber());


        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();

        BigInteger amountToDebit = debitRequest.getAmount().toBigInteger();

        if(availableBalance.intValue() < amountToDebit.intValue()){
            return BankResponse.builder()
                    .responseCode("009")
                    .responseMessage("Insufficient Balance")
                    .accountInfo(null)
                    .build();
        }

        userToDebit.setAccountBalance(userToDebit.getAccountBalance()
                .subtract(debitRequest.getAmount())
        );
        userRepository.save(userToDebit);

        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Debit Alert")
                .recipient(userToDebit.getEmail())
                .messageBody(EmailBody.getBalanceAlert(userToDebit.getFirstName(), userToDebit.getLastName(), userToDebit.getAccountBalance(), "DEBIT"))
                .build();

        emailService.sendMimeEmailAlert(debitAlert);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESSFULLY_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESSFULLY_MESSAGE)
                .accountInfo(AccountInfoDto.builder()
                        .accountName(userToDebit.getFirstName())
                        .accountBalance(userToDebit.getAccountBalance())
                        .accountNumber(userToDebit.getAccountNumber())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public BankResponse transfer(TransferRequest request) {
       /*
       1- first check if the destination account number exists
       2- then check if amount to send is available
       3- then deduct the amount to send
       4- then add to receiver balance
       5- send a debit alert to the sender
       4- send a credit alert to the receiver
        */

        boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        boolean isSourceAccountExist = userRepository.existsByAccountNumber(request.getSourceAccountNumber());

        if(!isDestinationAccountExist){
            return BankResponse.builder()
                    .responseCode("0011")
                    .responseMessage("Receivers account number does not exist")
                    .build();
        }

        if(!isSourceAccountExist){
            return BankResponse.builder()
                    .responseCode("0011")
                    .responseMessage("Senders account number does not exist")
                    .build();
        }

        // load the senders detail from db
        UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());

        // load the receivers detail from db
        UserEntity destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance())>0){

            return BankResponse.builder()
                    .responseMessage("0000222222")
                    .responseMessage("Insufficient Balance!")
                    .build();
        }

        // debit the source
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));

        // credit the receiver
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));

       // update the senders account
        userRepository.save(sourceAccountUser);

        // update the receivers account
        userRepository.save(destinationAccountUser);

        // get the senders account number
        String sourceUsername = sourceAccountUser.getFirstName() + " " +
                sourceAccountUser.getOtherName() + " " +
                sourceAccountUser.getLastName();


        // get the receivers full name
       String destinationUsername = destinationAccountUser.getFirstName() + " "+
       destinationAccountUser.getOtherName() + " "+ destinationAccountUser.getLastName();

       // credit message
        String senderMessage = "You have successfully Credited";

        // debit message
        String receiverMessage = "You Are Credited By";

        //todo : send email alert to the sender
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Debit Alert")
                .recipient(sourceAccountUser.getEmail())
                .messageBody(EmailBody.getDebitAndCredit(sourceAccountUser.getFirstName(), sourceAccountUser.getLastName(), sourceAccountUser.getAccountBalance(), "DEBIT", destinationUsername, senderMessage))
                .build();

        emailService.sendMimeEmailAlert(debitAlert);



        //todo : send email alert to the receiver
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Credit Alert")
                .recipient(destinationAccountUser.getEmail())
                .messageBody(EmailBody.getDebitAndCredit(destinationAccountUser.getFirstName(), destinationAccountUser.getLastName(), destinationAccountUser.getAccountBalance(), "CREDIT", sourceUsername, receiverMessage))
                .build();

        emailService.sendMimeEmailAlert(creditAlert);


        return BankResponse.builder()
                .responseCode("111")
                .responseMessage("You have successfully Credited: "+ destinationUsername)
                .accountInfo(null)
                .build();
    }
}
