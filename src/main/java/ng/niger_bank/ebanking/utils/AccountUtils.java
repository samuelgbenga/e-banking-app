package ng.niger_bank.ebanking.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE = "001";

    public static final String ACCOUNT_NUMBER_DOES_NOT_EXIST_CODE = "004";

    public static  final  String ACCOUNT_CREDITED_SUCCESSFULLY_CODE = "007";

    public static final String ACCOUNT_CREDITED_SUCCESSFULLY_MESSAGE = "Account Credited Successfully";

    public static final String ACCOUNT_NUMBER_EXIST_CODE = "003";

    public static final String ACCOUNT_EXIST_MESSAGE = "This User Currently Has an Account created!";

    public static final String ACCOUNT_NUMBER_DOES_NOT_EXIST_MESSAGE = "This Account Number does not exist!";

    public static final String ACCOUNT_NUMBER_EXIST_MESSAGE = "This Account exist!";

    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";

    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account has been created successfully!";

    public static final String ACCOUNT_DEBITED_SUCCESSFULLY_CODE = "010";

    public static final String ACCOUNT_DEBITED_SUCCESSFULLY_MESSAGE = "Account Debited successful";



    public static String generateAccountNumber(){

        // get the current year
        Year currentYear = Year.now();

        // get the  6 random digit
        int min = 100000;
        int max = 999999;

        // generate a random number between min and max
        int randomNumber = (int) Math.floor(Math.random() * (max-min + 1) + min);


        // convert current year and random six number to string and then concat it

        // you can do it this way but
       // return ""+ currentYear+ "" + randomNumber;

        String year = String.valueOf(currentYear);
        String randomString = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder().append(year).append(randomNumber);

        return accountNumber.toString();

    }
}
