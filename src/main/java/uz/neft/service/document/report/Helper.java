package uz.neft.service.document.report;

import org.dhatim.fastexcel.Worksheet;

public class Helper {

    public static Worksheet operatingModeWell(Worksheet ws,int h,int w){
        ws.range(0,0,h,w).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();
        for (int i = 1; i <= 32; i++) {
            ws.value(3,i-1,i);
        }
        ws.value(0,0,"№");
        ws.range(0,0,2,0).merge();

        ws.value(0,1,"№ скв");
        ws.range(0,1,2,1).merge();


        ws.value(0,2,"Горизонт");
        ws.range(0,2,2,2).merge();


        ws.value(0,3,"D екс.кол");
        ws.range(0,3,2,3).merge();


        /** Glubina group start **/
        ws.value(0,4,"Глубина m");
        ws.range(0,4,0,9).merge();

        ws.value(1,4,"Искусственный \nзабой");
        ws.range(1,4,2,4).merge();


        ws.value(1,5,"Интервал\n перфарации");
        ws.range(1,5,1,6).merge();
        ws.value(2,5,"верхний");
        ws.value(2,6,"нижний");


        ws.value(1,7,"C");
        ws.range(1,7,2,7).merge();


        ws.value(1,8,"Подвески");
        ws.range(1,8,1,9).merge();
        ws.value(2,8,"d mm");
        ws.value(2,9,"L,m");


        /** Режим эксплуатация скважин за последний 10 дней **/
        ws.value(0,10,"Режим эксплуатация скважин за последний 10 дней ");
        ws.range(0,10,0,20).merge();

        ws.value(1,10,"D sht, mm");
        ws.range(1,10,2,10).merge();

        //Давления group
        ws.value(1,11,"Давления, кгс/см2");
        ws.range(1,11,1,16).merge();

        ws.value(2,11,"Ртр ");
        ws.value(2,12,"Рзтр ");
        ws.value(2,13,"Рстат ");
        ws.value(2,14,"Рзаб ");
        ws.value(2,15,"Рпл ");
        ws.value(2,16,"ΔР ");


        //Дебит
        ws.value(1,17,"Дебит");
        ws.range(1,17,1,18).merge();

        ws.value(2,17,"Газа, тыс.м3/сут");
        ws.value(2,18,"Конденсат т/сут");


        //skorost
        ws.value(1,19,"Устьевая скорость, м/с");
        ws.range(1,19,2,19).merge();

        ws.value(1,20,"Забойная скорость, м/с");
        ws.range(1,20,2,20).merge();







        /** Намечаемый режим эксплуатация скважин **/
        ws.value(0,21,"Намечаемый режим эксплуатация скважин");
        ws.range(0,21,0,31).merge();

        ws.value(1,21,"D sht, mm");
        ws.range(1,21,2,21).merge();

        //Давления group
        ws.value(1,22,"Давления, кгс/см2");
        ws.range(1,22,1,27).merge();

        ws.value(2,22,"Ртр ");
        ws.value(2,23,"Рзтр ");
        ws.value(2,24,"Рстат ");
        ws.value(2,25,"Рзаб ");
        ws.value(2,26,"Рпл ");
        ws.value(2,27,"ΔР ");


        //Дебит
        ws.value(1,28,"Дебит");
        ws.range(1,28,1,29).merge();

        ws.value(2,28,"Газа, тыс.м3/сут");
        ws.value(2,29,"Конденсат т/сут");


        //skorost
        ws.value(1,30,"Устьевая скорость, м/с");
        ws.range(1,30,2,30).merge();

        ws.value(1,31,"Забойная скорость, м/с");
        ws.range(1,31,2,31).merge();
        return ws;
    }

    public static Worksheet operatingStaff(Worksheet ws,int h,int w){
        ws.range(0,0,h,w).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();
//        for (int i = 1; i <= 32; i++) {
//            ws.value(3,i-1,i);
//        }
        ws.value(0,0,"Наименование");
        ws.value(0,1,"В работе");
        ws.value(0,2,"В отпуске");
        ws.value(0,3,"На больничном");
        ws.value(0,4,"Б/С");
        return ws;
    }

    public static Worksheet operatingElectricity(Worksheet ws,int h,int w){
        ws.range(0,0,h,w).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();

        ws.value(0,0,"Наименование");
        ws.value(0,1,"Часовая");
        ws.value(0,2,"Сутки");
        ws.value(0,3,"С начало месяца");
        ws.value(0,4,"С начало года");
        return ws;
    }

    public static Worksheet operatingProduction(Worksheet ws,int h,int w){
        ws.range(0,0,h,w).style().wrapText(true).horizontalAlignment("center").verticalAlignment("center").set();


        ws.value(0,0,"Наименование месторождений");
        ws.range(0,0,1,0).merge();

        ws.value(0,1,"За текущий месяц");
        ws.range(0,1,0,4).merge();
        ws.value(1,1,"План добычи тыс.м3");
        ws.value(1,2,"Факт. добыча тыс.м3");
        ws.value(1,3,"% выполнения");
        ws.value(1,4,"Отставание/Перевыполнение");



        ws.value(0,5,"С начала года");
        ws.range(0,5,0,9).merge();
        ws.value(1,5,"План добычи тыс.м3");
        ws.value(1,6,"Факт. добыча тыс.м3");
        ws.value(1,7,"За аналог. период прошлого года");
        ws.value(1,8,"% выполнения");
        ws.value(1,9,"Отставание/Перевыполнение");

//        ws.value(0,1,"Часовая");
//        ws.value(0,2,"Сутки");
//        ws.value(0,3,"С начало месяца");
//        ws.value(0,4,"С начало года");
        return ws;
    }
}
