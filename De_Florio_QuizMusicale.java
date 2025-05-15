import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class De_Florio_QuizMusicale extends JFrame {

    private JTextField nomeField, cognomeField;
    private JTextArea risultatoArea;
    private JButton inviaButton;

    private String[] domande = {
        "1. Qual è l'altezza della nota DO sulla scala?",
        "2. Quante corde ha un violino?",
        "3. Che nota è un LA a 440 Hz?",
        "4. Il pentagramma ha quante linee?",
        "5. Il violino fa parte della famiglia dei?",
        "6. Come si chiama la chiave usata per il violino?",
        "7. Il metronomo serve per?",
        "8. Quale compositore ha scritto 'Le Quattro Stagioni'?",
        "9. Il violino si suona con?",
        "10. Quale di questi è un tempo musicale?"
    };

    private String[][] opzioni = {
        {"1", "2", "3", "Dipende dal contesto"},
        {"2", "3", "4", "6"},
        {"FA", "LA", "DO", "SOL"},
        {"3", "4", "5", "6"},
        {"Fiati", "Legni", "Archi", "Percussioni"},
        {"Chiave di basso", "Chiave di violino", "Chiave di tenore", "Chiave di contralto"},
        {"Misurare il tempo", "Intonare", "Cambiare tonalità", "Trascrivere spartiti"},
        {"Mozart", "Beethoven", "Vivaldi", "Chopin"},
        {"Bacchetta", "Martello", "Archetto", "Plettri"},
        {"Presto", "Nota", "Battuta", "Scala"}
    };

    private int[] risposteCorrette = {0, 2, 1, 2, 2, 1, 0, 2, 2, 0};
    private ButtonGroup[] gruppiRisposte = new ButtonGroup[10];

    public De_Florio_QuizMusicale() {
        setTitle("🎶 Quiz Musicale Interattivo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello per nome e cognome
        JPanel datiPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datiPanel.setBorder(BorderFactory.createTitledBorder("Dati Utente"));
        nomeField = new JTextField();
        cognomeField = new JTextField();
        datiPanel.add(new JLabel("Nome:"));
        datiPanel.add(nomeField);
        datiPanel.add(new JLabel("Cognome:"));
        datiPanel.add(cognomeField);

        // Pannello delle domande
        JPanel domandePanel = new JPanel();
        domandePanel.setLayout(new BoxLayout(domandePanel, BoxLayout.Y_AXIS));
        domandePanel.setBorder(BorderFactory.createTitledBorder("Domande"));

        for (int i = 0; i < domande.length; i++) {
            JPanel domandaPanel = new JPanel(new GridLayout(0, 1));
            domandaPanel.setBorder(BorderFactory.createTitledBorder(domande[i]));
            gruppiRisposte[i] = new ButtonGroup();

            for (String opzione : opzioni[i]) {
                JRadioButton r = new JRadioButton(opzione);
                r.setBackground(Color.WHITE);
                gruppiRisposte[i].add(r);
                domandaPanel.add(r);
            }

            domandaPanel.setBackground(new Color(245, 245, 255));
            domandePanel.add(domandaPanel);
        }

        JScrollPane scroll = new JScrollPane(domandePanel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Pannello invio e risultato
        JPanel risultatoPanel = new JPanel(new BorderLayout(10, 10));
        inviaButton = new JButton("Invia Risposte");
        inviaButton.setFont(new Font("Arial", Font.BOLD, 16));
        inviaButton.setBackground(new Color(70, 130, 180));
        inviaButton.setForeground(Color.WHITE);

        risultatoArea = new JTextArea(4, 40);
        risultatoArea.setEditable(false);
        risultatoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        risultatoArea.setBorder(BorderFactory.createTitledBorder("Risultato"));

        inviaButton.addActionListener(e -> calcolaRisultato());

        risultatoPanel.add(inviaButton, BorderLayout.NORTH);
        risultatoPanel.add(new JScrollPane(risultatoArea), BorderLayout.CENTER);

        // Inserimento dei pannelli
        add(datiPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(risultatoPanel, BorderLayout.SOUTH);
    }

    private void calcolaRisultato() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();

        if (nome.isEmpty() || cognome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci nome e cognome prima di inviare.", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica che tutte le domande siano state risposte
        for (int i = 0; i < gruppiRisposte.length; i++) {
            boolean selezionata = false;
            for (Enumeration<AbstractButton> buttons = gruppiRisposte[i].getElements(); buttons.hasMoreElements();) {
                if (buttons.nextElement().isSelected()) {
                    selezionata = true;
                    break;
                }
            }
            if (!selezionata) {
                JOptionPane.showMessageDialog(this, "Rispondi a tutte le domande prima di inviare (manca la domanda " + (i + 1) + ").", "Domanda incompleta", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        int punteggio = 0;
        for (int i = 0; i < gruppiRisposte.length; i++) {
            int indice = 0;
            for (Enumeration<AbstractButton> buttons = gruppiRisposte[i].getElements(); buttons.hasMoreElements(); indice++) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    if (indice == risposteCorrette[i]) {
                        punteggio++;
                    }
                }
            }
        }

        risultatoArea.setText("🎓 Risultato per " + nome + " " + cognome + ":\n");
        risultatoArea.append("✔ Hai risposto correttamente a " + punteggio + " su 10 domande.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new De_Florio_QuizMusicale().setVisible(true));
    }
}