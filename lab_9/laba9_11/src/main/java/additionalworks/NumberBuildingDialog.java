package additionalworks;

import javax.swing.*;
import java.awt.*;

public class NumberBuildingDialog extends JDialog {
	private JTextField buildingField;
	private JButton button;

	private int building;
	private boolean error = false;

	public NumberBuildingDialog(JFrame frame) {
		super(frame, "Учебное здание", true);
		this.setLayout(new GridLayout(3, 2));
		this.setBounds(500, 100, 400, 300);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	private void initializeFields() {
		buildingField = new JTextField();
		button = new JButton("Подтвердить");
	}

	private void addFieldsToDialog() {
		add(new JLabel("Учебное здание"));
		add(buildingField);
		add(button);
	}

	private void addActionToButton() {
		button.addActionListener(e -> {
			try {
				if (isEmptyFields()) {
					throw new Exception();
				}
				setParametersFromFields();
			} catch (Exception exception) {
				error = true;
			}
			setVisible(false);
		});
	}

	protected void setParametersFromFields() throws NumberFormatException {
		building = Integer.parseInt(buildingField.getText());
	}

	protected boolean isEmptyFields() {
		return buildingField.getText().isEmpty();
	}

	public int getBuilding() {
		return building;
	}

	public boolean isError() {
		return error;
	}
}
