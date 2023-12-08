
import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import views.DibujarFig;


public class Programa {

	public static void main(String[] args) {

		try {

			for (var info : UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {

					UIManager.setLookAndFeel(info.getClassName());
					break;

				}
			}
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {

		}

		EventQueue.invokeLater(new DibujarFig());
	}

}
