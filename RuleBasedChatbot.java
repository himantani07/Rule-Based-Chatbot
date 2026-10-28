import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class RuleBasedChatbot extends JFrame {

    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;

    public RuleBasedChatbot() {

        setTitle("Rule-Based Chatbot");
        setSize(650, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(38, 95, 160));
        header.setBorder(new EmptyBorder(18, 15, 18, 15));

        JLabel title = new JLabel("CHATBOT");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your Rule-Based Virtual Assistant");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Chat area
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(245, 248, 252));
        chatPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Bottom input area
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 15));
        inputField.setToolTipText("Type your message here");

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(38, 95, 160));
        sendButton.setFocusPainted(false);

        JButton clearButton = new JButton("Clear Chat");

        bottomPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        buttons.add(clearButton);
        buttons.add(sendButton);

        bottomPanel.add(buttons, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        clearButton.addActionListener(e -> {
            chatPanel.removeAll();
            addMessage(
                "Hello! Welcome back. How can I help you?",
                false
            );
        });

        // Initial chatbot message
        addMessage(
            "Hello! I am your chatbot. Type 'help' to see what I can do.",
            false
        );
    }

    private void sendMessage() {

        String message = inputField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        addMessage(message, true);
        inputField.setText("");

        String response = getResponse(message);

        addMessage(response, false);
    }

    private String getResponse(String input) {

        String message = input.toLowerCase(Locale.ROOT)
                              .replaceAll("[^a-z0-9\\s]", " ")
                              .replaceAll("\\s+", " ")
                              .trim();

        // Greetings
        if (message.matches(".*\\b(hi|hello|hey)\\b.*")) {
            return "Hello! Nice to talk to you. How can I help?";
        }

        // Name
        else if (message.contains("your name")
                || message.equals("who are you")) {
            return "I am a simple rule-based chatbot built using Java Swing.";
        }

        // Well-being
        else if (message.contains("how are you")) {
            return "I am functioning properly! Thank you for asking.";
        }

        // Help
        else if (message.equals("help")
                || message.contains("what can you do")) {
            return "You can ask me about my name, Java, the date, "
                 + "the time, or tell me a joke. You can also say hello or goodbye.";
        }

        // Time
        else if (message.contains("time")) {
            return "The current time is "
                 + LocalTime.now().withNano(0) + ".";
        }

        // Date
        else if (message.contains("date")
                || message.contains("today")) {
            return "Today's date is " + LocalDate.now() + ".";
        }

        // Programming
        else if (message.contains("java")) {
            return "Java is an object-oriented programming language "
                 + "used to build desktop, web, and enterprise applications.";
        }

        // Joke
        else if (message.contains("joke")) {
            return "Why do programmers prefer dark mode? "
                 + "Because light attracts bugs!";
        }

        // Thanks
        else if (message.contains("thank")) {
            return "You're welcome! Happy to help.";
        }

        // Goodbye
        else if (message.matches(".*\\b(bye|goodbye|exit)\\b.*")) {
            return "Goodbye! Have a wonderful day.";
        }

        // Unknown input
        else {
            return "Sorry, I don't understand that yet. "
                 + "Type 'help' to see the available commands.";
        }
    }

    private void addMessage(String message, boolean isUser) {

        JPanel row = new JPanel(new FlowLayout(
            isUser ? FlowLayout.RIGHT : FlowLayout.LEFT
        ));

        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea bubble = new JTextArea(message);
        bubble.setFont(new Font("Arial", Font.PLAIN, 14));
        bubble.setLineWrap(true);
        bubble.setWrapStyleWord(true);
        bubble.setEditable(false);
        bubble.setFocusable(false);
        bubble.setOpaque(true);

        bubble.setBackground(
            isUser
                ? new Color(205, 235, 255)
                : new Color(225, 232, 242)
        );

        bubble.setForeground(new Color(30, 40, 55));
        bubble.setBorder(new EmptyBorder(10, 12, 10, 12));
        bubble.setColumns(28);
        bubble.setSize(new Dimension(350, Short.MAX_VALUE));

        row.add(bubble);
        chatPanel.add(row);

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new RuleBasedChatbot().setVisible(true);
        });
    }
}