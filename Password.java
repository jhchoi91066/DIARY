import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Password {
    private static final String PASSWORD = "1111";  // 비밀번호 설정

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("일기장 비밀번호 확인");  // 프레임 제목
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("비밀번호를 입력하세요:");
            JPasswordField passwordField = new JPasswordField(10);  // 비밀번호를 입력받는 JPasswordField 생성  ->
            JButton loginButton = new JButton("확인");  // 확인 버튼 생성

            loginButton.addActionListener(new ActionListener() {  // 확인 버튼이 클릭되었을때의 코드 구현
                @Override
                public void actionPerformed(ActionEvent e) {
                    char[] enteredPasswordChars = passwordField.getPassword();
                    String enteredPassword = new String(enteredPasswordChars);

                    if (checkPassword(enteredPassword)) {
                        JOptionPane.showMessageDialog(frame, "비밀번호가 일치합니다. 일기장에 접속합니다.");

                        // 여기에서 다음 화면으로 이동하는 코드를 추가합니다.
                        openDiaryMainScreen();

                        // 현재 화면은 닫아도 됩니다.
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "비밀번호가 일치하지 않습니다. 다시 입력하세요.");
                    }

                    // 입력된 비밀번호 배열을 지우기
                    passwordField.setText("");
                }
            });

            panel.setLayout(new GridBagLayout());  // GridBagLayout을 사용하여 중앙에 배치
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 0, 0); // 상단에 여백 추가
            panel.add(label, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(10, 0, 0, 0); // 상단에 여백 추가
            panel.add(passwordField, gbc);

            gbc.gridy = 2;
            gbc.insets = new Insets(10, 0, 0, 0); // 상단에 여백 추가
            panel.add(loginButton, gbc);

            frame.getContentPane().add(panel);
            frame.setSize(800, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static boolean checkPassword(String enteredPassword) {
        return PASSWORD.equals(enteredPassword);
    }

    private static void openDiaryMainScreen() {
        // 다음 화면을 나타내는 코드를 추가합니다.
        JFrame mainScreenFrame = new JFrame("일기장 메인 화면");
        // 여기에서 mainScreenFrame에 대한 설정과 컴포넌트를 추가하는 코드를 작성합니다.
        mainScreenFrame.setSize(800, 800);
        mainScreenFrame.setLocationRelativeTo(null);
        mainScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreenFrame.setVisible(true);
    }
}
