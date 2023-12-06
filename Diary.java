import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Calendar;
import javax.swing.*;

import java.util.HashMap;
import java.util.Map;


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

    // 일기 데이터를 저장할 Map
    private Map<String, String> diaryData = new HashMap<>();

    // 추가된 부분: 감정 선택을 위한 패널 초기화
    JPanel emotionSelectionPanel;
    String selectedEmotion = "";



    // 다크모드 설정 코드 
    private boolean isDarkMode = false; // 일반모드가 기본상태로 지정
   
   
   
   
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
        lb_title.setFont(new Font("Arial-Black", Font.BOLD,25));
        lb_title.setPreferredSize(new Dimension(300, 30));


        // 감정 표현 아이콘 추가
        JPanel emotionPanel = new JPanel();
        String[] emotionImagePaths = {
                "emotion/angry.png",
                "emotion/fear.png",
                "emotion/frustrated.png",
                "emotion/joy.png",
                "emotion/sad.png"
        };



        for (String imagePath : emotionImagePaths) {
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel label = new JLabel(icon);
            // 감정 아이콘 클릭 이벤트 추가
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 클릭한 감정을 저장
                    selectedEmotion = imagePath;

                    // 선택한 감정을 화면에 표시하는 부분 (예를 들어, 경고 창)
                    JOptionPane.showMessageDialog(null, "선택한 감정: " + selectedEmotion);
                }
            });
            // 추가된 부분: 감정 선택을 위한 패널 초기화
            emotionSelectionPanel = new JPanel();


            emotionSelectionPanel.add(label);
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


        menu.add(alarmItem);
        menu.add(searchItem);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        getCurrentDate(); // 현재 날짜 객체 생성
        getDateInfo(); // 날짜 객체로부터 정보들 구하기
        setDateTitle(); // 타이틀 라벨에 날짜 표시하기
        createDay(); // 요일 박스 생성
        createDate(); // 날짜 박스 생성
        printDate(); // 상자에 날짜 그리기

        // 추가된 부분: 파일에서 일기 데이터를 읽어와 메모리에 저장
        readDiaryFromFile();

        setVisible(true);
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        JButton darkModeButton = new JButton("다크 모드");  //다크모드
        darkModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isDarkMode = !isDarkMode; // 다크모드가 아닐 경우
                toggleDarkMode(); // 다크모드로 변경
            }
        });
        p_north.add(darkModeButton); // 다크모드 버튼을 오른쪽 상단에 위치
    }

    public void toggleDarkMode() {
        Color calendarColor;
        Color backgroundColor;
        Color textColor;  // 색 지정

        if (isDarkMode) {
            //다크모드 일때 색깔 지정
            backgroundColor = Color.darkGray; // 배경색
            textColor = Color.WHITE; // 글자색 
        } else {
            // 다크모드가 아닐 때 색깔 지정
            backgroundColor = Color.WHITE;  
            textColor = Color.darkGray;
            calendarColor = Color.WHITE; 
        }

        calendarColor = Color.GRAY; 

    
        p_center.setBackground(backgroundColor);
        // 위에서 설정한 색들을 불러오는 기능

        lb_title.setForeground(textColor);
        lb_title.setBackground(backgroundColor);
        lb_title.setOpaque(true);
        bt_prev.setBackground(backgroundColor);
        bt_prev.setForeground(textColor);
        bt_next.setBackground(backgroundColor);
        bt_next.setForeground(textColor);

        getContentPane().setBackground(backgroundColor);

        for (DateBox dateBox : dateBoxAr) {
            dateBox.setBackground(calendarColor);
            dateBox.color = calendarColor; // Update box color
            dateBox.repaint(); // Repaint the box
        }
        
        repaint();
    }


    // 추가된 부분: 파일에서 일기 데이터를 읽어오는 메서드
    private void readDiaryFromFile() {
        diaryData.clear(); // 기존 데이터를 초기화합니다.
        try (BufferedReader reader = new BufferedReader(new FileReader("diary_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    diaryData.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            DateBox dayBox = new DateBox(dayAr[i], Color.GRAY, 70, 50);
            p_center.add(dayBox);
        }
    }

    //날짜 생성
    public void createDate() {
        for(int i = 0; i < dayAr.length*6; i++) {
            DateBox dateBox = new DateBox("", Color.LIGHT_GRAY, 70, 70);
            p_center.add(dateBox);
            dateBoxAr[i] = dateBox;

            // 날짜 박스에 클릭 이벤트 추가
            dateBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openDiaryWindow(dateBox.day);
                }

                // 추가된 부분: 감정 선택 패널을 날짜 창에 추가하는 메서드
                private void addEmotionSelectionPanel(JFrame diaryWindow) {
                    diaryWindow.add(emotionSelectionPanel, BorderLayout.SOUTH);
                }

                private void openDiaryWindow(String selectedDate) {
                    JFrame diaryWindow = new JFrame("일기 작성 - " + selectedDate);
                    JTextArea diaryTextArea = new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(diaryTextArea);
                    JButton saveButton = new JButton("저장");

                    // 추가된 부분: 감정 선택 패널을 날짜 창에 추가
                    addEmotionSelectionPanel(diaryWindow);

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 여기에 일기 저장 로직을 추가할 수 있습니다.
                            String diaryContent = diaryTextArea.getText();
                            saveDiary(selectedDate, diaryContent,selectedEmotion);

                            // 저장 로직 추가
                            // ...
                            JOptionPane.showMessageDialog(diaryWindow, "일기가 저장되었습니다.");
                            // 추가된 부분: 파일에서 일기 데이터를 읽어와 메모리에 저장
                            readDiaryFromFile();
                            diaryWindow.dispose(); // 윈도우 닫기
                        }
                    });

                    // 일기를 불러와서 TextArea에 표시
                    String savedDiary = diaryData.get(selectedDate);
                    if (savedDiary != null) {
                        diaryTextArea.setText(savedDiary);
                    }

                    diaryWindow.setLayout(new BorderLayout());
                    diaryWindow.add(scrollPane, BorderLayout.CENTER);
                    diaryWindow.add(saveButton, BorderLayout.SOUTH);

                    diaryWindow.setSize(400, 300);
                    diaryWindow.setLocationRelativeTo(null); // 중앙에 표시
                    diaryWindow.setVisible(true);
                }
                // 일기 저장 메서드 추가
                // 일기 저장 메서드 추가
                private void saveDiary(String date, String content, String emotion) {
                    diaryData.put(date, content + ":" + emotion);
                    try (PrintWriter writer = new PrintWriter(new FileWriter("diary_data.txt", true))) {
                        writer.println(date + ":" + content + ":" + emotion);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }



            });
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
