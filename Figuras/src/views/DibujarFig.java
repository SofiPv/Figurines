package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import models.*;


public class DibujarFig extends JFrame implements Runnable {

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	public DibujarFig() {
		initComponents();
	}

	private void initComponents() {
		leftPanel = new JPanel();
                leftPanel.setBackground(new java.awt.Color(168,116,64));
		centerPanel = new JPanel();
                centerPanel.setBackground(new java.awt.Color(168,116,64));
		rightPanel = new JPanel();
                rightPanel.setBackground(new java.awt.Color(168,116,64));
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnTriangulo = new JToggleButton();
		btnCuadrilatero = new JToggleButton();
		btnCirculo = new JToggleButton();
                btnAyuda = new JButton();

		canvasPanel = new PanelCav();
		scrollLogger = new JScrollPane();
		logger = new JTextArea();

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new ListaFiguras());

		setTitle("Puntos y Figuras");
		setResizable(false);
		setLayout(new BorderLayout());

		// Si la ventana pierde el foco del teclado, recuperarlo
		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							//System.out.println(e.toString());
							requestFocusInWindow();
						});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				ModeloFigura selectedModel = listFiguras.getSelectedValue();

				// Quitar la seleccion de la figura
				if (e.getExtendedKeyCode() == VK_ESCAPE && selectedModel != null) {
					logger.append(selectedModel.getNombre() + " deseleccionado.");
					listFiguras.clearSelection();
					return;
				}

				if (selectedModel == null || !selectedModel.canDraw()) {
					return;
				}

				switch (e.getExtendedKeyCode()) {
					case VK_W -> {
						// Up
						selectedModel.getPuntos().up(5d);
						logger.append("[W ↑] " + selectedModel.getNombre() + "\n");
					}
					case VK_S -> {// Down
						selectedModel.getPuntos().down(5d);
						logger.append("[S ↓] " + selectedModel.getNombre() + "\n");
					}
					case VK_A -> {// Left
						selectedModel.getPuntos().left(5d);
						logger.append("[A ←] " + selectedModel.getNombre() + "\n");
					}
					case VK_D -> {// Right
						selectedModel.getPuntos().right(5d);
						logger.append("[D →] " + selectedModel.getNombre() + "\n");
					}
					case VK_Q -> {// Rotate left
						selectedModel.getPuntos().rotateLeft(90);
						logger.append("[Q ↶] " + selectedModel.getNombre() + "\n");
					}
					case VK_E -> {// Rotate right
						selectedModel.getPuntos().rotateRight(90);
						logger.append("[E ↷] " + selectedModel.getNombre() + "\n");
					}
					case VK_SHIFT -> { // Zoom in
						selectedModel.getPuntos().zoomIn(2d);
						logger.append("[Shift ←█→] " + selectedModel.getNombre() + "\n");
					}
					case VK_CONTROL -> {// Zoom out
						selectedModel.getPuntos().zoomOut(2d);
						logger.append("[Ctrl →|←] " + selectedModel.getNombre() + "\n");
					}
				}

				repaint();
			}

		});

		/* leftPanel componentes y desiño */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		leftPanelTitle.setText("Figuras");
                leftPanelTitle.setForeground(new java.awt.Color(105, 19, 6));
                leftPanelTitle.setForeground(new java.awt.Color(105, 19, 6));
		leftPanelTitle.setFont(new Font("Georgia", Font.BOLD, 24));

		btnTriangulo.setText("Triángulo");
                btnTriangulo.setBackground(new java.awt.Color(255,222,173));
                btnTriangulo.setForeground(new java.awt.Color(210,105,30));
		btnTriangulo.setFont(new Font("Georgia", Font.BOLD, 16));
		btnTriangulo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Crea un triángulo.\n");

			btnTriangulo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnCirculo.setEnabled(true);

			currentFigura = new ModeloTriangulo();
		});

		btnCuadrilatero.setText("Cuadrilatero");
                btnCuadrilatero.setBackground(new java.awt.Color(255,222,173));
                btnCuadrilatero.setForeground(new java.awt.Color(210,105,30));
		btnCuadrilatero.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCuadrilatero.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Crea un cuadrilatero.\n");

			btnCuadrilatero.setEnabled(false);
			btnTriangulo.setEnabled(true);
			btnCirculo.setEnabled(true);

			currentFigura = new ModeloCuadrilatero();
		});

		btnCirculo.setText("Circulo");
                btnCirculo.setBackground(new java.awt.Color(255,222,173));
                btnCirculo.setForeground(new java.awt.Color(210,105,30));
		btnCirculo.setFont(new Font("Georgia", Font.BOLD, 16));
		btnCirculo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Crea un circulo.\n");

			btnCirculo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnTriangulo.setEnabled(true);

			currentFigura = new ModeloCirculo();
		});
                
                btnAyuda.setText("¡Ayuda!");
                btnAyuda.setBackground(new java.awt.Color(255,222,173));
                btnAyuda.setForeground(new java.awt.Color(210,105,30));
		btnAyuda.setFont(new Font("Georgia", Font.BOLD, 16));
		btnAyuda.addActionListener((ActionEvent e) -> {

			logger.append("Se va a abierto en menu de ayuda.\n");

			String message = "Uso: \n";
			message += " Al dar clic a algun boton de figura, esta se creara,\n";
			message += " encima de la esfera que se muestra.\n";
			message += " Nota: Esa esfera representa donde apunta la vision de la camara.\n";
			
			message += "Controles:\n";
			message += " - Shift + (W,A,S,D,Q,E): Cambian la posición de la camara.\n";
			message += " - Ctrl + (W,A,S,D,Q,E): Cambian el punto que ve la camara.\n";
			message += " - Ctrl + (↑, ↓): Cambian la distancia de visión de la camara.\n";
			message += " - W,A,S,D,Q,E: Cambian la posición de la figura seleccionada.\n";
			message += " - C: Cambia el color de la figura seleccionada.\n";
			message += " - L: Habilita/deshabilita la luz en la figura seleccionada.\n";

			JOptionPane.showMessageDialog(this, message, "¿Cómo funciona?", JOptionPane.INFORMATION_MESSAGE);

		});

		buttonGroup.add(btnTriangulo);
		buttonGroup.add(btnCuadrilatero);
		buttonGroup.add(btnCirculo);
                buttonGroup.add(btnAyuda);

		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnTriangulo);
		leftPanel.add(btnCuadrilatero);
		leftPanel.add(btnCirculo);
                leftPanel.add(btnAyuda);

		/* centerPanel components & design */
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);

		canvasPanel.setBackground(centerPanel.getBackground());
		canvasPanel.setBorder(new LineBorder(Color.PINK, 2));
		canvasPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (currentFigura == null) {
					return;
				}

				for (int i = 0; i < currentFigura.getPuntos().size(); i++) {

					if (currentFigura.getPuntos().getValueAt(i) == null) {

						currentFigura.getPuntos().setValueAt(i, e.getPoint());
						logger.append("P" + (i + 1) + " (" + e.getX() + ", " + e.getY() + ")\n");

						listModel.addElement(currentFigura);
						canvasPanel.setModel(listModel);
						repaint();
						break;
					}
				}

				if (currentFigura.canDraw()) {
					try {
						logger.append(currentFigura.getNombre() + " creado.\n");
						currentFigura = (ModeloFigura) currentFigura.getClass().getConstructors()[0].newInstance(new Object[0]);
					} catch (Exception ex) {

					}
				}

			}

		});

		logger.setRows(10);
		logger.setLineWrap(true);
		logger.setEditable(false);

		scrollLogger.setViewportView(logger);

		centerPanel.add(canvasPanel, BorderLayout.CENTER);
		centerPanel.add(scrollLogger, BorderLayout.PAGE_END);

		/* rightPanel components & design */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		rightPanelTitle.setText("Historial del programa");
                rightPanelTitle.setForeground(new java.awt.Color(105, 19, 6));
		rightPanelTitle.setFont(new Font("Georgia", Font.BOLD, 24));

		listModel = (ListaFiguras) listFiguras.getModel();
		listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listFiguras.addListSelectionListener((ListSelectionEvent e) -> {

			listModel.unselectAll();

			var selectedModel = listFiguras.getSelectedValue();

			if (selectedModel == null) {
				repaint();
				return;
			}

			selectedModel.setSelected(true);
			logger.append(selectedModel.getNombre() + " seleccionado.\n");
			repaint();
		});

		//canvasPanel.setModel(listModel);
		scrollFiguras.setViewportView(listFiguras);

		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}

	/* Declarando Variables */
	private ModeloFigura currentFigura;

	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	/* leftPanel componentes */
	private ButtonGroup buttonGroup;
	private JToggleButton btnTriangulo;
	private JToggleButton btnCuadrilatero;
	private JToggleButton btnCirculo;
        private JButton btnAyuda;

	/* centerPanel componentes */
	private PanelCav canvasPanel;
	private JScrollPane scrollLogger;
	private JTextArea logger;

	/* rightPanel componentes */
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<ModeloFigura> listFiguras;
	private ListaFiguras listModel;

}
