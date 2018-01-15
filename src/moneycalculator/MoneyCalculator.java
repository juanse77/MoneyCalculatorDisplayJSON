package moneycalculator;

import controlador.Controlador;
import view.Ventana;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
        
        Ventana ventana = new Ventana();
        Controlador control =  new Controlador(ventana);
   
    }
    
}
