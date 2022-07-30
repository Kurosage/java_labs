package bookworks;

import javax.swing.*;
import java.awt.*;

public class BookCreator extends JDialog {
	JTextField authorField;
	JTextField publicationField;
	JTextField publishingHouseField;
	JTextField yearPublicField;
	JTextField pagesField;
	JTextField yearWriteField;
	JTextField weightField;
	JTextField placeIdField;
	String author;
	String publication;
	String publishingHouse;
	int yearPublic;
	int pages;
	int yearWrite;
	int weight;
	int placeId;
	JButton button;

	boolean error = false;

	public BookCreator(JFrame frame) {
		super(frame, "Книга", true);
		this.setLayout(new GridLayout(9, 2));
		this.setBounds(500, 100, 500, 500);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	protected void initializeFields() {
		authorField = new JTextField();
		publicationField = new JTextField();
		publishingHouseField = new JTextField();
		yearPublicField = new JTextField();
		pagesField = new JTextField();
		yearWriteField = new JTextField();
		weightField = new JTextField();
		placeIdField = new JTextField();
		button = new JButton("Подтвердить");
	}

	protected void addFieldsToDialog() {
		add(new JLabel("Введите автора"));
		add(authorField);
		add(new JLabel("Введите издание"));
		add(publicationField);
		add(new JLabel("Введите издательство"));
		add(publishingHouseField);
		add(new JLabel("Введите год публикации"));
		add(yearPublicField);
		add(new JLabel("Введите кол-во страниц"));
		add(pagesField);
		add(new JLabel("Введите год написания"));
		add(yearWriteField);
		add(new JLabel("Введите вес в граммах"));
		add(weightField);
		add(new JLabel("Введите id места, где будет находиться книга"));
		add(placeIdField);
		add(button);
	}

	protected void addActionToButton() {
		button.addActionListener(e -> {
			try {
				System.out.println("Подтвердить нажата!");
				if (isEmptyFields()) {
					throw new Exception();
				}
				System.out.println("на пустоту проверено!");
				setParametersFromFields();
			} catch (Exception exception) {
				System.out.println("исключение!");
				error = true;
			}
			setVisible(false);
		});
	}

	protected void setParametersFromFields() throws NumberFormatException {
		author = authorField.getText();
		publication = publicationField.getText();
		publishingHouse = publishingHouseField.getText();
		yearPublic = Integer.parseInt(yearPublicField.getText());
		pages = Integer.parseInt(pagesField.getText());
		yearWrite = Integer.parseInt(yearWriteField.getText());
		weight = Integer.parseInt(weightField.getText());
		placeId = Integer.parseInt(placeIdField.getText());
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return authorField.getText().isEmpty() ||
				publicationField.getText().isEmpty() ||
				publishingHouseField.getText().isEmpty() ||
				yearPublicField.getText().isEmpty() ||
				pagesField.getText().isEmpty() ||
				yearWriteField.getText().isEmpty() ||
				weightField.getText().isEmpty() ||
				placeIdField.getText().isEmpty();
	}

	public boolean isError() {
		return error;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublication() {
		return publication;
	}

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public int getYearPublic() {
		return yearPublic;
	}

	public int getPages() {
		return pages;
	}

	public int getYearWrite() {
		return yearWrite;
	}

	public int getWeight() {
		return weight;
	}

	public int getPlaceId() {
		return placeId;
	}
}
