import additionalworks.AdditionalCasesHandler;
import bookworks.BookPanelHandler;
import dbworks.DatabaseConnector;
import placeworks.PlacePanelHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class GUIHomeLibrary {

	public static void main(String[] args) {
		try {
			MainMenu mainMenu = new MainMenu();

			//System.out.println("Выключаемся!");
			//connection.close();
		}
		catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных!");
		}
	}


	public static class MainMenu extends JFrame {

		PlacePanelHandler placePanel;
		BookPanelHandler bookPanel;
		AdditionalCasesHandler additionalPanel;
		Connection connection;

		public MainMenu() throws SQLException {
			connection = DatabaseConnector.connectToDataBase();
			initializeMainFrame();
			initializePanelsInMainFrame();
		}

		private void initializeMainFrame() {
			this.setVisible(true);
			this.setSize(900, 700);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(new GridLayout(3,1));
		}

		private void initializePanelsInMainFrame() {
			bookPanel = new BookPanelHandler(connection, this);
			this.add(bookPanel.getBookPanel());
			placePanel = new PlacePanelHandler(connection, this);
			this.add(placePanel.getPlacePanel());
			additionalPanel = new AdditionalCasesHandler(connection, this);
			this.add(additionalPanel.getAdditionalPanel());
		}
	}
}
