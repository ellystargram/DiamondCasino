import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
	private int screenMaxWidth = 500;
	private int screenMaxHeight = 500;
	private long chips = 10000;
	private Color ribonBackgroundColor = new Color(32, 32, 32);
	private Color ribonFontColor = new Color(255, 255, 255);
	private Color defaultBackgroundColor = new Color(64, 64, 64);
	private Color defaultFontColor = new Color(255, 255, 255);
	private Color buttonBackgroundColor = new Color(98, 98, 98);
	private Color buttonFontColor = new Color(255, 255, 255);
	private boolean darkmode = true;

	private JPanel ribonPanel = new JPanel(null);
	private JLabel leftChipsLabel = new JLabel("Chips: " + chips);
	private JButton darkmodeButton = new JButton("Darkmode");
	private JButton exitButton = new JButton("X");

	public static void main(String[] args) {
		new Main();
	}

	Main() {
		makeWindow();
		makeRibonPanel();
		mainMenu();
	}

	private void restart() {
		makeRibonPanel();
		mainMenu();
	}

	private void makeWindow() {
		this.remove(this.getContentPane());
		screenMaxWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenMaxHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setSize(screenMaxWidth, screenMaxHeight);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("CASINO");
		this.setUndecorated(true);
	}

	private void makeRibonPanel() {
		ribonPanel = new JPanel(null);
		leftChipsLabel = new JLabel("Chips: " + chips);
		darkmodeButton = new JButton("Darkmode");

		ribonPanel.setPreferredSize(new Dimension(screenMaxWidth, 50));
		ribonPanel.setBackground(ribonBackgroundColor);
		ribonPanel.setForeground(ribonFontColor);
		ribonPanel.setOpaque(true);
		ribonPanel.add(leftChipsLabel);
		leftChipsLabel.setBounds(10, 10, 100, 30);
		leftChipsLabel.setForeground(ribonFontColor);
		ribonPanel.add(darkmodeButton);
		darkmodeButton.setBounds(screenMaxWidth - 200, 0, 100, 50);
		darkmodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				darkmode = !darkmode;
				if (darkmode) {
					ribonBackgroundColor = new Color(32, 32, 32);
					ribonFontColor = new Color(255, 255, 255);
					defaultBackgroundColor = new Color(64, 64, 64);
					defaultFontColor = new Color(255, 255, 255);
					buttonBackgroundColor = new Color(98, 98, 98);
					buttonFontColor = new Color(255, 255, 255);
				} else {
					ribonBackgroundColor = new Color(255, 255, 255);
					ribonFontColor = new Color(32, 32, 32);
					defaultBackgroundColor = new Color(180, 180, 180);
					defaultFontColor = new Color(0, 0, 0);
					buttonBackgroundColor = new Color(232, 232, 232);
					buttonFontColor = new Color(0, 0, 0);
				}
				restart();
			}
		});
		darkmodeButton.setBackground(buttonBackgroundColor);
		darkmodeButton.setForeground(buttonFontColor);

		ribonPanel.add(exitButton);
		exitButton.setBounds(screenMaxWidth - 100, 0, 100, 50);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setBackground(new Color(233,10,10));
		exitButton.setForeground(new Color(255,255,255));
	}

	private void mainMenu() {
		JPanel gameSelectPanel = new JPanel(null);
		JButton blackjackButton = new JButton("Blackjack");
		this.remove(this.getContentPane());
		this.add(ribonPanel, "North");
		this.add(gameSelectPanel);

		gameSelectPanel.add(blackjackButton);
		gameSelectPanel.setBackground(defaultBackgroundColor);
		gameSelectPanel.setForeground(defaultFontColor);

		int buttonWidth = (screenMaxWidth - 50) / (screenMaxWidth / 300);
		int buttonHeight = (screenMaxHeight - 50) / (screenMaxHeight / 200);
		blackjackButton.setBounds(50, 50, buttonWidth, buttonHeight);
		blackjackButton.setBackground(buttonBackgroundColor);
		blackjackButton.setForeground(buttonFontColor);
		blackjackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Blackjack(1);
			}
		});


		setVisible(true);
	}
}