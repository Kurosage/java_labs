package bookworks;

import javax.swing.*;
import java.awt.*;

public class BookUpdater extends BookCreator {
	JTextField idField;
	int id;

	public BookUpdater(JFrame frame) {
		super(frame);
	}

	protected void initializeFields() {
		idField = new JTextField();
		super.initializeFields();
		setLayout(new GridLayout(10, 2));
	}

	protected void addFieldsToDialog() {
		add(new JLabel("Введите id книги"));
		add(idField);
		super.addFieldsToDialog();
	}

	protected void addActionToButton() {
		button.addActionListener(e -> {
			try {
				System.out.println("Подтвердить нажата!");
				if (isEmptyFields()) {
					throw new Exception();
				}
				System.out.println("на пустоту проверено!");

				id = Integer.parseInt(idField.getText());
				super.setParametersFromFields();
			} catch (Exception exception) {
				System.out.println("исключение!");
				error = true;
			}
			setVisible(false);
		});
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return super.isEmptyFields() ||
				idField.getText().isEmpty();
	}

	public int getId() {
		return id;
	}
}
