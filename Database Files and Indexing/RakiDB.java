import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.Arrays;

public class RakiDB {
	private static final String rakibase_tables = "Data\\Catalog\\rakibase_tables.tbl";
	public static final String rakibase_columns = "Data\\Catalog\\rakibase_columns.tbl";
	private static final String tableLocation = "Data\\User_Data\\";
	private static final String tableFormat = ".tbl";
	private static final String prompt = "rakisql> ";
	private static final String version = "v2.0";
	private static final String copyright = "@2017 Rakesh Balasubramani";
	private static boolean isExit = false;
	private static RandomAccessFile rakiBaseColumnFile;
	private static RandomAccessFile rakiBasetableFile;
	static long pageSize = 512;
	static Scanner scanner = new Scanner(System.in).useDelimiter(";");

	public static void printTable(
			List<LinkedHashMap<String, ArrayList<String>>> table) {
		if (table.isEmpty()) {
			System.out.println("0 Rows Returned");
			return;
		}

		int size[] = new int[table.get(0).size()];
		Arrays.fill(size, -1);
		int k = 0;
		for (LinkedHashMap<String, ArrayList<String>> x : table) {
			k = 0;
			for (String y : x.keySet()) {
				if (size[k] < y.length()) {
					size[k] = y.length();
				}
				if (size[k] < x.get(y).get(0).length()) {
					size[k] = x.get(y).get(0).length();
				}
				k++;
			}
		}
		for (int i : size)
			System.out.print("+" + line("-", i));
		System.out.print("+\n");
		k = 0;
		for (String x : table.get(0).keySet())
			System.out.print("|" + format(x.toUpperCase(), size[k++]));
		System.out.print("|\n");
		for (int i : size)
			System.out.print("+" + line("-", i));
		System.out.print("+\n");
		for (LinkedHashMap<String, ArrayList<String>> x : table) {
			k = 0;
			for (String y : x.keySet())
				System.out.print("|" + format(x.get(y).get(0), size[k++]));
			System.out.print("|\n");
			for (int i : size)
				System.out.print("+" + line("-", i));
			System.out.print("+\n");
		}

	}

	public static String format(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}

	public static void main(String[] args) {

		File folder = new File("Data");
		if (!folder.exists()) {
			folder.mkdir();
			folder = new File("Data/Catalog");
			folder.mkdir();
			folder = new File("Data/User_Data");
			folder.mkdir();
		}
		splashScreen();
		String userCommand = "";
		BTree rakiBasetabletree = null;
		try {
			rakiBasetableFile = new RandomAccessFile(rakibase_tables, "rw");
			rakiBasetabletree = new BTree(rakiBasetableFile, "rakibase_tables",
					false, true);
			if (rakiBasetableFile.length() == 0) {
				LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
				ArrayList<String> array = new ArrayList<String>();
				array.add("int");
				array.add("1");
				token.put("rowid", new ArrayList<String>(array));
				array.clear();
				array.add(" text");
				array.add("rakibase_tables");
				token.put("table_name", new ArrayList<String>(array));
				rakiBasetabletree.createNewTableLeaf(token);
				token.clear();
				array.clear();
				array.add("int");
				array.add("2");
				token.put("rowid", new ArrayList<String>(array));
				array.clear();
				array.add(" text");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));
				rakiBasetabletree.insertNewRecord(token);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unexpected Error");
		}

		try {
			rakiBaseColumnFile = new RandomAccessFile(rakibase_columns, "rw");
			BTree rakiBaseColumnFiletree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);

			if (rakiBaseColumnFile.length() == 0) {
				LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
				ArrayList<String> array = new ArrayList<String>();

				array.add("INT");
				array.add("1");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_tables");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rowid");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("INT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("1");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));

				rakiBaseColumnFiletree.createNewTableLeaf(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("2");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_tables");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("table_name");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TEXT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("2");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("3");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rowid");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("INT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("1");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("4");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("table_name");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TEXT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("2");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("5");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("column_name");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TEXT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("3");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("6");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("data_type");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TEXT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("4");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("7");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("ordinal_position");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TINYINT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("5");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

				array.clear();
				token.clear();

				array.add("INT");
				array.add("8");
				token.put("rowid", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("rakibase_columns");
				token.put("table_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("is_nullable");
				token.put("column_name", new ArrayList<String>(array));

				array.clear();
				array.add(" TEXT");
				array.add("TEXT");
				token.put("data_type", new ArrayList<String>(array));

				array.clear();
				array.add("TINYINT");
				array.add("6");
				token.put("ordinal_position", new ArrayList<String>(array));

				array.clear();
				array.add("TEXT");
				array.add("NO");
				token.put("is_nullable", new ArrayList<String>(array));
				rakiBaseColumnFiletree.insertNewRecord(token);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unexpected Error");
		}

		while (!isExit) {
			System.out.print(prompt);
			userCommand = scanner.next().replace("\n", "").replace("\r", "")
					.trim().toLowerCase();
			// userCommand = userCommand.replace("\n", "").replace("\r", "");
			parseUserCommand(userCommand);
		}
		System.out.println("Exiting...");

	}

	public static void splashScreen() {
		System.out.println(line("-", 80));
		System.out.println("Welcome to RakaiBaseLite"); // Display the string.
		System.out.println("RakiBaseLite Version " + getVersion());
		System.out.println(getCopyright());
		System.out.println("\nType \"help;\" to display supported commands.");
		System.out.println(line("-", 80));
	}

	public static String line(String s, int num) {
		String a = "";
		for (int i = 0; i < num; i++) {
			a += s;
		}
		return a;
	}

	public static void help() {
		System.out.println(line("*", 80));
		System.out.println("SUPPORTED COMMANDS");
		System.out.println("All commands below are case insensitive");
		System.out.println();
		System.out
		.println("\tCREATE TABLE table_name (column_name1 INT PRIMARY KEY, column_name2 data_type2 [NOT NULL],... );                         Create a new table schema if not already exist.");
		System.out
		.println("\tSELECT * FROM table_name;                        Display all records in the table.");
		System.out
		.println("\tSELECT * FROM table_name WHERE <column_name> = <value>;  Display records whose column is <value>.");
		System.out
		.println("\tUPDATE <table_name> SET column_name = value <primary_key=value>;  Modifies one or more records in a table.");
		System.out
		.println("\tINSERT INTO <table_name> (column_list) VALUES (value1, value2, value3,..);  Insert a new record into the indicated table.");
		System.out
		.println("\tDELETE FROM <table_name> WHERE primary_key = key_value; Delete a single row/record from a table given the row_id primary key.");
		System.out
		.println("\tDROP TABLE table_name;                           Remove table data and its schema.");
		System.out
		.println("\tSHOW TABLES;                                     Displays a list of all tables in the Database.");
		System.out
		.println("\tVERSION;                                         Show the program version.");
		System.out
		.println("\tHELP;                                            Show this help information");
		System.out
		.println("\tEXIT;                                            Exit the program");
		System.out.println();
		System.out.println();
		System.out.println(line("*", 80));
	}

	public static String getVersion() {
		return version;
	}

	public static String getCopyright() {
		return copyright;
	}

	public static void displayVersion() {
		System.out.println("RakiBaseLite Version " + getVersion());
		System.out.println(getCopyright());
	}

	public static void parseUserCommand(String userCommand) {

		/*
		 * commandTokens is an array of Strings that contains one token per
		 * array element The first token can be used to determine the type of
		 * command The other tokens can be used to pass relevant parameters to
		 * each command-specific method inside each case statement
		 */
		// String[] commandTokens = userCommand.split(" ");
		ArrayList<String> commandTokens = new ArrayList<String>(
				Arrays.asList(userCommand.split(" ")));

		/*
		 * This switch handles a very small list of hardcoded commands of known
		 * syntax. You will want to rewrite this method to interpret more
		 * complex commands.
		 */
		switch (commandTokens.get(0)) {
		case "select":
			parseQueryString(userCommand);
			break;
		case "drop":
			if (commandTokens.get(1).equals("table"))
				dropTable(commandTokens.get(2));
			else
				System.out.println("Syntax Error");
			break;
		case "create":
			parseCreateString(userCommand);
			break;
		case "insert":
			parseInsertString(userCommand);
			break;
		case "delete":
			parseDeleteString(userCommand);
			break;
		case "update":
			parseUpdateString(userCommand);
			break;
		case "show":
			parseShowString(userCommand);
			break;
		case "help":
			help();
			break;
		case "version":
			displayVersion();
			break;
		case "exit":
			isExit = true;
			break;
		case "quit":
			isExit = true;
		default:
			System.out.println("I didn't understand the command: \""
					+ userCommand + "\"");
			break;
		}
	}

	public static void parseQueryString(String queryString) {
		try {
			queryString = queryString.replace("*", " * ");
			queryString = queryString.replace("=", " = ");
			queryString = queryString.replace(",", " , ");
			ArrayList<String> queryTokens = new ArrayList<String>(
					Arrays.asList(queryString.split("\\s+")));
			String tableName = "";
			ArrayList<String> cols = new ArrayList<String>();
			Map<String, ArrayList<String>> tableInfo = new LinkedHashMap<String, ArrayList<String>>();
			RandomAccessFile newTable = null;
			try {
				rakiBaseColumnFile = new RandomAccessFile(rakibase_columns,
						"rw");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			BTree columnBTree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);
			int flag = 1;
			if (queryTokens.get(1).equals("*")
					&& queryTokens.get(2).equals("from")
					&& queryTokens.size() == 4) {
				tableName = queryTokens.get(3);
				tableInfo = columnBTree.getSchema(queryTokens.get(3));
				if (tableInfo != null) {
					// System.out.println("*"); //select * from table;
					BTree tableBTree;
					try {
						if (tableName.trim().equals("rakibase_tables")) {
							tableBTree = new BTree(rakiBasetableFile,
									tableName, false, true);

						} else if (tableName.trim().equals("rakibase_columns")) {
							tableBTree = columnBTree;

						} else {
							newTable = new RandomAccessFile(tableLocation
									+ tableName + tableFormat, "rw");
							tableBTree = new BTree(newTable, tableName);
						}
						printTable(tableBTree.printAll());
						try {
							if (newTable != null)
								newTable.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Unexpected Error");
						}

					}

					catch (FileNotFoundException e) {
						System.out
						.println("Error while reading table, Corresponding table.tbl not found.");
					}
				}
			} else if (queryTokens.get(1).equals("*")
					&& queryTokens.get(2).equals("from")
					&& queryTokens.get(4).equals("where")
					&& queryTokens.get(6).equals("=")) {
				tableName = queryTokens.get(3);
				tableInfo = columnBTree.getSchema(queryTokens.get(3));
				if (tableInfo != null
						&& tableInfo.keySet().contains(queryTokens.get(5))) {
					// System.out.println("*" + queryTokens.get(5)
					// + queryTokens.get(7)); ////select * from table where
					// cond;

					BTree tableBTree;
					try {

						if (tableName.trim().equals("rakibase_tables")) {
							tableBTree = new BTree(rakiBasetableFile,
									tableName, false, true);

						} else if (tableName.trim().equals("rakibase_columns")) {
							tableBTree = new BTree(new RandomAccessFile(
									rakibase_columns, "rw"), tableName, true,
									false);

						} else {
							newTable = new RandomAccessFile(tableLocation
									+ tableName + tableFormat, "rw");
							tableBTree = new BTree(newTable, tableName);
						}

					} catch (FileNotFoundException e) {
						System.out
						.println("Error while reading table, Corresponding table.tbl not found.");
						return;
					}
					// search cond
					ArrayList<String> arryL = new ArrayList<String>();
					Integer ordinalPos = 0;
					String dataType = "";
					for (String key : tableInfo.keySet()) {
						if (key.equals(queryTokens.get(5))) {
							dataType = tableInfo.get(key).get(0);
							break;
						}
						ordinalPos++;
					}
					arryL.add(ordinalPos.toString()); // search cond col ordinal
					// position
					arryL.add(dataType); // search cond col data type
					if ((queryTokens.get(7).charAt(0) == '\'' && queryTokens
							.get(7).charAt(queryTokens.get(7).length() - 1) == '\'')
							|| (queryTokens.get(7).charAt(0) == '"' && queryTokens
							.get(7).charAt(
									queryTokens.get(7).length() - 1) == '"'))
						arryL.add(queryTokens.get(7).substring(1,
								queryTokens.get(7).length() - 1));
					else
						arryL.add(queryTokens.get(7));
					// arryL.add(queryTokens.get(7)); // search cond col value

					if (ordinalPos == 0) {
						LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
						ArrayList<String> array = new ArrayList<String>();
						array.add(dataType);
						if ((queryTokens.get(7).charAt(0) == '\'' && queryTokens
								.get(7).charAt(queryTokens.get(7).length() - 1) == '\'')
								|| (queryTokens.get(7).charAt(0) == '"' && queryTokens
								.get(7)
								.charAt(queryTokens.get(7).length() - 1) == '"'))
							array.add(queryTokens.get(7).substring(1,
									queryTokens.get(7).length() - 1));
						else
							array.add(queryTokens.get(7));

						token.put(queryTokens.get(5), new ArrayList<String>(
								array));
						LinkedHashMap<String, ArrayList<String>> op = tableBTree
								.searchWithPrimaryKey(token);
						List<LinkedHashMap<String, ArrayList<String>>> temp = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
						temp.add(op);
						printTable(temp);

					} else {
						List<LinkedHashMap<String, ArrayList<String>>> op = tableBTree
								.searchNonPrimaryCol(arryL);
						printTable(op);

					}
					try {
						if (newTable != null)
							newTable.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Unexpected Error");
					}

				}
			} else {
				tableName = queryTokens.get(queryTokens.indexOf("from") + 1);
				tableInfo = columnBTree.getSchema(queryTokens.get(queryTokens
						.indexOf("from") + 1));
				if (tableInfo != null) {
					for (int i = 1; i < queryTokens.indexOf("from")
							&& flag == 1; i++) {
						if (tableInfo.keySet().contains(queryTokens.get(i))
								&& queryTokens.get(i + 1).equals(",")) {
							cols.add(queryTokens.get(i));
							i++;
						} else if (tableInfo.keySet().contains(
								queryTokens.get(i))
								&& queryTokens.get(i + 1).equals("from"))
							cols.add(queryTokens.get(i));
						else
							flag = 0;
					}

					if (flag == 1) // select "coln" from table;
					{
						ArrayList<String> removeColumn = new ArrayList<String>(
								tableInfo.keySet());
						removeColumn.removeAll(cols);
						BTree tableBTree;
						try {

							if (tableName.trim().equals("rakibase_tables")) {
								tableBTree = new BTree(rakiBasetableFile,
										tableName, false, true);

							} else if (tableName.trim().equals(
									"rakibase_columns")) {
								tableBTree = new BTree(new RandomAccessFile(
										rakibase_columns, "rw"), tableName,
										true, false);

							} else {
								newTable = new RandomAccessFile(tableLocation
										+ tableName + tableFormat, "rw");
								tableBTree = new BTree(newTable, tableName);
							}

							List<LinkedHashMap<String, ArrayList<String>>> op = tableBTree
									.printAll();
							for (LinkedHashMap<String, ArrayList<String>> map : op) {

								for (String x : removeColumn) {
									map.remove(x);
								}

							}
							if (!queryTokens.contains("where")) // condition not
								// working
							{
								printTable(op);
							}
							if (newTable != null)
								newTable.close();
						}

						catch (Exception e) {
							System.out
							.println("Error while reading table, Corresponding table.tbl not found.");
						}

					}
					if (queryTokens.size() > queryTokens.indexOf("from") + 2
							&& queryTokens.get(queryTokens.indexOf("from") + 2)
							.equals("where")
							&& queryTokens.get(queryTokens.indexOf("from") + 4)
							.equals("=")
							&& tableInfo.keySet()
							.contains(
									queryTokens.get(queryTokens
											.indexOf("from") + 3))) {
						// System.out.println(queryTokens.get(queryTokens
						// .indexOf("from") + 3)
						// + queryTokens.get(queryTokens.indexOf("from") + 5));
						// // select coln from table where cond;

						ArrayList<String> removeColumn = new ArrayList<String>(
								tableInfo.keySet());
						removeColumn.removeAll(cols);

						// System.out.println("*" + queryTokens.get(5)
						// + queryTokens.get(7)); ////select * from table where
						// cond;

						BTree tableBTree;
						try {

							if (tableName.trim().equals("rakibase_tables")) {
								tableBTree = new BTree(rakiBasetableFile,
										tableName, false, true);

							} else if (tableName.trim().equals(
									"rakibase_columns")) {
								tableBTree = new BTree(new RandomAccessFile(
										rakibase_columns, "rw"), tableName,
										true, false);

							} else {
								newTable = new RandomAccessFile(tableLocation
										+ tableName + tableFormat, "rw");
								tableBTree = new BTree(newTable, tableName);
							}

						} catch (FileNotFoundException e) {
							System.out
							.println("Error while reading table, Corresponding table.tbl not found.");
							return;
						}
						// search cond
						ArrayList<String> arryL = new ArrayList<String>();
						Integer ordinalPos = 0;
						String dataType = "";
						for (String key : tableInfo.keySet()) {
							if (key.equals(queryTokens.get(queryTokens.size() - 3))) {
								dataType = tableInfo.get(key).get(0);
								break;
							}
							ordinalPos++;
						}
						arryL.add(ordinalPos.toString()); // search cond col
						// ordinal position
						arryL.add(dataType); // search cond col data type
						if ((queryTokens.get(queryTokens.size() - 1).charAt(0) == '\'' && queryTokens
								.get(queryTokens.size() - 1).charAt(
										queryTokens.get(queryTokens.size() - 1)
										.length() - 1) == '\'')
										|| (queryTokens.get(queryTokens.size() - 1)
												.charAt(0) == '"' && queryTokens.get(
														queryTokens.size() - 1).charAt(
																queryTokens.get(queryTokens.size() - 1)
																.length() - 1) == '"'))
							queryTokens.get(queryTokens.size() - 1).substring(
									1,
									queryTokens.get(queryTokens.size() - 1)
									.length() - 1);
						arryL.add(queryTokens.get(queryTokens.size() - 1)); // search
						// cond
						// col
						// value

						if (ordinalPos == 0) {
							LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
							ArrayList<String> array = new ArrayList<String>();
							array.add(dataType);
							array.add(queryTokens.get(7));

							token.put(queryTokens.get(5),
									new ArrayList<String>(array));
							LinkedHashMap<String, ArrayList<String>> op = tableBTree
									.searchWithPrimaryKey(token);
							List<LinkedHashMap<String, ArrayList<String>>> temp = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
							temp.add(op);

							for (LinkedHashMap<String, ArrayList<String>> map : temp) {

								for (String x : removeColumn) {
									map.remove(x);
								}

							}
							printTable(temp);

						} else {
							List<LinkedHashMap<String, ArrayList<String>>> op = tableBTree
									.searchNonPrimaryCol(arryL);
							for (LinkedHashMap<String, ArrayList<String>> map : op) {

								for (String x : removeColumn) {
									map.remove(x);
								}

							}
							printTable(op);

						}
						try {

							if (newTable != null)
								newTable.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Unexpected Error");
						}

					}

				}
			}
			if (flag == 0)
				System.out.println("Syntax Error");
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}

	}

	public static void parseCreateString(String createTableString) {

		try {

			int flag = 1, op = 1;
			try {
				rakiBasetableFile = new RandomAccessFile(rakibase_tables, "rw");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			BTree rakiBasetabletree = new BTree(rakiBasetableFile,
					"rakibase_tables", false, true);
			BTree rakiBaseColumntree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);
			int cr = rakiBaseColumntree.getNextMaxRowID() + 1;
			createTableString = createTableString.replace("(", " ( ");
			createTableString = createTableString.replace(")", " ) ");
			createTableString = createTableString.replace(",", " , ");
			List<LinkedHashMap<String, ArrayList<String>>> schematableColList = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
			LinkedHashMap<String, ArrayList<String>> newTable = new LinkedHashMap<String, ArrayList<String>>();
			LinkedHashMap<String, ArrayList<String>> newColumns = new LinkedHashMap<String, ArrayList<String>>();
			ArrayList<String> dataType = new ArrayList<String>(Arrays.asList(
					"tinyint", "smallint", "int", "bigint", "real", "double",
					"datetime", "date", "text"));
			ArrayList<String> createTableTokens = new ArrayList<String>(
					Arrays.asList(createTableString.split("\\s+")));

			// createTableTokens.removeAll(Collections.singleton(""));
			if (createTableTokens.get(2).trim().equals("rakibase_columns")) {
				System.out.println("Schema table name is not allowed");
				return;
			} else if (createTableTokens.get(2).trim()
					.equals("rakibase_tables")) {
				System.out.println("Schema table name is not allowed");
				return;
			}

			File folder = new File("Data/User_Data");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++)
				if (listOfFiles[i].getName().equals(
						createTableTokens.get(2) + ".tbl")) {
					System.out.println("Table already exists!!!");
					flag = 0;
					return;

				}
			if (!createTableTokens.get(3).equals("(")
					|| !createTableTokens.get(1).equals("table")
					|| !createTableTokens.get(2).matches(
							"^[a-zA-Z][a-zA-Z0-9_]*$")
							|| !createTableTokens.get(4).matches(
									"^[a-zA-Z][a-zA-Z0-9_]*$")
									|| !createTableTokens.get(5).equals("int")
									|| !createTableTokens.get(6).equals("primary")
									|| !createTableTokens.get(7).equals("key"))
				flag = 0;
			if (flag == 1) {
				newTable.put(
						"rowid",
						new ArrayList<String>(Arrays.asList("int",
								String.valueOf(rakiBasetabletree
										.getNextMaxRowID() + 1))));
				newTable.put(
						"table_name",
						new ArrayList<String>(Arrays.asList("text",
								createTableTokens.get(2))));
				newColumns.put(
						"rowid",
						new ArrayList<String>(Arrays.asList("int",
								String.valueOf(cr++))));
				newColumns.put(
						"table_name",
						new ArrayList<String>(Arrays.asList("text",
								createTableTokens.get(2))));
				newColumns.put(
						"column_name",
						new ArrayList<String>(Arrays.asList("text",
								createTableTokens.get(4))));
				newColumns.put("data_type",
						new ArrayList<String>(Arrays.asList("text", "int")));
				newColumns.put(
						"ordinal_position",
						new ArrayList<String>(Arrays.asList("tinyint",
								String.valueOf(op++))));
				newColumns.put("is_nullable",
						new ArrayList<String>(Arrays.asList("text", "no")));
				schematableColList
				.add(new LinkedHashMap<String, ArrayList<String>>(
						newColumns));
				newColumns.clear();
			}
			if (createTableTokens.get(8).equals(","))
				for (int i = 9; i < createTableTokens.size() && flag == 1; i++) {
					newColumns.clear();
					if (createTableTokens.get(i).matches(
							"^[a-zA-Z][a-zA-Z0-9_]*$")
							&& dataType.contains(createTableTokens.get(i + 1))) {
						newColumns.put(
								"rowid",
								new ArrayList<String>(Arrays.asList("int",
										String.valueOf(cr++))));
						newColumns.put(
								"table_name",
								new ArrayList<String>(Arrays.asList("text",
										createTableTokens.get(2))));
						newColumns.put(
								"column_name",
								new ArrayList<String>(Arrays.asList("text",
										createTableTokens.get(i))));
						newColumns.put(
								"data_type",
								new ArrayList<String>(Arrays.asList("text",
										createTableTokens.get(i + 1))));
						newColumns.put(
								"ordinal_position",
								new ArrayList<String>(Arrays.asList("tinyint",
										String.valueOf(op++))));
						i++;
						if (createTableTokens.get(i + 1).equals("not")
								&& createTableTokens.get(i + 2).equals("null")
								&& createTableTokens.get(i + 3).equals(",")) {
							i = i + 3;
							newColumns.put(
									"is_nullable",
									new ArrayList<String>(Arrays.asList("text",
											"no")));
							schematableColList
							.add(new LinkedHashMap<String, ArrayList<String>>(
									newColumns));
						} else if (createTableTokens.get(i + 1).equals("not")
								&& createTableTokens.get(i + 2).equals("null")
								&& createTableTokens.get(i + 3).equals(")")) {
							i = i + 3;
							newColumns.put(
									"is_nullable",
									new ArrayList<String>(Arrays.asList("text",
											"no")));
							schematableColList
							.add(new LinkedHashMap<String, ArrayList<String>>(
									newColumns));
						} else if (createTableTokens.get(i + 1).equals(",")) {
							i++;
							newColumns.put(
									"is_nullable",
									new ArrayList<String>(Arrays.asList("text",
											"yes")));
							schematableColList
							.add(new LinkedHashMap<String, ArrayList<String>>(
									newColumns));
						} else if (createTableTokens.get(i + 1).equals(")")) {
							newColumns.put(
									"is_nullable",
									new ArrayList<String>(Arrays.asList("text",
											"yes")));
							schematableColList
							.add(new LinkedHashMap<String, ArrayList<String>>(
									newColumns));
							i++;
						} else
							flag = 0;
					} else
						flag = 0;
				}
			if (flag == 0)
				System.out.println("Syntax ERROR");
			else {
				try {

					BTree columnBTree = new BTree(rakiBaseColumnFile,
							"rakibase_columns", true, false);
					BTree tableBTree = new BTree(rakiBasetableFile,
							"rakibase_tables", false, true);
					for (LinkedHashMap<String, ArrayList<String>> rows : schematableColList) {
						columnBTree.insertNewRecord(rows);
					}
					tableBTree.insertNewRecord(newTable);
					RandomAccessFile tableFile = new RandomAccessFile(
							"Data/User_Data/" + createTableTokens.get(2)
							+ ".tbl", "rw");
					tableFile.setLength(0);

					new BTree(tableFile, createTableTokens.get(2))
					.createEmptyTable();
					if (tableBTree != null)
						tableFile.close();
					System.out.println("Table Created");
				} catch (Exception e) {
					System.out.println("Unexpected Error");
				}

			}
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}
	}

	public static void parseInsertString(String insertTableString) {
		try {
			BTree columnBTree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);
			insertTableString = insertTableString.replace("(", " ( ");
			insertTableString = insertTableString.replace(")", " ) ");
			insertTableString = insertTableString.replace(",", " , ");
			Map<String, ArrayList<String>> tableInfo = new LinkedHashMap<String, ArrayList<String>>();
			Map<String, ArrayList<String>> tableVal = new LinkedHashMap<String, ArrayList<String>>();
			ArrayList<String> colName = new ArrayList<String>();
			ArrayList<String> insertTableTokens = new ArrayList<String>(
					Arrays.asList(insertTableString.split("\\s+")));
			int flag = 1;
			File folder = new File("Data/User_Data");
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++)
				if (listOfFiles[i].getName().equals(
						insertTableTokens.get(2) + ".tbl"))
					flag = 0;
			if (flag == 1 || !insertTableTokens.get(1).equals("into"))
				System.out.println("Table does not exist/Syntax Error");
			else
				tableInfo = columnBTree.getSchema(insertTableTokens.get(2));
			if (insertTableTokens.get(3).equals("(")) {
				for (String x : tableInfo.keySet())
					if (tableInfo.get(x).contains("no"))
						colName.add(x);
				int k = insertTableTokens.indexOf(")");
				if (insertTableTokens.get(k + 1).equals("values")
						&& insertTableTokens.get(k + 2).equals("(")
						&& insertTableTokens.get(insertTableTokens.size() - 1)
						.equals(")")) {
					for (int i = 4; !insertTableTokens.get(i).equals(")")
							&& flag == 0; i++) {
						if (colName.contains(insertTableTokens.get(i)))
							colName.remove(insertTableTokens.get(i));
						if (tableInfo.keySet().contains(
								insertTableTokens.get(i))
								&& (checkValue(
										tableInfo.get(insertTableTokens.get(i))
										.get(0),
										insertTableTokens.get(k + i - 1)))
										&& insertTableTokens.get(i + 1).equals(",")) {
							tableVal.put(
									insertTableTokens.get(i),
									new ArrayList<String>(Arrays.asList(
											tableInfo.get(
													insertTableTokens.get(i))
													.get(0), insertTableTokens
													.get(k + i - 1))));
							i++;
						} else if (tableInfo.keySet().contains(
								insertTableTokens.get(i))
								&& (checkValue(
										tableInfo.get(insertTableTokens.get(i))
										.get(0),
										insertTableTokens.get(k + i - 1)))
										&& insertTableTokens.get(i + 1).equals(")"))
							tableVal.put(
									insertTableTokens.get(i),
									new ArrayList<String>(Arrays.asList(
											tableInfo.get(
													insertTableTokens.get(i))
													.get(0), insertTableTokens
													.get(k + i - 1))));
						else
							flag = 1;
					}
				} else
					flag = 1;
				if (colName.size() != 0)
					flag = 1;
			} else if (insertTableTokens.get(3).equals("values")
					&& insertTableTokens.get(4).equals("(")
					&& insertTableTokens.get(insertTableTokens.size() - 1)
					.equals(")")) {
				int k = 5;
				for (String x : tableInfo.keySet()) {
					if (tableInfo.get(x).get(1).equals("no")) {
						if (checkValue(tableInfo.get(x).get(0),
								insertTableTokens.get(k))
								&& insertTableTokens.get(k + 1).equals(",")) {
							tableVal.put(
									x,
									new ArrayList<String>(Arrays.asList(
											tableInfo.get(x).get(0),
											insertTableTokens.get(k))));
							k += 2;
						} else if (checkValue(tableInfo.get(x).get(0),
								insertTableTokens.get(k))
								&& insertTableTokens.get(k + 1).equals(")")) {
							tableVal.put(
									x,
									new ArrayList<String>(Arrays.asList(
											tableInfo.get(x).get(0),
											insertTableTokens.get(k))));
							k++;
						} else
							flag = 1;
					} else {
						if (!insertTableTokens.get(k).equals(",")
								&& !insertTableTokens.get(k).equals(")")) {
							if (checkValue(tableInfo.get(x).get(0),
									insertTableTokens.get(k))
									&& insertTableTokens.get(k + 1).equals(",")) {
								tableVal.put(
										x,
										new ArrayList<String>(Arrays.asList(
												tableInfo.get(x).get(0),
												insertTableTokens.get(k))));
								k += 2;
							} else if (checkValue(tableInfo.get(x).get(0),
									insertTableTokens.get(k))
									&& insertTableTokens.get(k + 1).equals(")")) {
								tableVal.put(
										x,
										new ArrayList<String>(Arrays.asList(
												tableInfo.get(x).get(0),
												insertTableTokens.get(k))));
								k++;
							}
						} else {
							tableVal.put(
									x,
									new ArrayList<String>(Arrays.asList(
											tableInfo.get(x).get(0), "NULL")));
						}

					}
				}
			} else
				flag = 1;
			if (flag == 0) {
				int primaryKeyVal = -1;
				for (String key : tableVal.keySet()) {
					String primaryKey = tableVal.get(key).get(1);
					primaryKeyVal = Integer.parseInt(primaryKey);
					break;
				}
				for (String key : tableVal.keySet()) {
					tableInfo.put(key, tableVal.get(key));
				}
				String fileName = tableLocation + insertTableTokens.get(2)
						+ tableFormat;
				try {
					RandomAccessFile newTable = new RandomAccessFile(fileName,
							"rw");
					BTree tableTree = new BTree(newTable,
							insertTableTokens.get(2));

					if (tableTree.isEmptyTable()) {
						tableTree.createNewTableLeaf(tableInfo);

					} else {
						if (tableTree.isPrimaryKeyExists(primaryKeyVal)) {
							System.out.println(" Primary key with value "
									+ primaryKeyVal + " already exists");
							return;
						} else {
							for (String x : tableInfo.keySet())
								if ((tableInfo.get(x).get(1).charAt(0) == '\'' && tableInfo
								.get(x)
								.get(1)
								.charAt(tableInfo.get(x).get(1)
										.length() - 1) == '\'')
										|| (tableInfo.get(x).get(1).charAt(0) == '"' && tableInfo
										.get(x)
										.get(1)
										.charAt(tableInfo.get(x).get(1)
												.length() - 1) == '"'))
									tableInfo
									.put(x,
											new ArrayList<String>(
													Arrays.asList(
															"text",
															tableInfo
															.get(x)
															.get(1)
															.substring(
																	1,
																	tableInfo
																	.get(x)
																	.get(1)
																	.length() - 1))));
							tableTree.insertNewRecord(tableInfo);
							if (newTable != null)
								newTable.close();

						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Unexpected Error");
				}

				System.out.println("1 row inserted");

			} else
				System.out.println("Error in syntax");
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}
	}

	public static void parseDeleteString(String deleteTableString) {
		try {
			BTree columnBTree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);
			deleteTableString = deleteTableString.replace("=", " = ");
			ArrayList<String> deleteTableTokens = new ArrayList<String>(
					Arrays.asList(deleteTableString.split("\\s+")));
			Map<String, ArrayList<String>> tableInfo = new LinkedHashMap<String, ArrayList<String>>();
			int flag = 1;
			if (deleteTableTokens.get(1).equals("from")) {
				if (deleteTableTokens.size() > 3
						&& deleteTableTokens.get(3).equals("where")
						&& deleteTableTokens.get(5).equals("=")) {
					tableInfo = columnBTree.getSchema(deleteTableTokens.get(2));
					if (tableInfo != null) {
						for (String x : tableInfo.keySet()) {
							if (!deleteTableTokens.get(4).equals(x))
								flag = 0;
							break;
						}
						String dataTypeOfDeleteKey = "int";
						if (tableInfo != null) {
							for (String x : tableInfo.keySet()) {
								if (deleteTableTokens.get(4).equals(x)) {
									dataTypeOfDeleteKey = tableInfo.get(x).get(
											0);
								}

								break;
							}
						}

						if (flag == 1) {
							LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
							ArrayList<String> array = new ArrayList<String>();
							array.add(dataTypeOfDeleteKey);
							if ((deleteTableTokens.get(6).charAt(0) == '\'' && deleteTableTokens
									.get(6)
									.charAt(deleteTableTokens.get(6).length() - 1) == '\'')
									|| (deleteTableTokens.get(6).charAt(0) == '"' && deleteTableTokens
									.get(6).charAt(
											deleteTableTokens.get(6)
											.length() - 1) == '"'))
								array.add(deleteTableTokens.get(6).substring(1,
										deleteTableTokens.get(6).length() - 1));
							else
								array.add(deleteTableTokens.get(6));
							token.put(deleteTableTokens.get(4),
									new ArrayList<String>(array));
							RandomAccessFile filename;
							try {
								filename = new RandomAccessFile(tableLocation
										+ deleteTableTokens.get(2)
										+ tableFormat, "rw");

								BTree rakiBaseColumnFiletree = new BTree(
										filename, deleteTableTokens.get(2));
								rakiBaseColumnFiletree.deleteRecord(token);
								System.out.println("1 row deleted");

								if (filename != null)
									filename.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("Table does not exists");
							}
						}
					} else
						System.out.println("Table does not exist!!!");
				} else {
					tableInfo = columnBTree.getSchema(deleteTableTokens.get(2));
					if (tableInfo != null)
						System.out.println("Schema doesn't exists");
					else
						System.out.println("Table does not exist!!!");
				}
			}
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}
	}

	public static boolean dropTable(String tableName) {
		boolean isDropped = false;
		int flag = 1;
		RandomAccessFile dropTableFile = null;
		File folder = new File("Data/User_Data");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].getName().equals(tableName + ".tbl")) {
				flag = 0;
			}
		if (flag == 1) {
			System.out.println("Table does not exist");
			return false;
		}
		try {
			rakiBaseColumnFile = new RandomAccessFile(rakibase_columns, "rw");
			rakiBasetableFile = new RandomAccessFile(rakibase_tables, "rw");
			dropTableFile = new RandomAccessFile(tableLocation + tableName
					+ tableFormat, "rw");
		} catch (FileNotFoundException e1) {
			System.out.println(" Table file not found");
			return false;
		}
		try {
			dropTableFile.setLength(0);
			if (dropTableFile != null)
				dropTableFile.close();
			File f = new File(tableLocation + tableName + tableFormat);
			f.delete();
			System.out.println("Table Dropped");
		} catch (IOException e) {
			return false;
		}
		BTree tableBTree = new BTree(rakiBasetableFile, "rakibase_tables",
				false, true);
		BTree columnBTree = new BTree(rakiBaseColumnFile, "rakibase_columns",
				true, false);
		ArrayList<String> arryL = new ArrayList<String>();
		arryL.add(new Integer(1).toString()); // search cond col ordinal
		// position
		arryL.add("text"); // search cond col data type
		arryL.add(tableName); // search cond col value
		List<LinkedHashMap<String, ArrayList<String>>> op = tableBTree
				.searchNonPrimaryCol(arryL);
		for (LinkedHashMap<String, ArrayList<String>> map : op) {
			Integer rowId = Integer.parseInt(map.get("rowid").get(0));
			LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
			ArrayList<String> array = new ArrayList<String>();
			array.add("int");
			array.add(rowId.toString());
			token.put("rowid", new ArrayList<String>(array));
			isDropped = tableBTree.deleteRecord(token);

		}
		arryL = new ArrayList<String>();
		arryL.add(new Integer(1).toString()); // search cond col ordinal
		// position
		arryL.add("text"); // search cond col data type
		arryL.add(tableName); // search cond col value
		List<LinkedHashMap<String, ArrayList<String>>> opp = columnBTree
				.searchNonPrimaryCol(arryL);
		for (LinkedHashMap<String, ArrayList<String>> map : opp) {
			Integer rowId = Integer.parseInt(map.get("rowid").get(0));
			LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
			ArrayList<String> array = new ArrayList<String>();
			array.add("int");
			array.add(rowId.toString());
			token.put("rowid", new ArrayList<String>(array));
			isDropped = columnBTree.deleteRecord(token);
		}
		return isDropped;

	}

	public static void showTables() {

		try {
			rakiBasetableFile = new RandomAccessFile(rakibase_tables, "rw");
		} catch (FileNotFoundException e1) {
			System.out.println(" Table file not found");
		}

		BTree tableBTree = new BTree(rakiBasetableFile, "rakibase_tables",
				false, true);

		printTable(tableBTree.printAll());

	}

	public static void parseUpdateString(String updateTableString) {
		try {
			updateTableString = updateTableString.replace("=", " = ");
			BTree columnBTree = new BTree(rakiBaseColumnFile,
					"rakibase_columns", true, false);
			ArrayList<String> updateTableTokens = new ArrayList<String>(
					Arrays.asList(updateTableString.split("\\s+")));
			Map<String, ArrayList<String>> tableInfo = new LinkedHashMap<String, ArrayList<String>>();
			int flag = 1;
			if (updateTableTokens.get(2).equals("set")
					&& updateTableTokens.get(4).equals("=")) {
				if (updateTableTokens.size() > 6
						&& updateTableTokens.get(6).equals("where")
						&& updateTableTokens.get(8).equals("=")) {
					tableInfo = columnBTree.getSchema(updateTableTokens.get(1));
					if (tableInfo != null) {
						if (tableInfo.keySet().contains(
								updateTableTokens.get(3))
								&& tableInfo.keySet().contains(
										updateTableTokens.get(7))) {
							if (checkValue(
									tableInfo.get(updateTableTokens.get(3))
									.get(0), updateTableTokens.get(5))
									&& checkValue(
											tableInfo.get(
													updateTableTokens.get(7))
													.get(0),
													updateTableTokens.get(9))) {

								ArrayList array = new ArrayList<String>();
								LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();

								BTree tableTree = null;
								RandomAccessFile newTable = null;
								try {
									newTable = new RandomAccessFile(
											tableLocation
											+ updateTableTokens.get(1)
											+ tableFormat, "rw");
									tableTree = new BTree(newTable,
											updateTableTokens.get(1));
								} catch (FileNotFoundException e1) {
									System.out
									.println(" Table not found during update");
									return;
								}
								String dataType = tableInfo.get(
										updateTableTokens.get(7)).get(0);
								array.add(dataType);
								if ((updateTableTokens.get(9).charAt(0) == '\'' && updateTableTokens
										.get(9).charAt(
												updateTableTokens.get(9)
												.length() - 1) == '\'')
												|| (updateTableTokens.get(9).charAt(0) == '"' && updateTableTokens
												.get(9).charAt(
														updateTableTokens
														.get(9)
														.length() - 1) == '"'))
									array.add(updateTableTokens.get(9)
											.substring(
													1,
													updateTableTokens.get(9)
													.length() - 1));
								else
									array.add(new String(updateTableTokens
											.get(9)));

								token.put(updateTableTokens.get(7),
										new ArrayList<String>(array));

								LinkedHashMap<String, ArrayList<String>> result = tableTree
										.searchWithPrimaryKey(token);
								if (result == null) {
									return;
								}
								LinkedHashMap<String, ArrayList<String>> table = columnBTree
										.getSchema(updateTableTokens.get(1));
								for (String column : result.keySet()) {
									if (column.equals(updateTableTokens.get(3)))// colname
									{
										ArrayList<String> value = table
												.get(column);
										value.remove(value.size() - 1);
										if ((updateTableTokens.get(5).charAt(0) == '\'' && updateTableTokens
												.get(5).charAt(
														updateTableTokens
														.get(5)
														.length() - 1) == '\'')
														|| (updateTableTokens.get(5)
																.charAt(0) == '"' && updateTableTokens
																.get(5)
																.charAt(updateTableTokens
																		.get(5)
																		.length() - 1) == '"'))
											value.add(updateTableTokens
													.get(5)
													.substring(
															1,
															updateTableTokens
															.get(5)
															.length() - 1)); // newValue
										else
											value.add(updateTableTokens.get(5));
										table.put(column, value);
									} else {
										ArrayList<String> value = table
												.get(column);
										ArrayList<String> res = result
												.get(column);
										value.remove(value.size() - 1);
										String val = res.get(0);
										value.add(val);
										table.put(column, value);
									}

								}
								token.clear();
								array.clear();
								dataType = tableInfo.get(
										updateTableTokens.get(7)).get(0);
								array.add(dataType);
								array.add(new String(updateTableTokens.get(9)));
								token.put(updateTableTokens.get(7),
										new ArrayList<String>(array));
								tableTree.deleteRecord(token);
								try {
									tableTree.insertNewRecord(table);
									System.out.println(" 1 row updated");
									if (newTable != null)
										newTable.close();
								} catch (Exception e) {

									System.out.println(" Update Failed");
								}

							} else
								flag = 0;
						} else
							flag = 0;
					} else
						flag = 0;
				} else {
					tableInfo = columnBTree.getSchema(updateTableTokens.get(1));
					if (tableInfo != null) {
						if (tableInfo.keySet().contains(
								updateTableTokens.get(3))) {
							if (checkValue(
									tableInfo.get(updateTableTokens.get(3))
									.get(0), updateTableTokens.get(5))) {

								

								int noOfRows = 0;
								BTree tableTree = null;
								RandomAccessFile newTable = null;
								try {
									newTable = new RandomAccessFile(
											tableLocation
											+ updateTableTokens.get(1)
											+ tableFormat, "rw");
									tableTree = new BTree(newTable,
											updateTableTokens.get(1));
								} catch (FileNotFoundException e1) {
									System.out
									.println(" Table not found during update");
									return;
								}

								List<LinkedHashMap<String, ArrayList<String>>> list_result = tableTree
										.printAll();
								for (LinkedHashMap<String, ArrayList<String>> result : list_result) {
									LinkedHashMap<String, ArrayList<String>> table = columnBTree
											.getSchema(updateTableTokens.get(1));
									LinkedHashMap<String, ArrayList<String>> token = new LinkedHashMap<String, ArrayList<String>>();
									ArrayList<String> array = new ArrayList<String>();

									for (String column : result.keySet()) {
										if (column.equals(updateTableTokens
												.get(3)))// colname
										{
											ArrayList<String> value = table
													.get(column);
											value.remove(value.size() - 1);
											if ((updateTableTokens.get(5)
													.charAt(0) == '\'' && updateTableTokens
													.get(5)
													.charAt(updateTableTokens
															.get(5).length() - 1) == '\'')
															|| (updateTableTokens
																	.get(5).charAt(0) == '"' && updateTableTokens
																	.get(5)
																	.charAt(updateTableTokens
																			.get(5)
																			.length() - 1) == '"'))
												value.add(updateTableTokens
														.get(5)
														.substring(
																1,
																updateTableTokens
																.get(5)
																.length() - 1)); // newValue
											else
												value.add(updateTableTokens
														.get(5));
											table.put(column, value);
										} else {
											ArrayList<String> value = table
													.get(column);
											ArrayList<String> res = result
													.get(column);
											value.remove(value.size() - 1);
											String val = res.get(0);
											value.add(val);
											table.put(column, value);
										}

									}
									token.clear();
									array.clear();
								
									array.add("int");
									array.add(new String(table.get(
											tableTree.getPrimaryKey()).get(1)));
									token.put(tableTree.getPrimaryKey(),
											new ArrayList<String>(array));
									tableTree.deleteRecord(token);
									try {
										tableTree.insertNewRecord(table);
										noOfRows++;
									} catch (Exception e) {

										System.out.println(" Update Failed");
									}

								}
								if (newTable != null)
									try {
										newTable.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										System.out.println("Unexpected Error");
									}

								System.out.println(noOfRows
										+ " row(s) updated.");

							} else
								flag = 0;
						} else
							flag = 0;
					} else
						flag = 0;
				}
			} else
				flag = 0;
			if (flag == 0)
				System.out.println("Syntax Error");
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}
	}

	public static void parseShowString(String showTableString) {
		try {
			ArrayList<String> showTableTokens = new ArrayList<String>(
					Arrays.asList(showTableString.split("\\s+")));
			if (showTableTokens.get(1).equals("tables"))
				showTables();
			else
				System.out.println("Syntax Error");
		} catch (Exception e) {
			System.out.println("Syntax Error");
		}
	}

	public static boolean checkValue(String type, String value) {
		switch (type.toLowerCase()) {
		case "text":
			if ((value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'')
					|| (value.charAt(0) == '"' && value
					.charAt(value.length() - 1) == '"'))
				return true;
			break;
		case "tinyint":
			if (Integer.parseInt(value) >= Byte.MIN_VALUE
			&& Integer.parseInt(value) <= Byte.MAX_VALUE)
				return true;
			break;
		case "smallint":
			if (Integer.parseInt(value) >= Short.MIN_VALUE
			&& Integer.parseInt(value) <= Short.MAX_VALUE)
				return true;
			break;
		case "int":
			if (Integer.parseInt(value) >= Integer.MIN_VALUE
			&& Integer.parseInt(value) <= Integer.MAX_VALUE)
				return true;
			break;
		case "bigint":
			if (Long.parseLong(value) >= Long.MIN_VALUE
			&& Long.parseLong(value) <= Long.MAX_VALUE)
				return true;
			break;
		case "real":
			if (Float.parseFloat(value) >= Float.MIN_VALUE
			&& Float.parseFloat(value) <= Float.MAX_VALUE)
				return true;
			break;
		case "double":
			if (Double.parseDouble(value) >= Double.MIN_VALUE
			&& Double.parseDouble(value) <= Double.MAX_VALUE)
				return true;
			break;
		case "datetime":
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			try {
				Date date = df.parse(value);
			} catch (ParseException e) {
				return false;
			}
			return true;
		case "date":
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = d.parse(value);
			} catch (ParseException e) {
				return false;
			}
			return true;
		default:
			return false;
		}
		return false;

	}
}