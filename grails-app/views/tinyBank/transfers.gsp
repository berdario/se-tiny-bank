<%@ page import="se.tiny.bank.*" %>

<html>
<head>
    <title>See transactions</title>
    <meta name="layout" content="main" />
</head>
<body>
<div style="padding:10px;">
    <i style="color: #18aa81">${request.message}</i>
    <h2>Transfers</h2>
    <g:link action="pay">Pay someone</g:link>
    <br/>
    <br/>
    <g:form name="transaction" action="listTransfers">
        <b>Person:</b>
        <g:select name="account.id"
                  from="${Account.list()}"
                  optionKey="id"
                  optionValue="${{it.name + " : £" + it.balance}}"
                  noSelection="['':'-Choose account name-']"/>
        <input type="submit" value="Show transactions">
    </g:form>
</div>
</body>
</html>
