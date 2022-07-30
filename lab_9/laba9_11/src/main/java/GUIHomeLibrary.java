import additionalworks.AdditionalCasesHandler;
import classRoomCrud.ClassRoomPanelHandler;
import db.DatabaseConnector;
import personCrud.PersonPanelHandler;

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

		PersonPanelHandler placePanel;
		ClassRoomPanelHandler bookPanel;
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
			bookPanel = new ClassRoomPanelHandler(connection, this);
			this.add(bookPanel.getClassRoomPanel());
			placePanel = new PersonPanelHandler(connection, this);
			this.add(placePanel.getPersonPanel());
			additionalPanel = new AdditionalCasesHandler(connection, this);
			this.add(additionalPanel.getAdditionalPanel());
		}
	}
}
