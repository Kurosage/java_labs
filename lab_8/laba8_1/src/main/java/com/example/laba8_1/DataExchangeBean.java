package com.example.laba8_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
/*1101(10=2)
5. Программный код вычисляемой функции разместить: 1 –в классе Bean-компоненты.
6. Заголовки страниц должны иметь следующий вид: 1 - «Раз», «Два» и «Три».+
7. Формат «Стартовой страницы»: 0 – содержит текст задания на лабораторную работу, ФИО студента и ссылку для перехода на «Главную страницу»,
8. Организовать вывод результатов работы функции на «Финишной странице»:  1 – результаты должны быть каким-то
образом размещены в видимой таблице, в таблице допускается произвольное число
столбцов и строк.
9. При повторном переходе на «Главную страницу», например при нажатии кнопки
«Возврат» на «Финишной странице», на «Главной странице» должно отображаться: 0
– изменение триггера, размещённого на «Главной странице»; 0 – изменение триггера,
размещённого в Bean-компоненте;  2 – изменение счётчика, размещённого в Bean-компоненте;
*/
public class DataExchangeBean {
	private int countPageRefresh;
	private int summEvenOrNegativeNumbers = 0;
	private int summOddOrNegativeNumbers = 0;
	private boolean arrayWithInvalidNumbers = false;
	private String strWithNumbers;
	private String itog;
	private String answerTask;

	public DataExchangeBean(){
		countPageRefresh = 0;
	}

	public int getCountPageRefresh() {
		return countPageRefresh;
	}
	public void setCountPageRefresh(int countPageRefresh) {
		this.countPageRefresh = countPageRefresh;
	}
	public String getStritog(){return itog;};
	public void setStritog(String itog){this.itog = itog;};
	public void setSummOddOrNegativeNumbers(int summOddOrNegativeNumbers) {
		this.summOddOrNegativeNumbers = summOddOrNegativeNumbers;
	}
	public void setSummEvenOrNegativeNumbers(int summEvenOrNegativeNumbers) {
		this.summEvenOrNegativeNumbers = summEvenOrNegativeNumbers;
	}
	public void setArrayWithInvalidNumbers(boolean arrayWithInvalidNumbers) {
		this.arrayWithInvalidNumbers = arrayWithInvalidNumbers;
	}
	public int getSummEvenOrNegativeNumbers() {
		return summEvenOrNegativeNumbers;
	}
	public int getSummOddOrNegativeNumbers() {
		return summOddOrNegativeNumbers;
	}

	public String getStrWithNumbers() {
		return strWithNumbers;
	}

	public void setStrWithNumbers(String strWithNumbers) {
		this.strWithNumbers = strWithNumbers;
	}

	public String getAnswerTask() {
		return answerTask;
	}

	public void setAnswerTask(String answerTask) {
		this.answerTask = answerTask;
	}

	public boolean isArrayWithInvalidNumbers() {
		return arrayWithInvalidNumbers;
	}


}
