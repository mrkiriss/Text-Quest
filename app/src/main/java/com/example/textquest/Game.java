package com.example.textquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Game extends AppCompatActivity {

    public static Player player = new Player();
    public static Story story = new Story();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // загрузка картинок в кнопки
        Button b= findViewById(R.id.button_mood);
        b.setBackground(getDrawable(R.drawable.mood));

        b= (Button) findViewById(R.id.button_money);
        b.setBackground(getDrawable(R.drawable.money));

        b= (Button) findViewById(R.id.button_knowledge);
        b.setBackground(getDrawable(R.drawable.knowledge));

        // начальное заполнение значений progressbar
        ProgressBar p = (ProgressBar) findViewById(R.id.progress_knowledge);
        p.setProgress(player.knowledge);

        p = (ProgressBar) findViewById(R.id.progress_money);
        p.setProgress(player.money);

        p = (ProgressBar) findViewById(R.id.progress_mood);
        p.setProgress(player.mood);

//        TextView t =(TextView) findViewById(R.id.text_story);
//        t.setBackground(getDrawable(R.drawable.up));

        Player.name="mrkiriss";
        refreshScreen();
    }

    // обрабатывает нажатие на кнопки выбора ситуации, вызывает функцию обновления экрана
    public void onClickArrow(View view){

        switch(view.getId()){
            case R.id.buttonLeftEvent:
                story.changeSituation(0);
                break;
            case R.id.buttonRightEvent:
                story.changeSituation(1);
                break;
        }
        player.changeValues(story.currentSituation.dMood,story.currentSituation.dMoney,story.currentSituation.dKnowledge);
        refreshScreen();

        // обработка окончания игры
        String gameCondition = story.isEnd(player.mood,player.money,player.knowledge);
        if (gameCondition!=""){
            changeEnable(false);
            Toast.makeText(this,"Game Over. You "+gameCondition,Toast.LENGTH_SHORT).show();
        }
    }
    // обновляет компоненты экрана: состояние характеристик, историю, картинку, превью кнопок
    public void refreshScreen(){
        ProgressBar p = (ProgressBar) findViewById(R.id.progress_knowledge);
        p.setProgress(player.knowledge);

        p = (ProgressBar) findViewById(R.id.progress_money);
        p.setProgress(player.money);

        p = (ProgressBar) findViewById(R.id.progress_mood);
        p.setProgress(player.mood);

        Button b = (Button) findViewById(R.id.buttonLeftEvent);
        b.setText(story.currentSituation.preview1);

        b = (Button) findViewById(R.id.buttonRightEvent);
        b.setText(story.currentSituation.preview2);

        TextView t= (TextView) findViewById(R.id.text_story);
        t.setText(story.currentSituation.text);

        t= (TextView) findViewById(R.id.text_login);
        t.setText("Login: "+Player.name);
    }

    // перезапускает игру
    public void resetGame(View view){
        player = new Player();
        story = new Story();
        refreshScreen();
        changeEnable(true);
    }

    // меняет состояние кнопок смены ситуации на заданное
    void changeEnable(boolean bl){
        Button b= (Button) findViewById(R.id.buttonRightEvent);
        b.setEnabled(bl);

        b= (Button) findViewById(R.id.buttonLeftEvent);
        b.setEnabled(bl);
    }

    // вызывает Activity запроса логина нового пользователя
    public void startLogin(View v){
        Intent i = new Intent(this,Login.class);
        startActivityForResult(i,0);
    }

    // получение результата ввода логина
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            Toast.makeText(this,"Логин не был получен",Toast.LENGTH_SHORT).show();
        }else{
            Player.name = data.getStringExtra("login");
            Toast.makeText(this,"Логин успешно изменён",Toast.LENGTH_SHORT).show();
            refreshScreen();
        }
    }
}

class Player{
    public int mood, money, knowledge;
    public static String name;

    Player(){
        this.mood=50;
        this.money=50;
        this.knowledge=50;
    }

    void changeValues(int dMood, int dMoney, int dKnowledge){
        this.mood+=dMood;
        this.money+=dMoney;
        this.knowledge+=dKnowledge;
    }
}

class Situation{
    int dMood, dMoney, dKnowledge;
    Situation[] directions;
    String preview1, preview2, text;

    Situation(String preview1,String preview2, String text, int choices, int dMood, int dMoney, int dKnowledge){
        this.preview1=preview1;
        this.preview2=preview2;
        this.text=text;
        this.dMood=dMood;
        this.dMoney=dMoney;
        this.dKnowledge=dKnowledge;
        directions=new Situation[choices];
    }
}

class Story{
    public Situation currentSituation;
    Situation firstSituation;
    //public String way;
    Story(){
        // начальная ситуация
        firstSituation= new Situation(
                "Взять пистолет и...",
                "Пойти умываться",
                "Утро. Вы просыпаетесь, встаёте с кровати.\n"
                + " На тумбочке вы видите красный пистолет и мыльно-рыльные принадлежности.\n"
                + "Это тяжёлый выбор...",
                2,0,0,0
        );
        // возможные ситуации после первого выбора
        firstSituation.directions[0]=new Situation(
                "Начать готовится к сессии",
                "Немножко поиграть...",
                "Вы берёте мощнейщий игрушечный пистолет и стреляете в своего спящего соседа.\n"
                        + " От громкого выстрела сосед подскакивает с кровати, и, не теряя ни секунды, награждает вас сочным подзатыльником\n"
                        + " Вы, довольный своим выбором: вы поднимаете себе настроение на весь день, а также находите монетку, выпавшую из вашего кармана\n"
                        + " Садясь за своё рабочее место, вы решаете...",
                2,+45,+5, 0
        );
        firstSituation.directions[1]=new Situation(
                "Согласиться на прогулку",
                "Отказаться и вернуться спать",
                "После водных процедур вы полностью просыпаетесь и чувствуете себя прекрасно."
                        + "Идя в свою комнату, вы сталкиваетесь с соседом, который предлогает вам прогуляться.",
                2,+25,0,+0
        );
        // возможные ситуации после второго выбора
        firstSituation.directions[0].directions[0]=new Situation(
                "---",
                "---",
                "Постепенно вы осознаёте, что ничего не знаете.\n"
                        + "Вы впадаете в отчаяние, а ваша история подходит к концу.",
                0,-99,-99,-99
        );
        firstSituation.directions[0].directions[1]=new Situation(
                "---",
                "---",
                "Вы играете, играете, а потом ещё играете.\n"
                        + "Случайно вы выиграваете парочку турниров, обеспечивая тем самым себе и своим детям, внукам и правнукам безбудную жизнь.",
                0,+100,+100,-49
        );
        firstSituation.directions[1].directions[0]=new Situation(
                "---",
                "---",
                "В время своей прогуки вы встречаете загадочного мужчину, который за нескромную плату отдаёт вам ответы на грядущую сессию.\n"
                        + "Вы сливаете эти ответы своим одногруппникам и всю зиму отмечаете успешную здачу сессии.",
                0,+100,-49,-49
        );
        firstSituation.directions[1].directions[1]=new Situation(
                "---",
                "---",
                "Проспав целый день, вы обрекаете себя на хорошее настроение.\n"
                        + "Однако ваш сосед находит на тумбочке красный пистолет, которым и будит вас.\n"
                        + " Ваше настроение стремительно падает, а рутинный день только начинается...",
                0,-99,0,0
        );
        currentSituation=firstSituation;
    }

    // проверяет, закончилась ли игра по состоянию харакеристик, количеству возможных ситаций на след. ходе
    public String isEnd(int mood, int money, int knowledge) {
        if (mood<1 || money<1 || knowledge<1) return "lost";
        if (mood>=100 || money>=100 || knowledge>=100) return "won";
        return "";
    }
    // продвигает вперёд по истории в соответствии с кодом выбранной ситуации
    public void changeSituation(int id){
        //way+=String.valueOf(id);
        currentSituation=currentSituation.directions[id];
    }
}
