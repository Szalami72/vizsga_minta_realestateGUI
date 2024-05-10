import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.*;

import java.util.ArrayList;

public class MainFrame extends JFrame {

    JList<Seller> sellersList = new JList<>();
    JScrollPane scrollPane = new JScrollPane(sellersList);
    JLabel nameLabel = new JLabel("Eladó neve:");
    JLabel phoneLabel = new JLabel("Eladó telefonszáma:");
    JLabel adsJLabel = new JLabel("Hírdetések száma:");
    JLabel emptyLabel = new JLabel("");
    JButton btn = new JButton("Hírdetések betöltése");

    public MainFrame() throws Exception {
        getSellers();
        initComponents();
    }

    private void initComponents() {
        setSize(800, 600);
        setTitle("Ingatlanok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 30;
        add(nameLabel, gbc);

        gbc.gridy = 1;
        gbc.ipady = 30;
        add(phoneLabel, gbc);

        gbc.gridy = 2;
        gbc.ipady = 8;
        add(btn, gbc);

        gbc.gridy = 3;

        gbc.weighty = 0.0;
        gbc.ipady = 30;
        add(adsJLabel, gbc);

        gbc.gridy = 4;
        gbc.weighty = 0.0;
        gbc.ipady = 30;

        add(emptyLabel, gbc);

        setVisible(true);
    }

    private void getSellers() throws Exception {

        DataService dataService = new DataService();
        DefaultListModel<Seller> listModel = new DefaultListModel<>();
        ArrayList<Seller> sellerList = dataService.getSellerList();

        for (Seller seller : sellerList) {
            listModel.addElement(seller);
        }

        sellersList.setModel(listModel);

        sellersList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                Seller selectedSeller = sellersList.getSelectedValue();

                if (selectedSeller != null) {
                    try {
                        this.btn.addActionListener(event -> {
                            try {
                                getAdsNumber(selectedSeller.getId());
                            } catch (Exception e1) {
                                System.out.println("Nincs kiválasztva eladó");
                                e1.printStackTrace();
                            }
                        });
                        nameLabel.setText("Eladó neve:                     " + selectedSeller.getName());
                        phoneLabel.setText("Eladó telefonszáma:     " + selectedSeller.getPhone());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void getAdsNumber(int id) throws Exception {

        DataService dataService = new DataService();
        int adsNumber = dataService.getAdsNumber(id);
        adsJLabel.setText("Hírdetések száma:      " + adsNumber);

    }

}
