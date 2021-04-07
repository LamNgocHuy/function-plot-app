package sample;

import java.util.ArrayList;
import java.util.List;

public class Handle {
    static List<Double> heso;
    static List<Double> mu;
    static List<Integer> vitriplus;
    static List<String> elements;
    static List<Double> xAxis;
    // Ham xoa khoang trang

    public Handle() {
    }

    static String squeeze(String var, String kitu)
    {
        String kq = "";
        String[] words = var.split(kitu);
        for (String w : words){
            kq+=w;
        }
        return kq;
    }
    // Ham tao danh sach vitriplus
    static void setVitriplus(String var){
        vitriplus = new ArrayList<Integer>();
        vitriplus.add(0);
        for(int i = 0 ; i < var.length(); i++){
            String charAti = String.valueOf(var.charAt(i));
            if (charAti.equals("+")){
                vitriplus.add(i);
            }
        }
        vitriplus.add(var.length()-1);
    }
    // Ham tao danh sach heso va danh sach mu
    // aaax^bbb
    static void setHesovaMu(){
        heso = new ArrayList<Double>();
        mu = new ArrayList<>();
        for (String e : elements){
            if(e.indexOf("x")==-1&&e.indexOf("^")==-1&&e.indexOf("+")==-1){
                heso.add(Double.parseDouble(e));
                mu.add(0.0);
            }
            else if(e.indexOf("^")==-1&&e.indexOf("x")>-1)
            {
                if(e.equals("x")){
                    heso.add(1.0);
                    mu.add(1.0);
                }else if(e.equals("-x"))
                {
                    heso.add(-1.0);
                    mu.add(1.0);
                }
                else{
                    double hs;
                    try {
                        hs = Double.parseDouble(e.substring(0,e.indexOf("x")));
                    }catch (Exception ex){
                        hs = 0.0;
                    }
                    heso.add(hs);
                    mu.add(1.0);
                }
            }
            else
                {
                    double hs;
                    if(e.indexOf("x")==0){
                        hs = 1.0;
                    }else if(e.indexOf("-x")==0)
                    {
                        hs = -1.0;
                    }
                    else{
                        hs = Double.parseDouble(e.substring(0,e.indexOf("x")));
                    }
                    heso.add(hs);
                    double m = Double.parseDouble(e.substring(e.indexOf("^")+1,e.length()));
                    mu.add(m);
            }
        }
    }
    // Ham chia thanh phan
    static void setElements(List<Integer> vitriplus, String fn){
        elements = new ArrayList<>();
        if(vitriplus.size()==2){
            elements.add(fn.substring(0,vitriplus.get(1)+1));
        }else{
            for (int i=0; i < vitriplus.size()-1; i++){
                if(i==0){
                    elements.add(fn.substring(vitriplus.get(i),vitriplus.get(i+1)));
                }else if(i==vitriplus.size()-2)
                {
                    elements.add(fn.substring(vitriplus.get(i)+1,vitriplus.get(i+1)+1));
                }
                else{
                    elements.add(fn.substring(vitriplus.get(i)+1,vitriplus.get(i+1)));
                }
            }
        }
//        System.out.println(elements);
    }
    // Tinh gia tri ham da thuc
    static List<Double> calFunc(List<Double> heso, List<Double> mu){
        List<Double> y_value = new ArrayList<>();

        for(double x : xAxis){
            double y = 0;
            for(int i=0; i<heso.size(); i++){
                y += heso.get(i)*(Math.pow(x,mu.get(i)));
            }
            y_value.add(y);
        }
        return y_value;
    }
    // Tinh gia tri fn cua ham luong giac
    static List<Double> calFnOfLGFunc(List<Double> heso, List<Double> mu, String lg, double hslg){
        List<Double> yAxis_lg = new ArrayList<>();

        for(double x : xAxis){
            double y = 0;
            for(int i=0; i<heso.size(); i++){
                y += heso.get(i)*(Math.pow(x,mu.get(i)));
            }
            if(lg.equals("sin")){
                yAxis_lg.add(hslg*Math.sin(y));
            }
            if(lg.equals("cos")){
                yAxis_lg.add(hslg*Math.cos(y));
            }
            if(lg.equals("tan")){
                yAxis_lg.add(hslg*Math.tan(y));
            }
        }
        return yAxis_lg;
    }
    // Tinh ham so LG (asin(fn) or cos or tan)
    static List<Double> calLGFunction(String fn, String type, double xMin, double xMax){
        List<Double> yAxis_lg = new ArrayList<>();
        double hs = sethesolg(fn,type);
        fn = fn.substring(fn.indexOf(type)+4,fn.length()-1);
        settingFunc(fn,xMin,xMax);
        yAxis_lg = calFnOfLGFunc(heso,mu,type,hs);
        return yAxis_lg;
    }
    static void settingFunc(String fn, double xMin, double xMax){
        // set vi tri
        setVitriplus(fn);
        // set thanh phan
        setElements(vitriplus,fn);
        // set he so
        setHesovaMu();
        // set xAxis
        xAxis = new ArrayList<>();
        for(double x=xMin; x<=xMax; x+=0.01){
            xAxis.add(x);
        }
    }
    // set he so ham luong giac asin(fx)-->heso=a
    static double sethesolg(String fn,String type){
        if(fn.indexOf("-"+type)>-1){
            return -1.0;
        }else if(fn.indexOf("(")<fn.indexOf(type)){
            String str = fn.substring(fn.indexOf("(")+1,fn.indexOf(type)-1);
            double tuso = Integer.parseInt(str.substring(0,str.indexOf("/")));
            double mauso = Integer.parseInt(str.substring(str.indexOf("/")+1,str.length()));
            return tuso/mauso;
        }else{
            if(fn.indexOf(type)>0) {return Double.parseDouble(fn.substring(0,fn.indexOf(type)));}
            else {return 1;}
        }
    }
    static List<Double> sum_List(List<Double> fn1, List<Double> fn2, double xMin, double xMax){
                int a = 0;
                List<Double> y_value = new ArrayList<>();
                for(double i = xMin; i<=xMax; i+=0.01){
                    double y1 = fn1.get(a);
                    double y2 = fn2.get(a);
                    y_value.add(y1+y2);
                    a++;
                }
        return y_value;
    }
    static List<Double> cal_PT(String fn,Double xMin, Double xMax){
        List<Double> y = new ArrayList<>();
        List<Double> y1 = new ArrayList<>();
        List<Double> y2 = new ArrayList<>();
        y1 = cal_da_thuc(fn.substring(0,fn.indexOf("|")),xMin,xMax);
        y2 = cal_da_thuc(fn.substring(fn.indexOf("|")+1,fn.length()),xMin,xMax);
        int a = 0;
        for(double i = xMin; i<=xMax; i+=0.01){
            double j = ((double)y1.get(a))/y2.get(a);
            y.add(j);
            a++;
        }
        return y;
    }
//     ham tinh tong da thuc
    public static List<Double> cal_da_thuc(String fn, double xMin, double xMax) {
        // Cat khoang trang
        fn = squeeze(fn," ");
        if(fn.indexOf("sin")==-1&&fn.indexOf("cos")==-1&&fn.indexOf("tan")==-1&&fn.indexOf("&")==-1&&fn.indexOf("|")==-1){
            // Cai dat ham so
            settingFunc(fn,xMin,xMax);
            // Tinh da thuc
            return calFunc(heso,mu);
        }
        else{
            List<Double> y_value = new ArrayList<Double>();
            // tinh gia tri ham luong giac
            if(fn.indexOf("sin")>-1&&fn.indexOf("cos")==-1&&fn.indexOf("tan")==-1&&fn.indexOf("&")==-1&&fn.indexOf("|")==-1){
                return calLGFunction(fn,"sin",xMin,xMax);
            }
            if(fn.indexOf("cos")>-1&&fn.indexOf("sin")==-1&&fn.indexOf("tan")==-1&&fn.indexOf("&")==-1&fn.indexOf("|")==-1){
                return calLGFunction(fn,"cos",xMin,xMax);
            }
            if(fn.indexOf("tan")>-1&&fn.indexOf("sin")==-1&&fn.indexOf("cos")==-1&&fn.indexOf("&")==-1&fn.indexOf("|")==-1){
                return calLGFunction(fn,"tan",xMin,xMax);
            }
            if (fn.indexOf("&")>-1&&fn.indexOf("|")==-1){
                List<Integer> vitriAnd = new ArrayList<>();
                vitriAnd.add(0);
                for(int i = 0 ; i < fn.length(); i++){
                    String charAti = String.valueOf(fn.charAt(i));
                    if (charAti.equals("&")){
                        vitriAnd.add(i);
                    }
                }
                vitriAnd.add(fn.length());
                y_value = cal_da_thuc(fn.substring(0,vitriAnd.get(1)),xMin,xMax);
                for (int i=1; i<vitriAnd.size()-1; i++){
                    y_value = sum_List(y_value,cal_da_thuc(fn.substring(vitriAnd.get(i)+1,vitriAnd.get(i+1)),xMin,xMax),xMin,xMax);
                }
                return y_value;
            }
            if (fn.indexOf("&")>-1&&fn.indexOf("|")>-1){
                List<Integer> vitriAnd = new ArrayList<>();
                vitriAnd.add(0);
                for(int i = 0 ; i < fn.length(); i++){
                    String charAti = String.valueOf(fn.charAt(i));
                    if (charAti.equals("&")){
                        vitriAnd.add(i);
                    }
                }
                vitriAnd.add(fn.length());
                y_value = cal_da_thuc(fn.substring(0,vitriAnd.get(1)),xMin,xMax);
                for (int i=1; i<vitriAnd.size()-1; i++){
                    if((fn.substring(vitriAnd.get(i)+1,vitriAnd.get(i+1)).indexOf("|")>-1))
                    {
                        y_value = sum_List(y_value,cal_PT(fn.substring(vitriAnd.get(i)+1,vitriAnd.get(i+1)),xMin,xMax),xMin,xMax);
                    }else{
                        y_value = sum_List(y_value,cal_da_thuc(fn.substring(vitriAnd.get(i)+1,vitriAnd.get(i+1)),xMin,xMax),xMin,xMax);
                    }
                }
                return y_value;
            }
            if (fn.indexOf("&")==-1&&fn.indexOf("|")>-1){return cal_PT(fn,xMin,xMax);}
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(cal_da_thuc("x|x+1",-1,1));

    }
}
