package model;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDataHandler {

	private String pathToFile;
	private List<ClassRoom> classRoomList = new ArrayList<>();
	private List<Person> personList = new ArrayList<>();

	public boolean isError() {
		return error;
	}

	private boolean error = false;

public FileDataHandler(String pathToFile) {
	this.pathToFile = pathToFile;
}

	public String readData() {
		try (Scanner scanner = new Scanner(new File(pathToFile))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty())
					continue;
				String[] arrayElements = line.split("\\|");
				if (arrayElements.length == 4) {
					addPersonToListFromFileArrayElements(arrayElements);
				}
				else if (arrayElements.length == 5) {
					addClassRoomToListFromFileArrayElements(arrayElements);
				}
			}
			return "Данные из файлы получены!";
		}
		catch (NumberFormatException e) {
			error = true;
			return "Проблема с аргументами!";
		}
		catch (IOException e) {
			error = true;
			return "Проблема с файлом!";
		}
	}

	private void addClassRoomToListFromFileArrayElements(String[] arrayElements) throws NumberFormatException  {
		ClassRoom classRoom = new ClassRoom(Integer.parseInt(arrayElements[0]), Integer.parseInt(arrayElements[1]),
				Double.parseDouble(arrayElements[3]),
				arrayElements[2],
				Integer.parseInt(arrayElements[4]));
		classRoomList.add(classRoom);
	}

	private void addPersonToListFromFileArrayElements(String[] arrayElements) throws NumberFormatException {
		Person person = new Person(
				arrayElements[0],
				arrayElements[1],
				arrayElements[2],
				Integer.parseInt(arrayElements[3]));
		personList.add(person);
	}

	public List<ClassRoom> getClassRoomList() {
		return classRoomList;
	}

	public List<Person> getPersonList() {
		return personList;
	}
}
