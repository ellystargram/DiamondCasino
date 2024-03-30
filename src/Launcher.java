import javax.swing.*;
import java.awt.*;

public class Launcher extends CasinoFrame {
	final int screenMaxWidth = getToolkit().getScreenSize().width;
	final int screenMaxHeight = getToolkit().getScreenSize().height;
	JButton blackjackButton = new JButton("Blackjack");
	JButton VenttiButton = new JButton("Ventti");
	private final ChipReadWrite chipReadWrite = new ChipReadWrite();
	JLabel chipsLabel = new JLabel();
	JTextField chipsEnterField = new JTextField();

	Launcher() {
		super();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.exitButton.addActionListener(e -> System.exit(0));
		chipsLabel.setForeground(new Color(255, 255, 255));
		chipsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		chipsEnterField.setFont(new Font("Arial", Font.PLAIN, 20));
		chipsEnterField.setText("100");
		chipsEnterField.setHorizontalAlignment(JTextField.CENTER);
		chipsEnterField.setBackground(new Color(32,32,32));
		chipsEnterField.setForeground(new Color(255, 255, 255));

		Thread chipThread = new Thread(() -> {
			chipsLabel.setText("/ Chips: " + chipReadWrite.readChips());
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
				chipsLabel.setText("/ Chips: " + chipReadWrite.readChips());
			}
		});
		chipThread.start();

		final int buttonWidth = (screenMaxWidth - 100) / (screenMaxWidth / 350);
		final int buttonHeight = (screenMaxHeight - 100) / (screenMaxHeight / 250);

		try {
			chipsLabel.setText("/ Chips: " + chipReadWrite.readChips());
		} catch (Exception e) {
			chipReadWrite.writeChips(100);
			chipsLabel.setText("/ Chips: " + chipReadWrite.readChips());
		}

		super.ribonPanel.add(chipsLabel);
		chipsLabel.setBounds(super.screenMaxWidth/2, 0, 500, 50);
		super.ribonPanel.add(chipsEnterField);
		chipsEnterField.setBounds(super.screenMaxWidth/2-100, 0, 100, 50);

		JPanel contentPanel = new JPanel(null);
		contentPanel.setBackground(new Color(64, 64, 64));

		contentPanel.add(blackjackButton);
		blackjackButton.addActionListener(e -> new Blackjack(Long.parseLong(chipsEnterField.getText())));
		blackjackButton.setBounds(50, 50, buttonWidth, buttonHeight);
		blackjackButton.setBackground(new Color(32, 32, 32));
		blackjackButton.setForeground(new Color(255, 255, 255));
		blackjackButton.setFont(new Font("Arial", Font.PLAIN, 20));

		contentPanel.add(VenttiButton);
//		VenttiButton.addActionListener(e -> new Ventti(Long.parseLong(chipsEnterField.getText())));
		VenttiButton.setBounds(50 +  (buttonWidth), 50, buttonWidth, buttonHeight);
		VenttiButton.setBackground(new Color(32, 32, 32));
		VenttiButton.setForeground(new Color(255, 255, 255));
		VenttiButton.setFont(new Font("Arial", Font.PLAIN, 20));

		add(contentPanel, "Center");
		setVisible(true);
	}

	public static void main(String[] args) {
		new Launcher();
	}
}