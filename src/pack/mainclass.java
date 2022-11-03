package pack;
import java.util.Scanner;

public class mainclass()
{
	/*
	 * Spielregeln und dokumentation: Zuerst 2 Sekunden warten und danach die Namen
	 * der 2 Spieler eingeben. Danach kommt die erste Phase, bei welcher man die
	 * Steine Platziert. Steine von Spieler A werden mit "A"s und Steine von Spieler
	 * B werden mit "B"s dargestellt. Man darf nur auf leere Felder platzieren.
	 * Danach kommt die zweite Phase und man bewegt die gesetzten Steine. Wenn man
	 * mehr als 3 Steine hat, kann man die Steine nur auf nahegelegene Felder
	 * bewegen. Wenn man 3 Steine hat, darf man zu jedem leeren Feld auf der Karte
	 * springen und wenn man weniger als 3 Srteine hat, hat man verloren. Wenn man 3
	 * Steine in einer Horizontalen oder Vertikalen Linie hat, Darf man einen
	 * gegnerischen Stein entfernen.
	 */
	private static String nameA;
	private static String nameB;
	private static byte[][] map; // falls die Koordinate 3 ist, ist es leer und in der Lage ein Stein zu
									// beinhalten, 2= Stein von SpielerB und 3= Stein von Spieler A.
	private static int changedX;
	private static int changedY;
	public static boolean[] wins = new boolean[12];

	public static void main(String[] args)
	{
		map = mapsetup();
		boolean winner = true;
		String playername;
		setname();
		showmap(map);
		// Folgende Schleife dient der ersten Phase, in welcher die Spieler ihre Steine
		// platzieren können.
		// Die Information, welcher Spieler im moment spielt wird per boolean geteilt
		// (true=SpielerA und false=SpielerB)
		for (int i = 0; i <= 8; i++)
		{
			firststage(true);
			showmap(map);
			threeinarow(true);

			firststage(false);
			showmap(map);
			threeinarow(false);
		}
		// Folgende Schleife dient der zweiten Phase des Spiels, in welcher die Steine
		// bewegt werden. true=SpielerA, fals=SpielerB
		while (count(true) != 1 || count(false) != 1)
		{
			if (count(true) == 0) moveB(true, false);
			else if (count(true) == 2) moveB(true, true);
			else if (count(true) == 1)
			{
				winner = false;
				break;
			}
			showmap(map);
			threeinarow(true);

			if (count(false) == 0) moveB(false, false);
			else if (count(false) == 2) moveB(false, true);
			else if (count(false) == 1)
			{
				winner = true;
				break;
			}
			showmap(map);
			threeinarow(false);
		}
		if (winner == true) playername = nameA;
		else playername = nameB;
		System.out.println(playername + ", Hat gewonnen");
	}

	public static void threeinarow(boolean player) // Diese Methode findet heraus, ob irgendwo 3 Steine in einer Reihe
													// sind
	{

		if ((map[0][0] != 3) && (map[0][0] == map[0][3]) && (map[0][3] == map[0][6]) && (changedX == 0))
		{
			pickup(player);
		}
		else if ((map[2][2] != 3) && (map[2][2] == map[2][3]) && (map[2][3] == map[2][4]) && (changedX == 2))
		{
			pickup(player);
		}
		else if ((map[1][1] != 3) && (map[1][1] == map[1][3]) && (map[1][3] == map[1][5]) && (changedX == 1))
		{
			pickup(player);
		}
		else if ((map[3][1] != 3) && (map[3][0] == map[3][1]) && (map[3][1] == map[3][2]) && (changedX == 3)
				&& (changedY < 3))
		{
			pickup(player);
		}
		else if ((map[4][2] != 3) && (map[4][2] == map[4][3]) && (map[4][3] == map[4][4]) && (changedX == 4))
		{
			pickup(player);
		}
		else if ((map[3][4] != 3) && (map[3][4] == map[3][5]) && (map[3][5] == map[3][6]) && (changedX == 3)
				&& (changedY > 3))
		{
			pickup(player);
		}
		else if ((map[5][1] != 3) && (map[5][1] == map[5][3]) && (map[5][3] == map[5][5]) && (changedX == 5))
		{
			pickup(player);
		}
		else if ((map[6][6] != 3) && (map[6][6] == map[6][3]) && (map[6][3] == map[6][0]) && (changedX == 0))
		{
			pickup(player);
		}
		// Y-Achse //
		else if ((map[0][0] != 3) && (map[0][0] == map[3][0]) && map[3][0] == map[6][0] && (changedY == 0))
		{
			pickup(player);
		}
		else if ((map[2][2] != 3) && (map[2][2] == map[3][2]) && map[3][2] == map[4][2] && (changedY == 2))
		{
			pickup(player);
		}
		else if ((map[1][1] != 3) && (map[1][1] == map[3][1]) && map[3][1] == map[5][1] && (changedY == 1))
		{
			pickup(player);
		}
		else if ((map[0][3] != 3) && (map[0][3] == map[1][3]) && map[1][3] == map[2][3] && (changedY == 3)
				&& (changedX < 3))
		{
			pickup(player);
		}
		else if ((map[2][4] != 3) && (map[2][4] == map[3][4]) && map[3][4] == map[4][4] && (changedY == 4))
		{
			pickup(player);
		}
		else if ((map[4][3] != 3) && (map[4][3] == map[5][3]) && map[5][3] == map[6][3] && (changedY == 3)
				&& (changedX > 3))
		{
			pickup(player);
		}
		else if ((map[1][5] != 3) && (map[1][5] == map[3][5]) && map[3][5] == map[5][5] && (changedY == 5))
		{
			pickup(player);
		}
		else if ((map[0][6] != 3) && (map[0][6] == map[3][6]) && map[3][6] == map[6][6] && (changedY == 6))
		{
			pickup(player);
		}
	}

	public static void pickup(boolean player) // Diese Methode ermöglicht das entfernen eines gegnerischen steines
	{
		Scanner sc = new Scanner(System.in);
		String Input;
		String playername;
		int temp;
		if (player == true)
		{
			playername = nameA;
			temp = 2;
		}
		else
		{
			playername = nameB;
			temp = 1;
		}
		System.out.println(playername + ", choose a Stone to be removed");
		Input = sc.nextLine();
		if (pickupvalid(Input, player) == true)
		{
			String[] InputCord = Input.split("/");
			map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] = 3;
		}
		showmap(map);
	}

	public static boolean pickupvalid(String Input, boolean player) // Diese Methode dient der überprüfung, ob ein Stein
																	// gültig ist zu entfernen
	{
		String[] InputCord = Input.split("/");
		short playerB;
		if (player == true) playerB = 2;
		else playerB = 1;
		if ((Input.length() == 3
				&& (Character.isDigit(Input.charAt(0)) && Input.charAt(1) == '/' && Character.isDigit(Input.charAt(2))))
				&& map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] == playerB)
		{
			return true;
		}
		else return false;
	}

	public static byte[][] mapsetup()// Diese methode erstellt die Karte (Die Felder, in welchen die steine platziert
										// werden können)
	{
		System.out.println("" + "     _____                                             \r\n"
				+ " ___|    _|__  __   _  ______  __   _  ____    ______  \r\n"
				+ "|    \\  /  | ||  | | ||   ___||  |_| ||    |  |   ___| \r\n"
				+ "|     \\/   | ||  |_| ||   ___||   _  ||    |_ |   ___| \r\n"
				+ "|__/\\__/|__|_||______||______||__| |_||______||______| \r\n"
				+ "    |_____|                                            \r\n"
				+ "                                                       \r\n");
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		System.out.println("Created by Adrian, David and Yannic");
		System.out.println("");
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		// Da wir mit Ascii zeichen arbeiten, muss ein Font verwendet werden, bei
		// welchem alle Zeichen gleich breit sind.
		System.out.println("Folgeder text dient des Debuggen von Grafikfehlern.\r\n"
				+ "Folgende 5 Zeilen sollten alle von gleicher länge sein.\r\n"
				+ "Sollte dies nicht sein, wird ein font benutzt, welches\r\n"
				+ "verschiedene Charaktergrössen benutzt und sollte daher\r\n" + "verändert werden.\r\n");
		System.out.println("AAAAAAAAAA");
		System.out.println("BBBBBBBBBB");
		System.out.println("()()()()()");
		System.out.println("──────────");
		System.out.println("││││││││││");
		System.out.println("");
		byte map[][] = new byte[7][7];
		// Da Mühle unregelmäsig aufgebaut ist, muss zuerst entschieden werden, welche
		// Koordinaten spielbare felder sind
		map[0][0] = 3;
		map[3][0] = 3;
		map[6][0] = 3;

		map[1][1] = 3;
		map[3][1] = 3;
		map[5][1] = 3;

		map[2][2] = 3;
		map[3][2] = 3;
		map[4][2] = 3;

		map[0][3] = 3;
		map[1][3] = 3;
		map[2][3] = 3;

		map[4][3] = 3;
		map[5][3] = 3;
		map[6][3] = 3;

		map[2][4] = 3;
		map[3][4] = 3;
		map[4][4] = 3;

		map[1][5] = 3;
		map[3][5] = 3;
		map[5][5] = 3;

		map[0][6] = 3;
		map[3][6] = 3;
		map[6][6] = 3;
		return map;
	}

	public static void showmap(byte map[][])// Diese Methode zeigt die Karte grafisch dar
	{
		for (short y = 12; y >= 0; y--)
		{
			for (short x = 0; x < 13; x++)
			{
				boolean found = false;
				if (x % 2 == 0 && y % 2 == 0)
				{
					if (map[x / 2][y / 2] == 1)
					{
						found = true;
						System.out.print("A ");
					}
					else if (map[x / 2][y / 2] == 2)
					{
						found = true;
						System.out.print("B ");
					}
					else if (map[x / 2][y / 2] == 3)
					{
						found = true;
						System.out.print("()");
					}
				}
				if (found == false)
				{
					if (y == 0 || y == 12)
					{
						System.out.print("──");
					}
					else if (x == 0 || x == 12)
					{
						System.out.print("│ ");
					}
					else if (x == 6 && (y >= 8 || y <= 4))
					{
						System.out.print("│ ");
					}
					else if (y == 6 && (x >= 8 || x <= 4))
					{
						System.out.print("──");
					}
					else if ((x == 2 || x == 10) && y > 2 && y < 10)
					{
						System.out.print("│ ");
					}
					else if ((y == 2 || y == 10) && x > 2 && x < 10)
					{
						System.out.print("──");
					}

					else if ((x == 4 || x == 8) && y > 4 && y < 8)
					{
						System.out.print("│ ");
					}
					else if ((y == 4 || y == 8) && x > 4 && x < 8)
					{
						System.out.print("──");
					}
					else
					{
						System.out.print("  ");
					}
				}
			}
			if (y % 2 == 0)
			{
				System.out.print(" " + (y - (y / 2)));
			}
			else
			{
				System.out.print("  ");
			}

			System.out.println("");
		}
		for (short x = 0; x < 13; x++)
		{
			if (x % 2 == 0)
			{
				System.out.print(" " + (x - (x / 2)));
			}
			else
			{
				System.out.print("  ");
			}
		}
		System.out.println("");
		for (short i = 0; i <= 6 * 2; i++)
		{
			System.out.print("--");
		}
		System.out.println("");
	}

	public static void setname()// Die Strings nameB und nameA werden eingegeben
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Player A enter your name");
		nameA = sc.nextLine();
		System.out.println("Player B enter your name");
		nameB = sc.nextLine();
	}

	public static void firststage(boolean player) // Diese Methode dient der ersten Phase des Spiels, in welcher man die
													// Steine platziert
	{
		Scanner sc = new Scanner(System.in);
		String playername;
		String Input;
		if (player == true) playername = nameA;
		else playername = nameB;
		while (true)
		{
			System.out.println(playername + ", place a Stone by typing in the coorinates \"x/y\"");
			Input = sc.nextLine();
			if (firststagevalid(Input) == true)
			{
				String[] InputCord = Input.split("/");
				if (player == true)
				{
					map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] = 1;
				}
				else
				{
					map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] = 2;
				}
				changedX = Integer.parseInt(InputCord[0]);
				changedY = Integer.parseInt(InputCord[1]);
				break;
			}
			else
			{
				System.out.println("Invalid input");
			}
		}
		System.out.println("Input " + Input + " is valid");
	}

	public static boolean firststagevalid(String Input)// Diese Mathode dient der entscheidung, ob eine Bewegung Gültig
														// ist und wird von der Methode firststage() benutzt
	{
		String[] InputCord = Input.split("/");
		if ((Input.length() == 3
				&& (Character.isDigit(Input.charAt(0)) && Input.charAt(1) == '/' && Character.isDigit(Input.charAt(2))))
				&& map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] == 3)
		{
			return true;
		}
		else return false;
	}

	public static boolean thirdagevalid(String Input, boolean player) // Diese Mathode dient der entscheidung, ob eine
																		// Bewegung Gültig ist und wird von der Methode
																		// moveB() benutzt
	{
		String[] InputCord = Input.split("/");
		int playernum;
		if (player == true) playernum = 1;
		else playernum = 2;
		if ((Input.length() == 3
				&& (Character.isDigit(Input.charAt(0)) && Input.charAt(1) == '/' && Character.isDigit(Input.charAt(2))))
				&& map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] == playernum)
		{
			return true;
		}
		else return false;
	}

	public static byte count(boolean player) // Diese Methode gibt eine 1, falls dieser Spieler verloren hat, eine 2,
												// falls der Spieler springen darf und eine 0, falls er das icht darf
	{
		byte scanned = 0;
		byte found = 0;
		if (player == true) scanned = 1;
		else scanned = 2;
		for (short y = 0; y < 7; y++)
		{
			for (short x = 0; x < 7; x++)
			{
				if (map[x][y] == scanned) found++;
				else if (map[x][y] == scanned) found++;
			}
		}
		byte ret = 0;
		if (found < 3) ret = 1;
		else if (found == 3) ret = 2;
		return ret;
	}

	public static void moveB(boolean player, boolean jump) // Diese Methode dient der Zweiten phase, in welcher man die
															// Steine bewegen kann
	{
		Scanner sc = new Scanner(System.in);
		String playername;
		String Input;
		String[] InputCord;
		String[] InputCordB;
		if (player == true) playername = nameA;
		else playername = nameB;
		while (true)
		{
			System.out.println(playername + ", Choose a stone X/Y");
			Input = sc.nextLine();
			if (thirdagevalid(Input, player) == true)
			{
				InputCord = Input.split("/");
				if (map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] == 1 && player == true)
				{
					System.out.println("Valid input");
					break;
				}
				else if (map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] == 2 && player == false)
				{
					System.out.println("Valid input");
					break;
				}
			}
		}
		while (true)
		{
			System.out.println(playername + ", Choose a Field X/Y");
			Input = sc.nextLine();
			if (firststagevalid(Input) == true)
			{
				InputCordB = Input.split("/");
				if (jump == true)
				{
					System.out.println("Valid input");
					break;
				}
				else if ((map[Integer.parseInt(InputCordB[0])][Integer.parseInt(InputCordB[1])] == 3)
						&& nextto(Integer.parseInt(InputCord[0]), Integer.parseInt(InputCord[1]),
								Integer.parseInt(InputCordB[0]), Integer.parseInt(InputCordB[1])))
				{
					System.out.println("Valid input");
					break;
				}
			}
		}
		map[Integer.parseInt(InputCord[0])][Integer.parseInt(InputCord[1])] = 3;
		if (player == true) map[Integer.parseInt(InputCordB[0])][Integer.parseInt(InputCordB[1])] = 1;
		else if (player == false) map[Integer.parseInt(InputCordB[0])][Integer.parseInt(InputCordB[1])] = 2;
		else System.out.println("Error accured");
		changedX = Integer.parseInt(InputCordB[0]);
		changedY = Integer.parseInt(InputCordB[1]);
	}

	public static boolean nextto(int xa, int ya, int xb, int yb) // Diese Methode überpr¨üft, ob map[xa][ya] neben
																	// [xb][yb], um zu entscheiden, ob solche bewegung
																	// legal ist
	{
		System.out.println(xa + "/" + ya + "--" + xb + "/" + yb);
		if (xa > xb && ya == yb)
		{
			for (int i = 1; i <= 4; i++)
			{
				if (xa - i >= 0) if (map[xa - i][ya] == 1 || map[xa - i][ya] == 2) return false;
				else if (map[xa - i][ya] == 3) return true;
			}
		}
		else if (xa < xb && ya == yb)
		{
			for (int i = 1; i <= 4; i++)
			{
				if (xa + i >= 0) if (map[xa + i][ya] == 1 || map[xa + i][ya] == 2) return false;
				else if (map[xa + i][ya] == 3) return true;
			}
		}
		else if (xa == xb && ya > yb)
		{
			for (int i = 1; i <= 4; i++)
			{
				if (ya - i >= 0) if (map[xa][ya - i] == 1 || map[xa][ya - i] == 2) return false;
				else if (map[xa][ya - i] == 3) return true;
			}
		}
		else if (xa == xb && ya < yb)
		{
			for (int i = 1; i <= 4; i++)
			{
				if (ya + i >= 0) if (map[xa][ya + i] == 1 || map[xa][ya + i] == 2) return false;
				else if (map[xa][ya + i] == 3) return true;
			}
		}
		return false;
	}
}