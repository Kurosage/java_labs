package placeworks;

import bookworks.BookDeleter;
import dbworks.SqlRequests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class PlacePanelHandler {
	JPanel placePanel;
	Connection connection;
	JFrame mainFrame;

	JButton showButton;
	JButton addButton;
	JButton updateButton;
	JButton deleteButton;
	JLabel showPlacesLabel;

	public PlacePanelHandler(Connection connection, JFrame mainFrame) {
		placePanel = new JPanel();
		this.mainFrame = mainFrame;
		this.connection = connection;
		//PlacePanel.setLayout(new GridLayout(2, 4));
		initializeButtons();
		initializeLabels();
		setActionsToButtons();
	}

	public JPanel getPlacePanel() {
		return placePanel;
	}

	private void initializeButtons() {
		showButton = new JButton("Показать все места");
		placePanel.add(showButton);
		addButton = new JButton("Добавить");
		placePanel.add(addButton);
		updateButton = new JButton("Изменить");
		placePanel.add(updateButton);
		deleteButton = new JButton("Удалить");
		placePanel.add(deleteButton);
	}
	private void initializeLabels() {
		showPlacesLabel = new JLabel();
		placePanel.add(showPlacesLabel, new GridBagConstraints());
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
			showPlacesLabel.setText(sqlRequests.showAllPlaces());
		});
	}

	private void actionAddButton() {
		addButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PlaceCreator dialog = new PlaceCreator(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(placePanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.createNewRowInPlaceTable(
					dialog.getFloor(),
					dialog.getWardrobe(),
					dialog.getShelf());
			JOptionPane.showMessageDialog(placePanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void actionUpdateButton() {
		updateButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PlaceUpdater dialog = new PlaceUpdater(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(placePanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isPlaceExist(dialog.getId())) {
				JOptionPane.showMessageDialog(placePanel, "Книги с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.updatePlace(
					dialog.getId(),
					dialog.getFloor(),
					dialog.getWardrobe(),
					dialog.getShelf());
			JOptionPane.showMessageDialog(placePanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});

	}

	private void actionDeleteButton() {
		deleteButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PlaceDeleter dialog = new PlaceDeleter(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(placePanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isPlaceExist(dialog.getId())) {
				JOptionPane.showMessageDialog(placePanel, "Места с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.deletePlace(dialog.getId());
			JOptionPane.showMessageDialog(placePanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private boolean isPlaceExist(int placeId) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsPlaceById(placeId);
	}

}
