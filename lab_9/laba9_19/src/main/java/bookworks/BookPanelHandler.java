package bookworks;

import dbworks.SqlRequests;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class BookPanelHandler {
	JPanel bookPanel;
	Connection connection;
	JFrame mainFrame;

	JButton showButton;
	JButton addButton;
	JButton updateButton;
	JButton deleteButton;
	JLabel showBooksLabel;

	public BookPanelHandler(Connection connection, JFrame mainFrame) {
		bookPanel = new JPanel();
		this.mainFrame = mainFrame;
		this.connection = connection;
		//bookPanel.setLayout(new GridLayout(2, 4));
		initializeButtons();
		initializeLabels();
		setActionsToButtons();
	}

	public JPanel getBookPanel() {
		return bookPanel;
	}

	private void initializeButtons() {
		showButton = new JButton("Показать все книги");
		bookPanel.add(showButton);
		addButton = new JButton("Добавить");
		bookPanel.add(addButton);
		updateButton = new JButton("Изменить");
		bookPanel.add(updateButton);
		deleteButton = new JButton("Удалить");
		bookPanel.add(deleteButton);
	}
	private void initializeLabels() {
		showBooksLabel = new JLabel();
		bookPanel.add(showBooksLabel, new GridBagConstraints());
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
			BookDeleter dialog = new BookDeleter(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(bookPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isBookExist(dialog.getId())) {
				JOptionPane.showMessageDialog(bookPanel, "Книги с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.deleteBook(dialog.getId());
			JOptionPane.showMessageDialog(bookPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});

	}

	private void actionUpdateButton() {
		updateButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			BookUpdater dialog = new BookUpdater(mainFrame);
			dialog.setVisible(true);
			System.out.println("placeId =" + dialog.getPlaceId());
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(bookPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isBookExist(dialog.getId())) {
				JOptionPane.showMessageDialog(bookPanel, "Книги с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!isPlaceExist(dialog.getPlaceId())){
				JOptionPane.showMessageDialog(bookPanel, "Места с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.updateBook(
					dialog.getId(),
					dialog.getAuthor(),
					dialog.getPublication(),
					dialog.getPublishingHouse(),
					dialog.getYearPublic(),
					dialog.getPages(),
					dialog.getYearWrite(),
					dialog.getWeight(),
					dialog.getPlaceId());
			JOptionPane.showMessageDialog(bookPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void actionAddButton() {
		addButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			BookCreator dialog = new BookCreator(mainFrame);
			dialog.setVisible(true);
			System.out.println("placeId =" + dialog.getPlaceId());
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(bookPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!isPlaceExist(dialog.getPlaceId())){
				JOptionPane.showMessageDialog(bookPanel, "Места с таким id не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String message = sqlRequests.createNewRowInBookTable(
					dialog.getAuthor(),
					dialog.getPublication(),
					dialog.getPublishingHouse(),
					dialog.getYearPublic(),
					dialog.getPages(),
					dialog.getYearWrite(),
					dialog.getWeight(),
					dialog.getPlaceId());
			JOptionPane.showMessageDialog(bookPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);

		});
	}

	private void actionShowButton() {
		showButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			showBooksLabel.setText(sqlRequests.showAllBooks());
		});
	}

	private boolean isPlaceExist(int placeId) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsPlaceById(placeId);
	}

	private boolean isBookExist(int id) {
		SqlRequests sqlRequests = new SqlRequests(connection);
		return sqlRequests.checkExistsBookById(id);
	}

}
