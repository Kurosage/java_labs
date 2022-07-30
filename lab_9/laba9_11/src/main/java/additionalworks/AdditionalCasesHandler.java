package additionalworks;

import db.SqlRequests;
import model.ClassRoom;
import model.FileDataHandler;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdditionalCasesHandler {
	JPanel additionalPanel;
	Connection connection;
	JFrame mainFrame;

	JButton getFromFileButton;
	JButton thirdFieldByLexicOrderButton;
	JButton secondFieldInClassRoomButton;
	JButton showNumbersButton;
	JButton totalSurfaceButton;
	JLabel  labelShowTable;

	public AdditionalCasesHandler(Connection connection, JFrame mainFrame) {
		additionalPanel = new JPanel();
		this.mainFrame = mainFrame;
		this.connection = connection;
		initializeButtons();
		initializeLabels();
		setActionsToButtons();
	}

	private void initializeButtons() {
		getFromFileButton = new JButton("Получить данные из файла");
		additionalPanel.add(getFromFileButton);
		thirdFieldByLexicOrderButton = new JButton("Получить 2-е поле аудитории в лексикографическом порядке");
		additionalPanel.add(thirdFieldByLexicOrderButton);
		secondFieldInClassRoomButton = new JButton("Получить 2 поле ответственного в лексикографическом порядке");
		additionalPanel.add(secondFieldInClassRoomButton);
		showNumbersButton = new JButton("Вывести список аудиторий в указанном здании");
		additionalPanel.add(showNumbersButton);
		totalSurfaceButton = new JButton("Вывести список аудиторий закреплённых за указанным ответственным");
		additionalPanel.add(totalSurfaceButton);
	}

	private void initializeLabels() {
		labelShowTable = new JLabel();
		additionalPanel.add(labelShowTable);
	}
	private void setActionsToButtons() {
		getThirdFieldByLexicOrderAction();
		getFromFileAction();
		getSecondFieldInBooksAction();
		getNumbersInBuildingAction();
		getTotalSurfaceToPersonAction();
	}

	private void getTotalSurfaceToPersonAction() {
		totalSurfaceButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			TotalSurfaceDialog dialog = new TotalSurfaceDialog(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(additionalPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelShowTable.setText(sqlRequests.showTotalSurfaceInPerson(dialog.getResponsiblePerson()));
		});
	}

	private void getNumbersInBuildingAction() {
		showNumbersButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			NumberBuildingDialog dialog = new NumberBuildingDialog(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(additionalPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelShowTable.setText(sqlRequests.showNumberInBuilding(
					dialog.getBuilding()));
		});
	}

	private void getThirdFieldByLexicOrderAction() {
		thirdFieldByLexicOrderButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			labelShowTable.setText(sqlRequests.showThirdFieldInAllClassRoomByLexicOrder());
		});
	}

	private void getFromFileAction() {
		getFromFileButton.addActionListener(e -> {
			replaceWithDefaultDataFromFile();
		});
	}

	private void getSecondFieldInBooksAction() {
		secondFieldInClassRoomButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			labelShowTable.setText(sqlRequests.showSecondFieldInAllClassRoom());
		});
	}

	private void replaceWithDefaultDataFromFile() {
		SqlRequests sqlRequests = new SqlRequests(connection);
		FileDataHandler fileHandler = new FileDataHandler("src/main/java/DataFile.txt");
		String message = fileHandler.readData();
		if (!fileHandler.isError()) {
			sqlRequests.deleteAllClassRoom();
			sqlRequests.deleteAllPerson();
			for (Person person: fileHandler.getPersonList()) {
				sqlRequests.createNewRowInPersonTable(person.getFio(), person.getPosition(), person.getPhone(), person.getAge());
			}
			for ( ClassRoom classRoom: fileHandler.getClassRoomList()) {
				sqlRequests.createNewRowInClassRoomTableWOPersonId(
						classRoom.getBuilding(), classRoom.getNumber(), classRoom.getName(), classRoom.getSurface());
			}
		}
		JOptionPane.showMessageDialog(additionalPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
	}

	public JPanel getAdditionalPanel() {
		return additionalPanel;
	}
}
