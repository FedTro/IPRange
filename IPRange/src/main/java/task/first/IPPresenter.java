package task.first;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author Fedor
 * @version 1.0
 * @since 2016-12-05
 */
public class IPPresenter {

	private static Logger log = Logger.getLogger(IPPresenter.class);

	private String ipPattern = "^(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])(\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[0-9]{2}|[0-9])){3}$";

	private Scanner in;
	private PrintStream out;

	public IPPresenter() {
		in = new Scanner(System.in);
		out = System.out;
	}

	/**
	 * Start point of the program.
	 */
	public int start() {

		log.info("Program is started");

		try {
			while (true) {

				out.println("Please enter two IP addresses or press 'Q' to exit\nFirst address: ");
				String firstAddr = in.nextLine();
				log.debug("First address: " + firstAddr);

				if ("Q".equals(firstAddr)) {
					out.println("Program is finished");
					log.debug("'Q' key was pressed");
					break;
				}

				if (!checkAddress(firstAddr)) {
					out.println("ERROR: address must belong to range: 0.0.0.0 - 255.255.255.255");
					continue;
				}

				out.println("Second address: ");
				String secondAddr = in.nextLine();
				log.debug("Second address: " + firstAddr);

				if (!checkAddress(secondAddr)) {
					out.println("ERROR: please enter a correct address in range: 0.0.0.0 - 255.255.255.255");
					continue;
				}

				showRange(firstAddr, secondAddr);

			}

			in.close();

		} catch (Exception exc) {
			log.error("Error was occured during program running", exc);
		}

		log.info("Program is finished");

		return 0;
	}

	/**
	 * 
	 * @param addr
	 *            IP address is checked according to the regular expression
	 *            template.
	 * @return true - IP address is correct, false - IP address is not correct.
	 */
	public boolean checkAddress(String addr) {
		Pattern p = Pattern.compile(ipPattern);
		Matcher m = p.matcher(addr);
		return m.matches();

	}

	/**
	 * 
	 * @param addr1
	 *            first entered IP address, a low bound of a range.
	 * @param addr2
	 *            second entered IP address, an upper bound of a range.
	 * @return
	 */
	public int showRange(String addr1, String addr2) {

		int[] addrArr1 = new int[4], addrArr2 = new int[4];

		String[] arr1 = addr1.split("\\.");
		String[] arr2 = addr2.split("\\.");

		for (int i = 0; i < 4; i++) {
			addrArr1[i] = Integer.parseInt(arr1[i]);
			addrArr2[i] = Integer.parseInt(arr2[i]);
		}

		int indx = 0;
		while (indx < 3 && addrArr1[indx] == addrArr2[indx])
			indx++;

		if (addrArr1[indx] >= addrArr2[indx]) {
			out.println("ERROR: first address must be greater than second one");
			return 0;
		}

		addrArr1[3]++;
		addrArr2[3]--;

		out.println("Addresses in the entered range:");
		return outputIP(indx, addrArr2[indx], addrArr1, addrArr2, true);

	}

	private int outputIP(int indx, int max, int[] arr1, int[] arr2,
			boolean prevLimit) {

		int count = 0;
		while (arr1[indx] <= max) {
			if (indx == 3) {
				out.printf("%d.%d.%d.%d\n", arr1[0], arr1[1], arr1[2], arr1[3]);
				++count;
			} else {
				if (arr1[indx] == arr2[indx] && prevLimit)
					count += outputIP(indx + 1, arr2[indx + 1], arr1, arr2,
							true);
				else
					count += outputIP(indx + 1, 255, arr1, arr2, false);
			}
			arr1[indx]++;
		}
		if (max == 255)
			arr1[indx] = 0;
		return count;

	}
}
