package models;

import java.util.ArrayList;
import java.util.List;

public class DefDBDataHandler {

	private List<Book> bookList = new ArrayList<>();
	private List<Place> placeList = new ArrayList<>();

	public void writeDataToLists() {
		addDefPlacesToList();
		addDefBooksToList();
	}

	private void addDefBooksToList() {
		Book book = new Book("Первый автор", "Издание 1",
		"Издатель1", 1800, 1000, 1799, 500);
		bookList.add(book);
		book = new Book("Второй автор", "Издание второе",
				"Издатель второй", 1600, 800, 1524, 350);
		bookList.add(book);
		book = new Book("Третий автор", "Новое издание",
				"Издательство", 1877, 730, 1856, 450);
		bookList.add(book);
		book = new Book("Последний автор", "Отмененное издание",
				" Топ издатель", 2000, 843, 1999, 650);
		bookList.add(book);
	}

	private void addDefPlacesToList() {
		Place place = new Place(1, 1, 1);
		placeList.add(place);
		place = new Place(1, 2, 2);
		placeList.add(place);
		place = new Place(1, 2, 1);
		placeList.add(place);
	}


	public List<Book> getBookList() {
		return bookList;
	}

	public List<Place> getPlaceList() {
		return placeList;
	}
}
