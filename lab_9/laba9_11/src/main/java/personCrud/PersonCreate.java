package personCrud;

import javax.swing.*;
import java.awt.*;

public class PersonCreate extends JDialog {


	private JTextField fioField;
	private JTextField positionField;
	private JTextField phoneField;
	private JTextField ageField;

	private String fio;
	private String position;
	private String phone;
	private int age;

	JButton button;
	boolean error = false;

	public PersonCreate(JFrame frame) {
		super(frame, "Ответственный", true);
		this.setLayout(new GridLayout(5, 2));
		this.setBounds(500, 100, 500, 500);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	protected void initializeFields() {
		fioField = new JTextField();
		positionField = new JTextField();
		phoneField = new JTextField();
		ageField = new JTextField();
		button = new JButton("Подтвердить");
	}

	protected void addFieldsToDialog() {
		add(new JLabel("ФИО"));
		add(fioField);
		add(new JLabel("Должность"));
		add(positionField);
		add(new JLabel("Номер телефона"));
		add(phoneField);
		add(new JLabel("Возраст"));
		add(ageField);
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

		fio = fioField.getText();
		position = positionField.getText();
		phone = phoneField.getText();
		age = Integer.parseInt(ageField.getText());
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return fioField.getText().isEmpty() ||
				positionField.getText().isEmpty() ||
				phoneField.getText().isEmpty() ||
				ageField.getText().isEmpty();
	}

	public boolean isError() {
		return error;
	}

	public String getFio() {
		return fio;
	}

	public String getPosition() {
		return position;
	}

	public String getPhone() {
		return phone;
	}

	public int getAge() {
		return age;
	}
}
