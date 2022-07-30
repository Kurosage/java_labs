<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Три</title>
</head>
<body>
    <jsp:useBean id="mybean" scope="session" class="com.example.laba8_1.DataExchangeBean" />
    <%
        String summEvenOrNegativeNumbers;
        String summOddOrNegativeNumbers;
        String itog;

        if (mybean.isArrayWithInvalidNumbers()) {
            summEvenOrNegativeNumbers = "Неверные агрументы";
            summOddOrNegativeNumbers = "Неверные агрументы";
            itog = "Неверные аргументы";
        }
        else {
            summEvenOrNegativeNumbers = String.valueOf(mybean.getSummEvenOrNegativeNumbers());
            summOddOrNegativeNumbers = String.valueOf(mybean.getSummOddOrNegativeNumbers());
            itog = String.valueOf(mybean.getStritog());
        }
    %>

    <h1>
        <table border="2">
            <tr><td>Количество нечетных и отрицательных чисел: <%= summOddOrNegativeNumbers %></td>
            <tr><td>Количество четных и отрицательных чисел: <%= summEvenOrNegativeNumbers %></td>
            <tr><td>Вывод: <%= itog %></td>

        </table>
    </h1>

<form name="back to start" action="main.jsp">
    <input type="submit" value="Главная страница" name="button2" />

</form>


</body>
</html>
