package tests;

import org.junit.BeforeClass;

import coviddiff.App;
import org.junit.jupiter.api.Test;
/**
 * Testes para a app.
 * @author Macedo
 *
 */
class AppTest {

	static App app1;
	static App app2;
	@BeforeClass
	public static void setUpBeforeClass() {
		app1 = new App();
		app2 = new App();
	}
	@Test
	void testApp() {
		setUpBeforeClass();
		app1.equals(app2);
	}

	@Test
	void testPath() {
		App.getPath().equals("../covid-evolution-diff_files");
	}
}
