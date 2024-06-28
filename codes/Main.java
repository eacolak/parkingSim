import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        ArrayList<araba> durum = new ArrayList<>();
        Queue<araba> kuyruk = new LinkedList<>();
        otopark otopark = new otopark();
        Random rnd = new Random();
        otopark.olusturYer();
        String girdi;
        int aracsayisi=0;
        int gun=1;

        System.out.println("MERİÇ OTOPARK'A HOŞGELDİNİZ");
        do{
            System.out.println("---------GÜN:"+gun+"---------");
            for (int i = 9; i < 24; i++) {
                System.out.println("---------SAAT:"+i+"---------");
                for(int j=0;j<60;j++){
                    LocalTime zaman = LocalTime.of(i, j);
                    //Thread.sleep(300);//Sunum esnasında kaldırılırsa daha rahat kontrol edilir.
                    for(int g=0;g<otopark.getYerler().size();g++){
                        if(!kuyruk.isEmpty()&&!otopark.getYerler().isEmpty()){
                            System.out.println("----------Saat "+zaman+"---------");
                            durum.add(kuyruk.poll());
                            durum.get(aracsayisi).setYer(otopark.setDoldurYer());
                            durum.get(aracsayisi).setSaatler(zaman);
                            System.out.println("GİRİŞ "+durum.get(aracsayisi));
                            System.out.println("Boş Yerler: "+otopark.getYerler());
                            System.out.println("Kuyruktaki Araç Sayısı: " +kuyruk.size());
                            aracsayisi++;
                            g--;
                        }
                    }
                    int varMi =rnd.nextInt(1,10);
                    if(varMi==1 && i!= otopark.getKapanisSaati()){
                        araba arac = new araba(zaman, "Kuyrukta");
                        if (!otopark.getYerler().isEmpty()) {
                            System.out.println("----------Saat "+zaman+"---------");
                            arac.setYer(otopark.setDoldurYer());
                            durum.add(arac);
                            System.out.println("GİRİŞ "+durum.get(aracsayisi));
                            System.out.println("Boş Yerler: "+otopark.getYerler());
                            aracsayisi++;

                        }else if(i<20 && kuyruk.size()<20){
                            arac.setSaatler(LocalTime.of(0,0));
                            kuyruk.offer(arac);
                            System.out.println("----------Saat " + zaman + "---------\n" +
                                    "BOŞ PARK YERİ YOK GELEN ARAÇ KUYRUĞA EKLENDİ.\n" +
                                    "Kuyruktaki Araç Sayısı: " + kuyruk.size() + "\n" +
                                    "Kuyruğa Giren Son Araç Plakası: " + arac.getPlaka());
                        }
                        else{
                            System.out.println("----------Saat " + zaman + "---------\nOTOPARK ARAÇ ALIMINA KAPALI.");

                        }
                    }
                    if(!durum.isEmpty()){
                        for(int cikisKontrol=0;cikisKontrol<durum.size();cikisKontrol++){
                            if(durum.get(cikisKontrol).getCikisSaati().equals(zaman)){
                                System.out.println("----------Saat "+zaman+"---------");
                                otopark.setBosaltYer(durum.get(cikisKontrol).getYer());
                                switch (durum.get(cikisKontrol).getTip()){
                                    case("Sedan/Hatchback"):otopark.setGelenGunluk(1,0,0,0);
                                        durum.get(cikisKontrol).setOdenen(otopark.odemeAl(durum.get(cikisKontrol).getKalmaSuresi(),otopark.getFiyatlandirma(0),durum.get(cikisKontrol).getOtoYikama()));
                                        break;
                                    case("Suv"):otopark.setGelenGunluk(0,1,0,0);
                                        durum.get(cikisKontrol).setOdenen(otopark.odemeAl(durum.get(cikisKontrol).getKalmaSuresi(),otopark.getFiyatlandirma(1),durum.get(cikisKontrol).getOtoYikama()));
                                        break;
                                    case("Motosiklet"):otopark.setGelenGunluk(0,0,1,0);
                                        durum.get(cikisKontrol).setOdenen(otopark.odemeAl(durum.get(cikisKontrol).getKalmaSuresi(),otopark.getFiyatlandirma(2),durum.get(cikisKontrol).getOtoYikama()));
                                        break;
                                    case("Minivan"):otopark.setGelenGunluk(0,0,0,1);
                                        durum.get(cikisKontrol).setOdenen(otopark.odemeAl(durum.get(cikisKontrol).getKalmaSuresi(),otopark.getFiyatlandirma(3),durum.get(cikisKontrol).getOtoYikama()));
                                        break;
                                }
                                System.out.println("ÇIKIŞ "+durum.remove(cikisKontrol));
                                aracsayisi--;
                                cikisKontrol--;
                                System.out.println("Boş Yerler: "+otopark.getYerler());
                            }
                        }
                    }
                }
                System.out.println("----------------------------------------------------");
                System.out.println(otopark.getYerler());
                for (araba araba : durum) {//Her saat sonu boş yerleri ve otoparktaki araçları görüntüler.
                    System.out.println(araba);
                }
                System.out.println("----------------------------------------------------");
            }
            System.out.println(gun+". Güne Ait Veriler:\n"+"Günlük Sedan/Hatchback Ziyaretçi : "+otopark.getGelenGunlukDizi()[0]+"  || SUV Ziyaretçi : "
                    +otopark.getGelenGunlukDizi()[1]+"  || Motosiklet Ziyaretçi : "+otopark.getGelenGunlukDizi()[2]+"  || Minivan Ziyaretçi : "
                    +otopark.getGelenGunlukDizi()[3]+"\nGÜNLÜK Ziyaretçi Sayısı:"+otopark.getGelenGunluk()+"\nGÜNLÜK Ciro:"+otopark.getGunlukCiro()+" TL  ||  "
                    +"GÜNLÜK Gider: "+otopark.hizmetMaliyeti()+"  ||  GÜNLÜK Kar:"+(otopark.getGunlukCiro()- otopark.hizmetMaliyeti())+" TL");

            otopark.setToplamCiro(otopark.getGunlukCiro());
            otopark.setToplamKar();
            otopark.setGelenToplam();
            otopark.olusturYer();


            System.out.println("Tüm Günlere Ait Veriler: \n"+gun+" Günlük TOPLAM Sedan/Hatchback Ziyaretçi Sayısı: "+otopark.getGelenToplamDizi()[0]+"\n"+gun+" Günlük TOPLAM SUV Ziyaretçi Sayısı: "
                    +otopark.getGelenToplamDizi()[1]+"\n"+gun+" Günlük TOPLAM Motosiklet Ziyaretçi Sayısı: "+otopark.getGelenToplamDizi()[2]+"\n"+gun+" Günlük TOPLAM Minivan Ziyaretçi Sayısı: "
                    +otopark.getGelenToplamDizi()[3]+"\n"+gun+" Günlük TOPLAM Ziyaretçi Sayısı: "+otopark.getGelenToplam()+"\n"+gun+" Günlük TOPLAM Ciro: "+otopark.getToplamCiro()+" TL  ||  "
                    +gun+" Günlük TOPLAM Gider: "+(otopark.hizmetMaliyeti()*gun)+"  ||  "+gun+" Günlük TOPLAM Kar: "+otopark.getToplamKar()+" TL");

            otopark.gunlukSifirla();
            gun++;
            System.out.print("DİĞER GÜNE GEÇMEK İÇİN ENTER'A, ÇIKMAK İÇİN FARKLI BİR TUŞA BASINIZ:");
            girdi= scan.nextLine();


        }while(girdi.isEmpty());

        System.out.println("MERİÇ OTOPARK'A "+gun+" GÜNDE\nTOPLAM Sedan/Hatchback Ziyaretçi Sayısı: "+otopark.getGelenToplamDizi()[0]+"\nTOPLAM SUV Ziyaretçi Sayısı: "
                +otopark.getGelenToplamDizi()[1]+"\nTOPLAM Motosiklet Ziyaretçi Sayısı: "+otopark.getGelenToplamDizi()[2]+"\nGünlük TOPLAM Minivan Ziyaretçi Sayısı: "
                +otopark.getGelenToplamDizi()[3]+"\nTOPLAM Ziyaretçi Sayısı: "+otopark.getGelenToplam()+"\n"+gun+" Günlük TOPLAM Ciro: "+otopark.getToplamCiro()+" TL  ||  "
                +gun+" Günlük TOPLAM Gider: "+(otopark.hizmetMaliyeti()*gun)+"  ||  "+gun+" Günlük TOPLAM Kar: "+otopark.getToplamKar()+" TL\nAraç Başı Ortalama Kar: "+otopark.getToplamKar()/otopark.getGelenToplam()+"\nGünlük Ortalama Kar: "+otopark.getToplamKar()/gun);

    }
}