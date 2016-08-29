package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Button;

import org.w3c.dom.Text;

public class MainActivity extends Activity{
    SeekBar seekBar1;
    TextView dias;
    TextView cantidad;
    TextView resultado;
    Button boton;
    EditText edTextMonto;
    EditText mail;
    EditText cuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar1=(SeekBar)findViewById(R.id.seekbar_cantidad_dias);
        dias = (TextView)findViewById(R.id.dias);
        dias.setText("0");
        cantidad = (TextView) findViewById(R.id.cantidad);
        boton = (Button) findViewById(R.id.botonPlazoFijo);
        edTextMonto = (EditText) findViewById(R.id.importe2);
        resultado = (TextView) findViewById(R.id.resultadoFinal);
        mail = (EditText) findViewById(R.id.email);
        cuit = (EditText) findViewById(R.id.cuit2);


        edTextMonto.setOnKeyListener(new View.OnKeyListener() {
           public boolean onKey(View view, int i, KeyEvent keyEvent) {
               try{
                   cantidad.setText("$" + calcularValor());
               }
               catch (NumberFormatException e){
                   cantidad.setText("$");
               }
               return false;
            }
        });
        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
                dias.setText(String.valueOf(progress));
                try{
                    cantidad.setText("$" + calcularValor());
                }
                catch (NumberFormatException e){
                    cantidad.setText("$");
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        boton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                try{
                    if(mail.getText().toString().equals("") || cuit.getText().toString().equals("") || edTextMonto.getText().toString().equals("")) {
                        resultado.setTextColor(getResources().getColor(R.color.error));
                        resultado.setText("Ocurrió un error, faltan datos");
                    }
                    else{
                        resultado.setTextColor(getResources().getColor(R.color.correcto));
                        resultado.setText("Plazo fijo realizado. Recibirá " + cantidad.getText() + " al vencimiento");
                    }
                }
                catch (Exception e){

                }

            }
        });
    }
    protected String calcularValor(){
        float interes;
        double tasa = 0;
        interes = 0;
        double dias = 0;
        double monto = Integer.valueOf(edTextMonto.getText().toString());
        switch (caso()){
            case 1:
                tasa = Double.valueOf(getString(R.string.caso1));
                break;
            case 2:
                tasa = Double.valueOf(getString(R.string.caso2));
                break;
            case 3:
                tasa = Double.valueOf(getString(R.string.caso3));
                break;
            case 4:
                tasa = Double.valueOf(getString(R.string.caso4));
                break;
            case 5:
                tasa = Double.valueOf(getString(R.string.caso5));
                break;
            case 6:
                tasa = Double.valueOf(getString(R.string.caso6));
                break;
        }
        dias = Double.valueOf(this.dias.getText().toString());
        interes = (float) (monto * (Math.pow((1+tasa),(dias/360)) - 1));
        return String.valueOf(interes);
    }
    private int caso(){
        int caso = 0;
        if(!edTextMonto.getText().equals(null)){
            int monto = Integer.valueOf(edTextMonto.getText().toString());
            if (seekBar1.getProgress()<30){
               if(monto < 5000){
                   caso = 1;
               }
               else if(monto < 99999){
                   caso = 3;
               }
               else{
                   caso = 5;
               }
            }
            else{
                if(monto < 5000){
                    caso = 2;
                }
                else if(monto < 99999){
                    caso = 4;
                }
                else{
                    caso = 6;
                }
            }
        }
        return caso;
    }
}