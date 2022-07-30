<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Два</title>
</head>
<body>
<jsp:useBean id="mybean" scope="session" class="com.example.laba8_1.DataExchangeBean" />
<%
    try {
        String array = request.getParameter("array");
        String[] arrayWithNumbers = array.split(" ");
        ArrayList<Integer> listInt = new ArrayList<>();
        mybean.setSummOddOrNegativeNumbers(0);
        mybean.setSummEvenOrNegativeNumbers (0);
        mybean.setArrayWithInvalidNumbers(false);

        for ( String elem: arrayWithNumbers) {
            listInt.add(Integer.parseInt(elem));
        }
        for (Integer number : listInt) {
            if (isNumberEvenOrNegative(number)) {
                mybean.setSummEvenOrNegativeNumbers (mybean.getSummEvenOrNegativeNumbers() + 1);
            }
            else if (isNumberOddOrNegative(number)) {
                mybean.setSummOddOrNegativeNumbers(mybean.getSummOddOrNegativeNumbers() + 1);
            }
        }
        if (mybean.getSummEvenOrNegativeNumbers() > mybean.getSummOddOrNegativeNumbers())
        {mybean.setStritog("чётных и отрицательных чисел больше");}
        else if (mybean.getSummEvenOrNegativeNumbers() < mybean.getSummOddOrNegativeNumbers())
        {mybean.setStritog( "нечётных и отрицательных чисел больше");}
        else
        {mybean.setStritog("Одинаковое количество чисел");}
    }
    catch (Exception ignored){
        mybean.setSummOddOrNegativeNumbers(0);
        mybean.setSummEvenOrNegativeNumbers (0);
        mybean.setArrayWithInvalidNumbers(true);
    }
%>
<%!
    private boolean isNumberEvenOrNegative(int number) {
        return ((number % 2 == 0) &&( number < 0));
    }

    private boolean isNumberOddOrNegative(int number) {
        return ((number < 0) && (-number % 2 == 1));
    }
%>
<h3>Введите числа через пробел</h3>
<form name="Input form" action="main.jsp">
    <label>
        <input type="text" name="array" />
    </label>
    <input type="submit" value="Подтвердить" name="button2" />
</form>
<form name="Input form2" action="finish.jsp">
    <input type="submit" value="Результат" name="button3" />
</form>
<%
    mybean.setCountPageRefresh(mybean.getCountPageRefresh() + 1);
%>
<h3>Счётчик, размещённый в Bean-компоненте: ${mybean.countPageRefresh}</h3>
</body>
</html>
