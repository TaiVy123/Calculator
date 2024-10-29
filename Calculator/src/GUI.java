/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author taing
 */
public class GUI extends JFrame implements ActionListener {
    private JTextField display;
    private Calculator calculator;
    private String currentOperator;
    private double firstNumber, secondNumber;
    private boolean startNewNumber = true;

    public GUI() {
        calculator = new Calculator();
        setTitle("Calculator");
        setSize(300, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4)); // Adjusted to fit an extra row for the "←" button

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+",
            "←" // Added "←" as the remove/backspace button
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Check if the button pressed is a number
        if (command.matches("[0-9]")) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        }
        // Handle operator buttons (+, -, *, /)
        else if (command.matches("[+\\-*/]")) {
            firstNumber = Double.parseDouble(display.getText());
            currentOperator = command;
            startNewNumber = true;
        }
        // Handle the equal button
        else if (command.equals("=")) {
            secondNumber = Double.parseDouble(display.getText());
            double result = 0;
            switch (currentOperator) {
                case "+":
                    result = calculator.add(firstNumber, secondNumber);
                    break;
                case "-":
                    result = calculator.subtract(firstNumber, secondNumber);
                    break;
                case "*":
                    result = calculator.multiply(firstNumber, secondNumber);
                    break;
                case "/":
                    result = calculator.divide(firstNumber, secondNumber);
                    break;
            }
            display.setText(String.valueOf(result));
            startNewNumber = true;
        }
        // Handle the clear button
        else if (command.equals("C")) {
            display.setText("");
            firstNumber = secondNumber = 0;
            currentOperator = "";
            startNewNumber = true;
        }
        // Handle the remove/backspace button
        else if (command.equals("←")) {
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}

