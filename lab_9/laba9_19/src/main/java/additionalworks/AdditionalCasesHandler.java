package additionalworks;

import dbworks.SqlRequests;
import models.Book;
import models.Place;
import models.DefDBDataHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdditionalCasesHandler {
	JPanel additionalPanel;
	Connection connection;
	JFrame mainFrame;

	JButton getDefFromProgramButton;
	JButton thirdFieldByLexicOrderButton;
	JButton firstFieldInBookButton;
	JButton authorsInWardrobeButton;
	JButton sumWeightInWardrobeButton;
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
		getDefFromProgramButton = new JButton("Получить данные из программы");
		additionalPanel.add(getDefFromProgramButton);
		thirdFieldByLexicOrderButton = new JButton("Получить 3 поле книг в лекс. порядке");
		additionalPanel.add(thirdFieldByLexicOrderButton);
		firstFieldInBookButton = new JButton("Получить 1 поле книг");
		additionalPanel.add(firstFieldInBookButton);
		authorsInWardrobeButton = new JButton("Получить авторов в шкафу в лекс. порядке");
		additionalPanel.add(authorsInWardrobeButton);
		sumWeightInWardrobeButton = new JButton("Получить суммарный вес изданий в шкафу");
		additionalPanel.add(sumWeightInWardrobeButton);
	}

	private void initializeLabels() {
		labelShowTable = new JLabel();
		additionalPanel.add(labelShowTable, new GridBagConstraints());
	}
	private void setActionsToButtons() {
		getThirdFieldByLexicOrderAction();
		getDefFromProgramAction();
		getFirstFieldInBooksAction();
		getAuthorsInWardrobeAction();
		getSumWeightInWardrobeAction();
	}

	private void getSumWeightInWardrobeAction() {
		sumWeightInWardrobeButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			FieldsInWardrobeDialog dialog = new FieldsInWardrobeDialog(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(additionalPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelShowTable.setText("Суммарный вес: " + sqlRequests.showSummWeightInWardrobe(
					dialog.getFloor(),
					dialog.getWardrobe()) + "г");
		});


	}


	private void getAuthorsInWardrobeAction() {
		authorsInWardrobeButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			FieldsInWardrobeDialog dialog = new FieldsInWardrobeDialog(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(additionalPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelShowTable.setText(sqlRequests.showPublishingHouseInWardrobe(
					dialog.getFloor(),
					dialog.getWardrobe()));
		});
	}

	private void getThirdFieldByLexicOrderAction() {
		thirdFieldByLexicOrderButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			labelShowTable.setText(sqlRequests.showThirdFieldInAllBooksByLexicOrder());
		});
	}

	private void getDefFromProgramAction() {
		getDefFromProgramButton.addActionListener(e -> {
			replaceWithDefaultDataFromProgram();
		});
	}

	private void getFirstFieldInBooksAction() {
		firstFieldInBookButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			labelShowTable.setText(sqlRequests.showFirstFieldInAllBooks());
		});
	}

	private void replaceWithDefaultDataFromProgram() {
		SqlRequests sqlRequests = new SqlRequests(connection);
		sqlRequests.deleteAllBook();
		sqlRequests.deleteAllPlace();
		DefDBDataHandler dataHandler = new DefDBDataHandler();
		dataHandler.writeDataToLists();
		for (Place place: dataHandler.getPlaceList()) {
			sqlRequests.createNewRowInPlaceTable(
					place.getFloor(), place.getWardrobe(), place.getShelf());
		}
		for ( Book book: dataHandler.getBookList()) {
			sqlRequests.createNewRowInBookTableWOPlaceId(
					book.getAuthor(), book.getPublication(),
					book.getPublishing_house(), book.getYear_public(),
					book.getPages(), book.getYear_write(),
					book.getWeight());
		}
		JOptionPane.showMessageDialog(additionalPanel, "Данные из программы получены!",
				"Инфо", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public JPanel getAdditionalPanel() {
		return additionalPanel;
	}
}
