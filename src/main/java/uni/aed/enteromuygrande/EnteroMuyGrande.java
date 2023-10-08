package uni.aed.enteromuygrande;

public class EnteroMuyGrande {

    private static final char MENOS = '-';
    private Nodo cabeza;
    private byte signo;

    public EnteroMuyGrande() {
        this("0");
    }
    
    public EnteroMuyGrande(long numero){
        this("" + numero);
    }
    
    public EnteroMuyGrande(String numero){
        numero.trim();
        signo = +1;
        if(numero.charAt(0) == MENOS){
            signo = -1;
            numero = numero.substring(1); //remueve el primer caracter
        }
        numero = extraeCerosPrecedentes(numero);        
        if(numero.equals("0"))
            signo = +1; //+0 o -0 se convierte a +0 internamente        
        cabeza = new Nodo(); //usa un nodo cabez ficticio
        Nodo cola = cabeza;
        String digitos;        
        while(!numero.equals("")){
            int loc = Math.max(numero.length() - Nodo.DIGITOS_MAX, 0);
            digitos = numero.substring(loc); //corta los ultimos 3 digitos
            numero = numero.substring(0, loc);
            //si loc == 0, el numero se convierte en ""
            Nodo bloque = new Nodo(digitos);
            cola.siguiente = bloque;
            cola = bloque;
        }
        cabeza = cabeza.siguiente;  //remueve el nodo ficticio
    }
    public String aString(){
        StringBuffer strBuf = new StringBuffer("");
        String formato = "%0" + Nodo.DIGITOS_MAX + "d";
        Nodo p = cabeza;
        while (p.siguiente != null) {            
            strBuf.insert(0, String.format(formato, p.valor));
                                //rellena los ceros precedentes si los digitos estan
                                //en medio del numero
            p = p.siguiente;
        }
        strBuf.insert(0, p.valor); //procesa el nodo mas significativo
                                           //no rellena los 0 precedentes para este nodo
        if(signo < 0)
            strBuf.insert(0, "-");
        return strBuf.toString();
    }
    
    private static String extraeCerosPrecedentes(String str){
        StringBuffer strBuf = new StringBuffer(str);
        int length = strBuf.length();
        for(int i=0; i<length; i++){
            if(strBuf.charAt(0)=='0')
                strBuf.deleteCharAt(0);
        }
        if(strBuf.length() == 0)
            strBuf.append('0');
        return strBuf.toString();
    }
    
        public class Nodo {
        /*Numero de digitos a almacenar en un bloque*/
        private static final short DIGITOS_MAX = 3;
        private short valor; //varia de 0 a RANGO_VALOR - 1
        private Nodo siguiente;
        private Nodo(){
            this("0");
        }
        private Nodo(String str){
            this(Short.parseShort(str));
        }
        private Nodo(short val){
            valor = val;
            siguiente = null;
        }
    }
    
    public static void main(String[] args) {
        EnteroMuyGrande[] eg = new EnteroMuyGrande[7];
        eg[0] = new EnteroMuyGrande(123456789);
        eg[1] = new EnteroMuyGrande(-45);
        eg[2] = new EnteroMuyGrande("123456789012344");
        eg[3] = new EnteroMuyGrande("-0004000000");
        eg[4] = new EnteroMuyGrande(-3458);
        eg[5] = new EnteroMuyGrande(-0000);
        eg[6] = new EnteroMuyGrande();
        
        for(int i = 0; i < eg.length; i++){
            System.out.println(i + ":" + eg[i].aString());
        }        
    }
}
