package ng.niger_bank.ebanking.service;

import ng.niger_bank.ebanking.payload.request.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);

    // send mime EmailAlert

    void sendMimeEmailAlert(EmailDetails emailDetails);
}
