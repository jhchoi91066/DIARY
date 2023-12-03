import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class login extends JFrame {
    private JTextField passwordField;
    private String storedPassword;
    private boolean isPasswordSet = false;

    public login() {
        setTitle("Diary");  // jframe 의 제목을 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 누르면 코드 종료
        setSize(600, 600); // jframe 의 크기를 설정합니다.
        setLocationRelativeTo(null); // jframe을 중앙에 배치합니다

        JPanel mainPanel = new JPanel(); // Renamed the panel variable to mainPanel
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("My Diary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100)); // 글자의 크기 폰트 굵기를 정합니다.
        mainPanel.add(titleLabel, BorderLayout.NORTH); // 메인패널을 추가하는 코드


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel passwordLabel = new JLabel("비밀번호 입력",SwingConstants.CENTER);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("로그인");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = passwordField.getText();
                if (!isPasswordSet) {
                    // 처음 비밀번호 설정
                    storedPassword = enteredPassword;
                    isPasswordSet = true;
                    JOptionPane.showMessageDialog(null, "비밀번호가 성공적으로 설정되었습니다!");
                    clearPasswordField();
                } else {
                    // 로그인 확인
                    if (isCorrectPassword(enteredPassword)) {
                        openDiary();
                    } else {
                        JOptionPane.showMessageDialog(null, "잘못된 비밀번호입니다. 접근이 거부되었습니다.");
                        clearPasswordField();
                    }
                }
            }
        });
        panel.add(loginButton);
        mainPanel.add(panel, BorderLayout.CENTER); // Add the panel to mainPanel

        add(mainPanel);
        setVisible(true);
    }

    private boolean isCorrectPassword(String enteredPassword) {
        return enteredPassword.equals(storedPassword);
    }

    private void openDiary() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 로그인 성공 시 다이어리 클래스 열기
                new Diary();
            }
        });
        dispose(); // 로그인 창 닫기
    }

    private void clearPasswordField() {
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new login();
            }
        });
    }
}
