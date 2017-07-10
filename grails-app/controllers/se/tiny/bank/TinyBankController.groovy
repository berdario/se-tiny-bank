package se.tiny.bank

class TinyBankController {
    private static final String FROM_EMAIL = "no-reply@tinybank.se"

    def mailService

    def transfers() {}

    def listTransfers() {}

    def pay() {}

    def doPayment() {
        Account dr = Account.get(params.dr as long)
        Account cr = Account.get(params.cr as long)
        long amount = params.amt as long

        if (cr.balance >= amount) {
            dr.balance += amount
            cr.balance -= amount
            Transfer tr = new Transfer(debit: dr, credit: cr, value: amount).save()

            saveAll(dr, cr, tr)

            sendDebitConfirmation(tr)
            sendCreditConfirmation(tr)
        }

        request.message = "Transfer complete"
        render(view: "pay")
    }

    private void saveAll(Object... data) {
        data.each { it.save(flush: true) }
    }

    private void sendCreditConfirmation(Transfer transfer) {
        mailService.sendMail {
            to transfer.credit.email
            from FROM_EMAIL
            subject "Credit transfer"
            body "${transfer.value} was transferred from your account to ${transfer.debit.name}"
        }
    }

    private void sendDebitConfirmation(Transfer transfer) {
        mailService.sendMail {
            to transfer.debit.email
            from FROM_EMAIL
            subject "Debit transfer"
            body "${transfer.value} was transferred to your account from ${transfer.credit.name}"
        }
    }
}
