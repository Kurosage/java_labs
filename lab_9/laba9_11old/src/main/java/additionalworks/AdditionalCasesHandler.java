package additionalworks;

import dbworks.SqlRequests;
import models.Book;
import models.FileDataHandler;
import models.Place;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdditionalCasesHandler {
	JPanel additionalPanel;
	Connection connection;
	JFrame mainFrame;

	JButton getFromFileButton;
	JButton thirdFieldByLexicOrderButton;
	JButton secondFieldInBookButton;
	JButton PHInWardrobeButton;
	JButton minAndMaxPagesButton;
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
		thirdFieldByLexicOrderButton = new JButton("Получить 3 поле книг в лекс. порядке");
		additionalPanel.add(thirdFieldByLexicOrderButton);
		secondFieldInBookButton = new JButton("Получить 2 поле книг");
		additionalPanel.add(secondFieldInBookButton);
		PHInWardrobeButton = new JButton("Получить издательства в шкафу в лекс. порядке");
		additionalPanel.add(PHInWardrobeButton);
		minAndMaxPagesButton = new JButton("Получить издательства с мин и макс страницами");
		additionalPanel.add(minAndMaxPagesButton);
	}

	private void initializeLabels() {
		labelShowTable = new JLabel();
		additionalPanel.add(labelShowTable, new GridBagConstraints());
	}
	private void setActionsToButtons() {
		getThirdFieldByLexicOrderAction();
		getFromFileAction();
		getSecondFieldInBooksAction();
		getPHInWardrobeAction();
		getMinAndMaxPagesAction();
	}

	private void getMinAndMaxPagesAction() {
		minAndMaxPagesButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PagesCounterDialog dialog = new PagesCounterDialog(mainFrame);
			dialog.setVisible(true);
			if (dialog.isError()) {
				JOptionPane.showMessageDialog(additionalPanel, "Ошибка параметров!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelShowTable.setText(sqlRequests.showBookWithMinAndMaxPages(
					dialog.getFloor()));
		});
	}

	private void getPHInWardrobeAction() {
		PHInWardrobeButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			PHInWardrobe dialog = new PHInWardrobe(mainFrame);
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

	private void getFromFileAction() {
		getFromFileButton.addActionListener(e -> {
			replaceWithDefaultDataFromFile();
		});
	}

	private void getSecondFieldInBooksAction() {
		secondFieldInBookButton.addActionListener(e -> {
			SqlRequests sqlRequests = new SqlRequests(connection);
			labelShowTable.setText(sqlRequests.showSecondFieldInAllBooks());
		});
	}

	private void replaceWithDefaultDataFromFile() {
		SqlRequests sqlRequests = new SqlRequests(connection);
		FileDataHandler fileHandler = new FileDataHandler("src/main/java/DataFile.txt");
		String message = fileHandler.readData();
		if (!fileHandler.isError()) {
			sqlRequests.deleteAllBook();
			sqlRequests.deleteAllPlace();
			for (Place place: fileHandler.getPlaceList()) {
				sqlRequests.createNewRowInPlaceTable(place.getFloor(), place.getWardrobe(), place.getShelf());
			}
			for ( Book book: fileHandler.getBookList()) {
				sqlRequests.createNewRowInBookTableWOPlaceId(
						book.getAuthor(), book.getPublication(),
						book.getPublishing_house(), book.getYear_public(),
						book.getPages(), book.getYear_write(),
						book.getWeight());
			}
		}
		JOptionPane.showMessageDialog(additionalPanel, message, "Инфо", JOptionPane.INFORMATION_MESSAGE);
	}





	public JPanel getAdditionalPanel() {
		return additionalPanel;
	}
}
