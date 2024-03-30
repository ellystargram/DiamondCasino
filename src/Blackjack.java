import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

enum Suit {
	HEARTS, DIAMONDS, CLUBS, SPADES
}

enum Rank {
	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

enum Owner {
	PLAYER1, PLAYER2, PLAYER3, PLAYER4, DEALER, NONE
}

class Blackjack extends CasinoFrame {
	final int screenMaxWidth = getToolkit().getScreenSize().width;
	final int screenMaxHeight = getToolkit().getScreenSize().height;
	final double cardWidthRatio = 1;
	final double cardHeightRatio = 1.5;
	final int cardWidth = (int) (screenMaxWidth / (4 * 6) * cardWidthRatio);
	final int cardHeight = (int) (cardWidth * cardHeightRatio);
	private final ChipReadWrite chipReadWrite = new ChipReadWrite();
	JPanel contentPanel = new JPanel(null) {
		@Override
		protected void paintComponent(Graphics gd) {
			super.paintComponent(gd);
			Graphics2D grap = (Graphics2D) gd;

			if (player1.playEnabled) {
				grap.setColor(new Color(0, 255, 0));
				grap.setFont(new Font("Arial", Font.PLAIN, 30));
				grap.drawString(player1.name, 50, player1.buttonPanel.getY() - 20);
				grap.setColor(new Color(255, 0, 255));
				grap.drawString(": " + player1.result, 50 + grap.getFontMetrics().stringWidth(player1.name), player1.buttonPanel.getY() - 20);
				for (Card card : player1.hand) {
					if (card.isFliped) {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15) - 5, screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 0));
						grap.fillRect(50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15), screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
					} else {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15) - 5, screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 180));
						grap.fillRect(50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15), screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
						grap.setFont(new Font("Arial", Font.PLAIN, 20));
						switch (card.suit) {
							case HEARTS, DIAMONDS -> grap.setColor(Color.RED);
							case CLUBS, SPADES -> grap.setColor(Color.BLACK);
						}
						String cardRankString = switch (card.rank) {
							case ACE -> "A";
							case JACK -> "J";
							case QUEEN -> "Q";
							case KING -> "K";
							default -> String.valueOf(card.rank.ordinal() + 1);
						};
						grap.drawString(cardRankString, 50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15) + 5, screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)) + 20);
						grap.setFont(new Font("Arial", Font.PLAIN, 30));
						String cardSuitString = switch (card.suit) {
							case HEARTS -> "♥";
							case DIAMONDS -> "♦";
							case CLUBS -> "♣";
							case SPADES -> "♠";
						};
						grap.drawString(cardSuitString, 50 + (player1.hand.indexOf(card) % 4) * (cardWidth + 15) + 5, screenMaxHeight - 150 - cardHeight - ((player1.hand.indexOf(card) / 4) * (cardHeight + 15)) + 50);
					}
				}
				grap.setColor(new Color(0, 255, 0));
				if (player1.score == 21 && player1.hand.size() == 2) {
					grap.setFont(new Font("Arial", Font.PLAIN, 20));
					grap.drawString("Blackjack", 50 + (player1.hand.size() % 4) * (cardWidth + 15), screenMaxHeight - 150 - cardHeight - ((player1.hand.size() / 4) * (cardHeight + 15)) + 20);
				} else {
					grap.setFont(new Font("Arial", Font.PLAIN, 40));
					grap.drawString(String.valueOf(player1.score), 50 + (player1.hand.size() % 4) * (cardWidth + 15) + 5, screenMaxHeight - 150 - cardHeight - ((player1.hand.size() / 4) * (cardHeight + 15)) + 30);
				}
			}

			if (player2.playEnabled) {
				grap.setColor(new Color(0, 255, 0));
				grap.setFont(new Font("Arial", Font.PLAIN, 30));
				grap.drawString(player2.name, 50 + (screenMaxWidth / 4), player2.buttonPanel.getY() - 20);
				grap.setColor(new Color(255, 0, 255));
				grap.drawString(": " + player2.result, 50 + grap.getFontMetrics().stringWidth(player2.name) + (screenMaxWidth / 4), player2.buttonPanel.getY() - 20);
				for (Card card : player2.hand) {
					if (card.isFliped) {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 0));
						grap.fillRect(50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
					} else {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 180));
						grap.fillRect(50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
						grap.setFont(new Font("Arial", Font.PLAIN, 20));
						switch (card.suit) {
							case HEARTS, DIAMONDS -> grap.setColor(Color.RED);
							case CLUBS, SPADES -> grap.setColor(Color.BLACK);
						}
						String cardRankString = switch (card.rank) {
							case ACE -> "A";
							case JACK -> "J";
							case QUEEN -> "Q";
							case KING -> "K";
							default -> String.valueOf(card.rank.ordinal() + 1);
						};
						grap.drawString(cardRankString, 50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)) + 20);
						grap.setFont(new Font("Arial", Font.PLAIN, 30));
						String cardSuitString = switch (card.suit) {
							case HEARTS -> "♥";
							case DIAMONDS -> "♦";
							case CLUBS -> "♣";
							case SPADES -> "♠";
						};
						grap.drawString(cardSuitString, 50 + (player2.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.indexOf(card) / 4) * (cardHeight + 15)) + 50);
					}
				}
				grap.setColor(new Color(0, 255, 0));
				if (player2.score == 21 && player2.hand.size() == 2) {
					grap.setFont(new Font("Arial", Font.PLAIN, 20));
					grap.drawString("Blackjack", 50 + (player2.hand.size() % 4) * (cardWidth + 15) + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.size() / 4) * (cardHeight + 15)) + 20);
				} else {
					grap.setFont(new Font("Arial", Font.PLAIN, 40));
					grap.drawString(String.valueOf(player2.score), 50 + (player2.hand.size() % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4), screenMaxHeight - 150 - cardHeight - ((player2.hand.size() / 4) * (cardHeight + 15)) + 30);
				}
			}

			if (player3.playEnabled) {
				grap.setColor(new Color(0, 255, 0));
				grap.setFont(new Font("Arial", Font.PLAIN, 30));
				grap.drawString(player3.name, 50 + (screenMaxWidth / 2), player3.buttonPanel.getY() - 20);
				grap.setColor(new Color(255, 0, 255));
				grap.drawString(": " + player3.result, 50 + grap.getFontMetrics().stringWidth(player3.name) + (screenMaxWidth / 2), player3.buttonPanel.getY() - 20);
				for (Card card : player3.hand) {
					final int playerNumber = 3;
					if (card.isFliped) {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 0));
						grap.fillRect(50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
					} else {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 180));
						grap.fillRect(50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
						grap.setFont(new Font("Arial", Font.PLAIN, 20));
						switch (card.suit) {
							case HEARTS, DIAMONDS -> grap.setColor(Color.RED);
							case CLUBS, SPADES -> grap.setColor(Color.BLACK);
						}
						String cardRankString = switch (card.rank) {
							case ACE -> "A";
							case JACK -> "J";
							case QUEEN -> "Q";
							case KING -> "K";
							default -> String.valueOf(card.rank.ordinal() + 1);
						};
						grap.drawString(cardRankString, 50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)) + 20);
						grap.setFont(new Font("Arial", Font.PLAIN, 30));
						String cardSuitString = switch (card.suit) {
							case HEARTS -> "♥";
							case DIAMONDS -> "♦";
							case CLUBS -> "♣";
							case SPADES -> "♠";
						};
						grap.drawString(cardSuitString, 50 + (player3.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player3.hand.indexOf(card) / 4) * (cardHeight + 15)) + 50);
					}
				}
				grap.setColor(new Color(0, 255, 0));
				if (player3.score == 21 && player3.hand.size() == 2) {
					grap.setFont(new Font("Arial", Font.PLAIN, 20));
					grap.drawString("Blackjack", 50 + (player3.hand.size() % 4) * (cardWidth + 15) + (screenMaxWidth / 2), screenMaxHeight - 150 - cardHeight - ((player3.hand.size() / 4) * (cardHeight + 15)) + 20);
				} else {
					grap.setFont(new Font("Arial", Font.PLAIN, 40));
					grap.drawString(String.valueOf(player3.score), 50 + (player3.hand.size() % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 2), screenMaxHeight - 150 - cardHeight - ((player3.hand.size() / 4) * (cardHeight + 15)) + 30);
				}
			}

			if (player4.playEnabled) {
				grap.setColor(new Color(0, 255, 0));
				grap.setFont(new Font("Arial", Font.PLAIN, 30));
				grap.drawString(player4.name, 50 + (screenMaxWidth / 4) * 3, player4.buttonPanel.getY() - 20);
				grap.setColor(new Color(255, 0, 255));
				grap.drawString(": " + player4.result, 50 + grap.getFontMetrics().stringWidth(player4.name) + (screenMaxWidth / 4) * 3, player4.buttonPanel.getY() - 20);
				for (Card card : player4.hand) {
					final int playerNumber = 4;
					if (card.isFliped) {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 0));
						grap.fillRect(50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
					} else {
						grap.setColor(new Color(60, 60, 60));
						grap.fillRect(50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) - 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
						grap.setColor(new Color(180, 180, 180));
						grap.fillRect(50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
						grap.setFont(new Font("Arial", Font.PLAIN, 20));
						switch (card.suit) {
							case HEARTS, DIAMONDS -> grap.setColor(Color.RED);
							case CLUBS, SPADES -> grap.setColor(Color.BLACK);
						}
						String cardRankString = switch (card.rank) {
							case ACE -> "A";
							case JACK -> "J";
							case QUEEN -> "Q";
							case KING -> "K";
							default -> String.valueOf(card.rank.ordinal() + 1);
						};
						grap.drawString(cardRankString, 50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)) + 20);
						grap.setFont(new Font("Arial", Font.PLAIN, 30));
						String cardSuitString = switch (card.suit) {
							case HEARTS -> "♥";
							case DIAMONDS -> "♦";
							case CLUBS -> "♣";
							case SPADES -> "♠";
						};
						grap.drawString(cardSuitString, 50 + (player4.hand.indexOf(card) % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4) * (playerNumber - 1), screenMaxHeight - 150 - cardHeight - ((player4.hand.indexOf(card) / 4) * (cardHeight + 15)) + 50);
					}
				}
				grap.setColor(new Color(0, 255, 0));
				if (player4.score == 21 && player4.hand.size() == 2) {
					grap.setFont(new Font("Arial", Font.PLAIN, 20));
					grap.drawString("Blackjack", 50 + (player4.hand.size() % 4) * (cardWidth + 15) + (screenMaxWidth / 4) * 3, screenMaxHeight - 150 - cardHeight - ((player4.hand.size() / 4) * (cardHeight + 15)) + 20);
				} else {
					grap.setFont(new Font("Arial", Font.PLAIN, 40));
					grap.drawString(String.valueOf(player4.score), 50 + (player4.hand.size() % 4) * (cardWidth + 15) + 5 + (screenMaxWidth / 4) * 3, screenMaxHeight - 150 - cardHeight - ((player4.hand.size() / 4) * (cardHeight + 15)) + 30);
				}
			}

			grap.setColor(new Color(0, 255, 0));
			grap.setFont(new Font("Arial", Font.PLAIN, 30));
			grap.drawString(dealer.name, (screenMaxWidth - grap.getFontMetrics().stringWidth(dealer.name)) / 2, 30);
			final int dealerCenter = (screenMaxWidth - 4 * (cardWidth + 15)) / 2;

			for (Card card : dealer.hand) {
				if (card.isFliped) {
					grap.setColor(new Color(60, 60, 60));
					grap.fillRect(dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15) - 5, 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
					grap.setColor(new Color(180, 180, 0));
					grap.fillRect(dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15), 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
				} else {
					grap.setColor(new Color(60, 60, 60));
					grap.fillRect(dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15) - 5, 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)) - 5, cardWidth + 10, cardHeight + 10);
					grap.setColor(new Color(180, 180, 180));
					grap.fillRect(dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15), 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)), cardWidth, cardHeight);
					grap.setFont(new Font("Arial", Font.PLAIN, 20));
					switch (card.suit) {
						case HEARTS, DIAMONDS -> grap.setColor(Color.RED);
						case CLUBS, SPADES -> grap.setColor(Color.BLACK);
					}
					String cardRankString = switch (card.rank) {
						case ACE -> "A";
						case JACK -> "J";
						case QUEEN -> "Q";
						case KING -> "K";
						default -> String.valueOf(card.rank.ordinal() + 1);
					};
					grap.drawString(cardRankString, dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15) + 5, 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)) + 20);
					grap.setFont(new Font("Arial", Font.PLAIN, 30));
					String cardSuitString = switch (card.suit) {
						case HEARTS -> "♥";
						case DIAMONDS -> "♦";
						case CLUBS -> "♣";
						case SPADES -> "♠";
					};
					grap.drawString(cardSuitString, dealerCenter + (dealer.hand.indexOf(card) % 4) * (cardWidth + 15) + 5, 100 + ((dealer.hand.indexOf(card) / 4) * (cardHeight + 15)) + 50);
				}
			}
			grap.setColor(new Color(0, 255, 0));
			if (dealer.score == 21 && dealer.hand.size() == 2) {
				grap.setFont(new Font("Arial", Font.PLAIN, 20));
				grap.drawString("Blackjack", dealerCenter + (dealer.hand.size() % 4) * (cardWidth + 15), 100 + ((dealer.hand.size() / 4) * (cardHeight + 15)) + 20);
			} else {
				grap.setFont(new Font("Arial", Font.PLAIN, 40));
				grap.drawString(String.valueOf(dealer.score), dealerCenter + (dealer.hand.size() % 4) * (cardWidth + 15) + 5, 100 + ((dealer.hand.size() / 4) * (cardHeight + 15)) + 30);
			}

			grap.setColor(new Color(255, 0, 255));
			grap.setFont(new Font("Arial", Font.PLAIN, 60));
			grap.drawString(resultLabel.getText(), (screenMaxWidth - grap.getFontMetrics().stringWidth(resultLabel.getText())) / 2, screenMaxHeight / 2 - 60);

			grap.setFont(new Font("Arial", Font.PLAIN, 30));

			repaint();
		}
	};
	private final JLabel resultLabel = new JLabel();
	Person player1;
	Person player2;
	Person player3;
	Person player4;
	Person dealer;
	ArrayList<Card> deck;
	private final long betChips;

	public Blackjack(long betChips) {
		super();
		this.betChips = betChips;
		add(contentPanel, "Center");
		contentPanel.setBackground(new Color(64, 64, 64));
		startGame();
		setVisible(true);
	}

	private void startGame() {
		shuffleDeck();
		player1 = new Person("Player1");
		player1.role = Owner.PLAYER1;
		player2 = new Person("Player2");
		player2.role = Owner.PLAYER2;
		player3 = new Person("Player3");
		player3.role = Owner.PLAYER3;
		player4 = new Person("Player4");
		player4.role = Owner.PLAYER4;
		dealer = new Person("Dealer");
		dealer.role = Owner.DEALER;

		player1.playEnabled = true;
		for (int i = 0; i < 2; i++) {
			getCard(player1);
			player1.hand.get(i).isFliped = false;
		}
		player1.calculateScore();
		player1.setEnableButton(true);
		player1.standButton.addActionListener(e -> standCard(player1));
		player1.hitButton.addActionListener(e -> hitCard(player1));
		player1.doubleDownButton.addActionListener(e -> doubleDownCard(player1));
		player1.splitButton.addActionListener(e -> splitCard(player1));
		player1.splitButton.setEnabled(player1.hand.get(0).realValue() == player1.hand.get(1).realValue());
		player1.bet = this.betChips;
		player1.result = "Bet: " + player1.bet;

		player2.standButton.addActionListener(e -> standCard(player2));
		player2.hitButton.addActionListener(e -> hitCard(player2));
		player2.doubleDownButton.addActionListener(e -> doubleDownCard(player2));
		player2.splitButton.addActionListener(e -> splitCard(player2));

		player3.standButton.addActionListener(e -> standCard(player3));
		player3.hitButton.addActionListener(e -> hitCard(player3));
		player3.doubleDownButton.addActionListener(e -> doubleDownCard(player3));
		player3.splitButton.addActionListener(e -> splitCard(player3));

		player4.standButton.addActionListener(e -> standCard(player4));
		player4.hitButton.addActionListener(e -> hitCard(player4));
		player4.doubleDownButton.addActionListener(e -> doubleDownCard(player4));
		player4.splitButton.addActionListener(e -> splitCard(player4));

		dealer.playEnabled = true;
		for (int i = 0; i < 2; i++) {
			getCard(dealer);
		}
		dealer.hand.getFirst().isFliped = false;
		dealer.calculateScore();

		contentPanel.add(player1.buttonPanel);
		contentPanel.add(player2.buttonPanel);
		contentPanel.add(player3.buttonPanel);
		contentPanel.add(player4.buttonPanel);

		player1.buttonPanel.setBounds(50, screenMaxHeight - 100, 4 * cardWidth + 45, 50);
		player2.buttonPanel.setBounds(50 + (screenMaxWidth / 4), screenMaxHeight - 100, 4 * cardWidth + 45, 50);
		player3.buttonPanel.setBounds(50 + (screenMaxWidth / 2), screenMaxHeight - 100, 4 * cardWidth + 45, 50);
		player4.buttonPanel.setBounds(50 + (screenMaxWidth / 4) * 3, screenMaxHeight - 100, 4 * cardWidth + 45, 50);
	}

	private void shuffleDeck() {
		deck = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 52; i++) {
			deck.add(new Card(Suit.values()[i / 13], Rank.values()[i % 13]));
		}
		for (int i = 0; i < 52; i++) {
			int randomIndex = rand.nextInt(52);
			Card temp = deck.get(i);
			deck.set(i, deck.get(randomIndex));
			deck.set(randomIndex, temp);
		}
	}

	private void splitCard(Person origin) {
		Card splitCard = origin.hand.get(1);
		origin.hand.remove(1);
		getCard(origin);
		origin.hand.get(1).isFliped = false;
		origin.calculateScore();
		origin.splitButton.setEnabled(origin.hand.get(0).realValue() == origin.hand.get(1).realValue());

		if (!player2.playEnabled) {
			player2.playEnabled = true;
			player2.hand.add(splitCard);
			getCard(player2);
			player2.hand.get(1).isFliped = false;
			player2.calculateScore();
			player2.setEnableButton(true);
			player2.splitButton.setEnabled(player2.hand.get(0).realValue() == player2.hand.get(1).realValue());
			player2.bet = origin.bet;
			player2.result = "Bet: " + player2.bet;
		} else if (!player3.playEnabled) {
			player3.playEnabled = true;
			player3.hand.add(splitCard);
			getCard(player3);
			player3.hand.get(1).isFliped = false;
			player3.calculateScore();
			player3.setEnableButton(true);
			player3.splitButton.setEnabled(player3.hand.get(0).realValue() == player3.hand.get(1).realValue());
			player3.bet = origin.bet;
			player3.result = "Bet: " + player3.bet;
		} else if (!player4.playEnabled) {
			player4.playEnabled = true;
			player4.hand.add(splitCard);
			getCard(player4);
			player4.hand.get(1).isFliped = false;
			player4.calculateScore();
			player4.setEnableButton(true);

			player4.bet = origin.bet;
			player4.result = "Bet: " + player4.bet;

			player1.splitButton.setEnabled(false);
			player2.splitButton.setEnabled(false);
			player3.splitButton.setEnabled(false);
			player4.splitButton.setEnabled(false);
		}
		checkAllPlayerEnd();
	}

	void hitCard(Person person) {
		getCard(person);
		person.hand.getLast().isFliped = false;
		person.splitButton.setEnabled(false);
		person.calculateScore();
		if (person.score > 21) {
			person.setEnableButton(false);
			person.result = "Bust";
		} else if (person.score == 21 && person.hand.size() == 2) {
			person.result = "Blackjack";
		} else {
			person.result = "Hit";
		}
		checkAllPlayerEnd();
	}

	void standCard(Person person) {
		person.setEnableButton(false);
		person.result = "Stand";
		checkAllPlayerEnd();
	}

	void doubleDownCard(Person person) {
		person.bet *= 2;
		getCard(person);
		person.hand.getLast().isFliped = false;
		person.calculateScore();
		if (person.score > 21) {
			person.result = "Bust";
		} else if (person.score == 21 && person.hand.size() == 2) {
			person.result = "Blackjack";
		} else {
			person.result = "Double Down";
		}
		person.setEnableButton(false);
		checkAllPlayerEnd();
	}

	void checkAllPlayerEnd() {
		if (!player1.standButton.isEnabled() && !player2.standButton.isEnabled() && !player3.standButton.isEnabled() && !player4.standButton.isEnabled()) {
			dealerTurn();
		}
	}

	void dealerTurn() {
		dealer.hand.get(1).isFliped = false;
		dealer.calculateScore();
		Thread dealerThread = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
			final int dealerTarget = getDealerTarget();
			while (dealer.score < 17 && dealer.score <= dealerTarget) {
				getCard(dealer);
				dealer.hand.getLast().isFliped = false;
				dealer.calculateScore();
				if(!(dealer.score < 17 && dealer.score <= dealerTarget)){
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (dealer.score > 21) {
				resultLabel.setText("Dealer Bust");
			} else {
				resultLabel.setText("Dealer Score: " + dealer.score);
			}
			calculateWinLoss(player1);
			calculateWinLoss(player2);
			calculateWinLoss(player3);
			calculateWinLoss(player4);

			final long totalBetChips = player1.bet + player2.bet + player3.bet + player4.bet;
			final long totalRefundChips = player1.refund + player2.refund + player3.refund + player4.refund;
			System.out.println("Total Bet: " + totalBetChips);
			System.out.println("Total Refund: " + totalRefundChips);

			final long previousUpdateChips = chipReadWrite.readChips();
			chipReadWrite.writeChips(previousUpdateChips + totalRefundChips - totalBetChips);
		});
		dealerThread.start();
	}

	private void calculateWinLoss(Person person) {
		if (!person.playEnabled) {
			return;
		}
		if (person.score > 21) {
			person.result = "Bust";
			return;
		} else if (person.score == 21 && person.hand.size() == 2) {
			person.result = "Blackjack: " + person.bet * 2.5;
			person.refund = (int) (person.bet * 2.5);
		}
		if (dealer.score > 21) {
			person.result = "Get: " + person.bet * 2;
			person.refund = person.bet * 2;
		} else if (dealer.score == 21 && dealer.hand.size() == 2) {
			if (person.score == 21 && person.hand.size() == 2) {
				person.result = "Push";
				person.refund = person.bet;
			} else {
				person.result = "Lose";
			}
		} else if (dealer.score > person.score) {
			person.result = "Lose";
		} else if (dealer.score < person.score) {
			person.result = "Get: " + person.bet * 2;
			person.refund = person.bet * 2;
		} else {
			person.result = "Push";
			person.refund = person.bet;
		}
	}

	private int getDealerTarget() {
		final int player1Score;
		final int player2Score;
		final int player3Score;
		final int player4Score;
		if (player1.score > 21) {
			player1Score = 0;
		} else {
			player1Score = player1.score;
		}
		if (player2.score > 21) {
			player2Score = 0;
		} else {
			player2Score = player2.score;
		}
		if (player3.score > 21) {
			player3Score = 0;
		} else {
			player3Score = player3.score;
		}
		if (player4.score > 21) {
			player4Score = 0;
		} else {
			player4Score = player4.score;
		}
		return Math.max(Math.max(player1Score, player2Score), Math.max(player3Score, player4Score));
	}

	void getCard(Person person) {
		deck.getFirst().owner = person.role;
		person.hand.add(deck.getFirst());
		deck.removeFirst();
	}
}

class Card {
	Suit suit;
	Rank rank;
	Owner owner;
	boolean isFliped = true;

	Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
		this.owner = Owner.NONE;
	}

	int realValue() {
		return switch (rank) {
			case ACE -> 11;
			case TWO -> 2;
			case THREE -> 3;
			case FOUR -> 4;
			case FIVE -> 5;
			case SIX -> 6;
			case SEVEN -> 7;
			case EIGHT -> 8;
			case NINE -> 9;
			case TEN, JACK, QUEEN, KING -> 10;
		};
	}
}

class Person {
	ArrayList<Card> hand = new ArrayList<>();
	String name;
	Owner role;
	int score;
	boolean playEnabled = false;
	JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
	JButton standButton = new JButton("Stand");
	JButton hitButton = new JButton("Hit");
	JButton doubleDownButton = new JButton("DD");
	JButton splitButton = new JButton("Split");
	String result = "";
	long bet = 0;
	long refund = 0;

	Person(String name) {
		this.name = name;

		buttonPanel.add(standButton);
		buttonPanel.add(hitButton);
		buttonPanel.add(doubleDownButton);
		buttonPanel.add(splitButton);

		standButton.setEnabled(false);
		hitButton.setEnabled(false);
		doubleDownButton.setEnabled(false);
		splitButton.setEnabled(false);

		buttonPanel.setBackground(new Color(128, 128, 128));
		standButton.setBackground(new Color(128, 128, 128));
		hitButton.setBackground(new Color(128, 128, 128));
		doubleDownButton.setBackground(new Color(128, 128, 128));
		splitButton.setBackground(new Color(128, 128, 128));

		standButton.setForeground(new Color(255, 255, 255));
		hitButton.setForeground(new Color(255, 255, 255));
		doubleDownButton.setForeground(new Color(255, 255, 255));
		splitButton.setForeground(new Color(255, 255, 255));
	}

	void setEnableButton(boolean bool) {
		standButton.setEnabled(bool);
		hitButton.setEnabled(bool);
		doubleDownButton.setEnabled(bool);
		splitButton.setEnabled(bool);
	}

	void calculateScore() {
		int countAce = 0;
		for (Card card : hand) {
			if (card.rank == Rank.ACE && !card.isFliped) {
				countAce++;
			}
		}
		score = 0;
		for (Card card : hand) {
			if (card.isFliped) {
				continue;
			}
			switch (card.rank) {
				case TWO:
					score += 2;
					break;
				case THREE:
					score += 3;
					break;
				case FOUR:
					score += 4;
					break;
				case FIVE:
					score += 5;
					break;
				case SIX:
					score += 6;
					break;
				case SEVEN:
					score += 7;
					break;
				case EIGHT:
					score += 8;
					break;
				case NINE:
					score += 9;
					break;
				case TEN:
				case JACK:
				case QUEEN:
				case KING:
					score += 10;
					break;
			}
		}
		int aceSimulate;
		for (aceSimulate = 0; aceSimulate <= countAce; aceSimulate++) {
			if (aceSimulate * 11 + score > 21) {
				break;
			}
		}
		if (score <= 21) {
			aceSimulate--;
		}
		score = score + aceSimulate * 11 + (countAce - aceSimulate);
	}
}