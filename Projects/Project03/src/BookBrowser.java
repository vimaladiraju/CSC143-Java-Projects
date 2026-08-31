import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class BookBrowser extends JFrame {
    private static final String FILE_PATH = "BooksDataFile.txt";
    private static final String FIELD_ISBN = "ISBN";
    private static final String FIELD_AUTHORS = "Authors";
    private static final String FIELD_YEAR = "Publication Year";
    private static final String FIELD_ORIG_TITLE = "Original Title";
    private static final String FIELD_TITLE = "Title";
    private static final String FIELD_RATING = "Average Rating";

    private final Map<String, Book[]> bookIndices;
    private Book[] currentSortedBooks;
    private int currentIndex;

    private JComboBox<String> orderComboBox;
    private JTextField indexField;
    private JTextField isbnField;
    private JTextField authorsField;
    private JTextField yearField;
    private JTextField origTitleField;
    private JTextField titleField;
    private JTextField ratingField;
    private CoverPanel coverPanel;

    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : FILE_PATH;
        SwingUtilities.invokeLater(() -> new BookBrowser(file));
    }

    public BookBrowser(String filePath) {
        super("Book Browser");
        this.bookIndices = new HashMap<>();
        this.currentIndex = 0;

        loadDataAndBuildIndexes(filePath);

        initGui();
        updateDisplay();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadDataAndBuildIndexes(String filePath) {
        TreeMap<String, Book> isbnTree = new TreeMap<>();
        TreeMap<String, Book> authorsTree = new TreeMap<>();
        TreeMap<Integer, Book> yearTree = new TreeMap<>();
        TreeMap<String, Book> origTitleTree = new TreeMap<>();
        TreeMap<String, Book> titleTree = new TreeMap<>();
        TreeMap<Double, Book> ratingTree = new TreeMap<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("~", -1);
                if (parts.length >= 8) {
                    try {
                        String isbn = parts[2].trim();
                        String authors = parts[3].trim();
                        int pubYear = parts[4].trim().isEmpty() ? 0 : Integer.parseInt(parts[4].trim());
                        String origTitle = parts[5].trim();
                        String title = parts[6].trim();
                        double rating = parts[7].trim().isEmpty() ? 0.0 : Double.parseDouble(parts[7].trim());

                        Book book = new Book(isbn, authors, pubYear, origTitle, title, rating);

                        isbnTree.put(isbn, book);
                        authorsTree.put(authors, book);
                        yearTree.put(pubYear, book);
                        origTitleTree.put(origTitle, book);
                        titleTree.put(title, book);
                        ratingTree.put(rating, book);
                    } catch (NumberFormatException e) {
                        // skip all malformed rows
                    }
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        bookIndices.put(FIELD_ISBN, isbnTree.toValueArray(new Book[0]));
        bookIndices.put(FIELD_AUTHORS, authorsTree.toValueArray(new Book[0]));
        bookIndices.put(FIELD_YEAR, yearTree.toValueArray(new Book[0]));
        bookIndices.put(FIELD_ORIG_TITLE, origTitleTree.toValueArray(new Book[0]));
        bookIndices.put(FIELD_TITLE, titleTree.toValueArray(new Book[0]));
        bookIndices.put(FIELD_RATING, ratingTree.toValueArray(new Book[0]));

        currentSortedBooks = bookIndices.get(FIELD_ISBN);

    }

    private void initGui() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Order by: "));
        String[] options = {FIELD_ISBN, FIELD_AUTHORS, FIELD_YEAR, FIELD_ORIG_TITLE, FIELD_TITLE, FIELD_RATING};
        orderComboBox = new JComboBox<>(options);
        orderComboBox.addActionListener(e -> {
            String selected = (String) orderComboBox.getSelectedItem();
            if (selected != null && bookIndices.containsKey(selected)) {
                currentSortedBooks = bookIndices.get(selected);
                currentIndex = 0;
                updateDisplay();
            }
        });
        topPanel.add(orderComboBox);
        add(topPanel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldsPanel.add(new JLabel("ISBN:"));
        isbnField = createReadOnlyField();
        fieldsPanel.add(isbnField);

        fieldsPanel.add(new JLabel("Authors:"));
        authorsField = createReadOnlyField();
        fieldsPanel.add(authorsField);

        fieldsPanel.add(new JLabel("Year:"));
        yearField = createReadOnlyField();
        fieldsPanel.add(yearField);

        fieldsPanel.add(new JLabel("Orig. Title:"));
        origTitleField = createReadOnlyField();
        fieldsPanel.add(origTitleField);

        fieldsPanel.add(new JLabel("Title:"));
        titleField = createReadOnlyField();
        fieldsPanel.add(titleField);

        fieldsPanel.add(new JLabel("Avg. Rating:"));
        ratingField = createReadOnlyField();
        fieldsPanel.add(ratingField);

        JPanel centerContainer = new JPanel(new BorderLayout(10, 10));
        centerContainer.add(fieldsPanel, BorderLayout.CENTER);

        coverPanel = new CoverPanel();
        coverPanel.setPreferredSize(new Dimension(180, 240));
        centerContainer.add(coverPanel, BorderLayout.EAST);

        add(centerContainer, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(new JLabel("Index:"));
        indexField = new JTextField(5);
        indexField.addActionListener(e -> {
            try {
                int val = Integer.parseInt(indexField.getText().trim());
                if (val < 0) val = 0;
                if (val >= currentSortedBooks.length) {
                    val = Math.max(0, currentSortedBooks.length - 1);
                }
                currentIndex = val;
                updateDisplay();
            } catch (NumberFormatException n) {
                indexField.setText(String.valueOf(currentIndex));
            }
        });

        bottomPanel.add(indexField);

        JButton firstButton = new JButton("|<-");
        JButton prevButton = new JButton("< Prev");
        JButton nextButton = new JButton("Next >");
        JButton lastButton = new JButton("->|");

        firstButton.addActionListener(e -> {currentIndex = 0; updateDisplay();});
        prevButton.addActionListener(e -> { if (currentIndex > 0) { currentIndex --; updateDisplay(); }});
        nextButton.addActionListener(e -> { if (currentIndex < currentSortedBooks.length - 1) { currentIndex++; updateDisplay(); }});
        lastButton.addActionListener(e -> {currentIndex = Math.max(0, currentSortedBooks.length - 1); updateDisplay(); });

        bottomPanel.add(firstButton);
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(lastButton);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    private JTextField createReadOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    private void updateDisplay() {
        if (currentSortedBooks == null || currentSortedBooks.length == 0) {
            indexField.setText("0");
            isbnField.setText("");
            authorsField.setText("");
            yearField.setText("");
            origTitleField.setText("");
            titleField.setText("");
            ratingField.setText("");
            coverPanel.loadCover(null);
            return;
        }

        indexField.setText(String.valueOf(currentIndex));
        Book book = currentSortedBooks[currentIndex];
        isbnField.setText(book.isbn());
        authorsField.setText(book.authors());
        yearField.setText(String.valueOf(book.publicationYear()));
        origTitleField.setText(book.originalTitle());
        titleField.setText(book.title());
        ratingField.setText(String.valueOf(book.averageRating()));

        coverPanel.loadCover(book.isbn());
    }

    private static class CoverPanel extends JPanel {
        private Image coverImage;
        private boolean loadFailed;

        public void loadCover(String isbn) {
            this.coverImage = null;
            this.loadFailed = false;

            if (isbn != null && !isbn.isBlank()) {
                try {
                    String urlString = "https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg";
                    URI uri = new URI(urlString);
                    URL url = uri.toURL();
                    Image img = Toolkit.getDefaultToolkit().getImage(url);
                    MediaTracker tracker = new MediaTracker(this);
                    tracker.addImage(img, 0);
                    tracker.waitForID(0);

                    if (tracker.isErrorAny() || img.getWidth(this) <= 1) {
                        this.loadFailed = true;
                    } else this.coverImage = img;
                } catch (Exception e) {
                    this.loadFailed = true;
                }
            } else this.loadFailed = true;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            if (coverImage != null) {
                g.drawImage(coverImage, 0, 0, width, height, this);
            } else if (loadFailed) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, width, height);
            }

        }
    }
}
