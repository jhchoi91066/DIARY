import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            openDiaryMainScreen();
        });
    }

    private static void openDiaryMainScreen() {
        JFrame mainScreenFrame = new JFrame("일기장 메인 화면");
        mainScreenFrame.setSize(800, 800);
        mainScreenFrame.setLocationRelativeTo(null);
        mainScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메뉴 바 생성
        JMenuBar menuBar = new JMenuBar();
        mainScreenFrame.setJMenuBar(menuBar);

        // 메뉴 생성
        JMenu searchMenu = new JMenu("검색");

        // 메뉴 아이템 생성
        JMenuItem keywordSearchItem = new JMenuItem("키워드 검색");
        keywordSearchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 키워드 검색 창 열기
                String keyword = JOptionPane.showInputDialog(mainScreenFrame, "키워드를 입력하세요:");
                if (keyword != null) {
                    // 키워드를 사용하여 검색하는 기능을 추가하세요
                    JOptionPane.showMessageDialog(mainScreenFrame, "키워드 '" + keyword + "'로 검색을 수행합니다.");
                }
            }
        });

        JMenuItem dateSearchItem = new JMenuItem("날짜 검색");
        dateSearchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 날짜 검색 창 열기
                String date = JOptionPane.showInputDialog(mainScreenFrame, "날짜를 입력하세요 (YYYY-MM-DD 형식):");
                if (date != null) {
                    // 날짜를 사용하여 검색하는 기능을 추가하세요
                    JOptionPane.showMessageDialog(mainScreenFrame, "날짜 '" + date + "'로 검색을 수행합니다.");
                }
            }
        });

        // 메뉴 아이템을 메뉴에 추가
        searchMenu.add(keywordSearchItem);
        searchMenu.add(dateSearchItem);

        // 메뉴를 메뉴 바에 추가
        menuBar.add(searchMenu);

        mainScreenFrame.setVisible(true);
    }
}
