package classRoomCrud;

import javax.swing.*;
import java.awt.*;

public class
ClassRoomCreate extends JDialog {
	JTextField buildingField;
	JTextField numberField;
	JTextField nameField;
	JTextField surfaceField;
	JTextField responsiblePersonField;

	int building;
	String name;
	int number;
	double surface;
	int responsiblePerson;

	JButton button;

	boolean error = false;

	public ClassRoomCreate(JFrame frame) {
		super(frame, "Аудитория", true);
		this.setLayout(new GridLayout(9, 2));
		this.setBounds(500, 100, 500, 500);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	protected void initializeFields() {
		buildingField = new JTextField();
		numberField = new JTextField();
		nameField = new JTextField();
		surfaceField = new JTextField();
		responsiblePersonField = new JTextField();
		button = new JButton("Подтвердить");
	}

	protected void addFieldsToDialog() {
		add(new JLabel("Номер здания"));
		add(buildingField);
		add(new JLabel("Номер аудитории"));
		add(numberField);
		add(new JLabel("Название"));
		add(nameField);
		add(new JLabel("Площадь"));
		add(surfaceField);
		add(new JLabel("ID ответственного лица"));
		add(responsiblePersonField);
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
		building = Integer.parseInt(buildingField.getText());
		name = nameField.getText();
		number = Integer.parseInt(numberField.getText());
		surface = Double.parseDouble(surfaceField.getText());
		responsiblePerson = Integer.parseInt(responsiblePersonField.getText());
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return buildingField.getText().isEmpty() ||
				numberField.getText().isEmpty() ||
				nameField.getText().isEmpty() ||
				surfaceField.getText().isEmpty() ||
				responsiblePersonField.getText().isEmpty();
	}

	public boolean isError() {
		return error;
	}

	public int getBuilding() {
		return building;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public double getSurface() {
		return surface;
	}

	public int getResponsiblePerson() {
		return responsiblePerson;
	}
}
