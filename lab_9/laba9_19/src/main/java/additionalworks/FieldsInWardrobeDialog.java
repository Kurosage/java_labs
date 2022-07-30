package additionalworks;

import javax.swing.*;
import java.awt.*;

public class FieldsInWardrobeDialog extends JDialog {
	private JTextField wardrobeField;
	private JTextField floorField;
	private JButton button;

	private int wardrobe;
	private int floor;
	private boolean error = false;

	public FieldsInWardrobeDialog(JFrame frame) {
		super(frame, "В шкафу", true);
		this.setLayout(new GridLayout(3, 2));
		this.setBounds(500, 100, 400, 300);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	private void initializeFields() {
		wardrobeField = new JTextField();
		floorField = new JTextField();
		button = new JButton("Подтвердить");
	}

	private void addFieldsToDialog() {
		add(new JLabel("Введите этаж"));
		add(floorField);
		add(new JLabel("Введите шкаф"));
		add(wardrobeField);
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
		floor = Integer.parseInt(floorField.getText());
		wardrobe = Integer.parseInt(wardrobeField.getText());
	}

	protected boolean isEmptyFields() {
		return floorField.getText().isEmpty() ||
				wardrobeField.getText().isEmpty();
	}

	public int getWardrobe() {
		return wardrobe;
	}

	public int getFloor() {
		return floor;
	}

	public boolean isError() {
		return error;
	}
}
