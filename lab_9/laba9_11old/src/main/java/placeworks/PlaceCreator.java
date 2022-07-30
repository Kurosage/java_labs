package placeworks;

import javax.swing.*;
import java.awt.*;

public class PlaceCreator  extends JDialog {


	private JTextField floorField;
	private JTextField wardrobeField;
	private JTextField shelfField;

	private int floor;

	private int wardrobe;
	private int shelf;
	JButton button;
	boolean error = false;

	public PlaceCreator(JFrame frame) {
		super(frame, "Книга", true);
		this.setLayout(new GridLayout(4, 2));
		this.setBounds(500, 100, 500, 500);

		initializeFields();
		addFieldsToDialog();
		addActionToButton();
	}

	protected void initializeFields() {
		floorField = new JTextField();
		wardrobeField = new JTextField();
		shelfField = new JTextField();
		button = new JButton("Подтвердить");
	}

	protected void addFieldsToDialog() {
		add(new JLabel("Введите этаж"));
		add(floorField);
		add(new JLabel("Введите шкаф"));
		add(wardrobeField);
		add(new JLabel("Введите полку"));
		add(shelfField);
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

		floor = Integer.parseInt(floorField.getText());
		wardrobe = Integer.parseInt(wardrobeField.getText());
		shelf = Integer.parseInt(shelfField.getText());
	}

	protected boolean isEmptyFields() {
		System.out.println("Проверка на пустоту");
		return floorField.getText().isEmpty() ||
				wardrobeField.getText().isEmpty() ||
				shelfField.getText().isEmpty();
	}

	public boolean isError() {
		return error;
	}

	public int getFloor() {
		return floor;
	}

	public int getWardrobe() {
		return wardrobe;
	}

	public int getShelf() {
		return shelf;
	}


}
