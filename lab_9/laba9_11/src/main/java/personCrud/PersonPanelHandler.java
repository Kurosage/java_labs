package personCrud;

import db.SqlRequests;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class PersonPanelHandler {
	JPanel personPanel;
	Connection connection;
	JFrame mainFrame;

	JButton showButton;
	JButton addButton;
	JButton updateButton;
	JButton deleteButton;
	JLabel showPlacesLabel;

	public PersonPanelHandler(Connection connection, JFrame mainFrame) {
		personPanel = new JPanel();
		this.mainFrame = mainFrame;
		this.connection = connection;
		initializeButtons();
		initializeLabels();
		setActionsToButtons();
	}

	public JPanel getPersonPanel() {
		return personPanel;
	}

	private void initializeButtons() {
		showButton = new JButton("Показать всех ответственных");
		personPanel.add(showButton);
		addButton = new JButton("Добавить");
		personPanel.add(addButton);
		updateButton = new JButton("Изменить");
		personPanel.add(updateButton);
		deleteButton = new JButton("Удалить");
		personPanel.add(deleteButton);
	}
	private void initializeLabels() {
		showPlacesLabel = new JLabel();
		personPanel.add(showPlacesLabel, new GridBagConstraints());
	}

	private void setActionsToButtons() {
		actionShowButton();
		actionAddButton();
		actionUpdateButton();
		actionDeleteButton();
	}

	private void actionShowButton() {
		showButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			showPlacesLabel.setText(sqlRequests.showAllPersons());
		});
	}

	private void actionAddButton() {
		addButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PersonCreate dialog = new PersonCreate(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(personPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.createNewRowInPersonTable(
					dialog.getFio(),
					dialog.getPosition(),
					dialog.getPhone(),
					dialog.getAge());
			JOptionPane.showMessageDialog(personPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void actionUpdateButton() {
		updateButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PersonUpdate dialog = new PersonUpdate(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(personPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isPersonExist(dialog.getId())) {
				JOptionPane.showMessageDialog(personPanel, "Человек с таким ID не найден", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.updatePerson(
					dialog.getId(),
					dialog.getFio(),
					dialog.getPosition(),
					dialog.getPhone(),
					dialog.getAge());
			JOptionPane.showMessageDialog(personPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});

	}

	private void actionDeleteButton() {
		deleteButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PersonDelete dialog = new PersonDelete(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(personPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isPersonExist(dialog.getId())) {
				JOptionPane.showMessageDialog(personPanel, "Человек с таким ID не найден", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.deletePerson(dialog.getId());
			JOptionPane.showMessageDialog(personPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private boolean isPersonExist(int personId) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsPersonById(personId);
	}
}
