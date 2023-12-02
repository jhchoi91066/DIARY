import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;

public class Diary extends JFrame {

    String[] dayAr = {"Sun", "Mon", "Tue", "Wen", "Thur", "Fri", "Sat"};
    DateBox[] dateBoxAr = new DateBox[dayAr.length * 6];
    JPanel p_north;
    JButton bt_prev;
    JLabel lb_title;
    JButton bt_next;
    JPanel p_center; // 날짜 박스 처리할 영역
    Calendar cal; // 날짜 객체

    int yy; // 기준점이 되는 년도
    int mm; // 기준점이 되는 월
    int startDay; // 월의 시작 요일
    int lastDate; // 월의 마지막 날

    // 생성자
    public Diary() {

        setTitle("Daily Private");
        // 디자인
        p_north = new JPanel();
        bt_prev = new JButton("이전");
        lb_title = new JLabel("년도 올 예정", SwingConstants.CENTER);
        bt_next = new JButton("다음");
        p_center = new JPanel();

        // 라벨에 폰트 설정
        lb_title.setFont(new Font("Arial-Black", Font.BOLD, 25));
        lb_title.setPreferredSize(new Dimension(300, 30));

        // 감정 표현 아이콘 추가
        JPanel emotionPanel = new JPanel();
        String[] emotionImagePaths = {
                "/바탕화면/학교/객체지향프로그래밍/emotion/angry.png",
                "/바탕화면/학교/객체지향프로그래밍/emotion/fear.png",
                "/바탕화면/학교/객체지향프로그래밍/emotion/frustrated.png",
                "/바탕화면/학교/객체지향프로그래밍/emotion/joy.png",
                "/바탕화면/학교/객체지향프로그래밍/emotion/sad.png"
        };

        for (String imagePath : emotionImagePaths) {
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel label = new JLabel(icon);
            emotionPanel.add(label);
        }

        p_north.add(emotionPanel);
        p_north.add(bt_prev);
        p_north.add(lb_title);
        p_north.add(bt_next);
        add(p_north, BorderLayout.NORTH);
        add(p_center);

        // 이전 버튼을 눌렀을 때 전 월로 이동해야함
        bt_prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMonth(-1);
            }
        });

        // 다음 버튼을 눌렀을 때 다음 달로 이동해야함
        bt_next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMonth(1);
            }
        });

        // 메뉴 바 추가 (2번 기능)
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("***");
        JMenuItem alarmItem = new JMenuItem("알람 설정");
        JMenuItem searchItem = new JMenuItem("키워드 검색");
        JMenuItem darkModeItem = new JMenuItem("다크 모드 전환");

        // 알람, 키워드 검색, 다크 모드 전환 기능 추가 (3번 기능)
        alarmItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setAlarm();
            }

            private void setAlarm() {
            }
        });

        searchItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchByKeyword();
            }

            private void searchByKeyword() {
            }
        });

        darkModeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchToDarkMode();
            }

            private void switchToDarkMode() {
            }
        });

        menu.add(alarmItem);
        menu.add(searchItem);
        menu.add(darkModeItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);

        getCurrentDate(); // 현재 날짜 객체 생성
        getDateInfo(); // 날짜 객체로부터 정보들 구하기
        setDateTitle(); // 타이틀 라벨에 날짜 표시하기
        createDay(); // 요일 박스 생성
        createDate(); // 날짜 박스 생성
        printDate(); // 상자에 날짜 그리기

        setVisible(true);
        setBounds(100, 100, 780, 780);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //현재날짜 객체 만들기
    public void getCurrentDate() {
        cal = Calendar.getInstance();
    }

    //시작 요일, 끝 날 등 구하기
    public void getDateInfo() {
        yy = cal.get(Calendar.YEAR);
        mm = cal.get(Calendar.MONTH);
        startDay = getFirstDayOfMonth(yy, mm);
        lastDate = getLastDate(yy, mm);
    }

    //요일 생성
    public void createDay() {
        for(int i = 0; i < 7; i++){
            DateBox dayBox = new DateBox(dayAr[i], Color.gray, 100, 70);
            p_center.add(dayBox);
        }
    }

    //날짜 생성
    public void createDate() {
        for(int i = 0; i < dayAr.length*6; i++) {
            DateBox dateBox = new DateBox("", Color.LIGHT_GRAY, 100, 100);
            p_center.add(dateBox);
            dateBoxAr[i] = dateBox;
        }
    }

    //해당 월의 시작 요일 구하기
    //개발 원리 : 날짜 객체를 해당 월의 1일로 조작한 후, 요일 구하기
    //사용 방법 : 2021년 2월을 구할시 2021, 1을 넣으면 됨
    public int getFirstDayOfMonth(int yy, int mm) {
        Calendar cal = Calendar.getInstance(); //날짜 객체 생성
        cal.set(yy, mm, 1);
        return cal.get(Calendar.DAY_OF_WEEK)-1;//요일은 1부터 시작으로 배열과 쌍을 맞추기 위해 -1
    }

    //사용 방법 : 2021년 2월을 구할시 2021, 1을 넣으면 됨
    public int getLastDate(int yy, int mm) {
        Calendar cal = Calendar.getInstance();
        cal.set(yy, mm+1, 0);
        //마지막 날을 의미한다.
        return cal.get(Calendar.DATE);
    }

    //날짜 박스에 날짜 출력하기
    public void printDate() {
        System.out.println("시작 요일"+startDay);
        System.out.println("마지막 일"+lastDate);

        int n = 1;
        for(int i = 0; i < dateBoxAr.length; i++) {
            if(i >= startDay && n <= lastDate) {
                dateBoxAr[i].day = Integer.toString(n);
                dateBoxAr[i].repaint();
                n++;
            }else {
                dateBoxAr[i].day = "";
                dateBoxAr[i].repaint();
            }
        }
    }

    //달력을 넘기거나 전으로 이동할 때 날짜 객체에 대한 정보도 변경
    public void updateMonth(int data) {
        //캘린더 객체에 들어있는 날짜를 기준으로 월 정보를 바꿔준다.
        cal.set(Calendar.MONTH, mm+data);
        getDateInfo();
        printDate();
        setDateTitle();
    }

    //몇년도 몇월인지를 보여주는 타이틀 라벨의 값을 변경
    public void setDateTitle() {
        lb_title.setText(yy+"-"+StringManager.getZeroString(mm+1));
        lb_title.updateUI();
    }

    public static void main(String[] args) {
        new Diary();
    }
}
