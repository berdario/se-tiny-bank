<%@ page import="se.tiny.bank.*" %>

<html>
<head>
    <title>See transfers</title>
    <meta name="layout" content="main" />
</head>
<body>
<div style="padding:10px;">
    <i style="color: #18aa81">${request.message}</i>
    <h2>Transfers</h2>
    <g:link action="pay">Pay someone</g:link>
    <br/>
    <br/>
    <g:form name="transfer" action="listTransfers">
        <b>Person:</b>
        <g:select name="account.id"
                  from="${Account.list()}"
                  optionKey="id"
                  optionValue="${{it.name + " : £" + it.balance}}"
                  noSelection="['':'-Choose account name-']"/>
        <input type="submit" value="Show transfers">
    </g:form>
</div>
<br/>
<table>
    <thead>
        <th>ID</th>
        <th>From</th>
        <th>To</th>
        <th>Amount</th>
    </thead>
    <tbody>
        <g:set var="account" value="${Account.read(params.account.id as long)}"/>
        <g:each in="${Transfer.findAllByCreditOrDebit(account, account)}" var="transfer">
            <tr>
                <td>${transfer.id}</td>
                <td>${transfer.credit.name}</td>
                <td>${transfer.debit.name}</td>
                <td>${transfer.value}</td>
            </tr>
        </g:each>
    </tbody>
</table>
</body>
</html>