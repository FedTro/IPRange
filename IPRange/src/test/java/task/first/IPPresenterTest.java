package task.first;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IPPresenterTest {

	static IPPresenter presenter;

	@BeforeClass
	public static void init() {
		presenter = new IPPresenter();
	}

	@Test
	public void ipCheckTestCorrect() {
		boolean isCorrect = presenter.checkAddress("192.168.0.1");
		assertEquals(true, isCorrect);
	}

	@Test
	public void ipCheckTestLimit() {
		boolean isCorrect = presenter.checkAddress("192.168.257.1");
		assertEquals(false, isCorrect);
	}

	@Test
	public void ipCheckTestNumbers() {
		boolean isCorrect = presenter.checkAddress("1111.168.257.1");
		assertEquals(false, isCorrect);
	}

	@Test
	public void ipCheckTestFormat1() {
		boolean isCorrect = presenter.checkAddress("192.168.0.1.");
		assertEquals(false, isCorrect);
	}

	@Test
	public void ipCheckTestFormat2() {
		boolean isCorrect = presenter.checkAddress("192.168.0");
		assertEquals(false, isCorrect);
	}

	@Test
	public void printedIPCheckCorrectRange1() {
		int shownAddresses = presenter.showRange("192.168.0.1", "192.168.0.5");
		assertEquals(3, shownAddresses);
	}
	
	@Test
	public void printedIPCheckCorrectRange2() {
		int shownAddresses = presenter.showRange("192.168.10.0", "192.168.11.5");
		assertEquals(260, shownAddresses);
	}
	
	@Test
	public void printedIPCheckGreater() {
		int shownAddresses = presenter.showRange("192.169.10.0", "192.168.10.0");
		assertEquals(0, shownAddresses);
	}


}
