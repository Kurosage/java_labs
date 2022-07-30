package additionalworks;

import javax.swing.*;
import java.awt.*;

public class PagesCounterDialog extends JDialog {
	JTextField floorField;
	JButton button;
	int floor;
	boolean error = false;

	public PagesCounterDialog(JFrame frame) {
		super(frame, "Удалить", true);
		this.setLayout(new GridLayout(2, 2));
		this.setBounds(500, 100, 400, 200);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	private void initializeFields() {
		floorField = new JTextField();
		button = new JButton("Подтвердить");
	}

	private void addFieldsToDialog() {
		add(new JLabel("Введите этаж"));
		add(floorField);
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
				floor = Integer.parseInt(floorField.getText());
			} catch (Exception exception) {
				System.out.println("исключение!");
				error = true;
			}
			setVisible(false);
		});
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return floorField.getText().isEmpty();
	}

	public int getFloor() {
		return floor;
	}

	public boolean isError() {
		return error;
	}

}
