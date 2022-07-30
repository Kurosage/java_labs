package classRoomCrud;

import db.SqlRequests;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ClassRoomPanelHandler {
	JPanel classRoomPanel;
	Connection connection;
	JFrame mainFrame;

	JButton showButton;
	JButton addButton;
	JButton updateButton;
	JButton deleteButton;
	JLabel showClassRoomsLabel;

	public ClassRoomPanelHandler(Connection connection, JFrame mainFrame) {
		classRoomPanel = new JPanel();
		this.mainFrame = mainFrame;
		this.connection = connection;
		initializeButtons();
		initializeLabels();
		setActionsToButtons();
	}

	public JPanel getClassRoomPanel() {
		return classRoomPanel;
	}

	private void initializeButtons() {
		showButton = new JButton("Показать все аудитории");
		classRoomPanel.add(showButton);
		addButton = new JButton("Добавить");
		classRoomPanel.add(addButton);
		updateButton = new JButton("Изменить");
		classRoomPanel.add(updateButton);
		deleteButton = new JButton("Удалить");
		classRoomPanel.add(deleteButton);
	}
	private void initializeLabels() {
		showClassRoomsLabel = new JLabel();
		classRoomPanel.add(showClassRoomsLabel, new GridBagConstraints());
	}

	private void setActionsToButtons() {
		actionShowButton();
		actionAddButton();
		actionUpdateButton();
		actionDeleteButton();
	}

	private void actionDeleteButton() {
		deleteButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			ClassRoomDelete dialog = new ClassRoomDelete(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(classRoomPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isClassRoomExist(dialog.getId())) {
				JOptionPane.showMessageDialog(classRoomPanel, "Аудитории с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.deleteClassRoom(dialog.getId());
			JOptionPane.showMessageDialog(classRoomPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});

	}

	private void actionUpdateButton() {
		updateButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			ClassRoomUpdate dialog = new ClassRoomUpdate(mainFrame);
			dialog.setVisible(true);
			System.out.println("personId =" + dialog.getResponsiblePerson());
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(classRoomPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isClassRoomExist(dialog.getId())) {
				JOptionPane.showMessageDialog(classRoomPanel, "Аудитория с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String message = sqlRequests.updateClassRoom(
					dialog.getId(),
					dialog.getBuilding(),
					dialog.getNumber(),
					dialog.getName(),
					dialog.getSurface(),
					dialog.getResponsiblePerson());
			JOptionPane.showMessageDialog(classRoomPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void actionAddButton() {
		addButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			ClassRoomCreate dialog = new ClassRoomCreate(mainFrame);
			dialog.setVisible(true);
			System.out.println("personId =" + dialog.getResponsiblePerson());
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(classRoomPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}

//			if (!isPersonExist(dialog.getResponsiblePerson())){
//				JOptionPane.showMessageDialog(classRoomPanel, "Ответственного с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			String message = sqlRequests.createNewRowInClassRoomTable(
					dialog.getBuilding(),
					dialog.getNumber(),
					dialog.getName(),
					dialog.getSurface(),
					dialog.getResponsiblePerson());
			JOptionPane.showMessageDialog(classRoomPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);

		});
	}

	private void actionShowButton() {
		showButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			showClassRoomsLabel.setText(sqlRequests.showAllClassRooms());
		});
	}

	private boolean isPersonExist(int personId) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsPersonById(personId);
	}

	private boolean isClassRoomExist(int id) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsClassRoomById(id);
	}

}
