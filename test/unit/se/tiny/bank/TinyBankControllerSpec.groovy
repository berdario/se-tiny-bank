package se.tiny.bank

import grails.plugin.mail.MailService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(TinyBankController)
@Mock([Account, Transaction])
class TinyBankControllerSpec extends Specification {

    def setup() {
        controller.mailService = Mock(MailService)
    }

    def cleanup() {
        controller.mailService == null
    }

    void "test Account has correct fields"() {
        given:
            Account account = new Account()
        expect:
            account.hasProperty("name")
            account.hasProperty("email")
            account.hasProperty("balance")
    }

    void "test Transaction has correct fields"() {
        given:
            Transaction transaction = new Transaction()
        expect:
            transaction.hasProperty("debit")
            transaction.hasProperty("credit")
            transaction.hasProperty("value")
    }

    void "test Account default balance"() {
        given:
            def acc = new Account(id: 1, name: "acc1", email:"acc1@test.com").save()
        expect:
            acc.balance == 200
    }

    void "test doPayment"() {
        given:
            def dr = new Account(id: 1, name: "acc1", email:"acc1@test.com").save()
            def cr = new Account(id: 2, name: "acc2", email:"acc2@test.com").save()

        when:
            params.dr = dr.id
            params.cr = cr.id
            params.amt = 12
            controller.doPayment()
            def transactions = Transaction.list()
        then:
            dr.balance == 212
            cr.balance == 188
        and:
            transactions.size() == 1
            transactions[0].debit == dr
            transactions[0].credit == cr
            transactions[0].value == params.amt
        and:
            view == '/tinyBank/pay'

        when:
            params.dr = dr.id
            params.cr = cr.id
            params.amt = 1000
            controller.doPayment()
        then:
            dr.balance == 212
            cr.balance == 188
    }
}
