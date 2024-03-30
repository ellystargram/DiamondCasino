import javax.swing.*;
import java.awt.*;

public class CasinoFrame extends JFrame {
	int screenMaxWidth = getToolkit().getScreenSize().width;
	int screenMaxHeight = getToolkit().getScreenSize().height;
	JPanel contentPanel = new JPanel(new BorderLayout());
	JPanel ribonPanel = new JPanel(null);
	JButton exitButton = new JButton("X");

	CasinoFrame() {
		makeRibonPanel();
		makeWindow();
		contentPanel.setBackground(new Color(64, 64, 64));
		setVisible(true);
	}

	private void makeWindow() {
		setSize(screenMaxWidth, screenMaxHeight);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("CASINO");
		setUndecorated(true);
		super.add(ribonPanel, "North");
		super.add(contentPanel, "Center");
	}

	private void makeRibonPanel() {
		ribonPanel.setPreferredSize(new Dimension(screenMaxWidth, 50));
		ribonPanel.add(exitButton);
		exitButton.setBounds(0, 0, 50, 50);
		exitButton.setBackground(new Color(233, 10, 10));
		exitButton.setForeground(new Color(255, 255, 255));
		exitButton.addActionListener(e -> dispose());
		ribonPanel.setBackground(new Color(0, 0, 0));
	}

	@Override
	public void add(Component comp, Object constraints) {
		if (this.contentPanel == null) {
			super.add(comp, constraints);
		} else {
			this.contentPanel.add(comp, constraints);
		}
	}

	@Override
	public void add(Component comp, Object constraints, int index) {
		if (this.contentPanel == null) {
			super.add(comp, constraints, index);
		} else {
			this.contentPanel.add(comp, constraints, index);
		}
	}

	@Override
	public Component add(Component comp) {
		if (this.contentPanel == null) {
			return super.add(comp);
		} else {
			return this.contentPanel.add(comp);
		}
	}

	@Override
	public Component add(Component comp, int index) {
		if (this.contentPanel == null) {
			return super.add(comp, index);
		} else {
			return this.contentPanel.add(comp, index);
		}

	}

	@Override
	public Component add(String name, Component comp) {
		if (this.contentPanel == null) {
			return super.add(name, comp);
		} else {
			return this.contentPanel.add(name, comp);
		}

	}

	@Override
	public void add(PopupMenu popup) {
		if (this.contentPanel == null) {
			super.add(popup);
		} else {
			this.contentPanel.add(popup);
		}

	}

	@Override
	public void remove(Component comp) {
		if (this.contentPanel == null) {
			super.remove(comp);
		} else {
			this.contentPanel.remove(comp);
		}
	}

	@Override
	public void remove(int index) {
		if (this.contentPanel == null) {
			super.remove(index);
		} else {
			this.contentPanel.remove(index);
		}
	}

	@Override
	public void removeAll() {
		if (this.contentPanel == null) {
			super.removeAll();
		} else {
			this.contentPanel.removeAll();
		}
	}
}
