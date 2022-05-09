import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenu extends JPanel {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private boolean isScrapping;
    private JPanel convertPanel;
    private JTable dataTable;

    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 500;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;
    public static final int LABEL_WIDTH = 400;
    public static final int LABEL_HEIGHT = 50;
    public static final int REFRESH_TIME = 1000;
    public static final int EUR_USD = 1;
    public static final int GBP_USD = 2;
    public static final int USD_JPY = 3;
    public static final int USD_ILS = 4;
    public static final int EUR_ILS = 5;
    public static final int INDEX_OF_GATE = 2;
    public static final int INDEX_OF_CHANGE = 5;
    public static final String []  TITLE_TABLE = {"Name","Pair","Last","High","Low","Change","Change%","Time"};


    public MainMenu() {
        this.setLayout(null);
        this.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setBackground(Color.CYAN);
        ImageIcon background = new ImageIcon("background.jpg");
        JLabel backGround = new JLabel(background);
        backGround.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        this.add(backGround);
        mainMenu();
    }

    public boolean isScrapping() {
        return isScrapping;
    }

    public void setScrapping(boolean scrapping) {
        isScrapping = scrapping;
    }

    public void hideMainButton (){
        this.button1.setVisible(false);
        this.button2.setVisible(false);
        this.button3.setVisible(false);
        this.button4.setVisible(false);
        this.button5.setVisible(false);
    }

    public void showMainButton (){
        this.button1.setVisible(true);
        this.button2.setVisible(true);
        this.button3.setVisible(true);
        this.button4.setVisible(true);
        this.button5.setVisible(true);
    }

    public void mainMenu() {
        button1 = new JButton("EUR/USD");
        button1.setBounds(WINDOW_WIDTH / 7, WINDOW_HEIGHT / 7, BUTTON_WIDTH, BUTTON_HEIGHT);
        button2 = new JButton("GBP/USD");
        button2.setBounds(WINDOW_WIDTH / 7 + 100, WINDOW_HEIGHT / 7, BUTTON_WIDTH, BUTTON_HEIGHT);
        button3 = new JButton("USD/JPY");
        button3.setBounds(WINDOW_WIDTH / 7 + 200, WINDOW_HEIGHT / 7, BUTTON_WIDTH, BUTTON_HEIGHT);
        button4 = new JButton("USD/ILS");
        button4.setBounds(WINDOW_WIDTH / 7 + 300, WINDOW_HEIGHT / 7, BUTTON_WIDTH, BUTTON_HEIGHT);
        button5 = new JButton("EUR/ILS");
        button5.setBounds(WINDOW_WIDTH / 7 + 400, WINDOW_HEIGHT / 7, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        button1.addActionListener((e) -> {
            hideMainButton();
            convertMenu(EUR_USD);

        });
        button2.addActionListener((e) -> {
            hideMainButton();
            convertMenu(GBP_USD);

        });
        button3.addActionListener((e) -> {
            hideMainButton();
            convertMenu(USD_JPY);

        });
        button4.addActionListener((e) -> {
            hideMainButton();
            convertMenu(USD_ILS);
        });
        button5.addActionListener((e) -> {
            hideMainButton();
            convertMenu(EUR_ILS);
        });
        this.setVisible(true);
    }

    public void convertMenu(int kind) {
        this.convertPanel = new JPanel();
        this.convertPanel.setLayout(null);
        this.convertPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.convertPanel.setBackground(Color.GRAY);
        JButton backButton = new JButton("Back");
        backButton.setBounds(WINDOW_WIDTH / 7+50, WINDOW_HEIGHT / 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(WINDOW_WIDTH / 70, WINDOW_HEIGHT / 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.convertPanel.add(backButton);
        this.convertPanel.add(convertButton);
        Font font = new Font("Arial", Font.BOLD, 20);
        JTextField textField = new JTextField();
        textField.setBounds(WINDOW_WIDTH/2-200, WINDOW_HEIGHT/2, LABEL_WIDTH, LABEL_HEIGHT);
        textField.setBackground(Color.GRAY);
        textField.setText("Enter number and press convert");
        textField.setFont(font);
        this.convertPanel.add(textField);
        JLabel label = new JLabel("------");
        label.setBounds(WINDOW_WIDTH/2-200, WINDOW_HEIGHT/2+50, LABEL_WIDTH, LABEL_HEIGHT);
        label.setBackground(Color.RED);
        label.setFont(font);
        this.convertPanel.add(label);
        this.add(this.convertPanel);
        scrapping(kind);
        setScrapping(true);
        backButton.addActionListener((e) -> {
            this.convertPanel.setVisible(false);
            this.convertPanel.removeAll();
            showMainButton();
            setScrapping(false);
        });
        convertButton.addActionListener((e)->{
            try {
                String userInput = textField.getText();
                double num = Double.parseDouble(userInput);
                String pair =(String) this.dataTable.getValueAt(0,1), coin1="",coin2="";
                for (int i=0;i<3;i++){
                    coin1 = coin1 + pair.charAt(i);
                }
                for (int i=4;i<7;i++){
                    coin2 = coin2 + pair.charAt(i);
                }
                label.setText(num + " "+ coin1 +"= " + calculate(num) + " " + coin2);
            }
            catch (NumberFormatException exception) {
                label.setText("Incorrect");
            }
        });
    }

    public void scrapping(int kind) {
        Thread t = new Thread(() -> {
            while (isScrapping) {
                Document website = null;
                try {
                    website = Jsoup.connect("https://m.il.investing.com/currencies/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (kind) {
                    case EUR_USD: {
                        Element eurUsd = website.getElementById("pair_1");
                        System.out.println(eurUsd.text());
                        addTable(eurUsd);
                        break;
                    }
                    case GBP_USD: {
                        Element gbpUsd = website.getElementById("pair_2");
                        System.out.println(gbpUsd.text());
                        addTable(gbpUsd);
                        break;
                    }
                    case USD_JPY: {
                        Element usdJpy = website.getElementById("pair_3");
                        System.out.println(usdJpy.text());
                        addTable(usdJpy);
                        break;
                    }
                    case USD_ILS: {
                        Element usdIls = website.getElementById("pair_63");
                        System.out.println(usdIls.text());
                        addTable(usdIls);
                        break;
                    }
                    case EUR_ILS: {
                        Element eurIls = website.getElementById("pair_64");
                        System.out.println(eurIls.text());
                        addTable(eurIls);
                        break;
                    }
                }
                try {
                    Thread.sleep(REFRESH_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }


    public void addTable (Element coin){
        String [][]  coinToSt = new String[1][coin.childrenSize()];
        int index=0;
        for (int i = 1; i<coin.childrenSize();i++){
            coinToSt[0][index] = coin.child(i).text();
            index++;
        }
        this.dataTable = new JTable(coinToSt,TITLE_TABLE);
        this.dataTable.setGridColor(Color.BLACK);
        String change = (String) this.dataTable.getValueAt(0,INDEX_OF_CHANGE);
        double changeValue = Double.valueOf(change);
        if (changeValue<0){
            this.dataTable.setBackground(Color.RED);
        }
        else {
            this.dataTable.setBackground(Color.GREEN);
        }
        this.dataTable.setBounds(50,WINDOW_HEIGHT/5+100,WINDOW_WIDTH-100,WINDOW_HEIGHT/10);
        this.dataTable.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(this.dataTable);
        pane.setBounds(50,WINDOW_HEIGHT/5+100,WINDOW_WIDTH-100,WINDOW_HEIGHT/10);
        this.convertPanel.add(pane);
    }

    public String calculate (double num){
        String gate = (String) this.dataTable.getValueAt(0,INDEX_OF_GATE);
        double gateValue = Double.valueOf(gate);
        double ans = num*gateValue;
        return String.valueOf(ans);
    }
}
