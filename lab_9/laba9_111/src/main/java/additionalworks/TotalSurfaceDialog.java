package additionalworks;

import javax.swing.*;
import java.awt.*;

public class TotalSurfaceDialog extends JDialog {
	JTextField responsiblePersonField;
	JButton button;
	int responsiblePerson;
	boolean error = false;

	public TotalSurfaceDialog(JFrame frame) {
		super(frame, "Ответственный человек", true);
		this.setLayout(new GridLayout(2, 2));
		this.setBounds(500, 100, 400, 200);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	private void initializeFields() {
		responsiblePersonField = new JTextField();
		button = new JButton("Подтвердить");
	}

	private void addFieldsToDialog() {
		add(new JLabel("Введите id ответственного"));
		add(responsiblePersonField);
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
				responsiblePerson = Integer.parseInt(responsiblePersonField.getText());
			} catch (Exception exception) {
				System.out.println("исключение!");
				error = true;
			}
			setVisible(false);
		});
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return responsiblePersonField.getText().isEmpty();
	}

	public int getResponsiblePerson() {
		return responsiblePerson;
	}

	public boolean isError() {
		return error;
	}

}
