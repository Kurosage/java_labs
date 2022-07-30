package personCrud;

import javax.swing.*;
import java.awt.*;

public class PersonDelete extends JDialog {
	JTextField idField;
	JButton button;
	int id;
	boolean error = false;

	public PersonDelete(JFrame frame) {
		super(frame, "Удалить", true);
		this.setLayout(new GridLayout(2, 2));
		this.setBounds(500, 100, 400, 200);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	private void initializeFields() {
		idField = new JTextField();
		button = new JButton("Подтвердить");
	}

	private void addFieldsToDialog() {
		add(new JLabel("Введите уникальный ключ"));
		add(idField);
		add(button);
	}

	private void addActionToButton() {
		button.addActionListener(e -> {
			try {
				System.out.println("Подтвердить нажата!");
				if (isEmptyFields()) {
					throw new Exception();
				}
				System.out.println("на пустоту проверено!");
				id = Integer.parseInt(idField.getText());
			} catch (Exception exception) {
				System.out.println("исключение!");
				error = true;
			}
			setVisible(false);
		});
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return idField.getText().isEmpty();
	}

	public int getId() {
		return id;
	}

	public boolean isError() {
		return error;
	}
}
