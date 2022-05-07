//todo - data, main, convert,window,collorbutton

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenu extends JPanel {

    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    boolean isScrapping;
    JPanel convert;
    JTable table;
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 500;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;
    public static final int LABEL_WIDTH = 200;
    public static final int LABEL_HEIGHT = 50;
    public static final int REFRESH_TIME = 1000;
    public static final int EUR_USD = 1;
    public static final int GBP_USD = 2;
    public static final int USD_JPY = 3;
    public static final int USD_ILS = 4;
    public static final int EUR_ILS = 5;
    public static final String []  TITEL_TABLE = {"Name","Pair","Last","High","Low","Change","Change%","Time"};


    public MainMenu() {
        this.setLayout(null);
        this.setBounds(0, 0, 700, 700);
        this.setBackground(Color.CYAN);
        ImageIcon background = new ImageIcon("background.jpg");
        JLabel backGround = new JLabel(background);
        backGround.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        this.add(backGround);
        mainMenu();
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
            this.button1.setVisible(false);
            this.button2.setVisible(false);
            this.button3.setVisible(false);
            this.button4.setVisible(false);
            this.button5.setVisible(false);
            convertMenu(EUR_USD);

        });
        button2.addActionListener((e) -> {
            this.button1.setVisible(false);
            this.button2.setVisible(false);
            this.button3.setVisible(false);
            this.button4.setVisible(false);
            this.button5.setVisible(false);
            convertMenu(GBP_USD);

        });
        button3.addActionListener((e) -> {
            this.button1.setVisible(false);
            this.button2.setVisible(false);
            this.button3.setVisible(false);
            this.button4.setVisible(false);
            this.button5.setVisible(false);
            convertMenu(USD_JPY);

        });
        button4.addActionListener((e) -> {
            this.button1.setVisible(false);
            this.button2.setVisible(false);
            this.button3.setVisible(false);
            this.button4.setVisible(false);
            this.button5.setVisible(false);
            convertMenu(USD_ILS);
        });
        button5.addActionListener((e) -> {
            this.button1.setVisible(false);
            this.button2.setVisible(false);
            this.button3.setVisible(false);
            this.button4.setVisible(false);
            this.button5.setVisible(false);
            convertMenu(EUR_ILS);
        });
    }

    public void convertMenu(int kind) {
        this.convert = new JPanel();
        this.convert.setLayout(null);
        this.convert.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.convert.setBackground(Color.GRAY);
        JButton backButton = new JButton("Back");
        backButton.setBounds(WINDOW_WIDTH / 7, WINDOW_HEIGHT / 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        JButton convertButton = new JButton("convert");
        convertButton.setBounds(WINDOW_WIDTH / 70, WINDOW_HEIGHT / 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.convert.add(backButton);
        this.convert.add(convertButton);
        Font font = new Font("Arial", Font.BOLD, 20);
        JTextField textField = new JTextField();
        textField.setBounds(WINDOW_WIDTH/2-200, WINDOW_HEIGHT/2, LABEL_WIDTH*2, LABEL_HEIGHT);
        textField.setBackground(Color.GRAY);
        textField.setText("Enter number and press convert");
        textField.setFont(font);
        this.convert.add(textField);
        JLabel label = new JLabel("------");
        label.setBounds(WINDOW_WIDTH/2-100, WINDOW_HEIGHT/2+50, LABEL_WIDTH, LABEL_HEIGHT);
        label.setBackground(Color.RED);
        label.setFont(font);
        this.convert.add(label);
        this.add(this.convert);
        scrapping(kind);
        this.isScrapping = true;
        backButton.addActionListener((e) -> {
            this.convert.setVisible(false);
            this.convert.removeAll();
            this.button1.setVisible(true);
            this.button2.setVisible(true);
            this.button3.setVisible(true);
            this.button4.setVisible(true);
            this.button5.setVisible(true);
            this.isScrapping = false;
        });
        convertButton.addActionListener((e)->{
            try {
                String userInput = textField.getText();
                double num = Double.parseDouble(userInput);
                label.setText(calculate(num));
            }
            catch (NumberFormatException exception) {
                label.setText("Incorrect");
            }
        });
    }

    public void scrapping(int kind) {
        try {
            Document website = Jsoup.connect("https://m.il.investing.com/currencies/").get();
            Thread t = new Thread(() -> {
                while (this.isScrapping) {
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

        } catch (IOException e) {
        }
    }

    public void addTable (Element coin){
        String [][]  coinToSt = new String[1][coin.childrenSize()];
        int index=0;
        for (int i = 1; i<coin.childrenSize();i++){
            coinToSt[0][index] = coin.child(i).text();
            index++;
        }
        System.out.println(coinToSt.toString());
        this.table = new JTable(coinToSt,TITEL_TABLE);
        this.table.setBackground(Color.WHITE);
        this.table.setBounds(100,200,500,50);
        this.table.setGridColor(Color.BLACK);
        this.table.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(this.table);
        pane.setBounds(100,200,500,50);
        this.convert.add(pane);
    }

    public String calculate (double num){
        String gate = (String) this.table.getValueAt(0,2);
        double gateValue = Double.valueOf(gate);
        double ans = num*gateValue;
        return String.valueOf(ans);
    }
}
