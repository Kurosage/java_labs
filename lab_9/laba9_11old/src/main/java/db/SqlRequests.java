package db;

import java.sql.*;
import java.util.Random;


public class SqlRequests {
	Connection connection;
	PreparedStatement preparedStatement;

	public SqlRequests(Connection connection) {
		this.connection = connection;
	}

	public String createNewRowInPersonTable(String fio, String position, String phone, int age) {
		try {
			preparedStatement = connection.prepareStatement(
					"insert into persons(fio, position, phone, age) values (?, ?, ?, ?)");
			preparedStatement.setString(1, fio);
			preparedStatement.setString(2, position);
			preparedStatement.setString(3, phone);
			preparedStatement.setInt(4, age);
			preparedStatement.executeUpdate();
			return "Новое ответственное лицо создано";
		}
		catch (SQLException e){
			return "Дупликаты запрещены!";
		}
	}

	public String showAllPersons() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from persons order by id");
			StringBuilder table = new StringBuilder("<html><pre> id|                 fio                |" +
					"     position       |        phone    | age<br>");
			while (resultSet.next()) {
				table.append(String.format("%3d %30s %25s %15s %7d<br>",
						resultSet.getInt("id"),
						resultSet.getString("fio"),
						resultSet.getString("position"),
						resultSet.getString("phone"),
						resultSet.getInt("age")));
			}
			table.append("</pre></html>");
			return table.toString();
		} catch (SQLException e) {
			return "Произошла ошибка при просмотре!";
		}
	}

	public String createNewRowInClassRoomTable(int building, int number, String name,
			double surface, int responsiblePerson) {
		try {
			preparedStatement = connection.prepareStatement(
					"insert into class_rooms (building, number, name," +
							" surface, responsible_person)" +
							" values (?, ?, ?, ?, ?)");
			setClassRoomParametersInStatement(building, number, name, surface);
			preparedStatement.setInt(5, responsiblePerson);
			preparedStatement.executeUpdate();
			return "Новая аудитория создана!";
		}
		catch (SQLException e) {
			return "Произошла ошибка при добавлении!";
		}
	}

	public String showAllClassRooms() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from class_rooms order by id");
			return "<html><pre>" +
					getClassRoomTableFromResultSet(resultSet) +
					"</pre></html>";
		}
		catch (SQLException e) {
			return "Произошла ошибка при просмотре!";
		}
	}

	public boolean checkExistsPersonById(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from persons order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			ResultSet resultSet2 = statement.executeQuery( "select count(*) from persons where id = " + resultSet.getInt("id"));
			resultSet2.next();
			System.out.println("count = " + resultSet2.getInt("count"));
			return resultSet2.getInt("count") == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public String updatePerson(int id, String fio, String position, String phone, int age) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from persons order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			preparedStatement = connection.prepareStatement(
					"update persons set fio = ?, position = ?, phone = ?, age = ? where id = ?");
			preparedStatement.setString(1, fio);
			preparedStatement.setString(2, position);
			preparedStatement.setString(3, phone);
			preparedStatement.setInt(4, age);
			preparedStatement.setInt(5, resultSet.getInt("id"));
			preparedStatement.executeUpdate();
			return "Ответственное лицо обновлено";
		}
		catch (SQLException e){
			return "Соединение закрыто!";
		}

	}

	public boolean checkExistsClassRoomById(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from class_rooms order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			statement = connection.createStatement();
			ResultSet resultSet2 = statement.executeQuery( "select count(*) from class_rooms where id = " + resultSet.getInt("id"));
			resultSet2.next();
			return resultSet2.getInt("count") == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public String updateClassRoom(int id, int building, int number, String name,
								  double surface, int responsiblePerson) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from class_rooms order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			preparedStatement = connection.prepareStatement(
					"update class_rooms set building = ?, number = ?, name = ?," +
							" surface = ?, responsible_person = ? where id = ?");
			setClassRoomParametersInStatement(building, number, name,
					surface);
			preparedStatement.setInt(5, responsiblePerson);
			preparedStatement.setInt(6, resultSet.getInt("id"));
			preparedStatement.executeUpdate();
			return "Аудитория обновлена!";
		}
		catch (SQLException e) {
			return "Произошла ошибка при обновлении!";
		}
	}

	private void setClassRoomParametersInStatement(int building, int number, String name,
			double surface) throws SQLException {
		preparedStatement.setInt(1, building);
		preparedStatement.setInt(2, number);
		preparedStatement.setString(3, name);
		preparedStatement.setDouble(4, surface);
	}

	public String deletePerson(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from persons order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			preparedStatement = connection.prepareStatement(
					"delete from persons where id = ?");
			preparedStatement.setInt(1, resultSet.getInt("id"));
			if (preparedStatement.executeUpdate() == 0)
				return "Ошибка при удалении ответственного лица";
			return "Ответственное лицо удалено!";
		}
		catch (SQLException e) {
			return "Ошибка при удалении ответственного лица";
		}
	}

	public String deleteClassRoom(int id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from class_rooms order by id");

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			preparedStatement = connection.prepareStatement(
					"delete from class_rooms where id = ?");
			preparedStatement.setInt(1, resultSet.getInt("id"));
			if (preparedStatement.executeUpdate() == 0)
				return "Ошибка при удалении аудитории!";
			return "Аудитория удалена!";
		}
		catch (SQLException e) {
			return "Ошибка при удалении аудитории!";
		}
	}

	public String showSecondFieldInAllClassRoom() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery( "select building from class_rooms");
			StringBuilder table = new StringBuilder("<html>building:<br>");
			while (resultSet.next()) {
				table.append(resultSet.getString("building"))
						.append("<br>");
			}
			table.append("</html>");
			return table.toString();

		} catch (SQLException e) {
			return "Произошла ошибка при просмотре!";
		}
	}

	public String showThirdFieldInAllClassRoomByLexicOrder() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select name from class_rooms order by class_rooms.name");
			StringBuilder table = new StringBuilder("<html>name:<br>");
			while (resultSet.next()) {
				table.append(resultSet.getString("name"))
						.append("<br>");
			}
			table.append("</html>");
			return table.toString();
		} catch (SQLException e) {
			return "Произошла ошибка при просмотре!";
		}
	}

	public void deleteAllClassRoom() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"delete from class_rooms");
		}
		catch (SQLException ignored) {
		}
	}

	public void deleteAllPerson() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"delete from persons");
		}
		catch (SQLException ignored) {
		}
	}

	public String createNewRowInClassRoomTableWOPersonId( int building, int number, String name,
			double surface) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from persons order by id");
			Random random = new Random();
			int id = random.nextInt(2) + 1;

			for (int j = 0; j < id; j++) {
				resultSet.next();
			}

			preparedStatement = connection.prepareStatement(
					"insert into class_rooms (building, number, name," +
							" surface, responsible_person)" +
							" values (?, ?, ?, ?, ?)");
			setClassRoomParametersInStatement(building, number, name, surface);
			preparedStatement.setInt(5, resultSet.getInt("id"));
			preparedStatement.executeUpdate();
			return "Новая аудитория создана!";
		}
		catch (SQLException e) {
			System.out.println("Произошла ошибка при добавлении!");
			return "Произошла ошибка при добавлении!";
		}

	}

	public double showTotalSurfaceInPerson(int responsiblePerson) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select sum(surface) from class_rooms where responsible_person = " + responsiblePerson);
			resultSet.next();
			return resultSet.getDouble("sum");
		}
		catch (SQLException ignored) {
			return -1;
		}
	}

	public String showNumberInBuilding(int building) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select number from class_rooms where building = " + building + " order by number");
			StringBuilder table = new StringBuilder("<html>number:<br>");
			while (resultSet.next()) {
				table.append(resultSet.getString("number"))
						.append("<br>");
			}
			return table.toString();
		}
		catch (SQLException ignored) {
			return "Произошла ошибка при просмотре!";
		}
	}

	private String getClassRoomTableFromResultSet(ResultSet resultSet) throws SQLException {
		StringBuilder table = new StringBuilder(
				" id| building | number |      name     |" +
						"   surface  |responsible_person <br>");
		while (resultSet.next()) {
			table.append(String.format(
					"%3d %5d %10d %15s %15f %5d <br>",
					resultSet.getInt("id"),
					resultSet.getInt("building"),
					resultSet.getInt("number"),
					resultSet.getString("name"),
					resultSet.getDouble("surface"),
					resultSet.getInt("responsible_person")));
		}
		return table.toString();
	}
}
